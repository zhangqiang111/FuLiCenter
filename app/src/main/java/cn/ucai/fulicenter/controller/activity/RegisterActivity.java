package cn.ucai.fulicenter.controller.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.bean.Result;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.model.utils.ResultUtils;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etNick)
    EditText etNick;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etConfirm)
    EditText etConfirm;
    @BindView(R.id.btRegister)
    Button btRegister;
    @BindView(R.id.activity_register)
    RelativeLayout activityRegister;
    IModelUser model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ivBack, R.id.btRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                MFGT.finish(this);
                break;
            case R.id.btRegister:
                checkInput();
                break;
        }
    }

    private void checkInput() {
        String username = etUserName.getText().toString().trim();
        String nick = etNick.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirm = etConfirm.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            etUserName.setError(getResources().getString(R.string.user_name_connot_be_empty));
            etUserName.requestFocus();
        } else if (!username.matches("[a-zA-Z]\\w{5,15}")) {
            etUserName.setError(getResources().getString(R.string.illegal_user_name));
        } else if (TextUtils.isEmpty(username)) {
            etNick.setError(getResources().getString(R.string.user_name_connot_be_empty));
            etNick.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError(getResources().getString(R.string.user_name_connot_be_empty));
            etPassword.requestFocus();
        } else if (TextUtils.isEmpty(confirm)) {
            etConfirm.setError(getResources().getString(R.string.user_name_connot_be_empty));
            etConfirm.requestFocus();
        } else if (!password.equals(confirm)) {
            etConfirm.setError(getResources().getString(R.string.two_input_password));
            etConfirm.requestFocus();
        } else {
            register(username, nick, password);
        }
    }

    private void register(String username, String nick, String password) {
        model = new ModelUser();
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.registering));
        model.regiter(this, username, nick, password, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                if (s != null) {
                    Result result = ResultUtils.getResultFromJson(s, Result.class);
                    if (result.isRetMsg()) {
                        dialog.dismiss();

                        MFGT.finish(RegisterActivity.this);
                    } else {
                        dialog.dismiss();
                        CommonUtils.showLongToast(R.string.register_fail_exists);
                    }

                } else {
                    dialog.dismiss();
                    CommonUtils.showShortToast(R.string.register_fail);
                }
            }

            @Override
            public void onError(String error) {
                dialog.dismiss();
                CommonUtils.showLongToast(error);

            }
        });
    }
}
