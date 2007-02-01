<%--

	DIRTY FIX, um das Menue Rollenspezifisch anzuzeigen

	unterscheidung zwischen den usernames
	- sysop
	- admin
	- sa
	- sl
	- stat

--%>

<div id="menue">

<div id="navigation">
<% if (!user.getUsername().equals("stat")) { %>
<ul>   
	<li class="top_m">Benutzerverwaltung</li>
<% if (!user.getUsername().equals("sysop")) { %>
	<li><a  class="sub_BV n" href="daten_aendern.jsp">Daten &auml;ndern</a></li>
<% } %>
<% if (user.getUsername().equals("admin")) { %>
	<li><a class="sub_BV n" href="admin_liste.jsp">Benutzer anzeigen</a></li>
<% } %>
<% if (user.getUsername().equals("sl")) { %>
	<li><a class="sub_BV n" href="studienaerzte_liste.jsp">Studien&auml;rzte anzeigen</a></li>
<%	} %>
<% if (user.getUsername().equals("sysop")) { %>
	<li><a class="sub_BV n" href="admin_liste.jsp">Admins anzeigen</a></li>
<%	} %>
</ul>
<% } %>
<% if (user.getUsername().equals("sl") || user.getUsername().equals("admin")) { %>
<ul>
	<li class="top_m">Zentrenverwaltung</li>
	<li><a class="sub_ZV n entry" href="zentrum_anzeigen.jsp">Zentren anzeigen</a></li>
	<% if (user.getUsername().equals("admin")) { %>
	<li><a class="sub_ZV n entry" href="zentrum_anlegen.jsp">Zentrum anlegen</a></li>
	<% } %>
</ul>
<% } %>
<% if (!user.getUsername().equals("sysop")) { %>
<ul>
	<li class="top_m">Studienverwaltung</li>
	<% if (user.getUsername().equals("admin")) { %>
	<li><a class="sub_SV n entry" href="studie_auswaehlen.jsp">Studien anzeigen</a></li>
	<% } %>
	<% if (user.getUsername().equals("sl")|| user.getUsername().equals("sa")||user.getUsername().equals("stat")) { %>
	<li><a class="sub_SV n entry" href="studie_ansehen.jsp">Studie ansehen</a></li>
	<% } %>	
	<% if (user.getUsername().equals("sa")) { %>
	<li><a class="sub_SV n entry" href="patient_hinzufuegen.jsp">Patient hinzuf&uuml;gen</a></li>
	<% } %>
</ul>
<% } %>
<% if (user.getUsername().equals("sysop") || user.getUsername().equals("admin")) { %>
<ul>
	<li class="top_m">Systemadministration</li>
	<% if (user.getUsername().equals("sysop")) { %>		
	<li class="last"><a class="sub_SA n entry" href="system_sperren.jsp">System sperren</a></li>
	<li class="last"><a class="sub_SA n entry" href="admin_anlegen.jsp">Admin anlegen</a></li>
	<% } else { %>
	<li class="last"><a class="sub_SA n entry" href="studienleiter_anlegen.jsp">Studienleiter anlegen</a></li>
	<% } %>
</ul>
<% } %>
</div>
</div>
