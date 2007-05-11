<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ page import= "de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import= "de.randi2.model.fachklassen.beans.PersonBean"
	import= "java.util.GregorianCalendar"
	import= "java.text.SimpleDateFormat" 
	import= "java.util.Locale"
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>Randi2 :: Studie anlegen</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Studie anlegen</h1>
<form>
<fieldset><legend><b>Studienangaben</b></legend>
<table>
	<tbody>
		<tr>
			<td>Name der Studie *<br>
			<input size="40" maxlength="40" name="Name_Studie" tabindex="1"
				type="text"></td>
			<td>Startdatum *<br>
			<input size="40" maxlength="40" name="Startdatum" tabindex="3"
				value="Vorerst nur ein Textfeld" type="text"></td>
		</tr>
		<tr>
			<td>Beschreibung der Studie *<br>
			<textarea cols="37" rows="4" name="Studie_Beschreibung" tabindex="2"></textarea></td>
			<td>Enddatum *<br>
			<input size="40" maxlength="40" name="Enddatum" tabindex="4"
				value="Vorerst nur ein Textfeld" type="text"></td>
		</tr>
	</tbody>
</table>
</fieldset>
<br>
<fieldset><legend><b>Zusatzangaben</b></legend>
<table>
	<tbody>
		<tr>
			<td>Studienprotokoll *&nbsp;&nbsp;&nbsp;<input name="Datei"
				size="50" maxlength="100000" accept="text/*" id="datei" tabindex="5"
				type="file"><br>
			<br>
			</td>
		</tr>
		<tr>
			<td>Arme der Studie</td>
		</tr>
	</tbody>
</table>
<table width="90%">
	<tbody>
		<tr>
			<th align="left">Bezeichnung</th>
			<th align="left">Beschreibung</th>
		</tr>
		<tr>
			<td><input name="studienarm1" value="Studienarm1" size="30"
				type="text"></td>
			<td><input name="beschreibung1" value="..." size="80"
				type="text"></td>
		</tr>
		<tr>
			<td><input name="studienarm2" value="Studienarm2" size="30"
				type="text"></td>
			<td><input name="beschreibung2" value="..." size="80"
				type="text"></td>
		</tr>
		<tr>
			<td><input name="studienarm3" value="Studienarm3" size="30"
				type="text"></td>
			<td><input name="beschreibung3" value="..." size="80"
				type="text"></td>
		</tr>
		<tr>
			<td><input name="studienarm4" value="Studienarm4" size="30"
				type="text"></td>
			<td><input name="beschreibung4" value="..." size="80"
				type="text"></td>
		</tr>
		<tr>
			<td><input name="studienarm5" value="Studienarm5" size="30"
				type="text"></td>
			<td><input name="beschreibung5" value="..." size="80"
				type="text"></td>
		</tr>
	</tbody>
</table><br>
<fieldset style="width: 70%;"><legend><b>Statistiker</b></legend>
<table>
	<tbody>
		<tr>
			<td>Soll ein Statistiker-Account f&uuml;r die Studie angelegt
			werden ?</td>
		</tr>
		<tr>
			<td><br>
			<input name="stat_anlegen" value=" Ja " tabindex="1" type="radio">Ja&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input name="stat_anlegen" value="Nein" tabindex="2" type="radio">Nein</td>
		</tr>
	</tbody>
</table>
</fieldset>
<br>
<table align="center">
	<tbody>
		<tr>
			<td><input name="bestaetigen" value="Bestätigen" tabindex="11"
				onclick="location.href='randomisation.jsp'" type="button">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td><input name="abbrechen" value="Abbrechen" tabindex="12"
				onclick="top.location.href='studie_auswaehlen.jsp'" type="button"></td>
		</tr>
	</tbody>
</table>
</fieldset>

<%@include file="include/inc_footer.jsp"%> 


</form></div></body>
</html>
