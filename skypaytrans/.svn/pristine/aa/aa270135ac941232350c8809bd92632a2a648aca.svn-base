package com.saifintex.services.impl;

import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saifintex.common.AbstractBase;
import com.saifintex.dao.FiscalYearDAO;
import com.saifintex.dto.FiscalYearDTO;
import com.saifintex.entity.FiscalYearEntity;
import com.saifintex.services.FiscalYearService;
import com.saifintex.utils.DateUtils;
import com.saifintex.web.dto.WebUsersDTO;
@Service("fiscalYearService")
@Transactional
public class FiscalYearServiceImpl extends AbstractBase implements FiscalYearService {

	@Autowired
	FiscalYearDAO fiscalYearDAO;
	
	@Override
	public FiscalYearDTO fetchFiscalYear() {
		FiscalYearEntity entity=fiscalYearDAO.fetchFiscalYear();
		FiscalYearDTO dto=new FiscalYearDTO();
		BeanUtils.copyProperties(entity, dto);
		dto=getFiscalYear(dto);
		return dto;
	}
	
	private FiscalYearDTO getFiscalYear(FiscalYearDTO dto){
		StringBuilder quarter1=new StringBuilder();
		StringBuilder quarter2=new StringBuilder();
		StringBuilder quarter3=new StringBuilder();
		StringBuilder quarter4=new StringBuilder();
		int i=1;
		LocalDate date1 = new LocalDate(dto.getStartDate());
		LocalDate date2 = new LocalDate(dto.getEndDate()); 
		 while(date1.isBefore(date2)){
			 if(i>=1 &&i<=3){
				quarter1.append(date1.toString("MMM"));
				quarter1.append(",");
			 }else if(i>=4 &&i<=6){
				 quarter2.append(date1.toString("MMM"));
				 quarter2.append(",");
			 }else if(i>=7 &&i<=9){
				 quarter3.append(date1.toString("MMM"));
				 quarter3.append(",");
			 }else{
				 quarter4.append(date1.toString("MMM"));
				 quarter4.append(",");
			 }
		     System.out.println(date1.toString("MMM"));
		     date1 = date1.plus(Period.months(1));
		     i++;
		 }
		 dto.setQuarter1(quarter1.deleteCharAt(quarter1.lastIndexOf(",")));
		 dto.setQuarter2(quarter2.deleteCharAt(quarter2.lastIndexOf(",")));
		 dto.setQuarter3(quarter3.deleteCharAt(quarter3.lastIndexOf(",")));
		 dto.setQuarter4(quarter4.deleteCharAt(quarter4.lastIndexOf(",")));
		getLogger().info(dto.getStartDate());
		getLogger().info(dto.getEndDate());
		return dto;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FiscalYearDTO> findAll() {		
		List<FiscalYearEntity> list=fiscalYearDAO.findAll(FiscalYearEntity.class);	
		return (List<FiscalYearDTO>)  (List<?>) copyList(list, FiscalYearDTO.class);
	}
	
	@Override
	public FiscalYearDTO addFiscalYear(FiscalYearDTO dto,WebUsersDTO webUser) {
		FiscalYearEntity entity = new FiscalYearEntity();
		BeanUtils.copyProperties(dto, entity);
		Date date = DateUtils.getCurrentDateTime();
		entity.setCreatedBy(webUser.getWebUserId());
		entity.setCreatedOn(date);
		entity.setModifiedBy(webUser.getWebUserId());
		entity.setModifiedOn(date);		
		entity = fiscalYearDAO.saveAndGet(entity);
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}
}
