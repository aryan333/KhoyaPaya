package com.saifintex.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.saifintex.common.AbstractBase;
import com.saifintex.dao.PasswordResetDAO;
import com.saifintex.dao.RolesDAO;
import com.saifintex.dao.WebUsersDAO;
import com.saifintex.entity.RolesEntity;
import com.saifintex.entity.UserDetailsEntity;
import com.saifintex.services.WebUsersService;
import com.saifintex.utils.DateUtils;
import com.saifintex.utils.ResponseCode;
import com.saifintex.web.domain.WebUsers;
import com.saifintex.web.dto.WebUsersDTO;
import com.saifintex.web.entity.WebUsersEntity;

@Transactional
@Service
public class WebUsersServiceImpl extends AbstractBase implements WebUsersService {

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	WebUsersDAO webUsersDao;
	
	@Autowired
	RolesDAO rolesDao;
	
	@Autowired
	PasswordResetDAO passDao;
	
	@Override
	public int createWebUserService(WebUsersDTO webUsersDTO) {
		WebUsersEntity entity = copyWebUserDtoInEntity(webUsersDTO);
		Date date = DateUtils.getCurrentDateTime();
		entity.setPassword(passwordEncoder.encode(webUsersDTO.getPassword()));
		entity.setUserLogin(webUsersDTO.getUserDetailDTO().getEmailId());		
		entity.setTestUser(true);
		entity.setCreatedOn(date);
		entity.setCreatedBy(1);
		entity.setModifiedOn(date);
		entity.setModifiedBy(1);
		entity.setEnabled(true);
		entity.setAccounNonLocked(true);
		entity.setAccountNonExpired(true);
		entity.setCredentialsNonExpired(true);
		entity.setLastResetPasswordDate(date);
		UserDetailsEntity udEntity = entity.getUserDetailsEntity();
		udEntity.setStarValue(new BigDecimal(1.00));
		udEntity.setCreatedBy(1);
		udEntity.setCreatedOn(date);
		udEntity.setModifiedBy(1);
		udEntity.setModifiedOn(date);
		int id = createWebUser(entity);
		return id;
	}

	private int createWebUser(WebUsersEntity webUsersEntity) {
		int roleId = webUsersEntity.getRoles().iterator().next().getId();
		RolesEntity roleEntity = rolesDao.getOne(RolesEntity.class, roleId);
		List<RolesEntity> rolesEntityList = new ArrayList<RolesEntity>();
		rolesEntityList.add(roleEntity);		
		webUsersEntity.setRoles(rolesEntityList);
		int id = webUsersDao.save(webUsersEntity);
		return id;
		
	}
	
	@Override
	public WebUsersDTO getWebUserByIdService(int id) {
		WebUsersEntity entity = webUsersDao.getOne(WebUsersEntity.class, id);		
		return copyWebUserEntityInDto(entity);
	}

	@Override
	public WebUsersDTO getWebUserByUserLoginService(String userLogin) {
		WebUsersEntity entity = webUsersDao.findByUserLoginWeb(userLogin);	
		if(entity==null) {
			return null;
		}
		return copyWebUserEntityInDto(entity);
	}
	
	/*private WebUsersDTO copyInDto(WebUsersEntity entity) {
		WebUsersDTO wuDto = new WebUsersDTO();
		BeanUtils.copyProperties(entity, wuDto);
		UserDetailDTO udDto = new UserDetailDTO();
		UserDetailsEntity udEntity = entity.getUserDetailsEntity();
		BeanUtils.copyProperties(udEntity, udDto);
		wuDto.setUserDetailDTO(udDto);
		List<RolesDTO> list = new ArrayList<RolesDTO>();
		for (RolesEntity roles : entity.getRoles()) {
			RolesDTO dto = new RolesDTO();
			BeanUtils.copyProperties(roles, dto);
			list.add(dto);
		}
		wuDto.setRolesDTO(list);
		return wuDto;
	}*/

	
	@Override
	public boolean updateWebUserService(WebUsers user) {
		getLogger().info("update web users");
		return updateWebUserData(user);
	}
	
	private boolean updateWebUserData(WebUsers user) {
		getLogger().info("id == "+user.getWebUserId());
		WebUsersEntity entity = webUsersDao.getOne(WebUsersEntity.class, user.getWebUserId());
		UserDetailsEntity udEntity = entity.getUserDetailsEntity();
		udEntity.setEmailId(user.getUserDetail().getEmailId());
		udEntity.setAddress1(user.getUserDetail().getAddress1());
		udEntity.setGender(user.getUserDetail().getGender());
		Date d = DateUtils.getCurrentDateTime();
		udEntity.setModifiedOn(d);
		udEntity.setModifiedBy(user.getWebUserId());
		entity.setFirstName(user.getFirstName());
		entity.setLastName(user.getLastName());
		entity.setModifiedOn(d);
		entity.setModifiedBy(user.getWebUserId());
		entity.setUserDetailsEntity(udEntity);
		webUsersDao.saveOrUpdate(entity);
		return true;
	}

	@Override
	public void updatePasswordService(String newPass,String token) {
		WebUsersDTO userDto = (WebUsersDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		WebUsersEntity entity = webUsersDao.getOne(WebUsersEntity.class, userDto.getWebUserId());
		entity.setPassword(passwordEncoder.encode(newPass));
		Date d = DateUtils.getCurrentDateTime();
		entity.setLastResetPasswordDate(d);
		entity.setModifiedOn(d);
		entity.setModifiedBy(userDto.getWebUserId());
		webUsersDao.saveOrUpdate(entity);	
		passDao.deleteByToken(token);
	}

	@Override
	public int updatePasswordService(WebUsersDTO dto, String newPass,String currentPassword) {
		getLogger().info("user id == "+dto.getWebUserId());	
		if(!passwordEncoder.matches(currentPassword, dto.getPassword())) {			
			getLogger().info("password not valid ");
			return ResponseCode.OLD_PASSWORD_DOES_NOT_MATCH;
		}		
		WebUsersEntity entity = webUsersDao.getOne(WebUsersEntity.class, dto.getWebUserId());
		entity.setPassword(passwordEncoder.encode(newPass));
		Date date = DateUtils.getCurrentDateTime();
		entity.setLastResetPasswordDate(date);
		entity.setModifiedBy(dto.getWebUserId());
		entity.setModifiedOn(date);
		return ResponseCode.UPDATED_IN_DB;
	}

}
