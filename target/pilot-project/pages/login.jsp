<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="../common/head.jsp" />
<title>Login</title>
</head>
<body>
<header class="header-login">
	<div class="container">
		<div class="d-flex">
			<div class="main-logo-login">Pilot Project</div>
		</div>
	</div>
</header>	
	<div class="container">
		<form action="/loginAction"  method="POST" class="loginform">
			<h2 class="d-flex justify-content-center">Sign In</h3>
			<div class="form-group">
				<label for="username">Username</label>
				<input type="text" class="form-control form-login"  name="username" id="username" placeholder="Username" required />
			</div>
			<div class="form-group">
				<label for="password">Password</label>
				<input type="password" class="form-control form-login"  name="password" id="password" placeholder="Password" required />
			</div>
			<div class="form-group form-check">
      			<label class="form-check-label">
       			<input class="form-check-input" type="checkbox" name="remember"> Remember me
     			</label>
   			</div>
    		<button type="submit" class="btn btn-md btn-block btn-primary form-login">Sign In</button>
		</form>
	</div>
</body>
</html>