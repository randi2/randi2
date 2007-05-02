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
	<tr>
		<td>Name der Studie *<br>
		<input type="text" size="40" maxlength="40" name="Name_Studie"
			tabindex="1"></td>
		<td>Startdatum *<br>
		<input type="text" size="40" maxlength="40" name="Startdatum"
			tabindex="3" value="Vorerst nur ein Textfeld"></td>
	</tr>
	<tr>
		<td>Beschreibung der Studie *<br>
		<textarea cols="37" rows="4" name="Studie_Beschreibung" tabindex="2"></textarea></td>
		<td>Enddatum *<br>
		<input type="text" size="40" maxlength="40" name="Enddatum"
			tabindex="4" value="Vorerst nur ein Textfeld"></td>
	</tr>
</table>
</fieldset>
<br>
<fieldset><legend><b>Zusatzangaben</b></legend>
<table>
	<tr>
		<td>Studienprotokoll *&nbsp;&nbsp;&nbsp;<input name="Datei"
			type="file" size="50" maxlength="100000" accept="text/*" id=datei
			tabindex="5"><br>
		<br>
		</td>
	</tr>
	<tr>
		<td>Arme der Studie</td>
	</tr>

	<table width="90%">
		<tr>
			<th align="left">Bezeichnung</th>
			<th align="left">Beschreibung</th>
		</tr>
		<tr>
			<td><input name="studienarm1" value="Studienarm1" type="text"
				size="30"></td>
			<td><input name="beschreibung1" value="..." type="text"
				size="80"></td>
		</tr>
		<tr> 
			<td><input name="studienarm2" value="Studienarm2" type="text"
				size="30"></td>
			<td><input name="beschreibung2" value="..." type="text"
				size="80"></td>
		</tr>
		<tr>
			<td><input name="studienarm3" value="Studienarm3" type="text"
				size="30"></td>
			<td><input name="beschreibung3" value="..." type="text"
				size="80"></td>
		</tr>
		<tr>
			<td><input name="studienarm4" value="Studienarm4" type="text"
				size="30"></td>
			<td><input name="beschreibung4" value="..." type="text"
				size="80"></td>
		</tr>
		<tr>
			<td><input name="studienarm5" value="Studienarm5" type="text"
				size="30"></td>
			<td><input name="beschreibung5" value="..." type="text"
				size="80"></td>
		</tr>
	</table>

<br> 
<fieldset style="width:70%">
	<legend><b>Statistiker</b></legend>
		<table>
		<tr>
		<td>Soll ein Statistiker-Account für die Studie angelegt werden ?</td>
		</tr>
		<tr>
		<td><br><input type="radio" name="stat_anlegen" value=" Ja " tabindex="1" >Ja&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" name="stat_anlegen" value="Nein" tabindex="2" >Nein</td>
		</tr>
		</table>

</fieldset><br>

<table align="center"> 
	<tr>
		<td><input type="button" name="bestaetigen"
			value="Best&auml;tigen" tabindex="11" onClick="location.href='randomisation.jsp'">
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="button" name="abbrechen" value="Abbrechen"
			tabindex="12" onClick="top.location.href='studie_auswaehlen.jsp'"></td>
	</tr>
</table>
<%@include file="include/inc_footer.jsp"%> 


</table></fieldset></form></body>
</html>
