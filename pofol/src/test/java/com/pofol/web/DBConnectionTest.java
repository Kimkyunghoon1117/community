package com.pofol.web;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pofol.web.dao.BoardDao;
import com.pofol.web.domain.BoardDto;

@ExtendWith(SpringExtension.class) // Junit5
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})

public class DBConnectionTest  {
	@Autowired
	DataSource ds;
	
	@Autowired
    private BoardDao boardDao;
	
	@Test
	public void main()throws Exception {
	    Connection conn = ds.getConnection(); // �����ͺ��̽��� ������ ��´�.
	
	    System.out.println("conn = " + conn);
	    assertTrue(conn!=null);
	}
	
	@Test
	public void selectTest() throws Exception {
		BoardDto boardDto = new BoardDto("no title", "no content", "asdf");
		assertTrue(boardDao.insert(boardDto) == 1);

		Integer bno = boardDao.selectAll().get(0).getBno();
		boardDto.setBno(bno);
		BoardDto boardDto2 = boardDao.select(bno);
		assertTrue(boardDto.equals(boardDto2));
	}
}