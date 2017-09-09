<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
            	<th>Endereço</th>
            	<td>${cliente.endereco.endereco}</td>	            	
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
       </table>
    </div>
     
</tags:pageTamplate>    
 
 
 
 
 
    