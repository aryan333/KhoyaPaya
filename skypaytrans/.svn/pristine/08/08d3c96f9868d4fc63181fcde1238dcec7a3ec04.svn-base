package com.saifintex.web.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.saifintex.entity.BaseEntity;
import com.saifintex.entity.RolesEntity;
import com.saifintex.entity.UserDetailsEntity;

@Entity
@Table(name = "WebUsers")
public class WebUsersEntity extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5550726408331122357L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "WebUserId")
	private int webUserId;

	@Column(name = "FirstName", length = 40)
	private String firstName;

	@Column(name = "LastName", length = 20)
	private String lastName;

	@Column(name = "UserLogin", length = 30)
	private String userLogin;

	@Column(name = "Password", length = 100)
	private String password;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "UserDetailsId")
	private UserDetailsEntity userDetailsEntity;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "WebUsers_Roles", joinColumns = { @JoinColumn(name = "WebUserId") }, inverseJoinColumns = {
			@JoinColumn(name = "RoleId") })
	private List<RolesEntity> roles;

	@Column(name = "IsTestUser")
	private boolean isTestUser;

	private boolean isEnabled;
	private boolean isAccountNonExpired;
	private boolean isCredentialsNonExpired;
	private boolean isAccountNonLocked;
	
	@Column(name="LastResetPasswordDate")
	private Date lastResetPasswordDate;

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

	public UserDetailsEntity getUserDetailsEntity() {
		return userDetailsEntity;
	}

	public void setUserDetailsEntity(UserDetailsEntity userDetailsEntity) {
		this.userDetailsEntity = userDetailsEntity;
	}

	public List<RolesEntity> getRoles() {
		return roles;
	}

	public void setRoles(List<RolesEntity> roles) {
		this.roles = roles;
	}

	public boolean isTestUser() {
		return isTestUser;
	}

	public void setTestUser(boolean isTestUser) {
		this.isTestUser = isTestUser;
	}

	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	
	public boolean isEnabled() {
		return isEnabled;
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
		this.isAccountNonLocked = isAccounNonLocked;
	}

	public Date getLastResetPasswordDate() {
		return lastResetPasswordDate;
	}

	public void setLastResetPasswordDate(Date lastResetPasswordDate) {
		this.lastResetPasswordDate = lastResetPasswordDate;
	}

	
}
