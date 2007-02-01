<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%-- pruefe auf Welche Seite wir verzweigen, abhaengig vom Account --%>    
<%
	String username = request.getParameter("username");
%>
<% if(username.equals("sa")||username.equals("sl")) { %>
	<jsp:include page="studie_auswaehlen.jsp"/>

<% } if(username.equals("sysop")||username.equals("admin")) { %>
	<jsp:include page="global_welcome.jsp"/>
<% } if (username.equals("stat")) {%>
	<jsp:include page="studie_ansehen.jsp"/>
<% } %>