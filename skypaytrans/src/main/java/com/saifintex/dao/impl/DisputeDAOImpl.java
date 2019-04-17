package com.saifintex.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.saifintex.dao.DisputeDAO;
import com.saifintex.entity.DisputeReasonsEntity;
import com.saifintex.entity.DisputesEntity;
@Repository
public class DisputeDAOImpl extends BaseDAOImpl<DisputesEntity, Integer> implements DisputeDAO{

	@Override
	public DisputeReasonsEntity getReasonById(Integer id) {
		DisputeReasonsEntity entity=sessionFactory.getCurrentSession().get(DisputeReasonsEntity.class, id);
		return entity;
	}

	@Override
	public List<DisputeReasonsEntity> findAllReasons() {
		@SuppressWarnings("unchecked")
		List<DisputeReasonsEntity> reasonList=sessionFactory.getCurrentSession().createCriteria(DisputeReasonsEntity.class).list();
		return reasonList;
	}

}
