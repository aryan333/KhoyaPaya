package com.saifintex.services.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saifintex.common.AbstractBase;
import com.saifintex.dao.AppsDAO;
import com.saifintex.dao.ClientExceptionsDAO;
import com.saifintex.dao.FiscalYearDAO;
import com.saifintex.dao.ItemCategoryDAO;
import com.saifintex.dao.RewardPointsDAO;
import com.saifintex.dto.AppPreferencesDto;
import com.saifintex.dto.ClientExceptionsDTO;
import com.saifintex.dto.FiscalYearDTO;
import com.saifintex.dto.ItemCategoryDto;
import com.saifintex.entity.AppPreferencesEntity;
import com.saifintex.entity.ClientExceptionsEntity;
import com.saifintex.entity.FiscalYearEntity;
import com.saifintex.entity.ItemCategoryEntity;
import com.saifintex.entity.RewardPointsEntity;
import com.saifintex.services.AppService;
import com.saifintex.utils.DateUtils;
import com.saifintex.utils.EmailUtils;

@Service("appService")
@Transactional
public class AppServiceImpl extends AbstractBase implements AppService {

	
	
	
	@Autowired
	AppsDAO appsDao;
	
	@Autowired
	RewardPointsDAO rewardPointsDAO;

	@Autowired
	ItemCategoryDAO itemCategoryDAO;
	@Autowired
	ClientExceptionsDAO clientExceptionsDAO;
	@Autowired
	FiscalYearDAO fiscalYearDAO;

	@Value("${catalina.home}")
	private String serverPath;

	@Value("${path.file.log.error}")
	private String errorFile;

	@Value("${path.file.log.info}")
	private String infoFile;

	@Value("${test.path.file.log.windows}")
	private String windowServerPath;
	@Value("${mail.mailsubject}")
	
	private String mailSubject;
	
	@Value("${mail.mailto}")
	private String mailTo;
	
	@Value("${mail.mailto.cc}")
	private String mailToCC;
	
@Value("${mail.mailhead}")
	
	private String mailHead;
	
	@Value("${mail.maildesc}")
	private String mailDesc;
	
	@Value("${mail.mailfooter}")
	private String mailFooter;
	
	
	@Override
	public FiscalYearDTO fetchFiscalYear() {
		FiscalYearEntity entity = fiscalYearDAO.fetchFiscalYear();
		FiscalYearDTO dto = new FiscalYearDTO();
		BeanUtils.copyProperties(entity, dto);
		dto = getFiscalYear(dto);
		return dto;
	}

	private FiscalYearDTO getFiscalYear(FiscalYearDTO dto) {
		StringBuilder quarter1 = new StringBuilder();
		StringBuilder quarter2 = new StringBuilder();
		StringBuilder quarter3 = new StringBuilder();
		StringBuilder quarter4 = new StringBuilder();
		int i = 1;
		LocalDate date1 = new LocalDate(dto.getStartDate());
		LocalDate date2 = new LocalDate(dto.getEndDate());
		while (date1.isBefore(date2)) {
			if (i >= 1 && i <= 3) {
				quarter1.append(date1.toString("MMM"));
				quarter1.append(",");
			} else if (i >= 4 && i <= 6) {
				quarter2.append(date1.toString("MMM"));
				quarter2.append(",");
			} else if (i >= 7 && i <= 9) {
				quarter3.append(date1.toString("MMM"));
				quarter3.append(",");
			} else {
				quarter4.append(date1.toString("MMM"));
				quarter4.append(",");
			}
			System.out.println(date1.toString("MMM"));
			date1 = date1.plus(Period.months(1));
			i++;
		}
		dto.setQuarter1(quarter1.deleteCharAt(quarter1.lastIndexOf(",")));
		dto.setQuarter2(quarter2.deleteCharAt(quarter2.lastIndexOf(",")));
		dto.setQuarter3(quarter3.deleteCharAt(quarter3.lastIndexOf(",")));
		dto.setQuarter4(quarter4.deleteCharAt(quarter4.lastIndexOf(",")));
		getLogger().info(dto.getStartDate());
		getLogger().info(dto.getEndDate());
		return dto;
	}

	/*
	 * this method is created to get item category list
	 * 
	 * @see com.saifintex.services.AppService#getItemCategoryList()
	 */
	@Override
	public List<ItemCategoryDto> getItemCategoryList() {
		List<ItemCategoryEntity> entityList = itemCategoryDAO.findAll(ItemCategoryEntity.class);
		List<ItemCategoryDto> dtoList = new ArrayList<ItemCategoryDto>();
		for (ItemCategoryEntity entity : entityList) {
			ItemCategoryDto dto = new ItemCategoryDto();
			BeanUtils.copyProperties(entity, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}

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
		List<ClientExceptionsEntity> entityList = clientExceptionsDAO.findAll(ClientExceptionsEntity.class);
		List<ClientExceptionsDTO> dtoList = (List<ClientExceptionsDTO>) (List<?>) copyList(entityList,
				ClientExceptionsDTO.class);
		return dtoList;
	}

	@Override
	public String readTodayInfoLogs() {
		String fileInfoPath = serverPath + infoFile + DateUtils.getCurrentDate() + ".log";
		// String fileInfoPath = path+infoFile+"."+DateUtils.getCurrentDate()+".log";
		String info = readLogs(fileInfoPath);
		return info;
	}

	@Override
	public String readTodayExceptionLogs() {
		String filErrorPath = serverPath + errorFile + DateUtils.getCurrentDate() + ".log";
		// String filErrorPath = path+errorFile+"."+DateUtils.getCurrentDate()+".log";
		String err = readLogs(filErrorPath);
		return err;
	}

	@Override
	public String readExceptionLogOnSpecifiedDate(String date) {
		String filePath = serverPath + errorFile + date + ".log";
		return readLogs(filePath);
	}

	@Override
	public String readInfoLogOnSpecifiedDate(String date) {
		String filePath = serverPath + infoFile + date + ".log";
		return readLogs(filePath);
	}

	/*private String readLogs(String filePath) {
		FileReader reader = null;
		try {
			getLogger().info("server path == " + filePath);
			reader = new FileReader(filePath);
		} catch (FileNotFoundException e) {
			getLogger().error("Can not find file at specified path", e.fillInStackTrace());
			return "";
		}

		BufferedReader b = new BufferedReader(reader);
		String exc = "";
		try {
			while (b.readLine() != null) {
				exc = exc + b.readLine();
			}
		} catch (IOException e) {
			getLogger().error("Problem in reading file", e.fillInStackTrace());
		} finally {
			try {
				reader.close();
				b.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				getLogger().error("can not close file", e.fillInStackTrace());
			}

		}
		return exc;
	}*/

	private String readLogs(String filePath) {
		FileInputStream fileInputStream = null;
		Scanner sc = null;
		String exc = "";
		try {
			getLogger().info("server path == " + filePath);
			fileInputStream = new FileInputStream(filePath);
			sc = new Scanner(fileInputStream);
			while (sc.hasNext()) {
				exc = exc + sc.nextLine();
			}			
		} catch (FileNotFoundException e) {
			getLogger().error("Can not find file at specified path", e.fillInStackTrace());
			return "";
		} finally {
			if (sc != null) {
				sc.close();
			}
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}

		}

		return exc;
	}

	@Override
	public AppPreferencesDto getAppPreferences() {
		AppPreferencesEntity entity =  appsDao.getOne(AppPreferencesEntity.class, 1);
		if(entity!=null) {
			AppPreferencesDto dto = new AppPreferencesDto();
			BeanUtils.copyProperties(entity, dto);
			return dto;
		}		
		return null;
	}

	@Override
	public void updateAppPreferences(AppPreferencesDto appPrefDto) {
		AppPreferencesEntity appPrefEntity = appsDao.getOne(AppPreferencesEntity.class, 1);
		appPrefEntity.setInviteBenefit(appPrefDto.isInviteBenefit());
		appPrefEntity.setShowTestUsers(appPrefDto.isShowTestUsers());
		appPrefEntity.setSignupBenefit(appPrefDto.isSignupBenefit());
		appPrefEntity.setMaxPointsEarned(appPrefDto.getMaxPointsEarned());
		updateReferralSchemeStatus(appPrefDto);
		
	}
	
	private void updateReferralSchemeStatus(AppPreferencesDto appPreferencesDto) {
		RewardPointsEntity rpEntity = rewardPointsDAO.getOne(RewardPointsEntity.class, 1);  // Referral Reward Scheme
		rpEntity.setActive(appPreferencesDto.isInviteBenefit());
		rpEntity = rewardPointsDAO.getOne(RewardPointsEntity.class, 2);    // Signup Reward
		rpEntity.setActive(appPreferencesDto.isSignupBenefit());
	}

	@Override
	public void sendDashBoardDetailViaEmail(String to,String cc, String subject, String text, String html) {
		StringBuilder builder=new StringBuilder("<h3>" +mailHead +"<h3><br> <h3>"+mailDesc+"</h3>");
		builder.append("<table height=300 style='border-collapse: collapse;' border='1'");
		
		builder.append(html);
		builder.append("</table>");
		
		builder.append("<h5>"+mailFooter+"</h5>");
		System.out.println("table data="+builder.toString().replaceAll("button", "span"));
		EmailUtils.sendHTMLEmail(mailTo, mailToCC,mailSubject+"("+DateUtils.getCurrentDateTime()+")", text, builder.toString().replaceAll("button", "span"));
		
	}

}
