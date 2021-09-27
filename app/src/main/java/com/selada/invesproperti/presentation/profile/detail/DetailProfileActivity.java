package com.selada.invesproperti.presentation.profile.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.selada.invesproperti.R;
import com.selada.invesproperti.presentation.profile.detail.efek.ChangeEfekActivity;
import com.selada.invesproperti.presentation.profile.detail.income.ChangeIncomeYearActivity;
import com.selada.invesproperti.presentation.profile.detail.pass.ChangePassActivity;
import com.selada.invesproperti.presentation.profile.detail.phone.ChangePhoneActivity;
import com.selada.invesproperti.presentation.profile.disclaimer.DisclaimerActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailProfileActivity extends AppCompatActivity {

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.btn_ubah_pass)
    void onClickUbahPass(){
        Intent intent = new Intent(DetailProfileActivity.this, ChangePassActivity.class);
        startActivity(intent);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.btn_ubah_no)
    void onClickUbahNo(){
        Intent intent = new Intent(DetailProfileActivity.this, ChangePhoneActivity.class);
        startActivity(intent);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.btn_ubah_pendapatan)
    void onClickUbahPendapatan(){
        Intent intent = new Intent(DetailProfileActivity.this, ChangeIncomeYearActivity.class);
        startActivity(intent);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.btn_ubah_tahun)
    void onClickUbahTahun(){
        Intent intent = new Intent(DetailProfileActivity.this, ChangeEfekActivity.class);
        startActivity(intent);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profile);
        ButterKnife.bind(this);
    }
}