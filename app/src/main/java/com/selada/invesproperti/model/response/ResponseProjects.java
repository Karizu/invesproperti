package com.selada.invesproperti.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseProjects {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("fundingAmount")
    @Expose
    private Integer fundingAmount;
    @SerializedName("requestedAmount")
    @Expose
    private Integer requestedAmount;
    @SerializedName("pricePerLot")
    @Expose
    private Integer pricePerLot;
    @SerializedName("totalLot")
    @Expose
    private Integer totalLot;
    @SerializedName("dividenPeriod")
    @Expose
    private String dividenPeriod;
    @SerializedName("dividenEstimation")
    @Expose
    private String interestRate;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("deadlineDate")
    @Expose
    private String deadlineDate;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("picture")
    @Expose
    private List<String> picture;

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

    public Integer getFundingAmount() {
        return fundingAmount;
    }

    public void setFundingAmount(Integer fundingAmount) {
        this.fundingAmount = fundingAmount;
    }

    public Integer getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(Integer requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Integer getPricePerLot() {
        return pricePerLot;
    }

    public void setPricePerLot(Integer pricePerLot) {
        this.pricePerLot = pricePerLot;
    }

    public Integer getTotalLot() {
        return totalLot;
    }

    public void setTotalLot(Integer totalLot) {
        this.totalLot = totalLot;
    }

    public String getDividenPeriod() {
        return dividenPeriod;
    }

    public void setDividenPeriod(String dividenPeriod) {
        this.dividenPeriod = dividenPeriod;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
