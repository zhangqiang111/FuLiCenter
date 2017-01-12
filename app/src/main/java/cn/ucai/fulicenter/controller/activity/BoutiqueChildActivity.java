package cn.ucai.fulicenter.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;

public class BoutiqueChildActivity extends AppCompatActivity {

    @BindView(R.id.tvBoutique_Child)
    TextView tvBoutiqueChild;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boutique);
        ButterKnife.bind(this);
        initData();
        Intent intent = getIntent();
        tvBoutiqueChild.setText(intent.getStringExtra(I.Boutique.NAME));
    }

    private void initData() {
        getSupportFragmentManager().beginTransaction().add(R.id.flBoutique_Container, new NewGoodsFragment()).commit();
    }
    public void onBack(View view){
        this.finish();
    }
}
