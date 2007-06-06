<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="de.randi2.model.fachklassen.beans.*"%>
<%
			Rolle.Rollen aRolle = ((BenutzerkontoBean) request.getSession()
			.getAttribute("aBenutzer")).getRolle().getRollenname();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: Studie ausw&auml;hlen</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<script language="Javascript" src="js/motionpack.js"> </script>
</head>
<body onload="toggleSlide('filterdiv');">
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Studie ausw&auml;hlen</h1>

<%
if (aRolle == Rolle.Rollen.STUDIENLEITER) {
%>
<form action="DispatcherServlet" method="POST"><input
	type="hidden" name="anfrage_id"
	value="JSP_STUDIE_AUSWAEHLEN_NEUESTUDIE"><input type="submit"
	value="Neue Studie anlegen"></form>
&nbsp;&nbsp;&nbsp;::&nbsp;&nbsp;&nbsp;
<form action="DispatcherServlet" method="POST"><input
	type="hidden" name="anfrage_id"
	value="JSP_STUDIE_AUSWAEHLEN_SIMULATION"><input type="submit"
	value="Simulation"></form>
<br>
<br>
<%
}
%> 
<img alt="Filter anzeigen" src="images/find.png" onmousedown="toggleSlide('filterdiv');" title="Filter anzeigen" style="cursor:pointer"/>
Filter

<!--  TODO Table  BUG #2-->
<div id="filterdiv" style="overflow:hidden; height: 100px;">
<table width="600" border="0" cellspacing="5" cellpadding="2"
	bgcolor="#e3e3e3">
	<tr>
		<td align="left" id="filterbezeichnung">Name</td>
		<td align="left" style="width: 200px"><input type="text"
			name="name" id="filterfeld"></td>
		<td align="left" id="filterbezeichnung">Status</td>
		<td align="left" style="width: 200px"><select size="1"
			id="filterfeld">
			<option>gestartet</option>
			<option>aktiv</option>
			<option>pausiert</option>
			<option>abgeschlossen</option>
		</select></td>
	</tr>
	<tr>
		<td align="left" id="filterbezeichnung">Zentrum</td>
		<td align="left" colspan="3"><select size="1" id="filterfeld">
			<option>Uniklinikum Heidelberg, Hygieneinstitut</option>
		</select></td>
	</tr>
	<tr>
		<td align="right" colspan="4"><input type="submit"
			value="Aktualisieren" style="width: 100px"></td>
	</tr>
</table>
</div>
<br>
<br>
<table width="600" cellspacing="0" cellpadding="0">
	<tr class="tblrow1" align="center">
		<th width="30%">Name</th>
		<th width="60%">Leitendes Zentrum</th>
		<th width="10%">Status</th>
	</tr>
	<tr class="tblrow2">
		<td align="center"><a href="studie_ansehen.jsp">Aspirin</a></td>
		<td align="center">Von Hand eingetragen I</td>
		<td align="center">aktiv</td>
		<%
		if (aRolle == Rolle.Rollen.ADMIN) {
		%>
		<td><a class="il_ContainerItemCommand"
			href="studie_abbrechen.jsp">abbrechen</a></td>
		<%
		}
		%>
	</tr>
	<tr class="tblrow1">
		<td align="center">GemTex5</td>
		<td align="center">Von Hand eingetragen II</td>
		<td align="center">aktiv</td>
		<%
		if (aRolle == Rolle.Rollen.ADMIN) {
		%>
		<td><a class="il_ContainerItemCommand"
			href="studie_abbrechen.jsp">abbrechen</a></td>
		<%
		}
		%>
	</tr>
	<tr class="tblrow2">
		<td align="center">XXX</td>
		<td align="center">Von Hand eingetragen III</td>
		<td align="center">aktiv</td>
		<%
		if (aRolle == Rolle.Rollen.ADMIN) {
		%>
		<td><a class="il_ContainerItemCommand"
			href="studie_abbrechen.jsp">abbrechen</a></td>
		<%
		}
		%>
	</tr>
</table>

<%@include file="include/inc_footer.jsp"%></div>
<%
if (aRolle == Rolle.Rollen.ADMIN) {
%>
<%@include file="/include/inc_menue.jsp"%>
<%
}
%>
</body>
</html>
