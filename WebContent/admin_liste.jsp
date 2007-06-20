<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ page 
	import= "de.randi2.model.fachklassen.beans.*"
	import= "de.randi2.utility.*"
	import= "java.util.GregorianCalendar"
	import= "java.text.SimpleDateFormat" 
	import= "java.util.Locale"
	import= "java.util.Iterator"
	import= "java.util.Vector"
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
<h1> Benutzer-Liste </h1>
<table width="600" border="0" cellspacing="5" cellpadding="2" bgcolor="#e3e3e3">
<tr>  
<td align="left" id="filterbezeichnung">Vorname</td>
<td align="left" style="width: 200px"><input type="text" name="name" id="filterfeld"></td>
<td align="left" id="filterbezeichnung">Nachname</td>
<td align="left" style="width: 200px"><input type="text" name="vorname" id="filterfeld"></td>
</tr>
<tr>
<td align="left" id="filterbezeichnung">Login-Name</td>
<td align="left" style="width: 200px"><input type="text" name="loginname" id="filterfeld"></td>
<td align="left" id="filterbezeichnung">E-Mail</td> 
<td align="left" style="width: 200px"><input type="text" name="email" id="filterfeld"></td>
</tr>
<tr>
<td align="left" id="filterbezeichnung">Institut</td>
<td align="left" style="width: 200px"><input type="text" name="Institut" id="filterfeld"></td>
<td align="right" colspan="4"><input type="submit" value="Aktualisieren" style="width: 100px"></td>
</tr>
</table>
<p></p>
<table  width="90%">
<tr class="tblrow1"  align="left">
	<th width="10%"> Nachname </th>
	<th width="10%"> Vorname </th>
	<th width="10%"> Loginname </th>
	<th width="30%"> Institut </th>
	<th width="20%"> E-Mail </th>
	<th width="20%"> Aktionen </th>
</tr>
<%
	Iterator listeBenutzer = ((Vector)request.getAttribute("listeBenutzer")).iterator();
	Iterator listePerson = ((Vector)request.getAttribute("listePerson")).iterator();
	Iterator listeZentrum = ((Vector)request.getAttribute("listeZentrum")).iterator();
	String reihe = "tblrow1"; 
	int tabIndex = 1;
	while(listeBenutzer.hasNext() && listePerson.hasNext() && listeZentrum.hasNext()) {
		BenutzerkontoBean bKonto = (BenutzerkontoBean)listeBenutzer.next();
		PersonBean person = (PersonBean)listePerson.next();
		ZentrumBean zentrum = (ZentrumBean)listeZentrum.next();
%>
	<tr class="<%=reihe%>">
	<td><%=person.getNachname()%></td>
	<td><%=person.getVorname()%></td>
	<td><%=bKonto.getBenutzername()%></td>
	<td><%=zentrum.getInstitution()%></td>
	<td><%=person.getEmail()%></td>
<td> <a class="il_ContainerItemCommand" href="admin_anzeigen.jsp">anzeigen</a><a class="il_ContainerItemCommand" href="benutzer_sperren.jsp">sperren</a><a class="il_ContainerItemCommand" href="">l&ouml;schen</a></td>
</tr>
<%
		tabIndex++;
		if(reihe.equals("tblrow1")) {
			reihe = "tblrow2";
		} else {
			reihe = "tblrow1";
		}
	}//while
%>
</table>
	<%@include file="include/inc_footer.jsp"%>
  </div>
	<div id="show_none">
	</div>
<div id="show_none">
<%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
