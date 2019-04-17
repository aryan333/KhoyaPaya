package com.saifintex.services.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saifintex.common.AbstractBase;
import com.saifintex.dao.PasswordResetDAO;
import com.saifintex.services.PasswordResetService;
import com.saifintex.utils.DateUtils;
import com.saifintex.utils.EmailThread;
import com.saifintex.utils.EmailUtils;
import com.saifintex.utils.ThreadUtils;
import com.saifintex.web.dto.PasswordResetTokenDTO;
import com.saifintex.web.dto.WebUsersDTO;
import com.saifintex.web.entity.PasswordResetTokenEntity;

@Service
@Transactional
public class PasswordResetServiceImpl extends AbstractBase implements PasswordResetService {

	@Autowired
	private PasswordResetDAO passDao;

	@Value("${domain.server}")
	private String serverdomain;

	@Value("${email.password.reset.subject}")
	String subject;

	@Override
	public int sendEmailWithToken(WebUsersDTO userDto, String contextPath, String email) {
		int uId = userDto.getWebUserId();
		String token = generateToken();
		PasswordResetTokenEntity oldEntity = checkIfTokenAlreadyExist(uId);
		if (oldEntity != null) {	
			int res = updateToken(oldEntity, token, userDto);
			if(res!=0) {
				invokeThreadToSendEmail(email, subject, constructEmailBody(token, contextPath));
			}
			return res;
		}
		PasswordResetTokenEntity newEntity = new PasswordResetTokenEntity();
		Date d = DateUtils.getCurrentDateTime();
		newEntity.setToken(token);
		newEntity.setWebUsersEntity(copyWebUserDtoInEntity(userDto));
		newEntity.setCreatedOn(d);
		newEntity.setCreatedBy(uId);
		newEntity.setModifiedOn(d);
		newEntity.setModifiedBy(uId);
		int id = passDao.save(newEntity);
		if (id != 0) {
			invokeThreadToSendEmail(email, subject, constructEmailBody(token, contextPath));
			return id;
		}
		return id;
	}
	
	private void invokeThreadToSendEmail(String email,String subject,String text) {
		EmailThread emailThread = new EmailThread(email,subject,text);		
		ThreadUtils.start(emailThread);
	}

	private PasswordResetTokenEntity checkIfTokenAlreadyExist(int id) {
		PasswordResetTokenEntity entity = passDao.getEntityByUserId(id);
		return entity;
	}

	private String generateToken() {
		String token = UUID.randomUUID().toString();
		return token;
	}

	private int updateToken(PasswordResetTokenEntity entity, String token, WebUsersDTO userDto) {
		Date d = DateUtils.getCurrentDateTime();
		entity.setToken(token);
		entity.setModifiedOn(d);
		entity.setModifiedBy(userDto.getWebUserId());
		getLogger().info("token updated to reset password!");
		return passDao.save(entity);
	}

	private String constructEmailBody(String token, String contextPath) {
		String passResetLink = serverdomain + contextPath + "/admin/forgotPasswordToken?t=" + token;
		return passResetLink;
	}

	@Override
	public String validateTokenService(String token) {
		PasswordResetTokenEntity entity = passDao.getEntityByToken(token);
		if (entity == null) {
			return "invalid token";
		}
		if (checkIfTokenExpired(entity)) {
			return "Sorry !token expired";
		}
		PasswordResetTokenDTO dto = new PasswordResetTokenDTO();
		BeanUtils.copyProperties(entity, dto);
		WebUsersDTO webUsersDTO = copyWebUserEntityInDto(entity.getWebUsersEntity());
		dto.setWebUsersDTO(webUsersDTO);
		Authentication auth = new UsernamePasswordAuthenticationToken(webUsersDTO, null,
				Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
		SecurityContextHolder.getContext().setAuthentication(auth);
		return null;
	}

	private boolean checkIfTokenExpired(PasswordResetTokenEntity entity) {
		Calendar cal = Calendar.getInstance();
		if ((cal.getTime().getTime() - entity.getCreatedOn().getTime()) >= 86400000) {
			return true;
		}
		return false;
	}
}
