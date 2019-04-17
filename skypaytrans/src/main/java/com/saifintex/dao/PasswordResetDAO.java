package com.saifintex.dao;

import com.saifintex.web.entity.PasswordResetTokenEntity;

public interface PasswordResetDAO extends BaseDAO<PasswordResetTokenEntity, Integer> {
	
	 public PasswordResetTokenEntity getEntityByToken(String token);
	 
	 public void deleteByToken(String token);
	 
	 public PasswordResetTokenEntity getEntityByUserId(int id);
	
}
