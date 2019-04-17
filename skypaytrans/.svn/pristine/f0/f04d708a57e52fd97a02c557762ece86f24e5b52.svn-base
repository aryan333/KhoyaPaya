package com.saifintex.controller.rest;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.saifintex.common.AbstractBase;
import com.saifintex.domain.MerchantInfoBySalesPerson;
import com.saifintex.dto.MerchantInfoBySalesPersonDto;
import com.saifintex.dto.UserWithOpponentUserBalanceDto;
import com.saifintex.services.MerchantInfoBySalesPersonService;
import com.saifintex.services.UserService;
import com.saifintex.web.domain.UsersList;
import com.saifintex.web.dto.UsersListDTO;

/**
 * @author Ajay
 *
 */

@RestController
@RequestMapping("/rest")
public class SalesPersonRestController extends AbstractBase {
	/**
	 * Inject User Service
	 * 
	 * @author Ajay
	 * 
	 */
	@Autowired
	UserService userService;

	/**
	 * Inject message
	 * 
	 * @author Ajay
	 * 
	 */
	@Value("${phoneno.valid}")
	public String mobileNoNotValid;

	/**
	 * Inject message
	 * 
	 * @author Ajay
	 * 
	 */
	@Value("${phonno.not.empty}")
	public String mobileNoCantEmpty;

	/**
	 * Inject message
	 * 
	 * @author Ajay
	 * 
	 */
	@Value("${app.user.not.exists}")
	public String notExists;

	/**
	 * Inject MerchantInfoBySalesPersonService
	 * 
	 * @author Ajay
	 * 
	 */
	@Autowired
	private MerchantInfoBySalesPersonService merchantInfoBySalesPersonService;
	/**
	 * Inject MessageSource
	 * 
	 * @author Ajay
	 * 
	 */

	@Autowired
	private MessageSource messageSource;

	/**
	 * This API method is to get referal users
	 * 
	 * @author Ajay
	 * @param period
	 *            time duration
	 * @param inviteCode
	 *            user's invite code
	 * @param userType
	 *            type of the user
	 * @return Json Response
	 */

	@RequestMapping(value = "/public/getReferalUsers/{period}/{inviteCode}/{userType}")
	public @ResponseBody Map<String, List<UsersList>> referalUsersHandler(@PathVariable("period") String period,
			@PathVariable("inviteCode") String inviteCode, @PathVariable("userType") String userType) {
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

	/**
	 * 'Method to get the user saved by the sales person to save the further
	 * information
	 * 
	 * @author Ajay
	 * @param loggedInUserId
	 * @param userLogin
	 * @return Json Data user registered by sales person
	 */
	@RequestMapping(value = "/private/getUserByMobileNumber/{loggedInUserId}/{userLogin}", method = RequestMethod.GET)
	public ResponseEntity<?> getBalanceIfUserExist(@PathVariable("loggedInUserId") int loggedInUserId,
			@PathVariable("userLogin") String userLogin) {
		getLogger().info("---------/getBalanceIfUserExist/------------");
		Map<String, Object> responseMap = new HashMap<String, Object>();
		if (userLogin == null || userLogin.isEmpty() || userLogin.length() == 0) {
			responseMap.put("response", mobileNoCantEmpty);
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.BAD_REQUEST);
		} else if (!userLogin.matches("[0-9]{10}+")) {
			responseMap.put("response", mobileNoNotValid);
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.BAD_REQUEST);
		}

		UserWithOpponentUserBalanceDto userWithOpponentUserBalanceDto = userService
				.getBalanceIfUserExist(loggedInUserId, userLogin);
		if (userWithOpponentUserBalanceDto == null) {
			responseMap.put("response", notExists);
			return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.BAD_REQUEST);

		}
		MerchantInfoBySalesPerson userRegisteredBySalesPersonDetail = copyInDomainUserWithOpponentUserBalance(
				userWithOpponentUserBalanceDto);
		responseMap.put("response", userRegisteredBySalesPersonDetail);
		return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.OK);

	}

	/**
	 * API to update the users info or save new info of a user by sales person
	 * 
	 * @author Ajay
	 * @param param
	 * @param bindResult
	 * @return success or failure
	 */
	@PostMapping
	@RequestMapping(value = "/private/updateUserInfoBySalesPerson")
	public @ResponseBody ResponseEntity<?> updateUserInfoBySalesPerson(
			@Valid @ModelAttribute("param") MerchantInfoBySalesPerson param, BindingResult bindResult) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		getLogger().info("update User Info By sales Person API -------------------------------------------");
		if (param.isMerchant()) {

			String fileName = param.getFile().getOriginalFilename();
			Map<String, String> map = isParamsValid(bindResult, messageSource);
			/*
			 * if (!map.get("response").equals("ok")) { return new
			 * ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST); } else
			 */
			if (fileName.endsWith("jpg") || fileName.endsWith("png") || fileName.endsWith("gif")
					|| fileName.endsWith("jpeg")) {

				MerchantInfoBySalesPersonDto bySalesPersonDto = new MerchantInfoBySalesPersonDto();
				BeanUtils.copyProperties(param, bySalesPersonDto);
				bySalesPersonDto = merchantInfoBySalesPersonService.saveMerchantInfo(bySalesPersonDto);
				BeanUtils.copyProperties(bySalesPersonDto, param);

				return new ResponseEntity<MerchantInfoBySalesPerson>(param, HttpStatus.OK);
			}
		} else {
			MerchantInfoBySalesPersonDto bySalesPersonDto = new MerchantInfoBySalesPersonDto();
			BeanUtils.copyProperties(param, bySalesPersonDto);
			bySalesPersonDto = merchantInfoBySalesPersonService.saveMerchantInfo(bySalesPersonDto);
			BeanUtils.copyProperties(bySalesPersonDto, param);
			return new ResponseEntity<MerchantInfoBySalesPerson>(param, HttpStatus.OK);
		}

		responseMap.put("response", "failure");
		return new ResponseEntity<Map<String, Object>>(responseMap, HttpStatus.BAD_REQUEST);
	}

	/**
	 * This method is to copy the UserWithOpponentUserBalanceDto to
	 * MerchantInfoBySalesPerson
	 * 
	 * @author Ajay
	 * @param UserWithOpponentUserBalanceDto
	 *            required to copy in its domain
	 * @return MerchantInfoBySalesPerson
	 */
	private MerchantInfoBySalesPerson copyInDomainUserWithOpponentUserBalance(UserWithOpponentUserBalanceDto dto) {
		MerchantInfoBySalesPerson userRegisteredBySalesPersonDetail = new MerchantInfoBySalesPerson();
		if (dto.getMappedName() == null || dto.getMappedName().isEmpty()) {
			userRegisteredBySalesPersonDetail.setName(dto.getFirstName());
		} else {
			userRegisteredBySalesPersonDetail.setName(dto.getMappedName());
		}

		userRegisteredBySalesPersonDetail.setMobileNumber(dto.getPhNumber());
		// userRegisteredBySalesPersonDetail.setUserId(dto.getId());
		BeanUtils.copyProperties(dto.getUserDetail(), userRegisteredBySalesPersonDetail);
		return userRegisteredBySalesPersonDetail;
	}

}
