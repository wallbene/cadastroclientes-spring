<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:pageTamplate titulo="Detalhes do Cliente">

	<div class="container">	
	    <h1>Detalhes do Cliente</h1>
	    
    <table class="table table-triped table-hover">
    	
            <tr>
            	<th>Nome</th>
            	<td>${cliente.nome}</td>
            </tr>
            <tr>
            	<th>Email</th>
            	<td>${cliente.email}</td>
            </tr>
            <tr>
            	<th>Data de Nascimento</th>
            	<td><fmt:formatDate value="${cliente.dataNascimento.time}" pattern="dd/MM/yyyy"/></td>
            </tr>
            <tr>
            	<th>Logradouro</th>
            	<td>${cliente.endereco.logradouro}</td>	            	
            </tr>
            <tr>
            	<th>Bairro</th>
            	<td>${cliente.endereco.bairro}</td>	            	
            </tr>
            <tr>
            	<th>Estado</th>
                <td>${cliente.endereco.estado.nome}</td>
            </tr>
            <tr>
            	<th>Cidade</th>
                <td>${cliente.endereco.cidade}</td>
            	
            </tr>
            <tr>
            	<th>Cep</th>
                <td>${cliente.endereco.cep}</td>
            	
            </tr>
            
            
            <!-- caminhos -->
		                <c:url value="/clientes/${cliente.id}/alterar" var="alterarPath" />
		                <c:url value="/clientes/${cliente.id}/remover" var="removerPath" />
		               	                                          
            <tr>
            	<th>Alterar</th>
                <td>
                	<button class="btn btn-primary" onclick="location.href='${alterarPath}'" title="Alterar ${cliente.nome }">
                		<span class="glyphicon glyphicon-wrench" aria-hidden="true"></span>
					</button>
                </td>            	
            </tr>
            <tr>
            	<th>Remover</th>
                <td>
	                <form:form action="${removerPath}"  method="post">
				  		<button type="submit" id="removerCliente" class="btn btn-danger" title="Remover ${cliente.nome }">
				  		  <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
				   		</button>
	                </form:form>
				</td>
            	
            </tr>
            
            
            
            
       </table>
    </div>
     
</tags:pageTamplate>    
 
 
 
 
 
    