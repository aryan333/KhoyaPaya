package com.saifintex.controller.rest;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.saifintex.common.AbstractBase;
import com.saifintex.exception.UserDoesNotExistException;

@ControllerAdvice("com.saifintex.controller.rest")
public class GlobalExceptionHandlerController extends AbstractBase {

	@ExceptionHandler(value = org.hibernate.exception.ConstraintViolationException.class)
	public ResponseEntity<Map<String, String>> duplicateEntryExceptionHandler(
			org.hibernate.exception.ConstraintViolationException e) {
		getLogger().error("duplicate data entry !! ", e.fillInStackTrace());
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("response", "internal server error");
		return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = SocketTimeoutException.class)
	public ResponseEntity<Map<String, String>> handleNoConnectionExceptoin(SocketTimeoutException e) {
		getLogger().error("Client Abort Exception ! Socket Timeout Exception. !!", e);
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("response", "Problem in establishing connection with server");
		return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.REQUEST_TIMEOUT);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Map<String, String>> genericExceptionHandler(Exception e) {
		getLogger().error("something went wrong !!", e);
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("response", "internal server error");
		return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = BadCredentialsException.class)
	public ResponseEntity<Map<String, String>> badCredentials(BadCredentialsException e) {
		getLogger().error("Bad credentials");
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("response", "Bad Credentials");
		return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value=LockedException.class)
	public ResponseEntity<Map<String, String>> UserAccountLockedHanlder(LockedException e) {
		getLogger().error("User account is locked");
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("response", "User account is locked");
		return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value=DisabledException.class)
	public ResponseEntity<Map<String, String>> UserAccountDisabledHanlder(DisabledException e) {
		getLogger().error("User is disabled");
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("response", "User is disabled");
		return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value=CredentialsExpiredException.class)
	public ResponseEntity<Map<String, String>> UserAccountCredentialsExpiredHanlder(CredentialsExpiredException e) {
		getLogger().error("User credentials have expired");
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("response", "User credentials have expired");
		return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value=AccountExpiredException.class)
	public ResponseEntity<Map<String, String>> UserAccountExpiredHanlder(AccountExpiredException e) {
		getLogger().error("User account has expired");
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("response", "User account has expired");
		return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.UNAUTHORIZED);
	}
	
	/*@ExceptionHandler(value=UnauthorizedException.class)
	public ResponseEntity<Map<String, String>> UserTestHanlder(UnauthorizedException e) {
		getLogger().error("User account unauthorized");
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("response", "User account unauthorized");
		return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.UNAUTHORIZED);
	}*/
	
	@ExceptionHandler(value = UserDoesNotExistException.class)
	public ResponseEntity<Map<String, String>> userDoesNotExistException(Exception e) {
		getLogger().error("UserDoesNotExistException !!", e);
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("response", "User does not exist");
		return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value=UsernameNotFoundException.class)
	public ResponseEntity<Map<String,String>> usernamenotfoundhandler(UsernameNotFoundException e){
		getLogger().error("username(user login number) not found in database");
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("response", "oops!Some error occured. please do login again.");
		return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
