/*package com.saifintex.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.saifintex.common.AbstractBase;

@Controller
public class WebErrorController extends AbstractBase implements ErrorController {
	
	private static final String ERROR_MAPPING = "/error";

	@RequestMapping(value = ERROR_MAPPING, method = RequestMethod.GET)
	public ModelAndView renderErrorPage(HttpServletRequest httpRequest, ModelAndView model) {
		getLogger().info("============/error==========");
		int httpErrorCode = getErrorCode(httpRequest);
		String errorMsg = "";
		switch (httpErrorCode) {
		case 400: {
			errorMsg = "Http Error Code: 400. Bad Request";
			break;
		}
		case 401: {
			errorMsg = "Http Error Code: 401. Unauthorized";
			break;
		}
		case 404: {
			errorMsg = "Http Error Code: 404. Page not found";
			break;
		}
		case 500: {
			errorMsg = "Http Error Code: 500. Internal Server Error";
			break;
		}
		case 405: {
			errorMsg = "Http Error Code: 405. Not Allowed";
			break;
		}
		}
		model.addObject("errorMsg", errorMsg);
		model.setViewName("web-exception");
		return model;
	}

	private int getErrorCode(HttpServletRequest httpRequest) {
		return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
	}

	@Override
	public String getErrorPath() {
		return ERROR_MAPPING;
	}
};*/