package com.saifintex.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.saifintex.dao.BillNumberSeriesDAO;
import com.saifintex.entity.BillNumberSeriesEntity;
@Repository
public class BillNumberSeriesDAOImpl  extends BaseDAOImpl<BillNumberSeriesEntity, Long> implements BillNumberSeriesDAO {

	@Override
	public BillNumberSeriesEntity getBYUserId(int userId) {
		Session session=sessionFactory.getCurrentSession();
		String hql="from BillNumberSeriesEntity B where B.userId=:userId";
		Query query=session.createQuery(hql);
		query.setParameter("userId", userId);
		BillNumberSeriesEntity billNumberSeriesEntity=(BillNumberSeriesEntity)query.uniqueResult();
		return billNumberSeriesEntity;
	}

}
