package com.saifintex.dao;

import java.util.List;

import com.saifintex.entity.AppPreferencesEntity;
import com.saifintex.entity.ClientExceptionsEntity;
import com.saifintex.entity.FiscalYearEntity;
import com.saifintex.entity.ItemCategoryEntity;



public interface AppsDAO extends BaseDAO<AppPreferencesEntity, Integer>{
	
	public FiscalYearEntity fetchFiscalYear();
	
	public List<ItemCategoryEntity> getItemCategoryList();

	public int storeClientException(ClientExceptionsEntity entity);

	public List<ClientExceptionsEntity> fetchExceptions();
}
