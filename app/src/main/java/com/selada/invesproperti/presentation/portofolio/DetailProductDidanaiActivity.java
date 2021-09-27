package com.selada.invesproperti.presentation.portofolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.selada.invesproperti.R;
import com.selada.invesproperti.presentation.portofolio.tablayout.FinancialFragment;
import com.selada.invesproperti.presentation.portofolio.tablayout.InfoFragment;
import com.selada.invesproperti.presentation.portofolio.tablayout.ReportFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailProductDidanaiActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.viewpagertab)
    SmartTabLayout viewpagertab;
    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;

    private FragmentPagerItemAdapter adapter;

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product_didanai);
        ButterKnife.bind(this);

        initComponent();
    }

    @SuppressLint("SetTextI18n")
    private void initComponent() {
        tv_title_bar.setText("Yellow Carwash Galuh Mas");
        adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.titleA, InfoFragment.class)
                .add(R.string.titleB, FinancialFragment.class)
                .add(R.string.titleC, ReportFragment.class)
                .create());
        viewPager.setAdapter(adapter);
        viewpagertab.setViewPager(viewPager);
        viewpagertab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        DetailProductDidanaiActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}