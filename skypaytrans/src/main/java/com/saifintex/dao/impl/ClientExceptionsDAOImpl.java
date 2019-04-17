package com.saifintex.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.saifintex.dao.ClientExceptionsDAO;
import com.saifintex.dto.BugReportStats;
import com.saifintex.entity.ClientExceptionsEntity;
import com.saifintex.web.dto.TransactionStatsDashboardDTO;
@Repository("clientExceptionDao")
public class ClientExceptionsDAOImpl extends BaseDAOImpl<ClientExceptionsEntity, Integer>  implements ClientExceptionsDAO{

	@Override
	public BugReportStats getBugStats() {
		Session session=sessionFactory.getCurrentSession();
		String hql=new StringBuilder(" SELECT COUNT(*) AS totalBugs, "
				+ " (SELECT COUNT(E.id) FROM ClientExceptionsEntity E WHERE DATE(E.createdOn)=CURDATE()) AS todaysBugs, " + 
				" (SELECT E.exceptionDescription FROM ClientExceptionsEntity E where E.id=(Select MAX(E.id) from ClientExceptionsEntity E )) AS lastBug, "
				+ "MAX(E.createdOn) AS lastBugDate,"
				+ "(SELECT E.userDevice FROM ClientExceptionsEntity E where E.id=(Select MAX(E.id) from ClientExceptionsEntity E )) AS lastBugDeviceName  "
				+ " FROM ClientExceptionsEntity E ")
				
				
				
				
				
				.toString();
		
		
		Query query = session.createQuery(hql)
				
				.setResultTransformer(Transformers.aliasToBean(BugReportStats.class));
		BugReportStats bugReportStats = (BugReportStats) query.uniqueResult();
		
		return bugReportStats;
		
		
	}

}
