<%@ page import="jodd.madvoc.component.MadvocConfig" %>
<%@taglib prefix="j" uri="/jodd" %>
<%@taglib prefix="appfn" uri="/appfn" %>
<html>
<head>
<style type="text/css">
	h2 {margin-top:20px;}
	table.adminTable   		{ border: 1px solid gray; font-size:10px; margin: 2px; line-height: 14px;}
	table.adminTable td     { border-top: 1px solid gray; padding: 2px 10px; border-right: 1px dotted #ddd;}
	table.adminTable td a	{ text-decoration: none;}
	table.adminTable th     { font-weight: bold; color: #eee; background-color:#333; font-size:1.1em; padding: 4px;}
</style>

</head>
<body>

<h1 style="margin-top:50px;">Madvoc</h1>

<%
	MadvocConfig madvocConfig = (MadvocConfig) request.getAttribute("madvocConfig");
%>
<pre>
<%= madvocConfig.toString() %>
</pre>

<h2>Actions</h2>

Total actions: ${appfn:length(actions)}.
<table border="0" style="border: 1px solid #666;" class="adminTable">
	<tr>
		<th>Action path</th>
		<th>method</th>
		<th>alias</th>
		<th>Action</th>
		<th>Interceptors</th>
	</tr>
	<j:iter items="${actions}" var="cfg">
		<tr>
			<td><a href="${cfg.actionPath}">${cfg.actionPath}</a></td>
			<td>${cfg.actionMethod}</td>
			<td>${appfn:replace(aliases[cfg.actionPath], "com.uphea.action.", ".")}</td>
			<td>${appfn:replace(cfg.actionString, "com.uphea.action.", ".")}</td>
			<td><j:iter items="${cfg.interceptors}" var="iter">
					${iter.getClass().simpleName}<br>
				</j:iter>
			</td>
		</tr>
	</j:iter>

</table>

<h2>Results</h2>

Total results: ${appfn:length(results)}.
<table border="0" class="adminTable">
	<tr>
		<th>Type</th>
		<th>Result</th>
	</tr>
	<j:iter items="${results}" var="result">
		<tr>
			<td style="text-align:right;font-weight:bold">${result}</td>
			<td>${result}</td>
		</tr>
	</j:iter>
</table>


<h2>Interceptors</h2>

Total interceptors: ${appfn:length(interceptors)}.
<table border="0" class="adminTable">
	<tr>
		<th>Interceptor</th>
	</tr>
	<j:iter items="${interceptors}" var="interceptor">
		<tr>
			<td>${interceptor}</td>
		</tr>
	</j:iter>
</table>


<h2>Filters</h2>

Total filters: ${appfn:length(filters)}.
<table border="0" class="adminTable">
	<tr>
		<th>Filters</th>
	</tr>
	<j:iter items="${filters}" var="filter">
		<tr>
			<td>${filter}</td>
		</tr>
	</j:iter>
</table>


</body>
</html>