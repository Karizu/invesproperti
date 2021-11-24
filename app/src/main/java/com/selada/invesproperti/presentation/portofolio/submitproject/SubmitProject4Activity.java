package com.selada.invesproperti.presentation.portofolio.submitproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.PhotoUriModel;
import com.selada.invesproperti.model.request.SubmitProductRequest;
import com.selada.invesproperti.presentation.portofolio.submitproject.adapter.ImageListAdapter;
import com.selada.invesproperti.util.Loading;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.selada.invesproperti.presentation.profile.ProfileFragment.getRealPathFromURI;

public class SubmitProject4Activity extends AppCompatActivity {

    @BindView(R.id.et_company_name)
    EditText et_company_name;
    @BindView(R.id.et_pic_position)
    EditText et_pic_position;
    @BindView(R.id.et_akta_number)
    EditText et_akta_number;
    @BindView(R.id.et_tax_card_number)
    EditText et_tax_card_number;
    @BindView(R.id.et_ahu_number)
    EditText et_ahu_number;
    @BindView(R.id.tv_procuration)
    TextView tv_procuration;
    @BindView(R.id.tv_akta_file)
    TextView tv_akta_file;
    @BindView(R.id.tv_tax_card_file)
    TextView tv_tax_card_file;
    @BindView(R.id.tv_ahu_file)
    TextView tv_ahu_file;
    @BindView(R.id.spinner_company_type)
    Spinner spinner_company_type;

    private static final int PROCURATION = 1;
    private static final int AKTA_FILE = 2;
    private static final int TAX_CARD_FILE = 3;
    private static final int AHU_FILE = 4;
    private SubmitProductRequest submitProductRequest;
    private String companyTypeSelectedItem;
    private PhotoUriModel mArrayUri;
    private String procurmentFilePath = "";
    private String aktaFilePath = "";
    private String taxCardFilePath = "";
    private String ahuFilePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_project4);
        ButterKnife.bind(this);
        new PreferenceManager(this);
        getListCompanyType();
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
            try {
                et_tax_card_number.setText(submitProductRequest.getTaxCardNumber());
                et_ahu_number.setText(submitProductRequest.getaHUNumber());
                et_pic_position.setText(submitProductRequest.getPicPosition());
                et_company_name.setText(submitProductRequest.getCompanyName());
                et_akta_number.setText(submitProductRequest.getAktaNumber());
                tv_ahu_file.setText(MethodUtil.getFileName(SubmitProject4Activity.this, submitProductRequest.getaHUFile()));
                tv_tax_card_file.setText(MethodUtil.getFileName(SubmitProject4Activity.this, submitProductRequest.getTaxCardFile()));
                tv_akta_file.setText(MethodUtil.getFileName(SubmitProject4Activity.this, submitProductRequest.getAktaFile()));
                tv_procuration.setText(MethodUtil.getFileName(SubmitProject4Activity.this, submitProductRequest.getProcuration()));
                spinner_company_type.setSelection(MethodUtil.getIndex(spinner_company_type, submitProductRequest.getCompanyType()));
                procurmentFilePath = submitProductRequest.getProcuration();
                ahuFilePath = submitProductRequest.getaHUFile();
                aktaFilePath = submitProductRequest.getAktaFile();
                taxCardFilePath = submitProductRequest.getTaxCardFile();
            } catch (Exception e){}

        }
    }

    private void getListCompanyType(){
        String[] strings = {"PT", "CV"};
        ArrayAdapter aa = new ArrayAdapter(SubmitProject4Activity.this, R.layout.custom_simple_spinner_item, strings);
        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_company_type.setAdapter(aa);
        spinner_company_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                companyTypeSelectedItem = strings[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void setProductDataModel() {
        submitProductRequest.setCompanyName(et_company_name.getText().toString());
        submitProductRequest.setCompanyType(companyTypeSelectedItem);
        submitProductRequest.setPicPosition(et_pic_position.getText().toString());
        submitProductRequest.setAktaNumber(et_akta_number.getText().toString());
        submitProductRequest.setTaxCardNumber(et_tax_card_number.getText().toString());
        submitProductRequest.setaHUNumber(et_ahu_number.getText().toString());
        submitProductRequest.setProcuration(procurmentFilePath);
        submitProductRequest.setAktaFile(aktaFilePath);
        submitProductRequest.setTaxCardFile(taxCardFilePath);
        submitProductRequest.setaHUFile(ahuFilePath);

        Log.d("SubmitProductRequest", new Gson().toJson(submitProductRequest));
        PreferenceManager.setSubmitProject(submitProductRequest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        submitProductRequest = PreferenceManager.getSubmitProject();
        Log.d("onStart", new Gson().toJson(submitProductRequest));
    }

    @Override
    protected void onResume() {
        super.onResume();
        submitProductRequest = PreferenceManager.getSubmitProject();
        Log.d("onResume", new Gson().toJson(submitProductRequest));
    }

    @OnClick(R.id.frame_upload_procuration)
    void onClickUploadProcuration(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent,"Select File"), PROCURATION);
    }

    @OnClick(R.id.frame_upload_akta_file)
    void onClickUploadAkta(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent,"Select File"), AKTA_FILE);
    }

    @OnClick(R.id.frame_upload_tax_card_file)
    void onClickUploadTax(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent,"Select File"), TAX_CARD_FILE);
    }

    @OnClick(R.id.frame_upload_ahu_file)
    void onClickUploadAhu(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent,"Select File"), AHU_FILE);
    }

    @OnClick(R.id.frame_save)
    void onClickSave(){
        //onSave
        PreferenceManager.setIsSaveProductData(true);
        setProductDataModel();
        MethodUtil.showSnackBar(findViewById(android.R.id.content), "Data berhasil disimpan");
    }

    @OnClick(R.id.btn_lanjut)
    void onClickLanjut(){
        if (TextUtils.isEmpty(et_company_name.getText().toString())){
            et_company_name.setError("Nama Badan Usaha tidak boleh kosong");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Nama Badan Usaha tidak boleh kosong");
            return;
        }

        if (TextUtils.isEmpty(et_pic_position.getText().toString())){
            et_pic_position.setError("Posisi tidak boleh kosong");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Posisi tidak boleh kosong");
            return;
        }

        if (TextUtils.isEmpty(et_akta_number.getText().toString())){
            et_akta_number.setError("Nomor Akta tidak boleh kosong");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Nomor Akta tidak boleh kosong");
            return;
        }

        if (TextUtils.isEmpty(et_ahu_number.getText().toString())){
            et_ahu_number.setError("Nomor AHU tidak boleh kosong");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Nomor AHU tidak boleh kosong");
            return;
        }

        if (TextUtils.isEmpty(et_tax_card_number.getText().toString())){
            et_tax_card_number.setError("Nomor NPWP tidak boleh kosong");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Nomor NPWP tidak boleh kosong");
            return;
        }

        setProductDataModel();

        Intent intent = new Intent(SubmitProject4Activity.this, SubmitProject5Activity.class);
        intent.putExtra("json_photos", new Gson().toJson(mArrayUri));
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resuleCode, Intent intent) {
        try {
            if (requestCode == PROCURATION && resuleCode == RESULT_OK
                    && null != intent) {
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                if(intent.getData()!=null){
                    ArrayList<Uri> mArrayUri = new ArrayList<>();
                    Uri uri = intent.getData();
                    mArrayUri.add(uri);
                    Cursor cursor = getContentResolver().query(uri,
                            filePathColumn, null, null, null);
                    Objects.requireNonNull(cursor).moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    File file = new File(cursor.getString(columnIndex));
                    tv_procuration.setText(file.getName());
                    procurmentFilePath = getRealPathFromURI(SubmitProject4Activity.this, uri);
                    cursor.close();
                }
            } else if (requestCode == AKTA_FILE && resuleCode == RESULT_OK
                    && null != intent) {
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                if(intent.getData()!=null){
                    ArrayList<Uri> mArrayUri = new ArrayList<>();
                    Uri uri = intent.getData();
                    mArrayUri.add(uri);
                    Cursor cursor = getContentResolver().query(uri,
                            filePathColumn, null, null, null);
                    Objects.requireNonNull(cursor).moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    File file = new File(cursor.getString(columnIndex));
                    tv_akta_file.setText(file.getName());
                    aktaFilePath = getRealPathFromURI(SubmitProject4Activity.this, uri);
                    cursor.close();
                }
            } else if (requestCode == TAX_CARD_FILE && resuleCode == RESULT_OK
                    && null != intent) {
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                if(intent.getData()!=null){
                    ArrayList<Uri> mArrayUri = new ArrayList<>();
                    Uri uri = intent.getData();
                    mArrayUri.add(uri);
                    Cursor cursor = getContentResolver().query(uri,
                            filePathColumn, null, null, null);
                    Objects.requireNonNull(cursor).moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    File file = new File(cursor.getString(columnIndex));
                    tv_tax_card_file.setText(file.getName());
                    taxCardFilePath = getRealPathFromURI(SubmitProject4Activity.this, uri);
                    cursor.close();
                }
            } else if (requestCode == AHU_FILE && resuleCode == RESULT_OK
                    && null != intent) {
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                if(intent.getData()!=null){
                    ArrayList<Uri> mArrayUri = new ArrayList<>();
                    Uri uri = intent.getData();
                    mArrayUri.add(uri);
                    Cursor cursor = getContentResolver().query(uri,
                            filePathColumn, null, null, null);
                    Objects.requireNonNull(cursor).moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    File file = new File(cursor.getString(columnIndex));
                    tv_ahu_file.setText(file.getName());
                    ahuFilePath = getRealPathFromURI(SubmitProject4Activity.this, uri);
                    cursor.close();
                }
            } else {
                Toast.makeText(this, "You haven't picked File",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resuleCode, intent);
    }
}