package com.selada.invesproperti.api;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    String BASE_URL = "http://superfriends-api.ap-southeast-1.elasticbeanstalk.com/api/";
    String BASE_URL_CREATE_DEVICE_BANDUNG = "https://www.seladasegar.com/wp-admin/";
//    String BASE_URL_CREATE_DEVICE_BANDUNG = "https://bandung.seladasegar.com/wp-admin/";
    String BASE_URL_CREATE_DEVICE_BELITUNG = "https://belitung.seladasegar.com/wp-admin/";
    String BASE_URL_CREATE_DEVICE_CILEGON = "https://cilegon.seladasegar.com/wp-admin/";
    String BASE_URL_CREATE_DEVICE_BEKASI = "https://bekasi.seladasegar.com/wp-admin/";
    String BASE_URL_CREATE_DEVICE_TANGERANG = "https://tangerang.seladasegar.com/wp-admin/";
    String BASE_URL_CREATE_DEVICE_KARAWANG = "https://karawang.seladasegar.com/wp-admin/";
    String BASE_URL_CREATE_DEVICE_CIREBON = "https://cirebon.seladasegar.com/wp-admin/";

    String BASE_URL_SELADA_SEGAR = "https://www.seladasegar.com/wp-json/wc/";
    String BASE_URL_SELADA_SEGAR_BANDUNG = "https://www.seladasegar.com/wp-json/wc/";
//    String BASE_URL_SELADA_SEGAR_BANDUNG = "https://bandung.seladasegar.com/wp-json/wc/";
    String BASE_URL_SELADA_SEGAR_KARAWANG = "https://karawang.seladasegar.com/wp-json/wc/";
    String BASE_URL_SELADA_SEGAR_BELITUNG = "https://belitung.seladasegar.com/wp-json/wc/";
    String BASE_URL_SELADA_SEGAR_CILEGON = "https://cilegon.seladasegar.com/wp-json/wc/";
    String BASE_URL_SELADA_SEGAR_BEKASI = "https://bekasi.seladasegar.com/wp-json/wc/";
    String BASE_URL_SELADA_SEGAR_TANGERANG = "https://tangerang.seladasegar.com/wp-json/wc/";
    String BASE_URL_SELADA_SEGAR_CIREBON = "https://cirebon.seladasegar.com/wp-json/wc/";


    String BASE_URL_SLIDER_SELADA_SEGAR_BANDUNG = "https://www.seladasegar.com/wp-content/uploads";
    String BASE_URL_SLIDER_SELADA_SEGAR_KARAWANG = "https://karawang.seladasegar.com/wp-content/uploads";
    String BASE_URL_SLIDER_SELADA_SEGAR_BELITUNG = "https://belitung.seladasegar.com/wp-content/uploads";
    String BASE_URL_SLIDER_SELADA_SEGAR_CILEGON = "https://cilegon.seladasegar.com/wp-content/uploads";
    String BASE_URL_SLIDER_SELADA_SEGAR_BEKASI = "https://bekasi.seladasegar.com/wp-content/uploads";
    String BASE_URL_SLIDER_SELADA_SEGAR_TANGERANG = "https://tangerang.seladasegar.com/wp-content/uploads";
    String BASE_URL_SLIDER_SELADA_SEGAR_CIREBON = "https://cirebon.seladasegar.com/wp-content/uploads";

    String consumer_key_seladasegar = "ck_71dcb4eed4566bf18a42dc32188d6bb55882aee5";
    String consumer_secret_seladasegar = "cs_fba4304ffce1a95ed51f0aa92527df7786b2bdda";
    String consumer_key_seladasegarbandung = "ck_d04d96b66ab13a3d4cc2388e88966b6ea74ac9d5";
    String consumer_secret_seladasegarbandung = "cs_6601bed02e3e4ca88cd9beb6b397d758620b7d60";
    String consumer_key_seladasegarkarawang = "ck_5e2750d4472f3830c5c8f3504499345f7cd7a82b";
    String consumer_secret_seladasegarkarawang = "cs_bd0d2f06fe148e2f7c9fcaf3e4edde884eac4d45";
//    String consumer_key_seladasegarbandung = "ck_5e2750d4472f3830c5c8f3504499345f7cd7a82b";
//    String consumer_secret_seladasegarbandung = "cs_bd0d2f06fe148e2f7c9fcaf3e4edde884eac4d45";


}
