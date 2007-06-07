<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.beans.AktivierungBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.Parameter"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="de.randi2.controller.ZentrumServlet"%>
<%@page import="de.randi2.controller.DispatcherServlet"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: Neues Zentrum anlegen</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Neues Zentrum anlegen</h1>
<form action="DispatcherServlet" method="POST" name="zentrum_anlegen">
<input type="hidden"
	value="<%=DispatcherServlet.anfrage_id.JSP_ZENTRUM_ANLEGEN %>"
	name="<%=Parameter.anfrage_id %>">
<fieldset style="width: 60%"><legend><b>Angaben zum Zentrum</b></legend>
<table>
	<tr>
		<td>Name der Institution *<br>
		<input type="text" size="46" maxlength="40" name="<%=Parameter.zentrum.INSTITUTION %>"
			tabindex="1"></td>

	</tr>
	<tr>
		<td>Name der genauen Abteilung *<br>
		<input type="text" size="46" maxlength="40" name="<%=Parameter.zentrum.ABTEILUNGSNAME %>"
			tabindex="2"></td>

	</tr>
	<tr>
		<td>Strasse *
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hausnr
		* <br>
		<input type="text" size="30" maxlength="30" name=<%=Parameter.zentrum.STRASSE %>
			tabindex="3" value=""> &nbsp;&nbsp;&nbsp; <input type="text"
			size="8" maxlength="8" name="<%=Parameter.zentrum.HAUSNUMMER %>" tabindex="4" value=""></td>

	</tr>
	<tr>
		<td>PLZ * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ort * <br>
		<input type="text" size="6" maxlength="6" name="<%=Parameter.zentrum.ORT%>" tabindex="5"
			value="">&nbsp;&nbsp;&nbsp; <input type="text" size="33"
			maxlength="33" name="<%=Parameter.zentrum.ORT %>" tabindex="6" value=""></td>

	</tr>

</table>
</fieldset>
<br>
<fieldset><legend><b>Angaben zum Ansprechpartner</b></legend>
<table>
	<tr>
		<td>Vorname *<br>
		<input type="text" size"38" maxlength="40" name="<%=Parameter.person.VORNAME %>" tabindex="7"
			value="">&nbsp;&nbsp;&nbsp;</td>
		<td>Nachname *<br>
		<input type="text" size="38" maxlength="40" name="<%=Parameter.person.NACHNAME %>"
			tabindex="8" value=""></td>
	</tr>
	<tr>
		<td>Telefon *<br>
		<input type="text" size="40" maxlength="40" name="<%=Parameter.person.TELEFONNUMMER %>" tabindex="9"
			value=""></td>
	</tr>
	<tr>
		<td>Fax<br>
		<input type="text" size="40" maxlength="40" name="<%=Parameter.person.FAX %>" tabindex="10"
			value=""></td>
	</tr>
	<tr>
		<td>Email *<br>
		<input type="text" size="46" maxlength="50" name="<%=Parameter.person.EMAIL %>" tabindex="11"
			value="">
			<input type="submit" name="Submit"
			value="Zentrum anlegen" tabindex="12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>
</table>
</fieldset>
<br>

&nbsp;Felder mit '*' sind Pflichtfelder und m&uuml;ssen ausgef&uuml;llt
werden.</form>
<table>

	<tr>
		<td><input type="button" name="passwort_anfordern"
			value="Passwort anfordern" tabindex="12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="button" name="abbrechen" value="Abbrechen"
			tabindex="13"></td>
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
