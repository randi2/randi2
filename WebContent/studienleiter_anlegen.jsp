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
<title>Randi2 :: Studienleiter anlegen</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>


<div id="content">

<h1>Studienleiter anlegen</h1>
<form>
<fieldset style="width: 60%"><legend><b>Pers&ouml;nliche
Angaben</b></legend>
<table height="20%">
	<tr>
		<td>Titel<br>
		<select name="Standardauswahl" tabindex="1">
			<option value="kein Titel">kein Titel</option>
			<option value="Dr.">Dr.</option>
			<option value="Prof.">Prof.</option>
			<option value="Prof. Dr.">Prof. Dr.</option>
		</select>
	</tr>
	<tr>
		<td>Vorname * <input type="text" size="25" maxlength="30"
			name="Vorname" tabindex="2"></td>
		<td>Nachname * &nbsp;&nbsp;&nbsp;<input type="text" size="25"
			maxlength="30" name="Nachname" tabindex="3"></td>
	</tr>
	<tr>
		<td>Login-Name * &nbsp;&nbsp;&nbsp;<input type="text" size="25"
			maxlength="30" name="Nachname" tabindex="4"></td>
	</tr>
</table>
</fieldset>
<br>
<fieldset style="width: 60%"><legend><b>Kontaktdaten</b></legend>

<table>
	<tr>
		<td>E-Mail Adresse<br>
		<input type="text" size="25" maxlength="30" name="Email" tabindex="5"><br>
		</td>

		<td>Fax&nbsp;&nbsp;&nbsp;<br>
		<input type="text" size="25" maxlength="30" name="Email" tabindex="6"></td>
	</tr>
	<tr>
		<td><br>
		Telefonnummer *<br>
		<input type="text" size="25" maxlength="30" name="Telefon"
			tabindex="7"></td>
	</tr>
	<tr>
		<td><br>
		Telefonnummer(Mobil)<br>
		<input type="text" size="25" maxlength="30" name="Fax" tabindex="8"></td>
	</tr>
	<tr>
		<td><br>
		Telefonnummer(Sekret&auml;rin) *<br>
		<input type="text" size="25" maxlength="30" name="Institut"
			tabindex="9"></td>
	</tr>
</table>
</fieldset>
<br>

&nbsp;Felder mit '*' sind Pflichtfelder und m&uuml;ssen ausgef&uuml;llt
werden.</form>
<table>
	<tr>
		<td><input type="button" name="bestaetigen"
			value="Best&auml;tigen" tabindex="10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="button" name="abbrechen" value="Abbrechen"
			tabindex="11"></td>
	</tr>
</table>

<br>
<%@include file="include/inc_footer.jsp"%></div>
<div id="show_none"></div>
<div id="show_none">
<div id="sub_SV"><%@include file="include/inc_menue.jsp"%>
</div>
</div>
</body>
</html>
