package com.saifintex.controller.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.saifintex.common.AbstractBase;
import com.saifintex.domain.AppPreferencesDomain;
import com.saifintex.domain.ClientExceptions;
import com.saifintex.dto.AppPreferencesDto;
import com.saifintex.dto.BugReportStats;
import com.saifintex.dto.ClientExceptionsDTO;
import com.saifintex.dto.UserStatsReportDTO;
import com.saifintex.services.AppService;
import com.saifintex.services.ClientExceptionsService;
import com.saifintex.services.TransactionService;
import com.saifintex.services.UserService;
import com.saifintex.services.UsersOTPService;
import com.saifintex.web.domain.TotalReferalUserBySp;
import com.saifintex.web.domain.TransactionStatsReport;
import com.saifintex.web.domain.UserStatsReport;
import com.saifintex.web.dto.TotalReferalUserBySPDTO;
import com.saifintex.web.dto.TransactionStatsReportDTO;

@Controller
@RequestMapping("/admin")
public class DashboardController extends AbstractBase {
	@Autowired
	private UsersOTPService userOtpService;
	@Autowired
	UserService userService;

	@Autowired
	TransactionService transactionService;

	@Autowired
	ClientExceptionsService clientExceptionsService;

	@Autowired
	AppService appService;

	@RequestMapping(value = "/dashboard")
	public ModelAndView getDashboard() {
		getLogger().info("=========/dashboard===========");
		/*
		 * UserStatsReportDTO userStatsDto = userService.getUserStatsReport(true);
		 * UserStatsReport report = null; TransactionStatsReport transStats = null; if
		 * (userStatsDto != null) { report = new UserStatsReport();
		 * BeanUtils.copyProperties(userStatsDto, report); } TransactionStatsReportDTO
		 * transStatsDto = transactionService.getTransactionsReportOfAllUsers(true); if
		 * (transStatsDto != null) { transStats = new TransactionStatsReport();
		 * BeanUtils.copyProperties(transStatsDto, transStats); }
		 */

		BugReportStats bugReportStats = clientExceptionsService.getBugReportStats();
		return new ModelAndView("index")/* .addObject("userStats", report).addObject("transStats", transStats) */
				.addObject("dateParams", getDashboardDateParamsInDDMMYYYYFormat())
				.addObject("bugStats", bugReportStats);
	}

	@RequestMapping(value = "/sdashboard")
	public ModelAndView superAdminDashboardHandler() {
		getLogger().info("=========/dashboard/superadmin===========");
		/*
		 * UserStatsReportDTO userStatsDto = userService.getUserStatsReport(true);
		 * UserStatsReport report = null; TransactionStatsReport transStats = null; if
		 * (userStatsDto != null) { report = new UserStatsReport();
		 * BeanUtils.copyProperties(userStatsDto, report); } TransactionStatsReportDTO
		 * transStatsDto = transactionService.getTransactionsReportOfAllUsers(true); if
		 * (transStatsDto != null) { transStats = new TransactionStatsReport();
		 * BeanUtils.copyProperties(transStatsDto, transStats); }
		 */

		// BugReportStats bugReportStats=clientExceptionsService.getBugReportStats();
		return new ModelAndView("index-super-admin")/*
													 * .addObject("userStats", report).addObject("transStats",
													 * transStats)
													 */
				.addObject("dateParams", getDashboardDateParamsInDDMMYYYYFormat());
	}

	/**
	 * @param testUser
	 *            - true or false. whether to include test users or not
	 * @return transaction stats and user stats
	 */
	 
		@RequestMapping(value = "/dashboardrest")
		public @ResponseBody Map<String, Object> getDashboardData(@RequestParam("testUser") boolean testUser) {
			getLogger().info("=========/dashboardrest===========");
			UserStatsReportDTO userStatsDto = userService.getUserStatsReport(testUser);
			UserStatsReport report = null;
			TransactionStatsReport transStats = null;
			TotalReferalUserBySp spreferalStats= null;
			if (userStatsDto != null) {
				report = new UserStatsReport();
				BeanUtils.copyProperties(userStatsDto, report);
			}
			TransactionStatsReportDTO transStatsDto = transactionService.getTransactionsReportOfAllUsers(testUser);
			if (transStatsDto != null) {
				transStats = new TransactionStatsReport();
				BeanUtils.copyProperties(transStatsDto, transStats);
			}
			
			TotalReferalUserBySPDTO  totalReferalUserBySPDTO=userService.getTotalUsersBySP();
			
			if (totalReferalUserBySPDTO != null) {
				spreferalStats = new TotalReferalUserBySp();
				BeanUtils.copyProperties(totalReferalUserBySPDTO, spreferalStats);
			}
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			
			System.out.println(report.getTotalActiveUsers());
			responseMap.put("userStats", report);
			responseMap.put("transStats", transStats);
			responseMap.put("spreferalStats", spreferalStats);
			System.out.println("referal count"+spreferalStats.getOverAllTotalCount());
			return responseMap;
		}


	@RequestMapping(value = "/notification")
	public ModelAndView notificationHandler() {
		getLogger().info("=========/notifications===========");
		return new ModelAndView("notifications");
	}

	@RequestMapping(value = "/contactus")
	public ModelAndView contactHandler() {
		getLogger().info("=========/contactus===========");
		return new ModelAndView("contactus");
	}

	@RequestMapping(value = "/feedback")
	public ModelAndView feedbackHandler() {
		getLogger().info("=========/feedback===========");
		return new ModelAndView("feedback");
	}

	@RequestMapping(value = "/bugReports")
	public ModelAndView bugReportsHandler() {
		getLogger().info("=========/bugReports===========");
		Map<String, String> responseMap = new HashMap<String, String>();
		String exception = clientExceptionsService.readTodayExceptionLogs();
		if (!exception.isEmpty()) {
			responseMap.put("exceptions", exception);
		} else {
			responseMap.put("exceptions", "No Logs");
		}
		String info = clientExceptionsService.readTodayInfoLogs();
		if (!info.isEmpty()) {
			responseMap.put("info", info);
		} else {
			responseMap.put("info", "No Logs");
		}
		return new ModelAndView("bug-reports", responseMap);
	}

	@RequestMapping(value = "/getExceptionLog/{date}")
	@ResponseBody
	public Map<String, String> bugReportsExceptionHandler(@PathVariable String date) {
		getLogger().info("---------/getExceptionLog/----------------------");
		getLogger().info("date == " + date);
		String exception = clientExceptionsService.readExceptionLogOnSpecifiedDate(date);
		Map<String, String> responseMap = new HashMap<String, String>();
		if (!exception.isEmpty()) {
			responseMap.put("exceptions", exception);
		} else {
			responseMap.put("exceptions", "No logs found on the specified date");
		}
		return responseMap;
	}
	@RequestMapping(value = "/qrcodes")
	public ModelAndView qrCodesHandler() {
		getLogger().info("=========/QR codes===========");
		return new ModelAndView("qrcodes");
	}
	
	@RequestMapping(value = "/mapqrcode")
	public ModelAndView mapQrCodeHandler() {
		getLogger().info("=========/QR codes===========");
		return new ModelAndView("mapqrcode");
	}
	@RequestMapping(value = "/getInfoLog/{date}")
	@ResponseBody
	public Map<String, String> bugReportsInfoHandler(@PathVariable String date) {
		getLogger().info("---------/getInfoLog/----------------------");
		getLogger().info("date == " + date);
		String info = clientExceptionsService.readInfoLogOnSpecifiedDate(date);
		Map<String, String> responseMap = new HashMap<String, String>();
		if (!info.isEmpty()) {
			responseMap.put("info", info);
		} else {
			responseMap.put("info", "No logs found on the specified date");
		}
		return responseMap;
	}

	@RequestMapping(value = "/getTransStatsDashboard")
	@ResponseBody
	public Map<String, String> getTransStatsDashboardHandler() {
		getLogger().info("---------/getTransStatsDashboard/----------------------");

		Map<String, String> responseMap = new HashMap<String, String>();
		/*
		 * if (!info.isEmpty()) { //responseMap.put("info", info); } else {
		 * responseMap.put("info", "No logs found on the specified date"); }
		 */
		return responseMap;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/getClientExceptions")
	public @ResponseBody Map<String, List<ClientExceptions>> AllClientExceptionsHandler() {
		getLogger().info("---------/getClientExceptions----------------------");
		List<ClientExceptionsDTO> dtoList = clientExceptionsService.getAllClientExceptionsService();
		List<ClientExceptions> domainList = (List<ClientExceptions>) (List<?>) copyList(dtoList,
				ClientExceptions.class);
		Map<String, List<ClientExceptions>> responseMap = new HashMap<String, List<ClientExceptions>>();
		responseMap.put("data", domainList);
		return responseMap;
	}

	@RequestMapping("/appPreferences")
	public ModelAndView appPreferenecesHandler() {
		getLogger().info("---------/appPreferences/----------------------");
		AppPreferencesDto appPrefDto = appService.getAppPreferences();
		return new ModelAndView("app-preferences", "appPreference", appPrefDto);
	}

	
	@RequestMapping(value = "/updateAppPreferences", method = RequestMethod.POST)
	public String apPreferenecsHandler(@ModelAttribute AppPreferencesDomain appPreferences,
			RedirectAttributes redirect) {
		getLogger().info("---------/updateAppPreferences/----------------------");
		AppPreferencesDto dto = new AppPreferencesDto();
		BeanUtils.copyProperties(appPreferences, dto);
		appService.updateAppPreferences(dto);
		return "redirect:/admin/appPreferences";
	}

	@RequestMapping(value = "/getRemainingOTPs", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getRemainingOTPs() {
		getLogger().info("---------/getRemainingOTPs/----------------------");
		long otps = userOtpService.getRemainigOTPs();
		return new ResponseEntity<Long>(otps, HttpStatus.OK);
	}

	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> sendMail(@RequestParam String table) {
		getLogger().info("---------/sendMail/----------------------");
		appService.sendDashBoardDetailViaEmail(null, null, null, null, table);
		return new ResponseEntity<Long>(1L, HttpStatus.OK);
	}
	
	
}
