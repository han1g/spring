package org.zerock.sercurity;

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
		
		List<String> roleNames = new ArrayList<>();
		
		auth.getAuthorities().forEach(el -> {roleNames.add(el.getAuthority());});
		
		log.warn("Roles : " + roleNames);
		if(roleNames.contains("ROLE_ADMIN")) {
			response.sendRedirect("/controller/sample/admin");//spring 컨트롤러로 들어가기전이라 root가 "/"임
			return;
		}
		if(roleNames.contains("ROLE_MEMBER")) {
			response.sendRedirect("/controller/sample/member");
			return;
		}
		
		response.sendRedirect("/");
		
		//priority admin -> member -> /
	}
	

}
