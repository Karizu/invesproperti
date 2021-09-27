package com.selada.invesproperti.util;

import android.hardware.fingerprint.FingerprintManager;

public interface FingerPrintAuthCallback {

    void onAuthSuccess(FingerprintManager.AuthenticationResult result);

    void onAuthFailed();
}