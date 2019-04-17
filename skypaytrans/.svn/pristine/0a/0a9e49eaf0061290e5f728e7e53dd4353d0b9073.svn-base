package com.saifintex.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.saifintex.common.AbstractBase;
import com.saifintex.dao.FCMDAO;
import com.saifintex.entity.UserEntity;
import com.saifintex.utils.DateUtils;

@Repository("fcmDao")
public class FCMDAOImpl extends AbstractBase implements FCMDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	SessionFactory sessionFactory;
	int result;
 
	@Override 
	public int updateFCMToken(final int userId,final  String token) {
		Integer result=0;
		Session session=sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(UserEntity.class);
		UserEntity userEntity=(UserEntity)criteria.add(Restrictions.eq("id", userId)).uniqueResult();
		if(userEntity!=null){
			userEntity.setFcmToken(token);
			userEntity.setModifiedOn(DateUtils.getCurrentDateTime());
			userEntity.setModifiedBy(userId);
			result=(Integer)session.save(userEntity);
			return result;
		}
		return result;
	}

	@Override
	public UserEntity getToken(final int userId) {
		Session session=sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(UserEntity.class);
		UserEntity userEntity=(UserEntity)criteria.add(Restrictions.eq("id", userId)).uniqueResult();
		return userEntity;
		
	
		}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTokenOfAllUsers() {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		String hql="SELECT u.fcmToken FROM UserEntity u WHERE u.userType=:userType AND u.fcmToken IS NOT NULL";
		
		List<String> list=session.createQuery(hql).setParameter("userType", "P").list();
		return list;
	}
	
	
}

