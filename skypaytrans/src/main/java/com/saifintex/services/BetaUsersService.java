package com.saifintex.services;

import org.springframework.stereotype.Service;

@Service
public interface BetaUsersService {
public boolean isBetaUser(String phoneNumber);
}
