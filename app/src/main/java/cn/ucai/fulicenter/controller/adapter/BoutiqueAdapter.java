package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.view.FooterViewHolder;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter {
    Context context;
    List<BoutiqueBean> mGoodsList;
    String footer;
    boolean isMore;
    boolean isDragging;

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public boolean isDragging() {
        return isDragging;
    }

    public void setDragging(boolean dragging) {
        isDragging = dragging;
        notifyDataSetChanged();
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
        notifyDataSetChanged();
    }

    public BoutiqueAdapter(Context context, List<BoutiqueBean> mGoods) {
        this.context = context;
        mGoodsList = new ArrayList<>();
        this.mGoodsList.addAll(mGoods);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = null;
        if (viewType == I.TYPE_FOOTER) {
            layout = LayoutInflater.from(context).inflate(R.layout.footer_item, null);
            return new FooterViewHolder(layout);
        } else {
            layout = LayoutInflater.from(context).inflate(R.layout.boutique_adapter, null);
            return new BoutiqueViewHolder(layout);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==I.TYPE_FOOTER){
            FooterViewHolder fv = (FooterViewHolder) holder;
            fv.tvFooter.setText(getFooter());
            return;
        }
        BoutiqueViewHolder bvh = (BoutiqueViewHolder) holder;
        BoutiqueBean boutiqueBean = mGoodsList.get(position);
        ImageLoader.downloadImg(context,bvh.ivBoutiqueGoodsThumb,boutiqueBean.getImageurl());
        bvh.tvTitle.setText(boutiqueBean.getTitle());
        bvh.tvDescription.setText(boutiqueBean.getDescription());
        bvh.tvName.setText(boutiqueBean.getName());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mGoodsList.size()+1;
    }

    public void initData(ArrayList<BoutiqueBean> mList) {
        if (mList != null) {
            this.mGoodsList.clear();
        }
        addList(mList);
    }

    public void addList(ArrayList<BoutiqueBean> mList) {
        this.mGoodsList.addAll(mList);
        notifyDataSetChanged();
    }

    static class BoutiqueViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_boutique_goodsThumb)
        ImageView ivBoutiqueGoodsThumb;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvDescription)
        TextView tvDescription;

        BoutiqueViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
