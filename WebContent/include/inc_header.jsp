<%@ page import="de.randi2.model.fachklassen.*"
	import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.beans.AktivierungBean"
	import="java.util.GregorianCalendar"
	import="de.randi2.model.fachklassen.beans.PersonBean"
	import="de.randi2.controller.DispatcherServlet"%>
<%
			BenutzerkontoBean aBenutzer = (BenutzerkontoBean) (request
			.getSession()).getAttribute("aBenutzer");

	Rolle seineRolle = aBenutzer.getRolle();

	StudieBean aStudie_header = (StudieBean) (request.getSession()
			.getAttribute(DispatcherServlet.sessionParameter.AKTUELLE_STUDIE
			.toString()));

	String aLocation = (String) request
			.getAttribute(DispatcherServlet.requestParameter.TITEL
			.toString());
	String aStudieName = "";

	if (aLocation == null) {

		aLocation = "KEIN TITEL GESETZT!";

	}

	if (aStudie_header == null) {

		aStudieName = "KEINE STUDIE GEWAEHLT!";

	} else {

		aStudieName = aStudie_header.getName();
	}

	PersonBean aPersonHeader = null;
	Rolle.Rollen aRolleHeader = null;

	if (aBenutzer != null) {
		aBenutzer = (BenutzerkontoBean) request.getSession()
		.getAttribute("aBenutzer");
		aPersonHeader = aBenutzer.getBenutzer();
		aRolleHeader = aBenutzer.getRolle().getRollenname();
	} else {
		response.sendRedirect(response.encodeRedirectURL("index.jsp"));
		return;
	}
%>
<%@page import="de.randi2.utility.Config"%>
<%@page import="de.randi2.utility.Parameter"%>
<%@page import="de.randi2.controller.StudieServlet"%>
<div id="header"><img
	src="<%=Config.getProperty(Config.Felder.RELEASE_BILD_LOGO) %>"
	width="337" height="63" title="" alt=""></div>

<div id="breadcrumb">
<table width="100%" border="0" cellSpacing="2" class="breadcrumb_tbl">
	<tr>
		<td>
		<%
					if (aRolleHeader != Rolle.Rollen.SYSOP
					&& request
					.getAttribute(DispatcherServlet.requestParameter.TITEL
					.toString()) != "Studie anlegen"
					&& request
					.getAttribute(DispatcherServlet.requestParameter.TITEL
					.toString()) != "Studie ausw&auml;hlen") {
		%> <span id="studie_highlight">Aktuelle Studie <%=aStudieName%></span>
		&gt; <%=aLocation%> <%
 } else {
 %> <span id="studie_highlight">RANDI2</span> &gt; <%=aLocation%> <%
 }
 %>
		</td>
		<td align="right">
		<%
		if (aPersonHeader.getGeschlecht() == 'm') {
		%>Herr<%
		} else {
		%>Frau<%
		}
		%>&nbsp;<%=aPersonHeader.getVorname()%>&nbsp;<%=aPersonHeader.getNachname()%>&nbsp;<%
		if (aRolleHeader != Rolle.Rollen.STUDIENARZT) {
		%>(<span id="rolle_highlight"><%=aRolleHeader%></span>)<%
		}
		%> ::
		<form action="DispatcherServlet" method="POST" name="logout_form"
			id="logout_form"><input type="hidden"
			name="<%=Parameter.anfrage_id %>" value=""></form>
		<span id="logout_link" style="cursor:pointer"
			onClick="document.forms['logout_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.AKTION_LOGOUT.name() %>';document.forms['logout_form'].submit();">
		Logout</span> &nbsp;&nbsp;&nbsp;</td>
		<td align="right" width="48">
		<%
					if ((aStudie_header != null
					& (seineRolle == Rolle.getStudienarzt() | seineRolle == Rolle
					.getStudienleiter()) | seineRolle == Rolle
					.getStatistiker())
					|| seineRolle == Rolle.getAdmin()
					|| seineRolle == Rolle.getSysop()) {
		%>
		<form action="DispatcherServlet" method="POST"
			name="nachrichtendienst_form" id="nachrichtendienst_form"><input
			type="hidden" name="<%=Parameter.anfrage_id %>" value=""></form>
		<img src="images/message.gif" border="0" alt="Nachricht senden"
			title="Nachricht senden" width="22" height="22"
			style="cursor:pointer"
			onClick="document.forms['nachrichtendienst_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_HEADER_NACHRICHTENDIENST.name() %>';document.forms['nachrichtendienst_form'].submit();">&nbsp;<%
		}
		%>
		<form action="DispatcherServlet" method="POST" name="hilfe_form"
			id="hilfe_form"><input type="hidden"
			name="<%=Parameter.anfrage_id %>" value=""></form>
		<img src="images/help.gif" border="0" alt="Hilfe" title="Hilfe"
			width="22" height="22" style="cursor:pointer"
			onClick="document.forms['hilfe_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_HEADER_HILFE.name() %>';document.forms['hilfe_form'].submit();"></td>
	</tr>
</table>
</div>
