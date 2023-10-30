<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/resources/css/bootstrap.min.css">
<script src="/resources/js/jquery-3.6.4.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
<style>
        body {
            background-color: black; /* 배경색을 검정색으로 설정 */
            color: white; /* 텍스트 색상을 흰색으로 설정 */
        }

        .form-control {
            background-color: rgba(255, 255, 255, 0.1); /* 입력 필드의 배경색을 반투명한 흰색으로 설정 */
            color: white; /* 입력 필드의 텍스트 색상을 흰색으로 설정 */
            width: 40%;
        }
		
		.form-control:disabled {
			background-color: rgba(255, 255, 255, 0.1);
		}
        .btn-primary {
            background-color: #343a40; /* 프라이머리 버튼의 배경색을 어두운 회색으로 설정 */
            border-color: #343a40; /* 프라이머리 버튼의 테두리 색상을 동일하게 설정 */
        }

        .btn-primary:hover {
            background-color: #212529; /* 마우스 오버시 버튼의 배경색을 어두운 회색으로 변경 */
            border-color: #212529; /* 마우스 오버시 버튼의 테두리 색상을 어두운 회색으로 변경 */
        }
    </style>
    
<script>
    $(document).ready(function() {
        $("#pw , #confirm-pw").keyup(checkPasswordMatch);
        $("#id").keyup(checkId);
        $("#name").keyup(checkName);
        $("#pw").keyup(checkPw);
        $("#birth").keyup(checkBirth);
        
        $("#domain").change(changeDomain);
        $("#submitBtn").click(removeDisabled);
        
        $("select").attr('style', 'background-color:black');
    });
    
    let changeDomain = function() {
        var domin = $(this).val();

        if (domin === "1") {
            $("#email2").attr("disabled", false);
            $("#email2").val("");
        } else {
            $("#email2").attr("disabled", true);
            $("#email2").val(domin);
        }
    }
    
    let checkPw = function() {
        let pw = $(this).val();
        let pattern = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{6,15}$/; // 한글,영문 대소문자와 숫자, 4자 이상 12자 이하
        let valid = pattern.test(pw);
        let setColor = function(col) {
			$("#pw-match").text(" ※ 영문,숫자,특수문자 조합 6~15자리").attr('style', 'color:'+col);
		}
		if(pw === "")
			$("#pw-match").text("");
        else if (valid)
        	setColor("green");
        else
        	setColor("red");
	}
    
    let checkName = function() {
        let name = $(this).val();
        let pattern = /^[가-힣a-zA-Z]{2,15}$/; // 한글,영문 대소문자와 숫자, 4자 이상 12자 이하
        let valid = pattern.test(name);
        let setColor = function(col) {
			$("#name-match").text(" ※ 한글,영문대소문자 길이는 2~15").attr('style', 'color:'+col);
		}
		if(name === "")
			$("#name-match").text("");
        else if (valid)
        	setColor("green");
        else
        	setColor("red");
	}
    		
    let checkId = function() {
        let id = $(this).val();
        let pattern = /^[a-zA-Z0-9]{4,12}$/; // 영문 대소문자와 숫자, 4자 이상 12자 이하
        let valid = pattern.test(id);
        let setColor = function(col) {
			$("#id-match").text(" ※ 영문대소문자와 숫자, 길이는 4~12").attr('style', 'color:'+col);
		}
		if(id === "")
			$("#id-match").text("");
        else if (valid)
        	setColor("green");
        else
        	setColor("red");
	}
    
    let checkPasswordMatch = function() {
        let password = $("#pw").val();
        let confirmPassword = $("#confirm-pw").val();
        if(confirmPassword === "")
        	$("#confirmPw-match").text(""); 
        else if (password === confirmPassword)
            $("#confirmPw-match").text(" ※ 비밀번호가 일치합니다").attr('style', 'color:green');           
        else
           	$("#confirmPw-match").text(" ※ 비밀번호가 일치하지 않습니다").attr('style', 'color:red');
    }
    
    let checkBirth = function() {
        var birth = $("#birth").val();
        var pattern = /^\d{4}-\d{2}-\d{2}$/;
        var valid = pattern.test(birth);
        let setColor = function(col) {
			$("#birth-match").text(" ※ 올바른 날짜 형식(yyyy-MM-dd)으로 입력하세요.").attr('style', 'color:'+col);
		}
			if(birth === "")
				$("#birth-match").text("");
        	else if (valid) {
        		setColor("green");
            } else {
            	setColor("red");
            }
	}
    
    let removeDisabled = function() {
		const input = $("input");
		console.dir(input);
		for(let element of input){
			$(element).prop("disabled", false);
		}
		$("#memberForm").submit();
	}
</script>
</head>
<body>
    <div class="container">
    	<h2>회원가입</h2>
        <div class="row justify-content-center">
	        <div class="col-md-6">
		        <form id="memberForm" action="<c:url value='/member/write.do'/>" method="post">
		            <div class="form-group">
		                <label for="id">아이디<span id="id-match"></label>
		                <input id="id" name="id" required class="form-control" pattern="^[a-zA-Z0-9]{4,12}$" placeholder="아이디">
		            </div>
		            <div class="form-group">
		                <label for="name">이름<span id="name-match"></label>
		                <input id="name" name="name" required class="form-control" pattern="^[가-힣a-zA-Z]{2,15}$" placeholder="이름">
		            </div>
		            <div class="form-group">
		                <label for="pw">비밀번호<span id="pw-match"></label>
		                <input id="pw" name="pw" required type="password" class="form-control" pattern="^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{6,15}$" placeholder="비밀번호">
		            </div>
		            <div class="form-group">
		                <label for="confirm-password">비밀번호 확인<span id="confirmPw-match"></span></label>
		                <input id="confirm-pw" type="password" class="form-control" placeholder="비밀번호 확인">
		            </div>
		            <div class="form-group">
			            <label for="birth">생년월일<span id="birth-match"></label>
						<input id="birth" name="birth" type="text" class="form-control" pattern="^\d{4}-\d{2}-\d{2}$" placeholder="yyyy-MM-dd" required>
		            </div>
		            <div class="form-group">
		                <label for="email">이메일 주소</label>
		                <div class="form-inline">
							<input id="email1" name="email1" type="text" class="email form-control" placeholder="이메일 주소" required>
		            	@ <input id="email2" name="email2" type="text" class="email form-control" disabled required>
		            	<select id="domain" class="email form-control">
			            	<option value="" selected >선택하세요</option>
							<option value="naver.com">naver.com</option>
							<option value="kakao.com">kakao.com</option>
						    <option value="google.com">google.com</option>
						    <option value="1">직접입력</option>
						</select>		
						              
		                </div>
		            </div>
		            <button type="button" id="submitBtn" class="btn btn-primary">가입하기</button>
		        </form>
	        </div>
        </div>
    </div>
</body>
</html>