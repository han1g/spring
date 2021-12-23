package org.zerock.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.BoardMapper;
import org.zerock.mapper.ReplyMapper;
import org.zerock.service.ReplyService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Setter(onMethod_ = @Autowired)
	ReplyMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	BoardMapper boardMapper;

	@Override
	public ReplyPageDTO getList(Criteria cri, Long bno) {
		// TODO Auto-generated method stub
		return new ReplyPageDTO(mapper.getCountByBno(bno),mapper.getListWithPaging(cri, bno));
	}

	@Transactional
	@Override
	public int register(ReplyVO reply) {
		// TODO Auto-generated method stub
		log.info(reply);
		boardMapper.updateReplyCnt(reply.getBno(), 1);
		return mapper.insert(reply);
	}

	@Override
	public ReplyVO get(Long rno) {
		// TODO Auto-generated method stub
		return mapper.read(rno);
	}
	
	@Transactional
	@Override
	public int remove(Long rno) {
		// TODO Auto-generated method stub

		boardMapper.updateReplyCnt(mapper.read(rno).getBno(), -1);
		return mapper.delete(rno);
	}

	@Override
	public int modify(ReplyVO reply) {
		// TODO Auto-generated method stub
		return mapper.update(reply);
	}

}
