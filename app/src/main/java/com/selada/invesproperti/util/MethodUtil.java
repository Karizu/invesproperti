package com.selada.invesproperti.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.selada.invesproperti.R;
import com.selada.invesproperti.SplashScreen;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.request.RequestRefreshToken;
import com.selada.invesproperti.model.response.ResponseError;
import com.selada.invesproperti.model.response.ResponseLogin;
import com.selada.invesproperti.model.response.ResponseRefreshToken;
import com.selada.invesproperti.presentation.auth.LoginActivity;
import com.selada.invesproperti.presentation.auth.RegisterActivity;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dhimas on 9/29/17.
 */

public class MethodUtil extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        MethodUtil.context = getApplicationContext();
    }

    public static Dialog showDialog(Context context, int layout, String title) {
        Dialog dialog = new Dialog(Objects.requireNonNull(context));
        //set content
        dialog.setContentView(layout);
        dialog.setTitle(title);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, R.color.very_light_pink)));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        return dialog;
    }

    public static ProgressDialog getLoadingBar(Context context, String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(msg);
        progressDialog.show();
        return progressDialog;
    }

    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }

    public static Dialog getDialogCart(int layout, Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(layout);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogThemes;
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    public static void toggleTransitionSlideEnd(ViewGroup parent, View layout, boolean isShow) {
        Transition transition = new Explode();
        transition.setDuration(300);
        transition.addTarget(layout);

        TransitionManager.beginDelayedTransition(parent, transition);
        layout.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public static void toggleTransitionSlideBottom(ViewGroup parent, View layout, boolean isShow) {
        Transition transition = new Explode();
        transition.setDuration(300);
        transition.addTarget(layout);

        TransitionManager.beginDelayedTransition(parent, transition);
        layout.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public static void toggleTransitionExplode(ViewGroup parent, View layout, boolean isShow) {
        Transition transition = new ChangeTransform();
        transition.setDuration(100);
        transition.addTarget(layout);

        TransitionManager.beginDelayedTransition(parent, transition);
        layout.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public static void toggleTransitionSlideStart(ViewGroup parent, View layout, boolean isShow) {
        Transition transition = new Explode();
        transition.setDuration(300);
        transition.addTarget(layout);

        TransitionManager.beginDelayedTransition(parent, transition);
        layout.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public static String toCurrencyFormat(final String value) {

        if (!TextUtils.isEmpty(value)) {
            String formattedPrice = value.replaceAll("[^\\d]", "");

            String reverseValue = new StringBuilder(formattedPrice).reverse().toString();
            StringBuilder finalValue = new StringBuilder();
            for (int i = 1; i <= reverseValue.length(); i++) {
                char val = reverseValue.charAt(i - 1);
                finalValue.append(val);
                if (i % 3 == 0 && i != reverseValue.length() && i > 0) {
                    finalValue.append(".");
                }
            }

            return finalValue.reverse().toString();
        }

        return StringUtils.EMPTY;
    }

    public static String toDateFormat(final String value) {
        if (!TextUtils.isEmpty(value)) {
            String formattedPrice = value.replaceAll("[^\\d]", "");

            String reverseValue = new StringBuilder(formattedPrice).reverse().toString();
            StringBuilder finalValue = new StringBuilder();
            for (int i = 1; i <= reverseValue.length(); i++) {
                char val = reverseValue.charAt(i - 1);
                finalValue.append(val);
                if (i % 2 == 0 && i != reverseValue.length() && i > 0) {
                    finalValue.append("/");
                }
            }

            return finalValue.reverse().toString();
        }

        return StringUtils.EMPTY;
    }

    public static String strToDateFormat(final String value) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat fromUser = new SimpleDateFormat("yyyyMM");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat myFormat = new SimpleDateFormat("MMM yy");

        try {
            return myFormat.format(fromUser.parse(value));
        } catch (ParseException e) {
            e.printStackTrace();
            return value;
        }
    }

    public static String formatTokenNumber(final String number) {
        String cleanString = number.replace(" ", "");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cleanString.length(); i++) {
            if (i % 4 == 0 && i != 0) {
                result.append("-");
            }

            result.append(cleanString.charAt(i));
        }

        return result.toString();
    }

    public static String formatCardNumber(final String number) {
        String cleanString = number.replace(" ", "");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cleanString.length(); i++) {
            if (i % 4 == 0 && i != 0) {
                result.append(" ");
            }

            result.append(cleanString.charAt(i));
        }

        return result.toString();
    }

    public static String[] formatDateAndTime(String dateTime) {
        String[] tempDateTime = new String[2];
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", new Locale("id")).parse(dateTime);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));
            @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("HH : mm");
            tempDateTime[0] = dateFormat.format(date);
            tempDateTime[1] = timeFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tempDateTime;
    }

    public static String formatDateCreditcard(String date) {
        String cleanDate = date.trim();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cleanDate.length(); i++) {
            if (i % 2 == 0 && i != 0) {
                result.append("/");
            }
            result.append(cleanDate.charAt(i));
        }
        return result.toString();

    }

    public static String getResponseError(String json) throws JSONException {
        JSONObject jObjError = new JSONObject(json);
        return jObjError.getString("error");
    }

    public static SpannableString formatStrikeString(String text) {
        SpannableString string = new SpannableString(text);
        string.setSpan(new StrikethroughSpan(), 0, string.length(), 0);
        return string;
    }

    public static String getErrorBody(String errorBody) {
        try {
            JSONObject jObjError = new JSONObject(Objects.requireNonNull(errorBody));
            return jObjError.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void getErrorMessage(ResponseBody body, Activity activity){
        ResponseError responseError = new Gson().fromJson(Objects.requireNonNull(body).charStream(), ResponseError.class);
        String errorMessage = responseError.getErrorMessage();
        Dialog dialog = MethodUtil.getDialogCart(R.layout.dialog_lottie_failed, activity);
        TextView textView = dialog.findViewById(R.id.tv_msg);
        textView.setText(errorMessage);
    }

    public static void showOnCatch(View parentLayout){
        String errorMessage = "Terjadi kesalahan, perika koneksi internet anda\nResponse Code: Catch On";
        Snackbar.make(parentLayout, errorMessage, Snackbar.LENGTH_LONG)
                .show();
    }

    public static void showSnackBar(View parentLayout, String Msg){
        Snackbar.make(parentLayout, Msg, Snackbar.LENGTH_LONG)
                .show();
    }

    public static byte[] bitmapToByteArray(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap byteArrayToBitmap(byte[] byteArray){
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    public static void refreshToken(Activity activity){
        new PreferenceManager(activity);
        ResponseLogin responseLogin = PreferenceManager.getLoginResponse();

        RequestRefreshToken requestRefreshToken = new RequestRefreshToken();
        requestRefreshToken.setEmail(responseLogin.getEmail());
        requestRefreshToken.setAccessToken(responseLogin.getAccessToken());
        requestRefreshToken.setRefreshToken(responseLogin.getRefreshToken());

        ApiCore.apiInterface().doRefreshToken(requestRefreshToken).enqueue(new Callback<ResponseRefreshToken>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResponseRefreshToken> call, Response<ResponseRefreshToken> response) {
                try {
                    if (response.isSuccessful()){
                        PreferenceManager.setIsUnauthorized(false);
                        responseLogin.setAccessToken(Objects.requireNonNull(response.body()).getAccessToken());
                        PreferenceManager.setSessionToken("Bearer " + response.body().getAccessToken());
                    } else {
                        Dialog dialog = getDialogCart(R.layout.dialog_finger_failed, activity);
                        TextView tv_title = dialog.findViewById(R.id.tv_title);
                        TextView tv_desc = dialog.findViewById(R.id.tv_msg);
                        CardView btn_proses = dialog.findViewById(R.id.btn_batal);
                        TextView tv_text_btn = dialog.findViewById(R.id.tv_text_btn);

                        tv_title.setText("Peringatan");
                        tv_desc.setText("Sepertinya sesi anda telah berakhir, lakukan login ulang");
                        btn_proses.setOnClickListener(view -> {
                            PreferenceManager.logOut();
                            Intent intent = new Intent(activity, LoginActivity.class);
                            activity.startActivity(intent);
                            activity.finish();
                        });
                        tv_text_btn.setText("Login");

                    }
                } catch (Exception e){
                    e.printStackTrace();
                    showSnackBar(activity.findViewById(android.R.id.content), "Response Code: On Catch");
                }
            }

            @Override
            public void onFailure(Call<ResponseRefreshToken> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static String emailPattern(){
        return "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    }

    public static boolean filterPhone(String phone_text) {
        boolean correct;
        if ((phone_text.length() <= 13) && (phone_text.matches("^[0-9-]+$")))
            correct = true;
        else
            correct = false;

        System.out.println("correct =" + correct);
        return correct;
    }
}
