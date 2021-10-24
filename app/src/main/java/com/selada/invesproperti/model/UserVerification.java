package com.selada.invesproperti.model;

public class UserVerification {
    private boolean IsInvestor;
    private boolean IsProjectOwner;
    private String Name;
    private String Gender;
    private String Birthplace;
    private String BirthDate;
    private String EducationId;
    private String OccupationId;
    private String MaritalStatus;
    private String SpouseName;
    private String Phone;
    private String Email;
    private String AddressIdCard;
    private String CityIdCard;
    private String DistrictIdCard;
    private String SubDistrictIdCard;
    private String PostalCodeIdCard;
    private boolean IsDomicileSameWithIdCard;
    private String AddressDomicile;
    private String CityDomicile;
    private String DistrictDomicile;
    private String SubDistrictDomicile;
    private String PostalCodeDomicile;
    private String IdCardNumber;
//TaxCardNumber;
    private String MotherName;
    private String HeirName;
    private String Relation;
    private String YearlyIncome;
    private String FundSourceId;
    private String InvestmentGoalId;
    private boolean HasSecuritiesAccount;
    private String Password;
    private String BankId;
    private String AccountName;
    private String AccountNumber;
    private byte[] PhotoKtp;
    private byte[] photoSelfie;

    public boolean isInvestor() {
        return IsInvestor;
    }

    public void setInvestor(boolean investor) {
        IsInvestor = investor;
    }

    public boolean isProjectOwner() {
        return IsProjectOwner;
    }

    public void setProjectOwner(boolean projectOwner) {
        IsProjectOwner = projectOwner;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBirthplace() {
        return Birthplace;
    }

    public void setBirthplace(String birthplace) {
        Birthplace = birthplace;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getEducationId() {
        return EducationId;
    }

    public void setEducationId(String educationId) {
        EducationId = educationId;
    }

    public String getOccupationId() {
        return OccupationId;
    }

    public void setOccupationId(String occupationId) {
        OccupationId = occupationId;
    }

    public String getMaritalStatus() {
        return MaritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        MaritalStatus = maritalStatus;
    }

    public String getSpouseName() {
        return SpouseName;
    }

    public void setSpouseName(String spouseName) {
        SpouseName = spouseName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddressIdCard() {
        return AddressIdCard;
    }

    public void setAddressIdCard(String addressIdCard) {
        AddressIdCard = addressIdCard;
    }

    public String getCityIdCard() {
        return CityIdCard;
    }

    public void setCityIdCard(String cityIdCard) {
        CityIdCard = cityIdCard;
    }

    public String getDistrictIdCard() {
        return DistrictIdCard;
    }

    public void setDistrictIdCard(String districtIdCard) {
        DistrictIdCard = districtIdCard;
    }

    public String getSubDistrictIdCard() {
        return SubDistrictIdCard;
    }

    public void setSubDistrictIdCard(String subDistrictIdCard) {
        SubDistrictIdCard = subDistrictIdCard;
    }

    public String getPostalCodeIdCard() {
        return PostalCodeIdCard;
    }

    public void setPostalCodeIdCard(String postalCodeIdCard) {
        PostalCodeIdCard = postalCodeIdCard;
    }

    public boolean getIsDomicileSameWithIdCard() {
        return IsDomicileSameWithIdCard;
    }

    public void setIsDomicileSameWithIdCard(boolean isDomicileSameWithIdCard) {
        IsDomicileSameWithIdCard = isDomicileSameWithIdCard;
    }

    public String getIdCardNumber() {
        return IdCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        IdCardNumber = idCardNumber;
    }

    public String getMotherName() {
        return MotherName;
    }

    public void setMotherName(String motherName) {
        MotherName = motherName;
    }

    public String getHeirName() {
        return HeirName;
    }

    public void setHeirName(String heirName) {
        HeirName = heirName;
    }

    public String getRelation() {
        return Relation;
    }

    public void setRelation(String relation) {
        Relation = relation;
    }

    public String getYearlyIncome() {
        return YearlyIncome;
    }

    public void setYearlyIncome(String yearlyIncome) {
        YearlyIncome = yearlyIncome;
    }

    public String getFundSourceId() {
        return FundSourceId;
    }

    public void setFundSourceId(String fundSourceId) {
        FundSourceId = fundSourceId;
    }

    public String getInvestmentGoalId() {
        return InvestmentGoalId;
    }

    public void setInvestmentGoalId(String investmentGoalId) {
        InvestmentGoalId = investmentGoalId;
    }

    public boolean getHasSecuritiesAccount() {
        return HasSecuritiesAccount;
    }

    public void setHasSecuritiesAccount(boolean hasSecuritiesAccount) {
        HasSecuritiesAccount = hasSecuritiesAccount;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getBankId() {
        return BankId;
    }

    public void setBankId(String bankId) {
        BankId = bankId;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public byte[] getPhotoKtp() {
        return PhotoKtp;
    }

    public void setPhotoKtp(byte[] photoKtp) {
        PhotoKtp = photoKtp;
    }

    public byte[] getPhotoSelfie() {
        return photoSelfie;
    }

    public void setPhotoSelfie(byte[] photoSelfie) {
        this.photoSelfie = photoSelfie;
    }

    public boolean isDomicileSameWithIdCard() {
        return IsDomicileSameWithIdCard;
    }

    public void setDomicileSameWithIdCard(boolean domicileSameWithIdCard) {
        IsDomicileSameWithIdCard = domicileSameWithIdCard;
    }

    public String getAddressDomicile() {
        return AddressDomicile;
    }

    public void setAddressDomicile(String addressDomicile) {
        AddressDomicile = addressDomicile;
    }

    public String getCityDomicile() {
        return CityDomicile;
    }

    public void setCityDomicile(String cityDomicile) {
        CityDomicile = cityDomicile;
    }

    public String getDistrictDomicile() {
        return DistrictDomicile;
    }

    public void setDistrictDomicile(String districtDomicile) {
        DistrictDomicile = districtDomicile;
    }

    public String getSubDistrictDomicile() {
        return SubDistrictDomicile;
    }

    public void setSubDistrictDomicile(String subDistrictDomicile) {
        SubDistrictDomicile = subDistrictDomicile;
    }

    public String getPostalCodeDomicile() {
        return PostalCodeDomicile;
    }

    public void setPostalCodeDomicile(String postalCodeDomicile) {
        PostalCodeDomicile = postalCodeDomicile;
    }

    public boolean isHasSecuritiesAccount() {
        return HasSecuritiesAccount;
    }
}
