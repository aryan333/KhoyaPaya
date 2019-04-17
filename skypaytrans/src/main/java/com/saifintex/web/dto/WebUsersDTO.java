package com.saifintex.web.dto;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.saifintex.dto.BaseDto;
import com.saifintex.dto.RolesDTO;
import com.saifintex.dto.UserDetailDTO;

public class WebUsersDTO extends BaseDto implements UserDetails{

	private int webUserId;
	private String firstName;
	private String lastName;
	private String userLogin;
	private String password;
	private UserDetailDTO userDetailDTO;
	private List<RolesDTO> rolesDTO;
	private boolean isTestUser;
	private boolean isEnabled;
	private boolean isAccountNonExpired;
	private boolean isCredentialsNonExpired;
	private boolean isAccounNonLocked;
	private Date lastResetPasswordDate;
	private List<GrantedAuthority> grantedAuthorities;

	public WebUsersDTO() {

	}
	
	public WebUsersDTO(int webUserId, String firstName, String lastName, String userLogin, String password,
			UserDetailDTO userDetailDTO, List<RolesDTO> rolesDTO, boolean isTestUser, boolean isEnabled,
			boolean isAccountNonExpired, boolean isCredentialsNonExpired, boolean isAccounNonLocked,
			List<GrantedAuthority> grantedAuthorities) {
		super();
		this.webUserId = webUserId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userLogin = userLogin;
		this.password = password;
		this.userDetailDTO = userDetailDTO;
		this.rolesDTO = rolesDTO;
		this.isTestUser = isTestUser;
		this.isEnabled = isEnabled;
		this.isAccountNonExpired = isAccountNonExpired;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
		this.isAccounNonLocked = isAccounNonLocked;
		this.grantedAuthorities = grantedAuthorities;
	}

	public int getWebUserId() {
		return webUserId;
	}

	public void setWebUserId(int webUserId) {
		this.webUserId = webUserId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserDetailDTO getUserDetailDTO() {
		return userDetailDTO;
	}

	public void setUserDetailDTO(UserDetailDTO userDetailDTO) {
		this.userDetailDTO = userDetailDTO;
	}

	public List<RolesDTO> getRolesDTO() {
		return rolesDTO;
	}

	public void setRolesDTO(List<RolesDTO> rolesDTO) {
		this.rolesDTO = rolesDTO;
	}

	public boolean isTestUser() {
		return isTestUser;
	}

	public void setTestUser(boolean isTestUser) {
		this.isTestUser = isTestUser;
	}	
	
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}

	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	public void setAccounNonLocked(boolean isAccounNonLocked) {
		this.isAccounNonLocked = isAccounNonLocked;
	}

	public void setGrantedAuthorities(List<GrantedAuthority> grantedAuthorities) {
		this.grantedAuthorities = grantedAuthorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return grantedAuthorities;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userLogin;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return isAccounNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return isEnabled;
	}

	public Date getLastResetPasswordDate() {
		return lastResetPasswordDate;
	}

	public void setLastResetPasswordDate(Date lastResetPasswordDate) {
		this.lastResetPasswordDate = lastResetPasswordDate;
	}

	
}
