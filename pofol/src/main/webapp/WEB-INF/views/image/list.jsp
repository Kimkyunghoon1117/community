<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이미지 갤러리</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<
<link rel="stylesheet" href="/resources/css/bootstrap.min.css">
<script src="/resources/js/jquery-3.6.4.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
<style>
	.dataRow.thumbnail:hover {
		cursor: pointer;
		/* opacity: 0.5; */
	}
	.imgDiv {
		overflow: hidden;
	}
	.title{
		overflow: hidden;
		white-space: nowrap; /* 한줄처리 */
		text-overflow: ellipsis;
	}
</style>
<script type="text/javascript">
	$(function(){
		//이미지 갱신
		resize();
		// 브라우저 크기가 변경되면 이미지 크기 갱신
		$(window).resize(function(){
			resize();
		});
		
		// option & pagesize 데이터 세팅
		$("#option").val("${(empty ph.sc.option)?"t":ph.sc.option}");
		$("#pageSize").val("${ph.sc.pageSize}");
		
		// 이벤트 처리 (리스트)
		$(".dataRow").click(function(){
			// 클릭한 태그안에 data-no= 설정
			let no = $(this).data("no");
			location = "view.do${ph.sc.queryString}&no="+no;
		});

		$("#pageSize").change(function(){
			//alert("페이지당 데이터의 개수를 변경했습니다.");
			location = "list.do?page=1&pageSize=" + $("#pageSize").val()
				+ "&keyword=${ph.sc.keyword}&option=${ph.sc.option}";
		});
		
		$(".dataRow").mouseover(function() {
			$(this).find("img").css("opacity", 0.5);
		}).mouseout(function() {
			$(this).find("img").css("opacity", 1);
		});
	});
	
	// 이미지의 높이를 처리하는 함수
	function resize() {
		// 이미지의 너비를 구해보자.
		let width = $(".dataRow .imgDiv:first").width();
		let height = width / 3 * 2;
		$(".dataRow .imgDiv").height(height + "px");
	}
</script>
</head>
<body>
	<div class="container">
		<h2>이미지게시판 리스트</h2>
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
						<span class="input-group-addon">Rows/Page</span> <select
							class="form-control" id="pageSize" name="pageSize">
							<option value="8">8</option>
							<option value="12">12</option>
							<option value="16">16</option>
							<option value="20">20</option>
						</select>
					</div>
				</form>
			</div>
		</div>
	
		
		<div class="row">
		<!-- varStatus 1.count(1~) 2.index(0~) -->
		<c:forEach items="${list}" var="dto" varStatus="vs">
		<c:if test="vs.index > 0 && vs.index%4 ==0">
			<!-- 4개의 데이터가 찍히면 줄바꿈하는 처리 -->
			<c:out value="</div>"/>
			<c:out value="<div class='row'>"/>
		</c:if>
			<div class="col-md-3 dataRow" data-no="${dto.no}">
				<div class="thumbnail">
					<div class="imgDiv">
					<!-- <div class="imgDiv" style="width: 100%"> -->
					<img src="${dto.fileName }" style="width: 100%">
					</div>
						<div class="caption">
							<div class="title">${dto.title }</div>
							<div style="border-top: 1px dotted #aaa;">${dto.id }
							<span class="pull-right">
								<c:choose>
									<c:when test="${dto.reg_date.time >= startOfToday}">
										<td class="regdate"><fmt:formatDate value="${dto.reg_date}"
												pattern="HH:mm" type="time" /></td>
									</c:when>
									<c:otherwise>
										<td class="regdate"><fmt:formatDate value="${dto.reg_date}"
												pattern="yyyy-MM-dd" type="date" /></td>
									</c:otherwise>
								</c:choose>
							</span></div>
						</div>
				</div>
			</div>
		</c:forEach>
		</div>

		<ul class="pagination">
			<c:if test="${totalCnt==null || totalCnt==0}">
				<div>게시물이 없습니다.</div>
			</c:if>
			<c:if test="${totalCnt!=null && totalCnt!=0}">
				<c:if test="${ph.showPrev}">
					<li class="page-item"><a class="page-link"
						href="<c:url value="/image/list.do${ph.sc.getQueryString(ph.beginPage-1)}"/>">&lt;</a>
						<!-- <i class="glyphicon glyphicon-fast-backward"></i> --></li>
				</c:if>
				<c:forEach var="i" begin="${ph.beginPage}" end="${ph.endPage}">
					<li class="page-item ${i==ph.sc.page? "active" : ""}"><a
						class="page-link"
						href="<c:url value="/image/list.do${ph.sc.getQueryString(i)}"/>">${i}</a></li>
				</c:forEach>
				<c:if test="${ph.showNext}">
					<li class="page-item"><a class="page-link"
						href="<c:url value="/image/list.do${ph.sc.getQueryString(ph.endPage+1)}"/>">&gt;</a>
						<!-- <i class="glyphicon glyphicon-fast-forward"></i> --></li>
				</c:if>
			</c:if>
		</ul>
		<br> <a class="btn btn-default"
			href="<c:url value="/image/write.do"/>">등록</a>
	</div>
</body>
</html>