package com.selada.invesproperti.presentation.verification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.selada.invesproperti.R;
import com.selada.invesproperti.util.Constant;
import com.selada.invesproperti.util.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerificationRedirectActivity extends AppCompatActivity {

    @BindView(R.id.btn_verifikasi)
    RelativeLayout btn_verifikasi;
    @BindView(R.id.content_verification)
    TextView tv_content;

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

        if (PreferenceManager.getUserStatus().equals(Constant.ON_VERIFICATION)){
            btn_verifikasi.setVisibility(View.GONE);
            tv_content.setText("Akun sedang dalam proses \nverifikasi data");
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}