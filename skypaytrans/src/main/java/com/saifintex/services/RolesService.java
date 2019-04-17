package com.saifintex.services;

import java.util.List;

import com.saifintex.dto.RolesDTO;
import com.saifintex.web.dto.WebUsersDTO;

public interface RolesService {
	
	public List<RolesDTO> findAll();
	
	public RolesDTO addRole(RolesDTO rolesDTO,WebUsersDTO usersDTO);

}
