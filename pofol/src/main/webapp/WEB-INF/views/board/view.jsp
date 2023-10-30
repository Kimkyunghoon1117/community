<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/resources/css/bootstrap.min.css">
<script src="/resources/js/jquery-3.6.4.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
<style type="text/css">
		#deleteDiv{
			display:none;
		}
</style>
<script type="text/javascript">
	$(function(){
		$("#deleteBtn, #cancelBtn").click(function(){
			//alert("deleteBtn");
			$("#deleteDiv").slideToggle();
			return false; // 앵커태그 페이지 이동 취소
		});
	});
</script>
</head>
<body>
<div class="container">
	<h2>일반게시판 글보기</h2>
	<table class="table">
		<tr>
			<th>번호</th>
			<td>${boardDto.bno}</td></tr>
		<tr>
			<th>제목</th>
			<td>${boardDto.title}</td></tr>
		<tr>
			<th>내용</th>
			<td>${boardDto.content}</td></tr>
		<tr>
			<th>작성자</th>
			<td>${boardDto.writer}</td></tr>
		<tr>
			<th>작성일</th>
			<td><fmt:formatDate value="${boardDto.reg_date}" pattern="yyyy-MM-dd" type="date" /></td></tr>
		<tr>
			<th>조회수</th>
			<td>${boardDto.view_cnt}</td></tr>
	</table>
	<a href="<c:url value="/board/update.do?page=${param.page}&pageSize=${param.pageSize}&option=${param.option}&keyword=${param.keyword}&bno=${boardDto.bno}"/>" class="btn btn-default">수정</a>
	<a href="#" class="btn btn-default" id="deleteBtn">삭제</a>
	<%-- <a href="<c:url value="/board/list.do${ph.sc.queryString}"/>" class="btn btn-default">목록</a> --%>
	<a href="<c:url value="/board/list.do?page=${param.page}&pageSize=${param.pageSize}&option=${param.option}&keyword=${param.keyword}&bno=${boardDto.bno}"/>" class="btn btn-default">목록</a>
	<div id="deleteDiv">
		<form action="<c:url value="/board/delete.do"/>" method="post">
			<input name="bno" type="hidden" value="${boardDto.bno}">
			<div class="panel panel-default">
			  <div class="panel-heading">글삭제 확인창입니다</div>
			  <div class="panel-footer">
			  	<button class="btn btn-default">삭제</button>
			  	<button id="cancelBtn" class="btn btn-default" type="button">
			  	취소</button>
			  </div>
			</div>
		</form>
	</div>
</div>
</body>
</html>