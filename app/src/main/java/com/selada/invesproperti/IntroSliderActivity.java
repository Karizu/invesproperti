package com.selada.invesproperti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.selada.invesproperti.util.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroSliderActivity extends AppCompatActivity {

    private MyViewPagerAdapter myViewPagerAdapter;
    private int[] layouts;
    private TextView[] dots;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 0;
    private String TAG = "GoogleSignIn";

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.layoutDots)
    LinearLayout dotsLayout;
    @BindView(R.id.btn_skip)
    TextView btn_skip;
    @BindView(R.id.frame_next)
    FrameLayout frame_next;
    @BindView(R.id.frame_back)
    FrameLayout frame_back;
    @BindView(R.id.btn_register)
    RelativeLayout btn_register;

    @OnClick(R.id.btnSignInGoogle)
    void onClickBtnSignInGoogle(){
        signIn();
    }

    @OnClick(R.id.frame_next)
    void onClickBtnNext(){
        int current = getItem(1, true);
        if (current < layouts.length) {
            viewPager.setCurrentItem(current);
        } else {
            launchHomeScreen();
        }
    }

    @OnClick(R.id.frame_back)
    void onClickBtnBack(){
        int current = getItem(1, false);
        viewPager.setCurrentItem(current);
    }

    @OnClick(R.id.btn_skip)
    void onClickBtnSkip(){
        viewPager.setCurrentItem(layouts.length - 1);
    }

    @OnClick(R.id.btn_register)
    void onClickbtn_register(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!PreferenceManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_intro_slider);
        ButterKnife.bind(this);

        layouts = new int[]{
                R.layout.intro_slide_1,
                R.layout.intro_slide_2,
                R.layout.intro_slide_3
        };

        addBottomDots(0);

        changeStatusBarColor();
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        configureGoogleSignIn();
    }

    private void configureGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            directToMainActivity();
        }
//        updateUI(account);
    }

    private void directToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        IntroSliderActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            PreferenceManager.setGoogleSignInAccount(account);
            directToMainActivity();

        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "Terjadi kesalahan saat masuk akun google anda", Toast.LENGTH_SHORT).show();
        }
    }

    private void launchHomeScreen() {
        PreferenceManager.setFirstTimeLaunch(false);
        startActivity(new Intent(IntroSliderActivity.this, MainActivity.class));
        finish();
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("°"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[currentPage].setTextColor(colorsActive[currentPage]);
//            dots[currentPage].setMinWidth(10);
        }
    }

    private int getItem(int i, boolean isNext) {
        if (isNext){
            return viewPager.getCurrentItem() + i;
        } else {
            return viewPager.getCurrentItem() - i;
        }
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            // changing the next button text 'NEXT' / 'GOT IT'
            switch (position){
                case 0:
                    frame_back.setVisibility(View.GONE);
                    frame_next.setVisibility(View.VISIBLE);
                    btn_register.setVisibility(View.GONE);
                    btn_skip.setVisibility(View.VISIBLE);
                    dotsLayout.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    frame_back.setVisibility(View.VISIBLE);
                    frame_next.setVisibility(View.VISIBLE);
                    btn_register.setVisibility(View.GONE);
                    btn_skip.setVisibility(View.VISIBLE);
                    dotsLayout.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    frame_back.setVisibility(View.VISIBLE);
                    frame_next.setVisibility(View.GONE);
                    btn_register.setVisibility(View.VISIBLE);
                    btn_skip.setVisibility(View.GONE);
                    dotsLayout.setVisibility(View.GONE);
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    PreferenceManager.logOut();
                });
    }
}