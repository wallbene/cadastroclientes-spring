<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="titulo"  required="true"%>
<%@ attribute name="scriptInputs" fragment="true" %>
<%@ attribute name="csrfOnHead"  fragment="true" %>
<%@ attribute name="scriptLista"  fragment="true" %>


<!DOCTYPE html>
<html>
	<head>	
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<jsp:invoke fragment="csrfOnHead"/>
		<c:url value="/resources/" var="resourcePath" />
		<link rel="stylesheet" href="${resourcePath}css/bootstrap.min.css" >
		<link rel="stylesheet" href="${resourcePath}css/bootstrap-theme.min.css" >
		<link rel="stylesheet" href="${resourcePath}css/navbar-fixed-top.css" >
	
	
	<title>${titulo } - BVRio</title>
	</head>
	<body>
	<%@include file="/WEB-INF/views/cabecalho.jsp" %>


	<jsp:doBody  />

	<hr>
	
	<%@include file="/WEB-INF/views/rodape.jsp" %>

    <script src="${resourcePath}js/jquery-3.2.1.min.js" ></script>
    <script src="${resourcePath}js/bootstrap.min.js" ></script>
	<jsp:invoke fragment="scriptInputs"/>
	<jsp:invoke fragment="scriptLista"/>
	
 </body>
</html>