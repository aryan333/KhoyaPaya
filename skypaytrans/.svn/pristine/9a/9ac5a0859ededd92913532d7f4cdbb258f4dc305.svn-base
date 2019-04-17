package com.saifintex.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.saifintex.dao.UsersOTPDAO;
import com.saifintex.entity.UserOTPEntity;
@Repository
public class UsersOTPDAOImpl extends BaseDAOImpl<UserOTPEntity, Integer> implements UsersOTPDAO{

	@Override
	public UserOTPEntity getOtpByMobileNo(String mobileNumber) {
		Session session=sessionFactory.getCurrentSession();
		String hql="from UserOTPEntity where mobileNumber=:mobileNumber ";
		Query query=session.createQuery(hql);
		query.setParameter("mobileNumber", mobileNumber);
		UserOTPEntity entity=(UserOTPEntity)query.uniqueResult();
		return entity;
	}

}
