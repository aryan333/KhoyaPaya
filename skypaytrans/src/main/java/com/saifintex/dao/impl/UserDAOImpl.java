package com.saifintex.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Repository;

import com.saifintex.dao.UserDAO;
import com.saifintex.domain.ChangePassword;
import com.saifintex.domain.ForgotPassword;
import com.saifintex.domain.TransactionDetail;
import com.saifintex.domain.UpdatePin;
import com.saifintex.domain.UpdateRelationParams;
import com.saifintex.domain.UpdateUserParams;
import com.saifintex.domain.User;
import com.saifintex.domain.UserPaymentDetail;
import com.saifintex.dto.DashboardDataDateParams;
import com.saifintex.dto.MerchantInfoBySalesPersonDto;
import com.saifintex.dto.UserStatsReportDTO;
import com.saifintex.entity.ProfileImageEntity;
import com.saifintex.entity.RelationsEntity;
import com.saifintex.entity.RolesEntity;
import com.saifintex.entity.UserContactsDetailEntity;
import com.saifintex.entity.UserDetailsEntity;
import com.saifintex.entity.UserEntity;
import com.saifintex.entity.UserLoginLocationEntity;
import com.saifintex.entity.UsersBalanceEntity;
import com.saifintex.utils.DateUtils;
import com.saifintex.web.dto.LatLngDTO;
import com.saifintex.web.dto.MerchantInfoBySPDTO;
import com.saifintex.web.dto.QrMappedUserDetailDTO;
import com.saifintex.web.dto.SalesInfoDTO;
import com.saifintex.web.dto.SalesPersonReferalInfoDTO;
import com.saifintex.web.dto.TotalReferalUserBySPDTO;
import com.saifintex.web.dto.UsersListDTO;
import com.saifintex.web.dto.UsersStatsDashboardDTO;

@Repository("userDao")
public class UserDAOImpl extends BaseDAOImpl<UserEntity, Integer> implements UserDAO {

	private StringBuilder stringBuilder;

	@Value("${admin.user.type}")
	private String userTypeAdmin;

	@Value("${permanent.user.type}")
	private String userTypePermanent;

	@Value("${temporary.user.type}")
	private String userTypeTemporary;

	@Value("${app.maxResult}")
	private int maxResult;

	@Override
	public int registerUser(UserEntity userEntity) {
		Set<RolesEntity> rolesEntitySet = userEntity.getRoles();
		int roleId = rolesEntitySet.iterator().next().getId();
		Session session = sessionFactory.getCurrentSession();
		Set<RolesEntity> rolesSet = new HashSet<RolesEntity>();
		RolesEntity roleEntityFromDB = session.get(RolesEntity.class, roleId);
		roleEntityFromDB.setId(roleId);
		rolesSet.add(roleEntityFromDB);		
		userEntity.setRoles(rolesSet);
		Integer id = (Integer) session.save(userEntity);
		return id;
	}

	@Override
	public User getUser(String userLogin, String password, String userType) {

		return null;
	}

	@Override
	public int inserPayment(UserPaymentDetail userPaymentDetail) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List getUserByLogin(String userLogin) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class)
				.add(Restrictions.eq("phNumber", userLogin));
		UserEntity userEntity = (UserEntity) criteria.uniqueResult();
		if (userEntity != null) {
			ProfileImageEntity pEntity = getProfileEntityForBlobId(userEntity.getId());
			List list = new ArrayList();
			list.add(userEntity);
			list.add(pEntity);
			return list;
		}
		return null;
	}
	
	@Override
	public List getUserByStandee(String standeeId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class)
				.add(Restrictions.eq("standeeId", standeeId));
		UserEntity userEntity = (UserEntity) criteria.uniqueResult();
		if (userEntity != null) {
			ProfileImageEntity pEntity = getProfileEntityForBlobId(userEntity.getId());
			List list = new ArrayList();
			list.add(userEntity);
			list.add(pEntity);
			return list;
		}
		return null;
	}

	@Override
	public List<Object> getUserByLoginAndOtherParams(String userLogin) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class)
				.add(Restrictions.eq("phNumber", userLogin));
		UserEntity userEntity = (UserEntity) criteria.uniqueResult();
		if (userEntity != null) {			
			if (!userEntity.isEnabled()) {
				throw new DisabledException("user is disabled");
			} else if (!userEntity.isAccountNonLocked()) {
				throw new LockedException("user account is locked");
			} else if (!userEntity.isAccountNonExpired()) {
				throw new AccountExpiredException("user account has expired");
			} else if (!userEntity.isCredentialsNonExpired()) {
				throw new CredentialsExpiredException("credentials expired");
			}
			ProfileImageEntity pEntity = getProfileEntityForBlobId(userEntity.getId());
			List<Object> list = new ArrayList<Object>();
			list.add(userEntity);
			list.add(pEntity);
			return list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private ProfileImageEntity getProfileEntityForBlobId(int userId) {
		String hql = "from ProfileImageEntity where id =" + " (select MAX(id) from ProfileImageEntity where userId = '"
				+ userId + "')";
		List<ProfileImageEntity> list = (List<ProfileImageEntity>) sessionFactory.getCurrentSession().createQuery(hql)
				.list();
		if (list != null && list.size() != 0 && !list.isEmpty()) {
			ProfileImageEntity entity = list.get(0);
			return entity;
		}
		return null;
	}

	@Override
	public int insertLoginLocations(UserLoginLocationEntity entity) {
		Integer result = (Integer) sessionFactory.getCurrentSession().save(entity);
		return result;
	}

	@Override
	public List<TransactionDetail> getUserTransactionHistoryById(int userId) {
		return null;
	}

	@Override
	public boolean updatePassword(ForgotPassword forgotPassword) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		UserEntity entity = (UserEntity) criteria.add(Restrictions.eq("phNumber", forgotPassword.getPhoneNo()))
				.add(Restrictions.eq("userType", userTypePermanent)).uniqueResult();
		if (entity != null) {
			Date date = DateUtils.getCurrentDateTime();
			entity.setPassword(forgotPassword.getPassword());
			entity.setModifiedBy(entity.getId());
			entity.setLastResetPasswordDate(date);
			return true;
		}
		return false;
	}

	@Override
	public int insertProfileImage(ProfileImageEntity entity) {
		Integer result = (Integer) sessionFactory.getCurrentSession().save(entity);
		return result;

	}

	@Override
	public UserEntity doesUserExist(String mobile) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		Object obj = criteria.add(Restrictions.eq("phNumber", mobile)).uniqueResult();
		if (obj != null) {
			UserEntity userEntity = (UserEntity) obj;
			return userEntity;
		}
		return null;
	}

	@Override
	public UserEntity getUserById(int userId) {
		UserEntity userEntity = (UserEntity) sessionFactory.getCurrentSession().get(UserEntity.class, userId);
		if (userEntity != null) {
			return userEntity;
		}
		return null;
	}

	@Override
	public boolean updatePin(UpdatePin updatePin) {
		UserEntity userEntity = (UserEntity) sessionFactory.getCurrentSession().get(UserEntity.class,
				updatePin.getUserId());
		if (userEntity != null) {
			userEntity.setPin(updatePin.getNewPin());
			userEntity.setModifiedOn(DateUtils.getCurrentDateTime());
			userEntity.setModifiedBy(userEntity.getId());
			return true;
		}
		return false;
	}

	@Override
	public boolean changePassword(ChangePassword changePassword) {
		UserEntity userEntity = (UserEntity) sessionFactory.getCurrentSession().get(UserEntity.class,
				changePassword.getUserId());
		if (userEntity != null) {
			Date date = DateUtils.getCurrentDateTime();
			userEntity.setPassword(changePassword.getNewPassword());
			userEntity.setModifiedOn(date);
			userEntity.setModifiedBy(userEntity.getId());
			userEntity.setLastResetPasswordDate(date);
			return true;
		}
		return false;
	}

	@Override
	public boolean updateUserInfo(UpdateUserParams user) {
		Session session = sessionFactory.getCurrentSession();
		UserEntity entity = (UserEntity) session.get(UserEntity.class, user.getUserId());
		if (entity == null) {
			return false;
		}
		entity.getUserDetailsEntity().setAddress1(user.getAddress1());
		entity.getUserDetailsEntity().setEmailId(user.getEmailId());
		entity.getUserDetailsEntity().setGender(user.getGender());
		entity.setFirstName(user.getFirstName());
		entity.setLastName(user.getLastName());
		Date d = DateUtils.getCurrentDateTime();
		entity.setModifiedOn(d);
		entity.setModifiedBy(user.getUserId());
		entity.getUserDetailsEntity().setModifiedOn(d);
		entity.getUserDetailsEntity().setModifiedBy(user.getUserId());
		Serializable obj = session.save(entity);
		if (obj != null) {
			return true;
		}
		return false;
	}

	@Override
	public UserEntity updateUser(UserEntity userEntity) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		UserEntity entity = (UserEntity) criteria.add(Restrictions.eq("phNumber", userEntity.getPhNumber()))
				.uniqueResult();
		if (entity != null) {
			entity.setPassword(userEntity.getPassword());
			entity.setModifiedOn(userEntity.getModifiedOn());
			entity.setModifiedBy(entity.getId());
			entity.setFirstName(userEntity.getFirstName());
			entity.setLastName(userEntity.getLastName());
			entity.setUserType(userEntity.getUserType());
			entity.setInviteCode(userEntity.getInviteCode());
			entity.setReferralCode(userEntity.getReferralCode());
			entity.setGstinNumber(userEntity.getGstinNumber());
			entity.setLastResetPasswordDate(userEntity.getLastResetPasswordDate());
			entity.setActualUser(true);
			entity.setCreatedOn(userEntity.getCreatedOn());
			UserDetailsEntity udEntity = entity.getUserDetailsEntity();
			udEntity.setModifiedOn(userEntity.getUserDetailsEntity().getModifiedOn());
			udEntity.setModifiedBy(userEntity.getUserDetailsEntity().getModifiedBy());
			udEntity.setLatitude(userEntity.getUserDetailsEntity().getLatitude());
			udEntity.setLongitude(userEntity.getUserDetailsEntity().getLongitude());
			session.save(entity);
			return entity;
		}
		return entity;
	}

	@Override
	public UserEntity getStarValue(int userId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class)
				.add(Restrictions.eq("id", userId));
		UserEntity userEntity = (UserEntity) criteria.uniqueResult();

		return userEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getAllUsers() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from UserEntity U where U.isActive<>:isActive AND U.userType=:userType";
		Query query = session.createQuery(hql);
		query.setParameter("isActive", false);
		query.setParameter("userType", "P");
		List<UserEntity> list = query.list();
		return list;
	}

	@Override
	public int inserContacts(List<UserContactsDetailEntity> list) {
		Session session = sessionFactory.openSession();
		Integer result = new Integer(0);
		try {
			session.beginTransaction();
			Query query = null;
			for (int i = 0; i < list.size(); i++) {
				UserContactsDetailEntity userContactEntity = list.get(i);
				session.saveOrUpdate(userContactEntity);
				if (i % 50 == 0) {
					session.flush();
					session.clear();
				}
			}
			session.getTransaction().commit();
		} catch (ConstraintViolationException e) {
			getLogger().error(e);
			e.printStackTrace();
		} finally {
			session.close();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getAssociatedUsers(int id) {
		Session session = sessionFactory.getCurrentSession();

		String hql = "SELECT U,UB " + " FROM UserEntity U,UsersBalanceEntity UB WHERE "
				+ " U.id=(CASE WHEN UB.user1=:UserID THEN UB.user2 ELSE UB.user1 END )"
				+ " AND (UB.user1=:UserID OR UB.user2=:UserID)";

		Query query = session.createQuery(hql).setParameter("UserID", id);

		List<Object[]> list = query.list();
		List<UserEntity> userEntityList = new ArrayList<UserEntity>();

		for (Object[] object : list) {
			userEntityList.add((UserEntity) object[0]);
		}
		return userEntityList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> fetchRelations() {
		List<Object> list = sessionFactory.getCurrentSession().createCriteria(RelationsEntity.class).list();
		return list;
	}

	@Override
	public int saveRelation(UpdateRelationParams params) {
		/*
		 * Session session = sessionFactory.getCurrentSession(); UsersRelationEntity
		 * entity = getRelation(session, params); Date d =
		 * DateUtils.getCurrentDateTime(); if (entity != null) { if (entity.getUser1()
		 * == params.getLoggedInUser()) { entity.setRelationId(params.getRelationId());
		 * entity.setModifiedBy(params.getLoggedInUser()); } else {
		 * entity.setOppositeRelationId(params.getRelationId());
		 * entity.setModifiedBy(params.getLoggedInUser()); } entity.setModifiedOn(d); }
		 * else { entity = new UsersRelationEntity();
		 * entity.setUser1(params.getLoggedInUser());
		 * entity.setUser2(params.getOpponentUser());
		 * entity.setRelationId(params.getRelationId());
		 * entity.setOppositeRelationId(params.getRelationId()); entity.setCreatedOn(d);
		 * entity.setCreatedBy(params.getLoggedInUser());
		 * entity.setModifiedBy(params.getLoggedInUser()); entity.setModifiedOn(d); }
		 * int id = (Integer) session.save(entity); return id;
		 */
		return 1;
	}


	@Override
	public Object[] getIndividualRelation(int loginId, int oppId) {
		String hql = "SELECT re.relation,re.id FROM RelationsEntity re WHERE re.id ="
				+ "(SELECT (CASE WHEN ure.user1=:lId THEN ure.relationId ELSE ure.oppositeRelationId END) FROM	"
				+ "	UsersRelationEntity ure WHERE (ure.user1=:lId AND  ure.user2 =:oId) OR (ure.user1=:oId AND  ure.user2 =:lId))";
		Object[] objArray = (Object[]) sessionFactory.getCurrentSession().createQuery(hql).setParameter("lId", loginId)
				.setParameter("oId", oppId).uniqueResult();
		return objArray;
	}

	@Override
	public boolean savePin(int uId, String pin) {
		Session session = sessionFactory.getCurrentSession();
		UserEntity entity = getUserById(uId);
		entity.setPin(pin);
		entity.setPinProtected(true);
		entity.setModifiedBy(uId);
		entity.setModifiedOn(DateUtils.getCurrentDateTime());
		Integer id = (Integer) session.save(entity);
		if (id != null) {
			return true;
		}
		return false;
	}

	/*
	 * @Override public boolean resetPin(int userId, String pin) { Session session =
	 * sessionFactory.getCurrentSession(); UserEntity entity = getUserById(userId);
	 * entity.setPin(pin); entity.setModifiedBy(userId);
	 * entity.setModifiedOn(DateUtils.getCurrentDateTime()); Integer id = (Integer)
	 * session.save(entity); if (id != null) { return true; } return false; }
	 */

	@Override
	public boolean saveGstinNumber(int userId, String gstinNumber) {
		Session session = sessionFactory.getCurrentSession();
		UserEntity entity = getUserById(userId);
		entity.setGstinNumber(gstinNumber);
		entity.setModifiedBy(userId);
		entity.setModifiedOn(DateUtils.getCurrentDateTime());
		Integer id = (Integer) session.save(entity);
		if (id != null) {
			return true;
		}
		return false;
	}

	@Override
	public UsersBalanceEntity getUserBalanceEntityForMutualBalance(int userId1, int userId2) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UsersBalanceEntity.class);
		Criterion cr1 = Restrictions.eq("user1", userId1);
		Criterion cr2 = Restrictions.eq("user2", userId2);
		Criterion cr3 = Restrictions.eq("user1", userId2);
		Criterion cr4 = Restrictions.eq("user2", userId1);
		LogicalExpression andExpression = Restrictions.and(cr1, cr2);
		LogicalExpression andExpression2 = Restrictions.and(cr3, cr4);
		LogicalExpression orExpression = Restrictions.or(andExpression, andExpression2);
		UsersBalanceEntity uBEntity = (UsersBalanceEntity) criteria.add(orExpression).uniqueResult();
		return uBEntity;
	}

	@Value("${permanent.user.type}")
	private String permanentUser;

	@Value("${temporary.user.type}")
	private String temporaryUser;

	@Override
	public UsersStatsDashboardDTO getUserStats(Date startDateCurrWeek, int month) {
		stringBuilder = new StringBuilder();
		String hql = stringBuilder.append("select Count(u.id) as totalUsers, ")
				.append("Count(Case When u.userType =:perm Then u.id END) as totalPermanentUsers, ")
				.append("Count(Case When u.userType =:temp Then u.id END) as totalTemporaryUsers, ")
				.append("Count(Case When DATE(u.createdOn) = current_date() AND u.userType =:perm Then u.id END) as newRegisteredUsersToday, ")
				.append("Count(Case When DATE(u.createdOn) = current_date() AND u.userType =:temp Then u.id END) as newTemporaryUsersToday,")
				.append("Count(Case When u.createdOn Between :d AND NOW() AND u.userType =:perm Then u.id END) as newRegisteredUsersWeek,")
				.append("Count(Case When u.createdOn Between :d AND NOW() AND u.userType =:temp Then u.id END) as newTemporaryUsersWeek,")
				.append("Count(Case When MONTH(u.createdOn) =:m AND u.userType =:perm Then u.id END) as newRegisteredUsersMonth,")
				.append("Count(Case When MONTH(u.createdOn) =:m AND u.userType =:temp Then u.id END) as newUnregisteredUsersMonth")
				.append(" from UserEntity u").toString();
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("d", startDateCurrWeek)
				.setParameter("perm", permanentUser).setParameter("temp", temporaryUser).setParameter("m", month);
		UsersStatsDashboardDTO userStats = (UsersStatsDashboardDTO) query
				.setResultTransformer(Transformers.aliasToBean(UsersStatsDashboardDTO.class)).uniqueResult();
		return userStats;
	}

	@Override
	public UserStatsReportDTO getUserStats(DashboardDataDateParams dateParams, boolean includeTestUsers) {
		stringBuilder = new StringBuilder("select count(u.id) as totalUsers,");
		stringBuilder.append("count(Case When Date(u.createdOn) = current_date() then u.id end) as todayUsers,");
		stringBuilder.append("count(Case When Date(u.createdOn) =:yesterday then u.id end) as yesterdayUsers,");
		stringBuilder.append(
				"count(Case When Date(u.createdOn) between :startDateOfWeek and :endDateOfWeek then u.id end) as usersInWeekDateRange,");
		stringBuilder.append(
				"count(Case When Date(u.createdOn) between :startDate and :uptoDate then u.id end) as usersInDateRange,");
		stringBuilder.append("count(Case When u.isActive =:active then u.id end) as totalBlockedUsers,");
		stringBuilder.append(
				"count(Case When Date(u.modifiedOn) =:yesterday and u.isActive =:active then u.id end) as totalYesterdayBlockedUsers,");
		stringBuilder.append(
				"count(Case When Date(u.modifiedOn) between :startDateOfWeek and :endDateOfWeek and u.isActive =:active then u.id end) as blockedUsersInWeekDateRange,");
		stringBuilder.append(
				"count(Case When Date(u.modifiedOn) between :startDate and :uptoDate and u.isActive =:active then u.id end) as blockedUsersInDateRange,");
		stringBuilder.append(
				"count(Case When Date(u.modifiedOn) = current_date() and u.isActive =:active then u.id end) as blockedUsersToday");
		stringBuilder.append(" from UserEntity u ");
		if (includeTestUsers) {
			stringBuilder.append("where u.isTestUser=:test and u.userType =:uType");
		} else {
			stringBuilder.append("where u.isActualUser =:type");

		}
		Query query = sessionFactory.getCurrentSession().createQuery(stringBuilder.toString()).setParameter("yesterday",
				dateParams.getYesterdayDate());
		query.setParameter("startDateOfWeek", dateParams.getStartingDateOfWeek())
				.setParameter("endDateOfWeek", dateParams.getEndDateOfWeek())
				.setParameter("startDate", dateParams.getStartingDate())
				.setParameter("uptoDate", dateParams.getUptoDate()).setParameter("active", false);
		if (includeTestUsers) {
			query.setParameter("test", false).setParameter("uType", permanentUser);

		} else {
			query.setParameter("type", true);
		}

		UserStatsReportDTO dto = (UserStatsReportDTO) query
				.setResultTransformer(Transformers.aliasToBean(UserStatsReportDTO.class)).uniqueResult();
		return dto;
	}

	@Override
	public List<Long> getActiveUsersCount(DashboardDataDateParams dateParams, boolean includeTestUsers) {
		List<Long> list = new ArrayList<Long>();
		
		
		
		list.add(getActiveUsers(null, null, includeTestUsers,false)); // get Active users count today
		list.add(getActiveUsers(dateParams.getYesterdayDate(), null, includeTestUsers,false)); // get active users count
																							// yesterday
		list.add(getActiveUsers(dateParams.getStartingDateOfWeek(), dateParams.getEndDateOfWeek(), includeTestUsers,false)); // get
																														// active
																														// users
		// count in week
		// range
		list.add(getActiveUsers(dateParams.getStartingDate(), dateParams.getUptoDate(), includeTestUsers,false)); // get
																											// active
																											// users
																											// count in
		// from start date to
		// specific date
		
		list.add(getActiveUsers(null, null, includeTestUsers,true));
		return list;
	}

	private long getActiveUsers(Date startDate, Date endDate, boolean includeTestUser, boolean isOverAllUsers) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		if (startDate == null && !isOverAllUsers) {
			if (includeTestUser) {
				String sql = "select Count(Uid) as result from ((select Distinct t.InitiatedFrom as Uid from Transactions t inner join Users u on t.InitiatedFrom = u.UserId where Date(t.CreatedOn) = Current_Date() and u.isTestUser=:test) "
						+ " union "
						+ "(select Distinct t.`ModifiedBy` as Uid from Transactions t inner join Users u on t.ModifiedBy = u.UserId where Date(t.CreatedOn) = Current_Date() And t.CreatedBy <> t.ModifiedBy and u.isTestUser=:test)) res";
				query = session.createSQLQuery(sql).setParameter("test", false);
			} else {
				String sql = "select Count(Uid) as result from ((select Distinct t.InitiatedFrom as Uid from Transactions t inner join Users u on t.InitiatedFrom = u.UserId where Date(t.CreatedOn) = Current_Date() and u.IsActualUser =:user)"
						+ " union "
						+ "(select Distinct t.`ModifiedBy` as Uid from Transactions t inner join Users u on t.ModifiedBy = u.UserId where Date(t.CreatedOn) = Current_Date() And t.CreatedBy <> t.ModifiedBy and u.IsActualUser =:user)) res";
				query = session.createSQLQuery(sql).setParameter("user", true);
			}

		} else if (startDate != null && endDate == null && !isOverAllUsers) {
			if (includeTestUser) {
				String sql = "select Count(Uid) as result from ((select Distinct t.InitiatedFrom as Uid from Transactions t inner join Users u on t.InitiatedFrom = u.UserId where Date(t.CreatedOn) =:date and u.isTestUser=:test)"
						+ " union "
						+ "(select Distinct t.ModifiedBy as Uid from Transactions t inner join Users u on t.ModifiedBy = u.UserId where Date(t.CreatedOn) =:date And t.CreatedBy <> t.ModifiedBy and u.isTestUser=:test)) res";
				query = session.createSQLQuery(sql).setParameter("date", startDate).setParameter("test", false);
			} else {
				String sql = "select Count(Uid) as result from ((select Distinct t.InitiatedFrom as Uid from Transactions t inner join Users u on t.InitiatedFrom = u.UserId where Date(t.CreatedOn) =:date and u.IsActualUser =:user)"
						+ " union "
						+ "(select Distinct t.ModifiedBy as Uid from Transactions t inner join Users u on t.ModifiedBy = u.UserId where Date(t.CreatedOn) =:date And t.CreatedBy <> t.ModifiedBy and u.IsActualUser =:user)) res";
				query = session.createSQLQuery(sql).setParameter("date", startDate).setParameter("user", true);
			}

		} 
		
		
		else if (isOverAllUsers) {
			System.out.println("is over all users");
			if (includeTestUser) {
				String sql = "select Count(Uid) as result from ((select Distinct t.InitiatedFrom as Uid from Transactions t inner join Users u on t.InitiatedFrom = u.UserId where  u.isTestUser=:test)"
						+ " union "
						+ "(select Distinct t.ModifiedBy as Uid from Transactions t inner join Users u on t.ModifiedBy = u.UserId where  t.CreatedBy <> t.ModifiedBy and u.isTestUser=:test)) res";
				query = session.createSQLQuery(sql).setParameter("test", false);
			} else {
				String sql = "select Count(Uid) as result from ((select Distinct t.InitiatedFrom as Uid from Transactions t inner join Users u on t.InitiatedFrom = u.UserId where  u.IsActualUser =:user)"
						+ " union "
						+ "(select Distinct t.ModifiedBy as Uid from Transactions t inner join Users u on t.ModifiedBy = u.UserId where  t.CreatedBy <> t.ModifiedBy and u.IsActualUser =:user)) res";
				query = session.createSQLQuery(sql).setParameter("user", true);
			}

		} 
		
		else {
			if (includeTestUser) {
				String sql = "select Count(Uid) as result from ((select Distinct t.InitiatedFrom as Uid from Transactions t inner join Users u on t.InitiatedFrom = u.UserId where Date(t.CreatedOn) between :sDate and :eDate and u.isTestUser=:test)"
						+ " union "
						+ "(select Distinct t.ModifiedBy as Uid from Transactions t inner join Users u on t.ModifiedBy = u.UserId where Date(t.CreatedOn) between :sDate and :eDate And t.CreatedBy <> t.ModifiedBy and u.isTestUser=:test)) res";
				query = session.createSQLQuery(sql).setParameter("sDate", startDate).setParameter("eDate", endDate)
						.setParameter("test", false);
			} else {
				String sql = "select Count(Uid) as result from ((select Distinct t.InitiatedFrom as Uid from Transactions t inner join Users u on t.InitiatedFrom = u.UserId where Date(t.CreatedOn) between :sDate and :eDate and u.IsActualUser =:user)"
						+ " union "
						+ "(select Distinct t.ModifiedBy as Uid from Transactions t inner join Users u on t.ModifiedBy = u.UserId where Date(t.CreatedOn) between :sDate and :eDate And t.CreatedBy <> t.ModifiedBy and u.IsActualUser =:user)) res";
				query = session.createSQLQuery(sql).setParameter("sDate", startDate).setParameter("eDate", endDate)
						.setParameter("user", true);
			}
		}
		BigInteger res = (BigInteger) query.uniqueResult();
		if (res == null) {
			return 0l;
		}
		return res.longValue();
	}

	/*@Override
	public List<UsersListDTO> getOnBoardedUsers(Date startDate, Date endDate, boolean includeTestUser) {
		Session session = sessionFactory.getCurrentSession();
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.id().as("userId")).add(Projections.property("firstName").as("firstName"))
				.add(Projections.property("phNumber").as("phNumber"))
				.add(Projections.property("createdOn").as("createdOn"))
				.add(Projections.property("ud.latitude").as("latitude"))
				.add(Projections.property("ud.longitude").as("longitude"));

		Criteria criteria = session.createCriteria(UserEntity.class);
		if (startDate == null) {
			criteria.add(Restrictions.eq("createdOn", DateUtils.getCurrentDateInDateObject()));
		} else if (startDate != null && endDate == null) {
			criteria.add(Restrictions.eq("createdOn", startDate));			
		} else {			
			LogicalExpression dateExpr = Restrictions.and(Restrictions.ge("createdOn", startDate),Restrictions.le("createdOn", endDate));
			criteria.add(dateExpr);
		}

		if (includeTestUser) {
			criteria.add(Restrictions.eq("userType", permanentUser)).add(Restrictions.eq("isTestUser", false));
		} else {
			criteria.add(Restrictions.eq("isActualUser", true));
		}
		criteria.createAlias("userDetailsEntity", "ud").setProjection(projectionList)
				.setResultTransformer(Transformers.aliasToBean(UsersListDTO.class));		
		List<UsersListDTO> list = (List<UsersListDTO>)criteria.list();
		return list;
	}*/
	
	@Override
	public List<UsersListDTO> getOnBoardedUsers(Date startDate, Date endDate, boolean includeTestUser) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		stringBuilder = new StringBuilder("select u.id as userId,u.firstName as firstName ,u.phNumber as phNumber,u.createdOn as createdOn,u.userDetailsEntity.latitude as latitude,u.userDetailsEntity.longitude as longitude"
				+ " from UserEntity u");
		if(includeTestUser) {
			if(startDate==null) {			
				stringBuilder.append(" where Date(u.createdOn)=current_date() and u.userType=:user and u.isTestUser=:test");
				query = session.createQuery(stringBuilder.toString());
			}else if (startDate != null && endDate == null) {	
				stringBuilder.append(" where Date(u.createdOn)=:sDate and u.userType=:user and u.isTestUser=:test");
				query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate);
			}else {
				stringBuilder.append(" where Date(u.createdOn) between :sDate and :eDate and u.userType=:user and u.isTestUser=:test");
				query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("eDate", endDate);
			}
			query.setParameter("user", permanentUser).setParameter("test", false);
		}else {
			if(startDate==null) {			
				stringBuilder.append(" where Date(u.createdOn)=current_date() and u.isActualUser=:user");
				query = session.createQuery(stringBuilder.toString());
			}else if (startDate != null && endDate == null) {	
				stringBuilder.append(" where Date(u.createdOn)=:sDate and u.isActualUser=:user");
				query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate);
			}else {
				stringBuilder.append(" where Date(u.createdOn) between :sDate and :eDate and u.isActualUser=:user");
				query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("eDate", endDate);
			}
			query.setParameter("user", true);
		}
		List<UsersListDTO> list = query.setResultTransformer(Transformers.aliasToBean(UsersListDTO.class)).list();		
		return list;
	}

	// users who are making transactions and responding as well
	@SuppressWarnings("unchecked")
	@Override
	public List<UsersListDTO> getActiveUsersList(Date startDate, Date endDate, boolean includeTestUsers, boolean overAllUsers) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		
		
		
		if (includeTestUsers) {
			
			
			if (startDate == null && !overAllUsers ) {
				String sql = "select Distinct t.InitiatedFrom as userId,u.FirstName as firstName,u.UserLogin as phNumber from Transactions t inner join Users u on t.InitiatedFrom = u.UserId where Date(t.CreatedOn)=current_date() and u.isTestUser=:test"
						+ "	union "
						+ "select Distinct t.ModifiedBy as userId,u.FirstName as firstName,u.UserLogin as phNumber from Transactions t inner join Users u on t.ModifiedBy = u.UserId where Date(t.CreatedOn)=current_date() And t.CreatedBy <> t.ModifiedBy and u.isTestUser=:test";
				query = session.createSQLQuery(sql);
			} else if (startDate != null && endDate == null && !overAllUsers) {
				String sql = "select Distinct t.InitiatedFrom as userId,u.FirstName as firstName,u.UserLogin as phNumber from Transactions t inner join Users u on t.InitiatedFrom = u.UserId where Date(t.CreatedOn)=:date and u.isTestUser=:test"
						+ "	union "
						+ "select Distinct t.ModifiedBy as userId,u.FirstName as firstName,u.UserLogin as phNumber from Transactions t inner join Users u on t.ModifiedBy = u.UserId where Date(t.CreatedOn)=:date And t.CreatedBy <> t.ModifiedBy and u.isTestUser=:test";
				query = session.createSQLQuery(sql).setParameter("date", startDate);
			} else if (startDate != null && endDate != null && !overAllUsers) {
				String sql = "select Distinct t.InitiatedFrom as userId,u.FirstName as firstName,u.UserLogin as phNumber from Transactions t inner join Users u on t.InitiatedFrom = u.UserId where Date(t.CreatedOn) between :sDate and :eDate and u.isTestUser=:test"
						+ "	union "
						+ "select Distinct t.ModifiedBy as userId,u.FirstName as firstName,u.UserLogin as phNumber from Transactions t inner join Users u on t.ModifiedBy = u.UserId where Date(t.CreatedOn) between :sDate and :eDate And t.CreatedBy <> t.ModifiedBy and u.isTestUser=:test";
				query = session.createSQLQuery(sql).setParameter("sDate", startDate).setParameter("eDate", endDate);
			}
			
			else{
				
				String sql = "select Distinct t.InitiatedFrom as userId,u.FirstName as firstName,u.UserLogin as phNumber from Transactions t inner join Users u on t.InitiatedFrom = u.UserId where u.isTestUser=:test"
						+ "	union "
						+ "select Distinct t.ModifiedBy as userId,u.FirstName as firstName,u.UserLogin as phNumber from Transactions t inner join Users u on t.ModifiedBy = u.UserId where  t.CreatedBy <> t.ModifiedBy and u.isTestUser=:test";
				query = session.createSQLQuery(sql);
				
			}
			query.setParameter("test", false);
		} else {
			
			
			
			if (startDate == null && !overAllUsers) {
				String sql = "select Distinct t.InitiatedFrom as userId,u.FirstName as firstName,u.UserLogin as phNumber from Transactions t inner join Users u on t.InitiatedFrom = u.UserId where Date(t.CreatedOn)=current_date() and u.IsActualUser =:user"
						+ "	union "
						+ "select Distinct t.ModifiedBy as userId,u.FirstName as firstName,u.UserLogin as phNumber from Transactions t inner join Users u on t.ModifiedBy = u.UserId where Date(t.CreatedOn)=current_date() And t.CreatedBy <> t.ModifiedBy and u.IsActualUser =:user";
				query = session.createSQLQuery(sql);
			} else if (startDate != null && endDate == null && !overAllUsers) {
				String sql = "select Distinct t.InitiatedFrom as userId,u.FirstName as firstName,u.UserLogin as phNumber from Transactions t inner join Users u on t.InitiatedFrom = u.UserId where Date(t.CreatedOn)=:date and u.IsActualUser =:user"
						+ "	union "
						+ "select Distinct t.ModifiedBy as userId,u.FirstName as firstName,u.UserLogin as phNumber from Transactions t inner join Users u on t.ModifiedBy = u.UserId where Date(t.CreatedOn)=:date And t.CreatedBy <> t.ModifiedBy and u.IsActualUser =:user";
				query = session.createSQLQuery(sql).setParameter("date", startDate);
			} else if (startDate != null && endDate != null && !overAllUsers){
				String sql = "select Distinct t.InitiatedFrom as userId,u.FirstName as firstName,u.UserLogin as phNumber from Transactions t inner join Users u on t.InitiatedFrom = u.UserId where Date(t.CreatedOn) between :sDate and :eDate and u.IsActualUser =:user"
						+ "	union "
						+ "select Distinct t.ModifiedBy as userId,u.FirstName as firstName,u.UserLogin as phNumber from Transactions t inner join Users u on t.ModifiedBy = u.UserId where Date(t.CreatedOn) between :sDate and :eDate And t.CreatedBy <> t.ModifiedBy and u.IsActualUser =:user";
				query = session.createSQLQuery(sql).setParameter("sDate", startDate).setParameter("eDate", endDate);
			}
			
			else {
				

				String sql = "select Distinct t.InitiatedFrom as userId,u.FirstName as firstName,u.UserLogin as phNumber from Transactions t inner join Users u on t.InitiatedFrom = u.UserId where u.isTestUser=:test"
						+ "	union "
						+ "select Distinct t.ModifiedBy as userId,u.FirstName as firstName,u.UserLogin as phNumber from Transactions t inner join Users u on t.ModifiedBy = u.UserId where  t.CreatedBy <> t.ModifiedBy and u.isTestUser=:test";
				query = session.createSQLQuery(sql);
				
			}
			query.setParameter("user", true);
		}
		List<UsersListDTO> userList = (List<UsersListDTO>) query
				.setResultTransformer(Transformers.aliasToBean(UsersListDTO.class)).list();
		return userList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getAllBluPayUsers() {
		List<UserEntity> list = sessionFactory.getCurrentSession().createCriteria(UserEntity.class).setFirstResult(0).setMaxResults(100)
				.addOrder(Order.desc("id")).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getAllRegisteredBluPayUsers() {
		List<UserEntity> list = sessionFactory.getCurrentSession().createCriteria(UserEntity.class)
				.add(Restrictions.eq("userType", userTypePermanent)).addOrder(Order.desc("id")).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getAllTemporaryBluPayUsers() {
		List<UserEntity> list = sessionFactory.getCurrentSession().createCriteria(UserEntity.class)
				.add(Restrictions.eq("userType", userTypeTemporary)).addOrder(Order.desc("id")).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getAllRegisteredUsersToday() {
		String hql = "from UserEntity u where u.userType =:perm and DATE(u.createdOn) = CURDATE() order by u.createdOn desc";
		List<UserEntity> list = sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("perm", userTypePermanent).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getAllTemporaryUsersToday() {
		String hql = "from UserEntity u where u.userType =:temp and DATE(u.createdOn) = CURDATE() order by u.createdOn desc";
		List<UserEntity> list = sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("temp", userTypeTemporary).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getAllUsersToday() {
		String hql = "from UserEntity u where  DATE(u.createdOn) = CURDATE() order by u.id desc";
		List<UserEntity> list = sessionFactory.getCurrentSession().createQuery(hql).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getAllUsersBetweenDateRange(Date startDate, Date endDate, String flag) {
		stringBuilder = new StringBuilder("from UserEntity u where DATE(u.createdOn) BETWEEN :sDate AND :eDate");
		if (flag.equalsIgnoreCase("P")) {
			stringBuilder.append(" AND u.userType =:perm");
		} else if (flag.equalsIgnoreCase("T")) {
			stringBuilder.append(" AND u.userType =:temp");
		}
		stringBuilder.append(" order by u.id desc");
		Query query = sessionFactory.getCurrentSession().createQuery(stringBuilder.toString())
				.setParameter("sDate", startDate).setParameter("eDate", endDate);
		if (flag.equalsIgnoreCase("P")) {
			query.setParameter("perm", permanentUser);
		} else if (flag.equalsIgnoreCase("T")) {
			query.setParameter("temp", temporaryUser);
		}
		List<UserEntity> list = query.list();
		return list;
	}

	@Override
	public UsersStatsDashboardDTO getUsersStatsBetweenDateRange(Date startDate, Date endDate) {
		stringBuilder = new StringBuilder(
				"Select Count(CASE WHEN DATE(u.createdOn) BETWEEN :sDate AND :eDate THEN u.id END) as totalUsers,");
		stringBuilder.append(
				"Count(Case When DATE(u.createdOn) BETWEEN :sDate AND :eDate AND u.userType =:perm Then u.id END) as totalPermanentUsers,")
				.append("Count(Case When DATE(u.createdOn) BETWEEN :sDate AND :eDate AND u.userType =:temp Then u.id END) as totalTemporaryUsers")
				.append(" from UserEntity u");
		Query query = sessionFactory.getCurrentSession().createQuery(stringBuilder.toString())
				.setParameter("perm", permanentUser).setParameter("temp", temporaryUser)
				.setParameter("sDate", startDate).setParameter("eDate", endDate);
		UsersStatsDashboardDTO dto = (UsersStatsDashboardDTO) query
				.setResultTransformer(Transformers.aliasToBean(UsersStatsDashboardDTO.class)).uniqueResult();
		return dto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getAllUsersInCurrentWeek(Date startDateOfCurrWeek) {
		String hql = "from UserEntity u where u.createdOn between :d and NOW() order by u.id desc";
		List<UserEntity> list = sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("d", startDateOfCurrWeek).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getAllRegisteredUsersInCurrentWeek(Date startDateOfCurrWeek) {
		String hql = "from UserEntity u where u.createdOn between :d and NOW() and u.userType =:perm order by u.id desc";
		List<UserEntity> list = sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("d", startDateOfCurrWeek).setParameter("perm", permanentUser).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getAllTempUsersInCurrentWeek(Date startDateOfCurrWeek) {
		String hql = "from UserEntity u where u.createdOn between :d and NOW() and u.userType =:temp order by u.id desc";
		List<UserEntity> list = sessionFactory.getCurrentSession().createQuery(hql)
				.setParameter("d", startDateOfCurrWeek).setParameter("temp", temporaryUser).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getAllTemporaryUsersInCurrentMonth(int month) {
		String hql = "from UserEntity u where Month(u.createdOn) =:m and u.userType =:temp order by u.id desc";
		List<UserEntity> list = sessionFactory.getCurrentSession().createQuery(hql).setParameter("m", month)
				.setParameter("temp", temporaryUser).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getAllRegsiteredUsersInCurrentMonth(int month) {
		String hql = "from UserEntity u where Month(u.createdOn) =:m and u.userType =:perm order by u.id desc";
		List<UserEntity> list = sessionFactory.getCurrentSession().createQuery(hql).setParameter("m", month)
				.setParameter("perm", permanentUser).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserEntity> getAllUsersInCurrentMonth(int month) {
		String hql = "from UserEntity u where Month(u.createdOn) =:m order by u.id desc";
		List<UserEntity> list = sessionFactory.getCurrentSession().createQuery(hql).setParameter("m", month).list();
		return list;
	}

	@Override
	public List<UsersListDTO> getInactiveUsers(Date startDate, Date endDate,boolean includeTestUsers){
		stringBuilder = new StringBuilder("select u.firstName as firstName,u.phNumber as phNumber from UserEntity u ");
		Query query = null;
		Session session = sessionFactory.getCurrentSession();
		if(includeTestUsers) {
			if(startDate == null) {
				stringBuilder.append("where Date(u.createdOn) = current_date() and u.userType=:type and u.isActive=:active and u.isTestUser=:user");
				query = session.createQuery(stringBuilder.toString());
			}else if(startDate != null && endDate == null) {
				stringBuilder.append("where Date(u.createdOn) =:date and u.userType=:type and u.isActive=:active and u.isTestUser=:user");
				query = session.createQuery(stringBuilder.toString()).setParameter("date", startDate);
			}else {
				stringBuilder.append("where Date(u.createdOn) between :sDate and :eDate and u.userType=:type and u.isActive=:active and u.isTestUser=:user");
				query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate)
						.setParameter("eDate", endDate);
			}
			query.setParameter("user", false).setParameter("active", false);
		}else {
			if(startDate == null) {
				stringBuilder.append("where Date(u.createdOn) = current_date() and u.userType=:type and u.isActive=:active and u.isActualUser=:user");
				query = session.createQuery(stringBuilder.toString());
			}else if(startDate != null && endDate == null) {
				stringBuilder.append("where Date(u.createdOn) =:date and u.userType=:type and u.isActive=:active and u.isActualUser=:user");
				query = session.createQuery(stringBuilder.toString()).setParameter("date", startDate);
			}else {
				stringBuilder.append("where Date(u.createdOn) between :sDate and :eDate and u.userType=:type and u.isActive=:active and u.isActualUser=:user");
				query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate)
						.setParameter("eDate", endDate);
			}
			query.setParameter("user", true).setParameter("active", false);
		}
		query.setParameter("type", permanentUser);
		return query.setResultTransformer(Transformers.aliasToBean(UsersListDTO.class)).list();
	}
	
	@Override
	public int updateFCMToken(final int userId, final String token) {
		Integer result = 0;
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		UserEntity userEntity = (UserEntity) criteria.add(Restrictions.eq("id", userId)).uniqueResult();
		if (userEntity != null) {
			userEntity.setFcmToken(token);
			result = (Integer) session.save(userEntity);
			return result;
		}
		return result;
	}

	@Override
	public UserEntity getToken(final int userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserEntity.class);
		UserEntity userEntity = (UserEntity) criteria.add(Restrictions.eq("id", userId)).uniqueResult();
		return userEntity;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getTokenOfAllUsers() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT u.fcmToken FROM UserEntity u WHERE u.userType=:userType AND u.fcmToken IS NOT NULL";

		List<String> list = session.createQuery(hql).setParameter("userType", "P").list();
		return list;
	}
	
	@Override
	public String getTokenOfIndiviual(String mob) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT u.fcmToken FROM UserEntity u WHERE u.phNumber=:mob AND u.fcmToken IS NOT NULL";

		String fcmToken = (String) session.createQuery(hql).setParameter("mob", mob).uniqueResult();
		return fcmToken;
	}

	@Override
	public void getUserWithAllInfo(String mob) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "";
		session.createQuery(hql);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> fetchEarnedPointsDetail(int userId, int pageSize) {
		String hql = "select upm,u from UsersPointsMappingEntity upm join upm.rewardPointsEntity rpe,UserEntity u where "
				+ "upm.referenceId =:uId And u.id=upm.userId order by rpe.name";
		List<Object[]> list = sessionFactory.getCurrentSession().createQuery(hql).setParameter("uId", userId)
				.setFirstResult(pageSize * maxResult).setMaxResults(maxResult).list();
		return list;
	}
	
	@Override
	public List<LatLngDTO> findOnBoardedUsersLatLng(Date startDate,Date endDate){
		
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		
		stringBuilder = new StringBuilder("select u.firstName as title,u.phNumber as phNumber,u.userDetailsEntity.latitude as lat,u.userDetailsEntity.longitude as lng from UserEntity u Where u.userType=:userType and u.userDetailsEntity.latitude <> 0 and u.userDetailsEntity.latitude is not null ");
		
		if(startDate==null) {			
			stringBuilder.append(" AND Date(u.createdOn)=current_date() ");
			query = session.createQuery(stringBuilder.toString()).setParameter("userType", "P");
		}else if (startDate != null && endDate == null) {	
			stringBuilder.append(" AND Date(u.createdOn)=:sDate ");
			query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("userType", "P");
		}else {
			stringBuilder.append(" AND Date(u.createdOn) between :sDate and :eDate ");
			query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("eDate", endDate).setParameter("userType", "P");
		}
		
		List<LatLngDTO> dtoList = query.setResultTransformer(Transformers.aliasToBean(LatLngDTO.class)).list();
		
		return dtoList;
	}

	@Override
	public List<QrMappedUserDetailDTO> getQrMappedUsers() {
		String hql="select u.firstName as Name, u.standeeId as qrSeries, u.qrMapDate as qrMappedDate ,u.phNumber as mobileNumber from UserEntity u where u.standeeId is not null order by u.qrMapDate desc";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		List<QrMappedUserDetailDTO> listOfUsers=query.setResultTransformer(Transformers.aliasToBean(QrMappedUserDetailDTO.class)).list();
		return listOfUsers;
	}

	@Override
	public UserEntity getOnlyUserByUserLogin(String userLogin) {
		Session session=sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(UserEntity.class).add(Restrictions.eq("phNumber", userLogin)).add(Restrictions.eq("userType", "P"));
		UserEntity entity=(UserEntity)criteria.uniqueResult();
		return entity;
	}
	
	
public List<SalesPersonReferalInfoDTO> SalesInfoReferal(DashboardDataDateParams dateParams) {
		
		String hql="SELECT U.firstName AS salesPersonName , U.phNumber AS salesPersonMobileNumber , U.inviteCode As slaesPersonInviteCode,U.createdOn As salesPersonSignUp,"
				+ " (SELECT COUNT(U1.referralCode)  FROM UserEntity U1 WHERE U1.referralCode=U.inviteCode AND U1.userType='P') as totalCount,"
				+ " (SELECT COUNT(U1.referralCode)  FROM UserEntity U1 WHERE U1.referralCode=U.inviteCode AND DATE(U1.createdOn)=CURRENT_DATE() AND U1.userType='P') as currentDateCount,"
				+ " (SELECT COUNT(U1.referralCode) FROM UserEntity U1 WHERE U1.referralCode=U.inviteCode AND DATE(U1.createdOn)=:yesterday AND U1.userType='P') AS yesterdayDateCount,"
				+ " (SELECT COUNT(U1.referralCode) FROM UserEntity U1 WHERE U1.referralCode=U.inviteCode AND DATE(U1.createdOn) BETWEEN :startDateOfWeek and :endDateOfWeek  AND U1.userType='P') AS weekCount,"
				+ " (SELECT COUNT(U1.referralCode) FROM UserEntity U1 WHERE U1.referralCode=U.inviteCode AND DATE(U1.createdOn) BETWEEN :startDate and :uptoDate AND U1.userType='P') AS uptoDateCount,"
                + " (SELECT MAX(U1.createdOn)  FROM UserEntity U1 WHERE U1.referralCode=U.inviteCode AND U1.userType='P' and U1.createdOn is not null  order by U1.createdOn desc) As lastSingupTime,"

                + " (SELECT COUNT(U1.referralCode)  FROM UserEntity U1 WHERE U1.referralCode=U.inviteCode AND U1.userType='T') as totalCountNAP,"
                + " (SELECT COUNT(U1.referralCode)  FROM UserEntity U1 WHERE U1.referralCode=U.inviteCode AND DATE(U1.createdOn)=CURRENT_DATE() AND U1.userType='T') as currentDateCountNAP,"
                + " (SELECT COUNT(U1.referralCode) FROM UserEntity U1 WHERE U1.referralCode=U.inviteCode AND DATE(U1.createdOn)=:yesterday AND U1.userType='T') AS yesterdayDateCountNAP,"
                + " (SELECT COUNT(U1.referralCode) FROM UserEntity U1 WHERE U1.referralCode=U.inviteCode AND DATE(U1.createdOn) BETWEEN :startDateOfWeek and :endDateOfWeek  AND U1.userType='T') AS weekCountNAP,"
                + " (SELECT COUNT(U1.referralCode) FROM UserEntity U1 WHERE U1.referralCode=U.inviteCode AND DATE(U1.createdOn) BETWEEN :startDate and :uptoDate AND U1.userType='T') AS uptoDateCountNAP,"
				+ " (SELECT MAX(U1.createdOn)  FROM UserEntity U1 WHERE U1.referralCode=U.inviteCode AND U1.userType='T' and U1.createdOn is not null  order by U1.createdOn desc) As lastSingupTimeNap"
                
				+ " FROM UserEntity U WHERE U.userCategroy='S' order by lastSingupTime desc";
		
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("yesterday",
				dateParams.getYesterdayDate());
		query.setParameter("startDateOfWeek", dateParams.getStartingDateOfWeek())
				.setParameter("endDateOfWeek", dateParams.getEndDateOfWeek())
				.setParameter("startDate", dateParams.getStartingDate())
				.setParameter("uptoDate", dateParams.getUptoDate());
		List<SalesPersonReferalInfoDTO> dtoList=query.setResultTransformer(Transformers.aliasToBean(SalesPersonReferalInfoDTO.class)).list();
		
		return dtoList;
	}


@Override
	public List<UsersListDTO> getReferalUsers(Date startDate, Date endDate, String inviteCode, String userType){
		
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		
		if(userType.equals("P")) {
		
		stringBuilder = new StringBuilder("select u.id as userId,u.firstName as firstName ,u.phNumber as phNumber,u.createdOn as createdOn,u.userDetailsEntity.latitude as latitude,u.userDetailsEntity.longitude as longitude from UserEntity u, UserEntity u1 where u.referralCode=u1.inviteCode AND u.referralCode=:inviteCode  AND u.userType=:userType ");
		
		if(startDate==null) {			
			stringBuilder.append(" AND Date(u.createdOn)=current_date() ");
			query = session.createQuery(stringBuilder.toString()).setParameter("userType", "P")
					.setParameter("inviteCode", inviteCode);;
		}else if (startDate != null && endDate == null) {	
			stringBuilder.append(" AND Date(u.createdOn)=:sDate ");
			query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("userType", "P")
					.setParameter("inviteCode", inviteCode);
		}else {
			stringBuilder.append(" AND Date(u.createdOn) between :sDate and :eDate ");
			query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("eDate", endDate).setParameter("userType", "P")
					.setParameter("inviteCode", inviteCode);;
		}
		}
		
		else {
			stringBuilder = new StringBuilder("select u.id as userId,u.firstName as firstName ,u.phNumber as phNumber,u.createdOn as createdOn,u.userDetailsEntity.latitude as latitude,u.userDetailsEntity.longitude as longitude from UserEntity u, UserEntity u1 where u.referralCode=u1.inviteCode AND u.referralCode=:inviteCode  AND u.userType=:userType ");
			
			if(startDate==null) {			
				stringBuilder.append(" AND Date(u.createdOn)=current_date() ");
				query = session.createQuery(stringBuilder.toString()).setParameter("userType", "T")
						.setParameter("inviteCode", inviteCode);;
			}else if (startDate != null && endDate == null) {	
				stringBuilder.append(" AND Date(u.createdOn)=:sDate ");
				query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("userType", "T")
						.setParameter("inviteCode", inviteCode);
			}else {
				stringBuilder.append(" AND Date(u.createdOn) between :sDate and :eDate ");
				query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("eDate", endDate).setParameter("userType", "T")
						.setParameter("inviteCode", inviteCode);;
			}
			
			
		}
	       
		
		List<UsersListDTO> list = query.setResultTransformer(Transformers.aliasToBean(UsersListDTO.class)).list();		
		return list;
		
	}

@Override
public TotalReferalUserBySPDTO getTotalUsersBySP(DashboardDataDateParams dateParams) {
	
	
	String hql="select"
			   + " count(case when u.userType='P' then u.referralCode end)  as overAllTotalCount,"
			   + " count(Case When Date(u.createdOn) = current_date() AND u.userType='P' then u.referralCode end) as totalCurrentDateCount ,"
			   + " count(Case When Date(u.createdOn) =:yesterday  AND u.userType='P' then u.referralCode end) as totalYesterdayDateCount,"
			   + " count(Case When Date(u.createdOn) BETWEEN :startDateOfWeek and :endDateOfWeek  AND u.userType='P' then u.referralCode end) as totalWeekCount ,"
			   + " count(Case When Date(u.createdOn) BETWEEN :startDate and :uptoDate  AND u.userType='P' then u.referralCode end) as totalUptoDateCount, "
			   
			   
               + " count(case when u.userType='T' then u.referralCode end)  as overAllTotalCountNAP,"
               + " count(Case When Date(u.createdOn) = current_date() AND u.userType='T' then u.referralCode end) as totalCurrentDateCountNAP ,"
               + " count(Case When Date(u.createdOn) =:yesterday  AND u.userType='T' then u.referralCode end) as totalYesterdayDateCountNAP,"
               + " count(Case When Date(u.createdOn) BETWEEN :startDateOfWeek and :endDateOfWeek  AND u.userType='T' then u.referralCode end) as totalWeekCountNAP ,"
               + " count(Case When Date(u.createdOn) BETWEEN :startDate and :uptoDate  AND u.userType='T' then u.referralCode end) as totalUptoDateCountNAP "
			   
			   + " FROM UserEntity u where u.referralCode in (select u.inviteCode from UserEntity u where u.userCategroy='S')";
	
	        Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("yesterday",
			                    dateParams.getYesterdayDate());
	                     query.setParameter("startDateOfWeek", dateParams.getStartingDateOfWeek())
			                   .setParameter("endDateOfWeek", dateParams.getEndDateOfWeek())
			                   .setParameter("startDate", dateParams.getStartingDate())
			                   .setParameter("uptoDate", dateParams.getUptoDate());
	               TotalReferalUserBySPDTO dto=(TotalReferalUserBySPDTO) query.setResultTransformer(Transformers.aliasToBean(TotalReferalUserBySPDTO.class)).uniqueResult();
	               
	               return dto;
}



@Override
public List<UsersListDTO> getAllBySPReferalUsersService(Date startDate, Date endDate,String userType){
	
	Session session = sessionFactory.getCurrentSession();
	Query query = null;
	
	if(userType.equals("P")) {
	
	stringBuilder = new StringBuilder("select u1.firstName as salesPersonName,u.firstName as firstName ,u.phNumber as phNumber,u.createdOn as createdOn,u.userDetailsEntity.latitude as latitude,u.userDetailsEntity.longitude as longitude from UserEntity u, UserEntity u1 where u.referralCode=u1.inviteCode AND u.referralCode in (select u.inviteCode from UserEntity u where u.userCategroy='S') AND u.userType=:userType ");
	
	if(startDate==null) {			
		stringBuilder.append(" AND Date(u.createdOn)=current_date() ");
		query = session.createQuery(stringBuilder.toString()).setParameter("userType", "P");
	}else if (startDate != null && endDate == null) {	
		stringBuilder.append(" AND Date(u.createdOn)=:sDate ");
		query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("userType", "P");
	}else {
		stringBuilder.append(" AND Date(u.createdOn) between :sDate and :eDate ");
		query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("eDate", endDate).setParameter("userType", "P");
	}
	}
	
	else {
		stringBuilder = new StringBuilder("select u1.firstName as salesPersonName,u.firstName as firstName ,u.phNumber as phNumber,u.createdOn as createdOn,u.userDetailsEntity.latitude as latitude,u.userDetailsEntity.longitude as longitude from UserEntity u, UserEntity u1 where u.referralCode=u1.inviteCode AND u.referralCode in (select u.inviteCode from UserEntity u where u.userCategroy='S') AND u.userType=:userType ");
		
		if(startDate==null) {			
			stringBuilder.append(" AND Date(u.createdOn)=current_date() ");
			query = session.createQuery(stringBuilder.toString()).setParameter("userType", "T");
		}else if (startDate != null && endDate == null) {	
			stringBuilder.append(" AND Date(u.createdOn)=:sDate ");
			query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("userType", "T");
		}else {
			stringBuilder.append(" AND Date(u.createdOn) between :sDate and :eDate ");
			query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("eDate", endDate).setParameter("userType", "T");
		}
	}
       
	
	List<UsersListDTO> list = query.setResultTransformer(Transformers.aliasToBean(UsersListDTO.class)).list();		
	return list;
	
}

@Override
public List<LatLngDTO> findAllRegisteredUserBySPLatLng(Date startDate,Date endDate,String userType){
	
	Session session = sessionFactory.getCurrentSession();
	Query query = null;
	
	if(userType.equals("P")) {
	
  stringBuilder = new StringBuilder("select u.firstName as title ,u.phNumber as phNumber,u.userDetailsEntity.latitude as lat,u.userDetailsEntity.longitude as lng from UserEntity u, UserEntity u1 where u.referralCode=u1.inviteCode AND u.referralCode in (select u.inviteCode from UserEntity u where u.userCategroy='S') AND u.userType=:userType");
	
    stringBuilder.append(" and u.userDetailsEntity.latitude <> 0 and u.userDetailsEntity.latitude is not null ");
  
	if(startDate==null) {			
		stringBuilder.append(" AND Date(u.createdOn)=current_date() ");
		query = session.createQuery(stringBuilder.toString()).setParameter("userType", "P");
	}else if (startDate != null && endDate == null) {	
		stringBuilder.append(" AND Date(u.createdOn)=:sDate ");
		query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("userType", "P");
	}else {
		stringBuilder.append(" AND Date(u.createdOn) between :sDate and :eDate ");
		query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("eDate", endDate).setParameter("userType", "P");
	}
	
	}
	else {
		
		stringBuilder = new StringBuilder("select u.firstName as title ,u.phNumber as phNumber,u.userDetailsEntity.latitude as lat,u.userDetailsEntity.longitude as lng from UserEntity u, UserEntity u1 where u.referralCode=u1.inviteCode AND u.referralCode in (select u.inviteCode from UserEntity u where u.userCategroy='S') AND u.userType=:userType");
		
	    stringBuilder.append(" and u.userDetailsEntity.latitude <> 0 and u.userDetailsEntity.latitude is not null ");
	  
		if(startDate==null) {			
			stringBuilder.append(" AND Date(u.createdOn)=current_date() ");
			query = session.createQuery(stringBuilder.toString()).setParameter("userType", "T");
		}else if (startDate != null && endDate == null) {	
			stringBuilder.append(" AND Date(u.createdOn)=:sDate ");
			query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("userType", "T");
		}else {
			stringBuilder.append(" AND Date(u.createdOn) between :sDate and :eDate ");
			query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("eDate", endDate).setParameter("userType", "T");
		}
		
	}
	
	List<LatLngDTO> dtoList = query.setResultTransformer(Transformers.aliasToBean(LatLngDTO.class)).list();
	return dtoList;
}




@Override
public List<UsersListDTO> getReferalUsersLatLng(Date startDate, Date endDate, String inviteCode, String userType){
	
	Session session = sessionFactory.getCurrentSession();
	Query query = null;
	
	if(userType.equals("P")) {
	
	stringBuilder = new StringBuilder("select u.firstName as firstName ,u.phNumber as phNumber,u.createdOn as createdOn,u.userDetailsEntity.latitude as latitude,u.userDetailsEntity.longitude as longitude from UserEntity u, UserEntity u1 where u.referralCode=u1.inviteCode AND u.referralCode=:inviteCode  AND u.userType=:userType AND u.userDetailsEntity.latitude <> 0 and u.userDetailsEntity.latitude is not null ");
	
	if(startDate==null) {			
		stringBuilder.append(" AND Date(u.createdOn)=current_date() ");
		query = session.createQuery(stringBuilder.toString()).setParameter("userType", "P")
				.setParameter("inviteCode", inviteCode);;
	}else if (startDate != null && endDate == null) {	
		stringBuilder.append(" AND Date(u.createdOn)=:sDate ");
		query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("userType", "P")
				.setParameter("inviteCode", inviteCode);
	}else {
		stringBuilder.append(" AND Date(u.createdOn) between :sDate and :eDate ");
		query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("eDate", endDate).setParameter("userType", "P")
				.setParameter("inviteCode", inviteCode);;
	}
	}
	
	else {
		stringBuilder = new StringBuilder("select u1.firstName as firstName,u.firstName as firstName ,u.phNumber as phNumber,u.createdOn as createdOn,u.userDetailsEntity.latitude as latitude,u.userDetailsEntity.longitude as longitude from UserEntity u, UserEntity u1 where u.referralCode=u1.inviteCode AND u.referralCode=:inviteCode  AND u.userType=:userType AND u.userDetailsEntity.latitude <> 0 and u.userDetailsEntity.latitude is not null ");
		
		if(startDate==null) {			
			stringBuilder.append(" AND Date(u.createdOn)=current_date() ");
			query = session.createQuery(stringBuilder.toString()).setParameter("userType", "T")
					.setParameter("inviteCode", inviteCode);;
		}else if (startDate != null && endDate == null) {	
			stringBuilder.append(" AND Date(u.createdOn)=:sDate ");
			query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("userType", "T")
					.setParameter("inviteCode", inviteCode);
		}else {
			stringBuilder.append(" AND Date(u.createdOn) between :sDate and :eDate ");
			query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("eDate", endDate).setParameter("userType", "T")
					.setParameter("inviteCode", inviteCode);;
		}
		
		
	}
       
	
	List<UsersListDTO> list = query.setResultTransformer(Transformers.aliasToBean(UsersListDTO.class)).list();		
	return list;
	
}




public List<SalesPersonReferalInfoDTO> getSalesPersonDataInDateRange(Date startDate,Date endDate) {
	
	String hql="SELECT U.firstName AS salesPersonName , U.phNumber AS salesPersonMobileNumber , U.inviteCode As slaesPersonInviteCode,U.createdOn As salesPersonSignUp,"
			
			+ " (SELECT COUNT(U1.referralCode) FROM UserEntity U1 WHERE U1.referralCode=U.inviteCode AND DATE(U1.createdOn) BETWEEN :startDate and :endDate AND U1.userType='P') AS totalCount,"
            + " (SELECT MAX(U1.createdOn)  FROM UserEntity U1 WHERE U1.referralCode=U.inviteCode AND U1.userType='P' and U1.createdOn is not null  order by U1.createdOn desc) As lastSingupTime,"

          
            + " (SELECT COUNT(U1.referralCode) FROM UserEntity U1 WHERE U1.referralCode=U.inviteCode AND DATE(U1.createdOn) BETWEEN :startDate and :endDate AND U1.userType='T') AS totalCountNAP,"
			+ " (SELECT MAX(U1.createdOn)  FROM UserEntity U1 WHERE U1.referralCode=U.inviteCode AND U1.userType='T' and U1.createdOn is not null  order by U1.createdOn desc) As lastSingupTimeNap"
            
			+ " FROM UserEntity U WHERE U.userCategroy='S'";
	
	Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("startDate", startDate).setParameter("endDate", endDate);
			
	List<SalesPersonReferalInfoDTO> dtoList=query.setResultTransformer(Transformers.aliasToBean(SalesPersonReferalInfoDTO.class)).list();
	System.out.println("List size=="+dtoList.size());
	for(SalesPersonReferalInfoDTO dto:dtoList) {
		System.out.println("count"+dto.getTotalCount());
	}
	return dtoList;
}





@Override
public List<UsersListDTO> getReferalUsersInDateRange(Date startDate, Date endDate, String inviteCode, String userType){
	
	Session session = sessionFactory.getCurrentSession();
	Query query = null;
	
	if(userType.equals("P")) {
	
	stringBuilder = new StringBuilder("select u.id as userId,u.firstName as firstName ,u.phNumber as phNumber,u.createdOn as createdOn,u.userDetailsEntity.latitude as latitude,u.userDetailsEntity.longitude as longitude from UserEntity u, UserEntity u1 where u.referralCode=u1.inviteCode AND u.referralCode=:inviteCode  AND u.userType=:userType ");
	
	
		stringBuilder.append(" AND Date(u.createdOn) between :sDate and :eDate ");
		query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("eDate", endDate).setParameter("userType", "P")
				.setParameter("inviteCode", inviteCode);;
	
	}
	
	else {
		stringBuilder = new StringBuilder("select u.id as userId,u.firstName as firstName ,u.phNumber as phNumber,u.createdOn as createdOn,u.userDetailsEntity.latitude as latitude,u.userDetailsEntity.longitude as longitude from UserEntity u, UserEntity u1 where u.referralCode=u1.inviteCode AND u.referralCode=:inviteCode  AND u.userType=:userType ");
		
		
			stringBuilder.append(" AND Date(u.createdOn) between :sDate and :eDate ");
			query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("eDate", endDate).setParameter("userType", "T")
					.setParameter("inviteCode", inviteCode);;
		
		
		
	}
       
	
	List<UsersListDTO> list = query.setResultTransformer(Transformers.aliasToBean(UsersListDTO.class)).list();		
	return list;
	
}



@Override
public List<UsersListDTO> getIndivualfilterreferaluserLatLng(Date startDate, Date endDate, String inviteCode, String userType){
	
	Session session = sessionFactory.getCurrentSession();
	Query query = null;
	
	if(userType.equals("P")) {
	
	stringBuilder = new StringBuilder("select u.id as userId,u.firstName as firstName ,u.phNumber as phNumber,u.createdOn as createdOn,u.userDetailsEntity.latitude as latitude,u.userDetailsEntity.longitude as longitude from UserEntity u, UserEntity u1 where u.referralCode=u1.inviteCode AND u.referralCode=:inviteCode  AND u.userType=:userType AND u.userDetailsEntity.latitude <> 0 and u.userDetailsEntity.latitude is not null");
	
	
		stringBuilder.append(" AND Date(u.createdOn) between :sDate and :eDate ");
		query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("eDate", endDate).setParameter("userType", "P")
				.setParameter("inviteCode", inviteCode);;
	
	}
	
	else {
		stringBuilder = new StringBuilder("select u.id as userId,u.firstName as firstName ,u.phNumber as phNumber,u.createdOn as createdOn,u.userDetailsEntity.latitude as latitude,u.userDetailsEntity.longitude as longitude from UserEntity u, UserEntity u1 where u.referralCode=u1.inviteCode AND u.referralCode=:inviteCode  AND u.userType=:userType AND u.userDetailsEntity.latitude <> 0 and u.userDetailsEntity.latitude is not null ");
		
		
			stringBuilder.append(" AND Date(u.createdOn) between :sDate and :eDate ");
			query = session.createQuery(stringBuilder.toString()).setParameter("sDate", startDate).setParameter("eDate", endDate).setParameter("userType", "T")
					.setParameter("inviteCode", inviteCode);;
		
		
		
	}
       
	
	List<UsersListDTO> list = query.setResultTransformer(Transformers.aliasToBean(UsersListDTO.class)).list();		
	return list;
	
}


@Override
public List<MerchantInfoBySPDTO> merchantInfoBySalesPerson(String userId){
	
	Session session = sessionFactory.getCurrentSession();
	Query query = null;
	
	stringBuilder = new StringBuilder("select u.FirstName as salesPersonName ,u.UserLogin as "
			+ " salesPersonMobileNumber, m.Name as name,m.MobileNumber as mobileNumber,"
			+ " m.Enterprise as enterprise,m.City as city,m.State as state,m.ShopBlobID as "
			+ " shopBlobId,m.NatureOfBusiness as natureOfBusiness,m.InstallationStatus as installationStatus,"
			+ "  m.ShopArea as shopArea, m.IsMerchant as merchant, "
			+ " m.installationMedium as InstallationMedium, cast(m.CreatedOn AS DATETIME) as createdOn "
			+ " from MerchantInfoSP m LEFT JOIN Users u ON m.CreatedBy=u.UserId");
	if(userId.equals("0")) {
	
	
	}
	else {
		stringBuilder.append(" where UserId="+userId+"");
	}
	stringBuilder.append(" order by m.CreatedOn desc");
	query=session.createSQLQuery(stringBuilder.toString());
	List<MerchantInfoBySPDTO> dtoList = query.setResultTransformer(Transformers.aliasToBean(MerchantInfoBySPDTO.class)).list();
	return dtoList;
} 

@Override
public List<SalesInfoDTO> salesPersonInfo(){
	String hql = "SELECT U.firstName AS salesPersonName , U.phNumber AS salesPersonMobileNumber , U.inviteCode As slaesPersonInviteCode,U.id As salesPersonId FROM UserEntity U WHERE U.userCategroy='S' ";
			
	Query query = sessionFactory.getCurrentSession().createQuery(hql);
	List<SalesInfoDTO> dtoList = query.setResultTransformer(Transformers.aliasToBean(SalesInfoDTO.class)).list();
	return dtoList;
}

@Override
public int getTotalEarnedPoints(int userId) {
	Session session=sessionFactory.getCurrentSession();
	String hql="select u.usersTotalEarnedPointsEntity.totalPointsEarned from  UserEntity u where u.id=:userId";
	Query query=session.createQuery(hql);
	query.setParameter("userId", userId);
	return (Integer)query.uniqueResult();
}



@Override
public List<LatLngDTO> getAllMerchantbyIndiviualspmap(int userId){
	
	if(userId==0) {
		String hql = "select m.name as title ,m.mobileNumber as phNumber,m.salesPersonlatitude as lat,m.salesPersonlongitude as lng from MerchantInfoBySalesPersonEntity m where "
				+ " m.salesPersonlatitude <> 0 and m.salesPersonlongitude is not null";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<LatLngDTO> dtoList = query.setResultTransformer(Transformers.aliasToBean(LatLngDTO.class)).list();
		System.out.println("aya "+dtoList.size());
		return dtoList;
	    
	}
	
	else {
		
		String hql = "select m.name as title ,m.mobileNumber as phNumber,m.salesPersonlatitude as lat,m.salesPersonlongitude as lng from MerchantInfoBySalesPersonEntity m where salesPersonId=:userId "
				+ " and  m.salesPersonlatitude <> 0 and m.salesPersonlongitude is not null";
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("userId", userId);
		List<LatLngDTO> dtoList = query.setResultTransformer(Transformers.aliasToBean(LatLngDTO.class)).list();
		return dtoList;
		
	}
	
	
}

@Override
public List<LatLngDTO> getBlupayUsersLatLng(String period ,Date startDate, Date endDate,Date weekStartDate,int month){
	
	Session session = sessionFactory.getCurrentSession();
	Query query = null;
	
	
	stringBuilder = new StringBuilder("select u.firstName as title,u.phNumber as phNumber, u.userDetailsEntity.latitude as lat,u.userDetailsEntity.longitude as lng from UserEntity u where  (u.userDetailsEntity.latitude <> 0 and u.userDetailsEntity.latitude is not null) ");
	
	// this if is giving you all registerd users latitude , longitude and user firstName, phnumber
	
 if(period.equalsIgnoreCase("AllR")) {
		
		startDate=null;
		endDate=null;
		
		stringBuilder.append(" AND u.userType='P' ");
		
		
	}
 
//this else if is giving you all Unregisterd users latitude , longitude and user firstName, phnumber
 
 
 else if(period.equalsIgnoreCase("AllU")) {
	   
	   startDate=null;
		endDate=null;
		
		stringBuilder.append(" AND u.userType='T' ");
		
	}
 

 //this else if is giving you Today registerd users latitude , longitude and user firstName, phnumber
 
 else if(period.equalsIgnoreCase("TR")) {
	 
	 startDate=null;
	 endDate=null;
	 
	 stringBuilder.append("AND Date(u.createdOn)=current_date() AND UserType='P' ");
 }
 
 
 //this else if is giving you Today Unregisterd users latitude , longitude and user firstName, phnumber

 
 else if(period.equalsIgnoreCase("TU")) {
	 
	 startDate=null;
	 endDate=null;
	 
	 stringBuilder.append("AND Date(u.createdOn)=current_date() AND UserType='T' ");
 }
 
 
 //this else if is giving you CurrentWeek registerd users latitude , longitude and user firstName, phnumber

 
else if(period.equalsIgnoreCase("WR")) {
	 
	 startDate=null;
	 endDate=null;
	 
	 stringBuilder.append("AND Date(u.createdOn) between :weekstartday and NOW() AND UserType='P' ");
 }
 
 //this else if is giving you CurrentWeek Unregisterd users latitude , longitude and user firstName, phnumber

 
else if(period.equalsIgnoreCase("WU")) {
	 
	 startDate=null;
	 endDate=null;
	 
	 stringBuilder.append("AND Date(u.createdOn) between :weekstartday and NOW() AND UserType='T' ");
}
 
 
 //this else if is giving you CurrentMonth registerd users latitude , longitude and user firstName, phnumber

 
else if(period.equalsIgnoreCase("MR")) {
	 
	 startDate=null;
	 endDate=null;
	 
	 stringBuilder.append("AND Month(u.createdOn)=:month AND UserType='P' ");
}
 
 
 //this else if is giving you CurrentMonth Unregisterd users latitude , longitude and user firstName, phnumber

 
else if(period.equalsIgnoreCase("MU")) {
	 
	 startDate=null;
	 endDate=null;
	 
	 stringBuilder.append("AND Month(u.createdOn)=:month AND UserType='T' ");
}
 

	
 //this else if is giving you  users latitude , longitude and user firstName, phnumber in a date range

	
 else if(startDate!=null && endDate!=null) {
		stringBuilder.append(" AND Date(u.createdOn) between :sDate and :eDate ");
		System.out.println("daoImpl mai period"+period);
		
		 //this  if is giving you All  users latitude , longitude and user firstName, phnumber in a Date Range

		
		if(period.equalsIgnoreCase("AllD")) {
			
		}
		
		 //this  if is giving you All registerd  users latitude , longitude and user firstName, phnumber in a Date Range

		
		else if(period.equalsIgnoreCase("RD")){
			
			stringBuilder.append(" AND u.userType='P' ");
			
			
	//this  else is giving you All temporary  users latitude , longitude and user firstName, phnumber in a Date Range

		}
		else {
			
			stringBuilder.append(" AND u.userType='T' ");
		}
		
	}
	
	
	
   
	
	
	query = session.createQuery(stringBuilder.toString());
	setParameters(query, period, startDate, endDate,weekStartDate,month);
	System.out.println("query===="+stringBuilder.toString());
	List<LatLngDTO> list = query.setResultTransformer(Transformers.aliasToBean(LatLngDTO.class)).list();		
	return list;
	
	
}

private void setParameters(Query query,String period,Date startDate,Date endDate, Date weekStartDate,int month) {
	
	DashboardDataDateParams dateParams = getDashboardDateParams();	
if(startDate!=null && endDate!=null) {

		
		query.setParameter("sDate",startDate);
		query.setParameter("eDate", endDate);
		   
	

}

if((period.equalsIgnoreCase("WR")) || (period.equalsIgnoreCase("WU"))) {
	
	query.setParameter("weekstartday",weekStartDate);
	
	
}

if((period.equalsIgnoreCase("MR")) || (period.equalsIgnoreCase("MU"))) {
	
	query.setParameter("month",month);
	
	
}
}


}
