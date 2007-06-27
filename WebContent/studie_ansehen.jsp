<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.utility.*"%>
<%
		request.setAttribute(DispatcherServlet.requestParameter.TITEL
		.toString(), JspTitel.STUDIE_ANSEHEN.toString());
%>
<%
	Rolle.Rollen aRolle = ((BenutzerkontoBean) request.getSession()
	.getAttribute(
	DispatcherServlet.sessionParameter.A_Benutzer
	.toString())).getRolle().getRollenname();
	StudieBean aStudie = (StudieBean) request.getSession()
	.getAttribute(
	DispatcherServlet.sessionParameter.AKTUELLE_STUDIE
	.toString());
	Vector<StudienarmBean> aStudienarme = aStudie.getStudienarme();
	int counter = aStudienarme.size();
	SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy",
	Locale.GERMANY);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Vector"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: <%=request.getAttribute(DispatcherServlet.requestParameter.TITEL.toString())%></title>
<%@include file="include/inc_extjs.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css">

</head>


<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Studie ansehen</h1>
<fieldset>
<table style="text-align: left; width: 100%;" border="0" cellpadding="2"
	cellspacing="2">
	<tbody>
		<tr>
			<td style="width: 300px; text-align: left; vertical-align: top;">
			<table style="text-align: left; width: 100%;" border="0"
				cellpadding="2" cellspacing="2">
				<tbody>
					<tr class="tblrow3">
						<td>Studienname: &nbsp;</td>
					</tr>
					<tr class="tblrow1">
						<td>
						<h3><%=aStudie.getName()%></h3>
						</td>
					</tr>
				</tbody>
			</table>
			<br>
			</td>
			<td>
			<table style="text-align: left; width: 100%; height: 100%;"
				border="0" cellpadding="2" cellspacing="2">
				<tbody>
					<tr class="tblrow3">
						<td style="width: 200px; text-align: left;">Leitende
						Institution</td>
						<td style="text-align: left;">Verantwortliche(r)
						Studienleiter(in)</td>
					</tr>
					<tr class="tblrow1">
						<td style="text-align: left;"><%=aStudie.getBenutzerkonto().getZentrum().getInstitution()%></td>
						<td style="text-align: left;"><%=(aStudie.getBenutzerkonto().getBenutzer().getVorname()
							+ " " + aStudie.getBenutzerkonto().getBenutzer()
							.getNachname())%></td>
					</tr>
					<tr class="tblrow3">
						<td style="text-align: left;">Startdatum</td>
						<td style="text-align: left;">Enddatum</td>
					</tr>
					<tr class="tblrow1">
						<td style="text-align: left;"><%=formater.format(aStudie.getStartDatum().getTime())%></td>
						<td style="text-align: left;"><%=formater.format(aStudie.getEndDatum().getTime())%></td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td colspan="2" rowspan="1">
			<table style="text-align: left; width: 100%;" border="0"
				cellpadding="2" cellspacing="2">
				<tbody>
					<tr class="tblrow3" align="left">
						<td>Beschreibung</td>
					</tr>
					<tr align="left" class="tblrow1">
						<td><%=aStudie.getBeschreibung()%></td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr></tr>
		<tr>
			<td style="text-align: left; width: 50%; vertical-align: top;">
			<table style="text-align: left; width: 100%;" border="0"
				cellpadding="2" cellspacing="2">
				<tbody>
					<tr class="tblrow3">
						<td>Randomisationsbezogene Eigenschaften</td>
					</tr>
					<tr>
						<td class="tblrow1"><%=aStudie.getAlgorithmus()%></td>
					</tr>
				</tbody>
			</table>
			<br>
			</td>
			<td colspan="1" rowspan="2"
				style="text-align: left; vertical-align: top;">
			<table style="text-align: left; width: 100%; height: 100%;"
				border="0" cellpadding="2" cellspacing="2">
				<tbody>
					<tr class="tblrow3">
						<td>Studienarme</td>
					</tr>
					<tr align="left">
						<td class="tblrow1">
						<ul>
							<li><%=aStudienarme.get(aStudienarme.size() - counter)
							.getBezeichnung()%></li>
						</ul>
						<%
						counter--;
						%>
						</td>
					</tr>
					<%
					while (counter > 0) {
					%>
					<tr align="left">
						<td class="tblrow1">
						<ul>
							<li><%=aStudienarme.get(aStudienarme.size() - counter)
								.getBezeichnung()%></li>
						</ul>
						<%
						counter--;
						%>
						</td>
					</tr>
					<%
					}
					%>
				</tbody>
			</table>
			<br>
			</td>
		</tr>
		<tr>
			<td style="text-align: left; vertical-align: top;">
			<table style="text-align: left; width: 100%;" border="0"
				cellpadding="2" cellspacing="2">
				<tbody>
					<tr class="tblrow3">
						<td>Studienprotokoll &nbsp;</td>
					</tr>
					<tr>
						<td class="tblrow1"><a
							href="<%=aStudie.getStudienprotokollpfad() %>"><%=aStudie.getStudienprotokollpfad()%></a></td>
					</tr>
				</tbody>
			</table>

			</td>
		</tr>
	</tbody>
</table>

</fieldset>

<!--   fieldset><legend><b>M&ouml;liche Aktionen</b></legend>--> <%
 if (aRolle == Rolle.Rollen.STATISTIKER) {
 %>
<form>
<fieldset><legend><b>Studienauswahl</b></legend>
<table align="center">
	<tr>
		<td><input type="button" name="statistik"
			value="Statistik anzeigen" tabindex="6"
			onclick="location.href='studie_anzeigen_statistikanzeigen.jsp'">&nbsp;&nbsp;</td>
		<td><input type="button" name="randomisation"
			value="Randomisationsergebnisse" tabindex="7"
			onclick="location.href='ergebnisse.jsp'">&nbsp;&nbsp;</td>
	</tr>
</table>
</fieldset>
</form>
<%
}
%> <%
 if (aRolle == Rolle.Rollen.STUDIENLEITER) {
 %>
<form>
<fieldset><legend><b>Studienauswahl</b></legend>
<table align="center">
	<tr>
		<td><input type="button" name="studie_aendern"
			value="&Auml;ndern" tabindex="2"
			onclick="location.href='studie_aendern.jsp'">&nbsp;&nbsp;</td>
		<%
		if (aStudie.getStatus().equals(Studie.Status.AKTIV)) {
		%>
		<td><input type="button" name="studie_pausieren"
			value="Pausieren" tabindex="3"
			onclick="location.href='studie_pausieren_eins.jsp'">&nbsp;&nbsp;</td>
		<%
		}
		%>
		<%
		if (aStudie.getStatus().equals(Studie.Status.PAUSE)) {
		%>
		<td><input type="button" name="studie_fortsetzen"
			value="Fortsetzen" tabindex="3"
			onclick="location.href='studie_fortsetzen_eins.jsp'">&nbsp;&nbsp;</td>
		<%
		}
		%>
		<td><input type="button" name="arm" value="Arm ausw&auml;hlen"
			tabindex="4" onclick="location.href='armauswaehlen_eins.jsp'">&nbsp;&nbsp;</td>
		<!--<td><input type="button" name="simulation" value="Simulation" tabindex="5" onclick="location.href='simulation.jsp'">&nbsp;&nbsp;</td>-->
		<td><input type="button" name="statistik"
			value="Statistik anzeigen" tabindex="6"
			onclick="location.href='studie_anzeigen_statistikanzeigen.jsp'">&nbsp;&nbsp;</td>
		<td><input type="button" name="randomisation"
			value="Randomisationsergebnisse" tabindex="7"
			onclick="location.href='ergebnisse.jsp'">&nbsp;&nbsp;</td>
	</tr>
</table>
</fieldset>
</form>
<%
}
%> <%
 if (aRolle == Rolle.Rollen.ADMIN) {
 %>
<form>
<fieldset><legend><b>Studienauswahl</b></legend>
<table align="center">
	<tr>
		<td><input type="button" name="statistik"
			value="Statistik anzeigen" tabindex="6"
			onclick="location.href='studie_anzeigen_statistikanzeigen.jsp'">&nbsp;&nbsp;</td>
		<td><input type="button" name="randomisation"
			value="Randomisationsergebnisse" tabindex="7"
			onclick="location.href='ergebnisse.jsp'">&nbsp;&nbsp;</td>
	</tr>
</table>
</fieldset>
</form>
<%
}
%> <%@include file="include/inc_footer.jsp"%></div>
<%@include file="include/inc_menue.jsp"%>
</body>
</html>
