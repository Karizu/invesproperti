package com.selada.invesproperti.presentation.portofolio;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.madapps.liquid.LiquidRefreshLayout;
import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.response.ResponseProjects;
import com.selada.invesproperti.presentation.adapter.HomeFeedAdapter;
import com.selada.invesproperti.util.Loading;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ExploreBisnisPropertiActivity extends AppCompatActivity {

    @BindView(R.id.rv_explore_bisnis)
    RecyclerView rv_explore_bisnis;

    @BindView(R.id.btn_semua)
    FrameLayout btn_semua;
    @BindView(R.id.btn_baru)
    FrameLayout btn_baru;
    @BindView(R.id.btn_sedang_berjalan)
    FrameLayout btn_sedang_berjalan;
    @BindView(R.id.btn_terdanai)
    FrameLayout btn_terdanai;

    @BindView(R.id.tv_semua)
    TextView tv_semua;
    @BindView(R.id.tv_baru)
    TextView tv_baru;
    @BindView(R.id.tv_sedang_berjalan)
    TextView tv_sedang_berjalan;
    @BindView(R.id.tv_terdanai)
    TextView tv_terdanai;
    @BindView(R.id.tv_title_bar)
    TextView tv_title_bar;
    @BindView(R.id.refreshLayout)
    LiquidRefreshLayout refreshLayout;

    private HomeFeedAdapter adapter;
    private Typeface tf_bold;
    private Typeface tf_regular;

    @OnClick(R.id.btn_semua)
    void onClickBtnSemua(){

        btn_semua.setBackground(getResources().getDrawable(R.drawable.bg_round_tab));
        btn_baru.setBackground(getResources().getDrawable(R.drawable.bg_round_tab_normal));
        btn_sedang_berjalan.setBackground(getResources().getDrawable(R.drawable.bg_round_tab_normal));
        btn_terdanai.setBackground(getResources().getDrawable(R.drawable.bg_round_tab_normal));

        tv_semua.setTypeface(tf_bold);
        tv_baru.setTypeface(tf_regular);
        tv_sedang_berjalan.setTypeface(tf_regular);
        tv_terdanai.setTypeface(tf_regular);

        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_baru)
    void onClickbtn_baru(){

        btn_semua.setBackground(getResources().getDrawable(R.drawable.bg_round_tab_normal));
        btn_baru.setBackground(getResources().getDrawable(R.drawable.bg_round_tab));
        btn_sedang_berjalan.setBackground(getResources().getDrawable(R.drawable.bg_round_tab_normal));
        btn_terdanai.setBackground(getResources().getDrawable(R.drawable.bg_round_tab_normal));

        tv_semua.setTypeface(tf_regular);
        tv_baru.setTypeface(tf_bold);
        tv_sedang_berjalan.setTypeface(tf_regular);
        tv_terdanai.setTypeface(tf_regular);

        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_sedang_berjalan)
    void onClickbtn_sedang_berjalan(){

        btn_semua.setBackground(getResources().getDrawable(R.drawable.bg_round_tab_normal));
        btn_baru.setBackground(getResources().getDrawable(R.drawable.bg_round_tab_normal));
        btn_sedang_berjalan.setBackground(getResources().getDrawable(R.drawable.bg_round_tab));
        btn_terdanai.setBackground(getResources().getDrawable(R.drawable.bg_round_tab_normal));

        tv_semua.setTypeface(tf_regular);
        tv_baru.setTypeface(tf_regular);
        tv_sedang_berjalan.setTypeface(tf_bold);
        tv_terdanai.setTypeface(tf_regular);

        adapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick(R.id.btn_terdanai)
    void onClickbtn_terdanai(){

        btn_semua.setBackground(getResources().getDrawable(R.drawable.bg_round_tab_normal));
        btn_baru.setBackground(getResources().getDrawable(R.drawable.bg_round_tab_normal));
        btn_sedang_berjalan.setBackground(getResources().getDrawable(R.drawable.bg_round_tab_normal));
        btn_terdanai.setBackground(getResources().getDrawable(R.drawable.bg_round_tab));

        tv_semua.setTypeface(tf_regular);
        tv_baru.setTypeface(tf_regular);
        tv_sedang_berjalan.setTypeface(tf_regular);
        tv_terdanai.setTypeface(tf_bold);

        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_bisnis_properti);
        ButterKnife.bind(this);

        initComponent();
    }

    @SuppressLint("SetTextI18n")
    private void initComponent() {
        tv_title_bar.setText("Eksplore Bisnis & Property");
        tf_bold = getResources().getFont(R.font.titilliumweb_bold);
        tf_regular = getResources().getFont(R.font.titilliumweb_regular);
        getListProjects(false);
        refreshLayout.setOnRefreshListener(new LiquidRefreshLayout.OnRefreshListener() {
            @Override
            public void completeRefresh() {

            }

            @Override
            public void refreshing() {
                getListProjects(true);
            }
        });
    }

    private void getListProjects(boolean isRefresh){
        Loading.show(ExploreBisnisPropertiActivity.this);
        ApiCore.apiInterface().getListProjects(PreferenceManager.getSessionToken()).enqueue(new Callback<List<ResponseProjects>>() {
            @Override
            public void onResponse(Call<List<ResponseProjects>> call, Response<List<ResponseProjects>> response) {
                Loading.hide(ExploreBisnisPropertiActivity.this);
                if (isRefresh) refreshLayout.finishRefreshing();
                try {
                    if (response.isSuccessful()){
                        rv_explore_bisnis.setLayoutManager(new LinearLayoutManager(ExploreBisnisPropertiActivity.this, LinearLayoutManager.VERTICAL, false));
                        adapter = new HomeFeedAdapter(response.body(), getApplicationContext(), ExploreBisnisPropertiActivity.this);
                        rv_explore_bisnis.setAdapter(adapter);
                        rv_explore_bisnis.scheduleLayoutAnimation();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    MethodUtil.showOnCatch(findViewById(android.R.id.content));
                }
            }

            @Override
            public void onFailure(Call<List<ResponseProjects>> call, Throwable t) {
                if (isRefresh) refreshLayout.finishRefreshing();
                Loading.hide(ExploreBisnisPropertiActivity.this);
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