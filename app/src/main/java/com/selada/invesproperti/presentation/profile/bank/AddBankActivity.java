package com.selada.invesproperti.presentation.profile.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.selada.invesproperti.R;
import com.selada.invesproperti.util.Constant;
import com.selada.invesproperti.util.MethodUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddBankActivity extends AppCompatActivity {

    @BindView(R.id.tv_bank)
    TextView tv_bank;
    @BindView(R.id.et_no_rekening)
    EditText et_no_rekening;
    @BindView(R.id.et_pemilik_rekening)
    EditText et_pemilik_rekening;

    private String bankName;
    private Context context;

    @OnClick(R.id.btn_tambah)
    void onClickTambah(){

    }

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.frame_bank)
    void onClickFrameBank(){
        Dialog dialog = MethodUtil.getDialogCart(R.layout.dialog_pilih_bank, context);
        Button btnPilihBank = dialog.findViewById(R.id.btn_tambah);
        ImageView btnClose = dialog.findViewById(R.id.imgClose);
        RadioGroup radio_group = dialog.findViewById(R.id.radio_group);
        RadioButton rb_1 = dialog.findViewById(R.id.rb_1);
        RadioButton rb_2 = dialog.findViewById(R.id.rb_2);
        RadioButton rb_3 = dialog.findViewById(R.id.rb_3);
        RadioButton rb_4 = dialog.findViewById(R.id.rb_4);
        RadioButton rb_5 = dialog.findViewById(R.id.rb_5);

        btnClose.setOnClickListener(view -> dialog.dismiss());
        radio_group.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (radioGroup.getCheckedRadioButtonId()){
                case R.id.rb_1:
                    radioGroup.check(R.id.rb_1);
                    bankName = rb_1.getText().toString();
                    break;
                case R.id.rb_2:
                    radioGroup.check(R.id.rb_2);
                    bankName = rb_2.getText().toString();
                    break;
                case R.id.rb_3:
                    radioGroup.check(R.id.rb_3);
                    bankName = rb_3.getText().toString();
                    break;
                case R.id.rb_4:
                    radioGroup.check(R.id.rb_4);
                    bankName = rb_4.getText().toString();
                    break;
                case R.id.rb_5:
                    radioGroup.check(R.id.rb_5);
                    bankName = rb_5.getText().toString();
                    break;
            }
        });

        btnPilihBank.setOnClickListener(view -> {
            tv_bank.setText(bankName);
            dialog.dismiss();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank);
        ButterKnife.bind(this);
        context = this;

        initComponent();
    }

    private void initComponent() {
        if (getIntent().getIntExtra("flag", Constant.ACTION_ADD) == Constant.ACTION_EDIT){
            tv_bank.setText("Bank Mandiri");
            et_no_rekening.setText("9999 9999 1234");
            et_pemilik_rekening.setText("M Rizki Dharmaan");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}