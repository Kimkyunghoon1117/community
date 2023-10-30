package com.pofol.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pofol.web.dao.MemberDao;
import com.pofol.web.domain.LoginDto;
import com.pofol.web.domain.MemberDto;
import com.pofol.web.domain.SearchCondition;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	MemberDao dao;
	@Override
	public LoginDto login(LoginDto dto) {
		return dao.login(dto);
	}
	@Override
	public List<MemberDto> list(SearchCondition sc) {
		return dao.list(sc);
	}
	@Override
	public int getTotalRow(SearchCondition sc) {
		return dao.getTotalRow(sc);
	}
	@Override
	public MemberDto view(String id) {
		return dao.view(id);
	}
	@Override
	public int modify(MemberDto dto) {
		return dao.modify(dto);
	}
	@Override
	public int write(MemberDto dto) {
		return dao.write(dto);
	}
}
