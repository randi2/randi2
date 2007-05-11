<%@page import="de.randi2.model.fachklassen.AktivierungBean"
	import="java.util.GregorianCalendar"
	import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="de.randi2.model.fachklassen.beans.*"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"%>
<%if(request.getAttribute(DispatcherServlet.FEHLERNACHRICHT)!=null){%>
<center>
<div id="nachricht" class="warnung"><%= request.getAttribute(DispatcherServlet.FEHLERNACHRICHT)%>
</div>
</center>
<%} %>
