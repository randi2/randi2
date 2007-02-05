<%@ page import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"%>

<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Locale"%>
<%
	BenutzerkontoBean aBenutzerkontoFooter = (BenutzerkontoBean) request
			.getSession().getAttribute("aBenutzer");
	GregorianCalendar letzterZugriff = aBenutzerkontoFooter
			.getLetzterLogin();
	SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy",
			Locale.GERMANY);
%>

<br>
<table width="100%" border="0" cellPadding="0" cellSpacing="0">
	<tr>
		<td align="right" class="footer">letzter Zugriff: <%= sdf.format(letzterZugriff.getTime()) %> :: copyright</td>
	</tr>
</table>
