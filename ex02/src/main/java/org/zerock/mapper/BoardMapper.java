package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardVO;

public interface BoardMapper {
	public List<BoardVO> getList();
	public void insert(BoardVO vo);
	public void insertSelectKey(BoardVO vo);
	public int delete(Long bno);
	public int update(BoardVO vo);
	public BoardVO get(Long bno);
}
