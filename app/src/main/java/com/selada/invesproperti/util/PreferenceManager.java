package com.selada.invesproperti.util;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.orhanobut.hawk.Hawk;


import java.util.List;

/**
 * Created by Dhimas on 10/9/17.
 */

public class PreferenceManager {

    private static final String SESSION_TOKEN = "sessionToken";
    private static final String SESSION_TOKEN_ARDI = "sessionTokenArdi";
    private static final String IS_LOGIN = "isLogin";
    private static final String IS_ARDI = "isARDI";
    private static final String USER_LOGIN = "userLogin";
    private static final String AGENT = "agent";
    private static final String MEMBER_ID = "memberId";
    private static final String SELADA_USER_ID = "seladaUserId";

    private static final String BASE_URL = "baseUrl";
    private static final String CONSUMER_KEY = "consumerKey";
    private static final String CONSUMER_SECRET = "consumerSecret";
    private static final String CODE_COUPON = "codeCoupon";

    private static final String BOOTH_ID = "booth_id";
    private static final String MASTER_KEY = "master_key";
    private static final String MENU_CATEGORY = "menu_category";
    private static final String HOME_FEED_ONE = "home_feed_one";
    private static final String HOME_FEED_TWO = "home_feed_two";
    private static final String HOME_FEED_THREE = "home_feed_three";
    private static final String NAMA_CABANG = "namaCabang";
    private static final String LOGIN_DATA = "loginData";
    private static final String LOGIN_EMAIL = "loginEmail";
    private static final String PHONE_SELADA_SEGAR = "phoneSeladaSegar";
    private static final String BASE_URL_CREATE_DEVICE = "baseUrlCreateDevice";
    private static final String IS_FIRST_OPEN = "isFirstOpen";
    private static final String IS_FIREBASE_USER = "isFirebaseUser";
    private static final String BASE_URL_SLIDER = "baseUrlSlider";
    private static final String GOOGLE_SIGN_IN_OBJECT = "googleSignInObject";

    private static final String PREF_NAME = "welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static Context ctx;
    private static PreferenceManager mInstance;

    public PreferenceManager(Context context) {
//        Hawk.init(context)
//                .setEncryptionMethod(HawkBuilder.EncryptionMethod.HIGHEST)
//                .setStorage(HawkBuilder.newSharedPrefStorage(context))
//                .setPassword("P@ssw0rd123")
//                .build();
        Hawk.init(context).build();
    }

    public static synchronized PreferenceManager getInstance(Context context){
        if (mInstance == null)
            mInstance = new PreferenceManager(context);
        return mInstance;
    }



    public static void setNamaCabang(String namaCabang){
        Hawk.put(NAMA_CABANG, namaCabang);
    }

    public static String getNamaCabang() {
        return Hawk.get(NAMA_CABANG, "KOTA BANDUNG");
    }

    public static void setBaseUrl(String baseUrl){
        Hawk.put(BASE_URL, baseUrl);
    }

    public static String getSessionToken() {
        return Hawk.get(SESSION_TOKEN, "");
    }

    public static void logOut() {
        //Hawk.put(USER_LOGIN, null);
        Hawk.put(IS_LOGIN, false);
        Hawk.deleteAll();
    }

    public static void clearHomeData() {
        Hawk.delete(MENU_CATEGORY);
        Hawk.delete(HOME_FEED_ONE);
        Hawk.delete(HOME_FEED_TWO);
        Hawk.delete(HOME_FEED_THREE);
    }

    public static void setLoginData(GoogleSignInClient loginData) {
        Hawk.put(IS_LOGIN, true);
        Hawk.put(USER_LOGIN, loginData);
    }

    public static GoogleSignInClient getLoginData(){
        return Hawk.get(USER_LOGIN, null);
    }

    public static boolean getIsLogin() {
        return Hawk.get(IS_LOGIN, false);
    }

    public static boolean setFirstTimeLaunch(boolean isFirstTime) {
        return Hawk.put(IS_FIRST_TIME_LAUNCH, isFirstTime);
    }

    public static boolean isFirstTimeLaunch() {
        return Hawk.get(IS_FIRST_TIME_LAUNCH, true);
    }

    public static boolean getFirstOpen() {
        return Hawk.get(IS_FIRST_OPEN, true);
    }


    public static boolean setFirstOpenFalse() {
        return Hawk.put(IS_FIRST_OPEN, false);
    }

    public static boolean isFirebaseUser() {
        return Hawk.get(IS_FIREBASE_USER, false);
    }


    public static boolean setFirebaseUser(boolean isFirebase) {
        return Hawk.put(IS_FIREBASE_USER, isFirebase);
    }

    public static String getPhoneSeladaSegar() {
        return Hawk.get(PHONE_SELADA_SEGAR, Constant.PHONE_SELADA_SEGAR_BANDUNG);
    }

    public static void setGoogleSignInAccount(GoogleSignInAccount getGoogleSignInObject){
        Hawk.put(IS_LOGIN, true);
        Hawk.put(GOOGLE_SIGN_IN_OBJECT, getGoogleSignInObject);
    }

    public static GoogleSignInAccount getGoogleSignInObject() {
        return Hawk.get(GOOGLE_SIGN_IN_OBJECT, null);
    }

}
