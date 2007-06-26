<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="de.randi2.model.fachklassen.beans.PersonBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.controller.DispatcherServlet"
	import="de.randi2.model.fachklassen.beans.StudieBean"
	import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.SYSTEM_SPERREN_MAIN.toString());

	boolean gesperrt = (Boolean) request
			.getAttribute("system_gesperrt");
	String msg_gesperrt = (String) request
			.getAttribute("mitteilung_system_gesperrt");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<script type="text/javascript" src="js/prototype.js"></script>
<script type="text/javascript" src="js/zebda.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
if (gesperrt) {
%>
<title>Randi2 :: <%=request
										.getAttribute(DispatcherServlet.requestParameter.TITEL
												.toString())%></title>
<%
} else {
%>
<title>Randi2 :: <%=request
										.getAttribute(DispatcherServlet.requestParameter.TITEL
												.toString())%></title>
<%
}
%>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<%
if (gesperrt) {
%>
<h1>System entsperren</h1>
<p>Das System ist derzeit gesperrt.</p>
<p>Folgende Begr&uuml;ndung wurde angegeben;<br>
"<%=msg_gesperrt%>"</p>
<%
} else {
%>
<h1>System sperren</h1>
<p>Das System ist derzeit nicht gesperrt.</p>
<p>Wenn Sie das System sperren m&ouml;chten, so werden sich keine
neuen Benutzer in das System einloggen k&ouml;nnen. Bereits eingeloggte
Benutzer bleiben allerdings eingeloggt!</p>
<%
}
%>
<form id="Formular" action="DispatcherServlet" method="post">
<fieldset style="width:60%">
<%
if (gesperrt) {
%> <legend>System entsperren</legend> <input type="hidden"
	name="anfrage_id"
	value="<%=DispatcherServlet.anfrage_id.AKTION_SYSTEM_ENTSPERREN.name() %>">
<input type="submit" name="bestaetigen" value="System entsperren"
	tabindex="1"> <%
 } else {
 %> <legend>System sperren</legend> <input type="hidden"
	name="anfrage_id"
	value="<%=DispatcherServlet.anfrage_id.AKTION_SYSTEM_SPERREN.name() %>">
Grund der letzten Sperrung/ Default-Einstellung:<br>
<textarea
	name="<%=DispatcherServlet.requestParameter.MITTEILUNG_SYSTEM_GESPERRT.toString() %>"
	cols="50" rows="10" z:required="true"
	z:message="Bitte den Grund der Sperrung angeben"><%=msg_gesperrt%></textarea>
<input type="submit" name="bestaetigen" value="System sperren"
	tabindex="1"> <%
 }
 %>
</fieldset>
</form>
</div>
<%@include file="include/inc_footer.jsp"%>
<div id="show_none"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
