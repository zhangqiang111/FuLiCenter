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
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuliCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.AlbumsBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelGoodsDetails;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelGoodsDetails;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
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

    boolean isCollect;
    @BindView(R.id.ivCollect)
    ImageView mIvCollect;
    int goodsId;
    IModelUser mModelUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mModelUser = new ModelUser();
        goodsId = intent.getIntExtra(I.Goods.KEY_GOODS_ID, 0);
        if (goodsId == 0) {
            MFGT.finish(this);
        } else {
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
        webview.loadDataWithBaseURL(null, result.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);
        salv.startPlayLoop(indictor, getAlbumImgUrl(result), getAlbumImgCount(result));
    }


    private int getAlbumImgCount(GoodsDetailsBean result) {
        if (result.getProperties() != null && result.getProperties().size() > 0) {
            return result.getProperties().get(0).getAlbums().size();
        }
        return 0;
    }

    private String[] getAlbumImgUrl(GoodsDetailsBean result) {
        if (result.getProperties() != null && result.getProperties().size() > 0) {
            List<AlbumsBean> albums = result.getProperties().get(0).getAlbums();
            String[] urls = new String[albums.size()];
            for (int i = 0; i < albums.size(); i++) {
                urls[i] = albums.get(i).getImgUrl();
            }
            return urls;
        }
        return new String[0];
    }

    public void onBack(View view) {
        MFGT.finish(this);
    }

    @OnClick(R.id.ivCollect)
    public void onClick() {
        User user = FuliCenterApplication.getUser();
        if (user != null) {
            setCollect(user);
        } else {
            MFGT.gotoLogin(this, I.REQUEST_CODE_LOGIN);
        }
    }

    @OnClick(R.id.ivCart)
    public void onCart() {
        User user = FuliCenterApplication.getUser();
        mModelUser.updateCart(this, I.ACTION_CART_ADD, user.getMuserName(), goodsId, 1, 0, new OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result != null && result.isSuccess()) {
                    CommonUtils.showLongToast(R.string.add_goods_success);
                }

            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void setCollect(User user) {
        model.setCollect(this, goodsId, user.getMuserName(), isCollect ? I.ACTION_DELETE_COLLECT : I.ACTION_ADD_COLLECT,
                new OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            isCollect = !isCollect;
                            setCollectStatus();
                            CommonUtils.showLongToast(result.getMsg());
                            sendBroadcast(new Intent(I.BROADCAST_UPDATA_COLLECT).putExtra(I.Collect.GOODS_ID, goodsId));
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initCollectStatus();
        setCollectStatus();
    }

    private void setCollectStatus() {
        if (isCollect) {
            mIvCollect.setImageResource(R.mipmap.bg_collect_out);
        } else {
            mIvCollect.setImageResource(R.mipmap.bg_collect_in);
        }
    }

    private void initCollectStatus() {
        User user = FuliCenterApplication.getUser();
        if (user != null) {
            model.isCollect(this, goodsId, user.getMuserName(), new OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if (result != null && result.isSuccess()) {
                        isCollect = true;
                    } else {
                        isCollect = false;
                    }
                    setCollectStatus();
                }

                @Override
                public void onError(String error) {
                    isCollect = false;
                    setCollectStatus();
                }
            });
        }
    }

    @OnClick(R.id.ivShare)
    public void onShare() {
        showShare();

    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}
