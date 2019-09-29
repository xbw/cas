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
					<%=session.getAttribute("userName")%>
					!
				</h2></td>
			<td><input type="button" value="注销"
				onclick="location.href='./logout'"></td>
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
