package org.zerock.controller;



import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.SampleVO;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/sample")
@Log4j
public class SampleController {
	
	@GetMapping(value = "getText", produces = "text/plain; charset=UTF-8")
	public String getText() {
		
		log.info("MIME TYPE: "+ MediaType.TEXT_PLAIN_VALUE);
		return "æ»≥Á«œººø‰";
	}
	@PostMapping(value = "postJson")
	public SampleVO postJson(@RequestBody SampleVO vo) {
		
		log.info(vo);
		return vo;
	}
	
	@GetMapping(value = "/getSample", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
	public SampleVO getSample() {
		return new SampleVO(112,"a","b");
	}
	
	@GetMapping(value = "/getMap")
	public Map<String,Object> getMap() {
		Map<String,Object> map = new HashMap<>();
		map.put("first", new SampleVO(112,"a","b"));
		return map;
	}
	
	@GetMapping(value = "/getPath/{a}/{b}")
	public SampleVO getPath(@PathVariable("a")String  a,@PathVariable("b")String  b) {
		return new SampleVO(111,a,b);
		
	}
}
