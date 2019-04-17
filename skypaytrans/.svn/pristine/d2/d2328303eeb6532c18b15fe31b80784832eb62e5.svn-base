package com.saifintex.controller.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.saifintex.common.AbstractBase;
import com.saifintex.dto.UserDTO;
import com.saifintex.services.QRGenerateService;
import com.saifintex.services.QRGeneratedFileDetailService;
import com.saifintex.services.UserService;
import com.saifintex.utils.ResponseCode;
import com.saifintex.web.domain.QrMappedUserDetail;
import com.saifintex.web.dto.QRBulkZipDownloadResponseParams;
import com.saifintex.web.dto.QRGeneratedFilesDetailDTO;
import com.saifintex.web.dto.QrMappedUserDetailDTO;

@Controller
@RequestMapping("/admin")
public class QRGenerationController extends AbstractBase {
	@Autowired
	private QRGenerateService qrGenerateService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private QRGeneratedFileDetailService qrGeneratedFileDetailService;
	@Value("${app.qr.maximum.number}")
	private int maxNumber;

	@RequestMapping(value = "/generateQRCodesInBulk/{number}", method = RequestMethod.GET)
	public ResponseEntity<?> generateQRCodesInBulk(@PathVariable("number") int number) {

		Map<String, String> responseMap = new HashMap<String, String>();
		if (number > maxNumber) {
			responseMap.put("response", "please enter upto " + maxNumber);
			return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.BAD_REQUEST);
		}
		System.out.println("hello uin qr");
		Object result = qrGenerateService.generateQRCodes(number);
		if (result instanceof Boolean) {
			responseMap.put("response", "succesfully generated");
			return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.OK);
		}
		responseMap.put("response", "failed to generate qr code");
		return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/getQrFileDetails", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getQrFileDetails() {
		Map<String, List<QRGeneratedFilesDetailDTO>> responseMap = new HashMap<String, List<QRGeneratedFilesDetailDTO>>();
		responseMap.put("data", qrGeneratedFileDetailService.findAll());
		return new ResponseEntity<Map<String, List<QRGeneratedFilesDetailDTO>>>(responseMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/downloadQRFile/{fileName}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> downloadQRFile(@PathVariable("fileName") String fileName) {
		System.out.println("filename=" + fileName);
		QRBulkZipDownloadResponseParams params = qrGenerateService.getFileDetailTODownload(fileName);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "public");
		headers.add("Expires", "0");
		headers.add("Content-Disposition", "attachment;filename=" + params.getFile().getName());
		headers.add("Content-Transfer-Encoding", "binary");
		headers.add("connection", "keep-alive");

		return ResponseEntity.ok().headers(headers).contentLength(params.getFile().length())
				.contentType(MediaType.parseMediaType("application/x-compressed"))
				// .body(new InputStreamResource(new FileInputStream(file.toFile())));
				.body(params.getByteArrayResource());

	}

	 @RequestMapping(value="/sendQrViaMail", method=RequestMethod.GET)
	 public @ResponseBody ResponseEntity<?> sendQrViaEmail(@RequestParam("fileName") String fileName){
		 getLogger().info("In QR send Via email API\n File Name="+fileName);
		 Integer result=qrGenerateService.sendQrFileViaEMail(fileName);
		 Map<String,String> responseMap=new HashMap<String,String>();
		 if(result==ResponseCode.FILE_DOES_NOT_EXIST) {
			 responseMap.put("response", "File does not exist");
			 return new ResponseEntity<Map<String,String>>(responseMap,HttpStatus.OK);
		 }else if(result==ResponseCode.PROBLEM_SENDING_FILE) {
			 responseMap.put("response", "Problem Sending email");
			 return new ResponseEntity<Map<String,String>>(responseMap,HttpStatus.OK);
		 }else {
			 responseMap.put("response", "Email Sent Successfully");
			 return new ResponseEntity<Map<String,String>>(responseMap,HttpStatus.OK);
		 }
		 
	 }
	 
	 @GetMapping("/getQrMappedUsers")
	  public @ResponseBody ResponseEntity<?> getQrMappedUsers(){
	   Map<String,List<QrMappedUserDetail>> responseMap=new HashMap<String,List<QrMappedUserDetail>>();
	   
	   List<QrMappedUserDetailDTO> listOfUsers=userService.getQrMappedUsers();
	   responseMap.put("data", (List<QrMappedUserDetail>) (List<?>)copyList(listOfUsers, QrMappedUserDetail.class));
		 return new ResponseEntity<Map<String,List<QrMappedUserDetail>>>(responseMap,HttpStatus.OK);
	 }
	 
	 @PostMapping("/mapQrCode")
	  public @ResponseBody ResponseEntity<?> mapQrCodes(@RequestParam CommonsMultipartFile file, HttpSession session){
	   
		 System.out.println(file.getOriginalFilename());
		qrGenerateService.mapQrCodes(file);
		 
		 
		 return new ResponseEntity<>(null,HttpStatus.OK);
	 }
	 

}
