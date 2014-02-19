<%@ taglib prefix="j" uri="/jodd" %>
<%@ taglib prefix="appfn" uri="/appfn" %>
<script type="text/javascript">
var csrf = '${appfn:prepareCsrfToken(pageContext)}';
var userVoteId = '${userVoteId}';
</script>
<ul>
<j:iter items="${question.answers}" var="answer" status="s">
	<j:set name="isUserAnswer" value="${(not empty userAnswer) and (answer.id eq userAnswer.id)}"/>
	<li>
		<j:if test="${isUserAnswer}">
			<div id="thank"><h2>Thank you for sharing opinion with us!</h2></div>
		</j:if>
		<a href="#" onclick="<j:if test="${not isUserAnswer}">vote(${answer.id});</j:if>return false;" class="answered-${answer.id eq userAnswer.id} round8">
		<div>${appfn:txt2html(answer.text)}</div>
		<div class="cls">
			<div class="graph"><div style="width: ${appfn:fixedFloat(answer.votesPercent)}%;"></div></div>
			<div class="percent">${appfn:fixedFloat(answer.votesPercent)}%</div>
		</div>
	</a></li>
</j:iter>
</ul>
<%@include file="/jspf/total.jspf"%>