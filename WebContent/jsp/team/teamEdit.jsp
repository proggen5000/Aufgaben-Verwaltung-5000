<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Zugriff nicht �ber Servlet --%>
<c:if test="${!valid_request}">
	<c:redirect url="/error.jsp"><c:param name="error" value="Zugriff verweigert" /></c:redirect>
</c:if>

<c:if test="${param.mode == 'edit'}">
	<jsp:include page="../header.jsp"><jsp:param name="page_title" value="${team.name}" /></jsp:include>
</c:if>
<c:if test="${param.mode == 'new'}">
	<jsp:include page="../header.jsp"><jsp:param name="page_title" value="Team erstellen" /></jsp:include>
</c:if>

<jsp:include page="../menu.jsp"><jsp:param name="menu" value="teams" /></jsp:include>
			
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
			
			<%-- Team bearbeiten/erstellen --%>
			<c:if test="${param.mode == 'edit'}"><h1>Team bearbeiten</h1></c:if>
			<c:if test="${param.mode == 'new'}"><h1>Team erstellen</h1></c:if>
			
			<form class="form" action="/team" method="post">
		  		<div class="form-group col-xs row">
		  			<div class="col-md-6">
		  				<label for="name"><span class="glyphicon glyphicon-briefcase"></span> Teamname*</label>
						<input id="name" name="name" type="text" class="form-control input-lg" value="${team.name}" />
		  			</div>
		  			<div class="col-md-6">
		  				<label for="manager"><span class="glyphicon glyphicon-user"></span> Teammanager*</label>
						<c:if test="${param.mode == 'new'}">
							<p class="form-control-static">Sie sind vorerst der Teammanager.<br />Dies k&ouml;nnen Sie sp&auml;ter &auml;ndern.</p>
						</c:if>
						<c:if test="${param.mode == 'edit'}">
							<select name="manager" size="1" class="form-control input-lg">					
								<c:forEach var="user" items="${users}">
									<c:if test="${user.id == team.gruppenfuehrer.id}">
										<option value="${user.id}" selected>${user.username}</option>
									</c:if>
									<c:if test="${user.id != team.gruppenfuehrer.id}">
										<option value="${user.id}">${user.username}</option>
									</c:if>
								</c:forEach>
							</select>
						</c:if>
		  			</div>
				</div>
				<div class="form-group col-xs">
					<label for="description"><span class="glyphicon glyphicon-align-left"></span> Slogan / Beschreibung</label>
					<textarea id="description" name="description" class="form-control" rows="2">${team.beschreibung}</textarea>
				</div>
				<div class="form-group col-xs">
					<label for="members"><span class="glyphicon glyphicon-user"></span> Mitglieder</label> <span class="badge" data-toggle="tooltip" data-placement="right" data-original-title="Mehrere Mitglieder durck Gedr&uuml;ckthalten von STRG bzw. CMD markieren">?</span>
					<div class="row">
						<div class="col-xs-5">
							<select multiple id="leftValues" name="members" size="8" class="form-control">
								<c:forEach var="user" items="${users}">
									<option value="${user.id}">${user.username} (${user.vorname} ${user.nachname})</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="col-xs-1" style="vertical-align: center;">
							<input type="button" id="btnLeft" class="btn btn-default" value="&lt;&lt;" />
	       					<input type="button" id="btnRight" class="btn btn-default" value="&gt;&gt;" />
						</div>
						
						<div class="col-xs-5">
							<select multiple id="rightValues" size="8" class="form-control">
								<c:forEach var="userAll" items="${usersRest}">
									<option value="${userAll.id}">${userAll.username} (${userAll.vorname} ${userAll.nachname})</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<section class="container">
					    <div>
					        <select id="leftValues" size="5" multiple></select>
					    </div>
					    <div>
					        <input type="button" id="btnLeft" value="&lt;&lt;" />
					        <input type="button" id="btnRight" value="&gt;&gt;" />
					    </div>
					    <div>
					        <select id="rightValues" size="4" multiple>
					            <option>1</option>
					            <option>2</option>
					            <option>3</option>
					        </select>
					        <div>
					            <input type="text" id="txtRight" />
					        </div>
					    </div>
					</section>
					
				</div>
				
				<c:if test="${param.mode == 'new'}"><input type="hidden" name="mode" value="new" /></c:if>
				<c:if test="${param.mode == 'edit'}"><input type="hidden" name="mode" value="edit" /></c:if>
				
				<div class="form-group">
					<c:if test="${param.mode == 'new'}">
						<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Erstellen</button>
						<a href="/" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Abbrechen</a>
					</c:if>
					<c:if test="${param.mode == 'edit'}">
						<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Speichern</button>
						<a href="/team?mode=view&id=${team.id}" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span> Abbrechen</a>
						<a href="/team?mode=remove&id=${team.id}" class="btn btn-danger pull-right"><span class="glyphicon glyphicon-remove"></span> Team l&ouml;schen</a>
					</c:if>
				</div>
			</form>
			
			<jsp:include page="../sidebar.jsp" />
<jsp:include page="../footer.jsp" />
