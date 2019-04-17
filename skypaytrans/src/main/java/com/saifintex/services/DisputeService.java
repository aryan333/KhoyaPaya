package com.saifintex.services;

import java.util.List;

import com.saifintex.dto.DisputeReasonsDTO;
import com.saifintex.dto.DisputesDTO;

public interface DisputeService  {
	public DisputesDTO insertDisputes(DisputesDTO disputesDTO);
	public List<DisputeReasonsDTO> fetechDisputeReasons();
}
