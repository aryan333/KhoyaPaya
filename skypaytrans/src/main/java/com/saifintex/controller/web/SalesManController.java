package com.saifintex.controller.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.saifintex.common.AbstractBase;
import com.saifintex.services.UserService;
import com.saifintex.web.domain.LatLng;
import com.saifintex.web.domain.SalesPersonReferalInfo;
import com.saifintex.web.domain.UsersList;
import com.saifintex.web.dto.LatLngDTO;
import com.saifintex.web.dto.MerchantInfoBySPDTO;
import com.saifintex.web.dto.SalesInfoDTO;
import com.saifintex.web.dto.TotalReferalUserBySPDTO;
import com.saifintex.web.dto.UsersListDTO;

@Controller
@RequestMapping("/admin")

public class SalesManController extends AbstractBase {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/salesInfo")
	public ModelAndView viewMasterTable() {

		List<SalesPersonReferalInfo> salesInfo = userService.salesPersonReferalInfo();
		TotalReferalUserBySPDTO totalReferalUserBySPDTO = userService.getTotalUsersBySP();

		System.out.println(totalReferalUserBySPDTO.getOverAllTotalCount());

		return new ModelAndView("salesInfo")/* .addObject("userStats", report).addObject("transStats", transStats) */
				.addObject("dateParams", getDashboardDateParamsInDDMMYYYYFormat())
				.addObject("salesRefDetails", salesInfo).addObject("totaluser", totalReferalUserBySPDTO);
	}

	@RequestMapping(value = "/salesInfoSA")
	public ModelAndView salesInfoHandlerSuperAdmin() {

		List<SalesPersonReferalInfo> salesInfo = userService.salesPersonReferalInfo();
		TotalReferalUserBySPDTO totalReferalUserBySPDTO = userService.getTotalUsersBySP();

		System.out.println(totalReferalUserBySPDTO.getOverAllTotalCount());

		return new ModelAndView("salesInfo-super-admin")/*
														 * .addObject("userStats", report).addObject("transStats",
														 * transStats)
														 */
				.addObject("dateParams", getDashboardDateParamsInDDMMYYYYFormat())
				.addObject("salesRefDetails", salesInfo).addObject("totaluser", totalReferalUserBySPDTO);
	}

	@RequestMapping(value = "/getReferalUsers/{period}")
	public @ResponseBody Map<String, List<UsersList>> referalUsersHandler(@PathVariable("period") String period,
			@RequestParam("inviteCode") String inviteCode, @RequestParam("userType") String userType) {
		
		getLogger().info("=====/getReferalUsers/" + period + " == " + inviteCode + "  ==" + userType);
		List<UsersListDTO> dtoList = userService.getReferalUsersService(period, inviteCode, userType);
		Map<String, List<UsersList>> responseMap = new HashMap<String, List<UsersList>>();
		List<UsersList> userList = new ArrayList<UsersList>();
		
		if (dtoList != null && !dtoList.isEmpty()) {
			for (UsersListDTO dto : dtoList) {
				UsersList user = new UsersList();
				BeanUtils.copyProperties(dto, user);
				userList.add(user);
			}
		}
		responseMap.put("data", userList);
		return responseMap;
	}

	@RequestMapping(value = "/getAllBySPReferalUsers/{period}")
	public @ResponseBody Map<String, List<UsersList>> allBySPreferalUsersHandler(@PathVariable("period") String period,
			@RequestParam("userType") String userType) {
		getLogger().info("=====/getReferalUsers/" + period + " == " + userType);
		List<UsersListDTO> dtoList = userService.getAllBySPReferalUsersService(period, userType);
		Map<String, List<UsersList>> responseMap = new HashMap<String, List<UsersList>>();
		List<UsersList> userList = new ArrayList<UsersList>();
		if (dtoList != null && !dtoList.isEmpty()) {
			for (UsersListDTO dto : dtoList) {
				UsersList user = new UsersList();
				BeanUtils.copyProperties(dto, user);
				userList.add(user);
			}
		}
		responseMap.put("data", userList);

		return responseMap;
	}

	@RequestMapping(value = "/getAllRegisteredUserBySPDateRangeLatLng/{dateRange}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<LatLng>> latLngHandler(@PathVariable("dateRange") String dateRange,
			@RequestParam("userType") String userType) {
		getLogger().info("========/getAllRegisteredUserBySPDateRangeLatLng=========" + dateRange +" ==" +userType+"==");
		List<LatLngDTO> list = userService.getAllRegisteredUserBySPLatLng(dateRange,userType);
		List<LatLng> responseList = null;
		if (list != null && !list.isEmpty()) {
			responseList = new ArrayList<LatLng>();
			for (LatLngDTO dto : list) {
				LatLng latLng = new LatLng();
				BeanUtils.copyProperties(dto, latLng);
				responseList.add(latLng);
			}
		}

		return new ResponseEntity<List<LatLng>>(responseList, HttpStatus.OK);
	}

	
	
	@RequestMapping(value = "/registeredreferaluserbyspInDaterangemap/{dateRange}", method = RequestMethod.GET)
	public ModelAndView blupayUsersmapHandler(@PathVariable("dateRange") String dateRange,
			@RequestParam("userType") String userType)
			 {
		getLogger().info("=====/registeredreferaluserbyspInDaterangemap/" + dateRange + " =="+ userType +" == ");
		return new ModelAndView("registeredreferaluserbyspInDaterangemap").addObject("dateRange", dateRange)
				.addObject("userType", userType);

	}
	
	


	@RequestMapping(value = "/indivualreferaluserbyspmap/{period}", method = RequestMethod.GET)
	public ModelAndView indivualReferalUserBySpMapHandler(@PathVariable("period") String period,
			@RequestParam("inviteCode") String inviteCode, @RequestParam("userType") String userType) {
		getLogger().info("=====/getReferalUsers/" + period + " == " + inviteCode + "  ==" + userType);
		return new ModelAndView("indivualreferaluserbyspmap").addObject("inviteCode", inviteCode)
				.addObject("userType", userType).addObject("period", period);

	}

	@RequestMapping(value = "/getIndivualreferaluserbyspmap/{period}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> ReferalUsersHandler(@PathVariable("period") String period,
			@RequestParam("inviteCode") String inviteCode, @RequestParam("userType") String userType) {
		getLogger().info("=====/getIndivualreferaluserbyspmap/" + period + " == " + inviteCode + "  ==" + userType);
		List<UsersListDTO> dtoList = userService.getReferalUsersLatLngService(period, inviteCode, userType);
		List<UsersList> userList = new ArrayList<UsersList>();
		if (dtoList != null && !dtoList.isEmpty()) {
			for (UsersListDTO dto : dtoList) {
				UsersList user = new UsersList();
				BeanUtils.copyProperties(dto, user);
				userList.add(user);
			}
		}

		return new ResponseEntity<List<UsersList>>(userList, HttpStatus.OK);

	}

	// through this method we can get all salesperson data from last to till date

	@RequestMapping(value = "/filterSlaesPersonData")
	public ModelAndView viewFilterSalesPersonData() {

		getLogger().info("=====/FilterSalesPersonDataInDateRange/");

		List<SalesPersonReferalInfo> salesInfo = userService.salesPersonReferalInfo();

		return new ModelAndView("filterSlaesPersonData")/*
														 * .addObject("userStats", report).addObject("transStats",
														 * transStats)
														 */
				.addObject("dateParams", getDashboardDateParamsInDDMMYYYYFormat())
				.addObject("salesRefDetails", salesInfo);
	}

	@RequestMapping(value = "/filterSlaesPersonDataSA")
	public ModelAndView viewFilterSalesPersonDataSA() {

		getLogger().info("=====/FilterSalesPersonDataInDateRange/");

		List<SalesPersonReferalInfo> salesInfo = userService.salesPersonReferalInfo();

		return new ModelAndView("filterSlaesPersonDataSA")/*
														 * .addObject("userStats", report).addObject("transStats",
														 * transStats)
														 */
				.addObject("dateParams", getDashboardDateParamsInDDMMYYYYFormat())
				.addObject("salesRefDetails", salesInfo);
	}

	// through this method we can filter in date range referalUsers salesPerson Data

	@RequestMapping(value = "/getSalesPersonDataInDateRange/{startDate}/{endDate}")
	public @ResponseBody ResponseEntity<?> getSalesPersonDataInDateRangeHandler(
			@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {
		getLogger().info("=====/getSalesPersonDataInDateRange/" + startDate + " == " + endDate);
		List<SalesPersonReferalInfo> List = userService.getSalesPersonDataInDateRange(startDate, endDate);
		Map<String, List<SalesPersonReferalInfo>> responseMap = new HashMap<String, List<SalesPersonReferalInfo>>();

		responseMap.put("data", List);
		return new ResponseEntity<Map<String, List<SalesPersonReferalInfo>>>(responseMap, HttpStatus.OK);
	}

	// through this method we can filter in date range referalUsers that are refered
	// by salesPerson

	@RequestMapping(value = "/getReferalUsersInDateRange/{startDate}/{endDate}")
	public @ResponseBody Map<String, List<UsersList>> getReferalUsersInDateRangeHandler(
			@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate,
			@RequestParam("inviteCode") String inviteCode, @RequestParam("userType") String userType) {
		getLogger().info("=====/getReferalUsersInDateRange/" + startDate + " ===" + endDate + " == " + inviteCode
				+ "  ==" + userType);
		List<UsersListDTO> dtoList = userService.getReferalUsersInDateRangeService(startDate, endDate, inviteCode,
				userType);
		Map<String, List<UsersList>> responseMap = new HashMap<String, List<UsersList>>();
		List<UsersList> userList = new ArrayList<UsersList>();
		if (dtoList != null && !dtoList.isEmpty()) {
			for (UsersListDTO dto : dtoList) {
				UsersList user = new UsersList();
				BeanUtils.copyProperties(dto, user);
				userList.add(user);
			}
		}
		responseMap.put("data", userList);
		return responseMap;
	}

	// through this method we call the indivualfilterreferaluserbyspmap jsp and sent
	// usertype, invitecode of person , startDate,endDate

	@RequestMapping(value = "/indivualfilterreferaluserbyspmap/{startDate}/{endDate}", method = RequestMethod.GET)
	public ModelAndView indivualfilterreferaluserbyspMapHandler(@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate, @RequestParam("inviteCode") String inviteCode,
			@RequestParam("userType") String userType) {
		getLogger().info("=====/indivualfilterreferaluserbyspmap/" + startDate + " ==" + endDate + "== " + inviteCode
				+ "  ==" + userType);
		return new ModelAndView("indivualfilterreferaluserbyspmap").addObject("inviteCode", inviteCode)
				.addObject("userType", userType).addObject("startDate", startDate).addObject("endDate", endDate);

	}

	// through this method we can filter in date range referalUsers map location
	// that are refered by salesPerson

	@RequestMapping(value = "/getIndivualfilterreferaluserbyspmap/{startDate}/{endDate}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> Indivualfilterreferaluser(@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate, @RequestParam("inviteCode") String inviteCode,
			@RequestParam("userType") String userType) {
		getLogger().info("=====/getIndivualfilterreferaluserbyspmap/" + startDate + " ==" + endDate + "== " + inviteCode
				+ "  ==" + userType);
		List<UsersListDTO> dtoList = userService.getIndivualfilterreferaluserLatLngService(startDate, endDate,
				inviteCode, userType);

		List<UsersList> userList = new ArrayList<UsersList>();
		if (dtoList != null && !dtoList.isEmpty()) {
			for (UsersListDTO dto : dtoList) {
				UsersList user = new UsersList();
				BeanUtils.copyProperties(dto, user);
				userList.add(user);
			}
		}

		return new ResponseEntity<List<UsersList>>(userList, HttpStatus.OK);

	}

	@RequestMapping(value = "/MerchantInfo")
	public ModelAndView viewMerchantInfo() {

		getLogger().info("MerchantInfo");
		

		List<MerchantInfoBySPDTO> merchantInfo = userService.merchantInfoBySalesPerson("0");
		List<SalesInfoDTO> salePersonInfo = userService.salesPersonInfo();

		return new ModelAndView("MerchantInfo").addObject("merchnatInfoBySp", merchantInfo).addObject("salePersonInfo",
				salePersonInfo);/* .addObject("userStats", report).addObject("transStats", transStats) */

	}
	
	@RequestMapping(value = "/MerchantInfoSA")
	public ModelAndView viewMerchantInfoSA() {

		getLogger().info("MerchantInfoSA");
		

		List<MerchantInfoBySPDTO> merchantInfo = userService.merchantInfoBySalesPerson("0");
		List<SalesInfoDTO> salePersonInfo = userService.salesPersonInfo();

		return new ModelAndView("MerchantInfoSA").addObject("merchnatInfoBySp", merchantInfo).addObject("salePersonInfo",
				salePersonInfo);/* .addObject("userStats", report).addObject("transStats", transStats) */

	}

	@RequestMapping(value = "/getMerchantInfo")
	public @ResponseBody ResponseEntity<?> getMerchantInfoHandler(@RequestParam("userId") String userId) {
		getLogger().info("=====/getMerchantInfo/" + userId);

		List<MerchantInfoBySPDTO> merchantInfo = userService.merchantInfoBySalesPerson(userId);
		Map<String, List<MerchantInfoBySPDTO>> responseMap = new HashMap<String, List<MerchantInfoBySPDTO>>();

		responseMap.put("data", merchantInfo);

		return new ResponseEntity<Map<String, List<MerchantInfoBySPDTO>>>(responseMap, HttpStatus.OK);
	}

	
	// for all merchant location by salesperson through mobile app
	
	
	
	
	@RequestMapping(value = "/allmerchantLocationbyindiviualspmap", method = RequestMethod.GET)
	public ModelAndView indivualReferalUserBySpMapHandler(@RequestParam("userId") String userId) {
		getLogger().info("=====/AllMerchantLocationByIndivual SP/=="+userId+"");
		return new ModelAndView("allmerchantLocationbyindiviualspmap").addObject("userId", userId);
	

}
	
	@RequestMapping(value = "/getAllMerchantbyIndiviualspmap", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> ReferalUsersHandler(@RequestParam("userId") int userId) {
		getLogger().info("=====/AllMerchantbyIndiviualspmap/  =="+userId+"");
		List<LatLngDTO> dtoList = userService.getAllMerchantbyIndiviualspmap(userId);
		Map<String, List<LatLngDTO>> responseMap = new HashMap<String, List<LatLngDTO>>();
		List<LatLng> latlngList = new ArrayList<LatLng>();
		if (dtoList != null && !dtoList.isEmpty()) {
			for (LatLngDTO dto : dtoList) {	
				System.out.println("controler mai "+dtoList.size());
				LatLng user = new LatLng();
				BeanUtils.copyProperties(dto, user);
				System.out.println("name is "+dto.getTitle());
				System.out.println("name is "+dto.getLat());
				latlngList.add(user);
			}
		}
		
		return new ResponseEntity<List<LatLng>>(latlngList,HttpStatus.OK);
	

}
	
	
	
	
	
	
}
