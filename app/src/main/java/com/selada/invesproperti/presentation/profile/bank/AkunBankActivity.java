package com.selada.invesproperti.presentation.profile.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.selada.invesproperti.IntroSliderActivity;
import com.selada.invesproperti.R;
import com.selada.invesproperti.presentation.RegisterActivity;
import com.selada.invesproperti.presentation.adapter.ListBankAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AkunBankActivity extends AppCompatActivity {

    @BindView(R.id.rv_akun_bank)
    RecyclerView rv_akun_bank;

    private ListBankAdapter adapter;
    private Context context;

    @OnClick(R.id.frame_tambah_rekening)
    void onClickTambahRekening(){
        Intent intent = new Intent(AkunBankActivity.this, AddBankActivity.class);
        startActivity(intent);
        AkunBankActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_akun_bank.setLayoutManager(layoutManager);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1; i++){
            list.add("List "+i);
        }

        adapter = new ListBankAdapter(list, context, this);
        rv_akun_bank.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}