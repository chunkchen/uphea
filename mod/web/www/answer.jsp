<%@ taglib prefix="j" uri="/jodd" %>
<%@ taglib prefix="jfn" uri="/joddfn" %>
<%@ taglib prefix="appfn" uri="/appfn" %>
<ul>
<j:iter items="${question.answers}" var="answer" status="s">
	<li>
		<div class="ans round8">
			<div>${appfn:txt2html(answer.text)}</div>
			<div class="cls">
				<div class="graph"><div style="width: ${appfn:fixedFloat(answer.votesPercent)}%;"></div></div>
				<div class="percent">${appfn:fixedFloat(answer.votesPercent)}%</div>
			</div>
		</div>
	</li>
</j:iter>
</ul>
<%@include file="/jspf/total.jspf"%>