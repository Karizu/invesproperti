package com.selada.invesproperti.presentation.portofolio.withdrawal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;

import com.selada.invesproperti.R;
import com.selada.invesproperti.presentation.profile.cs.CallCenterActivity;
import com.selada.invesproperti.util.FingerPrintAuthCallback;
import com.selada.invesproperti.util.FingerPrintAuthHelper;
import com.selada.invesproperti.util.MethodUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WithdrawalConfirmationActivity extends AppCompatActivity implements FingerPrintAuthCallback {

    private FingerPrintAuthHelper printAuthHelper;

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.frame_pass)
    void onClickFramePass(){
        Intent intent = new Intent(this, WithdrawalConfirmationPassActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.etKataSandi)
    void onClickSandi(){
        Intent intent = new Intent(this, WithdrawalConfirmationPassActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btn_cs)
    void onClickCs(){
        Intent intent = new Intent(this, CallCenterActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_withdrawal);
        ButterKnife.bind(this);

        boolean allowFingerPrintAuth = FingerPrintAuthHelper.isFingerPrintSupported(this);
        printAuthHelper = new FingerPrintAuthHelper(this, this);

        if(allowFingerPrintAuth){
            printAuthHelper.startAuth();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public void onAuthSuccess(FingerprintManager.AuthenticationResult result) {
        Intent intent = new Intent(this, CompleteWithdrawalActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public void onAuthFailed() {
        Dialog dialog = MethodUtil.getDialogCart(R.layout.dialog_finger_failed, this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        CardView btnBatal = dialog.findViewById(R.id.btn_batal);
        btnBatal.setOnClickListener(view -> dialog.dismiss());
    }
}