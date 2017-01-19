package cn.ucai.fulicenter.controller.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuliCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.adapter.CollectAdapter;
import cn.ucai.fulicenter.controller.adapter.GoodsAdapter;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.DisplayUtils;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.model.utils.SpaceItemDecoration;

public class CollectActivity extends AppCompatActivity {

    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.tvRefresh)
    TextView mTvRefresh;
    @BindView(R.id.recy_new_goods)
    RecyclerView mRecyNewGoods;
    int pageId = 1;
    ArrayList<CollectBean> mList;
    IModelUser model;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;
    CollectAdapter mAdapter;
    GridLayoutManager mManager;
    User mUser;
    UpdateCollectReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        mUser = FuliCenterApplication.getUser();
        if (mUser == null) {
            MFGT.gotoLogin(this);
            finish();
        } else {
            initView();
            initData(pageId,I.ACTION_DOWNLOAD);
            setListener();
            registerReceiver();
        }
    }

    private void registerReceiver() {
        mReceiver = new UpdateCollectReceiver();
        IntentFilter fliter = new IntentFilter(I.BROADCAST_UPDATA_COLLECT);
        registerReceiver(mReceiver,fliter);
    }

    private void setListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(true);
                mTvRefresh.setVisibility(View.VISIBLE);
                pageId = 1;
                initData(pageId, I.ACTION_PULL_DOWN);
            }
        });
        mRecyNewGoods.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mAdapter.setDragging(newState == RecyclerView.SCROLL_STATE_DRAGGING);
                int lastposition = mManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mAdapter.isMore() && lastposition == mAdapter.getItemCount() - 1) {
                    pageId++;
                    initData(pageId, I.ACTION_PULL_UP);
                }
            }
        });
    }

    private void initData(int pageId, final int action) {
        model = new ModelUser();
        model.getCollect(this, mUser.getMuserName(), pageId, I.PAGE_SIZE_DEFAULT, new OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                mSrl.setRefreshing(false);
                mTvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(result != null && result.length > 0);
                if (!mAdapter.isMore()) {
                    mAdapter.setFooter("没有更多的数据");
                    return;
                }
                mAdapter.setFooter("加载更多的数据");
                if (result != null) {
                    ArrayList<CollectBean> list = ConvertUtils.array2List(result);
                    switch (action) {
                        case I.ACTION_DOWNLOAD:
                            mAdapter.initData(list);
                            break;
                        case I.ACTION_PULL_DOWN:
                            mAdapter.initData(list);
                            break;
                        case I.ACTION_PULL_UP:
                            mAdapter.addList(list);
                            break;
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView() {
        mSrl.setColorSchemeColors(getResources().getColor(R.color.blue),
                getResources().getColor(R.color.red),
                getResources().getColor(R.color.green));
        mList = new ArrayList<>();
        mAdapter = new CollectAdapter(this, mList);
        mManager = new GridLayoutManager(this, 2);
        mManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mAdapter.getItemViewType(position)) {
                    case I.TYPE_FOOTER:
                        return 2;
                    case I.TYPE_ITEM:
                        return 1;
                    default:
                        return 1;
                }
            }
        });
        mRecyNewGoods.setLayoutManager(mManager);
        mRecyNewGoods.addItemDecoration(new SpaceItemDecoration(30));

        mRecyNewGoods.setAdapter(mAdapter);
        DisplayUtils.initBackWithTitle(this, "个人收藏");
    }
    class UpdateCollectReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int goodsId = intent.getIntExtra(I.Collect.GOODS_ID,0);
            mAdapter.removeItem(goodsId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver==null){
            unregisterReceiver(mReceiver);
        }
    }
}
