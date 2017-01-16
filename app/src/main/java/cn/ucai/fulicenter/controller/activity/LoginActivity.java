package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.model.utils.ResultUtils;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btLogin)
    Button btLogin;
    @BindView(R.id.btRegister)
    Button btRegister;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPassword)
    EditText etPassword;
    IModelUser model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btLogin, R.id.btRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btLogin:
                checkInput();
                break;
            case R.id.btRegister:
                MFGT.gotoRegister(this);
                break;
        }
    }

    private void checkInput() {
        String username = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)){
            etUserName.setError(getResources().getString(R.string.user_name_connot_be_empty));
            etUserName.requestFocus();
        } else if (TextUtils.isEmpty(password)){
            etPassword.setError(getResources().getString(R.string.password_connot_be_empty));
            etPassword.requestFocus();
        } else {
            login(username,password);
        }
    }

    private void login(final String username, String password) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.logining));
        model = new ModelUser();
        model.login(this, username, password, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                if (s!=null){
                    Result result = ResultUtils.getResultFromJson(s, User.class);
                    if (result.isRetMsg()){
                        User user = (User) result.getRetData();
                        SharePrefrenceUtils.getInstance(LoginActivity.this).saveUser(user.getMuserName());
                        FuliCenterApplication.setUser(user);
                        MFGT.finish(LoginActivity.this);
                    } else {
                        if (result.getRetCode()== I.MSG_LOGIN_UNKNOW_USER){
                            CommonUtils.showLongToast(getString(R.string.login_fail_unknow_user));
                        }
                        if (result.getRetCode()== I.MSG_LOGIN_ERROR_PASSWORD){
                            CommonUtils.showLongToast(getString(R.string.login_fail_error_password));
                        }
                    }
                }else {
                    CommonUtils.showLongToast(getString(R.string.login_fail));

                }
                dialog.dismiss();

            }

            @Override
            public void onError(String error) {
                dialog.dismiss();
                CommonUtils.showLongToast(getString(R.string.login_fail));

            }
        });
    }
}
