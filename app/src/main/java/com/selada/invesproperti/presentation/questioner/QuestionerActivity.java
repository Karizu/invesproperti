package com.selada.invesproperti.presentation.questioner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.selada.invesproperti.R;
import com.selada.invesproperti.presentation.adapter.QuestionerViewPagerAdapter;
import com.selada.invesproperti.presentation.adapter.ViewPagerAdapter;
import com.white.progressview.HorizontalProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionerActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager view_pager;
    @BindView(R.id.progressBarItem)
    HorizontalProgressView progressBarItem;
    @BindView(R.id.frame_back)
    FrameLayout frame_back;

    private QuestionerViewPagerAdapter mViewPagerAdapter;

    @OnClick(R.id.btn_berikutnya)
    void onClickBtnBerikutnya(){
        int current = getItem(1, true);
        if (current < mViewPagerAdapter.getCount()) {
            view_pager.setCurrentItem(current, true);
        } else {
            Intent intent = new Intent(this, QuesionerCompleteActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @OnClick(R.id.frame_back)
    void onClickFrameBack(){
        int current = getItem(1, false);
        view_pager.setCurrentItem(current, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questioner);
        ButterKnife.bind(this);

        initComponent();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initComponent() {
        frame_back.setVisibility(View.GONE);
        progressBarItem.setVisibility(View.GONE);
        mViewPagerAdapter = new QuestionerViewPagerAdapter(getSupportFragmentManager());
        view_pager.setAdapter(mViewPagerAdapter);
        view_pager.setOnTouchListener((v, event) -> true);
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        frame_back.setVisibility(View.GONE);
                        progressBarItem.setVisibility(View.GONE);
                        break;
                    case 1:
                        frame_back.setVisibility(View.VISIBLE);
                        progressBarItem.setVisibility(View.VISIBLE);
                        progressBarItem.setProgress(15);
                        break;
                    case 2:
                        frame_back.setVisibility(View.VISIBLE);
                        progressBarItem.setVisibility(View.VISIBLE);
                        progressBarItem.setProgress(35);
                        break;
                    case 3:
                        frame_back.setVisibility(View.VISIBLE);
                        progressBarItem.setVisibility(View.VISIBLE);
                        progressBarItem.setProgress(55);
                        break;
                    case 4:
                        frame_back.setVisibility(View.VISIBLE);
                        progressBarItem.setVisibility(View.VISIBLE);
                        progressBarItem.setProgress(75);
                        break;
                    case 5:
                        frame_back.setVisibility(View.VISIBLE);
                        progressBarItem.setVisibility(View.VISIBLE);
                        progressBarItem.setProgress(90);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private int getItem(int i, boolean isNext) {
        if (isNext){
            return view_pager.getCurrentItem() + i;
        } else {
            return view_pager.getCurrentItem() - i;
        }
    }
}