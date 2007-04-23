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
<title>neues Zentrum anlegen</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>neues Zentrum anlegen</h1>
<form>
<fieldset style="width: 60%"><legend><b>Angaben
zum Zentrum</b></legend>
<table>
	<tr>
		<td>Name der Institution *<br>
		<input type="text" size="46" maxlength="40" name="Name_Institution"
			tabindex="1"></td>

	</tr>
	<tr>
		<td>Name der genauen Abteilung *<br>
		<input type="text" size="46" maxlength="40" name="Zentrum_Abteilung"
			tabindex="2"></td>

	</tr>
	<tr>
		<td>Strasse *
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hausnr
		* <br>
		<input type="text" size="30" maxlength="30" name="strasse"
			tabindex="3" value=""> &nbsp;&nbsp;&nbsp; <input type="text"
			size="8" maxlength="8" name="hausnummer" tabindex="4" value=""></td>

	</tr>
	<tr>
		<td>PLZ * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ort * <br>
		<input type="text" size="6" maxlength="6" name="PLZ" tabindex="5"
			value="">&nbsp;&nbsp;&nbsp; <input type="text" size="33"
			maxlength="33" name="Ort" tabindex="6" value=""></td>

	</tr>

</table>
</fieldset>
<br>
<fieldset><legend><b>Angaben zum Ansprechpartner</b></legend>
<table>
	<tr>
		<td>Vorname *<br>
		<input type="text" size"38" maxlength="40" name="vorname" tabindex="7"
			value="">&nbsp;&nbsp;&nbsp;</td>
		<td>Nachname *<br>
		<input type="text" size"38" maxlength="40" name="nachname"
			tabindex="8" value=""></td>
	</tr>
	<tr>
		<td>Telefon *<br>
		<input type="text" size"40" maxlength="40" name="telefon" tabindex="9"
			value=""></td>
	</tr>
	<tr>
		<td>Fax<br>
		<input type="text" size"40" maxlength="40" name="fax" tabindex="10"
			value=""></td>
	</tr>
	<tr>
		<td>Email *<br>
		<input type="text" size"46" maxlength="50" name="email" tabindex="11"
			value=""></td>
	</tr>
</table>
</fieldset>
<br>

&nbsp;Felder mit '*' sind Pflichtfelder und m&uuml;ssen ausgef&uuml;llt
werden.</form>
<table>

	<tr>
		<td><input type="button" name="passwort_anfordern"
			value="Passwort anfordern" tabindex="12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="button" name="abbrechen" value="Abbrechen"
			tabindex="13"></td>
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
