<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="de.randi2.model.fachklassen.beans.*"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.controller.Nachrichtendienst"
	import="de.randi2.controller.DispatcherServlet"
	import="de.randi2.utility.*"%>
<%

request.setAttribute(DispatcherServlet.requestParameter.TITEL
		.toString(), JspTitel.NACHRICHTENDIENST.toString());

%>
<%
	PersonBean aPerson = ((BenutzerkontoBean)session.getAttribute("aBenutzer")).getBenutzer();
//Feldervorbelegungen
	String betreff = (String)request.getAttribute(Nachrichtendienst.requestParameter.BETREFF.name());
	if (betreff==null){
    	betreff="Default-Betreff";
	}
	String nachrichtentext= (String)request.getAttribute(Nachrichtendienst.requestParameter.NACHRICHTENTEXT.name());
	if (nachrichtentext==null){
	    nachrichtentext="Default-nachrichtentext";
	}   	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="include/inc_extjs.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: <%=request.getAttribute(DispatcherServlet.requestParameter.TITEL.toString()) %></title>
<script type="text/javascript" src="js/prototype.js"></script>
<script type="text/javascript" src="js/zebda.js"></script>
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Nachrichtendienst</h1>
<%@include file="include/inc_nachricht.jsp"%>
<form action="Nachrichtendienst" method="post" id="Nachrichtenversand">
<input type="hidden"
	name="<%=Nachrichtendienst.requestParameter.ANFRAGE_ID.name()%>"
	value="<%=Nachrichtendienst.anfrage_id.VERSENDE_NACHRICHT.name() %>">
<fieldset><legend><b>Mitteilung schreiben</b></legend>
<table border="0">
	<tr>
		<td><b>Absender:</b>&nbsp;<%= aPerson.getVorname() %>&nbsp;<%=aPerson.getNachname()%><br>
		<b>E-Mail:</b>&nbsp;<%=aPerson.getEmail()%></td>
	</tr>
	<tr>
		<td><label for="empfaenger"><b>Empf&auml;nger:</b> (An
		folgende Benutzer k&ouml;nnen Sie eine Nachricht versenden)</label><br>
		<select id="empfaenger"
			name="<%= Nachrichtendienst.requestParameter.EMPFAENGER.name() %>"
			tabindex="1" z:required="true"
			z:message="Bitte wählen Sie einen Empfänger aus">
			<%=Nachrichtendienst.getEmpfaengerListe(request)%>
		</select></td>
	</tr>
	<tr>
		<td><label for="betreff"><b>Betreff:</b></label><br>
		<input type="text"
			name="<%=Nachrichtendienst.requestParameter.BETREFF.name() %>"
			id="betreff" size="50" tabindex="2" z:required="true"
			z:message="Bitte geben Sie einen Betreff ein" value="<%=betreff %>"></td>
	</tr>
	<tr>
		<td><label for="text"><b>Nachrichtentext:</b></label><br>
		<textarea
			name="<%=Nachrichtendienst.requestParameter.NACHRICHTENTEXT.name() %>"
			id="text" rows="7" cols="50" tabindex="3" z:required="true"
			z:message="Bitte geben Sie einen Nachrichtentext ein"><%=nachrichtentext%></textarea><br>
		</td>
	</tr>
	<tr>
		<td><input type="submit" name="bestaetigen"
			value="Nachricht versenden" tabindex="4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="reset" name="abbrechen" value="Formular l&ouml;schen"
			tabindex="5"></td>
	</tr>
</table>
</fieldset>
</form>
<br>
<%@include file="include/inc_footer.jsp"%></div>
<div id="show_none"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
