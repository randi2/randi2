<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.beans.AktivierungBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.STUDIENLISTE.toString());
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
<script language="JavaScript">

</script>
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Studien-Liste</h1>

<table width="100%">
	<tr class="tblrow1" align="left">
		<th width="10%">Name</th>
		<th width="10%">Beschreibung</th>
		<th width="10%">Startdatum</th>
		<th width="10%">Enddatum</th>
		<th width="20%">Studienprotokoll</th>
	</tr>

	<tr class="tblrow2" align="left">
		<td>Studie1</td>
		<td>woass koane</td>
		<td>12.12.2012</td>
		<td>12.12.12012</td>
		<td>-</td>
	</tr>
</table>

<form><br>
<select NAME="list" size="1">

	<option value="studie_ansehen.jsp">Studie Ansehen</option>
	<option value="patient_hinzufuegen.jsp">Patient
	hinzuf&uuml;gen</option>
	<option value="ergebnisse.jsp">Randomisation Ergebnisse</option>
</select>&nbsp;&nbsp;&nbsp;<input TYPE="button" VALUE="und los"
	onClick="top.location.href=this.form.list.options[this.form.list.selectedIndex].value">
</form>

<%@include file="include/inc_footer.jsp"%></div>
<div id="show_none"></div>
<div id="show_none"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
