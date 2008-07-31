<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<html>
<head>
<title>Login</title>
</head>
<body>
<div id="login"
	style="top: 10%; position: absolute; left: 30%; width: 505px; height: 250px; background-image: url('img/background1.gif');">
<form action="<c:url value='j_spring_security_check'/>" method="POST">
<br />
<br />
<br />
<br />
<br />
<br />
<table style="position: relative; left: 35%;" cellpadding="3">
	<tr>
		<td><span style="font-weight: bold; color: #FFFFFF;">Username:</span></td>
		<td><input type='text' name='j_username'></td>
	</tr>
	<tr>
		<td><span style="font-weight: bold; color: #FFFFFF;">Password:</span></td>
		<td><input type='password' name='j_password'></td>
	</tr>
	<tr>
		<td colspan='2'><input value="Log in" type="submit"
			style="position: relative; left: 180px;"></td>
	</tr>
	<tr>
	</tr>
	<tfoot>
		<tr>
			<td><a href="register.jspx"
				style="font-weight: bold; color: #FFFFFF; ">Register</a></td>
			<td><a href="terms.jspx"
				style="font-weight: bold; color: #FFFFFF;">Terms
			of use</a></td>
		</tr>
	</tfoot>
</table>
</form>

</div>
</body>
</html>

