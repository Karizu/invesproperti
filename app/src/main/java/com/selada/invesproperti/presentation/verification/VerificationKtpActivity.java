package com.selada.invesproperti.presentation.verification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.selada.invesproperti.R;
import com.white.progressview.HorizontalProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerificationKtpActivity extends AppCompatActivity {

    @BindView(R.id.progressBarItem)
    HorizontalProgressView progressBarItem;

    @OnClick(R.id.btn_back)
    void onClickBtnBack(){
        onBackPressed();
    }

    @OnClick(R.id.frame_tahap_1)
    void onClickFrameTahap1(){

    }

    @OnClick(R.id.frame_tahap_2)
    void onClickFrameTahap2(){

    }

    @OnClick(R.id.btn_lanjut)
    void onClickBtnLanjut(){
        Intent intent = new Intent(this, VerificationData1Activity.class);
        startActivity(intent);
        this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_ktp);
        ButterKnife.bind(this);

        initComponent();
    }

    private void initComponent() {
        // set progress when foto uploaded
        progressBarItem.setProgress(45);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}