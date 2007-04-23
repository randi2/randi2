<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import= "de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import= "java.util.GregorianCalendar"
	import= "java.text.SimpleDateFormat" 
	import= "java.util.Locale"
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Benutzer anlegen</title>

</head>
<body>
<%@include file="include/inc_header_clean.jsp"%>

<div id="content">
<form>
<h1>Benutzer anlegen</h1>

<fieldset style="width: 60%"><legend><b>Pers&ouml;nliche
Angaben</b></legend>
<table height="244">
	<tr>
		<td>Titel<br>
		<select name="Standardauswahl">
			<option value="kein Titel">kein Titel</option>
			<option value="Dr.">Dr.</option>
			<option value="Prof.">Prof.</option>
			<option value="Prof. Dr.">Prof. Dr.</option>
		</select><br>
		</td>
	</tr>
	<tr>
		<td>Vorname *<br>
		<input type="text" size="25" maxlength="30" name="Vorname"
			tabindex="2"></td>
		<td>&nbsp;&nbsp;&nbsp;Nachname *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Nachname" tabindex="3"></td>
	</tr>
	<tr>
		<td>Geschlecht *<br>
		<input type="radio" name="weiblich">weiblich <input
			type="radio" name="maennlich">m&auml;nnlich</td>
	</tr>
	<tr>
		<td>Passwort *<br>
		<input type="text" size="25" maxlength="30" name="Passwort"
			tabindex="6"></td>
	</tr>
	<tr>
		<td>Passwort wiederholen *<br>
		<input type="text" size="25" maxlength="30" name="Passwort_wh"
			tabindex="7"></td>
	</tr>
</table>
</fieldset>
<br>
<fieldset style="width: 60%"><legend><b>Kontaktdaten</b></legend>
<table>
	<tr>
		<td>E-Mail *<br>
		<input type="text" size="25" maxlength="30" name="Email" tabindex="8"></td>
		<td>&nbsp;&nbsp;&nbsp;Telefonnummer *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Telefon" tabindex="9"></td>
	</tr>
	<tr>
		<td>Fax<br>
		<input type="text" size="25" maxlength="30" name="Fax" tabindex="10"></td>
		<td>&nbsp;&nbsp;&nbsp;Institut *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Institut" tabindex="11"></td>
	</tr>
</table>
</fieldset>
<br>

<fieldset style="width: 60%"><legend><b>Angaben
zum Ansprechpartner</b></legend>
<table>
	<tr>
		<td>Vorname *<br>
		<input type="text" size="25" maxlength="30" name="Vorname"
			tabindex="12" value=""></td>
		<td>&nbsp;&nbsp;&nbsp;Nachname *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Nachname" tabindex="13" value=""></td>
	</tr>
	<tr>
		<td>Telefonnummer *<br>
		<input type="text" size="25" maxlength="30" name="Telefon"
			tabindex="14" value=""></td>
	</tr>
</table>
</fieldset>
<br>

</form>
<form action="index.html" method="POST">
<table>
	<tr>
		<td><input type="submit" name="anlegen" value="Anlegen"
			tabindex="12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="submit" name="abbrechen"
			value="   Zur&uuml;ck   " tabindex="13"
			onClick="location.href='benutzer_anlegen_zwei'"></td>
	</tr>
</table>
</form>
<br>
&nbsp;Die mit '*' gekennzeichneten Felder sind Pflichtfelder. <%@include
	file="include/inc_footer.jsp"%></div>
<div id="show_none"></div>
<div id="show_none"></div>
</body>
</html>
