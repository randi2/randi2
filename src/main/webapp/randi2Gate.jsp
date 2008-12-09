<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ page
	import="org.springframework.security.ui.AbstractProcessingFilter"%>
<%@ page
	import="org.springframework.security.ui.webapp.AuthenticationProcessingFilter"%>
<%@ page import="org.springframework.security.AuthenticationException"%>
<html>
<head>
<title>Login</title>
<style type="text/css">
body {
	font-family: "Lucida Grande", "Lucida Sans Unicode", Verdana, Arial,
		Helvetica, sans-serif;
	font-size: 11px;
}

#main {
	width: 600px;
	margin: 0 auto;
}

/* ---------------------- */ 
/* NAV BAR 				  */
#navbar {
	background: url(img/bg.png) repeat-x;
	height: 29px;
	line-height: 29px;
}

#navbar ul,#navbar li,#navbar form,#navbar button {
	border: 0;
	margin: 0;
	padding: 0;
	list-style: none;
}

#navbar li a {
	margin: 0 6px;
	text-decoration: none;
	color: #000000;
	font-weight: bold;
	border-bottom: dotted 1px #000000;
}

#login_menu {
	background: #6699CC;
	border: solid 1px #666666;
	width: 340px;
	padding: 10px;
	color: #FFFFFF;
	position: absolute;
	font-weight: bold;
	font-size: 12px;
	line-height: 18px;
}

#login_menu li {
	padding-bottom: 6px;
	text-align: right;
}

#new-user-col {
	padding-right: 10px;
	border-right: 1px #DEDEDE solid;
	height: 149px;
	width: 100px;
	float: left;
	line-height: 12px;
}

#signup-user-col {
	padding-left: 20px;
	height: 149px;
	width: 200px;
	float: left;
	line-height: 12px;
	text-align: right;
}

#login_menu label {
	font-size: 11px;
	font-weight: normal;
}

#login_menu input {
	font-size: 11px;
	color: #333333;
	margin-left: 10px;
}

#login_menu button {
	line-height: 24px;
	float: right;
	color: #FFFFFF;
	font-size: 11px;
	font-weight: bold;
	text-align: center;
	cursor: pointer;
}

.green-button {
	background: url(img/button2.png);
	display: block;
	color: #FFFFFF;
	font-size: 11px;
	text-decoration: none;
	width: 81px;
	height: 26px;
	line-height: 24px;
	text-align: center;
}

.spacer {
	clear: both;
	height: 1px;
}
</style>
</head>
<body>
<div id="main" style="top: 10%; position: absolute; left: 30%;">
<div id="login_menu" style="display: block;">
<div id="new-user-col">New User:<br />
<br />
<a href="register.jspx" class="green-button">Register</a> <a
	href="terms.jspx" class="green-button">Terms of use</a></div>

<div id="signup-user-col">Existing User log in below:<br />
<br />
<form action="<c:url value='j_spring_security_check'/>" method="POST">
<ul>
	<li><label for="email">Username:</label> <input type='text'
		name='j_username' size="18"></li>
	<li><label for="psw">Password:</label> <input type='password'
		name='j_password' size="18"></li>
	<button type="submit" class="green-button">Log-in</button>
</ul>
</form>
</div>
<%
	if (request.getParameter("login_error") != null) {
%>
<div id="loginError" style="position: absolute; left: 30%;"><font
	color="red"> Your login attempt was not successful, try again. <BR />
Reason: <%=((AuthenticationException) session
										.getAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY))
										.getMessage()%> </font></div>
<%
	}
%>
<div class="spacer"></div>
</div>
</div>
</body>
</html>