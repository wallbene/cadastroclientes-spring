<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

		<!-- Fixed navbar --> 
		<nav class="navbar navbar-inverse navbar-fixed-top">
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
		    <!-- Usuário Logado -->
		    <security:authorize access="isAuthenticated()">
			      <ul class="nav navbar-nav">
			        <li><a href="${s:mvcUrl('CC#listar').build()}"><fmt:message key="navegacao.listaCliente"/></a></li>
			        <li><a href="${s:mvcUrl('CC#formAdicionar').build()}"><fmt:message key="navegacao.novoCliente"/></a></li>
			      </ul>
			</security:authorize> <!-- Fim Usuario Logago -->
			
				 <!-- Usuário deslogado -->
			      <ul class="nav navbar-nav navbar-right">			      
			      	<li id="fat-menu" class="dropdown">
						<a href="#" class="dropdown-toggle" id="drop" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
					  	<fmt:message key="idioma.palavra"/> <span class="caret"></span>
					 	</a>
					  <ul class="dropdown-menu" aria-labelledby="drop">
					  	<c:url value="/resources/img/flags" var="flags"/>
					  	<li><a href="?locale=en_us"  rel="nofollow"><img alt="English" src="${flags}/us.png"> English (US)</a></li> 
					   	<li><a href="?locale=pt" rel="nofollow"><img alt="Português" src="${flags}/br.png"> Português (Br)</a></li> 					   	 
					  </ul> 
					</li>
			      
			      	<!-- Usuário Logado -->
			      	<security:authorize access="isAuthenticated()">				
												
					<security:authentication property="principal" var="usuario"/>
			      	<li>
			      		<a href="#">
					      	 <fmt:message key="sessao.usuario">
					      	 	<fmt:param value="${usuario.nome }"/>
					      	 </fmt:message>
			      		</a>
			      	</li>
					
			      	<li>
			      	<a href='<c:url value="/logout" />'><fmt:message key="navegacao.logout"/></a>
			      	</li>			    		 
		      		</security:authorize>
		      		
		      		<!-- Usuário deslogado -->
			      	<security:authorize access="!isAuthenticated()">			     
				  		<li><a href='<c:url value="/login" />'><fmt:message key="navegacao.login"/></a></li>				  
			   	  	</security:authorize>
			      </ul>
		      
		      
		    </div><!-- /.navbar-collapse -->
		  </div>
		</nav>