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
<script language="Javascript" src="js/motionpack.js"> </script>
<title>Randi2 :: Benutzerverwaltung</title>
<script type="text/javascript">
	function hideFilter(){
		document.getElementById('filterdiv').style.display = 'none';
	}
</script>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body onload="hideFilter();">
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Benutzer suchen</h1>
<fieldset style="width: 90%;"><legend><b>Benutzer suchen </b></legend>
<form action="DispatcherServlet" method="POST"><input
	type="hidden" name="anfrage_id"
	value="<%=DispatcherServlet.anfrage_id.BENUTZER_SUCHEN.name() %>">
	
<img alt="Filter anzeigen" src="images/find.png" onmousedown="toggleSlide('filterdiv');" title="Filter anzeigen" style="cursor:pointer"/>
<b>Filter ein-/ausblenden</b>
<div id="filterdiv" style="overflow:hidden; height: 90px;">
<table width="90%">
	<tr>
		<td>
			Vorname:
		</td>
		<td>
			<input type="Text" name="<%=Parameter.person.VORNAME.name() %>" value="" size="30"
	maxlength="50"/>
		</td>
		<td>
			Nachname:
		</td>
		<td>
			<input type="Text"
	name="<%=Parameter.person.NACHNAME.name() %>" value="" size="30" maxlength="50"/>
		</td>
	</tr>
	<tr>
		<td>
			Loginname:
		</td>
		<td>
			<input type="Text"
	name="<%=Parameter.benutzerkonto.LOGINNAME.name() %>" value="" size="30" maxlength="50"/>
		</td>
		<td>
			E-Mail:
		</td>
		<td>
			<input type="Text"
	name="<%=Parameter.person.EMAIL.name() %>" value="" size="30" maxlength="50"/>
		</td>
	</tr>
	<tr>
		<td>
			Institut:
		</td>
		<td>
			<input type="Text"
	name="<%=Parameter.zentrum.INSTITUTION.name() %>" value="" size="30" maxlength="70"/>
		</td>		
		<td>
			<input
	type="submit" name="Aktualisieren" value="Aktualisieren"/>
		</td>
	</tr>	
</table>
<p></p>	
</div>
<p></p>
<table  width="90%">
<thead align="left" >
<tr class="tblrow1"  align="left">
	<th width="10%"> Nachname </th>
	<th width="10%"> Vorname </th>
	<th width="10%"> Loginname </th>
	<th width="30%"> Institut </th>
	<th width="20%"> E-Mail </th>
	<th width="20%"> Aktionen </th>
</tr>
</thead>
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
</form>
</fieldset>
	<%@include file="include/inc_footer.jsp"%>
  </div>
	<div id="show_none">
	</div>
<div id="show_none">
<%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
