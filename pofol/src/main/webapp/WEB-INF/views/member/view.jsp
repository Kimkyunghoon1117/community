<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${empty param.id ? "내":"회원"}정보 보기</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/resources/css/bootstrap.min.css">
<script src="/resources/js/jquery-3.6.4.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
<style>
    .container {
        max-width: 800px;
        margin: 0 auto;
        padding: 20px;
        background-color: #333; /* 어두운 배경색 설정 */
        color: white; /* 텍스트 색상 설정 */
        border-radius: 5px;
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
        font-family: 'Arial', sans-serif; /* 원하는 글꼴 설정 */
    }

    .row {
        margin-bottom: 10px;
    }

    .col-md-3 {
        background-color: #222; /* 어두운 배경색 설정 */
        padding: 10px;
        border-radius: 5px;
    }

    .col-md-9 {
        background-color: #444; /* 어두운 배경색 설정 */
        padding: 10px;
        border: 1px solid #333; /* 어두운 테두리 설정 */
        border-radius: 5px;
    }

    h2 {
        color: #fff; /* 헤더 텍스트 색상 설정 */
        font-family: 'Helvetica Neue', sans-serif; /* 다른 글꼴 설정 */
    }
</style>

</head>
<body>
    <div class="container my-5">
    	<h2 class="text-center text-dark" >${empty param.id ? "내":"회원"}정보 보기</h2>
    	<!-- dto id,name,email,birth,reg_date,grade -->
    	<div class="row">
    		<div class="col-sm-3">아이디</div>
    		<div class="col-sm-9">${dto.id }</div>
    	</div>
    	<div class="row">
    		<div class="col-sm-3">닉네임</div>
    		<div class="col-sm-9">${dto.name }</div>
    	</div>
    	<div class="row">
    		<div class="col-sm-3">이메일</div>
    		<div class="col-sm-9">${dto.email }</div>
    	</div>
    	<div class="row">
    		<div class="col-sm-3">생년월일</div>
    		<div class="col-sm-9">
    		<span class="badge badge-secondary">
    			<fmt:formatDate value="${dto.birth}" pattern="yyyy-MM-dd" type="date" />
    		</span>
    		</div>
    	</div>
    	<div class="row">
    		<div class="col-sm-3">등록일</div>
			<div class="col-sm-9">
			<span class="badge badge-secondary">
				<fmt:formatDate value="${dto.reg_date}" pattern="yyyy-MM-dd" type="date" />
			</span>
			</div>
    	</div>
    	<div class="row">
    		<div class="col-sm-3">등급</div>
    		<div class="col-sm-9">${dto.grade }</div>
    	</div>
    	
    </div>
</body>
</html>