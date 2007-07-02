<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.BENUTZER_ANZEIGEN_ADMIN.toString());
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
<form action="DispatcherServlet" method="POST" name="menue_form"
			id="menue_form"><input type="hidden"
			name="<%=Parameter.anfrage_id %>" value=""></form>
<%@include file="include/inc_header.jsp"%>
<% //Die 3 Objekte holen
BenutzerkontoBean benutzerkonto=(BenutzerkontoBean)request.getAttribute("aBenutzer");
ZentrumBean zentrum=benutzerkonto.getZentrum();
PersonBean person=benutzerkonto.getBenutzer();

%>
<div id="content">
<h1>Benutzerdetails</h1>
<br>
<br>
<br>
Nachfolgend finden Sie ausführliche Informationen zum Benutzerkonto.
<br>
<br>
<table width="90%">
	<tr class="tblrow1" align="left">
		<th width="5%">Benutzername</th>
		<th width="20%">Rolle</th>
		<th width="20%">Erster Login</th>
		<th width="13%">Letzter Login</th>
		<th width="50%">Gesperrt</th>

	</tr>
	<tr class="tblrow2" align="left">
		<td><%=benutzerkonto.getBenutzername() %></td>
		<td><%=benutzerkonto.getRolle().toString() %></td>
		<td><%GregorianCalendar ersterLogin = benutzerkonto.getErsterLogin();
		if (ersterLogin != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy",
			Locale.GERMANY);
			out.print(sdf.format(ersterLogin.getTime()));
		} %></td>
		<td><%GregorianCalendar letzterLogin = benutzerkonto.getLetzterLogin();
		if (letzterLogin != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy",
			Locale.GERMANY);
			out.print(sdf.format(letzterLogin.getTime()));}%>
</td>
		<td><%if(!benutzerkonto.isGesperrt()){out.print("nicht gesperrt");}else{out.print("gesperrt");} %></td>

	</tr>
</table>
<br>
<br>
<br>
<br>
<br>
Nachfolgend finden Sie ausführliche Informationen zu den Kontaktdaten
<br>
<br>
<table width="90%">
	<tr class="tblrow1" align="left">
		<th width="10%">Titel</th>
		<th width="10%">Vorname</th>
		<th width="10%">Nachname</th>
		<th width="10%">Geschlecht</th>
		<th width="10%">Handynummer</th>
		<th width="10%">Telefonnummer</th>
		<th width="10%">Fax</th>
		<th width="30%">Email</th>

	</tr>
	<tr class="tblrow2" align="left">
		<td><%=person.getTitel().toString() %></td>
		<td><%=person.getVorname() %></td>
		<td><%=person.getNachname() %></td>
		<td><%if(person.getGeschlecht()=='w'){out.print("weiblich");}else{out.print("maennlich");} %></td>
		<td><%=person.getHandynummer()%></td>
		<td><%=person.getTelefonnummer()%></td>
		<td><%=person.getFax() %></td>
		<td><%=person.getEmail()%></td>

	</tr>
</table> 
<br>
<br>
<br>
<br>
<br>
Nachfolgend finden Sie ausführliche Informationen zu dem Zentrum in dem Benutzerkonto registriert ist.
<br>
<br>
<table width="90%">
	<tr class="tblrow1" align="left">
		<th width="5%">Instituion</th>
		<th width="20%">Abteilung</th>
		<th width="20%">Strasse</th>
		<th width="13%">Hausnummer</th>
		<th width="50%">PLZ</th>
		<th width="50%">Ort</th>
		<th width="50%">Aktiviert</th>

	</tr>
	<tr class="tblrow2" align="left">
		<td><%=zentrum.getInstitution() %></td>
		<td><%=zentrum.getAbteilung() %></td>
		<td><%=zentrum.getStrasse() %></td>
		<td><%=zentrum.getHausnr() %></td>
		<td><%=zentrum.getPlz() %></td>
		<td><%=zentrum.getOrt() %></td>
		<td><%if(zentrum.getIstAktiviert()){out.print("aktiviert");}else{out.print("deaktiviert");} %></td>

	</tr>
</table>
<br>
<br>
<br>
<br>
<br>
<%PersonBean ansprechpartner=person.getStellvertreter(); %>
Nachfolgend finden Sie Ausführliche Informationen zum Ansprechpartner des Benutzerkontos.
<br>
<br>
<table width="90%">
	<tr class="tblrow1" align="left">
		<th width="30%">Vorname Ansprechpartner</th>
		<th width="30%">Nachname Ansprechpartner</th>
		<th width="30%">Telefonnummer Ansprechpartner</th>
	</tr>
	<tr class="tblrow2" align="left">
		<td><%if(ansprechpartner!=null){out.print(ansprechpartner.getVorname());}else{out.print("k. A.");}%></td>
		<td><%if(ansprechpartner!=null){out.print(ansprechpartner.getNachname());}out.print("k. A.");%></td>
		<td><%if(ansprechpartner!=null){out.print(ansprechpartner.getTelefonnummer());}out.print("k. A.");%></td>
	</tr>
</table>
<p></p>
<table align="left">
	<tr>
		<td><span class="sub_SA n entry" style="cursor:pointer"
			onClick="document.forms['menue_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.BENUTZER_SUCHEN.name() %>';document.forms['menue_form'].submit();">
		zur&uuml;ck zu
		 <%
//Rolle holen
Rolle.Rollen aRolle=((BenutzerkontoBean)request.getSession().getAttribute(DispatcherServlet.sessionParameter.A_Benutzer.toString())).getRolle().getRollenname(); 
%>
		 <%if(aRolle==Rolle.Rollen.ADMIN){out.print("Benutzer anzeigen/suchen");}
else if(aRolle==Rolle.Rollen.STUDIENLEITER){out.print("Studien&auml;rzte anzeigen/suchen");}
else if (aRolle==Rolle.Rollen.SYSOP){out.print("Admins anzeigen/suchen");}
%>
	</span></td>
	</tr>
</table>
<%@include file="include/inc_footer.jsp"%></div>
<div id="show_none"></div>
<div id="show_none"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>

