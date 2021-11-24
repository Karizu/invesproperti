package com.selada.invesproperti.presentation.profile.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.request.BankAccountRequest;
import com.selada.invesproperti.model.request.UpdateBankAccountRequest;
import com.selada.invesproperti.model.response.Bank;
import com.selada.invesproperti.model.response.bankaccount.BankAccountResponse;
import com.selada.invesproperti.util.Constant;
import com.selada.invesproperti.util.Loading;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBankActivity extends AppCompatActivity {

    @BindView(R.id.tv_bank)
    TextView tv_bank;
    @BindView(R.id.et_no_rekening)
    EditText et_no_rekening;
    @BindView(R.id.et_pemilik_rekening)
    EditText et_pemilik_rekening;
    @BindView(R.id.spinnerBank)
    Spinner spinnerBank;
    @BindView(R.id.btn_tambah)
    Button btn_tambah;
    @BindView(R.id.tv_title)
    TextView tv_title;

    private String bankName;
    private Context context;
    private String bankSelectedItemId = "";
    private boolean isAddAction = false;
    private BankAccountResponse accountResponse;

    @OnClick(R.id.btn_tambah)
    void onClickTambah(){
        if (bankSelectedItemId.equals("")){
            MethodUtil.showSnackBar(AddBankActivity.this, "Silahkan pilih bank");
            return;
        }

        if (TextUtils.isEmpty(et_no_rekening.getText().toString())){
            MethodUtil.showSnackBar(AddBankActivity.this, "Nomor Rekening harus diisi");
            return;
        }

        if (TextUtils.isEmpty(et_pemilik_rekening.getText().toString())){
            MethodUtil.showSnackBar(AddBankActivity.this, "Nama Pemilik Rekening harus diisi");
            return;
        }

        if (isAddAction){
            createBankAccount();
        } else {
            updateBankAccount();
        }
    }

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.tv_bank)
    void onClickBank(){
        spinnerBank.setVisibility(View.VISIBLE);
        spinnerBank.performClick();
    }

//    @OnClick(R.id.frame_bank)
//    void onClickFrameBank(){
//        Dialog dialog = MethodUtil.getDialogCart(R.layout.dialog_pilih_bank, context);
//        Button btnPilihBank = dialog.findViewById(R.id.btn_tambah);
//        ImageView btnClose = dialog.findViewById(R.id.imgClose);
//        RadioGroup radio_group = dialog.findViewById(R.id.radio_group);
//        RadioButton rb_1 = dialog.findViewById(R.id.rb_1);
//        RadioButton rb_2 = dialog.findViewById(R.id.rb_2);
//        RadioButton rb_3 = dialog.findViewById(R.id.rb_3);
//        RadioButton rb_4 = dialog.findViewById(R.id.rb_4);
//        RadioButton rb_5 = dialog.findViewById(R.id.rb_5);
//
//        btnClose.setOnClickListener(view -> dialog.dismiss());
//        radio_group.setOnCheckedChangeListener((radioGroup, i) -> {
//            switch (radioGroup.getCheckedRadioButtonId()){
//                case R.id.rb_1:
//                    radioGroup.check(R.id.rb_1);
//                    bankName = rb_1.getText().toString();
//                    break;
//                case R.id.rb_2:
//                    radioGroup.check(R.id.rb_2);
//                    bankName = rb_2.getText().toString();
//                    break;
//                case R.id.rb_3:
//                    radioGroup.check(R.id.rb_3);
//                    bankName = rb_3.getText().toString();
//                    break;
//                case R.id.rb_4:
//                    radioGroup.check(R.id.rb_4);
//                    bankName = rb_4.getText().toString();
//                    break;
//                case R.id.rb_5:
//                    radioGroup.check(R.id.rb_5);
//                    bankName = rb_5.getText().toString();
//                    break;
//            }
//        });
//
//        btnPilihBank.setOnClickListener(view -> {
//            tv_bank.setText(bankName);
//            dialog.dismiss();
//        });
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank);
        ButterKnife.bind(this);
        context = this;

        initComponent();
    }

    @SuppressLint("SetTextI18n")
    private void initComponent() {
        getListBank();

        if (getIntent().getIntExtra("flag", Constant.ACTION_ADD) == Constant.ACTION_EDIT){
            isAddAction = false;
            String json = getIntent().getStringExtra("json");
            accountResponse = new Gson().fromJson(json, BankAccountResponse.class);
            String bankName = accountResponse.getBank().getName();
            String accountName = accountResponse.getAccountName();
            String accountNumber = accountResponse.getAccountNumber();

            bankSelectedItemId = accountResponse.getBankId();

            tv_bank.setText(bankName);
            et_pemilik_rekening.setText(accountName);
            et_no_rekening.setText(accountNumber);

            btn_tambah.setText("Ubah data rekening");
            tv_title.setText("Ubah Rekening");
        } else {
            isAddAction = true;
            tv_title.setText("Rekening Baru");
        }
    }

    private void updateBankAccount(){
        Loading.show(AddBankActivity.this);

        UpdateBankAccountRequest updateBankAccountRequest = new UpdateBankAccountRequest();
        updateBankAccountRequest.setId(accountResponse.getId());
        updateBankAccountRequest.setBankId(bankSelectedItemId);
        updateBankAccountRequest.setAccountName(et_pemilik_rekening.getText().toString());
        updateBankAccountRequest.setAccountNumber(et_no_rekening.getText().toString());

        ApiCore.apiInterface().updateBankAccount(updateBankAccountRequest, PreferenceManager.getSessionToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Loading.hide(AddBankActivity.this);
                try {
                    if (response.isSuccessful()){
                        Intent intent = new Intent(AddBankActivity.this, AkunBankActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        AddBankActivity.this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                        Toast.makeText(context, "Akun Bank Anda berhasil diubah", Toast.LENGTH_SHORT).show();
                    } else {
                        MethodUtil.getErrorMessage(response.errorBody(), AddBankActivity.this);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    MethodUtil.showOnCatch(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void createBankAccount(){
        Loading.show(this);

        BankAccountRequest bankAccountRequest = new BankAccountRequest();
        bankAccountRequest.setBankId(bankSelectedItemId);
        bankAccountRequest.setAccountName(et_pemilik_rekening.getText().toString());
        bankAccountRequest.setAccountNumber(et_no_rekening.getText().toString());
        ApiCore.apiInterface().createBankAccount(bankAccountRequest, PreferenceManager.getSessionToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Loading.hide(AddBankActivity.this);
                try {
                    if (response.isSuccessful()){
                        Intent intent = new Intent(AddBankActivity.this, AkunBankActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        AddBankActivity.this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                        Toast.makeText(context, "Akun Bank Anda berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    } else {
                        MethodUtil.getErrorMessage(response.errorBody(), AddBankActivity.this);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    MethodUtil.showOnCatch(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Loading.hide(AddBankActivity.this);
            }
        });
    }

    private void getListBank(){
        Loading.show(this);
        List<String> bankIdList = new ArrayList<>();
        List<String> bankList = new ArrayList<>();
        ApiCore.apiInterface().getListBank(PreferenceManager.getSessionToken()).enqueue(new Callback<List<Bank>>() {
            @Override
            public void onResponse(Call<List<Bank>> call, Response<List<Bank>> response) {
                Loading.hide(AddBankActivity.this);
                try {
                    if (response.isSuccessful()){
                        for (int i = 0; i < response.body().size(); i++){
                            Bank bank = response.body().get(i);
                            bankList.add(bank.getName());
                            bankIdList.add(bank.getId());
                        }

                        ArrayAdapter aa = new ArrayAdapter(AddBankActivity.this, R.layout.custom_simple_spinner_item, bankList);
                        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                        spinnerBank.setAdapter(aa);
                        spinnerBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                bankSelectedItemId = bankIdList.get(i);
                                tv_bank.setText(bankList.get(i));
                                spinnerBank.setVisibility(View.GONE);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                spinnerBank.setVisibility(View.GONE);
                            }
                        });
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    MethodUtil.showOnCatch(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<List<Bank>> call, Throwable t) {
                t.printStackTrace();
                Loading.hide(AddBankActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}