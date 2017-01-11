package cn.ucai.fulicenter.controller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;

public class MainActivity extends AppCompatActivity {
RadioButton [] radioButtons;
    RadioButton mNewgoods,mBoutique,mCart,mCategory,mCenter;
    int index,currentIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVeiw();
    }

    private void initVeiw() {
        radioButtons = new RadioButton[5];
        mNewgoods = (RadioButton) findViewById(R.id.Layout_NewGoods);
        mBoutique = (RadioButton) findViewById(R.id.Layout_Boutique);
        mCart = (RadioButton) findViewById(R.id.Layout_Cart);
        mCategory = (RadioButton) findViewById(R.id.Layout_Category);
        mCenter = (RadioButton) findViewById(R.id.Layout_Personal_Center);
        radioButtons[0] = mNewgoods;
        radioButtons[1] = mBoutique;
        radioButtons[2] = mCategory;
        radioButtons[3]= mCart;
        radioButtons[4] = mCenter;

        getSupportFragmentManager().beginTransaction().add(R.id.Layout_Container,new NewGoodsFragment()).commit();
    }

    public void onCheckedChange(View view){
        switch (view.getId()){
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
                index = 4;
                break;
        }
            if (index!=currentIndex){
                setStatus();
            }
    }

    private void setStatus() {
        for (int i = 0;i<radioButtons.length;i++){
            if (index!=i){
                radioButtons[i].setChecked(false);
            }else {
                radioButtons[i].setChecked(true);
            }
        }
        currentIndex=index;
    }
}
