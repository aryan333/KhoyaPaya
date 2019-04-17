package com.saifintex.dao;

import com.saifintex.entity.FiscalYearEntity;

public interface FiscalYearDAO extends BaseDAO<FiscalYearEntity, Integer> {
	public FiscalYearEntity fetchFiscalYear();
}
