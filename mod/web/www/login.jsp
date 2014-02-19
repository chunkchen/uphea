<%@ taglib prefix="j" uri="/jodd" %>
<%@ taglib prefix="joy" uri="/jodd-joy" %>
<html>
<head>
	<title>uphea | Sign In</title>
<script type="text/javascript">
$(function() {
	<j:if test="${not empty param.err}">$('#login_username_error').show();</j:if>
});
</script>
</head>
<body>
<div id="page">

	<h1 style="text-align:center;">Welcome to Uphea!</h1>

	<joy:auth auth="false">
	<p style="text-align:center;">
		<span style="font-size:1.2em;">Sign in for maximum experience!</span><br/>
		<b class="c-ora">Uphea</b> users may answer on past questions,<br/>bookmark favorite questions and much, much more!
	</p>

	<div id="login_username_error" class="msg msg_error msg_stat" style="margin-top:30px;">Sign in error, please try again...</div>

	<div style="margin: 50px 0 50px 200px;">
		<j:form>
		<form action="${CTX}/j_login" id="login" method="post" autocomplete="off">

			<div class="frow">
				<label for="login_j_username" class="g3">Email:</label><input type="text" name="j_username" id="login_j_username" class="g6" maxlength="50"/>
			</div>

			<div class="frow">
				<label for="login_j_password" class="g3">Password:</label><input type="password" name="j_password" id="login_j_password" class="g6" maxlength="25"/>
			</div>

			<input type="hidden" name="j_path"/>

			<j:csrfToken name="j_token"/>

			<div style="border: 2px solid #F29720; width: 260px; color: #00529B; background-color: #fcefa1; font-family: Tahoma, sans-serif; font-size: 14px; font-weight: bold; padding: 6px; text-align: center; margin-left: 110px; margin-top: 10px;">
				Use the following credentials:<br>admin/admin!
			</div>


			<div class="frow" style="margin:40px 0 0 0">
				<div class="g10 push4">
					<input type="submit" class="g3 submit" value="Sign in"/>

				</div>
			</div>

			<div class="from" style="margin-top:20px;">
				<div class="g10 push4">
					<img src="gfx/user_register.png" style="position:relative;top:2px; margin:0 4px 0 5px;"/><a href="registration.html"><b>register</b></a>
					<img src="gfx/lost.png" style="position:relative;top:2px; margin: 0 4px 0 20px;"/><a href="lostPassword.html"><b>lost password</b></a>
				</div>
			</div>

		</form>
		</j:form>
	</div>
	</joy:auth>

	<joy:auth auth="true">
		<div style="text-align:center; margin-top:60px">You are already logged in!<br/>
			Why don't you check some <a href="index.html">questions</a> and give your opinion?</div>
	</joy:auth>
</div>

</body>
</html>