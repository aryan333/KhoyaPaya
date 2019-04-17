package com.saifintex.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saifintex.common.AbstractBase;
import com.saifintex.dao.RolesDAO;
import com.saifintex.dto.RolesDTO;
import com.saifintex.entity.RolesEntity;
import com.saifintex.services.RolesService;
import com.saifintex.utils.DateUtils;
import com.saifintex.web.dto.WebUsersDTO;

@Service
@Transactional
public class RolesServiceImpl extends AbstractBase implements RolesService {

	@Autowired

	private RolesDAO rolesDAO;

	@SuppressWarnings("unchecked")
	@Override
	public List<RolesDTO> findAll() {
		List<RolesEntity> list = rolesDAO.findAll(RolesEntity.class);
		return (List<RolesDTO>) (List<?>) copyList(list, RolesDTO.class);
	}
	
	@Override
	public RolesDTO addRole(RolesDTO rolesDTO,WebUsersDTO usersDTO) {
		RolesEntity rolesEntity = new RolesEntity();
		BeanUtils.copyProperties(rolesDTO, rolesEntity);
		Date date = DateUtils.getCurrentDateTime();
		rolesEntity.setCreatedBy(usersDTO.getWebUserId());
		rolesEntity.setCreatedOn(date);
		rolesEntity.setModifiedBy(usersDTO.getWebUserId());
		rolesEntity.setModifiedOn(date);		
		rolesEntity = rolesDAO.saveAndGet(rolesEntity);
		BeanUtils.copyProperties(rolesEntity, rolesDTO);
		return rolesDTO;
	}

}
