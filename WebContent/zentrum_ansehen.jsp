<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.beans.AktivierungBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.ZENTRUM_ANSEHEN.toString());
%>
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
<h1>Zentrum anzeigen</h1>
<form>
<fieldset style="width: 60%"><legend><b>Angaben
zum Zentrum</b></legend>
<table>
	<tr>
		<td>Name der Institution *<br>
		<input type="text" size="49" maxlength="40" name="Name_Institution"
			tabindex="1" value="$Name der Institution"></td>
	</tr>
	<tr>
		<td>Name der genauen Abteilung *<br>
		<input type="text" size="49" maxlength="40" name="Zentrum_Abteilung"
			tabindex="2" value="$Name der Abteilung"></td>
	</tr>
	<tr>
		<td>Ort *<br>
		<input type="text" size="40" maxlength="40" name="Ort" tabindex="3"
			value="$Ort"></td>
		<td>PLZ *<br>
		<input type="text" size="6" maxlength="6" name="PLZ" tabindex="4"
			value="$PLZ"></td>
	</tr>
	<tr>
		<td>Strasse * <br>
		<input type="text" size="40" maxlength="40" name="strasse"
			tabindex="5" value="$Strasse"></td>
		<td>Hausnummer *<br>
		<input type="text" size"10" maxlength="11" name="hausnummer"
			tabindex="6" value="$Hausnummer"></td>
	</tr>
	<tr>
		<td>Passwort * <br>
		<input type="password" size"20" maxlength="20" name="hausnummer"
			tabindex="7" value=""></td>
	</tr>
</table>
</fieldset>
<br>
<fieldset style="width: 60%"><legend><b>Angaben
zum Ansprechpartner</b></legend>
<table>
	<tr>
		<td>Nachname *<br>
		<input type="text" size"40" maxlength="40" name="nachname"
			tabindex="8" value="$Nachname"></td>
		<td>Vorname *<br>
		<input type="text" size"40" maxlength="40" name="vorname" tabindex="9"
			value="$Vorname"></td>
	</tr>
	<tr>
		<td>Telefon *<br>
		<input type="text" size"40" maxlength="40" name="telefon"
			tabindex="10" value="$Telefonnummer"></td>
	</tr>
	<tr>
		<td>Fax<br>
		<input type="text" size"40" maxlength="40" name="fax" tabindex="11"
			value="$Fax"></td>
	</tr>
	<tr>
		<td>Email *<br>
		<input type="text" size"40" maxlength="40" name="email" tabindex="12"
			value="$Email"></td>
	</tr>

</table>
</fieldset>
</form>
<table align="left">
	<tr>

		<td><a href="zentrum_anzeigen.jsp"><input type="button"
			name="zurueck" value="zur&uuml;ck" tabindex="13"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
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