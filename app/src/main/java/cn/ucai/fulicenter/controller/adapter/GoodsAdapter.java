package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
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
import cn.ucai.fulicenter.controller.activity.BoutiqueChildActivity;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.view.FooterViewHolder;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class GoodsAdapter extends RecyclerView.Adapter {
    Context context;
    List<NewGoodsBean> mGoodsList;
    String footer;
    boolean isMore;
    boolean isDragging;
    View.OnClickListener listener;

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

    public GoodsAdapter(final Context context, List<NewGoodsBean> mGoods) {
        this.context = context;
        mGoodsList = new ArrayList<>();
        this.mGoodsList.addAll(mGoods);
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cartId = (int) v.getTag(R.id.tag_first);
                Intent intent = new Intent(context, BoutiqueChildActivity.class);
                intent.putExtra(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS,cartId);
                context.startActivity(intent);
            }
        };
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = null;
        if (viewType == I.TYPE_FOOTER) {
//            layout = LayoutInflater.from(context).inflate(R.layout.footer_item,parent,false);
            layout = LayoutInflater.from(context).inflate(R.layout.footer_item, null);
            return new FooterViewHolder(layout);
        } else {
            layout = LayoutInflater.from(context).inflate(R.layout.goods_adapter, null);
            layout.setOnClickListener(listener);
            return new GoodsViewHolder(layout);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==I.TYPE_FOOTER){
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
        gvh.itemView.setTag(R.id.tag_first,goodsDetailsBean.getCatId());
        gvh.itemView.setTag(R.id.tag_second,goodsDetailsBean.getGoodsName());

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
