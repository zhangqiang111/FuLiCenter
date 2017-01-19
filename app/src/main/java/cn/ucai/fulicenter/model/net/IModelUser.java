package cn.ucai.fulicenter.model.net;

import android.content.Context;

import java.io.File;

import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.User;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public interface IModelUser {
    void login(Context context, String username, String password, OnCompleteListener<String> listener);
    void regiter(Context context, String username, String nick,String password, OnCompleteListener<String> listener);
    void updateNick(Context context, String username, String newNick, OnCompleteListener<String> listener);
    void uploadAvatar(Context context, String username, File file, OnCompleteListener<String> listener);
    void collectCount(Context context, String username, OnCompleteListener<MessageBean> listener);
    void getCollect(Context context, String username,int pageId,int pageSize, OnCompleteListener<CollectBean[]> listener);
    void getCart(Context context,String username ,OnCompleteListener<CartBean[]> listener);
    void addCart(Context context,String username,int goodsId,int count, OnCompleteListener<MessageBean> listener);
    void deleteCart(Context context,int cartId, OnCompleteListener<MessageBean> listener);
    void updateCart(Context context,int cartId,int count, OnCompleteListener<MessageBean> listener);
    void updateCart(Context context,int action,String username,int goodsId,int count,int cartId,OnCompleteListener<MessageBean> listener);
}
