package com.selada.invesproperti.presentation.portofolio;

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
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.madapps.liquid.LiquidRefreshLayout;
import com.selada.invesproperti.R;
import com.selada.invesproperti.presentation.adapter.PortofolioFeedAdapter;
import com.selada.invesproperti.presentation.adapter.PortofolioFeedPOAdapter;
import com.selada.invesproperti.presentation.portofolio.deposit.DepositActivity;
import com.selada.invesproperti.presentation.portofolio.history.HistoryActivity;
import com.selada.invesproperti.presentation.portofolio.withdrawal.WithdrawalActivity;
import com.selada.invesproperti.presentation.profile.cs.CallCenterActivity;
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
    @BindView(R.id.cv_aset_po)
    CardView cv_aset_po;
    @BindView(R.id.cv_aset)
    CardView cv_aset;
    @BindView(R.id.btn_isi_saldo)
    FrameLayout btn_isi_saldo;
    @BindView(R.id.layout_no_investasi)
    LinearLayout layout_no_investasi;
    @BindView(R.id.layout_no_produk)
    LinearLayout layout_no_produk;
    @BindView(R.id.refreshLayout)
    LiquidRefreshLayout refreshLayout;
    @BindView(R.id.text_btn)
    TextView text_btn;

    @OnClick(R.id.btn_verifikasi)
    void onClickBtnVerifikasi(){
        Intent intent = new Intent(requireActivity(), VerificationActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_cs)
    void onClickCs(){
        Intent intent = new Intent(requireActivity(), CallCenterActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btn_isi_saldo)
    void onClickSaldo(){
        Intent intent = new Intent(requireActivity(), DepositActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.pull_up_from_bottom, R.anim.push_out_to_bottom);
    }

    @OnClick(R.id.btn_tarik_saldo)
    void onClickTarikSaldo(){
        Intent intent = new Intent(requireActivity(), WithdrawalActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btn_riwayat)
    void onClickRiwayat(){
        Intent intent = new Intent(requireActivity(), HistoryActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btn_mulai_investasi)
    void onClickMulaiInvestasi(){
        Intent intent = new Intent(requireActivity(), ExploreBisnisPropertiActivity.class);
        requireActivity().startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btn_buat_produk)
    void onClickBuatProduk(){

    }

    @OnClick(R.id.btn_add)
    void onClickAdd(){
        if (PreferenceManager.getUserStatus().equals(Constant.INVESTOR)){
            Intent intent = new Intent(requireActivity(), ExploreBisnisPropertiActivity.class);
            startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        } else {

        }
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

    @SuppressLint("SetTextI18n")
    private void setContentHome() {
        refreshLayout.setOnRefreshListener(new LiquidRefreshLayout.OnRefreshListener() {
            @Override
            public void completeRefresh() {
                refreshLayout.finishRefreshing();
            }

            @Override
            public void refreshing() {

            }
        });

        switch (PreferenceManager.getUserStatus()){
            case Constant.GUEST:
                layoutGuest.setVisibility(View.VISIBLE);
                layout_verified.setVisibility(View.GONE);
                break;
            case Constant.ON_VERIFICATION:
                layout_verified.setVisibility(View.GONE);
                layoutGuest.setVisibility(View.VISIBLE);
                cv_aset.setVisibility(View.VISIBLE);
                cv_aset_po.setVisibility(View.GONE);
                btn_isi_saldo.setVisibility(View.VISIBLE);
                break;
            case Constant.INVESTOR:
                getPortofolioInvestor();
                layoutGuest.setVisibility(View.GONE);
                layout_verified.setVisibility(View.VISIBLE);
                cv_aset.setVisibility(View.VISIBLE);
                cv_aset_po.setVisibility(View.GONE);
                btn_isi_saldo.setVisibility(View.VISIBLE);
                text_btn.setText("Tambah Investasi Lain");
                break;
            case Constant.PRODUCT_OWNER:
                getPortofolioPO();
                layoutGuest.setVisibility(View.GONE);
                layout_verified.setVisibility(View.VISIBLE);
                cv_aset.setVisibility(View.GONE);
                cv_aset_po.setVisibility(View.VISIBLE);
                btn_isi_saldo.setVisibility(View.INVISIBLE);
                text_btn.setText("Tambah Produk");
                break;
        }
    }

    private void getPortofolioInvestor(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            list.add("List "+i);
        }

        //if list null
        if (list.size() == 0){
            rv_portofolio.setVisibility(View.GONE);
            if (PreferenceManager.getUserStatus().equals(Constant.PRODUCT_OWNER)){
                layout_no_produk.setVisibility(View.VISIBLE);
            } else {
                layout_no_investasi.setVisibility(View.VISIBLE);
            }
        }

        rv_portofolio.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        PortofolioFeedAdapter adapter = new PortofolioFeedAdapter(list, getContext(), getActivity());
        rv_portofolio.setAdapter(adapter);
        rv_portofolio.scheduleLayoutAnimation();
    }

    private void getPortofolioPO(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            list.add("List "+i);
        }

        //if list null
        if (list.size() == 0){
            rv_portofolio.setVisibility(View.GONE);
            if (PreferenceManager.getUserStatus().equals(Constant.PRODUCT_OWNER)){
                layout_no_produk.setVisibility(View.VISIBLE);
            } else {
                layout_no_investasi.setVisibility(View.VISIBLE);
            }
        }

        rv_portofolio.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        PortofolioFeedPOAdapter adapter = new PortofolioFeedPOAdapter(list, getContext(), getActivity());
        rv_portofolio.setAdapter(adapter);
        rv_portofolio.scheduleLayoutAnimation();
    }

    private void showBottomDialog() {
        ShareBottomDialog dialog = new ShareBottomDialog();
        dialog.show(getFragmentManager());
    }
}
