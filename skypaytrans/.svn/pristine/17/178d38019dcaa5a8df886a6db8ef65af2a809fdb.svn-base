package com.saifintex.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.saifintex.common.AbstractBase;
import com.saifintex.dao.BaseDAO;
import com.saifintex.entity.AppPreferencesEntity;
import com.saifintex.entity.UserEntity;
import com.saifintex.web.entity.WebUsersEntity;
@Repository
public class BaseDAOImpl<T, S extends Serializable> extends AbstractBase implements BaseDAO<T, S> {

	@Autowired
	SessionFactory sessionFactory;
	@Value("${app.maxResult}")
	private int maxResult;

	
	@SuppressWarnings("unchecked")
	@Override

	public S save(T t) {
		S s = (S) sessionFactory.getCurrentSession().save(t);
		return s;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T saveAndGet(T t) {
		Session session = sessionFactory.getCurrentSession();
		S s = (S) session.save(t);
		T entity = (T) getOne((Class<T>) t.getClass(), s);
		return entity;
	}

	@Override
	public T getOne(Class<T> clz, S arg0) {
		T t = sessionFactory.getCurrentSession().get(clz, arg0);
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(Class<T> t) {
		List<T> list = sessionFactory.getCurrentSession().createCriteria(t).list();
		return list;
	}

	@Override
	public void saveOrUpdate(T t) {
		sessionFactory.getCurrentSession().saveOrUpdate(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(Class<T> t, Integer pageNumber) {
		List<T> list = sessionFactory.getCurrentSession().createCriteria(t).setFirstResult(pageNumber)
				.setMaxResults(maxResult).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(Class<T> t, Integer pageNumber, String sortBy) {
		List<T> list = sessionFactory.getCurrentSession().createCriteria(t).setFirstResult(pageNumber)
				.setMaxResults(maxResult).addOrder(Order.desc(sortBy)).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(Class<T> t, String sortBy) {
		List<T> list = sessionFactory.getCurrentSession().createCriteria(t).addOrder(Order.desc(sortBy)).list();
		return list;
	}


	@Override
	public UserEntity findByUserLogin(String param) {
		UserEntity entity	= (UserEntity)sessionFactory.getCurrentSession().createCriteria(UserEntity.class).add(Restrictions.eq("phNumber", param)).uniqueResult();
				/*.add(Restrictions.ne("isEnabled", false)).add(Restrictions.ne("isAccountNonLocked", false))
				.add(Restrictions.ne("isAccountNonExpired", false))
				.add(Restrictions.ne("isCredentialsNonExpired", false)).uniqueResult();
		*/return entity;
	}

	@Override
	public WebUsersEntity findByUserLoginWeb(String param) {
		WebUsersEntity entity =(WebUsersEntity)sessionFactory.getCurrentSession().createCriteria(WebUsersEntity.class).add(Restrictions.eq("userLogin", param))
				.add(Restrictions.ne("isEnabled", false)).add(Restrictions.ne("isAccountNonLocked", false))
				.add(Restrictions.ne("isAccountNonExpired", false))
				.add(Restrictions.ne("isCredentialsNonExpired", false)).uniqueResult();
		return entity;
	}

	@Override
	public AppPreferencesEntity getAppPreferences(int id) {
		System.out.println("id=============="+id);
		AppPreferencesEntity entity=(AppPreferencesEntity)sessionFactory.getCurrentSession().get(AppPreferencesEntity.class, id);
		return entity;
	}

	@Override
	public UserEntity findByInviteCode(String refCode) {
		UserEntity entity = (UserEntity) sessionFactory.getCurrentSession().createCriteria(UserEntity.class)
				.add(Restrictions.eq("inviteCode", refCode)).uniqueResult();
		return entity;
	}

	@Override
	public T merge(T t) {
		
	Session session=	sessionFactory.getCurrentSession();
	
		//session.buildLockRequest(new LockOptions(LockMode.PESSIMISTIC_WRITE)).lock(t);
	
		@SuppressWarnings("unchecked")
		
		T entity=(T)session.merge(t);
		TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
		System.out.println("Users Balance merge session object---"+status.hashCode());
		//session.refresh(t);
		session.flush();
		return entity;
	}

	@Override
	public void update(T t) {
		sessionFactory.getCurrentSession().update(t);
		
	}

	@Override
	public T load(Class<T> clz,S id) {
		T t=(T) sessionFactory.getCurrentSession().load(clz, id);
		return t;
	}

}
