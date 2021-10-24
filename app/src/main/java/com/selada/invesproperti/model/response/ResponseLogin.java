package com.selada.invesproperti.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("accessTokenValidDate")
    @Expose
    private String accessTokenValidDate;
    @SerializedName("refreshToken")
    @Expose
    private String refreshToken;
    @SerializedName("refreshTokenValidDate")
    @Expose
    private String refreshTokenValidDate;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;
    @SerializedName("isInvestor")
    @Expose
    private boolean isInvestor;
    @SerializedName("isProjectOwner")
    @Expose
    private boolean isProjectOwner;
    @SerializedName("status")
    @Expose
    private String status;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshTokenValidDate() {
        return refreshTokenValidDate;
    }

    public void setRefreshTokenValidDate(String refreshTokenValidDate) {
        this.refreshTokenValidDate = refreshTokenValidDate;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isInvestor() {
        return isInvestor;
    }

    public void setInvestor(boolean investor) {
        isInvestor = investor;
    }

    public boolean isProjectOwner() {
        return isProjectOwner;
    }

    public void setProjectOwner(boolean projectOwner) {
        isProjectOwner = projectOwner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
