package com.selada.invesproperti.presentation.portofolio.submitproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gun0912.tedpicker.Config;
import com.gun0912.tedpicker.ImagePickerActivity;
import com.selada.invesproperti.R;
import com.selada.invesproperti.model.PhotoUriModel;
import com.selada.invesproperti.model.request.SubmitProductRequest;
import com.selada.invesproperti.presentation.portofolio.submitproject.adapter.ImageListAdapter;
import com.selada.invesproperti.presentation.portofolio.submitproject.adapter.ImageListFilePathAdapter;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.selada.invesproperti.presentation.profile.ProfileFragment.getRealPathFromURI;

public class SubmitProject3Activity extends AppCompatActivity {

    @BindView(R.id.rv_image)
    RecyclerView rv_image;

    private int PICK_IMAGE_MULTIPLE = 1;
    private String imageEncoded;
    private List<String> imagesEncodedList;
    private List<Uri> mArrayUri;
    private PhotoUriModel photoUriModel;
    private SubmitProductRequest submitProductRequest;
    private List<String> filePathList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_project3);
        ButterKnife.bind(this);
        new PreferenceManager(this);
        initComponent();
    }

    private void initComponent() {
        filePathList = new ArrayList<>();
        submitProductRequest = PreferenceManager.getSubmitProject();
        if (PreferenceManager.getIsSaveProductData()){
            try {
                if (submitProductRequest.getPhotos()!=null){
                    filePathList = submitProductRequest.getPhotos();
                    ImageListFilePathAdapter adapter = new ImageListFilePathAdapter(submitProductRequest.getPhotos(), SubmitProject3Activity.this, SubmitProject3Activity.this);
                    rv_image.setAdapter(adapter);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private void getImages() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resuleCode, Intent intent) {
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE && resuleCode == RESULT_OK
                    && null != intent) {
                // Get the Image from data

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<>();
                if(intent.getData()!=null){
                    mArrayUri = new ArrayList<>();
                    Uri mImageUri = intent.getData();
                    mArrayUri.add(mImageUri);

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    Objects.requireNonNull(cursor).moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    Log.d("image", imageEncoded);

                    cursor.close();

                    ImageListAdapter adapter = new ImageListAdapter(mArrayUri, SubmitProject3Activity.this, SubmitProject3Activity.this);
                    rv_image.setAdapter(adapter);

                } else {
                    if (intent.getClipData() != null) {
                        ClipData mClipData = intent.getClipData();
                        mArrayUri = new ArrayList<>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);

                            Log.d("image", imageEncoded);

                            cursor.close();

                        }

                        ImageListAdapter adapter = new ImageListAdapter(mArrayUri, SubmitProject3Activity.this, SubmitProject3Activity.this);
                        rv_image.setAdapter(adapter);

                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
        super.onActivityResult(requestCode, resuleCode, intent);
    }

    public void updateMArrayUri(Uri uri){
        mArrayUri.remove(uri);
    }

    public void updateMArrayFile(String filePath){
        filePathList.remove(filePath);
    }

    @OnClick(R.id.frame_upload)
    void onClickUpload(){
        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(SubmitProject3Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SubmitProject3Activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return;
            }
        }

        getImages();
    }

    private void setProductDataModel() {
        submitProductRequest = PreferenceManager.getSubmitProject();
        if (filePathList.size() > 0){
            submitProductRequest.setPhotos(filePathList);
        } else {
            for (Uri uri: mArrayUri){ filePathList.add(getRealPathFromURI(SubmitProject3Activity.this, uri)); }
            submitProductRequest.setPhotos(filePathList);
        }

        PreferenceManager.setSubmitProject(submitProductRequest);
        Log.d("SubmitProductRequest", new Gson().toJson(PreferenceManager.getSubmitProject()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        submitProductRequest = PreferenceManager.getSubmitProject();
        Log.d("onStart", new Gson().toJson(submitProductRequest));
    }

    @Override
    protected void onResume() {
        super.onResume();
        submitProductRequest = PreferenceManager.getSubmitProject();
        Log.d("onResume", new Gson().toJson(submitProductRequest));
    }

    @OnClick(R.id.frame_save)
    void onClickSave(){
        //onSave
        PreferenceManager.setIsSaveProductData(true);
        setProductDataModel();
        MethodUtil.showSnackBar(findViewById(android.R.id.content), "Data berhasil disimpan");
    }

    @OnClick(R.id.btn_lanjut)
    void onClickLanjut(){
        if (mArrayUri == null && filePathList.size() == 0){
            MethodUtil.showSnackBar(SubmitProject3Activity.this, "Silahkan upload galeri usaha anda");
            return;
        }

        setProductDataModel();
        photoUriModel = new PhotoUriModel();
        photoUriModel.setUriList(mArrayUri);

        Intent intent = new Intent(SubmitProject3Activity.this, SubmitProject4Activity.class);
        intent.putExtra("json_photos", new Gson().toJson(photoUriModel));
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}