package com.saifintex.dao.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.saifintex.dao.RewardPointsDAO;
import com.saifintex.entity.RewardPointsEntity;

@Repository
public class RewardPointsDAOImpl extends BaseDAOImpl<RewardPointsEntity, Integer> implements RewardPointsDAO{

	
	@Override
	public RewardPointsEntity findRewardPointEntityWithActiveState(int rId) {
		RewardPointsEntity entity = (RewardPointsEntity) sessionFactory.getCurrentSession()
				.createCriteria(RewardPointsEntity.class).add(Restrictions.eq("id", rId))
				.add(Restrictions.eq("isActive", true)).uniqueResult();
	
		return entity;
	}
	
	

}
