package com.selada.invesproperti.api;

import com.selada.invesproperti.model.request.ChangePassRequest;
import com.selada.invesproperti.model.request.PaymentRequest;
import com.selada.invesproperti.model.request.RequestLogin;
import com.selada.invesproperti.model.request.RequestRefreshToken;
import com.selada.invesproperti.model.request.RequestRegister;
import com.selada.invesproperti.model.request.RequestUpdatePhone;
import com.selada.invesproperti.model.response.ApiResponse;
import com.selada.invesproperti.model.response.Bank;
import com.selada.invesproperti.model.response.City;
import com.selada.invesproperti.model.response.Country;
import com.selada.invesproperti.model.response.Education;
import com.selada.invesproperti.model.response.FundSources;
import com.selada.invesproperti.model.response.InvestmentGoal;
import com.selada.invesproperti.model.response.Occupation;
import com.selada.invesproperti.model.response.Province;
import com.selada.invesproperti.model.response.ResponseLogin;
import com.selada.invesproperti.model.response.ResponseProjects;
import com.selada.invesproperti.model.response.ResponseRefreshToken;
import com.selada.invesproperti.model.response.ResponseRegister;
import com.selada.invesproperti.model.response.ResponseUserProfile;
import com.selada.invesproperti.model.response.ResponseUserVerification;
import com.selada.invesproperti.model.response.detailproject.ResponseDetailProject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

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

    @POST("applicationuser/verification")
    Call<ResponseBody> doUserVerification(@Body RequestBody requestBody, @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @POST("user/refreshtoken")
    Call<ResponseRefreshToken> doRefreshToken(@Body RequestRefreshToken requestRefreshToken);

    @GET("education")
    Call<List<Education>> getListEducation(@Header("Authorization") String token);

    @GET("occupation")
    Call<List<Occupation>> getListOccupation(@Header("Authorization") String token);

    @GET("country")
    Call<List<Country>> getListCountry(@Header("Authorization") String token);

    @GET("province")
    Call<List<Province>> getListProvince(@Header("Authorization") String token);

    @GET("city")
    Call<List<City>> getListCity(@Header("Authorization") String token);

    @GET("fundsource")
    Call<List<FundSources>> getListFundSource(@Header("Authorization") String token);

    @GET("investmentgoal")
    Call<List<InvestmentGoal>> getListInvestmenGoal(@Header("Authorization") String token);

    @GET("bank")
    Call<List<Bank>> getListBank(@Header("Authorization") String token);

    @GET("project")
    Call<List<ResponseProjects>> getListProjects(@Header("Authorization") String token);

    @GET("project/{id}")
    Call<ResponseDetailProject> getDetailProject(@Path("id") String id, @Header("Authorization") String token);

    @GET("applicationuser/userprofile")
    Call<ResponseUserProfile> getUserProfile(@Header("Authorization") String token);

    @POST("crowdfunding")
    Call<ResponseBody> createPayment(@Body PaymentRequest paymentRequest, @Header("Authorization") String token);

    @POST("user/changepassword")
    Call<ResponseBody> changePass(@Body ChangePassRequest changePassRequest, @Header("Authorization") String token);

    @POST("applicationuser/updateavatar")
    Call<ResponseBody> updateProfilePicture(@Body RequestBody requestBody, @Header("Authorization") String token);

    @Streaming
    @GET("project/prospectus/{id}")
    Call<ResponseBody> getProspectus(@Path("id") String id, @Header("Authorization") String token);

    @PUT("applicationuser")
    Call<ResponseBody> updatePhoneNumber(@Body RequestUpdatePhone requestUpdatePhone, @Header("Authorization") String token);
}
