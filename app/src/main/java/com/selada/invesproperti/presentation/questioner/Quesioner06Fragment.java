package com.selada.invesproperti.presentation.questioner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.selada.invesproperti.R;
import com.selada.invesproperti.util.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Quesioner06Fragment extends Fragment {

    @BindView(R.id.rb_1)
    RadioButton rb_1;
    @BindView(R.id.rb_2)
    RadioButton rb_2;
    @BindView(R.id.rb_3)
    RadioButton rb_3;
    @BindView(R.id.rb_4)
    RadioButton rb_4;
    @BindView(R.id.rb_5)
    RadioButton rb_5;

    private String data1 = "";

    @OnClick(R.id.rb_1)
    void onClickRb1(){
        data1 = "";
        rb_1.setChecked(true);
        rb_2.setChecked(false);
        rb_3.setChecked(false);
        rb_4.setChecked(false);
        rb_5.setChecked(false);

    }
    @OnClick(R.id.rb_2)
    void onClickRb2(){
        data1 = "";
        rb_1.setChecked(false);
        rb_2.setChecked(true);
        rb_3.setChecked(false);
        rb_4.setChecked(false);
        rb_5.setChecked(false);

    }
    @OnClick(R.id.rb_3)
    void onClickRb3(){
        data1 = "";
        rb_1.setChecked(false);
        rb_2.setChecked(false);
        rb_3.setChecked(true);
        rb_4.setChecked(false);
        rb_5.setChecked(false);

    }
    @OnClick(R.id.rb_4)
    void onClickRb4(){
        data1 = "";
        rb_1.setChecked(false);
        rb_2.setChecked(false);
        rb_3.setChecked(false);
        rb_4.setChecked(true);
        rb_5.setChecked(false);

    }
    @OnClick(R.id.rb_5)
    void onClickRb5(){
        data1 = "";
        rb_1.setChecked(false);
        rb_2.setChecked(false);
        rb_3.setChecked(false);
        rb_4.setChecked(false);
        rb_5.setChecked(true);

    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quesioner_06, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        new PreferenceManager(requireActivity());
        initComponent();
    }

    private void initComponent() {

    }
}
