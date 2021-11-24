package com.selada.invesproperti.presentation.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.selada.invesproperti.R;
import com.selada.invesproperti.model.response.detailproject.ResponseDetailProject;
import com.selada.invesproperti.presentation.portofolio.deposit.DepositActivity;
import com.selada.invesproperti.util.MethodUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InputPaymentActivity extends AppCompatActivity {

    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;
    @BindView(R.id.frame_min)
    FrameLayout frame_min;
    @BindView(R.id.frame_field)
    FrameLayout frame_field;
    @BindView(R.id.tv_total_pembelian)
    TextView tv_total_pembelian;
    @BindView(R.id.et_jumlah_lot)
    EditText et_jumlah_lot;
    @BindView(R.id.img_min)
    ImageView img_min;
    @BindView(R.id.tv_peringatan)
    TextView tv_peringatan;
    @BindView(R.id.btn_beli)
    Button btn_beli;

    @BindView(R.id.check_text_1)
    CheckedTextView check_text_1;
    @BindView(R.id.check_text_2)
    CheckedTextView check_text_2;
    @BindView(R.id.check_text_3)
    CheckedTextView check_text_3;
    @BindView(R.id.check_text_4)
    CheckedTextView check_text_4;
    @BindView(R.id.check_text_5)
    CheckedTextView check_text_5;
    @BindView(R.id.check_text_6)
    CheckedTextView check_text_6;
    @BindView(R.id.check_text_7)
    CheckedTextView check_text_7;

    @BindView(R.id.rp_1_350_000_lot)
    TextView tvPricePerLot;
    @BindView(R.id.tv_harga_per_lot)
    TextView tvPricePerLotOnPayment;
    @BindView(R.id._7000_lot)
    TextView tvTotalLot;
    @BindView(R.id.rp_1_350_000_lot2)
    TextView tvProvideDividen;
    @BindView(R.id.rp_1_350_000_lot8)
    TextView tvEstimateDividen;

    @BindView(R.id.tv_balance)
    TextView tvBalance;

    private int pricePerLot = 0;
    private int qty = 0;
    private boolean IS_PLUS = true;
    private boolean IS_MIN = false;
    private String json;
    private int total;

    @OnClick(R.id.check_text_1)
    void check_text_1(){
        et_jumlah_lot.setText("4");
        calculateLot(IS_PLUS);
        check_text_1.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot_green));
        check_text_2.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_3.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_4.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_5.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_6.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_7.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        btn_beli.setBackground(getResources().getDrawable(R.drawable.bg_round_green));
    }
    @OnClick(R.id.check_text_2)
    void check_text_2(){
        et_jumlah_lot.setText("9");
        calculateLot(IS_PLUS);
        check_text_1.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_2.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot_green));
        check_text_3.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_4.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_5.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_6.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_7.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        btn_beli.setBackground(getResources().getDrawable(R.drawable.bg_round_green));
    }
    @OnClick(R.id.check_text_3)
    void check_text_3(){
        et_jumlah_lot.setText("14");
        calculateLot(IS_PLUS);
        check_text_1.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_2.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_3.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot_green));
        check_text_4.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_5.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_6.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_7.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        btn_beli.setBackground(getResources().getDrawable(R.drawable.bg_round_green));
    }
    @OnClick(R.id.check_text_4)
    void check_text_4(){
        et_jumlah_lot.setText("24");
        calculateLot(IS_PLUS);
        check_text_1.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_2.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_3.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_4.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot_green));
        check_text_5.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_6.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_7.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        btn_beli.setBackground(getResources().getDrawable(R.drawable.bg_round_green));
    }
    @OnClick(R.id.check_text_5)
    void check_text_5(){
        et_jumlah_lot.setText("49");
        calculateLot(IS_PLUS);
        check_text_1.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_2.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_3.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_4.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_5.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot_green));
        check_text_6.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_7.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        btn_beli.setBackground(getResources().getDrawable(R.drawable.bg_round_green));
    }
    @OnClick(R.id.check_text_6)
    void check_text_6(){
        et_jumlah_lot.setText("99");
        calculateLot(IS_PLUS);
        check_text_1.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_2.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_3.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_4.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_5.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_6.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot_green));
        check_text_7.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        btn_beli.setBackground(getResources().getDrawable(R.drawable.bg_round_green));
    }
    @OnClick(R.id.check_text_7)
    void check_text_7(){
        et_jumlah_lot.setText("149");
        calculateLot(IS_PLUS);
        check_text_1.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_2.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_3.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_4.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_5.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_6.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot));
        check_text_7.setBackground(getResources().getDrawable(R.drawable.bg_round_input_lot_green));
        btn_beli.setBackground(getResources().getDrawable(R.drawable.bg_round_green));
    }

    @OnClick(R.id.btn_back)
    void onClickBtnBack(){
        onBackPressed();
    }

    @OnClick(R.id.frame_plus)
    void onClickFramePlus(){
        calculateLot(IS_PLUS);
    }

    @OnClick(R.id.frame_min)
    void onClickFrameMin(){
        calculateLot(IS_MIN);
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.btn_beli)
    void onClickBtnBeli(){
        if (et_jumlah_lot.getText().toString().equals("0") || et_jumlah_lot.getText().toString().equals("")){
            et_jumlah_lot.setText("Masukkan jumlah lot");
            MethodUtil.showSnackBar(InputPaymentActivity.this, "Masukkan jumlah lot");
            return;
        }

        Intent intent = new Intent(InputPaymentActivity.this, InquiryPaymentActivity.class);
        intent.putExtra("json", json);
        intent.putExtra("inq_total_lot", et_jumlah_lot.getText().toString());
        intent.putExtra("inq_total_amount", String.valueOf(total));
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btn_isi_saldo)
    void onClickSaldo(){
        Intent intent = new Intent(InputPaymentActivity.this, DepositActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.pull_up_from_bottom, R.anim.push_out_to_bottom);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_payment);
        ButterKnife.bind(this);

        initComponent();
    }

    @SuppressLint("SetTextI18n")
    private void initComponent() {
        try {
            checkBalanceInquiry();

            json = getIntent().getStringExtra("json");
            ResponseDetailProject detailProject = new Gson().fromJson(json, ResponseDetailProject.class);
            String projectName = detailProject.getName();
            pricePerLot = detailProject.getPricePerLot();
            int lotAvailable = detailProject.getTotalLot();
            String provideDividen = detailProject.getDividenPeriod();
            String estimateDividen = detailProject.getInterestRate();


            tvPricePerLot.setText("Rp. " + MethodUtil.toCurrencyFormat(String.valueOf(pricePerLot)) + "/lot");
            tvPricePerLotOnPayment.setText(MethodUtil.toCurrencyFormat(String.valueOf(pricePerLot)));
            tvProvideDividen.setText(provideDividen);
            tvTotalLot.setText(lotAvailable + " lot");
            tvEstimateDividen.setText(estimateDividen);
            tv_title_bar.setText(projectName);
            et_jumlah_lot.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().equals("")){
                        tv_total_pembelian.setText("0");
                        et_jumlah_lot.setText("0");
                    } else {
                        total = Integer.parseInt(charSequence.toString()) * pricePerLot;
                        tv_total_pembelian.setText(MethodUtil.toCurrencyFormat(String.valueOf(total)));
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void checkBalanceInquiry(){

    }

    private void setStyleOnClickPlus() {
        frame_min.setEnabled(true);
        frame_min.setBackground(getResources().getDrawable(R.drawable.bg_circle_slide_outside));
        img_min.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_remove_green_24));
        frame_field.setBackground(getResources().getDrawable(R.drawable.bg_round_field_green));
        btn_beli.setEnabled(true);
        btn_beli.setBackground(getResources().getDrawable(R.drawable.bg_round_green));
    }

    private void setStyleOnClickMin() {
        if (qty == 0){
            frame_field.setBackground(getResources().getDrawable(R.drawable.bg_round_field_verification));
            img_min.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_remove_24));
            frame_min.setBackground(getResources().getDrawable(R.drawable.bg_circle_min));
            frame_min.setEnabled(false);
            btn_beli.setEnabled(false);
            btn_beli.setBackground(getResources().getDrawable(R.drawable.bg_round_disable));
        }
    }

    private void calculateLot(boolean isPlus){
        qty = Integer.parseInt(et_jumlah_lot.getText().toString().equals("")?"0":et_jumlah_lot.getText().toString());
        if (isPlus){
            qty++;
            setStyleOnClickPlus();
        } else {
            qty--;
            setStyleOnClickMin();
            if (qty<0) {
                qty = 0;
            }
        }

        int total = qty * pricePerLot;
        et_jumlah_lot.setText(String.valueOf(qty));
        tv_total_pembelian.setText(MethodUtil.toCurrencyFormat(String.valueOf(total)));
    }

    private void setwarningMode(){
        frame_field.setBackground(getResources().getDrawable(R.drawable.bg_round_field_red));
        tv_peringatan.setText(View.VISIBLE);
    }
}