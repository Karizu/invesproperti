package com.selada.invesproperti.presentation.portofolio.withdrawal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;

import com.selada.invesproperti.MainActivity;
import com.selada.invesproperti.R;
import com.selada.invesproperti.presentation.auth.ActivateFingerActivity;
import com.selada.invesproperti.util.FingerPrintAuthCallback;
import com.selada.invesproperti.util.FingerPrintAuthHelper;
import com.selada.invesproperti.util.MethodUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class KonfirmasiWithdrawalActivity extends AppCompatActivity implements FingerPrintAuthCallback {

    private FingerPrintAuthHelper printAuthHelper;

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.frame_pass)
    void onClickFramePass(){
        Intent intent = new Intent(this, KonfirmasiWithdrawalPassActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @OnClick(R.id.etKataSandi)
    void onClickSandi(){
        Intent intent = new Intent(this, KonfirmasiWithdrawalPassActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
        finish();
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onAuthSuccess(FingerprintManager.AuthenticationResult result) {
        Intent intent = new Intent(this, CompleteWithdrawalActivity.class);
        startActivity(intent);
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