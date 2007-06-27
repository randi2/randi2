<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.NACH_AKTIVIERUNGSLINK_FEHLER
			.toString());
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<%@include file="include/inc_extjs.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>

</head>
<body>
<%@include file="include/inc_header_clean.jsp"%>

<div id="content">
<h1>Der Aktivierungslink ist falsch, bereits aktiviert oder zu alt.</h1>

<br>
<%@include file="include/inc_footer_clean.jsp"%></div>
</body>
</html>
