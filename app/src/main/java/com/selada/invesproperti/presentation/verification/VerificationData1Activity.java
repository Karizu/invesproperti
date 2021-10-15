package com.selada.invesproperti.presentation.verification;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.UserVerification;
import com.selada.invesproperti.model.response.Education;
import com.selada.invesproperti.model.response.Occupation;
import com.selada.invesproperti.util.Loading;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;
import com.skydoves.powerspinner.IconSpinnerAdapter;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationData1Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.rb_laki)
    RadioButton rb_laki;
    @BindView(R.id.rb_perempuan)
    RadioButton rb_perempuan;
    @BindView(R.id.et_nama_lengkap)
    EditText et_nama_lengkap;
    @BindView(R.id.et_tempat_lahir)
    EditText et_tempat_lahir;
    @BindView(R.id.et_deskripsi_pekerjaan)
    EditText et_deskripsi_pekerjaan;
    @BindView(R.id.et_nama_pasangan)
    EditText et_nama_pasangan;
    @BindView(R.id.spinner_pendidikan)
    Spinner spinner_pendidikan;
    @BindView(R.id.spinner_pekerjaan)
    Spinner spinner_pekerjaan;
    @BindView(R.id.spinner_status)
    Spinner spinner_status;
    @BindView(R.id.tv_tgl_lahir)
    TextView tv_tgl_lahir;

    private boolean isInvestor, isProjectOwner;
    private byte[] photoKtp, photoSelfie;
    private List<String> educationIdList, occupationIdList;
    private String educationSelectedItemId, occupationSelectedItemId, statusSelectedItem;
    private String gender = "";
    private Calendar newCalendar;
    private DatePickerDialog datePickerDialog;
    private Activity appActivity;
    private boolean isUnauthorized = false;

    @OnClick(R.id.rb_laki)
    void onClickRbLaki(){
        gender = "MALE";
        rb_laki.setChecked(true);
        rb_perempuan.setChecked(false);
    }

    @OnClick(R.id.rb_perempuan)
    void onClickRbPerempuan(){
        gender = "FEMALE";
        rb_laki.setChecked(false);
        rb_perempuan.setChecked(true);
    }

    @OnClick(R.id.frame_save)
    void onClickSave(){
        //onSave
        PreferenceManager.setIsSaveVerificationData(true);
        setUserVerificationModel();
        MethodUtil.showSnackBar(findViewById(android.R.id.content), "Data berhasil disimpan");
    }

    @OnClick(R.id.tv_tgl_lahir)
    void onClickTgl(){
        datePickerDialog.show();
    }

    @OnClick(R.id.btn_back)
    void onClickBtnBack(){
        onBackPressed();
    }

    @OnClick(R.id.btn_lanjut)
    void onClickBtnLanjut() {
        //send all data to verif data 2
        if (TextUtils.isEmpty(et_nama_lengkap.getText().toString()) || TextUtils.isEmpty(et_nama_pasangan.getText().toString())
                || TextUtils.isEmpty(et_tempat_lahir.getText().toString()) || TextUtils.isEmpty(gender)
                || TextUtils.isEmpty(tv_tgl_lahir.getText().toString()) || TextUtils.isEmpty(educationSelectedItemId)
                || TextUtils.isEmpty(occupationSelectedItemId) || TextUtils.isEmpty(statusSelectedItem))  {
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Silahkan lengkapi data diri");
            return;
        }

        setUserVerificationModel();

        Intent intent = new Intent(this, VerificationData2Activity.class);
        intent.putExtra("is_investor", isInvestor);
        intent.putExtra("is_project_owner", isProjectOwner);
        intent.putExtra("photo_ktp", photoKtp);
        intent.putExtra("photo_selfie", photoSelfie);
        intent.putExtra("fullname", et_nama_lengkap.getText().toString());
        intent.putExtra("gender", gender);
        intent.putExtra("birth_place", et_tempat_lahir.getText().toString());
        intent.putExtra("birth_date", tv_tgl_lahir.getText().toString());
        intent.putExtra("education_id", educationSelectedItemId);
        intent.putExtra("occupation_id", occupationSelectedItemId);
        intent.putExtra("marital_status", statusSelectedItem);
        intent.putExtra("spouse_name", et_nama_pasangan.getText().toString());
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_verifikasi_data_pribadi);
        ButterKnife.bind(this);
        new PreferenceManager(this);
        initComponent();
    }

    private void initComponent() {
        appActivity = this;
        newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(
                this, VerificationData1Activity.this, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        if (getIntent() != null){
            isInvestor = getIntent().getBooleanExtra("is_investor", false);
            isProjectOwner = getIntent().getBooleanExtra("is_project_owner", false);
            photoKtp = getIntent().getByteArrayExtra("photo_ktp");
            photoSelfie = getIntent().getByteArrayExtra("photo_selfie");
        }

        if (PreferenceManager.getIsSaveVerificationData()){
            UserVerification userVerification = PreferenceManager.getUserVerification();
            et_nama_pasangan.setText(userVerification.getSpouseName());
            et_tempat_lahir.setText(userVerification.getBirthplace());
            et_nama_lengkap.setText(userVerification.getName());
        }

        setSpinnerData();
        checkVerificationDataIsAvailable();
        if (PreferenceManager.getIsUnauthorized()){
            MethodUtil.refreshToken(this);
            initComponent();
        }
    }

    private void checkVerificationDataIsAvailable() {
        if (PreferenceManager.getIsSaveVerificationData()){
            UserVerification userVerification = PreferenceManager.getUserVerification();
            et_nama_lengkap.setText(userVerification.getName());
            et_tempat_lahir.setText(userVerification.getBirthplace());
            tv_tgl_lahir.setText(userVerification.getBirthDate());
            et_nama_pasangan.setText(userVerification.getSpouseName());
            if (userVerification.getGender().equals("MALE")) rb_laki.setChecked(true);
            if (userVerification.getGender().equals("FEMALE")) rb_perempuan.setChecked(true);
        }
    }

    private void setUserVerificationModel() {
        UserVerification userVerification = new UserVerification();
        userVerification.setInvestor(isInvestor);
        userVerification.setProjectOwner(isProjectOwner);
        userVerification.setPhotoKtp(photoKtp);
        userVerification.setPhotoSelfie(photoSelfie);
        userVerification.setName(et_nama_lengkap.getText().toString());
        userVerification.setGender(gender);
        userVerification.setBirthplace(et_tempat_lahir.getText().toString());
        userVerification.setBirthDate(tv_tgl_lahir.getText().toString());
        userVerification.setEducationId(educationSelectedItemId);
        userVerification.setOccupationId(occupationSelectedItemId);
        userVerification.setMaritalStatus(statusSelectedItem);
        userVerification.setSpouseName(et_nama_pasangan.getText().toString());
        PreferenceManager.setUserVerification(userVerification);
    }

    private void setSpinnerData() {
        getListEducation();
        getListOccupation();

        String[] status = {"Pilih status pernikahan", "Menikah", "Belum Menikah"};
        ArrayAdapter aa_3 = new ArrayAdapter(this, R.layout.custom_simple_spinner_item, status){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textview = (TextView) view;
                if (position == 0) {
                    textview.setTextColor(getResources().getColor(R.color.grey_text));
                } else {
                    textview.setTextColor(getResources().getColor(R.color.black_primary));
                }
                return view;
            }
        };
        aa_3.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_status.setAdapter(aa_3);
        spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0){
                    statusSelectedItem = adapterView.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getListEducation() {
        Loading.show(this);

        educationIdList = new ArrayList<>();
        List<String> educationList = new ArrayList<>();
        ApiCore.apiInterface().getListEducation(PreferenceManager.getSessionToken()).enqueue(new Callback<List<Education>>() {
            @Override
            public void onResponse(Call<List<Education>> call, Response<List<Education>> response) {
                Loading.hide(appActivity);
                try {
                    if (response.isSuccessful()){
                        for (int i = 0; i < response.body().size(); i++){
                            Education education = response.body().get(i);
                            educationList.add(education.getName());
                            educationIdList.add(education.getId());
                        }

                        ArrayAdapter aa = new ArrayAdapter(appActivity, R.layout.custom_simple_spinner_item, educationList);
                        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                        spinner_pendidikan.setAdapter(aa);
                        spinner_pendidikan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                educationSelectedItemId = educationIdList.get(i);
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
            public void onFailure(Call<List<Education>> call, Throwable t) {
                Loading.hide(appActivity);
                t.printStackTrace();
            }
        });
    }

    private void getListOccupation(){
        Loading.show(this);

        occupationIdList = new ArrayList<>();
        List<String> occupationList = new ArrayList<>();
        ApiCore.apiInterface().getListOccupation(PreferenceManager.getSessionToken()).enqueue(new Callback<List<Occupation>>() {
            @Override
            public void onResponse(Call<List<Occupation>> call, Response<List<Occupation>> response) {
                Loading.hide(appActivity);
                try {
                    if (response.isSuccessful()){
                        for (int i = 0; i < response.body().size(); i++){
                            Occupation occupation = response.body().get(i);
                            occupationList.add(occupation.getName());
                            occupationIdList.add(occupation.getId());
                        }

                        ArrayAdapter aa = new ArrayAdapter(appActivity, R.layout.custom_simple_spinner_item, occupationList);
                        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                        spinner_pekerjaan.setAdapter(aa);
                        spinner_pekerjaan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                occupationSelectedItemId = occupationIdList.get(i);
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
            public void onFailure(Call<List<Occupation>> call, Throwable t) {
                Loading.hide(appActivity);
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar newDate = Calendar.getInstance();
        newDate.set(i, i1, i2);
        tv_tgl_lahir.setText(dateFormatter.format(newDate.getTime()));
    }
}