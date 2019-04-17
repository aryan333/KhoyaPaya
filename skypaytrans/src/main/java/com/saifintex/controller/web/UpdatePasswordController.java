/*package com.saifintex.controller.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.saifintex.common.AbstractBase;
import com.saifintex.services.EmailOTPService;
import com.saifintex.services.UpdatePasswordService;

@Controller
@RequestMapping("/user")
public class UpdatePasswordController extends AbstractBase{

	@Autowired
	EmailOTPService emailOtpService;
	
	@Autowired
	UpdatePasswordService updatePasswordService;
	
	@RequestMapping("/forgotpassword")
	public ModelAndView showEmailVerificationPage() {
		ModelAndView modelAndView = new ModelAndView("forgotpassword");
		return modelAndView;
	}

	@RequestMapping(value = "/emailotpverification", method = RequestMethod.POST)
	public ModelAndView showUpdatePasswordPage(@RequestParam("emailTo") String emailTo, HttpSession session) {

		//getLogger().info(userDao.getUserByEmail(emailTo));
		String otp = emailOtpService.generateRandomAlphaNumeric(6);
		session.setAttribute("otp", otp);
		session.setAttribute("emailId", emailTo);
		session.setMaxInactiveInterval(600);
		emailOtpService.sendMail(emailTo, otp);
		ModelAndView modelAndView = new ModelAndView("otp");
		return modelAndView;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView getUpdate(@RequestParam("otp") String otp, HttpSession session) {
		String userOtp = (String) session.getAttribute("otp");
		if (userOtp.equals(otp)) {
			ModelAndView modelAndView = new ModelAndView("updatepassword");
			return modelAndView;
		} else {
			ModelAndView modelAndView = new ModelAndView("otp");
			return modelAndView;
		}

	}

	@RequestMapping(value = "/passwordupdatesuccess", method = RequestMethod.POST)
	public ModelAndView getSuccessPassPage(@RequestParam("newPassword") String newPassword, HttpSession session) {

		String email = (String) session.getAttribute("emailId");
		getLogger().info(email);
		
		boolean result = updatePasswordService.updatePassword(email, newPassword);
		getLogger().info("result : " + result);
		ModelAndView modelAndView;
		if (result) {

			modelAndView = new ModelAndView("successupdatepassword");
			return modelAndView;
		} else {
			modelAndView = new ModelAndView("updatepassword");
			modelAndView.addObject("password not updated !! Please try again");
			return modelAndView;
		}

	}

}
*/