package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.model.bean.NewGoodsBean;

/**
 * Created by Administrator on 2017/1/13 0013.
 */

public interface IModelCategoryChild {
    void dowanloadData(Context cotext, int catId, int pageId, OnCompleteListener<NewGoodsBean[]> listener);
}
