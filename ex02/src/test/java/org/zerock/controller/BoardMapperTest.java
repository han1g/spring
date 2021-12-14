package org.zerock.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTest {

	@Setter(onMethod_ = @Autowired)
	private BoardMapper boardMapper;
	
	@Test
	public void listTest() {
		boardMapper.getList().forEach(board -> log.info(board));
	}
	
	@Test
	public void updateReplyTest() {
		boardMapper.updateReplyCnt( 32L, 1);
		log.info("sss");
	}
	@Test
	public void updateTest() {
		BoardVO vo = new BoardVO();
		vo.setWriter("作成者");
		vo.setContent("内容");
		vo.setTitle("題名");
		vo.setBno(32L);
		log.info(vo);
		boardMapper.update(vo);
		log.info(vo);
		//vo에 bno값이 저장되서 돌아온다
	}
	
	@Test
	public void insertTest() {
		BoardVO vo = new BoardVO();
		vo.setWriter("作成者");
		vo.setContent("内容");
		vo.setTitle("題名");
		log.info(vo);
		boardMapper.insertSelectKey(vo);
		log.info(vo);
		//vo에 bno값이 저장되서 돌아온다
	}
	
	@Test
	public void totalTest() {
		int total = boardMapper.getTotalCount(new Criteria());
		log.info(total);
	}
	


}
