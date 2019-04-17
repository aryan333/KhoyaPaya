package com.saifintex.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.saifintex.dao.QRUniqueSeriesDAO;
import com.saifintex.web.entity.QRUniqueSeriesEntity;
@Repository
public class QRUniqueSeriesDAOImpl extends BaseDAOImpl<QRUniqueSeriesEntity, Long> implements QRUniqueSeriesDAO {

	@Override
	public QRUniqueSeriesEntity getBySeries(String series) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(QRUniqueSeriesEntity.class).add(Restrictions.eq("qrSeries",series));
		QRUniqueSeriesEntity entity=(QRUniqueSeriesEntity)criteria.uniqueResult();
		return entity;
	}

}
