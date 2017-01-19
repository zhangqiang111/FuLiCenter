package cn.ucai.fulicenter.model.net;

import android.content.Context;

import java.io.File;

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
}
