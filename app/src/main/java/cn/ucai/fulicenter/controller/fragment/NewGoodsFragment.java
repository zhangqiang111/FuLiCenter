package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.activity.MainActivity;
import cn.ucai.fulicenter.controller.adapter.GoodsAdapter;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.net.IModelNewGoods;
import cn.ucai.fulicenter.model.net.ModelNewGoods;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {
    final int ACTION_DOWNLOAD = 0;
    final int ACTION_PULL_UP = 1;
    final int ACTION_PULL_DOWN = 2;
    @BindView(R.id.recy_new_goods)
    RecyclerView recyNewGoods;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    int pageId = 1;
    GridLayoutManager manager;
    GoodsAdapter adapter;
    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    IModelNewGoods model;
    ArrayList<NewGoodsBean> mList;

    public NewGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, layout);
        model = new ModelNewGoods();
        initView();
        getData(pageId, ACTION_DOWNLOAD);
        setListener();
        return layout;
    }

    public void sortGoods(int sortBy) {
        adapter.sort(sortBy);
    }

    private void setListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                tvRefresh.setVisibility(View.VISIBLE);
                pageId = 1;
                getData(pageId, ACTION_PULL_DOWN);
            }
        });
        recyNewGoods.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                adapter.setDragging(newState == RecyclerView.SCROLL_STATE_DRAGGING);
                int lastposition = manager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && adapter.isMore() && lastposition == adapter.getItemCount()-1) {
                    pageId++;
                    getData(pageId, ACTION_PULL_UP);
                }
                if (!adapter.isMore()){
                    getData(pageId,ACTION_PULL_UP);
                }
            }
        });
    }

    private void initView() {
        mList = new ArrayList<>();
        adapter = new GoodsAdapter(getContext(), mList);
        manager = new GridLayoutManager(getContext(), I.COLUM_NUM);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case I.TYPE_FOOTER:
                        return 2;
                    case I.TYPE_ITEM:
                        return 1;
                    default:
                        return 1;
                }
            }
        });
        recyNewGoods.setLayoutManager(manager);
        recyNewGoods.addItemDecoration(new SpaceItemDecoration(30));
        recyNewGoods.setHasFixedSize(true);
        recyNewGoods.setAdapter(adapter);
    }

    private void getData(int pageId, final int action) {
        int cartId = getActivity().getIntent().getIntExtra(I.NewAndBoutiqueGoods.CAT_ID, I.CAT_ID);
        model.downloadNewGoods(getContext(), cartId, pageId, new OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                adapter.setMore(result != null && result.length > 0);
                if (!adapter.isMore()) {
//                    Toast.makeText(getContext(), "别扯了,没了",Toast.LENGTH_LONG).show();
//                    adapter.setFooter("没有更多的数据");
                    setTask();
                    return;
                }
                adapter.setFooter("加载更多的数据");
                ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                switch (action) {
                    case ACTION_DOWNLOAD:
                        adapter.initData(list);
                        break;
                    case ACTION_PULL_DOWN:
                        srl.setRefreshing(false);
                        tvRefresh.setVisibility(View.GONE);
                        adapter.initData(list);
                        break;
                    case ACTION_PULL_UP:
                        adapter.addList(list);
                        break;
                }
            }

            @Override
            public void onError(String error) {
            }
        });
    }

    private void setTask() {
//        final Toast toast = Toast.makeText(getContext(), info, Toast.LENGTH_SHORT);
        final Toast toast1= new Toast(getContext());
        View parent = LayoutInflater.from(getContext()).inflate(R.layout.item_toast,null);
        TextView textView = (TextView) parent.findViewById(R.id.tvToast);
        toast1.setView(parent);
        toast1.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                toast1.cancel();
            }
        }, 1000);

    }
}
