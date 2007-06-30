<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.STUDIE_STARTEN.toString());
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
<form action="DispatcherServlet" method="post" name="bestaetigung" id="bestaetigung"><input
	type="hidden" name="<%=Parameter.anfrage_id %>"
	value="<%=StudieServlet.anfrage_id.JSP_STUDIE_STARTEN_JA.toString() %>">
<h1>Studie starten</h1>
<fieldset style="width:60%"><legend><b>Studie</b></legend>
<table>
	<tr>
		<td>Sind Sie sicher, dass Sie die Studie starten wollen?</td>
	</tr>
	<tr>
		<td><input type="submit" name="entsp_ja" value="Ja" tabindex="1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" name="entsp_nein"
			value="Nein" tabindex="2"
			onClick="document.forms['bestaetigung'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_STUDIE_ANSEHEN%>';document.forms['bestaetigung'].submit();">&nbsp;&nbsp;</td>
	</tr>
</table>

</fieldset>
<br>
</form>
<%@include file="include/inc_footer.jsp"%>
</div>
<%@include file="include/inc_menue.jsp"%>
</body>
</html>
