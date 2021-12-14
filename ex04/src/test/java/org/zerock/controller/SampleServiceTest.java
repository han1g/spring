package org.zerock.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.zerock.service.SampleService;
import org.zerock.service.SampleTxService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
public class SampleServiceTest {
	@Setter(onMethod_ = @Autowired)
	private SampleService service;
	
	@Setter(onMethod_ = @Autowired)
	private SampleTxService txService;
	
	@Test
	public void testClass() {
		log.info(service);
		log.info(service.getClass().getName());
		//Class명이 SampleServiceImpl이 아니고 Proxy임
	}
	
	@Test
	public void testAdd() throws Exception {
		log.info(service.doAdd("123","456"));
	}
	
	@Test
	public void testAddErr() throws Exception {
		log.info(service.doAdd("123","asd"));
	}
	
	@Test
	public void testTransaction() throws Exception {
		txService.addData("qojeoiqwjeoiqwjeoiqj");
	}
}
