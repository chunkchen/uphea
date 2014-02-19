<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="appfn" uri="/appfn" %>
<%@ taglib prefix="j" uri="/jodd" %>

<t:pager-body cols="7" itemName="user">
	<td>
		<div style="width:240px; height:19px; overflow:hidden;">
		<div style="width:400px">
			<img src="/gfx/user${user.sex}.png" alt="user" class="uimg"/><span class="level${user.level.value}">${user.email}</span>
			<j:if test="${not empty user.name}">(${user.name})</j:if>
		</div>
		</div>
	</td>
	<td>${user.country.name}</td>
	<td align="center">${user.birthYear}</td>
	<td>
		${user.lastLogin}
	</td>
	<td>
		${appfn:fmtDate(user.since)}
	</td>
	<td style="text-align: center;">
		<a href="#" onclick="edit(${user.id}); return false;" title="Edit user"><img src="/gfx/edit.png" alt="edit"/></a>
		<a href="#" onclick="changePass(${user.id}); return false;" title="Change password"><img src="/gfx/key.png" alt="key"/></a>
		<a href="#" onclick="deleteUser(${user.id}); return false;" title="Delete user"><img src="/gfx/delete.png" alt="delete"/></a>
	</td>
</t:pager-body>