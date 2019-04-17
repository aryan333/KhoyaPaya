package com.saifintex.controller.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.saifintex.common.AbstractBase;
import com.saifintex.domain.UserPaymentDetail;
import com.saifintex.services.UserService;


@Controller
@RequestMapping(value="/payment")
public class PaymentController extends AbstractBase{
		int result=0;
		
		@Autowired
		UserService userService;
		
		
		
	@RequestMapping(value="/success",method=RequestMethod.POST)
	public ModelAndView paymentSuccess(@ModelAttribute UserPaymentDetail userPaymentDetail,RedirectAttributes redirect){
		getLogger().info("mphin success"+userPaymentDetail.getMihpayid());
		result=userService.insertPayment(userPaymentDetail);
		ModelAndView modelAndView=new ModelAndView("redirect:../payment/successful");
		redirect.addFlashAttribute("userPaymentDetail",userPaymentDetail);
		return modelAndView;
		
	}
	
	@RequestMapping(value="/fail",method=RequestMethod.POST)
	public ModelAndView paymentFail(@ModelAttribute UserPaymentDetail userPaymentDetail,RedirectAttributes redirect){
		getLogger().info("mphin"+userPaymentDetail.getMihpayid());
		result=userService.insertPayment(userPaymentDetail);
		ModelAndView modelAndView=new ModelAndView("redirect:../payment/failure");
		redirect.addFlashAttribute("userPaymentDetail",userPaymentDetail);
		return modelAndView;
	}
	
	@RequestMapping(value="/successful",method=RequestMethod.GET)
	public String paymentSuccessful(){
		return "success";
	}
	@RequestMapping(value="/failure",method=RequestMethod.GET)
	public String paymentFailed(){
		return "failure";
	}

	

}
