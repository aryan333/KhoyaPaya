package com.saifintex.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saifintex.dao.BetaUsersDAO;
import com.saifintex.entity.BetaUsersEntity;
import com.saifintex.services.BetaUsersService;
@Service
@Transactional
public class BetaUsersServiceImpl implements BetaUsersService {
@Autowired
private BetaUsersDAO betaUsersDao;
	@Override
	public boolean isBetaUser(String phoneNumber) {
		BetaUsersEntity betaUsersEntity=betaUsersDao.getBetaUser(phoneNumber);
		if(betaUsersEntity!=null) {
			return true;
		}
		return false;
	}

}
