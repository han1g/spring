package org.zerock.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.ibatis.javassist.tools.reflect.Sample;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.SampleDto;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {
	@RequestMapping("")
	public void basic() {
		log.info("basic...........");
		
	}//리턴값이 void이면 같은 이름의 jsp리턴
	
	
	@RequestMapping(value = "/basic", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseStatus(value=HttpStatus.OK)
	public void basicGet() {
		log.info("basic...........");
		
	}
	@GetMapping(value = "/basicOnlyGet")
	public void basicGet2() {
		log.info("basic...........");
		
	}
	
	@GetMapping(path  = "/ModelAnnotation", params = {"dueDate","page"})
	public String basic(SampleDto dto,@ModelAttribute("page") int page) {
		//dto의 date형을 String으로 주고받기
		//파라미터로 받은 page를 자동으로 model에 넣음
		//attribute name 생성 필요
		//기본생성자가 없으면 무조건 값을 넣어줘야함 안그러면 에러남
		
		log.info("basic...........");
		return "home";
		
	}
	
	@GetMapping(path  = "/ModelAnnotation", params = {"dueDate"})
	public String basic(SampleDto dto) {
		int defaultValue = 1;
		return basic(dto,defaultValue);
	}
	//(기본생성자가 없으면 무조건 값을 넣어줘야함 안그러면 에러남) 억지로 해결
	
	@GetMapping("/json")
	public @ResponseBody SampleDto json() throws ParseException {
		
		//리턴값에 @ResponseBody붙이고 객체(Object) 리턴하면 알아서 json으로 응답 보내줌
		//!!!!!!!!!!!!!jacksonDatabind 라이브러리 필요
		//자바빈 규약을 만족해야함
		//javascript Ajax통신 시 사용
		
		SampleDto ret = new SampleDto();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd");
		ret.setDueDate(transFormat.parse("2222/11/11"));
		ret.setDueDate2("2222/11/11");
		return ret;
		
	}
	
	@GetMapping("/jsonResponse")
	public ResponseEntity<SampleDto> jsonResponse() throws ParseException  {
		
		SampleDto ret = new SampleDto();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy/MM/dd");
		ret.setDueDate(transFormat.parse("2222/11/11"));
		ret.setDueDate2("2222/11/11");
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
		header.add("fuck", "you");
		return new ResponseEntity<SampleDto>(ret, header, HttpStatus.BAD_REQUEST);
		
		//ResponseEntity로 리턴하면 응답코드,상태코드 등 세밀한 설정 가능
		
	}
	
	@GetMapping("/exUpload")
	public void exUpload() {
		log.info("uploadPage");	
	}
	@GetMapping("/exUploadMulti")
	public void exUploadMulti() {
		log.info("uploadPageMulti");	
	}
	
	@PostMapping("/exUploadPost")
	public void exUpload(ArrayList<MultipartFile> files) throws Exception{
		log.info("uploadFiles");
		String uploadPath = "C:/upload/tmp";
		files.forEach(new Consumer<MultipartFile>() {

			@Override
			public void accept(MultipartFile file){
				//do something with each file
			}
		});
		
		//lambda version
		upload(uploadPath,files);

	}
	
	
	public static void upload(String uploadPath,ArrayList<MultipartFile> files) throws Exception {
		
		Exception[] e = {null};
		files.forEach(file -> {	
			log.info("upload");
			try {throw new Exception();}
			catch (Exception e1) {
				e[0] = e1;
			}
			/*if(file.isEmpty())
				return;
			
			File f = new File(uploadPath + "/" + file.getOriginalFilename());
			try {
				file.transferTo(f);
			} catch (IllegalStateException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
		});
		if(e[0] != null)
			throw e[0];
	}
	
	
	
	@GetMapping("/badRequest")
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public String badRequest() {
		//web.xml에서 콜 하는 url
		log.error("badRequest");
		return "/sample/badRequest";
		//responseStatus어노테이션이 있으면 void로 찾아가는게 안됨
	}
	
	@GetMapping("/notFound")
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public String notFound() {
		//web.xml에서 콜 하는 url
		log.error("notFound");
		return "/sample/notFound";
		//responseStatus어노테이션이 있으면 void로 찾아가는게 안됨
	}
	 
}
