<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import= "de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import= "de.randi2.model.fachklassen.beans.PersonBean"
	import= "java.util.GregorianCalendar"
	import= "java.text.SimpleDateFormat" 
	import= "java.util.Locale"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Randi2 :: Studie &auml;ndern</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Studie &auml;ndern</h1>
<form>
<fieldset><legend><b>Studienangaben</b></legend>
<table>
	<tr>
		<td>Name der Studie *<br>
		<input type="text" size="40" maxlength="40" name="Name_Studie"
			tabindex="1"></td>
	</tr>
	<tr>
		<td>Beschreibung der Studie *<br>
		<textarea cols="37" rows="4" name="Studie_Beschreibung" tabindex="2"></textarea></td>
	</tr>
	<tr>
		<td>Startdatum *<br>
		<input type="text" size="40" maxlength="40" name="Startdatum"
			tabindex="3" value="Vorerst nur ein Textfeld"></td>
		<td>Enddatum *<br>
		<input type="text" size="40" maxlength="40" name="Enddatum"
			tabindex="4" value="Vorerst nur ein Textfeld"></td>
	</tr>
	<tr>

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
	<table>
	<tr>
		<td><br>
		Randomisationsbezogene Eigenschaften *<br>
		<textarea cols="37" rows="4"
			name="Randomisationsbezogene_Eigenschaften" tabindex="6"></textarea></td>
	</tr>
	<tr>
		<td><br>
		Leitende Institution<br>
		<input type="text" size="40" maxlength="40" name="ID_Institution"
			tabindex="7"></td>
		<td></td>
		<td><br>
		Verantwortliche(r) Studienleiter(in)<br>
		<input type="text" size="40" maxlength="40" name="ID_Studienleiter"
			tabindex="8"></td>
	</tr>
</table>
</table>
</fieldset>
</form>
<table align="center">
	<tr>
		<td><input type="button" name="bestaetigen"
			value="Best&auml;tigen" tabindex="11">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="button" name="abbrechen" value="Abbrechen"
			tabindex="12" onclick="location.href='studie_ansehen.jsp'"></td>
	</tr>
</table>
<br>
<%@include file="include/inc_footer.jsp"%></div>
<div id="show_none"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
