package com.pofol.web.dao;

import java.util.List;

import com.pofol.web.domain.LoginDto;
import com.pofol.web.domain.MemberDto;
import com.pofol.web.domain.SearchCondition;

public interface MemberDao {
	public LoginDto login(LoginDto dto);

	public List<MemberDto> list(SearchCondition sc);

	public int getTotalRow(SearchCondition sc);

	public MemberDto view(String id);

	public int modify(MemberDto dto);

	public int write(MemberDto dto);

}