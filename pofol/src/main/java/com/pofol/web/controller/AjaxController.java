package com.pofol.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pofol.web.domain.MemberDto;
import com.pofol.web.service.MemberService;

// 자동 생성  @Controller -> HTML이나 url 데이터가 전달
// @RestController -> 문자열 데이터 전달
@RestController
@RequestMapping("/ajax")
public class AjaxController {
	
	@Autowired
	@Qualifier("memberServiceImpl")
	private MemberService memberService;
	// 회원상태
	// ResponseEntity - 처리결과데이터와 처리상태를 같이 넘길수있는 객체
	// value는 url, consumes 받는데이터 형식 ,produces 보내는 데이터 형식 MIME 타입형태 encoding utf-8
	// ResponseEntity 반환데이터가 한글일경우 깨지는거없이 보내짐
	@PatchMapping(value = "/modify.do", produces = "application/text; charset=utf-8")
	public ResponseEntity<String> modify(MemberDto dto){
		//System.out.println("modify.id = " + dto.getId());
		//System.out.println("modify.status = " + dto.getStatus());
		try {
			if (memberService.modify(dto) != 1)
				throw new Exception("Write failed.");
			return new ResponseEntity<String>("MOD_OK", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("MOD_ERR", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		//return new ResponseEntity<String>("멤버의 상태가 변경되었습니다",HttpStatus.OK);
	}
}
