package com.selada.invesproperti.presentation.profile.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.response.bankaccount.BankAccountResponse;
import com.selada.invesproperti.util.Constant;
import com.selada.invesproperti.util.Loading;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBankActivity extends AppCompatActivity {

    @BindView(R.id.tv_bank_name)
    TextView tv_bank_name;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_account_no)
    TextView tv_account_no;

    private String json = "";
    private BankAccountResponse accountResponse;

    @OnClick(R.id.btn_ubah)
    void onClickUbah(){
        Intent intent = new Intent(DetailBankActivity.this, AddBankActivity.class);
        intent.putExtra("json", json);
        intent.putExtra("flag", Constant.ACTION_EDIT);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btn_delete)
    void onClickBtnDelete(){
        Dialog dialog = MethodUtil.getDialogCart(R.layout.dialog_delete_account_no, DetailBankActivity.this);
        Button btn_ya = dialog.findViewById(R.id.btn_ya);
        Button btn_tidak = dialog.findViewById(R.id.btn_tidak);

        btn_ya.setOnClickListener(view -> {
            deleteBankAccount();
            dialog.dismiss();
        });
        btn_tidak.setOnClickListener(view -> dialog.dismiss());
    }

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bank);
        ButterKnife.bind(this);

        initComponent();
    }

    private void initComponent() {
        json = getIntent().getStringExtra("json");
        accountResponse = new Gson().fromJson(json, BankAccountResponse.class);
        String bankName = accountResponse.getBank().getName();
        String accountName = accountResponse.getAccountName();
        String accountNumber = accountResponse.getAccountNumber();

        tv_bank_name.setText(bankName);
        tv_name.setText(accountName);
        tv_account_no.setText(accountNumber);
    }

    private void deleteBankAccount(){
        Loading.show(DetailBankActivity.this);
        ApiCore.apiInterface().deleteBankAccount(accountResponse.getId(), PreferenceManager.getSessionToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Loading.hide(DetailBankActivity.this);
                try {
                    if (response.isSuccessful()){
                        Intent intent = new Intent(DetailBankActivity.this, AkunBankActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        DetailBankActivity.this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                        Toast.makeText(DetailBankActivity.this, "Akun Bank berhasil dihapus", Toast.LENGTH_SHORT).show();
                    } else {
                        MethodUtil.getErrorMessage(response.errorBody(), DetailBankActivity.this);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    MethodUtil.showOnCatch(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Loading.hide(DetailBankActivity.this);
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}