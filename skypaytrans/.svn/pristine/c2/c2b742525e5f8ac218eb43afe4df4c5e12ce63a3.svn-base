package com.saifintex.services;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.saifintex.web.dto.QRBulkZipDownloadResponseParams;

public interface QRGenerateService {
public Object generateQRCodes(int number);
public QRBulkZipDownloadResponseParams getFileDetailTODownload(String fileName);
public int sendQrFileViaEMail(String fileName);

public int mapQrCodes(CommonsMultipartFile file);
}
