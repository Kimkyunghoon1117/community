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
<link rel="stylesheet" href="/css/bootstrap.min.css">
<script src="/js/jquery-3.6.4.js"></script>
<script src="/js/bootstrap.min.js"></script>
<style type="text/css">
	#deleteDiv{
		display:none;
	}
	#data .row > div{
		padding: 10px 20px;
	}
	#data .row {
		border-bottom: 1px dotted #aaa
	}
	#imageListDiv  .imgDiv:hover {
		 cursor: pointer;
		 opacity: 0.5;
	}
	.btn {
		 margin:10px 5px;
	}
	#changeImageDiv {
		display: none;
	}
</style>
<script type="text/javascript">
	$(function(){
		// 큰이미지에 pr=1을 보이게한다
		selectImage($("#imageListDiv").find("img:first"));
		// 이미지 삭제버튼은 안보이게한다
		$("#deleteImageBtn").hide();
		
		$("#deleteBtn").click(function(){
			if(!confirm("정말삭제하시겠습니까?"))
				return false;
		});
		
		$("#imageListDiv").on("click",".imgDiv",function(){
			// closest 현재요소(this)에서 상위 트리로 이동
			selectImage($(this).find("img"));
		});
		
		//이미지 갱신
		resize();
		// 브라우저 크기가 변경되면 이미지 크기 갱신
		$(window).resize(function(){
			resize();
		});
		
		//이미지 변경, 이미지 삭제
		$("#changeImageBtn").click(function(){
			let fno = $("#bigImage").data("fno");
			$("#changeFno").val(fno);
			$("#deleteFileName").val($("#bigImage").attr("src"));
			$("#changeImageDiv").slideToggle();
			$("#changeImage").val("");
		});
		$("#deleteImageBtn").click(function(){
			if(!confirm("이미지를 정말 삭제하시겠습니까?"))
				return
			let fno = $("#bigImage").data("fno");
			let deleteFile = $("#bigImage").attr("src");
			//alert("fno : "+fno+" deleteFile : "+deleteFile);
			// encodeURI : 한글등 encode한다. 단 특수문자(= & 등 제외)
			// encodeURIComponent : 한글, 특수문자 모두 encode한다
			location = "deleteImage.do?fno="+fno
					+"&deleteFile="+deleteFile +"&query="+ encodeURIComponent("<%=request.getQueryString()%>");
		});
	});
	
	let selectImage = function(image) {
		let src = image.attr("src");
		if($("#bigImage").attr("src") == src)
			return;
		$("#bigImage").attr("src",src).data("fno",image.data("fno"));
		// 작은이미지 fno -> 큰이미지 fno
		//download a href
		$("#imageDownload").attr("href",src);
		$("#imageListDiv").find(".imgDiv").css("border","");
		image.closest(".imgDiv").css("border","1.5px solid #000");
		
		// 이미지 변경 폼 사라지게한다
		$("#changeImageDiv").slideUp();
		
		//pr=1 삭제버튼 x
		if(image.data("pr")==1) $("#deleteImageBtn").hide();
		else $("#deleteImageBtn").show();
	}
	
	let resize = function() {
		// 메인 이미지의 너비를 구해보자.
		let width = $("#bigImage").width();
		let height = width / 3 * 2.5;
		$("#bigImage").height(height + "px");
		// 추가 이미지의 너비를 구해보자.
		width = $("#imageListDiv img:first").width();
		height = width / 3 * 2.5;
		$("#imageListDiv img").height(height + "px");
	}
</script>
</head>
<body>
<div class="container">
    <h2>이미지게시판 글보기</h2>
    <div id="data" class="row">
        <!-- Left Column -->
        <div class="col-md-4">
            <!-- 큰 이미지 -->
            <div class="row text-center">
            	<div>
            		<img class="img-thumbnail" id="bigImage" style="width: 80%" >
            	</div>
            	<div class="btn-group">
	            	<%-- <c:if test="${imageDto.id eq  loginId}"> --%>
						<button type="button" class="btn btn-warning btn-xs" id="changeImageBtn">이미지 바꾸기</button>
						<button type="button" class="btn btn-danger btn-xs" id="deleteImageBtn">이미지 삭제</button>
					<%-- </c:if> --%>
					<a type="button" class="btn btn-success btn-xs" id="imageDownload" download >다운로드</a>
				</div>
				<div id="changeImageDiv">
					<form action="changeImage.do" method="post" enctype="multipart/form-data">
						<input type="hidden" name="query" value="<%=request.getQueryString()%>">
						<input type="hidden" name="fno" id="changeFno">
						<input type="hidden" name="deleteFileName" id="deleteFileName">
						<div class="form-group">
							<label>바꿀 이미지</label>
							<input name="changeImage" type="file" class="form-control" id="changeImage" required>
						</div>
						<button>바꾸기</button>
					</form>
				</div>
            </div>
            <!-- 작은 이미지 표시 -->
            <div class="row" id="imageListDiv" style="margin-left:20px">
                <c:forEach items="${imageFileList }" var="fileDto">
                    <div class="col-sm-2" style="padding: 10px 3px">
                        <div class="imgDiv">
                            <img src="${fileDto.fileName}" class="img-thumbnail" style="width:100%" data-fno="${fileDto.fno }"
                            data-pr="${fileDto.pr}">
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <!-- Right Column -->
        <div class="col-md-8">
			<div class="row">
				<div class="col-sm-3">번호</div>
				<div class="col-sm-9">${imageDto.no }</div>
			</div>
			<div class="row">
				<div class="col-sm-3">제목</div>
				<div class="col-sm-9">${imageDto.title}</div>
			</div>
	
			<div class="row">
				<div class="col-sm-3">내용</div>
				<div class="col-sm-9">${imageDto.content}</div>
			</div>
			<div class="row">
				<div class="col-sm-3">작성자</div>
				<div class="col-sm-9">${imageDto.id }</div>
			</div>
			<div class="row">
				<div class="col-sm-3">작성일</div>
				<div class="col-sm-9">
					<c:choose>
						<c:when test="${imageDto.reg_date.time >= startOfToday}">
							<td class="regdate"><fmt:formatDate
									value="${imageDto.reg_date}" pattern="HH:mm" type="time" /></td>
						</c:when>
						<c:otherwise>
							<td class="regdate"><fmt:formatDate
									value="${imageDto.reg_date}" pattern="yyyy-MM-dd" type="date" /></td>
						</c:otherwise>
					</c:choose>
				</div>
				</div>
			</div>
		</div>
    </div> <!-- data -->
    <a href="<c:url value="/image/update.do?page=${param.page}&pageSize=${param.pageSize}&option=${param.option}&keyword=${param.keyword}&no=${imageDto.no}"/>" class="btn btn-default" style="margin-left: 100px;">수정</a>
    <a href="<c:url value="/image/delete.do?page=${param.page}&pageSize=${param.pageSize}&option=${param.option}&keyword=${param.keyword}&no=${imageDto.no}"/>" class="btn btn-default" id="deleteBtn">삭제</a>
    <a href="<c:url value="/image/list.do?page=${param.page}&pageSize=${param.pageSize}&option=${param.option}&keyword=${param.keyword}&no=${imageDto.no}"/>" class="btn btn-default">목록</a>
</div>
</body>
</html>