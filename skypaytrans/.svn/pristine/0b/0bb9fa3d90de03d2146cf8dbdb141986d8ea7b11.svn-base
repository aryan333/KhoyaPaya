package com.saifintex.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saifintex.common.AbstractBase;
import com.saifintex.dao.WebUsersDAO;
import com.saifintex.dto.RolesDTO;
import com.saifintex.dto.UserDTO;
import com.saifintex.dto.UserDetailDTO;
import com.saifintex.entity.RolesEntity;
import com.saifintex.jwt.JwtUserFactory;
import com.saifintex.services.UserService;
import com.saifintex.web.dto.WebUsersDTO;
import com.saifintex.web.entity.WebUsersEntity;

@Service
@Transactional
public class JwtUserDetailsServiceImpl extends AbstractBase implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Autowired
	WebUsersDAO webUsersDao;

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {				
    	if(!username.contains("@")) {     // to check if username associated with admin panel login
    		UserDTO user = userService.getUserByLogin(username);
            if (user == null) {
               throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));            	
            }    
            else {            	
                return JwtUserFactory.create(user);
            }
    	}
    //	boolean enabled = true, accountNonExpired = true, credentialsNonExpired = true, accountNonLocked = true;
		WebUsersEntity webUser = webUsersDao.getAdmin(username);
		if(webUser == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		}
		
		return copyInDto(webUser);
    }

	private WebUsersDTO copyInDto(WebUsersEntity webUsersEntity) {
		UserDetailDTO udDto = new UserDetailDTO();
		BeanUtils.copyProperties(webUsersEntity.getUserDetailsEntity(), udDto);
		List<RolesDTO> rolesDtos = new ArrayList<RolesDTO>();
		List<RolesEntity> rEntityList = webUsersEntity.getRoles();
		for (RolesEntity roles : rEntityList) {
			RolesDTO dto = new RolesDTO();
			BeanUtils.copyProperties(roles, dto);
			rolesDtos.add(dto);
		}
		WebUsersDTO userDto = new WebUsersDTO(webUsersEntity.getWebUserId(), webUsersEntity.getFirstName(),
				webUsersEntity.getLastName(), webUsersEntity.getUserLogin(), webUsersEntity.getPassword(), udDto,
				rolesDtos, webUsersEntity.isTestUser(), webUsersEntity.isEnabled(),
				webUsersEntity.isAccountNonExpired(), webUsersEntity.isCredentialsNonExpired(),
				webUsersEntity.isAccountNonLocked(), getAuthorities(webUsersEntity.getRoles()));
		return userDto;
	}

	private List<GrantedAuthority> getAuthorities(List<RolesEntity> rolesEntity) {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		for (RolesEntity roles : rolesEntity) {
			list.add(new SimpleGrantedAuthority("ROLE_" + roles.getId()));
		}
		return list;
	}

}
