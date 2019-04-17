package com.saifintex.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saifintex.common.AbstractBase;
import com.saifintex.dao.QRGeneratedFilesDetailDAO;
import com.saifintex.services.QRGeneratedFileDetailService;
import com.saifintex.web.dto.QRGeneratedFilesDetailDTO;
import com.saifintex.web.entity.QRGeneratedFilesDetailEntity;

@Service

public class QRGeneratedFileDetailServiceImpl extends AbstractBase implements QRGeneratedFileDetailService {
	@Autowired
	private QRGeneratedFilesDetailDAO qrGeneratedFilesDetailDAO;
	
	
	@Transactional(readOnly = true)
	@Override
	public List<QRGeneratedFilesDetailDTO> findAll() {
		List<QRGeneratedFilesDetailEntity> listOfEntities =qrGeneratedFilesDetailDAO.findAll(QRGeneratedFilesDetailEntity.class,"id");
		List<QRGeneratedFilesDetailDTO> dtos=(List<QRGeneratedFilesDetailDTO>) (List<?>) copyList(listOfEntities, QRGeneratedFilesDetailDTO.class);
	return dtos;
	}

}
