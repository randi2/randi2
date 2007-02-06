<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Benutzer &auml;ndern</title>

</head>
<body>
<%@include file="include/inc_header.jsp"%>

<div id="content">
<form>
<h1>Daten &auml;ndern</h1>
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
	</tr>
	<tr>
		<td>Vorname *<br>
		<!--<input type="text" size="25" maxlength="30" name="Vorname"
			tabindex="2" readonly value="$vorname">--><b>$vorname</b></td>
		<td>&nbsp;&nbsp;&nbsp;Nachname *<br>
		&nbsp;&nbsp;&nbsp;<!--<input type="text" size="25" maxlength="30"
			name="Nachname" tabindex="3" readonly value="$nachname">--><b>$nachname</b></td>
	</tr>
	<tr>
		<td>Geschlecht *<br>
		<input type="radio" name="weiblich" checked disabled>weiblich
		<input type="radio" name="maennlich" disabled>m&auml;nnlich</td>
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
		<!--<input type="text" size="25" maxlength="30" name="Email" tabindex="8" value="mail@host" readonly>--><b>$email</b></td>
		<td>&nbsp;&nbsp;&nbsp;Telefonnummer *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Telefon" tabindex="9" value="112"></td>
	</tr>
	<tr>
		<td>Handynummer<br>
		<input type="text" size="25" maxlength="30" name="Handy" tabindex="10"
			value="111"></td>
		<td>&nbsp;&nbsp;&nbsp;Fax<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Fax" tabindex="10" value="110"></td>
	</tr>
	<tr>
		<td colspan="2" align="left">Institut *<br>
		<!--<input type="text" size="25" maxlength="30"
			name="Institut" tabindex="11" value="Max-Planck-Institut" readonly>--><b>$institut</b></td>
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
			tabindex="12" value="$ansprechpartner_vorn"></td>
		<td>&nbsp;&nbsp;&nbsp;Nachname *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Nachname" tabindex="13" value="$ansprechpartner_nachn"></td>
	</tr>
	<tr>
		<td>Telefonnummer *<br>
		<input type="text" size="25" maxlength="30" name="Telefon"
			tabindex="14" value="$ansprechpartner_telenr"></td>
	</tr>
</table>
</fieldset>
<br>

</form>
<table>
	<tr>
		<td><input type="button" name="speichern" value="Speichern"
			tabindex="12" onclick="location.href='global_welcome.jsp'">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="button" name="abbrechen" value="Abbrechen"
			tabindex="13" onClick="location.href='studie_auswaehlen.jsp'"></td>
	</tr>

</table>

<br>

&nbsp;Die mit '*' gekennzeichneten Felder sind Pflichtfelder. <%@include
	file="include/inc_footer.jsp"%></div>
<%@include file="include/inc_menue.jsp"%>
</body>
</html>
