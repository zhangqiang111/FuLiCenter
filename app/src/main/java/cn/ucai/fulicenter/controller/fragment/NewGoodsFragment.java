package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {


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
    MainActivity context;
    public NewGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, layout);
        context = (MainActivity) getContext();
        initView();
        initData();
        return layout;
    }

    private void initView() {
        mList = new ArrayList<>();
        adapter = new GoodsAdapter(getContext(), mList);
        manager = new GridLayoutManager(context, I.COLUM_NUM);
        recyNewGoods.setLayoutManager(manager);
        recyNewGoods.setHasFixedSize(true);
        recyNewGoods.setAdapter(adapter);
    }

    private void initData() {
        model = new ModelNewGoods();
        model.downloadNewGoods(context, I.CAT_ID, pageId, new OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                Log.e("main",list.toString());
                adapter.initData(list);
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
