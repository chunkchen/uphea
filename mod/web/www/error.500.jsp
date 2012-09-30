<%@ taglib prefix="j" uri="/jodd" %>
<%@ taglib prefix="joyfn" uri="/joddfn-joy" %>
${joyfn:initPage(pageContext)}
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<title>uphea | misbehave</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<link href="${CTX}/reset.css" rel="stylesheet" type="text/css" />
	<link href="${CTX}/960_24_col.css" rel="stylesheet" type="text/css" />
	<link href="${CTX}/style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.console {font: 12px "Courier New", Courier, sans-serif; font-weight: bold; margin: 10px 5px 0 5px; border-top: 1px dotted gray; padding-top:10px;}
</style>
</head>
<body>

<div id="box" style="margin-top:90px;">
	<h1>Don't you love crashes?</h1>

	<p style="font-size:1.5em; margin-top:50px; line-height:30px;">
		Ooops. Something went wrong. Its not your fault!<br/> 
		Try starting <a href="${CTX}/">all over again</a> or going to <a href="javascript:history.go(-1)">previous</a> page.<br/>
		<j:if test="${not empty errorNumber}">If somebody asks, error number is: #${errorNumber}<br/></j:if>
	</p>

	<p>Or just scream 'HTTP-500' for help;)</p>
</div>

</body>
</html>