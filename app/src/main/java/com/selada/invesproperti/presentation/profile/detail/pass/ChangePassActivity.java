package com.selada.invesproperti.presentation.profile.detail.pass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.selada.invesproperti.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePassActivity extends AppCompatActivity {

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.btn_ganti)
    void onClickBtnGanti(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}