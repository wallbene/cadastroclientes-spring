<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:pageTamplate titulo="Home">
		
		<!-- ajustar o paramentro de logout -->
		<c:if test="${param.logout != null}">
	         <div class="alert alert-success">
	               Você efetuou logout.
	         </div>
  		</c:if>
		
		
		<section class="jumbotron text-center">
	      <div class="container">
	        <h1 class="jumbotron-heading"><fmt:message key="titulo.principal"/></h1>
	        <p class="lead text-muted"><fmt:message key="descricao.principal"/>
	        						 	</p>
	        					<ul class="list-group">
	        						<li class="list-group-item active"><fmt:message key="titulo.tecnologia"/></li>
		        					<li class="list-group-item"><strong>Spring MVC</strong></li>
		        					<li class="list-group-item"><strong>JSP, Templates</strong></li>
		        					<li class="list-group-item"><strong>JSTL</strong></li>
		        					<li class="list-group-item"><strong>JPA e Hibernete</strong></li>
		        					<li class="list-group-item"><strong>Spring Security</strong></li>
		        					<li class="list-group-item"><strong>Tomcat</strong></li>
									<li class="list-group-item"><strong>Maven</strong></li>		        					
		        					<li class="list-group-item"><strong>JUnit e Spring-test</strong></li>
		        					<li class="list-group-item"><strong>Cache com Guava</strong></li>
		        					<li class="list-group-item"><strong>Mysql em desenvolvimento</strong></li>
		        					<li class="list-group-item"><strong>Postgresql em produção</strong></li>
		        					<li class="list-group-item"><strong>Bootstrap</strong></li>
	        					</ul>		 
	        							
	        <p>
	        
	          <security:authorize access="!isAuthenticated()">
		          <a href='<c:url value="/login" />' class="btn btn-primary"><fmt:message key="navegacao.login"/></a> 
		          <a href='<c:url value="/usuarios/adicionar" />' class="btn btn-secondary"><fmt:message key="navegacao.nova.conta"/></a>
	          </security:authorize>
	        </p>
	      </div>
	    </section>
</tags:pageTamplate>
