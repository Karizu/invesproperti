package com.selada.invesproperti.presentation.profile.cs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.selada.invesproperti.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CallCenterActivity extends AppCompatActivity {

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
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}