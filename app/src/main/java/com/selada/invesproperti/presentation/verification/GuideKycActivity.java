package com.selada.invesproperti.presentation.verification;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.selada.invesproperti.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideKycActivity extends AppCompatActivity {

    @BindView(R.id.img_kyc)
    ImageView img_kyc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_kyc);
        ButterKnife.bind(this);

        boolean isKyc1 = getIntent().getBooleanExtra("is_kyc_1", false);
        if (isKyc1){
            img_kyc.setImageDrawable(getResources().getDrawable(R.drawable.panduan_kyc_poin1));
        } else {
            img_kyc.setImageDrawable(getResources().getDrawable(R.drawable.panduan_kyc_poin2));
        }

        boolean isFromArticle = getIntent().getBooleanExtra("is_from_article", false);
        if (isFromArticle){
            byte[] decodedString = getIntent().getByteArrayExtra("bitmap");
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, Objects.requireNonNull(decodedString).length);
            img_kyc.setImageBitmap(bitmap);
        }
    }
}