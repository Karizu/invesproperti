package com.selada.invesproperti.presentation.verification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.selada.invesproperti.MainActivity;
import com.selada.invesproperti.R;
import com.selada.invesproperti.util.PreferenceManager;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.selada.invesproperti.util.Constant.INVESTOR;
import static com.selada.invesproperti.util.Constant.ON_VERIFICATION;

public class VerificationCompleteActivity extends AppCompatActivity {

    @OnClick(R.id.img_close)
    void onClickClose(){
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_complete);
        ButterKnife.bind(this);

        new PreferenceManager(this);
        PreferenceManager.setUserStatus(ON_VERIFICATION);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}