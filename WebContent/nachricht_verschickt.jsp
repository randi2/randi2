<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="de.randi2.model.fachklassen.beans.*"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.controller.Nachrichtendienst"
	import="de.randi2.controller.DispatcherServlet"
	import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.NACHRICHT_VERSCHICKT.toString());
%>
<%
			PersonBean aPerson = ((BenutzerkontoBean) session
			.getAttribute("aBenutzer")).getBenutzer();
	// Feldervorbelegungen
	String betreff = (String) request
			.getAttribute(Nachrichtendienst.requestParameter.BETREFF
			.name());
	String nachrichtentext = (String) request
			.getAttribute(Nachrichtendienst.requestParameter.NACHRICHTENTEXT
			.name());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Nachrichtendienst</h1>
<form>
<fieldset><legend><b>Mitteilung verschickt</b></legend>
<table border="0">
	<tr>
		<td><b>Absender:</b>&nbsp;<%=aPerson.getVorname()%>&nbsp;<%=aPerson.getNachname()%>
		(<%=aPerson.getEmail()%>)</td>
	</tr>
	<tr>
		<td><b>Empf&auml;nger:</b><br>
		fgdsf gdfg rr r</td>
	</tr>
	<tr>
		<td><b>Betreff:</b><br>
		<%=betreff%></td>
	</tr>
	<tr>
		<td><label for="text"><b>Nachrichtentext:</b></label><br>
		<%=nachrichtentext%></td>
	</tr>
</table>
</fieldset>
</form>
<br>
<%@include file="include/inc_footer.jsp"%></div>
<div id="show_none"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
