<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	
	import= "de.randi2.model.fachklassen.*" 
	import= "de.randi2.model.fachklassen.beans.*"
	import= "java.util.GregorianCalendar"
	import= "java.text.SimpleDateFormat" 
	import= "java.util.Locale"
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="de.randi2.controller.listener.SessionListener"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="refresh" content="30; URL=systemadministration.jsp">
<title>RANDI2 :: Systemverwaltung</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Systemadministration</h1>
<p>
Anzahl der im System angemeldeten Personen insgesamt: <%= SessionListener.getCounter() %>

</p>
<!-- Footer -->
<%@include file="include/inc_footer.jsp"%>
</div>
<div id="show_none">
<%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
