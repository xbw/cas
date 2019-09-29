<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

</head>
<body>

	<h2>登录</h2>

	<form action="login" method="post">

		<table>
			<tr>
				<td>用户名:</td>
				<td><input type="text" name="userName" value="admin"></td>
			</tr>
			<tr>
				<td>密码:</td>
				<td><input type="password" name="password" value="admin"></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="登录" /></td>
			</tr>

		</table>

	</form>
</body>
</html>
