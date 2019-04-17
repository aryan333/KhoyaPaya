package com.saifintex.services.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.saifintex.common.AbstractBase;
import com.saifintex.dao.MerchantInfoBySalesPersonDAO;
import com.saifintex.dao.UserDAO;
import com.saifintex.dto.MerchantInfoBySalesPersonDto;
import com.saifintex.entity.MerchantInfoBySalesPersonEntity;
import com.saifintex.entity.UserEntity;
import com.saifintex.services.MerchantInfoBySalesPersonService;
import com.saifintex.services.UserService;
import com.saifintex.utils.DateUtils;

@Service
@Transactional
public class MerchantInfoBySalesPersonServiceImpl extends AbstractBase implements MerchantInfoBySalesPersonService {

	@Value("${app.shop.pic.folder}")
	private String shopPicPath;

	/**
	 * Inject MerchantInfoBySalesPersonDAO
	 * 
	 * @author Ajay
	 * 
	 */
	@Autowired
	private MerchantInfoBySalesPersonDAO merchantInfoBySalesPersonDAO;

	/**
	 * Inject User Service
	 * 
	 * @author Ajay
	 * 
	 */
	@Autowired
	private UserService userService;

	/**
	 * Inject User DAO
	 * 
	 * @author Ajay
	 * 
	 */
	@Autowired
	private UserDAO userDao;

	/**
	 * Method to save merchant info
	 * 
	 * @author Ajay
	 * 
	 * @param MerchantInfoBySalesPersonDto
	 * 
	 * @return MerchantInfoBySalesPersonDto
	 */
	@Override
	public MerchantInfoBySalesPersonDto saveMerchantInfo(MerchantInfoBySalesPersonDto bySalesPersonDto) {

		MerchantInfoBySalesPersonEntity existingBySalesPersonEntity = merchantInfoBySalesPersonDAO
				.getMerchantByMobileNumber(bySalesPersonDto.getMobileNumber());
		Date date = DateUtils.getCurrentDateTime();
		if (bySalesPersonDto.isMerchant()) {

			String blobId = getShopPicFileName(bySalesPersonDto.getFile().getOriginalFilename(), bySalesPersonDto);

			bySalesPersonDto.setShopBlobId(blobId);
			MerchantInfoBySalesPersonEntity bySalesPersonEntity = new MerchantInfoBySalesPersonEntity();
			BeanUtils.copyProperties(bySalesPersonDto, bySalesPersonEntity);

			if (uploadFile(bySalesPersonDto.getFile(), shopPicPath, bySalesPersonDto.getShopBlobId())) {

				if (existingBySalesPersonEntity == null) {
					bySalesPersonEntity.setCreatedBy(bySalesPersonDto.getSalesPersonId());
					bySalesPersonEntity.setCreatedOn(date);
					existingBySalesPersonEntity = merchantInfoBySalesPersonDAO.saveAndGet(bySalesPersonEntity);
					BeanUtils.copyProperties(existingBySalesPersonEntity, bySalesPersonDto);
					return bySalesPersonDto;
				} else {

					return updateMerchantInfo(existingBySalesPersonEntity, bySalesPersonDto);
				}

			}

		}

		if (existingBySalesPersonEntity == null) {
			MerchantInfoBySalesPersonEntity bySalesPersonEntity = new MerchantInfoBySalesPersonEntity();
			BeanUtils.copyProperties(bySalesPersonDto, bySalesPersonEntity);
			bySalesPersonEntity.setCreatedBy(bySalesPersonDto.getSalesPersonId());
			bySalesPersonEntity.setCreatedOn(date);
			existingBySalesPersonEntity = merchantInfoBySalesPersonDAO.saveAndGet(bySalesPersonEntity);
			BeanUtils.copyProperties(existingBySalesPersonEntity, bySalesPersonDto);
			return bySalesPersonDto;
		}

		return updateMerchantInfo(existingBySalesPersonEntity, bySalesPersonDto);

	}

	/**
	 * Method to update merchant info who already exists.
	 * 
	 * @author Ajay
	 * @param bySalesPersonEntity
	 * @param bySalesPersonDto
	 * @return MerchantInfoSalesPersonDto
	 */
	public MerchantInfoBySalesPersonDto updateMerchantInfo(MerchantInfoBySalesPersonEntity bySalesPersonEntity,
			MerchantInfoBySalesPersonDto bySalesPersonDto) {
		Date date = DateUtils.getCurrentDateTime();
		bySalesPersonEntity.setBrands(bySalesPersonDto.getBrands());
		bySalesPersonEntity.setDeviceModel(bySalesPersonDto.getDeviceModel());
		bySalesPersonEntity.setEnterprise(bySalesPersonDto.getEnterprise());
		bySalesPersonEntity.setInstallationMedium(bySalesPersonDto.getInstallationMedium());
		bySalesPersonEntity.setInstallationStatus(bySalesPersonDto.getInstallationStatus());
		bySalesPersonEntity.setName(bySalesPersonDto.getName());
		bySalesPersonEntity.setNatureOfBusiness(bySalesPersonDto.getNatureOfBusiness());
		bySalesPersonEntity.setSalesPersonlatitude(bySalesPersonDto.getSalesPersonlatitude());
		bySalesPersonEntity.setSalesPersonlongitude(bySalesPersonDto.getSalesPersonlongitude());
		bySalesPersonEntity.setModifiedOn(date);
		bySalesPersonEntity.setModifiedBy(bySalesPersonDto.getSalesPersonId());
		bySalesPersonEntity.setSalesPersonInviteCode(bySalesPersonDto.getSalesPersonInviteCode());
		bySalesPersonEntity.setCity(bySalesPersonDto.getCity());
		bySalesPersonEntity.setState(bySalesPersonDto.getState());
		bySalesPersonEntity.setShopBlobId(bySalesPersonDto.getShopBlobId());
		bySalesPersonEntity.setMerchant(bySalesPersonDto.isMerchant());
		BeanUtils.copyProperties(bySalesPersonEntity, bySalesPersonDto);
		return bySalesPersonDto;
	}

	/**
	 * Method to upload a file
	 * 
	 * @author Ajay
	 * @param file
	 * @param path
	 * @param fileName
	 * @return boolean whether file is uploaded or not
	 */
	public boolean uploadFile(CommonsMultipartFile file, String path, String fileName) {
		byte[] bytes = file.getBytes();
		BufferedOutputStream bufferedOutputStream = null;
		try {
			File fileToUpload = new File(path + File.separator + fileName);
			FileOutputStream fileOutputStream = new FileOutputStream(fileToUpload);
			bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			bufferedOutputStream.write(bytes);
			bufferedOutputStream.flush();
			bufferedOutputStream.close();
			fileOutputStream.close();

		} catch (FileNotFoundException e) {
			getLogger().error(e);
			return false;
		} catch (IOException e) {
			getLogger().error(e);
			return false;
		} finally {
			try {
				if (bufferedOutputStream != null) {
					bufferedOutputStream.close();
				}
			} catch (IOException e) {
				getLogger().error(e);
				return false;

			}
		}
		return true;

	}

	/**
	 * Method to get the name of shop picture.
	 * 
	 * @author Ajay
	 * @param fileName
	 * @param bySalesPersonDto
	 * @return String a shop file name
	 */
	public String getShopPicFileName(String fileName, MerchantInfoBySalesPersonDto bySalesPersonDto) {
		UUID uuid = UUID.randomUUID();
		String newfileName = bySalesPersonDto.getMobileNumber() + "_" + uuid.toString() + "."
				+ FilenameUtils.getExtension(fileName);
		return newfileName;
	}

	/**
	 * Method to set the user as merchant if already exist in user table. Because
	 * according to sales person the user is a merchant
	 * 
	 * @author Ajay
	 * @param userMobile
	 * @return void
	 */
	public void setMerchantBySp(String userMobile) {
		UserEntity entity = userDao.getOnlyUserByUserLogin(userMobile);
		if (entity == null) {
			return;
		}
		if (!entity.isMerchant()) {
			entity.setMerchant(true);
		}

	}
}
