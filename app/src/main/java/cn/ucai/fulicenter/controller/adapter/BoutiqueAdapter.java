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
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.MFGT;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class BoutiqueAdapter extends RecyclerView.Adapter {
    Context context;
    List<BoutiqueBean> mGoodsList;
    String footer;
    boolean isMore;
    boolean isDragging;
//    View.OnClickListener listener;

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

    public BoutiqueAdapter(final Context context, List<BoutiqueBean> mGoods) {
        this.context = context;
        mGoodsList = new ArrayList<>();
        this.mGoodsList.addAll(mGoods);
        /*listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cartId = (int) v.getTag(R.id.tag_first);
                String name = (String) v.getTag(R.id.tag_second);
                Intent intent = new Intent(context, BoutiqueChildActivity.class);
                intent.putExtra(I.REQUEST_FIND_NEW_BOUTIQUE_GOODS,cartId);
                intent.putExtra("goodName",name);
                context.startActivity(intent);
            }
        };*/
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.boutique_adapter, null);
//        layout.setOnClickListener(listener);
        return new BoutiqueViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BoutiqueViewHolder bv = (BoutiqueViewHolder) holder;
        final BoutiqueBean boutiqueBean = mGoodsList.get(position);
        bv.tvName.setText(boutiqueBean.getName());
        bv.tvDescription.setText(boutiqueBean.getDescription());
        bv.tvTitle.setText(boutiqueBean.getTitle());
        ImageLoader.downloadImg(context, bv.ivBoutiqueGoodsThumb,boutiqueBean.getImageurl());
       /* bv.itemView.setTag(R.id.tag_first,boutiqueBean.getId());
        bv.itemView.setTag(R.id.tag_second,boutiqueBean.getName());*/
        bv.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MFGT.gotoBoutiqueChild(context,boutiqueBean);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mGoodsList.size();
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
