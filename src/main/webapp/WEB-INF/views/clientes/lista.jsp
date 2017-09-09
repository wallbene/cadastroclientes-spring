<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:pageTamplate titulo="Lista dos Clientes cadastrados">

	<div class="container">
	<c:if test="${not empty msg}">
		<div class="alert alert-${css } alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="close">
				<span aria-hidden="true">x</span>
			</button>
			<strong>${msg }</strong>
		</div>
	</c:if>
		
	    <h1>Lista de Clientes</h1>
	    
	    <table class="table table-triped table-hover">
	    <thead>
	        <tr>
	            <th>Nome</th>
	            <th>Email</th>
	            <th>Data de Nascimento</th>
	            <th></th>
	            <th></th>
	        </tr>
		</thead>
		<tbody>
	        <c:forEach items="${clientes}" var="cliente">
	            <tr>
	            	<c:url value="/clientes/${cliente.id}" var="detalhePath" />
	                <td><a title="Exibir mais detalhes " href="${detalhePath}">${cliente.nome}</a></td>
	                <td>${cliente.email}</td>
	                <td><fmt:formatDate value="${cliente.dataNascimento.time}" pattern="dd/MM/yyyy"/></td>
	                              
	                <!-- caminhos -->
	                <c:url value="/clientes/${cliente.id}/alterar" var="alterarPath" />
	                <c:url value="/clientes/${cliente.id}/remover" var="removerPath" />
	               	                              
	                <td>
	                	<button class="btn btn-primary" onclick="location.href='${alterarPath}'">Alterar</button>
	                </td>
	                
		                <td>
	                <form:form action="${removerPath}"  method="post">
					  		<button type="submit" id="removerCliente" class="btn btn-danger">Remover</button>
	                </form:form>
						</td>
	                
	            </tr>
	        </c:forEach>
	      </tbody>
	    </table>
    </div>
     
</tags:pageTamplate>    
 
 
 
 
 
    