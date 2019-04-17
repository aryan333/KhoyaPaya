package com.saifintex.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.saifintex.domain.BaseDomain;


@Entity
@Table(name="ItemMaster")

public class ItemMasterEntity extends BaseDomain implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ItemId")
	private int itemId;
	
	@Column(name="UserId")
	private int userId;
	
	@Column(name="ItemName")
	private String itemName;
	
	@Column(name="HSNCODE")
	private String hsnCode;
	
	@Column(name="StockQuantity")
	private int stockQuantity;
	
	@Column(name="CGST")
	private BigDecimal cgst;
	
	@Column(name="SGST")
	private BigDecimal sgst;
	
	@Column(name="IGST")
	private BigDecimal igst;
	
	@Column(name="ThresQuantity")
	private int thresQuantity;
	
	@Column(name="Rate")
	private BigDecimal rate;
	
	@Column(name="Unit")
	private String unit;
	
	@Column(name="ItemCategory")
	private String itemCategory;

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public BigDecimal getCgst() {
		return cgst;
	}

	public void setCgst(BigDecimal cgst) {
		this.cgst = cgst;
	}

	public BigDecimal getSgst() {
		return sgst;
	}

	public void setSgst(BigDecimal sgst) {
		this.sgst = sgst;
	}

	public BigDecimal getIgst() {
		return igst;
	}

	public void setIgst(BigDecimal igst) {
		this.igst = igst;
	}

	public int getThresQuantity() {
		return thresQuantity;
	}

	public void setThresQuantity(int thresQuantity) {
		this.thresQuantity = thresQuantity;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	

	

}
