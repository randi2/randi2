<%if(request.getAttribute("fehlernachricht")!=null){%>
<center>
	<div id="nachricht">
	<%= request.getAttribute("fehlernachricht")%>
	</div>
</center>
<%} %>
