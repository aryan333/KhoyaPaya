package com.saifintex.services.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
/*import org.apache.commons.io.FilenameUtils;*/
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.JsonObject;
import com.saifintex.common.AbstractBase;
import com.saifintex.dao.AppPreferencesDAO;
import com.saifintex.dao.RelationsDAO;
import com.saifintex.dao.RewardPointsDAO;
import com.saifintex.dao.TransactionDAO;
import com.saifintex.dao.UserDAO;
import com.saifintex.dao.UsersBalanceDAO;
import com.saifintex.dao.UsersNameMappingDAO;
import com.saifintex.dao.UsersPointsMappingDAO;
import com.saifintex.dao.UsersTotalEarnedPointsDAO;
import com.saifintex.domain.ChangePassword;
import com.saifintex.domain.ForgotPassword;
import com.saifintex.domain.ForgotPin;
import com.saifintex.domain.NotificationEnableParams;
import com.saifintex.domain.TransactionDetail;
import com.saifintex.domain.UpdatePin;
import com.saifintex.domain.UpdateRelationParams;
import com.saifintex.domain.UpdateUserParams;
import com.saifintex.domain.User;
import com.saifintex.domain.UserContactsDetail;
import com.saifintex.domain.UserPaymentDetail;
import com.saifintex.domain.MerchantInfoBySalesPerson;
import com.saifintex.dto.DashboardDataDateParams;
import com.saifintex.dto.MerchantInfoBySalesPersonDto;
import com.saifintex.dto.RelationsDTO;
import com.saifintex.dto.RewardPointsDTO;
import com.saifintex.dto.RolesDTO;
import com.saifintex.dto.SaiCardDTO;
import com.saifintex.dto.UserDTO;
import com.saifintex.dto.UserDetailDTO;
import com.saifintex.dto.UserPointsEarnedDetailDTO;
import com.saifintex.dto.UserProfileDTO;
import com.saifintex.dto.UserStatsReportDTO;
import com.saifintex.dto.UserWithOpponentUserBalanceDto;
import com.saifintex.dto.UsersTotalEarnedPointsDTO;
import com.saifintex.entity.AppPreferencesEntity;
import com.saifintex.entity.ProfileImageEntity;
import com.saifintex.entity.RelationsEntity;
import com.saifintex.entity.RewardPointsEntity;
import com.saifintex.entity.RolesEntity;
import com.saifintex.entity.SaiCardsEntity;
import com.saifintex.entity.UserDetailsEntity;
import com.saifintex.entity.UserEntity;
import com.saifintex.entity.UserLoginLocationEntity;
import com.saifintex.entity.UsersBalanceEntity;
import com.saifintex.entity.UsersInvitationsDetailEntity;
import com.saifintex.entity.UsersNameMapping;
import com.saifintex.entity.UsersPointsMappingEntity;
import com.saifintex.entity.UsersTotalEarnedPointsEntity;
import com.saifintex.services.UserService;
import com.saifintex.utils.DateUtils;
import com.saifintex.utils.FcmNotificationUtil;
import com.saifintex.utils.ResponseCode;
import com.saifintex.utils.SmsThread;
import com.saifintex.utils.ThreadUtils;
import com.saifintex.web.domain.MerchantInfoBySP;
import com.saifintex.web.domain.SalesPersonReferalInfo;
import com.saifintex.web.dto.LatLngDTO;
import com.saifintex.web.dto.MerchantInfoBySPDTO;
import com.saifintex.web.dto.QrMappedUserDetailDTO;
import com.saifintex.web.dto.SalesInfoDTO;
import com.saifintex.web.dto.SalesPersonReferalInfoDTO;
import com.saifintex.web.dto.TotalReferalUserBySPDTO;
import com.saifintex.web.dto.TransactionStatsDashboardDTO;
import com.saifintex.web.dto.UsersListDTO;
import com.saifintex.web.dto.UsersStatsDashboardDTO;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends AbstractBase implements UserService {
	int result = 0;

	@Autowired
	UserDAO userDao;

	@Autowired
	TransactionDAO transactionDao;

	@Autowired
	UsersPointsMappingDAO usersPointsMappingDAO;

	@Autowired
	RewardPointsDAO rewardPointsDao;

	@Autowired
	UsersTotalEarnedPointsDAO usersTotalEarnedPointsDAO;

	@Autowired
	AppPreferencesDAO appPreferencesDAO;

	@Autowired
	PasswordEncoder passEncoder;

	@Autowired
	RelationsDAO relationsDao;

	@Autowired
	private UsersBalanceDAO usersBalanceDAO;

	@Autowired
	UsersNameMappingDAO userNameMappingDao;

	@Value("${app.profile.pic.folder}")
	private String path;
	
	@Value("${app.shop.pic.folder}")
	private String shopPicPath;

	@Value("${user.default.star.value}")
	private String defaultStarValue;

	@Value("${sai.card.status}")
	private String saiCardStatus;

	@Value("${app.notify.signup}")
	private String notificationMessage;

	@Value("${app.FCMAuthKey}")
	public String authKey;

	/*
	 * @Value("${permanent.user.type}") private String permanentUser;
	 */

	@Value("${app.temp.userName}")
	private String tempUserName;

	@Value("${app.invite.url}")
	private String appInviteUrl;
	/*
	 * @Value("${user.type.registered}") private String userTypeRegistered;
	 * 
	 * @Value("${user.type.unregistered}") private String userTypeUnregistered;
	 */

	@Value("${user.saicard.number.IIN}")
	private String iin;

	@Value("${user.saicard.number.length}")
	private int cardLength;

	@Value("${app.sms.message.to.non.app.person.part1}")
	private String messageToNonAppPersonPart1;

	@Value("${app.sms.message.to.non.app.person.part2}")
	private String messageToNonAppPersonPart2;

	@Value("${app.salesperson.inst.count.colorRed}")
	private String colorRed;

	@Value("${app.salesperson.inst.count.colorGreen}")
	private String colorGreen;
	
	@Value("${app.merchant.shop.pic}")
	private String shopPic;

	/*
	 * @Value("${support.contact.number}") private String contactSupportNo;
	 */
	@Override
	public Object signUp(UserDTO userDTO) {
		UserEntity userEntity = copyInEntity(userDTO);
		if(!isRefereeUserExist(userDTO.getReferralCode())) {
			return ResponseCode.REFEREE_USER_NOT_EXIST;
		}
		result = userDao.registerUser(userEntity);
		setReferralRewardIfAvailableForUser(result, userDTO);
		UserEntity generatedUserEntity = userDao.getUserById(result);
		userDTO = copyInDto(generatedUserEntity, null);
		return userDTO;
	}

	private void setReferralRewardIfAvailableForUser(int newlySignedUpUserid, UserDTO userDTO) {
		Set<RewardPointsDTO> rewardPointsDTOs = userDTO.getRewardPointsDTOs();
		if (rewardPointsDTOs != null && !rewardPointsDTOs.isEmpty()) {
			RewardPointsDTO rpDto = rewardPointsDTOs.iterator().next();
			RewardPointsEntity rpEntity = rewardPointsDao.findRewardPointEntityWithActiveState(rpDto.getId());
			String refCode = userDTO.getReferralCode();
			int signedupUserId = newlySignedUpUserid;
			int points = 0;
			if (rpEntity != null && rpEntity.getId() == 1 && refCode != null && !refCode.isEmpty()) {
				UserEntity entityByInviteCode = userDao.findByInviteCode(userDTO.getReferralCode()); // referenced user
																										// entity
				if (entityByInviteCode != null) {
					int referencedUserId = entityByInviteCode.getId();
					UsersPointsMappingEntity upmEntity = setRewardPointsMapping(referencedUserId, signedupUserId,
							rpEntity); // set reward point for referenced user
					points = upmEntity.getPointsEarned();
					usersPointsMappingDAO.save(upmEntity);
					saveTotalEarnedPointsForUser(points, entityByInviteCode);
					notifyUser(entityByInviteCode.getFcmToken(), userDTO.getFirstName());
					if (checkForSignedUpUserBenefitPreference()) {
						upmEntity = setRewardPointsMapping(0, signedupUserId, null); // set reward point for signed up
																						// user
						if (upmEntity != null) {
							points = upmEntity.getPointsEarned();
							usersPointsMappingDAO.save(upmEntity);
							saveTotalEarnedPointsForUser(points, signedupUserId);
						}
					}
				}
			} else if (checkForSignedUpUserBenefitPreference()) { // reward point id and refferal code is there but
																	// inactive so check if there is signup benefit
				UsersPointsMappingEntity upmEntity = setRewardPointsMapping(0, signedupUserId, rpEntity);
				if (upmEntity != null) {
					points = upmEntity.getPointsEarned();
					usersPointsMappingDAO.save(upmEntity);
					saveTotalEarnedPointsForUser(points, newlySignedUpUserid);
				}

			}
		} else if (checkForSignedUpUserBenefitPreference()) { // referral code is not there so checking if signup
																// benefit is there
			UsersPointsMappingEntity upmEntity = setRewardPointsMapping(0, newlySignedUpUserid, null);
			if (upmEntity != null) {
				int points = upmEntity.getPointsEarned();
				usersPointsMappingDAO.save(upmEntity);
				saveTotalEarnedPointsForUser(points, newlySignedUpUserid);
			}

		}
	}
private boolean isRefereeUserExist(String referalCode) {
	if(referalCode==null || referalCode.isEmpty() ) {
		return true;
	}
	UserEntity entityByInviteCode = userDao.findByInviteCode(referalCode);
	if(entityByInviteCode==null){
		return false;
	}
	 return true;
}
	// check if reward point to be given to signed up users
	private boolean checkForSignedUpUserBenefitPreference() {
		AppPreferencesEntity apEntity = appPreferencesDAO.getOne(AppPreferencesEntity.class, 1);
		return apEntity.isSignupBenefit(); // null not checked as there must be one row in app preference table
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

	// notify user having invite code
	private void notifyUser(String token, String signedUpUsername) {
		JsonObject data = new JsonObject();
		data.addProperty("type", "referralSignup");
		FcmNotificationUtil.sendNotification(token, signedUpUsername + notificationMessage, authKey, data);
	}

	@Override
	public String getTokenOfIndiviual(String mob) {
		String fcmToken = userDao.getTokenOfIndiviual(mob);

		return fcmToken;
	}

	@Override
	public String updateProfileImage(UserProfileDTO dto) {
		int result = 0;
		String blobId = getFileName(dto.getFile().getOriginalFilename());

		dto.setBlobId(blobId);

		if (uploadFile(dto.getFile(), path, dto.getBlobId())) {
			ProfileImageEntity entity = new ProfileImageEntity();
			BeanUtils.copyProperties(dto, entity);
			result = userDao.insertProfileImage(entity);
		}
		if (result <= 0) {
			blobId = null;
		}
		return blobId;
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

	public String getFileName(String fileName) {
		UUID uuid = UUID.randomUUID();
		String newfileName = uuid.toString() + "." + FilenameUtils.getExtension(fileName);
		return newfileName;
	}

	public int insertPayment(UserPaymentDetail userPaymentDetail) {
		result = 0;
		userPaymentDetail.setUserId(getUserId(userPaymentDetail.getPhone()));
		userPaymentDetail.setPaymentDate(DateUtils.getCurrentTime());
		userDao.inserPayment(userPaymentDetail);
		return result;
	}

	@Override
	public UserDTO getUserById(int userId) {
		UserEntity userEntity = userDao.getUserById(userId);
		UserDTO dto = null;
		if (userEntity != null) {
			dto = copyInDto(userEntity, null);
		}
		return dto;
	}

	public boolean updateUserPassword(ForgotPassword forgotPassword) {
		String encryptPassword = encryptPassword(forgotPassword.getPassword());
		forgotPassword.setPassword(encryptPassword);
		boolean result = userDao.updatePassword(forgotPassword);
		return result;
	}

	public List<TransactionDetail> getTransactionHistory(int userId) {
		List<TransactionDetail> transactionHistory = userDao.getUserTransactionHistoryById(userId);
		return transactionHistory;
	}

	public String getTranscationId() {
		Random rand = new Random();
		String rndm = Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L);
		String txnid = hashCal("SHA-256", rndm).substring(0, 20);
		return txnid;
	}

	private String hashCal(String type, String str) {
		byte[] hashseq = str.getBytes();
		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest algorithm = MessageDigest.getInstance(type);
			algorithm.reset();
			algorithm.update(hashseq);
			byte messageDigest[] = algorithm.digest();
			for (int i = 0; i < messageDigest.length; i++) {
				String hex = Integer.toHexString(0xFF & messageDigest[i]);
				if (hex.length() == 1)
					hexString.append("0");
				hexString.append(hex);
			}

		} catch (NoSuchAlgorithmException nsae) {
		}

		return hexString.toString();

	}

	private boolean empty(String s) {
		if (s == null || s.trim().equals(""))
			return true;
		else
			return false;
	}

	@Override
	public int insertLoginLocations(String lat, String lon, int userId) {
		UserLoginLocationEntity entity = new UserLoginLocationEntity();
		entity.setUserId(userId);
		entity.setLatitude(lat);
		entity.setLongitude(lon);
		entity.setCreatedOn(DateUtils.getCurrentDateTime());
		entity.setCreatedBy(userId);
		return userDao.insertLoginLocations(entity);
	}

	public UserEntity copyInEntity(UserDTO userDTO) {
		Date date = DateUtils.getCurrentDateTime();
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDTO, userEntity);
		userEntity.setPassword(encryptPassword(userDTO.getPassword()));
		if (userDTO.getFirstName() != null && !userDTO.getFirstName().isEmpty()) {
			userEntity.setInviteCode(getInviteCode(userDTO));
		}
		userEntity.setActualUser(true);
		userEntity.setEnabled(true);
		userEntity.setAccountNonLocked(true);
		userEntity.setAccountNonExpired(true);
		userEntity.setCredentialsNonExpired(true);
		userEntity.setActive(true);
		userEntity.setLastResetPasswordDate(date);
		userEntity.setNotificationEnable(true);
		if (userDTO.getCreatedBy() == 0) {
			userEntity.setCreatedBy(1);
			userEntity.setModifiedBy(1);
			userEntity.setCreatedOn(date);
		} else {
			userEntity.setModifiedBy(userDTO.getId());
		}
		userEntity.setModifiedOn(date);
		UserDetailsEntity udEntity = new UserDetailsEntity();
		BeanUtils.copyProperties(userDTO.getUserDetailDTO(), udEntity);
		udEntity.setGender("N");
		udEntity.setStarValue(new BigDecimal(defaultStarValue));
		udEntity.setCreatedBy(userEntity.getCreatedBy());
		udEntity.setCreatedOn(userEntity.getCreatedOn());
		udEntity.setModifiedOn(userEntity.getModifiedOn());
		udEntity.setModifiedBy(userEntity.getModifiedBy());
		userEntity.setUserDetailsEntity(udEntity);
		userEntity.setMerchant(false);
		SaiCardsEntity saiCardEntity = new SaiCardsEntity();
		saiCardEntity.setSaiCardNumber(getSaiCardNumber(iin, cardLength));
		saiCardEntity.setCreatedOn(udEntity.getCreatedOn());
		saiCardEntity.setSaiCardIssueDate(udEntity.getCreatedOn());
		saiCardEntity.setSaiCardStatus(saiCardStatus);
		saiCardEntity.setValidUpto(getCardsValidity());
		saiCardEntity.setCreatedBy(udEntity.getCreatedBy());
		saiCardEntity.setModifiedOn(date);
		saiCardEntity.setModifiedBy(udEntity.getCreatedBy());
		userEntity.setSaiCardEntity(saiCardEntity);

		Set<RolesEntity> roleEntitySet = new HashSet<RolesEntity>();
		RolesEntity roleEntity = new RolesEntity();
		RolesDTO roleDTO = userDTO.getRolesDto().iterator().next();
		BeanUtils.copyProperties(roleDTO, roleEntity);
		roleEntitySet.add(roleEntity);
		userEntity.setRoles(roleEntitySet);

		UsersTotalEarnedPointsEntity utepEntity = new UsersTotalEarnedPointsEntity();
		utepEntity.setTotalPointsEarned(0);
		utepEntity.setCreatedBy(userEntity.getCreatedBy());
		utepEntity.setCreatedOn(date);
		utepEntity.setModifiedBy(userEntity.getCreatedBy());
		utepEntity.setModifiedOn(date);
		userEntity.setUsersTotalEarnedPointsEntity(utepEntity);
		Set<RewardPointsDTO> rpDtos = userDTO.getRewardPointsDTOs();
		if (rpDtos != null && !rpDtos.isEmpty()) {
			if (rpDtos.iterator().next().getId() == 2) {
				userEntity.setReferralCode("");
			}
		}
		return userEntity;

	}

	public String encryptPassword(String password) {
		String encryptPassword = passEncoder.encode(password);
		return encryptPassword;
	}

	@Override
	public Object signIn(String userLogin, String password, String lat, String lng) {
		List<Object> list = userDao.getUserByLoginAndOtherParams(userLogin);
		if (list != null) {
			UserEntity entity = (UserEntity) list.get(0);
			if (entity.getUserType().equalsIgnoreCase("T")) {
				return ResponseCode.RECORD_NOT_FOUND;
			}
			ProfileImageEntity pEntity = (ProfileImageEntity) list.get(1);
			int result = 0;
			if (passEncoder.matches(password, entity.getPassword())) {
				result = insertLoginLocations(lat, lng, entity.getId());
				if (result != 0) {
					UserDTO dto = copyInDto(entity, pEntity);
					return dto;
				}
			} else {
				return ResponseCode.INVALID_PASSWORD;
			}
		}
		return ResponseCode.RECORD_NOT_FOUND;

	}

	private UserDTO copyInDto(UserEntity entity, ProfileImageEntity pEntity) {
		UserDetailDTO userDetailDto = new UserDetailDTO();
		BeanUtils.copyProperties(entity.getUserDetailsEntity(), userDetailDto);
		if (pEntity != null) {
			userDetailDto.setBlobId(pEntity.getBlobId());
		}

		UserDTO userDto = new UserDTO();
		BeanUtils.copyProperties(entity, userDto);
		if (!entity.isEnabled() || !entity.isCredentialsNonExpired() || !entity.isAccountNonExpired()
				|| !entity.isAccountNonLocked()) {
			userDto.setActive(false);
		}

		SaiCardDTO saiDto = new SaiCardDTO();
		BeanUtils.copyProperties(entity.getSaiCardEntity(), saiDto);

		Set<RolesDTO> rolesDtoSet = new HashSet<RolesDTO>();
		Set<RolesEntity> rolesEntitySet = entity.getRoles();
		for (RolesEntity use : rolesEntitySet) {
			RolesDTO urd = new RolesDTO();
			BeanUtils.copyProperties(use, urd);
			rolesDtoSet.add(urd);
		}
		if (entity.getUsersTotalEarnedPointsEntity() != null) {
			UsersTotalEarnedPointsDTO utepDto = new UsersTotalEarnedPointsDTO();
			BeanUtils.copyProperties(entity.getUsersTotalEarnedPointsEntity(), utepDto);
			userDto.setUsersTotalEarnedPointsDTO(utepDto);
		}
		userDto.setUserDetailDTO(userDetailDto);
		userDto.setSaiCardDto(saiDto);
		userDto.setRolesDto(rolesDtoSet);
		return userDto;
	}

	@Override
	public User getUserForPay(String userId, String amount, String description) {
		return null;
	}

	@Override
	public int changePassword(ChangePassword changePassword) {
		UserEntity userEntity = userDao.getUserById(changePassword.getUserId());
		if (userEntity != null) {
			if (!passEncoder.matches(changePassword.getOldPassword(), userEntity.getPassword())) {
				return ResponseCode.OLD_PASSWORD_DOES_NOT_MATCH;
			}
			String pass = encryptPassword(changePassword.getNewPassword());
			changePassword.setNewPassword(pass);
			boolean result = userDao.changePassword(changePassword);
			if (result) {
				return ResponseCode.UPDATED_IN_DB;
			}
		}
		return ResponseCode.NOT_UPDATED_IN_DB;
	}

	@Override
	public int getUserId(String userLogin) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional(readOnly = true)
	@Override
	public UserDTO isUserAlreadyExist(String mobileNumber) {
		UserEntity userEntity = userDao.doesUserExist(mobileNumber);
		UserDTO userDTO = null;
		if (userEntity != null) {
			userDTO = copyInDto(userEntity, null);
			return userDTO;
		}
		return userDTO;
	}

	/*
	 * private long getSaiCardNumber() { String cardNumber = "558" +
	 * System.currentTimeMillis();
	 * 
	 * return Long.parseLong(cardNumber); }
	 */

	private Date getCardsValidity() {
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		cal.add(Calendar.YEAR, 1); // to get previous year add -1
		Date validity = cal.getTime();
		return validity;
	}

	private String getInviteCode(UserDTO userDto) {
		String [] toCharArray=userDto.getFirstName().split(" ");
		StringBuilder builder=new StringBuilder();
		for(int i=0;i<toCharArray.length;i++) {
			builder.append(toCharArray[i]);
		}
		
		String inviteCode = builder.substring(0, 3) + userDto.getPhNumber().substring(3, 10);
		return inviteCode.toLowerCase();
	}

	@Override
	public boolean updateUserInfo(UpdateUserParams user) {
		return userDao.updateUserInfo(user);
	}

	/*
	 * API is use to create an temporary account if not exists @Ajay
	 */
	@Override
	public UserWithOpponentUserBalanceDto createTempUser(UserDTO userDto) {

		UserWithOpponentUserBalanceDto userWithOpponentUserBalanceDto = null;
		int loggedInUserId = userDto.getCreatedBy();
		UserDTO existingUserDto = getUserByLogin(userDto.getPhNumber());
		UserEntity loggedInUser = userDao.getUserById(userDto.getCreatedBy());
		if (existingUserDto == null) {
			getLogger().info("In User Entity null");
			UserEntity userEntityToInsert = copyInEntity(userDto);
			userEntityToInsert.setCreatedOn(DateUtils.getCurrentDateTime());
			userEntityToInsert.setFirstName(tempUserName);
			userEntityToInsert.setActualUser(false);
			Integer id = (Integer) userDao.registerUser(userEntityToInsert);
			UserEntity generatedUserEntity = userDao.getUserById(id);
			UsersBalanceEntity usersBalanceEntity = usersBalanceDAO.getByBothUsersId(userDto.getCreatedBy(),
					generatedUserEntity.getId());
			if (usersBalanceEntity == null) {
				getLogger().info("In User balance Entity null");

				saveUserBalance(userDto.getCreatedBy(), generatedUserEntity.getId(), userDto.getMappedName());
				usersBalanceEntity = usersBalanceDAO.getByBothUsersId(userDto.getCreatedBy(),
						generatedUserEntity.getId());
			}
			userDto = copyInDto(generatedUserEntity, null);
			userWithOpponentUserBalanceDto = copyInUsersWithOpponentUserBalanceDto(loggedInUserId, userDto,
					usersBalanceEntity);
			if (generatedUserEntity != null) {
				// UserEntity loggedInUser=userDao.getOne(UserEntity.class,
				// userDto.getCreatedBy());

				SmsThread smsThread = new SmsThread(userDto.getPhNumber(),
						messageToNonAppPersonPart1 + " " + loggedInUser.getFirstName() + " "
								+ messageToNonAppPersonPart2 + " " + appInviteUrl + userDto.getReferralCode());
				ThreadUtils.start(smsThread);
			}
			return userWithOpponentUserBalanceDto;
		} else {
			getLogger().info("In User Entity NOT null");
			UsersBalanceEntity usersBalanceEntity = usersBalanceDAO.getByBothUsersId(userDto.getCreatedBy(),
					existingUserDto.getId());
			if (usersBalanceEntity == null) {
				getLogger().info("In User Balance Entity  null");

				saveUserBalance(userDto.getCreatedBy(), existingUserDto.getId(), userDto.getMappedName());

			} else {
				getLogger().info("In UserBalance Entity NOT null");
				if (usersBalanceEntity.getUser1() == userDto.getCreatedBy()) {
					getLogger().info("In UserBalance user1=loggedin");
					userNameMappingDao.updateUserNameMappping(usersBalanceEntity.getUsersNameMapping().getId(), null,
							userDto.getMappedName());
				} else {
					getLogger().info("In UserBalance user1!=loggedin");
					userNameMappingDao.updateUserNameMappping(usersBalanceEntity.getUsersNameMapping().getId(),
							userDto.getMappedName(), null);

				}
			}
			usersBalanceEntity = usersBalanceDAO.getByBothUsersId(userDto.getCreatedBy(), existingUserDto.getId());
			userWithOpponentUserBalanceDto = copyInUsersWithOpponentUserBalanceDto(loggedInUserId, existingUserDto,
					usersBalanceEntity);

		}

		if (userWithOpponentUserBalanceDto.getUserType().equalsIgnoreCase("T")) {
			SmsThread smsThread = new SmsThread(userDto.getPhNumber(),
					messageToNonAppPersonPart1 + " " + loggedInUser.getFirstName() + " " + messageToNonAppPersonPart2
							+ " " + appInviteUrl + userDto.getReferralCode());
			ThreadUtils.start(smsThread);
		}
		return userWithOpponentUserBalanceDto;
	}

	@Override
	public Object updateUser(UserDTO userDto) {
		if(!isRefereeUserExist(userDto.getReferralCode())) {
			return ResponseCode.REFEREE_USER_NOT_EXIST;
		}
		UserEntity userEntity = copyInEntity(userDto);
		userEntity.setId(userDto.getId());
		userEntity.setCreatedOn(DateUtils.getCurrentDateTime());
		userEntity.setCreatedBy(userDto.getCreatedBy());
		userEntity = userDao.updateUser(userEntity);
		setReferralRewardIfAvailableForUser(userEntity.getId(), userDto);
		UserDTO dto = copyInDto(userEntity, null);
		return dto;
	}

	@Override
	public UserDTO getStarValue(int userId) {
		UserDetailDTO userDetailDTO = new UserDetailDTO();
		UserDTO userDTO = new UserDTO();
		UserEntity userEntity = userDao.getStarValue(userId);
		BeanUtils.copyProperties(userEntity, userDTO);
		BeanUtils.copyProperties(userEntity.getUserDetailsEntity(), userDetailDTO);
		userDetailDTO.setStarValue(userDetailDTO.getStarValue());
		userDTO.setUserDetailDTO(userDetailDTO);
		return userDTO;
	}

	@Override
	public List<UserContactsDetail> checkUserContactsExistance(List<UserContactsDetail> list) {
		List<UserEntity> listOfUserEntities = userDao.getAllUsers();
		setUserExistance(list, listOfUserEntities);
		return list;
	}

	@Override
	public List<UserContactsDetail> checkUserContactsExistanceD(List<UserContactsDetail> list) {
		List<UserEntity> listOfUserEntities = userDao.getAllUsers();
		return setUserExistanceD(list, listOfUserEntities);

	}

	private void setUserExistance(List<UserContactsDetail> listDto, List<UserEntity> listOfUserEntities) {
		for (UserEntity userEntity : listOfUserEntities) {
			for (UserContactsDetail contact : listDto) {
				if (contact.getContactNumber().equalsIgnoreCase(userEntity.getPhNumber()) && !userEntity.isTestUser()) {
					contact.setExist(true);
					contact.setUserServerName(userEntity.getFirstName());
					// dto.setUserId(userEntity.getId());
				} else if (contact.getContactNumber().equalsIgnoreCase(userEntity.getPhNumber())
						&& !userEntity.isTestUser()) {
					contact.setTest(true);
					contact.setUserServerName(userEntity.getFirstName());
					// dto.setUserId(userEntity.getId());
				}
			}
		}
	}

	private List<UserContactsDetail> setUserExistanceD(List<UserContactsDetail> list,
			List<UserEntity> listOfUserEntities) {
		List<UserContactsDetail> userContactsDetails = new ArrayList<UserContactsDetail>();
		for (UserEntity userEntity : listOfUserEntities) {

			for (UserContactsDetail contact : list) {

				if (contact.getContactNumber().equalsIgnoreCase(userEntity.getPhNumber()) && !userEntity.isTestUser()) {
					UserContactsDetail userContactsDetail = new UserContactsDetail();
					userContactsDetail.setExist(true);
					userContactsDetail.setUserServerName(userEntity.getFirstName());
					// dto.setUserId(userEntity.getId());
					userContactsDetail.setContactName(contact.getContactName());
					userContactsDetail.setContactNumber(contact.getContactNumber());
					userContactsDetails.add(userContactsDetail);
				} else if (contact.getContactNumber().equalsIgnoreCase(userEntity.getPhNumber())
						&& !userEntity.isTestUser()) {
					UserContactsDetail userContactsDetail = new UserContactsDetail();
					userContactsDetail.setTest(true);
					userContactsDetail.setUserServerName(userEntity.getFirstName());
					userContactsDetail.setContactNumber(contact.getContactNumber());
					// dto.setUserId(userEntity.getId());
					userContactsDetail.setContactName(contact.getContactName());
					userContactsDetails.add(userContactsDetail);

				}
			}

		}
		return userContactsDetails;
	}

	@Override
	public List<UserDTO> getAssociatedUsers(int id) {
		List<UserEntity> list = userDao.getAssociatedUsers(id);
		List<UserDTO> dtoList = new ArrayList<UserDTO>();
		for (UserEntity userEntity : list) {
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(userEntity, userDTO);
			dtoList.add(userDTO);
		}
		return dtoList;
	}

	@Override
	public List<RelationsDTO> getRelationsService() {
		List<Object> list = userDao.fetchRelations();
		List<RelationsDTO> dtoList = new ArrayList<RelationsDTO>();
		for (Object obj : list) {
			RelationsDTO dto = new RelationsDTO();
			BeanUtils.copyProperties(obj, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public boolean saveRelationService(UpdateRelationParams params) {
		UsersBalanceEntity usersBalanceEntity = usersBalanceDAO.getByBothUsersId(params.getLoggedInUser(),
				params.getOpponentUser());
		if (usersBalanceEntity == null) {
			return false;
		}
		UsersNameMapping usersNameMapping = usersBalanceEntity.getUsersNameMapping();
		RelationsEntity rEntity = relationsDao.getOne(RelationsEntity.class, params.getRelationId());
		if (usersBalanceEntity.getUser1() == params.getLoggedInUser()) {
			usersNameMapping.setRelationEntity(rEntity);
		} else {
			usersNameMapping.setOppositeRelationEntity(rEntity);
		}
		// usersBalanceEntity.setUsersNameMapping(usersNameMapping);
		return true;
	}

	@Override
	public Map<String, String> getIndividualRelationService(int loginId, int oppId) {
		Object[] arr = userDao.getIndividualRelation(loginId, oppId);
		if (arr == null) {
			return null;
		}
		String relation = (String) arr[0];
		int relationId = (Integer) arr[1];
		Map<String, String> map = new HashMap<String, String>();
		map.put("relation", relation);
		map.put("relationId", relationId + "");
		return map;
	}

	@Override
	public boolean isPinValid(int userId, String pin) {
		UserEntity userEntity = userDao.getOne(UserEntity.class, userId);
		if (passEncoder.matches(pin, userEntity.getPin())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean savePinService(int uId, String pin) {
		String encryptedPin = encryptPassword(pin);
		boolean result = userDao.savePin(uId, encryptedPin);
		if (result) {
			return true;
		}
		return false;
	}

	@Override
	public int resetPinService(ForgotPin forgotPin) {
		UserEntity userEntity = userDao.getOne(UserEntity.class, forgotPin.getUserId());
		if (userEntity == null) {
			return ResponseCode.RECORD_NOT_FOUND;
		}
		if (passEncoder.matches(forgotPin.getPassword(), userEntity.getPassword())) {
			userEntity.setPin(passEncoder.encode(forgotPin.getPin()));
			return ResponseCode.UPDATED_IN_DB;
		}

		return ResponseCode.OLD_PASSWORD_DOES_NOT_MATCH;
	}

	@Override
	public int updatePin(UpdatePin updatePin) {
		boolean value = isPinValid(updatePin.getUserId(), updatePin.getPin());
		if (value) {
			String pass = encryptPassword(updatePin.getNewPin());
			updatePin.setNewPin(pass);
			boolean result = userDao.updatePin(updatePin);
			if (result) {

				return ResponseCode.UPDATED_IN_DB;
			}

			return ResponseCode.NOT_UPDATED_IN_DB;

		}
		return ResponseCode.OLD_PASSWORD_DOES_NOT_MATCH;
	}

	@Override
	public boolean saveGstinNumber(int userId, String gstinNumber) {

		boolean result = userDao.saveGstinNumber(userId, gstinNumber);
		setIsMerchant(userId);
		if (result) {
			return true;
		}
		return false;
	}

	@Override
	public UserDTO getUserByLogin(String username) {
		List list = userDao.getUserByLogin(username);
		if (list != null) {
			UserEntity entity = (UserEntity) list.get(0);
			ProfileImageEntity pEntity = (ProfileImageEntity) list.get(1);
			UserDTO userDto = copyInDto(entity, pEntity);
			return userDto;
		}
		return null;
	}

	@Override
	public UserDTO getUserByStandee(String standeeId) {
		List list = userDao.getUserByStandee(standeeId);
		if (list != null) {
			UserEntity entity = (UserEntity) list.get(0);
			ProfileImageEntity pEntity = (ProfileImageEntity) list.get(1);
			UserDTO userDto = copyInDto(entity, pEntity);
			return userDto;
		}
		return null;
	}

	@Override
	public Object getUserByLoginAndOtherParamsService(String username) {
		Object obj = userDao.getUserByLoginAndOtherParams(username);
		if (obj instanceof List) {
			List<Object> list = (List<Object>) obj;
			UserEntity entity = (UserEntity) list.get(0);
			ProfileImageEntity pEntity = (ProfileImageEntity) list.get(1);
			UserDTO userDto = copyInDto(entity, pEntity);
			return userDto;
		}
		return obj;
	}

	@Override
	public UserDTO getOnlyUserInfoByLogin(String mobile) {
		UserEntity entity = userDao.findByUserLogin(mobile);
		if (entity == null) {
			return null;
		}
		UserDTO userDTO = copyInDto(entity, null);
		return userDTO;
	}

	@Override
	public void deactivateUserService(String userLogin, String action) {
		UserEntity entity = userDao.findByUserLogin(userLogin);
		switch (action) {
		case "disable":
			entity.setEnabled(false);
			break;
		case "lock":
			entity.setAccountNonLocked(false);
			break;
		case "expAcc":
			entity.setAccountNonExpired(false);
			break;
		case "expCred":
			entity.setCredentialsNonExpired(false);
			break;
		default:
			break;
		}
		entity.setModifiedOn(DateUtils.getCurrentDateTime());
		entity.setActive(false);
	}

	@Override
	public void activateUserService(String userLogin, String fieldToUpdate) {
		UserEntity entity = userDao.findByUserLogin(userLogin);
		switch (fieldToUpdate) {
		case "Disabled":
			entity.setEnabled(true);
			break;
		case "Account Locked":
			entity.setAccountNonLocked(true);
			break;
		case "Account Expired":
			entity.setAccountNonExpired(true);
			break;
		case "Credentials Expired":
			entity.setCredentialsNonExpired(true);
			break;
		default:
			break;
		}
		entity.setModifiedOn(DateUtils.getCurrentDateTime());
		entity.setActive(true);
	}

	@Override
	public TransactionStatsDashboardDTO getSpecificUserTransStats(int userId) {
		TransactionStatsDashboardDTO dto = transactionDao.getSpecificUserTransactionStats(userId);
		return dto;
	}

	@Override
	public BigDecimal getMutualBalance(int loggedInUserId, int opponentUserId) {
		UsersBalanceEntity usersBalanceEntity = userDao.getUserBalanceEntityForMutualBalance(loggedInUserId,
				opponentUserId);
		if (usersBalanceEntity == null) {
			return BigDecimal.ZERO;
		}
		BigDecimal mutualBalance = getMutualBalance(usersBalanceEntity, loggedInUserId, opponentUserId);
		return mutualBalance;
	}

	private BigDecimal getMutualBalance(UsersBalanceEntity balanceEntity, int loggedInuserId, int opponentUserId) {
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

	@Override
	public UsersStatsDashboardDTO usersStatsService() {
		return userDao.getUserStats(DateUtils.getStartDateOfCurrWeek(), DateUtils.getCurrentMonth());
	}

	@Override
	public List<UserDTO> getAllBluPayUsersService() {
		List<UserEntity> list = userDao.getAllBluPayUsers();
		return copyEntityListIntoDTOList(list);
	}

	@Override
	public List<UserDTO> getRegisteredBluPayUsersService() {
		List<UserEntity> list = userDao.getAllRegisteredBluPayUsers();
		return copyEntityListIntoDTOList(list);
	}

	@Override
	public List<UserDTO> getTemporaryBluPayUsersService() {
		List<UserEntity> list = userDao.getAllTemporaryBluPayUsers();
		return copyEntityListIntoDTOList(list);
	}

	@Override
	public List<UserDTO> getNewRegisteredBluPayUsersTodayService() {
		List<UserEntity> list = userDao.getAllRegisteredUsersToday();
		return copyEntityListIntoDTOList(list);
	}

	@Override
	public List<UserDTO> getNewTemporaryBluPayUsersTodayService() {
		List<UserEntity> list = userDao.getAllTemporaryUsersToday();
		return copyEntityListIntoDTOList(list);
	}

	@Override
	public List<UserDTO> getNewBluPayUsersTodayService() {
		List<UserEntity> list = userDao.getAllUsersToday();
		return copyEntityListIntoDTOList(list);
	}

	@Override
	public List<UserDTO> getUsersBetweenDateRangeService(String sDate, String eDate, String flag) {
		List<UserEntity> list = userDao.getAllUsersBetweenDateRange(DateUtils.getDate(sDate), DateUtils.getDate(eDate),
				flag);
		return copyEntityListIntoDTOList(list);
	}

	@Override
	public List<UserDTO> getUsersInCurrWeekService() {
		List<UserEntity> list = userDao.getAllUsersInCurrentWeek(DateUtils.getStartDateOfCurrWeek());
		return copyEntityListIntoDTOList(list);
	}

	@Override
	public List<UserDTO> getRegisteredUsersInCurrWeekService() {
		List<UserEntity> list = userDao.getAllRegisteredUsersInCurrentWeek(DateUtils.getStartDateOfCurrWeek());
		return copyEntityListIntoDTOList(list);
	}

	@Override
	public List<UserDTO> getTemporaryUsersInCurrWeekService() {
		List<UserEntity> list = userDao.getAllTempUsersInCurrentWeek(DateUtils.getStartDateOfCurrWeek());
		return copyEntityListIntoDTOList(list);
	}

	@Override
	public List<UserDTO> getUsersInCurrMonthService() {
		List<UserEntity> list = userDao.getAllUsersInCurrentMonth(DateUtils.getCurrentMonth());
		return copyEntityListIntoDTOList(list);
	}

	@Override
	public List<UserDTO> getRegUsersInCurrMonthService() {
		List<UserEntity> list = userDao.getAllRegsiteredUsersInCurrentMonth(DateUtils.getCurrentMonth());
		return copyEntityListIntoDTOList(list);
	}

	@Override
	public List<UserDTO> getTempUsersInCurrMonthService() {
		List<UserEntity> list = userDao.getAllTemporaryUsersInCurrentMonth(DateUtils.getCurrentMonth());
		return copyEntityListIntoDTOList(list);
	}

	@Override
	public UsersStatsDashboardDTO getUserStatsBetweenDateRangeService(String startDate, String endDate) {
		return userDao.getUsersStatsBetweenDateRange(DateUtils.getDate(startDate), DateUtils.getDate(endDate));
	}

	private List<UserDTO> copyEntityListIntoDTOList(List<UserEntity> list) {
		List<UserDTO> listDto = new ArrayList<UserDTO>();
		if (list != null || !list.isEmpty()) {
			for (UserEntity entity : list) {
				UserDetailsEntity udEntity = entity.getUserDetailsEntity();
				UserDetailDTO udDto = new UserDetailDTO();
				UserDTO uDto = new UserDTO();
				BeanUtils.copyProperties(udEntity, udDto);
				BeanUtils.copyProperties(entity, uDto);
				UsersTotalEarnedPointsDTO earnedPointsDTO = new UsersTotalEarnedPointsDTO();
				BeanUtils.copyProperties(entity.getUsersTotalEarnedPointsEntity(), earnedPointsDTO);
				/*
				 * if(uDto.getUserType().startsWith(permanentUser)) {
				 * uDto.setUserType(userTypeRegistered); }else {
				 * uDto.setUserType(userTypeUnregistered); }
				 */
				uDto.setUserDetailDTO(udDto);
				uDto.setUsersTotalEarnedPointsDTO(earnedPointsDTO);
				listDto.add(uDto);
			}
		}
		return listDto;
	}

	private Random random = new Random(System.currentTimeMillis());

	/**
	 * Generates a random valid credit card number. For more information about the
	 * credit card number generation algorithms and credit card numbers refer to
	 * <a href="http://euro.ecom.cmu.edu/resources/elibrary/everycc.htm">Everything
	 * you ever wanted to know about CC's</a>,
	 * <a href="http://www.darkcoding.net/credit-card/">Graham King's blog</a>, and
	 * <a href=
	 * "http://codytaylor.org/2009/11/this-is-how-credit-card-numbers-are-generated.html"
	 * >This is How Credit Card Numbers Are Generated</a>
	 *
	 * @param bin
	 *            The bank identification number, a set digits at the start of the
	 *            credit card number, used to identify the bank that is issuing the
	 *            credit card.
	 * @param length
	 *            The total length (i.e. including the BIN) of the credit card
	 *            number.
	 * @return A randomly generated, valid, credit card number.
	 */
	public Long getSaiCardNumber(String bin, int length) {

		// The number of random digits that we need to generate is equal to the
		// total length of the card number minus the start digits given by the
		// user, minus the check digit at the end.
		int randomNumberLength = length - (bin.length() + 1);

		StringBuilder builder = new StringBuilder(bin);
		for (int i = 0; i < randomNumberLength; i++) {
			int digit = this.random.nextInt(10);
			builder.append(digit);
		}

		// Do the Luhn algorithm to generate the check digit.
		int checkDigit = this.getCheckDigit(builder.toString());
		builder.append(checkDigit);

		return Long.parseLong(builder.toString());
	}

	/**
	 * Generates the check digit required to make the given credit card number valid
	 * (i.e. pass the Luhn check)
	 *
	 * @param number
	 *            The credit card number for which to generate the check digit.
	 * @return The check digit required to make the given credit card number valid.
	 */
	private int getCheckDigit(String number) {

		// Get the sum of all the digits, however we need to replace the value
		// of the first digit, and every other digit, with the same digit
		// multiplied by 2. If this multiplication yields a number greater
		// than 9, then add the two digits together to get a single digit
		// number.
		//
		// The digits we need to replace will be those in an even position for
		// card numbers whose length is an even number, or those is an odd
		// position for card numbers whose length is an odd number. This is
		// because the Luhn algorithm reverses the card number, and doubles
		// every other number starting from the second number from the last
		// position.
		int sum = 0;
		for (int i = 0; i < number.length(); i++) {

			// Get the digit at the current position.
			int digit = Integer.parseInt(number.substring(i, (i + 1)));

			if ((i % 2) == 0) {
				digit = digit * 2;
				if (digit > 9) {
					digit = (digit / 10) + (digit % 10);
				}
			}
			sum += digit;
		}

		// The check digit is the number required to make the sum a multiple of
		// 10.
		int mod = sum % 10;
		return ((mod == 0) ? 0 : 10 - mod);
	}

	@Override
	public String getToken(int userId) {
		String token = null;
		UserEntity userEntity = userDao.getToken(userId);
		if (userEntity != null) {
			token = userEntity.getFcmToken();
		}
		return token;
	}

	@Override
	public int updateFCMToken(int userId, String token) {
		int result = userDao.updateFCMToken(userId, token);
		return result;
	}

	@Override
	public List<String> getTokenOfAllUsers() {
		List<String> list = userDao.getTokenOfAllUsers();
		List<String> listOfTokens = new ArrayList<>();
		for (String token : list) {
			listOfTokens.add(token);

		}
		return listOfTokens;
	}

	private void saveUserBalance(int user1, int user2, String opponentUserName) {
		UsersBalanceEntity usersBalanceEntity = createUsersBalanceEntity(user1, user2, opponentUserName);
		usersBalanceDAO.save(usersBalanceEntity);
	}

	private UsersBalanceEntity createUsersBalanceEntity(int user1, int user2, String opponentUserName) {
		UsersBalanceEntity balanceEntity = new UsersBalanceEntity();
		Date date = DateUtils.getCurrentDateTime();
		balanceEntity.setBalance(BigDecimal.ZERO);
		balanceEntity.setCreatedOn(date);
		balanceEntity.setTotalCash(BigDecimal.ZERO);
		balanceEntity.setCreatedBy(user1);
		balanceEntity.setUser1(user1);
		balanceEntity.setUser2(user2);
		balanceEntity.setPendingTransactionAmount(BigDecimal.ZERO);
		balanceEntity.setModifiedBy(user1);
		balanceEntity.setCreatedOn(date);
		balanceEntity.setModifiedOn(date);
		balanceEntity.setTransacted(false);
		UsersNameMapping nameMapping = new UsersNameMapping();

		nameMapping.setUserName1("");
		nameMapping.setUserName2(opponentUserName);
		nameMapping.setCreatedBy(user1);
		nameMapping.setCreatedOn(date);
		balanceEntity.setUsersNameMapping(nameMapping);
		UsersInvitationsDetailEntity invitationsDetailEntity = new UsersInvitationsDetailEntity();
		invitationsDetailEntity.setFcmInviteCount(0);
		invitationsDetailEntity.setSmsInviteCount(0);
		invitationsDetailEntity.setUser1Id(user1);
		invitationsDetailEntity.setUser2Id(user2);
		invitationsDetailEntity.setCreatedBy(user1);
		invitationsDetailEntity.setModifiedBy(user1);
		invitationsDetailEntity.setCreatedOn(date);
		invitationsDetailEntity.setModifiedOn(date);
		balanceEntity.setUsersInvitationsDetailEntity(invitationsDetailEntity);
		return balanceEntity;
	}

	private UserWithOpponentUserBalanceDto copyInUsersWithOpponentUserBalanceDto(int loggedInUserId,
			UserDTO opponentUserDto, UsersBalanceEntity entity) {
		UserWithOpponentUserBalanceDto userWithOpponentUserBalanceDto = new UserWithOpponentUserBalanceDto();
		BeanUtils.copyProperties(opponentUserDto, userWithOpponentUserBalanceDto);
		userWithOpponentUserBalanceDto.setBlobId(opponentUserDto.getUserDetailDTO().getBlobId());
		if (entity != null) {
			setMappedAndMappedName(opponentUserDto.getId(), userWithOpponentUserBalanceDto, entity);
		}
		userWithOpponentUserBalanceDto.setSaiCards(opponentUserDto.getSaiCardDto());
		userWithOpponentUserBalanceDto.setUserDetail(opponentUserDto.getUserDetailDTO());
		userWithOpponentUserBalanceDto.setRoles(opponentUserDto.getRolesDto());
		userWithOpponentUserBalanceDto.setMutualBalance(getMutualBalance(loggedInUserId, opponentUserDto.getId()));
		return userWithOpponentUserBalanceDto;
	}

	public void setMappedAndMappedName(int opponentUserId,
			UserWithOpponentUserBalanceDto userWithOpponentUserBalanceDto, UsersBalanceEntity entity) {
		if (opponentUserId == entity.getUser1()) {
			if (entity.getUsersNameMapping().getUserName1() != null
					&& !entity.getUsersNameMapping().getUserName1().isEmpty()) {
				userWithOpponentUserBalanceDto.setMapped(true);
				userWithOpponentUserBalanceDto.setMappedName(entity.getUsersNameMapping().getUserName1());
			} else {
				userWithOpponentUserBalanceDto.setMapped(false);
			}
		} else {
			if (entity.getUsersNameMapping().getUserName2() != null
					&& !entity.getUsersNameMapping().getUserName2().isEmpty()) {
				userWithOpponentUserBalanceDto.setMapped(true);
				userWithOpponentUserBalanceDto.setMappedName(entity.getUsersNameMapping().getUserName2());
			} else {
				userWithOpponentUserBalanceDto.setMapped(false);
			}
		}
	}

	@Override
	public UserDTO enableDisableNotification(NotificationEnableParams params) {
		UserEntity entity = userDao.getOne(UserEntity.class, params.getUserId());
		if (entity == null) {
			return null;
		}
		entity.setNotificationEnable(params.isNotificationEnabled());
		UserDTO dto = copyInDto(entity, null);
		return dto;
	}

	@Override
	public UserWithOpponentUserBalanceDto getBalanceIfUserExist(int loggedInUserId, String opponentUserLogin) {
		UserDTO dto = getUserByLogin(opponentUserLogin);
		if (dto == null) {
			return null;
		}
		UsersBalanceEntity entity = usersBalanceDAO.getByBothUsersId(loggedInUserId, dto.getId());
		UserWithOpponentUserBalanceDto userWithOpponentUserBalanceDto = copyInUsersWithOpponentUserBalanceDto(
				loggedInUserId, dto, entity);
		return userWithOpponentUserBalanceDto;
	}

	@Override
	public UserWithOpponentUserBalanceDto getBalanceIfStandeeUserExist(int loggedInUserId, String standeeId) {
		UserDTO dto = getUserByStandee(standeeId);
		if (dto == null) {
			return null;
		}
		UsersBalanceEntity entity = usersBalanceDAO.getByBothUsersId(loggedInUserId, dto.getId());
		UserWithOpponentUserBalanceDto userWithOpponentUserBalanceDto = copyInUsersWithOpponentUserBalanceDto(
				loggedInUserId, dto, entity);
		return userWithOpponentUserBalanceDto;
	}

	@Override
	public List<UserPointsEarnedDetailDTO> getUserEarnedPointsDetail(int userId, int pageSize) {
		List<Object[]> list = userDao.fetchEarnedPointsDetail(userId, pageSize);
		if (list.isEmpty()) {
			return null;
		}
		List<UserPointsEarnedDetailDTO> dtos = new ArrayList<UserPointsEarnedDetailDTO>();
		for (Object[] obj : list) {
			UsersPointsMappingEntity upmEntity = (UsersPointsMappingEntity) obj[0];
			
			UserEntity uEntity = (UserEntity) obj[1];
			RewardPointsEntity rPointEntity=upmEntity.getRewardPointsEntity().iterator().next();
			UserPointsEarnedDetailDTO dto = new UserPointsEarnedDetailDTO();
			dto.setPointsEarned(upmEntity.getPointsEarned());
			dto.setName(uEntity.getFirstName());
			dto.setMobNumber(uEntity.getPhNumber());
			dto.setCreatedBy(upmEntity.getCreatedBy());
			dto.setCreatedOn(upmEntity.getCreatedOn());
			dto.setModifiedOn(upmEntity.getModifiedOn());
			dto.setModifiedBy(upmEntity.getModifiedBy());
			dto.setRewardSchemeName(rPointEntity.getName());
			dto.setRewardSchemeDesc(rPointEntity.getDesc());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public UserStatsReportDTO getUserStatsReport(boolean includeTestUser) {
		DashboardDataDateParams dateParams = getDashboardDateParams();
		UserStatsReportDTO dto = userDao.getUserStats(dateParams, includeTestUser);
		List<Long> counts = userDao.getActiveUsersCount(dateParams, includeTestUser);
		dto.setTodayActiveUsers(counts.get(0));
		dto.setYesterdayActiveUsers(counts.get(1));
		dto.setActiveUsersInWeekDateRange(counts.get(2));
		dto.setActiveUsersInDateRange(counts.get(3));
		dto.setTotalActiveUsers(counts.get(4));
		System.out.println("TotalActiveUsers" + counts.get(4));
		return dto;
	}

	@Transactional(readOnly = true)
	@Override
	public List<UsersListDTO> getActiveUsersList(String period, boolean testUsers) {
		List<UsersListDTO> dtoList = null;
		DashboardDataDateParams dateParams = getDashboardDateParams();
		if (period.equalsIgnoreCase("all")) {
			dtoList = userDao.getActiveUsersList(null, null, testUsers, true);
		}

		else if (period.equalsIgnoreCase("today")) {
			dtoList = userDao.getActiveUsersList(null, null, testUsers, false);
		} else if (period.equalsIgnoreCase("yest")) {
			dtoList = userDao.getActiveUsersList(dateParams.getYesterdayDate(), null, testUsers, false);
		} else if (period.equalsIgnoreCase("week")) {
			dtoList = userDao.getActiveUsersList(dateParams.getStartingDateOfWeek(), dateParams.getEndDateOfWeek(),
					testUsers, false);
		} else {
			dtoList = userDao.getActiveUsersList(dateParams.getStartingDate(), dateParams.getUptoDate(), testUsers,
					false);
		}
		return dtoList;
	}

	public List<UsersListDTO> getOnBoardedUsersService(String period, boolean includeTestUsers) {
		List<UsersListDTO> dtoList = null;
		DashboardDataDateParams dateParams = getDashboardDateParams();
		if (period.equalsIgnoreCase("today")) {
			dtoList = userDao.getOnBoardedUsers(null, null, includeTestUsers);
		} else if (period.equalsIgnoreCase("yest")) {
			dtoList = userDao.getOnBoardedUsers(dateParams.getYesterdayDate(), null, includeTestUsers);
		} else if (period.equalsIgnoreCase("week")) {
			dtoList = userDao.getOnBoardedUsers(dateParams.getStartingDateOfWeek(), dateParams.getEndDateOfWeek(),
					includeTestUsers);
		} else if (period.equalsIgnoreCase("start")) {
			dtoList = userDao.getOnBoardedUsers(dateParams.getStartingDate(), dateParams.getUptoDate(),
					includeTestUsers);
		} else {
			dtoList = userDao.getOnBoardedUsers(dateParams.getStartingDate(), DateUtils.getCurrentDateInDateObject(),
					includeTestUsers);
		}
		return dtoList;
	}

	@Override
	public List<UsersListDTO> getBlockedUsers(boolean includeTestUsers, String period) {
		List<UsersListDTO> dtoList = null;
		DashboardDataDateParams dateParams = getDashboardDateParams();
		if (period.equalsIgnoreCase("today")) {
			dtoList = userDao.getInactiveUsers(null, null, includeTestUsers);
		} else if (period.equalsIgnoreCase("yest")) {
			dtoList = userDao.getInactiveUsers(dateParams.getYesterdayDate(), null, includeTestUsers);
		} else if (period.equalsIgnoreCase("week")) {
			dtoList = userDao.getInactiveUsers(dateParams.getStartingDateOfWeek(), dateParams.getEndDateOfWeek(),
					includeTestUsers);
		} else if (period.equalsIgnoreCase("start")) {
			dtoList = userDao.getInactiveUsers(dateParams.getStartingDate(), dateParams.getUptoDate(),
					includeTestUsers);
		} else {
			dtoList = userDao.getInactiveUsers(dateParams.getStartingDate(), DateUtils.getCurrentDateInDateObject(),
					includeTestUsers);
		}
		getLogger().info(dtoList.size());
		return dtoList;
	}

	@Override
	public List<LatLngDTO> getOnBoardedUsersLatLng(String dateRange) {
		
		DashboardDataDateParams dateParams = getDashboardDateParams();
		List<LatLngDTO> dtoList =null;
		if (dateRange.equalsIgnoreCase("today")) {
			dtoList = userDao.findOnBoardedUsersLatLng(null, null);
		} else if (dateRange.equalsIgnoreCase("yest")) {
			dtoList = userDao.findOnBoardedUsersLatLng(dateParams.getYesterdayDate(), null);
		} else if (dateRange.equalsIgnoreCase("week")) {
			dtoList = userDao.findOnBoardedUsersLatLng(dateParams.getStartingDateOfWeek(), dateParams.getEndDateOfWeek());
		} else if (dateRange.equalsIgnoreCase("start")) {
			dtoList = userDao.findOnBoardedUsersLatLng(dateParams.getStartingDate(), dateParams.getUptoDate());
		} else {
			dtoList = userDao.findOnBoardedUsersLatLng(dateParams.getStartingDate(), DateUtils.getCurrentDateInDateObject());
		}
		return dtoList;

	}

	@Override
	public List<QrMappedUserDetailDTO> getQrMappedUsers() {
		List<QrMappedUserDetailDTO> list = userDao.getQrMappedUsers();

		return list;
	}

	@Override
	public List<SalesPersonReferalInfo> salesPersonReferalInfo() {

		DashboardDataDateParams dateParams = getDashboardDateParams();

		List<SalesPersonReferalInfo> spReferalInfoList = new ArrayList<SalesPersonReferalInfo>();
		List<SalesPersonReferalInfoDTO> salesInfoReferalDto = userDao.SalesInfoReferal(dateParams);
		
		
		
		for (SalesPersonReferalInfoDTO dto : salesInfoReferalDto) {
			SalesPersonReferalInfo spReferalInfo = new SalesPersonReferalInfo();
			BeanUtils.copyProperties(dto, spReferalInfo);
			if (dto.getLastSingupTime() != null) {

				spReferalInfo.setLastSingupTime(DateUtils.getTimeAgo(dto.getLastSingupTime()));

			} else {
				spReferalInfo.setLastSingupTime("Yet to start work");

			}

			if (dto.getLastSingupTimeNap() != null) {

				spReferalInfo.setLastSingupTimeNap(DateUtils.getTimeAgo(dto.getLastSingupTimeNap()));
			} else {

				spReferalInfo.setLastSingupTimeNap("Yet to start work");
			}
			
			
			// this give average count
			
			
			if(dto.getSalesPersonSignUp() !=null) {
				
				long day=DateUtils.getDayAgo(dto.getSalesPersonSignUp());
				if(day==0) {
					day=1;
				}
				
	             if(dto.getTotalCount()!=0) {
					
					long avdays=dto.getTotalCount();
					
					avdays=avdays/day;
					spReferalInfo.setSalesPersonSignUp(avdays);
					
				}
				
				}
			
			
			spReferalInfoList.add(spReferalInfo);

			if (dto.getCurrentDateCount() < 10) {

				spReferalInfo.setColorCode(colorRed);
			} else {
				spReferalInfo.setColorCode(colorGreen);

			}
		}

		return spReferalInfoList;
	}

	@Override
	public TotalReferalUserBySPDTO getTotalUsersBySP() {

		DashboardDataDateParams dateParams = getDashboardDateParams();

		TotalReferalUserBySPDTO totalReferalUser = userDao.getTotalUsersBySP(dateParams);

		return totalReferalUser;

	}

	@Override
	public List<UsersListDTO> getReferalUsersService(String period, String inviteCode, String userType) {

		List<UsersListDTO> dtoList = null;
		DashboardDataDateParams dateParams = getDashboardDateParams();
		if (period.equalsIgnoreCase("today")) {
			dtoList = userDao.getReferalUsers(null, null, inviteCode, userType);
		} else if (period.equalsIgnoreCase("yest")) {
			dtoList = userDao.getReferalUsers(dateParams.getYesterdayDate(), null, inviteCode, userType);
		} else if (period.equalsIgnoreCase("week")) {
			dtoList = userDao.getReferalUsers(dateParams.getStartingDateOfWeek(), dateParams.getEndDateOfWeek(),
					inviteCode, userType);
		} else if (period.equalsIgnoreCase("start")) {
			dtoList = userDao.getReferalUsers(dateParams.getStartingDate(), dateParams.getUptoDate(), inviteCode,
					userType);
		} else {
			dtoList = userDao.getReferalUsers(dateParams.getStartingDate(), DateUtils.getCurrentDateInDateObject(),
					inviteCode, userType);
		}
		return dtoList;

	}

	@Override
	public List<UsersListDTO> getAllBySPReferalUsersService(String period, String userType) {

		List<UsersListDTO> dtoList = null;
		DashboardDataDateParams dateParams = getDashboardDateParams();
		if (period.equalsIgnoreCase("today")) {
			dtoList = userDao.getAllBySPReferalUsersService(null, null, userType);
		} else if (period.equalsIgnoreCase("yest")) {
			dtoList = userDao.getAllBySPReferalUsersService(dateParams.getYesterdayDate(), null, userType);
		} else if (period.equalsIgnoreCase("week")) {
			dtoList = userDao.getAllBySPReferalUsersService(dateParams.getStartingDateOfWeek(),
					dateParams.getEndDateOfWeek(), userType);
		} else if (period.equalsIgnoreCase("start")) {
			dtoList = userDao.getAllBySPReferalUsersService(dateParams.getStartingDate(), dateParams.getUptoDate(),
					userType);
		} else {
			dtoList = userDao.getAllBySPReferalUsersService(dateParams.getStartingDate(),
					DateUtils.getCurrentDateInDateObject(), userType);
		}
		return dtoList;

	}

	@Override
	public List<LatLngDTO> getAllRegisteredUserBySPLatLng(String dateRange,String userType) {
		
		List<LatLngDTO> dtoList = null;
		DashboardDataDateParams dateParams = getDashboardDateParams();
		if (dateRange.equalsIgnoreCase("today")) {
			dtoList = userDao.findAllRegisteredUserBySPLatLng(null, null,userType);
		} else if (dateRange.equalsIgnoreCase("yest")) {
			dtoList = userDao.findAllRegisteredUserBySPLatLng(dateParams.getYesterdayDate(), null,userType);
		} else if (dateRange.equalsIgnoreCase("week")) {
			dtoList = userDao.findAllRegisteredUserBySPLatLng(dateParams.getStartingDateOfWeek(), dateParams.getEndDateOfWeek(),userType);
		} else if (dateRange.equalsIgnoreCase("start")) {
			dtoList = userDao.findAllRegisteredUserBySPLatLng(dateParams.getStartingDate(), dateParams.getUptoDate(),userType);
		} else {
			dtoList = userDao.findAllRegisteredUserBySPLatLng(dateParams.getStartingDate(),
					DateUtils.getCurrentDateInDateObject(),userType);
		}
		return dtoList;


	}


	@Override
	public List<UsersListDTO> getReferalUsersLatLngService(String period, String inviteCode, String userType) {

		List<UsersListDTO> dtoList = null;
		DashboardDataDateParams dateParams = getDashboardDateParams();
		if (period.equalsIgnoreCase("today")) {
			dtoList = userDao.getReferalUsersLatLng(null, null, inviteCode, userType);
		} else if (period.equalsIgnoreCase("yest")) {
			dtoList = userDao.getReferalUsersLatLng(dateParams.getYesterdayDate(), null, inviteCode, userType);
		} else if (period.equalsIgnoreCase("week")) {
			dtoList = userDao.getReferalUsersLatLng(dateParams.getStartingDateOfWeek(), dateParams.getEndDateOfWeek(),
					inviteCode, userType);
		} else if (period.equalsIgnoreCase("start")) {
			dtoList = userDao.getReferalUsersLatLng(dateParams.getStartingDate(), dateParams.getUptoDate(), inviteCode,
					userType);
		} else {
			dtoList = userDao.getReferalUsersLatLng(dateParams.getStartingDate(),
					DateUtils.getCurrentDateInDateObject(), inviteCode, userType);
		}
		return dtoList;

	}

	@Override
	public UserDTO updateUserInfoBySalesPerson(MerchantInfoBySalesPerson bySalesPersonDetail) {
		
		UserEntity userEntity=(UserEntity)userDao.getOnlyUserByUserLogin(bySalesPersonDetail.getMobileNumber());;
		if(userEntity==null) {
			return null;
		}
		
		String blobId = getShopPicFileName(bySalesPersonDetail.getFile().getOriginalFilename(),bySalesPersonDetail);

		bySalesPersonDetail.setShopBlobId(blobId);

		if (uploadFile(bySalesPersonDetail.getFile(), shopPicPath, bySalesPersonDetail.getShopBlobId())) {
			updateUserInfoBySP(bySalesPersonDetail,userEntity);
		}
		UserDTO userDTO=copyInDto(userEntity, null);
		return userDTO;
}
		private void updateUserInfoBySP(MerchantInfoBySalesPerson bySalesPersonDetail ,UserEntity userEntity) {
			userEntity.getUserDetailsEntity().setBrands(bySalesPersonDetail.getBrands());
			userEntity.getUserDetailsEntity().setEnterprise(bySalesPersonDetail.getEnterprise());
			userEntity.getUserDetailsEntity().setNatureOfBusiness(bySalesPersonDetail.getNatureOfBusiness());
			userEntity.getUserDetailsEntity().setShopBlobId(bySalesPersonDetail.getShopBlobId());
			userEntity.getUserDetailsEntity().setShopArea(bySalesPersonDetail.getShopArea());
			userEntity.getUserDetailsEntity().setInstallationStatus(bySalesPersonDetail.getInstallationStatus());
			userEntity.getUserDetailsEntity().setInstallationMedium(bySalesPersonDetail.getInstallationMedium());
			userEntity.getUserDetailsEntity().setDeviceModel(bySalesPersonDetail.getDeviceModel());
			userEntity.getUserDetailsEntity().setSalesPersonlatitude(bySalesPersonDetail.getSalesPersonlatitude());
			userEntity.getUserDetailsEntity().setSalesPersonlongitude(bySalesPersonDetail.getSalesPersonlongitude());
		}
		
		public String getShopPicFileName(String fileName,MerchantInfoBySalesPerson bySalesPersonDetail) {
			UUID uuid = UUID.randomUUID();
			String newfileName = bySalesPersonDetail.getMobileNumber()+"_"+uuid.toString() + "." + FilenameUtils.getExtension(fileName);
			return newfileName;
		}
		
		
		
		
		@Override
		public List<SalesPersonReferalInfo> getSalesPersonDataInDateRange(String startDate,String endDate) {
			
			
			

			List<SalesPersonReferalInfo> spReferalInfoList = new ArrayList<SalesPersonReferalInfo>();
			List<SalesPersonReferalInfoDTO> salesInfoReferalDto = userDao.getSalesPersonDataInDateRange(DateUtils.getDate(startDate),
					DateUtils.getDate(endDate));
			
			
			
			for (SalesPersonReferalInfoDTO dto : salesInfoReferalDto) {
				SalesPersonReferalInfo spReferalInfo = new SalesPersonReferalInfo();
				BeanUtils.copyProperties(dto, spReferalInfo);
				if (dto.getLastSingupTime() != null) {

					spReferalInfo.setLastSingupTime(DateUtils.getTimeAgo(dto.getLastSingupTime()));

				} else {
					spReferalInfo.setLastSingupTime("Yet to start work");

				}

				if (dto.getLastSingupTimeNap() != null) {

					spReferalInfo.setLastSingupTimeNap(DateUtils.getTimeAgo(dto.getLastSingupTimeNap()));
				} else {

					spReferalInfo.setLastSingupTimeNap("Yet to start work");
				}
				
				
				// this give average count
				
				
				if(dto.getSalesPersonSignUp() !=null) {
					
					long day=DateUtils.getDayAgo(dto.getSalesPersonSignUp());
					
					if(day==0) {
						day=1;
					}
		             if(dto.getTotalCount()!=0) {
						
						long avdays=dto.getTotalCount();
						
						avdays=avdays/day;
						spReferalInfo.setSalesPersonSignUp(avdays);
						
					}
					
					}
				spReferalInfoList.add(spReferalInfo);
				
			}

			return spReferalInfoList;
		}
		
		
		
		
		
		@Override
		public List<UsersListDTO>  getReferalUsersInDateRangeService(String startDate,String endDate, String inviteCode,String userType) {

			List<UsersListDTO> dtoList = null;
			
			
				dtoList = userDao.getReferalUsersInDateRange(DateUtils.getDate(startDate),DateUtils.getDate(endDate) , inviteCode, userType);
		
			return dtoList;

		}
		
		
		
		
		@Override
		public List<UsersListDTO>getIndivualfilterreferaluserLatLngService(String startDate,String endDate, String inviteCode,String userType) {

			List<UsersListDTO> dtoList = null;
			
				dtoList = userDao.getIndivualfilterreferaluserLatLng(DateUtils.getDate(startDate),DateUtils.getDate(endDate) , inviteCode, userType);
			
			return dtoList;

		}
		
		
		
		@Override
		public List<MerchantInfoBySPDTO> merchantInfoBySalesPerson(String userId) {

			

			List<MerchantInfoBySPDTO> merchantInfo = userDao.merchantInfoBySalesPerson(userId);
			
			
			for(MerchantInfoBySPDTO dto:merchantInfo) {
				if(dto.getShopBlobId()!=null && !dto.getShopBlobId().isEmpty()) {
					dto.setShopBlobId(shopPic+dto.getShopBlobId());
				}
				
			}

			return merchantInfo;

		}
		
		
		@Override
		public List<SalesInfoDTO> salesPersonInfo() {


			List<SalesInfoDTO> salesPersonInfo = userDao.salesPersonInfo();

			return salesPersonInfo;

		}
		
		
		
		
		@Override
		public List<LatLngDTO> getAllMerchantbyIndiviualspmap(int userId) {

			 
			
			List<LatLngDTO> dtoList= userDao.getAllMerchantbyIndiviualspmap(userId);
			
			return dtoList;

		}
	
		@Override
		public void setIsMerchant(int userId) {
			UserEntity user=userDao.getUserById(userId);
			if(!user.isMerchant()) {
				user.setMerchant(true);
			}
			
		}
		@Override
		 public List<LatLngDTO> getBlupayUsersLatLngService( String period, String startDate,  String endDate){
			
			DashboardDataDateParams dateParams = getDashboardDateParams();
			
			List<LatLngDTO> dtoList= userDao.getBlupayUsersLatLng(period,DateUtils.getDate(startDate),DateUtils.getDate(endDate),DateUtils.getStartDateOfCurrWeek(),DateUtils.getCurrentMonth());
			
			return dtoList; 
		 }
		
		
		
		
}
