package com.selada.invesproperti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.ImageView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.selada.invesproperti.presentation.adapter.ViewPagerAdapter;
import com.selada.invesproperti.presentation.home.HomeFragment;
import com.selada.invesproperti.util.Constant;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.MyViewPager;
import com.selada.invesproperti.util.PreferenceManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.EasyImage;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
//    @BindView(R.id.bottom_navigation)
//    MeowBottomNavigation bottomNavigation;
    @BindView(R.id.view_pager)
    MyViewPager viewPager;

    private GoogleSignInClient mGoogleSignInClient;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        context = this;
        new PreferenceManager(this);

        configureGoogleSignIn();
        initComponent();
    }

    private void initComponent() {
//        loadFragment(new HomeFragment());///
        bottomNavigation.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mViewPagerAdapter);
        bottomNavigation.getMenu().findItem(R.id.item0).setChecked(true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigation.getMenu().findItem(R.id.item0).setChecked(true);
                        bottomNavigation.getMenu().findItem(R.id.item0).setIcon(R.drawable.ic_baseline_home_24);
                        bottomNavigation.getMenu().findItem(R.id.item1).setIcon(R.drawable.ic_outline_featured_play_list_24);
                        bottomNavigation.getMenu().findItem(R.id.item2).setIcon(R.drawable.ic_outline_mail_outline_24);
                        bottomNavigation.getMenu().findItem(R.id.item3).setIcon(R.drawable.ic_outline_account_box_24);
                        break;
                    case 1:
                        bottomNavigation.getMenu().findItem(R.id.item1).setChecked(true);
                        bottomNavigation.getMenu().findItem(R.id.item0).setIcon(R.drawable.ic_outline_home_24);
                        bottomNavigation.getMenu().findItem(R.id.item1).setIcon(R.drawable.ic_baseline_featured_play_list_24);
                        bottomNavigation.getMenu().findItem(R.id.item2).setIcon(R.drawable.ic_outline_mail_outline_24);
                        bottomNavigation.getMenu().findItem(R.id.item3).setIcon(R.drawable.ic_outline_account_box_24);
                        break;
                    case 2:
                        bottomNavigation.getMenu().findItem(R.id.item2).setChecked(true);
                        bottomNavigation.getMenu().findItem(R.id.item0).setIcon(R.drawable.ic_outline_home_24);
                        bottomNavigation.getMenu().findItem(R.id.item1).setIcon(R.drawable.ic_outline_featured_play_list_24);
                        bottomNavigation.getMenu().findItem(R.id.item2).setIcon(R.drawable.ic_baseline_drafts_24);
                        bottomNavigation.getMenu().findItem(R.id.item3).setIcon(R.drawable.ic_outline_account_box_24);
                        break;
                    case 3:
                        bottomNavigation.getMenu().findItem(R.id.item3).setChecked(true);
                        bottomNavigation.getMenu().findItem(R.id.item0).setIcon(R.drawable.ic_outline_home_24);
                        bottomNavigation.getMenu().findItem(R.id.item1).setIcon(R.drawable.ic_outline_featured_play_list_24);
                        bottomNavigation.getMenu().findItem(R.id.item2).setIcon(R.drawable.ic_outline_mail_outline_24);
                        bottomNavigation.getMenu().findItem(R.id.item3).setIcon(R.drawable.ic_baseline_account_box_24);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item0:
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.item1:
                viewPager.setCurrentItem(1, true);
                break;
            case R.id.item2:
                viewPager.setCurrentItem(2, true);
                break;
            case R.id.item3:
                viewPager.setCurrentItem(3, true);
                break;
        }
        return true;
    }

    private void signOut() {
        Dialog dialog = MethodUtil.getDialogCart(R.layout.dialog_logout, context);
        CardView btnLogout = dialog.findViewById(R.id.btnLogout);
        ImageView btnClose = dialog.findViewById(R.id.imgClose);

        btnLogout.setOnClickListener(view -> {

            switch (PreferenceManager.getLoginFrom()){
                case Constant.LOGIN_FROM_GOOGLE:
                    mGoogleSignInClient.signOut()
                            .addOnCompleteListener(this, task -> PreferenceManager.logOut());
                    startActivity(new Intent(MainActivity.this, SplashScreen.class));
                    finish();
                    break;
                case Constant.LOGIN_FROM_FACEBOOK:
                    FirebaseAuth.getInstance().signOut();
                    PreferenceManager.logOut();
                    startActivity(new Intent(MainActivity.this, SplashScreen.class));
                    finish();
                    break;
                case Constant.LOGIN_FROM_EMAIL:
                    break;
            }

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