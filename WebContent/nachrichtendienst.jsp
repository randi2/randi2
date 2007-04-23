<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import= "de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import= "java.util.GregorianCalendar"
	import= "java.text.SimpleDateFormat" 
	import= "java.util.Locale"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Nachrichtendienst</title>
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Nachrichtendienst</h1>
<form>
<fieldset>
	<legend><b>Nachricht</b></legend>
		<table>
		<tr>
		<td>Geben Sie hier Ihre Nachricht ein</td> 
		</tr>
		<tr>
		<td><textarea name="Area" rows="7" cols="50"></textarea><br></td>
		</tr>
		</table>
</fieldset><br>
<fieldset>
	<legend><b>Empfänger</b></legend>
		<table>
		<tr>
		<td>Bitte w&auml;hlen Sie aus der Empf&auml;ngerliste</td>
		</tr>
		<tr>
		<td><select name="Standardauswahl" tabindex="1">
			<option value="1">Studienleiter</option>
			<option value="2">Admin</option>
			<option value="3">SysOp</option>
			<option value="4">Alle Studienleiter</option>
			<option value="5">Alle Testzentren</option>
			<option value="6">Alle Admins</option>
			</select> <br>
		</td>
		</tr>
		</table>
</fieldset>
</form>
		<table align="left"> 
		<tr>
		<td><input type="button" name="bestaetigen" value="Best&auml;tigen" tabindex="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="button" name="abbrechen" value="Abbrechen" tabindex="3"></td>
		</tr>
		</table>
	<%@include file="include/inc_footer.jsp"%>
	</div>
	<div id="show_none">
		<%@include file="include/inc_menue.jsp"%>
	</div>
</body>
</html>