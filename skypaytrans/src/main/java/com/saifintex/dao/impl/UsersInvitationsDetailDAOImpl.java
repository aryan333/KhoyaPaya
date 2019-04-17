package com.saifintex.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.saifintex.dao.UsersInvitationsDetailDAO;
import com.saifintex.entity.UsersBalanceEntity;
import com.saifintex.entity.UsersInvitationsDetailEntity;
@Repository
public class UsersInvitationsDetailDAOImpl extends BaseDAOImpl<UsersInvitationsDetailEntity, Long> implements UsersInvitationsDetailDAO {

	
@Override
public UsersInvitationsDetailEntity getByBothId(int user1, int user2) {
	Session session=sessionFactory.getCurrentSession();
	Criteria criteria = session.createCriteria(UsersInvitationsDetailEntity.class);
	Criterion cr1 = Restrictions.eq("user1Id", user1);
	Criterion cr2 = Restrictions.eq("user2Id", user2);
	Criterion cr3 = Restrictions.eq("user1Id", user2);
	Criterion cr4 = Restrictions.eq("user2Id", user1);
	LogicalExpression andExpression = Restrictions.and(cr1, cr2);
	LogicalExpression andExpression2 = Restrictions.and(cr3, cr4);
	LogicalExpression orExpression = Restrictions.or(andExpression, andExpression2);
	UsersInvitationsDetailEntity usersInvitationsDetailEntity = (UsersInvitationsDetailEntity) criteria.add(orExpression).uniqueResult();
	

	return usersInvitationsDetailEntity;
}
}
