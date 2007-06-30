<%@ page import="de.randi2.model.fachklassen.Rolle"
	import="java.util.GregorianCalendar"
	import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="de.randi2.model.fachklassen.beans.*"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"%>
<%
Rolle.Rollen aRolleMenue=((BenutzerkontoBean)request.getSession().getAttribute(DispatcherServlet.sessionParameter.A_Benutzer.toString())).getRolle().getRollenname();
Studie.Status aStudieStatusMenue = null;
if(aRolleMenue==Rolle.getStudienarzt().getRollenname()){
	aStudieStatusMenue = (Studie.Status) ((StudieBean) request.getSession().getAttribute(DispatcherServlet.sessionParameter.AKTUELLE_STUDIE.toString())).getStatus();	
}
%>
<%@page import="de.randi2.controller.DispatcherServlet"%>
<%@page import="de.randi2.utility.Parameter"%>
<%@page import="de.randi2.model.fachklassen.Studie"%>
<div id="menue">

<div id="navigation">
<%--Das einzigste Geisterformular --%>
<form action="DispatcherServlet" method="POST" name="menue_form"
			id="menue_form"><input type="hidden"
			name="<%=Parameter.anfrage_id %>" value=""></form>
<% if (aRolleMenue!=Rolle.Rollen.STATISTIKER) { %>
<ul>
	<li class="top_m">Benutzerverwaltung</li>
	<% if (aRolleMenue!=Rolle.Rollen.SYSOP) { %>
	<li><span class="sub_SA n entry" style="cursor:pointer"
			onClick="document.forms['menue_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_INC_MENUE_DATEN_AENDERN.name() %>';document.forms['menue_form'].submit();">
		Daten &auml;ndern
		</span>
	</li>
	<% } %>
	<% if (aRolleMenue==Rolle.Rollen.ADMIN) { %>
	<li class="sub_BV n">
	<span class="sub_SA n entry" style="cursor:pointer"
			onClick="document.forms['menue_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.BENUTZER_SUCHEN.name() %>';document.forms['menue_form'].submit();">
		Benutzer suchen
	</span>
	</li>
	<% } %>
	<% if (aRolleMenue==Rolle.Rollen.STUDIENLEITER) { %>
	<li class="sub_BV n">
		<span  class="sub_SA n entry" style="cursor:pointer"
			onClick="document.forms['menue_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_INC_MENUE_STUDIENAERZTE_LISTE.name() %>';document.forms['menue_form'].submit();">
		Studien&auml;rzte
	</span></li>
	<%	} %>
	<% if (aRolleMenue==Rolle.Rollen.SYSOP) { %>
	<li class="sub_BV n">
	<span  class="sub_SA n entry" style="cursor:pointer"
			onClick="document.forms['menue_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_INC_MENUE_ADMIN_LISTE.name() %>';document.forms['menue_form'].submit();">
		Admins anzeigen
	</span></li>
	<%	} %>
</ul>
<% } %> <% if (aRolleMenue==Rolle.Rollen.STUDIENLEITER) { %>
<ul>
	<li class="top_m">Zentrenverwaltung</li>
	<li class="sub_BV n">
		<span  class="sub_SA n entry" style="cursor:pointer"
			onClick="document.forms['menue_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_ZENTRUM_ANZEIGEN.name() %>';document.forms['menue_form'].submit();">
		Zentrum anzeigen</span></li>
</ul>
<% } %>  <% if (aRolleMenue==Rolle.Rollen.ADMIN) { %>
<ul>
	<li class="top_m">Zentrenverwaltung</li>
	<li class="sub_BV n">
		<span  class="sub_SA n entry" style="cursor:pointer"
			onClick="document.forms['menue_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.ZENTRUM_ANZEIGEN_ADMIN.name() %>';document.forms['menue_form'].submit();">
		Zentren anzeigen</span>
	</li>
</ul>
<% } %><% if (aRolleMenue!=Rolle.Rollen.SYSOP) { %>
<ul>
	<li class="top_m">Studienverwaltung</li>
	<% if (aRolleMenue==Rolle.Rollen.ADMIN) { %>
	<li class="sub_SV n entry">
	<span  class="sub_SA n entry" style="cursor:pointer"
			onClick="document.forms['menue_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_INC_MENUE_STUDIEN_ANZEIGEN.name() %>';document.forms['menue_form'].submit();">
		Studien	anzeigen</span></li>
	<% } %>
	<% if (aRolleMenue==Rolle.Rollen.STUDIENLEITER|| aRolleMenue==Rolle.Rollen.STUDIENARZT||aRolleMenue==Rolle.Rollen.STATISTIKER) { %>
	
	<li class="sub_BV n">
		<span  class="sub_SA n entry" style="cursor:pointer"
			onClick="document.forms['menue_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_STUDIE_ANSEHEN.name() %>';document.forms['menue_form'].submit();">
		Studie ansehen</span></li>
	
	
	<% } %>
	<% if (aRolleMenue==Rolle.Rollen.STUDIENARZT && aStudieStatusMenue==Studie.Status.AKTIV) { %>
	<li class="sub_SV n entry">
	<span  class="sub_SA n entry" style="cursor:pointer"
			onClick="document.forms['menue_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_INC_MENUE_PATIENT_HINZUFUEGEN.name() %>';document.forms['menue_form'].submit();">
		Patient	hinzuf&uuml;gen</span></li>
	<% } %>
</ul>
<% } %> <% if (aRolleMenue==Rolle.Rollen.SYSOP || aRolleMenue==Rolle.Rollen.ADMIN) { %>
<ul>
	<% if (aRolleMenue==Rolle.Rollen.SYSOP) { %>
	<li class="sub_SA n entry">
	<span  class="sub_SA n entry" style="cursor:pointer"
			onClick="document.forms['menue_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_INC_MENUE_SYSTEMSPERREN.name() %>';document.forms['menue_form'].submit();">
		System sperren</span></li>
	<li class="sub_SA n entry">
	<span  class="sub_SA n entry" style="cursor:pointer"
			onClick="document.forms['menue_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_INC_MENUE_ADMIN_ANLEGEN.name() %>';document.forms['menue_form'].submit();">
		Admin anlegen</span></li>
	<% } else { %>
	<li class="top_m">Systemadministration</li>
	<li class="sub_SA n entry">
		<span  class="sub_SA n entry" style="cursor:pointer"
			onClick="document.forms['menue_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_INC_MENUE_STUDIENLEITER_ANLEGEN.name() %>';document.forms['menue_form'].submit();">
		Studienleiter anlegen</span></li>
	<% } %>
</ul>
<% } %>
</div>
</div>
