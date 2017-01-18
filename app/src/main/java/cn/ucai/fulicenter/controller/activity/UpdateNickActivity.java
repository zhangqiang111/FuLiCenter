package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuliCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.Result;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.dao.UserDao;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.net.SharePrefrenceUtils;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.model.utils.ResultUtils;

public class UpdateNickActivity extends AppCompatActivity {

    @BindView(R.id.etUpdateUserNick)
    EditText mEtUpdateUserNick;
    IModelUser model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nick);
        ButterKnife.bind(this);
        intData();
    }

    private void intData() {
        mEtUpdateUserNick.setText(FuliCenterApplication.getUser().getMuserNick());
        mEtUpdateUserNick.setSelection(mEtUpdateUserNick.getText().length());
    }

    @OnClick({R.id.ivBack, R.id.btSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(this);
                break;
            case R.id.btSave:
                User user = FuliCenterApplication.getUser();
                if (TextUtils.isEmpty(mEtUpdateUserNick.getText().toString())) {
                    CommonUtils.showLongToast(R.string.nick_name_connot_be_empty);
                } else if (mEtUpdateUserNick.getText().equals(user.getMuserNick())) {
                    CommonUtils.showLongToast(R.string.update_nick_fail_unmodify);
                } else {
                    saveUserNick(user, mEtUpdateUserNick.getText().toString());
                }
                break;
        }
    }

    private void saveUserNick(final User user, String nick) {
        model = new ModelUser();
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("更新昵称中...");
        dialog.show();
        model.updateNick(this, user.getMuserName(), nick, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                int msg = R.string.update_fail;
                if (s != null) {
                    Result result = ResultUtils.getResultFromJson(s, User.class);
                    if (result != null) {
                        if (result.isRetMsg()) {
                            msg = R.string.update_user_nick_success;
                            User user = (User) result.getRetData();
                            saveUser(user);
                            setResult(RESULT_OK);
                            MFGT.finish(UpdateNickActivity.this);
                        } else {
                            if (result.getRetCode() == I.MSG_USER_SAME_NICK || result.getRetCode() == I.MSG_USER_UPDATE_NICK_FAIL) {
                                msg = R.string.update_nick_fail_unmodify;
                            }
                        }
                    }
                }
                CommonUtils.showLongToast(msg);
                dialog.dismiss();
            }

            @Override
            public void onError(String error) {
                CommonUtils.showLongToast(R.string.update_fail);
                dialog.dismiss();
            }
        });

    }

    private void saveUser(User user) {
        UserDao.getInstance().saveUser(user);
        FuliCenterApplication.setUser(user);
    }
}
