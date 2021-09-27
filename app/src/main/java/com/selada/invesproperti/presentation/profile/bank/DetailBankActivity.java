package com.selada.invesproperti.presentation.profile.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.selada.invesproperti.R;
import com.selada.invesproperti.util.Constant;
import com.selada.invesproperti.util.MethodUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailBankActivity extends AppCompatActivity {

    @OnClick(R.id.btn_ubah)
    void onClickUbah(){
        Intent intent = new Intent(DetailBankActivity.this, AddBankActivity.class);
        intent.putExtra("flag", Constant.ACTION_EDIT);
        startActivity(intent);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.btn_delete)
    void onClickBtnDelete(){
        Dialog dialog = MethodUtil.getDialogCart(R.layout.dialog_delete_account_no, DetailBankActivity.this);
        Button btn_ya = dialog.findViewById(R.id.btn_ya);
        Button btn_tidak = dialog.findViewById(R.id.btn_tidak);

        btn_ya.setOnClickListener(view -> dialog.dismiss());
        btn_tidak.setOnClickListener(view -> dialog.dismiss());
    }

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bank);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}