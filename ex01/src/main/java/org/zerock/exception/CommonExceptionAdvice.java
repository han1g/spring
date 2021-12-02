package org.zerock.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.log4j.Log4j;

@ControllerAdvice
@Log4j
public class CommonExceptionAdvice {
	/*@ExceptionHandler(Exception.class)
	public String except(Exception ex,Model model) {
		ex.printStackTrace();
		log.error(ex.getMessage());
		model.addAttribute("exception",ex);
		return "error_page";
		
	}*/
}
