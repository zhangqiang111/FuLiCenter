package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuliCenterApplication;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.SharePrefrenceUtils;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.MFGT;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.tvUserName)
    TextView mTvUserName;
    @BindView(R.id.tvNick)
    TextView mTvNick;
    @BindView(R.id.ivAvatar)
    ImageView mIvAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        User user = FuliCenterApplication.getUser();
        if (user != null) {
            initData(user);
        }
    }

    private void initData(User user) {
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), this,mIvAvatar);
        mTvUserName.setText(user.getMuserName());
        mTvNick.setText(user.getMuserNick());
    }

    @OnClick({R.id.ivBack, R.id.btQuit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(this);
                break;
            case R.id.btQuit:
                FuliCenterApplication.setUser(null);
                SharePrefrenceUtils.getInstance(this).removeUser();
                MFGT.gotoLogin(this);
                finish();
                break;
        }
    }
}