package cn.ucai.fulicenter.model.net;

import android.content.Context;
import android.view.View;

import cn.ucai.fulicenter.model.bean.NewGoodsBean;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public interface IModelNewGoods {
    void downloadNewGoods(Context context, int catId, int pageId, OnCompleteListener<NewGoodsBean[]> listener);
}
