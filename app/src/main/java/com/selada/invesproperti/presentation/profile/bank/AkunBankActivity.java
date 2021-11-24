package com.selada.invesproperti.presentation.profile.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.selada.invesproperti.IntroSliderActivity;
import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.response.bankaccount.BankAccountResponse;
import com.selada.invesproperti.presentation.adapter.ListBankAdapter;
import com.selada.invesproperti.util.Loading;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AkunBankActivity extends AppCompatActivity {

    @BindView(R.id.rv_akun_bank)
    RecyclerView rv_akun_bank;
    @BindView(R.id.layout_empty_bank)
    RelativeLayout layout_empty_bank;

    private ListBankAdapter adapter;
    private Context context;

    @OnClick(R.id.frame_tambah_rekening)
    void onClickTambahRekening(){
        Intent intent = new Intent(AkunBankActivity.this, AddBankActivity.class);
        startActivity(intent);
        AkunBankActivity.this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_bank);
        ButterKnife.bind(this);
        context = this;
        initComponent();

    }

    private void initComponent() {
        getListBankAccount();
    }

    private void getListBankAccount() {
        Loading.show(context);
        ApiCore.apiInterface().getBankAccount(PreferenceManager.getSessionToken()).enqueue(new Callback<List<BankAccountResponse>>() {
            @Override
            public void onResponse(Call<List<BankAccountResponse>> call, Response<List<BankAccountResponse>> response) {
                Loading.hide(context);
                try {
                    if (response.isSuccessful()){
                        List<BankAccountResponse> responseList = response.body();
                        if (Objects.requireNonNull(responseList).size()>0){
                            layout_empty_bank.setVisibility(View.GONE);
                            rv_akun_bank.setVisibility(View.VISIBLE);
                            adapter = new ListBankAdapter(responseList, context, AkunBankActivity.this);
                            rv_akun_bank.setAdapter(adapter);
                        } else {
                            rv_akun_bank.setVisibility(View.GONE);
                            layout_empty_bank.setVisibility(View.VISIBLE);
                        }
                    } else {
                        MethodUtil.getErrorMessage(response.errorBody(), AkunBankActivity.this);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    MethodUtil.showOnCatch(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<List<BankAccountResponse>> call, Throwable t) {
                Loading.hide(context);
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}