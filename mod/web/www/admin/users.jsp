<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/pager.css"/>
<script type="text/javascript" src="/jquery.form.js"></script>	
<script type="text/javascript" src="/reform.js"></script>
<script type="text/javascript" src="/repager.js"></script>
<script type="text/javascript" src="/jquery.center.js"></script>
<script type="text/javascript" src="/jquery-ui/jquery-ui-1.8.1.custom.min.js"></script>
<%--
<script type="text/javascript" src="/jquery-ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="/jquery-ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="/jquery-ui/jquery.ui.position.js"></script>
<script type="text/javascript" src="/jquery-ui/jquery.ui.mouse.js"></script>
<script type="text/javascript" src="/jquery-ui/jquery.ui.draggable.js"></script>
<script type="text/javascript" src="/jquery-ui/jquery.ui.dialog.js"></script>
--%>
<script type="text/javascript" src="/jquery.reUI.js"></script>
<link rel="stylesheet" type="text/css" href="/jquery-ui/themes/smoothness/jquery-ui.css"/>

<style type="text/css">
img.uimg {margin-right:4px;}
span.level100 {font-weight: bold;}
</style>

<script type="text/javascript">
var dlg;
$(function() {
	dlg = $("#dialog");
	dlg.dialog({
		autoOpen: false,
		width: 600,
		modal: true
	});
	$("div.msg").center();
});
function edit(id) {
	dlg.load("users.edit.htm?user.id=" + id, function() {
		dlg.dialog('open');
	});
}
function changePass(id) {
	dlg.load("changePassword.edit.htm?user.id=" + id, function() {
		dlg.dialog('open');
	});
}
function deleteUser(id){
	var d = $('#delete').dialog({
		width: 400,
		title: 'Warning!',
		modal: true,
		close: function(ev, ui) {$(this).dialog('destroy'); },
		buttons: {
			"No": function() { $(this).dialog("close"); },
			"Yes": function() {
				$(this).dialog("close");
				pleaseWait();
				$.post("/admin/users.delete", { "user.id": id },
					  function(data) {
						  pager_users.goto();
					  });
			}
		} });
	d.dialog('open');
}
</script>
</head>
<body>

<h1 style="margin-top:50px;">Users administration</h1>

<div id="saveSuccess" class="msg msg_success">
	User is successfully saved.
</div>

<div style="text-align:right;margin:2px;"><a href="#" onclick="edit(${user.id}); return false;"><img src="/gfx/user_add.png" alt="add user" style="position:relative;top:4px;">new user</a></div>

<t:pager-head pagerId="users" pagerAction="users.page">
	<td width="240" class="sort sort_asc">E-mail</td>
	<td width="160">Country</td>
	<td width="80" align="center">Birth year</td>
	<td width="200" class="sort">Last login</td>
	<td width="150">Member since</td>
	<td width="60">&nbsp;</td>
</t:pager-head>
<%@include file="users.page.jsp"%>
<t:pager-foot/>

<div id="dialog" style="display:none; margin-top: 10px;">
</div>

<div id="delete" style="display:none">
	Do you really want to delete user?<br/>
</div>

</body>
</html>