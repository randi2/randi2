<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:setProperty name="user" property="*" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Statistiker-Account</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>

<div id="content">
<h1>Statistiker-Account</h1>
<form>
<fieldset style="width:60%">
	<legend><b>Statistiker</b></legend>
		<table>
		<tr>
		<td>Soll ein Statistiker-Account für die Studie angelegt werden ?</td>
		</tr>
		<tr>
		<td><br><input type="button" name="entsp_ja" value=" Ja " tabindex="1" onclick="location.href=''">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" name="entsp_nein" value="Nein" tabindex="2" onclick="top.location.href='studie_anlegen.jsp'"></td>
		</tr>
		</table>

</fieldset><br>
</form>
	</div>
	<div id="show_none">
		
	</div>

<%@include file="include/inc_footer.jsp"%>
<div id="show_none">
<%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
