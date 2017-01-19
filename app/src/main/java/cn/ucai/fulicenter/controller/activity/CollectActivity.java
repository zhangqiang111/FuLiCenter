package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuliCenterApplication;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        User user = FuliCenterApplication.getUser();
        if (user == null) {
            MFGT.gotoLogin(this);
            finish();
        } else {
            initView();
            initData(user.getMuserName());
        }
    }

    private void initData(String muserName) {
        model = new ModelUser();
        model.getCollect(this, muserName, pageId, 10, new OnCompleteListener<CollectBean[]>() {
            @Override
            public void onSuccess(CollectBean[] result) {
                if (result != null) {
                    ArrayList<CollectBean> list = ConvertUtils.array2List(result);
                    mAdapter.initData(list);
                    Log.e(">>>>>>",list.size()+"");
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
        mAdapter = new CollectAdapter(this,mList);
        mManager = new GridLayoutManager(this,2);
        mRecyNewGoods.setLayoutManager(mManager);
        mRecyNewGoods.addItemDecoration(new SpaceItemDecoration(30));

        mRecyNewGoods.setAdapter(mAdapter);
        DisplayUtils.initBackWithTitle(this, "个人收藏");
    }
}
