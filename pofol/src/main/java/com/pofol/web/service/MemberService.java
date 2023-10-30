package com.pofol.web.service;

import java.util.List;

import com.pofol.web.domain.LoginDto;
import com.pofol.web.domain.MemberDto;
import com.pofol.web.domain.SearchCondition;

public interface MemberService {
	// 로그인
	public LoginDto login(LoginDto dto);
	// 회원리스트
	public List<MemberDto> list(SearchCondition sc);
	// 회원리스트 카운트
	public int getTotalRow(SearchCondition sc);
	
	public MemberDto view(String id);
	// 변경
	public int modify(MemberDto dto);
	// 등록
	public int write(MemberDto dto);
	
}
