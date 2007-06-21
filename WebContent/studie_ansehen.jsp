<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ page import= "de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import= "java.util.GregorianCalendar"
	import= "java.text.SimpleDateFormat" 
	import= "java.util.Locale"
%>
<%@ page 
		import= "de.randi2.model.fachklassen.beans.*"%>
<%
	Rolle.Rollen aRolle=((BenutzerkontoBean)request.getSession().getAttribute("aBenutzer")).getRolle().getRollenname(); 
	StudieBean aStudie = (StudieBean) request.getSession().getAttribute(DispatcherServlet.sessionParameter.AKTUELLE_STUDIE.name());
	Vector<StudienarmBean> aStudienarme = aStudie.getStudienarme();
	int counter = aStudienarme.size();
	SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy",Locale.GERMANY);


%>
   <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Vector"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: Studie anzeigen</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%> 
<div id="content">
<h1>Studie ansehen</h1>
<%@include file="include/inc_nachricht.jsp"%>
<table width="100%">
	<tr class="tblrow1" align="left">
		<th width="10%">Name der Studie</th>
		<th width="10%">Beschreibung</th>
		<th width="10%">Startdatum</th>
		<th width="10%">Enddatum</th>
	</tr>
	<tr class="tblrow2" align="left">
		<td><%=aStudie.getName() %></td>
		<td><%=aStudie.getBeschreibung() %></td>
		<td><%=formater.format(aStudie.getStartDatum().getTime()) %></td>
		<td><%=formater.format(aStudie.getEndDatum().getTime()) %></td>
	</tr>
</table><br><br><br>
<table>
	<tr class="tblrow1" align="left">
		<th width="10%">Studienprotokoll</th>
		<th width="10%">Arme der Studie</th>
		<th width="10%">Randomisationsart</th>
	</tr>
	<tr class="tblrow2" align="left">
		<td><a href="<%=aStudie.getStudienprotokollpfad() %>"><%=aStudie.getStudienprotokollpfad() %></a></td>
		<td><%=aStudienarme.get(aStudienarme.size()-counter).getBezeichnung()%><%counter--; %></td>
		<td><%=aStudie.getRandomisationsart() %></td>
	</tr>
	<%
		while(counter>0){
	%>
		<tr align="left">
		<td></td>
		<td><%=aStudienarme.get(aStudienarme.size()-counter).getBezeichnung()%><%counter--; %></td>
		<td></td>
		</tr>		
	<%	
	counter--;
		}
	%>
	
</table><br><br><br>
<table>
	<tr class="tblrow1" align="left">
		<th width="10%">Leitende Institution</th>
		<th width="10%">Verantwortliche(r) Studienleiter(in)</th>
	</tr>
	<tr class="tblrow2" align="left">
		<td><%=aStudie.getBenutzerkonto().getZentrum().getInstitution() %></td>
		<td><%=(aStudie.getBenutzerkonto().getBenutzer().getVorname()+" "+aStudie.getBenutzerkonto().getBenutzer().getNachname() ) %></td>
	</tr>
</table><br><br><br>

<%	if(aRolle==Rolle.Rollen.STATISTIKER) { 		%>
<form>
		<fieldset>
			<legend><b>Studienauswahl</b></legend>
				<table align="center">
					<tr>
						<td><input type="button" name="statistik" value="Statistik anzeigen" tabindex="6" onclick="location.href='studie_anzeigen_statistikanzeigen.jsp'">&nbsp;&nbsp;</td>
						<td><input type="button" name="randomisation" value="Randomisationsergebnisse" tabindex="7" onclick="location.href='ergebnisse.jsp'">&nbsp;&nbsp;</td>
					</tr>
				</table>
		</fieldset>
	</form>
<%					}		%>

<%	if(aRolle==Rolle.Rollen.STUDIENLEITER) { 		%>
<form>
		<fieldset>
			<legend><b>Studienauswahl</b></legend>
				<table align="center">
					<tr>
						<td><input type="button" name="studie_aendern" value="&Auml;ndern" tabindex="2" onclick="location.href='studie_aendern.jsp'">&nbsp;&nbsp;</td>
					<% if(aStudie.getStatus().equals(Studie.Status.AKTIV)) { %>
						<td><input type="button" name="studie_pausieren" value="Pausieren" tabindex="3" onclick="location.href='studie_pausieren_eins.jsp'">&nbsp;&nbsp;</td>
						<%					}		%>
						<%if(aStudie.getStatus().equals(Studie.Status.PAUSE)) { %> 
						<td><input type="button" name="studie_fortsetzen" value="Fortsetzen" tabindex="3" onclick="location.href='studie_fortsetzen_eins.jsp'">&nbsp;&nbsp;</td>
						<%					}		%>
						<td><input type="button" name="arm" value="Arm ausw&auml;hlen" tabindex="4" onclick="location.href='armauswaehlen_eins.jsp'">&nbsp;&nbsp;</td>
						<!--<td><input type="button" name="simulation" value="Simulation" tabindex="5" onclick="location.href='simulation.jsp'">&nbsp;&nbsp;</td>-->
						<td><input type="button" name="statistik" value="Statistik anzeigen" tabindex="6" onclick="location.href='studie_anzeigen_statistikanzeigen.jsp'">&nbsp;&nbsp;</td>
						<td><input type="button" name="randomisation" value="Randomisationsergebnisse" tabindex="7" onclick="location.href='ergebnisse.jsp'">&nbsp;&nbsp;</td>
					</tr>
				</table>
		</fieldset>
	</form>
<%					}		%>

<%	if(aRolle==Rolle.Rollen.ADMIN) { 		%>
<form>
		<fieldset>
			<legend><b>Studienauswahl</b></legend>
				<table align="center">
					<tr>
						<td><input type="button" name="statistik" value="Statistik anzeigen" tabindex="6" onclick="location.href='studie_anzeigen_statistikanzeigen.jsp'">&nbsp;&nbsp;</td>
						<td><input type="button" name="randomisation" value="Randomisationsergebnisse" tabindex="7" onclick="location.href='ergebnisse.jsp'">&nbsp;&nbsp;</td>
					</tr>
				</table>
		</fieldset>
	</form>
<%					}		%>
	
<%@include file="include/inc_footer.jsp"%>
</div>
<%@include file="include/inc_menue.jsp"%>
</body>
</html>
