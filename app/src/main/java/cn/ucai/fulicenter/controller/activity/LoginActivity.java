package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.utils.MFGT;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btLogin)
    Button btLogin;
    @BindView(R.id.btRegister)
    Button btRegister;

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
                break;
            case R.id.btRegister:
                MFGT.gotoRegister(this);
                break;
        }
    }
}
