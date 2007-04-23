<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import= "de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import= "java.util.GregorianCalendar"
	import= "java.text.SimpleDateFormat" 
	import= "java.util.Locale"
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">
<%@ page import="de.randi2.controller.DispatcherServlet" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: Benutzer anlegen</title>

</head>
<body>
<%@include file="include/inc_header.jsp"%>

<div id="content">
<form>
<h1>Benutzer anlegen</h1>

<fieldset style="width: 60%">
	<legend><b>Pers&ouml;nliche Angaben</b></legend>
		<table height="244">
		<tr>
		<td>Titel<br><select name="Standardauswahl">
			<option value="Dr.">Dr.</option>
			<option value="Prof.">Prof.</option>
			<option value="Prof. Dr.">Prof. Dr.</option>
			</select><br><br></td>
		</tr>
		<tr>
		<td>Vorname *<br>
		  <input type="text" size="25" maxlength="30" name="Vorname" tabindex="2"></td>
		<td>&nbsp;&nbsp;&nbsp;Nachname *<br>
		  &nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30" name="Nachname" tabindex="3"></td>
		</tr>
		<tr>
		<td>
			<br><input type="radio" name="weiblich" tabindex="4">Weiblich
			<input type="radio" name="maennlich" tabindex="5">M&auml;nnlich<br><br>
		</td>
		</tr>
		<tr>
		<td>Passwort *<br><input type="text" size="25" maxlength="30" name="Passwort" tabindex="6"></td>
		</tr>
		<tr>
		<td>Passwort wiederholen *<br><input type="text" size="25" maxlength="30" name="Passwort_wh" tabindex="7"></td>
		</tr>
	</table>
</fieldset><br>
<fieldset style="width: 60%">
	<legend><b>Kontaktdaten</b></legend>
		<table>
		<tr>
		<td>E-Mail Adresse *<br><input type="text" size="25" maxlength="30" name="Email" tabindex="8"></td>
		<td>&nbsp;&nbsp;&nbsp;Telefonnummer *<br>&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30" name="Telefon" tabindex="9"></td>
		</tr>
		<tr>
		<td>Fax<br><input type="text" size="25" maxlength="30" name="Fax" tabindex="10"></td>
		<td>&nbsp;&nbsp;&nbsp;Institut *<br>&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30" name="Institut" tabindex="11"></td>
		</tr>
		</table>
</fieldset>
			<br>&nbsp;Felder mit '*' sind Pflichtfelder und m&uuml;ssen ausgef&uuml;llt werden.
</form>
<form action="index.html" method="POST">
		<table>
		<tr>
		<td><input type="submit" name="bestaetigen" value="Best&auml;tigen" tabindex="12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="submit" name="abbrechen" value="Abbrechen" tabindex="13" onClick=""></td>
		</tr>
		</table>
</form>
				<%@include file="include/inc_footer.jsp"%>
	</div>
	<div id="show_none">

	</div>
<div id="show_none">
</div>
</body>
</html>
