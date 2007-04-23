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
<title>Studienarm</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1> Studienarm beenden</h1>
<table  width="90%">
<tr class="tblrow1"  align="left">
	<th width="30%"> Bezeichnung des Arms </th>
	<th width="30%"> Beschreibung des Arms </th>
	<th width="30%"> Anzahl der Patienten </th>

</tr>
<tr class="tblrow2">
	<td>Ulna</td>
	<td> Elle </td>
	<td> 2 </td>
	<td> <a class="il_ContainerItemCommand" href="">beenden</a></td>

</tr>
<tr class="tblrow1">
	<td> Radius </td>
	<td> Speiche </td>
	<td> 2 </td>
		<td> <a class="il_ContainerItemCommand" href="">beenden</a></td>
	
</tr>
<tr class="tblrow2">
	<td> Clavia </td>
	<td> Schl&uuml;sselbein </td>
	<td> 2 </td>
	<td> <a class="il_ContainerItemCommand" href="">beenden</a></td>
	
</tr>
</table><br><br>
<table align="left">
	<tr>
		<td><input type="button" name="entsp_ja" value="Zur&uuml;ck" tabindex="1" onclick="location.href='studie_ansehen.jsp'"></td>
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

