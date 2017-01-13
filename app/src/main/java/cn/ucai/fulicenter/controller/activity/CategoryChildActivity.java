package cn.ucai.fulicenter.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.model.utils.MFGT;

public class CategoryChildActivity extends AppCompatActivity {

    @BindView(R.id.rl_boutique)
    RelativeLayout rlBoutique;
    @BindView(R.id.btSortPrice)
    Button btSortPrice;
    @BindView(R.id.btSortTime)
    Button btSortTime;
    NewGoodsFragment newGoodsFragment;
    boolean priceClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        newGoodsFragment = new NewGoodsFragment();
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
                if (priceClick){
                    sortBy = I.SORT_BY_ADDTIME_ASC;
                }else {
                    sortBy = I.SORT_BY_ADDTIME_DESC;
                }
                priceClick=!priceClick;
                break;
            case R.id.btSortTime:
                sortBy = I.SORT_BY_PRICE_DESC;
                break;
        }
        newGoodsFragment.sortGoods(sortBy);
    }
}
