package com.saifintex.services;

import java.util.List;

import com.saifintex.dto.AppPreferencesDto;
import com.saifintex.dto.ClientExceptionsDTO;
import com.saifintex.dto.FiscalYearDTO;
import com.saifintex.dto.ItemCategoryDto;



public interface AppService{
	
	public FiscalYearDTO fetchFiscalYear();
	
	public List<ItemCategoryDto> getItemCategoryList();
	
	public int storeClientExceptionService(ClientExceptionsDTO clientExceptionsDTO);
	
	public List<ClientExceptionsDTO> getAllClientExceptionsService();
	
	public String readTodayInfoLogs();
	
	public String readTodayExceptionLogs();
	
	public String readExceptionLogOnSpecifiedDate(String date);
	
	public String readInfoLogOnSpecifiedDate(String date);
	
	public AppPreferencesDto getAppPreferences();
	
	public void updateAppPreferences(AppPreferencesDto appPrefDto);
	public void sendDashBoardDetailViaEmail(String to,String cc,String subject,String text,String html);
	
}

