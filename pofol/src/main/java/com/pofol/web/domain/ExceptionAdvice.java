package com.pofol.web.domain;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionAdvice {
	
	// 서버의 Controller에서나오는 모든 Exception을 전부 잡아서 처리한다
	@ExceptionHandler(Exception.class)
	public String exception(Exception ex, Model model) {
		model.addAttribute("ex", ex);
		return "error/error_page";
	}
	
//	// 404 처리
//	@ExceptionHandler(NoHandlerFoundException.class)
//	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
//	public String exception404(NoHandlerFoundException ex) {
//		return "error/error_404";
//	}
}
