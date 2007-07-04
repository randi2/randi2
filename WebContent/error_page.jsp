<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="org.apache.log4j.Logger" import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.ERROR_PAGE.toString());
%>

<%--import= "de.randi2.model.fachklassen.beans.*" --%>

<%@ page isErrorPage="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%--@page import="java.io.StringWriter"--%>
<%@page import="java.io.PrintWriter"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<%@include file="include/inc_extjs.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header_clean.jsp"%>

<div id="content">

<h1>Fehler</h1>
<br>
In der Applikation ist ein Fehler aufgetreten. Wenden Sie sich bitte an
den Administrator.
<h2>Stacktrace, wird spaeter entfernt.</h2>
<%
	exception.printStackTrace(new PrintWriter(out));
	exception.printStackTrace();
%> <%@include file="include/inc_footer.jsp"%></div>
<div id="show_none"></div>
<div id="show_none"></div>
</body>
</html>
