package com.saifintex.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.saifintex.common.AbstractBase;
import com.saifintex.services.PasswordResetService;
import com.saifintex.services.WebUsersService;
import com.saifintex.web.dto.WebUsersDTO;

@Controller
@RequestMapping("/admin")
public class PasswordResetController extends AbstractBase {

	@Autowired
	WebUsersService webUserService;

	@Autowired
	PasswordResetService passService;	
	
	private String token;

	@RequestMapping(value = "/forgotPassword.htm")
	public String getForgotPasswordPage(RedirectAttributes redirect, HttpSession session) {
		getLogger().info("/forgotPassword----------------------------------------------");
		return "forgot-password";
	}

	@RequestMapping(value = "/forgotPassword.action", method = RequestMethod.POST)
	public String forgotPasswordHanlder(@RequestParam("email") String email, HttpServletRequest request, ModelMap map) {
		getLogger().info("/forgotPassword.action ----------------------------------------------");
		if(email.isEmpty()) {
			map.addAttribute("emailNotExist", "email cant be empty");
			return "forgot-password";
		}
		String emailId = email.trim();
		WebUsersDTO userDto = webUserService.getWebUserByUserLoginService(emailId);
		if (userDto != null) {
			int res = passService.sendEmailWithToken(userDto, request.getContextPath(), emailId);
			if (res != 0) {
				return "email-success";
			}
		}
		map.addAttribute("emailNotExist", "email is not registered with us");
		return "forgot-password";
	}

	@RequestMapping(value = "/forgotPasswordToken", method = RequestMethod.GET)
	public String forgotPasswordTokenCheckHanlder(@RequestParam("t") String token, HttpServletRequest request,
			ModelMap map,RedirectAttributes redirect) {
		getLogger().info("/forgotPasswordToken ----------------------------------------------");		
		String result = passService.validateTokenService(token);
		if(result==null) {
			this.token = token;
			return "redirect:/admin/resetPassword";
		}		
		map.addAttribute("tokenNotValid", result);
		return "invalid-token";
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
	public String getResetPasswordPage(RedirectAttributes redirect,ModelMap map) {
		getLogger().info("/resetPassword ----------------------------------------------");
		map.addAttribute("token", token);
		return "reset-password";
	}	
	
}
