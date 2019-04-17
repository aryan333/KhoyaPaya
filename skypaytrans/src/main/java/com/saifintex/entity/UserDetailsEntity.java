package com.saifintex.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "UserDetails")
public class UserDetailsEntity extends BaseEntity implements Serializable {

	/**
	 * user related details are defined here
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "UserDetailsId")
	private int id;		

	@Column(name ="AadhaarCardNumber" ,length = 12)
	private String aadhaarNumber;
	
	@Column(name="EmailId", length = 50)
	private String emailId;	
	
	@Column(name="Address1" , length = 30)
	private String address1;
	
	@Column(name="Address2", length = 30)
	private String address2;
	
	@Column(name="CityId", length = 10,nullable=true)
	private int cityId;
	
	@Column(name="StateId", length = 10,nullable=true)
	private int stateId;
	
	@Column(name="CountryId", length = 10,nullable=true)
	private int countryId;
	
	@Column(name="Latitude", length = 60)
	private String latitude;
	
	@Column(name="Longitude", length = 60)
	private String longitude;

	@Column(name="MobileNumber1", length = 15)
	private String mobileNumber1;
	
	@Column(name="MobileNumber2", length = 15)
	private String mobileNumber2;
	
	@Column(name="StarValue",columnDefinition="Decimal(3,2) default '1.00'")
	private BigDecimal starValue;
	
	@Column(name="Gender",columnDefinition="varchar(1) default 'N'")
	private String gender;
	
	public BigDecimal getStarValue() {
		return starValue;
	}

	public void setStarValue(BigDecimal starValue) {
		this.starValue = starValue;
	}

	@Temporal(TemporalType.DATE)
	@Column(name="DOB")
	private Date dob;
		
	@Column(name="Enterprise")
	private String enterprise;
	@Column(name="NatureOfBusiness")
	private String natureOfBusiness;
	
	@Column(name="Brands")
	private String brands;
	
	@Column(name="ShopBlobId")
	private String shopBlobId;
	@Column(name="ShopArea")
	private String shopArea;
	
	@Column(name="InstallationStatus")
	private String installationStatus;
	@Column(name="InstallationMedium")
	private String installationMedium;
	
	@Column(name="DeviceModel")
	private String deviceModel;
	
	
	@Column(name="SalesPersonLatitude", length = 60)
	private String salesPersonlatitude;
	
	@Column(name="SalesPersonLongitude", length = 60)
	private String salesPersonlongitude;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
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

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
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
