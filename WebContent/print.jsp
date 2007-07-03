<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page 
	import="de.randi2.utility.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="de.randi2.controller.DispatcherServlet"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Drucken</title>
</head>
<body onLoad="window.print()" bgcolor="white">
<img src="images/randilogo.png">
<br><br>
<p style="font-family: arial, sans-serif;font-size: 12px;">
<%=request.getSession().getAttribute(DispatcherServlet.sessionParameter.PRINT_NACHRICHT.toString()) %>
</p>
<br><br>
<center><input type="submit" value="Schliessen" onClick="window.close()"></center>
</html>