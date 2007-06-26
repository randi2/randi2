<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.*" import="java.util.Iterator"
	import="java.util.Vector" import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.ZENTRUM_ANZEIGEN_ADMIN.toString());
%>
<%@ page import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>

<%
			Iterator listeZentrum = ((Vector) request
			.getAttribute("listeZentrum")).iterator();
	Iterator listePerson = ((Vector) request
			.getAttribute("listePerson")).iterator();
	ZentrumBean zentrum = new ZentrumBean();
	PersonBean person = new PersonBean();
	if (listeZentrum.hasNext() && listePerson.hasNext()) {
		zentrum = (ZentrumBean) listeZentrum.next();
		person = (PersonBean) listePerson.next();
	}
%>
<div id="content">
<h1>Zentrum anzeigen</h1>
<form action="DispatcherServlet" method="POST"><input
	type="hidden" name="anfrage_id"
	value="<%=DispatcherServlet.anfrage_id.ZENTRUM_ANZEIGEN_ADMIN.name() %>">
<fieldset style="width: 60%"><legend><b>Angaben
zum Zentrum</b></legend>
<table>
	<tr>
		<td>Name der Institution *<br>
		<input type="text" size="49" maxlength="40"
			name="<%=Parameter.zentrum.INSTITUTION %>" tabindex="1"
			value="<%=zentrum.getInstitution() %>"></td>
	</tr>
	<tr>
		<td>Name der genauen Abteilung *<br>
		<input type="text" size="49" maxlength="40"
			name="<%=Parameter.zentrum.ABTEILUNGSNAME %>" tabindex="2"
			value="<%=zentrum.getAbteilung() %>"></td>
	</tr>
	<tr>
		<td>Ort *<br>
		<input type="text" size="40" maxlength="40"
			name="<%=Parameter.zentrum.ORT %>" tabindex="3"
			value="<%=zentrum.getOrt() %>"></td>
		<td>PLZ *<br>
		<input type="text" size="6" maxlength="6"
			name="<%=Parameter.zentrum.PLZ %>" tabindex="4"
			value="<%=zentrum.getPlz() %>"></td>
	</tr>
	<tr>
		<td>Strasse * <br>
		<input type="text" size="40" maxlength="40"
			name="<%=Parameter.zentrum.STRASSE %>" tabindex="5"
			value="<%=zentrum.getStrasse() %>"></td>
		<td>Hausnummer *<br>
		<input type="text" size"10" maxlength="11"
			name="<%=Parameter.zentrum.HAUSNUMMER %>" tabindex="6"
			value="<%=zentrum.getHausnr() %>"></td>
	</tr>
	<tr>
		<td>Passwort * <br>
		<input type="password" size"20" maxlength="20"
			name="<%=Parameter.zentrum.PASSWORT %>" tabindex="7" value=""></td>
	</tr>
</table>
</fieldset>
<br>
<fieldset style="width: 60%"><legend><b>Angaben
zum Ansprechpartner</b></legend>
<table>
	<tr>
		<td>Nachname *<br>
		<input type="text" size"40" maxlength="40"
			name="<%=Parameter.person.NACHNAME %>" tabindex="8"
			value="<%=person.getNachname() %>"></td>
		<td>Vorname *<br>
		<input type="text" size"40" maxlength="40"
			name="<%=Parameter.person.VORNAME %>" tabindex="9"
			value="<%=person.getVorname() %>"></td>
	</tr>
	<tr>
		<td>Telefon *<br>
		<input type="text" size"40" maxlength="40"
			name="<%=Parameter.person.TELEFONNUMMER %>" tabindex="10"
			value="<%=person.getTelefonnummer() %>"></td>
	</tr>
	<tr>
		<td>Fax<br>
		<input type="text" size"40" maxlength="40"
			name="<%=Parameter.person.FAX %>" tabindex="11"
			value="<%=person.getFax() %>"></td>
	</tr>
	<tr>
		<td>Email *<br>
		<input type="text" size"40" maxlength="40"
			name="<%=Parameter.person.EMAIL %>" tabindex="12"
			value="<%=person.getEmail() %>"></td>
	</tr>

</table>
</fieldset>
</form>
<table align="left">
	<tr>

		<td><a href="zentrum_anzeigen.jsp"><input type="button"
			name="zurueck" value="zur&uuml;ck" tabindex="13"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="button" name="aendern"
			value="&Auml;nderungen speichern" tabindex="14">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="button" name="aktivieren"
			value="Zentrum aktivieren" tabindex="15">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="button" name="deaktivieren"
			value="Zentrum deaktivieren" tabindex="16">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>
</table>
<br>
<br>


<div id="show_none"><%@include file="include/inc_footer.jsp"%></div>


</div>

<div id="show_BV"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
