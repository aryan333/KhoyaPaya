package com.javatechie.spring.orm.api.model;





import com.javatechie.spring.orm.api.dto.ProductTypeDTO;
import com.javatechie.spring.orm.api.dto.StateDTO;
import com.javatechie.spring.orm.api.dto.UserDto;

public class ProductFound {
	
	private int productFindId;
	private String productName;
	private ProductType productType;
	private String country;
	private State state;
	private String locationName;
	private String productfindtime;
	private User user;
	
	public ProductFound(){
		
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
	public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	
	
	
	

}
