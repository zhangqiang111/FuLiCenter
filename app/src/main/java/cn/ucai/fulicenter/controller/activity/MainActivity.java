package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuliCenterApplication;
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
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    MyViewAdapter adapter;

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
        mNewgoods = (RadioButton) findViewById(R.id.Layout_NewGoods);
        mBoutique = (RadioButton) findViewById(R.id.Layout_Boutique);
        mCart = (RadioButton) findViewById(R.id.Layout_Cart);
        mCategory = (RadioButton) findViewById(R.id.Layout_Category);
        mCenter = (RadioButton) findViewById(R.id.Layout_Personal_Center);
        radioButtons[0] = mNewgoods;
        radioButtons[1] = mBoutique;
        radioButtons[2] = mCategory;
        radioButtons[3] = mCart;
        radioButtons[4] = mCenter;

        fragments[0] = new NewGoodsFragment();
        fragments[1] = new BoutiqueFragment();
        fragments[2] = new CategoryFragment();
        fragments[3] = new CartFragment();
        fragments[4] = new CenterFragment();
        adapter = new MyViewAdapter(getSupportFragmentManager(), fragments);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == currentIndex) {
                    return;
                }
                radioButtons[currentIndex].setChecked(false);
                radioButtons[position].setChecked(true);
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
                index = 3;
                break;
            case R.id.Layout_Personal_Center:
                if (FuliCenterApplication.getUser() == null) {
                    MFGT.gotoLogin(this);
                } else {
                    index = 4;
                }
                break;
        }
        if (index != currentIndex) {
            setStatus();
            viewPager.setCurrentItem(index);
        }
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

    class MyViewAdapter extends FragmentPagerAdapter {
        Fragment[] fragments;

        public MyViewAdapter(FragmentManager fm, Fragment[] fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }
}
