package com.selada.invesproperti.presentation.verification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.selada.invesproperti.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerificationData1Activity extends AppCompatActivity {

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
    private String gender;

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
    }

    @OnClick(R.id.btn_back)
    void onClickBtnBack(){
        onBackPressed();
    }

    @OnClick(R.id.btn_lanjut)
    void onClickBtnLanjut(){
        Intent intent = new Intent(this, VerificationData2Activity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_verifikasi_data_pribadi);
        ButterKnife.bind(this);

        initComponent();
    }

    private void initComponent() {
        if (getIntent() != null){
            isInvestor = getIntent().getBooleanExtra("is_investor", false);
            isProjectOwner = getIntent().getBooleanExtra("is_project_owner", false);
            photoKtp = getIntent().getByteArrayExtra("photo_ktp");
            photoSelfie = getIntent().getByteArrayExtra("photo_selfie");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

}