package com.saifintex.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.saifintex.dao.UserContactsDAO;
import com.saifintex.entity.UsersContactsEntity;
@Repository
public class UsersContactsDAOImpl extends BaseDAOImpl<UsersContactsEntity, Long> implements UserContactsDAO{
	@Autowired
	 private EntityManager entityManager;
	@Override
	public List<String> getExistingContatcs(List<String> listOfContacts) {
		Session session=sessionFactory.getCurrentSession();
		String hql="select u.contactNumber from UsersContactsEntity u where u.contactNumber IN(:contactNumbers)";
		Query query=session.createQuery(hql);
		query.setParameterList("contactNumbers", listOfContacts);
		List<String> contactList=(List<String>)query.list();
		return contactList;
	}

	@Override
	public void bulkInsert(List<UsersContactsEntity> listOfContacts) {
		listOfContacts.forEach(listOfContact -> entityManager.persist(listOfContact));
		
	}
	
	

}
