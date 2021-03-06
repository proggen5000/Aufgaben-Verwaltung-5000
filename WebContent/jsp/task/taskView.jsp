<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- Zugriff nicht �ber Servlet --%>
<c:if test="${!valid_request}">
	<c:redirect url="/error.jsp"><c:param name="error" value="Zugriff verweigert" /></c:redirect>
</c:if>

<jsp:include page="../header.jsp"><jsp:param name="page_title" value="${task.name}" /></jsp:include>
<jsp:include page="../menu.jsp"><jsp:param name="menu" value="teams" /></jsp:include>
			
				<ol class="breadcrumb">
					<li><a href="${pageContext.request.contextPath}/">Start</a></li>
					<li><a href="${pageContext.request.contextPath}/team?mode=view&id=${task.taskGroup.team.id}">${task.taskGroup.team.name}</a></li>
					<li><a href="${pageContext.request.contextPath}/taskGroup?mode=edit&id=${task.taskGroup.id}">${task.taskGroup.name}</a></li>
					<li class="active"></li>
				</ol>
				
				<h1>${task.name} <span class="glyphicon glyphicon-time small"></span></h1>
				<p>${task.description}</p>
				
				<c:if test="${fn:length(files) > 0}">
					<div class="panel panel-default">
						<div class="panel-heading"><h3 class="panel-title"><span class="glyphicon glyphicon-paperclip"></span> Verkn&uuml;pfte Dateien</h3></div>
						<div class="panel-body">
							<c:forEach var="file" items="${files}">
								<span class="glyphicon glyphicon-file"></span> <a href="${pageContext.request.contextPath}/file?mode=view&id=${file.id}">${file.name}</a> (${file.size} KB)<br />
							</c:forEach>
						</div>
					</div>
				</c:if>
			
			</div><%-- Ende content --%>
			<%-- Sidebar --%>
			<div class="sidebar col-sm-3">
				<h1>Aktionen</h1>
				<div class="list-group">
					<a href="${pageContext.request.contextPath}/task?mode=edit&id=${task.id}" class="list-group-item"><span class="glyphicon glyphicon-pencil"></span> Aufgabe bearbeiten</a>
					<a href="${pageContext.request.contextPath}/task?mode=remove&id=${task.id}" class="list-group-item"><span class="glyphicon glyphicon-remove"></span> Aufgabe l&ouml;schen</a>
				</div>
				<div class="list-group">
					<a href="${pageContext.request.contextPath}/file?mode=new&teamId=${task.taskGroup.team.id}&taskId=${task.id}" class="list-group-item"><span class="glyphicon glyphicon-file"></span> Datei hochladen &amp; verkn&uuml;pfen</a>
				</div>	
			
				<h1>Details</h1>
				<div class="list-group">
					<div class="list-group-item"><span class="glyphicon glyphicon-barcode"></span> ID: ${task.id}</div>
					<div class="list-group-item"><span class="glyphicon glyphicon-calendar"></span> <fmt:formatDate pattern="dd.MM.yyyy" value="${task.dateObject}" /></div>
					<c:if test="${task.deadline > 0}">
						<div class="list-group-item"><span class="glyphicon glyphicon-bell"></span> <fmt:formatDate pattern="dd.MM.yyyy" value="${task.deadlineObject}" /></div>
					</c:if>
					<div class="list-group-item"><span class="glyphicon glyphicon-dashboard"></span> Status: ${task.status}%</div>
				</div>
				
				<c:if test="${fn:length(users) > 0}">
					<h1>Zuteilung</h1>
					<div class="list-group">
						<c:forEach var="user" items="${users}">
							<a href="${pageContext.request.contextPath}/user?mode=view&id=${user.id}" class="list-group-item">
								<span class="glyphicon glyphicon-user"></span> ${user.name}
								<c:if test="${user.id == task.author.id}">
									<span class="label label-default pull-right">Ersteller</span>
								</c:if>
							</a>
						</c:forEach>						
					</div>
				</c:if>
			</div><%-- Ende Sidebar --%>
<jsp:include page="../footer.jsp" />