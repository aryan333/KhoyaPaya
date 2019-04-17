package com.saifintex.dao.impl;

import org.springframework.stereotype.Repository;

import com.saifintex.dao.UsersNameMappingDAO;
import com.saifintex.entity.UsersNameMapping;
@Repository("userNameMappingDao")
public class UserNameMappingDAOImpl extends BaseDAOImpl<UsersNameMapping, Integer> implements UsersNameMappingDAO{

	@Override
	public void updateUserNameMappping(int id, String userName1, String userName2) {
		//Session session=sessionFactory.getCurrentSession();
		
		UsersNameMapping mapping=getOne(UsersNameMapping.class, id);
		if(userName1!=null) {
			mapping.setUserName1(userName1);
		}else {
			mapping.setUserName2(userName2);
		}
		
	}
}
