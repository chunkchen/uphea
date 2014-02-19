<%@ taglib prefix="j" uri="/jodd" %>
<%@ taglib prefix="joy" uri="/jodd-joy" %>
<%@ taglib prefix="appfn" uri="/appfn" %>
<html>
<head>
<j:set name="question_text" value="${appfn:txtPlain(question.text)}"/>
<title>uphea | ${question_text}</title>
<script type="text/javascript">
/**
 * Votes for some answer.
 */
function vote(answerId) {
	$.ajax({
		type: 'POST',
		url: '${CTX}/answer',
		data: 'answer.id=' + answerId + '&userVote.id=' + userVoteId + '&_csrf_token=' + csrf,
		beforeSend: function() {
			pleaseWait(true);
		},
		success: function(msg) {
			pleaseWait(false);
			$('#answers').html(msg);
			var t = $('#thank');
			t.show();
			setTimer(3000, function() {
				t.slideToggle();
			});

		}
	});
}
</script>
</head>
<body>

<%@include file="/jspf/favorite.jspf"%>

<div id="box">
	<div id="date">
		<div id="left">&nbsp;<j:if test="${not empty prevQuestion}"><a href="${appfn:urlQuestion(prevQuestion)}"><img src="${CTX}/gfx/left.png" alt="left" title="previous question"/></a></j:if></div>
		Question for <span>${appfn:fmtDate(questionDate)}</span>
		<div id="right"><j:if test="${not isActiveQuestion}"><a href="${appfn:urlQuestion(nextQuestion)}"><img src="${CTX}/gfx/right.png" alt="right" title="next question"/></a></j:if>&nbsp;</div>
	</div>
	<j:if test="${not isActiveQuestion}">
		<div id="jump"><a href="${CTX}/index.html">(jump back to today's question)</a></div>
	</j:if>

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

	<j:ifelse test="${registeredOnlyWarn}">
		<j:then>
			<div id="answers">
				<%@include file="answer.jsp"%>
			</div>
			<div id="regwarn">
			<j:ifelse test="${isActiveQuestion}">
				<j:then>
					This question can be answered<br/>only by registered users.
					<br/><br/>
					Please <a href="${CTX}/login.html">sign in</a> or <a href="${CTX}/registration.html">register</a> to proceed.
				</j:then>
				<j:else>
					Sorry, this question is from the past and may be<br/>answered only by registered users.
					<br/>
					Please <a href="${CTX}/login.html">sign in</a> or <a href="${CTX}/registration.html">register</a> to proceed,
					or <a href="${CTX}/index.html">jump to today's</a> question.
				</j:else>
			</j:ifelse>
			</div>
		</j:then>
		<j:else>
			<div id="answers">
				<%@include file="answer.ok.jsp"%>
			</div>
		</j:else>
	</j:ifelse>

</div>


<div id="previews" class="cls">

	<div id="prev">
	&nbsp;
	<j:if test="${not empty prevQuestion}">
		<span class="small">previous question:<br/></span><a href="${appfn:urlQuestion(prevQuestion)}">${appfn:txtPlain(prevQuestion.text)}</a><br/>
		<span class="small">posted on</span> ${appfn:fmtIntDate(prevQuestion.date)}
	</j:if>
	</div>


	<div id="random">
	&nbsp;
	<j:if test="${not empty randomQuestion}">
		<span class="small">random question from the past:<br/></span><a href="${appfn:urlQuestion(randomQuestion)}">${appfn:txtPlain(randomQuestion.text)}</a><br/>
		<span class="small">posted on</span> ${appfn:fmtIntDate(randomQuestion.date)}
	</j:if>
	</div>

	<div id="next">
	&nbsp;
	<j:if test="${not empty nextQuestion}">
		<span class="small">following question:<br/></span><b>${appfn:txtPlain(nextQuestion.text)}</b><br/>
		<span class="small">scheduled on</span> ${appfn:fmtDate(nextQuestionDate)}&nbsp;<span class="small"><br/>(<b style="font-size:1.3em;">${daysToNextQuestion}</b> days remaining)</span>
	</j:if>
	</div>

</div>

</body>
</html>