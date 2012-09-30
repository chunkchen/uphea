<%@ page import="jodd.bean.BeanTool" %>
<%@ page import="jodd.madvoc.component.MadvocConfig" %>
<%@taglib prefix="j" uri="/jodd" %>
<%@taglib prefix="jfn" uri="/joddfn" %>
<html>
<head>
<style type="text/css">
	h2 {margin-top:20px;}
	table.adminTable   		{ border: 1px solid gray; font-size:0.7em; margin: 2px;}
	table.adminTable td     { border-top: 1px solid gray; padding: 2px 10px;}
	table.adminTable td a	{ text-decoration: none;}
	table.adminTable th     { font-weight: bold; color: #eee; background-color:#333; font-size:1.1em; padding: 4px;}
</style>

</head>
<body>

<h1 style="margin-top:50px;">Madvoc</h1>

<%
	MadvocConfig madvocConfig = (MadvocConfig) request.getAttribute("madvocConfig");
	String cfg = BeanTool.attributesToString(madvocConfig);
%>
<pre>
<%= cfg %>
</pre>

<h2>Actions</h2>

Total actions: ${jfn:length(actions)}.
<table border="0" style="border: 1px solid #666;" class="adminTable">
	<tr>
		<th>Init?</th>
		<th>Action path</th>
		<th>method</th>
		<th>Action</th>
		<th>Interceptors</th>
	</tr>
	<j:iter items="${actions}" var="cfg">
		<tr>
			<td>${jfn:test(cfg.initialized, "yes", "&nbsp;")}</td>
			<td><a href="${cfg.actionPath}">${cfg.actionPath}</a></td>
			<td>${cfg.actionMethod}</td>
			<td>${jfn:replace(cfg.actionString, "com.uphea.action.", ".")}</td>
			<td><j:iter items="${cfg.interceptorClasses}" var="iter">
					${iter.simpleName}
				</j:iter>
			</td>
		</tr>
	</j:iter>

</table>

<h2>Results</h2>

Total results: ${jfn:length(results)}.
<table border="0" class="adminTable">
	<tr>
		<th>Type</th>
		<th>Result</th>
	</tr>
	<j:iter items="${results}" var="result">
		<tr>
			<td style="text-align:right;font-weight:bold">${result.type}</td>
			<td>${result}</td>
		</tr>
	</j:iter>
</table>


<h2>Interceptors</h2>

Total interceptors: ${jfn:length(interceptors)}.
<table border="0" class="adminTable">
	<tr>
		<th>Init?</th>
		<th>Interceptor</th>
	</tr>
	<j:iter items="${interceptors}" var="interceptor">
		<tr>
			<td>${jfn:test(interceptor.initialized, "yes", "&nbsp;")}</td>
			<td>${interceptor}</td>
		</tr>
	</j:iter>

</table>


</body>
</html>