package cn.ucai.fulicenter.controller.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.view.CatFilterButton;

public class CategoryChildActivity extends AppCompatActivity {

    @BindView(R.id.rl_boutique)
    RelativeLayout rlBoutique;
    @BindView(R.id.btSortPrice)
    Button btSortPrice;
    @BindView(R.id.btSortTime)
    Button btSortTime;
    NewGoodsFragment newGoodsFragment;
    boolean priceClick = false;
    boolean addTime = false;
    @BindView(R.id.cfButton)
    CatFilterButton cfButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        newGoodsFragment = new NewGoodsFragment();
        ArrayList<CategoryChildBean> list = (ArrayList<CategoryChildBean>) getIntent().getSerializableExtra(I.CategoryChild.DATA);
        cfButton.initCatFilterButton(getIntent().getStringExtra(I.Category.KEY_NAME),list);
        getSupportFragmentManager().beginTransaction().add(R.id.Layout_Container, newGoodsFragment).commit();
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }

    @OnClick({R.id.btSortPrice, R.id.btSortTime})
    public void onClick(View view) {
        int sortBy = I.SORT_BY_ADDTIME_ASC;
        switch (view.getId()) {
            case R.id.btSortPrice:
                if (priceClick) {
                    sortBy = I.SORT_BY_PRICE_ASC;
                    setDownDrawabele(btSortPrice);
                } else {
                    sortBy = I.SORT_BY_PRICE_DESC;
                    setUpDrawabele(btSortPrice);
                }
                priceClick = !priceClick;
                break;
            case R.id.btSortTime:
                if (addTime) {
                    sortBy = I.SORT_BY_ADDTIME_ASC;
                    setDownDrawabele(btSortTime);
                } else {
                    sortBy = I.SORT_BY_ADDTIME_DESC;
                    setUpDrawabele(btSortTime);
                }
                addTime = !addTime;
                break;
        }
        newGoodsFragment.sortGoods(sortBy);
    }

    private void setDownDrawabele(Button button) {
        Drawable drawaRight = ContextCompat.getDrawable(this, R.mipmap.arrow_order_down);
        drawaRight.setBounds(0, 0, drawaRight.getMinimumWidth(), drawaRight.getMinimumHeight());
        button.setCompoundDrawables(null, null, drawaRight, null);
    }

    private void setUpDrawabele(Button button) {
        Drawable drawaRight = ContextCompat.getDrawable(this, R.mipmap.arrow_order_up);
        drawaRight.setBounds(0, 0, drawaRight.getMinimumWidth(), drawaRight.getMinimumHeight());
        button.setCompoundDrawables(null, null, drawaRight, null);
    }
}
