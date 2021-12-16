package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;

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
	
	@GetMapping("/paging")
	public ModelAndView list(Criteria cri, ModelAndView model) {
		log.info("list");
		//model.addObject("list",service.getList(cri));
		cri.setType("C");
		model.addObject("pageMaker",new PageDTO(cri, 123));
		model.getModel().forEach((key,value) ->{
			log.info("key:" + key + "  value:" + value);
		});
		return model;
	}
	
	
	@GetMapping("/form") 
	public void postForm() {
		
	}
	
	@PostMapping("/postPage")
	public ModelAndView postResult(ModelAndView mv) {
		log.info("result");
		return mv;
	}
}
