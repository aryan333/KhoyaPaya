package com.saifintex.controller.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.saifintex.common.AbstractBase;

@ControllerAdvice("com.saifintex.controller.web")
public class WebExceptionHandlerController extends AbstractBase{

	@ExceptionHandler(value=HttpRequestMethodNotSupportedException.class)
	public ModelAndView handleStatus405(HttpRequestMethodNotSupportedException e) {
		getLogger().error(e,e.fillInStackTrace());
		Map<String, String> responseMap = new HashMap<String,String>();
		responseMap.put("errorMsg", "oops! Parameters missing.");
		return new ModelAndView("web-exception",responseMap);
	}
	
	@ExceptionHandler(value=org.springframework.web.bind.MissingServletRequestParameterException.class)
	public ModelAndView parameterInterferenceHandlerWeb(Exception e) {
		getLogger().error(e,e.fillInStackTrace());
		Map<String, String> responseMap = new HashMap<String,String>();
		responseMap.put("errorMsg", "Something Went Wrong !  Please check the URL");
		return new ModelAndView("web-exception",responseMap);
	}
	
	@ExceptionHandler(value=Exception.class)
	public ModelAndView genericExceptionHandlerWeb(Exception e) {
		getLogger().error("internal server error",e.fillInStackTrace());
		Map<String, String> responseMap = new HashMap<String,String>();
		responseMap.put("errorMsg", "Something Went Wrong !  Internal Server Error Possibility !");
		return new ModelAndView("web-exception",responseMap);
	}
}
