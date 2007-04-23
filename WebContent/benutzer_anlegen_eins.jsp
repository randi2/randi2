<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">
<html>
<%@ page import="de.randi2.controller.DispatcherServlet" %>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>BRandi2 :: enutzer anlegen</title>

</head>
<body>
<%@include file="include/inc_header_clean.jsp"%>

<div id="content">
<form>
<h1>Benutzer anlegen</h1>

<fieldset style="width: 60%">
	<legend><b>Haftungsausschluss</b></legend>
		<p align="justify"><i>Mit dem Urteil vom 12. Mai 1998 hat das Landgericht Hamburg 
		entschieden, dass man durch die Anbringung eines Links die Inhalte der gelinkten 
		Seiten ggf. mit zu verantworten hat. Dies kann nur dadurch verhindert werden, 
		dass man sich ausdrücklich von diesem Inhalt distanziert.
		Für alle Links auf dieser Homepage gilt: Ich distanziere mich hiermit ausdrücklich von 
		allen Inhalten aller gelinkten Seiten auf dieser Homepage und mache mir diese Inhalte 
		nicht zu Eigen.</i></p>
</fieldset>
</form>
<form action="DispatcherServlet" method="POST">
		<table>
		<tr>
		<td>
		<input type="hidden" name="anfrage_id" value="<%=DispatcherServlet.anfrage_id.JSP_BENUTZER_ANLEGEN_EINS_BENUTZER_REGISTRIEREN_ZWEI.name() %>">
		<input type="submit" tabindex="1" value="Akzeptieren">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="button" name="abbrechen" value="Abbrechen" tabindex="2" onClick="location.href='index.jsp'"></td>
		</tr>
		</table>
</form>
				<%--<%@include file="include/inc_footer.jsp"%>--%>
	</div>
	<div id="show_none">

	</div>
<div id="show_none">
</div>
</body>
</html>
