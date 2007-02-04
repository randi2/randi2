<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>RANDI 2</title>
<link rel="stylesheet" type="text/css" href="css/style_login.css" />
</head>

<body>
	<div id="header">
		<img src="images/dkfz_logo.gif" width="337"
		height="63" title="" alt="">
	</div>
	
	<div id="breadcrumb">
	<table class="breadcrumb_tbl" width="100%" height="25" summary="Impressum">
		<tr>
			<td align="right" valign="middle" height="25"><a href="impressum.jsp" id="logout_link">Impressum</a></td>
		</tr>
	</table>
	</div>
	
	<div id="inhalt_login">
		<p>
			<img src="images/heidelberg.jpg" width="537" height="291"
		alt="Heidelberg">
		</p>
	</div>
	
	<div id="login_benutzer">
		<p id="pageheader">Herzlich Willkommen</p>
		<%@include file="include/inc_nachricht.jsp"%>
		<p>Benutzername</p>
		<form action="DispatcherServlet" method="POST">
		<input type="hidden" value="JSP_INDEX_LOGIN" name="anfrage_id">
			<p>
				<input type="text" name="username" tabindex="1">
			</p>
			<p>Kennwort</p>
			<p>
				<input type="password" name="password" tabindex="2">
			</p>
			<p>
				<input name="Submit" type="submit" tabindex="3" onClick=""
				value="Login">
			</p>
		</form>
		<br>
		<table cellPadding="0" cellSpacing="0" border="0">
		<tr>
		<td align="right">
		<form action="DispatcherServlet" method="POST"><input type="hidden" name="anfrage_id" value="JSP_INDEX_BENUTZER_REGISTRIEREN_EINS"><input type="submit" value="Benutzer registrieren"></form>
		</td>
		<td>
		&nbsp;&nbsp;&nbsp;::&nbsp;&nbsp;&nbsp;
		</td>
		<td align="left">
		<form action="passwort_vergessen.jsp" method="POST"><input type="submit" value="Passwort vergessen?"></form>
		</td>
		</tr>
		</table>
	</div>
</html>
