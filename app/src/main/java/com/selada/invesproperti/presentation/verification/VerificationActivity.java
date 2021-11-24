package com.selada.invesproperti.presentation.verification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.selada.invesproperti.IntroSliderActivity;
import com.selada.invesproperti.R;
import com.selada.invesproperti.model.UserVerification;
import com.selada.invesproperti.util.PreferenceManager;
import com.white.progressview.HorizontalProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerificationActivity extends AppCompatActivity {

    @BindView(R.id.btn_lanjut)
    Button btn_lanjut;
    @BindView(R.id.img_checked_1)
    ImageView img_checked_1;
    @BindView(R.id.img_checked_2)
    ImageView img_checked_2;
    @BindView(R.id.progressBarItem)
    HorizontalProgressView progressBarItem;

    private boolean IsInvestor = false;
    private boolean IsProjectOwner = false;

    @OnClick(R.id.frame_product)
    void onClickFrameProduct(){
        img_checked_1.setBackground(getResources().getDrawable(R.drawable.bg_circle_unchecked));
        img_checked_2.setBackground(getResources().getDrawable(R.drawable.bg_circle_cheked));
        btn_lanjut.setEnabled(true);
        btn_lanjut.setBackground(getResources().getDrawable(R.drawable.bg_round_green));
        IsInvestor = false;
        IsProjectOwner = true;
    }

    @OnClick(R.id.frame_investor)
    void onClickFrameInvestor(){
        img_checked_1.setBackground(getResources().getDrawable(R.drawable.bg_circle_cheked));
        img_checked_2.setBackground(getResources().getDrawable(R.drawable.bg_circle_unchecked));
        btn_lanjut.setEnabled(true);
        btn_lanjut.setBackground(getResources().getDrawable(R.drawable.bg_round_green));
        IsInvestor = true;
        IsProjectOwner = false;
    }

    @OnClick(R.id.btn_back)
    void onClickBtnBack(){
        onBackPressed();
    }

    @OnClick(R.id.btn_lanjut)
    void onClickBtnLanjut(){
        Intent intent = new Intent(this, VerificationKtpActivity.class);
        intent.putExtra("is_investor", IsInvestor);
        intent.putExtra("is_project_owner", IsProjectOwner);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        ButterKnife.bind(this);

        initComponent();
    }

    private void initComponent() {
        progressBarItem.setProgress(15);

        if(PreferenceManager.getIsSaveVerificationData()) {
            UserVerification userVerification = PreferenceManager.getUserVerification();
            if (userVerification.isInvestor()) {
                img_checked_1.setBackground(getResources().getDrawable(R.drawable.bg_circle_cheked));
                img_checked_2.setBackground(getResources().getDrawable(R.drawable.bg_circle_unchecked));
                btn_lanjut.setEnabled(true);
                btn_lanjut.setBackground(getResources().getDrawable(R.drawable.bg_round_green));
                IsInvestor = true;
                IsProjectOwner = false;
            }

            if (userVerification.isProjectOwner()) {
                img_checked_1.setBackground(getResources().getDrawable(R.drawable.bg_circle_unchecked));
                img_checked_2.setBackground(getResources().getDrawable(R.drawable.bg_circle_cheked));
                btn_lanjut.setEnabled(true);
                btn_lanjut.setBackground(getResources().getDrawable(R.drawable.bg_round_green));
                IsInvestor = false;
                IsProjectOwner = true;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}