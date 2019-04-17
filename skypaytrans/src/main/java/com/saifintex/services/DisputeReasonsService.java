package com.saifintex.services;

import java.util.List;

import com.saifintex.dto.DisputeReasonsDTO;
import com.saifintex.web.dto.WebUsersDTO;

public interface DisputeReasonsService {
	
	public List<DisputeReasonsDTO> findAll();

	public DisputeReasonsDTO addDisputeReason(DisputeReasonsDTO dto,WebUsersDTO webUsersDTO);
}
