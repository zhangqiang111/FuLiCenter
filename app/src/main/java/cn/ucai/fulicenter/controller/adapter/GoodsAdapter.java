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
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class GoodsAdapter extends RecyclerView.Adapter {
    final int TYPE_FOOTER = 0;
    final int TYPE_CONTACT = 1;
    Context context;
    List<NewGoodsBean> mGoodsList;
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

    public GoodsAdapter(Context context, List<NewGoodsBean> mGoods) {
        this.context = context;
        mGoodsList = new ArrayList<>();
        this.mGoodsList.addAll(mGoods);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = null;
        if (viewType == TYPE_FOOTER) {
            layout = LayoutInflater.from(context).inflate(R.layout.footer_item, null);
            return new FooterViewHolder(layout);
        } else {
            layout = LayoutInflater.from(context).inflate(R.layout.goods_adapter, null);
            return new GoodsViewHolder(layout);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==TYPE_FOOTER){
            FooterViewHolder fv = (FooterViewHolder) holder;
            fv.tvFooter.setText(getFooter());
            return;
        }
        GoodsViewHolder gvh = null;
        if (holder != null) {
            gvh = (GoodsViewHolder) holder;
        }
        NewGoodsBean goodsDetailsBean = mGoodsList.get(position);
        ImageLoader.downloadImg(context, gvh.ivGoodsThumb, goodsDetailsBean.getGoodsThumb());
        gvh.tvGoodName.setText(goodsDetailsBean.getGoodsName());
        gvh.tvGoodsPrice.setText(goodsDetailsBean.getCurrencyPrice());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_CONTACT;
    }

    @Override
    public int getItemCount() {
        return mGoodsList.size()+1;
    }

    public void initData(ArrayList<NewGoodsBean> mList) {
        if (mList != null) {
            this.mGoodsList.clear();
        }
        addList(mList);
    }

    public void addList(ArrayList<NewGoodsBean> mList) {
        this.mGoodsList.addAll(mList);
        notifyDataSetChanged();
    }

    static class GoodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodName)
        TextView tvGoodName;
        @BindView(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvFooter)
        TextView tvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /*class GoodsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoods;
        @BindView(R.id.tvGoodName)
        TextView tvGoodName;
        @BindView(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;

        public GoodsViewHolder(View itemView) {
            super(itemView);
        }
    }*/

}
