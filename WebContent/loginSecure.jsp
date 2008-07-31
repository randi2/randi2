<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>


<html>
<head>
<title>Login</title>
</head>
<body>
<h1>Login</h1>
<form action="<c:url value='j_spring_security_check'/>" method="POST">
<table>
	<tr>
		<td>User:</td>
		<td><input type='text' name='j_username'></td>
	</tr>
	<tr>
		<td>Password:</td>
		<td><input type='password' name='j_password'></td>
	</tr>
	<tr>
		<td colspan='2'><input name="submit" type="submit"></td>
	</tr>
	<tr>
		<td colspan='2'><input name="reset" type="reset"></td>
	</tr>
</table>
</form>
</body>
</html>

