package com.pofol.web.service;

import java.util.List;
import java.util.Map;

import com.pofol.web.domain.BoardDto;
import com.pofol.web.domain.SearchCondition;

public interface BoardService {
	//list
	public List<BoardDto> list() throws Exception;
	
	//view
	public BoardDto read(Integer no, int inc) throws Exception;
	
	//write
	public int write(BoardDto boardDto) throws Exception;
	
	//update
	public int update(BoardDto boardDto) throws Exception;
	
	//delete
	public int delete(int no) throws Exception;
	
	public int getCount() throws Exception;
	public List<BoardDto> getPage(Map map) throws Exception;

	public int getSearchResultCnt(SearchCondition sc) throws Exception;
	public List<BoardDto> getSearchResultPage(SearchCondition sc) throws Exception;
}
