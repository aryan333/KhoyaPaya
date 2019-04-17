package com.saifintex.web.domain;

import java.math.BigDecimal;

public class DataBaseTablesDetail {
private String tableName;
private String rowCount;
private BigDecimal size;
public String getTableName() {
	return tableName;
}
public void setTableName(String tableName) {
	this.tableName = tableName;
}


public BigDecimal getSize() {
	return size;
}
public void setSize(BigDecimal size) {
	this.size = size;
}
public String getRowCount() {
	return rowCount;
}
public void setRowCount(String rowCount) {
	this.rowCount = rowCount;
}









}
