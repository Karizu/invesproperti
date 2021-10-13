package com.selada.invesproperti.api;

import com.selada.invesproperti.model.request.RequestLogin;
import com.selada.invesproperti.model.request.RequestRegister;
import com.selada.invesproperti.model.response.ApiResponse;
import com.selada.invesproperti.model.response.ResponseLogin;
import com.selada.invesproperti.model.response.ResponseRegister;
import com.selada.invesproperti.model.response.ResponseUserVerification;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    String BASE_URL_PROD = "http://128.199.233.153:8000/api/";
    String BASE_URL_DEV = "http://128.199.233.153:8000/api/";
    String BASE_URL = BASE_URL_DEV;

    @Headers("Content-Type: application/json")
    @POST("user/register")
    Call<ApiResponse<ResponseRegister>> doRegister(@Body RequestRegister requestRegister);

    @Headers("Content-Type: application/json")
    @POST("user/signin")
    Call<ResponseLogin> doSignIn(@Body RequestLogin requestLogin);


    @Headers("Content-Type: application/json")
    @POST("applicationuser/verification")
    Call<ApiResponse<ResponseUserVerification>> doUserVerification(@Body RequestBody requestBody);
}
