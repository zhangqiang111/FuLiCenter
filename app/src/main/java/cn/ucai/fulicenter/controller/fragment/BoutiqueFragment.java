package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.net.IModelBoutique;
import cn.ucai.fulicenter.model.net.ModelBoutique;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueFragment extends Fragment {


    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    @BindView(R.id.recy_new_goods)
    RecyclerView recyNewGoods;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    ArrayList<BoutiqueBean> mList;
    BoutiqueAdapter adapter;
    LinearLayoutManager manager;
    IModelBoutique model;
    @BindView(R.id.tvHint)
    TextView tvHint;

    public BoutiqueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_boutique, container, false);
        ButterKnife.bind(this, layout);
        model = new ModelBoutique();
        iniView();
        getData(I.ACTION_DOWNLOAD);
        setListener();
        return layout;
    }

    private void setListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                tvRefresh.setVisibility(View.VISIBLE);
                getData(I.ACTION_PULL_DOWN);
            }
        });
    }

    private void getData(final int action) {
        model.downloadBoutique(getContext(), new OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                tvHint.setVisibility(View.GONE);
                srl.setVisibility(View.VISIBLE);
                switch (action) {
                    case I.ACTION_DOWNLOAD:
                        adapter.initData(list);
                        break;
                    case I.ACTION_PULL_DOWN:
                        srl.setRefreshing(false);
                        tvRefresh.setVisibility(View.GONE);
                        adapter.initData(list);
                        break;
                }
            }

            @Override
            public void onError(String error) {
            }
        });
    }

    private void iniView() {
        tvHint.setVisibility(View.VISIBLE);
        srl.setVisibility(View.GONE);
        mList = new ArrayList<>();
        adapter = new BoutiqueAdapter(getContext(), mList);
        manager = new LinearLayoutManager(getContext());
        recyNewGoods.setLayoutManager(manager);
        recyNewGoods.addItemDecoration(new SpaceItemDecoration(40));
        recyNewGoods.setAdapter(adapter);
    }

}
