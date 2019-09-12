<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<table>
		<tr>
			<td><h2>
					Hello
					<%=session.getAttribute("userName").toString()%>
					!
				</h2></td>
			<td><input type="button" value="注销"
				onclick="location.href='http://127.0.0.1:8080/cas-server/logout'"></td>
		</tr>
		<tr>
			<td>session id:</td>
			<td><%=session.getId()%></td>
		</tr>
		<tr>
			<td>getRealPath:</td>
			<td><%=request.getRealPath("/")%></td>
		</tr>
		<tr>
			<td>getRemoteAddr</td>
			<td><%=request.getRemoteAddr()%></td>
		</tr>
		<tr>
			<td>getContextPath</td>
			<td><%=request.getContextPath()%></td>
		</tr>
	</table>
</body>
</html>
