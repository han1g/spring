package org.zerock.controller;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.service.BoardService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTest {
	
	@Setter(onMethod_ = @Autowired)
	private BoardService boardService;
	
	@Test
	public void testExist() {
		log.info(boardService);
		assertNotNull(boardService);
	}
	
	@Test
	public void testRegister() {
		BoardVO board = new BoardVO();
		board.setContent("content");
		board.setTitle("title");
		board.setWriter("writer");
		
		boardService.register(board);
	}
	@Test
	public void testList() {
		Criteria criteria = new Criteria(1,1000);
		//criteria.setType("T");
		//criteria.setKeyword("a");
		boardService.getList(criteria);
	}
	
	@Test
	public void totalTest() {
		Criteria criteria = new Criteria(1,1000);
		criteria.setType("T");
		criteria.setKeyword("a");
		int total = boardService.getTotal(criteria);
		log.info(total);
	}
	
}
