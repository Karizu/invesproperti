package com.selada.invesproperti.presentation.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.selada.invesproperti.MainActivity;
import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.request.RequestLogin;
import com.selada.invesproperti.model.request.RequestRegister;
import com.selada.invesproperti.model.response.ApiResponse;
import com.selada.invesproperti.model.response.ResponseLogin;
import com.selada.invesproperti.model.response.ResponseUserProfile;
import com.selada.invesproperti.util.Constant;
import com.selada.invesproperti.util.FingerPrintAuthCallback;
import com.selada.invesproperti.util.FingerPrintAuthHelper;
import com.selada.invesproperti.util.Loading;
import com.selada.invesproperti.util.LoadingPost;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.anshul.libray.PasswordEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements FingerPrintAuthCallback {

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etKataSandi)
    PasswordEditText etKataSandi;

    private FingerPrintAuthHelper printAuthHelper;

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
            MethodUtil.refreshToken(LoginActivity.this);
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
            if (PreferenceManager.isLogin()){
                Dialog dialogs = MethodUtil.getDialogCart(R.layout.dialog_finger_failed, this);
                dialogs.setCancelable(false);
                dialogs.setCanceledOnTouchOutside(false);
                CardView btnBatal = dialogs.findViewById(R.id.btn_batal);
                CardView btnAktifkan = dialogs.findViewById(R.id.btn_aktifkan);
                TextView tvMsg = dialogs.findViewById(R.id.tv_msg);
                tvMsg.setText("Silahkan aktifkan otentikasi sidik jari terlebih dahulu");
                btnBatal.setOnClickListener(view -> dialogs.dismiss());
                btnAktifkan.setVisibility(View.VISIBLE);
                btnAktifkan.setOnClickListener(view -> {
                    dialogs.dismiss();
                    onClickBtnActivate();
                });
            } else {
                Dialog dialogs = MethodUtil.getDialogCart(R.layout.dialog_finger_failed, this);
                dialogs.setCancelable(false);
                dialogs.setCanceledOnTouchOutside(false);
                CardView btnBatal = dialogs.findViewById(R.id.btn_batal);
                TextView tv_text_btn = dialogs.findViewById(R.id.tv_text_btn);
                tv_text_btn.setText("Kembali");
                TextView tvMsg = dialogs.findViewById(R.id.tv_msg);
                tvMsg.setText("Silahkan login terlebih dahulu");
                btnBatal.setOnClickListener(view -> dialogs.dismiss());
            }
        }
    }

    @OnClick(R.id.btn_login)
    void onClickLogin(){
        if (etEmail.getText().toString().equals("") || etKataSandi.getText().toString().equals("")) {
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Silahkan lengkapi data");
            return;
        }

        String email = etEmail.getText().toString().trim();
        if (email.matches(MethodUtil.emailPattern())){
            doLogin();
        } else {
            etEmail.setError("Format email salah");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Format email salah");
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
        if (PreferenceManager.isLogin()){
            onClickBtnFinger();
        }
    }

    @Override
    public void onAuthSuccess(FingerprintManager.AuthenticationResult result) {
        PreferenceManager.setIsFingerActive(true);
        directToMainActivity();
    }

    private void onClickBtnActivate(){
        Dialog dialog = MethodUtil.getDialogCart(R.layout.dialog_finger_print, LoginActivity.this);
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
            Dialog dialogs = MethodUtil.getDialogCart(R.layout.dialog_finger_failed, LoginActivity.this);
            dialogs.setCancelable(false);
            dialogs.setCanceledOnTouchOutside(false);
            CardView btnBatal = dialogs.findViewById(R.id.btn_batal);
            TextView tvMsg = dialogs.findViewById(R.id.tv_msg);
            tvMsg.setText("Perangkat anda tidak mendukung fitur fingerprint");
            btnBatal.setOnClickListener(view -> dialogs.dismiss());
        }
    }

    private void doLogin(){
        String email = etEmail.getText().toString();
        String pass = etKataSandi.getText().toString();

        RequestLogin requestLogin = new RequestLogin();
        requestLogin.setEmail(email);
        requestLogin.setPassword(pass);

        LoadingPost.show(LoginActivity.this);
        ApiCore.apiInterface().doSignIn(requestLogin).enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                LoadingPost.hide(LoginActivity.this);
                try {
                    if (response.isSuccessful()){
                        PreferenceManager.setIsUnauthorized(false);
                        PreferenceManager.setLoginResponse(response.body(), Constant.LOGIN_FROM_EMAIL);
                        PreferenceManager.setLoginData(Objects.requireNonNull(response.body()).getFullName(), response.body().getEmail());
                        PreferenceManager.setSessionToken("Bearer " + response.body().getAccessToken());

                        switch (Objects.requireNonNull(response.body()).getStatus()){
                            case Constant.GUEST:
                                PreferenceManager.setUserStatus(Constant.GUEST);
                                break;
                            case Constant.ON_VERIFICATION:
                                PreferenceManager.setUserStatus(Constant.ON_VERIFICATION);
                                break;
                            case Constant.VERIFIED:
                                if (response.body().isInvestor()){
                                    PreferenceManager.setUserStatus(Constant.INVESTOR);
                                } else {
                                    PreferenceManager.setUserStatus(Constant.PRODUCT_OWNER);
                                }
                                break;
                        }
                        directToMainActivity();
                    } else {
                        MethodUtil.getErrorMessage(response.errorBody(), LoginActivity.this);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    View view = findViewById(android.R.id.content);
                    MethodUtil.showOnCatch(view);
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                t.printStackTrace();
                LoadingPost.hide(LoginActivity.this);
            }
        });
    }

    @Override
    public void onAuthFailed() {
        try {
            Dialog dialog = MethodUtil.getDialogCart(R.layout.dialog_finger_failed, this);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            CardView btnBatal = dialog.findViewById(R.id.btn_batal);
            btnBatal.setOnClickListener(view -> dialog.dismiss());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void directToMainActivity() {
        if (!PreferenceManager.getFingerActive()){
            Intent intent = new Intent(this, ActivateFingerActivity.class);
            startActivity(intent);
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}