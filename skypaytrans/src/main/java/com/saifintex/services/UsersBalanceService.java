package com.saifintex.services;

import com.saifintex.dto.UsersBalanceDTO;
import com.saifintex.entity.UsersBalanceEntity;

public interface UsersBalanceService {
public UsersBalanceDTO getByBothIds(int user1,int user2);
public void update(UsersBalanceDTO dto);
}
