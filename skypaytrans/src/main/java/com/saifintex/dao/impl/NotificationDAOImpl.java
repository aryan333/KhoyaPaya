package com.saifintex.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.saifintex.dao.NotificationDAO;
import com.saifintex.entity.NotificationEntity;
@Repository
public class NotificationDAOImpl extends BaseDAOImpl<NotificationEntity, Long> implements NotificationDAO{

	@Override
	public List<NotificationEntity> getUserNotifications(int userId) {
		Session session=sessionFactory.getCurrentSession();
		String hql="from NotificationEntity N where N.senderId=:userId or N.receiverId=:userId Order BY N.id DESC ";
		Query query=session.createQuery(hql);
		query.setParameter("userId", userId);
		List<NotificationEntity> nList=query.list();
		return nList;
	}

}
