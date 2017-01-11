package cn.ucai.fulicenter.model.utils;

import android.widget.Toast;

import cn.ucai.fulicenter.application.FuliCenterApplication;

public class CommonUtils {
    public static void showLongToast(String msg){
        Toast.makeText(FuliCenterApplication.getInstance(),msg,Toast.LENGTH_LONG).show();
    }
    public static void showShortToast(String msg){
        Toast.makeText(FuliCenterApplication.getInstance(),msg,Toast.LENGTH_SHORT).show();
    }
    public static void showLongToast(int rId){
        showLongToast(FuliCenterApplication.getInstance().getString(rId));
    }
    public static void showShortToast(int rId){
        showShortToast(FuliCenterApplication.getInstance().getString(rId));
    }
}
