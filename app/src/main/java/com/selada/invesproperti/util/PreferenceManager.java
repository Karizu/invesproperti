package com.selada.invesproperti.util;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.orhanobut.hawk.Hawk;
import com.selada.invesproperti.model.UserVerification;
import com.selada.invesproperti.model.response.ResponseLogin;
import com.selada.invesproperti.model.response.ResponseUserProfile;


import java.util.List;

/**
 * Created by Dhimas on 10/9/17.
 */

public class PreferenceManager {

    private static final String SESSION_TOKEN = "sessionToken";
    private static final String SESSION_TOKEN_ARDI = "sessionTokenArdi";
    private static final String IS_LOGIN = "isLogin";
    private static final String USER_STATUS = "userStatus";
    private static final String GOOGLE_SIGN_IN_OBJECT = "googleSignInObject";
    private static final String FACEBOOK_SIGN_IN_OBJECT = "facebookSignInObject";
    private static final String LOGIN_FROM = "loginFrom";
    private static final String FULLNAME = "fullname";
    private static final String EMAIL = "email";
    private static final String IS_FINGER_ACTIVE = "isFingerActive";
    private static final String IS_ALREADY_QUESIONER = "isAlreadyQuesioner";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String LOGIN_RESPONSE = "loginResponse";
    private static final String USER_VERIFICATION = "userVerfication";
    private static final String IS_SAVE_VERIFICATION_DATA = "isSaveVerficationData";
    private static final String IS_UNAUTHORIZED = "isUnauthorized";
    private static final String USER_PROFILE = "userProfile";

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

    public static void setSessionToken(String sessionToken){
        Hawk.put(SESSION_TOKEN, sessionToken);
    }

    public static String getSessionToken() {
        return Hawk.get(SESSION_TOKEN, "");
    }

    public static boolean isLogin(){
        return Hawk.get(IS_LOGIN, false);
    }

    public static void logOut() {
        Hawk.put(IS_LOGIN, false);
        Hawk.delete(USER_STATUS);
        Hawk.delete(IS_UNAUTHORIZED);
        Hawk.delete(IS_SAVE_VERIFICATION_DATA);
        Hawk.delete(IS_FINGER_ACTIVE);
        Hawk.delete(SESSION_TOKEN);
        Hawk.delete(IS_ALREADY_QUESIONER);
        Hawk.delete(FACEBOOK_SIGN_IN_OBJECT);
        Hawk.delete(GOOGLE_SIGN_IN_OBJECT);
        Hawk.delete(USER_VERIFICATION);
        Hawk.delete(LOGIN_RESPONSE);
        Hawk.delete(LOGIN_FROM);
        Hawk.delete(USER_PROFILE);
    }

    public static void setIsAlreadyQuesioner(boolean isAlreadyQuesioner){
        Hawk.put(IS_ALREADY_QUESIONER, isAlreadyQuesioner);
    }

    public static boolean isAlreadyQuesioner() {
        return Hawk.get(IS_ALREADY_QUESIONER, false);
    }

    public static void setUserStatus(String userStatus){
        Hawk.put(USER_STATUS, userStatus);
    }

    public static String getUserStatus() {
        return Hawk.get(USER_STATUS, Constant.GUEST);
    }

    public static boolean setFirstTimeLaunch(boolean isFirstTime) {
        return Hawk.put(IS_FIRST_TIME_LAUNCH, isFirstTime);
    }

    public static boolean isFirstTimeLaunch() {
        return Hawk.get(IS_FIRST_TIME_LAUNCH, true);
    }

    public static void setGoogleSignInAccount(GoogleSignInAccount getGoogleSignInObject, String loginFrom){
        Hawk.put(IS_LOGIN, true);
        Hawk.put(GOOGLE_SIGN_IN_OBJECT, getGoogleSignInObject);
        Hawk.put(LOGIN_FROM, loginFrom);
    }

    public static GoogleSignInAccount getGoogleSignInObject() {
        return Hawk.get(GOOGLE_SIGN_IN_OBJECT, null);
    }

    public static void setFacebookSignInAccount(FirebaseUser firebaseUser, String loginFrom){
        Hawk.put(IS_LOGIN, true);
        Hawk.put(FACEBOOK_SIGN_IN_OBJECT, firebaseUser);
        Hawk.put(LOGIN_FROM, loginFrom);
    }

    public static FirebaseUser getFacebookSignInObject() {
        return Hawk.get(FACEBOOK_SIGN_IN_OBJECT, null);
    }

    public static String getLoginFrom(){
        return Hawk.get(LOGIN_FROM, "");
    }

    public static void setLoginData(String fullname, String email){
        Hawk.put(FULLNAME, fullname);
        Hawk.put(EMAIL,email);
    }

    public static String getFullname(){
        return Hawk.get(FULLNAME, "");
    }

    public static String getEmail(){
        return Hawk.get(EMAIL, "");
    }

    public static void setIsFingerActive(boolean isActive){
        Hawk.put(IS_FINGER_ACTIVE, isActive);
    }

    public static boolean getFingerActive(){
        return Hawk.get(IS_FINGER_ACTIVE, false);
    }


    public static void setLoginResponse(ResponseLogin loginResponse, String loginFrom){
        Hawk.put(IS_LOGIN, true);
        Hawk.put(LOGIN_RESPONSE, loginResponse);
        Hawk.put(LOGIN_FROM, loginFrom);
    }

    public static ResponseLogin getLoginResponse(){
        return Hawk.get(LOGIN_RESPONSE, null);
    }

    public static void setUserVerification(UserVerification userVerification){
        Hawk.put(USER_VERIFICATION, userVerification);
    }

    public static UserVerification getUserVerification(){
        return Hawk.get(USER_VERIFICATION, new UserVerification());
    }


    public static void setIsSaveVerificationData(boolean isSave){
        Hawk.put(IS_SAVE_VERIFICATION_DATA, isSave);
    }

    public static boolean getIsSaveVerificationData(){
        return Hawk.get(IS_SAVE_VERIFICATION_DATA, false);
    }


    public static void setIsUnauthorized(boolean isUnauthorized){
        Hawk.put(IS_UNAUTHORIZED, isUnauthorized);
    }

    public static boolean getIsUnauthorized(){
        return Hawk.get(IS_UNAUTHORIZED, false);
    }

    public static void setResponseUserProfile(ResponseUserProfile responseUserProfile){
        Hawk.put(USER_PROFILE, responseUserProfile);
    }

    public static ResponseUserProfile getUserProfile() {
        return Hawk.get(USER_PROFILE);
    }
}
