package com.selada.invesproperti.presentation.portofolio.deposit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.selada.invesproperti.R;
import com.selada.invesproperti.presentation.adapter.DepositBankAdapter;
import com.selada.invesproperti.presentation.adapter.ListBankAdapter;
import com.selada.invesproperti.presentation.portofolio.withdrawal.KonfirmasiWithdrawalActivity;
import com.selada.invesproperti.presentation.profile.bantuan.BantuanActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        Intent intent = new Intent(this, BantuanActivity.class);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_isi_saldo.setLayoutManager(layoutManager);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            list.add("List "+i);
        }

        adapter = new DepositBankAdapter(list, this, this);
        rv_isi_saldo.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.pull_up_from_bottom, R.anim.push_out_to_bottom);
    }
}