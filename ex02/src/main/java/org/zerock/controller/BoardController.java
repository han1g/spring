package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.zerock.domain.BoardVO;
import org.zerock.service.BoardService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
public class BoardController {
	
	@Setter(onMethod_ = @Autowired) 
	private BoardService service;
	
	@GetMapping("/list")
	public ModelAndView list(ModelAndView model) {
		log.info("list");
		model.addObject("list",service.getList());
		model.getModel().forEach((key,value) ->{
			log.info("key:" + key + "  value:" + value);
		});
		
		model.setViewName("forward:/board/forward");
		return model;
	}
	
	@GetMapping("/forward")
	public void forward(ModelAndView model) throws JsonProcessingException {
	//왜인지 모델로 하면 리스트가 안넘어옴
		
		
		model.getModel().forEach((key,value) ->{
			log.info("key:" + key + "  value:" + value);
		});
		log.info("forward");
		
	}
	
	@GetMapping("/get")
	public void get(@RequestParam("bno") Long bno, ModelAndView model) {
		log.info("get");
		model.addObject("board",service.get(bno));
		
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr) {
		log.info("get");
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
		}
		
		return "redirect:/board/list";
		
	}
	

	
	
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("register : " + board);
		service.register(board);
		rttr.addFlashAttribute("result", board.getBno());
		
		return "redirect:/board/list";
		
		//(board/list)요청의 모델에 result가 추가됨
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr) {
		log.info("register : " + board);
		if(service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		
		return "redirect:/board/list";
		
		//(board/list)요청의 모델에 result가 추가됨
	}
	
}
