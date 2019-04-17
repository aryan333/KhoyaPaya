/*package com.saifintex.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.paytm.pg.merchant.CheckSumServiceHelper;
import com.saifintex.common.AbstractBase;
import com.saifintex.domain.ClientExceptions;
import com.saifintex.domain.FiscalYear;
import com.saifintex.domain.ItemCategory;
import com.saifintex.domain.PaytmChecksumParam;
import com.saifintex.domain.PaytmChecksumResponse;
import com.saifintex.domain.Transactions;
import com.saifintex.dto.ClientExceptionsDTO;
import com.saifintex.dto.FiscalYearDTO;
import com.saifintex.dto.ItemCategoryDto;
import com.saifintex.dto.TransactionsDTO;
import com.saifintex.services.AppService;
import com.saifintex.services.ClientExceptionsService;
import com.saifintex.services.FiscalYearService;
import com.saifintex.services.ItemCategoryService;
import com.saifintex.services.TransactionService;

@RestController
@RequestMapping("/rest")
public class AppController extends AbstractBase {

	@Autowired
    private AppService appService;

	@Autowired
    private	FiscalYearService fiscalYearService;
	
	@Autowired
	private ItemCategoryService itemCategoryService;

	@Autowired
	private ClientExceptionsService clientExceptionsService;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private TransactionService transactionService;

	@Value("${response.error.client}")
	private String clientErrorResponse;

	
	
	private static String MID = "SaiPro40293747512051"; 
	private static String MercahntKey = "FMdWHv5MuXwUUKTQ";
	private static String INDUSTRY_TYPE_ID = "Retail";
	private static String CHANNLE_ID = "WAP";
	private static String WEBSITE = "APPSTAGING";
	private static String CALLBACK_URL = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
	
	
	@RequestMapping(value = "/public/fetchFiscalYear", method = RequestMethod.GET)
	public ResponseEntity<?> fetchFiscalYear() {
		getLogger().info("fetch fiscal api");
		FiscalYearDTO dto = fiscalYearService.fetchFiscalYear();
		FiscalYear fiscalYear = new FiscalYear();
		BeanUtils.copyProperties(dto, fiscalYear);

		return new ResponseEntity<FiscalYear>(fiscalYear, HttpStatus.OK);
	}

	@RequestMapping(value = "/public/getItemCategoryList", method = RequestMethod.GET)
	public ResponseEntity<?> getItemCategoryList() {
		List<ItemCategoryDto> dtoList = itemCategoryService.getItemCategoryList();
		List<ItemCategory> domainList = new ArrayList<ItemCategory>();
		for (ItemCategoryDto dto : dtoList) {
			ItemCategory domain = new ItemCategory();
			BeanUtils.copyProperties(dto, domain);
			domainList.add(domain);
		}
		return new ResponseEntity<List<ItemCategory>>(domainList, HttpStatus.OK);
	}

	@RequestMapping(value = "/public/storeException", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> clientExceptionHandler(@RequestBody ClientExceptions clientExceptions) {
		getLogger().info("--------storeException api-------------------");
		ClientExceptionsDTO dto = new ClientExceptionsDTO();
		BeanUtils.copyProperties(clientExceptions, dto);
		int id = clientExceptionsService.storeClientExceptionService(dto);
		Map<String, String> responseMap = new HashMap<String, String>();
		if (id != 0) {
			responseMap.put("response", clientErrorResponse);
			return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.OK);
		}
		responseMap.put("response", "some problem occur");
		return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/private/getHomePageData/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getHomePageData(@PathVariable("userId") int userId) {
		getLogger().info("Principal===" + getPrincipal());
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("netPayReceiveBalance",
				transactionService.getTotalPayReceiveAmountWithPendingRequestsCount(userId));
		responseMap.put("cashInOutTotal", transactionService.calculateCashInOut(userId));
		List<TransactionsDTO> transactionDto = transactionService.getTransactionHistory(userId, 0,"all");
		List<Transactions> transactionList = new ArrayList<Transactions>();
		for (TransactionsDTO dto : transactionDto) {

			Transactions transaction = new Transactions();
			BeanUtils.copyProperties(dto, transaction);
			transactionList.add(transaction);
			if (transactionList.size() == 20) {
				break;
			}
		}
		responseMap.put("transactions", transactionList);
		return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.OK);
	}
	
	@PostMapping
	@RequestMapping(value="/public/paytm/gencheck")
	public ResponseEntity<?> generateChecksum(@ModelAttribute PaytmChecksumParam param){
		
		System.out.println("param mobile="+param.getMobileNumber());
		System.out.println("param callback="+param.getCallbackUrl());
		System.out.println("param order="+param.getOrderId());
		String checkSum=generateChecksum1(param);
		PaytmChecksumResponse response=new PaytmChecksumResponse();
		response.setChecksumHash(checkSum);
		response.setOrderId(param.getOrderId());
		response.setPaytStatus("ACCEPT");
		return new ResponseEntity<PaytmChecksumResponse>(response,HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	private String generateChecksum1(PaytmChecksumParam param){
		
		
		TreeMap<String,String> paramMap = new TreeMap<String,String>();
		paramMap.put("MID" , MID);
		paramMap.put("ORDER_ID" , param.getOrderId());
		paramMap.put("CUST_ID" , param.getCustId());
		paramMap.put("INDUSTRY_TYPE_ID" , INDUSTRY_TYPE_ID);
		paramMap.put("CHANNEL_ID" , CHANNLE_ID);
		paramMap.put("TXN_AMOUNT" , param.getTxnAmount());
		paramMap.put("WEBSITE" , WEBSITE);
		//paramMap.put("EMAIL" , "ajay@gmail.com");
		//paramMap.put("MOBILE_NO" , param.getMobileNumber());
		paramMap.put("CALLBACK_URL" , CALLBACK_URL);
		String checkSum=null;
		try{
        checkSum =  CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(MercahntKey, paramMap);
		paramMap.put("CHECKSUMHASH" , checkSum);
		
		System.out.println("Paytm Payload: "+ paramMap);
		
		}catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return checkSum;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
*/