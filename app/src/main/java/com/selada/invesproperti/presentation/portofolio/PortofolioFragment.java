package com.selada.invesproperti.presentation.portofolio;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.selada.invesproperti.IntroSliderActivity;
import com.selada.invesproperti.R;
import com.selada.invesproperti.presentation.adapter.HomeFeedAdapter;
import com.selada.invesproperti.presentation.adapter.PortofolioFeedAdapter;
import com.selada.invesproperti.presentation.home.DetailProductActivity;
import com.selada.invesproperti.presentation.portofolio.deposit.DepositActivity;
import com.selada.invesproperti.presentation.portofolio.history.HistoryActivity;
import com.selada.invesproperti.presentation.portofolio.withdrawal.WithdrawalActivity;
import com.selada.invesproperti.presentation.profile.detail.DetailProfileActivity;
import com.selada.invesproperti.presentation.profile.detail.income.ChangeIncomeYearActivity;
import com.selada.invesproperti.presentation.verification.VerificationActivity;
import com.selada.invesproperti.util.Constant;
import com.selada.invesproperti.util.PreferenceManager;
import com.selada.invesproperti.util.ShareBottomDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PortofolioFragment extends Fragment {

    @BindView(R.id.rv_portofolio)
    RecyclerView rv_portofolio;
    @BindView(R.id.layoutGuest)
    LinearLayout layoutGuest;
    @BindView(R.id.layout_verified)
    NestedScrollView layout_verified;

    @OnClick(R.id.frame_explore_bisnis)
    void onClickExploreBisnis(){
        Intent intent = new Intent(requireActivity(), ExploreBisnisPropertiActivity.class);
        requireActivity().startActivity(intent);
    }

    @OnClick(R.id.btn_verifikasi)
    void onClickBtnVerifikasi(){
        Intent intent = new Intent(requireActivity(), VerificationActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.img_cs)
    void onClickImgCs(){

    }

    @OnClick(R.id.btn_isi_saldo)
    void onClickSaldo(){
        Intent intent = new Intent(requireActivity(), DepositActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.btn_tarik_saldo)
    void onClickTarikSaldo(){
        Intent intent = new Intent(requireActivity(), WithdrawalActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.btn_riwayat)
    void onClickRiwayat(){
        Intent intent = new Intent(requireActivity(), HistoryActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.btnFilterHome)
    void onClickbtnFilterHome(){
        showBottomDialog();
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_portofolio, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        setContentHome();
    }

    private void setContentHome() {
        switch (PreferenceManager.getUserStatus()){
            case Constant.GUEST:
                layoutGuest.setVisibility(View.VISIBLE);
                layout_verified.setVisibility(View.GONE);
                break;
            case Constant.ON_VERIFICATION:
                layout_verified.setVisibility(View.GONE);
                layoutGuest.setVisibility(View.VISIBLE);
                break;
            case Constant.INVESTOR:
                layoutGuest.setVisibility(View.GONE);
                layout_verified.setVisibility(View.VISIBLE);
                break;
            case Constant.PRODUCT_OWNER:

                break;
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_portofolio.setLayoutManager(layoutManager);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            list.add("List "+i);
        }

        PortofolioFeedAdapter adapter = new PortofolioFeedAdapter(list, getContext(), getActivity());
        rv_portofolio.setAdapter(adapter);
        rv_portofolio.scheduleLayoutAnimation();
    }

    private void showBottomDialog() {
        ShareBottomDialog dialog = new ShareBottomDialog();
        dialog.show(getFragmentManager());
    }
}
