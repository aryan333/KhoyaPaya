package com.saifintex.services;

import java.util.List;

import com.saifintex.dto.FiscalYearDTO;
import com.saifintex.web.dto.WebUsersDTO;

public interface FiscalYearService {
	
	public FiscalYearDTO fetchFiscalYear();
	
	public List<FiscalYearDTO> findAll();
	
	public FiscalYearDTO addFiscalYear(FiscalYearDTO dto,WebUsersDTO webUsersDTO);

}
