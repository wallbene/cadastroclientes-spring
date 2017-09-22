<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:pageTamplate titulo="Home">
	
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading"><h2>Estatísticas</h2></div>
			<div class="panel-body">
					
						<a href='<c:url value="/estatisticas/limpar"/>'>Limpar dados</a>
						<table class="table table-striped">
								<!-- Cache-->
							<thead>
								<tr>
									<th><h3>Cache</h3></th>									
								</tr>
							</thead>
							<tbody>
							<tr>
								<th>Hit</th>
								<th>Miss</th>
							</tr>
								<tr>									
									<!-- Hit -->
									<td>${statistics.secondLevelCacheHitCount}</td>
									<!-- Miss -->
									<td>${statistics.secondLevelCacheMissCount}</td>
								</tr>
							</tbody>
							
									<!-- Connections Count-->						
							<thead>
								<tr>
									<th><h3>Conexões</h3></th>									
								</tr>
							</thead>
							<tbody>
							<tr>
								<th>Sessões não fechadas</th>
								<th>Nº de conexões</th>
								<th>Nº total de transações</th>
								<th>Nº de transações ok</th>
							</tr>
								<tr>																		
									<td>${statistics.sessionOpenCount - statistics.sessionCloseCount}</td>
									<td>${statistics.connectCount}</td>									
									<td>${statistics.transactionCount}</td>
									<td>${statistics.successfulTransactionCount}</td>															
								</tr>
							</tbody>
									<!-- Query-->
							<thead>
								<tr>
									<th><h3>Query</h3></th>									
								</tr>
							</thead>
							<tbody>
							<tr>
								<th>Consulta mais lenta</th>
								<th>tempo</th>								
							</tr>
								<tr>																		
									<td>${statistics.queryExecutionMaxTimeQueryString}</td>
									<td>${statistics.queryExecutionMaxTime}</td>																																	
								</tr>
							</tbody>
						</table>
					
				</div>
			</div>
		</div>

</tags:pageTamplate>
