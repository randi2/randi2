<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ page import= "de.randi2.model.fachklassen.beans.*"
	import= "java.util.GregorianCalendar"
	import= "java.text.SimpleDateFormat" 
	import= "java.util.Locale"
%>
<jsp:setProperty name="user" property="*" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: Benutzerverwaltung</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1> Studien&auml;rzte - Liste </h1>
<table  width="90%">
<tr class="tblrow1"  align="left">
	<th width="10%"> Nachname </th>
	<th width="10%"> Vorname </th>
	<th width="10%"> Loginname </th>
	<th width="40%"> Institut </th>
	<th width="20%"> E-Mail </th>
</tr>
<tr class="tblrow2">
	<td>M&uuml;ller</td>
	<td> Chriz </td>
	<td> cmueller </td>
	<td> Institut </td>
	<td> blablabla@web.de </td>
</tr>
<tr class="tblrow1">
	<td> Berger </td>
	<td> Peter </td>
	<td> pberger </td>
	<td> Institut </td>
	<td> blablabla@freenet.de </td>
</tr>
<tr class="tblrow2">
	<td> Yildiz </td>
	<td> Bahty </td>
	<td> byildiz </td>
	<td> Institut </td>
	<td> blablabla@gmx.de </td>
</tr>
</table><br><br>
	<table align="left">
		<tr>
		<td><input type="button" name="zurueck" value="Zur&uuml;ck" tabindex="1" onclick="location.href='studie_ansehen.jsp'"></td>
		</tr>
		</table>

	<%@include file="include/inc_footer.jsp"%>
  </div>
	<div id="show_none">
	</div>
<div id="show_none">
<%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>

