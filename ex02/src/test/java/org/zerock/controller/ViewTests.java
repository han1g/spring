package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;


@Log4j
@Controller
@RequestMapping("/test/*")
public class ViewTests {
	
	@GetMapping("/header")
	public String header() {
		log.info("test/header");
		return "/includes/header";
	}
}
