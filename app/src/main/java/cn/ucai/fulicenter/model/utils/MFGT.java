package cn.ucai.fulicenter.model.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.activity.BoutiqueChildActivity;
import cn.ucai.fulicenter.controller.activity.CategoryChildActivity;
import cn.ucai.fulicenter.controller.activity.GoodsDetailsActivity;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;

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

    public static void gotoCategoryChild(Context context, int cartId, String name) {
        Intent intent = new Intent(context, CategoryChildActivity.class);
        intent.putExtra(I.NewAndBoutiqueGoods.CAT_ID,cartId);
        intent.putExtra(I.Category.KEY_NAME,name);
        startActivity((Activity) context,intent);
    }
}
