package cn.ucai.fulicenter.model.net;

import android.content.Context;
import android.os.Message;

import java.io.File;

import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.utils.MD5;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public class ModelUser implements IModelUser {
    @Override
    public void login(Context context, String username, String password, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME, username)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void regiter(Context context, String username, String nick, String password, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_REGISTER)
                .addParam(I.User.USER_NAME, username)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .addParam(I.User.NICK, nick)
                .post()
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void updateNick(Context context, String username, String newNick, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_USER_NICK)
                .addParam(I.User.USER_NAME, username)
                .addParam(I.User.NICK, newNick)
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void uploadAvatar(Context context, String username, File file, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_AVATAR)
                .addParam(I.NAME_OR_HXID, username)
                .addParam(I.AVATAR_TYPE, I.AVATAR_TYPE_USER_PATH)
                .addFile2(file)
                .post()
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void collectCount(Context context, String username, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECT_COUNT)
                .addParam(I.Collect.USER_NAME, username)
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void getCollect(Context context, String username, int pageId, int pageSize, OnCompleteListener<CollectBean[]> listener) {
        OkHttpUtils<CollectBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_COLLECTS)
                .addParam(I.Collect.USER_NAME, username)
                .addParam(I.PAGE_ID, String.valueOf(pageId))
                .addParam(I.PAGE_SIZE, String.valueOf(pageSize))
                .targetClass(CollectBean[].class)
                .execute(listener);
    }

    @Override
    public void getCart(Context context, String username, OnCompleteListener<CartBean[]> listener) {
        OkHttpUtils<CartBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CARTS)
                .addParam(I.User.USER_NAME, username)
                .targetClass(CartBean[].class)
                .execute(listener);
    }

    @Override
    public void addCart(Context context, String username, int goodsId, int count, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_ADD_CART)
                .addParam(I.User.USER_NAME, username)
                .addParam(I.Cart.GOODS_ID, String.valueOf(goodsId))
                .addParam(I.Cart.COUNT, String.valueOf(count))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void deleteCart(Context context, int cartId, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_DELETE_CART)
                .addParam(I.Cart.ID, String.valueOf(cartId))
                .targetClass(MessageBean.class)
                .execute(listener);
    }

    @Override
    public void updateCart(Context context, int cartId, int count, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_UPDATE_CART)
                .addParam(I.Cart.ID, String.valueOf(cartId))
                .addParam(I.Cart.COUNT, String.valueOf(count))
                .addParam(I.Cart.IS_CHECKED, String.valueOf(false))
                .targetClass(MessageBean.class)
                .execute(listener);

    }

    @Override
    public void updateCart(Context context, int action, String username, int goodsId, int count, int cartId, OnCompleteListener<MessageBean> listener) {
        OkHttpUtils<MessageBean> utils = new OkHttpUtils<>(context);
        if (action == I.ACTION_CART_ADD) {
            addCart(context, username, goodsId, 1, listener);
        } else if (action == I.ACTION_CART_DEL) {
            deleteCart(context, cartId, listener);
        } else {
            updateCart(context, cartId, count, listener);
        }
    }
}
