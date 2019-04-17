package com.saifintex.jwt;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.saifintex.common.AbstractBase;

/**
 * Created by stephan on 20.03.16.
 */
public class JwtUser extends AbstractBase implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int id;
	private final String username;
	private final String firstname;
	private final String password;
	private final Collection<? extends GrantedAuthority> authorities;
	private final Date lastPasswordResetDate;
	private final boolean enabled;
	private final boolean isAccountNonLocked;
	private final boolean isAccountNonExpired;
	private final boolean isCredentialsNonExpired;

	public JwtUser(int id, String username, String firstname, String password,
			Collection<? extends GrantedAuthority> authorities, Date lastPasswordResetDate, boolean enabled,
			boolean isAccountNonLocked, boolean isAccountNonExpired, boolean isCredentialsNonExpired) {
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.password = password;
		this.authorities = authorities;
		this.lastPasswordResetDate = lastPasswordResetDate;
		this.enabled = enabled;
		this.isAccountNonLocked = isAccountNonLocked;
		this.isAccountNonExpired = isAccountNonExpired;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	
	public int getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	
	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	
	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	
	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	public String getFirstname() {
		return firstname;
	}

	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	
	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}
}
