<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="java.util.Iterator" import="java.util.Vector"%>
<%@page import="de.randi2.controller.StudieServlet"%>
<%
			Rolle.Rollen aRolle = ((BenutzerkontoBean) request.getSession()
			.getAttribute("aBenutzer")).getRolle().getRollenname();

	Iterator listeStudien = ((Vector) request
			.getAttribute(StudieServlet.requestParameter.LISTE_DER_STUDIEN
			.name())).iterator();
	Iterator listeZentren = ((Vector) request
			.getAttribute(StudieServlet.requestParameter.LISTE_DER_ZENTREN
			.name())).iterator();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: Studie ausw&auml;hlen</title>

<script language="Javascript" src="js/motionpack.js"> </script>
<script type="text/javascript">
<!--
	function hideFilter(){
		document.getElementById('filterdiv').style.display = 'none';
	}
//-->
</script>
<link rel="stylesheet" type="text/css"
	href="js/ext/resources/css/ext-all.css" />
<!-- GC -->
<!-- link rel="stylesheet" type="text/css" 	href="js/ext/resources/css/xtheme-gray.css" /-->
<!-- LIBS -->
<script type="text/javascript" src="js/ext/adapter/yui/yui-utilities.js"></script>
<script type="text/javascript"
	src="js/ext/adapter/yui/ext-yui-adapter.js"></script>
<!-- ENDLIBS -->
<script type="text/javascript" src="js/ext/ext-all.js"></script>
<script type="text/javascript" src="js/studie_auswaehlen.js"></script>
<link rel="stylesheet" type="text/css" href="css/style.css">

</head>
<body onload="hideFilter();">
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Studie ausw&auml;hlen</h1>

<%
if (aRolle == Rolle.Rollen.STUDIENLEITER) {
%>
<form action="DispatcherServlet" method="POST"><input
	type="hidden" name="anfrage_id"
	value="JSP_STUDIE_AUSWAEHLEN_NEUESTUDIE"><input type="submit"
	value="Neue Studie anlegen"></form>
&nbsp;&nbsp;&nbsp;::&nbsp;&nbsp;&nbsp;
<form action="DispatcherServlet" method="POST"><input
	type="hidden" name="anfrage_id"
	value="JSP_STUDIE_AUSWAEHLEN_SIMULATION"><input type="submit"
	value="Simulation"></form>
<br>
<br>
<%
}
%>
<form action="StudieServlet" method="POST"><img
	alt="Filter anzeigen" src="images/find.png"
	onmousedown="toggleSlide('filterdiv');" title="Filter anzeigen"
	style="cursor:pointer" /><b> Filter ein-/ausblenden </b><!--  TODO Table  BUG #2-->
<div id="filterdiv" style="overflow:hidden; height: 100px;"><input
	type="hidden"
	name="<%=DispatcherServlet.requestParameter.ANFRAGE_Id
		.name() %>"
	value="<%=StudieServlet.anfrage_id.JSP_STUDIE_AUSWAEHLEN_FILTERN.name() %>">
<table width="600" border="0" cellspacing="5" cellpadding="2"
	bgcolor="#e3e3e3">
	<tr>
		<td align="left" id="filterbezeichnung">Name</td>
		<td align="left" style="width: 200px"><input type="text"
			name="name" id="filterfeld"></td>
		<td align="left" id="filterbezeichnung">Status</td>
		<td align="left" style="width: 200px"><select size="1"
			id="filterfeld" name="status">
			<% 
			StringBuffer status = new StringBuffer();
			for(int i=0;i<Studie.Status.values().length;i++){
				status.append(Studie.Status.values()[i].toString());
			%>
			<option><%=status %></option>
			<% 
			status.delete(0,status.length());
			} %>
		</select></td>
	</tr>
	<tr>
		<td align="left" id="filterbezeichnung">Zentrum</td>
		<td align="left" colspan="3"><select size="1" id="filterfeld"
			name="zentrum">
			<%
				StringBuffer zentrumString = new StringBuffer();
				ZentrumBean tempZentrum = null;
				while (listeZentren.hasNext()) {
					tempZentrum = (ZentrumBean) listeZentren.next();
					zentrumString.append(tempZentrum.getInstitution()).append(" / ").append(
					tempZentrum.getAbteilung());
			%>
			<option><%=zentrumString%></option>
			<%
				zentrumString.delete(0, zentrumString.length());
				}
			%>
		</select></td>
	</tr>
	<tr>
		<td align="right" colspan="4"><input type="submit" name="filtern"
			value="Filtern" style="width: 100px"></td>
	</tr>
</table>
</div>
</form>
<br>
<br>
<table width="600" cellspacing="0" cellpadding="0" id="studien">
	<thead align="left">
		<tr style="background:#eeeeee;">
			<th width="20%">Name</th>
			<th width="50%">Leitendes Zentrum</th>
			<th width="10%">Status</th>
			<th width="20%">Auswahl</th>
		</tr>
	</thead>
	<%
		String reihe = "tblrow1";
		int tabindex = 1;
		while (listeStudien.hasNext()) {
			StudieBean aStudie = (StudieBean) listeStudien.next();
	%>

	<tr class="<%=reihe %>">
		<td><%=aStudie.getName()%></td>
		<td><%=aStudie.getBenutzerkonto().getZentrum()
								.getInstitution()%></td>
		<td><%=aStudie.getStatus().toString()%></td>
		<td><input type="submit" name="aStudieId<%=aStudie.getId() %>"
			value="weiter"></input></td>
	</tr>
	<%
			tabindex++;
			if (reihe.equals("tblrow1")) {
				reihe = "tblrow2";
			} else {
				reihe = "tblrow1";
			}
		}
	%>
</table>

<%@include file="include/inc_footer.jsp"%></div>
<%
if (aRolle == Rolle.Rollen.ADMIN) {
%>
<%@include file="/include/inc_menue.jsp"%>
<%
}
%>
</body>
</html>
