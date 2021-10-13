package com.selada.invesproperti.presentation.profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.selada.invesproperti.R;
import com.selada.invesproperti.SplashScreen;
import com.selada.invesproperti.presentation.profile.bank.AkunBankActivity;
import com.selada.invesproperti.presentation.profile.bantuan.BantuanActivity;
import com.selada.invesproperti.presentation.profile.detail.DetailProfileActivity;
import com.selada.invesproperti.presentation.profile.disclaimer.DisclaimerActivity;
import com.selada.invesproperti.presentation.profile.kebijakan.KebijakanPrivasiActivity;
import com.selada.invesproperti.presentation.profile.ketentuan.SyaratKetentuanActivity;
import com.selada.invesproperti.presentation.profile.mitigasi.MitigasiResikoActivity;
import com.selada.invesproperti.presentation.questioner.QuestionerActivity;
import com.selada.invesproperti.presentation.verification.VerificationActivity;
import com.selada.invesproperti.util.Constant;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.jvm.internal.Intrinsics;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    @BindView(R.id.tv_profile_name)
    TextView tv_profile_name;
    @BindView(R.id.tv_profile_email)
    TextView tv_profile_email;
    @BindView(R.id.tv_profile_type)
    TextView tv_profile_type;
    @BindView(R.id.btn_profile)
    LinearLayout btn_profile;
    @BindView(R.id.btn_akun_bank)
    LinearLayout btn_akun_bank;
    @BindView(R.id.line_profile)
    ImageView line_profile;
    @BindView(R.id.line_akun_bank)
    ImageView line_akun_bank;
    @BindView(R.id.frameVerifikasi)
    FrameLayout frameVerifikasi;
    @BindView(R.id.tv_title_verification_notif)
    TextView tv_title_verification_notif;
    @BindView(R.id.img_verified)
    ImageView img_verified;
    @BindView(R.id.frame_profile_type)
    FrameLayout frame_profile_type;
    @BindView(R.id.img_profile)
    CircleImageView img_profile;

    private GoogleSignInClient mGoogleSignInClient;
    private static final int IMAGE_PICK_REQUEST = 12345;

    @OnClick(R.id.btn_signout)
    void onClickbtnSignOut(){
        signOut();
    }

    @OnClick(R.id.frameVerifikasi)
    void onClickVerifikasi(){
        if (PreferenceManager.isAlreadyQuesioner()){
            Intent intent = new Intent(requireActivity(), VerificationActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(requireActivity(), QuestionerActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.btn_cs)
    void onClickCs(){
        Intent intent = new Intent(requireActivity(), BantuanActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btn_profile)
    void onClickbtn_profile(){
        Intent intent = new Intent(requireActivity(), DetailProfileActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btn_disclaimer)
    void onClickbtn_disclaimer(){
        Intent intent = new Intent(requireActivity(), DisclaimerActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btn_kebijakan)
    void onClickbtn_kebijakan(){
        Intent intent = new Intent(requireActivity(), KebijakanPrivasiActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btn_syarat_ketentuan)
    void onClickbtn_syarat_ketentuan(){
        Intent intent = new Intent(requireActivity(), SyaratKetentuanActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btn_mitigasi)
    void onClickbtn_mitigasi(){
        Intent intent = new Intent(requireActivity(), MitigasiResikoActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btn_bantuan)
    void onClickBtnBantuan(){
        Intent intent = new Intent(requireActivity(), BantuanActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btn_akun_bank)
    void onClickBtnBank(){
        Intent intent = new Intent(requireActivity(), AkunBankActivity.class);
        startActivity(intent);
        requireActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.frame_profile_pic)
    void onClickFramePic(){
        displayChoiceDialog();
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        configureGoogleSignIn();
        setContentHome();
    }

    @SuppressLint("SetTextI18n")
    private void setContentHome() {
        tv_profile_name.setText(PreferenceManager.getFullname());
        tv_profile_email.setText(PreferenceManager.getEmail());

        switch (PreferenceManager.getUserStatus()){
            case Constant.GUEST:
                btn_profile.setVisibility(View.GONE);
                btn_akun_bank.setVisibility(View.GONE);
                line_profile.setVisibility(View.GONE);
                line_akun_bank.setVisibility(View.GONE);
                img_verified.setVisibility(View.GONE);
                frame_profile_type.setBackground(getResources().getDrawable(R.drawable.bg_round_profile_type));
                tv_profile_type.setText("Guest");
                tv_profile_type.setTextColor(getResources().getColor(R.color.black_primary));
                frameVerifikasi.setVisibility(View.VISIBLE);
                tv_title_verification_notif.setText("Verifikasi akun anda sekarang!");
                break;
            case Constant.ON_VERIFICATION:
                btn_profile.setVisibility(View.GONE);
                btn_akun_bank.setVisibility(View.GONE);
                line_profile.setVisibility(View.GONE);
                line_akun_bank.setVisibility(View.GONE);
                img_verified.setVisibility(View.GONE);
                frame_profile_type.setBackground(getResources().getDrawable(R.drawable.bg_round_profile_type));
                tv_profile_type.setText("Guest");
                tv_profile_type.setTextColor(getResources().getColor(R.color.black_primary));
                frameVerifikasi.setVisibility(View.VISIBLE);
                tv_title_verification_notif.setText("Akun sedang dalam proses verifikasi data");
                break;
            case Constant.INVESTOR:
                btn_profile.setVisibility(View.VISIBLE);
                btn_akun_bank.setVisibility(View.VISIBLE);
                line_profile.setVisibility(View.VISIBLE);
                line_akun_bank.setVisibility(View.VISIBLE);
                img_verified.setVisibility(View.VISIBLE);
                frame_profile_type.setBackground(getResources().getDrawable(R.drawable.bg_round_profile_type_green));
                tv_profile_type.setText("Investor");
                tv_profile_type.setTextColor(getResources().getColor(R.color.white));
                if (PreferenceManager.isAlreadyQuesioner()){
                    frameVerifikasi.setVisibility(View.GONE);
                } else {
                    tv_title_verification_notif.setText("Akun anda terverifikasi. Isi quesioner.");
                    frameVerifikasi.setVisibility(View.VISIBLE);
                }
                break;
            case Constant.PRODUCT_OWNER:
                btn_profile.setVisibility(View.VISIBLE);
                btn_akun_bank.setVisibility(View.VISIBLE);
                line_profile.setVisibility(View.VISIBLE);
                line_akun_bank.setVisibility(View.VISIBLE);
                img_verified.setVisibility(View.VISIBLE);
                frame_profile_type.setBackground(getResources().getDrawable(R.drawable.bg_round_profile_type_green));
                tv_profile_type.setText("Product Owner");
                tv_profile_type.setTextColor(getResources().getColor(R.color.white));
                frameVerifikasi.setVisibility(View.GONE);
                break;
        }
    }

    private void signOut() {
        Dialog dialog = MethodUtil.getDialogCart(R.layout.dialog_logout, requireContext());
        CardView btnLogout = dialog.findViewById(R.id.btnLogout);
        ImageView btnClose = dialog.findViewById(R.id.imgClose);

        btnLogout.setOnClickListener(view -> {

            switch (PreferenceManager.getLoginFrom()){
                case Constant.LOGIN_FROM_GOOGLE:
                    mGoogleSignInClient.signOut()
                            .addOnCompleteListener(requireActivity(), task -> PreferenceManager.logOut());
                    startActivity(new Intent(requireContext(), SplashScreen.class));
                    requireActivity().finish();
                    break;
                case Constant.LOGIN_FROM_FACEBOOK:
                    FirebaseAuth.getInstance().signOut();
                    PreferenceManager.logOut();
                    startActivity(new Intent(requireContext(), SplashScreen.class));
                    requireActivity().finish();
                    break;
                case Constant.LOGIN_FROM_EMAIL:
                    PreferenceManager.logOut();
                    startActivity(new Intent(requireContext(), SplashScreen.class));
                    requireActivity().finish();
                    break;
            }

        });

        btnClose.setOnClickListener(view -> dialog.dismiss());
    }

    private void configureGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==IMAGE_PICK_REQUEST) {
            if(resultCode == RESULT_OK&&data!=null) {
                Uri selectedImageUri = data.getData();
                if(selectedImageUri!=null){
                    img_profile.setImageURI(selectedImageUri);
                    String imagePath = getRealPathFromURI(requireContext(), selectedImageUri);
                } else {
                    Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                    img_profile.setImageBitmap(bitmap);
                }
            } else {
                Log.d("==>","Operation canceled!");
            }
        } else if (requestCode==0){
            if(resultCode == RESULT_OK){
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(
                        Intent.createChooser(intent, "Select profile picture"), IMAGE_PICK_REQUEST);
            }
        }
    }

    private void displayChoiceDialog() {
        String choiceString[] = new String[] {"Gallery" ,"Camera"};
        AlertDialog.Builder dialog= new AlertDialog.Builder(requireActivity());
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Select image from");
        dialog.setItems(choiceString,
                (dialog1, which) -> {
                    Intent intent = null;
                    if (which == 0) {
                        intent = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(
                                Intent.createChooser(intent, "Select profile picture"), IMAGE_PICK_REQUEST);
                    } else {
                        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                                == PackageManager.PERMISSION_DENIED) {
                            ActivityCompat.requestPermissions(requireActivity(), new String[] {Manifest.permission.CAMERA}, 0);
                        } else {
                            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(
                                    Intent.createChooser(intent, "Select profile picture"), IMAGE_PICK_REQUEST);
                        }
                    }

                }).show();
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, projection, null, null, null);

            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
