package com.saifintex.services;

import java.util.List;

import com.saifintex.dto.BugReportStats;
import com.saifintex.dto.ClientExceptionsDTO;

public interface ClientExceptionsService{
public int storeClientExceptionService(ClientExceptionsDTO clientExceptionsDTO);
	
	public List<ClientExceptionsDTO> getAllClientExceptionsService();
public String readTodayInfoLogs();
	
	public String readTodayExceptionLogs();
	
	public String readExceptionLogOnSpecifiedDate(String date);
	
	public String readInfoLogOnSpecifiedDate(String date);
	
	public BugReportStats getBugReportStats();
	
}
