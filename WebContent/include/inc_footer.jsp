<%@ page import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="java.util.GregorianCalendar"
	import="de.randi2.model.fachklassen.beans.PersonBean"
	import="de.randi2.model.fachklassen.*"
	import="java.text.SimpleDateFormat" import="java.util.Locale"%>
<%
String formatiertesDatum ="Fehler beim Datum"; 
			BenutzerkontoBean aBenutzerkontoFooter = (BenutzerkontoBean) request
			.getSession().getAttribute("aBenutzer");
if(aBenutzerkontoFooter!=null)
{
	GregorianCalendar letzterZugriff = aBenutzerkontoFooter
	.getLetzterLogin();
	if(letzterZugriff!=null)
	{
	    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy",
			Locale.GERMANY);
	    formatiertesDatum=sdf.format(letzterZugriff.getTime());
	}
}




%>

<%--@page import="java.util.GregorianCalendar"--%>
<br>
<table width="100%" border="0" cellPadding="0" cellSpacing="0">
	<tr>
		<td align="right" class="footer">letzter Zugriff: <%= formatiertesDatum %>
		:: copyright</td>
	</tr>
</table>
