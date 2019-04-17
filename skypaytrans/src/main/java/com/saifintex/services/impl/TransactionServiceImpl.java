package com.saifintex.services.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.JsonObject;
import com.saifintex.common.AbstractBase;
import com.saifintex.dao.AppPreferencesDAO;
import com.saifintex.dao.BillNumberSeriesDAO;
import com.saifintex.dao.ItemMasterDAO;
import com.saifintex.dao.NotificationDAO;
import com.saifintex.dao.NotificationStatusDAO;
import com.saifintex.dao.NotificationTypeDAO;
import com.saifintex.dao.RewardPointsDAO;
import com.saifintex.dao.TransactionDAO;
import com.saifintex.dao.UserDAO;
import com.saifintex.dao.UsersBalanceDAO;
import com.saifintex.dao.UsersPointsMappingDAO;
import com.saifintex.dao.UsersTotalEarnedPointsDAO;
import com.saifintex.domain.ReminderNotificationParams;
import com.saifintex.domain.TotalBillAmount;
//import com.saifintex.domain.Transaction;
import com.saifintex.domain.TransactionActionParams;
import com.saifintex.domain.TransactionsFilterParams;
import com.saifintex.dto.AssociatedUsersDTO;
import com.saifintex.dto.DashboardDataDateParams;
import com.saifintex.dto.ItemMasterDTO;
import com.saifintex.dto.MerchantInfoBySalesPersonDto;
import com.saifintex.dto.SalesPurchaseDetailDto;
import com.saifintex.dto.TransactionSummaryParamsDto;
import com.saifintex.dto.TransactionsDTO;
import com.saifintex.dto.UserDTO;
import com.saifintex.entity.AppPreferencesEntity;
import com.saifintex.entity.BillNumberSeriesEntity;
import com.saifintex.entity.ItemMasterEntity;
import com.saifintex.entity.NotificationEntity;
import com.saifintex.entity.NotificationStatusEntity;
import com.saifintex.entity.NotificationTypeEntity;
import com.saifintex.entity.RelationsEntity;
import com.saifintex.entity.RewardPointsEntity;
import com.saifintex.entity.TransactionsEntity;
import com.saifintex.entity.UserEntity;
import com.saifintex.entity.UsersBalanceEntity;
import com.saifintex.entity.UsersInvitationsDetailEntity;
import com.saifintex.entity.UsersNameMapping;
import com.saifintex.entity.UsersPointsMappingEntity;
import com.saifintex.entity.UsersTotalEarnedPointsEntity;
import com.saifintex.exception.FCMException;
import com.saifintex.exception.FileDoesNotUploadException;
import com.saifintex.services.TransactionService;
import com.saifintex.services.UserService;
import com.saifintex.services.UsersInvitationsDetailService;
import com.saifintex.utils.ApplicationProperties;
import com.saifintex.utils.DateUtils;
import com.saifintex.utils.FcmNotificationUtil;
import com.saifintex.web.domain.Transactions;
import com.saifintex.web.dto.TransactionStatsDashboardDTO;
import com.saifintex.web.dto.TransactionStatsReportDTO;
import com.saifintex.web.dto.TransactionsRecordDto;

@Service("transactionService")
@Transactional
public class TransactionServiceImpl extends AbstractBase implements TransactionService {

	private TotalBillAmount amount;
	@Autowired
	ApplicationProperties properties;
	@Autowired
	TransactionDAO transactionDao;
	@Autowired
	UserDAO userDao;
	@Autowired
	NotificationTypeDAO notificationTypeDao;
	
	@Autowired
	NotificationDAO notificationDao;
	
	@Autowired
	NotificationStatusDAO notificationStatusDao;
	
	@Autowired
	UsersPointsMappingDAO usersPointsMappingDAO;

	@Autowired
	RewardPointsDAO rewardPointsDao;

	@Autowired
	UsersTotalEarnedPointsDAO usersTotalEarnedPointsDAO;

	@Value("${app.FCMAuthKey}")
	public String authKey;
	
	@Autowired
	UserService userService;

	@Autowired
	private UsersBalanceDAO usersBalanceDAO;

	@Autowired
	private ItemMasterDAO itemMasterDao;

	@Autowired
	private AppPreferencesDAO appPreferencesDAO;
	
	@Autowired
	private BillNumberSeriesDAO billNumberSeriesDAO;
	@Autowired
	private UsersInvitationsDetailService usersInvitationsDetailService;
	
	@Value("${app.temptransaction.timelimit}")
	private long tempTransTimeLimit;

	@Value("${transaction.id.prefix}")
	private String transactionIdPrefix;

	@Value("${transaction.id.truncate.value}")
	private int transactionIdTrunValue;
	
	@Value("${app.invitation.timelimit}")
	private int invitationTimeLimit;

	@Value("${app.invitation.sms.limit}")
	private int invitationSMSLimit;
	
	@Value("${app.invoice.pic.folder}")
	private String invoicePicPath;
	// @Transactional(propagation=Propagation.REQUIRES_NEW)
	
	
	/**@author Ajay
	 * insert transaction method is required to store the transaction into db after implementing several business logics
	 * @param TransactionDTO transaction object having detail regarding transaction
	 * @return TransactionDTO return transaction dto object which is after storing it into database
	 */
	public TransactionsDTO insertTransaction(TransactionsDTO transaction) throws FileDoesNotUploadException {
		TransactionsEntity transactionEntity = new TransactionsEntity();

		int loggedInUserId = 0, opponentUserId = 0;
		int initiatedFrom = transaction.getInitiatedFrom();
		int payerId = transaction.getPayerId();
		if (payerId == initiatedFrom) {
			loggedInUserId = initiatedFrom;
			opponentUserId = transaction.getPayeeId();
		} else {
			loggedInUserId = initiatedFrom;
			opponentUserId = transaction.getPayerId();
		}
		
		

		List<?> list = null;
        transaction.setTransactionId(transactionId());
        
        if(transaction.getFile()!=null && !transaction.getFile().getOriginalFilename().isEmpty()) {
        String blobId = getInvoicePicFileName(transaction.getFile().getOriginalFilename(),transaction);
        transaction.setInvoiceBlobId(blobId);
        if (!uploadFile(transaction.getFile(), invoicePicPath, transaction.getInvoiceBlobId())) {
        	throw new FileDoesNotUploadException();
        }
        }
        
        
		UsersBalanceEntity ubEntity = usersBalanceDAO.getByBothUsersId(loggedInUserId, opponentUserId);
		transaction.setCurrTranPrevBal(getMutualBalance(ubEntity, loggedInUserId, opponentUserId));
		setCurrentPaymentStatus(transaction);
        transaction.setBillNumber(generateBillNumber(transaction));
		BeanUtils.copyProperties(transaction, transactionEntity);
		
		/*transactionEntity.setTransactionId(transactionId());*/
		transactionEntity.setCreatedBy(transactionEntity.getInitiatedFrom());
		Date date = DateUtils.getCurrentDateTime();
		transactionEntity.setPaymentDate(date);
		transactionEntity.setCreatedOn(date);
		transactionEntity.setModifiedOn(date);
		transactionEntity.setModifiedBy(transactionEntity.getInitiatedFrom());
		if (ubEntity == null) {
			insertMutualBalanceForFirstTime(transactionEntity, ubEntity);
		} else {
			updatePendingTransactionAmount(transactionEntity, ubEntity);
		}

		saveUsersItems(transactionEntity);

		list = transactionDao.insert(transactionEntity);
		if(transactionEntity.getTransactionType().equalsIgnoreCase("Business")) {
			userService.setIsMerchant(transactionEntity.getPayeeId());
		}

		TransactionsDTO transactionDto = copyInTransactionDto(list);
		
		if(transactionDto!=null) {
			AppPreferencesEntity appPreferencesEntity=appPreferencesDAO.getAppPreferences(1);
			if(appPreferencesEntity.isTransactionBenefits()) {
				if(appPreferencesEntity.isRewardOnFirstTxn()) {
					
					if(transactionDao.getUsersTransactionCount(loggedInUserId)<=1) {
						
						updateRewardsPoints(loggedInUserId,opponentUserId,transactionDto, appPreferencesEntity);
					}
					
				}else {
					
					updateRewardsPoints(loggedInUserId,opponentUserId,transactionDto, appPreferencesEntity);
				}
			}
		}
	/*@Ajay notifiy start - need to optimise later */					
		
		String token = null;
		int receiverId = 0;
		int senderId=0;
		if (transaction.getInitiatedFrom() == transaction.getPayerId()) {
			receiverId = transaction.getPayeeId();
			senderId=transaction.getPayerId();
		} else {
			receiverId = transaction.getPayerId();
			senderId=transaction.getPayeeId();
		}

		token = userService.getToken(receiverId);
		if (token != null && !token.isEmpty()) {
			NotificationTypeEntity notificationTypeEntity=null;
			NotificationStatusEntity nStatusEntity=notificationStatusDao.getOne(NotificationStatusEntity.class, 1);
			JsonObject data = new JsonObject();
			data.addProperty("type", "pending");
			data.addProperty("payId", transactionDto.getPayId());
			data.addProperty("phoneNumber", transaction.getRecipientMobileNumber());
			try {

				String message = null;
				if (transaction.getBillAmount().compareTo(BigDecimal.ZERO) == 1) {
					notificationTypeEntity=notificationTypeDao.getOne(NotificationTypeEntity.class, properties.getnTypeTransBusinessId());
					message = notificationTypeEntity.getNotificationTemplateEntity().getTemplateBody() + " " + transaction.getBillAmount() + "";
				} else {
					notificationTypeEntity=	notificationTypeDao.getOne(NotificationTypeEntity.class, properties.getnTypeTransId());
					message = notificationTypeEntity.getNotificationTemplateEntity().getTemplateBody() + " " + transaction.getCashReceived() + "";
				}
				
				notificationDao.save(createNotificationEntity(senderId, receiverId, message, 
						notificationTypeEntity.getNotificationTemplateEntity().getTemplateTitle(),
						notificationTypeEntity, nStatusEntity));
				FcmNotificationUtil.sendNotification(token, message, authKey, data);
			} catch (FCMException e) {
				getLogger().info("fcm notification exception ");
			}
		}
		
		
		/*@Ajay notifiy end - need to optimise later */	
		if(transactionDto.getUserType().equalsIgnoreCase("T")) {
			inviteOnTransaction(loggedInUserId, opponentUserId, transaction);
		}
		return transactionDto;
	}
	
	
	private void updateRewardsPoints(int loggedInUserId,int opponentUserId,TransactionsDTO transactionsDTO,AppPreferencesEntity appPreferencesEntity) {
		RewardPointsEntity rpEntity = rewardPointsDao.findRewardPointEntityWithActiveState(3);
		UsersPointsMappingEntity upmEntity = setRewardPointsMapping(loggedInUserId, opponentUserId,
				rpEntity); // set reward point for referenced user
		int points = upmEntity.getPointsEarned();
		usersPointsMappingDAO.save(upmEntity);
		UserEntity userEntity=userDao.getOne(UserEntity.class, loggedInUserId);
		transactionsDTO.setPoints(rpEntity.getValue());
		saveTotalEarnedPointsForUser(points, userEntity);
	}
	
	// map both users in UsersPointsMappingEntity table
		private UsersPointsMappingEntity setRewardPointsMapping(int referencedUserId, int signedUpUserId,
				RewardPointsEntity rewardPointsEntity) {
			UsersPointsMappingEntity upmEntity = new UsersPointsMappingEntity();
			upmEntity.setUserId(signedUpUserId); // set newly signed up user id
			upmEntity.setReferenceId(referencedUserId); // set referenced user's id
			Date date = DateUtils.getCurrentDateTime();
			upmEntity.setCreatedOn(date);
			upmEntity.setCreatedBy(signedUpUserId);
			upmEntity.setModifiedOn(date);
			upmEntity.setModifiedBy(signedUpUserId);
			Set<RewardPointsEntity> rewardPointsEntities = new HashSet<RewardPointsEntity>();
			if (rewardPointsEntity == null) {
				rewardPointsEntity = rewardPointsDao.findRewardPointEntityWithActiveState(2); // // 2 here to check sign up
																								// benefit is there or not
				if (rewardPointsEntity == null) {
					return null;
				}
			}
			rewardPointsEntities.add(rewardPointsEntity);
			upmEntity.setRewardPointsEntity(rewardPointsEntities);
			upmEntity.setPointsEarned(rewardPointsEntity.getValue());
			return upmEntity;
		}

		// for user having invite code
		private void saveTotalEarnedPointsForUser(int points, UserEntity userEntity) {
			Date date = DateUtils.getCurrentDateTime();
			UsersTotalEarnedPointsEntity utepEntity = userEntity.getUsersTotalEarnedPointsEntity();
			int earnedPoints = utepEntity.getTotalPointsEarned();
			utepEntity.setTotalPointsEarned(earnedPoints + points);
			utepEntity.setModifiedBy(userEntity.getId());
			utepEntity.setModifiedOn(date);
			userEntity.setUsersTotalEarnedPointsEntity(utepEntity);
		}

		// for newly signed up user
		private void saveTotalEarnedPointsForUser(int points, int userId) {
			UserEntity entityFromDB = userDao.getOne(UserEntity.class, userId);
			UsersTotalEarnedPointsEntity utepEntity = entityFromDB.getUsersTotalEarnedPointsEntity();
			int earnedPoints = utepEntity.getTotalPointsEarned();
			utepEntity.setTotalPointsEarned(earnedPoints + points);
			utepEntity.setModifiedBy(entityFromDB.getId());
			utepEntity.setModifiedOn(DateUtils.getCurrentDateTime());
		}

	
	
/**
 * A method to send invitation message to NAP user to install the app.
 * @author Ajay
 * @param loggedInUserId A user id required to get logged in user
 * @param opponentUserId A user id required to get opponent user
 * @param transaction A transaction object 
 * @return void
 */
	private void inviteOnTransaction(int loggedInUserId,int opponentUserId,TransactionsDTO transaction) {
		UserDTO opponentUser=userService.getUserById(opponentUserId);
		UserDTO loggedInUser=userService.getUserById(loggedInUserId);
		ReminderNotificationParams params=new ReminderNotificationParams();
		if(transaction.getBillAmount().compareTo(BigDecimal.ZERO)==1) {
		 params.setTotalBalance(transaction.getBillAmount());
		}else {
			params.setTotalBalance(transaction.getCashReceived());
		}
		params.setLoggedInUserName(transaction.getUserName());
		params.setRecipientMobileNumber(opponentUser.getPhNumber());
		params.setSenderReferralCode(loggedInUser.getInviteCode());
		usersInvitationsDetailService.sendInvitation(params);
	}
	private BigDecimal getMutualBalance(int loggedInUserId, int opponentUserId, UsersBalanceEntity ubEntity) {
		// UsersBalanceEntity
		// usersBalanceEntity=usersBalanceDAO.getByBothUsersId(loggedInUserId,
		// opponentUserId);
		if (ubEntity == null) {
			return BigDecimal.ZERO;
		}
		BigDecimal mutualBalance = getMutualBalance(ubEntity, loggedInUserId, opponentUserId);
		return mutualBalance;
	}

	private BigDecimal getMutualBalance(UsersBalanceEntity balanceEntity, int loggedInuserId, int opponentUserId) {
		if (balanceEntity == null) {
			return BigDecimal.ZERO;
		}
		BigDecimal balance = null;
		int user2 = balanceEntity.getUser2();
		BigDecimal balanceDecimal = balanceEntity.getBalance().add(balanceEntity.getPendingTransactionAmount());
		int result = balanceDecimal.compareTo(BigDecimal.ZERO);

		if (loggedInuserId == user2 && result == 1) {
			balance = balanceDecimal.negate();
			return balance;
		} else if (loggedInuserId == user2 && result == -1) {
			balance = balanceDecimal.abs();
			return balance;
		}

		balance = balanceDecimal;
		return balance;
	}
	
	private String generateBillNumber(TransactionsDTO transaction) {
		
		
		if(transaction.getTransactionType().equalsIgnoreCase("Business")) {
			
		return generateBillNumber(transaction.getPayeeId());
		}
		return generateBillNumber(transaction.getPayerId());
}
	
	private String generateBillNumber(int userId) {
		StringBuilder billNumber=new StringBuilder();
		BillNumberSeriesEntity billNumberSeriesEntity=billNumberSeriesDAO.getBYUserId(userId);
		
		billNumber.append(userId);
		billNumber.append("/");
		billNumber.append(DateUtils.getDateInDDMMMMYYYYFormat());
		billNumber.append("/");
		if(billNumberSeriesEntity==null) {
			saveBillNumberSeriesEntity(userId);
			billNumber.append("1");
		}else {
			billNumber.append(billNumberSeriesEntity.getIncrementValue());
			billNumberSeriesEntity.setIncrementValue(billNumberSeriesEntity.getIncrementValue()+1);
			
		}
		return billNumber.toString();
	}
	
	
	private void saveBillNumberSeriesEntity(int userId) {
		BillNumberSeriesEntity billNumberSeriesEntityToSave=new BillNumberSeriesEntity();
		billNumberSeriesEntityToSave.setIncrementValue(2);
		billNumberSeriesEntityToSave.setCreatedBy(1);
		billNumberSeriesEntityToSave.setUserId(userId);
		billNumberSeriesEntityToSave.setCreatedOn(DateUtils.getCurrentDateTime());
		billNumberSeriesEntityToSave.setModifiedBy(1);
		billNumberSeriesEntityToSave.setModifiedOn(DateUtils.getCurrentDateTime());
		billNumberSeriesDAO.save(billNumberSeriesEntityToSave);
	}

	@Override
	public List<AssociatedUsersDTO> getAssociatedUsers(int id, int pageSize) {
		List list = transactionDao.getAssociatedUsers(id, pageSize);
		if (list == null) {
			return null;
		}
		List<AssociatedUsersDTO> dtoList = getAssociatedUsersInDto(list, id, true);
		return dtoList;
	}

	@Override
	public List<TransactionsDTO> getLedger(int loggedInUserId, int oppositeUserId, int pageSize) {
		List<TransactionsEntity> entityList = transactionDao.getLedger(loggedInUserId, oppositeUserId, pageSize);
		List<TransactionsDTO> transactionsDto = new ArrayList<TransactionsDTO>();
		for (TransactionsEntity entity : entityList) {
			TransactionsDTO dto = new TransactionsDTO();
			BeanUtils.copyProperties(entity, dto);
			dto.setTempTransTimeLimit(tempTransTimeLimit);
			getMutulaBalanceForLedger(dto, loggedInUserId);
			dto.setCurrentPaymentStatus(changeBalanceAccordingToUser(dto.getCurrentPaymentStatus(),
					dto.getInitiatedFrom(), loggedInUserId));
			dto.setCurrTranPrevBal(
					changeBalanceAccordingToUser(dto.getCurrTranPrevBal(), dto.getInitiatedFrom(), loggedInUserId));
			dto.setTransactionId(truncateTransactionId(dto.getTransactionId()));
			transactionsDto.add(dto);
		}
		return transactionsDto;

	}

	private void getMutulaBalanceForLedger(TransactionsDTO dto, int loggedInUserId) {
		int payeeId = dto.getPayeeId();

		BigDecimal balance = dto.getSkyCredit();
		int result = balance.compareTo(BigDecimal.ZERO);
		if (loggedInUserId == payeeId) {
			if (result == 1) {
				dto.setSkyCredit(balance.negate());
				return;
			} else if (result == -1) {
				dto.setSkyCredit(balance.abs());
				return;
			}
		}
	}

	private List<AssociatedUsersDTO> getAssociatedUsersInDto(List list, int id, boolean isAssosicatedUsersRequest) {
		List<AssociatedUsersDTO> dtoList = new ArrayList<AssociatedUsersDTO>();
		for (int i = 0; i < list.size(); i++) {
			AssociatedUsersDTO dto = new AssociatedUsersDTO();
			Object[] obj = (Object[]) list.get(i);
			for (int j = 0; j < obj.length; j++) {
				if (obj[j] instanceof UserEntity) {
					UserEntity userEntity = (UserEntity) obj[j];
					dto.setUserId(userEntity.getId());
					dto.setName(userEntity.getFirstName());
					dto.setEmailId(userEntity.getUserDetailsEntity().getEmailId());
					dto.setPhNumber(userEntity.getPhNumber());
					dto.setAddress(userEntity.getUserDetailsEntity().getAddress1());
					dto.setUserType(userEntity.getUserType());
					dto.setGstinNumber(userEntity.getGstinNumber());
					dto.setActive(userEntity.isActive());
				} else if (obj[j] instanceof UsersBalanceEntity) {
					UsersBalanceEntity balanceEntity = (UsersBalanceEntity) obj[j];
					/*
					 * if(id==balanceEntity.getUser1()) {
					 * dto.setMappedName(balanceEntity.getUsersNameMapping().getUserName2()); }else
					 * { dto.setMappedName(balanceEntity.getUsersNameMapping().getUserName1()); }
					 */
					setMappedAndMappedName(id, balanceEntity, dto);
					setRelation(id, balanceEntity, dto);
					BigDecimal balance = getMutualBalance(balanceEntity, id);
					dto.setBalance(balance);
					dto.setCreatedOn(balanceEntity.getCreatedOn());
					dto.setCreatedBy(balanceEntity.getCreatedBy());
					dto.setModifiedBy(balanceEntity.getModifiedBy());
					dto.setModifiedOn(balanceEntity.getModifiedOn());
				} else {
					dto.setBlobId((String) obj[j]);
				}
			}

			dtoList.add(dto);
		}

		return dtoList;
	}

	private void setRelation(int loggedInUserId, UsersBalanceEntity usersBalanceEntity,
			AssociatedUsersDTO associatedUsersDTO) {

		if (usersBalanceEntity.getUser1() == loggedInUserId) {
			RelationsEntity rEntity = usersBalanceEntity.getUsersNameMapping().getRelationEntity();

			if (rEntity != null) {

				associatedUsersDTO.setRelation(rEntity.getRelation());
			} else {
				associatedUsersDTO.setRelation("Other");
			}

		} else {
			RelationsEntity orEntity = usersBalanceEntity.getUsersNameMapping().getOppositeRelationEntity();

			if (orEntity != null) {
				associatedUsersDTO.setRelation(orEntity.getRelation());
			} else {
				associatedUsersDTO.setRelation("Other");
			}

		}
	}

	private BigDecimal getMutualBalance(UsersBalanceEntity balanceEntity, int id) {
		BigDecimal balance = null;
		int user1 = balanceEntity.getUser1();
		int user2 = balanceEntity.getUser2();
		BigDecimal balanceDecimal = balanceEntity.getBalance().add(balanceEntity.getPendingTransactionAmount());
		int result = balanceDecimal.compareTo(BigDecimal.ZERO);

		if (id == user2 && result == 1) {
			balance = balanceDecimal.negate();
			return balance;
		} else if (id == user2 && result == -1) {
			balance = balanceDecimal.abs();
			return balance;
		}

		balance = balanceDecimal;
		return balance;
	}

	@Override
	public TransactionsDTO acceptTransactionService(TransactionActionParams transactionActionParams) {
		TransactionsEntity txnEntity = updateMutualbalance(transactionActionParams);
		TransactionsDTO dto = null;
		if (txnEntity != null) {
			dto = new TransactionsDTO();
			BeanUtils.copyProperties(txnEntity, dto);
			dto.setMappedName(transactionActionParams.getOppositeUserName());
			dto.setTransactionId(truncateTransactionId(dto.getTransactionId()));
		}
		String token = null;
		int receiverId = 0;
		int senderId=0;
		if (transactionActionParams.getUserId() == dto.getPayerId()) {
			receiverId = dto.getPayeeId();
			senderId=dto.getPayerId();
		} else {
			receiverId = dto.getPayerId();
			senderId=dto.getPayeeId();
		}
		
		token = userService.getToken(receiverId);
		getLogger().info("token == " + token);
		if (token != null && !token.isEmpty()) {
			NotificationTypeEntity notificationTypeEntity=null;
			NotificationStatusEntity nStatusEntity=notificationStatusDao.getOne(NotificationStatusEntity.class, 1);
			
			JsonObject data = new JsonObject();
			data.addProperty("type", "transactionHistory");
			data.addProperty("payId", dto.getPayId());
			data.addProperty("phoneNumber", transactionActionParams.getRecipientMobileNumber());
			String message = null;
			if (dto.getBillAmount().compareTo(BigDecimal.ZERO) == 1) {
				notificationTypeEntity=notificationTypeDao.getOne(NotificationTypeEntity.class, properties.getnTypeAcceptBusinessId());
				message = notificationTypeEntity.getNotificationTemplateEntity().getTemplateBody() + " " + dto.getBillAmount() + "";
			} else {
				notificationTypeEntity=notificationTypeDao.getOne(NotificationTypeEntity.class, properties.getnTypeAcceptBusinessId());
				message = notificationTypeEntity.getNotificationTemplateEntity().getTemplateBody()+ " " + dto.getCashReceived() + "";
			}
			try {
				FcmNotificationUtil.sendNotification(token, message, authKey, data);
			} catch (FCMException e) {
				
				getLogger().error("fcm notification exception", e.fillInStackTrace());
				return dto;
			}
			notificationDao.save(createNotificationEntity(senderId, receiverId, message, notificationTypeEntity.getNotificationTemplateEntity().getTemplateTitle(),
					notificationTypeEntity, nStatusEntity));
			
		}

		return dto;
	}

	private TransactionsEntity updateMutualbalance(TransactionActionParams txnActionParams) {
		BigDecimal skyCredit = null;
		TransactionsEntity txnEntity = null;
		txnEntity = transactionDao.getOne(TransactionsEntity.class, txnActionParams.getPayId());
		String paymentStatus = txnEntity.getPaymentStatus(); // null not checked as payId must be there in table
		if (!paymentStatus.equals("P")) {
			getLogger().info("Transaction is not in pending state !! ");
			return null;
		}
		int userId1 = txnEntity.getPayerId();
		int userId2 = txnEntity.getPayeeId();
		skyCredit = txnEntity.getSkyCredit();
		UsersBalanceEntity usersBalanceEntity = usersBalanceDAO.getByBothUsersId(userId1, userId2);
		if (usersBalanceEntity != null) {
			txnEntity = updateTransaction(usersBalanceEntity, txnEntity, txnActionParams);
		}
		return txnEntity;
	}

	private TransactionsEntity updateTransaction(UsersBalanceEntity usersBalanceEntity, TransactionsEntity txnEntity,
			TransactionActionParams txnActionParams) {
		BigDecimal mutualBalance = usersBalanceEntity.getBalance();
		Date date = DateUtils.getCurrentDateTime();
		String paymentStatus = "";
		txnEntity.setPreviousBalance(mutualBalance);
		if (txnActionParams.getUserAction() == 'C') {
			paymentStatus = "C";
		} else {
			paymentStatus = "A";
		}
		txnEntity.setPaymentStatus(paymentStatus);
		txnEntity.setConfirmLatitude(txnActionParams.getConfirmLatitude());
		txnEntity.setConfirmLongitude(txnActionParams.getConfirmLongitude());
		txnEntity.setModifiedBy(txnActionParams.getUserId());
		txnEntity.setModifiedOn(date);
		txnEntity.setGSTEnabled(txnActionParams.isGSTEnabled());
		usersBalanceEntity.setBalance(calculateMutualBalance(txnEntity, usersBalanceEntity));
		usersBalanceEntity
				.setPendingTransactionAmount(calculatePendingTransactionAmount(txnEntity, usersBalanceEntity));
		usersBalanceEntity.setModifiedBy(txnActionParams.getUserId());
		usersBalanceEntity.setModifiedOn(date);
		/*
		 * session.save(txnEntity); session.save(usersBalanceEntity);
		 */
		return txnEntity;
	}

	private BigDecimal calculateMutualBalance(TransactionsEntity txnEntity, UsersBalanceEntity usersBalanceEntity) {
		BigDecimal mutualBalance = usersBalanceEntity.getBalance();
		if (txnEntity.getPayerId() == usersBalanceEntity.getUser1()) {
			mutualBalance = txnEntity.getSkyCredit().add(mutualBalance);
		} else if (txnEntity.getPayerId() == usersBalanceEntity.getUser2()
				&& txnEntity.getSkyCredit().doubleValue() < 0) {
			mutualBalance = txnEntity.getSkyCredit().abs().add(mutualBalance);
		} else if (txnEntity.getPayerId() == usersBalanceEntity.getUser2()
				&& txnEntity.getSkyCredit().doubleValue() > 0) {
			mutualBalance = txnEntity.getSkyCredit().negate().add(mutualBalance);
		}
		return mutualBalance;
	}

	private BigDecimal calculatePendingTransactionAmount(TransactionsEntity transactionsEntity,
			UsersBalanceEntity usersBalanceEntity) {
		int payerId = transactionsEntity.getPayerId();
		BigDecimal pendingAmount = usersBalanceEntity.getPendingTransactionAmount();
		BigDecimal skyCredit = transactionsEntity.getSkyCredit();
		if (payerId == usersBalanceEntity.getUser1()) {
			pendingAmount = pendingAmount.subtract(skyCredit);
		} else if (payerId == usersBalanceEntity.getUser2()) {
			pendingAmount = pendingAmount.add(skyCredit);
		}
		/* getLogger().info("pendingAmount after calculation : " + pendingAmount); */
		return pendingAmount;
	}

	@Override
	public TransactionsDTO rejectTransaction(TransactionActionParams transactionActionParams) {
		TransactionsEntity entity = updateBalanceAfterRejection(transactionActionParams);
		TransactionsDTO dto = new TransactionsDTO();
		if (entity == null) {
			return null;
		}
		BeanUtils.copyProperties(entity, dto);
		dto.setTransactionId(truncateTransactionId(dto.getTransactionId()));
		dto.setMappedName(transactionActionParams.getOppositeUserName());
		

		String token = null;
		
		int receiverId = 0;
		int senderId=0;
		if (transactionActionParams.getUserId() == dto.getPayerId()) {
			receiverId = dto.getPayeeId();
			senderId= dto.getPayerId();
		} else {
			receiverId = dto.getPayerId();
			senderId=dto.getPayeeId();
		}
		token = userService.getToken(receiverId);
		if (token != null && !token.isEmpty()) {
		
		NotificationTypeEntity notificationTypeEntity=null;
		NotificationStatusEntity nStatusEntity=notificationStatusDao.getOne(NotificationStatusEntity.class, 1);
		
			JsonObject data = new JsonObject();
			data.addProperty("type", "transactionHistory");
			data.addProperty("phoneNumber", transactionActionParams.getRecipientMobileNumber());
			data.addProperty("payId", dto.getPayId());
			String message = null;
			if (dto.getBillAmount().compareTo(BigDecimal.ZERO) == 1) {
				notificationTypeEntity=notificationTypeDao.getOne(NotificationTypeEntity.class, properties.getnTypeRejectBusinessId());
				message = notificationTypeEntity.getNotificationTemplateEntity().getTemplateBody() + "" + dto.getBillAmount() + "";
			} else {
				notificationTypeEntity=notificationTypeDao.getOne(NotificationTypeEntity.class, properties.getnTypeRejectId());
				message = notificationTypeEntity.getNotificationTemplateEntity().getTemplateBody() + " " + dto.getCashReceived() + "";
			}
			try {
				FcmNotificationUtil.sendNotification(token, message, authKey, data);
				
			} catch (FCMException e) {
				
				getLogger().error("fcm notification exception", e.fillInStackTrace());
				return dto;
			}
			notificationDao.save(createNotificationEntity(senderId, receiverId, message, notificationTypeEntity.getNotificationTemplateEntity().getTemplateTitle(),
					notificationTypeEntity, nStatusEntity));
		}
		
		
		return dto;
	}

	// update mutual balance and current trans previous balance and pending amount
	// balance after rejection
	public TransactionsEntity updateBalanceAfterRejection(TransactionActionParams transactionActionParams) {
		TransactionsEntity entity = transactionDao.getOne(TransactionsEntity.class, transactionActionParams.getPayId());
		if (entity != null && entity.getPaymentStatus().equals("P")) {
			updateCurrentTransPrevBalanceAmount(entity);
			UsersBalanceEntity usersBalanceEntity = usersBalanceDAO.getByBothUsersId(entity.getPayerId(),
					entity.getPayeeId());
			usersBalanceEntity
					.setPendingTransactionAmount(calculatePendingTransactionAmount(entity, usersBalanceEntity));
			entity.setPaymentStatus("R");
			entity.setModifiedBy(transactionActionParams.getUserId());
			entity.setModifiedOn(DateUtils.getCurrentDateTime());
			entity.setConfirmLatitude(transactionActionParams.getConfirmLatitude());
			entity.setConfirmLongitude(transactionActionParams.getConfirmLongitude());
		} else {
			getLogger().info("transaction is not in pending state  !!");
			return entity = null;
		}
		return entity;
	}

	private void updateCurrentTransPrevBalanceAmount(TransactionsEntity rejectedTransaction) {
		List<TransactionsEntity> entityListToUpdate = transactionDao.getAllTransactionsToUpdate(rejectedTransaction);
		TransactionsEntity prevTransaction = null;
		if (entityListToUpdate != null && !entityListToUpdate.isEmpty()) {
			prevTransaction = transactionDao.getPreviousTransactionBetweenUsers(rejectedTransaction);
		} else {
			return;
		}
		BigDecimal tempCurTransPrevBal = null, /* tempSkyCredit =null, */tempCurNetBal = null;
		int prevTransPayerId = 0, tempPayerId = 0;
		if (prevTransaction != null) {
			prevTransPayerId = prevTransaction.getInitiatedFrom();
			tempPayerId = prevTransPayerId;
			tempCurTransPrevBal = prevTransaction.getCurrentPaymentStatus();
			tempCurNetBal = prevTransaction.getCurrentPaymentStatus();
			updateCurTransPrevBalOfAllEntities(entityListToUpdate, tempPayerId,
					tempCurTransPrevBal/* ,tempCurNetBal */);

		} else {
			TransactionsEntity firstTxnEntityToUpdate = entityListToUpdate.get(0);
			tempCurTransPrevBal = new BigDecimal("0.00");
			tempPayerId = firstTxnEntityToUpdate.getInitiatedFrom();
			updateCurTransPrevBalOfAllEntities(entityListToUpdate, tempPayerId,
					tempCurTransPrevBal/* ,tempCurNetBal */);
		}
	}

	private void updateCurTransPrevBalOfAllEntities(List<TransactionsEntity> entityListToUpdate, int tempPayerId,
			BigDecimal tempCurTransPrevBal/* ,BigDecimal tempCurNetBal */) {

		for (TransactionsEntity entity : entityListToUpdate) {
			if (entity.getInitiatedFrom() == tempPayerId) {
				entity.setCurrTranPrevBal(tempCurTransPrevBal);
			} else {
				entity.setCurrTranPrevBal(tempCurTransPrevBal.negate());
			}
			if (entity.getPayerId() == entity.getInitiatedFrom()) {
				entity.setCurrentPaymentStatus(entity.getCurrTranPrevBal().add(entity.getSkyCredit()));
			} else {
				entity.setCurrentPaymentStatus(entity.getCurrTranPrevBal().subtract(entity.getSkyCredit()));

			}
			tempPayerId = entity.getInitiatedFrom();
			tempCurTransPrevBal = entity.getCurrentPaymentStatus();
		}
	}

	@Override
	public Map<String, String> getTotalPayReceiveAmountWithPendingRequestsCount(int userId) {
		List<UsersBalanceEntity> entityList = transactionDao.getAllUserBalanceEntitiesWithUserId(userId);
		String pReqCount = transactionDao.getPendingRequestsCount(userId) + "";
		String totalEarnedPoints=userDao.getTotalEarnedPoints(userId)+"";
		Map<String, String> payReceiveMap = new HashMap<String, String>();
		if (entityList == null || entityList.isEmpty()) {

			payReceiveMap.put("totalPayable", "0.00");
			payReceiveMap.put("totalReceivable", "0.00");
			payReceiveMap.put("totalPendingRequests", pReqCount);
			payReceiveMap.put("appVersion", appPreferencesDAO.getAppPreferences(1).getAppVersion());
			payReceiveMap.put("totalEarnedPoints", totalEarnedPoints);
			return payReceiveMap;
		}
		BigDecimal totalPayable = new BigDecimal("0.00"), totalReceivable = new BigDecimal("0.00");

		for (UsersBalanceEntity entity : entityList) {
			BigDecimal result = entity.getBalance().add(entity.getPendingTransactionAmount());

			if (userId == entity.getUser1()) {
				if (result.doubleValue() > 0) {
					totalPayable = totalPayable.add(result);
				} else {
					totalReceivable = totalReceivable.add(result.abs());
				}
			} else if (userId == entity.getUser2()) {
				if (result.doubleValue() > 0) {
					totalReceivable = totalReceivable.add(result);
				} else {
					totalPayable = totalPayable.add(result.abs());
				}
			}
		}
		payReceiveMap.put("totalPayable", totalPayable.toString());
		payReceiveMap.put("totalReceivable", totalReceivable.toString());
		payReceiveMap.put("totalPendingRequests", pReqCount);
		payReceiveMap.put("appVersion", appPreferencesDAO.getAppPreferences(1).getAppVersion());
		payReceiveMap.put("totalEarnedPoints", totalEarnedPoints);
		return payReceiveMap;
	}

	@Override
	public List<AssociatedUsersDTO> getPayReceiveWithAssociatedUsersInfo(int userId, int pageSize) {
		List<Object[]> dataList = transactionDao.getMutualBalanceListWithUsersInfo(userId, pageSize);
		if (dataList == null) {

			return null;
		}
		List<AssociatedUsersDTO> dtoList = getAssociatedUsersInDto(dataList, userId, false);
		return dtoList;
	}

	@Override
	public Map<String, String> calculateCashInOut(int userId) {
		List<TransactionsEntity> list = transactionDao.fetchTransactionsForCashInOutCalc(userId);
		Map<String, String> map = new HashMap<String, String>();
		if (list == null || list.isEmpty()) {
			map.put("totalCashPaid", "0.00");
			map.put("totalCashReceived", "0.00");

		
		}else {
		BigDecimal totalCashPaid = new BigDecimal("0.00"), totalCashReceived = new BigDecimal("0.00");
		for (TransactionsEntity entity : list) {
			int payerId = entity.getPayerId();
			int payeeId = entity.getPayeeId();
			if (userId == payerId) {
				totalCashPaid = entity.getCashReceived().add(totalCashPaid);
			} else if (userId == payeeId) {
				totalCashReceived = entity.getCashReceived().add(totalCashReceived);

			}
		}
		map.put("totalCashPaid", totalCashPaid + "");
		map.put("totalCashReceived", totalCashReceived + "");
	}
		
	    SalesPurchaseDetailDto dto=getSalesPurchaseAmount(userId, null);
	    if(dto.getPurchaseAmount()==null) {
	    	dto.setPurchaseAmount(new BigDecimal("0.00"));
	    }if(dto.getSalesAmount()==null) {
	    	dto.setSalesAmount(new BigDecimal("0.00"));
	    }
		
		
		map.put("totalsalesAmount", dto.getSalesAmount()+"");
		map.put("totalPurchaseAmount", dto.getPurchaseAmount()+"");
		return map;
	}

	@Override
	public List<TransactionsDTO> getPendingTransactions(int userId, int pageSize) {
		List<Object[]> list = transactionDao.pendingTransactions(userId, pageSize);
		if (list != null) {
			List<TransactionsDTO> dtoList = copyPendingTxnListEntityInDto(list, userId);
			return dtoList;
		}
		return null;

	}

	private long getTransactionDuration(Date paymentDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String modifiedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date endDate = null;
		try {
			endDate = sdf.parse(modifiedDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long timeDiff = Math.abs(endDate.getTime() - paymentDate.getTime());
		long duration = TimeUnit.MILLISECONDS.toHours(timeDiff);
		return duration;
	}

	private List<TransactionsDTO> copyPendingTxnListEntityInDto(List<Object[]> list, int userId) {

		List<TransactionsDTO> dtoList = new ArrayList<TransactionsDTO>();
		for (Object[] objArray : list) {
			TransactionsDTO transactionsDTO = new TransactionsDTO();
			TransactionsEntity tEntity = (TransactionsEntity) objArray[0];			
			UserEntity uEntity = (UserEntity) objArray[1];
			String blobId = (String) objArray[2];
			UsersBalanceEntity ub=(UsersBalanceEntity) objArray[3];
			BeanUtils.copyProperties(tEntity, transactionsDTO);
			transactionsDTO.setTransactionId(truncateTransactionId(transactionsDTO.getTransactionId()));
			transactionsDTO.setRecipientMobileNumber(uEntity.getPhNumber());
			transactionsDTO.setRecipientName(uEntity.getFirstName());
			transactionsDTO.setUserType(uEntity.getUserType());
			BigDecimal skycredit = getskyCredit(tEntity, userId);
			transactionsDTO.setSkyCredit(skycredit);
			transactionsDTO.setCurrentPaymentStatus(changeBalanceAccordingToUser(
					transactionsDTO.getCurrentPaymentStatus(), transactionsDTO.getInitiatedFrom(), userId));
			transactionsDTO.setCurrTranPrevBal(changeBalanceAccordingToUser(transactionsDTO.getCurrTranPrevBal(),
					transactionsDTO.getInitiatedFrom(), userId));

			transactionsDTO.setTransactionDuration(getTransactionDuration(tEntity.getPaymentDate()));
			transactionsDTO.setTempTransTimeLimit(tempTransTimeLimit);
			if (blobId != null) {
				transactionsDTO.setRecipientBlobId(blobId);
			}
			if(ub!=null) {
				if(userId==ub.getUser1()) {
					transactionsDTO.setMappedName(ub.getUsersNameMapping().getUserName2());
				}else {
					transactionsDTO.setMappedName(ub.getUsersNameMapping().getUserName1());
				}
			}
			if(transactionsDTO.getMappedName()==null || transactionsDTO.getMappedName().isEmpty()) {
				transactionsDTO.setMappedName(transactionsDTO.getRecipientName());
			}
			if(uEntity.getUserType().equalsIgnoreCase("T")) {
			//	if(validateInvitation(ub.getUsersInvitationsDetailEntity())) {
					transactionsDTO.setInviteEnabled(true);
			//	}else {
			//		transactionsDTO.setInviteEnabled(false);
			//	}
			}else {
				transactionsDTO.setInviteEnabled(false);
			}
			
			dtoList.add(transactionsDTO);
		}
		
		
		return dtoList;
	}
	
	public String transactionId() {
		long current = System.currentTimeMillis();
		return (transactionIdPrefix + current + getRandomNumber());
	}

	private Integer getRandomNumber() {
		Random rand = new Random();
		return rand.nextInt(9000000) + 1000000;
	}

	private TransactionsDTO copyInTransactionDto(List list) {
		TransactionsDTO dto = new TransactionsDTO();
		TransactionsEntity entity = (TransactionsEntity) list.get(0);
		BeanUtils.copyProperties(entity, dto);
		UserEntity userEntity = (UserEntity) list.get(1);
		UsersBalanceEntity ubEntity = (UsersBalanceEntity) list.get(2);
		dto.setRecipientName(userEntity.getFirstName());
		dto.setRecipientMobileNumber(userEntity.getPhNumber());
		dto.setTransactionId(truncateTransactionId(dto.getTransactionId()));
		getLogger().info("Created BY ==" + dto.getCreatedBy() + "User1===" + ubEntity.getUser1());
		if (dto.getCreatedBy() == ubEntity.getUser1()) {
			dto.setMappedName(ubEntity.getUsersNameMapping().getUserName2());
		} else {
			dto.setMappedName(ubEntity.getUsersNameMapping().getUserName1());
		}
		if (dto.getMappedName() == null || dto.getMappedName().isEmpty()) {
			dto.setMappedName(dto.getRecipientName());
		}
		return dto;
	}

	public List<TransactionsDTO> getTransactionHistory(int userId, int pageSize, String type) {
		List<Object[]> list = transactionDao.fetchTransactionHistory(userId, pageSize, type);
		if (list == null) {
			return null;
		}

		List<TransactionsDTO> dtoList = getTransactionsInDTO(list, userId);
		return dtoList;
	}

	private List<TransactionsDTO> getTransactionsInDTO(List<Object[]> list, int userId) {
		List<TransactionsDTO> dtoList = new ArrayList<TransactionsDTO>();
		for (int i = 0; i < list.size(); i++) {
			TransactionsDTO dto = new TransactionsDTO();
			UserEntity userEntity = null;
			Object[] obj = (Object[]) list.get(i);
			for (int j = 0; j < obj.length; j++) {
				
				if (obj[j] instanceof UserEntity) {
					userEntity = (UserEntity) obj[j];
					dto.setRecipientName(userEntity.getFirstName());
					dto.setRecipientMobileNumber(userEntity.getPhNumber());
					dto.setUserType(userEntity.getUserType());
				}else if(obj[j] instanceof UsersBalanceEntity) {
					UsersBalanceEntity entity=(UsersBalanceEntity) obj[j];
					if(userId==entity.getUser1()) {
						dto.setMappedName(entity.getUsersNameMapping().getUserName2());
					}else {
						dto.setMappedName(entity.getUsersNameMapping().getUserName1());
					}
					
					if(userEntity.getUserType().equalsIgnoreCase("T")) {
						System.out.println("User ==="+userEntity.getFirstName());
						//if(validateInvitation(entity.getUsersInvitationsDetailEntity())) {
							dto.setInviteEnabled(true);
						//}else {
							//dto.setInviteEnabled(false);
						//}
					}else {
					dto.setInviteEnabled(false);
					}
						
				}else {
					TransactionsEntity transactionsEntity = (TransactionsEntity) obj[j];					
					BeanUtils.copyProperties(transactionsEntity, dto);
					dto.setTransactionId(truncateTransactionId(dto.getTransactionId()));
					BigDecimal skycredit = getskyCredit(transactionsEntity, userId);
					dto.setCurrentPaymentStatus(changeBalanceAccordingToUser(dto.getCurrentPaymentStatus(),
							dto.getInitiatedFrom(), userId));
					dto.setCurrTranPrevBal(
							changeBalanceAccordingToUser(dto.getCurrTranPrevBal(), dto.getInitiatedFrom(), userId));
					dto.setSkyCredit(skycredit);
					dto.setTransactionDuration(getTransactionDuration(transactionsEntity.getPaymentDate()));
				}
				
			}if(dto.getMappedName()==null || dto.getMappedName().isEmpty()) {
				dto.setMappedName(dto.getRecipientName());
			}
			dto.setUserType(userEntity.getUserType());
			
			dtoList.add(dto);
		}

		return dtoList;
	}

	
	
	
private boolean validateInvitation(UsersInvitationsDetailEntity usersInvitationsDetailEntity) {
	
		if(usersInvitationsDetailEntity.getInvitationSentDate()!=null && getTransactionDuration(usersInvitationsDetailEntity.getInvitationSentDate())<invitationTimeLimit) {
			return false;
		}else if(usersInvitationsDetailEntity.getSmsInviteCount()>=invitationSMSLimit) {
			return false;
		}else {
			return true; 
		}
	}
	
	private BigDecimal changeBalanceAccordingToUser(BigDecimal balance, int initiatedFrom, int loggedInUserId) {
		int result = balance.compareTo(BigDecimal.ZERO);
		if (initiatedFrom != loggedInUserId) {
			if (result == 1) {
				return balance.negate();
			} else {
				return balance.abs();
			}
		}
		return balance;

	}

	private BigDecimal getskyCredit(TransactionsEntity transactionsEntity, int userid) {
		BigDecimal skyCredit = null;
		int payer = transactionsEntity.getPayerId();
		int payee = transactionsEntity.getPayeeId();
		BigDecimal skycreditDecimal = transactionsEntity.getSkyCredit();
		int result = skycreditDecimal.compareTo(BigDecimal.ZERO);
		if (userid == payee && result == 1) {
			skyCredit = skycreditDecimal.negate();
			return skyCredit;
		} else if (userid == payee && result == -1) {
			skyCredit = skycreditDecimal.abs();
			return skyCredit;
		}

		skyCredit = skycreditDecimal;
		return skyCredit;
	}

	@Override
	public List<AssociatedUsersDTO> recentUsersService(int userId, int pageSize) {
		List<Object[]> list = transactionDao.fetchRecentUsers(userId, pageSize);
		if (list == null || list.isEmpty()) {

			return null;
		}
		List<AssociatedUsersDTO> recentUsersList = new ArrayList<AssociatedUsersDTO>();
		for (Object[] obj : list) {
			AssociatedUsersDTO dto = new AssociatedUsersDTO();
			UserEntity uEntity = (UserEntity) obj[0];
			dto.setName(uEntity.getFirstName());
			dto.setPhNumber(uEntity.getPhNumber());
			dto.setActive(uEntity.isActive());
			dto.setUserType(uEntity.getUserType());
			UsersBalanceEntity ub = (UsersBalanceEntity) obj[1];
			setMappedAndMappedName(userId, ub, dto);
			recentUsersList.add(dto);
		}
		return recentUsersList;
	}

	public List<ItemMasterDTO> fetchItem(int userId) {

		List<ItemMasterEntity> itemMasterEntity = transactionDao.fetchItem(userId);
		List<ItemMasterDTO> itemMasterDTO = CopyInDTO(itemMasterEntity);
		return itemMasterDTO;

	}

	public List<ItemMasterDTO> CopyInDTO(List<ItemMasterEntity> itemMasterEntity) {
		List<ItemMasterDTO> itemMasterDTOList = new ArrayList<ItemMasterDTO>();
		for (ItemMasterEntity itemMasterEntity2 : itemMasterEntity) {
			ItemMasterDTO dto = new ItemMasterDTO();
			BeanUtils.copyProperties(itemMasterEntity2, dto);
			itemMasterDTOList.add(dto);
		}
		return itemMasterDTOList;
	}

	public List<TransactionsDTO> dateWiseTransactionServiceForInvoice(int userId, String date) {
		List<TransactionsEntity> list = transactionDao.getDateWiseTransaction(userId, date);
		if (list == null || list.isEmpty()) {
			return null;
		}
		List<TransactionsDTO> dtoList = new ArrayList<TransactionsDTO>();
		for (TransactionsEntity entity : list) {
			TransactionsDTO dto = new TransactionsDTO();
			BeanUtils.copyProperties(entity, dto);
			dto.setTransactionId(truncateTransactionId(dto.getTransactionId()));
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public List<TotalBillAmount> monthWiseTransactionServiceForInvoice(int userId, String timePeriod) {
		String[] arr = timePeriod.split("-");
		List<TransactionsEntity> entityList = transactionDao.getMonthWiseTransaction(userId, Integer.parseInt(arr[1]),
				Integer.parseInt(arr[0]));
		if (entityList == null || entityList.isEmpty()) {
			return null;
		}
		BigDecimal firstWeekTotalBillAmount = new BigDecimal("0.00"),
				secondWeekTotalBillAmount = new BigDecimal("0.00"), thirdWeekTotalBillAmount = new BigDecimal("0.00"),
				fourthWeekTotalBillAmount = new BigDecimal("0.00"), fifthWeekTotalBillAmount = new BigDecimal("0.00");
		for (TransactionsEntity entity : entityList) {
			BigDecimal billAmount = entity.getBillAmount();
			int date = entity.getPaymentDate().getDate();
			if (date <= 7) {
				firstWeekTotalBillAmount = firstWeekTotalBillAmount.add(billAmount);
			} else if (date >= 8 && date <= 14) {
				secondWeekTotalBillAmount = secondWeekTotalBillAmount.add(billAmount);
			} else if (date >= 15 && date <= 21) {
				thirdWeekTotalBillAmount = thirdWeekTotalBillAmount.add(billAmount);
			} else if (date >= 22 && date <= 28) {
				fourthWeekTotalBillAmount = fourthWeekTotalBillAmount.add(billAmount);
			} else if (date >= 29) {
				fifthWeekTotalBillAmount = fifthWeekTotalBillAmount.add(billAmount);
			}
		}
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.add(fifthWeekTotalBillAmount);
		list.add(fourthWeekTotalBillAmount);
		list.add(thirdWeekTotalBillAmount);
		list.add(secondWeekTotalBillAmount);
		list.add(firstWeekTotalBillAmount);
		return setWeeklyData(list);
	}

	private List<TotalBillAmount> setWeeklyData(List<BigDecimal> dataList) {
		List<TotalBillAmount> list = new ArrayList<TotalBillAmount>();
		amount = new TotalBillAmount();
		amount.setBillAmount(dataList.get(4) + "");
		amount.setNarration("first week");
		list.add(amount);
		amount = new TotalBillAmount();
		amount.setBillAmount(dataList.get(3) + "");
		amount.setNarration("second week");
		list.add(amount);
		amount = new TotalBillAmount();
		amount.setBillAmount(dataList.get(2) + "");
		amount.setNarration("third week");
		list.add(amount);
		amount = new TotalBillAmount();
		amount.setBillAmount(dataList.get(1) + "");
		amount.setNarration("fourth week");
		list.add(amount);
		amount = new TotalBillAmount();
		amount.setBillAmount(dataList.get(0) + "");
		amount.setNarration("fifth week");
		list.add(amount);
		return list;
	}

	@Override
	public List<TotalBillAmount> quarterWiseTransactionSrevice(int userId, String timePeriod) {
		int index = timePeriod.indexOf(",");
		int fMonth = 0, year = 0, tMonth = 0;
		;
		try {
			fMonth = Integer.parseInt(timePeriod.substring(0, 2));
			getLogger().info("fMonth : " + fMonth);
			tMonth = Integer.parseInt(timePeriod.substring(index + 1, index + 3));
			getLogger().info("tMOnth : " + tMonth);
			year = Integer.parseInt(timePeriod.substring(timePeriod.lastIndexOf(",") + 1));
			getLogger().info("year : " + year);
			getLogger().info(DateUtils.getMonth(fMonth));
		} catch (NumberFormatException e) {
			getLogger().error("exception : ", e.fillInStackTrace());
		}
		List<TransactionsEntity> entityList = transactionDao.getQuarterlyTransaction(userId, fMonth, tMonth, year);

		if (entityList == null || entityList.isEmpty()) {
			return null;
		}
		BigDecimal firstMonthBillAmount = new BigDecimal("0.00"), secondMonthBillAmount = new BigDecimal("0.00"),
				thirdMonthBillAmount = new BigDecimal("0.00");
		for (TransactionsEntity entity : entityList) {
			BigDecimal billAmount = entity.getBillAmount();
			int month = entity.getPaymentDate().getMonth();
			if (fMonth == month + 1) {
				firstMonthBillAmount = firstMonthBillAmount.add(billAmount);
			} else if (tMonth == month + 1) {
				thirdMonthBillAmount = thirdMonthBillAmount.add(billAmount);
			} else {
				secondMonthBillAmount = secondMonthBillAmount.add(billAmount);
			}

		}
		List<BigDecimal> list = new ArrayList<BigDecimal>();
		list.add(firstMonthBillAmount);
		list.add(secondMonthBillAmount);
		list.add(thirdMonthBillAmount);
		int[] months = { fMonth, fMonth + 1, tMonth };
		return setQuarterlyData(list, months);
	}

	private void setCurrentPaymentStatus(TransactionsDTO dto) {
		int payerId = dto.getPayerId();
		int initiatedFrom = dto.getInitiatedFrom();
		if (payerId == initiatedFrom) {
			dto.setCurrentPaymentStatus(dto.getCurrTranPrevBal().add(dto.getSkyCredit()));
		} else {
			dto.setCurrentPaymentStatus(dto.getCurrTranPrevBal().subtract(dto.getSkyCredit()));
		}
	}

	private List<TotalBillAmount> setQuarterlyData(List<BigDecimal> dataList, int[] months) {
		List<TotalBillAmount> list = new ArrayList<TotalBillAmount>();
		amount = new TotalBillAmount();
		amount.setBillAmount(dataList.get(0) + "" + "");
		amount.setNarration(DateUtils.getMonth(months[0]));
		list.add(amount);
		amount = new TotalBillAmount();
		amount.setBillAmount(dataList.get(1) + "" + "");
		amount.setNarration(DateUtils.getMonth(months[1]));
		list.add(amount);
		amount = new TotalBillAmount();
		amount.setBillAmount(dataList.get(2) + "" + "");
		amount.setNarration(DateUtils.getMonth(months[2]));
		list.add(amount);
		return list;
	}

	@Override
	public List<com.saifintex.web.dto.TransactionsDTO> getAllTransactions(String txnType, int pageSize, int length,
			String search, com.saifintex.web.dto.TransactionsDTO transactionsWebDto) {
		List<Object[]> list = transactionDao.getAllTransactions(txnType, pageSize, length, search, transactionsWebDto);
		long count = transactionDao.getTransactionsCount(txnType, search, transactionsWebDto);
		List<com.saifintex.web.dto.TransactionsDTO> dtoList=copyInTransactionDtoList(list,count);

		return dtoList;
	}
	
	private List<com.saifintex.web.dto.TransactionsDTO> copyInTransactionDtoList(List<Object[]> list,long count){
		List<com.saifintex.web.dto.TransactionsDTO> dtoList = new ArrayList<com.saifintex.web.dto.TransactionsDTO>();
		for (int i = 0; i < list.size(); i++) {
			com.saifintex.web.dto.TransactionsDTO dto = new com.saifintex.web.dto.TransactionsDTO();
			Object[] obj = (Object[]) list.get(i);

			TransactionsEntity transactionsEntity = (TransactionsEntity) obj[0];
			BeanUtils.copyProperties(transactionsEntity, dto);
			// dto.setTotalRecords((Long)obj[1]);
			dto.setPayerName((String) obj[1]);
			dto.setPayeeName((String) obj[2]);
			dto.setTotalRecords(count);
			dto.setGstEnabled(transactionsEntity.isGSTEnabled());
			dtoList.add(dto);
		}
		
		return dtoList;
	}

	@Override
	public List<Transactions> getTransactionsListDashboard(boolean includeTestUsers, String paymentStatus,
			String period) {
		DashboardDataDateParams dateParams = getDashboardDateParams();		
		List<Transactions> list = null;
		if(paymentStatus.equals("all")) {
			paymentStatus = null;
		}
		if (period.equals("today")) {
			list = transactionDao.getTransactionsList(null, null, paymentStatus, includeTestUsers);
		} else if (period.equals("yest")) {
			list = transactionDao.getTransactionsList(dateParams.getYesterdayDate(), null, paymentStatus,
					includeTestUsers);
		} else if (period.equals("week")) {
			list = transactionDao.getTransactionsList(dateParams.getStartingDateOfWeek(), dateParams.getEndDateOfWeek(),
					paymentStatus, includeTestUsers);
		} else if (period.equals("start")) {
			list = transactionDao.getTransactionsList(dateParams.getStartingDate(), dateParams.getUptoDate(),
					paymentStatus, includeTestUsers);
		} else {
			list = transactionDao.getTransactionsList(dateParams.getStartingDate(),
					DateUtils.getCurrentDateInDateObject(), paymentStatus, includeTestUsers); // payment status is null for getting all type of txns
		}
		return list;
	}

	private long insertMutualBalanceForFirstTime(TransactionsEntity transactionsEntity,
			UsersBalanceEntity balanceEntity) {
		balanceEntity = new UsersBalanceEntity();
		Date date = DateUtils.getCurrentDateTime();
		balanceEntity.setBalance(BigDecimal.ZERO);
		balanceEntity.setCreatedOn(date);
		balanceEntity.setTotalCash(BigDecimal.ZERO);
		balanceEntity.setCreatedBy(transactionsEntity.getInitiatedFrom());
		balanceEntity.setUser1(transactionsEntity.getPayerId());
		balanceEntity.setUser2(transactionsEntity.getPayeeId());
		balanceEntity.setPendingTransactionAmount(transactionsEntity.getSkyCredit());
		balanceEntity.setModifiedBy(transactionsEntity.getInitiatedFrom());
		balanceEntity.setCreatedOn(date);
		balanceEntity.setModifiedOn(date);
		balanceEntity.setTransacted(true);
		UsersNameMapping nameMapping = new UsersNameMapping();
		nameMapping.setUserName1("");
		nameMapping.setUserName2("");
		nameMapping.setCreatedBy(transactionsEntity.getCreatedBy());
		nameMapping.setCreatedOn(date);
		balanceEntity.setUsersNameMapping(nameMapping);
		Long result = (Long) usersBalanceDAO.save(balanceEntity);
		return result;
	}

	private void updatePendingTransactionAmount(TransactionsEntity transactionEntity,
			UsersBalanceEntity usersBalanceEntity) {
		int payerId = transactionEntity.getPayerId();
		BigDecimal skyCredit = transactionEntity.getSkyCredit();
		BigDecimal pendingAmount = usersBalanceEntity.getPendingTransactionAmount();
		// getLogger().info("before update pending amount : " +
		// usersBalanceEntity.getPendingTransactionAmount());
		// calculate and update pending transaction amount according to the
		// conditions
		if (payerId == usersBalanceEntity.getUser1()) {
			pendingAmount = pendingAmount.add(skyCredit);
		} else if (payerId == usersBalanceEntity.getUser2()) {
			pendingAmount = pendingAmount.subtract(skyCredit);
		}
		usersBalanceEntity.setPendingTransactionAmount(pendingAmount);
		usersBalanceEntity.setModifiedOn(DateUtils.getCurrentDateTime());
		usersBalanceEntity.setModifiedBy(transactionEntity.getInitiatedFrom());
		usersBalanceEntity.setTransacted(true);
		// getLogger().info("after update pending amount : " + pendingAmount);
	}

	private void saveUsersItems(TransactionsEntity tEntity) {
		if (tEntity.getNarration() != null && !tEntity.getNarration().isEmpty()) {
			ItemMasterEntity itemMasterEntity = new ItemMasterEntity();
			itemMasterEntity.setUserId(tEntity.getInitiatedFrom());
			itemMasterEntity.setItemName(tEntity.getNarration());
			itemMasterDao.save(itemMasterEntity);
		}
	}

	@Override
	public TransactionsRecordDto getTransactionsRecord() {
		TransactionsRecordDto dto = transactionDao.getTransactionsRecord();
		return dto;
	}

	@Override
	public TransactionStatsDashboardDTO transactionStatsService() {
		return transactionDao.getTransactionStats();
	}

	private String truncateTransactionId(String transactionId) {
		String idAfterPrefix = transactionId.substring(transactionIdTrunValue);
		return idAfterPrefix.substring(0, idAfterPrefix.length() - 7);
	}

	@Override
	public TransactionSummaryParamsDto transactionSummary(int loggedInUserId, int opponentUserId) {
		TransactionSummaryParamsDto transactionSummaryParamsDto = transactionDao.transactionSummary(loggedInUserId,
				opponentUserId);
		transactionSummaryParamsDto.setBillBalance(transactionSummaryParamsDto.getTotalPurBillAmount()
				.subtract(transactionSummaryParamsDto.getTotalSalBillAmount()));

		transactionSummaryParamsDto.setBalance(
				transactionSummaryParamsDto.getTotalIReceived().subtract(transactionSummaryParamsDto.getTotalIPaid()));

		return transactionSummaryParamsDto;
	}

	private void setMappedAndMappedName(int loggedInUserId, UsersBalanceEntity entity, AssociatedUsersDTO usersDto) {
		if (loggedInUserId == entity.getUser1()) {
			usersDto.setMappedName(entity.getUsersNameMapping().getUserName2());
		} else {
			usersDto.setMappedName(entity.getUsersNameMapping().getUserName1());
		}
		if (usersDto.getMappedName() == null || usersDto.getMappedName().isEmpty()) {
			usersDto.setMappedName(usersDto.getName());
		}
	}

	@Override
	public TransactionsDTO getTransactionByPayIdWithUserInfo(int userId, long payId) {
		Object[] obj = transactionDao.getTransactionByPayIdWithUserInfo(userId, payId);
		if (obj != null && obj.length != 0) {
			return getTransactionInDTO(obj, userId);
		}
		return null;
	}

	private TransactionsDTO getTransactionInDTO(Object[] obj, int userId) {
		TransactionsDTO dto = new TransactionsDTO();
		TransactionsEntity txnEntity = (TransactionsEntity) obj[0];
		BeanUtils.copyProperties(txnEntity, dto);
		dto.setTransactionId(truncateTransactionId(dto.getTransactionId()));
		BigDecimal skycredit = getskyCredit(txnEntity, userId);
		dto.setCurrentPaymentStatus(
				changeBalanceAccordingToUser(dto.getCurrentPaymentStatus(), dto.getInitiatedFrom(), userId));
		dto.setCurrTranPrevBal(changeBalanceAccordingToUser(dto.getCurrTranPrevBal(), dto.getInitiatedFrom(), userId));
		dto.setSkyCredit(skycredit);
		UsersBalanceEntity ubEntity = (UsersBalanceEntity) obj[1];
		if (userId == ubEntity.getUser1()) {
			dto.setMappedName(ubEntity.getUsersNameMapping().getUserName2());
		} else {
			dto.setMappedName(ubEntity.getUsersNameMapping().getUserName1());
		}
		UserEntity uEntity = (UserEntity) obj[2];
		dto.setRecipientName(uEntity.getFirstName());
		dto.setRecipientMobileNumber(uEntity.getPhNumber());
		dto.setUserType(uEntity.getUserType());
		if (dto.getMappedName() == null || dto.getMappedName().isEmpty()) {
			dto.setMappedName(dto.getRecipientName());
		}
		return dto;

	}

	@Override
	public List<TransactionsDTO> getTodayTransactions(int userId, int pageSize, String type) {
		List<Object[]> list = transactionDao.findAllTodayTransactions(userId, pageSize, type);
		getLogger().info("list size ==" + list.size());
		return getTransactionsInDTO(list, userId);
	}

	@Override
	public List<TransactionsDTO> filterTransactions(TransactionsFilterParams params, int pageSize) {
		List<Object[]> list = transactionDao.filterTransactions(params, pageSize);
		if (list == null) {
			return null;
		}
		System.out.println("Fiter list size==" + list.size());
		List<TransactionsDTO> dtoList = getTransactionsInDTO(list, params.getLoggedInUserId());
		return dtoList;
	}

	@Override
	public List<AssociatedUsersDTO> getAllAssociatedUsers(int userId) {
		List list = transactionDao.getAllAssociatedUsers(userId);
		if (list == null) {
			return null;
		}
		List<AssociatedUsersDTO> dtoList = getAssociatedUsersInDto(list, userId, true);
		return dtoList;

	}

	@Override
	public TransactionStatsReportDTO getTransactionsReportOfAllUsers(boolean includeTestUsers) {
		TransactionStatsReportDTO dto = new TransactionStatsReportDTO();
		DashboardDataDateParams dateParams = getDashboardDateParams();
		dto.setAcceptedTxnsToday(getAcceptedTransactionCountToday(includeTestUsers));
		dto.setAcceptedTxnsYesterday(getAcceptedTransactionCountYesterday(dateParams, includeTestUsers));
		dto.setAcceptedTxnsInWeekRange(getAcceptedTransactionCountInWeek(dateParams, includeTestUsers));
		dto.setAcceptedTxnsInDateRange(getAcceptedTransactionCountInDateRange(dateParams, includeTestUsers));
		dto.setRejectedTxnsToday(getRejectedTransactionCountToday(includeTestUsers));
		dto.setRejectedTxnsYesterday(getRejectedTransactionCountYesterday(dateParams, includeTestUsers));
		dto.setRejectedTxnsInWeekRange(getRejectedTransactionCountInWeek(dateParams, includeTestUsers));
		dto.setRejectedTxnsInDateRange(getRejectedTransactionCountInDateRange(dateParams, includeTestUsers));
		dto.setClosedTxnsToday(getClosedTransactionCountToday(dateParams, includeTestUsers));
		dto.setClosedTxnsYesterday(getClosedTransactionCountYesterday(dateParams, includeTestUsers));
		dto.setClosedTxnsInWeekRange(getClosedTransactionCountInWeek(dateParams, includeTestUsers));
		dto.setClosedTxnsInDateRange(getClosedTransactionCountInDateRange(dateParams, includeTestUsers));
		dto.setPendingTxnsToday(getPendingTransactionCountToday(dateParams, includeTestUsers));
		dto.setPendingTxnsYesterday(getPendingTransactionCountYesterday(dateParams, includeTestUsers));
		dto.setPendingTxnsInWeekRange(getPendingTransactionCountInWeek(dateParams, includeTestUsers));
		dto.setPendingTxnsInDateRange(getPendingTransactionCountInDateRange(dateParams, includeTestUsers));
		dto.setAcceptedTxnsValueToday(getAcceptedTransactionValueToday(includeTestUsers));
		dto.setAcceptedTxnsValueYesterday(getAcceptedTransactionValueYesterday(dateParams, includeTestUsers));
		dto.setAcceptedTxnsValueInWeekRange(getAcceptedTransactionValueInWeek(dateParams, includeTestUsers));
		dto.setAcceptedTxnsValueInDateRange(getAcceptedTransactionValueInDateRange(dateParams, includeTestUsers));
		dto.setRejectedTxnsValueToday(getRejectedTransactionValueToday(includeTestUsers));
		dto.setRejectedTxnsValueYesterday(getRejectedTransactionValueYesterday(dateParams, includeTestUsers));
		dto.setRejectedTxnsValueInWeekRange(getRejectedTransactionValueInWeek(dateParams, includeTestUsers));
		dto.setRejectedTxnsValueInDateRange(getRejectedTransactionValueInDateRange(dateParams, includeTestUsers));
		dto.setClosedTxnsValueToday(getClosedTransactionValueToday(includeTestUsers));
		dto.setClosedTxnsValueYesterday(getClosedTransactionValueYesterday(dateParams, includeTestUsers));
		dto.setClosedTxnsValueInWeekRange(getClosedTransactionValueInWeek(dateParams, includeTestUsers));
		dto.setClosedTxnsValueInDateRange(getClosedTransactionValueInDateRange(dateParams, includeTestUsers));
		dto.setPendingTxnsValueToday(getPendingTransactionValueToday(includeTestUsers));
		dto.setPendingTxnsValueYesterday(getPendingTransactionValueYesterday(dateParams, includeTestUsers));
		dto.setPendingTxnsValueInWeekRange(getPendingTransactionValueInWeek(dateParams, includeTestUsers));
		dto.setPendingTxnsValueInDateRange(getPendingTransactionValueInDateRange(dateParams, includeTestUsers));
		dto.setTotalAcceptedTxnsValue(dto.getAcceptedTxnsValueToday().add(dto.getAcceptedTxnsValueYesterday())
				.add(dto.getAcceptedTxnsValueInWeekRange()).add(dto.getAcceptedTxnsValueInDateRange()));
		dto.setTotalRejectedTxnsValue(dto.getRejectedTxnsValueToday().add(dto.getRejectedTxnsValueYesterday())
				.add(dto.getRejectedTxnsValueInDateRange()).add(dto.getRejectedTxnsValueInWeekRange()));
		dto.setTotalPendingTxnsValue(dto.getPendingTxnsValueToday().add(dto.getPendingTxnsValueYesterday())
				.add(dto.getPendingTxnsValueInWeekRange()).add(dto.getPendingTxnsValueInDateRange()));
		dto.setTotalClosedTxnsValue(dto.getClosedTxnsValueToday().add(dto.getClosedTxnsValueYesterday())
				.add(dto.getClosedTxnsValueInWeekRange()).add(dto.getClosedTxnsValueInDateRange()));
		dto.setTotalTxnsValue(dto.getTotalAcceptedTxnsValue().add(dto.getTotalRejectedTxnsValue())
				.add(dto.getTotalPendingTxnsValue()).add(dto.getTotalClosedTxnsValue()));
		dto.setTotalTxnsValueToday(dto.getAcceptedTxnsValueToday().add(dto.getRejectedTxnsValueToday())
				.add(dto.getPendingTxnsValueToday()).add(dto.getClosedTxnsValueToday()));
		dto.setTotalTxnsValueYesterday(dto.getAcceptedTxnsValueYesterday().add(dto.getRejectedTxnsValueYesterday())
				.add(dto.getPendingTxnsValueYesterday()).add(dto.getClosedTxnsValueYesterday()));
		dto.setTotalTxnsValueInWeekRange(
				dto.getAcceptedTxnsValueInWeekRange().add(dto.getRejectedTxnsValueInWeekRange())
						.add(dto.getPendingTxnsValueInWeekRange()).add(dto.getClosedTxnsValueInWeekRange()));
		dto.setTotalTxnsValueInDateRange(
				dto.getAcceptedTxnsValueInDateRange().add(dto.getRejectedTxnsValueInDateRange())
						.add(dto.getPendingTxnsValueInDateRange()).add(dto.getClosedTxnsValueInDateRange()));
		return dto;
	}

	private long getAcceptedTransactionCountToday(boolean includeTestUsers) {
		return transactionDao.getTransactionsCount(DateUtils.getCurrentDateInDateObject(), null, null, "A",
				includeTestUsers);
	}

	private long getAcceptedTransactionCountYesterday(DashboardDataDateParams dateParams, boolean includeTestUsers) {
		return transactionDao.getTransactionsCount(dateParams.getYesterdayDate(), null, null, "A", includeTestUsers);
	}

	private long getAcceptedTransactionCountInWeek(DashboardDataDateParams dateParams, boolean includeTestUsers) {
		return transactionDao.getTransactionsCount(null, dateParams.getStartingDateOfWeek(),
				dateParams.getEndDateOfWeek(), "A", includeTestUsers);
	}

	private long getAcceptedTransactionCountInDateRange(DashboardDataDateParams dateParams, boolean includeTestUsers) {
		return transactionDao.getTransactionsCount(null, dateParams.getStartingDate(), dateParams.getUptoDate(), "A",
				includeTestUsers);
	}

	private long getRejectedTransactionCountToday(boolean includeTestUsers) {
		return transactionDao.getTransactionsCount(DateUtils.getCurrentDateInDateObject(), null, null, "R",
				includeTestUsers);
	}

	private long getRejectedTransactionCountYesterday(DashboardDataDateParams dateParams, boolean includeTestUsers) {
		return transactionDao.getTransactionsCount(dateParams.getYesterdayDate(), null, null, "R", includeTestUsers);
	}

	private long getRejectedTransactionCountInWeek(DashboardDataDateParams dateParams, boolean includeTestUsers) {
		return transactionDao.getTransactionsCount(null, dateParams.getStartingDateOfWeek(),
				dateParams.getEndDateOfWeek(), "R", includeTestUsers);
	}

	private long getRejectedTransactionCountInDateRange(DashboardDataDateParams dateParams, boolean includeTestUsers) {
		return transactionDao.getTransactionsCount(null, dateParams.getStartingDate(), dateParams.getUptoDate(), "R",
				includeTestUsers);
	}

	private long getPendingTransactionCountToday(DashboardDataDateParams dateParams, boolean includeTestUsers) {
		return transactionDao.getTransactionsCount(DateUtils.getCurrentDateInDateObject(), null, null, "P",
				includeTestUsers);
	}

	private long getPendingTransactionCountYesterday(DashboardDataDateParams dateParams, boolean includeTestUsers) {
		return transactionDao.getTransactionsCount(dateParams.getYesterdayDate(), null, null, "P", includeTestUsers);
	}

	private long getPendingTransactionCountInWeek(DashboardDataDateParams dateParams, boolean includeTestUsers) {
		return transactionDao.getTransactionsCount(null, dateParams.getStartingDateOfWeek(),
				dateParams.getEndDateOfWeek(), "P", includeTestUsers);
	}

	private long getPendingTransactionCountInDateRange(DashboardDataDateParams dateParams, boolean includeTestUsers) {
		return transactionDao.getTransactionsCount(null, dateParams.getStartingDate(), dateParams.getUptoDate(), "P",
				includeTestUsers);
	}

	private long getClosedTransactionCountToday(DashboardDataDateParams dateParams, boolean includeTestUsers) {
		return transactionDao.getTransactionsCount(DateUtils.getCurrentDateInDateObject(), null, null, "C",
				includeTestUsers);
	}

	private long getClosedTransactionCountYesterday(DashboardDataDateParams dateParams, boolean includeTestUsers) {
		return transactionDao.getTransactionsCount(dateParams.getYesterdayDate(), null, null, "C", includeTestUsers);
	}

	private long getClosedTransactionCountInWeek(DashboardDataDateParams dateParams, boolean includeTestUsers) {
		return transactionDao.getTransactionsCount(null, dateParams.getStartingDateOfWeek(),
				dateParams.getEndDateOfWeek(), "C", includeTestUsers);
	}

	private long getClosedTransactionCountInDateRange(DashboardDataDateParams dateParams, boolean includeTestUsers) {
		return transactionDao.getTransactionsCount(null, dateParams.getStartingDate(), dateParams.getUptoDate(), "C",
				includeTestUsers);
	}

	private BigDecimal getAcceptedTransactionValueToday(boolean includeTestUsers) {
		return transactionDao.getTransactionsValue(DateUtils.getCurrentDateInDateObject(), null, null, "A",
				includeTestUsers);
	}

	private BigDecimal getAcceptedTransactionValueYesterday(DashboardDataDateParams dateParams,
			boolean includeTestUsers) {
		return transactionDao.getTransactionsValue(dateParams.getYesterdayDate(), null, null, "A", includeTestUsers);
	}

	private BigDecimal getAcceptedTransactionValueInWeek(DashboardDataDateParams dateParams, boolean includeTestUsers) {
		return transactionDao.getTransactionsValue(null, dateParams.getStartingDateOfWeek(),
				dateParams.getEndDateOfWeek(), "A", includeTestUsers);
	}

	private BigDecimal getAcceptedTransactionValueInDateRange(DashboardDataDateParams dateParams,
			boolean includeTestUsers) {
		return transactionDao.getTransactionsValue(null, dateParams.getStartingDate(), dateParams.getUptoDate(), "A",
				includeTestUsers);
	}

	private BigDecimal getRejectedTransactionValueToday(boolean includeTestUsers) {
		return transactionDao.getTransactionsValue(DateUtils.getCurrentDateInDateObject(), null, null, "R",
				includeTestUsers);
	}

	private BigDecimal getRejectedTransactionValueYesterday(DashboardDataDateParams dateParams,
			boolean includeTestUsers) {
		return transactionDao.getTransactionsValue(dateParams.getYesterdayDate(), null, null, "R", includeTestUsers);
	}

	private BigDecimal getRejectedTransactionValueInWeek(DashboardDataDateParams dateParams, boolean includeTestUsers) {
		return transactionDao.getTransactionsValue(null, dateParams.getStartingDateOfWeek(),
				dateParams.getEndDateOfWeek(), "R", includeTestUsers);
	}

	private BigDecimal getRejectedTransactionValueInDateRange(DashboardDataDateParams dateParams,
			boolean includeTestUsers) {
		return transactionDao.getTransactionsValue(null, dateParams.getStartingDate(), dateParams.getUptoDate(), "R",
				includeTestUsers);
	}

	private BigDecimal getPendingTransactionValueToday(boolean includeTestUsers) {
		return transactionDao.getTransactionsValue(DateUtils.getCurrentDateInDateObject(), null, null, "P",
				includeTestUsers);
	}

	private BigDecimal getPendingTransactionValueYesterday(DashboardDataDateParams dateParams,
			boolean includeTestUsers) {
		return transactionDao.getTransactionsValue(dateParams.getYesterdayDate(), null, null, "P", includeTestUsers);
	}

	private BigDecimal getPendingTransactionValueInWeek(DashboardDataDateParams dateParams, boolean includeTestUsers) {
		return transactionDao.getTransactionsValue(null, dateParams.getStartingDateOfWeek(),
				dateParams.getEndDateOfWeek(), "P", includeTestUsers);
	}

	private BigDecimal getPendingTransactionValueInDateRange(DashboardDataDateParams dateParams,
			boolean includeTestUsers) {
		return transactionDao.getTransactionsValue(null, dateParams.getStartingDate(), dateParams.getUptoDate(), "P",
				includeTestUsers);
	}

	private BigDecimal getClosedTransactionValueToday(boolean includeTestUsers) {
		return transactionDao.getTransactionsValue(DateUtils.getCurrentDateInDateObject(), null, null, "C",
				includeTestUsers);
	}

	private BigDecimal getClosedTransactionValueYesterday(DashboardDataDateParams dateParams,
			boolean includeTestUsers) {
		return transactionDao.getTransactionsValue(dateParams.getYesterdayDate(), null, null, "C", includeTestUsers);
	}

	private BigDecimal getClosedTransactionValueInWeek(DashboardDataDateParams dateParams, boolean includeTestUsers) {
		return transactionDao.getTransactionsValue(null, dateParams.getStartingDateOfWeek(),
				dateParams.getEndDateOfWeek(), "C", includeTestUsers);
	}

	private BigDecimal getClosedTransactionValueInDateRange(DashboardDataDateParams dateParams,
			boolean includeTestUsers) {
		return transactionDao.getTransactionsValue(null, dateParams.getStartingDate(), dateParams.getUptoDate(), "C",
				includeTestUsers);
	}
	
	private NotificationEntity createNotificationEntity(int senderId,int receiverId,String message,String title,NotificationTypeEntity nTypeEntity,NotificationStatusEntity nSatusEntity) {
		Date date=DateUtils.getCurrentDateTime();
		NotificationEntity nEntity=new NotificationEntity();
		nEntity.setNotificationTitle(title);
		nEntity.setNotificationBody(message);
		nEntity.setNotificationStatus(nSatusEntity);
		nEntity.setNotificationTypeEntity(nTypeEntity);
		nEntity.setModifiedBy(senderId);
		nEntity.setCreatedBy(senderId);
		nEntity.setCreatedOn(date);
		nEntity.setModifiedOn(date);
		nEntity.setSenderId(senderId);
		nEntity.setReceiverId(receiverId);
		return nEntity;
	}
	@Override
	public List<com.saifintex.web.dto.TransactionsDTO> getUsersTransactions(int userId) {
		List<Object[]> list=transactionDao.getUsersTransactions(userId);
		List<com.saifintex.web.dto.TransactionsDTO> dtoList=copyInTransactionDtoList(list, 0);
		return dtoList;
	}
	
	
	public boolean uploadFile(CommonsMultipartFile file, String path, String fileName) {
		byte[] bytes = file.getBytes();
		BufferedOutputStream bufferedOutputStream = null;
		try {
			File fileToUpload = new File(path + File.separator + fileName);
			FileOutputStream fileOutputStream = new FileOutputStream(fileToUpload);
			bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			bufferedOutputStream.write(bytes);
			bufferedOutputStream.flush();
			bufferedOutputStream.close();
			fileOutputStream.close();

		} catch (FileNotFoundException e) {
			getLogger().error(e);
			return false;
		} catch (IOException e) {
			getLogger().error(e);
			return false;
		} finally {
			try {
				if (bufferedOutputStream != null) {
					bufferedOutputStream.close();
				}
			} catch (IOException e) {
				getLogger().error(e);
				return false;

			}
		}
		return true;

	}
	public String getInvoicePicFileName(String fileName,TransactionsDTO transactionDto) {
		String newfileName = transactionDto.getPayerId()+"_"+transactionDto.getPayeeId()+"_"+transactionDto.getTransactionId() + "." + FilenameUtils.getExtension(fileName);
		return newfileName;
	}

@Transactional(readOnly=true)
	@Override
	public SalesPurchaseDetailDto getSalesPurchaseAmount(int userId, String period) {
	SalesPurchaseDetailDto dto=transactionDao.getSalesPurchaseAmount(userId, period);
	return dto;
	
	}


}