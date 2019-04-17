package com.saifintex.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AjaxCallEntryPoint extends LoginUrlAuthenticationEntryPoint {

	

public AjaxCallEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
		// TODO Auto-generated constructor stub
	}

@Override
public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
	if(request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").equals("XMLHttpRequest")){
		((HttpServletResponse)response).sendError(400, "");
                
    }
	else{
		request.setAttribute("sessionError", "please login");
        super.commence(request, response, authException);
        System.out.println("Hello in webpages=="+request.getHeader("X-Requested-With") );
        
    }

}
}
