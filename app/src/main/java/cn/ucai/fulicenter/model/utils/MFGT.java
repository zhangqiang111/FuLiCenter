package cn.ucai.fulicenter.model.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.activity.BoutiqueChildActivity;
import cn.ucai.fulicenter.controller.activity.CategoryChildActivity;
import cn.ucai.fulicenter.controller.activity.CollectActivity;
import cn.ucai.fulicenter.controller.activity.GoodsDetailsActivity;
import cn.ucai.fulicenter.controller.activity.LoginActivity;
import cn.ucai.fulicenter.controller.activity.MainActivity;
import cn.ucai.fulicenter.controller.activity.OrderActivity;
import cn.ucai.fulicenter.controller.activity.RegisterActivity;
import cn.ucai.fulicenter.controller.activity.SettingActivity;
import cn.ucai.fulicenter.controller.activity.UpdateNickActivity;
import cn.ucai.fulicenter.controller.fragment.CartFragment;
import cn.ucai.fulicenter.controller.fragment.CenterFragment;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;

import static android.R.attr.name;
import static android.R.id.list;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class MFGT {
    public static void startActivity(Activity context,Class<?> cla){
        context.startActivity(new Intent(context,cla));
        context.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
    public static void startActivity(Activity context,Intent intent){
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
    public static void gotoBoutiqueChild(Context context, BoutiqueBean boutiqueBean){
        Intent intent = new Intent(context, BoutiqueChildActivity.class);
        intent.putExtra(I.NewAndBoutiqueGoods.CAT_ID,boutiqueBean.getId());
        intent.putExtra(I.Boutique.NAME,boutiqueBean.getTitle());
        startActivity((Activity) context,intent);
    }

    public static void gotoGoodsDetails(Context context, int goodsId) {
        Intent intetn = new Intent(context, GoodsDetailsActivity.class);
        intetn.putExtra(I.Goods.KEY_GOODS_ID,goodsId);
        startActivity((Activity) context,intetn);
    }

    public static void gotoCategoryChild(Context context, int cartId, String name, ArrayList<CategoryChildBean> list) {
        Intent intent = new Intent(context, CategoryChildActivity.class);
        intent.putExtra(I.NewAndBoutiqueGoods.CAT_ID,cartId);
        intent.putExtra(I.Category.KEY_NAME,name);
        intent.putExtra(I.CategoryChild.DATA,list);
        startActivity((Activity) context,intent);
    }

    public static void gotoLogin(Activity activity,int code) {
//        startActivity(mainActivity, LoginActivity.class);
        activity.startActivityForResult(new Intent(activity,LoginActivity.class),code);
    }
    public static void gotoLogin(Activity activity) {
//        startActivity(mainActivity, LoginActivity.class);
        activity.startActivityForResult(new Intent(activity,LoginActivity.class),I.REQUEST_CODE_LOGIN);
    }

    public static void gotoRegister(LoginActivity loginActivity) {
        startActivity(loginActivity, RegisterActivity.class);
    }

    public static void gotoSetting(MainActivity activity) {
        startActivity(activity,SettingActivity.class);
    }

    public static void gotoUpdateNick(Activity activity) {
        activity.startActivityForResult(new Intent(activity, UpdateNickActivity.class),I.REQUEST_CODE_NICK);
    }

    public static void gotoCollectActivity(Activity activity) {
        startActivity(activity, CollectActivity.class);
    }

    public static void gotoOrder(Context context, int priceSum) {
        Intent intent = new Intent(context, OrderActivity.class);
        intent.putExtra(I.Cart.PRICE,priceSum);
        startActivity((Activity) context,intent);
    }
}
