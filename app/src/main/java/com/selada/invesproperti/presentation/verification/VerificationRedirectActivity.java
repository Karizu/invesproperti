package com.selada.invesproperti.presentation.verification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.selada.invesproperti.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerificationRedirectActivity extends AppCompatActivity {

    @OnClick(R.id.img_close)
    void onClickImgClose()
    {
        onBackPressed();
    }

    @OnClick(R.id.btn_verifikasi)
    void onClickBtnVerifikasi(){
        Intent intent = new Intent(this, VerificationActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_redirect);
        ButterKnife.bind(this);
    }
}