package com.saifintex.services;

import com.saifintex.web.domain.WebUsers;
import com.saifintex.web.dto.WebUsersDTO;

public interface WebUsersService {
	
	public int createWebUserService(WebUsersDTO webUsersDTO);	
	
	public boolean updateWebUserService(WebUsers user);
	
	public WebUsersDTO getWebUserByIdService(int id);
	
	public WebUsersDTO getWebUserByUserLoginService(String userLogin);
	
	public void updatePasswordService(String newPass,String token);
	
	public int updatePasswordService(WebUsersDTO dto,String newPass,String currentPassword);	
	
}
