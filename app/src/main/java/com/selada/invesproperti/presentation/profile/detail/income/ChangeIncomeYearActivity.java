package com.selada.invesproperti.presentation.profile.detail.income;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.selada.invesproperti.R;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeIncomeYearActivity extends AppCompatActivity {

    @BindView(R.id.et_income)
    TextView et_income;
    @BindView(R.id.et_new_income)
    EditText et_new_income;

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.btn_simpan)
    void onClickSimpan(){
        if (et_new_income.getText().toString().equals("")){
            et_new_income.setError("Masukkan pendapatan baru anda");
            return;
        }

        if (et_new_income.getText().toString().length() < 6) {
            et_new_income.setError("Masukkan pendapatan baru anda dengan benar");
            return;
        }

        Toast.makeText(this, "Berhasil menyimpan perubahan", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_income_year);
        ButterKnife.bind(this);

        initComponent();
        setCurrency(et_new_income);
    }

    @SuppressLint("SetTextI18n")
    private void initComponent() {
        if (PreferenceManager.getUserProfile()!=null) et_income.setText("Rp. " + MethodUtil.toCurrencyFormat(String.valueOf(PreferenceManager.getUserProfile().getYearlyIncome())));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}