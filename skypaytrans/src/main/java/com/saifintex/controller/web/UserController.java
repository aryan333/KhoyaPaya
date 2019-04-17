/*package com.saifintex.controller.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.saifintex.common.AbstractBase;
import com.saifintex.domain.PaymentEntities;
import com.saifintex.domain.TransactionDetail;
import com.saifintex.domain.User;
import com.saifintex.domain.UserLogin;
import com.saifintex.services.UserService;

import com.saifintex.validators.PaymentValidator;
import com.saifintex.validators.UserLoginValidator;
import com.saifintex.validators.UserSignupValidation;


@Controller
@RequestMapping(value="/user")
public class UserController extends AbstractBase {
	
	@Autowired
	UserService userService;
	@Autowired
	UserSignupValidation signupValidator;
	@Autowired
	UserLoginValidator signinValidator;
	
	@Autowired
	PaymentValidator paymentValidator;
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signUp( @ModelAttribute("user") @Validated User user,BindingResult result,RedirectAttributes redirectAttributes){
		signupValidator.validate(user, result);
		if(result.hasErrors()){
			return "signup";
		}
		getLogger().info("---------------------------------------------------");
	//	int resultData= userService.signUp(user);
		if(resultData!=0){
						
				redirectAttributes.addFlashAttribute("message","Sucessfully Registered");
				return "redirect:../user/signin";
			} 
		if(success<0){
			redirectAttributes.addFlashAttribute("message","Sucessfully Registered");
			return "redirect:../user/signin";
		}
		redirectAttributes.addFlashAttribute("message","User Already Exists");
		return "redirect:../user/signin";

		return "signup";
	}
	@RequestMapping(value="/signin")
	public String getLoginPage(@ModelAttribute("userLogin") UserLogin userLogin){
		System.out.println("Hello in signin");
		return "index";
	}
	@RequestMapping(value="/signup")
	public String getSignupPage(@ModelAttribute("user") User user){
		return "signup";
	}
	
	@RequestMapping(value="/checkNumber")
	public @ResponseBody Map<String,String> isNumberExists(@RequestParam("mobileNumber") String mobileNumber){
		getLogger().info("inside right controller");
		Map<String, String> responseMap=new HashMap<String, String>();
		boolean check=userService.isUserAlreadyExist(mobileNumber);
		if(check){
		responseMap.put("valid", "true");
		return responseMap;
		}
		responseMap.put("valid", "false");
		return responseMap;

	}
	
	@RequestMapping(value="/signin", method=RequestMethod.POST)
	public String signIn(@ModelAttribute(value="userLogin") @Validated UserLogin userLogin,
	                 BindingResult result,
					RedirectAttributes redirect,HttpSession session,ModelMap model){
		   signinValidator.validate(userLogin, result);
			if(result.hasErrors()){
				return "index";
			}
		//	User userDetail=userService.signIn(userLogin.getPhoneNo(), userLogin.getPassword(),userLogin.getUserType());
			if(userDetail==null){
				
				redirect.addFlashAttribute("message", "Invalid UserName or Password");
			return "redirect:../user/signin";
			}
			//session.setAttribute("userId", userDetail.getUserId());
			//session.setMaxInactiveInterval(600);
		//	redirect.addFlashAttribute("user", userDetail);
			return "redirect:../user/home";
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String getIndexPage(){
		return("index");
		
	}
	
	@RequestMapping(value="/agreement",method=RequestMethod.GET)
	public String getAgreementPage(){
		return("agreement");
		
	}
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request){
		request.getSession().removeAttribute("userId");
		ModelAndView model=new ModelAndView("redirect:/");
		return model;	
	}
	@RequestMapping(value="/home",method=RequestMethod.GET)
	public ModelAndView home(@ModelAttribute("paymentEntities")PaymentEntities paymentEntities,HttpServletRequest request){
		Integer userId=(Integer)request.getSession().getAttribute("userId");
	//	User user=userService.getUserById(userId);
		ModelAndView model=new ModelAndView("fillamount1");
	//	model.addObject("user",user);
		return model;	
	}
	
	@RequestMapping(value="/pay",method=RequestMethod.POST)
	public String pay(@ModelAttribute("paymentEntities") @Validated PaymentEntities paymentEntities,BindingResult result,ModelMap model,RedirectAttributes redirectAttributes){
		paymentValidator.validate(paymentEntities, result);
		if(result.hasErrors()){
			return "redirect:../user/home";
		
		}
		User user=userService.getUserForPay(paymentEntities.getUserId(),paymentEntities.getAmount(),paymentEntities.getDescription());
		model.addAttribute("user",user);
		return "pay";
	}
	
	
	@RequestMapping(value="/transactionHistory",method=RequestMethod.GET)
	public ModelAndView transcationHistory(HttpServletRequest request){
		Integer userId=(Integer)request.getSession().getAttribute("userId");
		ModelAndView model=new ModelAndView("transaction");
		List<TransactionDetail> transactionHistory=userService.getTransactionHistory(userId);
		model.addObject("transactionHistory",transactionHistory);
		return model;
		
	}
	@RequestMapping(value="/sessionexpire")
	 public ModelAndView showSessionOut(){
	  getLogger().info("sessionexpire inside modelandview");
	  return new ModelAndView("sessionout");
	 }
	
	@RequestMapping("/checkEmail")	
	public @ResponseBody Map<String,String> checkEmailExistence(@RequestParam("email") String email){
		getLogger().info("check email existence "+ email);
		String emailId=null;//userService.getUserEmail(email);
		Map<String,String> responseMap=new HashMap<String,String>();
		if(emailId != null){
			responseMap.put("valid", "true");
			return responseMap;
		}
		responseMap.put("valid", "false");
		return responseMap;
	}
	
}
*/