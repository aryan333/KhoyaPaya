package com.saifintex.services.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saifintex.common.AbstractBase;
import com.saifintex.dao.ClientExceptionsDAO;
import com.saifintex.dto.BugReportStats;
import com.saifintex.dto.ClientExceptionsDTO;
import com.saifintex.entity.ClientExceptionsEntity;
import com.saifintex.services.ClientExceptionsService;
import com.saifintex.utils.DateUtils;
@Service("clientExceptionsService")
@Transactional
public class ClientExceptionsServiceImpl extends AbstractBase implements ClientExceptionsService {
	
	@Value("${catalina.home}")
	private String serverPath;
	
	@Value("${path.file.log.error}")
	private String errorFile;
	
	@Value("${path.file.log.info}")
	private String infoFile;
	
	@Value("${test.path.file.log.windows}")
	private String windowServerPath;

	
	@Autowired
	ClientExceptionsDAO clientExceptionsDAO ;
	@Override
	public int storeClientExceptionService(ClientExceptionsDTO clientExceptionsDTO) {
		ClientExceptionsEntity entity = new ClientExceptionsEntity();
		BeanUtils.copyProperties(clientExceptionsDTO, entity);
		Date date = DateUtils.getCurrentDateTime();
		entity.setCreatedOn(date);
		entity.setCreatedBy(clientExceptionsDTO.getUserId());
		entity.setModifiedOn(date);
		entity.setModifiedBy(clientExceptionsDTO.getUserId());
		int id = clientExceptionsDAO.save(entity);
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClientExceptionsDTO> getAllClientExceptionsService() {
		List<ClientExceptionsEntity> entityList = clientExceptionsDAO.findAll(ClientExceptionsEntity.class,"id");
		List<ClientExceptionsDTO> dtoList = (List<ClientExceptionsDTO>)(List<?>)copyList(entityList, ClientExceptionsDTO.class);
		return dtoList;
	}
	
	@Override
	public String readTodayInfoLogs() {			
		String fileInfoPath = serverPath+infoFile+DateUtils.getCurrentDate()+".log";		
	//	String fileInfoPath = "C:\\Users\\SaiFinTex\\Documents\\skypaytrans_info.2017-10-11.java";		
		String info = readLogs(fileInfoPath);
		return info;
	}	

	@Override
	public String readTodayExceptionLogs() {		
		String filErrorPath = serverPath+errorFile+DateUtils.getCurrentDate()+".log";
	//	String filErrorPath = path+errorFile+"."+DateUtils.getCurrentDate()+".log";
		String err = readLogs(filErrorPath);		
		return err;
	}
	
	@Override
	public String readExceptionLogOnSpecifiedDate(String date) {
		String filePath = serverPath+errorFile+date+".log";		
		return readLogs(filePath);
	}
	
	@Override
	public String readInfoLogOnSpecifiedDate(String date) {
		String filePath = serverPath+infoFile+date+".log";		
		return readLogs(filePath);
	}
	
	private String readLogs(String filePath) {		
		FileReader reader = null;
		try {
			reader = new FileReader(filePath);
		} catch (FileNotFoundException e) {			
			getLogger().error("Can not find file at specified path", e.fillInStackTrace());
			return "";
		}
		
		BufferedReader b = new BufferedReader(reader);
		StringBuilder exc = new StringBuilder();
		try {
			while(b.readLine()!=null) {
				exc = exc.append(b.readLine());				
			}
		} catch (IOException e) {			
			getLogger().error("Problem in reading file", e.fillInStackTrace());
		}finally {
			try {
				reader.close();
				b.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				getLogger().error("can not close file", e.fillInStackTrace());
			}
			
		}
		return exc.toString();
	}

	@Override
	public BugReportStats getBugReportStats() {
		BugReportStats stats=clientExceptionsDAO.getBugStats();
		return stats;
	}


}
