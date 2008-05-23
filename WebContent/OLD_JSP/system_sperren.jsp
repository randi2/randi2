<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.SYSTEM_SPERREN.toString());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="de.randi2.controller.DispatcherServlet;"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RANDI2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<jsp:forward page="/DispatcherServlet">
	<jsp:param name="anfrage_id"
		value="<%=DispatcherServlet.anfrage_id.JSP_SYSTEM_SPERREN %>" />
</jsp:forward>
</body>
</html>
