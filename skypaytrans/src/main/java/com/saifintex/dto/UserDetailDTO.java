package com.saifintex.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

public class UserDetailDTO extends BaseDto {

	private int id;
	private String aadhaarNumber;
	private String emailId;
	private Date dob;
	private String address1;
	private String address2;
	private int cityId;
	private int stateId;
	private int countryId;
	private String latitude;
	private String longitude;
	private String mobileNumber1;
	private String mobileNumber2;
	private String blobId;
	private String promocode;
	private String contactSupport;
	private BigDecimal starValue;
	private String gender;
private String enterprise;
private String natureOfBusiness;
private String brands;
private String shopBlobId;
private String shopArea;
private String installationStatus;
private String installationMedium;
private String deviceModel;


private String salesPersonlatitude;


private String salesPersonlongitude;
	public BigDecimal getStarValue() {
		return starValue;
	}

	public void setStarValue(BigDecimal starValue) {
		this.starValue = starValue;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAadhaarNumber() {
		return aadhaarNumber;
	}

	public void setAadhaarNumber(String aadhaarNumber) {
		this.aadhaarNumber = aadhaarNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public String getMobileNumber1() {
		return mobileNumber1;
	}

	public void setMobileNumber1(String mobileNumber1) {
		this.mobileNumber1 = mobileNumber1;
	}

	public String getMobileNumber2() {
		return mobileNumber2;
	}

	public void setMobileNumber2(String mobileNumber2) {
		this.mobileNumber2 = mobileNumber2;
	}

	public String getBlobId() {
		return blobId;
	}

	public void setBlobId(String blobId) {
		this.blobId = blobId;
	}

	public String getPromocode() {
		return promocode;
	}

	public void setPromocode(String promocode) {
		this.promocode = promocode;
	}

	public String getContactSupport() {
		return contactSupport;
	}

	public void setContactSupport(String contactSupport) {
		this.contactSupport = contactSupport;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}

	public String getNatureOfBusiness() {
		return natureOfBusiness;
	}

	public void setNatureOfBusiness(String natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}

	public String getBrands() {
		return brands;
	}

	public void setBrands(String brands) {
		this.brands = brands;
	}

	public String getShopBlobId() {
		return shopBlobId;
	}

	public void setShopBlobId(String shopBlobId) {
		this.shopBlobId = shopBlobId;
	}

	public String getShopArea() {
		return shopArea;
	}

	public void setShopArea(String shopArea) {
		this.shopArea = shopArea;
	}

	public String getInstallationStatus() {
		return installationStatus;
	}

	public void setInstallationStatus(String installationStatus) {
		this.installationStatus = installationStatus;
	}

	public String getInstallationMedium() {
		return installationMedium;
	}

	public void setInstallationMedium(String installationMedium) {
		this.installationMedium = installationMedium;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getSalesPersonlatitude() {
		return salesPersonlatitude;
	}

	public void setSalesPersonlatitude(String salesPersonlatitude) {
		this.salesPersonlatitude = salesPersonlatitude;
	}

	public String getSalesPersonlongitude() {
		return salesPersonlongitude;
	}

	public void setSalesPersonlongitude(String salesPersonlongitude) {
		this.salesPersonlongitude = salesPersonlongitude;
	}

}
