package com.pofol.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pofol.web.domain.ImageDto;
import com.pofol.web.domain.ImageFileDto;
import com.pofol.web.domain.LoginDto;
import com.pofol.web.domain.MemberDto;
import com.pofol.web.domain.SearchCondition;

@Repository
public class MemberDaoImpl implements MemberDao {
	@Autowired
    private SqlSession session;
    private static String namespace = "com.pofol.web.dao.MemberMapper.";
    
	@Override
	public LoginDto login(LoginDto dto) {
		return session.selectOne(namespace+"login", dto);
	}

	@Override
	public List<MemberDto> list(SearchCondition sc) {
		return session.selectList(namespace+"list", sc);
	}
	
	@Override
	public int getTotalRow(SearchCondition sc) {
		return session.selectOne(namespace+"getTotalRow", sc);
	}

	@Override
	public MemberDto view(String id) {
		return session.selectOne(namespace+"view", id);
	}

	@Override
	public int modify(MemberDto dto) {
		int row =0;
		System.out.println("status : "+dto.getStatus());
		System.out.println("grade : "+dto.getGrade());
		if(dto.getStatus() != null)
			row = session.update(namespace+"modifyStatus", dto);
		else if(dto.getGrade() != null)
			row = session.update(namespace+"modifyGrade", dto);
		return row;
	}

	@Override
	public int write(MemberDto dto) {
		return session.insert(namespace+"write", dto);
	}
    
}
