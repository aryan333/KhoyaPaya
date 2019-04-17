package com.saifintex.dao.impl;




import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.saifintex.dao.BetaUsersDAO;
import com.saifintex.entity.BetaUsersEntity;

@Repository
public class BetaUsersDAOImpl extends BaseDAOImpl<BetaUsersEntity, Integer> implements BetaUsersDAO {

	@Override
	public BetaUsersEntity getBetaUser(String phoneNumber) {
		Session session=sessionFactory.getCurrentSession();
		String hql="from BetaUsersEntity b where b.userLogin=:userLogin";
		Query query=session.createQuery(hql);
		query.setParameter("userLogin", phoneNumber);
		BetaUsersEntity betaUsersEntity=(BetaUsersEntity)query.uniqueResult();
		return betaUsersEntity;
	}

	
}
