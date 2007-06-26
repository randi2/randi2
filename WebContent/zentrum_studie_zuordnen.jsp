<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.beans.AktivierungBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.ZENTRUM_STUDIE_ZUORDNEN.toString());
%>
<jsp:setProperty name="user" property="*" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>

<div id="content">
<h1>Zentrum zur Studie hinzuf&uuml;gen</h1>
<form>
<fieldset style="width:60%"><legend><b>Zentrum</b></legend>
<table>
	<tr>
		<td>Sind Sie sicher, dass Sie das Zentrum der Studie zuordnen
		m&ouml;chten?</td>
	</tr>
	<tr>
		<td><input type="button" name="zentrum_ja" value="Ja"
			tabindex="1" onclick="location.href=''">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" name="zentrum_nein" value="Nein" tabindex="2"
			onclick="location.href='zentrum_anzeigen.jsp'"></td>
	</tr>
</table>

</fieldset>
<br>
</form>
</div>
<div id="show_none"></div>

<%@include file="include/inc_footer.jsp"%>
<div id="show_none"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
