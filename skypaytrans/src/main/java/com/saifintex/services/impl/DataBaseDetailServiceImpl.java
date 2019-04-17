package com.saifintex.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saifintex.dao.DataBaseDetailDAO;
import com.saifintex.services.DataBaseDetailService;
import com.saifintex.web.domain.DataBaseDetail;
import com.saifintex.web.domain.DataBaseTablesDetail;
@Service
@Transactional
public class DataBaseDetailServiceImpl implements DataBaseDetailService {

	@Autowired
	private DataBaseDetailDAO dataBaseDetailDAO;
	@Override
	public DataBaseDetail getDataBaseDetail() {
		DataBaseDetail baseDetail=dataBaseDetailDAO.getDataBaseDetail();
		
		return baseDetail;
	}

	@Override
	public DataBaseTablesDetail getUserTableDetail() {
		DataBaseTablesDetail baseTablesDetail=dataBaseDetailDAO.getUserTableDetail();
		System.out.println(baseTablesDetail.getRowCount());
		
		return baseTablesDetail;
	}

	@Override
	public DataBaseTablesDetail getTransactionsTableDetail() {
		DataBaseTablesDetail baseTablesDetail=dataBaseDetailDAO.getTransactionsTableDetail();
		return baseTablesDetail;
	}

	@Override
	public List<DataBaseTablesDetail> getAllTablesDetail() {
		List<DataBaseTablesDetail> baseTablesDetail=dataBaseDetailDAO.getAllTablesDetail();
		return baseTablesDetail;
	
	}

}
