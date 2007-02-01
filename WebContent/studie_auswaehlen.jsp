<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Studie ausw&auml;hlen</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%> 
<div id="content">
<h1>Studie ausw&auml;hlen</h1>

<% if (user.getUsername().equals("sl")) { %>
<input type="button" value="Neue Studie anlegen" onClick="top.location.href='studie_anlegen.jsp'">&nbsp;&nbsp;&nbsp;::&nbsp;&nbsp;&nbsp;
<input type="button" value="Simulation" onClick="top.location.href='simulation.jsp'"><br><br>
<% } %>
<table width="600" border="0" cellspacing="5" cellpadding="2" bgcolor="#e3e3e3">
<tr>  
<td align="left" id="filterbezeichnung">Name</td>
<td align="left" style="width: 200px"><input type="text" name="name" id="filterfeld"></td>
<td align="left" id="filterbezeichnung">Status</td>
<td align="left" style="width: 200px"><select size="1" id="filterfeld">
<option>gestartet</option>
<option>aktiv</option>
<option>pausiert</option>
<option>abgeschlossen</option>
</select></td>
</tr>
<tr>
<td align="left" id="filterbezeichnung">Zentrum</td>
<td align="left" colspan="3"><select size="1" id="filterfeld"><option>Uniklinikum Heidelberg, Hygieneinstitut</option></select></td>
</tr>
<tr>
<td align="right" colspan="4"><input type="submit" value="Aktualisieren" style="width: 100px"></td>
</tr>
</table>
<br><br>
<table width="100%" cellspacing="0" cellpadding="0">
	<tr class="tblrow1" align="center">
		<th width="30%">Name</th>
		<th width="60%">Leitendes Zentrum</th>
		<th width="10%">Status</th>
	</tr>
	<tr class="tblrow2">
		<td align="center"><a href="studie_ansehen.jsp">Aspirin</a></td>
		<td align="center">Uniklinkium Heidelberg, allg. Medizin</td>
		<td align="center">aktiv</td>
		<% if (user.getUsername().equals("admin"))  { %>
		<td> <a class="il_ContainerItemCommand" href="studie_abbrechen.jsp">abbrechen</a></td>
<% } %>
	</tr>
	<tr class="tblrow1">
		<td align="center">GemTex5</td>
		<td align="center">DKFZ</td>
		<td align="center">aktiv</td>
		<% if (user.getUsername().equals("admin"))  { %>
		<td> <a class="il_ContainerItemCommand" href="studie_abbrechen.jsp">abbrechen</a></td>
	<% } %>
	</tr>
	<tr class="tblrow2">
		<td align="center">XXX</td>
		<td align="center">AM Wroclaw, Onkologia</td>
		<td align="center">aktiv</td>
		<% if (user.getUsername().equals("admin"))  { %>
		<td> <a class="il_ContainerItemCommand" href="studie_abbrechen.jsp">abbrechen</a></td>
<% } %>
	</tr>
</table>

<%@include file="include/inc_footer.jsp"%>
</div>
<% if (user.getUsername().equals("admin"))  { %>
<%@include file="include/inc_menue.jsp"%>
<% } %>
</body>
</html>