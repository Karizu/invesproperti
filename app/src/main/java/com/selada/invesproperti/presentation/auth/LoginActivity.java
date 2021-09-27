package com.selada.invesproperti.presentation.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.selada.invesproperti.MainActivity;
import com.selada.invesproperti.R;
import com.selada.invesproperti.presentation.RegisterActivity;
import com.selada.invesproperti.util.FingerPrintAuthCallback;
import com.selada.invesproperti.util.FingerPrintAuthHelper;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements FingerPrintAuthCallback {

    private FingerPrintAuthHelper printAuthHelper;
    private boolean isLogin = false;

    @OnClick(R.id.btn_text_register)
    void onClickBtnTextRegister(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.btn_finger)
    void onClickBtnFinger(){
        //Check if the device supports finger print hardware
        if (PreferenceManager.getFingerActive()){
            boolean allowFingerPrintAuth = FingerPrintAuthHelper.isFingerPrintSupported(this);
            printAuthHelper = new FingerPrintAuthHelper(this, this);

            Dialog dialog = MethodUtil.getDialogCart(R.layout.dialog_finger_print, this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            TextView tv_batal = dialog.findViewById(R.id.tv_batal);
            tv_batal.setOnClickListener(view -> {
                printAuthHelper.onAuthenticationError(0,"");
                dialog.dismiss();
            });

            if(allowFingerPrintAuth){
                printAuthHelper.startAuth();
            } else{
                dialog.dismiss();
                Dialog dialogs = MethodUtil.getDialogCart(R.layout.dialog_finger_failed, this);
                dialogs.setCancelable(false);
                dialogs.setCanceledOnTouchOutside(false);
                CardView btnBatal = dialogs.findViewById(R.id.btn_batal);
                TextView tvMsg = dialogs.findViewById(R.id.tv_msg);
                tvMsg.setText("Perangkat anda tidak mendukung fitur fingerprint");
                btnBatal.setOnClickListener(view -> dialogs.dismiss());
            }
        } else {
            Dialog dialogs = MethodUtil.getDialogCart(R.layout.dialog_finger_failed, this);
            dialogs.setCancelable(false);
            dialogs.setCanceledOnTouchOutside(false);
            CardView btnBatal = dialogs.findViewById(R.id.btn_batal);
            TextView tvMsg = dialogs.findViewById(R.id.tv_msg);
            tvMsg.setText("Silahkan aktifkan otentikasi sidik jari terlebih dahulu pada menu pengaturan");
            btnBatal.setOnClickListener(view -> dialogs.dismiss());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initComponent();
    }

    private void initComponent() {
        isLogin = getIntent().getBooleanExtra("isLogin", false);
        if (isLogin){
            onClickBtnFinger();
        }
    }

    @Override
    public void onAuthSuccess(FingerprintManager.AuthenticationResult result) {
        directToMainActivity();
    }

    @Override
    public void onAuthFailed() {
        Dialog dialog = MethodUtil.getDialogCart(R.layout.dialog_finger_failed, this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        CardView btnBatal = dialog.findViewById(R.id.btn_batal);
        btnBatal.setOnClickListener(view -> dialog.dismiss());
    }

    private void directToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}