package com.selada.invesproperti.presentation.portofolio.withdrawal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.selada.invesproperti.R;
import com.selada.invesproperti.presentation.profile.cs.CallCenterActivity;
import com.selada.invesproperti.util.MethodUtil;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WithdrawalActivity extends AppCompatActivity {

    @BindView(R.id.et_nominal)
    EditText et_nominal;
    @BindView(R.id.tv_biaya_penarikan)
    TextView tv_biaya_penarikan;
    @BindView(R.id.tv_saldo)
    TextView tv_saldo;
    @BindView(R.id.tv_total)
    TextView tv_total;

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