<%@ taglib prefix="j" uri="/jodd" %>
<%@ taglib prefix="appfn" uri="/appfn" %>
<html>
<body>

<h1 style="text-align:center;">Question preview!</h1>

<div id="box">
	<div id="date">
		<div id="left">&nbsp;<j:if test="${not empty prevQuestion}"><a href="preview.html?date=${prevQuestion.date}"><img src="/gfx/left.png" alt="left" title="previous question"/></a></j:if></div>
		Question for <span>${appfn:fmtIntDate(question.date)}</span>
		<div id="right"><j:if test="${not empty nextQuestion}"><a href="preview.html?date=${nextQuestion.date}"><img src="/gfx/right.png" alt="right" title="next question"/></a></j:if>&nbsp;</div>
	</div>

	<div id="question">
		<j:set name="len" scope="page" value="${appfn:length(question_text)}"/>
		<j:set name="size" scope="page" value="1"/>
		<j:if test="${len > 100}">
			<j:set name="size" scope="page" value="0.9"/>
		</j:if>
		<span style="font-size:${size}em">
		${appfn:question2html(question)}
		</span>
	</div>
	<div id="answers">
		<%@include file="/answer.jsp"%>
	</div>
</div>

</body>
</html>