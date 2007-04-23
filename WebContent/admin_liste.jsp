<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import= "de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import= "java.util.GregorianCalendar"
	import= "java.text.SimpleDateFormat" 
	import= "java.util.Locale"
%>
<jsp:setProperty name="user" property="*" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Benutzerverwaltung</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1> Benutzer-Liste </h1>
<table width="600" border="0" cellspacing="5" cellpadding="2" bgcolor="#e3e3e3">
<tr>  
<td align="left" id="filterbezeichnung">Vorname</td>
<td align="left" style="width: 200px"><input type="text" name="name" id="filterfeld"></td>
<td align="left" id="filterbezeichnung">Nachname</td>
<td align="left" style="width: 200px"><input type="text" name="vorname" id="filterfeld"></td>
</tr>
<tr>
<td align="left" id="filterbezeichnung">Login-Name</td>
<td align="left" style="width: 200px"><input type="text" name="loginname" id="filterfeld"></td>
<td align="left" id="filterbezeichnung">E-Mail</td> 
<td align="left" style="width: 200px"><input type="text" name="email" id="filterfeld"></td>
</tr>
<tr>
<td align="left" id="filterbezeichnung">Institut</td>
<td align="left" style="width: 200px"><input type="text" name="Institut" id="filterfeld"></td>
<td align="right" colspan="4"><input type="submit" value="Aktualisieren" style="width: 100px"></td>
</tr>
</table>
<p></p>
<table  width="90%">
<tr class="tblrow1"  align="left">
	<th width="10%"> Nachname </th>
	<th width="10%"> Vorname </th>
	<th width="10%"> Loginname </th>
	<th width="30%"> Institut </th>
	<th width="20%"> E-Mail </th>
	<th width="20%"> Aktionen </th>
</tr>
<tr class="tblrow2">
	<td>M&uuml;ller</td>
	<td> Chriz </td>
	<td> cmueller </td>
	<td> Institut </td>
	<td> blablabla@web.de </td>
<td> <a class="il_ContainerItemCommand" href="admin_anzeigen.jsp">anzeigen</a><a class="il_ContainerItemCommand" href="benutzer_sperren.jsp">sperren</a><a class="il_ContainerItemCommand" href="">l&ouml;schen</a></td>
</tr>
<tr class="tblrow1">
	<td> Berger </td>
	<td> Peter </td>
	<td> pberger </td>
	<td> Institut </td>
	<td> blablabla@freenet.de </td>
<td> <a class="il_ContainerItemCommand" href="admin_anzeigen.jsp">anzeigen</a><a class="il_ContainerItemCommand" href="benutzer_sperren.jsp">sperren</a><a class="il_ContainerItemCommand" href="">l&ouml;schen</a></td>
</tr>
<tr class="tblrow2">
	<td> Yildiz </td>
	<td> Bahty </td>
	<td> byildiz </td>
	<td> Institut </td>
	<td> blablabla@gmx.de </td>
<td> <a class="il_ContainerItemCommand" href="admin_anzeigen.jsp">anzeigen</a><a class="il_ContainerItemCommand" href="benutzer_sperren.jsp">sperren</a><a class="il_ContainerItemCommand" href="">l&ouml;schen</a></td>
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