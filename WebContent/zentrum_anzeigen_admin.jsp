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
<script type="text/javascript">
	function hideFilter(){
		document.getElementById('filterdiv').style.display = 'none';
	}
</script>
<%@include file="include/inc_extjs.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: <%=request.getAttribute(DispatcherServlet.requestParameter.TITEL.toString())%></title>
</head>
<body onload="hideFilter();">
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Zentren suchen</h1>
<fieldset style="width: 90%;"><legend><b>Zentren suchen</b></legend>


<img alt="Filter anzeigen" src="images/find.png"
	onmousedown="toggleSlide('filterdiv');" title="Filter anzeigen"
	style="cursor:pointer" /> <b>Filter ein-/ausblenden</b>
<div id="filterdiv" style="overflow:hidden; height: 100px;">
<form action="DispatcherServlet" method="POST"><input type="hidden" name="anfrage_id"
	value="<%=DispatcherServlet.anfrage_id.ZENTRUM_ANZEIGEN_ADMIN.name() %>">
<table width="90%">
	<tr>
		<td>Name&nbsp;des&nbsp;Instituts:</td>
		<td><select name="<%=Parameter.zentrum.INSTITUTION.name() %>" size="3">
		<option "selected"><%=ZentrumServlet.ALLE_ZENTREN %></option>
		<%Iterator<ZentrumBean> it=Zentrum.getAlleZentrenAktiviertDeaktiviert().iterator();
		while(it.hasNext()){%>
			<option><%=it.next().getInstitution()%></option>
		<%} %>
		
		</select>
		</td>
		<td>Abteilungsname:</td>
		<td><input type="Text"
			name="<%=Parameter.zentrum.ABTEILUNGSNAME.name() %>" value="" size="30"
			maxlength="70" /></td>
	</tr>
	<tr>
		<td>Status:</td>
		<td><select name="<%=Parameter.zentrum.AKTIVIERT.name() %>" size="1">
		<%for(ZentrumServlet.aktiviertDeaktiviert aktdeakt: ZentrumServlet.aktiviertDeaktiviert.values()){ %>
		<option <%if(aktdeakt.equals(aktdeakt.KEINE_AUSWAHL)){out.print("selected");} %>><%=aktdeakt.toString() %></option>
		<%} %>
		
		</select></td>
		<td><input type="submit" name="<%=Parameter.filter %>"
			value="Aktualisieren" /></td>
	</tr>
</table>
</form>
<p></p>
</div>
<form action="DispatcherServlet" method="POST" name="liste_form" id="liste_form">
<input type="hidden" name="<%=Parameter.anfrage_id %>" value="<%=DispatcherServlet.anfrage_id.ZENTRUM_AENDERN_SPERREN.name() %>" />
<input type="hidden" name="button" value="" />
</form>

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
		boolean aktiviert = false;
		while (listeZentrum.hasNext()) {
			ZentrumBean zentrum = (ZentrumBean) listeZentrum.next();
	%>
	<tr class="<%=reihe%>">
		<td><%=zentrum.getInstitution()%></td>
		<td><%=zentrum.getAbteilung()%></td>
		<%
			aktiviert = zentrum.getIstAktiviert();
			if(aktiviert){
		%>
		<td>aktiviert</td>
		<%
			} else {
		%>
		<td>deaktiviert</td>
		<%
			}
		%>
		<td><span  id="anzeige_aendern_link" style="cursor:pointer"
			onClick="document.forms['liste_form'].button.value = 'a_<%=zentrum.getId() %>';document.forms['liste_form'].submit();">
		anzeigen/&auml;ndern
		</span></td>
		<td><span  id="sperren_link" style="cursor:pointer"
			onClick="document.forms['liste_form'].button.value = 's_<%=zentrum.getId() %>';document.forms['liste_form'].submit();">		
		<%
		if(aktiviert){
		%>
			deaktivieren
		<%
		} else {
		%>	
			aktivieren
		<%
			}
		%>
		</span></td>
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
</fieldset>
<%@include file="include/inc_footer.jsp"%></div>
<div id="show_none"></div>
<div id="show_none"><%@include file="include/inc_menue.jsp"%></div>
</body>
</html>
