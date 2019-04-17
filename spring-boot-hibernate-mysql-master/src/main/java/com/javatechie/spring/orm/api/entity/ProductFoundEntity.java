package com.javatechie.spring.orm.api.entity;



import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="ProductFound")
public class ProductFoundEntity {
	

	@Id
	@GeneratedValue
	private int productFindId;
	private String productName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "productType_id")
	private ProductTypeEntity productTypeEntity;
	private String country;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "state_id")
	private StateEntity stateEntity;
	private String locationName;
	private String productfindtime;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;
	
	
	public ProductFoundEntity(){
		
	}
	
	public String getProductfindtime() {
		return productfindtime;
	}
	public void setProductfindtime(String productfindtime) {
		this.productfindtime = productfindtime;
	}
	public int getProductFindId() {
		return productFindId;
	}
	public void setProductFindId(int productFindId) {
		this.productFindId = productFindId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public ProductTypeEntity getProductTypeEntity() {
		return productTypeEntity;
	}
	public void setProductTypeEntity(ProductTypeEntity productTypeEntity) {
		this.productTypeEntity = productTypeEntity;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public StateEntity getStateEntity() {
		return stateEntity;
	}
	public void setStateEntity(StateEntity stateEntity) {
		this.stateEntity = stateEntity;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	public UserEntity getUserEntity() {
		return userEntity;
	}
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
	

}
