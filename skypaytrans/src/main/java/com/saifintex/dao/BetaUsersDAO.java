package com.saifintex.dao;

import com.saifintex.entity.BetaUsersEntity;

public interface BetaUsersDAO extends BaseDAO<BetaUsersEntity, Integer>{
public BetaUsersEntity getBetaUser(String phoneNumber);
}
