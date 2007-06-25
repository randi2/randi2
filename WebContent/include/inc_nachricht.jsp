<%@page import="de.randi2.controller.DispatcherServlet"
	import="java.util.GregorianCalendar"
	import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="de.randi2.model.fachklassen.beans.*"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"%>
<%
if (request.getAttribute(DispatcherServlet.NACHRICHT_OK) != null) {
%>
<%@page import="de.randi2.utility.Config"%>
<center><br><div class="nachricht" id="erfolgreich_nachricht">
<%=request.getAttribute(DispatcherServlet.NACHRICHT_OK)%>
</div></center>
<%}%>
<%if (request.getAttribute(DispatcherServlet.FEHLERNACHRICHT) != null) {%>
<center><div class="nachricht" id="fehler_nachricht">
<%=request.getAttribute(DispatcherServlet.FEHLERNACHRICHT)%>
</div></center><br>
<%}%>
<script>
</script>
<noscript> 
<center>
<div class="nachricht" id="JS_FEHLT"><%=Config.getProperty(Config.Felder.RELEASE_JS_NICHT_AKTIVIERT) %>
</div>
</center>
</noscript>
