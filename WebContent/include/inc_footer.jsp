<%@ page import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="java.util.GregorianCalendar"
	import="de.randi2.model.fachklassen.beans.PersonBean"
	import="de.randi2.model.fachklassen.*"
	import="de.randi2.utility.*"
	import="de.randi2.controller.*"
	import="java.text.SimpleDateFormat" import="java.util.Locale"%>
<%
	String formatiertesDatum = null;
	BenutzerkontoBean aBenutzerkontoFooter = (BenutzerkontoBean) request
			.getSession().getAttribute("aBenutzer");
	if (aBenutzerkontoFooter != null) {
		GregorianCalendar letzterZugriff = aBenutzerkontoFooter
		.getLetzterLogin();
		if (letzterZugriff != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy",
			Locale.GERMANY);
			formatiertesDatum = sdf.format(letzterZugriff.getTime());
		}
	}
%>
<br>
<form action="DispatcherServlet" method="POST" name="impressum_form2"
			id="impressum_form2"><input type="hidden"
			name="<%=Parameter.anfrage_id %>" value=""></form><br>
<table width="100%" border="0" cellPadding="0" cellSpacing="0">
	<tr>
		<td align="right" class="footer"><%if(formatiertesDatum!=null){out.print("letzter Zugriff: "+formatiertesDatum+" ::");}%>
		&nbsp;&copy; RANDI2 (<span id="logout_link" style="cursor:pointer;color:black;"
			onClick="document.forms['impressum_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_HEADER_IMPRESSUM.name() %>';document.forms['impressum_form2'].submit();">Impressum</span>)</td>
	</tr>
</table>
