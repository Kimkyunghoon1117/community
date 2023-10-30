package com.pofol.web;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pofol.web.controller.BoardController;
import com.pofol.web.dao.BoardDao;
import com.pofol.web.domain.BoardDto;
import com.pofol.web.service.BoardService;

@ExtendWith(SpringExtension.class) // Junit5
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBInsertTest {
	@Autowired
	DataSource ds;
	
	@Autowired
    private BoardService bs;
	
	@Test
	public void test()throws Exception {
	    Connection conn = ds.getConnection();
	    
	    for(int i=0; i<100; i++) {
	    	BoardDto boardDto = new BoardDto("title"+i, "content"+i, "asdf");
		    bs.write(boardDto);
	    }
	    System.out.println("conn = " + conn);
	    //assertTrue(conn!=null);
	}
}
