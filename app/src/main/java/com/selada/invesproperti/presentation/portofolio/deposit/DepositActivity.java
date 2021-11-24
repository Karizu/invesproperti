package com.selada.invesproperti.presentation.portofolio.deposit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.response.Bank;
import com.selada.invesproperti.presentation.adapter.DepositBankAdapter;
import com.selada.invesproperti.presentation.portofolio.submitproject.SubmitProject6Activity;
import com.selada.invesproperti.presentation.profile.cs.CallCenterActivity;
import com.selada.invesproperti.util.Loading;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepositActivity extends AppCompatActivity {

    @BindView(R.id.rv_isi_saldo)
    RecyclerView rv_isi_saldo;

    private DepositBankAdapter adapter;

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.btn_cs)
    void onClickCs(){
        Intent intent = new Intent(this, CallCenterActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        ButterKnife.bind(this);

        initComponent();
    }

    private void initComponent() {
        getListBank();
    }

    private void getListBank() {
        Loading.show(this);
        ApiCore.apiInterface().getListBank(PreferenceManager.getSessionToken()).enqueue(new Callback<List<Bank>>() {
            @Override
            public void onResponse(Call<List<Bank>> call, Response<List<Bank>> response) {
                Loading.hide(DepositActivity.this);
                try {
                    if (response.isSuccessful()) {
                        rv_isi_saldo.setLayoutManager(new LinearLayoutManager(DepositActivity.this, LinearLayoutManager.VERTICAL, false));
                        adapter = new DepositBankAdapter(response.body(), DepositActivity.this, DepositActivity.this);
                        rv_isi_saldo.setAdapter(adapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    MethodUtil.showOnCatch(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<List<Bank>> call, Throwable t) {
                t.printStackTrace();
                Loading.hide(DepositActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.pull_up_from_bottom, R.anim.push_out_to_bottom);
    }
}