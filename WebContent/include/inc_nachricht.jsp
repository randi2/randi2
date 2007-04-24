<%@ page import="de.randi2.controller.DispatcherServlet" %>
<%if(request.getAttribute(DispatcherServlet.FEHLERNACHRICHT)!=null){%>
<center>
	<div id="nachricht" class="warnung">
	<%= request.getAttribute(DispatcherServlet.FEHLERNACHRICHT)%>
	</div>
</center>
<%} %>
