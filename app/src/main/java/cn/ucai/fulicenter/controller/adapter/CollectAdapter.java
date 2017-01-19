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
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.view.FooterViewHolder;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class CollectAdapter extends RecyclerView.Adapter {
    Context context;
    List<CollectBean> mGoodsList;
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

    public CollectAdapter(final Context context, List<CollectBean> mGoods) {
        this.context = context;
        mGoodsList = new ArrayList<>();
        this.mGoodsList.addAll(mGoods);
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cartId = (int) v.getTag(R.id.tag_first);
                Intent intent = new Intent(context, BoutiqueChildActivity.class);
                intent.putExtra(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS, cartId);
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
            layout = LayoutInflater.from(context).inflate(R.layout.item_collect, null);
            layout.setOnClickListener(listener);
            return new CollectViewHolder(layout);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterViewHolder fv = (FooterViewHolder) holder;
            fv.tvFooter.setText(getFooter());
            return;
        }
        CollectViewHolder gvh = null;
        if (holder != null) {
            gvh = (CollectViewHolder) holder;
        }
        final CollectBean mCollectBean = mGoodsList.get(position);
        ImageLoader.downloadImg(context, gvh.mIvGoodsThumb, mCollectBean.getGoodsThumb());
        gvh.mTvGoodName.setText(mCollectBean.getGoodsName());
        gvh.itemView.setTag(R.id.tag_first, mCollectBean.getId());
        gvh.itemView.setTag(R.id.tag_second, mCollectBean.getGoodsName());
        gvh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MFGT.gotoGoodsDetails(context, mCollectBean.getGoodsId());
            }
        });

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
        return mGoodsList.size() + 1;
    }

    public void initData(ArrayList<CollectBean> mList) {
        if (mList != null) {
            this.mGoodsList.clear();
        }
        addList(mList);

    }

    public void addList(ArrayList<CollectBean> mList) {
        this.mGoodsList.addAll(mList);
        notifyDataSetChanged();
    }


    static class CollectViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivGoodsThumb)
        ImageView mIvGoodsThumb;
        @BindView(R.id.tvGoodName)
        TextView mTvGoodName;
        @BindView(R.id.ivDelete)
        ImageView mIvDelete;

        CollectViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
