package com.saifintex.dao;

import com.saifintex.entity.UsersNameMapping;

public interface UsersNameMappingDAO extends BaseDAO<UsersNameMapping, Integer> {
	
	public void updateUserNameMappping(int id, String userName1, String userName2);
}
