<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.beans.AktivierungBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.STUDIENAERZTE_ANZEIGEN.toString());
%>
<jsp:setProperty name="user" property="*" />
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

<div id="content">
<form>
<h1>Studien&auml;rzte anzeigen</h1>
<fieldset style="width: 60%"><legend><b>Pers&ouml;nliche
Angaben</b></legend>
<table height="244">
	<tr>
		<td>Titel<br>
		<select name="Standardauswahl" tabindex="1">
			<option value="Dr.">Dr.</option>
			<option value="Prof.">Prof.</option>
			<option value="Prof. Dr.">Prof. Dr.</option>
		</select><br>
		<br>
	</tr>
	<tr>
		<td>Vorname *<br>
		<input type="text" size="25" maxlength="30" name="Vorname"
			tabindex="2"></td>
		<td>&nbsp;&nbsp;&nbsp;Nachname *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Nachname" tabindex="3"></td>
	</tr>
	<tr>
		<td><br>
		<input type="radio" name="weiblich" tabindex="4">Weiblich <input
			type="radio" name="maennlich" tabindex="5">M&auml;nnlich<br>
		<br>
		</td>
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
		<td>E-Mail Adresse *<br>
		<input type="text" size="25" maxlength="30" name="Email" tabindex="8"></td>
		<td>&nbsp;&nbsp;&nbsp;Telefonnummer *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Telefon" tabindex="9"></td>
	</tr>
	<tr>
		<td>Fax<br>
		<input type="text" size="25" maxlength="30" name="Fax" tabindex="10"></td>
		<td>&nbsp;&nbsp;&nbsp;Institut *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Institut" tabindex="11"></td>
	</tr>
</table>
</fieldset>
<br>
&nbsp;Felder mit '*' sind Pflichtfelder und m&uuml;ssen ausgef&uuml;llt
werden.</form>
<table>
	<tr>
		<td><a href="benutzer_liste.jsp"><input type="button"
			name="zurueck" value="Zur&uuml;ck" tabindex="12"></a></td>
	</tr>
</table>
<%@include file="include/inc_footer.jsp"%></div>
<div id="show_none"></div>


<div id="show_none"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
