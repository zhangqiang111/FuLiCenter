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
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuliCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.activity.BoutiqueChildActivity;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.net.IModelGoodsDetails;
import cn.ucai.fulicenter.model.net.ModelGoodsDetails;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
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
    IModelGoodsDetails model;

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
        model = new ModelGoodsDetails();
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
        gvh.bind(position);

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

    public void removeItem(int goodsId) {
        if (goodsId!=0){
            mGoodsList.remove(new CollectBean(goodsId));
            notifyDataSetChanged();
        }
    }


    class CollectViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivGoodsThumb)
        ImageView mIvGoodsThumb;
        @BindView(R.id.tvGoodName)
        TextView mTvGoodName;
        @BindView(R.id.ivDelete)
        ImageView mIvDelete;
        int itemPositon;

        CollectViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(int position) {
            ImageLoader.downloadImg(context, mIvGoodsThumb, mGoodsList.get(position).getGoodsThumb());
            mTvGoodName.setText(mGoodsList.get(position).getGoodsName());
            itemPositon = position;
        }
        @OnClick({R.id.ivDelete, R.id.rl_layout})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ivDelete:
                    model.setCollect(context, mGoodsList.get(itemPositon).getGoodsId(), FuliCenterApplication.getUser().getMuserName(), I.ACTION_DELETE_COLLECT, new OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if (result!=null&&result.isSuccess()){
                                mGoodsList.remove(itemPositon);
                                notifyDataSetChanged();

                            }
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });

                    break;
                case R.id.rl_layout:
                    MFGT.gotoGoodsDetails(context,mGoodsList.get(itemPositon).getGoodsId());
                    break;
            }
        }
    }
}
