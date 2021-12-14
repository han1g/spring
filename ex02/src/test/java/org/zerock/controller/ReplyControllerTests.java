package org.zerock.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Enumeration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.WebApplicationContext;
import org.zerock.domain.ReplyVO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml", "file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
public class ReplyControllerTests {
	@Setter(onMethod_ = {@Autowired})
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	

	@Test
	public void getListTest() throws Exception {
		log.info(mockMvc.perform(get("/replies/pages/33/1")).andReturn().getResponse().getContentAsString());
	}
	
	@Test
	public void insertTest() throws Exception {
		ReplyVO reply = new ReplyVO();
		reply.setBno(33L);
		reply.setReply("reply");
		reply.setReplyer("replyer");
		String jsonStr = new Gson().toJson(reply);
		log.info(jsonStr);
		log.info(mockMvc.perform(post("/replies/new").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(jsonStr)).andReturn().getResponse().getContentAsString());
	}
	

	
}
