<%@ taglib prefix="j" uri="/jodd" %>
<%@ taglib prefix="joy" uri="/jodd-joy" %>
<%@ taglib prefix="appfn" uri="/appfn" %>

<html>
<head>
	<title>uphea | reset password</title>
</head>
<body>

<div id="page">
<j:ifelse test="${user != null}">
<j:then>
	<h1>New password</h1>
<j:if test="${appfn:length(violations) > 0}">
	<div class="msg msg_error msg_show" style="width:600px">
		<joy:violation-msg violation="${violations[0]}"/>
	</div>
</j:if>

<div style="text-align:left;">
	<j:form>
		<form id="changePass" action="recover.changePass" method="post" autocomplete="off">
			<div class="form">
				<input type="hidden" name="n" value="${token}">
				<div class="frow">
					<label class="g5" for="changePass_newPassword">New password:</label>
					<input type="password" name="newPassword" id="changePass_newPassword" class="g7" maxlength="25"/>
				</div>

				<div class="frow">
					<label class="g5" for="changePass_newPassword2">New password (again):</label>
					<input type="password" name="newPassword2" id="changePass_newPassword2" class="g7" maxlength="25"/>
				</div>
				<div style="margin-top:40px;">
					<input type="submit" class="save submit g3 push5" value="Save">
				</div>
			</div>
		</form>
	</j:form>
</div>
</j:then>
<j:else>
	Nothing here.
</j:else>
</j:ifelse>

</div>
</body>
</html>