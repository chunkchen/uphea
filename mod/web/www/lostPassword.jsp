<%@ page import="jodd.util.RandomStringUtil" %>
<html>
<head>
	<title>uphea | Lost password?</title>
</head>
<body>

	<div id="page">

	<h1>Lost your password?</h1>

	<p>To reset your password, type the full email address you use to sign in.</p>

	<div style="margin: 20px 0;">
	<j:form>
	<form action="/lostPassword" id="lost" method="post" autocomplete="off">

		<div class="frow">
			<label for="email" class="g4">Email:</label><input type="text" name="email" id="email" class="g6" maxlength="50"/>
		</div>

		<div class="frow">
			<label for="captcha" class="g4">Text verification:</label>
			<img src="/stickyImg?t=<%= System.currentTimeMillis() %>" class="g6"/><br/>
		</div>
		<div class="frow">
			<input type="text" name="captchaAnswer" id="captcha" class="g4 push4" maxlength="20"/>
		</div>


		<div class="frow" style="margin:40px 0 0 0">
			<div class="g10 push4">
				<input type="submit" class="g4 submit" value="Reset password"/>

			</div>
		</div>

	</form>
	</j:form>

	</div>
	</div>

</body>
</html>