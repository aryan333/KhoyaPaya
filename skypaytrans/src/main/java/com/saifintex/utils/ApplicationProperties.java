package com.saifintex.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class ApplicationProperties {
@Value("${app.transaction.notify.insert.amount}")
private String transStoreTotalAmountNotify;

@Value("${app.transaction.notify.insert.billamount}")
private String transStoreBillAmountNotify;

@Value("${app.transaction.notify.accept.amount}")
private String acceptWithTotalAmount;

@Value("${app.transaction.notify.accept.billamount}")
private String acceptWithBillAmount;

@Value("${app.transaction.notify.reject.amount}")
private String rejectWithTotalAmount;

@Value("${app.transaction.notify.reject.billamount}")
private String rejectWithBillAmount;

@Value("${app.notifi.type.trans.id}")
private int nTypeTransId;

@Value("${app.notifi.type.accept.id}")
private int nTypeAcceptId;

@Value("${app.notifi.type.reject.id}")
private int nTypeRejectId;

@Value("${app.notifi.type.remibalance.id}")
private int nTypeRemBalanceId;

@Value("${app.notifi.type.trans.business.id}")
private int nTypeTransBusinessId;

@Value("${app.notifi.type.accept.business.id}")
private int nTypeAcceptBusinessId;

@Value("${app.notifi.type.reject.business.id}")
private int nTypeRejectBusinessId;



public String getTransStoreTotalAmountNotify() {
	return transStoreTotalAmountNotify;
}

public String getTransStoreBillAmountNotify() {
	return transStoreBillAmountNotify;
}

public String getAcceptWithTotalAmount() {
	return acceptWithTotalAmount;
}

public String getAcceptWithBillAmount() {
	return acceptWithBillAmount;
}

public String getRejectWithTotalAmount() {
	return rejectWithTotalAmount;
}

public String getRejectWithBillAmount() {
	return rejectWithBillAmount;
}

public int getnTypeTransId() {
	return nTypeTransId;
}

public int getnTypeAcceptId() {
	return nTypeAcceptId;
}

public int getnTypeRejectId() {
	return nTypeRejectId;
}

public int getnTypeRemBalanceId() {
	return nTypeRemBalanceId;
}

public int getnTypeTransBusinessId() {
	return nTypeTransBusinessId;
}

public int getnTypeAcceptBusinessId() {
	return nTypeAcceptBusinessId;
}

public int getnTypeRejectBusinessId() {
	return nTypeRejectBusinessId;
}


}
