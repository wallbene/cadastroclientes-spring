<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>


		<nav class="navbar navbar-inverse">
		  <div class="container">
		    <div class="navbar-header">
		      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
		        <span class="sr-only">Toggle navigation</span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		        <span class="icon-bar"></span>
		      </button>
		      <a class="navbar-brand" href="${s:mvcUrl('HC#index').build()}">BVRio</a>
		    </div>
		    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		    
		    <security:authorize access="isAuthenticated()">
			      <ul class="nav navbar-nav">
			        <li><a href="${s:mvcUrl('CC#listar').build()}">Lista de Clientes</a></li>
			        <li><a href="${s:mvcUrl('CC#formAdicionar').build()}">Novo Cliente</a></li>
			      </ul>
			      <ul class="nav navbar-nav navbar-right">
			      	<li><a href="#">
			<security:authentication property="principal" var="usuario"/>
			      	Usuario: ${usuario.nome }
			      	</a></li>
					
			      	<li>
			      	<a href='<c:url value="/logout" />'>Sair</a>
			      	</li>		 
			      </ul>
		      </security:authorize>
			      <security:authorize access="!isAuthenticated()">
			      <ul class="nav navbar-nav navbar-right">
				  	<li><a href='<c:url value="/login" />'>Entrar</a></li>
				  </ul>
			   </security:authorize>
		      
		      
		    </div><!-- /.navbar-collapse -->
		  </div>
		</nav>