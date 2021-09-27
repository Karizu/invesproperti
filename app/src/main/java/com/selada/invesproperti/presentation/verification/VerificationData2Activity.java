package com.selada.invesproperti.presentation.verification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.selada.invesproperti.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerificationData2Activity extends AppCompatActivity {

    @OnClick(R.id.btn_back)
    void onClickBtnBack(){
        onBackPressed();
    }

    @OnClick(R.id.btn_lanjut)
    void onClickBtnLanjut(){
        Intent intent = new Intent(this, VerificationData3Activity.class);
        startActivity(intent);
        this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_data2);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}