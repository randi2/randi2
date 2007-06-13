<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: Benutzer &auml;ndern</title>

<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.*"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.*" import="de.randi2.controller.*"%>
</head>

<body>
<%@include file="include/inc_header.jsp"%>

<div id="content">
<form action="DispatcherServlet" method="post" name="user" id="user"><input
	type="hidden" name="anfrage_id"
	value="<%=DispatcherServlet.anfrage_id.JSP_DATEN_AENDERN.name() %>">
<h1>Daten &auml;ndern</h1>
<fieldset style="width: 60%"><legend><b>Pers&ouml;nliche
Angaben</b></legend>
<table height="244">
	<tr>
		<td>Titel<br>
		<select name="Titel">
			<%
				//Holen des PersonBeans des aktuell angemeldeten Benutzers
				PersonBean aPerson = aBenutzer.getBenutzer();

				for (PersonBean.Titel e : PersonBean.Titel.values()) {
					out.print("<option value=\"" + e.toString() + "\"");//1. Option Teil
					//aktueller Titel
					if (aPerson.getTitel() != null && aPerson.getTitel() == e) {
						out.print("selected");
					}
					//Ende Option, Option Text
					out.print(">" + e.toString() + "</option>");
				}
			%>
		</select></td>
	</tr>
	<tr>
		<td>Vorname *<br>
		<input type="text" size="25" maxlength="30" name="Vorname"
			tabindex="2" readonly value="<%out.print(aPerson.getVorname());%>"
			tabindex="2""></td>

		<td>&nbsp;&nbsp;&nbsp;Nachname *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Nachname" tabindex="3" readonly
			value="<%out.print(aPerson.getNachname());%>"></td>
	</tr>
	<tr>
		<td>Geschlecht *<br>
		<input type="radio" name="weiblich"
			<%if (aPerson.getGeschlecht() == 'w') {out.print("checked");}%>
			disabled>weiblich <input type="radio" name="maennlich"
			<%if (aPerson.getGeschlecht() == 'm') {out.print("checked");}%>
			disabled>m&auml;nnlich</td>
	</tr>
	<tr>
		<!-- Wenn Passwort nicht geaendert werden soll, dann wird bei leer gelassenen Feldern
		das alte Passwort weiter verwendet. -->
		<td>Passwort *<br>
		<input type="password" size="25" maxlength="30" name="Passwort"
			tabindex="6" value=""></td>
	</tr>
	<tr>
		<td>Passwort wiederholen *<br>
		<input type="password" size="25" maxlength="30" name="Passwort_wh"
			tabindex="7" value=""></td>
	</tr>
</table>
</fieldset>
<br>

<fieldset style="width: 60%"><legend><b>Kontaktdaten</b></legend>
<table>
	<tr>
		<td>E-Mail *<br>
		<input type="text" size="25" maxlength="30" name="Email" tabindex="8"
			value="<%out.print(aPerson.getEmail());%>" readonly></td>
		<td>&nbsp;&nbsp;&nbsp;Telefonnummer *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Telefon" tabindex="9"
			value="<%out.print(aPerson.getTelefonnummer());%>"
			z:length="{max: 26, min: 6}"
			z:length_message="Telefonnummer muss 6 bis 26 Zeichen lang sein"></td>
	</tr>
	<tr>
		<td>Handynummer<br>
		<input type="text" size="25" maxlength="30" name="Handy" tabindex="10"
			value="<%if(aPerson.getHandynummer() != null){out.print(aPerson.getHandynummer());}%>"></td>
		<td>&nbsp;&nbsp;&nbsp;Fax<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Fax" tabindex="10"
			value="<%if(aPerson.getFax() != null){out.print(aPerson.getFax());}%>"></td>
	</tr>
	<tr>
		<td colspan="2" align="left">Institut *<br>
		<input type="text" size="25" maxlength="30" name="Institut"
			tabindex="11"
			value="<%if (aBenutzer.getZentrum().getInstitution() != null){out.print(aBenutzer.getZentrum().getInstitution());}%>"
			readonly></td>
	</tr>
</table>
</fieldset>

<br>

<fieldset style="width: 60%"><legend><b>Angaben
zum Stellvertreter</b></legend>
<p>Sobald ein Stellvertreter angegeben wird, sind alle Felder
auszuf&uuml;llen. Sonst keine.</p>
<table>
	<tr>
		<td>Vorname *<br>
		<input type="text" size="25" maxlength="30" name="VornameA"
			tabindex="12"
			value="<%if(aPerson.getStellvertreter() != null){if (aPerson.getStellvertreter().getVorname() != null){out.print(aPerson.getStellvertreter().getVorname());}}%>"
			z:length="{max: 50, min: 2}"
			z:length_message="Vorname muss 2 bis 50 Zeichen lang sein"></td>
		<td>&nbsp;&nbsp;&nbsp;Nachname *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="NachnameA" tabindex="13"
			value="<%if(aPerson.getStellvertreter() != null){if (aPerson.getStellvertreter().getNachname() != null){out.print(aPerson.getStellvertreter().getNachname());}}%>"
			z:length="{max: 50, min: 2}"
			z:length_message="Nachname muss 2 bis 50 Zeichen lang sein"></td>
	</tr>
	<tr>
		<td>Geschlecht *<br>
		<input type="radio" name="weiblichA"
			<%if(aPerson.getStellvertreter() != null){if (aPerson.getStellvertreter().getGeschlecht() == 'w'){out.print("checked");}}%>>weiblich
		<input type="radio" name="maennlichA"
			<%if(aPerson.getStellvertreter() != null){if (aPerson.getStellvertreter().getGeschlecht() == 'm'){out.print("checked");}}%>>m&auml;nnlich
		</td>
	</tr>
	<tr>
		<td>Telefonnummer *<br>
		<input type="text" size="25" maxlength="30" name="TelefonA"
			tabindex="14"
			value="<%if(aPerson.getStellvertreter() != null){if (aPerson.getStellvertreter().getTelefonnummer() != null){out.print(aPerson.getStellvertreter().getTelefonnummer());}}%>"
			z:length="{max: 26, min: 6}"
			z:length_message="Telefonnummer muss 6 bis 26 Zeichen lang sein">
		</td>
		<td>E-Mail *<br>
		<input type="text" size="25" maxlength="30" name="EmailA" tabindex="8"
			value="<%if(aPerson.getStellvertreter() != null){if (aPerson.getStellvertreter().getEmail() != null){out.print(aPerson.getStellvertreter().getEmail());}}%>">
		</td>
		<td><input type="submit" name="loeschenA"
			value="Ansprechpartner entfernen" tabindex="12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
	</tr>
</table>
</fieldset>

<br>

<table>
	<tr>
		<td><input type="submit" name="speichern" value="Speichern"
			tabindex="12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="button" name="abbrechen" value="Abbrechen"
			tabindex="13" onClick="location.href='studie_auswaehlen.jsp'"></td>
	</tr>

</table>
</form>
<br>

&nbsp;Die mit '*' gekennzeichneten Felder sind Pflichtfelder. <%@include
	file="include/inc_footer.jsp"%></div>
<%@include file="include/inc_menue.jsp"%>
</body>
</html>
