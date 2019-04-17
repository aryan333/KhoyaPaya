package com.saifintex.services.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.saifintex.dao.QRGeneratedFilesDetailDAO;
import com.saifintex.dao.QRUniqueSeriesDAO;
import com.saifintex.dao.UserDAO;
import com.saifintex.entity.UserEntity;
import com.saifintex.services.QRGenerateService;
import com.saifintex.utils.DateUtils;
import com.saifintex.utils.EmailUtils;
import com.saifintex.utils.EncryptionDecryptionUtils;
import com.saifintex.utils.ResponseCode;
import com.saifintex.web.domain.QrMappedUserDetail;
import com.saifintex.web.dto.QRBulkZipDownloadResponseParams;
import com.saifintex.web.dto.QRDetailsDTO;
import com.saifintex.web.dto.QRMappingCSVDetails;
import com.saifintex.web.entity.QRGeneratedFilesDetailEntity;
import com.saifintex.web.entity.QRUniqueSeriesEntity;

@Service
@Transactional
public class QRGenerateServiceImpl implements QRGenerateService {
	@Autowired
	private QRUniqueSeriesDAO qrUniqueSeriesDAO;
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private QRGeneratedFilesDetailDAO qrGeneratedFilesDetailDAO;
	@Value("${app.qr.series.prefix}")
	private String seriesPrefix;

	@Value("${app.qr.standee.key}")
	private String standeeKeyword;
	@Value("${app.qr.storage.path}")
	private String path;

	@Value("${app.deployment.envt}")
	private String deploymentEnvrt;

	@Value("${app.qr.domain}")
	private String qrDomain;

	@Value("${mail.qr.to}")
	private String[] mailTo;

	@Value("${mail.qr.subject}")
	private String mailSubject;

	@Value("${mail.qr.body}")
	private String mailBody;

	@Value("${mail.mailhead}")
	private String mailHead;
	@Value("${mail.qr.cc}")
	private String[] mailCC;

	@Value("${app.qr.map.csv.folder}")
	private String csvFolder;

	private static final String QR_SERIES = "QRSERIES";

	private static final String MOBILE_NUMBER = "MOBILENUMBER";
	private static final String[] FILE_HEADER_MAPPING = { "MOBILENUMBER", "QRSERIES" };

	@Override
	public Object generateQRCodes(int number) {
		String folderName = null;

		synchronized (this) {

			folderName = path + String.valueOf(DateUtils.getCurrentDate() + "_" + System.currentTimeMillis());
			File file = new File(folderName);
			if (!file.exists()) {

				if (deploymentEnvrt.equalsIgnoreCase("linux")) {
					file.setExecutable(true, false);
					file.setReadable(true, false);
					file.setWritable(true, false);
				}
				if (!file.mkdir()) {
					System.out.println("not creating");
				}

			} else {
				return null;
			}
			for (int i = 0; i < number; i++) {
				QRUniqueSeriesEntity entity = createQREntity();
				QRUniqueSeriesEntity generatedQrSeriesEntity = qrUniqueSeriesDAO.saveAndGet(entity);
				String text = getTextToBeFiedToQRCode(generatedQrSeriesEntity);
				String imageName = seriesPrefix + generatedQrSeriesEntity.getId() + ".png";
				String filePath = null;
				if (deploymentEnvrt.equalsIgnoreCase("linux")) {
					filePath = folderName + "/" + imageName;
				} else {
					filePath = folderName + "\\" + imageName;
				}

				if (generateQRCodeImage(text, 350, 350, filePath)) {
					generatedQrSeriesEntity.setQrGenerated(true);
					generatedQrSeriesEntity.setFolderName(folderName);
					generatedQrSeriesEntity.setQrImageName(imageName);
					generatedQrSeriesEntity.setQrSeries(seriesPrefix+generatedQrSeriesEntity.getId());
				}
			}

			try {
				zipFolder(Paths.get(folderName), folderName + ".zip");
			} catch (Exception e) {

				e.printStackTrace();
				return null;
			}
		}
		return true;
	}

	private static boolean generateQRCodeImage(String text, int width, int height, String filePath) {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		try {
			BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

			Path path = FileSystems.getDefault().getPath(filePath);

			MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
		} catch (WriterException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	private QRUniqueSeriesEntity createQREntity() {
		Date date = DateUtils.getCurrentDateTime();
		QRUniqueSeriesEntity entity = new QRUniqueSeriesEntity();
		entity.setCreatedOn(date);
		entity.setCreatedBy(1);
		entity.setModifiedBy(1);
		entity.setModifiedOn(date);
		return entity;

	}

	private String getTextToBeFiedToQRCode(QRUniqueSeriesEntity entity) {
		QRDetailsDTO dto = new QRDetailsDTO();
		dto.setQrType(standeeKeyword);
		dto.setUniqueSeries(seriesPrefix + entity.getId());
		dto.setCreatedDate(entity.getCreatedOn().toString());
		dto.setQrDomain(qrDomain);
		Gson gson = new Gson();
		return EncryptionDecryptionUtils.encryptQRData(gson.toJson(dto));
	}

	private void zipFolder(Path sourceFolderPath, String zipPath) throws Exception {
		Path zipPath1 = Paths.get(zipPath);

		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath1.toFile()));

		Files.walkFileTree(sourceFolderPath, new SimpleFileVisitor<Path>() {

			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

				zos.putNextEntry(new ZipEntry(sourceFolderPath.relativize(file).toString()));

				Files.copy(file, zos);

				zos.closeEntry();

				return FileVisitResult.CONTINUE;

			}

		});

		zos.close();

		if (deploymentEnvrt.equalsIgnoreCase("linux")) {
			zipPath1.toFile().setExecutable(true, false);
			zipPath1.toFile().setReadable(true, false);
			zipPath1.toFile().setWritable(true, false);
		}
		saveQrGeneratedDetails(zipPath1);

	}

	private ByteArrayResource getResourceToDownloadFile(String path) {

		Path file = Paths.get(path);
		ByteArrayResource resource = null;

		try {
			resource = new ByteArrayResource(Files.readAllBytes(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} finally {

		}
		return resource;
	}

	private void saveQrGeneratedDetails(Path path) {
		QRGeneratedFilesDetailEntity entity = new QRGeneratedFilesDetailEntity();
		entity.setFileName(path.toFile().getName());
		entity.setCreatedBy(1);
		entity.setCreatedOn(DateUtils.getCurrentDateTime());
		entity.setModifiedBy(1);
		entity.setModifiedOn(DateUtils.getCurrentDateTime());
		entity.setFolderName(path.toFile().getAbsolutePath());
		qrGeneratedFilesDetailDAO.save(entity);
	}

	@Override
	public QRBulkZipDownloadResponseParams getFileDetailTODownload(String fileName) {
		String folderName = null;
		if (!fileName.contains(".zip")) {
			folderName = path + fileName + ".zip";
		} else {
			folderName = path + fileName + ".zip";
		}
		QRBulkZipDownloadResponseParams params = createQRBulkZipDownloadResponseParams(folderName);
		return params;
	}

	private QRBulkZipDownloadResponseParams createQRBulkZipDownloadResponseParams(String fileName) {
		QRBulkZipDownloadResponseParams params = new QRBulkZipDownloadResponseParams();
		params.setByteArrayResource(getResourceToDownloadFile(fileName));
		Path file = Paths.get(fileName);
		params.setFile(file.toFile());
		return params;
	}

	@Override
	public int sendQrFileViaEMail(String fileName) {
		File file = new File(path + fileName);
		if (!file.exists()) {
			return ResponseCode.FILE_DOES_NOT_EXIST;
		}
		try {
			EmailUtils.sendHTMLEmailWithAttachement(mailTo, mailCC, mailSubject, mailHead + mailBody, null, fileName,
					file);
		} catch (MessagingException e) {
			e.printStackTrace();
			return ResponseCode.PROBLEM_SENDING_FILE;
		}

		return ResponseCode.FILE_SEND_SUCESSFULLY;
	}

	
	
	
	
	@Override
	public int mapQrCodes(CommonsMultipartFile file) {
		byte[] bytes = file.getBytes();
		String pathToUpload = null;

		synchronized (this) {

			pathToUpload = getCsvFileNameToUpload(file);
			Path path = Paths.get(pathToUpload);

			try {
				Files.write(path, bytes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		List<QRMappingCSVDetails> listOfMappingDetail = getQRmappingDetailFromCSVFile(pathToUpload);
		Date date=DateUtils.getCurrentDateTime();
		  for(QRMappingCSVDetails csvDetails:listOfMappingDetail) {
			  QRUniqueSeriesEntity qrUniqueSeriesEntity=qrUniqueSeriesDAO.getBySeries(csvDetails.getQrSeries());
			  UserEntity userEntity=userDao.getOnlyUserByUserLogin(csvDetails.getMobileNumber());
			  
			  if(qrUniqueSeriesEntity==null || userEntity==null) {
				  System.out.println("null one of them");
				  continue;
			  }else if(qrUniqueSeriesEntity.isMapped() || userEntity.getStandeeId()!=null){
				  System.out.println("mapped one of them");
				  continue;
		  }else {
			  userEntity.setStandeeId(csvDetails.getQrSeries());
			  qrUniqueSeriesEntity.setMapped(true);
			  qrUniqueSeriesEntity.setMappedNumber(csvDetails.getMobileNumber());
			  qrUniqueSeriesEntity.setModifiedOn(date);
			  userEntity.setQrMapDate(date);
			  
		  }
		
	}
		  return 0;
	}
	private String getCsvFileNameToUpload(CommonsMultipartFile file) {
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		;
		String path = csvFolder + System.currentTimeMillis() + "." + extension;
		return path;
	}

	private List<QRMappingCSVDetails> getQRmappingDetailFromCSVFile(String path) {

	

		List<QRMappingCSVDetails> qrMappingDetailList = new ArrayList<QRMappingCSVDetails>();

		FileReader fileReader = null;

		CSVParser csvFileParser = null;

		// Create the CSVFormat object with the header mapping

		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);

		try {

			

			// initialize FileReader object

			fileReader = new FileReader(path);

			// initialize CSVParser object

			csvFileParser = new CSVParser(fileReader, csvFileFormat);

			// Get a list of CSV file records

			List<CSVRecord> csvRecords = csvFileParser.getRecords();

			// Read the CSV file records starting from the second record to skip the header

			for (int i = 1; i < csvRecords.size(); i++) {

				CSVRecord record = (CSVRecord) csvRecords.get(i);

				// Create a new student object and fill his data
				QRMappingCSVDetails details = new QRMappingCSVDetails();
				details.setMobileNumber(record.get(MOBILE_NUMBER));
				details.setQrSeries(record.get(QR_SERIES));

				qrMappingDetailList.add(details);

			}

			// Print the new student list

			for (QRMappingCSVDetails detail : qrMappingDetailList) {

				System.out.println(detail.getMobileNumber());

			}

		}

		catch (Exception e) {

			System.out.println("Error in CsvFileReader !!!");

			e.printStackTrace();

		} finally {

			try {

				fileReader.close();

				csvFileParser.close();

			} catch (IOException e) {

				System.out.println("Error while closing fileReader/csvFileParser !!!");
				e.printStackTrace();

			}

		}

		return qrMappingDetailList;
	}

}
