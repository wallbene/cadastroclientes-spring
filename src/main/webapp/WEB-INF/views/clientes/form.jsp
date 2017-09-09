<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:pageTamplate titulo="Formulário de cadastro">

		<jsp:attribute name="inputMask">
		<c:url value="/resources/js" var="resourceJs" />
			<script src="${resourceJs}/inputmask-plugin.js" ></script>
		</jsp:attribute>
		
		
		<jsp:body>
		<div class="container">	
		
		<c:if test="${not empty clienteForm}" var="vazio"/>
		<c:choose>
			<c:when test="${ vazio or clienteForm['new']}">
				<h1>Novo Usuário</h1>
			</c:when>
			<c:otherwise>
				<h1>Alterar Usuário</h1>
			</c:otherwise>
		</c:choose>
		
			<c:url value="/clientes" var="actionPath" />
			<form:form action="${actionPath}" method="post" commandName="clienteForm">
			
			<form:hidden path="id"/>
			
				<s:bind path="nome" >
					<div class="form-group ${status.error ?  'has-error': ''}">
					 	<label>Nome</label>
					 	<form:input path="nome" cssClass="form-control ${status.error ?  'form-control-error': ''}" maxlength="40" placeholder="Nome completo" autofocus="true" required="true" />
						<form:errors path="nome" cssClass="control-label"/>				  
					</div>
				</s:bind>
				
				<s:bind path="email">
					<div class="form-group ${status.error ?  'has-error': ''}">
						<label>Email</label>
						<form:input path="email" cssClass="form-control ${status.error ?  'form-control-error': ''}" maxlength="40" type="email" placeholder="email@exemplo.com" required="true" />
						<form:errors path="email" cssClass="control-label"/>
					</div>
				</s:bind>
				<s:bind path="dataNascimento">
					<div class="form-group ${status.error ?  'has-error': ''}">
					<label>Data de Nascimento</label>
						<form:input path="dataNascimento" cssClass="form-control ${status.error ?  'form-control-error': ''}" placeholder="dd/mm/aaaa" data-mask="99/99/9999" required="true"/>
						<form:errors  path="dataNascimento" cssClass="control-label"/>
					</div>
				</s:bind>
				
				<fieldset>
					<legend>Dados de Endereço</legend>
					<s:bind path="endereco.endereco">
						<div class="form-group ${status.error ?  'has-error': ''}">
							<label>Enderenço</label>
							<form:input path="endereco.endereco" cssClass="form-control ${status.error ?  'form-control-error': ''}" maxlength="40" placeholder="Logradouro e complemento" required="true" />
							<form:errors path="endereco.endereco" cssClass="control-label"/>
						</div>
					</s:bind>
					
					<s:bind path="endereco.estado">
						<div class="form-group ${status.error ?  'has-error': ''}">
							<label>Estado</label>
							<form:select  path="endereco.estado" cssClass="form-control ${status.error ?  'form-control-error': ''}" maxlength="40" >
								<c:forEach items="${estados }" var="estado">
						    		<form:option value="${estado }">${estado.nome }</form:option>
						    	</c:forEach>
						    </form:select>
						    <form:errors path="endereco.estado" cssClass="control-label"/>
						</div>
					</s:bind>
					
					<s:bind path="endereco.cidade">
						<div class="form-group ${status.error ?  'has-error': ''}">
							<label>Cidade</label>
							<form:input path="endereco.cidade" cssClass="form-control ${status.error ?  'form-control-error': ''}" placeholder="Cidade" required="true" />
							<form:errors path="endereco.cidade" cssClass="control-label"/>
						</div>			
					</s:bind>
					
					<s:bind path="endereco.cep">
						<div class="form-group ${status.error ?  'has-error': ''}">
							<label>Cep</label>
							<form:input path="endereco.cep" cssClass="form-control ${status.error ?  'form-control-error': ''}" maxlength="9" data-mask="99999-999" placeholder="00000-000" required="true" />
							<form:errors path="endereco.cep" class="control-label" />	
						</div>			
					</s:bind>
					
				</fieldset>
				<button class="btn btn-primary" type="submit">Salvar</button>
				
			</form:form>
		</div><!-- class="container" -->
		</jsp:body>
				
</tags:pageTamplate>




