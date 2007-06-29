<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.utility.*" import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="java.util.Iterator" import="java.util.Vector"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.ADMIN_LISTE.toString());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="Javascript" src="js/motionpack.js"> </script>
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<script type="text/javascript">
	function hideFilter(){
		document.getElementById('filterdiv').style.display = 'none';
	}
</script>
<%@include file="include/inc_extjs.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body onload="hideFilter();">
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Benutzer suchen</h1>
<fieldset style="width: 90%;"><legend><b>Benutzer
suchen </b></legend>
<form action="DispatcherServlet" method="POST"><input
	type="hidden" name="anfrage_id"
	value="<%=DispatcherServlet.anfrage_id.BENUTZER_SUCHEN.name() %>">

<img alt="Filter anzeigen" src="images/find.png"
	onmousedown="toggleSlide('filterdiv');" title="Filter anzeigen"
	style="cursor:pointer" /> <b>Filter ein-/ausblenden</b>
<div id="filterdiv" style="overflow:hidden; height: 90px;">
<table width="90%">
	<tr>
		<td>Vorname:</td>
		<td><input type="Text"
			name="<%=Parameter.person.VORNAME.name() %>" value="" size="30"
			maxlength="50" /></td>
		<td>Nachname:</td>
		<td><input type="Text"
			name="<%=Parameter.person.NACHNAME.name() %>" value="" size="30"
			maxlength="50" /></td>
	</tr>
	<tr>
		<td>Loginname:</td>
		<td><input type="Text"
			name="<%=Parameter.benutzerkonto.LOGINNAME.name() %>" value=""
			size="30" maxlength="50" /></td>
		<td>E-Mail:</td>
		<td><input type="Text" name="<%=Parameter.person.EMAIL.name() %>"
			value="" size="30" maxlength="50" /></td>
	</tr>
	<tr>
		<td>Institut:</td>
		<td><select name="<%=Parameter.zentrum.INSTITUTION.name() %>" size="1">
		<option "selected"><%=ZentrumServlet.ALLE_ZENTREN %></option>
		<%Iterator<ZentrumBean> it=Zentrum.getAlleZentrenAktiviertDeaktiviert().iterator();
		while(it.hasNext()){%>
			<option><%=it.next().getInstitution()%></option>
		<%} %>
		
		</select>
		</td>
		<td><input type="submit" name="Aktualisieren"
			value="Aktualisieren" /></td>
	</tr>
</table>
</form>
<p></p>
</div>
<p></p>
<form action="DispatcherServlet" method="POST" name="liste_form" id="liste_form">
<input type="hidden" name="<%=Parameter.anfrage_id %>" value="<%=DispatcherServlet.anfrage_id.JSP_BENUTZER_LISTE_ADMIN_ANZEIGEN_SPERREN.name() %>" />
<input type="hidden" name="button" value="" /></form>
<table width="90%">



	<thead align="left">
		<tr class="tblrow1" align="left">
			<th width="10%">Nachname</th>
			<th width="10%">Vorname</th>
			<th width="10%">Loginname</th>
			<th width="30%">Institut</th>
			<th width="25%">E-Mail</th>
			<th width="15%">Aktionen</th>
		</tr>
	</thead>
	<%
				Iterator listeBenutzer = ((Vector) request
				.getAttribute("listeBenutzer")).iterator();
		String reihe = "tblrow1";
		int tabIndex = 1;
		while (listeBenutzer.hasNext()) {
			BenutzerSuchenBean benutzer = (BenutzerSuchenBean) listeBenutzer.next();
	%>
	<tr class="<%=reihe%>">
		<td><%=benutzer.getNachname()%></td>
		<td><%=benutzer.getVorname()%></td>
		<td><%=benutzer.getLoginname()%></td>
		<td><%=benutzer.getInstitut()%></td>
		<td><%=benutzer.getEmail()%></td>
		<td><span  id="benutzer_anzeigen_link" style="cursor:pointer"
			onClick="document.forms['liste_form'].button.value = 'a_<%=benutzer.getBenutzerId() %>';document.forms['liste_form'].submit();">
		anzeigen/&auml;ndern
		</span></td>
		<td><span  id="sperren_link" style="cursor:pointer"
			onClick="document.forms['liste_form'].button.value = 's_<%=benutzer.getBenutzerId() %>';document.forms['liste_form'].submit();">		
		EntSperren
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
