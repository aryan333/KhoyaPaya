package com.saifintex.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.saifintex.dao.UsersOTPDAO;
import com.saifintex.dto.UserOTPDTO;
import com.saifintex.entity.UserOTPEntity;
import com.saifintex.services.UsersOTPService;
import com.saifintex.utils.DateUtils;
import com.saifintex.utils.FcmNotificationUtil;
import com.saifintex.utils.OtpSMSThread;
import com.saifintex.utils.ResponseCode;
import com.saifintex.utils.StringUtils;
import com.saifintex.utils.ThreadUtils;
import com.saifintex.web.domain.RemainingOTPDomain;

@Service
@Transactional
public class UsersOTPServiceImpl implements UsersOTPService {

	@Autowired
	private UsersOTPDAO usersOTPDao;
	@Value("${otp.sms}")
	private String otpSms;

	@Value("${otp.tries.limit}")
	private int maxOtpTried;

	@Value("${otp.sms.unblock.time}")
	private long smsUnblockHours;

	@Value("${otp.sms.send.limit}")
	private int maxOtpSendLimit;
	
	@Value("${otp.expire.time}")
	public long otpExpireTime;
	
	@Value("#{'${testing.mobile.numbers}'.split(',')}")
	private List<String> testingNumberList;
	
	@Value("${otp.send.enable}")
	
	private boolean isSendEnabled;

	@Override
	public Object generateOTP(String mobileNumber) {
		UserOTPEntity entity = usersOTPDao.getOtpByMobileNo(mobileNumber);
		if (entity != null) {
        
			if (entity.getOtpBlockedDateTime() != null) {
				if (getBlockedDuration(entity.getOtpBlockedDateTime()) >= smsUnblockHours) {
					entity.setOtpBlockedDateTime(null);
					entity.setNumberOfOTPTried(0);

				} else {
					System.out.println("esle part " + getBlockedDuration(entity.getOtpBlockedDateTime()));
					return ResponseCode.TOO_MANY_OTP_TRIED;
				}
			} else if (entity.getOtpSendBlockedDateTime() != null) {
				if (getBlockedDuration(entity.getOtpSendBlockedDateTime()) >= smsUnblockHours) {
					entity.setOtpSendBlockedDateTime(null);
					entity.setTodayOTPCount(0);
					

				} else {
					return ResponseCode.TOO_MANY_OTP_SEND;
				}
			}
			if (entity.getTodayOTPCount() > maxOtpSendLimit) {
				entity.setOtpSendBlockedDateTime(DateUtils.getCurrentDateTime());
				return ResponseCode.TOO_MANY_OTP_SEND;

			}
			return updateOtpEntity(entity);
		}
		UserOTPEntity newUserOtpEntity = createOTPEntity(mobileNumber);
		newUserOtpEntity = usersOTPDao.saveAndGet(newUserOtpEntity);
		UserOTPDTO dto = new UserOTPDTO();
		BeanUtils.copyProperties(newUserOtpEntity, dto);
		String message = otpSms + " " + dto.getGeneratedOTP();
		System.out.println("isSendEnablesddddd--"+isSendEnabled);
		if(isSendEnabled) {
		OtpSMSThread otpSMSThread = new OtpSMSThread(dto.getMobileNumber(), message);
		ThreadUtils.start(otpSMSThread);
		}
		return dto;
	}

	@Override
	public Object verifyOTP(String mobileNumber, String otp) {

		UserOTPEntity otpEntity = usersOTPDao.getOtpByMobileNo(mobileNumber);
		
		if (otpEntity == null) {
			return false;
		}
		Object result = validateOTP(otpEntity, otp);
		return result;
	}

	private UserOTPEntity createOTPEntity(String mobileNumber) {
		UserOTPEntity userOTPEntity = new UserOTPEntity();
		userOTPEntity.setMobileNumber(mobileNumber);
		if(testingNumberList.contains(mobileNumber)) {
			userOTPEntity.setGeneratedOTP(101010);
		}else {
		userOTPEntity.setGeneratedOTP(StringUtils.generateSixDigitRandomNumber());
		}
		userOTPEntity.setNumberOfOTPTried(0);
		userOTPEntity.setLastOTPGenDate(DateUtils.getCurrentDateTime());
		userOTPEntity.setCreatedOn(DateUtils.getCurrentDateTime());
		userOTPEntity.setCreatedBy(1);
		userOTPEntity.setModifiedBy(1);
		userOTPEntity.setModifiedOn(DateUtils.getCurrentDateTime());
		userOTPEntity.setTodayOTPCount(1);
		userOTPEntity.setTotalOTPCount(1);
		userOTPEntity.setOtpExpired(false);
		userOTPEntity.setNumberOfOTPTried(0);
		return userOTPEntity;

	}

	private UserOTPDTO updateOtpEntity(UserOTPEntity entity) {
		if(testingNumberList.contains(entity.getMobileNumber())) {
		entity.setGeneratedOTP(101010);
		}else {
			entity.setGeneratedOTP(StringUtils.generateSixDigitRandomNumber());
		}
		entity.setModifiedBy(1);
		entity.setModifiedOn(DateUtils.getCurrentDateTime());
		entity.setLastOTPGenDate(DateUtils.getCurrentDateTime());
		entity.setTodayOTPCount(entity.getTodayOTPCount() + 1);
		entity.setTotalOTPCount(entity.getTotalOTPCount() + 1);
		entity.setOtpExpired(false);
		entity.setNumberOfOTPTried(0);
		UserOTPDTO dto = new UserOTPDTO();
		BeanUtils.copyProperties(entity, dto);
		String message = otpSms + " " + entity.getGeneratedOTP();
		if(isSendEnabled) {
		OtpSMSThread otpSMSThread = new OtpSMSThread(entity.getMobileNumber(), message);
		ThreadUtils.start(otpSMSThread);
		}
		return dto;

	}

	private long getBlockedDuration(Date paymentDate) {
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

	private Object validateOTP(UserOTPEntity entity, String otp) {
		
		if(getBlockedDuration(entity.getLastOTPGenDate())>=otpExpireTime) {
			return ResponseCode.OTP_EXPIRED;
		}
		if (entity.isOtpExpired()) {
			return ResponseCode.OTP_EXPIRED;
		}
		if (entity.getOtpBlockedDateTime() != null) {
			if (getBlockedDuration(entity.getOtpBlockedDateTime()) >= smsUnblockHours) {
				entity.setOtpBlockedDateTime(null);
				entity.setNumberOfOTPTried(1);

			} else {
				
				return ResponseCode.OTP_BLOCKED;
			}
		} else if (entity.getNumberOfOTPTried() > maxOtpTried) {
			entity.setOtpBlockedDateTime(DateUtils.getCurrentDateTime());

			return ResponseCode.OTP_BLOCKED;
		} else if (entity.getGeneratedOTP() == Integer.parseInt(otp)) {
			entity.setOtpExpired(true);
			entity.setNumberOfOTPTried(0);
			entity.setTodayOTPCount(0);
			return true;
		}
		entity.setNumberOfOTPTried(entity.getNumberOfOTPTried() + 1);
		return false;

	}

	@Override
	public long getRemainigOTPs() {
		return getRemainingOTPFromServiceProvider();
	
	}
	
	
	private long getRemainingOTPFromServiceProvider() {
		return FcmNotificationUtil.getRemainingOTPFromServiceProvider();
		
		
	}

	@Override
	public UserOTPDTO getUserOtpByMobileNumber(String mobileNumber) {
		UserOTPEntity userOTPEntity=usersOTPDao.getOtpByMobileNo(mobileNumber);
		if(userOTPEntity==null) {
			return null;
		}
		UserOTPDTO userOTPDTO=new UserOTPDTO();
		BeanUtils.copyProperties(userOTPEntity, userOTPDTO);
		return userOTPDTO;
		
	}
}
