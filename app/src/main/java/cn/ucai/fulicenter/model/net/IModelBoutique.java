package cn.ucai.fulicenter.model.net;

import android.content.Context;

import cn.ucai.fulicenter.model.bean.BoutiqueBean;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public interface IModelBoutique {
    void downloadBoutique(Context context, OnCompleteListener<BoutiqueBean[]> listener);
}
