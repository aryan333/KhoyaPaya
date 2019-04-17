package com.saifintex.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saifintex.dao.NotificationDAO;
import com.saifintex.dto.NotificationDTO;
import com.saifintex.dto.NotificationStatusDTO;
import com.saifintex.dto.NotificationTypeDTO;
import com.saifintex.entity.NotificationEntity;
import com.saifintex.services.NotificationService;


@Service
public class NotificationServiceImpl implements NotificationService{
	@Autowired
	NotificationDAO notificationDao;

	@Transactional(readOnly=true)
	@Override
	public List<NotificationDTO> getUserNotification(int id) {
		List<NotificationDTO> dtoList=copyListInNotificationDTO(notificationDao.getUserNotifications(id));
		return dtoList;
	}
	
	private List<NotificationDTO> copyListInNotificationDTO(List<NotificationEntity> entityList) {
		List<NotificationDTO> dtoList=new ArrayList<NotificationDTO>();
		for(NotificationEntity entity:entityList) {
			NotificationDTO notificationDto=new NotificationDTO();
		    BeanUtils.copyProperties(entity, notificationDto);
		    NotificationStatusDTO statusDto=new NotificationStatusDTO();
		    BeanUtils.copyProperties(entity.getNotificationStatus(), statusDto);
		    notificationDto.setNotificationStatusDTO(statusDto);
		    NotificationTypeDTO typeDto=new NotificationTypeDTO();
		    BeanUtils.copyProperties(entity.getNotificationTypeEntity(), typeDto);
		    notificationDto.setNotificationTypeDTO(typeDto);
		    dtoList.add(notificationDto);
		}
	return dtoList;
	}

}
