package com.selada.invesproperti.presentation.verification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.UserVerification;
import com.selada.invesproperti.model.response.City;
import com.selada.invesproperti.model.response.Education;
import com.selada.invesproperti.model.response.Province;
import com.selada.invesproperti.util.Loading;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationData2Activity extends AppCompatActivity {

    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_alamat_ktp)
    EditText et_alamat_ktp;
    @BindView(R.id.et_kodepos)
    EditText et_kodepos;
    @BindView(R.id.spinner_provinsi)
    Spinner spinner_provinsi;
    @BindView(R.id.spinner_kota)
    Spinner spinner_kota;
    @BindView(R.id.spinner_kecamatan)
    Spinner spinner_kecamatan;
    @BindView(R.id.spinner_kelurahan)
    Spinner spinner_kelurahan;
    @BindView(R.id.cb_sama_ktp)
    CheckBox cb_sama_ktp;

    private Activity appActivity;
    private boolean isInvestor, isProjectOwner, isDomicileSameWithIdCard;
    private byte[] photoKtp, photoSelfie;
    private List<String> provinceIdList, cityIdList, districtIdList, subDistrictIdList;
    private String fullname, maritalStatus, occupationId, educationId, birthDay, birthPlace, gender, spouseName;
    private String provinceSelectedItemId, citySelectedItemId, districtSelectedItemId, subDistrictSelectedItemId;
    private UserVerification userVerification;

    @OnClick(R.id.btn_back)
    void onClickBtnBack(){
        onBackPressed();
    }

    @OnClick(R.id.cb_sama_ktp)
    void onClickCbSamaKtp(){
        if (cb_sama_ktp.isChecked()){
            isDomicileSameWithIdCard = false;
            cb_sama_ktp.setChecked(false);
        } else {
            isDomicileSameWithIdCard = true;
            cb_sama_ktp.setChecked(true);
        }
    }

    @OnClick(R.id.frame_save)
    void onClickSave(){
        //onSave
        PreferenceManager.setIsSaveVerificationData(true);
        setUserVerificationModel();
        MethodUtil.showSnackBar(findViewById(android.R.id.content), "Data berhasil disimpan");
    }

    @OnClick(R.id.btn_lanjut)
    void onClickBtnLanjut(){
        if (TextUtils.isEmpty(et_alamat_ktp.getText().toString()) || TextUtils.isEmpty(et_email.getText().toString())
                || TextUtils.isEmpty(et_kodepos.getText().toString()) || TextUtils.isEmpty(et_phone.getText().toString())) {
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Silahkan lengkapi data diri");
            return;
        }

        String email = et_email.getText().toString().trim();
        if (!email.matches(MethodUtil.emailPattern())){
            et_email.setError("Format email salah");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Format email salah");
            return;
        }

        setUserVerificationModel();

        Intent intent = new Intent(this, VerificationData3Activity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_data2);
        ButterKnife.bind(this);
        appActivity = this;
        initComponent();
    }

    private void initComponent() {
        if (getIntent() != null){
            isInvestor = getIntent().getBooleanExtra("is_investor", false);
            isProjectOwner = getIntent().getBooleanExtra("is_project_owner", false);
            photoKtp = getIntent().getByteArrayExtra("photo_ktp");
            photoSelfie = getIntent().getByteArrayExtra("photo_selfie");
            fullname = getIntent().getStringExtra("fullname");
            gender = getIntent().getStringExtra("gender");
            birthPlace = getIntent().getStringExtra("birth_place");
            birthPlace = getIntent().getStringExtra("birth_date");
            educationId = getIntent().getStringExtra("education_id");
            occupationId = getIntent().getStringExtra("occupation_id");
            maritalStatus = getIntent().getStringExtra("marital_status");
            spouseName = getIntent().getStringExtra("spouse_name");
        }

            userVerification = new UserVerification();
        if (PreferenceManager.getIsSaveVerificationData()){
            userVerification = PreferenceManager.getUserVerification();
            et_phone.setText(userVerification.getPhone()!=null?userVerification.getPhone():"");
            et_kodepos.setText(userVerification.getPostalCodeIdCard()!=null?userVerification.getPostalCodeIdCard():"");
            et_email.setText(userVerification.getEmail()!=null?userVerification.getEmail():"");
            et_alamat_ktp.setText(userVerification.getAddressIdCard()!=null?userVerification.getAddressIdCard():"");
        }

//        setValidEmail(et_email);
        getListProvince();
        getListCity();
        getDistrict();
        getSubDistrict();
        if (PreferenceManager.getIsUnauthorized()){
            MethodUtil.refreshToken(this);
            initComponent();
        }
    }

    private void setValidEmail(final  EditText emailValidate){
        String email = emailValidate.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        emailValidate.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (email.matches(emailPattern) && s.length() > 0){

                } else {
                    MethodUtil.showSnackBar(findViewById(android.R.id.content), "format email salah");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });
    }

    private void getListProvince(){
        Loading.show(this);

        provinceIdList = new ArrayList<>();
        List<String> provinceList = new ArrayList<>();
        ApiCore.apiInterface().getListProvince(PreferenceManager.getSessionToken()).enqueue(new Callback<List<Province>>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                Loading.hide(appActivity);
                try {
                    if (response.isSuccessful()){
                        for (int i = 0; i < response.body().size(); i++){
                            Province province = response.body().get(i);
                            provinceList.add(province.getName());
                            provinceIdList.add(province.getId());
                        }

                        ArrayAdapter aa = new ArrayAdapter(appActivity, R.layout.custom_simple_spinner_item, provinceList);
                        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                        spinner_provinsi.setAdapter(aa);
                        spinner_provinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                provinceSelectedItemId = provinceIdList.get(i);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    } else {
                        if (response.message().toLowerCase().contains("unauthorized")){
                            PreferenceManager.setIsUnauthorized(true);
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Province>> call, Throwable t) {
                Loading.hide(appActivity);
                t.printStackTrace();
            }
        });
    }

    private void getListCity(){
        Loading.show(this);

        cityIdList = new ArrayList<>();
        List<String> cityList = new ArrayList<>();
        ApiCore.apiInterface().getListCity(PreferenceManager.getSessionToken()).enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                Loading.hide(appActivity);
                try {
                    if (response.isSuccessful()){
                        for (int i = 0; i < response.body().size(); i++){
                            City city = response.body().get(i);
                            cityList.add(city.getName());
                            cityIdList.add(city.getId());
                        }

                        ArrayAdapter aa = new ArrayAdapter(appActivity, R.layout.custom_simple_spinner_item, cityList);
                        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                        spinner_kota.setAdapter(aa);
                        spinner_kota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                citySelectedItemId = cityIdList.get(i);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        if (userVerification.getCityIdCard()!=null) {
                            for (int i = 0; i < cityIdList.size(); i++){
                                if (cityIdList.get(i).equals(userVerification.getCityIdCard())){
                                    spinner_kota.setSelection(i);
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
                }
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                Loading.hide(appActivity);
                t.printStackTrace();
            }
        });
    }

    private void getDistrict() {
        String[] district = {"Cibeunying", "Kiaracondong", "Margahayu"};
        ArrayAdapter aa_3 = new ArrayAdapter(this, R.layout.custom_simple_spinner_item, district){
            @Override
            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    return false;
//                } else {
//                    return true;
//                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textview = (TextView) view;
//                if (position == 0) {
//                    textview.setTextColor(getResources().getColor(R.color.grey_text));
//                } else {
//                    textview.setTextColor(getResources().getColor(R.color.black_primary));
//                }
                return view;
            }
        };
        aa_3.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_kecamatan.setAdapter(aa_3);
        spinner_kecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                districtSelectedItemId = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (userVerification.getDistrictIdCard() != null) {
            int spinnerPosition = aa_3.getPosition(userVerification.getDistrictIdCard());
            spinner_kecamatan.setSelection(spinnerPosition);
        }
    }

    private void getSubDistrict() {
        String[] subDistrict = {"Antapani", "Cicaheum", "Rancaekek Kencama"};
        ArrayAdapter aa_3 = new ArrayAdapter(this, R.layout.custom_simple_spinner_item, subDistrict){
            @Override
            public boolean isEnabled(int position) {
//                if (position == 0) {
//                    return false;
//                } else {
//                    return true;
//                }
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textview = (TextView) view;
//                if (position == 0) {
//                    textview.setTextColor(getResources().getColor(R.color.grey_text));
//                } else {
//                    textview.setTextColor(getResources().getColor(R.color.black_primary));
//                }
                return view;
            }
        };
        aa_3.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_kelurahan.setAdapter(aa_3);
        spinner_kelurahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subDistrictSelectedItemId = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (userVerification.getSubDistrictIdCard() != null) {
            int spinnerPosition = aa_3.getPosition(userVerification.getSubDistrictIdCard());
            spinner_kelurahan.setSelection(spinnerPosition);
        }
    }

    private void setUserVerificationModel() {
        UserVerification userVerification = PreferenceManager.getUserVerification();
        userVerification.setCityIdCard(citySelectedItemId);
        userVerification.setDistrictIdCard(districtSelectedItemId);
        userVerification.setSubDistrictIdCard(subDistrictSelectedItemId);
        userVerification.setPhone(et_phone.getText().toString().startsWith("0")?"62" + et_phone.getText().toString().substring(1) : "62" + et_phone.getText().toString());
        userVerification.setEmail(et_email.getText().toString());
        userVerification.setAddressIdCard(et_alamat_ktp.getText().toString());
        userVerification.setPostalCodeIdCard(et_kodepos.getText().toString());
        userVerification.setIsDomicileSameWithIdCard(isDomicileSameWithIdCard);
        PreferenceManager.setUserVerification(userVerification);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}