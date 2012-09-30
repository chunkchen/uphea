<%@ taglib prefix="j" uri="/jodd" %>
<%@ taglib prefix="jfn" uri="/joddfn" %>
<%@ taglib prefix="appfn" uri="/appfn" %>
<script type="text/javascript">
var formChangePass;
$(function() {
	formChangePass = new ReForm('changePass', {
		liveValidation: false,
		ajaxPost: true,
		activateOnAjaxSubmitSuccess: false
	});
	formChangePass.opts.onAjaxSubmitSuccess = function() {
		dlg.dialog('close');
		$("#saveSuccess").show().delay(2500).fadeOut("fast", function() {
			pager_users.goto();
		});
	};
	dlg.dialog('option', 'title', 'User: ${user.email}');
});

</script>

<div style="text-align:left;">
	<j:form>
		<form id="changePass" action="changePassword.save" method="post" autocomplete="off">
			<div class="form">
				<input type="hidden" name="user.id">
				<div class="frow">
					<label class="g5" for="changePass_newPassword">New password:</label>
					<input type="password" name="newPassword" id="changePass_newPassword" class="g7" maxlength="25"/>
					<div class="error error_msg pre5" id="changePass_newPassword_error"></div>
				</div>

				<div class="frow">
					<label class="g5" for="changePass_newPassword2">New password (again):</label>
					<input type="password" name="newPassword2" id="changePass_newPassword2" class="g7" maxlength="25"/>
					<div class="error error_msg pre5" id="changePass_newPassword2_error"></div>
				</div>
				<div style="margin-top:40px;">
					<input type="submit" class="save submit g3 push5" value="Save">
				</div>
			</div>
		</form>
	</j:form>

</div>