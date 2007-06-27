<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.beans.AktivierungBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.STUDIENVERWALTUNG.toString());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<%@include file="include/inc_extjs.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Studienverwaltung</h1>
<p id="bodytext">SAMPLE</p>
<p id="bodytext">SAMPLE</p>
<p id="bodytext">SAMPLE</p>
<p id="bodytext">SAMPLE</p>
<p id="bodytext">SAMPLE</p>
<p id="bodytext">SAMPLE</p>
<p id="bodytext">SAMPLE</p>
<p id="bodytext">SAMPLE</p>
<p id="bodytext">SAMPLE</p>
<p id="bodytext">SAMPLE</p>
<p id="bodytext"><%@include file="include/inc_footer.jsp"%>
</div>
<div id="show_SV"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
