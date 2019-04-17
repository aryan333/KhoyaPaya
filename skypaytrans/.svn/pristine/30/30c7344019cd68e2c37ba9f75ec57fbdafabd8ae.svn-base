package com.saifintex.controller.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.saifintex.common.AbstractBase;
import com.saifintex.domain.JwtAuthenticationRefreshResponse;
import com.saifintex.domain.JwtAuthenticationResponse;
import com.saifintex.jwt.JwtAuthenticationRequest;
import com.saifintex.jwt.JwtTokenUtil;
import com.saifintex.jwt.JwtUser;


@RestController
public class AuthenticationController extends AbstractBase {
	

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
			throws AuthenticationException {
		getLogger().info("Authintication Controller - - - - Auth");
		// Perform the security
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
						authenticationRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Reload password post-security so we can generate token
		UserDetails userDetails=null;
		
	     userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		if(userDetails==null) {
			getLogger().info("in authitactions hello");
			return new ResponseEntity<String>("Bad Credentials",HttpStatus.BAD_REQUEST);
		}
		final String token = jwtTokenUtil.generateToken(userDetails);
		final String refreshToken = jwtTokenUtil.generateRereshToken(userDetails);
		
		getLogger().info("generated token for client id "+authenticationRequest.getUsername()+"  is == "+token);
		// Return the token
		return ResponseEntity.ok(new JwtAuthenticationResponse(token,refreshToken));
	}

	@RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
		getLogger().info("Authintication Controller - - - - Refresh");
		String refreshToken = request.getHeader(tokenHeader);
		String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
		JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

		if (jwtTokenUtil.canTokenBeRefreshed(refreshToken, user.getLastPasswordResetDate())) {
			
			final String authToken=jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(username));
			return ResponseEntity.ok(new JwtAuthenticationRefreshResponse(authToken));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@RequestMapping(value = "user", method = RequestMethod.GET)
	public JwtUser getAuthenticatedUser(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
		return user;
	}
}
