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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.madapps.liquid.LiquidRefreshLayout;
import com.selada.invesproperti.R;
import com.selada.invesproperti.model.SliderItem;
import com.selada.invesproperti.presentation.adapter.HomeFeedAdapter;
import com.selada.invesproperti.presentation.adapter.SliderAdapterExample;
import com.selada.invesproperti.presentation.portofolio.deposit.DepositActivity;
import com.selada.invesproperti.presentation.portofolio.withdrawal.WithdrawalActivity;
import com.selada.invesproperti.presentation.profile.bantuan.BantuanActivity;
import com.selada.invesproperti.presentation.questioner.QuestionerActivity;
import com.selada.invesproperti.presentation.verification.VerificationActivity;
import com.selada.invesproperti.util.Constant;
import com.selada.invesproperti.util.Loading;
import com.selada.invesproperti.util.PreferenceManager;
import com.selada.invesproperti.util.ShareBottomDialog;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        Intent intent = new Intent(requireActivity(), BantuanActivity.class);
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
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        new PreferenceManager(requireActivity());
        setContentHome();
    }

    @SuppressLint("SetTextI18n")
    private void setContentHome() {
        tv_fullname.setText(PreferenceManager.getFullname());
        refreshLayout.setOnRefreshListener(new LiquidRefreshLayout.OnRefreshListener() {
            @Override
            public void completeRefresh() {
                setContentHome();
            }

            @Override
            public void refreshing() {
                if (PreferenceManager.getUserStatus().equals(Constant.ON_VERIFICATION)){
                    PreferenceManager.setUserStatus(Constant.INVESTOR);
                    refreshLayout.finishRefreshing();
                }
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvHome.setLayoutManager(layoutManager);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            list.add("List "+i);
        }

        HomeFeedAdapter adapter = new HomeFeedAdapter(list, getContext(), getActivity());
        rvHome.setAdapter(adapter);
        rvHome.scheduleLayoutAnimation();

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
            e.printStackTrace();
        }
    }
}
