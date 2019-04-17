package com.saifintex.dao.impl;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.saifintex.dao.PasswordResetDAO;
import com.saifintex.web.entity.PasswordResetTokenEntity;

@Repository
public class PasswordResetDAOImpl extends BaseDAOImpl<PasswordResetTokenEntity, Integer> implements PasswordResetDAO{

	@Override
	public PasswordResetTokenEntity getEntityByToken(String token) {
		PasswordResetTokenEntity tokenEntity = (PasswordResetTokenEntity) sessionFactory.getCurrentSession()
				.createCriteria(PasswordResetTokenEntity.class).add(Restrictions.eq("token", token))
				.uniqueResult();
		return tokenEntity;
	}

	@Override
	public PasswordResetTokenEntity getEntityByUserId(int id) {
		PasswordResetTokenEntity tokenEntity = (PasswordResetTokenEntity) sessionFactory.getCurrentSession()
				.createCriteria(PasswordResetTokenEntity.class).add(Restrictions.eq("createdBy", id))
				.uniqueResult();
		return tokenEntity;
	}

	@Override
	public void deleteByToken(String token) {
		Session session = sessionFactory.getCurrentSession();
		PasswordResetTokenEntity entity = (PasswordResetTokenEntity) session
				.createCriteria(PasswordResetTokenEntity.class).add(Restrictions.eq("token", token)).uniqueResult();
		session.delete(entity);
	}
	
}
