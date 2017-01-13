package cn.ucai.fulicenter.controller.fragment;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.controller.adapter.CategoryAdapter;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.net.IModelCategory;
import cn.ucai.fulicenter.model.net.ModelCategory;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;

public class CategoryFragment extends Fragment {


    @BindView(R.id.expandListView)
    ExpandableListView expandListView;
    IModelCategory model;
    int groupCount;
    CategoryAdapter adapter;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, layout);
        model = new ModelCategory();
        mGroupList = new ArrayList<>();
        mChildList = new ArrayList<>();
        adapter = new CategoryAdapter(getContext(), mGroupList, mChildList);
        expandListView.setAdapter(adapter);
        expandListView.setGroupIndicator(null);
        initView();
        initData();
        return layout;
    }

    private void initData() {
        model.getCategoryGroupData(getContext(), new OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if (result != null) {
                    expandListView.setVisibility(View.VISIBLE);
                    ArrayList<CategoryGroupBean> categoryGroupList = ConvertUtils.array2List(result);
                    mGroupList.addAll(categoryGroupList);
                    for (int i = 0; i < categoryGroupList.size(); i++) {
                        mChildList.add(new ArrayList<CategoryChildBean>());
                        downloadChildData(categoryGroupList.get(i).getId(),i);
                        Log.e("main", categoryGroupList.get(i).getId() + "");
                    }
                } else {
                    initView();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void downloadChildData(int groupId, final int index) {
        model.getCategoryChildData(getContext(), groupId, new OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                groupCount++;
                if (result != null) {
                    ArrayList<CategoryChildBean> list = ConvertUtils.array2List(result);
                    mChildList.set(index,list);
                }
                if (groupCount == mGroupList.size()) {
                    adapter.initData(mGroupList, mChildList);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initView() {
        expandListView.setVisibility(View.VISIBLE);
    }
}
