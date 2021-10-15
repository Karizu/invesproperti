package com.selada.invesproperti.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseRefreshToken {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("accessTokenValidDate")
    @Expose
    private String accessTokenValidDate;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessTokenValidDate() {
        return accessTokenValidDate;
    }

    public void setAccessTokenValidDate(String accessTokenValidDate) {
        this.accessTokenValidDate = accessTokenValidDate;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}