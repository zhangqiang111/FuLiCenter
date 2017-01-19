package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.LoaderTestCase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuliCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.model.utils.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    IModelUser model;
    @BindView(R.id.bt_account)
    Button mBtAccount;
    @BindView(R.id.tvSum)
    TextView mTvSum;
    @BindView(R.id.tvRefresh)
    TextView mTvRefresh;
    @BindView(R.id.recy_new_goods)
    RecyclerView mRecyNewGoods;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;
    User mUser;
    LinearLayoutManager manager;
    ArrayList<CartBean> mList;
    public CartFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, layout);
        model = new ModelUser();
        mUser = FuliCenterApplication.getUser();
        iniView();
        initData(I.ACTION_DOWNLOAD);
        setListener();
        return layout;
    }

    private void initData(final int action) {
        if (mUser==null){
            MFGT.gotoLogin(getActivity());
        }else {
            model.getCart(getContext(), mUser.getMuserName(), new OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    mSrl.setRefreshing(false);
                    mTvRefresh.setVisibility(View.GONE);
                   /* mAdapter.setMore(result != null && result.length > 0);
                    if (!mAdapter.isMore()) {
                        mAdapter.setFooter("没有更多的数据");
                        return;
                    }
                    mAdapter.setFooter("加载更多的数据");*/
                    if (result != null) {
                        Log.e("Result:",result.toString());
                        ArrayList<CartBean> list = ConvertUtils.array2List(result);
                        Log.e(">>>>>>>>>>","Cart list "+list.size());
                        switch (action) {
                            case I.ACTION_DOWNLOAD:
//                                mAdapter.initData(list);
                                break;
                            case I.ACTION_PULL_DOWN:
//                                mAdapter.initData(list);
                                break;
                            case I.ACTION_PULL_UP:
//                                mAdapter.addList(list);
                                break;
                        }
                    }
                }

                @Override
                public void onError(String error) {

                }
            });
        }
    }

    private void setListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(true);
                mTvRefresh.setVisibility(View.VISIBLE);
                initData(I.ACTION_PULL_DOWN);
            }
        });
    }


    private void iniView() {
//        tvHint.setVisibility(View.VISIBLE);
        mSrl.setVisibility(View.GONE);
        mList = new ArrayList<>();
//        adapter = new BoutiqueAdapter(getContext(), mList);
        manager = new LinearLayoutManager(getContext());
        mRecyNewGoods.setLayoutManager(manager);
        mRecyNewGoods.addItemDecoration(new SpaceItemDecoration(40));
//        mRecyNewGoods.setAdapter(adapter);
    }


}
