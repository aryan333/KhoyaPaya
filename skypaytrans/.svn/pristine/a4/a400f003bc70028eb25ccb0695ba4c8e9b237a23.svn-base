package com.saifintex.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saifintex.common.AbstractBase;
import com.saifintex.dao.DisputeDAO;
import com.saifintex.domain.ClientExceptions;
import com.saifintex.dto.DisputeReasonsDTO;
import com.saifintex.dto.DisputesDTO;
import com.saifintex.entity.DisputeReasonsEntity;
import com.saifintex.entity.DisputesEntity;
import com.saifintex.services.DisputeService;
@Transactional
@Service
public class DisputeServiceImpl extends AbstractBase implements DisputeService {
@Autowired
DisputeDAO disputeDao;

	@Override
	public DisputesDTO insertDisputes(DisputesDTO disputesDTO) {
		int disputeReasonId=disputesDTO.getReasons().iterator().next().getDisputeReasonId();
		getLogger().info("Dispute Reason set="+disputeReasonId);
				DisputeReasonsEntity entity=disputeDao.getReasonById(disputeReasonId );
				if(entity==null) {
					return null;
				}
				DisputesEntity disputeEntity=copyInEntity(disputesDTO);
				
				Set<DisputeReasonsEntity> reasonSet=new HashSet<DisputeReasonsEntity>();
				disputeEntity.setReasons(reasonSet);
				reasonSet.add(entity);
				Integer disputeId=disputeDao.save(disputeEntity);
			disputeEntity=disputeDao.getOne(DisputesEntity.class, disputeId);
			getLogger().info("Dispute service = ="+disputeEntity);
				DisputesDTO dto=copyInDto(disputeEntity);
		return dto;
	}
	@Override
	public List<DisputeReasonsDTO> fetechDisputeReasons() {
		List<DisputeReasonsEntity> entityList=disputeDao.findAllReasons();
		List<DisputeReasonsDTO> dtoList=(List<DisputeReasonsDTO>) (List<?>) copyList(entityList, DisputeReasonsDTO.class);
		return dtoList;
	}
	private DisputesEntity copyInEntity(DisputesDTO dto) {
		DisputesEntity entity=new DisputesEntity();
		BeanUtils.copyProperties(dto, entity);
		Set<DisputeReasonsEntity> set=new HashSet<DisputeReasonsEntity>();
		for( DisputeReasonsDTO reasons: dto.getReasons()) {
			DisputeReasonsEntity reasonEntity=new DisputeReasonsEntity();
			BeanUtils.copyProperties(reasons, reasonEntity);
			set.add(reasonEntity);
			
		}
		
		entity.setReasons(set);
		return entity;
	}
private DisputesDTO copyInDto(DisputesEntity entity) {
	DisputesDTO dto=new DisputesDTO();
	BeanUtils.copyProperties(entity, dto);
	Set<DisputeReasonsDTO> set=new HashSet<DisputeReasonsDTO>();
	for( DisputeReasonsEntity reasons: entity.getReasons()) {
		DisputeReasonsDTO reasonDto=new DisputeReasonsDTO();
		BeanUtils.copyProperties(reasons, reasonDto);
		set.add(reasonDto);
		
	}
	
	dto.setReasons(set);
	return dto;
}


}
