package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.CustomUser;
import org.zerock.domain.PageDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
public class BoardController {
	
	@Setter(onMethod_ = @Autowired) 
	private BoardService service;
	
	@GetMapping("/list")
	public ModelAndView list(Criteria cri, ModelAndView model) {
		log.info("list");
		model.addObject("list",service.getList(cri));
		model.addObject("pageMaker",new PageDTO(cri, service.getTotal(cri)));
		model.getModel().forEach((key,value) ->{
			log.debug("key:" + key + "  value:" + value);
		});
		return model;
	}
	
	@GetMapping("/forward")
	public ModelAndView forward(ModelAndView model) throws JsonProcessingException {
	//왜인지 모델로 하면 리스트가 안넘어옴
		
		
		model.getModel().forEach((key,value) ->{
			log.info("key:" + key + "  value:" + value);
		});
		log.info("forward");
		
		return model;
		
	}
	
	@GetMapping("/get")
	public ModelAndView get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, ModelAndView model) {
		log.info("get");
		model.addObject("board",service.get(bno));
		return model;
		
	}
	
	@PreAuthorize(value = "principal.username == #writer")
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno,Criteria cri, RedirectAttributes rttr) {
		log.info("get");
		
		List<BoardAttachVO> tmp = service.getAttachList(bno);//saveList before delete
		
		if(service.remove(bno)) {//delete from db
			
			tmp.forEach( el -> {
				log.info(el.getFileName());
				log.info(el.getUploadPath());
				new UploadController().deleteFile(el.getUploadPath() + "\\" + el.getUuid() + "_" + el.getFileName(),el.isFileType());
			});//delete real file
			rttr.addFlashAttribute("result", "success");
		}
		/*rttr.addAttribute("pageNum",cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());*/
		//attribute가 늘어날 때 마다 적으면 귀찮음
		
		
		return "redirect:/board/list" + cri.getListLink();
		//매크로 만들어서 불러오기
		
	}
	
	@PostMapping("/modify")
	@PreAuthorize(value = "principal.username == #writer")
	public String modify(BoardVO board, Criteria cri, RedirectAttributes rttr) {
		//request body 뿐만아니라 url 파라미터도 같이 받음
		log.info("modifyPost : " + board);
		if(service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		
		/*rttr.addAttribute("pageNum",cri.getPageNum());
		rttr.addAttribute("amount",cri.getAmount());*/
		//redirect attribute는 객체로 못넘김
		return "redirect:/board/list" + cri.getListLink();
		
		//(board/list)요청의 모델에 result가 추가됨
	}
	
	@GetMapping("/modify")
	@PreAuthorize(value = "isAuthenticated()")
	public ModelAndView modify(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, ModelAndView model) {
		log.info("modifyGet");
		model.addObject("board",service.get(bno));
		return model;
		//(board/list)요청의 모델에 result가 추가됨
	}
	
	
	@PreAuthorize(value = "isAuthenticated()")
	@GetMapping("/register")
	public void register() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		log.info(auth.getPrincipal());
	}

	
	@PreAuthorize(value = "isAuthenticated()")
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUser princial = (CustomUser)auth.getPrincipal();
		board.setWriter(princial.getMember().getUserid());//html 수정해서 작성자 아이디 바꾸는 거 방지
		log.info("register : " + board);
		service.register(board);
		rttr.addFlashAttribute("result", board.getBno());
		
		if(board.getAttachList() != null) {
			board.getAttachList().forEach(log::info);
		}
		
		return "redirect:/board/list";
		
		//(board/list)요청의 모델에 result가 추가됨
	}
	
	@GetMapping(value ="/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno){
		
		return new ResponseEntity<>(service.getAttachList(bno),HttpStatus.OK);
	}
	
}
