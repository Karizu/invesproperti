package com.selada.invesproperti.presentation.portofolio.history;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.selada.invesproperti.R;
import com.selada.invesproperti.presentation.adapter.HistoryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryActivity extends AppCompatActivity {

    @BindView(R.id.rv_history)
    RecyclerView rv_history;
    @BindView(R.id.layout_empty_history)
    LinearLayout layout_empty_history;

    @OnClick(R.id.btn_back)
    void onClickBack(){
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        initComponent();
    }

    private void initComponent() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_history.setLayoutManager(layoutManager);

        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");

        HistoryAdapter historyAdapter = new HistoryAdapter(list, this, this);
        rv_history.setAdapter(historyAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}