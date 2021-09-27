package com.selada.invesproperti.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import com.google.gson.Gson;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

@TargetApi(Build.VERSION_CODES.M)
public class FingerPrintAuthHelper extends FingerprintManager.AuthenticationCallback {
    private static final String KEY_NAME = "example_key";
    private KeyStore mKeyStore;
    private Cipher mCipher;

    private Context mContext;
    private FingerPrintAuthCallback mCallback;


    public FingerPrintAuthHelper(Context context, FingerPrintAuthCallback callback) {
        mCallback = callback;
        mContext = context;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            throw new RuntimeException("Finger authentication requires API 23 or above.");
    }

    @SuppressWarnings({"RedundantIfStatement"})
    public static boolean isFingerPrintSupported(Context context) {
        // Check if we're running on Android 6.0 (M) or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //Fingerprint API only available on from Android 6.0 (M)
            FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context);

            if (!fingerprintManager.isHardwareDetected()) {
                // Device doesn't support fingerprint authentication
                return false;
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                // User hasn't enrolled any fingerprints to authenticate with
                return false;
            } else {
                // Everything is ready for fingerprint authentication
                return true;
            }
        }

        //Below API 23 fingerprint were not supported.
        return false;
    }

    @TargetApi(23)
    private boolean generateKey() {
        mKeyStore = null;
        KeyGenerator keyGenerator;

        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException |
                NoSuchProviderException e) {
            throw new RuntimeException(
                    "Failed to get KeyGenerator instance", e);
        }

        if (mKeyStore == null || keyGenerator == null) return false;

        try {
            mKeyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();

            return true;
        } catch (NoSuchAlgorithmException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException e) {
            return false;
        }
    }

    @TargetApi(23)
    private boolean cipherInit() {
        boolean isKeyGenerated = generateKey();

        if (!isKeyGenerated) return false;

        try {
            mCipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            return false;
        }

        try {
            mKeyStore.load(null);
            SecretKey key = (SecretKey) mKeyStore.getKey(KEY_NAME, null);
            mCipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            return false;
        }
    }

    @TargetApi(23)
    @Nullable
    public FingerprintManager.CryptoObject getCryptoObject() {
        return cipherInit() ? new FingerprintManager.CryptoObject(mCipher) : null;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void startAuth() {
        FingerprintManager fingerprintManager = (FingerprintManager) mContext.getSystemService(Context.FINGERPRINT_SERVICE);

        FingerprintManager.CryptoObject cryptoObject = getCryptoObject();
        Log.d("cryptoObject", new Gson().toJson(cryptoObject));
        Log.d("cryptoObject", String.valueOf(cryptoObject));

        if (cryptoObject == null) {
            mCallback.onAuthFailed();
        } else {
            //noinspection MissingPermission
            fingerprintManager.authenticate(cryptoObject, new CancellationSignal(), 0, this, null);
        }
    }

    @Override
    public void onAuthenticationError(int errMsgId,
                                      CharSequence errString) {
        mCallback.onAuthFailed();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Log.d("FingerPrint", "auth help");
    }

    @Override
    public void onAuthenticationFailed() {
        mCallback.onAuthFailed();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        mCallback.onAuthSuccess(result);
    }
}