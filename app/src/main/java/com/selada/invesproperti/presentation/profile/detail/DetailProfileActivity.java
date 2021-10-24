package com.selada.invesproperti.presentation.profile.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.response.ResponseUserProfile;
import com.selada.invesproperti.presentation.profile.detail.efek.ChangeEfekActivity;
import com.selada.invesproperti.presentation.profile.detail.income.ChangeIncomeYearActivity;
import com.selada.invesproperti.presentation.profile.detail.pass.ChangePassActivity;
import com.selada.invesproperti.presentation.profile.detail.phone.ChangePhoneActivity;
import com.selada.invesproperti.presentation.profile.disclaimer.DisclaimerActivity;
import com.selada.invesproperti.util.Constant;
import com.selada.invesproperti.util.Loading;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProfileActivity extends AppCompatActivity {

    @BindView(R.id.tv_fullname)
    TextView tv_fullname;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_no_ktp)
    TextView tv_no_ktp;
    @BindView(R.id.tv_pendapatan_tahunan)
    TextView tv_pendapatan_tahunan;
    @BindView(R.id.tv_tahun_rekening)
    TextView tv_tahun_rekening;
    @BindView(R.id.tv_alamat)
    TextView tv_alamat;

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.btn_ubah_pass)
    void onClickUbahPass(){
        Intent intent = new Intent(DetailProfileActivity.this, ChangePassActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btn_ubah_no)
    void onClickUbahNo(){
        Intent intent = new Intent(DetailProfileActivity.this, ChangePhoneActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btn_ubah_pendapatan)
    void onClickUbahPendapatan(){
        Intent intent = new Intent(DetailProfileActivity.this, ChangeIncomeYearActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btn_ubah_tahun)
    void onClickUbahTahun(){
        Intent intent = new Intent(DetailProfileActivity.this, ChangeEfekActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profile);
        ButterKnife.bind(this);
        new PreferenceManager(this);
        getUserProfile();
    }

    private void getUserProfile(){
        Loading.show(DetailProfileActivity.this);
        ApiCore.apiInterface().getUserProfile(PreferenceManager.getSessionToken()).enqueue(new Callback<ResponseUserProfile>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResponseUserProfile> call, Response<ResponseUserProfile> response) {
                Loading.hide(DetailProfileActivity.this);
                try {
                    if (response.isSuccessful()){
                        ResponseUserProfile userProfile = response.body();

                        PreferenceManager.setResponseUserProfile(userProfile);

                        String fullname = Objects.requireNonNull(userProfile).getName();
                        String email = userProfile.getEmail();
                        String phone = userProfile.getPhone();
                        String ktp = userProfile.getIdCardNumber();
                        int yearlyIncome = userProfile.getYearlyIncome();
                        String address = userProfile.getAddress().getStreet() + " " +userProfile.getAddress().getSubDistrict() + " " + userProfile.getAddress().getDistrict();

                        tv_fullname.setText(fullname);
                        tv_email.setText(email);
                        tv_phone.setText(phone);
                        tv_no_ktp.setText(ktp);
                        tv_pendapatan_tahunan.setText("Rp " + MethodUtil.toCurrencyFormat(String.valueOf(yearlyIncome)));
                        tv_alamat.setText(address);

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
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseUserProfile> call, Throwable t) {
                Loading.hide(DetailProfileActivity.this);
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}