package org.zerock.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication auth) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.warn("Login Success");
		//springSecurity의 인증 통과함
		log.info(request.getParameter("remember-me"));
		List<String> roleNames = new ArrayList<>();
		
		auth.getAuthorities().forEach(el -> {roleNames.add(el.getAuthority());});
		
		String referer = request.getHeader("Referer");
		log.info(referer);
		response.sendRedirect(referer);
		
		//priority admin -> member -> /
	}
	

}
