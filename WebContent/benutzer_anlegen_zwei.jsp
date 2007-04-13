<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page 
		import="de.randi2.model.fachklassen.beans.*"
		import="java.util.*"
		import="de.randi2.controller.DispatcherServlet"	
%>
<%Iterator<ZentrumBean> listeZentren=((Vector<ZentrumBean>)request.getAttribute("listeZentren")).iterator(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Benutzer anlegen</title>

</head>
<body>
<%@include file="include/inc_header_clean.jsp"%>

<div id="content">

<h1>Benutzer anlegen</h1>
<%@include file="include/inc_nachricht.jsp"%>

<fieldset style="width: 60%"><legend><b>Zentrum suchen </b></legend>
<form action="DispatcherServlet" method="POST">
<input type="hidden" name="anfrage_id" value="<%=DispatcherServlet.anfrage_id.JSP_BENUTZER_ANLEGEN_ZWEI_BENUTZER_REGISTRIEREN_DREI.name() %>">
<p>


Name&nbsp;der&nbsp;Institution:&nbsp;&nbsp;/Name Abteilung
<br>
<input type="Text" name="name_institution" value="" size="30" maxlength="50">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="Text" name="name_abteilung" value="" size="30" maxlength="50">
<input type="submit" name="Filtern" value="Filtern">
</p>
<table width="90%">
	<tr class="tblrow1" align="left">
		<th width="40%">Name der Institution</th>
		<th width="30%">Abteilung</th>
		<th width="20%">Passwort</th>
	</tr>
	
	<%
		String reihe="tblrow2";
		int tabindex=1;
		while(listeZentren.hasNext()){
		ZentrumBean aktuellesZentrum=listeZentren.next();
	%>
	
	<tr class="<%=reihe %>">
		<td><%=aktuellesZentrum.getInstitution() %></td>
		<td><%=aktuellesZentrum.getAbteilung() %></td>
		<td><input type="password" name="zentrum_passwort<%=aktuellesZentrum.getId() %>" tabindex="<%=tabindex %>"></td>
		<td>
		<input type="submit" name="bestaetigen<%=aktuellesZentrum.getId() %>" value="weiter">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>
	<%
		tabindex++;
		if(reihe.equals("tblrow1"))reihe="tblrow2";
		else reihe="tblrow1";
		}
	%>
	
</table>
</form>
</fieldset>      

<form>
		<table>
		<tr>
		<td><input type="button" name="abbrechen" value="Zur&uuml;ck" tabindex="2" onClick="location.href='benutzer_anlegen_eins.jsp'"></td>
		</tr>
		</table>
</form>
				<%@include file="include/inc_footer_clean.jsp"%>
	</div>

</body>
</html>
