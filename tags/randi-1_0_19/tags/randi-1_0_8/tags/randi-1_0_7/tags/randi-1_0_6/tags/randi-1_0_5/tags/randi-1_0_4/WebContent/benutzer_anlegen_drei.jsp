<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Benutzer anlegen</title>
<%@ page import="de.randi2.model.fachklassen.beans.*" %>
</head>
<body>
<%@include file="include/inc_header_clean.jsp"%>

<div id="content">
<form action="DispatcherServlet" method="post">
<input type="hidden" name="anfrage_id" value="JSP_BENUTZER_ANLEGEN_DREI_BENUTZER_REGISTRIEREN_VIER">
<input type="hidden" name="aZentrum" value ="<%=request.getAttribute("aZentrum") %>">
><h1>Benutzer anlegen</h1>
<%@include file="include/inc_nachricht.jsp"%>
<fieldset style="width: 60%"><legend><b>Pers&ouml;nliche
Angaben</b></legend>
<table height="244">
	<tr>
		<td>Titel<br>
		<select name="Titel">
			<option <% if(request.getAttribute("Titel")==null){out.print("selected");} %> value="kein Titel" >kein Titel</option>
			<option <% if(request.getAttribute("Titel")!=null&&request.getAttribute("Titel").equals("Dr.")){out.print("selected");} %> value="Dr." >Dr.</option>
			<option <% if(request.getAttribute("Titel")!=null&&request.getAttribute("Titel").equals("Prof.")){out.print("selected");} %> value="Prof." >Prof.</option>
			<option <% if(request.getAttribute("Titel")!=null&&request.getAttribute("Titel").equals("Prof. Dr.")){out.print("selected");} %> value="Prof. Dr." >Prof. Dr.</option>
		</select><br>
		</td>
	</tr>
	<tr>
		<td>Vorname *<br>
		<input type="text" size="25" maxlength="30" name="Vorname" value="<%if(request.getAttribute("Vorname")!=null){out.print(request.getAttribute("Vorname"));} %>"
			tabindex="2"></td>
		<td>&nbsp;&nbsp;&nbsp;Nachname *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Nachname" tabindex="3" value="<%if(request.getAttribute("Nachname")!=null){out.print(request.getAttribute("Nachname"));} %>"></td>
	</tr>
	<tr>
		<td>Geschlecht *<br>
		<input type="radio" name="weiblich" <% if(request.getAttribute("weiblich")!=null){out.print("checked");} %> >weiblich 
		<input type="radio" name="maennlich" <% if(request.getAttribute("maennlich")!=null){out.print("checked");} %>>m&auml;nnlich</td>
	</tr>
	<tr>
		<td>Passwort *<br>
		<input type="password" size="25" maxlength="30" name="Passwort" value="<%if(request.getAttribute("Passwort")!=null){out.print(request.getAttribute("Passwort"));} %>"
			tabindex="6"></td>
	</tr>
	<tr>
		<td>Passwort wiederholen *<br>
		<input type="password" size="25" maxlength="30" name="Passwort_wh" value="<%if(request.getAttribute("Passwort_wh")!=null){out.print(request.getAttribute("Passwort_wh"));} %>"
			tabindex="7"></td>
	</tr>
</table>
</fieldset>
<br>
<fieldset style="width: 60%"><legend><b>Kontaktdaten</b></legend>
<table>
	<tr>
		<td>E-Mail *<br>
		<input type="text" size="25" maxlength="30" name="Email" tabindex="8" value="<%if(request.getAttribute("Email")!=null){out.print(request.getAttribute("Email"));} %>"></td>
		<td>&nbsp;&nbsp;&nbsp;Telefonnummer *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Telefon" tabindex="9" value="<%if(request.getAttribute("Telefon")!=null){out.print(request.getAttribute("Telefon"));} %>"></td>
	</tr>
	<tr>
		<td>Handy<br>
		<input type="text" size="25" maxlength="30" name="Handy" tabindex="10" value="<%if(request.getAttribute("Handy")!=null){out.print(request.getAttribute("Handy"));} %>"></td>
		<td>&nbsp;&nbsp;&nbsp;Fax<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30" name="Fax" tabindex="11" value="<%if(request.getAttribute("Fax")!=null){out.print(request.getAttribute("Fax"));} %>"></td>
	</tr>
</table>
</fieldset>
<br>



<table>
	<tr>
		<td><input type="submit" name="anlegen" value="Anlegen"
			tabindex="13">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="submit" name="abbrechen"
			value="   Zur&uuml;ck   " tabindex="14"
			onClick="location.href='benutzer_anlegen_zwei'"></td>
	</tr>
</table>
</form>
<br>
&nbsp;Die mit '*' gekennzeichneten Felder sind Pflichtfelder. <%@include
	file="include/inc_footer_clean.jsp"%></div>
</body>
</html>
