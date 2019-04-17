package com.saifintex.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.saifintex.dao.AppsDAO;
import com.saifintex.entity.AppPreferencesEntity;
import com.saifintex.entity.ClientExceptionsEntity;
import com.saifintex.entity.FiscalYearEntity;
import com.saifintex.entity.ItemCategoryEntity;


@Repository("appsDao")
public class AppsDAOImpl extends BaseDAOImpl<AppPreferencesEntity, Integer> implements AppsDAO{
	
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public FiscalYearEntity fetchFiscalYear() {
	Session session=sessionFactory.getCurrentSession();
	//FiscalYearEntity  entity=(FiscalYearEntity)session.get(FiscalYearEntity.class, 1);
	Criteria criteria = session.createCriteria(FiscalYearEntity.class);
	criteria.setFirstResult(0);
	criteria.setMaxResults(1);
	Object obj = criteria.add(Restrictions.eq("isActive", true)).uniqueResult();
	
	
		return (FiscalYearEntity)obj;
	}

	@Override
	public List<ItemCategoryEntity> getItemCategoryList() {
		Session session=sessionFactory.getCurrentSession();
		List<ItemCategoryEntity> list=session.createCriteria(ItemCategoryEntity.class).list();
		return list;
	}

	@Override
	public int storeClientException(ClientExceptionsEntity entity) {
		Integer id = (Integer)sessionFactory.getCurrentSession().save(entity);	
		return id;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClientExceptionsEntity> fetchExceptions(){
		List<ClientExceptionsEntity> list = sessionFactory.getCurrentSession().createCriteria(ClientExceptionsEntity.class)
		.addOrder(Order.desc("id")).list();
		return list;
	}
	
}

