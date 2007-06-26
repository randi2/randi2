<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.beans.AktivierungBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.PATIENT_HINZUFUEGEN.toString());
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
<h1>Patient hinzuf&uuml;gen</h1>
<br>
<form>
<fieldset style="width:60%"><legend><b>Patienteneigenschaften</b></legend>
<table>
	<tr>
		<td><br>
		Initialen *&nbsp;&nbsp;&nbsp;<br>
		<input type="text" size="4" maxlength="10" name="Initialen"
			tabindex="1">&nbsp;(Albrecht D&uuml;rer: A.D.)</td>
	</tr>
	<tr>
		<td>Geburtsdatum *<br>
		<input type="text" size="25" maxlength="30" name="Geburtsdatum"
			tabindex="2" value="14.03.1984"></td>
		<td><br>
		&nbsp;&nbsp;&nbsp;Geschlecht *<br>
		<input type="radio" name="geschlecht" value="weiblich" tabindex="3">Weiblich
		<input type="radio" name="geschlecht" value="maennlich" tabindex="4">M&auml;nnlich<br>
		<br>
		</td>
	</tr>
	<tr>
		<td>Datum der Patientenaufkl&auml;rung *<br>
		<input type="text" size="25" maxlength="30" name="DatumAufklaerung"
			tabindex="5" value="20.12.1999"></td>
	</tr>
	<tr>
		<td>K&ouml;rperoberfl&auml;che *<br>
		<input type="text" size="4" maxlength="30" name="Koerperoberflaeche"
			tabindex="6">&nbsp;m&sup2;</td>
		<td><br>
		&nbsp;&nbsp;&nbsp;Performancestatus *<br>
		&nbsp;&nbsp;&nbsp; <select name="Standardauswahl" tabindex="7">
			<option value="eins">1</option>
			<option value="zwei">2</option>
			<option value="drei">3</option>
			<option value="vier">4</option>
		</select><br>
		<br>
	</tr>
</table>
</fieldset>
<br>
&nbsp;Felder mit '*' sind Pflichtfelder und m&uuml;ssen ausgef&uuml;llt
werden.</form>
<table>
	<tr>
		<td><input type=submit name="bestaetigen" value="Best&auml;tigen"
			tabindex="8" onclick="location.href='studie_ansehen.jsp'">&nbsp;&nbsp;&nbsp;</td>
		<td><input type=submit name="abbrechen" value="Abbrechen"
			tabindex="9" onclick="location.href='studie_ansehen.jsp'"></td>
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
