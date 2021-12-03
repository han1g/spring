package org.zerock.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
public class BoardControllerTest {
	@Setter(onMethod_ = @Autowired)
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	//웹앱 테스트를 위한 사전작업
	//mockMvc를 사용해 서버를 실행시키지 않고 부분적으로 테스트 가능
	//뷰가 없어도 콘솔에서 네트워크 패킷을 확인 가능
	
	@Test
	public void testList() throws Exception {
		ModelAndView mv = mockMvc.perform(MockMvcRequestBuilders.get("/board/list")).andReturn().getModelAndView();
		log.info(mv.getModelMap());
		//requestBuilder로 get요청 발생시키고 perform으로 실행함
		//url요청은 컨트롤러의 내부 로직을 따름
		
		log.info("view name : " + mv.getViewName());
		
	}
	
	@Test
	public void testRegister() throws Exception {
		log.info(mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
				.param("title", "postTitle")
				.param("content","postContent")
				.param("writer","postWriter")
				).andReturn().getModelAndView().getViewName());
		//
		
	}
	
	@Test
	public void testModify() throws Exception {
		log.info(mockMvc.perform(MockMvcRequestBuilders.post("/board/modify")
				.param("bno","1")
				.param("title", "updateTitle")
				.param("content","postContent")
				.param("writer","postWriter")
				).andReturn().getModelAndView().getViewName());
		//
		
	}
	
	@Test
	public void testget() throws Exception {
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/get")
				.param("bno", "2")
				).andReturn().getModelAndView().getModelMap());
		//
		
	}
	
	@Test
	public void testRemove() throws Exception {
		log.info(mockMvc.perform(MockMvcRequestBuilders.post("/board/remove")
				.param("bno", "2")
				).andReturn().getModelAndView().getViewName());
		//
		
	}
	
}
