package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.model.bean.NewGoodsBean;

/**
 * Created by Administrator on 2017/1/12 0012.
 */

public interface IModelBoutiuqeChild {
    void downloadNewGoods(Context context, int catId, int pageId, OnCompleteListener<NewGoodsBean[]> listener);
}
