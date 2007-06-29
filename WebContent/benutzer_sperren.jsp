<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.beans.AktivierungBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.*"%>

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
<form>
<%@include file="include/inc_header.jsp"%>

<%BenutzerkontoBean benutzer=(BenutzerkontoBean)session.getAttribute(DispatcherServlet.sessionParameter.BENUTZER_SPERREN_ENTSPERREN_ADMIN.toString()); %>
<form action="DispatcherServlet" method="POST" name="sperren_form" id="sperren_form">
<div id="content">
<%@include file="include/inc_nachricht.jsp" %>
<h1><%if(!benutzer.isGesperrt()){out.print("Benutzer sperren");}else{out.print("Benutzer entsperren");} %></h1>

<input type="hidden" name="<%=Parameter.anfrage_id %>" value="<%=DispatcherServlet.anfrage_id.JSP_BENUTZER_SPERREN_SPERREN_ENTSPERREN.name() %>" />
<fieldset style="width:60%"><legend><b>System</b></legend>
<table>
	<tr>
		<td>Wollen sie den gewählten Benutzer (Benutzername: <%= benutzer.getBenutzername()%>) wirklich <%if(!benutzer.isGesperrt()){out.print("sperren");}else{out.print("entsperren");} %>?</td>
	</tr>
	<tr>
		<td><br>
		Geben Sie hier bitte Ihren Grund an</td>
	</tr>
	<tr>
		<td><input type="text" name="nachricht" width="300"></td>
	</tr>
	<tr>
		<td><br>
				<td><input type="submit" name="sperrenEntsperren" value="<%if(!benutzer.isGesperrt()){out.print("Benutzer sperren");}else{out.print("Benutzer entsperren");} %>"
			tabindex="12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="submit" name="sperrenAbrechen" value="<%if(!benutzer.isGesperrt()){out.print("Benutzer sperren abbrechen");}else{out.print("Benutzer entsperren abbrechen");} %>" tabindex="2"></td>
	</tr>
</table>

</fieldset>
<br>
</div>
</form>
<div id="show_none"></div>

<%@include file="include/inc_footer.jsp"%>
<div id="show_none"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
