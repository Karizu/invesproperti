package com.selada.invesproperti.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.selada.invesproperti.model.response.detailproject.Address;

public class ResponseUserProfile {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("idCardNumber")
    @Expose
    private String idCardNumber;
    @SerializedName("yearlyIncome")
    @Expose
    private Integer yearlyIncome;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("isInvestor")
    @Expose
    private boolean isInvestor;
    @SerializedName("isProjectOwner")
    @Expose
    private boolean isProjectOwner;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public Integer getYearlyIncome() {
        return yearlyIncome;
    }

    public void setYearlyIncome(Integer yearlyIncome) {
        this.yearlyIncome = yearlyIncome;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
