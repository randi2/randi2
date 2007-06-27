<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.*" import="java.util.Iterator"
	import="java.util.Vector" import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.ZENTRUM_ANZEIGEN_ADMIN.toString());
%>
<%@ page import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="Javascript" src="js/motionpack.js"> </script>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: <%=request.getAttribute(DispatcherServlet.requestParameter.TITEL.toString())%></title>
</head>
<body onload="hideFilter();">
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Zentren anzeigen</h1>
<fieldset style="width: 90%;"><legend><b>Zentren anzeigen</b></legend>
<form action="DispatcherServlet" method="POST"><input type="hidden" name="anfrage_id"
	value="<%=DispatcherServlet.anfrage_id.ZENTRUM_ANZEIGEN_ADMIN.name() %>">

<img alt="Filter anzeigen" src="images/find.png"
	onmousedown="toggleSlide('filterdiv');" title="Filter anzeigen"
	style="cursor:pointer" /> <b>Filter ein-/ausblenden</b>
<div id="filterdiv" style="overflow:hidden; height: 70px;">
<table width="90%">
	<tr>
		<td>Name&nbsp;des&nbsp;Instituts:</td>
		<td><input type="Text"
			name="<%=Parameter.zentrum.INSTITUTION.name() %>" value="" size="30"
			maxlength="70" /></td>
		<td>Abteilungsname:</td>
		<td><input type="Text"
			name="<%=Parameter.zentrum.ABTEILUNGSNAME.name() %>" value="" size="30"
			maxlength="70" /></td>
	</tr>
	<tr>
		<td>Status:</td>
		<td><select name="<%=Parameter.zentrum.AKTIVIERT.name() %> size="3">
		<%for(ZentrumServlet.aktiviertDeaktiviert aktdeakt: ZentrumServlet.aktiviertDeaktiviert.values()){ %>
		<option <%if(aktdeakt.equals(aktdeakt.KEINE_AUSWAHL)){out.print("selected");} %>><%=aktdeakt.toString() %></option>
		<%} %>
		
		</select></td>
		<td><input type="submit" name="<%=Parameter.filter %>"
			value="Aktualisieren" /></td>
	</tr>
</table>
<p></p>
</div>
<p></p>
<table width="90%">
	<thead align="left">
		<tr class="tblrow1" align="left">
			<th width="35%">Name&nbsp;des&nbsp;Instituts</th>
			<th width="35%">Abteilungsname</th>
			<th width="20%">Status</th>
		</tr>
	</thead>
	<%
		Iterator listeZentrum = ((Vector) request.getAttribute("listeZentrum")).iterator();
		String reihe = "tblrow1";
		int tabIndex = 1;
		while (listeZentrum.hasNext()) {
			ZentrumBean zentrum = (ZentrumBean) listeZentrum.next();
	%>
	<tr class="<%=reihe%>">
		<td><%=zentrum.getInstitution()%></td>
		<td><%=zentrum.getAbteilung()%></td>
		<%
			if(zentrum.getIstAktiviert()){
		%>
		<td>aktiviert</td>
		<%
			} else {
		%>
		<td>deaktiviert</td>
		<%
			}
		%>
		<td><a class="il_ContainerItemCommand" href="zentrum_anzeigen.jsp">anzeigen</a><a 
		class="il_ContainerItemCommand" href="zentrum_aendern.jsp">&auml;ndern</a><a
			class="il_ContainerItemCommand" href="">aktivieren</a><a
			class="il_ContainerItemCommand" href="">deaktivieren</a></td>
	</tr>
	<%
			tabIndex++;
			if (reihe.equals("tblrow1")) {
				reihe = "tblrow2";
			} else {
				reihe = "tblrow1";
			}
		}//while
	%>
</table>
</form>
</fieldset>
<%@include file="include/inc_footer.jsp"%></div>
<div id="show_none"></div>
<div id="show_none"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
