<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:pageTamplate titulo="Lista dos clientes cadastrados">

	
	<jsp:attribute name="csrfOnHead">
	<!-- csrf ficará disponível no head para o envio das requisições ajax -->
		<meta name="_csrf" content="${_csrf.token}"/>
		<meta name="_csrf_header" content="${_csrf.headerName}"/>
	</jsp:attribute>
	
	<jsp:attribute name="scriptLista">
		<c:url value="/resources/js" var="resourceJs" />
			<script src="${resourceJs}/remove-cliente.js" ></script>
			<script src="${resourceJs}/filtra-clientes.js" ></script>		
	</jsp:attribute>
	
	<jsp:body>
		<div class="container">
		<c:if test="${not empty msg}">
			<div class="alert alert-${css } alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="close">
					<span aria-hidden="true">x</span>
				</button>
				<strong><fmt:message key="${msg }"/></strong>
			</div><!-- fim mensagens de alertas -->
		</c:if>
			
		    <h1><fmt:message key="titulo.principal.lista.cliente"/></h1>
		    <div class="table-responsive">
		    	<label>Busca</label>
		    	<input type="search" class="form-control" name="filtro" id="filtra-clientes" placeholder="Digite o nome do cliente">
			    <table class="table table-condensed table-triped table-bordered table-hover">
			    <thead>
			        <tr>
			            <th><fmt:message key="label.nome"/></th>
			            <th><fmt:message key="label.email"/></th>
			            <th><fmt:message key="label.dataNascimento"/></th>
			            <th></th>
			            <th></th>
			        </tr>
				</thead>
				<tbody id="tabela-clientes">
			        <c:forEach items="${clientes}" var="cliente">
			            <tr class="cliente" id="${cliente.id}">			       
			               	<fmt:message key="field.title.mostrar.detalhes" var="titleDetalhes"/>
			                <td class="info-nome">${cliente.nome}</td>
			                <td class="info-email">${cliente.email}</td>
			                <td class="info-data"><fmt:formatDate value="${cliente.dataNascimento.time}" pattern="dd/MM/yyyy"/></td>
			                              
			                <!-- caminhos -->
			                <c:url value="/clientes/${cliente.id}" var="detalhePath" />
			                <c:url value="/clientes/${cliente.id}/alterar" var="alterarPath" />
			                <c:url value="/clientes/${cliente.id}/remover" var="removerPath" />
			                <td>
			                	<button class="btn btn-primary" onclick="location.href='${detalhePath}'" title="${titleDetalhes } ${cliente.nome }">
			                		<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
								</button>
			                </td>			                
			                <fmt:message key="field.title.alterar" var="titleAlterar"/>
			                <td>
			                	<button class="btn btn-primary" onclick="location.href='${alterarPath}'" title="${titleAlterar } ${cliente.nome }">
			                		<span class="glyphicon glyphicon-wrench" aria-hidden="true"></span>
								</button>
			                </td>
			                <td>
				                <fmt:message key="field.title.remover" var="titleRemover"/>
				                <button id="removerCliente" class="btn btn-danger" onclick="removeCliente('${removerPath}', '${cliente.id }')" title="${titleRemover } ${cliente.nome }">
								  		  <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>ajax
								 </button>
							</td> 
			            </tr>
			        </c:forEach>
			      </tbody>
			    </table>
		    </div>
	    </div>
    </jsp:body>
     
</tags:pageTamplate>    
 
 
 
 
 
    