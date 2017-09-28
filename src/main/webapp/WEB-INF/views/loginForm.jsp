<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
		<link rel="stylesheet" href="${resourcePath}css/login.css" >
	
	
	<title>Login - BVRio</title>
	</head>
	<body>
		<div class="container">
			<form:form servletRelativeAction="/login" method="post" cssClass="form-signin">
			<h2 class="form-signin-heading"><fmt:message key="login.subtitulo"/></h2>
				<c:if test="${param.error != null}">
	                <div class="alert alert-danger">
	                       <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">	                       	
	                       	<fmt:message key="erro.autenticacao"/>	                       		                       	
	                       	<%-- <c value="${SPRING_SECURITY_LAST_EXCEPTION.message}" /> --%>
	                       </c:if>
	                </div>
	            </c:if>
			
				<label for="inputEmail" class="sr-only"><fmt:message key="label.email"/></label>					
				<input type="email" id="inputEmail" Class="form-control" name="username" placeholder="<fmt:message key="label.email"/>" required autofocus/>				
				<label for="inputPassword" class="sr-only"><fmt:message key="label.senha"/></label>
				<input Class="form-control" id="inputPassword" type="password" placeholder="<fmt:message key="label.senha"/>" name="password" required />
				<!-- não implementado -->
				<div class="checkbox">
		          <label>
		            <input type="checkbox" value="remember-me"> Remember me
		          </label>
        		</div>
				<button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="field.button.entrar"/></button>
				<div>
					<a href="#">Esqueci minha senha</a>
				</div>
			</form:form>
		</div><!-- fim container -->
	</body>
</html>