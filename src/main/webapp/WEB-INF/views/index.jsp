<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tags:pageTamplate titulo="Home">
		
		
		<c:if test="${param.logout != null}">
	         <div class="alert alert-success">
	               Você efetuou logout.
	         </div>
  		</c:if>
		
		
		<section class="jumbotron text-center">
	      <div class="container">
	        <h1 class="jumbotron-heading">Sistema de Cadastro de Usuários</h1>
	        <p class="lead text-muted">Essa aplicação foi desenvolvida exclusivamente para o processo seletivo da <strong>BVRio</strong>.
	        						    Esse é um sistema simples desenvolvido em <strong>Java</strong>, porém aborda diversas
	        						     ferramentas no desenvolvimento web que a torna robusta e escalável.
	        						     Uma vesão do projeto pode ser encontrada no <a href="https://github.com/wallbene/cadastroclientes-spring"><strong>Github</strong></a>
	        						 	</p>
	        					<ul class="list-group">
	        						<li class="list-group-item active">Tecnologias abordadas</li>
		        					<li class="list-group-item"><strong>Spring MVC</strong></li>
		        					<li class="list-group-item"><strong>JSP, Tamplates</strong></li>
		        					<li class="list-group-item"><strong>JSTL</strong></li>
		        					<li class="list-group-item"><strong>JPA e Hibernete</strong></li>
		        					<li class="list-group-item"><strong>Spring Security</strong></li>
		        					<li class="list-group-item"><strong>Tomcat</strong></li>
		        					<li class="list-group-item"><strong>JUnit e Spring-test</strong></li>
		        					<li class="list-group-item"><strong>Cache com Guava</strong></li>
		        					<li class="list-group-item"><strong>Mysql em desenvolvimento</strong></li>
		        					<li class="list-group-item"><strong>Postgresql em produção</strong></li>
		        					<li class="list-group-item"><strong>Bootstrap</strong></li>
	        					</ul>		 
	        							
	        <p>
	        
	          <security:authorize access="!isAuthenticated()">
		          <a href='<c:url value="/login" />' class="btn btn-primary">Efetuar Login</a> ou
		          <a href='<c:url value="/usuarios/adicionar" />' class="btn btn-secondary">Criar um novo cadastro</a>
	          </security:authorize>
	        </p>
	      </div>
	    </section>
</tags:pageTamplate>
