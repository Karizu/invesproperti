package com.selada.invesproperti.presentation.home;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.madapps.liquid.LiquidRefreshLayout;
import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.SliderItem;
import com.selada.invesproperti.model.response.detailproject.ResponseDetailProject;
import com.selada.invesproperti.presentation.adapter.SliderAdapterExample;
import com.selada.invesproperti.presentation.payment.InputPaymentActivity;
import com.selada.invesproperti.presentation.verification.VerificationActivity;
import com.selada.invesproperti.presentation.verification.VerificationRedirectActivity;
import com.selada.invesproperti.util.Constant;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;
import com.skydoves.transformationlayout.TransformationAppCompatActivity;
import com.skydoves.transformationlayout.TransformationCompat;
import com.skydoves.transformationlayout.TransformationLayout;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends TransformationAppCompatActivity {

    @BindView(R.id.imageSlider)
    SliderView imageSlider;
    @BindView(R.id.layoutDots)
    LinearLayout dotsLayout;
    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;
    @BindView(R.id.tv_count_day)
    TextView tvCountDay;
    @BindView(R.id.tv_start_price)
    TextView tv_start_price;
    @BindView(R.id.tv_end_price)
    TextView tv_end_price;
    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @BindView(R.id.rp_1_350_000_lot)
    TextView tv_price_per_lot;
    @BindView(R.id._7000_lot)
    TextView tv_total_lot;
    @BindView(R.id.rp_1_350_000_lot5)
    TextView tv_period_deviden;
    @BindView(R.id.rp_1_350_000_lot4)
    TextView tv_estimasi_deviden;
    @BindView(R.id.textView)
    TextView tv_desc_usaha;
    @BindView(R.id.textView2)
    TextView tv_address;
    @BindView(R.id.lottie_anim)
    LottieAnimationView loading;
    @BindView(R.id.refreshLayout)
    LiquidRefreshLayout refreshLayout;

    private List<SliderItem> mSliderItems;
    private String id;

    @OnClick(R.id.frame_open_maps)
    void onClickOpenMaps(){

    }

    @OnClick(R.id.frame_unduh)
    void onClickUnduhProspektus(){

    }

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.btn_beli)
    void onClickBtnBeli(){
        // status user belum verifikasi
        if (PreferenceManager.getUserStatus().equals(Constant.INVESTOR)){
            Intent intent1 = new Intent(this, InputPaymentActivity.class);
            startActivity(intent1);
            this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        } else if (PreferenceManager.getUserStatus().equals(Constant.PRODUCT_OWNER)) {

        } else {
            Intent intent = new Intent(this, VerificationRedirectActivity.class);
            startActivity(intent);
            this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        ButterKnife.bind(this);

        initComponent();
    }

    @SuppressLint("SetTextI18n")
    private void initComponent() {
        if (getIntent()!=null){
            id = getIntent().getStringExtra("id");
            getDetailProject(id, false);
            String name = getIntent().getStringExtra("name");
            tv_title_bar.setText(name);
        }

        String day = "44";
        String styledText = "<font color='#428828;'>"+ day +"</font> hari lagi";
        tvCountDay.setText(Html.fromHtml(styledText), TextView.BufferType.SPANNABLE);

        mSliderItems = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            SliderItem sliderItem = new SliderItem();
            sliderItem.setDescription("");
            sliderItem.setImg_url("https://archinect.imgix.net/uploads/p9/p9m1npbrua0zyn97.jpg?fit=crop&auto=compress%2Cformat&w=1200");
            mSliderItems.add(sliderItem);
        }

        imageSlider.setSliderAdapter(new SliderAdapterExample(this, mSliderItems));
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        try {
            imageSlider.setCurrentPageListener(this::addBottomDots);
        } catch (Exception e){}
        imageSlider.setScrollTimeInSec(4);
        imageSlider.startAutoCycle();

        addBottomDots(0);

        refreshLayout.setOnRefreshListener(new LiquidRefreshLayout.OnRefreshListener() {
            @Override
            public void completeRefresh() {

            }

            @Override
            public void refreshing() {
                getDetailProject(id, true);
            }
        });
    }

    private void getDetailProject(String id, boolean isRefresh){
        loading.setVisibility(View.VISIBLE);
        ApiCore.apiInterface().getDetailProject(id, PreferenceManager.getSessionToken()).enqueue(new Callback<ResponseDetailProject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResponseDetailProject> call, Response<ResponseDetailProject> response) {
                loading.setVisibility(View.GONE);
                if (isRefresh) refreshLayout.finishRefreshing();
                try {
                    if (response.isSuccessful()){
                        ResponseDetailProject detailProject = response.body();
                        String street = Objects.requireNonNull(detailProject).getAddress().getStreet();
                        String subDistrict = detailProject.getAddress().getSubDistrict();
                        String district = detailProject.getAddress().getDistrict();
                        String city = detailProject.getAddress().getCity()!=null? ", " + detailProject.getAddress().getCity().getName():"";
                        String province = detailProject.getAddress().getCity()!=null? ", " + detailProject.getAddress().getProvince().getName():"";

                        tv_start_price.setText("Rp " + MethodUtil.toCurrencyFormat(String.valueOf(Objects.requireNonNull(detailProject).getFundingAmount())));
                        tv_end_price.setText("Rp " + MethodUtil.toCurrencyFormat(String.valueOf(detailProject.getRequestedAmount())));
                        tv_company_name.setText(detailProject.getCompanyName()!=null?detailProject.getCompanyName():"-");
                        tv_price_per_lot.setText("Rp " + MethodUtil.toCurrencyFormat(String.valueOf(detailProject.getPricePerLot())) + "/lot");
                        tv_total_lot.setText(detailProject.getTotalLot() + " lot");
                        tv_period_deviden.setText(detailProject.getDividenPeriod());
                        tv_estimasi_deviden.setText(detailProject.getInterestRate());
                        tv_desc_usaha.setText(detailProject.getDetail());
                        tv_address.setText(street + ", " + subDistrict + ", " + district +  city + province);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseDetailProject> call, Throwable t) {
                if (isRefresh) refreshLayout.finishRefreshing();
                loading.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });
    }

    private void addBottomDots(int currentPage) {
        FrameLayout[] dots = new FrameLayout[mSliderItems.size()];
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(6, 0, 0, 0);

        dotsLayout.removeAllViews();
        try {
            for (int i = 0; i < dots.length; i++) {
                dots[i] = new FrameLayout(this);
                dots[i].setBackground(getResources().getDrawable(R.drawable.ellipse_4));
                dots[i].setLayoutParams(params);
                dotsLayout.addView(dots[i]);
            }

            if (dots.length > 0) {
                dots[currentPage].setBackground(getResources().getDrawable(R.drawable.rectangle_4));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}