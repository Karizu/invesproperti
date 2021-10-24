package com.selada.invesproperti.presentation.profile.detail.pass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.request.ChangePassRequest;
import com.selada.invesproperti.model.response.ResponseUserProfile;
import com.selada.invesproperti.util.LoadingPost;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.anshul.libray.PasswordEditText;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassActivity extends AppCompatActivity {

    @BindView(R.id.et_pass_lama)
    PasswordEditText et_pass_lama;
    @BindView(R.id.et_pass_baru)
    PasswordEditText et_pass_baru;
    @BindView(R.id.et_konfirm_pass)
    PasswordEditText et_konfirm_pass;

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @OnClick(R.id.btn_ganti)
    void onClickBtnGanti(){
        if (TextUtils.isEmpty(et_pass_lama.getText().toString()) || TextUtils.isEmpty(et_pass_baru.getText().toString())
        || TextUtils.isEmpty(et_konfirm_pass.getText().toString())){
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Silahkan lengkapi data");
        } else {
            changePass();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        ButterKnife.bind(this);

        new PreferenceManager(this);
    }

    private void changePass(){
        ResponseUserProfile userProfile = PreferenceManager.getUserProfile();
        String email = userProfile.getEmail();

        ChangePassRequest changePassRequest = new ChangePassRequest();
        changePassRequest.setEmail(email);
        changePassRequest.setOldPassword(et_pass_lama.getText().toString());
        changePassRequest.setPassword(et_pass_baru.getText().toString());
        changePassRequest.setRePassword(et_konfirm_pass.getText().toString());

        LoadingPost.show(ChangePassActivity.this);
        ApiCore.apiInterface().changePass(changePassRequest, PreferenceManager.getSessionToken()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                LoadingPost.hide(ChangePassActivity.this);
                try {
                    if (response.isSuccessful()){
                        Toast.makeText(ChangePassActivity.this, "Berhasil Ubah Password", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        MethodUtil.getErrorMessage(response.errorBody(), ChangePassActivity.this);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    MethodUtil.showOnCatch(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                LoadingPost.hide(ChangePassActivity.this);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}