package com.selada.invesproperti.presentation.portofolio.submitproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.request.SubmitProductRequest;
import com.selada.invesproperti.model.response.BusinessType;
import com.selada.invesproperti.model.response.Country;
import com.selada.invesproperti.presentation.portofolio.submitproject.adapter.ImageListAdapter;
import com.selada.invesproperti.presentation.verification.VerificationData1Activity;
import com.selada.invesproperti.util.Loading;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.io.File;
import java.text.NumberFormat;
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

import static com.selada.invesproperti.presentation.profile.ProfileFragment.getRealPathFromURI;

public class SubmitProject2Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    @BindView(R.id.spinner_business_type)
    Spinner  spinner_business_type;
    @BindView(R.id.et_requested_amount)
    EditText et_requested_amount;
    @BindView(R.id.et_total_lot)
    EditText et_total_lot;
    @BindView(R.id.et_price_per_lot)
    EditText et_price_per_lot;
    @BindView(R.id.et_dividen_period)
    EditText et_dividen_period;
    @BindView(R.id.et_min_estimate_dividen_amount)
    EditText et_min_estimate_dividen_amount;
    @BindView(R.id.et_max_estimate_dividen_amount)
    EditText et_max_estimate_dividen_amount;
    @BindView(R.id.spinner_dividen_uom)
    Spinner spinner_dividen_uom;
    @BindView(R.id.et_dividen_amount_period)
    EditText et_dividen_amount_period;
    @BindView(R.id.spinner_dividen_amount_period_uom)
    Spinner spinner_dividen_amount_period_uom;
    @BindView(R.id.spinner_dividen_period_uom)
    Spinner spinner_dividen_period_uom;
    @BindView(R.id.tv_start_date_business)
    TextView tv_start_date_business;
    @BindView(R.id.tv_start_date_estimate)
    TextView tv_start_date_estimate;
    @BindView(R.id.et_income_per_month)
    EditText et_income_per_month;
    @BindView(R.id.tv_file_name)
    TextView tv_file_name;

    private String fileNameProspectus = "";
    private String businessSelectedItem;
    private String devidenPeriodUOMSelectedItem;
    private String devidenUOMSelectedItem;
    private String devidenAmountPeriodUOMSelectedItem;
    private Calendar newCalendar;
    private DatePickerDialog datePickerDialog;
    private boolean isExistingBusiness = false;
    private File file;
    private Uri mImageUri;
    private String businessNameSaved = "";
    private SubmitProductRequest submitProductRequest;
    private String prospectusFilePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_project2);
        ButterKnife.bind(this);
//        new PreferenceManager(this);
        initComponent();
        getListBusinessType();
        getListDevidenPeriodUOM();
        getListDevidenAmountPeriodUOM();
        getListDevidenUOM();
        setCurrency(et_requested_amount);
        setCurrency(et_price_per_lot);
        setCurrency(et_min_estimate_dividen_amount);
        setCurrency(et_max_estimate_dividen_amount);
        setCurrency(et_income_per_month);
        newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(
                this, SubmitProject2Activity.this, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @SuppressLint("SetTextI18n")
    private void initComponent(){
        submitProductRequest = PreferenceManager.getSubmitProject();
        if (PreferenceManager.getIsSaveProductData()){
            et_requested_amount.setText(submitProductRequest.getRequestedAmount());
            et_dividen_period.setText(submitProductRequest.getDividenPeriod());
            et_dividen_amount_period.setText(submitProductRequest.getDividenAmountPeriod());
            et_income_per_month.setText(submitProductRequest.getMonthlyIncome());
            et_max_estimate_dividen_amount.setText(submitProductRequest.getMaxDividenEstimation());
            et_min_estimate_dividen_amount.setText(submitProductRequest.getMinDividenEstimation());
            et_price_per_lot.setText(submitProductRequest.getPricePerLot());
            et_total_lot.setText(submitProductRequest.getTotalLot());
            tv_start_date_estimate.setText(submitProductRequest.getEstablishmentDateEstimation());
            tv_start_date_business.setText(submitProductRequest.getEstablishmentDate());
            spinner_business_type.setSelection(MethodUtil.getIndex(spinner_business_type, businessNameSaved));
            spinner_dividen_period_uom.setSelection(MethodUtil.getIndex(spinner_dividen_period_uom, submitProductRequest.getDividenPeriodUOM()));
            spinner_dividen_uom.setSelection(MethodUtil.getIndex(spinner_dividen_uom, submitProductRequest.getDividenUOM()));
            spinner_dividen_amount_period_uom.setSelection(MethodUtil.getIndex(spinner_dividen_amount_period_uom, submitProductRequest.getDividenAmountPeriodUOM()));
            tv_file_name.setText(MethodUtil.getFileName(SubmitProject2Activity.this, submitProductRequest.getProspectus()));
            fileNameProspectus = MethodUtil.getFileName(SubmitProject2Activity.this, submitProductRequest.getProspectus());
            prospectusFilePath = submitProductRequest.getProspectus();
        }
    }

    private void setProductDataModel() {
        submitProductRequest = PreferenceManager.getSubmitProject();
        submitProductRequest.setBusinessTypeId(businessSelectedItem);
        submitProductRequest.setRequestedAmount(et_requested_amount.getText().toString().replace(".",""));
        submitProductRequest.setTotalLot(et_total_lot.getText().toString());
        submitProductRequest.setPricePerLot(et_price_per_lot.getText().toString().replace(".", ""));
        submitProductRequest.setDividenPeriod(et_dividen_period.getText().toString());
        submitProductRequest.setDividenPeriodUOM(devidenPeriodUOMSelectedItem);
        submitProductRequest.setDividenAmountPeriod(et_dividen_amount_period.getText().toString());
        submitProductRequest.setDividenAmountPeriodUOM(devidenAmountPeriodUOMSelectedItem);
        submitProductRequest.setMonthlyIncome(et_income_per_month.getText().toString().replace(".", ""));
        submitProductRequest.setMonthlyIncomeUOM("IDR");
        submitProductRequest.setMinDividenEstimation(et_min_estimate_dividen_amount.getText().toString().replace(".", ""));
        submitProductRequest.setMaxDividenEstimation(et_max_estimate_dividen_amount.getText().toString().replace(".", ""));
        submitProductRequest.setDividenUOM(devidenUOMSelectedItem);
        submitProductRequest.setEstablishmentDate(tv_start_date_business.getText().toString());
        submitProductRequest.setEstablishmentDateEstimation(tv_start_date_estimate.getText().toString());
        submitProductRequest.setProspectus(prospectusFilePath);


        PreferenceManager.setSubmitProject(submitProductRequest);
        Log.d("SubmitProductRequest", new Gson().toJson(PreferenceManager.getSubmitProject()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        ButterKnife.bind(this);
        submitProductRequest = PreferenceManager.getSubmitProject();
        Log.d("onStart", new Gson().toJson(submitProductRequest));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ButterKnife.bind(this);
        submitProductRequest = PreferenceManager.getSubmitProject();
        Log.d("onResume", new Gson().toJson(submitProductRequest));
    }

    private void getListBusinessType(){
        Loading.show(SubmitProject2Activity.this);

        List<String> businessIdList = new ArrayList<>();
        List<String> businessList = new ArrayList<>();
        ApiCore.apiInterface().getListBusinessType(PreferenceManager.getSessionToken()).enqueue(new Callback<List<BusinessType>>() {
            @Override
            public void onResponse(Call<List<BusinessType>> call, Response<List<BusinessType>> response) {
                Loading.hide(SubmitProject2Activity.this);
                try {
                    if (response.isSuccessful()) {
                        for (int i = 0; i < Objects.requireNonNull(response.body()).size(); i++){
                            BusinessType businessType = response.body().get(i);
                            businessList.add(businessType.getName());
                            businessIdList.add(businessType.getId());

                            if (PreferenceManager.getIsSaveProductData()) {
                                SubmitProductRequest submitProductRequest = PreferenceManager.getSubmitProject();
                                if (businessType.getId().equals(submitProductRequest.getBusinessTypeId())){
                                    businessNameSaved = businessType.getName();
                                }
                            }
                        }

                        ArrayAdapter aa = new ArrayAdapter(SubmitProject2Activity.this, R.layout.custom_simple_spinner_item, businessList);
                        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                        spinner_business_type.setAdapter(aa);
                        spinner_business_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                businessSelectedItem = businessIdList.get(i);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    } else {
                        MethodUtil.getErrorMessage(response.errorBody(), SubmitProject2Activity.this);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    MethodUtil.showOnCatch(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<List<BusinessType>> call, Throwable t) {
                Loading.hide(SubmitProject2Activity.this);
                t.printStackTrace();
            }
        });
    }

    private void getListDevidenPeriodUOM(){
        String[] labelList = {"Bulan", "Tahun"};
        String[] valueList = {"month", "year"};

        ArrayAdapter aa = new ArrayAdapter(SubmitProject2Activity.this, R.layout.custom_simple_spinner_item, labelList);
        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_dividen_period_uom.setAdapter(aa);
        spinner_dividen_period_uom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                devidenPeriodUOMSelectedItem = valueList[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getListDevidenUOM(){
        String[] labelList = {"%"};

        ArrayAdapter aa = new ArrayAdapter(SubmitProject2Activity.this, R.layout.custom_simple_spinner_item, labelList);
        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_dividen_uom.setAdapter(aa);
        spinner_dividen_uom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                devidenUOMSelectedItem = labelList[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getListDevidenAmountPeriodUOM(){
        String[] labelList = {"Bulan", "Tahun"};
        String[] valueList = {"month", "year"};

        ArrayAdapter aa = new ArrayAdapter(SubmitProject2Activity.this, R.layout.custom_simple_spinner_item, labelList);
        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner_dividen_amount_period_uom.setAdapter(aa);
        spinner_dividen_amount_period_uom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                devidenAmountPeriodUOMSelectedItem = valueList[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setCurrency(final EditText edt) {
        edt.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    edt.removeTextChangedListener(this);

                    Locale local = new Locale("id", "id");
                    String replaceable = String.format("[Rp,.\\s]",
                            NumberFormat.getCurrencyInstance().getCurrency()
                                    .getSymbol(local));
                    String cleanString = s.toString().replaceAll(replaceable,
                            "");

                    double parsed;
                    try {
                        parsed = Double.parseDouble(cleanString);
                    } catch (NumberFormatException e) {
                        parsed = 0.00;
                    }

                    NumberFormat formatter = NumberFormat
                            .getCurrencyInstance(local);
                    formatter.setMaximumFractionDigits(0);
                    formatter.setParseIntegerOnly(true);
                    String formatted = formatter.format((parsed));

                    String replace = String.format("[Rp\\s]",
                            NumberFormat.getCurrencyInstance().getCurrency()
                                    .getSymbol(local));
                    String clean = formatted.replaceAll(replace, "");

                    current = formatted;
                    edt.setText(clean);
                    edt.setSelection(clean.length());
                    edt.addTextChangedListener(this);
                }
            }
        });
    }

    @OnClick(R.id.frame_start_date_business)
    void onClickStartDateBusiness(){
        isExistingBusiness = true;
        datePickerDialog.show();
    }

    @OnClick(R.id.frame_start_date_estimate)
    void onClickStartDateEstimate(){
        isExistingBusiness = false;
        datePickerDialog.show();
    }

    @OnClick(R.id.frame_upload)
    void onClickUpload(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent,"Select File"), 2);
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
        if (TextUtils.isEmpty(et_requested_amount.getText().toString())){
            et_requested_amount.setError("Jumlah Dana Yang Dibutuhkan tidak boleh kosong");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Jumlah Dana Yang Dibutuhkan tidak boleh kosong");
            return;
        }

        if (TextUtils.isEmpty(et_total_lot.getText().toString())){
            et_total_lot.setError("Jumlah Lot tidak boleh kosong");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Jumlah Lot tidak boleh kosong");
            return;
        }

        if (TextUtils.isEmpty(et_price_per_lot.getText().toString())){
            et_price_per_lot.setError("Harga per Lot tidak boleh kosong");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Harga per Lot tidak boleh kosong");
            return;
        }

        if (TextUtils.isEmpty(et_dividen_period.getText().toString())){
            et_dividen_period.setError("Periode Deviden tidak boleh kosong");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Periode Deviden tidak boleh kosong");
            return;
        }

        if (TextUtils.isEmpty(et_min_estimate_dividen_amount.getText().toString())){
            et_min_estimate_dividen_amount.setError("Min Estimasi Nilai Deviden tidak boleh kosong");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Min Estimasi Nilai Deviden tidak boleh kosong");
            return;
        }

        if (TextUtils.isEmpty(et_max_estimate_dividen_amount.getText().toString())){
            et_max_estimate_dividen_amount.setError("Max Estimasi Nilai Deviden tidak boleh kosong");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Max Estimasi Nilai Deviden tidak boleh kosong");
            return;
        }

        if (fileNameProspectus.equals("")){
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Silahkan upload file Prospectus");
            return;
        }

        setProductDataModel();

        Intent intent = new Intent(SubmitProject2Activity.this, SubmitProject3Activity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar newDate = Calendar.getInstance();
        newDate.set(i, i1, i2);
        if (isExistingBusiness){
            tv_start_date_business.setText(dateFormatter.format(newDate.getTime()));
        } else {
            tv_start_date_estimate.setText(dateFormatter.format(newDate.getTime()));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resuleCode, Intent intent) {
        try {
            // When an Image is picked
            if (requestCode == 2 && resuleCode == RESULT_OK
                    && null != intent) {
                // Get the Image from data
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                if(intent.getData()!=null){
                    ArrayList<Uri> mArrayUri = new ArrayList<>();
                    mImageUri = intent.getData();
                    mArrayUri.add(mImageUri);
                    prospectusFilePath = getRealPathFromURI(SubmitProject2Activity.this, mImageUri);

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    Objects.requireNonNull(cursor).moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    file = new File(cursor.getString(columnIndex));
                    fileNameProspectus = file.getName();
                    tv_file_name.setText(fileNameProspectus);
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