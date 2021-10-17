package com.selada.invesproperti.presentation.verification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.UserVerification;
import com.selada.invesproperti.model.response.Bank;
import com.selada.invesproperti.model.response.InvestmentGoal;
import com.selada.invesproperti.model.response.ResponseError;
import com.selada.invesproperti.model.response.ResponseErrorVerification;
import com.selada.invesproperti.util.Loading;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationBankActivity extends AppCompatActivity {

    @BindView(R.id.spinner_bank)
    Spinner spinner_bank;
    @BindView(R.id.et_pemilik_rekening)
    EditText et_pemilik_rekening;
    @BindView(R.id.et_no_rekening)
    EditText et_no_rekening;

    private String bankSelectedItemId = "";
    private List<String> bankIdList;
    private Activity appActivity;
    private UserVerification userVerification;

    @OnClick(R.id.frame_save)
    void onClickSave(){
        //onSave
        PreferenceManager.setIsSaveVerificationData(true);
        setUserVerificationModel();
        MethodUtil.showSnackBar(findViewById(android.R.id.content), "Data berhasil disimpan");
    }

    @OnClick(R.id.btn_back)
    void onClickBtnBack(){
        onBackPressed();
    }

    @OnClick(R.id.btn_lanjut)
    void onClickBtnLanjut(){
        if (TextUtils.isEmpty(et_pemilik_rekening.getText().toString()) || TextUtils.isEmpty(et_no_rekening.getText().toString())) {
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Silahkan lengkapi data diri");
            return;
        }

        setUserVerificationModel();
        doUserVerification();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_bank);
        ButterKnife.bind(this);
        appActivity = this;

        initComponent();
    }

    private void initComponent() {
        userVerification = new UserVerification();
        if (PreferenceManager.getIsSaveVerificationData()){
            userVerification = PreferenceManager.getUserVerification();
            et_no_rekening.setText(userVerification.getAccountNumber()!=null?userVerification.getAccountNumber():"");
            et_pemilik_rekening.setText(userVerification.getAccountName()!=null?userVerification.getAccountName():"");
        }

        getListBank();

        if (PreferenceManager.getIsUnauthorized()){
            MethodUtil.refreshToken(this);
            initComponent();
        }
    }

    private void doUserVerification(){
        Loading.show(appActivity);

        userVerification = PreferenceManager.getUserVerification();

        ByteArrayOutputStream byteKtp = new ByteArrayOutputStream();
        ByteArrayOutputStream byteSelfie = new ByteArrayOutputStream();
        MethodUtil.byteArrayToBitmap(userVerification.getPhotoKtp()).compress(Bitmap.CompressFormat.JPEG, 20, byteKtp);
        MethodUtil.byteArrayToBitmap(userVerification.getPhotoSelfie()).compress(Bitmap.CompressFormat.JPEG, 20, byteSelfie);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("IsInvestor", String.valueOf(userVerification.isInvestor()))
                .addFormDataPart("IsProjectOwner", String.valueOf(userVerification.isProjectOwner()))
                .addFormDataPart("Name", userVerification.getName())
                .addFormDataPart("Gender", userVerification.getGender())
                .addFormDataPart("Birthplace", userVerification.getBirthplace())
                .addFormDataPart("BirthDate", userVerification.getBirthDate())
                .addFormDataPart("EducationId", userVerification.getEducationId())
                .addFormDataPart("OccupationId", userVerification.getOccupationId())
                .addFormDataPart("MaritalStatus", userVerification.getMaritalStatus())
                .addFormDataPart("SpouseName", userVerification.getSpouseName())
                .addFormDataPart("Phone", userVerification.getPhone())
                .addFormDataPart("Email", userVerification.getEmail())
                .addFormDataPart("AddressIdCard", userVerification.getAddressIdCard())
                .addFormDataPart("CityIdCard", userVerification.getCityIdCard())
                .addFormDataPart("DistrictIdCard", userVerification.getDistrictIdCard())
                .addFormDataPart("SubDistrictIdCard", userVerification.getSubDistrictIdCard())
                .addFormDataPart("PostalCodeIdCard", userVerification.getPostalCodeIdCard())
                .addFormDataPart("IsDomicileSameWithIdCard", String.valueOf(userVerification.getIsDomicileSameWithIdCard()))
                .addFormDataPart("IdCardNumber", userVerification.getIdCardNumber())
                .addFormDataPart("MotherName", userVerification.getMotherName())
                .addFormDataPart("HeirName", userVerification.getHeirName())
                .addFormDataPart("Relation", userVerification.getRelation())
                .addFormDataPart("YearlyIncome", userVerification.getYearlyIncome())
                .addFormDataPart("FundSourceId", userVerification.getFundSourceId())
                .addFormDataPart("InvestmentGoalId", userVerification.getInvestmentGoalId())
                .addFormDataPart("HasSecuritiesAccount", String.valueOf(userVerification.getHasSecuritiesAccount()))
                .addFormDataPart("Password", userVerification.getPassword())
                .addFormDataPart("BankId", userVerification.getBankId())
                .addFormDataPart("AccountName", userVerification.getAccountName())
                .addFormDataPart("AccountNumber", userVerification.getAccountNumber())
                .addFormDataPart("IdCardFile", "id_card_file.jpeg", RequestBody.create(MediaType.parse("image/jpeg"), byteKtp.toByteArray()))
                .addFormDataPart("SelfieFile", "selfie_file.jpeg", RequestBody.create(MediaType.parse("image/jpeg"), byteSelfie.toByteArray()))
                .build();

        ApiCore.apiInterface().doUserVerification(requestBody, PreferenceManager.getSessionToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Loading.hide(appActivity);
                try {
                    if (response.isSuccessful()){
                        PreferenceManager.setUserVerification(null);
                        PreferenceManager.setIsSaveVerificationData(false);
                        Intent intent = new Intent(appActivity, VerificationCompleteActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        appActivity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    } else {
                        if (response.message().toLowerCase().contains("unauthorized")){
                            PreferenceManager.setIsUnauthorized(true);
                        } else {
                            ResponseErrorVerification responseError = new Gson().fromJson(Objects.requireNonNull(response.body()).charStream(), ResponseErrorVerification.class);
                            String errorMessage = responseError.getTitle();
                            Dialog dialog = MethodUtil.getDialogCart(R.layout.dialog_lottie_failed, VerificationBankActivity.this);
                            TextView textView = dialog.findViewById(R.id.tv_msg);
                            textView.setText(errorMessage);
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    MethodUtil.showOnCatch(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Loading.hide(appActivity);
            }
        });
    }

    private void getListBank(){
        Loading.show(this);
        bankIdList = new ArrayList<>();
        List<String> bankList = new ArrayList<>();
        ApiCore.apiInterface().getListBank(PreferenceManager.getSessionToken()).enqueue(new Callback<List<Bank>>() {
            @Override
            public void onResponse(Call<List<Bank>> call, Response<List<Bank>> response) {
                Loading.hide(appActivity);
                try {
                    if (response.isSuccessful()){
                        for (int i = 0; i < response.body().size(); i++){
                            Bank bank = response.body().get(i);
                            bankList.add(bank.getName());
                            bankIdList.add(bank.getId());
                        }

                        ArrayAdapter aa = new ArrayAdapter(appActivity, R.layout.custom_simple_spinner_item, bankList);
                        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                        spinner_bank.setAdapter(aa);
                        spinner_bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                bankSelectedItemId = bankIdList.get(i);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        if (userVerification.getBankId()!=null) {
                            for (int i = 0; i < bankIdList.size(); i++){
                                if (bankIdList.get(i).equals(userVerification.getBankId())){
                                    spinner_bank.setSelection(i);
                                }
                            }
                        }
                    } else {
                        if (response.message().toLowerCase().contains("unauthorized")){
                            PreferenceManager.setIsUnauthorized(true);
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    MethodUtil.showOnCatch(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<List<Bank>> call, Throwable t) {
                t.printStackTrace();
                Loading.hide(appActivity);
            }
        });
    }

    private void setUserVerificationModel() {
        UserVerification userVerification = PreferenceManager.getUserVerification();
        userVerification.setBankId(bankSelectedItemId);
        userVerification.setAccountName(et_pemilik_rekening.getText().toString());
        userVerification.setAccountNumber(et_no_rekening.getText().toString());
        PreferenceManager.setUserVerification(userVerification);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}