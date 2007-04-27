<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: Administrator anlegen</title>

<%@ page import="de.randi2.model.fachklassen.beans.*" %>
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<form action="DispatcherServlet" method="post">
<input type="hidden" name="" value="">
<input type="hidden" name="" value ="">

<h1>Administrator anlegen</h1>
<%@include file="include/inc_nachricht.jsp"%>
<fieldset style="width: 60%"><legend><b>Pers&ouml;nliche
Angaben</b></legend>
<table heiht="20%">
	<tr>
		<td>Titel *<br>

		<select name="Titel" tabindex="1">
			<option <% if(request.getAttribute("Titel")==null){out.print("selected");} %> value="kein Titel" >kein Titel</option>
			<option <% if(request.getAttribute("Titel")!=null&&request.getAttribute("Titel").equals("Dr.")){out.print("selected");} %> value="Dr." >Dr.</option>
			<option <% if(request.getAttribute("Titel")!=null&&request.getAttribute("Titel").equals("Prof.")){out.print("selected");} %> value="Prof." >Prof.</option>
			<option <% if(request.getAttribute("Titel")!=null&&request.getAttribute("Titel").equals("Prof. Dr.")){out.print("selected");} %> value="Prof. Dr." >Prof. Dr.</option>
		</select><br>
	</tr>
	<tr>
		<td>Vorname *<br>
		<input type="text" size="25" maxlength="30" name="Vorname" value="<%if(request.getAttribute("Vorname")!=null){out.print(request.getAttribute("Vorname"));} %>"
			tabindex="2"></td>
		<td>Nachname *<br>
		<input type="text" size="25" maxlength="30" name="Nachname" value="<%if(request.getAttribute("Nachname")!=null){out.print(request.getAttribute("Nachname"));} %>"
			tabindex="3"></td>
	</tr>
	<tr>
		<td>Geschlecht *<br>
		<input type="radio" name="weiblich" <% if(request.getAttribute("weiblich")!=null){out.print("checked");} %>>weiblich <input
			type="radio" name="maennlich" <% if(request.getAttribute("maennlich")!=null){out.print("checked");} %>>m&auml;nnlich</td>
	</tr>
</table>
</fieldset>
<br>
<fieldset style="width: 60%"><legend><b>Kontaktdaten</b></legend>
<table>
	<tr>
		<td>E-Mail Adresse *<br>
		<input type="text" size="25" maxlength="30" name="Email" tabindex="5"></td>
		<td>&nbsp;&nbsp;&nbsp;Telefonnummer *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Telefon" <%if(request.getAttribute("Email")!=null){out.print(request.getAttribute("Email"));} %>" tabindex="6"></td>
	</tr>
	<tr>
		<td>Fax<br>
		<input type="text" size="25" maxlength="30" name="Fax" tabindex="7"></td>
		<td>&nbsp;&nbsp;&nbsp;Institut *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Institut" value="<%if(request.getAttribute("Fax")!=null){out.print(request.getAttribute("Fax"));} %>" tabindex="8"></td>
	</tr>
</table>
</fieldset>
</form>
<form>&nbsp;Felder mit '*' sind Pflichtfelder und m&uuml;ssen
ausgef&uuml;llt werden.</form>
<table>
	<tr>
		<td><input type="submit" name="anlegen" value="Anlegen"
			tabindex="9">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="submit" name="abbrechen"
			value="   Zur&uuml;ck   " tabindex="10"></td>
	</tr>
</table>
</div>
<div id="show_none"></div>

<div id="show_SA"><%@include file="include/inc_menue.jsp"%></div>

</body>
</html>
