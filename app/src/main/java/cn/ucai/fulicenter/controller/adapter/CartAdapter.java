package cn.ucai.fulicenter.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.utils.ImageLoader;

/**
 * Created by Administrator on 2017/1/20 0020.
 */

public class CartAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<CartBean> mList;


    public CartAdapter(Context context, ArrayList<CartBean> list) {
        mContext = context;
        mList = list;
        mList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.item_cart, null);
        return new CartViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            CartViewHolder cvh = (CartViewHolder) holder;
            cvh.bind(position);
        }
    }


    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public void initData(ArrayList<CartBean> list) {
        if (mList != null) {
            mList.clear();

        }
        mList.addAll(list);
        notifyDataSetChanged();
    }


    class CartViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_image)
        ImageView mIvImage;
        @BindView(R.id.tv_count)
        TextView mTvCount;
        @BindView(R.id.tv_money)
        TextView mTvMoney;
        @BindView(R.id.cb_select)
        CheckBox mCbSelect;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(int position) {
            GoodsDetailsBean details = mList.get(position).getGoods();
            if (details != null) {
                ImageLoader.downloadImg(mContext, mIvImage, mList.get(position).getGoods().getGoodsThumb());
                mTvCount.setText("(" + mList.get(position).getCount() + ")");
                mTvMoney.setText(mList.get(position).getGoods().getCurrencyPrice());
            }
            mCbSelect.setChecked(mList.get(position).isChecked());

        }

        @OnCheckedChanged(R.id.cb_select)
        public void onChecked(boolean checked) {
            if (checked) {
                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
            }
        }

    }

}
