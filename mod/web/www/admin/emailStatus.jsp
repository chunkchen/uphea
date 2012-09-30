<%@ taglib prefix="j" uri="/jodd" %>
<html>
<body>

<div id="page">
<h1>Email status</h1>

<p>Sender thread is in: <b>${status}</b> mode.</p>

<p>
	Pending emails: <b>${pendingEmailsCount}</b>
</p>

<p>
	Error emails: <b>${allEmailsCount - pendingEmailsCount}</b>
</p>

<j:if test="${not empty exception}">
<h2>
	There was an exception
</h2>

<div class="msg msg_error msg_show">
	Last exception: ${exception}
</div>
</j:if>

</div>
</body>
</html>