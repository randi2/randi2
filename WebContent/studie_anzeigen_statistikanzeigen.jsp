<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ page import= "de.randi2.model.fachklassen.beans.*"
	import= "java.util.GregorianCalendar"
	import= "java.text.SimpleDateFormat" 
	import= "java.util.Locale"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Template</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>

<div id="content">
<h1>Auswertungsmethoden zu einer Statistik</h1>
<form action="statistik_anzeigen.jsp" method="POST">
<fieldset>
	<legend><b>Studienangaben</b></legend>
		<table>
		<tr>
		<td><br>Anzahl von weiblichen und männlichen Patienten in Bezug auf alle Patienten</td>
		<td></td>
		<td><br><input type="checkbox" name="EineCheckBox1" tabindex="1"> </td>
		</tr>
		<tr>
		<td><br>Anzeige von durchschnittlichen Lebensalter bzw. Verteilung des Alters</td>
		<td></td>
		<td><br><input type="checkbox" name="EineCheckBox2" tabindex="2"> </td>
		</tr>
		<tr>
		<td><br>Anzahl der Patienten in Studienarme</td>
		<td></td>
		<td><br><input type="checkbox" name="EineCheckBox3" tabindex="3"> </td>
		</tr>
		<tr>
		<td><br>Kombination der verschiedenen Statistiken</td>
		<td></td>
		<td><br><input type="checkbox" name="EineCheckBox4" tabindex="4"> </td>
		</tr>
		<tr>
		<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
		<td><br><input type="submit" name="auswertungsmethode_waehlen" value="Best&auml;tigen" onClick="" tabindex="5"></td>
		</tr>
		<tr>
		
		</tr>
		</table>
</fieldset><br><br>
</form>

<table align="left">
	<tr>
		<td><input type="button" name="entsp_ja" value="Zur&uuml;ck" tabindex="1" onclick="location.href='studie_ansehen.jsp'"></td>
	</tr>
</table>


<%@include file="include/inc_footer.jsp"%>
</div>
<div id="show_none">
<%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
