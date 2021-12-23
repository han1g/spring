package org.zerock.service_impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardAttachMapper;
import org.zerock.mapper.BoardMapper;
import org.zerock.service.BoardService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class BoardServiceImpl implements BoardService {
	
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;
	
	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;

	@Transactional
	@Override
	public void register(BoardVO board) {
		log.info("regitser : " + board);
		mapper.insertSelectKey(board);
		if(board.getAttachList() == null || board.getAttachList().size() == 0) {
			return;
		}
		board.getAttachList().forEach(attach ->{
			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
		
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		
		return mapper.getListWithPaging(cri);
	}

	
	@Transactional
	@Override
	public boolean modify(BoardVO board) {
		
		attachMapper.deleteAll(board.getBno());
		boolean success = mapper.update(board) == 1;
		
		if(success && board.getAttachList() != null && board.getAttachList().size() > 0) {
			board.getAttachList().forEach(attach ->{
				attach.setBno(board.getBno());
				attachMapper.insert(attach);
			});
		}
		return success;
	}


	@Override
	public BoardVO get(Long bno) {
		// TODO Auto-generated method stub
		return mapper.get(bno);
	}
	
	@Transactional
	@Override
	public boolean remove(Long bno) {
		// TODO Auto-generated method stub
		
		attachMapper.deleteAll(bno);
		
		return mapper.delete(bno) == 1;
	}

	@Override
	public int getTotal(Criteria cri) {
		// TODO Auto-generated method stub
		return mapper.getTotalCount(cri);
	}

	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		return attachMapper.findByBno(bno);
	}
	
	
	
}
