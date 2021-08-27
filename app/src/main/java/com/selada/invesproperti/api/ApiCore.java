package com.selada.invesproperti.api;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.selada.invesproperti.util.PreferenceManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCore {

    @SuppressLint("StaticFieldLeak")
    public static Activity activity;

    public static <T> T builder(Class<T> endpoint) {
        new PreferenceManager(activity);
        return new Retrofit.Builder()
                .client(NetworkManager.client())
                .baseUrl(ApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(endpoint);
    }

    public static ApiInterface apiInterface() {
        return builder(ApiInterface.class);
    }

}
