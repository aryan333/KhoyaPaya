package com.saifintex.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saifintex.common.AbstractBase;
import com.saifintex.dao.RewardPointsDAO;
import com.saifintex.dto.RewardPointsDTO;
import com.saifintex.entity.RewardPointsEntity;
import com.saifintex.services.RewardPointsService;
import com.saifintex.utils.DateUtils;
import com.saifintex.web.dto.WebUsersDTO;

@Service
@Transactional
public class RewardPointsServiceImpl extends AbstractBase implements RewardPointsService{

	@Autowired
	private RewardPointsDAO rewardPointsDAO;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RewardPointsDTO> getAll() {
		List<RewardPointsEntity> rewardPointsEntities = rewardPointsDAO.findAll(RewardPointsEntity.class);
		List<RewardPointsDTO> list = (List<RewardPointsDTO>)(List<?>)copyList(rewardPointsEntities, RewardPointsDTO.class);
		return list;
	}

	@Override
	public RewardPointsDTO addRewardPoint(RewardPointsDTO rewardPointDTO,WebUsersDTO webUser) {
		RewardPointsEntity rewardPointEntity = new RewardPointsEntity();
		BeanUtils.copyProperties(rewardPointDTO, rewardPointEntity);
		Date date = DateUtils.getCurrentDateTime();
		rewardPointEntity.setCreatedBy(webUser.getWebUserId());
		rewardPointEntity.setCreatedOn(date);
		rewardPointEntity.setModifiedBy(webUser.getWebUserId());
		rewardPointEntity.setModifiedOn(date);
		RewardPointsEntity rEntity = rewardPointsDAO.saveAndGet(rewardPointEntity);
		rewardPointDTO = new RewardPointsDTO();
		BeanUtils.copyProperties(rEntity, rewardPointDTO);
		return rewardPointDTO;
	}

	
}
