<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:pageTamplate titulo="Lista dos Clientes cadastrados">

	
	<jsp:attribute name="csrfOnHead">
	<!-- csrf ficará disponível no head para o envio da requisição ajax -->
		<meta name="_csrf" content="${_csrf.token}"/>
		<meta name="_csrf_header" content="${_csrf.headerName}"/>
	</jsp:attribute>
	
	<jsp:attribute name="scriptRemove">
	
		<script>
			function removeCliente(url, id){ 
		 
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
		
			               $.ajax({
			                     type: "POST",	                   
			                     url: url,
			                     beforeSend: function(xhr) {	                        
			                         xhr.setRequestHeader(header, token);
			                     },
			                     success: function () {
			                    	 
			                    	 $('#'+id).remove();
			                    	   
			                     }
			               });
			};
					
	</script>
			
	</jsp:attribute>
	
	<jsp:body>
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
		    <div class="table-responsive">
			    <table class="table table-condensed table-triped table-hover">
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
			            <tr id="${cliente.id}">
			            	<c:url value="/clientes/${cliente.id}" var="detalhePath" />
			                <td><a title="Exibir mais detalhes " href="${detalhePath}">${cliente.nome}</a></td>
			                <td>${cliente.email}</td>
			                <td><fmt:formatDate value="${cliente.dataNascimento.time}" pattern="dd/MM/yyyy"/></td>
			                              
			                <!-- caminhos -->
			                <c:url value="/clientes/${cliente.id}/alterar" var="alterarPath" />
			                <c:url value="/clientes/${cliente.id}/remover" var="removerPath" />
			               	                              
			                <td>
			                	<button class="btn btn-primary" onclick="location.href='${alterarPath}'" title="Alterar ${cliente.nome }">
			                		<span class="glyphicon glyphicon-wrench" aria-hidden="true"></span>
								</button>
			                </td>
			                <td>
			                
			                <button id="removerCliente" class="btn btn-danger" onclick="removeCliente('${removerPath}', '${cliente.id }')" title="Remover ${cliente.nome }">
							  		  <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>ajax
							 </button>
			                
			                <form:form action="${removerPath}"  method="post">
							  		<button type="submit" class="btn btn-danger" title="Remover ${cliente.nome }">
							  		  <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
							   		</button>
			                </form:form>
								</td>
			                
			            </tr>
			        </c:forEach>
			      </tbody>
			    </table>
		    </div>
	    </div>
    </jsp:body>
     
</tags:pageTamplate>    
 
 
 
 
 
    