package com.saifintex.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saifintex.common.AbstractBase;
import com.saifintex.dao.DisputeReasonsDAO;
import com.saifintex.dto.DisputeReasonsDTO;

import com.saifintex.entity.DisputeReasonsEntity;
import com.saifintex.services.DisputeReasonsService;
import com.saifintex.utils.DateUtils;
import com.saifintex.web.dto.WebUsersDTO;

@Service
@Transactional

public class DisputeReasonsServiceImpl extends AbstractBase implements DisputeReasonsService {
	
	@Autowired	
	private DisputeReasonsDAO disputeReasonsDAO;

	@SuppressWarnings("unchecked")
	@Override
	public List<DisputeReasonsDTO> findAll() {		
		List<DisputeReasonsEntity> list=disputeReasonsDAO.findAll(DisputeReasonsEntity.class);
		return (List<DisputeReasonsDTO>)  (List<?>) copyList(list, DisputeReasonsDTO.class);
	}
	
	@Override
	public DisputeReasonsDTO addDisputeReason(DisputeReasonsDTO dto,WebUsersDTO webUsersDTO) {
		DisputeReasonsEntity entity = new DisputeReasonsEntity();
		BeanUtils.copyProperties(dto, entity);
		Date date = DateUtils.getCurrentDateTime();
		entity.setCreatedBy(webUsersDTO.getWebUserId());
		entity.setCreatedOn(date);
		entity.setModifiedBy(webUsersDTO.getWebUserId());
		entity.setModifiedOn(date);
		entity = disputeReasonsDAO.saveAndGet(entity);
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

}
