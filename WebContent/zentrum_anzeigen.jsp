<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.controller.DispatcherServlet"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.*"
	import="de.randi2.controller.StudieServlet"
	import="de.randi2.utility.Parameter" import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.ZENTRUM_ANZEIGEN.toString());
%>
<%
			Vector<ZentrumBean> zugehoerigeZentren = (Vector<ZentrumBean>) request
			.getAttribute(StudieServlet.requestParameter.ZUGHOERIGE_ZENTREN
			.toString());
	Iterator itZugehoerigeZentren = null;

	Vector<ZentrumBean> nichtZugehoerigeZentren = (Vector<ZentrumBean>) request
			.getAttribute(StudieServlet.requestParameter.NICHT_ZUGEHOERIGE_ZENTREN
			.toString());
	ZentrumBean aktuellesZentrum = null;
	Iterator itNichtZugehoerigeZentren = null;

	Iterator filterZentren = null;
	Vector<ZentrumBean> gefilterteZentren = null;
	try {
		itZugehoerigeZentren = zugehoerigeZentren.iterator();
		itNichtZugehoerigeZentren = nichtZugehoerigeZentren.iterator();
		filterZentren = ((Vector) request
		.getAttribute(StudieServlet.requestParameter.GEFILTERTE_ZENTREN
				.toString())).iterator();
		gefilterteZentren = (Vector<ZentrumBean>) request
		.getAttribute(StudieServlet.requestParameter.GEFILTERTE_ZENTREN
				.toString());
	} catch (NullPointerException npe) {
		;
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>

<%@include file="include/inc_extjs.jsp"%>

<script type="text/javascript">
<!--
	function hideFilter(){
		document.getElementById('filterdiv').style.display = 'none';
	}
//-->
</script>
<%@include file="include/inc_extjs.jsp"%>



<script language="Javascript" src="js/motionpack.js"
	type="text/javascript"> </script>
<script type="text/javascript" src="js/zentrum_anzeigen.js"></script>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>


<body onload="hideFilter();">

<%@include file="include/inc_header.jsp"%>

<div id="content">
<form action="DispatcherServlet" method="post"><input
	type="hidden" name="<%=Parameter.anfrage_id %>"
	value="<%=StudieServlet.anfrage_id.JSP_ZENTRUM_ANZEIGEN.name() %>">

<h1>Zentrum suchen</h1>

<fieldset style="width: 90%;"><legend><b>Zentrum
suchen </b></legend><br />

&nbsp;&nbsp;&nbsp; <img alt="Filter anzeigen" src="images/find.png"
	onmousedown="toggleSlide('filterdiv');" title="Filter anzeigen"
	style="cursor:pointer" /> <b>Filter ein-/ausblenden</b>

<div id="filterdiv" style="overflow:hidden; height: 70px;">
<table width="90%">
	<tr>
		<td>Name&nbsp;der&nbsp;Institution:</td>
		<td>Name&nbsp;der&nbsp;Abteilung:</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td><input type="Text"
			name="<%=Parameter.zentrum.INSTITUTION.name() %>" value="" size="30"
			maxlength="50" /></td>
		<td><input type="Text"
			name="<%=Parameter.zentrum.ABTEILUNGSNAME.name() %>" value=""
			size="30" maxlength="50" /></td>
		<td><input type="submit" name="Filtern" value="Filtern" /></td>
	</tr>

</table>
<div id="form_filter"></div>
</div>
</fieldset>
</form>
<table width="90%" id="zentren">
	<thead align="left">
		<tr style="background:#eeeeee;">
			<th width="30%">Name der Institution</th>
			<th width="30%">Abteilung</th>
			<th width="10%">Status</th>
			<th width="30%">Aktion</th>
		</tr>
	</thead>
	<tbody>

		<%
			String aktiv = null;
			int tabindex = 1;
			String reihe = "tblrow2";
			if (gefilterteZentren != null) {

				while (filterZentren.hasNext()) {
					aktuellesZentrum = (ZentrumBean) filterZentren.next();
					if (aktuellesZentrum.getIstAktiviert()) {
				aktiv = "aktiv";
					} else {
				aktiv = "inaktiv";
					}
		%>

		<tr class="<%=reihe %>">
			<td><%=aktuellesZentrum.getInstitution()%></td>
			<td><%=aktuellesZentrum.getAbteilung()%></td>
			<td><%=aktiv%></td>
			<td>
			<form action="DispatcherServlet" method="POST"
				name="zentrenAnzeigen_form<%=tabindex %>"
				id="zentrenAnzeigen_form<%=tabindex %>"><input type="hidden"
				name="<%=Parameter.zentrum.ZENTRUM_ID.toString()%>"
				value="<%=aktuellesZentrum.getId() %>"><input type="hidden"
				name="<%=Parameter.anfrage_id %>" value="hallo"></form>
			<span id="zentrenAnzeigen_link<%=tabindex %>" style="cursor:pointer"
				onClick="document.forms['zentrenAnzeigen_form<%=tabindex %>'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_ZENTRUM_ANSEHEN.name() %>';

				document.forms['zentrenAnzeigen_form<%=tabindex %>'].<%=Parameter.zentrum.ZENTRUM_ID.toString()%>.value = '<%=aktuellesZentrum.getId() %>';document.forms['zentrenAnzeigen_form<%=tabindex %>'].submit();">

			<b>Zentrumsdetails</b></span>				</td>
		</tr>
		<%
					tabindex++;
					if (reihe.equals("tblrow1"))
				reihe = "tblrow2";
					else
				reihe = "tblrow1";
				}

			} else {
				if (zugehoerigeZentren != null) {

					while (itZugehoerigeZentren.hasNext()) {
				aktuellesZentrum = (ZentrumBean) itZugehoerigeZentren
						.next();
				if (aktuellesZentrum.getIstAktiviert()) {
					aktiv = "aktiv";
				} else {
					aktiv = "inaktiv";
				}
		%>

		<tr class="<%=reihe %>">
			<td><%=aktuellesZentrum.getInstitution()%></td>
			<td><%=aktuellesZentrum.getAbteilung()%></td>
			<td><%=aktiv%></td>
			<td>
			<form action="DispatcherServlet" method="POST"
				name="zentrenAnzeigen_form<%=tabindex %>"
				id="zentrenAnzeigen_form<%=tabindex %>"><input type="hidden"
				name="<%=Parameter.zentrum.ZENTRUM_ID.toString()%>"
				value="<%=aktuellesZentrum.getId() %>"><input type="hidden"
				name="<%=Parameter.anfrage_id %>" value="hallo"></form>
			<span id="zentrenAnzeigen_link<%=tabindex %>" style="cursor:pointer"
				onClick="document.forms['zentrenAnzeigen_form<%=tabindex %>'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_ZENTRUM_ANSEHEN.name() %>';

				document.forms['zentrenAnzeigen_form<%=tabindex %>'].<%=Parameter.zentrum.ZENTRUM_ID.toString()%>.value = '<%=aktuellesZentrum.getId() %>';document.forms['zentrenAnzeigen_form<%=tabindex %>'].submit();">
			<b>Zentrumsdetails</b></span></td>
		</tr>
		<%
				tabindex++;
				if (reihe.equals("tblrow1"))
					reihe = "tblrow2";
				else
					reihe = "tblrow1";
					}

				}
				if (nichtZugehoerigeZentren != null) {

					while (itNichtZugehoerigeZentren.hasNext()) {
				aktuellesZentrum = (ZentrumBean) itNichtZugehoerigeZentren
						.next();
				if (aktuellesZentrum.getIstAktiviert()) {
					aktiv = "aktiv";
				} else {
					aktiv = "inaktiv";
				}
		%>

		<tr class="<%=reihe %>">
			<td><%=aktuellesZentrum.getInstitution()%></td>
			<td><%=aktuellesZentrum.getAbteilung()%></td>
			<td><%=aktiv%></td>
			<td>
			<form action="DispatcherServlet" method="POST"
				name="zentrenAnzeigen_form<%=tabindex %>"
				id="zentrenAnzeigen_form<%=tabindex %>"><input type="hidden"
				name="<%=Parameter.zentrum.ZENTRUM_ID.toString()%>"
				value="<%=aktuellesZentrum.getId() %>"><input type="hidden"
				name="<%=Parameter.anfrage_id %>" value="hallo"></form>
			<span id="zentrenAnzeigen_link<%=tabindex %>" style="cursor:pointer"
				onClick="document.forms['zentrenAnzeigen_form<%=tabindex %>'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_ZENTRUM_ANSEHEN.name() %>';

				document.forms['zentrenAnzeigen_form<%=tabindex %>'].<%=Parameter.zentrum.ZENTRUM_ID.toString()%>.value = '<%=aktuellesZentrum.getId() %>';document.forms['zentrenAnzeigen_form<%=tabindex %>'].submit();">
			<b>Zentrumsdetails</b></span><span id="zentrenAnzeigen_link<%=tabindex %>" style="cursor:pointer"
				onClick="document.forms['zentrenAnzeigen_form<%=tabindex %>'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.AKTION_ZENTRUM_ZUWEISEN.name() %>';

				document.forms['zentrenAnzeigen_form<%=tabindex %>'].<%=Parameter.zentrum.ZENTRUM_ID.toString()%>.value = '<%=aktuellesZentrum.getId() %>';document.forms['zentrenAnzeigen_form<%=tabindex %>'].submit();"><b><br>Zentrum zur Studie hinzuf&uuml;gen</span></td>
		</tr>
		<%
				tabindex++;
				if (reihe.equals("tblrow1"))
					reihe = "tblrow2";
				else
					reihe = "tblrow1";
					}

				}
			}
		%>
	</tbody>
</table>

<table width="90%">
	<tr>
		<td><br>
		<form action="DispatcherServlet" method="POST"
			name="zentrenAnzeigen_form<%=tabindex %>"
			id="zentrenAnzeigen_form<%=tabindex %>"><input type="hidden"
			name="<%=Parameter.anfrage_id %>" value=""> <input
			type="hidden" name="einName"
			value="<%=aktuellesZentrum.getId() %>"></form>
		<span id="zentrenAnzeigen_link<%=tabindex %>" style="cursor:pointer"
			onClick="document.forms['zentrenAnzeigen_form<%=tabindex %>'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_STUDIE_ANSEHEN.name() %>';document.forms['zentrenAnzeigen_form<%=tabindex %>'].submit();">
		<b>zurück zur Studie</b></span></td>
	</tr>
</table>

<div id="show_none"><%@include file="include/inc_footer.jsp"%></div>



</div>

<div id="show_BV"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
