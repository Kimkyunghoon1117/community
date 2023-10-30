<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<
<link rel="stylesheet" href="/resources/css/bootstrap.min.css">
<script src="/resources/js/jquery-3.6.4.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
<style>
        body {
            background-color: #000;
            color: #fff;
        }
        .container {
            max-width: 400px;
            margin: 0 auto;
            padding: 20px;
            border-radius: 10px;
            background-color: rgba(0, 0, 0, 0.7);
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            margin-bottom: 30px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            font-weight: bold;
        }
        .btn-primary {
            width: 100%;
            background-color: #007bff;
            border-color: #007bff;
        }
        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #0056b3;
        }
        /* input.img-button {
  background: url("/resources/img/kakao_login.png") no-repeat;
  width: 250px;
  height: 150px;
} */
    </style>
</head>
<body>
    <div class="container">
        <h2 class="mt-5">로그인</h2>
        <form action="login.do" method="post">
            <div class="form-group">
                <label for="id">ID</label>
                <input type="text" name="id" required class="form-control" id="id">
            </div>
            <div class="form-group">
                <label for="pw">Password</label>
                <input type="password" name="pw" required class="form-control" id="pw">
            </div>
            <button type="submit" class="btn btn-primary">Login</button>
            <a href='https://kauth.kakao.com/oauth/authorize?client_id=24ceadf6977df8dcd7245a08be24c116&redirect_uri=http://localhost/member/kakao&response_type=code'><img height="38px" src="/resources/img/kakao_login.png"/></a>
        	<button type="button">
  <img src="/resources/img/kakao_login.png" alt="대체 텍스트" onclick="" ></button>
  <input type="button" class="img-button" onclick="alert('클릭!')">
        </form>
    </div>
</body>
</html>