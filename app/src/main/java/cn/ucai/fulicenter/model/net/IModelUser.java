package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.model.bean.User;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public interface IModelUser {
    void login(Context context, String username, String password, OnCompleteListener<String> listener);
    void regiter(Context context, String username, String nick,String password, OnCompleteListener<String> listener);
}
