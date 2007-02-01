<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import= "de.randi2.model.fachklassen.*"
		import= "de.randi2.model.fachklassen.beans.*"%>
<%Rolle.Rollen aRolle=((BenutzerkontoBean)request.getSession().getAttribute("aBenutzer")).getRolle().getRollenname(); %>
   <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Studie anzeigen</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%> 
<div id="content">
<h1>Studie anzeigen</h1>




<table width="100%">
	<tr class="tblrow1" align="left">
		<th width="10%">Name der Studie</th>
		<th width="10%">Beschreibung</th>
		<th width="10%">Startdatum</th>
		<th width="10%">Enddatum</th>
	</tr>
	<tr class="tblrow2" align="left">
		<td>Aspirin</td>
		<td>12.03.2003</td>
		<td>30.08.2006</td>
		<td>protokoll</td>
	</tr>
</table><br><br><br>
<table>
	<tr class="tblrow1" align="left">
		<th width="10%">Studienprotokoll</th>
		<th width="10%">Arme der Studie</th>
		<th width="10%">Randomisationsbezogene Eigenschaften</th>
	</tr>
	<tr class="tblrow2" align="left">
		<td>Aspirin</td>
		<td>12.03.2003</td>
		<td>30.08.2006</td>
	</tr>
</table><br><br><br>
<table>
	<tr class="tblrow1" align="left">
		<th width="10%">Leitende Institution</th>
		<th width="10%">Verantwortliche(r) Studienleiter(in)</th>
	</tr>
	<tr class="tblrow2" align="left">
		<td>protokoll</td>
		<td>blablabla</td>
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
						<td><input type="button" name="studie_pausieren" value="Pausieren" tabindex="3" onclick="location.href='studie_pausieren_eins.jsp'">&nbsp;&nbsp;</td>
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