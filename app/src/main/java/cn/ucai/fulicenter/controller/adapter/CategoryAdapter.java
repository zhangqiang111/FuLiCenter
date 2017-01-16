package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.MFGT;

/**
 * Created by Administrator on 2017/1/12 0012.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context context;
    ArrayList<CategoryGroupBean> mAGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mAChildList;

    public CategoryAdapter(Context context, ArrayList<CategoryGroupBean> mGroupList, ArrayList<ArrayList<CategoryChildBean>> mChildList) {
        this.context = context;
        mAGroupList = new ArrayList<>();
        mAGroupList.addAll(mGroupList);
        mAChildList = new ArrayList<>();
        mAChildList.addAll(mChildList);
    }

    @Override
    public int getGroupCount() {
        return mAGroupList != null ? mAGroupList.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mAChildList != null ? mAChildList.get(groupPosition).size() : 0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        if (mAGroupList != null && mAGroupList.get(groupPosition) != null) {
            return mAGroupList.get(groupPosition);
        }
        return null;
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        if (mAChildList != null && mAChildList.get(groupPosition).get(childPosition) != null) {
            return mAChildList.get(groupPosition).get(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder gvh = null;
        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.category_group, null);
            convertView = View.inflate(context,R.layout.category_group,null);
            gvh = new GroupViewHolder(convertView);
            convertView.setTag(gvh);
        } else {
            gvh = (GroupViewHolder) convertView.getTag();
        }
        final CategoryGroupBean ggb = mAGroupList.get(groupPosition);
        ImageLoader.downloadImg(context, gvh.ivCategoryGroup, ggb.getImageUrl());
        gvh.tvCategoryGroupName.setText(ggb.getName());
        gvh.ivCategoryGroupArray.setImageResource(isExpanded ? R.mipmap.expand_off : R.mipmap.expand_on);

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder cvh;
        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.category_child, null);
            convertView = View.inflate(context,R.layout.category_child,null);
            cvh = new ChildViewHolder(convertView);
            convertView.setTag(cvh);
        } else {
            cvh = (ChildViewHolder) convertView.getTag();
        }
        cvh.tvCategoryChildName.setText(getChild(groupPosition,childPosition).getName());
        ImageLoader.downloadImg(context, cvh.ivCategoryChild,getChild(groupPosition,childPosition).getImageUrl());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MFGT.gotoCategoryChild(context,getChild(groupPosition,childPosition).getId(),getChild(groupPosition,childPosition).getName());
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void initData(ArrayList<CategoryGroupBean> mGroupList, ArrayList<ArrayList<CategoryChildBean>> mChildList) {
        mAGroupList.clear();
        mAGroupList.addAll(mGroupList);
        mAChildList.clear();
        mAChildList.addAll(mChildList);
        notifyDataSetChanged();
    }

    static class GroupViewHolder {
        @BindView(R.id.ivCategoryGroup)
        ImageView ivCategoryGroup;
        @BindView(R.id.tvCategoryGroupName)
        TextView tvCategoryGroupName;
        @BindView(R.id.ivCategoryGroupArray)
        ImageView ivCategoryGroupArray;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.ivCategoryChild)
        ImageView ivCategoryChild;
        @BindView(R.id.tvCategoryChildName)
        TextView tvCategoryChildName;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
