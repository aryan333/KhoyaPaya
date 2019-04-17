package com.saifintex.controller.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.saifintex.common.AbstractBase;
import com.saifintex.domain.DisputeReasons;
import com.saifintex.domain.Disputes;
import com.saifintex.dto.DisputeReasonsDTO;
import com.saifintex.dto.DisputesDTO;
import com.saifintex.entity.DisputeReasonsEntity;
import com.saifintex.entity.DisputesEntity;
import com.saifintex.services.DisputeService;

@RestController
@RequestMapping("/rest")
public class DisputeController extends AbstractBase {
	
	@Autowired
	DisputeService disputeService;
	@RequestMapping(value = "/private/insertDisputes", method = RequestMethod.POST)
	public ResponseEntity<?> InsertDisputesHandler(@RequestBody Disputes disputes) {
		DisputesDTO disputesDTO=copyInDto(disputes);
		disputesDTO  = disputeService.insertDisputes(disputesDTO);
		getLogger().info("Dispute Controller = ="+disputesDTO);
		disputes=copyInDomain(disputesDTO);
		return new ResponseEntity<Disputes>(disputes, HttpStatus.OK);
	}

	@RequestMapping(value = "/public/fetchDisputeReasons", method = RequestMethod.GET)
	public ResponseEntity<?> FetchDisputeReasonsHandler() {

		List<DisputeReasonsDTO> disputeReasonsDTO = disputeService.fetechDisputeReasons();
		List<DisputeReasons> disputeReasonsList = (List<DisputeReasons>) (List<?>)copyList(disputeReasonsDTO, DisputeReasons.class);
		
		return new ResponseEntity<List<DisputeReasons>>(disputeReasonsList, HttpStatus.OK);
	}
	public DisputesDTO copyInDto(Disputes disputes) {
		DisputesDTO dto=new DisputesDTO();
		BeanUtils.copyProperties(disputes, dto);
		Set<DisputeReasonsDTO> set=new HashSet<DisputeReasonsDTO>();
		for( DisputeReasons reasons: disputes.getReasons()) {
			DisputeReasonsDTO reasonDto=new DisputeReasonsDTO();
			BeanUtils.copyProperties(reasons, reasonDto);
			set.add(reasonDto);
			
		}
		
		dto.setReasons(set);
		return dto;
	}
	
public Disputes	copyInDomain(DisputesDTO dto){
	Disputes disputes=new Disputes();
	BeanUtils.copyProperties(dto, disputes);
	Set<DisputeReasons> set=new HashSet<DisputeReasons>();
	for( DisputeReasonsDTO reasonsDto: dto.getReasons()) {
		DisputeReasons reason=new DisputeReasons();
		BeanUtils.copyProperties(reasonsDto, reason);
		set.add(reason);
		
	}
	
	disputes.setReasons(set);
	return disputes;
	}
}
