package com.selada.invesproperti.presentation.portofolio.submitproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.PhotoUriModel;
import com.selada.invesproperti.model.request.SubmitProductRequest;
import com.selada.invesproperti.model.response.City;
import com.selada.invesproperti.model.response.Country;
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

public class SubmitProject5Activity extends AppCompatActivity {

    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_rt)
    EditText et_rt;
    @BindView(R.id.et_rw)
    EditText et_rw;
    @BindView(R.id.et_postal_code)
    EditText et_postal_code;
    @BindView(R.id.spinner_country)
    Spinner spinner_country;
    @BindView(R.id.spinner_province)
    Spinner spinner_province;
    @BindView(R.id.spinner_city)
    Spinner spinner_city;
    @BindView(R.id.spinner_district)
    Spinner spinner_district;
    @BindView(R.id.spinner_sub_district)
    Spinner spinner_sub_district;
    @BindView(R.id.cb_same_address)
    CheckBox cb_same_address;
    @BindView(R.id.layout_address)
    LinearLayout layout_address;

    private boolean isSameAddress = false;
    private String countrySelectedItem = "";
    private String provinceSelectedItem = "";
    private String citySelectedItem = "";
    private String districtSelectedItem = "";
    private String subDistrictSelectedItem = "";
    private SubmitProductRequest submitProductRequest;
    private PhotoUriModel mArrayUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_project5);
        ButterKnife.bind(this);
        new PreferenceManager(this);
        getListCountry();
        cb_same_address.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                layout_address.setVisibility(View.GONE);
                isSameAddress = true;
            } else {
                layout_address.setVisibility(View.VISIBLE);
                isSameAddress = false;
            }
        });

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
        submitProductRequest = PreferenceManager.getSubmitProject();
        if (PreferenceManager.getIsSaveProductData()){
            if (submitProductRequest.isCompanySameWithProject()){
                layout_address.setVisibility(View.GONE);
                isSameAddress = true;
                cb_same_address.setChecked(true);
            } else {
                layout_address.setVisibility(View.VISIBLE);
                isSameAddress = false;
                cb_same_address.setChecked(false);
            }
        }
    }

    private void getListCountry(){
        Loading.show(this);

        List<String> countryIdList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        ApiCore.apiInterface().getListCountry(PreferenceManager.getSessionToken()).enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                Loading.hide(SubmitProject5Activity.this);
                try {
                    if (response.isSuccessful()){
                        for (int i = 0; i < response.body().size(); i++){
                            Country country = response.body().get(i);
                            countryList.add(country.getName());
                            countryIdList.add(country.getId());
                        }

                        ArrayAdapter aa = new ArrayAdapter(SubmitProject5Activity.this, R.layout.custom_simple_spinner_item, countryList);
                        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                        spinner_country.setAdapter(aa);
                        spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                countrySelectedItem = countryIdList.get(i);
                                getListProvince(countrySelectedItem);
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
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Loading.hide(SubmitProject5Activity.this);
                t.printStackTrace();
            }
        });
    }

    private void getListProvince(String countrySelectedItemId){
        List<String> provinceIdList = new ArrayList<>();
        List<String> provinceList = new ArrayList<>();
        ApiCore.apiInterface().getListProvince(PreferenceManager.getSessionToken()).enqueue(new Callback<List<Province>>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                try {
                    if (response.isSuccessful()){
                        for (int i = 0; i < response.body().size(); i++){
                            Province province = response.body().get(i);
                            if (province.getCountryId().equals(countrySelectedItemId)) {
                                provinceList.add(province.getName());
                                provinceIdList.add(province.getId());
                            }
                        }

                        ArrayAdapter aa = new ArrayAdapter(SubmitProject5Activity.this, R.layout.custom_simple_spinner_item, provinceList);
                        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                        spinner_province.setAdapter(aa);
                        spinner_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                provinceSelectedItem = provinceIdList.get(i);
                                getListCity(provinceSelectedItem);
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
                t.printStackTrace();
            }
        });
    }

    private void getListCity(String provinceSelectedItemId) {
        List<String> cityIdList = new ArrayList<>();
        List<String> cityList = new ArrayList<>();
        ApiCore.apiInterface().getListCity(PreferenceManager.getSessionToken()).enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                try {
                    if (response.isSuccessful()){
                        for (int i = 0; i < response.body().size(); i++){
                            City city = response.body().get(i);
                            if (city.getProvinceId().equals(provinceSelectedItemId)) {
                                cityList.add(city.getName());
                                cityIdList.add(city.getId());
                            }
                        }

                        ArrayAdapter aa = new ArrayAdapter(SubmitProject5Activity.this, R.layout.custom_simple_spinner_item, cityList);
                        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                        spinner_city.setAdapter(aa);
                        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                citySelectedItem = cityIdList.get(i);
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
            public void onFailure(Call<List<City>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getListDistrict(String citySelectedItemId) {
        String[] district = {"Cibeunying", "Kiaracondong", "Margahayu"};

        ArrayAdapter aa = new ArrayAdapter(SubmitProject5Activity.this, R.layout.custom_simple_spinner_item, district);
        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_district.setAdapter(aa);
        spinner_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                districtSelectedItem = adapterView.getSelectedItem().toString();
                getListSubDistrict(districtSelectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getListSubDistrict(String districtSelectedItem) {
        String[] subDistrict = {"Antapani", "Cicaheum", "Rancaekek Kencama"};

        ArrayAdapter aa = new ArrayAdapter(SubmitProject5Activity.this, R.layout.custom_simple_spinner_item, subDistrict);
        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_sub_district.setAdapter(aa);
        spinner_sub_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subDistrictSelectedItem = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setProductDataModel() {
        submitProductRequest.setCompanySameWithProject(isSameAddress);
        if (!isSameAddress){
            
        }

        Log.d("SubmitProductRequest", new Gson().toJson(submitProductRequest));
        PreferenceManager.setSubmitProject(submitProductRequest);
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
    void onClickLanjut(){

        if (!isSameAddress){
            if (TextUtils.isEmpty(et_address.getText().toString())){
                et_address.setError("Alamat usaha tidak boleh kosong");
                MethodUtil.showSnackBar(findViewById(android.R.id.content), "Alamat usaha tidak boleh kosong");
                return;
            }

            if (TextUtils.isEmpty(et_rt.getText().toString())){
                et_rt.setError("RT tidak boleh kosong");
                MethodUtil.showSnackBar(findViewById(android.R.id.content), "RT tidak boleh kosong");
                return;
            }

            if (TextUtils.isEmpty(et_rw.getText().toString())){
                et_rw.setError("RW tidak boleh kosong");
                MethodUtil.showSnackBar(findViewById(android.R.id.content), "RW tidak boleh kosong");
                return;
            }

            if (TextUtils.isEmpty(et_postal_code.getText().toString())){
                et_postal_code.setError("Kode Pos tidak boleh kosong");
                MethodUtil.showSnackBar(findViewById(android.R.id.content), "Kode Pos tidak boleh kosong");
                return;
            }
        }

        setProductDataModel();

        Intent intent = new Intent(SubmitProject5Activity.this, SubmitProject6Activity.class);
        intent.putExtra("json_photos", new Gson().toJson(mArrayUri));
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.frame_save)
    void onClickSave(){
        //onSave
        PreferenceManager.setIsSaveProductData(true);
        setProductDataModel();
        MethodUtil.showSnackBar(findViewById(android.R.id.content), "Data berhasil disimpan");
    }
}