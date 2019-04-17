package com.saifintex.services;

import java.util.List;

import com.saifintex.dto.RelationsDTO;
import com.saifintex.web.dto.WebUsersDTO;

public interface RelationsService {
	
	public List<RelationsDTO> findAll();
	
	public RelationsDTO addRelation(RelationsDTO dto,WebUsersDTO webUser);

}
