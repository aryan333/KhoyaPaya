package com.saifintex.controller.web;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.saifintex.common.AbstractBase;
import com.saifintex.services.TransactionService;
import com.saifintex.utils.DateUtils;
import com.saifintex.web.domain.Transactions;
import com.saifintex.web.domain.TransactionsRecord;
import com.saifintex.web.dto.TransactionsRecordDto;


@Controller
public class TransactionWebController extends AbstractBase{

	@Autowired
	TransactionService transactionService;
	
	@RequestMapping("/admin/transactions")
	public ModelAndView getTransactionDetails(@RequestParam("txnType")String txnType){
		TransactionsRecordDto dto=transactionService.getTransactionsRecord();
		TransactionsRecord domain=new TransactionsRecord();
		if(dto!=null) {
			BeanUtils.copyProperties(dto, domain);
		}					
		ModelAndView model=new ModelAndView("transactions");
		model.addObject("transRecord", domain);
		model.addObject("txnType",txnType);		
		return model;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/admin/transactionsList")
	public @ResponseBody ResponseEntity<?> getTransactions(
			@RequestParam("txnType")String txnType,
			@RequestParam int start,
			@RequestParam int length,
			@RequestParam("search[value]") String search,
			@RequestParam Map<String,String>  columns){
		getLogger().info("-----/admin/transactionsList------");
		com.saifintex.web.dto.TransactionsDTO transactionWebDTO=new com.saifintex.web.dto.TransactionsDTO();
		changeInTransactionDTO(transactionWebDTO, columns);
		
		
		List<com.saifintex.web.dto.TransactionsDTO> dtoList=transactionService.getAllTransactions(txnType, start,length,search,transactionWebDTO);
		List<Transactions> transDomainList=(List<Transactions>)(List<?>)copyList(dtoList, Transactions.class);
		long totalRecord=0;
		if(transDomainList!=null && transDomainList.size()>0) {
			totalRecord=transDomainList.get(0).getTotalRecords();
		}
		
		Map<String,Object> responseMap=new HashMap<String,Object>();
		responseMap.put("data", transDomainList);
		responseMap.put("txnType", txnType);
		responseMap.put("draw", columns.get("draw"));
		responseMap.put("recordsTotal", totalRecord);
		responseMap.put("recordsFiltered", totalRecord);
		
		return new ResponseEntity<Map<String,Object>>(responseMap,HttpStatus.OK);
	}
	
	private void changeInTransactionDTO(com.saifintex.web.dto.TransactionsDTO dto,Map<String,String> columns) {
		dto.setSkyCredit(null);
		dto.setPreviousBalance(null);
		if(columns.get("columns[0][search][value]")!=null && columns.get("columns[0][search][value]").matches("[0-9]+")) {
			dto.setTransactionId(columns.get("columns[0][search][value]"));
		}if(columns.get("columns[1][search][value]")!=null && columns.get("columns[1][search][value]").matches("[a-zA-Z\\s]+")) {
			dto.setPayerName(columns.get("columns[1][search][value]"));
		}if(columns.get("columns[2][search][value]")!=null && columns.get("columns[2][search][value]").matches("[a-zA-Z\\s]+")) {
			dto.setPayeeName(columns.get("columns[2][search][value]"));
		}if(columns.get("columns[3][search][value]")!=null && columns.get("columns[3][search][value]").matches("[a-zA-Z]+")) {
			dto.setPaymentStatus(columns.get("columns[3][search][value]"));
		}if(columns.get("columns[4][search][value]")!=null && columns.get("columns[4][search][value]").matches("[0-9]+(\\.[0-9][0-9]?)?")) {
			dto.setCashReceived(new BigDecimal(columns.get("columns[4][search][value]")));
		}if(columns.get("columns[5][search][value]")!=null && columns.get("columns[5][search][value]").matches("[0-9]+(\\.[0-9][0-9]?)?")) {
			dto.setSkyCredit(new BigDecimal(columns.get("columns[5][search][value]")));
		}
		 if(columns.get("columns[6][search][value]")!=null && columns.get("columns[6][search][value]").matches("[0-9]+(\\.[0-9][0-9]?)?")) {
			dto.setBillAmount(new BigDecimal(columns.get("columns[6][search][value]")));
		}if(columns.get("columns[7][search][value]")!=null && !columns.get("columns[7][search][value]").isEmpty()) {
			String value=columns.get("columns[7][search][value]");
			if(value.equalsIgnoreCase("yes")) {
			dto.setGstEnabled(true);
			}else {
				dto.setGstEnabled(false);
			}
		}
		 
		 
		 if(columns.get("columns[8][search][value]")!=null && columns.get("columns[8][search][value]").matches("[a-zA-Z]+")) {
			dto.setUserType(columns.get("columns[8][search][value]"));
		}if(columns.get("columns[9][search][value]")!=null && columns.get("columns[9][search][value]").trim().matches("^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])")){
			
			String createdOn=columns.get("columns[9][search][value]").trim();
		
			dto.setCreatedOn(DateUtils.getDate(createdOn));
		}if(columns.get("columns[10][search][value]")!=null && columns.get("columns[10][search][value]").trim().matches("^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])")){
			dto.setModifiedOn(DateUtils.getDate(columns.get("columns[10][search][value]").trim()));
		}
	}
	
	
	/**
	 * @param period
	 *            can have 4 String values :- 1. today for current date, 2. yest for
	 *            yesterday ,3. week for week range, 4. start for date range
	 *            starting from first app live date 5. all for starting date to
	 *            current date
	 * @param paymentStatus
	 *            it can be A,R,P,C and all in String
	 * @param includeTestUsers
	 *            a boolean value
	 * @return list of transactions with details
	 */
	@RequestMapping("/admin/getTransactions")
	public @ResponseBody ResponseEntity<Map<String, List<Transactions>>> transactionsHandler(@RequestParam("period") String period,@RequestParam("paymentStatus") String paymentStatus,@RequestParam("testUser") boolean includeTestUsers) {
		getLogger().info("===/getTransactions====");
		List<Transactions> list = transactionService.getTransactionsListDashboard(includeTestUsers, paymentStatus, period);
		Map<String, List<Transactions>> responseMap = new HashMap<String, List<Transactions>>();
		responseMap.put("data", list);
		return new ResponseEntity<Map<String,List<Transactions>>>(responseMap, HttpStatus.OK);		
	}
	
}
