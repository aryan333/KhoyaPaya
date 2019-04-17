package com.javatechie.spring.orm.api.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ProductType")
public class ProductTypeEntity {
	
	@Id
	private int productTypeid;
	private String producttype;
	
	
	public int getProductTypeid() {
		return productTypeid;
	}
	public void setProductTypeid(int productTypeid) {
		this.productTypeid = productTypeid;
	}
	public String getProducttype() {
		return producttype;
	}
	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}
	
	
	

}
