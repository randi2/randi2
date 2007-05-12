<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.beans.AktivierungBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: Benutzerverwaltung</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>

<div id="content">
<form>
<h1>Zentrum suchen</h1>
<table width="600" border="0" cellspacing="5" cellpadding="2"
	bgcolor="#e3e3e3">
	<tr>
		<td align="left" id="filterbezeichnung">Institut</td>
		<td align="left" style="width: 200px"><input type="text"
			name="Institut" id="filterfeld"></td>
		<td align="right" colspan="4"><input type="submit"
			value="Aktualisieren" style="width: 100px"></td>
	</tr>
</table>
<br />
<br />
<table width="80%">
	<tr class="tblrow1" align="left">
		<th width="30%">Name der Institution</th>
		<th width="30%">Abteilung</th>
		<th width="20%">Status</th>
		<th>Aktion</th>
	</tr>
	<tr class="tblrow2">
		<td>Zentrum1</td>
		<td>Abteilung xyz</td>
		<td>aktiv</td>
		<td>
	
		<%--if (aBenutzer.getBenutzername().equals("sl")) {	--%> 
			<a href="zentrum_anzeigen_sl.jsp"> <input type="submit" name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> 
			<a href="zentrum_studie_zuordnen.jsp"> <input type="submit" name="studie_hinzufuegen" value="Zu Studie hinzuf?gen"></a> 
		<%--  } else if (aBenutzer.getBenutzername().equals("admin")) {
 					--%> <a href="zentrum_anzeigen_admin.jsp"> <input type="submit" name="zentrum_auswaehlen" value="Zentrum anzeigen"></a>
		<%-- } --%>
		
		</td>

	</tr>
	<tr class="tblrow1">
		<td>Zentrum2</td>
		<td>Abteilung2</td>
		<td>inaktiv</td>

		<td>
		<%--
				if (aBenutzer.getBenutzername().equals("sl")) {
				--%> <a href="zentrum_anzeigen_sl.jsp"> <input type="submit"
			name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> <%--
 } else if (aBenutzer.getBenutzername().equals("admin")) {
 					--%> <a href="zentrum_anzeigen_admin.jsp"> <input
			type="submit" name="zentrum_auswaehlen" value="Zentrum anzeigen"></a>

		<%--
 				}
					 --%>
		td>
	</tr>
	<tr class="tblrow2">
		<td>Zentrum2</td>
		<td>Abteilung87</td>
		<td>aktiv</td>

		<td>

		<form><%--
				if (aBenutzer.getBenutzername().equals("sl")) {
				--%> <a href="zentrum_anzeigen_sl.jsp"> <input type="submit"
			name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> <%--
 } else if (aBenutzer.getBenutzername().equals("admin")) {
 					--%> <a href="zentrum_anzeigen_admin.jsp"> <input
			type="submit" name="zentrum_auswaehlen" value="Zentrum anzeigen"></a>

		<%--
 					}
					 --%></form>
		</td>
	</tr>

	<tr>
		<td><input type="button" name="zurueck" value="Zur&uuml;ck"
			tabindex="1" onclick="location.href='studie_ansehen.jsp'"></td>
	</tr>
</table>

<div id="show_none"><%@include file="include/inc_footer.jsp"%></div>


</form>
</div>

<div id="show_BV"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
