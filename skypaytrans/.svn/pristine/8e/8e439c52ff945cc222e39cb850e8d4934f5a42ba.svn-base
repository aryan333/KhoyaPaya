/*package com.saifintex.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
//import org.saifintex.camp7.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter  {
	
	@Value("${web.admin.session.time}")
	private int sessionTime;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("inside interceptor");
		// do not save cache on browser so that after logout , back button do not show the secured page
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.
        
		HttpSession session = request.getSession();
		String uri = request.getRequestURI();

		if(session==null){
			response.sendRedirect(request.getContextPath()+"/admin/sessionExpired");
			return false;
		}else if(session.getAttribute("admin")==null) {
			response.sendRedirect(request.getContextPath()+"/admin/login");
			return false;
		}
		return true;
	}
	
}*/