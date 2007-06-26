<%@ page import="de.randi2.model.fachklassen.beans.PersonBean"
	import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="de.randi2.controller.DispatcherServlet"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.model.fachklassen.beans.StudieBean"
	import="de.randi2.utility.*"%>
<%
		request.setAttribute(DispatcherServlet.requestParameter.TITEL
		.toString(), JspTitel.STUDIENLEITER_ANLEGEN.toString());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: <%=request.getAttribute(DispatcherServlet.requestParameter.TITEL.toString())%></title>
<script type="text/javascript" src="js/prototype.js"></script>
<script type="text/javascript" src="js/zebda.js"></script>

</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Studienleiter anlegen</h1>
<%@include file="include/inc_nachricht.jsp"%>
<form method="post" action="DispatcherServlet"><input
	type="hidden" name="anfrage_id"
	value="<%=DispatcherServlet.anfrage_id.AKTION_STUDIENLEITER_ANLEGEN.name() %>">

<%@include file="include/inc_nachricht.jsp"%>
<fieldset style="width: 60%"><legend><b>Pers&ouml;nliche
Angaben</b></legend>
<table height="20%">
	<tr>
		<td>Titel<br>
		<select id="Titel" name="Titel" tabindex="1">
			<%
			for (PersonBean.Titel aTitel : PersonBean.Titel.values()) {
			%>
			<option value="<%=aTitel.toString()%>"><%=aTitel.toString()%></option>
			<%
			}
			%>
		</select><br>
	</tr>
	<tr>
		<td><label for="Vorname">Vorname *</label><br>
		<input type="text" size="25" maxlength="30" id="Vorname"
			name="Vorname" tabindex="2" z:required="true"
			z:message="Bitte Vornamen angeben"></td>
		<td><label for="Nachname">Nachname *</label><br>
		<input type="text" size="25" maxlength="30" id="Nachname"
			name="Nachname" tabindex="3" z:required="true"
			z:message="Bitte Nachnamen angeben"></td>
	</tr>
	<tr>
		<td>Login-Name * &nbsp;&nbsp;&nbsp;<input type="text" size="25"
			maxlength="30" id="Login-Name" name="Login-Name" tabindex="4"
			z:required="true" z:message="Bitte Login-Name angeben"></td>
	</tr>
</table>
</fieldset>
<br>
<fieldset style="width: 60%"><legend><b>Kontaktdaten</b></legend>

<table>
	<tr>
		<td><label for="EMail">E-Mail Adresse *</label><br>
		<input type="text" size="25" maxlength="30" id="EMail" name="EMail"
			tabindex="5" z:required="true" z:message="Bitte E-Mail angeben"
			z:email="true"
			z:email_message="Bitte geben sie eine gžltige E-Mail Adresse"></td>

		<td><label for="Fax">Fax<br>
		<input type="text" size="25" maxlength="30" id="Fax" name="Fax"
			tabindex="7"></label></td>
	</tr>
	<tr>
		<td>&nbsp;&nbsp;&nbsp;<label for="Telefon">Telefonnummer
		*</label><br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			id="Telefon" name="Telefon" tabindex="6" z:required="true"
			z:message="Bitte Telefonnummer angeben"></td>
	</tr>
	<tr>
		<td>&nbsp;&nbsp;&nbsp;<label for="Telefon">Telefonnummer
		(mobil)</label><br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			id="Handy" name="Handy" tabindex="7" z:required="true"
			z:message="Bitte Handynummer angeben"></td>
	</tr>
	<tr>
		<td>&nbsp;&nbsp;&nbsp;<label for="Telefon">Telefonnummer
		(Sekret&auml;rin) *</label><br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			id="TelSek" name="TelSek" tabindex="7" z:required="true"
			z:message="Bitte Telefonnummer der Sekret&auml;rin angeben"></td>
	</tr>
</table>
</fieldset>
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

&nbsp;Felder mit '*' sind Pflichtfelder und m&uuml;ssen ausgef&uuml;llt
werden.</form>
<table>
	<tr>
		<td><input type="submit" name="anlegen" value="Anlegen"
			tabindex="11">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="submit" name="abbrechen"
			value="   Zur&uuml;ck   " tabindex="12"></td>
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
