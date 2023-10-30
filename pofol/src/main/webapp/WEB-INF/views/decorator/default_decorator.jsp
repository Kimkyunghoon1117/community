<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>pofol<decorator:title /></title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
	<<link rel="stylesheet" href="/resources/css/bootstrap.min.css">
	<script src="/resources/js/jquery-3.6.4.js"></script>
	<script src="/resources/js/bootstrap.min.js"></script>
<style type="text/css">
header, footer {
	background: AntiqueWhite;
}

pre { /* 엔터, 공백문자를 기대로 볼 수 있다. Bootstrap의 디자인이 의도한 대로 되지 않는다.
		pre 대신 \n -> <br> " " -> &nbsp; 변환해서 표시한다. textarea 태그 사용하고 readonly 속성 지정*/
	background: white;
	border: 0px;
}

/* Remove the navbar's default margin-bottom and rounded borders */
.navbar {
	margin-bottom: 0;
	border-radius: 0;
}

/* Add a gray background color and some padding to the footer */
footer {
	background-color: black;
	padding: 25px;
	color: #ddd;
}

.carousel-inner img {
	width: 100%; /* Set width to 100% */
	margin: auto;
	min-height: 200px;
}

/* Hide the carousel text when the screen is less than 600 pixels wide */
@media ( max-width : 600px) {
	.carousel-caption {
		display: none;
	}
}

article {
	min-height: 400px;
	margin-top: 80px;
	margin-bottom: 120px;
}

#welcome {
	color: grey;
	margin: 0 auto;
}
</style>
<script type="text/javascript">
/* 	$(document).ready(function() {
	});
	 */
	$(function(){
	});
</script>
<c:if test="${!empty msg}">
	<script type="text/javascript">
		$(function(){
			alert("${msg}");
		});
	</script>
</c:if>
<decorator:head/>
</head>
<body>
	<header>
		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target="#myNavbar">
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="/main/home.do">Portfolio</a>
				</div>
				<div class="collapse navbar-collapse" id="myNavbar">
					<ul class="nav navbar-nav">
						<!-- <li><a href="/notice/list.do">공지사항</a></li> -->
						<li><a href="/board/list.do">게시판</a></li>
						<li><a href="/image/list.do">갤러리</a></li>
						<!-- <li><a href="/qna/list.do">질문답변</a></li> -->
						<c:if test="${!empty loginDto && loginDto.id eq 'admin' }">
							<li><a href="/member/list.do">회원관리</a></li>
						</c:if>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<c:if test="${ empty loginDto }">
							<!-- 로그인이 되어 있지 않을 때 시작 -->
					    	<li><a href="/member/write.do"><span class="glyphicon glyphicon-user"></span> 회원가입</a></li>
							<li><a href="/member/login.do"><span class="glyphicon glyphicon-log-in"></span> 로그인</a></li>
							<!-- 로그인이 되어 있지 않을 때 끝 -->
						</c:if>
						<c:if test="${ ! empty loginDto }">
							<!-- 로그인이 되어 때 시작 -->
					    	<li><a href="/member/view.do"><span class="glyphicon glyphicon-user"></span> ${loginDto.name }</a></li>
							<li><a href="/member/logout.do"><span class="glyphicon glyphicon-log-in"></span> 로그아웃</a></li>
							<!-- 로그인이 되어 때 끝 -->
						</c:if>
				    </ul>
					
				</div>
			</div>
		</nav>
	</header>
	<article>
		<decorator:body />
	</article>
	<footer class="container-fluid text-center navbar navbar-inverse navbar-fixed-bottom">
		<p></p>
	</footer>
</body>
</html>