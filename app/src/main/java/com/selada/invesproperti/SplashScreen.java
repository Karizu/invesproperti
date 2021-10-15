package com.selada.invesproperti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.selada.invesproperti.presentation.auth.LoginActivity;
import com.selada.invesproperti.util.PreferenceManager;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreen extends Activity {

    @BindView(R.id.imgLogoSplash)
    ImageView imgLogoSplash;
    @BindView(R.id.imgSplash)
    ImageView imgSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);

        new PreferenceManager(this);
//        imgSplash.setAlpha(0f);
//        imgSplash.animate().alpha(1f).setDuration(500);
//        imgLogoSplash.animate().rotationY(360).setDuration(500);
        downTimer();
    }

    private void downTimer() {
        long futureMillis = TimeUnit.SECONDS.toMillis(2);
        new CountDownTimer(futureMillis, 5000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                if (seconds == 0) {
                    cancel();
                    onFinish();
                }
            }

            @Override
            public void onFinish() {
                if (!PreferenceManager.getFullname().equals("")){
                    directToLogin();
                } else {
                    Intent intent = new Intent(SplashScreen.this, IntroSliderActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    SplashScreen.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            }
        }.start();
    }

    private void directToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        SplashScreen.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}