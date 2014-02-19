<%@ taglib prefix="appfn" uri="/appfn" %>
<%@ taglib prefix="j" uri="/jodd" %>

<html>
<head>
	<title>uphea | User profile</title>
	<link rel="stylesheet" type="text/css" href="/pager.css"/>
	<script type="text/javascript" src="/jquery.form.js"></script>
	<script type="text/javascript" src="/reform.js"></script>
	<script type="text/javascript" src="/jquery.center.js"></script>
	<script type="text/javascript" src="/jquery-ui/jquery-ui-1.8.1.custom.min.js"></script>
	<script type="text/javascript" src="/jquery.reUI.js"></script>
	<link rel="stylesheet" type="text/css" href="/jquery-ui/themes/smoothness/jquery-ui.css"/>
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
	dlg.load("index.edit.htm", function() {
		dlg.dialog('open');
	});
}

function changePass(id) {
	dlg.load("changePassword.edit.htm", function() {
		dlg.dialog('open');
	});
}
</script>

</head>
<body>
<div id="saveSuccess" class="msg msg_success">
	User is successfully saved.
</div>

<div id="page">
	<h1>Your profile</h1>
	<div id="profile">
	<p>
		You are answering questions since <b>${appfn:fmtDate(user.since)}</b> and that is so great!
		Your emails are coming to <b>${user.email}</b> account. This visit started at <b>${appfn:fmtTime(userSession.sessionStart, "DD.MML hh:mm")}</b>.<br/>
	</p>

	<p>
	<j:switch value="${votesCount}">
		<j:case value="0">Oh, you haven't answer on any question yet! Why?</j:case>
		<j:case value="1"><b>One</b> question is done, more to go. Don't stop!</j:case>
		<j:case value="2"><b>Two</b> questions are answered by you!</j:case>
		<j:default>Believe it or not, you have answered <b>${votesCount}</b> questions! Keep answering!</j:default>
	</j:switch>
	</p>

	<p>
		<j:if test="${not empty user.name}">Your friends like to call you <b>${user.name}</b>.&nbsp;</j:if>
		<j:if test="${not empty user.country}">Your are living happily in <b>${user.country.name}</b>.&nbsp;</j:if>
		<j:if test="${not empty user.birthYear}">And since year <b>${user.birthYear}</b> you haven't found a better site for questions.&nbsp;</j:if>

		<j:if test="${appfn:contains(user.sex, 'M')}">Your liking of <b class="c-ora">uphea</b> means that you are dominant and clever <b>male</b>.</j:if>
		<j:if test="${appfn:contains(user.sex, 'F')}">Your fancying <b class="c-ora">uphea</b> means that you are smart and beautiful <b>female</b>.</j:if>
	</p>
	
	<a href="#" onclick="edit(${user.id}); return false;" title="Edit user"><img src="/gfx/edit.png" alt="edit"/> edit personal data</a>
	    &nbsp;
		<a href="#" onclick="changePass(${user.id}); return false;" title="Change password"><img src="/gfx/key.png" alt="key"/> change your password</a>
	</div>

	<div style="margin-top:60px;">
		<h2>Favorite questions</h2>
		<j:if test="${appfn:length(favorites) == 0}">You don't have favorite questions... go and get some!</j:if>
		<j:iter items="${favorites}" var="fav">
			<a href="${appfn:urlQuestion(fav)}">${appfn:txtPlain(fav.text)}</a> posted on ${appfn:fmtIntDate(fav.date)}<br/>			
		</j:iter>
	</div>
	<br/>

</div>


<div id="dialog" style="display:none; margin-top: 10px;"></div>

</body>
</html>