package com.saifintex.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saifintex.dao.ItemCategoryDAO;
import com.saifintex.dto.ItemCategoryDto;
import com.saifintex.entity.ItemCategoryEntity;
import com.saifintex.services.ItemCategoryService;
import com.saifintex.utils.DateUtils;
import com.saifintex.web.dto.WebUsersDTO;
@Service("itemCategoryService")
@Transactional
public class ItemCategoryServiceImpl implements ItemCategoryService {

	@Autowired
	ItemCategoryDAO itemCategoryDAO;
	
	@Override
	public List<ItemCategoryDto> getItemCategoryList() {
		List<ItemCategoryEntity> entityList=itemCategoryDAO.findAll(ItemCategoryEntity.class);
		List<ItemCategoryDto> dtoList=new ArrayList<ItemCategoryDto>();
		for(ItemCategoryEntity entity:entityList) {
			ItemCategoryDto dto=new ItemCategoryDto();
			BeanUtils.copyProperties(entity, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	@Override
	public ItemCategoryDto addItemCategory(ItemCategoryDto dto,WebUsersDTO webUser) {
		ItemCategoryEntity entity = new ItemCategoryEntity();
		BeanUtils.copyProperties(dto, entity);
		Date date = DateUtils.getCurrentDateTime();
		entity.setCreatedBy(webUser.getWebUserId());
		entity.setCreatedOn(date);
		entity.setModifiedBy(webUser.getWebUserId());
		entity.setModifiedOn(date);		
		entity = itemCategoryDAO.saveAndGet(entity);
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

}
