package cn.ucai.fulicenter.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.AlbumsBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.net.IModelGoodsDetails;
import cn.ucai.fulicenter.model.net.ModelGoodsDetails;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.view.FlowIndicator;
import cn.ucai.fulicenter.view.SlideAutoLoopView;

public class GoodsDetailsActivity extends AppCompatActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvEnglishName)
    TextView tvEnglishName;
    @BindView(R.id.tvChineseName)
    TextView tvChineseName;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.salv)
    SlideAutoLoopView salv;
    @BindView(R.id.indictor)
    FlowIndicator indictor;
    IModelGoodsDetails model;
    String[] imageUris;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.activity_goods_details)
    RelativeLayout activityGoodsDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int goodsId = intent.getIntExtra(I.Goods.KEY_GOODS_ID, 0);
        if (goodsId==0){
            MFGT.finish(this);
        }else {
        model = new ModelGoodsDetails();
        initData(goodsId);
        }
    }

    private void initData(int goodsId) {
        model.downData(this, goodsId, new OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                if (result != null) {
                    showData(result);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void showData(GoodsDetailsBean result) {
        tvEnglishName.setText(result.getGoodsEnglishName());
        tvChineseName.setText(result.getGoodsName());
        tvPrice.setText(result.getCurrencyPrice());
        webview.loadDataWithBaseURL(null,result.getGoodsBrief(),I.TEXT_HTML,I.UTF_8,null);
        salv.startPlayLoop(indictor,getAlbumImgUrl(result),getAlbumImgCount(result));
    }


    private int getAlbumImgCount(GoodsDetailsBean result) {
        if (result.getProperties()!=null&&result.getProperties().size()>0) {
            return result.getProperties().get(0).getAlbums().size();
        }
        return 0;
    }

    private String[] getAlbumImgUrl(GoodsDetailsBean result) {
        if (result.getProperties()!=null&&result.getProperties().size()>0){
            List<AlbumsBean> albums = result.getProperties().get(0).getAlbums();
            String []urls = new String[albums.size()];
            for (int i =0;i<albums.size();i++){
                urls[i] = albums.get(i).getImgUrl();
            }
            return urls;
        }
        return new String[0];
    }

    public void onBack(View view) {
        MFGT.finish(this);
    }
}