package com.selada.invesproperti.presentation.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.madapps.liquid.LiquidRefreshLayout;
import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.SliderItem;
import com.selada.invesproperti.model.response.ResponseProjects;
import com.selada.invesproperti.model.response.ResponseUserProfile;
import com.selada.invesproperti.presentation.adapter.HomeFeedAdapter;
import com.selada.invesproperti.presentation.adapter.SliderAdapterExample;
import com.selada.invesproperti.presentation.portofolio.deposit.DepositActivity;
import com.selada.invesproperti.presentation.portofolio.withdrawal.WithdrawalActivity;
import com.selada.invesproperti.presentation.profile.cs.CallCenterActivity;
import com.selada.invesproperti.presentation.questioner.QuestionerActivity;
import com.selada.invesproperti.presentation.verification.VerificationActivity;
import com.selada.invesproperti.util.Constant;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;
import com.selada.invesproperti.util.ShareBottomDialog;
import com.skydoves.transformationlayout.TransformationCompat;
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

public class HomeFragment extends Fragment {

    @BindView(R.id.rvHome)
    RecyclerView rvHome;
    @BindView(R.id.imageSlider)
    SliderView imageSlider;
    @BindView(R.id.layoutDots)
    LinearLayout dotsLayout;
    @BindView(R.id.tv_fullname)
    TextView tv_fullname;
    @BindView(R.id.tv_user_status)
    TextView tv_user_status;
    @BindView(R.id.tv_balance)
    TextView tv_balance;
    @BindView(R.id.tv_title_verification_notif)
    TextView tv_title_verification_notif;
    @BindView(R.id.frameVerifikasi)
    FrameLayout frameVerifikasi;
    @BindView(R.id.refreshLayout)
    LiquidRefreshLayout refreshLayout;
    @BindView(R.id.btn_tarik_saldo)
    FrameLayout btn_tarik_saldo;
    @BindView(R.id.btn_isi_saldo)
    FrameLayout btn_isi_saldo;
    @BindView(R.id.shimmerLayout)
    ShimmerFrameLayout shimmerLayout;
    @BindView(R.id.shimmerLayoutArticle)
    ShimmerFrameLayout shimmerLayoutArticle;
    @BindView(R.id.cv_slider)
    CardView cv_slider;

    private List<SliderItem> mSliderItems;
    private ViewGroup viewGroup;

    @OnClick(R.id.btnFilterHome)
    void onClickBtnFilterHome(){
        showBottomDialog();
    }

    @OnClick(R.id.btn_isi_saldo)
    void onClickBtnIsiSaldo(){
        Intent intent = new Intent(requireActivity(), DepositActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.pull_up_from_bottom, R.anim.push_out_to_bottom);
    }

    @OnClick(R.id.btn_tarik_saldo)
    void onClickBtnTarikSaldo(){
        Intent intent = new Intent(requireActivity(), WithdrawalActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btn_cs)
    void onClickCs(){
        Intent intent = new Intent(requireActivity(), CallCenterActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = container;
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TransformationCompat.onTransformationStartContainer(this);
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        new PreferenceManager(requireActivity());
        getListProjects(false);
        getUserProfile(false);
    }

    @SuppressLint("SetTextI18n")
    private void setContentHome() {
        tv_fullname.setText(PreferenceManager.getFullname());
        refreshLayout.setOnRefreshListener(new LiquidRefreshLayout.OnRefreshListener() {
            @Override
            public void completeRefresh() {

            }

            @Override
            public void refreshing() {
                getListProjects(true);
                getUserProfile(true);
            }
        });

        switch (PreferenceManager.getUserStatus()){
            case Constant.GUEST:
                tv_user_status.setText("Guest");
                tv_balance.setText("-");
                tv_title_verification_notif.setText("Verifikasi akun anda sekarang!");
                frameVerifikasi.setVisibility(View.VISIBLE);
                btn_isi_saldo.setVisibility(View.VISIBLE);
                btn_tarik_saldo.setVisibility(View.GONE);

                frameVerifikasi.setOnClickListener(view -> {
                    Intent intent = new Intent(requireActivity(), VerificationActivity.class);
                    startActivity(intent);
                });
                break;
            case Constant.ON_VERIFICATION:
                tv_user_status.setText("Guest");
                tv_balance.setText("-");
                tv_title_verification_notif.setText("Akun sedang dalam proses verifikasi data");
                frameVerifikasi.setVisibility(View.VISIBLE);
                btn_isi_saldo.setVisibility(View.VISIBLE);
                btn_tarik_saldo.setVisibility(View.GONE);

                frameVerifikasi.setOnClickListener(view -> {

                });
                break;
            case Constant.INVESTOR:
                tv_user_status.setText("Investor");
                tv_balance.setText("0");
                if (PreferenceManager.isAlreadyQuesioner()){
                    frameVerifikasi.setVisibility(View.GONE);
                } else {
                    tv_title_verification_notif.setText("Akun anda terverifikasi. Isi quesioner.");
                    frameVerifikasi.setVisibility(View.VISIBLE);
                    frameVerifikasi.setOnClickListener(view -> {
                        Intent intent = new Intent(requireActivity(), QuestionerActivity.class);
                        startActivity(intent);
                    });
                }
                btn_isi_saldo.setVisibility(View.VISIBLE);
                btn_tarik_saldo.setVisibility(View.GONE);
                break;
            case Constant.PRODUCT_OWNER:
                tv_user_status.setText("Product Owner");
                tv_balance.setText("0");
                frameVerifikasi.setVisibility(View.GONE);
                btn_isi_saldo.setVisibility(View.GONE);
                btn_tarik_saldo.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setSliderArticle() {
        mSliderItems = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            SliderItem sliderItem = new SliderItem();
            sliderItem.setDescription("Ide Desain \nRumah Modern Minimalis");
            sliderItem.setImg_url("https://www.bowcockpursaill.co.uk/wp-content/uploads/2016/12/Commercial-property-banner-with-overlay.jpg");
            mSliderItems.add(sliderItem);
        }

        imageSlider.setSliderAdapter(new SliderAdapterExample(getContext(), mSliderItems));
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        try {
            imageSlider.setCurrentPageListener(this::addBottomDots);
        } catch (Exception e){}
        imageSlider.setScrollTimeInSec(4);
        imageSlider.startAutoCycle();

        addBottomDots(0);
    }

    private void showBottomDialog() {
        ShareBottomDialog dialog = new ShareBottomDialog();
        dialog.show(getFragmentManager());
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
                dots[i] = new FrameLayout(requireActivity());
                dots[i].setBackground(getResources().getDrawable(R.drawable.ellipse_4));
                dots[i].setLayoutParams(params);
                dotsLayout.addView(dots[i]);
            }

            if (dots.length > 0) {
                dots[currentPage].setBackground(getResources().getDrawable(R.drawable.rectangle_4));
            }
        } catch (Exception e){
//            e.printStackTrace();
        }
    }

    private void getListProjects(boolean isRefresh){
        shimmerLayout.startShimmer();
        shimmerLayoutArticle.startShimmer();
        ApiCore.apiInterface().getListProjects(PreferenceManager.getSessionToken()).enqueue(new Callback<List<ResponseProjects>>() {
            @Override
            public void onResponse(Call<List<ResponseProjects>> call, Response<List<ResponseProjects>> response) {
                shimmerLayout.stopShimmer();
                shimmerLayoutArticle.stopShimmer();
                shimmerLayout.setVisibility(View.GONE);
                shimmerLayoutArticle.setVisibility(View.GONE);
                cv_slider.setVisibility(View.VISIBLE);
                if (isRefresh) refreshLayout.finishRefreshing();
                try {
                    if (response.isSuccessful()){
                        setSliderArticle();

                        rvHome.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        HomeFeedAdapter adapter = new HomeFeedAdapter(response.body(), getContext(), getActivity());
                        rvHome.setAdapter(adapter);
                        rvHome.scheduleLayoutAnimation();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    MethodUtil.showOnCatch(requireActivity().findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<List<ResponseProjects>> call, Throwable t) {
                if (isRefresh) refreshLayout.finishRefreshing();
                shimmerLayout.setVisibility(View.GONE);
                shimmerLayout.stopShimmer();
                shimmerLayoutArticle.setVisibility(View.GONE);
                shimmerLayoutArticle.stopShimmer();
                cv_slider.setVisibility(View.VISIBLE);
                t.printStackTrace();
            }
        });
    }


    private void getUserProfile(boolean isRefresh){
        ApiCore.apiInterface().getUserProfile(PreferenceManager.getSessionToken()).enqueue(new Callback<ResponseUserProfile>() {
            @Override
            public void onResponse(Call<ResponseUserProfile> call, Response<ResponseUserProfile> response) {
                if (isRefresh) refreshLayout.finishRefreshing();
                try {
                    if (response.isSuccessful()){
                        PreferenceManager.setResponseUserProfile(response.body());
                        switch (Objects.requireNonNull(response.body()).getStatus()){
                            case Constant.GUEST:
                                PreferenceManager.setUserStatus(Constant.GUEST);
                                break;
                            case Constant.ON_VERIFICATION:
                                PreferenceManager.setUserStatus(Constant.ON_VERIFICATION);
                                break;
                            case Constant.VERIFIED:
                                if (response.body().isInvestor()){
                                    PreferenceManager.setUserStatus(Constant.INVESTOR);
                                } else {
                                    PreferenceManager.setUserStatus(Constant.PRODUCT_OWNER);
                                }
                                break;
                        }

                        setContentHome();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseUserProfile> call, Throwable t) {
                if (isRefresh) refreshLayout.finishRefreshing();
                t.printStackTrace();
            }
        });
    }
}
