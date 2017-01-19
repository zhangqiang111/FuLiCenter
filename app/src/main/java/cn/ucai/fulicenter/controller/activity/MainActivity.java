package cn.ucai.fulicenter.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuliCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.fragment.BoutiqueFragment;
import cn.ucai.fulicenter.controller.fragment.CartFragment;
import cn.ucai.fulicenter.controller.fragment.CategoryFragment;
import cn.ucai.fulicenter.controller.fragment.CenterFragment;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.model.utils.MFGT;

public class MainActivity extends AppCompatActivity {
    RadioButton[] radioButtons;
    RadioButton mNewgoods, mBoutique, mCart, mCategory, mCenter;
    int index, currentIndex;
    Fragment[] fragments;
    @BindView(R.id.Layout_NewGoods)
    RadioButton mLayoutNewGoods;
    @BindView(R.id.Layout_Boutique)
    RadioButton mLayoutBoutique;
    @BindView(R.id.Layout_Category)
    RadioButton mLayoutCategory;
    @BindView(R.id.Layout_Cart)
    RadioButton mLayoutCart;
    @BindView(R.id.Layout_Personal_Center)
    RadioButton mLayoutPersonalCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initVeiw();
    }

    private void initVeiw() {
        radioButtons = new RadioButton[5];
        fragments = new Fragment[5];
        radioButtons[0] = mLayoutNewGoods;
        radioButtons[1] = mLayoutBoutique;
        radioButtons[2] = mLayoutCategory;
        radioButtons[3] = mLayoutCart;
        radioButtons[4] = mLayoutPersonalCenter;

        fragments[0] = new NewGoodsFragment();
        fragments[1] = new BoutiqueFragment();
        fragments[2] = new CategoryFragment();
        fragments[3] = new CartFragment();
        fragments[4] = new CenterFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.Layout_Container, fragments[0])
                .commit();
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.Layout_NewGoods:
                index = 0;
                break;
            case R.id.Layout_Boutique:
                index = 1;
                break;
            case R.id.Layout_Category:
                index = 2;
                break;
            case R.id.Layout_Cart:
                if (FuliCenterApplication.getUser() == null) {
                    MFGT.gotoLogin(this, I.REQUEST_CODE_LOGIN_FROM_CART);
                    radioButtons[3].setChecked(false);
                } else {
                    index = 3;
                }
                break;
            case R.id.Layout_Personal_Center:
                if (FuliCenterApplication.getUser() == null) {
                    MFGT.gotoLogin(this, I.REQUEST_CODE_LOGIN);
                    radioButtons[4].setChecked(false);
                } else {
                    index = 4;
                }
                break;
        }
        setFragment();
        if (index != currentIndex) {
            setStatus();
        }
    }

    private void setFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(fragments[currentIndex]);
        if (!fragments[index].isAdded()) {
            ft.add(R.id.Layout_Container, fragments[index]);
        }
        ft.show(fragments[index]).commitAllowingStateLoss();
    }

    private void setStatus() {
        for (int i = 0; i < radioButtons.length; i++) {
            if (index != i) {
                radioButtons[i].setChecked(false);
            } else {
                radioButtons[i].setChecked(true);
            }
        }
        currentIndex = index;
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e(">>>>", "Index:" + index + " currenIndex" + currentIndex);
        if (index == 4 && FuliCenterApplication.getUser() == null) {
            index = 0;
        }
        setFragment();
        setStatus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("Code", "requestCode:" + requestCode + " resultCode:" + resultCode);
        if (resultCode == RESULT_OK) {
            if (requestCode == I.REQUEST_CODE_LOGIN) {
                index = 4;
            }
            if (requestCode == I.REQUEST_CODE_LOGIN_FROM_CART) {
                index = 3;
            }
            setFragment();
            setStatus();
        }
    }
}
