package com.selada.invesproperti.presentation.profile.detail.phone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.selada.invesproperti.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePhoneActivity extends AppCompatActivity {

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.btn_simpan)
    void onClickSimpan(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        ButterKnife.bind(this);
    }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
}