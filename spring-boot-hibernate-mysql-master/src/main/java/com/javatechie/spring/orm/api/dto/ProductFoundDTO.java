package com.javatechie.spring.orm.api.dto;



public class ProductFoundDTO {
	
	private int productFindId;
	private String productName;
	private ProductTypeDTO productTypeDTO;
	private String country;
	private StateDTO stateDTO;
	private String locationName;
	private String productfindtime;
	private UserDto userDto;
	
	// yadi yaha hum StateDTo stateDTO kia badlai StateDTO state kar denai inner object copy karnai mai dikat nahi hogi
	
    public ProductFoundDTO(){
    	
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
	public ProductTypeDTO getProductTypeDTO() {
		return productTypeDTO;
	}
	public void setProductTypeDTO(ProductTypeDTO productTypeDTO) {
		this.productTypeDTO = productTypeDTO;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public StateDTO getStateDTO() {
		return stateDTO;
	}
	public void setStateDTO(StateDTO stateDTO) {
		this.stateDTO = stateDTO;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	public UserDto getUserDto() {
		return userDto;
	}
	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}
	
	
	
	

}
