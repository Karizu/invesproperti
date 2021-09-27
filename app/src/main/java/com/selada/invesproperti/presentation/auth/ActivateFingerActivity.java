package com.selada.invesproperti.presentation.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.selada.invesproperti.MainActivity;
import com.selada.invesproperti.R;
import com.selada.invesproperti.util.FingerPrintAuthCallback;
import com.selada.invesproperti.util.FingerPrintAuthHelper;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.util.concurrent.Executor;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivateFingerActivity extends AppCompatActivity implements FingerPrintAuthCallback {

    private FingerPrintAuthHelper printAuthHelper;
    private Context context;

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.btn_activate)
    void onClickBtnActivate(){
        Dialog dialog = MethodUtil.getDialogCart(R.layout.dialog_finger_print, context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        TextView tv_batal = dialog.findViewById(R.id.tv_batal);
        tv_batal.setOnClickListener(view -> {
            dialog.dismiss();
        });

        boolean allowFingerPrintAuth = FingerPrintAuthHelper.isFingerPrintSupported(this);
        printAuthHelper = new FingerPrintAuthHelper(this, this);
        if(allowFingerPrintAuth){
            printAuthHelper.startAuth();
        } else{
            dialog.dismiss();
            Dialog dialogs = MethodUtil.getDialogCart(R.layout.dialog_finger_failed, context);
            dialogs.setCancelable(false);
            dialogs.setCanceledOnTouchOutside(false);
            CardView btnBatal = dialogs.findViewById(R.id.btn_batal);
            TextView tvMsg = dialogs.findViewById(R.id.tv_msg);
            tvMsg.setText("Perangkat anda tidak mendukung fitur fingerprint");
            btnBatal.setOnClickListener(view -> dialogs.dismiss());
        }
    }

    @OnClick(R.id.tv_lewati)
    void onClickTvLewati(){
        PreferenceManager.setIsFingerActive(false);
        directToMainActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_finger);
        ButterKnife.bind(this);
        new PreferenceManager(this);
        context = this;
    }

    @Override
    public void onAuthSuccess(FingerprintManager.AuthenticationResult result) {
        PreferenceManager.setIsFingerActive(true);
        directToMainActivity();
    }

    @Override
    public void onAuthFailed() {
        Dialog dialog = MethodUtil.getDialogCart(R.layout.dialog_finger_failed, context);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        CardView btnBatal = dialog.findViewById(R.id.btn_batal);
        btnBatal.setOnClickListener(view -> dialog.dismiss());
    }

    private void directToMainActivity() {
        Intent intent = new Intent(ActivateFingerActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}