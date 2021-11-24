package com.selada.invesproperti.presentation.auth;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.selada.invesproperti.R;
import com.selada.invesproperti.api.ApiCore;
import com.selada.invesproperti.model.request.RequestLogin;
import com.selada.invesproperti.model.request.RequestRegister;
import com.selada.invesproperti.model.response.ApiResponse;
import com.selada.invesproperti.model.response.ResponseLogin;
import com.selada.invesproperti.model.response.ResponseRegister;
import com.selada.invesproperti.util.Constant;
import com.selada.invesproperti.util.LoadingPost;
import com.selada.invesproperti.util.MethodUtil;
import com.selada.invesproperti.util.PreferenceManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.anshul.libray.PasswordEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.selada.invesproperti.util.MethodUtil.isValidPassword;
import static com.selada.invesproperti.util.MethodUtil.validate;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.etNamaLengkap)
    EditText etNamaLengkap;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etKataSandi)
    PasswordEditText etKataSandi;

    private Context context;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 0;
    private String TAG = "GoogleSignIn";
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;
    private LoginButton loginButton;

    @OnClick(R.id.btn_google)
    void onClickBtnGoogle() {
        signIn();
    }

    @OnClick(R.id.btn_login)
    void onClickBtnLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.btn_facebook)
    void onClickBtnFacebook() {
        loginButton.performClick();
    }

    @OnClick(R.id.btn_register)
    void onClickRegister() {
        if (etNamaLengkap.getText().toString().equals("")) {
            etNamaLengkap.setError("Nama Lengkap tidak boleh kosong");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Nama Lengkap tidak boleh kosong");
            return;
        }

        if (etEmail.getText().toString().equals("")) {
            etEmail.setError("Email tidak boleh kosong");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Email tidak boleh kosong");
            return;
        }

        String email = etEmail.getText().toString().trim();
        if (!email.matches(MethodUtil.emailPattern())) {
            etEmail.setError("Format email salah");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Format email salah");
            return;
        }

        if (etKataSandi.getText().toString().equals("")) {
            etKataSandi.setError("Kata sandi tidak boleh kosong");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Kata sandi tidak boleh kosong");
            return;
        }

        if(etKataSandi.getText().toString().length()<8 ){
            etKataSandi.setError("Kata sandi minimal 8 karakter dengan huruf besar dan angka");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Kata sandi minimal 8 karakter dengan huruf besar dan angka");
            return;
        }

        if (!isValidPassword(etKataSandi.getText().toString())) {
            etKataSandi.setError("Kata sandi minimal 8 karakter dengan huruf besar dan angka");
            MethodUtil.showSnackBar(findViewById(android.R.id.content), "Kata sandi minimal 8 karakter dengan huruf besar dan angka");
            return;
        }

        doRegister();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        context = this;
        configureGoogleSignIn();
        printHashKey();
        FacebookSdk.sdkInitialize(RegisterActivity.this);
        mAuth = FirebaseAuth.getInstance();
        initFacebookLogin();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            directToMainActivity();
        }
        mAuth.addAuthStateListener(authStateListener);
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }
    }

    private void directToMainActivity() {
        Intent intent = new Intent(this, ActivateFingerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // add this line
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        // Pass the activity result back to the Facebook SDK

    }

    private void doRegister() {
        String name = etNamaLengkap.getText().toString();
        String email = etEmail.getText().toString();
        String pass = etKataSandi.getText().toString();

        RequestRegister requestRegister = new RequestRegister();
        requestRegister.setName(name);
        requestRegister.setEmail(email);
        requestRegister.setPassword(pass);
        requestRegister.setRepassword(pass);

        LoadingPost.show(this);
        ApiCore.apiInterface().doRegister(requestRegister).enqueue(new Callback<ApiResponse<ResponseRegister>>() {
            @Override
            public void onResponse(Call<ApiResponse<ResponseRegister>> call, Response<ApiResponse<ResponseRegister>> response) {
                LoadingPost.hide(context);
                try {
                    if (response.isSuccessful()) {
                        doLogin(email, pass);
                    } else {
                        MethodUtil.getErrorMessage(response.errorBody(), RegisterActivity.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    View parentLayout = findViewById(android.R.id.content);
                    MethodUtil.showOnCatch(parentLayout);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ResponseRegister>> call, Throwable t) {
                LoadingPost.hide(context);
                t.printStackTrace();
            }
        });
    }

    private void doLogin(String email, String pass) {
        RequestLogin requestLogin = new RequestLogin();
        requestLogin.setEmail(email);
        requestLogin.setPassword(pass);

        LoadingPost.show(this);
        ApiCore.apiInterface().doSignIn(requestLogin).enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                LoadingPost.hide(RegisterActivity.this);
                try {
                    if (response.isSuccessful()) {
                        PreferenceManager.setIsUnauthorized(false);
                        PreferenceManager.setLoginResponse(response.body(), Constant.LOGIN_FROM_EMAIL);
                        PreferenceManager.setLoginData(Objects.requireNonNull(response.body()).getFullName(), response.body().getEmail());
                        PreferenceManager.setSessionToken("Bearer " + Objects.requireNonNull(response.body()).getAccessToken());

                        Intent intent = new Intent(RegisterActivity.this, RegistrationCompleteActivity.class);
                        intent.putExtra("name", response.body().getFullName());
                        startActivity(intent);
                    } else {
                        MethodUtil.getErrorMessage(response.errorBody(), RegisterActivity.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    View view = findViewById(android.R.id.content);
                    MethodUtil.showOnCatch(view);
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                t.printStackTrace();
                LoadingPost.hide(RegisterActivity.this);
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            PreferenceManager.setLoginData(Objects.requireNonNull(user).getDisplayName(), user.getEmail());
                            PreferenceManager.setFacebookSignInAccount(user, Constant.LOGIN_FROM_FACEBOOK);
                            Log.d("User FB", new Gson().toJson(user));
                            directToMainActivity();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            PreferenceManager.setLoginData(account.getDisplayName(), account.getEmail());
            PreferenceManager.setGoogleSignInAccount(account, Constant.LOGIN_FROM_GOOGLE);
            directToMainActivity();

        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "Terjadi kesalahan saat masuk akun google anda", Toast.LENGTH_SHORT).show();
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void configureGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void printHashKey() {
        try {
            @SuppressLint("PackageManagerGetSignatures") PackageInfo info
                    = getPackageManager().getPackageInfo(
                    "com.selada.invesproperti",
                    PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md
                        = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:",
                        Base64.encodeToString(
                                md.digest(),
                                Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    private void initFacebookLogin() {
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.button_sign_in_fb);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });

        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {

            } else {

            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    FirebaseAuth.getInstance().signOut();
                }
            }
        };
    }
}