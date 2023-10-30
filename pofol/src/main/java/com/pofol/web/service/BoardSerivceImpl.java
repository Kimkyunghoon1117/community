package com.pofol.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pofol.web.dao.BoardDao;
import com.pofol.web.domain.BoardDto;
import com.pofol.web.domain.SearchCondition;
/*
 * 자동 생성 - @Controller, @Service, @Repository, @Conponent, @Advice, @RestController
 * servlet-context.xml, root-context.xml에 설정되어 있어야만 한다. 
 */
@Service
// 동일한 타입의 객체가 있는 경우 설정
@Qualifier("boardServiceImpl")
public class BoardSerivceImpl implements BoardService {
	
	@Autowired
	BoardDao boardDao;
	
	@Override
	public List<BoardDto> list() throws Exception {
		//System.out.println("BoardService.list()");
		return boardDao.selectAll();
	}
	
	public BoardDto read(Integer bno, int inc) throws Exception {
        //BoardDto boardDto = boardDao.select(bno);
		if(inc !=0)
			boardDao.increaseViewCnt(bno);
        return boardDao.select(bno);
    }
	
	@Override
	public int write(BoardDto boardDto) throws Exception {
		return boardDao.insert(boardDto);
	}

	@Override
	public int update(BoardDto boardDto) throws Exception {
		return boardDao.update(boardDto);
	}

	@Override
	public int delete(int bno) throws Exception {
		return boardDao.delete(bno);
	}

	@Override
	public int getCount() throws Exception {
		return boardDao.count();
	}

	@Override
	public List<BoardDto> getPage(Map map) throws Exception {
		return boardDao.selectPage(map);
	}

	@Override
	public int getSearchResultCnt(SearchCondition sc) throws Exception {
		return boardDao.searchResultCnt(sc);
	}

	@Override
	public List<BoardDto> getSearchResultPage(SearchCondition sc) throws Exception {
		return boardDao.searchSelectPage(sc);
	}
	
}
