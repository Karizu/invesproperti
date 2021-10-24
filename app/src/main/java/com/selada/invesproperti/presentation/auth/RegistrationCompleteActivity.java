package com.selada.invesproperti.presentation.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.request.RequestLogin;
import com.selada.invesproperti.model.response.ResponseLogin;
import com.selada.invesproperti.util.Constant;
import com.selada.invesproperti.util.LoadingPost;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationCompleteActivity extends AppCompatActivity {

    @BindView(R.id.tv_name)
    TextView tv_name;

    @OnClick(R.id.btn_lanjut)
    void onClickLanjut(){
        directToMainActivity();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_complete);
        ButterKnife.bind(this);

        String name = getIntent().getStringExtra("name");
        tv_name.setText(name + "!");
    }

    private void directToMainActivity() {
        Intent intent = new Intent(this, ActivateFingerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        directToMainActivity();
    }
}