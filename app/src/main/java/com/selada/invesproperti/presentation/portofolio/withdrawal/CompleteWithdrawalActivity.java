package com.selada.invesproperti.presentation.portofolio.withdrawal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.selada.invesproperti.MainActivity;
import com.selada.invesproperti.R;
import com.selada.invesproperti.presentation.payment.InquiryPaymentActivity;
import com.selada.invesproperti.presentation.payment.PaymentCompleteActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompleteWithdrawalActivity extends AppCompatActivity {

    @OnClick(R.id.img_close)
    void onClickClose(){
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_withdrawal);
        ButterKnife.bind(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}