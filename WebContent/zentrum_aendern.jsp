<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.ZENTRUM_AENDERN.toString());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="include/inc_extjs.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>

<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.beans.AktivierungBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.Parameter"
	import="de.randi2.controller.DispatcherServlet"%>
</head>

<body>
<%@include file="include/inc_header.jsp"%>

<div id="content">
<%@include file="include/inc_nachricht.jsp"%>
<form action="DispatcherServlet" method="post" name="user" id="user"><input
	type="hidden" name="anfrage_id"
	value="<%=DispatcherServlet.anfrage_id.JSP_ZENTRUM_AENDERN.name() %>">
<h1>Zentrum aendern</h1>

<fieldset style="width: 60%"><legend><b>Angaben
zum Zentrum</b></legend>
<table>
	<%
		//Holen des Zentrums, das angezeigt und geaendert werden soll.
		ZentrumBean aZentrum = (ZentrumBean) request.getAttribute("aZentrum");
	%>
	<tr>
		<td>Name der Institution *<br>
		<input type="text" size="46" maxlength="40" name="<%Parameter.zentrum.INSTITUTION.toString(); %>"
			value="<%if(aZentrum != null) {out.print(aZentrum.getInstitution());}%>"
			tabindex="1"></td>
	</tr>
	<tr>
		<td>Name der genauen Abteilung *<br>
		<input type="text" size="46" maxlength="40" name="<%Parameter.zentrum.ABTEILUNGSNAME.toString(); %>"
			value="<%if(aZentrum != null) {out.print(aZentrum.getAbteilung());}%>"
			tabindex="2"></td>
	</tr>
	<tr>
		<td>Strasse *
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		Hausnr * <br>
		<input type="text" size="30" maxlength="30" name="<%Parameter.zentrum.STRASSE.toString(); %>"
			value="<%if(aZentrum != null) {out.print(aZentrum.getStrasse());}%>"
			tabindex="3"> &nbsp;&nbsp;&nbsp; <input type="text" size="8"
			maxlength="8" name="<%Parameter.zentrum.HAUSNUMMER.toString(); %>"
			value="<%if(aZentrum != null) {out.print(aZentrum.getHausnr());}%>"
			tabindex="4"></td>
	</tr>
	<tr>
		<td>PLZ * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ort * <br>
		<input type="text" size="6" maxlength="6" name="<%Parameter.zentrum.PLZ.toString(); %>"
			value="<%if(aZentrum != null) {out.print(aZentrum.getPlz());}%>"
			tabindex="5">&nbsp;&nbsp;&nbsp; <input type="text" size="33"
			maxlength="33" name="<%Parameter.zentrum.ORT.toString(); %>"
			value="<%if(aZentrum != null) {out.print(aZentrum.getOrt());}%>"
			tabindex="6"></td>

	</tr>
	<tr>
		<!-- Wenn Passwort nicht geaendert werden soll, dann wird bei leer gelassenen Feldern
		das alte Passwort weiter verwendet. -->
		<td>Passwort *<br>
		<input type="password" size="25" maxlength="30" name="<%Parameter.benutzerkonto.PASSWORT.toString(); %>"
			tabindex="6" value=""></td>
	</tr>
	<tr>
		<td>Passwort wiederholen *<br>
		<input type="password" size="25" maxlength="30" name="<%Parameter.benutzerkonto.PASSWORT_WIEDERHOLUNG.toString(); %>"
			tabindex="7" value=""></td>
	</tr>
</table>
</fieldset>
<br>
<fieldset><legend><b>Angaben zum Ansprechpartner</b></legend>
<table>
	<tr>
		<td>Vorname *<br>
		<input type="text" size"38" maxlength="40" name="<%Parameter.person.VORNAME.toString(); %>"
			value="<%if(aZentrum != null) {if(aZentrum.getAnsprechpartner() != null) {out.print(aZentrum.getAnsprechpartner().getVorname());}}%>"
			tabindex="7">&nbsp;&nbsp;&nbsp;</td>
		<td>Nachname *<br>
		<input type="text" size="38" maxlength="40" name="<%Parameter.person.NACHNAME.toString(); %>"
			value="<%if(aZentrum != null) {if(aZentrum.getAnsprechpartner() != null) {out.print(aZentrum.getAnsprechpartner().getNachname());}}%>"
			tabindex="8"></td>
	</tr>
	<tr>
		<td>Geschlecht *<br>
		<input type="radio" name="<%Parameter.person.GESCHLECHT.toString(); %>" value="w"
			<%if (aZentrum != null) {if(aZentrum.getAnsprechpartner() != null){if(aZentrum.getAnsprechpartner().getGeschlecht() == 'w'){out.print("checked");}}}%>>weiblich
		<input type="radio" name="<%Parameter.person.GESCHLECHT.toString(); %>" value="m"
			<%if (aZentrum != null) {if(aZentrum.getAnsprechpartner() != null){if(aZentrum.getAnsprechpartner().getGeschlecht() == 'm'){out.print("checked");}}}%>>m&auml;nnlich</td>
	</tr>
	<tr>
		<td>Telefon *<br>
		<input type="text" size="40" maxlength="40" name="<%Parameter.person.TELEFONNUMMER.toString(); %>"
			value="<%if(aZentrum != null) {if(aZentrum.getAnsprechpartner() != null) {out.print(aZentrum.getAnsprechpartner().getTelefonnummer());}}%>"
			tabindex="9"></td>
	</tr>
	<tr>
		<td>Fax<br>
		<input type="text" size="40" maxlength="40" name="<%Parameter.person.FAX.toString(); %>"
			value="<%if(aZentrum != null) {
						if(aZentrum.getAnsprechpartner() != null) {
							String fax = aZentrum.getAnsprechpartner().getFax();
							if(fax != null) {
								out.print(fax);
							} else {
								out.print("");
							}
						}
					 }%>"
			tabindex="10"></td>
	</tr>
	<tr>
		<td>Email *<br>
		<input type="text" size="46" maxlength="50" name="<%Parameter.person.EMAIL.toString(); %>"
			value="<%if(aZentrum != null) {if(aZentrum.getAnsprechpartner() != null) {out.print(aZentrum.getAnsprechpartner().getEmail());}}%>"
			tabindex="11"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>
</table>
</fieldset>
<br>

<table>
	<tr>
		<td><input type="submit" name="Submit" value="Zentrum &auml;ndern"
			tabindex="12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="button" name="abbrechen" value="Abbrechen"
			tabindex="13"></td>
	</tr>
</table>
</form>
<br>

&nbsp;Die mit '*' gekennzeichneten Felder sind Pflichtfelder. <%@include
	file="include/inc_footer.jsp"%></div>
<%@include file="include/inc_menue.jsp"%>
</body>
</html>
