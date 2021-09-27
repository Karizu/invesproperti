package com.selada.invesproperti.presentation.profile.bantuan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.selada.invesproperti.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BantuanActivity extends AppCompatActivity {

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.cv_chat)
    void onClickChat(){

    }

    @OnClick(R.id.cv_email)
    void onClickEmail(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan);
        ButterKnife.bind(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}