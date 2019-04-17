package com.saifintex.web.dto;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.saifintex.dto.BaseDto;

public class MerchantInfoBySPDTO  extends BaseDto{
	
	private String name;
	private String mobileNumber;
	private String enterprise;
	private String natureOfBusiness;
	private String brands;
	private String shopArea;
	private String installationStatus;
	private String installationMedium;
	private String deviceModel;
	private CommonsMultipartFile file;
	
	private String shopBlobId;

	private String salesPersonInviteCode;
	private int salesPersonId;
	
	
	private String salesPersonlatitude;
 
	
	private String salesPersonlongitude;
	
	private String salesPersonName;
	private String salesPersonMobileNumber;
	
	private String city;
	private String state;
	private boolean merchant;
   
	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public boolean isMerchant() {
		return merchant;
	}


	public void setMerchant(boolean merchant) {
		this.merchant = merchant;
	}


	public String getSalesPersonName() {
		return salesPersonName;
	}


	public void setSalesPersonName(String salesPersonName) {
		this.salesPersonName = salesPersonName;
	}


	public String getSalesPersonMobileNumber() {
		return salesPersonMobileNumber;
	}


	public void setSalesPersonMobileNumber(String salesPersonMobileNumber) {
		this.salesPersonMobileNumber = salesPersonMobileNumber;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getMobileNumber() {
		return mobileNumber;
	}


	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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


	public CommonsMultipartFile getFile() {
		return file;
	}


	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}


	public String getShopBlobId() {
		return shopBlobId;
	}


	public void setShopBlobId(String shopBlobId) {
		this.shopBlobId = shopBlobId;
	}


	public String getSalesPersonInviteCode() {
		return salesPersonInviteCode;
	}


	public void setSalesPersonInviteCode(String salesPersonInviteCode) {
		this.salesPersonInviteCode = salesPersonInviteCode;
	}


	public int getSalesPersonId() {
		return salesPersonId;
	}


	public void setSalesPersonId(int salesPersonId) {
		this.salesPersonId = salesPersonId;
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
