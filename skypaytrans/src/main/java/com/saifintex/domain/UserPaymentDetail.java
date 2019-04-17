package com.saifintex.domain;

public class UserPaymentDetail {
private String mihpayid;
private String mode;
private String status;
private String reverse_hash;
private String error;
private String bankcode;
private String PG_TYPE;
private String bank_ref_num;
private String unmapped_status;
private String firstname;
private String lastname;
private String email;
private String password;
private String phone;
private String amount;
private String hash;
private String txnid;
private String key;
private String productinfo;
private int userId;
private String paymentDate;



public String getFirstname() {
	return firstname;
}
public void setFirstname(String firstname) {
	this.firstname = firstname;
}
public String getLastname() {
	return lastname;
}
public void setLastname(String lastname) {
	this.lastname = lastname;
}
public String getPaymentDate() {
	return paymentDate;
}
public void setPaymentDate(String paymentDate) {
	this.paymentDate = paymentDate;
}
public int getUserId() {
	return userId;
}
public void setUserId(int userId) {
	this.userId = userId;
}
public void setPhone(String phone) {
	this.phone = phone;
}

public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getPhone() {
	return phone;
}
public void setPhoneNo(String phone) {
	this.phone = phone;
}
public String getAmount() {
	return amount;
}
public void setAmount(String amount) {
	this.amount = amount;
}
public String getHash() {
	return hash;
}
public void setHash(String hash) {
	this.hash = hash;
}
public String getTxnid() {
	return txnid;
}
public void setTxnid(String txnid) {
	this.txnid = txnid;
}
public String getKey() {
	return key;
}
public void setKey(String key) {
	this.key = key;
}
public String getProductinfo() {
	return productinfo;
}
public void setProductinfo(String productinfo) {
	this.productinfo = productinfo;
}
public String getMihpayid() {
	return mihpayid;
}
public void setMihpayid(String mihpayid) {
	this.mihpayid = mihpayid;
}
public String getMode() {
	return mode;
}
public void setMode(String mode) {
	this.mode = mode;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}

public String getReverse_hash() {
	return reverse_hash;
}
public void setReverse_hash(String reverse_hash) {
	this.reverse_hash = reverse_hash;
}
public String getError() {
	return error;
}
public void setError(String error) {
	this.error = error;
}
public String getBankcode() {
	return bankcode;
}
public void setBankcode(String bankcode) {
	this.bankcode = bankcode;
}
public String getPG_TYPE() {
	return PG_TYPE;
}
public void setPG_TYPE(String pG_TYPE) {
	PG_TYPE = pG_TYPE;
}
public String getBank_ref_num() {
	return bank_ref_num;
}
public void setBank_ref_num(String bank_ref_num) {
	this.bank_ref_num = bank_ref_num;
}
public String getUnmapped_status() {
	return unmapped_status;
}
public void setUnmapped_status(String unmapped_status) {
	this.unmapped_status = unmapped_status;
}


}
