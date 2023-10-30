<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이미지 등록</title>
<script>
$(function(){
	//이벤트처리
	$("#subImageFileBtn").click(function(){
		let cnt = $(".imageFile").length;
		if(cnt ==5){
			alert("추가 이미지는 5개 까지만 가능합니다");
			return;
		}
		let html ="";
		html += '<div class="form-group">';
		html += '<labe>추가이미지<button class="removeImageFileBtn btn-danger"'
		html += 'style="margin-left:10px">X</button></label>';
		html += '<input class="imageFile" name="imageFile" required class="form-control" type="file"></div>';
		
		// html(data)-html 데이터 바꾸기. text(data)-text데이터바꾸기. 
		//append(data)-뒤에추가하기. preppend(data)-앞에추가하기
		$("#subImageFileDiv").append(html);
	});
	// 추가로 만들어진 태그에는 이벤트 추가x -> 기존있는 태그+ on() 사용
	$("#subImageFileDiv").on("click",".removeImageFileBtn",function(){
		// closest 현재요소(this)에서 상위 트리로 이동
		$(this).closest(".form-group").remove();
		
	});
});
</script>
</head>
<body>
<div class="container">
	<h2>이미지 게시판 등록</h2>
	<!-- <form action="write" method="post"> multipart/form-data 바이너리,텍스트 데이터를 보낸다--> 
	<form action="<c:url value='/image/write.do'/>" method="post" enctype="multipart/form-data">
	<div class="form-group">
		<label for="title">제목</label>
		<input id="title" name="title" required class="form-control">
	</div>
	<div class="form-group">
		<label for="content">내용</label>
		<textarea rows="5" id="content" name="content" required class="form-control"></textarea>
	</div>
	<div class="form-group">
		<label for="mainImageFile">대표이미지</label>
		<input id="mainImageFile" name="mainImageFile" required class="form-control" type="file">
	</div>
	<button id="subImageFileBtn" type="button" class="btn btn-success btn-block">추가이미지</button>
	<div id="subImageFileDiv" style="margin-bottom: 10px"></div>
	<button class="btn btn-default">등록</button>
	</form>
</div>
</body>
</html>