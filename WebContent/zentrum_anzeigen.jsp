<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.controller.DispatcherServlet"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.*"%>


<%
			StudieBean aSession = (StudieBean) request.getSession()
			.getAttribute(
			DispatcherServlet.sessionParameter.AKTUELLE_STUDIE
			.name());
	Vector<ZentrumBean> zugehoerigeZentren = aSession.getZentren();
	ZentrumBean zb = new ZentrumBean();
	zb.setIstAktiviert(true);
	zb.setFilter(true);

	Vector<ZentrumBean> zentrenliste = Zentrum.suchenZentrum(zb);
	
	for (int y = 0; y < zentrenliste.size(); y++) {
			for (int x = 0; x < zugehoerigeZentren.size(); x++) {
			if (zentrenliste.elementAt(y).equals(
			zugehoerigeZentren.elementAt(x))) {
		zentrenliste.removeElementAt(y);
			}
		}
	}
	
%>

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
		<td><%--if (aBenutzer.getBenutzername().equals("sl")) {	--%> <a
			href="zentrum_anzeigen_sl.jsp"> <input type="submit"
			name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> <a
			href="zentrum_studie_zuordnen.jsp"> <input type="submit"
			name="studie_hinzufuegen" value="Zu Studie hinzuf&uuml;gen"></a>
		<%--  } else if (aBenutzer.getBenutzername().equals("admin")) {
 					--%> <a href="zentrum_anzeigen_admin.jsp"> <input
			type="submit" name="zentrum_auswaehlen" value="Zentrum anzeigen"></a>
		<%-- } --%></td>

	</tr>
	<tr class="tblrow1">
		<td>Zentrum2</td>
		<td>Abteilung2</td>
		<td>inaktiv</td>

		<td><%--
				if (aBenutzer.getBenutzername().equals("sl")) {
				--%> <a href="zentrum_anzeigen_sl.jsp"> <input type="submit"
			name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> <%--
 } else if (aBenutzer.getBenutzername().equals("admin")) {
 					--%> <a href="zentrum_anzeigen_admin.jsp"> <input
			type="submit" name="zentrum_auswaehlen" value="Zentrum anzeigen"></a>

		<%--
 				}
					 --%>
		<td>
	</tr>
	<tr class="tblrow2">
		<td>Zentrum2</td>
		<td>Abteilung87</td>
		<td>aktiv</td>

		<td><%--
				if (aBenutzer.getBenutzername().equals("sl")) {
				--%> <a href="zentrum_anzeigen_sl.jsp"> <input type="submit"
			name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> <%--
 } else if (aBenutzer.getBenutzername().equals("admin")) {
 					--%> <a href="zentrum_anzeigen_admin.jsp"> <input
			type="submit" name="zentrum_auswaehlen" value="Zentrum anzeigen"></a>

		<%--
 					}
					 --%></td>
	</tr>

	<tr>
		<td><input type="button" name="zurueck" value="Zur&uuml;ck"
			tabindex="1" onclick="location.href='studie_ansehen.jsp'"></td>
	</tr>
</table>
<table width="90%" border="0" cellspacing="5" cellpadding="2">
	<thead align="left">
		<tr style="background:#eeeeee;" class="tblrow1">
			<th width="35%">Name der Institution</th>
			<th width="35%">Abteilung</th>
			<th width="10%">Status</th>
			<th width="20%">Aktion</th>
		</tr>
	</thead>

	<%
		String reihe = "tblrow1";
		boolean schalter = true;
		String aktiv = "aktiv";
		int anzahlZentren = aSession.getAnzahlZentren();
		int i = 0;

		if (!zugehoerigeZentren.isEmpty()) {
			while (i < anzahlZentren) {
				if (zugehoerigeZentren.elementAt(i).getIstAktiviert()) {
			aktiv = "aktiv";
				} else {
			aktiv = "inaktiv";
				}
	%>
	<tr class=<%=reihe %>>
		<td><%=zugehoerigeZentren.elementAt(i).getInstitution() %></td>
		<td><%=zugehoerigeZentren.elementAt(i).getAbteilung() %></td>
		<td align=center><%=aktiv %></td>
		<td> <%=aBenutzer.getRolle()%><br><%
		if (aBenutzer.getRolle().equals("STUDIENLEITER")) {
		%> <a href="zentrum_anzeigen_sl.jsp"> <input type="submit" name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> 
		<a href="zentrum_studie_zuordnen.jsp"> <input type="submit"	name="studie_hinzufuegen" value="Zu Studie hinzuf&uuml;gen"></a>
		<%
		} else if (aBenutzer.getRolle().equals("ADMINISTRATOR")) {
		%> <a href="zentrum_anzeigen_admin.jsp"> <input type="submit"
			name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> <%
 }
 %></td>
	</tr>

	<%
			if (schalter) {
			schalter = false;
			reihe = "tblrow2";
				} else {

			schalter = true;
			reihe = "tblrow1";
				}
				i++;
			}
		}
		i = 0;
		if (!zentrenliste.isEmpty()) {
			while (i < zentrenliste.size()) {
				if (zentrenliste.elementAt(i).getIstAktiviert()) {
			aktiv = "aktiv";
				} else {
			aktiv = "inaktiv";
				}
	%>
	<tr class=<%=reihe %>>
		<td><%=zentrenliste.elementAt(i).getInstitution()%></td>
		<td><%=zentrenliste.elementAt(i).getAbteilung()%></td>
		<td><%=aktiv%></td>
		<td>
		<%
		if (aBenutzer.getRolle().equals("STUDIENLEITER")) {
		%> <a
			href="zentrum_anzeigen_sl.jsp"> <input type="submit"
			name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> <a
			href="zentrum_studie_zuordnen.jsp"> <input type="submit"
			name="studie_hinzufuegen" value="Zu Studie hinzuf&uuml;gen"></a>
		<%
		} else if (aBenutzer.getRolle().equals("ADMINISTRATOR")) {
		%> <a href="zentrum_anzeigen_admin.jsp"> <input type="submit"
			name="zentrum_auswaehlen" value="Zentrum anzeigen"></a> <%
 }
 %>
		</td>
	</tr>

	<%
			if (schalter) {
			schalter = false;
			reihe = "tblrow2";
				} else {

			schalter = true;
			reihe = "tblrow1";
				}
				i++;
			}
		}
	%>

</table>

<div id="show_none"><%@include file="include/inc_footer.jsp"%></div>


</form>
</div>

<div id="show_BV"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
