<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:pageTamplate titulo="Formulário de cadastro">

		
		<jsp:attribute name="scriptInputs">
		<c:url value="/resources/js" var="resourceJs" />
			<script src="${resourceJs}/inputmask-plugin.js" ></script>
			<script src="${resourceJs}/buscaCep.js" ></script>
		</jsp:attribute>
		
		
		<jsp:body>
		<div class="container">	
		
		<c:choose>
			<c:when test="${clienteForm['new']}">
				<h1><fmt:message key="titulo.cadastrar"/></h1>
			</c:when>
			<c:otherwise>
				<h1><fmt:message key="titulo.alterar"/></h1>
			</c:otherwise>
		</c:choose>
		
			<c:url value="/clientes" var="actionPath" />
			<form:form action="${actionPath}" method="post" commandName="clienteForm">
			
			<form:hidden path="id"/>
			<form:hidden path="versao" value="${clienteForm.versao}"/>
			
				<fieldset>
					<legend><fmt:message key="fieldSet.Legend.pessoal"/></legend>
					<s:bind path="nome" >
						<div class="form-group ${status.error ?  'has-error': ''}">
						 	<label><fmt:message key="label.nome"/></label>
						 	<form:input path="nome" cssClass="form-control ${status.error ?  'form-control-error': ''}" maxlength="40" autofocus="true" />
							<form:errors path="nome" cssClass="control-label"/>				  
						</div>
					</s:bind>
					
					<s:bind path="email">
						<div class="form-group ${status.error ?  'has-error': ''}">
							<label><fmt:message key="label.email"/></label>
							<div class="input-group">
								<span class="input-group-addon">@</span>
								<form:input path="email" cssClass="form-control ${status.error ?  'form-control-error': ''}" maxlength="40" type="email" />
							</div>
								<form:errors path="email" cssClass="control-label"/>
						</div>
					</s:bind>
					<s:bind path="dataNascimento">
						<div class="form-group ${status.error ?  'has-error': ''}">
						<label><fmt:message key="label.dataNascimento"/></label>
							<form:input path="dataNascimento" cssClass="form-control ${status.error ?  'form-control-error': ''}" placeholder="dd/mm/aaaa" data-mask="99/99/9999"/>
							<form:errors  path="dataNascimento" cssClass="control-label"/>
						</div>
					</s:bind>
				</fieldset>
				
				<fieldset>
					<legend><fmt:message key="fieldSet.Legend.endereco"/></legend>
					
					<s:bind path="endereco.cep">
						<div class="form-group ${status.error ?  'has-error': ''}">
							<label><fmt:message key="label.endereco.cep"/></label>
							<form:input id="cep" path="endereco.cep" cssClass="form-control ${status.error ?  'form-control-error': ''}" maxlength="9" data-mask="99999-999" placeholder="00000-000" />
							<form:errors path="endereco.cep" class="control-label" />	
						</div>			
					</s:bind>
					
					<s:bind path="endereco.logradouro">
						<div class="form-group ${status.error ?  'has-error': ''}">
							<label><fmt:message key="label.endereco.logradouro"/></label>
							<form:input id="logradouro" path="endereco.logradouro" cssClass="form-control ${status.error ?  'form-control-error': ''}" maxlength="40" />
							<form:errors path="endereco.logradouro" cssClass="control-label"/>
						</div>
					</s:bind>
					
					<s:bind path="endereco.bairro">
						<div class="form-group ${status.error ?  'has-error': ''}">
							<label><fmt:message key="label.endereco.bairro"/></label>
							<form:input id="bairro" path="endereco.bairro" cssClass="form-control ${status.error ?  'form-control-error': ''}" maxlength="40" />
							<form:errors path="endereco.bairro" cssClass="control-label"/>
						</div>
					</s:bind>
								
					<s:bind path="endereco.cidade">
						<div class="form-group ${status.error ?  'has-error': ''}">
							<label><fmt:message key="label.endereco.cidade"/></label>
							<form:input id="localidade" path="endereco.cidade" cssClass="form-control ${status.error ?  'form-control-error': ''}" />
							<form:errors path="endereco.cidade" cssClass="control-label"/>
						</div>			
					</s:bind>
					
					<s:bind path="endereco.estado">
						<div class="form-group ${status.error ?  'has-error': ''}">
							<label><fmt:message key="label.endereco.estado"/></label>
							<form:select id="uf"  path="endereco.estado" cssClass="form-control ${status.error ?  'form-control-error': ''}" maxlength="40" >
								<c:forEach items="${estados }" var="estado">
						    		<form:option value="${estado }">${estado.nome }</form:option>
						    	</c:forEach>
						    </form:select>
						    <form:errors path="endereco.estado" cssClass="control-label"/>
						</div>
					</s:bind>
					
				</fieldset>
				<button class="btn btn-primary" type="submit"><fmt:message key="field.button.salvar"/></button>
				
			</form:form>
		</div><!-- class="container" -->
		</jsp:body>
				
</tags:pageTamplate>




