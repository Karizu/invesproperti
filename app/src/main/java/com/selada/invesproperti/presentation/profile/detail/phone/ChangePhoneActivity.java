package com.selada.invesproperti.presentation.profile.detail.phone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.request.RequestUpdatePhone;
import com.selada.invesproperti.util.Loading;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePhoneActivity extends AppCompatActivity {

    @BindView(R.id.et_new_phone)
    EditText et_new_phone;
    @BindView(R.id.et_phone)
    TextView et_phone;

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.btn_simpan)
    void onClickSimpan(){
        if (et_new_phone.getText().toString().equals("")){
            et_new_phone.setError("Masukkan no telpon baru");
            return;
        }

        if (!et_new_phone.getText().toString().startsWith("0") && !et_new_phone.getText().toString().startsWith("8") || et_new_phone.getText().toString().length() < 10) {
            et_new_phone.setError("Format telpon salah");
            return;
        }

        updatePhoneNumber();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        ButterKnife.bind(this);

        initComponent();
    }

    private void initComponent() {
        if (PreferenceManager.getUserProfile()!=null) et_phone.setText(PreferenceManager.getUserProfile().getPhone());
    }

    private void updatePhoneNumber(){
        String phone = et_new_phone.getText().toString();
        phone = et_new_phone.getText().toString().startsWith("0")?"+62"+phone.substring(1):"+62"+phone;

        RequestUpdatePhone requestUpdatePhone = new RequestUpdatePhone();
        requestUpdatePhone.setFullName(PreferenceManager.getFullname());
        requestUpdatePhone.setEmail(PreferenceManager.getEmail());
        requestUpdatePhone.setPhone(phone);

        Loading.show(ChangePhoneActivity.this);
        ApiCore.apiInterface().updatePhoneNumber(requestUpdatePhone, PreferenceManager.getSessionToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Loading.hide(ChangePhoneActivity.this);
                try {
                    if (response.isSuccessful()){
                        Toast.makeText(ChangePhoneActivity.this, "Berhasil menyimpan perubahan", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        MethodUtil.getErrorMessage(response.errorBody(), ChangePhoneActivity.this);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    MethodUtil.showOnCatch(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Loading.hide(ChangePhoneActivity.this);
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