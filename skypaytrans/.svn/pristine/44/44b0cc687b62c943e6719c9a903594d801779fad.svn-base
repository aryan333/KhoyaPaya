package com.saifintex.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.saifintex.services.DataBaseDetailService;
import com.saifintex.web.domain.DataBaseDetail;
import com.saifintex.web.domain.DataBaseTablesDetail;

@Controller
@RequestMapping("/admin")
public class DatabaseDetailController {
	
	@Autowired
	private DataBaseDetailService dataBaseDetailService;
	@RequestMapping(value="databaseDetail",method=RequestMethod.GET)
	public ModelAndView getDataBaseDetail() {
		DataBaseDetail detail=dataBaseDetailService.getDataBaseDetail();
		DataBaseTablesDetail usersTableDetail=dataBaseDetailService.getUserTableDetail();
		DataBaseTablesDetail transactionsTableDetail=dataBaseDetailService.getTransactionsTableDetail();
		List<DataBaseTablesDetail> tablesList=dataBaseDetailService.getAllTablesDetail();
		ModelAndView model=new ModelAndView("databasedetail");
		model.addObject("databaseDetail",detail);
		model.addObject("usersTableDetail",usersTableDetail);
		model.addObject("transactionTableDetail",transactionsTableDetail);
		model.addObject("tablesList",tablesList);
		return model;
	}

}
