package cn.ucai.fulicenter.application;

import android.app.Application;

import java.util.HashMap;

import cn.sharesdk.framework.ShareSDK;
import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.User;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class FuliCenterApplication extends Application {
    private static FuliCenterApplication instance;

    public static FuliCenterApplication getInstance() {
        return instance;
    }

    public static HashMap<Integer, CartBean> myCartList = new HashMap<>();

    public static HashMap<Integer, CartBean> getMyCartList() {
        return myCartList;
    }

    public static void setMyCartList(HashMap<Integer, CartBean> myCartList) {
        FuliCenterApplication.myCartList = myCartList;
    }

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FuliCenterApplication.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ShareSDK.initSDK(this);
    }
}
