package com.saifintex.controller.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.saifintex.common.AbstractBase;
import com.saifintex.domain.AssociatedUsers;
import com.saifintex.domain.ItemMaster;
import com.saifintex.domain.SalesPurchaseDetail;
import com.saifintex.domain.TotalBillAmount;
import com.saifintex.domain.TransactionActionParams;
import com.saifintex.domain.TransactionSummaryParams;
import com.saifintex.domain.Transactions;
import com.saifintex.domain.TransactionsFilterParams;
import com.saifintex.dto.AssociatedUsersDTO;
import com.saifintex.dto.ItemMasterDTO;
import com.saifintex.dto.SalesPurchaseDetailDto;
import com.saifintex.dto.TransactionSummaryParamsDto;
import com.saifintex.dto.TransactionsDTO;
import com.saifintex.exception.FCMException;
import com.saifintex.exception.FileDoesNotUploadException;
import com.saifintex.services.TransactionService;
import com.saifintex.services.UserService;
import com.saifintex.utils.ApplicationProperties;
import com.saifintex.utils.FcmNotificationUtil;

@RestController
@RequestMapping("/rest")
public class TransactionRestController extends AbstractBase {
	@Autowired
	TransactionService transactionService;
	@Autowired
	ApplicationProperties properties;

	@Autowired
	UserService userService;

	@Value("${app.FCMAuthKey}")
	public String authKey;

	@Value("${cant.do.transaction.with.self}")
	public String notAllowedSelfTransaction;

	@Value("${no.transaction.found}")
	public String noTransactionFound;
	
	

	@Autowired
	MessageSource messageSource;
	/*private static final ReentrantLock lock = new ReentrantLock();*/

	/* Store Transaction API Created By Ajay */
	@RequestMapping(value = "/private/storetransaction", method = RequestMethod.POST)
	public ResponseEntity<?> insertTransaction(@Valid @RequestBody Transactions requestTransactions,
			BindingResult results) {
		getLogger().info("-------------------store transaction api----------------");
		Map<String, String> responseMap = new HashMap<String, String>();
		if (requestTransactions.getPayerId() == requestTransactions.getPayeeId()) {
			responseMap.put("response", notAllowedSelfTransaction);
			return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.BAD_REQUEST);
		}
		if (results.hasErrors()) {
			FieldError error = results.getFieldError();
			String message = messageSource.getMessage(error, null);
			responseMap.put("response", error.getField() + " " + message);
			return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.BAD_REQUEST);
		}
		TransactionsDTO transactionsDTO = new TransactionsDTO();
		BeanUtils.copyProperties(requestTransactions, transactionsDTO);

		TransactionsDTO params = null;

		synchronized (this) {
			params = transactionService.insertTransaction(transactionsDTO);
		}
		Transactions transactions = new Transactions();

		BeanUtils.copyProperties(params, transactions);
     	getLogger().info("response committed for store txn api ---");
		return new ResponseEntity<Transactions>(transactions, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/private/storetransactioninvoice", method = RequestMethod.POST)
	public ResponseEntity<?> insertTransactionWithInvoice(@Valid @ModelAttribute Transactions requestTransactions,
			BindingResult results) {
		getLogger().info("-------------------store transaction api----------------");
		Map<String, String> responseMap = new HashMap<String, String>();
		if (requestTransactions.getPayerId() == requestTransactions.getPayeeId()) {
			responseMap.put("response", notAllowedSelfTransaction);
			return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.BAD_REQUEST);
		}
		if (results.hasErrors()) {
			FieldError error = results.getFieldError();
			String message = messageSource.getMessage(error, null);
			responseMap.put("response", error.getField() + " " + message);
			return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.BAD_REQUEST);
		}
		
		System.out.println("GST ENabled=="+requestTransactions.isGSTEnabled());
		if(requestTransactions.getFile()!=null && !requestTransactions.getFile().getOriginalFilename().isEmpty()) {
			String fileName = requestTransactions.getFile().getOriginalFilename();
			if (!fileName.endsWith("jpg") && !fileName.endsWith("png") && !fileName.endsWith("gif") && !fileName.endsWith("jpeg")) {
				responseMap.put("response", "please select valid file(JPG,JPEG,PNG)");
				return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.BAD_REQUEST);
			}
		}
		
		TransactionsDTO transactionsDTO = new TransactionsDTO();
		BeanUtils.copyProperties(requestTransactions, transactionsDTO);

		TransactionsDTO params = null;
		try {
		synchronized (this) {
			params = transactionService.insertTransaction(transactionsDTO);
		}
		Transactions transactions = new Transactions();

		BeanUtils.copyProperties(params, transactions);
     	getLogger().info("response committed for store txn api ---");
		return new ResponseEntity<Transactions>(transactions, HttpStatus.OK);
		}catch (FileDoesNotUploadException e) {
			return new ResponseEntity<String>("Error uploading file", HttpStatus.BAD_REQUEST);
		}
	}
	

	@RequestMapping(value = "/private/rejectTransaction", method = RequestMethod.POST)
	public ResponseEntity<?> rejectTransactionHandler(@RequestBody TransactionActionParams transactionActionParams) {
		getLogger().info("-----------reject transaction api------------");
		getLogger().info("client id who rejected -- " + transactionActionParams.getUserId());
		Map<String, String> responseMap = new HashMap<String, String>();
		TransactionsDTO dto = null;
		synchronized (this) {

			dto = transactionService.rejectTransaction(transactionActionParams);
		}
		if (dto == null) {
			responseMap.put("response", "failure");
			return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.BAD_REQUEST);
		}
		Transactions txnDomain = new Transactions();
		BeanUtils.copyProperties(dto, txnDomain);
		
		/*String token = null;
		
		int userId = 0;
		if (transactionActionParams.getUserId() == txnDomain.getPayerId()) {
			userId = txnDomain.getPayeeId();
		} else {
			userId = txnDomain.getPayerId();
		}
		token = userService.getToken(userId);
		if (token != null && !token.isEmpty()) {
			JsonObject data = new JsonObject();
			data.addProperty("type", "transactionHistory");
			data.addProperty("phoneNumber", transactionActionParams.getRecipientMobileNumber());
			data.addProperty("payId", txnDomain.getPayId());
			String message = null;
			if (txnDomain.getBillAmount().compareTo(BigDecimal.ZERO) == 1) {
				message = properties.getRejectWithBillAmount() + "" + txnDomain.getBillAmount() + "";
			} else {
				message = properties.getRejectWithTotalAmount() + " " + txnDomain.getCashReceived() + "";
			}
			try {
				FcmNotificationUtil.sendNotification(token, message, authKey, data);
			} catch (FCMException e) {
				getLogger().error("fcm notification exception", e.fillInStackTrace());
			}
		}

*/		return new ResponseEntity<Transactions>(txnDomain, HttpStatus.OK);

	}

	@RequestMapping(value = "/private/acceptTransaction", method = RequestMethod.POST)
	public ResponseEntity<?> acceptTransactionHandler(@RequestBody TransactionActionParams transactionActionParams) {
		getLogger().info(
				"=======================accept transaction api===================================================");

		Map<String, String> responseMap = new HashMap<String, String>();
		TransactionsDTO txnDto = null;
		synchronized (this) {
			txnDto = transactionService.acceptTransactionService(transactionActionParams);
		}
		if (txnDto == null) {
			responseMap.put("response", "failure");
			return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.BAD_REQUEST);
		}
		Transactions txnDomain = new Transactions();
		BeanUtils.copyProperties(txnDto, txnDomain);
		/*String token = null;
		int userId = 0;
		if (transactionActionParams.getUserId() == txnDomain.getPayerId()) {
			userId = txnDomain.getPayeeId();
		} else {
			userId = txnDomain.getPayerId();
		}
		
		token = userService.getToken(userId);
		getLogger().info("token == " + token);
		if (token != null && !token.isEmpty()) {

			JsonObject data = new JsonObject();
			data.addProperty("type", "transactionHistory");
			data.addProperty("payId", txnDomain.getPayId());
			data.addProperty("phoneNumber", transactionActionParams.getRecipientMobileNumber());
			String message = null;
			if (txnDomain.getBillAmount().compareTo(BigDecimal.ZERO) == 1) {
				message = properties.getAcceptWithBillAmount() + " " + txnDomain.getBillAmount() + "";
			} else {
				message = properties.getAcceptWithTotalAmount() + " " + txnDomain.getCashReceived() + "";
			}
			try {
				FcmNotificationUtil.sendNotification(token, message, authKey, data);
			} catch (FCMException e) {
				getLogger().error("fcm notification exception", e.fillInStackTrace());
			}
		}*/
		getLogger().info("response committed for accept txn api");
		return new ResponseEntity<Transactions>(txnDomain, HttpStatus.OK);
	}

	@RequestMapping(value = "/private/getNetPayReceiveBalance/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> totalPayReceiveHandler(@PathVariable int userId) {
		getLogger().info("==========getNetPayReceiveBalance api========================");
		getLogger().info("userId from client = " + userId);
		Map<String, String> responseMap = transactionService.getTotalPayReceiveAmountWithPendingRequestsCount(userId);
		if (responseMap == null) {
			Map<String, String> response = new HashMap<String, String>();
			response.put("response", "no transaction record found");
			new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/private/mutualAmountWithAsscociatedUsers/{userId}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, List<AssociatedUsers>>> mutualAmountPayReceiveHandler(@PathVariable int userId,
			@PathVariable int pageSize) {
		getLogger().info("========= payReceive api(mutualAmountWithAsscociatedusers) ========================");
		getLogger().info("userid from client side : " + userId);
		Map<String, List<AssociatedUsers>> responseMap = new HashMap<String, List<AssociatedUsers>>();
		List<AssociatedUsersDTO> dtoList = transactionService.getPayReceiveWithAssociatedUsersInfo(userId, pageSize);
		List<AssociatedUsers> associatedUsersList = new ArrayList<AssociatedUsers>();
		if (dtoList == null) {
			responseMap.put("response", associatedUsersList);
			return new ResponseEntity<Map<String, List<AssociatedUsers>>>(responseMap, HttpStatus.OK);
		}
		for (AssociatedUsersDTO dto : dtoList) {
			AssociatedUsers associatedUsers = new AssociatedUsers();
			BeanUtils.copyProperties(dto, associatedUsers);
			associatedUsersList.add(associatedUsers);
		}
		responseMap.put("response", associatedUsersList);
		return new ResponseEntity<Map<String, List<AssociatedUsers>>>(responseMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/private/getCashInOutTotal/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, String>> cashInOutHandler(@PathVariable int userId) {
		getLogger().info("===================== getCashInOutTotal api ================");
		getLogger().info("userId from client is : " + userId);
		Map<String, String> responseMap = transactionService.calculateCashInOut(userId);
		return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.OK);

	}

	/* Transaction History API Created By Aryan */
	@RequestMapping(value = "/private/transactionHistory/{userId}/{pageSize}/{type}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, List<Transactions>>> getTransactionHistory(@PathVariable int userId,
			@PathVariable int pageSize, @PathVariable String type) {
		getLogger().info("=============transaction history ========================================");
		getLogger()
				.info("user id , page size and txn type from client is :" + userId + " , " + pageSize + " , " + type);
		List<TransactionsDTO> transactionsDto = transactionService.getTransactionHistory(userId, pageSize, type);
		List<Transactions> list = new ArrayList<Transactions>();
		Map<String, List<Transactions>> responseMap = new HashMap<String, List<Transactions>>();
		if (transactionsDto == null || transactionsDto.size() == 0) {
			getLogger().info("no transaction history found ");
			responseMap.put("response", list);
			return new ResponseEntity<Map<String, List<Transactions>>>(responseMap, HttpStatus.OK);
		}

		for (TransactionsDTO txnDto : transactionsDto) {
			Transactions transactions = new Transactions();
			BeanUtils.copyProperties(txnDto, transactions);
			list.add(transactions);
		}

		responseMap.put("response", list);
		return new ResponseEntity<Map<String, List<Transactions>>>(responseMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/private/getTransaction/{userId}/{payId}", method = RequestMethod.GET)
	public ResponseEntity<Transactions> getTransactionByIdHandler(@PathVariable int userId, @PathVariable long payId) {
		getLogger().info("=============getTransactionById ========================================");
		getLogger().info("payId from client == " + payId);
		getLogger().info("user id == " + userId);
		TransactionsDTO transactionDto = transactionService.getTransactionByPayIdWithUserInfo(userId, payId);
		Transactions transactions = null;
		if (transactionDto != null) {
			transactions = new Transactions();
			BeanUtils.copyProperties(transactionDto, transactions);
			return new ResponseEntity<Transactions>(transactions, HttpStatus.OK);
		}
		return new ResponseEntity<Transactions>(transactions, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/private/pendingTransactions", method = RequestMethod.POST)
	public ResponseEntity<?> pendingTransactionsHandler(@RequestBody Map<String, String> requestMap) {
		getLogger().info(
				"========================pending transaction api ====================================================");
		int userId = Integer.parseInt(requestMap.get("userId"));
		int pageSize = Integer.parseInt(requestMap.get("pageSize"));
		getLogger().info("pageSize == " + pageSize + "\nuserId === " + userId);
		List<TransactionsDTO> transactionsDTO = transactionService.getPendingTransactions(userId, pageSize);

		List<Transactions> txnDomainList = null;
		Map<String, List<Transactions>> responseMap = new HashMap<String, List<Transactions>>();
		if (transactionsDTO == null) {

			responseMap.put("response", txnDomainList);
			return new ResponseEntity<Map<String, List<Transactions>>>(responseMap, HttpStatus.BAD_REQUEST);
		}

		txnDomainList = copyDtoListInDomain(transactionsDTO);

		responseMap.put("response", txnDomainList);
		return new ResponseEntity<Map<String, List<Transactions>>>(responseMap, HttpStatus.OK);

	}

	private List<Transactions> copyDtoListInDomain(List<TransactionsDTO> dtoList) {
		List<Transactions> list = new ArrayList<Transactions>();
		for (TransactionsDTO dto : dtoList) {
			Transactions txnDomain = new Transactions();
			BeanUtils.copyProperties(dto, txnDomain);

			list.add(txnDomain);
		}
		return list;
	}

	@RequestMapping(value = "/private/getAssociatedUsers/{userId}/{pageSize}", method = RequestMethod.GET)
	public @ResponseBody Map<String, List<AssociatedUsers>> getDueOutBalance(@PathVariable("userId") int userId,
			@PathVariable("pageSize") int pageSize) {
		getLogger().info("---------------Associated API------------");
		getLogger().info("user ID == " + userId);
		List<AssociatedUsersDTO> list = transactionService.getAssociatedUsers(userId, pageSize);
		List<AssociatedUsers> associatedUsersList = new ArrayList<AssociatedUsers>();
		for (AssociatedUsersDTO dto : list) {
			AssociatedUsers associatedUsers = new AssociatedUsers();
			BeanUtils.copyProperties(dto, associatedUsers);
			associatedUsersList.add(associatedUsers);
		}
		Map<String, List<AssociatedUsers>> responseMap = new HashMap<String, List<AssociatedUsers>>();
		responseMap.put("response", associatedUsersList);
		return responseMap;
	}

	@RequestMapping(value = "/private/getLedger/{loggedInUserId}/{oppositeUserId}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<?> getLedger(@PathVariable("loggedInUserId") int loggedInUserId,
			@PathVariable("oppositeUserId") int oppositeUserId, @PathVariable("pageSize") int pageSize) {
		getLogger().info("---------------LEDGER API------------");
		getLogger().info("logged In user Id=" + loggedInUserId);
		getLogger().info("opposite User Id=" + oppositeUserId);

		List<TransactionsDTO> dtoList = transactionService.getLedger(loggedInUserId, oppositeUserId, pageSize);
		List<Transactions> transactionsList = new ArrayList<Transactions>();
		for (TransactionsDTO dto : dtoList) {
			Transactions transactions = new Transactions();
			BeanUtils.copyProperties(dto, transactions);
			transactionsList.add(transactions);
		}
		TransactionSummaryParamsDto transactionSummaryParamsDto = transactionService.transactionSummary(loggedInUserId,
				oppositeUserId);
		TransactionSummaryParams transSummaryParams = new TransactionSummaryParams();
		BeanUtils.copyProperties(transactionSummaryParamsDto, transSummaryParams);

		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("response", transactionsList);
		responseMap.put("transactionSummary", transSummaryParams);
		return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/public/fetchitem/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> fetchItemHandler(@PathVariable int userId) {
		getLogger().info("------fetch items api--------------------");
		List<ItemMasterDTO> itemMasterDTO = transactionService.fetchItem(userId);
		Map<String, Object> responsemap = new HashMap<String, Object>();
		if (itemMasterDTO.isEmpty()) {
			responsemap.put("response", "no item found");
			return new ResponseEntity<Map<String, Object>>(responsemap, HttpStatus.OK);
		}
		List<ItemMaster> itemMasterList = new ArrayList<ItemMaster>();
		for (ItemMasterDTO itemMaster2 : itemMasterDTO) {
			ItemMaster itemMaster = new ItemMaster();
			BeanUtils.copyProperties(itemMaster2, itemMaster);
			itemMasterList.add(itemMaster);
		}
		responsemap.put("reponse", itemMasterList);
		return new ResponseEntity<Map<String, Object>>(responsemap, HttpStatus.OK);
	}

	@RequestMapping(value = "/private/getRecentUsers/{userId}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<?> recentUsersHandler(@PathVariable int userId, @PathVariable int pageSize) {
		getLogger().info("========== recent users api =====================");
		getLogger().info("client id : " + userId);
		List<AssociatedUsersDTO> list = transactionService.recentUsersService(userId, pageSize);

		if (list == null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("response", noTransactionFound);
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
		}
		Map<String, List<AssociatedUsers>> responseMap = new HashMap<String, List<AssociatedUsers>>();
		List<AssociatedUsers> domainList = copyListInDomain(list);
		responseMap.put("response", domainList);
		return new ResponseEntity<Map<String, List<AssociatedUsers>>>(responseMap, HttpStatus.OK);
	}

	@RequestMapping(value = "/private/generateInvoice", method = RequestMethod.POST)
	public ResponseEntity<?> generateGSTInvoiceHandler(@RequestBody Map<String, String> requestMap) {
		getLogger().info("//////////////////////////////////////////////////////////////////////");
		getLogger().info("============generate invoice api==========================");
		int userId = Integer.parseInt(requestMap.get("userId"));
		String timePeriod = requestMap.get("period");
		String flag = requestMap.get("flag");
		getLogger().info("client id : " + userId + "\ntime period : " + timePeriod);
		if (flag.equals("date")) {
			getLogger().info("generate date wise !!");
			List<TransactionsDTO> list = transactionService.dateWiseTransactionServiceForInvoice(userId, timePeriod);
			Map<String, List<Transactions>> responseMap = new HashMap<String, List<Transactions>>();
			if (list != null) {
				List<Transactions> txnDomainList = copyDtoListInDomain(list);
				responseMap.put("response", txnDomainList);
				return new ResponseEntity<Map<String, List<Transactions>>>(responseMap, HttpStatus.OK);
			}
		} else if (flag.equals("month")) {
			getLogger().info("generate particular month !!");
			List<TotalBillAmount> listBillAmount = transactionService.monthWiseTransactionServiceForInvoice(userId,
					timePeriod);
			if (listBillAmount != null) {
				Map<String, List<TotalBillAmount>> responseMap = new HashMap<String, List<TotalBillAmount>>();
				responseMap.put("response", listBillAmount);
				return new ResponseEntity<Map<String, List<TotalBillAmount>>>(responseMap, HttpStatus.OK);
			}
		} else if (flag.equals("quarter")) {
			getLogger().info("generate quarter wise !!");
			List<TotalBillAmount> listBillAmount = transactionService.quarterWiseTransactionSrevice(userId, timePeriod);
			if (listBillAmount != null) {
				Map<String, List<TotalBillAmount>> responseMap = new HashMap<String, List<TotalBillAmount>>();
				responseMap.put("response", listBillAmount);
				return new ResponseEntity<Map<String, List<TotalBillAmount>>>(responseMap, HttpStatus.OK);
			}
		}
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("response", "no data found");
		return new ResponseEntity<Map<String, String>>(responseMap, HttpStatus.BAD_REQUEST);
	}

	private List<AssociatedUsers> copyListInDomain(List<AssociatedUsersDTO> list) {
		List<AssociatedUsers> domainList = new ArrayList<AssociatedUsers>();
		for (AssociatedUsersDTO dto : list) {
			AssociatedUsers domain = new AssociatedUsers();
			BeanUtils.copyProperties(dto, domain);
			domainList.add(domain);
		}
		return domainList;
	}

	@RequestMapping(value = "/private/getTodayTransactions/{userId}/{pageSize}/{type}")
	public ResponseEntity<Map<String, List<Transactions>>> getCurrentDateTransactionsHandler(@PathVariable int userId,
			@PathVariable int pageSize, @PathVariable String type) {
		getLogger().info("==========/getTodayTransactions/====");
		getLogger().info("user id ,page size and type === " + userId + ", " + pageSize + ", " + type);
		List<TransactionsDTO> dtos = transactionService.getTodayTransactions(userId, pageSize, type);
		Map<String, List<Transactions>> responseMap = new HashMap<String, List<Transactions>>();
		List<Transactions> responseList = new ArrayList<Transactions>();
		if (dtos != null || !dtos.isEmpty()) {
			for (TransactionsDTO dto : dtos) {
				Transactions txn = new Transactions();
				BeanUtils.copyProperties(dto, txn);
				responseList.add(txn);
			}
		}
		responseMap.put("response", responseList);
		return new ResponseEntity<Map<String, List<Transactions>>>(responseMap, HttpStatus.OK);
	}
	
	@RequestMapping(value="/private/filter/{pageSize}")
	public ResponseEntity<?> filterTransactions(@ModelAttribute TransactionsFilterParams params,@PathVariable int pageSize){
		List<TransactionsDTO> transactionsDto = transactionService.filterTransactions(params, pageSize);
		List<Transactions> list = new ArrayList<Transactions>();
		Map<String, List<Transactions>> responseMap = new HashMap<String, List<Transactions>>();
		if (transactionsDto == null || transactionsDto.size() == 0) {
			getLogger().info("no transaction history found ");
			responseMap.put("response", list);
			return new ResponseEntity<Map<String, List<Transactions>>>(responseMap, HttpStatus.OK);
		}

		for (TransactionsDTO txnDto : transactionsDto) {
			Transactions transactions = new Transactions();
			BeanUtils.copyProperties(txnDto, transactions);
			list.add(transactions);
		}

		responseMap.put("response", list);
		return new ResponseEntity<Map<String, List<Transactions>>>(responseMap, HttpStatus.OK);
	
	}
	
	@RequestMapping(value="/private/getsalespurchaseamount/{userId}/{period}")
	public ResponseEntity<?> getSalesPurchaseAmount(@PathVariable("userId") int userId,@PathVariable("period") String period){
		SalesPurchaseDetailDto dto=transactionService.getSalesPurchaseAmount(userId, period);
		SalesPurchaseDetail detail=new SalesPurchaseDetail();
		BeanUtils.copyProperties(dto, detail);
		
		return new ResponseEntity<SalesPurchaseDetail>(detail,HttpStatus.OK);
	}

}
