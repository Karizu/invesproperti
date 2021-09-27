package com.selada.invesproperti.presentation.verification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.selada.invesproperti.IntroSliderActivity;
import com.selada.invesproperti.R;
import com.selada.invesproperti.presentation.RegisterActivity;
import com.white.progressview.HorizontalProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerificationActivity extends AppCompatActivity {

    @BindView(R.id.btn_lanjut)
    Button btn_lanjut;
    @BindView(R.id.img_checked_1)
    ImageView img_checked_1;
    @BindView(R.id.img_checked_2)
    ImageView img_checked_2;
    @BindView(R.id.progressBarItem)
    HorizontalProgressView progressBarItem;

    private String type = "";

    @OnClick(R.id.frame_product)
    void onClickFrameProduct(){
        img_checked_1.setBackground(getResources().getDrawable(R.drawable.bg_circle_unchecked));
        img_checked_2.setBackground(getResources().getDrawable(R.drawable.bg_circle_cheked));
        btn_lanjut.setEnabled(true);
        btn_lanjut.setBackground(getResources().getDrawable(R.drawable.bg_round_green));
        type = "product_owner";
    }

    @OnClick(R.id.frame_investor)
    void onClickFrameInvestor(){
        img_checked_1.setBackground(getResources().getDrawable(R.drawable.bg_circle_cheked));
        img_checked_2.setBackground(getResources().getDrawable(R.drawable.bg_circle_unchecked));
        btn_lanjut.setEnabled(true);
        btn_lanjut.setBackground(getResources().getDrawable(R.drawable.bg_round_green));
        type = "investor";
    }

    @OnClick(R.id.btn_back)
    void onClickBtnBack(){
        onBackPressed();
    }

    @OnClick(R.id.btn_lanjut)
    void onClickBtnLanjut(){
        Intent intent = new Intent(this, VerificationKtpActivity.class);
        startActivity(intent);
        this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        ButterKnife.bind(this);

        initComponent();
    }

    private void initComponent() {
        progressBarItem.setProgress(15);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}