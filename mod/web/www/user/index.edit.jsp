<%@ taglib prefix="j" uri="/jodd" %>
<%@ taglib prefix="appfn" uri="/appfn" %>
<script type="text/javascript">
var formSave;
$(function() {
	$("#save").reRadio();
	formSave = new ReForm('save', {
		liveValidation: true,
		ajaxPost: true,
		activateOnAjaxSubmitSuccess: false
	});
	formSave.opts.onAjaxSubmitSuccess = function() {
		dlg.dialog('close');
		$("#saveSuccess").show().delay(2500).fadeOut("fast", function() {
			reload();
		});
	};
	dlg.dialog('option', 'title',  'User: ${user.screenName}');
});

</script>


<div style="text-align:left;">
	<j:form>
		<form id="save" action="index.save" method="post" autocomplete="off">
			<div class="form">
				<input type="hidden" name="user.id">

				<div class="frow">
					<label class="g5" for="save_user_email">E-mail:</label>
					<input type="text" name="user.email" id="save_user_email" class="g7" maxlength="100" readonly="readonly"/>				
				</div>

				<div class="frow">
					<label class="g5" for="save_user_name">Name:</label>
					<input type="text" name="user.name" id="save_user_name" class="g7" maxlength="100"/>
					<div class="error error_msg pre5" id="save_user_name_error"></div>
				</div>

				<div class="frow">
					<label class="g5" for="save_user_country">Country:</label>
					<select name="user.countryId" id="save_user_country" class="g7">
						<option value="">(none)</option>
						<j:iter items="${countries}" var="c">
							<option value="${c.id}">${c.name}</option>
						</j:iter>
					</select>
				</div>
				
				<div class="frow">
					<label class="g5" for="save_user_birthYear">Birth year:</label>
					<input type="text" name="user.birthYear" id="save_user_birthYear" class="g2" maxlength="4"/>
					<div class="error error_msg pre5" id="save_user_birthYear_error"></div>
				</div>

				<div class="frow">
					<label class="g5">Sex:</label>
					<div class="g7">
						<label class="g4" for="save_user_sex_m">male</label>
						<input type="radio" value="M" id="save_user_sex_m" name="user.sex" checked="checked"/>
						<label class="g4" for="save_user_sex_f">female</label>
						<input type="radio" value="F" id="save_user_sex_f" name="user.sex"/>
					</div>
				</div>

				<j:if test="${empty user.id}">
					<div class="frow">
						<label class="g5" for="save_newPassword">Password:</label>
						<input type="password" name="newPassword" id="save_newPassword" class="g7" maxlength="25"/>
						<div class="error error_msg pre5" id="save_newPassword_error"></div>
					</div>

					<div class="frow">
						<label class="g5" for="save_newPassword2">Password(again):</label>
						<input type="password" name="newPassword2" id="save_newPassword2" class="g7" maxlength="25"/>
						<div class="error error_msg pre5" id="save_newPassword2_error"></div>
					</div>
				</j:if>
				<div style="margin-top:40px;">
					<input type="submit" class="save submit g3 push5" value="Save">
				</div>
			</div>
		</form>
	</j:form>

</div>