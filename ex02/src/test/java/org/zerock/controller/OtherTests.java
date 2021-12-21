package org.zerock.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.Criteria;
import org.zerock.task.FileCheckTask;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class OtherTests {
	
	@Setter(onMethod_ = @Autowired)
	private FileCheckTask task;
	
	@Test
	
	public void criteriaTest() {
		log.info(new Criteria().getListLink());
	}
	
	@Test
	public void taskTest() throws Exception {
		task.checkFiles();
		
		
	}
}
