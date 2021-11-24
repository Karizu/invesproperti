package com.selada.invesproperti.presentation.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;
import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.request.PaymentRequest;
import com.selada.invesproperti.model.response.detailproject.ResponseDetailProject;
import com.selada.invesproperti.presentation.portofolio.deposit.DepositActivity;
import com.selada.invesproperti.util.Loading;
import com.selada.invesproperti.util.LoadingPost;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquiryPaymentActivity extends AppCompatActivity {
    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.cbSyarat)
    CheckBox cbSyarat;
    @BindView(R.id.btn_bayar)
    Button btn_bayar;

    @BindView(R.id.tv_properti_name)
    TextView tvPropertiName;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.rp_1_350_000_lot)
    TextView tvPricePerLot;
    @BindView(R.id._7000_lot)
    TextView tvTotalLot;
    @BindView(R.id.rp_1_350_000_lot2)
    TextView tvProvideDividen;
    @BindView(R.id.rp_1_350_000_lot8)
    TextView tvEstimateDividen;

    @BindView(R.id.tv_jumlah_lot)
    TextView tvInqTotalLot;
    @BindView(R.id.tv_harga_per_lot)
    TextView tvInqPricePerLot;
    @BindView(R.id.tv_total_pembelian)
    TextView tvInqTotalAmount;

    @BindView(R.id.tv_ketentuan_investasi)
    TextView tvKetentuanInvestasi;
    @BindView(R.id.tv_fullname)
    TextView tvFullname;

    @BindView(R.id.tv_balance)
    TextView tvBalance;

    private String inqTotalAmount;
    private PaymentRequest paymentRequest;

    @OnClick(R.id.btn_bayar)
    void onClickBtnBayar(){
        createPayment();
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
        checkBalanceInquiry();
        getKetentuanInvestasi();
    }

    @SuppressLint("SetTextI18n")
    private void initComponent() {
        paymentRequest = new PaymentRequest();

        String json = getIntent().getStringExtra("json");
        String inqTotalLot = getIntent().getStringExtra("inq_total_lot");
        inqTotalAmount = getIntent().getStringExtra("inq_total_amount");

        ResponseDetailProject detailProject = new Gson().fromJson(json, ResponseDetailProject.class);

        String companyName = detailProject.getCompanyName();
        String projectName = detailProject.getName();
        int pricePerLot = detailProject.getPricePerLot();
        int lotAvailable = detailProject.getTotalLot();
        String provideDividen = detailProject.getDividenPeriod();
        String estimateDividen = detailProject.getInterestRate();

        paymentRequest.setProjectId(detailProject.getId());
        paymentRequest.setLot(Integer.parseInt(Objects.requireNonNull(inqTotalLot)));
        paymentRequest.setPricePerLot(pricePerLot);
        paymentRequest.setTotal(Integer.parseInt(inqTotalAmount));

        tvFullname.setText(PreferenceManager.getFullname());
        tv_title_bar.setText("Konfirmasi Pembelian");
        tvPropertiName.setText(projectName);
        tvCompanyName.setText(companyName);
        tvPricePerLot.setText("Rp. " + MethodUtil.toCurrencyFormat(String.valueOf(pricePerLot)) + "/lot");
        tvProvideDividen.setText(provideDividen);
        tvTotalLot.setText(lotAvailable + " lot");
        tvEstimateDividen.setText(estimateDividen);

        tvInqTotalLot.setText(inqTotalLot + " Lot");
        tvInqPricePerLot.setText(MethodUtil.toCurrencyFormat(String.valueOf(pricePerLot)));
        tvInqTotalAmount.setText(MethodUtil.toCurrencyFormat(inqTotalAmount));
    }

    @SuppressLint("SetTextI18n")
    private void checkBalanceInquiry(){
        tvBalance.setText("Rp. 0");
        int balanceInquiry = 0;

        //call balance inq here

//        if (balanceInquiry >= Integer.parseInt(inqTotalAmount)){
//            String styledText = "Saldo anda akan berkurang Rp. <font color='#000000;'><b>"+ inqTotalAmount +"</b></font> untuk pembelian ini.";
//            text1.setTextColor(getResources().getColor(R.color.black_primary));
//            text1.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);
//            cbSyarat.setEnabled(true);
//        } else {
//            text1.setText("Saldo anda tidak cukup. Silahkan lakukan pengisian.");
//            text1.setTextColor(getResources().getColor(R.color.red_alert_text));
//            cbSyarat.setEnabled(false);
//        }

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

    private void getKetentuanInvestasi(){
//        tvKetentuanInvestasi.setText("");
    }

    private void createPayment(){
        LoadingPost.show(InquiryPaymentActivity.this);
        ApiCore.apiInterface().createPayment(paymentRequest, PreferenceManager.getSessionToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                LoadingPost.hide(InquiryPaymentActivity.this);
                try {
                    if (response.isSuccessful()){
                        Intent intent = new Intent(InquiryPaymentActivity.this, PaymentCompleteActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        MethodUtil.getErrorMessage(response.errorBody(), InquiryPaymentActivity.this);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    MethodUtil.showOnCatch(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                LoadingPost.hide(InquiryPaymentActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}