<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>


<!DOCTYPE html>
<html>
	<head>	
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<c:url value="/resources/" var="resourcePath" />
		<link rel="stylesheet" href="${resourcePath}css/bootstrap.min.css" >
		<link rel="stylesheet" href="${resourcePath}css/bootstrap-theme.min.css" >
	
	
	<title>Login - BVRio</title>
	</head>
	<body>
		<div class="container">
			
		<h1>Login da BVRio</h1>
			<form:form servletRelativeAction="/login" method="post">
			
				<c:if test="${param.error != null}">
	                <div class="alert alert-danger">
	                       <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
	                       	<strong>Falha ao fazer Login</strong>
	                       	<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
	                       </c:if>
	                </div>
	            </c:if>
			
				<div class="form-group">
					<label>Email</label>
					<input type="email" Class="form-control" name="username" placeholder="fulano@email.com" required autofocus/>
				</div>
				<div class="form-group">
					<label>Senha</label>
					<input Class="form-control" type="password" name="password" placeholder="senha" required />
				</div>
				<button class="btn btn-primary" type="submit">Logar</button>
			</form:form>
		</div>
	</body>
</html>