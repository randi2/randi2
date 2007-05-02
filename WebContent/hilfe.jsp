<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ page import= "de.randi2.model.fachklassen.beans.*"
	import= "java.util.GregorianCalendar"
	import= "java.text.SimpleDateFormat" 
	import= "java.util.Locale"
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: Hilfe</title>
</head>
<body>
<%@include file="include/inc_header.jsp"%>

<div id="content">
<form>
<h1>Hilfe</h1>
	<p id="bodytext">
		Hier steht dann später die Hilfe!!!
	</p>
	<p id="bodytext">
		<table align="left">
		<tr>
		<td><input type="button" name="zurueck" value="zur&uuml;ck" tabindex="1" onClick="javascript:history.back()"></td>
		</tr>
		</table>
		<%@include file="include/inc_footer.jsp"%>
</form>

	</div>
	
	<div id="show_none">		
	</div>


<div id="show_none">
</div>
</body>
</html>
