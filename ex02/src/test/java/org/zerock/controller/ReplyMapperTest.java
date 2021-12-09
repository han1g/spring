package org.zerock.controller;

import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.ReplyMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTest {
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	private Long[] bnoArr = {33L,32L,31L,30L,29L};
	
	
	@Test
	public void testMapper() {
		log.info(mapper);
	}
	
	@Test
	public void inesrtTest() {
		IntStream.rangeClosed(1, 10).forEach(i -> {
			ReplyVO vo = new ReplyVO();
			
			vo.setBno(bnoArr [i % 5]);
			vo.setReply("´ñ±Û" + i);
			vo.setReplyer("replyer" + i);
			
			mapper.insert(vo);
		});
		
	}
	@Test
	public void readTest() {
		log.info(mapper.read(5L));
	}
	
	@Test
	public void deleteTest() {
		log.info(mapper.delete(1L));
	}
	
	@Test
	public void updateTest() {
		ReplyVO vo = new ReplyVO();
		vo.setRno(5L);
		vo.setReply("¼öÁ¤");
		log.info(mapper.update(vo));
	}
	
	@Test
	public void pagingTest() {
		log.info(mapper.getListWithPaging(new Criteria(), 33L));
	}
	
	@Test
	public void listTest() {
		log.info(mapper.getList());
	}
}
