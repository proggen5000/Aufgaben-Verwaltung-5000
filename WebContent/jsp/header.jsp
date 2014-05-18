<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="de">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<link type="text/css" rel="stylesheet" href="<c:url value="/css/bootstrap.css" />" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="/js/bootstrap.js"></script>
	<script src="/js/tooltip.js"></script>
	<script src="/js/selector.js"></script>
	<title>Tasks 5000 &#187; ${param.page_title}</title>
</head>
<body> 
	<div class="container"><div id="mainbox">
		<div class="header"><a href="/">Tasks 5000</a></div>
		<div class="row">
			<div class="content col-sm-9">
			
				<%-- Alerts, falls via Session �bergeben --%>
				<c:if test="${sessionScope.alert != null}">
		  			<c:if test="${sessionScope.alert_mode == null}">
		  				<div class="alert alert-success">${sessionScope.alert}</div>
		  			</c:if>
		  			<c:if test="${sessionScope.alert_mode != null}">
		  				<div class="alert alert-${sessionScope.alert_mode}">${sessionScope.alert}</div>
		  			</c:if>
		  			<c:remove var="alert" />
		  			<c:remove var="alert_mode" />
		  		</c:if>