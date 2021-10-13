package com.selada.invesproperti.presentation.verification;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.selada.invesproperti.R;
import com.selada.invesproperti.util.MethodUtil;
import com.white.progressview.HorizontalProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerificationKtpActivity extends AppCompatActivity {

    @BindView(R.id.progressBarItem)
    HorizontalProgressView progressBarItem;
    @BindView(R.id.img_ktp)
    ImageView img_ktp;
    @BindView(R.id.img_selfie)
    ImageView img_selfie;
    @BindView(R.id.btn_lanjut)
    Button btn_lanjut;
    @BindView(R.id.layout_text_1)
    LinearLayout layout_text_1;
    @BindView(R.id.layout_text_2)
    LinearLayout layout_text_2;

    private static final int KTP_CAMERA_PERMISSION_CODE = 1;
    private static final int SELFIE_CAMERA_PERMISSION_CODE = 2;
    private static final int KTP_CAMERA_REQUEST = 1;
    private static final int SELFIE_CAMERA_REQUEST = 2;
    private int flagCamera = 0;
    private boolean isInvestor;
    private boolean isProjectOwner;
    private Bitmap photoKtp;
    private Bitmap photoSelfie;

    @OnClick(R.id.btn_back)
    void onClickBtnBack() {
        onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.frame_tahap_1)
    void onClickFrameTahap1() {
        openCameraKtp();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.frame_tahap_2)
    void onClickFrameTahap2() {
        openCameraSelfie();
    }

    @OnClick(R.id.btn_lanjut)
    void onClickBtnLanjut() {
        Intent intent = new Intent(this, VerificationData1Activity.class);
        intent.putExtra("is_investor", isInvestor);
        intent.putExtra("is_project_owner", isProjectOwner);
        intent.putExtra("photo_ktp", MethodUtil.bitmapToByteArray(photoKtp));
        intent.putExtra("photo_selfie", MethodUtil.bitmapToByteArray(photoSelfie));
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_ktp);
        ButterKnife.bind(this);

        initComponent();
    }

    private void initComponent() {
        // set progress when foto uploaded
        progressBarItem.setProgress(40);

        if (getIntent() != null){
            isInvestor = getIntent().getBooleanExtra("is_investor", false);
            isProjectOwner = getIntent().getBooleanExtra("is_project_owner", false);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void openCameraKtp() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, KTP_CAMERA_PERMISSION_CODE);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, KTP_CAMERA_REQUEST);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void openCameraSelfie() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, SELFIE_CAMERA_PERMISSION_CODE);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, SELFIE_CAMERA_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == KTP_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, KTP_CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == SELFIE_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, SELFIE_CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == KTP_CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photoKtp = (Bitmap) data.getExtras().get("data");
            img_ktp.setImageBitmap(photoKtp);
            layout_text_1.setVisibility(View.GONE);
            flagCamera++;
            if (flagCamera == 2){
                btn_lanjut.setEnabled(true);
                btn_lanjut.setBackground(getResources().getDrawable(R.drawable.bg_round_green));
            }
        } else if (requestCode == SELFIE_CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photoSelfie = (Bitmap) data.getExtras().get("data");
            img_selfie.setImageBitmap(photoSelfie);
            layout_text_2.setVisibility(View.GONE);
            flagCamera++;
            if (flagCamera == 2){
                btn_lanjut.setEnabled(true);
                btn_lanjut.setBackground(getResources().getDrawable(R.drawable.bg_round_green));
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}