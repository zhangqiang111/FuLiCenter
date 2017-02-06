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
import cn.ucai.fulicenter.application.FuliCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ImageLoader;

/**
 * Created by Administrator on 2017/1/20 0020.
 */

public class CartAdapter extends RecyclerView.Adapter {
    Context mContext;
    ArrayList<CartBean> mList;
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    @BindView(R.id.iv_del)
    ImageView mIvDel;
    IModelUser model;
    User mUser;


    public CartAdapter(Context context, ArrayList<CartBean> list) {
        mContext = context;
        mList = list;
        model = new ModelUser();
        mUser = FuliCenterApplication.getUser();
//        mList.addAll(list);
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
        return mList == null ? 0 : mList.size();
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
        @BindView(R.id.tvGoodsName)
        TextView mTvGoodsName;
        int listPossition;

        CartViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(int position) {
            listPossition = position;
            GoodsDetailsBean details = mList.get(position).getGoods();
            if (details != null) {
                ImageLoader.downloadImg(mContext, mIvImage, mList.get(position).getGoods().getGoodsThumb());
                mTvCount.setText("(" + mList.get(position).getCount() + ")");
                mTvMoney.setText(mList.get(position).getGoods().getCurrencyPrice());
                mTvGoodsName.setText(mList.get(position).getGoods().getGoodsName());
            }
            mCbSelect.setChecked(mList.get(listPossition).isChecked());

        }

        @OnCheckedChanged(R.id.cb_select)
        public void onChecked(boolean checked) {
            mList.get(listPossition).setChecked(checked);
            mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
        }

        @OnClick({R.id.iv_add, R.id.iv_del})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_add:
                    model.updateCart(mContext, I.ACTION_CART_ADD, mUser.getMuserName(), mList.get(listPossition).getGoodsId(), 1,
                            mList.get(listPossition).getId(), new OnCompleteListener<MessageBean>() {
                                @Override
                                public void onSuccess(MessageBean result) {
                                    if (result != null && result.isSuccess()) {
                                        mList.get(listPossition).setCount(mList.get(listPossition).getCount() + 1);
                                        mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                                    }
                                }

                                @Override
                                public void onError(String error) {

                                }
                            });
                    break;
                case R.id.iv_del:
                    final int count = mList.get(listPossition).getCount();
                    int action = I.ACTION_CART_UPDATE;
                    if (count > 1) {
                        action = I.ACTION_CART_UPDATE;
                    } else {
                        action = I.ACTION_CART_DEL;
                    }
                        model.updateCart(mContext, action, mUser.getMuserName(),
                                mList.get(listPossition).getGoodsId(), count - 1, mList.get(listPossition).getId(), new OnCompleteListener<MessageBean>() {
                                    @Override
                                    public void onSuccess(MessageBean result) {
                                        if (result != null && result.isSuccess()) {
                                            if (count <= 1) {
                                                mList.remove(listPossition);
                                            } else {
                                                mList.get(listPossition).setCount(count - 1);
                                                mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                                            }
                                            mContext.sendBroadcast(new Intent(I.BROADCAST_UPDATA_CART));
                                        }
                                    }

                                    @Override
                                    public void onError(String error) {

                                    }
                                });
                    break;
            }
        }

    }

}
