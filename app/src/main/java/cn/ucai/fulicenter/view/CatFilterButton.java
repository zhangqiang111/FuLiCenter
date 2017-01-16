package cn.ucai.fulicenter.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.MFGT;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public class CatFilterButton extends Button {
    boolean isExpand;
    PopupWindow mPopupWindow;
    Context mContext;
    CatFilterAdapter adapter;
    GridView mGridView;
    String groupName;

    public CatFilterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void initCatFilterButton(String groupName, ArrayList<CategoryChildBean> list) {
        this.setText(groupName);
        this.groupName = groupName;
        setCatFilterButtonListener();
        adapter = new CatFilterAdapter(mContext, list);
        initGridView();
    }

    private void initGridView() {
        mGridView = new GridView(mContext);
        mGridView.setVerticalSpacing(10);
        mGridView.setHorizontalSpacing(10);
        mGridView.setNumColumns(GridView.AUTO_FIT);
        mGridView.setAdapter(adapter);
    }

    private void setCatFilterButtonListener() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isExpand) {
                    initPopupWindow();

                } else {
                    if (mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                }
                setArrow();
            }
        });
    }

    private void initPopupWindow() {
        mPopupWindow = new PopupWindow();
        mPopupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xbb000000));
        mPopupWindow.setContentView(mGridView);
        mPopupWindow.showAsDropDown(this);
    }

    private void setArrow() {
        Drawable right;
        if (!isExpand) {
            right = getResources().getDrawable(R.mipmap.arrow2_down);
        } else {
            right = getResources().getDrawable(R.mipmap.arrow2_up);
        }
        isExpand = !isExpand;
        right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
        this.setCompoundDrawablesWithIntrinsicBounds(null, null, right, null);
    }

    class CatFilterAdapter extends BaseAdapter {
        Context context;
        ArrayList<CategoryChildBean> mList;


        public CatFilterAdapter(Context mContext, ArrayList<CategoryChildBean> mList) {
            this.context = mContext;
            this.mList = mList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public CategoryChildBean getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CatFilterButtonViewHolder cfbvh;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cat_filter, null);
                cfbvh = new CatFilterButtonViewHolder(convertView);
                convertView.setTag(cfbvh);
            } else {
                cfbvh = (CatFilterButtonViewHolder) convertView.getTag();
            }
            cfbvh.bind(position);
            return convertView;
        }

        class CatFilterButtonViewHolder {
            @BindView(R.id.ivNewCategoryGroup)
            ImageView ivNewCategoryGroup;
            @BindView(R.id.tvNewCategoryGroupName)
            TextView tvNewCategoryGroupName;
            @BindView(R.id.Layout_Category_NEW)
            RelativeLayout LayoutCategoryNEW;

            CatFilterButtonViewHolder(View view) {
                ButterKnife.bind(this, view);
            }

            public void bind(final int position) {
                ImageLoader.downloadImg(context, ivNewCategoryGroup, mList.get(position).getImageUrl());
                tvNewCategoryGroupName.setText(mList.get(position).getName());
                LayoutCategoryNEW.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MFGT.gotoCategoryChild(mContext, mList.get(position).getId(), groupName, mList);
                        MFGT.finish((Activity) mContext);
                    }
                });
            }
        }
    }
}
