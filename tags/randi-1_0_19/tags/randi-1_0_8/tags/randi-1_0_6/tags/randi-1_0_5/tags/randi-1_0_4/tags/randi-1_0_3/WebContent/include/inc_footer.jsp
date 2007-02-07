<%@ page import= "de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import= "java.util.GregorianCalendar"
	import= "java.text.SimpleDateFormat" 
	import= "java.util.Locale"
%>
<%
			BenutzerkontoBean aBenutzerkontoFooter = (BenutzerkontoBean) request
			.getSession().getAttribute("aBenutzer");
	GregorianCalendar letzterZugriff = aBenutzerkontoFooter
			.getLetzterLogin();
	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy",
			Locale.GERMANY);
	String formatiertesDatum = sdf.format(letzterZugriff.getTime());
%>

<br>
<table width="100%" border="0" cellPadding="0" cellSpacing="0">
	<tr>
		<td align="right" class="footer">letzter Zugriff: <%= formatiertesDatum %>
		:: copyright</td>
	</tr>
</table>
