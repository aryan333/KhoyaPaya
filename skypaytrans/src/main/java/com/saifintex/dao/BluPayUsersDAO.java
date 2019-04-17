package com.saifintex.dao;

import java.util.List;

import com.saifintex.entity.UserEntity;

public interface BluPayUsersDAO extends BaseDAO<UserEntity, Long> {
	
	public List<UserEntity> getAllBluPayUsers();
	
	public List<UserEntity> getAllRegisteredBluPayUsers();

	
}
