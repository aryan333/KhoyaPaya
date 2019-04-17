package com.saifintex.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.saifintex.entity.BaseEntity;
@Entity
@Table(name="QRUniqueCodeSeries")
public class QRUniqueSeriesEntity extends BaseEntity {
/**
	 * 
	 */
private static final long serialVersionUID = 1L;

@Column(name="SeriesId")
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;

@Column(name="IsQRGenerated")
private boolean isQrGenerated;

@Column(name="FolderName")
private String folderName;

@Column(name="QRImageName")
private String qrImageName;
@Column(name="IsMapped")
private boolean mapped;
@Column(name="MappedNumber",length=15)
private String mappedNumber; 
@Column(name="RequestForMapping")
private boolean requestedForMapping;


@Column(name="QRSERIES")

private String qrSeries;
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public boolean isQrGenerated() {
	return isQrGenerated;
}
public void setQrGenerated(boolean isQrGenerated) {
	this.isQrGenerated = isQrGenerated;
}
public String getFolderName() {
	return folderName;
}
public void setFolderName(String folderName) {
	this.folderName = folderName;
}
public String getQrImageName() {
	return qrImageName;
}
public void setQrImageName(String qrImageName) {
	this.qrImageName = qrImageName;
}

public String getMappedNumber() {
	return mappedNumber;
}
public void setMappedNumber(String mappedNumber) {
	this.mappedNumber = mappedNumber;
}
public boolean isMapped() {
	return mapped;
}
public void setMapped(boolean mapped) {
	this.mapped = mapped;
}
public boolean isRequestedForMapping() {
	return requestedForMapping;
}
public void setRequestedForMapping(boolean requestedForMapping) {
	this.requestedForMapping = requestedForMapping;
}
public String getQrSeries() {
	return qrSeries;
}
public void setQrSeries(String qrSeries) {
	this.qrSeries = qrSeries;
}



}
