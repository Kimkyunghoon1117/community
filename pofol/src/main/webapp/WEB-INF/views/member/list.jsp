<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원관리 리스트</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/resources/css/bootstrap.min.css">
<script src="/resources/js/jquery-3.6.4.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
<style type="text/css">
	.dataRow:hover {
		background:#e4e4e4;	
	}
	.id:hover {
		cursor: pointer;
	}
</style>
<script type="text/javascript">
$(function(){
	//회원정보
	$(".id").click(function(){
		let id = $(this).text();
		//alert(id);
		location = "view.do${ph.sc.queryString}&id="+id;
	});
	
	// 회원 상태 클릭
	let clickCnt =0;
	let oldData ="";
	let newData ="";
	$(".status").click(function(){
		let val = $(this).val();
		if(clickCnt==0){
			clickCnt++;
			oldData = val;
		}
		else{
			clickCnt--;
			newData = val;
			
			if(oldData != newData){
				let id = $(this).closest(".dataRow").find(".id").text();
				if(confirm(id+"님의 상태를"+newData+"로 바꾸시겠습니까?")){
						$.ajax({
							type:'PATCH',
							// data:로 넘기던가 url에 붙여서 넘겨도된다
							url:"/ajax/modify.do?id="+id+"&status="+newData,
							success:function(result){
								alert(result);
							},
							error:function(result){
								alert(result.responseText);						
							},
						});
				}else{
					$(this).val(oldData);
				}
			}
		}
	});
	// 회원 등급 처리
	let oldDataGrade ="";
	let newDataGrade =$(".grade").val();
	$(".grade").change(function(){
		oldDataGrade = newDataGrade;
		newDataGrade = $(this).val();
		
		let id = $(this).closest(".dataRow").find(".id").text();
		if(confirm(id+"님의 등급을"+newDataGrade+"로 바꾸시겠습니까?")){
				$.ajax({
					type:'PATCH',
					// data:로 넘기던가 url에 붙여서 넘겨도된다
					url:"/ajax/modify.do?id="+id+"&grade="+newDataGrade,
					success:function(result){
						alert(result);
					},
					error:function(result){
						alert(result.responseText);						
					},
				});
		}else{
			$(this).val(oldData);
		}
	});
});
</script>
</head>
<body>
    <div class="container">
    	<h2>회원 리스트(관리자 전용)</h2>
    	<div style="color: red"> 회원 정보는 아이디 클릭</div>
    	<table class="table">
    		<tr> <!-- id, name, email, birth, reg_date, grade -->
    			<th>아이디</th>
    			<th>이름</th>
    			<th>이메일</th>
    			<th>생년월일</th>
    			<th>등록일</th>
    			<th>상태</th>
    			<th>등급</th>
    		</tr>
    		<c:forEach items="${list}" var="dto">
	    		<c:if test="${loginDto.id ne dto.id}">
	    			<tr class="dataRow">
	    				<td class="id">${dto.id }</td>
	    				<td>${dto.name }</td>
	    				<td>${dto.email }</td>
	    				<td><fmt:formatDate
									value="${dto.birth}" pattern="yyyy-MM-dd" type="date" /></td>
	    				<c:choose>
						<c:when test="${dto.reg_date.time >= startOfToday}">
							<td class="regdate"><fmt:formatDate
									value="${dto.reg_date}" pattern="HH:mm" type="time" /></td>
						</c:when>
						<c:otherwise>
							<td class="regdate"><fmt:formatDate
									value="${dto.reg_date}" pattern="yyyy-MM-dd" type="date" /></td>
						</c:otherwise>
					</c:choose>
	    				<td>
	    					<select class="status">
	    						<option ${(dto.status eq "정상")?"selected":""}>정상</option>
	    						<option ${(dto.status eq '휴면')?"selected":""}>휴면</option>
	    						<option ${(dto.status eq '탈퇴')?"selected":""}>탈퇴</option>
	    						<option ${(dto.status eq '강퇴')?"selected":""}>강퇴</option>
	    					</select>
	    				</td>
	    				<td>
	    					<input class="grade" type="text" maxlength="2" style="width:30px" value="${dto.grade }">
	    				</td>
	    			</tr>
	    		</c:if>	
    		</c:forEach>
    	</table>
    </div>
</body>
</html>