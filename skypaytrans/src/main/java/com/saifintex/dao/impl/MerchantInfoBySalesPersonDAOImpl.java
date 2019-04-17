package com.saifintex.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.saifintex.dao.MerchantInfoBySalesPersonDAO;
import com.saifintex.entity.MerchantInfoBySalesPersonEntity;
@Repository
public class MerchantInfoBySalesPersonDAOImpl extends BaseDAOImpl<MerchantInfoBySalesPersonEntity, Long> implements MerchantInfoBySalesPersonDAO {

	@Override
	public MerchantInfoBySalesPersonEntity getMerchantByMobileNumber(String mobileNumber) {
		Session session=sessionFactory.getCurrentSession();
		String hql="from MerchantInfoBySalesPersonEntity m where  m.mobileNumber=:mobileNumber ";
		Query query=session.createQuery(hql);
		query.setParameter("mobileNumber", mobileNumber);
		MerchantInfoBySalesPersonEntity bySalesPersonEntity=(MerchantInfoBySalesPersonEntity)query.uniqueResult();
		return bySalesPersonEntity;
	}

}
