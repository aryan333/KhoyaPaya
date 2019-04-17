package com.saifintex.jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.saifintex.dto.RolesDTO;
import com.saifintex.dto.UserDTO;

public final class JwtUserFactory {

	private JwtUserFactory() {
	}

	public static JwtUser create(UserDTO user) {
		return new JwtUser(user.getId(), user.getPhNumber(), user.getFirstName(), user.getPassword(),
				mapToGrantedAuthorities(user.getRolesDto()), user.getLastPasswordResetDate(), user.isEnabled(),
				user.isAccountNonLocked(), user.isAccountNonExpired(), user.isCredentialsNonExpired());
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(Set<RolesDTO> authorities) {

		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		authorities.forEach(role -> {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getId() + ""));
		});
		return grantedAuthorities;
	}
}
