<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:pageTamplate titulo="Cadastro de Usuário">
		
	<div class="container">
		
		<h1>Cadastro de Usuário</h1>
		
			<c:url value="/usuarios" var="actionPath" />
			<form:form action="${actionPath}" method="post" modelAttribute="usuario">
				<s:bind path="nome" >
					<div class="form-group ${status.error ?  'has-error': ''}">
					 	<label>Nome</label>
					 	<form:input path="nome" cssClass="form-control ${status.error ?  'form-control-error': ''}" maxlength="40" type="text" placeholder="Nome" autofocus="true" required="true"/>
						<form:errors path="nome" cssClass="control-label"/>
					</div>
				</s:bind>
				
				<s:bind path="email">
					<div class="form-group ${status.error ?  'has-error': ''}">
						<label>Email</label>
						<form:input path="email" cssClass="form-control ${status.error ?  'form-control-error': ''}" maxlength="40" type="email" placeholder="email como acesso" required="true" />
						<form:errors path="email" cssClass="control-label"/>
					</div>
				</s:bind>
				
				<s:bind path="senha">
					<div class="form-group ${status.error ?  'has-error': ''}">
					<label>senha</label>
						<form:input path="senha" cssClass="form-control ${status.error ?  'form-control-error': ''}" type="password" placeholder="senha" required="true"/>
						<form:errors  path="senha" cssClass="control-label"/>
					</div>
				</s:bind>						
					
				<button class="btn btn-primary" type="submit">Cadastrar</button>
				
			</form:form>
		</div><!--final "container" -->		
</tags:pageTamplate>
