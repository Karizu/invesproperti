package com.selada.invesproperti.presentation.portofolio.deposit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.selada.invesproperti.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailDepositActivity extends AppCompatActivity {

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.btn_cs)
    void onClickCs(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_deposit);
        ButterKnife.bind(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}