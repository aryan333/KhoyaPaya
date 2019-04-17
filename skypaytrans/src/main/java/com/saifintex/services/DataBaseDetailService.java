package com.saifintex.services;

import java.util.List;

import com.saifintex.web.domain.DataBaseDetail;
import com.saifintex.web.domain.DataBaseTablesDetail;

public interface DataBaseDetailService {
	public DataBaseDetail getDataBaseDetail();
	public DataBaseTablesDetail getUserTableDetail();
	public DataBaseTablesDetail getTransactionsTableDetail();
	public List<DataBaseTablesDetail> getAllTablesDetail();
}
