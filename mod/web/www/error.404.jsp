<%@ taglib prefix="j" uri="/jodd" %>
<%@ taglib prefix="joyfn" uri="/joddfn-joy" %>
${joyfn:initPage(pageContext)}
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<title>uphea | missing page</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<link href="${CTX}/reset.css" rel="stylesheet" type="text/css" />
	<link href="${CTX}/960_24_col.css" rel="stylesheet" type="text/css" />
	<link href="${CTX}/style.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div id="box" style="margin-top:90px;">
	<h1>Are you looking for something?</h1>

	<p style="font-size:1.5em; margin-top:50px; line-height:30px;">
		It looks like page <b>${requestScope['javax.servlet.error.request_uri']}</b> is missing.<br/>
		Maybe you can just go and <a href="${CTX}/">start all over again</a>.
	</p>

	<p>Or dial HTTP-404 for help;)</p>
</div>

</body>
</html>