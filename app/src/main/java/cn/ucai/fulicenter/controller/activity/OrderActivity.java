package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.model.utils.DisplayUtils;

public class OrderActivity extends AppCompatActivity {

    @BindView(R.id.tvSum)
    TextView mTvSum;
    @BindView(R.id.et_people)
    EditText mEtPeople;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_street)
    EditText mEtStreet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        int price = getIntent().getIntExtra(I.Cart.PRICE, 0);
        mTvSum.setText("合计: ¥" + price);
        intTitle();
    }

    private void intTitle() {
        DisplayUtils.initBackWithTitle(this, "收货地址");
        DisplayUtils.initBack(this);
    }

    @OnClick(R.id.bt_account)
    public void onClick() {
        checkInput();
    }

    private void checkInput() {
        String name = mEtPeople.getText().toString();
        String phone = mEtPhone.getText().toString();
        String street = mEtStreet.getText().toString();
        if (TextUtils.isEmpty(name)){
            mEtPeople.setError("收货人姓名不能为空");
            mEtPeople.requestFocus();
        }else if (!phone.matches("[1]\\d{10}")){
            mEtPhone.setError("请输入正确的手机号");
            mEtPhone.requestFocus();
        }else if(TextUtils.isEmpty(street)){
            mEtStreet.setError("收货地址不能为空");
        }else {

        }
    }
}
