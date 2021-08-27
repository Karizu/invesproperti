package com.selada.invesproperti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private Context context;

    @OnClick(R.id.btnSignOut)
    void onClickbtnSignOut(){
        signOut();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        context = this;
        new PreferenceManager(this);
        if(PreferenceManager.isFirstTimeLaunch()){
            PreferenceManager.setFirstTimeLaunch(false);
            startActivity(new Intent(MainActivity.this, IntroSliderActivity.class));
            finish();
        }

        configureGoogleSignIn();
    }

    private void signOut() {
        Dialog dialog = MethodUtil.getDialogCart(R.layout.dialog_logout, context);
        CardView btnLogout = dialog.findViewById(R.id.btnLogout);
        ImageView btnClose = dialog.findViewById(R.id.imgClose);

        btnLogout.setOnClickListener(view -> {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, task -> PreferenceManager.logOut());

            startActivity(new Intent(MainActivity.this, SplashScreen.class));
            finish();
        });

        btnClose.setOnClickListener(view -> dialog.dismiss());
    }

    private void configureGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
}