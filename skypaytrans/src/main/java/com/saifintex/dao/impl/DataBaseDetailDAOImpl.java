package com.saifintex.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.saifintex.dao.DataBaseDetailDAO;
import com.saifintex.web.domain.DataBaseDetail;
import com.saifintex.web.domain.DataBaseTablesDetail;
import com.saifintex.web.dto.TransactionsRecordDto;
@Repository
public class DataBaseDetailDAOImpl implements DataBaseDetailDAO {
	
	@Value("${server.database.name}")
	private String dataBaseName;
@Autowired
private SessionFactory sessionFactory;


	@Override
	public DataBaseDetail getDataBaseDetail() {
	Session session=sessionFactory.getCurrentSession();
	String nativeQuery="SELECT table_schema 'databaseName', SUM( data_length + index_length)/1024/1024 "
			+ " 'size' FROM information_schema.TABLES WHERE table_schema = :databaseName";
	Query query= session.createSQLQuery(nativeQuery);
	query.setParameter("databaseName", dataBaseName)
	.setResultTransformer(Transformers.aliasToBean(DataBaseDetail.class));
	DataBaseDetail detail=(DataBaseDetail)query.uniqueResult();
		return detail;
	}

	@Override
	public DataBaseTablesDetail getUserTableDetail() {
		Session session=sessionFactory.getCurrentSession();
		String nativeQuery="SELECT table_name 'tableName' , CAST(table_rows as CHAR(50)) as 'rowCount', "
				+ "   ROUND(((data_length + index_length)/1024/1024),2) 'size'   "
				+ "   FROM information_schema.TABLES WHERE table_schema = :databaseName AND table_name ='Users'";
		
		/*String nativeQuery="SELECT table_name 'tableName', table_rows 'rowCount', ROUND(((data_length + index_length)/1024/1024),2)" + 
				" 'size' FROM information_schema.TABLES WHERE table_schema = :databaseName AND table_name ='Users'";
		*/Query query= session.createSQLQuery(nativeQuery);
		query.setParameter("databaseName", dataBaseName)
		.setResultTransformer(Transformers.aliasToBean(DataBaseTablesDetail.class));
		DataBaseTablesDetail detail=(DataBaseTablesDetail)query.uniqueResult();
		return detail;
	}

	@Override
	public DataBaseTablesDetail getTransactionsTableDetail() {
		Session session=sessionFactory.getCurrentSession();
		String nativeQuery="SELECT table_name 'tableName', CAST(table_rows as CHAR(50)) as 'rowCount', ROUND(((data_length + index_length)/1024/1024),2)" + 
				" 'size' FROM information_schema.TABLES WHERE table_schema = :databaseName AND table_name ='Transactions'";
		Query query= session.createSQLQuery(nativeQuery);
		query.setParameter("databaseName", dataBaseName)
		.setResultTransformer(Transformers.aliasToBean(DataBaseTablesDetail.class));
		DataBaseTablesDetail detail=(DataBaseTablesDetail)query.uniqueResult();
		return detail;
	}

	@Override
	public List<DataBaseTablesDetail> getAllTablesDetail() {
		Session session=sessionFactory.getCurrentSession();
		String nativeQuery="SELECT table_name 'tableName', CAST(table_rows as CHAR(50)) as 'rowCount', ROUND(((data_length + index_length)/1024/1024),2)" + 
				" 'size' FROM information_schema.TABLES WHERE table_schema = :databaseName";
		Query query= session.createSQLQuery(nativeQuery);
		query.setParameter("databaseName", dataBaseName)
		.setResultTransformer(Transformers.aliasToBean(DataBaseTablesDetail.class));
		@SuppressWarnings("unchecked")
		List<DataBaseTablesDetail> detail=(List<DataBaseTablesDetail>)query.list();
		return detail;
	}

}
