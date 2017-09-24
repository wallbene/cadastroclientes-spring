<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:pageTamplate titulo="Detalhes do Cliente">

	<div class="container">	
	    <h1><fmt:message  key="titulo.principal.detalhe.cliente"/></h1>
	    
    <table class="table table-triped table-hover">
    	
            <tr>
            	<th><fmt:message key="label.nome"/></th>
            	<td>${cliente.nome}</td>
            </tr>
            <tr>
            	<th><fmt:message key="label.email"/></th>
            	<td>${cliente.email}</td>
            </tr>
            <tr>
            	<th><fmt:message key="label.dataNascimento"/></th>
            	<td><fmt:formatDate value="${cliente.dataNascimento.time}" pattern="dd/MM/yyyy"/></td>
            </tr>
            <tr>
            	<th><fmt:message key="label.endereco.logradouro"/></th>
            	<td>${cliente.endereco.logradouro}</td>	            	
            </tr>
            <tr>
            	<th><fmt:message key="label.endereco.bairro"/></th>
            	<td>${cliente.endereco.bairro}</td>	            	
            </tr>
            <tr>
            	<th><fmt:message key="label.endereco.estado"/></th>
                <td>${cliente.endereco.estado.nome}</td>
            </tr>
            <tr>
            	<th><fmt:message key="label.endereco.cidade"/></th>
                <td>${cliente.endereco.cidade}</td>
            	
            </tr>
            <tr>
            	<th><fmt:message key="label.endereco.cep"/></th>
                <td>${cliente.endereco.cep}</td>
            	
            </tr>
            
            
            <!-- caminhos -->
		                <c:url value="/clientes/${cliente.id}/alterar" var="alterarPath" />
		                <c:url value="/clientes/${cliente.id}/remover" var="removerPath" />
		               	                                          
            <tr>
            <fmt:message key="field.title.alterar" var="titleAlterar"/>
            	<th>${titleAlterar }</th>
                <td>
                	<button class="btn btn-primary" onclick="location.href='${alterarPath}'" title="${titleAlterar } ${cliente.nome }">
                		<span class="glyphicon glyphicon-wrench" aria-hidden="true"></span>
					</button>
                </td>            	
            </tr>
            <tr>
            <fmt:message key="field.title.remover" var="titleRemover"/>
            	<th>${titleRemover }</th>
                <td>
	                <form:form action="${removerPath}"  method="post">
				  		<button type="submit" id="removerCliente" class="btn btn-danger" title="${titleRemover } ${cliente.nome }">
				  		  <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
				   		</button>
	                </form:form>
				</td>
            	
            </tr>
            
            
            
            
       </table>
    </div>
     
</tags:pageTamplate>    
 
 
 
 
 
    