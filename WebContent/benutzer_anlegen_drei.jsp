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
<form action="DispatcherServlet" method="post">
<input type="hidden" name="anfrage_id" value="JSP_BENUTZER_ANLEGEN_DREI_BENUTZER_REGISTRIEREN_VIER">
<h1>Benutzer anlegen</h1>
<%@include file="include/inc_nachricht.jsp"%>
<fieldset style="width: 60%"><legend><b>Pers&ouml;nliche
Angaben</b></legend>
<table height="244">
	<tr>
		<td>Titel<br>
		<select name="Titel">
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
		<td>Handy<br>
		<input type="text" size="25" maxlength="30" name="Handy" tabindex="10"></td>
		<td>&nbsp;&nbsp;&nbsp;Fax<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30" name="Fax" tabindex="11"></td>
	</tr>
	<tr>
		<td colspan="2">Institut *<br>
		<input type="text" size="25" maxlength="30"
			name="Institut" tabindex="12"></td>
	</tr>
</table>
</fieldset>
<br>



<table>
	<tr>
		<td><input type="submit" name="anlegen" value="Anlegen"
			tabindex="13">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="submit" name="abbrechen"
			value="   Zur&uuml;ck   " tabindex="14"
			onClick="location.href='benutzer_anlegen_zwei'"></td>
	</tr>
</table>
</form>
<br>
&nbsp;Die mit '*' gekennzeichneten Felder sind Pflichtfelder. <%@include
	file="include/inc_footer_clean.jsp"%></div>
</body>
</html>
