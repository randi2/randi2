<%--

		TODO

--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="de.randi2.utility.*"
	import="de.randi2.controller.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.STUDIENUEBERBLICK.toString());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>RANDI2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
</head>
<body>

</body>
</html>
