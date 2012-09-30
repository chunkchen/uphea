<%@ taglib prefix="j" uri="/jodd" %>
<%@ taglib prefix="jfn" uri="/joddfn" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<html>
<head>
<title>uphea | Registration</title>
<script type="text/javascript" src="/jquery.form.js"></script>
<script type="text/javascript" src="/reform.js"></script>
<script type="text/javascript" src="/jquery.reUI.js"></script>
<script type="text/javascript">
var formSave;
$(function() {
	$("#save").reRadio();
	formSave = new ReForm('save', {
		ajaxPost: false,
		liveValidation: true,
		ajaxValidationOnSubmit: true
	});
});
</script>
</head>
<body>

<div id="page">

	<h1 style="text-align:center;">Register with Uphea to Vote smarter!</h1>

	<div style="margin: 50px 0 50px 100px;">
	<j:form>
		<form id="save" action="registration.save" method="post" autocomplete="off">
			<div class="form">
				<div class="frow">
					<label class="g5 required" for="save_user_email">E-mail:</label>
					<input type="text" name="user.email" id="save_user_email" class="g7" maxlength="100"/>
					<t:hint fieldId="save_user_email" widthPx="240">
						Your e-mail will be used only for<br/>
						your identification. Your e-mail<br/>
						address will NEVER be shared with<br/>
						third parties.
					</t:hint>
					<div class="error error_msg pre5" id="save_user_email_error"></div>
				</div>
				<div class="frow">
					<label class="g5 required" for="save_newPassword">Password:</label>
					<input type="password" name="newPassword" id="save_newPassword" class="g7" maxlength="25"/>
					<t:hint fieldId="save_newPassword" widthPx="240">
						Be wise: use <b>strong</b> passwords<br/>
						for maximum security!</t:hint>
					<div class="error error_msg pre5" id="save_newPassword_error"></div>
				</div>

				<div class="frow">
					<label class="g5" for="save_newPassword2">Password (again):</label>
					<input type="password" name="newPassword2" id="save_newPassword2" class="g7" maxlength="25"/>

					<div class="error error_msg pre5" id="save_newPassword2_error"></div>
				</div>


				<div style="border-top:1px dotted #666; margin:30px 80px 30px 60px; padding-top:10px; text-align:center;">
					While you are here, please enter some personal data:
				</div>

				<div class="frow">
					<label class="g5" for="save_user_name">Name:</label>
					<input type="text" name="user.name" id="save_user_name" class="g7" maxlength="100"/>
					<t:hint fieldId="save_user_name" widthPx="300">
						Enter your name to personalize experience:)<br/>
						Your name will appear on the site instead of the email.
					</t:hint>
					<div class="error error_msg pre5" id="save_user_name_error"></div>
				</div>

				<div class="frow">
					<label class="g5">Sex:</label>
					<div class="g7">
						<label class="g4" for="save_user_sex_m">male</label>
						<input type="radio" value="M" id="save_user_sex_m" name="user.sex"/>
						<label class="g4" for="save_user_sex_f">female</label>
						<input type="radio" value="F" id="save_user_sex_f" name="user.sex"/>
					</div>
				</div>


				<div class="frow">
					<label class="g5" for="save_user_birthYear">Birth year:</label>
					<input type="text" name="user.birthYear" id="save_user_birthYear" class="g2" maxlength="4"/>
					<t:hint fieldId="save_user_birthYear" widthPx="300">Your birth year is used <b>just</b> for statistics!</t:hint>
					<div class="error error_msg pre5" id="save_user_birthYear_error"></div>
				</div>
				
				<div class="frow">
					<label class="g5" for="save_user_country">Country:</label>
					<select name="user.countryId" id="save_user_country" class="g7">
						<option value="-1" style="font-style:italic;">(none)</option>
						<j:iter items="${countries}" var="c" status="s">
							<option value="${c.id}" class="row${s.modulus}">${c.name}</option>
						</j:iter>
					</select>
					<t:hint fieldId="save_user_country" widthPx="300">By choosing a country you will be able to<br/>answer on localized questions from your region!</t:hint>
				</div>

				<div style="margin-top:40px;">
					<input type="submit" class="submit g3 push7" value="Register"/>
				</div>
			</div>
		</form>
	</j:form>
	</div>
</div>

</body>
</html>