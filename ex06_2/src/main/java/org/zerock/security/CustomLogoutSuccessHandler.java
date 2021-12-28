package org.zerock.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		log.info("logoutSuccess");
		// TODO Auto-generated method stubee
		if(authentication != null) {
			authentication.getAuthorities().forEach(log::info);
		}
		String referer = request.getHeader("Referer");
		log.info(referer);
		response.sendRedirect(referer);
	}

}
