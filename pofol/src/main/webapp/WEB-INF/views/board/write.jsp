<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>일반게시판 글등록</h2>
<!-- <form action="write" method="post"> -->
<form action="<c:url value='/board/write.do'/>" method="post">
<div class="form-group">
	<label for="title">제목</label>
	<input id="title" name="title" required >
</div>
<div class="form-group">
	<label for="content">내용</label>
	<textarea rows="5" id="content" name="content" required></textarea>
</div>
<div class="form-group">
	<label for="writer">작성자</label>
	<input id="writer" name="writer" required >
</div>
<button>등록</button>
</form>
</body>
</html>