package com.saifintex.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.saifintex.dao.FiscalYearDAO;
import com.saifintex.entity.FiscalYearEntity;

@Repository("fiscalYearDao")
public class FiscalYearDAOImpl extends BaseDAOImpl<FiscalYearEntity, Integer> implements FiscalYearDAO {
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public FiscalYearEntity fetchFiscalYear() {
		Session session = sessionFactory.getCurrentSession();
		// FiscalYearEntity entity=(FiscalYearEntity)session.get(FiscalYearEntity.class,
		// 1);
		Criteria criteria = session.createCriteria(FiscalYearEntity.class);
		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		Object obj = criteria.add(Restrictions.eq("isActive", true)).uniqueResult();

		return (FiscalYearEntity) obj;
	}

}
