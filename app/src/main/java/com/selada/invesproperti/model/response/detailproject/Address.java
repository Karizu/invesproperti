
package com.selada.invesproperti.model.response.detailproject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.selada.invesproperti.model.response.City;
import com.selada.invesproperti.model.response.Province;

public class Address {

    @SerializedName("cifId")
    @Expose
    private String cifId;
    @SerializedName("cif")
    @Expose
    private String cif;
    @SerializedName("projectId")
    @Expose
    private String projectId;
//    @SerializedName("project")
//    @Expose
//    private Object project;
//    @SerializedName("companyId")
//    @Expose
//    private Object companyId;
//    @SerializedName("company")
//    @Expose
//    private Object company;
    @SerializedName("countryId")
    @Expose
    private String countryId;
//    @SerializedName("country")
//    @Expose
//    private Object country;
    @SerializedName("provinceId")
    @Expose
    private String provinceId;
    @SerializedName("province")
    @Expose
    private Province province;
    @SerializedName("cityId")
    @Expose
    private String cityId;
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("subDistrict")
    @Expose
    private String subDistrict;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("postalCode")
    @Expose
    private String postalCode;
//    @SerializedName("rt")
//    @Expose
//    private Object rt;
//    @SerializedName("rw")
//    @Expose
//    private Object rw;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("modifiedDate")
    @Expose
    private String modifiedDate;
    @SerializedName("modifiedBy")
    @Expose
    private String modifiedBy;

    public String getCifId() {
        return cifId;
    }

    public void setCifId(String cifId) {
        this.cifId = cifId;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
