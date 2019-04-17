package com.saifintex.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saifintex.common.AbstractBase;
import com.saifintex.dao.RelationsDAO;
import com.saifintex.dto.RelationsDTO;
import com.saifintex.entity.RelationsEntity;
import com.saifintex.services.RelationsService;
import com.saifintex.utils.DateUtils;
import com.saifintex.web.dto.WebUsersDTO;
@Service
@Transactional

public class RelationsServiceImpl extends AbstractBase implements RelationsService{
	
	@Autowired	
	private RelationsDAO relationsDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RelationsDTO> findAll() {
		// TODO Auto-generated method stub
		getLogger().info("in servic relation =");
		List<RelationsEntity> list=relationsDao.findAll(RelationsEntity.class);
		return (List<RelationsDTO>)  (List<?>) copyList(list, RelationsDTO.class);
	}

	@Override
	public RelationsDTO addRelation(RelationsDTO dto,WebUsersDTO webUser) {
		RelationsEntity entity = new RelationsEntity();
		BeanUtils.copyProperties(dto, entity);
		Date date = DateUtils.getCurrentDateTime();
		entity.setCreatedBy(webUser.getWebUserId());
		entity.setCreatedOn(date);
		entity.setModifiedBy(webUser.getWebUserId());
		entity.setModifiedOn(date);		
		entity = relationsDao.saveAndGet(entity);
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}
}
