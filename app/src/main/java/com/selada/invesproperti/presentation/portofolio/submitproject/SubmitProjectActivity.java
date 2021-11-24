package com.selada.invesproperti.presentation.portofolio.submitproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ibnux.locationpicker.LocationPickerActivity;
import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.request.SubmitProductRequest;
import com.selada.invesproperti.model.response.City;
import com.selada.invesproperti.model.response.Country;
import com.selada.invesproperti.model.response.Province;
import com.selada.invesproperti.util.Loading;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitProjectActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    @BindView(R.id.et_product_name)
    EditText et_product_name;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.et_detail)
    EditText et_detail;
    @BindView(R.id.tv_lat_long)
    TextView tv_lat_long;
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
    @BindView(R.id.tv_deadline_date)
    TextView tv_deadline_date;

    private double latitude;
    private double longitude;
    private String countrySelectedItem = "";
    private String provinceSelectedItem = "";
    private String citySelectedItem = "";
    private String districtSelectedItem = "";
    private String subDistrictSelectedItem = "";
    private Calendar newCalendar;
    private DatePickerDialog datePickerDialog;
    private String countryNameSaved = "";
    private String cityNameSaved = "";
    private String proviinceNameSaved = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_project);
        ButterKnife.bind(this);
        new PreferenceManager(this);
        initGeocoder();
        initComponent();
        getListCountry();
        newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(
                this, SubmitProjectActivity.this, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @SuppressLint("SetTextI18n")
    private void initComponent(){
        if (PreferenceManager.getIsSaveProductData()){
            SubmitProductRequest submitProductRequest = PreferenceManager.getSubmitProject();
            Log.d("SubmitProductRequest", new Gson().toJson(submitProductRequest));
            et_address.setText(submitProductRequest.getAddressProject());
            et_product_name.setText(submitProductRequest.getName());
            et_postal_code.setText(submitProductRequest.getPostalCodeProject());
            et_code.setText(submitProductRequest.getCode());
            et_detail.setText(submitProductRequest.getDetail());
            et_rt.setText(submitProductRequest.getRtProject());
            et_rw.setText(submitProductRequest.getRwProject());
            String latitude = submitProductRequest.getLatitude()!=null?submitProductRequest.getLatitude():"0";
            String longitude = submitProductRequest.getLongitude()!=null?submitProductRequest.getLongitude():"0";
            tv_lat_long.setText(latitude + ", " + longitude);
            tv_deadline_date.setText(submitProductRequest.getDeadlineDate());
//            spinner_country.setSelection(MethodUtil.getIndex(spinner_country, countryNameSaved));
//            spinner_province.setSelection(MethodUtil.getIndex(spinner_province, proviinceNameSaved));
            spinner_city.setSelection(MethodUtil.getIndex(spinner_city, cityNameSaved));
            spinner_sub_district.setSelection(MethodUtil.getIndex(spinner_sub_district, submitProductRequest.getSubDistrictProject()));
            spinner_district.setSelection(MethodUtil.getIndex(spinner_district, submitProductRequest.getDistrictProject()));
        }
    }

    private void getListCountry(){
        Loading.show(this);

        List<String> countryIdList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        ApiCore.apiInterface().getListCountry(PreferenceManager.getSessionToken()).enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                try {
                    if (response.isSuccessful()){
                        for (int i = 0; i < response.body().size(); i++){
                            Country country = response.body().get(i);
                            countryList.add(country.getName());
                            countryIdList.add(country.getId());

//                            if (PreferenceManager.getIsSaveProductData()) {
//                                SubmitProductRequest submitProductRequest = PreferenceManager.getSubmitProject();
//                                if (country.getId().equals(submitProductRequest.getCountryProject())){
//                                    countryNameSaved = country.getName();
//                                }
//                            }
                        }

                        ArrayAdapter aa = new ArrayAdapter(SubmitProjectActivity.this, R.layout.custom_simple_spinner_item, countryList);
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
                Loading.hide(SubmitProjectActivity.this);
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

//                                if (PreferenceManager.getIsSaveProductData()) {
//                                    SubmitProductRequest submitProductRequest = PreferenceManager.getSubmitProject();
//                                    if (province.getId().equals(submitProductRequest.getProvinceProject())){
//                                        proviinceNameSaved = province.getName();
//                                    }
//                                }
                            }
                        }

                        ArrayAdapter aa = new ArrayAdapter(SubmitProjectActivity.this, R.layout.custom_simple_spinner_item, provinceList);
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
                Loading.hide(SubmitProjectActivity.this);
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
                Loading.hide(SubmitProjectActivity.this);
                try {
                    if (response.isSuccessful()){
                        for (int i = 0; i < response.body().size(); i++){
                            City city = response.body().get(i);
                            if (city.getProvinceId().equals(provinceSelectedItemId)) {
                                cityList.add(city.getName());
                                cityIdList.add(city.getId());

                                if (PreferenceManager.getIsSaveProductData()) {
                                    SubmitProductRequest submitProductRequest = PreferenceManager.getSubmitProject();
                                    if (city.getId().equals(submitProductRequest.getCityIdProject())){
                                        cityNameSaved = city.getName();
                                    }
                                }
                            }
                        }

                        ArrayAdapter aa = new ArrayAdapter(SubmitProjectActivity.this, R.layout.custom_simple_spinner_item, cityList);
                        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                        spinner_city.setAdapter(aa);
                        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                citySelectedItem = cityIdList.get(i);
                                getListDistrict(citySelectedItem);
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
                Loading.hide(SubmitProjectActivity.this);
                t.printStackTrace();
            }
        });
    }

    private void getListDistrict(String citySelectedItemId) {
        String[] district = {"Cibeunying", "Kiaracondong", "Margahayu"};

        ArrayAdapter aa = new ArrayAdapter(SubmitProjectActivity.this, R.layout.custom_simple_spinner_item, district);
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

        ArrayAdapter aa = new ArrayAdapter(SubmitProjectActivity.this, R.layout.custom_simple_spinner_item, subDistrict);
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

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            Log.d("latlong 1", latitude + ", " + longitude);

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @SuppressLint("SetTextI18n")
    private void initGeocoder() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        try {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double longitude = Objects.requireNonNull(location).getLongitude();
            double latitude = location.getLatitude();

            Log.d("latlong 2", latitude + ", " + longitude);
        } catch (Exception e){}

        try {
            Geocoder gCoder = new Geocoder(SubmitProjectActivity.this);
            List<Address> addresses = gCoder.getFromLocation(SubmitProjectActivity.this.latitude, SubmitProjectActivity.this.longitude, 1);
            tv_lat_long.setText(SubmitProjectActivity.this.latitude + ", " + SubmitProjectActivity.this.longitude);
            if (addresses != null && addresses.size() > 0) {
                Log.d("Address", new Gson().toJson(addresses));
                et_address.setText(addresses.get(0).getAddressLine(0));
                et_postal_code.setText(addresses.get(0).getPostalCode());

                String jalan = addresses.get(0).getAddressLine(0);
                String desa = addresses.get(0).getSubLocality();
                String kecamatan = addresses.get(0).getLocality();
                String kabupaten = addresses.get(0).getSubAdminArea();
                String provinsi = addresses.get(0).getAdminArea();
                String negara = addresses.get(0).getCountryName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setProductDataModel() {
        if (PreferenceManager.getIsSaveProductData()) {
            SubmitProductRequest submitProductRequest = PreferenceManager.getSubmitProject();
            submitProductRequest.setName(et_product_name.getText().toString());
            submitProductRequest.setCode(et_code.getText().toString());
            submitProductRequest.setDetail(et_detail.getText().toString());
            submitProductRequest.setRtProject(et_rt.getText().toString());
            submitProductRequest.setRwProject(et_rw.getText().toString());
            submitProductRequest.setLatitude(String.valueOf(latitude));
            submitProductRequest.setLongitude(String.valueOf(longitude));
            submitProductRequest.setAddressProject(et_address.getText().toString());
            submitProductRequest.setPostalCodeProject(et_postal_code.getText().toString());
            submitProductRequest.setCityIdProject(citySelectedItem);
            submitProductRequest.setDistrictProject(districtSelectedItem);
            submitProductRequest.setSubDistrictProject(subDistrictSelectedItem);
            submitProductRequest.setDeadlineDate(tv_deadline_date.getText().toString());
            PreferenceManager.setSubmitProject(submitProductRequest);
            Log.d("SubmitProductRequest", new Gson().toJson(submitProductRequest));
        } else {
            SubmitProductRequest submitProductRequest = new SubmitProductRequest();
            submitProductRequest.setName(et_product_name.getText().toString());
            submitProductRequest.setCode(et_code.getText().toString());
            submitProductRequest.setDetail(et_detail.getText().toString());
            submitProductRequest.setLatitude(String.valueOf(latitude));
            submitProductRequest.setLongitude(String.valueOf(longitude));
            submitProductRequest.setRtProject(et_rt.getText().toString());
            submitProductRequest.setRwProject(et_rw.getText().toString());
            submitProductRequest.setAddressProject(et_address.getText().toString());
            submitProductRequest.setPostalCodeProject(et_postal_code.getText().toString());
            submitProductRequest.setCityIdProject(citySelectedItem);
            submitProductRequest.setDistrictProject(districtSelectedItem);
            submitProductRequest.setSubDistrictProject(subDistrictSelectedItem);
            submitProductRequest.setDeadlineDate(tv_deadline_date.getText().toString());
            PreferenceManager.setSubmitProject(submitProductRequest);
            Log.d("SubmitProductRequest", new Gson().toJson(PreferenceManager.getSubmitProject()));
        }

    }

    private void setInformasi(Intent data) {
        String lat = data.getStringExtra("lat");
        String lon = data.getStringExtra("lon");

        double latitude = Double.parseDouble(lat);
        double longitude = Double.parseDouble(lon);

        Geocoder geocoder = new Geocoder(SubmitProjectActivity.this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                bindView(address, latitude, longitude);
            }
        } catch (IOException e) {
            Log.e("Location Address Loader", "Unable connect to Geocoder", e);
        }
    }

    @SuppressLint("SetTextI18n")
    private void bindView(Address address, double latitude, double longitude) {
        String alamat = address.getAddressLine(0);
        String jalan = address.getThoroughfare();
        String desa = address.getSubLocality();
        String kecamatan = address.getLocality();
        String kabupaten = address.getSubAdminArea();
        String provinsi = address.getAdminArea();
        String negara = address.getCountryName();
        String postalCode = address.getPostalCode();

        this.latitude = latitude;
        this.longitude = longitude;
        tv_lat_long.setText(latitude + ", " + longitude);
        et_address.setText(alamat);
        et_postal_code.setText(postalCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 4268) {
                setInformasi(Objects.requireNonNull(data));
            }
        }
    }

    @OnClick(R.id.frame_deadline_date)
    void onClickDeadlineDate(){
        datePickerDialog.show();
    }

    @OnClick(R.id.frame_location)
    void onClickLocation(){
        Intent locationPickerIntent = new Intent(SubmitProjectActivity.this, LocationPickerActivity.class);
        SubmitProjectActivity.this.startActivityForResult(locationPickerIntent, 4268);
    }

    @OnClick(R.id.btn_lanjut)
    void onClickLanjut(){
        if (TextUtils.isEmpty(et_product_name.getText().toString())){
            et_product_name.setError("Nama usaha tidak boleh kosong");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Nama usaha tidak boleh kosong");
            return;
        }

        if (TextUtils.isEmpty(et_code.getText().toString())){
            et_code.setError("Kode usaha tidak boleh kosong");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Kode usaha tidak boleh kosong");
            return;
        }

        if (TextUtils.isEmpty(et_detail.getText().toString())){
            et_detail.setError("Detail usaha tidak boleh kosong");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Detail usaha tidak boleh kosong");
            return;
        }

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

        setProductDataModel();

        Intent intent = new Intent(SubmitProjectActivity.this, SubmitProject2Activity.class);
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

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar newDate = Calendar.getInstance();
        newDate.set(i, i1, i2);
        tv_deadline_date.setText(dateFormatter.format(newDate.getTime()));
    }
}