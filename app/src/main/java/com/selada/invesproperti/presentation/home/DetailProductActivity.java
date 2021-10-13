package com.selada.invesproperti.presentation.home;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madapps.liquid.LiquidRefreshLayout;
import com.selada.invesproperti.R;
import com.selada.invesproperti.model.SliderItem;
import com.selada.invesproperti.presentation.adapter.SliderAdapterExample;
import com.selada.invesproperti.presentation.payment.InputPaymentActivity;
import com.selada.invesproperti.presentation.verification.VerificationActivity;
import com.selada.invesproperti.presentation.verification.VerificationRedirectActivity;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailProductActivity extends AppCompatActivity {

    @BindView(R.id.imageSlider)
    SliderView imageSlider;
    @BindView(R.id.layoutDots)
    LinearLayout dotsLayout;
    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;
    @BindView(R.id.tv_count_day)
    TextView tvCountDay;
    @BindView(R.id.refreshLayout)
    LiquidRefreshLayout refreshLayout;

    private List<SliderItem> mSliderItems;

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.btn_beli)
    void onClickBtnBeli(){
        // status user belum verifikasi
//        Intent intent = new Intent(this, VerificationRedirectActivity.class);
//        startActivity(intent);

        //status user verified
        Intent intent1 = new Intent(this, InputPaymentActivity.class);
        startActivity(intent1);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
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
        tv_title_bar.setText("Yellow Carwash Galuh Mas");

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
                refreshLayout.finishRefreshing();
            }

            @Override
            public void refreshing() {

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