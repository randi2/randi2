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

			StudieBean aStudie_header = (StudieBean) (request.getSession().getAttribute(DispatcherServlet.sessionParameter.AKTUELLE_STUDIE.toString()));
			
			String aLocation = (String) request.getAttribute(DispatcherServlet.requestParameter.TITEL.toString());
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
		if (aRolleHeader != Rolle.Rollen.SYSOP) {
		%> <span id="studie_highlight">Aktuelle Studie <%= aStudieName %></span> &gt;
		<%=aLocation %> <%
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
		%> :: <a href="DispatcherServlet?<%=Parameter.anfrage_id%>=<%=DispatcherServlet.anfrage_id.AKTION_LOGOUT %>" id="logout_link">Logout</a>&nbsp;&nbsp;&nbsp;</td>
		<td align="right" width="48"><a href="DispatcherServlet?<%=Parameter.anfrage_id%>=<%=DispatcherServlet.anfrage_id.JSP_HEADER_NACHRICHTENDIENST %>"><img
			src="images/message.gif" border="0" alt="Nachricht senden"
			title="Nachricht senden" width="22" height="22"></a>&nbsp;<a
			href="DispatcherServlet?<%=Parameter.anfrage_id%>=<%=DispatcherServlet.anfrage_id.JSP_HEADER_HILFE %>"><img src="images/help.gif" border="0"
			alt="Hilfe" title="Hilfe" width="22" height="22"></a></td>
	</tr>
</table>
</div>
