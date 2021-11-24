package com.selada.invesproperti.presentation.portofolio.withdrawal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.response.bankaccount.BankAccountResponse;
import com.selada.invesproperti.presentation.adapter.ListBankAdapter;
import com.selada.invesproperti.presentation.profile.bank.AkunBankActivity;
import com.selada.invesproperti.presentation.profile.cs.CallCenterActivity;
import com.selada.invesproperti.util.Loading;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WithdrawalActivity extends AppCompatActivity {

    @BindView(R.id.et_nominal)
    EditText et_nominal;
    @BindView(R.id.tv_biaya_penarikan)
    TextView tv_biaya_penarikan;
    @BindView(R.id.tv_saldo)
    TextView tv_saldo;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.tv_bank_name)
    TextView tv_bank_name;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_account_no)
    TextView tv_account_no;

    private int withdrawalFee = 25000;
    private int totalWithdrawal = 0;
    private int inqBalance = 0;

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.btn_tarik)
    void onClickTarik(){
        if (inqBalance < totalWithdrawal) {
            MethodUtil.showSnackBar(this, "Saldo anda tidak mencukupi");
            return;
        }

        Intent intent = new Intent(this, WithdrawalConfirmationActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btn_cs)
    void onClickCs(){
        Intent intent = new Intent(this, CallCenterActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        ButterKnife.bind(this);

        getInquiryBalance();
        getFee();
        setCurrency(et_nominal);
        getListBankAccount();
    }

    private void getListBankAccount() {
        Loading.show(WithdrawalActivity.this);
        ApiCore.apiInterface().getBankAccount(PreferenceManager.getSessionToken()).enqueue(new Callback<List<BankAccountResponse>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<BankAccountResponse>> call, Response<List<BankAccountResponse>> response) {
                Loading.hide(WithdrawalActivity.this);
                try {
                    if (response.isSuccessful()){
                        List<BankAccountResponse> responseList = response.body();
                        if (Objects.requireNonNull(responseList).size()>0){
                            tv_bank_name.setText(responseList.get(0).getBank().getName());
                            tv_name.setText(responseList.get(0).getAccountName());
                            tv_account_no.setText(responseList.get(0).getAccountNumber().substring(0, 3) + "*****" + responseList.get(0).getAccountNumber().substring(responseList.get(0).getAccountNumber().length() - 3));
                        } else {
                            //todo
                        }
                    } else {
                        MethodUtil.getErrorMessage(response.errorBody(), WithdrawalActivity.this);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    MethodUtil.showOnCatch(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<List<BankAccountResponse>> call, Throwable t) {
                Loading.hide(WithdrawalActivity.this);
                t.printStackTrace();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void getInquiryBalance() {
        inqBalance = 0;
        tv_saldo.setText("Rp. " + MethodUtil.toCurrencyFormat(String.valueOf(inqBalance)));
    }

    private void getFee() {
        withdrawalFee = 25000;
        tv_biaya_penarikan.setText(MethodUtil.toCurrencyFormat(String.valueOf(withdrawalFee)));
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

                    totalWithdrawal = Integer.parseInt(getNominal()) + withdrawalFee;
                    tv_total.setText(MethodUtil.toCurrencyFormat(String.valueOf(totalWithdrawal)));
                }
            }
        });
    }

    private String getNominal(){
        return et_nominal.getText().toString().replace(".", "");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}