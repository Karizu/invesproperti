package com.selada.invesproperti.presentation.portofolio.submitproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.selada.invesproperti.MainActivity;
import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.PhotoUriModel;
import com.selada.invesproperti.model.request.SubmitProductRequest;
import com.selada.invesproperti.model.response.Bank;
import com.selada.invesproperti.util.Loading;
import com.selada.invesproperti.util.LoadingPost;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.io.File;
import java.io.IOException;
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

import static com.selada.invesproperti.presentation.profile.ProfileFragment.getRealPathFromURI;

public class SubmitProject6Activity extends AppCompatActivity {

    @BindView(R.id.et_account_number)
    EditText et_account_number;
    @BindView(R.id.et_account_name)
    EditText et_account_name;
    @BindView(R.id.spinner_bank_account_type)
    Spinner spinner_bank_account_type;
    @BindView(R.id.spinner_bank)
    Spinner spinner_bank;

    private Context context;
    private String bankSelectedItemId = "";
    private String accountTypeSelectedItem = "";
    private SubmitProductRequest submitProductRequest;
    private String bankNameSaved = "";
    private PhotoUriModel mArrayUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_project6);
        ButterKnife.bind(this);
//        new PreferenceManager(this);
        context = this;
        initComponent();
    }

    private void initComponent() {
        try {
            String jsonPhotos = getIntent().getStringExtra("json_photos");
            mArrayUri = new Gson().fromJson(jsonPhotos, PhotoUriModel.class);
            Log.d("jsonPhotos", new Gson().toJson(mArrayUri));
        } catch (Exception e){
            e.printStackTrace();
        }
        getListBank();
        getAccountType();
        submitProductRequest = PreferenceManager.getSubmitProject();
        if (PreferenceManager.getIsSaveProductData()) {
            try {
                spinner_bank.setSelection(MethodUtil.getIndex(spinner_bank, bankNameSaved));
                spinner_bank_account_type.setSelection(MethodUtil.getIndex(spinner_bank_account_type, submitProductRequest.getBankAccountType()));
                et_account_name.setText(submitProductRequest.getAccountName());
                et_account_number.setText(submitProductRequest.getAccountNumber());
            } catch (Exception e){}
        }
    }

    private void getAccountType() {
        String[] strings = {"GIRO", "TABUNGAN"};
        ArrayAdapter aa = new ArrayAdapter(SubmitProject6Activity.this, R.layout.custom_simple_spinner_item, strings);
        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_bank_account_type.setAdapter(aa);
        spinner_bank_account_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                accountTypeSelectedItem = strings[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getListBank() {
        Loading.show(this);
        List<String> bankIdList = new ArrayList<>();
        List<String> bankList = new ArrayList<>();
        ApiCore.apiInterface().getListBank(PreferenceManager.getSessionToken()).enqueue(new Callback<List<Bank>>() {
            @Override
            public void onResponse(Call<List<Bank>> call, Response<List<Bank>> response) {
                Loading.hide(SubmitProject6Activity.this);
                try {
                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().size(); i++) {
                            Bank bank = response.body().get(i);
                            bankList.add(bank.getName());
                            bankIdList.add(bank.getId());
                            if (PreferenceManager.getIsSaveProductData()) {
                                if (bank.getId().equals(submitProductRequest.getBankId())){
                                    bankNameSaved = bank.getName();
                                }
                            }
                        }

                        ArrayAdapter aa = new ArrayAdapter(SubmitProject6Activity.this, R.layout.custom_simple_spinner_item, bankList);
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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    MethodUtil.showOnCatch(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<List<Bank>> call, Throwable t) {
                t.printStackTrace();
                Loading.hide(SubmitProject6Activity.this);
            }
        });
    }

    private void setProductDataModel() {
        submitProductRequest.setBankAccountType(accountTypeSelectedItem);
        submitProductRequest.setBankId(bankSelectedItemId);
        submitProductRequest.setAccountName(et_account_name.getText().toString());
        submitProductRequest.setAccountNumber(et_account_number.getText().toString());

        Log.d("SubmitProductRequest", new Gson().toJson(submitProductRequest));
        PreferenceManager.setSubmitProject(submitProductRequest);
    }

    private void createProduct() {

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("name", submitProductRequest.getName());
        builder.addFormDataPart("code", submitProductRequest.getCode());
        builder.addFormDataPart("detail", submitProductRequest.getDetail());
        builder.addFormDataPart("longitude", submitProductRequest.getLongitude());
        builder.addFormDataPart("latitude", submitProductRequest.getLatitude());
        builder.addFormDataPart("businessTypeId", submitProductRequest.getBusinessTypeId());
        builder.addFormDataPart("totalLot", submitProductRequest.getTotalLot());
        builder.addFormDataPart("pricePerLot", submitProductRequest.getPricePerLot());
        builder.addFormDataPart("requestedAmount", submitProductRequest.getRequestedAmount());
        builder.addFormDataPart("dividenPeriod", submitProductRequest.getDividenPeriod());
        builder.addFormDataPart("dividenPeriodUOM", submitProductRequest.getDividenPeriodUOM());
        builder.addFormDataPart("deadlineDate", submitProductRequest.getDeadlineDate());
        builder.addFormDataPart("prospectus", "prospectus.pdf", getFile(submitProductRequest.getProspectus()));
        builder.addFormDataPart("establishmentDate", submitProductRequest.getEstablishmentDate());
        builder.addFormDataPart("establishmentDateEstimation", submitProductRequest.getEstablishmentDateEstimation());
        builder.addFormDataPart("monthlyIncome", submitProductRequest.getMonthlyIncome());
        builder.addFormDataPart("monthlyIncomeUOM", "IDR");
        builder.addFormDataPart("addressProject", submitProductRequest.getAddressProject());
        builder.addFormDataPart("rtProject", submitProductRequest.getRtProject());
        builder.addFormDataPart("rwProject", submitProductRequest.getRwProject());
        builder.addFormDataPart("cityIdProject", submitProductRequest.getCityIdProject());
        builder.addFormDataPart("districtProject", submitProductRequest.getDistrictProject());
        builder.addFormDataPart("subDistrictProject", submitProductRequest.getSubDistrictProject());
        builder.addFormDataPart("postalCodeProject", submitProductRequest.getPostalCodeProject());
        builder.addFormDataPart("isCompanySameWithProject", submitProductRequest.isCompanySameWithProject() + "");
        builder.addFormDataPart("minDividenEstimation", submitProductRequest.getMinDividenEstimation());
        builder.addFormDataPart("maxDividenEstimation", submitProductRequest.getMaxDividenEstimation());
        builder.addFormDataPart("dividenUOM", submitProductRequest.getDividenUOM());
        builder.addFormDataPart("dividenAmountPeriod", submitProductRequest.getDividenAmountPeriod());
        builder.addFormDataPart("dividenAmountPeriodUOM", submitProductRequest.getDividenAmountPeriodUOM());
        builder.addFormDataPart("companyName", submitProductRequest.getCompanyName());
        builder.addFormDataPart("companyType", submitProductRequest.getCompanyType());
        builder.addFormDataPart("picPosition", submitProductRequest.getPicPosition());
        builder.addFormDataPart("procuration", "procuration.pdf", getFile(submitProductRequest.getProcuration()));
        builder.addFormDataPart("aktaFile", "aktaFile.pdf", getFile(submitProductRequest.getAktaFile()));
        builder.addFormDataPart("aktaNumber", submitProductRequest.getAktaNumber());
        builder.addFormDataPart("taxCardFile", "taxCardFile.pdf", getFile(submitProductRequest.getTaxCardFile()));
        builder.addFormDataPart("taxCardNumber", submitProductRequest.getTaxCardNumber());
        builder.addFormDataPart("aHUFile", "aHUFile.pdf", getFile(submitProductRequest.getaHUFile()));
        builder.addFormDataPart("aHUNumber", submitProductRequest.getaHUNumber());
        builder.addFormDataPart("bankAccountType", accountTypeSelectedItem);
        builder.addFormDataPart("bankId", bankSelectedItemId);
        builder.addFormDataPart("accountName", et_account_name.getText().toString());
        builder.addFormDataPart("accountNumber", et_account_number.getText().toString());

        for (int i = 0; i < submitProductRequest.getPhotos().size(); i++) {
            String filePath = submitProductRequest.getPhotos().get(i);
            File file = getPhotos(filePath);
            builder.addFormDataPart("photos", Objects.requireNonNull(file).getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }

        RequestBody requestBody = builder.build();

        LoadingPost.show(SubmitProject6Activity.this);
        ApiCore.apiInterface().createProject(requestBody, PreferenceManager.getSessionToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                LoadingPost.hide(SubmitProject6Activity.this);
                try {
                    if (response.isSuccessful()) {
                        PreferenceManager.setIsSaveProductData(false);
                        PreferenceManager.setSubmitProject(new SubmitProductRequest());
                        Intent intent = new Intent(SubmitProject6Activity.this, SubmitProjectCompleteActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        SubmitProject6Activity.this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    } else {
                        MethodUtil.getErrorMessage(response.errorBody(), SubmitProject6Activity.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    MethodUtil.showOnCatch(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LoadingPost.hide(SubmitProject6Activity.this);
                t.printStackTrace();
            }
        });
    }

    private RequestBody getFile(String imagePath) {
        if (imagePath != null) {
            File file = new File(imagePath);
            Log.i("Register", "Nombre del archivo " + file.getName());

            return RequestBody.create(MediaType.parse("multipart/form-data"), file);
        }

        return RequestBody.create(MediaType.parse("multipart/form-data"), "file.pdf");
    }

    private File getPhotos(String imagePath) {
        if (imagePath != null) {
            File file = new File(imagePath);
            Log.i("Register", "Nombre del archivo " + file.getName());

            return file;
        }

        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        submitProductRequest = PreferenceManager.getSubmitProject();
    }

    @Override
    protected void onResume() {
        super.onResume();
        submitProductRequest = PreferenceManager.getSubmitProject();
    }

    @OnClick(R.id.btn_lanjut)
    void onClickLanjut() {
        setProductDataModel();

        createProduct();
    }

    @OnClick(R.id.frame_save)
    void onClickSave() {
        //onSave
        PreferenceManager.setIsSaveProductData(true);
        setProductDataModel();
        MethodUtil.showSnackBar(findViewById(android.R.id.content), "Data berhasil disimpan");
    }
}