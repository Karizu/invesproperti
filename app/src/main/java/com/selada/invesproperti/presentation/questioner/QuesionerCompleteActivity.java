package com.selada.invesproperti.presentation.questioner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.selada.invesproperti.MainActivity;
import com.selada.invesproperti.R;
import com.selada.invesproperti.util.PreferenceManager;

import butterknife.BindView;
import butterknife.OnClick;

public class QuesionerCompleteActivity extends AppCompatActivity {

    @BindView(R.id.tv_name)
    TextView tv_name;

    @OnClick(R.id.img_close)
    void onClickImgClose(){
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quesioner_complete);

        new PreferenceManager(this);
        PreferenceManager.setIsAlreadyQuesioner(true);

        if(PreferenceManager.getUserProfile()!=null) tv_name.setText(PreferenceManager.getUserProfile().getName());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(QuesionerCompleteActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}