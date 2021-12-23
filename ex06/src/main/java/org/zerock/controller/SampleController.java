package org.zerock.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/sample/*")
@Controller
public class SampleController {
	
	
	@GetMapping("/all")
	public void doAll(HttpSession session, Authentication auth) {
		log.info("everyone can access");
		log.info(auth);
		log.info(SecurityContextHolder.getContext().getAuthentication());
		Enumeration<String> attrs = session.getAttributeNames();
		while(attrs.hasMoreElements()) {
			log.info(attrs.nextElement());
		}
		
		
	}
	
	@GetMapping("/member")
	public void doMember() {
		log.info("logined user only");
	}
	
	@GetMapping("/admin")
	public void doAdmin() {
		log.info("admin only");
	}
}
