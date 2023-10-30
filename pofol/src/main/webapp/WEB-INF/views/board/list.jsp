<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<<link rel="stylesheet" href="/resources/css/bootstrap.min.css">
<script src="/resources/js/jquery-3.6.4.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
<style>
	.dataRow:hover{
		cursor: pointer;
		background: #dddddd;
	}
	
	
</style>
<script type="text/javascript">
	$(function(){
		// option & pagesize 데이터 세팅
		$("#option").val("${(empty ph.sc.option)?"t":ph.sc.option}");
		$("#pageSize").val("${ph.sc.pageSize}");
		
		// 이벤트 처리 (리스트)
		$(".dataRow").click(function(){
			let bno = $(this).find(".bno").text();
			// window 객체의 location
			//alert(${pageContext.request.contextPath});
			//location = "view.do${ph.sc.queryString}&bno=" + bno + "&inc=1";
			location.href = "/board/view.do${ph.sc.queryString}&bno=" + bno + "&inc=1";
		});

		$("#pageSize").change(function(){
			//alert("페이지당 데이터의 개수를 변경했습니다.");
			location = "list.do?page=1&pageSize=" + $("#pageSize").val()
				+ "&keyword=${ph.sc.keyword}&option=${ph.sc.option}";
		});
	});
</script>
</head>
<body>
	<div class="container">
		<h2>일반게시판 리스트</h2>
		<!-- 게시판의 검색 : 제목, 내용, 작성자, 그외 복합 
			페이지는 1페이지, 한페이지에 표시할 데이터 갯수 전달. : hidden-->
		<div style="margin-bottom: 10px" class="row">
			<div id="searchDiv">
				<form action="list.do" class="form-inline">
					<input name="page" value="1" type="hidden" />
					<div class="form-group">
						<select class="form-control" id="option" name="option">
							<%-- <option value="t" ${ph.sc.option =='t'||ph.sc.option =='' ?"selected":""}>제목</option>
							<option value="c" ${ph.sc.option =='c'?"selected":""} >내용</option>
							<option value="w" ${ph.sc.option =='w'?"selected":""}>작성자</option>
							<option value="tc" ${ph.sc.option =='tc'?"selected":""}>제목/내용</option> --%>
							<option value="t">제목</option>
							<option value="c">내용</option>
							<option value="w">작성자</option>
							<option value="tc">제목/내용</option>
						</select>
					</div>
					<div class="input-group">
						<input type="text" class="form-control" placeholder="Search"
							name="keyword" value="${ph.sc.keyword}">
						<div class="input-group-btn">
							<button class="btn btn-default" type="submit">
								<i class="glyphicon glyphicon-search"></i>
							</button>
						</div>
					</div>
					<div class="input-group pull-right">
						<span class="input-group-addon">Rows/Page</span>
						<select class="form-control" id="pageSize" name="pageSize">
							<option value="10">10</option>
							<option value="15">15</option>
							<option value="20">20</option>
							<option value="25">25</option>
						</select>
					</div>
				</form>
			</div>
		</div>

		<table class="table">
			<tr>
				<th class="no">번호</th>
				<th class="title">제목</th>
				<th class="writer">이름</th>
				<th class="regdate">등록일</th>
				<th class="viewcnt">조회수</th>
			</tr>
			<c:forEach var="boardDto" items="${list}">
				<tr class="dataRow">
					<td class="bno">${boardDto.bno}</td>
					<%-- <td class="title"><a href="<c:url value="/board/view?bno=${boardDto.bno}&inc=1"/>"><c:out value='${boardDto.title}' /></a> <span
						style="color: #FF0000">(${boardDto.comment_cnt})</span></td> --%>
					<td class="title">${boardDto.title}</td>	
					<td class="writer">${boardDto.writer}</td>
					<c:choose>
						<c:when test="${boardDto.reg_date.time >= startOfToday}">
							<td class="regdate"><fmt:formatDate
									value="${boardDto.reg_date}" pattern="HH:mm" type="time" /></td>
						</c:when>
						<c:otherwise>
							<td class="regdate"><fmt:formatDate
									value="${boardDto.reg_date}" pattern="yyyy-MM-dd" type="date" /></td>
						</c:otherwise>
					</c:choose>
					<td class="viewcnt">${boardDto.view_cnt}</td>
				</tr>
			</c:forEach>
		</table>
		<ul class="pagination">
	        <c:if test="${totalCnt==null || totalCnt==0}">
	          <div> 게시물이 없습니다. </div>
	        </c:if>
	        <c:if test="${totalCnt!=null && totalCnt!=0}">
	          <c:if test="${ph.showPrev}">
	          	<li class="page-item">
	            <a class="page-link" href="<c:url value="/board/list.do${ph.sc.getQueryString(ph.beginPage-1)}"/>">&lt;</a>
	            <!-- <i class="glyphicon glyphicon-fast-backward"></i> -->
	            </li>
	          </c:if>
	          <c:forEach var="i" begin="${ph.beginPage}" end="${ph.endPage}">
	            <li class="page-item ${i==ph.sc.page? "active" : ""}"><a class="page-link" href="<c:url value="/board/list.do${ph.sc.getQueryString(i)}"/>">${i}</a></li>
	          </c:forEach>
	          <c:if test="${ph.showNext}">
	            <li class="page-item">
	            <a class="page-link" href="<c:url value="/board/list.do${ph.sc.getQueryString(ph.endPage+1)}"/>">&gt;</a>
	            <!-- <i class="glyphicon glyphicon-fast-forward"></i> -->
	            </li>
	          </c:if>
	        </c:if>
	    </ul>
	    <br>
		<a class="btn btn-default" href="<c:url value="/board/write.do"/>">등록</a>
	</div>
</body>
</html>