package com.saifintex.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.saifintex.dao.UsersBalanceDAO;
import com.saifintex.dto.UsersBalanceDTO;
import com.saifintex.entity.UsersBalanceEntity;
import com.saifintex.services.UsersBalanceService;
@Service
@Transactional(isolation=Isolation.READ_COMMITTED)
public class UsersBalanceServiceImpl implements UsersBalanceService{
@Autowired
UsersBalanceDAO dao;
	@Override
	public UsersBalanceDTO getByBothIds(int user1, int user2) {
		UsersBalanceEntity entity=dao.getByBothUsersId(user1, user2);
		UsersBalanceDTO dto=new UsersBalanceDTO();
		BeanUtils.copyProperties(entity, dto);
		
		
		return dto;
	}

	@Override
	public void update(UsersBalanceDTO dto) {
		UsersBalanceEntity ubEntity=(UsersBalanceEntity)dao.load(UsersBalanceEntity.class, dto.getBalanceId());
		ubEntity.setBalance(dto.getBalance());
		
		
	}

}
