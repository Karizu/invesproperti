package com.selada.invesproperti.model.request;

import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SubmitProductRequest {
    private String name;
    private String code;
    private String detail;
    private String latitude;
    private String longitude;
    private String businessTypeId;
    private String totalLot;
    private String pricePerLot;
    private String requestedAmount;
    private String dividenPeriod;
    private String dividenPeriodUOM;
    private String deadlineDate;
    private String prospectus;
    private String establishmentDate;
    private String establishmentDateEstimation;
    private String monthlyIncome;
    private String monthlyIncomeUOM;
    private String addressProject;
    private String rtProject;
    private String rwProject;
    private String cityIdProject;
    private String districtProject;
    private String subDistrictProject;
    private String postalCodeProject;
    private boolean isCompanySameWithProject;
    private String minDividenEstimation;
    private String maxDividenEstimation;
    private String dividenUOM;
    private String dividenAmountPeriod;
    private String dividenAmountPeriodUOM;
    private String companyName;
    private String companyType;
    private String picPosition;
    private String procuration;
    private String aktaFile;
    private String aktaNumber;
    private String taxCardFile;
    private String taxCardNumber;
    private String aHUFile;
    private String aHUNumber;
    private String bankAccountType;
    private String bankId;
    private String accountName;
    private String accountNumber;
    private List<String> photos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(String businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public String getTotalLot() {
        return totalLot;
    }

    public void setTotalLot(String totalLot) {
        this.totalLot = totalLot;
    }

    public String getPricePerLot() {
        return pricePerLot;
    }

    public void setPricePerLot(String pricePerLot) {
        this.pricePerLot = pricePerLot;
    }

    public String getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(String requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public String getDividenPeriod() {
        return dividenPeriod;
    }

    public void setDividenPeriod(String dividenPeriod) {
        this.dividenPeriod = dividenPeriod;
    }

    public String getDividenPeriodUOM() {
        return dividenPeriodUOM;
    }

    public void setDividenPeriodUOM(String dividenPeriodUOM) {
        this.dividenPeriodUOM = dividenPeriodUOM;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getProspectus() {
        return prospectus;
    }

    public void setProspectus(String prospectus) {
        this.prospectus = prospectus;
    }

    public String getEstablishmentDate() {
        return establishmentDate;
    }

    public void setEstablishmentDate(String establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public String getEstablishmentDateEstimation() {
        return establishmentDateEstimation;
    }

    public void setEstablishmentDateEstimation(String establishmentDateEstimation) {
        this.establishmentDateEstimation = establishmentDateEstimation;
    }

    public String getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(String monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getMonthlyIncomeUOM() {
        return monthlyIncomeUOM;
    }

    public void setMonthlyIncomeUOM(String monthlyIncomeUOM) {
        this.monthlyIncomeUOM = monthlyIncomeUOM;
    }

    public String getAddressProject() {
        return addressProject;
    }

    public void setAddressProject(String addressProject) {
        this.addressProject = addressProject;
    }

    public String getRtProject() {
        return rtProject;
    }

    public void setRtProject(String rtProject) {
        this.rtProject = rtProject;
    }

    public String getRwProject() {
        return rwProject;
    }

    public void setRwProject(String rwProject) {
        this.rwProject = rwProject;
    }

    public String getCityIdProject() {
        return cityIdProject;
    }

    public void setCityIdProject(String cityIdProject) {
        this.cityIdProject = cityIdProject;
    }

    public String getDistrictProject() {
        return districtProject;
    }

    public void setDistrictProject(String districtProject) {
        this.districtProject = districtProject;
    }

    public String getSubDistrictProject() {
        return subDistrictProject;
    }

    public void setSubDistrictProject(String subDistrictProject) {
        this.subDistrictProject = subDistrictProject;
    }

    public String getPostalCodeProject() {
        return postalCodeProject;
    }

    public void setPostalCodeProject(String postalCodeProject) {
        this.postalCodeProject = postalCodeProject;
    }

    public boolean isCompanySameWithProject() {
        return isCompanySameWithProject;
    }

    public void setCompanySameWithProject(boolean companySameWithProject) {
        isCompanySameWithProject = companySameWithProject;
    }

    public String getMinDividenEstimation() {
        return minDividenEstimation;
    }

    public void setMinDividenEstimation(String minDividenEstimation) {
        this.minDividenEstimation = minDividenEstimation;
    }

    public String getMaxDividenEstimation() {
        return maxDividenEstimation;
    }

    public void setMaxDividenEstimation(String maxDividenEstimation) {
        this.maxDividenEstimation = maxDividenEstimation;
    }

    public String getDividenUOM() {
        return dividenUOM;
    }

    public void setDividenUOM(String dividenUOM) {
        this.dividenUOM = dividenUOM;
    }

    public String getDividenAmountPeriod() {
        return dividenAmountPeriod;
    }

    public void setDividenAmountPeriod(String dividenAmountPeriod) {
        this.dividenAmountPeriod = dividenAmountPeriod;
    }

    public String getDividenAmountPeriodUOM() {
        return dividenAmountPeriodUOM;
    }

    public void setDividenAmountPeriodUOM(String dividenAmountPeriodUOM) {
        this.dividenAmountPeriodUOM = dividenAmountPeriodUOM;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getPicPosition() {
        return picPosition;
    }

    public void setPicPosition(String picPosition) {
        this.picPosition = picPosition;
    }

    public String getProcuration() {
        return procuration;
    }

    public void setProcuration(String procuration) {
        this.procuration = procuration;
    }

    public String getAktaFile() {
        return aktaFile;
    }

    public void setAktaFile(String aktaFile) {
        this.aktaFile = aktaFile;
    }

    public String getAktaNumber() {
        return aktaNumber;
    }

    public void setAktaNumber(String aktaNumber) {
        this.aktaNumber = aktaNumber;
    }

    public String getTaxCardFile() {
        return taxCardFile;
    }

    public void setTaxCardFile(String taxCardFile) {
        this.taxCardFile = taxCardFile;
    }

    public String getTaxCardNumber() {
        return taxCardNumber;
    }

    public void setTaxCardNumber(String taxCardNumber) {
        this.taxCardNumber = taxCardNumber;
    }

    public String getaHUFile() {
        return aHUFile;
    }

    public void setaHUFile(String aHUFile) {
        this.aHUFile = aHUFile;
    }

    public String getaHUNumber() {
        return aHUNumber;
    }

    public void setaHUNumber(String aHUNumber) {
        this.aHUNumber = aHUNumber;
    }

    public String getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(String bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
