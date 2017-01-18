package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuliCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.Result;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.net.SharePrefrenceUtils;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.model.utils.OnSetAvatarListener;
import cn.ucai.fulicenter.model.utils.ResultUtils;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.tvUserName)
    TextView mTvUserName;
    @BindView(R.id.tvNick)
    TextView mTvNick;
    @BindView(R.id.ivAvatar)
    ImageView mIvAvatar;
    PopupWindow mPopupWindow;
    OnSetAvatarListener mListener;
    IModelUser model;

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
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), this, mIvAvatar);
        mTvUserName.setText(user.getMuserName());
        mTvNick.setText(user.getMuserNick());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Code", "requestCode:" + requestCode + " resultCode:" + resultCode);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == I.REQUEST_CODE_NICK) {
            mTvNick.setText(FuliCenterApplication.getUser().getMuserNick());
        } else if (requestCode == OnSetAvatarListener.REQUEST_CROP_PHOTO) {
            uploadAvatar();
        } else {
            mListener.setAvatar(requestCode, data, mIvAvatar);
        }
    }

    private void uploadAvatar() {
        model = new ModelUser();
        User user = FuliCenterApplication.getUser();
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.update_user_avatar));
        dialog.show();
        File file = null;
        file = new File(String.valueOf(OnSetAvatarListener.getAvatarFile(this,
                I.AVATAR_TYPE_USER_PATH+"/" + user.getMuserName() + user.getMavatarSuffix())));
        Log.e(">>>>>>","文件路径"+file.getAbsolutePath());
        model.uploadAvatar(this, user.getMuserName(), file, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                int msg = R.string.update_user_avatar_fail;
                if (s != null) {
                    Result result = ResultUtils.getListResultFromJson(s, User.class);
                    if (result != null) {
                        if (result.isRetMsg()) {
                            msg = R.string.update_user_avatar_success;
                        }
                    }
                }
                CommonUtils.showLongToast(msg);
                dialog.dismiss();

            }

            @Override
            public void onError(String error) {
                CommonUtils.showLongToast(R.string.update_user_avatar_fail);
                dialog.dismiss();
            }
        });
    }

    @OnClick({R.id.ivBack, R.id.btQuit, R.id.ll1, R.id.ll_user_nick})
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
            //更新昵称
            case R.id.ll_user_nick:
                MFGT.gotoUpdateNick(this);
                break;
            //上传头像
            case R.id.ll1:
                mListener = new OnSetAvatarListener(this, R.id.ll1, FuliCenterApplication.getUser().getMuserName()
                        , I.AVATAR_TYPE_USER_PATH);
                break;
        }
    }
}