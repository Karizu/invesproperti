package com.selada.invesproperti.presentation.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.selada.invesproperti.R;
import com.selada.invesproperti.presentation.portofolio.deposit.DepositActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InquiryPaymentActivity extends AppCompatActivity {
    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.cbSyarat)
    CheckBox cbSyarat;
    @BindView(R.id.btn_bayar)
    Button btn_bayar;

    @OnClick(R.id.btn_bayar)
    void onClickBtnBayar(){
        Intent intent = new Intent(InquiryPaymentActivity.this, PaymentCompleteActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @OnClick(R.id.btn_isi_saldo)
    void onClickSaldo(){
        Intent intent = new Intent(this, DepositActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.pull_up_from_bottom, R.anim.push_out_to_bottom);
    }

    @OnClick(R.id.btn_back)
    void onClickBtnBack(){
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_payment);
        ButterKnife.bind(this);

        initComponent();
    }

    @SuppressLint("SetTextI18n")
    private void initComponent() {
        tv_title_bar.setText("Konfirmasi Pembelian");

        //jika saldo kurang
        text1.setText("Saldo anda tidak cukup. Silahkan lakukan pengisian.");
        text1.setTextColor(getResources().getColor(R.color.red_alert_text));
        cbSyarat.setEnabled(false);

        //jika saldo cukup
        String amount = "13.500.000";
        String styledText = "Saldo anda akan berkurang Rp. <font color='#000000;'><b>"+ amount +"</b></font> untuk pembelian ini.";
        text1.setTextColor(getResources().getColor(R.color.black_primary));
        text1.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
        cbSyarat.setEnabled(true);

        cbSyarat.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b){
                btn_bayar.setEnabled(true);
                btn_bayar.setBackground(getResources().getDrawable(R.drawable.bg_round_green));
            } else {
                btn_bayar.setEnabled(false);
                btn_bayar.setBackground(getResources().getDrawable(R.drawable.bg_round_disable));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}