package com.selada.invesproperti.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePassRequest {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("oldPassword")
    @Expose
    private String oldPassword;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("rePassword")
    @Expose
    private String rePassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
}
