package com.saifintex.dao.impl;

import org.hibernate.Criteria;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.saifintex.dao.UsersBalanceDAO;
import com.saifintex.entity.UsersBalanceEntity;

@Transactional
@Repository("usersBalanceDao")
public class UsersBalanceDAOImpl extends BaseDAOImpl<UsersBalanceEntity, Long> implements UsersBalanceDAO{

	@Override
	public UsersBalanceEntity getByBothUsersId(int user1, int user2) {
		Session session=sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(UsersBalanceEntity.class);
			Criterion cr1 = Restrictions.eq("user1", user1);
			Criterion cr2 = Restrictions.eq("user2", user2);
			Criterion cr3 = Restrictions.eq("user1", user2);
			Criterion cr4 = Restrictions.eq("user2", user1);
			LogicalExpression andExpression = Restrictions.and(cr1, cr2);
			LogicalExpression andExpression2 = Restrictions.and(cr3, cr4);
			LogicalExpression orExpression = Restrictions.or(andExpression, andExpression2);
			UsersBalanceEntity uBEntity = (UsersBalanceEntity) criteria.add(orExpression).uniqueResult();
			return uBEntity;
		
	}

}
