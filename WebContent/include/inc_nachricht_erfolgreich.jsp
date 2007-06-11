<%@page import="de.randi2.controller.DispatcherServlet"
	import="java.util.GregorianCalendar"
	import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="de.randi2.model.fachklassen.beans.*"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"%>
<%if(request.getAttribute(DispatcherServlet.NACHRICHT_OK)!=null){%>
<center>
<div id="nachricht" class="erfolgreich"><%= request.getAttribute(DispatcherServlet.NACHRICHT_OK)%>
</div>
</center>
<%} %>