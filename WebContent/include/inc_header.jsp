<%@ page import= "de.randi2.model.fachklassen.*"
		import= "de.randi2.model.fachklassen.beans.*"
%>

<%PersonBean aPersonHeader=((BenutzerkontoBean)request.getSession().getAttribute("aBenutzer")).getBenutzer(); %>
<%Rolle.Rollen aRolleHeader=((BenutzerkontoBean)request.getSession().getAttribute("aBenutzer")).getRolle().getRollenname(); %>
<div id="header"><img src="images/dkfz_logo.gif" width="337"
	height="63" title="" alt=""></div>

<div id="breadcrumb">
<table width="100%" height="25" border="0" cellSpacing="2"
	class="breadcrumb_tbl">
	<tr>
		<td><span id="studie_highlight">Aktuelle Studie $NAME</span> &gt;
		$AKTUELLE_ANSICHT</td>
		<td align="right"><% if (aPersonHeader.getGeschlecht()=='m') { %>Herr<% } else { %>Frau<% } %>&nbsp;<%= aPersonHeader.getVorname() %>&nbsp;<%= aPersonHeader.getNachname() %>&nbsp;<% if (aRolleHeader!=Rolle.Rollen.STUDIENARZT) { %>(<font color="red"><%=aRolleHeader%></font>)<% }%>
		:: <a href="DispatcherServlet?anfrage_id=JSP_HEADER_LOGOUT" id="logout_link">Logout</a>&nbsp;&nbsp;&nbsp;</td>
		<td align="right" width="48"><a href="nachrichtendienst.jsp"><img  src="images/message.gif" border="0"
			alt="Nachricht senden" title="Nachricht senden" width="22"
			height="22"></a>&nbsp;<a href="hilfe.jsp"><img src="images/help.gif" border="0"
			alt="Hilfe" title="Hilfe" width="22" height="22"></a></td>
	</tr>
</table>
</div>