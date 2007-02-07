<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Administrator anlegen</title>

</head>
<body>
<%@include file="include/inc_header.jsp"%>

<div id="content">
<form>
<h1>Administrator anlegen</h1>
<fieldset style="width: 60%"><legend><b>Pers&ouml;nliche
Angaben</b></legend>
<table heiht="20%">
	<tr>
		<td>Titel *<br>

		<select name="auswahl_zentrum">
			<option value="kein Titel">kein Titel</option>
			<option>Dr.</option>
			<option>Prof.</option>
			<option>Prof.Dr</option>
		</select>
	</tr>
	<tr>
		<td>Vorname *<br>
		<input type="text" size="25" maxlength="30" name="Vorname"
			tabindex="2"></td>
		<td>Nachname *<br>
		<input type="text" size="25" maxlength="30" name="Nachname"
			tabindex="3"></td>
	</tr>
	<tr>
		<td>Geschlecht *<br>
		<input type="radio" name="weiblich">weiblich <input
			type="radio" name="maennlich">m&auml;nnlich</td>
	</tr>
</table>
</fieldset>
<br>
<fieldset style="width: 60%"><legend><b>Kontaktdaten</b></legend>
<table>
	<tr>
		<td>E-Mail Adresse *<br>
		<input type="text" size="25" maxlength="30" name="Email" tabindex="5"></td>
		<td>&nbsp;&nbsp;&nbsp;Telefonnummer *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Telefon" tabindex="6"></td>
	</tr>
	<tr>
		<td>Fax<br>
		<input type="text" size="25" maxlength="30" name="Fax" tabindex="7"></td>
		<td>&nbsp;&nbsp;&nbsp;Institut *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Institut" tabindex="8"></td>
	</tr>
</table>
</fieldset>
</form>
<form>&nbsp;Felder mit '*' sind Pflichtfelder und m&uuml;ssen
ausgef&uuml;llt werden.</form>
<table>
	<tr>
		<td><input type="submit" name="bestaetigen"
			value="Best&auml;tigen" tabindex="9">&nbsp;&nbsp;&nbsp;</td>
		<td><input type="submit" name="abbrechen" value="Abbrechen"
			tabindex="10" onClick=""></td>
	</tr>
</table>
</div>
<div id="show_none"></div>

<div id="show_SA"><%@include file="include/inc_menue.jsp"%></div>

</body>
</html>
