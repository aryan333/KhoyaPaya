package com.saifintex.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.saifintex.dao.TransactionDAO;
import com.saifintex.domain.TransactionsFilterParams;
import com.saifintex.dto.SalesPurchaseDetailDto;
import com.saifintex.dto.TransactionSummaryParamsDto;
import com.saifintex.entity.ItemMasterEntity;
import com.saifintex.entity.TransactionsEntity;
import com.saifintex.entity.UserEntity;
import com.saifintex.entity.UsersBalanceEntity;
import com.saifintex.web.domain.Transactions;
import com.saifintex.web.dto.TransactionStatsDashboardDTO;
import com.saifintex.web.dto.TransactionsDTO;
import com.saifintex.web.dto.TransactionsRecordDto;



@Repository("transactionDao")
public class TransactionDAOImpl extends BaseDAOImpl<TransactionsEntity, Long> implements TransactionDAO {

	@Value("${app.maxResult}")
	private int maxResult;

	@Value("${app.maxResultForToPayToReceive}")
	private int maxResultForToPayToReceiveList;

	@Value("${transaction.status.payment.pending}")
	private String paymentStatus;

	private StringBuilder stringBuilder;

	private boolean isTestUser;

	private boolean isShowTestUser;

	@Override
	public List insert(final TransactionsEntity transactionsEntity) {
		Session session = sessionFactory.getCurrentSession();
		Long id = (Long) session.save(transactionsEntity);
		Criteria criteria = session.createCriteria(TransactionsEntity.class);
		TransactionsEntity entity = (TransactionsEntity) criteria.add(Restrictions.eq("payId", id)).uniqueResult();
		/*
		 * Notify opposite user about this transaction So need to fetch its info from db
		 */
		getLogger().info(" Transaction ID" + entity.getPayId());
		String hql = "SELECT T, U,UB " + " FROM TransactionsEntity T,UserEntity U,UsersBalanceEntity UB WHERE"
				+ " U.id=(CASE WHEN T.payeeId=:InitiatedFrom THEN T.payerId ELSE T.payeeId END)"
				+ " AND ((UB.user1=T.payeeId AND UB.user2=T.payerId) OR (UB.user1=T.payerId AND UB.user2=T.payeeId))"
				+ " AND payId=:PayID";

		Query query = session.createQuery(hql).setParameter("InitiatedFrom", entity.getInitiatedFrom())
				.setParameter("PayID", entity.getPayId());
		List<Object[]> list = query.list();

		List resultList = new ArrayList();
		if (list != null && list.size() != 0 && !list.isEmpty()) {
			Object[] obj = list.get(0);
			entity = (TransactionsEntity) obj[0];
			// getLogger().info("DAO BILL AMOUNT+++" + entity.getCashReceived());
			UserEntity uEntity = (UserEntity) obj[1];
			UsersBalanceEntity ubEntity = (UsersBalanceEntity) obj[2];

			if (entity != null) {
				resultList.add(entity);
				resultList.add(uEntity);
				resultList.add(ubEntity);
			}
		}
		return resultList;
	}

	/*
	 * insert mutual balance as well as pending transaction amount (skycredit) in
	 * user balance table
	 */

	/*
	 * private long insertMutualBalanceForFirstTime(TransactionsEntity
	 * transactionsEntity, UsersBalanceEntity balanceEntity, Session session) {
	 * balanceEntity = new UsersBalanceEntity(); Date date =
	 * DateUtils.getCurrentDateTime(); balanceEntity.setBalance(BigDecimal.ZERO);
	 * balanceEntity.setCreatedOn(date);
	 * balanceEntity.setTotalCash(BigDecimal.ZERO);
	 * balanceEntity.setCreatedBy(transactionsEntity.getInitiatedFrom());
	 * balanceEntity.setUser1(transactionsEntity.getPayerId());
	 * balanceEntity.setUser2(transactionsEntity.getPayeeId());
	 * balanceEntity.setPendingTransactionAmount(transactionsEntity.getSkyCredit());
	 * balanceEntity.setModifiedBy(transactionsEntity.getInitiatedFrom());
	 * balanceEntity.setCreatedOn(date); balanceEntity.setModifiedOn(date);
	 * balanceEntity.setTransacted(false); UsersNameMapping nameMapping = new
	 * UsersNameMapping(); nameMapping.setUserName1("");
	 * nameMapping.setUserName2("");
	 * nameMapping.setCreatedBy(transactionsEntity.getCreatedBy());
	 * nameMapping.setCreatedOn(date);
	 * balanceEntity.setUsersNameMapping(nameMapping); Long result = (Long)
	 * session.save(balanceEntity); return result; }
	 */

	/*
	 * private void updatePendingTransactionAmount(TransactionsEntity
	 * transactionEntity, UsersBalanceEntity usersBalanceEntity, Session session) {
	 * int payerId = transactionEntity.getPayerId(); BigDecimal skyCredit =
	 * transactionEntity.getSkyCredit(); BigDecimal pendingAmount =
	 * usersBalanceEntity.getPendingTransactionAmount();
	 * 
	 * // calculate and update pending transaction amount according to the //
	 * conditions if (payerId == usersBalanceEntity.getUser1()) { pendingAmount =
	 * pendingAmount.add(skyCredit); } else if (payerId ==
	 * usersBalanceEntity.getUser2()) { pendingAmount =
	 * pendingAmount.subtract(skyCredit); }
	 * usersBalanceEntity.setPendingTransactionAmount(pendingAmount);
	 * usersBalanceEntity.setModifiedOn(DateUtils.getCurrentDateTime());
	 * usersBalanceEntity.setModifiedBy(transactionEntity.getInitiatedFrom());
	 * usersBalanceEntity.setTransacted(true); //
	 * getLogger().info("after update pending amount : " + pendingAmount); }
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> fetchTransactionHistory(int userId, int pageSize, String type) {
		StringBuilder hql = new StringBuilder(
				"SELECT U,T,UB " + "FROM UserEntity U,TransactionsEntity T,UsersBalanceEntity UB WHERE "
						+ "U.id=(CASE WHEN T.payerId=:UserID THEN T.payeeId ELSE T.payerId END ) AND ");
		if (isTestUser()) {
			hql.append("U.isTestUser=:isTestUser ");
		} else {
			hql.append("U.isTestUser <> :isTestUser ");
		}
		hql.append(" AND (T.payerId=:UserID OR T.payeeId=:UserID)");
		if (!type.equalsIgnoreCase("all")) {
			hql.append(" AND (T.paymentStatus=:paymStatus)");
		}
		hql.append(" AND ((UB.user1=T.payeeId AND UB.user2=T.payerId) OR (UB.user1=T.payerId AND UB.user2=T.payeeId))");
		hql.append(" ORDER BY T.payId desc");
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString()).setParameter("UserID", userId);
		query.setParameter("isTestUser", true);
		if (!type.equalsIgnoreCase("all")) {
			query.setParameter("paymStatus", type);
		}
		query.setFirstResult(getPageSize(pageSize));
		query.setMaxResults(maxResult);
		List<Object[]> list = query.list();
		/* getLogger().info("list " + list.size()); */
		return list;

	}

	@Override
	public Object[] getTransactionByPayIdWithUserInfo(int userId, long payId) {
		StringBuilder hql = new StringBuilder("SELECT T,UB,U "
				+ "FROM TransactionsEntity T,UsersBalanceEntity UB,UserEntity U WHERE "
				+ " T.payId =:pId AND U.id=(CASE WHEN T.payerId=:UserID THEN T.payeeId ELSE T.payerId END ) AND ");
		if (isTestUser()) {
			hql.append("U.isTestUser=:isTestUser ");
		} else {
			hql.append("U.isTestUser <> :isTestUser ");
		}
		hql.append(" AND ((UB.user1=T.payeeId AND UB.user2=T.payerId) OR (UB.user1=T.payerId AND UB.user2=T.payeeId))");
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString()).setParameter("UserID", userId);
		query.setParameter("isTestUser", true).setParameter("pId", payId);
		Object[] obj = (Object[]) query.uniqueResult();
		return obj;
	}

	@Override
	public List getAssociatedUsers(final int Id, final int pageSize) {
		isTestUser = isTestUser();
		Session session = sessionFactory.getCurrentSession();
		StringBuilder hql = new StringBuilder(
				"SELECT U,UB,(SELECT p.blobId FROM ProfileImageEntity p WHERE p.id=(SELECT MAX(p1.id) "
						+ " FROM ProfileImageEntity p1 WHERE p1.userId=U.id)) "
						+ " FROM UserEntity U, UsersBalanceEntity UB WHERE"
						+ " U.id=(CASE WHEN UB.user1=:UserID THEN UB.user2 ELSE UB.user1 END) AND ");
		if (isTestUser) {
			hql.append("U.isTestUser=:isTestUser "
					+ " AND (UB.user1=:UserID OR UB.user2=:UserID) ORDER BY UB.modifiedOn DESC");
		} else {
			hql.append("U.isTestUser<>:isTestUser "
					+ " AND (UB.user1=:UserID OR UB.user2=:UserID) ORDER BY UB.modifiedOn DESC");

		}
		Query query = session.createQuery(hql.toString()).setParameter("UserID", Id).setParameter("isTestUser", true);
		query.setFirstResult(getPageSize(pageSize)).setMaxResults(maxResult);
		List<Object[]> list = query.list();
		return list;
	}

	private int getPageSize(int pageSize) {
		pageSize = pageSize * maxResult;
		return pageSize;
	}

	/*
	 * @Override public TransactionsEntity rejectTransaction(TransactionActionParams
	 * transactionActionParams) { Session session =
	 * sessionFactory.getCurrentSession(); TransactionsEntity entity =
	 * getTxnWithAssociatedId(transactionActionParams.getPayId(), session); if
	 * (entity != null && entity.getPaymentStatus().equals("P")) {
	 * updateCurrentTransPrevBalanceAmount(session, entity); UsersBalanceEntity
	 * usersBalanceEntity =
	 * getUserBalanceEntityForMutualBalance(entity.getPayerId(),
	 * entity.getPayeeId(), session); usersBalanceEntity
	 * .setPendingTransactionAmount(calculatePendingTransactionAmount(entity,
	 * usersBalanceEntity)); entity.setPaymentStatus("R");
	 * entity.setModifiedBy(transactionActionParams.getUserId());
	 * entity.setModifiedOn(DateUtils.getCurrentDateTime());
	 * entity.setConfirmLatitude(transactionActionParams.getConfirmLatitude());
	 * entity.setConfirmLongitude(transactionActionParams.getConfirmLongitude()); }
	 * else { getLogger().info("transaction is not in pending state  !!"); return
	 * entity = null; } return entity; }
	 */

	/*
	 * private void updateCurrentTransPrevBalanceAmount(Session session,
	 * TransactionsEntity rejectedTransaction) { List<TransactionsEntity>
	 * entityListToUpdate = getAllTransactionsToUpdate(session,
	 * rejectedTransaction); TransactionsEntity prevTransaction = null; if
	 * (entityListToUpdate != null && !entityListToUpdate.isEmpty()) {
	 * prevTransaction = getPreviousTransactionBetweenUsers(session,
	 * rejectedTransaction); } else { return; } BigDecimal tempCurTransPrevBal =
	 * null, tempSkyCredit =null, tempCurNetBal = null; int prevTransPayerId = 0,
	 * tempPayerId = 0; if (prevTransaction != null) { prevTransPayerId =
	 * prevTransaction.getInitiatedFrom(); tempPayerId = prevTransPayerId;
	 * tempCurTransPrevBal = prevTransaction.getCurrentPaymentStatus();
	 * tempCurNetBal = prevTransaction.getCurrentPaymentStatus();
	 * updateCurTransPrevBalOfAllEntities(entityListToUpdate, tempPayerId,
	 * tempCurTransPrevBal ,tempCurNetBal );
	 * 
	 * } else { TransactionsEntity firstTxnEntityToUpdate =
	 * entityListToUpdate.get(0); tempCurTransPrevBal = new BigDecimal("0.00");
	 * tempPayerId = firstTxnEntityToUpdate.getInitiatedFrom();
	 * updateCurTransPrevBalOfAllEntities(entityListToUpdate, tempPayerId,
	 * tempCurTransPrevBal ,tempCurNetBal ); } }
	 */

	@Override
	public TransactionsEntity getPreviousTransactionBetweenUsers(TransactionsEntity rejectedTransaction) {
		String hql = "select t from TransactionsEntity t where t.payId <:id and (t.payerId =:uId or t.payeeId =:uId ) "
				+ "and (t.payerId =:uId2 or t.payeeId =:uId2) and t.paymentStatus <>:rejected order by t.payId desc";
		Session session = sessionFactory.getCurrentSession();
		TransactionsEntity entity = (TransactionsEntity) session.createQuery(hql)
				.setParameter("id", rejectedTransaction.getPayId())
				.setParameter("uId", rejectedTransaction.getPayerId())
				.setParameter("uId2", rejectedTransaction.getPayeeId()).setParameter("rejected", "R").setMaxResults(1)
				.uniqueResult();
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionsEntity> getAllTransactionsToUpdate(TransactionsEntity rejectedTransaction) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "select t from TransactionsEntity t where t.payId >:id and (t.payerId =:uId or t.payeeId =:uId )"
				+ " and (t.payerId =:uId2 or t.payeeId =:uId2) and t.paymentStatus <>:rejected";
		List<TransactionsEntity> entityList = session.createQuery(hql)
				.setParameter("id", rejectedTransaction.getPayId())
				.setParameter("uId", rejectedTransaction.getPayerId())
				.setParameter("uId2", rejectedTransaction.getPayeeId()).setParameter("rejected", "R").list();
		return entityList;
	}

	/*
	 * private void updateCurTransPrevBalOfAllEntities(List<TransactionsEntity>
	 * entityListToUpdate, int tempPayerId, BigDecimal tempCurTransPrevBal
	 * ,BigDecimal tempCurNetBal ) {
	 * 
	 * for (TransactionsEntity entity : entityListToUpdate) { if
	 * (entity.getInitiatedFrom() == tempPayerId) {
	 * entity.setCurrTranPrevBal(tempCurTransPrevBal); } else {
	 * entity.setCurrTranPrevBal(tempCurTransPrevBal.negate()); } if
	 * (entity.getPayerId() == entity.getInitiatedFrom()) {
	 * entity.setCurrentPaymentStatus(entity.getCurrTranPrevBal().add(entity.
	 * getSkyCredit())); } else {
	 * entity.setCurrentPaymentStatus(entity.getCurrTranPrevBal().subtract(entity.
	 * getSkyCredit()));
	 * 
	 * } tempPayerId = entity.getInitiatedFrom(); tempCurTransPrevBal =
	 * entity.getCurrentPaymentStatus();
	 * 
	 * 
	 * } }
	 */
	/*
	 * @Override public TransactionsEntity acceptTransaction(TransactionActionParams
	 * transactionActionParams) { TransactionsEntity entity =
	 * updateMutualbalance(transactionActionParams); return entity; }
	 * 
	 * private TransactionsEntity updateMutualbalance(TransactionActionParams
	 * txnActionParams) { BigDecimal skyCredit = null; TransactionsEntity txnEntity
	 * = null; Session session = sessionFactory.getCurrentSession(); txnEntity =
	 * getTxnWithAssociatedId(txnActionParams.getPayId(), session); String
	 * paymentStatus = txnEntity.getPaymentStatus(); if (!paymentStatus.equals("P"))
	 * { getLogger().info("Transaction is not in pending state !! "); return null; }
	 * int userId1 = txnEntity.getPayerId(); int userId2 = txnEntity.getPayeeId();
	 * skyCredit = txnEntity.getSkyCredit(); UsersBalanceEntity usersBalanceEntity =
	 * getUserBalanceEntityForMutualBalance(userId1, userId2, session); if
	 * (usersBalanceEntity != null) { return updateTransaction(usersBalanceEntity,
	 * txnEntity, session, txnActionParams); } else {
	 * getLogger().info("record(Users) not found in User balance !!"); return null;
	 * }
	 * 
	 * }
	 * 
	 * private TransactionsEntity updateTransaction(UsersBalanceEntity
	 * usersBalanceEntity, TransactionsEntity txnEntity, Session session,
	 * TransactionActionParams txnActionParams) { BigDecimal mutualBalance =
	 * usersBalanceEntity.getBalance(); Date date = DateUtils.getCurrentDateTime();
	 * String paymentStatus = ""; txnEntity.setPreviousBalance(mutualBalance); if
	 * (txnActionParams.getUserAction() == 'C') { paymentStatus = "C"; } else {
	 * paymentStatus = "A"; } txnEntity.setPaymentStatus(paymentStatus);
	 * txnEntity.setConfirmLatitude(txnActionParams.getConfirmLatitude());
	 * txnEntity.setConfirmLongitude(txnActionParams.getConfirmLongitude());
	 * txnEntity.setModifiedBy(txnActionParams.getUserId());
	 * txnEntity.setModifiedOn(date);
	 * txnEntity.setGSTEnabled(txnActionParams.isGSTEnabled());
	 * usersBalanceEntity.setBalance(calculateMutualBalance(txnEntity,
	 * usersBalanceEntity)); usersBalanceEntity
	 * .setPendingTransactionAmount(calculatePendingTransactionAmount(txnEntity,
	 * usersBalanceEntity));
	 * usersBalanceEntity.setModifiedBy(txnActionParams.getUserId());
	 * usersBalanceEntity.setModifiedOn(date); session.save(txnEntity);
	 * session.save(usersBalanceEntity); return txnEntity; }
	 * 
	 * private UsersBalanceEntity getUserBalanceEntityForMutualBalance(int userId1,
	 * int userId2, Session session) { Criteria criteria =
	 * session.createCriteria(UsersBalanceEntity.class); Criterion cr1 =
	 * Restrictions.eq("user1", userId1); Criterion cr2 = Restrictions.eq("user2",
	 * userId2); Criterion cr3 = Restrictions.eq("user1", userId2); Criterion cr4 =
	 * Restrictions.eq("user2", userId1); LogicalExpression andExpression =
	 * Restrictions.and(cr1, cr2); LogicalExpression andExpression2 =
	 * Restrictions.and(cr3, cr4); LogicalExpression orExpression =
	 * Restrictions.or(andExpression, andExpression2); UsersBalanceEntity uBEntity =
	 * (UsersBalanceEntity) criteria.add(orExpression).uniqueResult(); return
	 * uBEntity; }
	 * 
	 * private BigDecimal calculateMutualBalance(TransactionsEntity txnEntity,
	 * UsersBalanceEntity usersBalanceEntity) { BigDecimal mutualBalance =
	 * usersBalanceEntity.getBalance(); if (txnEntity.getPayerId() ==
	 * usersBalanceEntity.getUser1()) { mutualBalance =
	 * txnEntity.getSkyCredit().add(mutualBalance); } else if
	 * (txnEntity.getPayerId() == usersBalanceEntity.getUser2() &&
	 * txnEntity.getSkyCredit().doubleValue() < 0) { mutualBalance =
	 * txnEntity.getSkyCredit().abs().add(mutualBalance); } else if
	 * (txnEntity.getPayerId() == usersBalanceEntity.getUser2() &&
	 * txnEntity.getSkyCredit().doubleValue() > 0) { mutualBalance =
	 * txnEntity.getSkyCredit().negate().add(mutualBalance); }
	 * getLogger().info("mutual balance after calculations : " + mutualBalance);
	 * return mutualBalance; }
	 * 
	 * private BigDecimal calculatePendingTransactionAmount(TransactionsEntity
	 * transactionsEntity, UsersBalanceEntity usersBalanceEntity) { int payerId =
	 * transactionsEntity.getPayerId(); BigDecimal pendingAmount =
	 * usersBalanceEntity.getPendingTransactionAmount(); BigDecimal skyCredit =
	 * transactionsEntity.getSkyCredit(); if (payerId ==
	 * usersBalanceEntity.getUser1()) { pendingAmount =
	 * pendingAmount.subtract(skyCredit); } else if (payerId ==
	 * usersBalanceEntity.getUser2()) { pendingAmount =
	 * pendingAmount.add(skyCredit); }
	 * getLogger().info("pendingAmount after calculation : " + pendingAmount);
	 * return pendingAmount; }
	 * 
	 * private TransactionsEntity getTxnWithAssociatedId(long payId, Session
	 * session) { TransactionsEntity entity = (TransactionsEntity)
	 * session.get(TransactionsEntity.class, payId); return entity; }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<UsersBalanceEntity> getAllUserBalanceEntitiesWithUserId(int userId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UsersBalanceEntity.class);
		Criterion cr1 = Restrictions.eq("user1", userId);
		Criterion cr2 = Restrictions.eq("user2", userId);
		LogicalExpression orExp = Restrictions.or(cr1, cr2);
		List<UsersBalanceEntity> entityList = (List<UsersBalanceEntity>) criteria.add(orExp).list();
		return entityList;
	}

	public LogicalExpression getLogicalExpressionForAllPendingTransactionsWithUserId(int userId) {
		LogicalExpression orExp = Restrictions.or(Restrictions.eq("payerId", userId),
				Restrictions.eq("payeeId", userId));
		LogicalExpression andExp = Restrictions.and(orExp, Restrictions.eq("paymentStatus", "P"));
		return andExp;
	}

	@Override
	public long getPendingRequestsCount(int userId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(TransactionsEntity.class);
		LogicalExpression andExp = getLogicalExpressionForAllPendingTransactionsWithUserId(userId);
		Long count = (Long) criteria.add(andExp).setProjection(Projections.rowCount()).uniqueResult();
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getMutualBalanceListWithUsersInfo(int userId, int pageSize) {

		StringBuilder hql = new StringBuilder("SELECT U,UB,(Select p.blobId from ProfileImageEntity p  where p.id = "
				+ "(select Max(p1.id) from ProfileImageEntity p1 where p1.userId = U.id))"
				+ " FROM UserEntity U,UsersBalanceEntity UB WHERE "
				+ " U.id=(CASE WHEN UB.user1=:uId THEN UB.user2 ELSE UB.user1 END ) AND ");
		if (isTestUser()) {
			hql.append(" U.isTestUser=:isTestUser ");
		} else {
			hql.append(" U.isTestUser <> :isTestUser ");
		}
		hql.append(" AND (UB.user1=:uId OR UB.user2=:uId) AND UB.transacted=:isTransacted order by UB.modifiedOn desc");

		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString())/*
																					 * .setParameter("bal", new
																					 * BigDecimal(0.00))
																					 */
				.setParameter("uId", userId).setParameter("isTestUser", true).setParameter("isTransacted", true);
		query.setFirstResult(getPageSize(pageSize)).setMaxResults(maxResultForToPayToReceiveList);
		List<Object[]> dataList = query.list();
		return dataList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionsEntity> fetchTransactionsForCashInOutCalc(int userId) {
		String hql = "SELECT t.payId,t.payerId,t.payeeId,t.cashReceived From TransactionsEntity t Where "
				+ "DATE(t.paymentDate) =:date AND (t.payerId =:id OR t.payeeId =:id) AND t.paymentStatus <> :status ";

		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
		Date queryDate = java.sql.Date.valueOf(date);
		// System.out.println("====== " + queryDate);
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("date", queryDate)
				.setParameter("id", userId).setParameter("status", "R");
		List<Object[]> list = query.list();
		if (list == null) {
			return null;
		}
		List<TransactionsEntity> entityList = new ArrayList<TransactionsEntity>();
		for (Object[] row : list) {
			TransactionsEntity entity = new TransactionsEntity();
			entity.setPayId(Long.parseLong(row[0].toString()));
			entity.setPayerId(Integer.parseInt(row[1].toString()));
			entity.setPayeeId(Integer.parseInt(row[2].toString()));
			entity.setCashReceived(new BigDecimal(row[3].toString()));
			entityList.add(entity);
		}
		return entityList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> pendingTransactions(final int userId, int pageSize) {

		String hql = "SELECT T,U,(SELECT p.blobId FROM ProfileImageEntity p WHERE p.id ="
				+ " (SELECT MAX(p1.id) FROM ProfileImageEntity p1 WHERE p1.userId = U.id)) ,UB"
				+ "	FROM TransactionsEntity T, UserEntity U, UsersBalanceEntity UB	 WHERE (T.payerId =:id OR T.payeeId =:id) AND T.paymentStatus =:status"
				+ " AND U.id =(CASE WHEN T.payerId =:id THEN T.payeeId ELSE T.payerId END) "
				+ " AND ((UB.user1=T.payeeId AND UB.user2=T.payerId) OR (UB.user1=T.payerId AND UB.user2=T.payeeId))"
				+ " ORDER BY T.payId DESC ";

		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("status", "p").setParameter("id",
				userId);
		query.setFirstResult(getPageSize(pageSize));
		query.setMaxResults(maxResult);

		List<Object[]> list = query.list();
		return list;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionsEntity> getLedger(final int loggedInUserId, final int oppositeUserId, final int pageSize) {
		Session session = sessionFactory.getCurrentSession();
		/* getLogger().info(loggedInUserId + " " + oppositeUserId); */
		Criteria criteria = session.createCriteria(TransactionsEntity.class);
		Criterion loggedInOrPayee = Restrictions.eq("payeeId", loggedInUserId);
		Criterion loggedInOrPayer = Restrictions.eq("payerId", loggedInUserId);
		Criterion oppositeIdOrPayee = Restrictions.eq("payeeId", oppositeUserId);
		Criterion oppositeIdOrPayer = Restrictions.eq("payerId", oppositeUserId);
		LogicalExpression orExpLoggedInUser = Restrictions.or(loggedInOrPayee, loggedInOrPayer);
		LogicalExpression orExpOppositeInUser = Restrictions.or(oppositeIdOrPayee, oppositeIdOrPayer);
		LogicalExpression andExp = Restrictions.and(orExpLoggedInUser, orExpOppositeInUser);
		criteria.addOrder(Order.desc("payId"));
		criteria.setFirstResult(getPageSize(pageSize));
		criteria.setMaxResults(maxResult);
		List<TransactionsEntity> entityList = (List<TransactionsEntity>) criteria.add(andExp).list();
		/* getLogger().info("Entity size" + entityList.size()); */
		return entityList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> fetchRecentUsers(int userId, int pageSize) {
		isTestUser = isTestUser();

		StringBuilder hql = new StringBuilder("SELECT U,UB FROM UserEntity U,UsersBalanceEntity UB"
				+ " WHERE U.id =(CASE WHEN UB.user1 =:id THEN UB.user2 ELSE UB.user1 END) AND ");

		if (isTestUser) {
			hql.append("U.isTestUser=:isTestUser ");
		} else {
			hql.append("U.isTestUser <> :isTestUser ");
		}
		hql.append(
				"AND (UB.user1 =:id OR UB.user2 =:id) AND UB.transacted=:isTransacted AND U.isActive =:active ORDER BY UB.modifiedOn DESC");
		Query query = sessionFactory.getCurrentSession().createQuery(hql.toString()).setParameter("isTestUser", true)
				.setParameter("active", true).setParameter("isTransacted", true);
		query.setParameter("id", userId);
		query.setFirstResult(getPageSize(pageSize));
		query.setMaxResults(maxResult);
		List<Object[]> list = (List<Object[]>) query.list();
		return list;

	}

	@SuppressWarnings("unchecked")
	public List<ItemMasterEntity> fetchItem(int userId) {

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ItemMasterEntity.class)
				.add(Restrictions.eq("userId", userId))
				.setProjection(Projections.distinct(Projections.property("itemName")));

		List<String> listOfItems = (List<String>) criteria.list();
		List<ItemMasterEntity> list = new ArrayList<>();
		for (String item : listOfItems) {
			ItemMasterEntity entity = new ItemMasterEntity();
			entity.setItemName(item);
			list.add(entity);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionsEntity> getDateWiseTransaction(int userId, String dateInString) {
		String hql = "from TransactionsEntity te WHERE DATE(te.paymentDate) =:dateValue "
				+ "AND te.payeeId =:uId AND te.isGSTEnabled =:enable AND te.paymentStatus <> :status";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		/* System.out.println("date value ===="+dateInString); */
		Date date = java.sql.Date.valueOf(dateInString);
		query.setParameter("dateValue", date).setParameter("uId", userId).setParameter("enable", true)
				.setParameter("status", "R");
		List<TransactionsEntity> entityList = (List<TransactionsEntity>) query.list();
		/* getLogger().info("list size : " + entityList.size()); */
		return entityList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionsEntity> getMonthWiseTransaction(int userId, int month, int year) {
		String hql = "FROM TransactionsEntity te WHERE MONTH(te.paymentDate) =:mon AND YEAR(te.paymentDate) =:yr "
				+ "AND te.payeeId =:uId AND te.isGSTEnabled =:enable AND te.paymentStatus <> :status";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("mon", month).setParameter("yr", year).setParameter("enable", true)
				.setParameter("uId", userId).setParameter("status", "R");
		List<TransactionsEntity> list = (List<TransactionsEntity>) query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransactionsEntity> getQuarterlyTransaction(int userId, int fromMonth, int toMonth, int year) {
		String hql = "FROM TransactionsEntity te WHERE MONTH(te.paymentDate) BETWEEN :fMon AND :tMon AND YEAR(te.paymentDate) =:yr "
				+ "AND te.payeeId =:uId AND te.isGSTEnabled =:enable AND te.paymentStatus <> :status";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("fMon", fromMonth).setParameter("tMon", toMonth).setParameter("yr", year)
				.setParameter("enable", true).setParameter("uId", userId).setParameter("status", "R");
		return (List<TransactionsEntity>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllTransactions(String txnType, int pageSize, int length, String search,
			com.saifintex.web.dto.TransactionsDTO transactionsWebDto) {

		String hql = null;
		Query query = null;
		if (txnType != null && !txnType.isEmpty() && txnType.equalsIgnoreCase("P")) {
			hql = getHql(txnType, search, transactionsWebDto, false);
			/* getLogger().info("DAO Transaction==HQL==PP**=="+hql); */
			query = sessionFactory.getCurrentSession().createQuery(hql);
			setQueryParametersToGetAllTransactions(transactionsWebDto, query);
			query.setParameter("paymentStatus", txnType);
			query.setFirstResult(pageSize);
			query.setMaxResults(length);
			List<Object[]> list = (List<Object[]>) query.list();
			return list;
		}
		hql = getHql(txnType, search, transactionsWebDto, false);
		/* getLogger().info("DAO Transaction==HQL==**ALL=="+hql); */
		query = sessionFactory.getCurrentSession().createQuery(hql);
		setQueryParametersToGetAllTransactions(transactionsWebDto, query);
		query.setFirstResult(pageSize);
		query.setMaxResults(length);
		List<Object[]> list = (List<Object[]>) query.list();
		return list;

	}

	private void setQueryParametersToGetAllTransactions(TransactionsDTO transactionsWebDto, Query query) {

		if (transactionsWebDto.getPayeeName() != null) {
			query.setParameter("payeeName", "%" + transactionsWebDto.getPayeeName() + "%");
		}
		if (transactionsWebDto.getPayerName() != null) {
			query.setParameter("payerName", "%" + transactionsWebDto.getPayerName() + "%");
		}
		if (transactionsWebDto.getTransactionId() != null) {

			query.setParameter("transactionId", "%" + transactionsWebDto.getTransactionId() + "%");
		}
		if (transactionsWebDto.getCashReceived() != null) {
			query.setParameter("cashReceived", transactionsWebDto.getCashReceived());
		}
		if (transactionsWebDto.getSkyCredit() != null) {
			query.setParameter("skyCredit", transactionsWebDto.getSkyCredit());
			query.setParameter("negativCredit", transactionsWebDto.getSkyCredit().negate());

		}
		if (transactionsWebDto.getPaymentStatus() != null) {
			query.setParameter("paymentStatus", transactionsWebDto.getPaymentStatus());
		}
		if (transactionsWebDto.getBillAmount() != null) {
			query.setParameter("billAmount", transactionsWebDto.getBillAmount());
		}
		if (transactionsWebDto.getModifiedOn() != null) {
			/*
			 * String date = new
			 * SimpleDateFormat("yyyy-MM-dd").format(transactionsWebDto.getModifiedOn());
			 * Date queryDate = java.sql.Date.valueOf(date); query.setTimestamp("date",
			 * queryDate);
			 */
		}
		if (transactionsWebDto.getCreatedOn() != null) {
			/*
			 * String date = new
			 * SimpleDateFormat("yyyy-MM-dd").format(transactionsWebDto.getCreatedOn());
			 * Date queryDate = java.sql.Date.valueOf(date); query.setTimestamp("date",
			 * queryDate);
			 */
		}

		if (transactionsWebDto.getGstEnabled() != null) {
			query.setParameter("gstEnable", transactionsWebDto.getGstEnabled());
		}
		if (transactionsWebDto.getTransactionType() != null) {
			query.setParameter("userType", transactionsWebDto.getUserType());
		}
		
		if (transactionsWebDto.getUserType() != null) {
			query.setParameter("userType", transactionsWebDto.getUserType());
		}
	}

	private String getHql(String txnType, String search, com.saifintex.web.dto.TransactionsDTO transactionsWebDto,
			boolean isCallingMethodTransactionCount) {
		StringBuilder builder = new StringBuilder("SELECT T,");
		if (isCallingMethodTransactionCount) {
			builder.append("Count(T.payId),");
		}
		if (txnType != null && !txnType.isEmpty() && txnType.equalsIgnoreCase("P")) {
			builder.append("(SELECT U.firstName FROM UserEntity U WHERE U.id=T.payerId)"
					+ " AS PayerName,(SELECT U.firstName FROM UserEntity U WHERE U.id=T.payeeId)"
					+ " AS PayeeName FROM TransactionsEntity T WHERE T.paymentStatus=:paymentStatus and ");
		} else {
			builder.append("(SELECT U.firstName FROM UserEntity U WHERE U.id=T.payerId) AS PayerName, "
					+ " (SELECT U.firstName FROM UserEntity U WHERE U.id=T.payeeId) AS PayeeName FROM TransactionsEntity T where ");

		}
		if (transactionsWebDto.getPayerName() != null) {
			builder.append(" T.payerId IN(SELECT u.id FROM UserEntity u WHERE u.firstName LIKE :payerName) and ");
		}
		if (transactionsWebDto.getPayeeName() != null) {
			builder.append(" T.payeeId IN(SELECT u.id FROM UserEntity u WHERE u.firstName LIKE :payeeName) and ");
		}

		if (transactionsWebDto.getTransactionId() != null) {
			builder.append(" T.transactionId LIKE :transactionId and ");
		}
		if (transactionsWebDto.getCashReceived() != null) {
			builder.append(" T.cashReceived=:cashReceived and ");
		}
		if (transactionsWebDto.getSkyCredit() != null) {
			builder.append(" (T.skyCredit=:skyCredit or T.skyCredit=:negativCredit) and ");
		}
		if (transactionsWebDto.getPaymentStatus() != null) {
			builder.append(" T.paymentStatus=:paymentStatus and ");
		}
		if (transactionsWebDto.getBillAmount() != null) {
			builder.append(" T.billAmount=:billAmount and ");
		}
		if (transactionsWebDto.getModifiedOn() != null) {
			String date = new SimpleDateFormat("yyyy-MM-dd").format(transactionsWebDto.getModifiedOn());

			builder.append(" T.modifiedOn LIKE '%" + date + "%'" + " and ");
		}
		if (transactionsWebDto.getCreatedOn() != null) {
			String date = new SimpleDateFormat("yyyy-MM-dd").format(transactionsWebDto.getCreatedOn());

			builder.append(" T.createdOn LIKE '%" + date + "%'" + " and ");
		}
		if (transactionsWebDto.getUserType() != null) {
			builder.append(" T.userType=:userType " + " and ");
		}

		if (transactionsWebDto.getGstEnabled() != null) {
			builder.append(" T.isGSTEnabled=:gstEnable " + " and ");
		}
		if (builder.toString()
				.equalsIgnoreCase("SELECT T,(SELECT U.firstName FROM UserEntity U WHERE U.id=T.payerId) AS PayerName, "
						+ " (SELECT U.firstName FROM UserEntity U WHERE U.id=T.payeeId) AS PayeeName FROM TransactionsEntity T where ")) {
			builder.setLength(0);
			builder.append("SELECT T,(SELECT U.firstName FROM UserEntity U WHERE U.id=T.payerId) AS PayerName, "
					+ " (SELECT U.firstName FROM UserEntity U WHERE U.id=T.payeeId) AS PayeeName FROM TransactionsEntity T ");
		} else if (builder.toString()
				.equalsIgnoreCase("SELECT T,(SELECT U.firstName FROM UserEntity U WHERE U.id=T.payerId)"
						+ " AS PayerName,(SELECT U.firstName FROM UserEntity U WHERE U.id=T.payeeId)"
						+ " AS PayeeName FROM TransactionsEntity T WHERE T.paymentStatus=:paymentStatus and ")) {
			builder.setLength(0);
			builder.append("SELECT T,(SELECT U.firstName FROM UserEntity U WHERE U.id=T.payerId) AS PayerName, "
					+ " (SELECT U.firstName FROM UserEntity U WHERE U.id=T.payeeId) AS PayeeName FROM TransactionsEntity T WHERE T.paymentStatus=:paymentStatus ");
		} else if (builder.toString().equalsIgnoreCase(
				"SELECT T,Count(T.payId),(SELECT U.firstName FROM UserEntity U WHERE U.id=T.payerId) AS PayerName, "
						+ " (SELECT U.firstName FROM UserEntity U WHERE U.id=T.payeeId) AS PayeeName FROM TransactionsEntity T where ")
				|| builder.toString()
						.equalsIgnoreCase("SELECT T,(SELECT U.firstName FROM UserEntity U WHERE U.id=T.payerId)"
								+ " AS PayerName,(SELECT U.firstName FROM UserEntity U WHERE U.id=T.payeeId)"
								+ " AS PayeeName FROM TransactionsEntity T WHERE T.paymentStatus=:paymentStatus and ")) {
			builder.setLength(0);
			builder.append(
					"SELECT T,Count(T.payId),(SELECT U.firstName FROM UserEntity U WHERE U.id=T.payerId) AS PayerName, "
							+ " (SELECT U.firstName FROM UserEntity U WHERE U.id=T.payeeId) AS PayeeName FROM TransactionsEntity T ");
		} else {
			String query = builder.toString().substring(0, builder.toString().length() - 5);
			builder.setLength(0);
			builder.append(query);
		}
		builder.append(" ORDER BY T.payId DESC");
		return builder.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public TransactionsRecordDto getTransactionsRecord() {
		String hql = "SELECT  COUNT(T.payId) as totalTransactios ," + "	SUM(T.billAmount) as totalAmount  ,"
				+ "	SUM(T.skyCredit) as totalCreditFlow ," + "	SUM(T.cashReceived) as totalCashFlow ,"
				+ "	 COUNT(CASE WHEN T.paymentStatus='A' THEN T.paymentStatus END) as acceptedTransactions,"
				+ "	 COUNT(CASE WHEN T.paymentStatus='R' THEN T.paymentStatus END) as rejectedTransactions ,"
				+ "	 COUNT(CASE WHEN T.paymentStatus='P' THEN T.paymentStatus END) as pendingTransactins ,"
				+ "	 COUNT(CASE WHEN T.paymentStatus='C' THEN T.paymentStatus END) as closedTransactions,"
				+ "	 COUNT(CASE WHEN T.userType='T' THEN T.userType END) as tempTransactions, "
				+ "  Count(Case When T.transactionType =:business Then T.transactionType END) AS businessTransactions,"
				+ "  Count(Case When T.transactionType =:friend Then T.transactionType END) AS friendTransactions  "
				+ "	 " + "  FROM TransactionsEntity T";
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("business", "Business")
				.setParameter("friend", "Friend")
				.setResultTransformer(Transformers.aliasToBean(TransactionsRecordDto.class));

		TransactionsRecordDto dto = (TransactionsRecordDto) query.uniqueResult();
		return dto;

	}

	@Override
	public TransactionStatsDashboardDTO getTransactionStats() {
		stringBuilder = new StringBuilder();
		String hql = stringBuilder.append("Select Count(t.payId) as totalTrans, ")
				.append("SUM(t.billAmount) as totalTransAmount, ")
				.append(" Count(Case When t.paymentStatus =:status Then t.paymentStatus END) AS totalPendingTrans, ")
				.append(" SUM(Case When t.paymentStatus =:status Then t.billAmount END) AS totalPendingTransAmount, ")
				.append(" Count(Case When t.transactionType =:business Then t.transactionType END) AS totalBusinessTransaction, ")
				.append(" Count(Case When t.transactionType =:friend Then t.transactionType END) AS totalFriendTransactons ")
				.append("from TransactionsEntity t").toString();
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("status", paymentStatus)
				.setParameter("business", "Business").setParameter("friend", "Friend")
				.setResultTransformer(Transformers.aliasToBean(TransactionStatsDashboardDTO.class));
		TransactionStatsDashboardDTO transStats = (TransactionStatsDashboardDTO) query.uniqueResult();
		/* getLogger().info(transStats.getTotalPendingTransAmount()); */
		return transStats;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long getTransactionsCount(String txnType, String search, TransactionsDTO transactionsWebDto) {
		String hql = getHql(txnType, search, transactionsWebDto, true);
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		setQueryParametersToGetAllTransactions(transactionsWebDto, query);
		if (txnType.equalsIgnoreCase("P")) {
			query.setParameter("paymentStatus", txnType);
		}
		List<Object[]> list = query.list();
		long count = 0;
		if (list.size() > 0) {
			Object[] obj = list.get(0);
			count = (Long) obj[1];
		}

		return count;
	}

	@Override
	public TransactionSummaryParamsDto transactionSummary(int loggedInUserId, int opponentUserId) {
		stringBuilder = new StringBuilder();
		String hql = stringBuilder
				.append(" SELECT SUM(CASE WHEN T.payerId=:loggedInUserId THEN T.billAmount END) AS totalPurBillAmount,")
				.append("SUM(CASE WHEN T.payerId=:loggedInUserId THEN T.cashReceived END) AS totalIPaid, ")
				.append("SUM(CASE WHEN T.payeeId=:loggedInUserId THEN T.billAmount END) AS totalSalBillAmount, ")
				.append("SUM(CASE WHEN T.payeeId=:loggedInUserId THEN T.cashReceived END) AS totalIReceived,  ")
				.append("(IFNULL(SUM(CASE WHEN T.payerId=:loggedInUserId THEN T.skyCredit END), 0) - IFNULL(SUM(CASE WHEN T.payeeId=:loggedInUserId THEN T.skyCredit END), 0)) AS netBalance ")
				.append("FROM TransactionsEntity T WHERE (T.payeeId=:loggedInUserId OR T.payerId=:loggedInUserId) AND (T.payeeId=:opponentUserId OR T.payerId=:opponentUserId) AND T.paymentStatus <> :rejected ")
				.toString();
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("loggedInUserId", loggedInUserId)
				.setParameter("opponentUserId", opponentUserId).setParameter("rejected", "R")
				.setResultTransformer(Transformers.aliasToBean(TransactionSummaryParamsDto.class));
		TransactionSummaryParamsDto transSummary = (TransactionSummaryParamsDto) query.uniqueResult();
		/* getLogger().info(transSummary.getTotalIReceived()); */
		return transSummary;
	}

	private boolean isTestUser() {
		if (getPrincipal() == null) {
			return false;
		}
		UserEntity entity = findByUserLogin(getPrincipal());
		if (entity == null) {
			return false;
		}
		return entity.isTestUser();
	}

	/*
	 * private boolean isShowTestUser() { return
	 * getAppPreferences(1).isShowTestUsers(); }
	 */

	@Override
	public TransactionStatsDashboardDTO getSpecificUserTransactionStats(int uId) {
		stringBuilder = new StringBuilder();
		String hql = stringBuilder.append("Select Count(t.payId) as totalTrans, ")
				.append("SUM(t.billAmount) as totalTransAmount, ")
				.append("Count(Case When t.paymentStatus =:status Then t.paymentStatus END) AS totalPendingTrans, ")
				.append("Count(Case When t.paymentStatus =:acceptedStatus Then t.paymentStatus END) AS totalAccepted, ")
				.append("Count(Case When t.paymentStatus =:rejectedStatus Then t.paymentStatus END) AS totalRejected, ")

				.append("SUM(Case When t.paymentStatus =:status Then t.billAmount END) AS totalPendingTransAmount, ")
				.append(" Count(Case When t.transactionType =:business Then t.transactionType END) AS totalBusinessTransaction, ")
				.append(" Count(Case When t.transactionType =:friend Then t.transactionType END) AS totalFriendTransactons, ")
				.append(" (SELECT MAX(T.createdOn) FROM TransactionsEntity T WHERE T.initiatedFrom=:initiatedFrom) AS lastTransacted  ")
				.append("from TransactionsEntity t Where t.payerId =:userId OR t.payeeId =:userId").toString();
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("status", paymentStatus)
				.setParameter("userId", uId).setParameter("business", "Business").setParameter("friend", "Friend")
				.setParameter("acceptedStatus", "A").setParameter("rejectedStatus", "R")
				.setParameter("initiatedFrom", uId)
				.setResultTransformer(Transformers.aliasToBean(TransactionStatsDashboardDTO.class));
		TransactionStatsDashboardDTO transStats = (TransactionStatsDashboardDTO) query.uniqueResult();
		return transStats;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAllTodayTransactions(int userId, int pageSize, String type) {
		isTestUser = isTestUser();
		String checkPayerOrPayee = "", checkOpponentId = "";
		if (type.equalsIgnoreCase("A")) {
			checkPayerOrPayee = "(t.payerId =:id or t.payeeId =:id)";
			checkOpponentId = "(CASE WHEN t.payerId =:id THEN t.payeeId ELSE t.payerId END)";
		} else if (type.equalsIgnoreCase("P")) {
			checkPayerOrPayee = "t.payerId =:id";
			checkOpponentId = "t.payeeId";
		} else if(type.equalsIgnoreCase("R")) {
			checkPayerOrPayee = "t.payeeId =:id";
			checkOpponentId = "t.payerId";
		}else if(type.equalsIgnoreCase("BA")) {
			checkPayerOrPayee = "(t.payerId =:id or t.payeeId =:id)";
			checkOpponentId = "(CASE WHEN t.payerId =:id THEN t.payeeId ELSE t.payerId END)";
		}else if(type.equalsIgnoreCase("BP")) {
			checkPayerOrPayee = "t.payerId =:id";
			checkOpponentId = "t.payeeId";
		}else {
			checkPayerOrPayee = "t.payeeId =:id";
			checkOpponentId = "t.payerId";
		}
		stringBuilder = new StringBuilder(
				"Select t,u,ub from TransactionsEntity t,UserEntity u,UsersBalanceEntity ub Where ");
		stringBuilder.append(checkPayerOrPayee);
		if(type.contains("B")) {
			stringBuilder.append(" AND t.transactionType=:transactionType");
		}
		stringBuilder.append(" AND DATE(t.paymentDate) = CURDATE()");
		stringBuilder.append(" AND u.id =" + checkOpponentId);
		if (isTestUser) {
			stringBuilder.append(" AND u.isTestUser=:isTestUser ");
		} else {
			stringBuilder.append(" AND u.isTestUser <> :isTestUser ");
		}
		
		
		stringBuilder.append(
				" AND ((ub.user1=t.payeeId AND ub.user2=t.payerId) OR (ub.user1=t.payerId AND ub.user2=t.payeeId))");
		stringBuilder.append(" ORDER BY t.payId desc");
		Query query = sessionFactory.getCurrentSession().createQuery(stringBuilder.toString())
				.setParameter("id", userId).setParameter("isTestUser", true).setFirstResult(getPageSize(pageSize))
				.setMaxResults(maxResult);
		if(type.contains("B")) {
			query.setParameter("transactionType", "Business");
		}
		return query.list();
	}

	@Override
	public List<Object[]> filterTransactions(TransactionsFilterParams params, int pageSize) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		StringBuilder hql = new StringBuilder(
				"SELECT U,T,UB " + "FROM UserEntity U,TransactionsEntity T,UsersBalanceEntity UB WHERE "
						+ "U.id=(CASE WHEN T.payerId=:UserID THEN T.payeeId ELSE T.payerId END ) and ");

		if (isTestUser()) {
			hql.append("U.isTestUser=:isTestUser and ");
		} else {
			hql.append("U.isTestUser <> :isTestUser and ");
		}
		hql.append(" (T.payerId=:UserID OR T.payeeId=:UserID) and ");
		setTransactionFilterHql(params, hql);
		// String filterQuery=convertToFilterQuery(hql);
		hql.append(" and ((UB.user1=T.payeeId AND UB.user2=T.payerId) OR (UB.user1=T.payerId AND UB.user2=T.payeeId))");
		hql.append(" ORDER BY T.payId desc");

		System.out.println("hql after at alll======" + hql);
		query = session.createQuery(hql.toString());
		setQueryParametersForFilter(params, query);
		query.setParameter("UserID", params.getLoggedInUserId());
		query.setParameter("isTestUser", true);
		query.setFirstResult(pageSize);
		query.setMaxResults(maxResult);
		List<Object[]> list = query.list();

		return list;
	}

	private void setTransactionFilterHql(TransactionsFilterParams params, StringBuilder hql) {
		if (params.isGst()) {
			hql.append(" T.isGSTEnabled=:isGstEnabled and ");
		}
		if (params.isBusiness()) {
			hql.append(" T.transactionType=:buisness and ");
		}
		if (params.isFriend()) {
			hql.append(" T.transactionType=:friend and");
		}
		if (params.isBalancing()) {
			hql.append(" T.transactionType=:balancing and ");
		}
		if (params.getDateRange() != null && !params.getDateRange().isEmpty()) {
			if (params.getDateRange().equalsIgnoreCase("oneweek")) {
				// hql.append("T.createdOn >= DATE(NOW()) - INTERVAL 7 DAY and ");
				hql.append(" T.createdOn >='" + getDateRangeFilter("oneweek") + "' and ");
			} else if (params.getDateRange().equalsIgnoreCase("twoweek")) {
				hql.append(" T.createdOn >='" + getDateRangeFilter("twoweek") + "' and ");
			} else if (params.getDateRange().equalsIgnoreCase("onemonth")) {
				hql.append(" T.createdOn >='" + getDateRangeFilter("onemonth") + "' and ");
			} else {
				hql.append(" T.createdOn >='" + getDateRangeFilter("twomonth") + "' and ");
			}
		}

		if (params.isAccepted()) {
			if (params.isRejected() || params.isPending() || params.isPendingOnMe() || params.isPendingOnOther()
					|| params.isRejectedByMe() || params.isRejectedByOther()) {
				hql.append(" T.paymentStatus=:accepted" + " " + " or ");
			} else {
				hql.append(" T.paymentStatus=:accepted and ");
			}
		}

		if (params.isRejected()) {
			if (params.isAccepted() || params.isPending() || params.isPendingOnMe() || params.isPendingOnOther()
					|| params.isAcceptedByMe() || params.isAcceptedByOthers()) {
				hql.append(" T.paymentStatus=:rejected" + " " + " or ");
			} else {
				hql.append(" T.paymentStatus=:rejected and ");
			}
		}

		if (params.isPending()) {
			if (params.isAccepted() || params.isRejected() || params.isRejectedByMe() || params.isRejectedByOther()
					|| params.isAcceptedByMe() || params.isAcceptedByOthers()) {
				hql.append(" T.paymentStatus=:pending" + " " + " or ");
			} else {
				hql.append(" T.paymentStatus=:pending and ");
			}
		}

		if (params.isAcceptedByMe()) {
			if (params.isPending() || params.isRejected() || params.isRejectedByMe() || params.isRejectedByOther()
					|| params.isPendingOnMe() || params.isPendingOnOther()) {
				hql.append(" (T.paymentStatus=:accepted AND T.initiatedFrom <>:acceptedUserId)" + " " + " or ");
			} else {
				hql.append(" (T.paymentStatus=:accepted AND T.initiatedFrom <>:acceptedUserId) and ");
			}

		}

		if (params.isAcceptedByOthers()) {
			if (params.isPending() || params.isRejected() || params.isRejectedByMe() || params.isRejectedByOther()
					|| params.isPendingOnMe() || params.isPendingOnOther()) {
				hql.append(" (T.paymentStatus=:accepted AND T.initiatedFrom=:acceptedUserId)" + " " + " or ");
			} else {
				hql.append(" (T.paymentStatus=:accepted AND T.initiatedFrom=:acceptedUserId) and ");
			}

		}

		if (params.isRejectedByMe()) {
			if (params.isAccepted() || params.isPending() || params.isPendingOnMe() || params.isPendingOnOther()
					|| params.isAcceptedByMe() || params.isAcceptedByOthers()) {
				hql.append(" (T.paymentStatus=:rejected AND T.initiatedFrom <>:rejectedUserId)" + " " + " or ");
			} else {
				hql.append(" (T.paymentStatus=:rejected AND T.initiatedFrom <>:rejectedUserId) and ");
			}

		}

		if (params.isRejectedByOther()) {
			if (params.isAccepted() || params.isPending() || params.isPendingOnMe() || params.isPendingOnOther()
					|| params.isAcceptedByMe() || params.isAcceptedByOthers()) {
				hql.append(" (T.paymentStatus=:rejected AND T.initiatedFrom=:rejectedUserId)" + " " + " or ");
			} else {
				hql.append(" (T.paymentStatus=:rejected AND T.initiatedFrom=:rejectedUserId) and ");
			}
		}

		if (params.isPendingOnMe()) {
			if (params.isAccepted() || params.isRejected() || params.isRejectedByMe() || params.isRejectedByOther()
					|| params.isAcceptedByMe() || params.isAcceptedByOthers()) {
				hql.append(" (T.paymentStatus=:pending AND T.initiatedFrom <>:pendingUserId)" + " " + " or ");
			} else {
				hql.append(" (T.paymentStatus=:pending AND T.initiatedFrom <>:pendingUserId) and ");
			}
		}

		if (params.isPendingOnOther()) {
			if (params.isAccepted() || params.isRejected() || params.isRejectedByMe() || params.isRejectedByOther()
					|| params.isAcceptedByMe() || params.isAcceptedByOthers()) {
				hql.append(" (T.paymentStatus=:pending AND T.initiatedFrom=:pendingUserId)" + " " + " or ");
			} else {
				hql.append(" (T.paymentStatus=:pending AND T.initiatedFrom <>:pendingUserId) and ");
			}
		}

		String query = convertToFilterQuery(hql);

		System.out.println("query=========" + query);
		hql.setLength(0);
		hql.append(query);

		System.out.println("comming from all conditions appending all brackets=========" + query);
		query = null;
	}

	private void setQueryParametersForFilter(TransactionsFilterParams params, Query query) {
		if (params.isGst()) {

			query.setParameter("isGstEnabled", true);
		}
		if (params.isBusiness()) {

			query.setParameter("buisness", "Business");
		}
		if (params.isFriend()) {

			query.setParameter("friend", "Friend");
		}
		/*
		 * if(params.getDateRange()!=null&& !params.getDateRange().isEmpty()) {
		 * if(params.getDateRange().equalsIgnoreCase("oneweek")) { Calendar cal=
		 * getDateRangeFilter("oneweek");
		 * 
		 * System.out.println("min time=="+dateFormat); query.setParameter("minDate",
		 * dateFormat); } }
		 */
		if (params.isBalancing()) {

			query.setParameter("balancing", "Balancing");
		}
		if (params.isAccepted()) {

			query.setParameter("accepted", "A");
		}

		if (params.isRejected()) {

			query.setParameter("rejected", "R");
		}

		if (params.isPending()) {

			query.setParameter("pending", "P");
		}

		if (params.isAcceptedByMe()) {

			query.setParameter("accepted", "A");
			query.setParameter("acceptedUserId", params.getLoggedInUserId());

		}

		if (params.isAcceptedByOthers()) {

			query.setParameter("accepted", "A");
			query.setParameter("acceptedUserId", params.getLoggedInUserId());

		}

		if (params.isRejectedByMe()) {

			query.setParameter("rejected", "R");
			query.setParameter("rejectedUserId", params.getLoggedInUserId());
		}

		if (params.isRejectedByOther()) {
			query.setParameter("rejected", "R");
			query.setParameter("rejectedUserId", params.getLoggedInUserId());
		}

		if (params.isPendingOnMe()) {
			query.setParameter("pending", "P");
			query.setParameter("pendingUserId", params.getLoggedInUserId());
		}

		if (params.isPendingOnOther()) {
			query.setParameter("pending", "P");
			query.setParameter("pendingUserId", params.getLoggedInUserId());
		}

	}

	private String convertToFilterQuery(StringBuilder hql) {
		System.out.println("Query before converted == == = =" + hql.toString());
		StringBuilder convertedHql = new StringBuilder();
		String[] splitedHql = hql.toString().trim().split("and");
		/*
		 * for(String splitedPart:splitedHql) {
		 * convertedHql.append("("+splitedPart+") and "); }
		 */
		convertedHql.append(splitedHql[0] + " and ");
		for (int i = 1; i < splitedHql.length - 1; i++) {

			convertedHql.append("(" + splitedHql[i] + ") and ");

		}
		convertedHql.append("(" + splitedHql[splitedHql.length - 1] + ")");
		System.out.println("Query just before converted == == = =" + convertedHql.toString());
		String query = null;
		if (convertedHql.toString().trim().endsWith("or)")) {
			query = convertedHql.toString().substring(0, convertedHql.toString().length() - 5) + ")";
		} else {
			query = convertedHql.toString();
		}
		convertedHql.setLength(0);
		convertedHql.append(query);

		System.out.println("converted hql ====" + convertedHql.toString());
		return convertedHql.toString();
	}

	private String getDateRangeFilter(String dateRange) {
		Calendar minDate = Calendar.getInstance();
		if (dateRange.equalsIgnoreCase("oneweek")) {
			minDate.add(Calendar.DATE, -7);
		} else if (dateRange.equalsIgnoreCase("twoweek")) {
			minDate.add(Calendar.DATE, -14);
		} else if (dateRange.equalsIgnoreCase("onemonth")) {
			minDate.add(Calendar.DATE, -30);
		} else {
			minDate.add(Calendar.DATE, -60);
		}
		DateFormat fromat = new SimpleDateFormat("yyyy-MM-dd");

		String dateFormat = fromat.format(minDate.getTime());
		System.out.println("format===" + dateFormat);
		return dateFormat;
	}

	@Override
	public List getAllAssociatedUsers(int userId) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder hql = new StringBuilder(
				"SELECT U,UB,(SELECT p.blobId FROM ProfileImageEntity p WHERE p.id=(SELECT MAX(p1.id) "
						+ " FROM ProfileImageEntity p1 WHERE p1.userId=U.id)) "
						+ " FROM UserEntity U, UsersBalanceEntity UB WHERE"
						+ " U.id=(CASE WHEN UB.user1=:UserID THEN UB.user2 ELSE UB.user1 END) AND ");
		if (isTestUser) {
			hql.append("U.isTestUser=:isTestUser "
					+ " AND (UB.user1=:UserID OR UB.user2=:UserID) ORDER BY UB.modifiedOn DESC");
		} else {
			hql.append("U.isTestUser<>:isTestUser "
					+ " AND (UB.user1=:UserID OR UB.user2=:UserID) ORDER BY UB.modifiedOn DESC");

		}
		Query query = session.createQuery(hql.toString()).setParameter("UserID", userId).setParameter("isTestUser",
				true);

		List<Object[]> list = query.list();
		return list;

	}

	
	/**
	 * @param date
	 *            :- when there is current date or yesterday date then this param
	 *            will have values otherwise null in case of date ranges
	 * @param startDate
	 *            :- when there is date range value then startDate will have start
	 *            date value
	 * @param endDate
	 *            :- when there is date range value then endDate will have end date
	 *            value
	 * @param paymentStatus
	 *            :- represents payment status (A , P, R, C)
	 */

	@Override
	public long getTransactionsCount(Date date, Date startDate, Date endDate, String paymentStatus,
			boolean includeTestUsers) {
		String sql = null;
		Query query = null;
		Session session = sessionFactory.getCurrentSession();
		if (date != null) {
			if (includeTestUsers) {
				sql = "select Count(t.PayId) from Transactions t inner join Users u on t.PayerId = u.UserId"			 
						+"	inner join Users u2 on t.PayeeId = u2.UserId" 
						+" where Date(t.CreatedOn)=:d and t.PaymentStatus=:pStatus and u.isTestUser =:testUser and u2.isTestUser =:testUser";
				query = session.createSQLQuery(sql).setParameter("testUser", false);
			} else {
				sql = "select Count(t.PayId) from Transactions t inner join Users u on t.PayerId = u.`UserId`"
						+ "	inner join Users u2 on t.PayeeId = u2.`UserId`"
						+ " where Date(t.CreatedOn)=:d and t.PaymentStatus =:pStatus and (u.`IsActualUser` =:user or u2.IsActualUser =:user)";
				query = sessionFactory.getCurrentSession().createSQLQuery(sql).setParameter("user", true);
			}
			query.setParameter("d", date);
		} else {
			if (includeTestUsers) {				
				sql = "select Count(t.PayId) from Transactions t inner join Users u on t.PayerId = u.UserId"			 
						+"	inner join Users u2 on t.PayeeId = u2.UserId" 
						+" where Date(t.CreatedOn)" 
						+"	between :sDate and :eDate and t.PaymentStatus=:pStatus and u.isTestUser =:testUser and u2.isTestUser =:testUser";
				query = sessionFactory.getCurrentSession().createSQLQuery(sql).setParameter("testUser", false);

			} else {				
				sql = "select Count(t.PayId) from Transactions t inner join Users u on t.PayerId = u.`UserId`"
						+ "	inner join Users u2 on t.PayeeId = u2.`UserId`"
						+ " where Date(t.CreatedOn) between :sDate and :eDate and t.PaymentStatus =:pStatus and (u.`IsActualUser` =:user or u2.IsActualUser =:user)";
				query = sessionFactory.getCurrentSession().createSQLQuery(sql).setParameter("user", true);
			}
			query.setParameter("sDate", startDate).setParameter("eDate", endDate);
		}
		query.setParameter("pStatus", paymentStatus);
		BigInteger count = (BigInteger) query.uniqueResult();
		return count.longValue();
	}

	@Override
	public BigDecimal getTransactionsValue(Date date, Date startDate, Date endDate, String paymentStatus,
			boolean includeTestUsers) {
		String sql = null;
		Query query = null;
		Session session = sessionFactory.getCurrentSession();
		if (date != null) {
			if (includeTestUsers) {
				sql = "select SUM(t.CashReceived) from Transactions t inner join Users u on t.PayerId = u.UserId"			 
						+"	inner join Users u2 on t.PayeeId = u2.UserId" 
						+" where Date(t.CreatedOn)=:d and t.PaymentStatus=:pStatus and u.isTestUser =:testUser and u2.isTestUser =:testUser";
				query = session.createSQLQuery(sql).setParameter("testUser", false);

			} else {
				sql = "select SUM(t.CashReceived) from Transactions t inner join Users u on t.PayerId = u.`UserId`"
						+ "	inner join Users u2 on t.PayeeId = u2.`UserId`"
						+ " where Date(t.CreatedOn)=:d and t.PaymentStatus =:pStatus and (u.`IsActualUser` =:user or u2.IsActualUser =:user)";
				query = sessionFactory.getCurrentSession().createSQLQuery(sql).setParameter("user", true);
				
			}
			query.setParameter("d", date);

		} else {
			if (includeTestUsers) {
				sql = "select SUM(t.CashReceived) from Transactions t inner join Users u on t.PayerId = u.UserId"			 
						+"	inner join Users u2 on t.PayeeId = u2.UserId" 
						+" where Date(t.CreatedOn)" 
						+"	between :sDate and :eDate and t.PaymentStatus=:pStatus and u.isTestUser =:testUser and u2.isTestUser =:testUser";
				query = sessionFactory.getCurrentSession().createSQLQuery(sql).setParameter("testUser", false);				
			} else {
				sql = "select SUM(t.CashReceived) from Transactions t inner join Users u on t.PayerId = u.`UserId`"
						+ "	inner join Users u2 on t.PayeeId = u2.`UserId`"
						+ " where Date(t.CreatedOn) between :sDate and :eDate and t.PaymentStatus =:pStatus and (u.`IsActualUser` =:user or u2.IsActualUser =:user)";
				query = sessionFactory.getCurrentSession().createSQLQuery(sql).setParameter("user", true);
				query = session.createSQLQuery(sql);
				query.setParameter("user", true);
			}
			query.setParameter("sDate", startDate).setParameter("eDate", endDate);
		}
		query.setParameter("pStatus", paymentStatus);
		BigDecimal value = (BigDecimal) query.uniqueResult();
		if (value == null) {
			return new BigDecimal("0.00");
		}
		return value;
	}

	@Override
	public List<Transactions> getTransactionsList(Date startDate, Date endDate, String paymentStatus,
			boolean includeTestUsers) {
		Query query = null;
		Session session = sessionFactory.getCurrentSession();
		stringBuilder = new StringBuilder("Select u.FirstName as payerName,u2.FirstName as payeeName,"
				+ "t.BillAmount as billAmount,t.CashReceived as cashReceived,t.SkyCredit as skyCredit,"
				+ "t.IsGstEnabled as isGSTEnabled,t.TransactionType as transactionType,"
				+ "t.UserType as userType,t.PaymentStatus as paymentStatus,t.CreatedOn as createdOn");
		stringBuilder.append(" from Transactions t inner join Users u on t.PayerId = u.UserId");
		stringBuilder.append(" inner join Users u2 on t.PayeeId = u2.UserId");
		if (includeTestUsers) {
			if (paymentStatus == null) {
				if (startDate == null) {
					stringBuilder.append(
							" where Date(t.CreatedOn) = current_date() and u.isTestUser =:test and u2.isTestUser =:test");
					query = session.createSQLQuery(stringBuilder.toString());
				} else if (startDate != null && endDate == null) {
					stringBuilder
							.append(" where Date(t.CreatedOn) =:date and u.isTestUser =:test and u2.isTestUser =:test");
					query = session.createSQLQuery(stringBuilder.toString()).setParameter("date", startDate);
				} else {
					stringBuilder.append(
							" where Date(t.CreatedOn) between :sDate and :eDate and u.isTestUser =:test and u2.isTestUser =:test");
					query = session.createSQLQuery(stringBuilder.toString()).setParameter("sDate", startDate)
							.setParameter("eDate", endDate);
				}
				query.setParameter("test", false);
			}else {
				if (startDate == null) {
					stringBuilder.append(
							" where Date(t.CreatedOn) = current_date() and t.PaymentStatus =:pStatus and u.isTestUser =:test and u2.isTestUser =:test");
					query = session.createSQLQuery(stringBuilder.toString());
				} else if (startDate != null && endDate == null) {
					stringBuilder
							.append(" where Date(t.CreatedOn) =:date and t.PaymentStatus =:pStatus and u.isTestUser =:test and u2.isTestUser =:test");
					query = session.createSQLQuery(stringBuilder.toString()).setParameter("date", startDate);
				} else {
					stringBuilder.append(
							" where Date(t.CreatedOn) between :sDate and :eDate and t.PaymentStatus =:pStatus and u.isTestUser =:test and u2.isTestUser =:test");
					query = session.createSQLQuery(stringBuilder.toString()).setParameter("sDate", startDate)
							.setParameter("eDate", endDate);
				}
				query.setParameter("test", false).setParameter("pStatus", paymentStatus);
			}
		} else {
			if(paymentStatus==null) {
				if (startDate == null) {
					stringBuilder.append(
							" where Date(t.CreatedOn) = current_date() and (u.IsActualUser =:user OR u2.IsActualUser =:user)");
					query = session.createSQLQuery(stringBuilder.toString());
				} else if (startDate != null && endDate == null) {
					stringBuilder.append(
							" where Date(t.CreatedOn) =:date and (u.IsActualUser =:user OR u2.IsActualUser =:user)");
					query = session.createSQLQuery(stringBuilder.toString()).setParameter("date", startDate);
				} else {
					stringBuilder.append(
							" where Date(t.CreatedOn) between :sDate and :eDate and (u.IsActualUser =:user OR u2.IsActualUser =:user)");
					query = session.createSQLQuery(stringBuilder.toString()).setParameter("sDate", startDate)
							.setParameter("eDate", endDate);
				}
				query.setParameter("user", true);
			}else {
				if (startDate == null) {
					stringBuilder.append(
							" where Date(t.CreatedOn) = current_date() and t.PaymentStatus =:pStatus and (u.IsActualUser =:user OR u2.IsActualUser =:user)");
					query = session.createSQLQuery(stringBuilder.toString());
				} else if (startDate != null && endDate == null) {
					stringBuilder.append(
							" where Date(t.CreatedOn) =:date and t.PaymentStatus =:pStatus and (u.IsActualUser =:user OR u2.IsActualUser =:user)");
					query = session.createSQLQuery(stringBuilder.toString()).setParameter("date", startDate);
				} else {
					stringBuilder.append(
							" where Date(t.CreatedOn) between :sDate and :eDate and t.PaymentStatus =:pStatus and (u.IsActualUser =:user OR u2.IsActualUser =:user)");
					query = session.createSQLQuery(stringBuilder.toString()).setParameter("sDate", startDate)
							.setParameter("eDate", endDate);
				}
				query.setParameter("user", true).setParameter("pStatus", paymentStatus);
			}
			
		}
		return query.setResultTransformer(Transformers.aliasToBean(Transactions.class)).list();

	}

	@Override
	public Long getUsersTransactionCount(int userId) {
		Session session=sessionFactory.getCurrentSession();
		String hql="select count(t.payId) from TransactionsEntity t where t.initiatedFrom=:userId";
		Query query=session.createQuery(hql);
		query.setParameter("userId", userId);
		Long count=(Long)query.uniqueResult();
		
		return count;
	}

	@Override
	public List<Object[]> getUsersTransactions(int userId) {
		Session session=sessionFactory.getCurrentSession();
		String hql="Select t, (SELECT U.firstName FROM UserEntity U WHERE U.id=t.payerId) " + 
				"					 AS PayerName,(SELECT U.firstName FROM UserEntity U WHERE U.id=t.payeeId) as PayeeName  from TransactionsEntity t Where t.payerId =:userId OR t.payeeId =:userId";
		
		Query query=session.createQuery(hql);
		query.setParameter("userId", userId);
		List<Object[]> list = query.list();
		return list;
		
	}
	
/* This method is used to fetch sales and purchase amount from transaction table based on user's business transaction
 * @Author Ajay
 * @param userId required to fetch the user's data
 * @param period to fetch data according to particular period
 */
	@Override
	public SalesPurchaseDetailDto getSalesPurchaseAmount(int userId, String period) {
		Session session=sessionFactory.getCurrentSession();
		String hql= "select " + 
				"sum(case when t.payerId=:userId then t.billAmount end) as purchaseAmount, " + 
				"sum(case when t.payeeId=:userId then t.billAmount end) as salesAmount " + 
				"from TransactionsEntity t where (t.payeeId=:userId or t.payerId=:userId) and t.transactionType=:transactionType and Date(t.createdOn)=Date(Now())";
		Query query=session.createQuery(hql);
		query.setParameter("userId", userId);
		query.setParameter("transactionType", "Business");
		return (SalesPurchaseDetailDto)query.setResultTransformer(Transformers.aliasToBean(SalesPurchaseDetailDto.class)).uniqueResult();
		
	}

}
