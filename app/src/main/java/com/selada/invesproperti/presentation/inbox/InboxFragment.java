package com.selada.invesproperti.presentation.inbox;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.selada.invesproperti.R;
import com.selada.invesproperti.presentation.verification.VerificationActivity;
import com.selada.invesproperti.util.Constant;
import com.selada.invesproperti.util.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InboxFragment extends Fragment {

    @BindView(R.id.layoutGuest)
    LinearLayout layoutGuest;
    @BindView(R.id.layoutInvestor)
    LinearLayout layoutInvestor;

    @OnClick(R.id.btn_verifikasi)
    void onClickBtnVerifikasi(){
        Intent intent = new Intent(requireActivity(), VerificationActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.img_cs)
    void onClickImgCs(){

    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inbox, null);
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
                layoutInvestor.setVisibility(View.GONE);
                break;
            case Constant.ON_VERIFICATION:
                layoutInvestor.setVisibility(View.GONE);
                layoutGuest.setVisibility(View.VISIBLE);
                break;
            case Constant.INVESTOR:
                layoutGuest.setVisibility(View.GONE);
                layoutInvestor.setVisibility(View.VISIBLE);
                break;
            case Constant.PRODUCT_OWNER:

                break;
        }
    }
}
