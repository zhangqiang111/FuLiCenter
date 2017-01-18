package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.bean.MessageBean;

/**
 * Created by Administrator on 2017/1/12 0012.
 */

public interface IModelGoodsDetails {
    void downData(Context context,int goodsId,OnCompleteListener<GoodsDetailsBean> listener);
    void isCollect(Context context,int goodsId,String username,OnCompleteListener<MessageBean> listener);
    void setCollect(Context context,int goodsId,String username,int action,OnCompleteListener<MessageBean> listener);
}
