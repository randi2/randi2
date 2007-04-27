<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Studienverwaltung</title>
<%@ page import="de.randi2.model.fachklassen.beans.*" %>

</head>
<body>
<%@include file="include/inc_header.jsp"%>


<div id="content">
<form action="DispatcherServlet" method="post">
<input type="hidden" name="" value="">

<h1>Studienleiter anlegen</h1>
<%@include file="include/inc_nachricht.jsp"%>
<fieldset style="width: 60%"><legend><b>Pers&ouml;nliche
Angaben</b></legend>
<table height="20%">
	<tr>
		<td>Titel<br>
		<select name="Titel" tabindex="1">
			<option <% if(request.getAttribute("Titel")==null){out.print("selected");} %> value="kein Titel" >kein Titel</option>
			<option <% if(request.getAttribute("Titel")!=null&&request.getAttribute("Titel").equals("Dr.")){out.print("selected");} %> value="Dr." >Dr.</option>
			<option <% if(request.getAttribute("Titel")!=null&&request.getAttribute("Titel").equals("Prof.")){out.print("selected");} %> value="Prof." >Prof.</option>
			<option <% if(request.getAttribute("Titel")!=null&&request.getAttribute("Titel").equals("Prof. Dr.")){out.print("selected");} %> value="Prof. Dr." >Prof. Dr.</option>
		</select><br>
	</tr>
	<tr>
		<td>Vorname * <input type="text" size="25" maxlength="30"
			name="Vorname" value="<%if(request.getAttribute("Vorname")!=null){out.print(request.getAttribute("Vorname"));} %>" tabindex="2"></td>
		<td>Nachname * &nbsp;&nbsp;&nbsp;<input type="text" size="25"
			maxlength="30" name="Nachname" tabindex="3"></td>
	</tr>
	<tr>
		<td>Login-Name * &nbsp;&nbsp;&nbsp;<input type="text" size="25"
			maxlength="30" name="Nachname" value="<%if(request.getAttribute("Nachname")!=null){out.print(request.getAttribute("Nachname"));} %>" tabindex="4"></td>
	</tr>
</table>
</fieldset>
<br>
<fieldset style="width: 60%"><legend><b>Kontaktdaten</b></legend>

<table>
	<tr>
		<td>E-Mail Adresse<br>
		<input type="text" size="25" maxlength="30" name="Email" value="<%if(request.getAttribute("Email")!=null){out.print(request.getAttribute("Email"));} %>" tabindex="5"><br>
		</td>

		<td>Fax&nbsp;&nbsp;&nbsp;<br>
		<input type="text" size="25" maxlength="30" name="Email" value="<%if(request.getAttribute("Fax")!=null){out.print(request.getAttribute("Fax"));} %>" tabindex="6"></td>
	</tr>
	<tr>
		<td><br>
		Telefonnummer *<br>
		<input type="text" size="25" maxlength="30" name="Telefon" value="<%if(request.getAttribute("Telefon")!=null){out.print(request.getAttribute("Telefon"));} %>"
			tabindex="7"></td>
	</tr>
	<tr>
		<td><br>
		Telefonnummer(Mobil)<br>
		<input type="text" size="25" maxlength="30" name="Fax" value="<%if(request.getAttribute("Handy")!=null){out.print(request.getAttribute("Handy"));} %>" tabindex="8"></td>
	</tr>
	<tr>
		<td><br>
		Telefonnummer(Sekret&auml;rin) *<br>
		<input type="text" size="25" maxlength="30" name="tel_sekretaer" value="<%if(request.getAttribute("Telefon")!=null){out.print(request.getAttribute("Telefon"));} %>"
			tabindex="9"></td>
	</tr>
</table>
</fieldset>
<br>

&nbsp;Felder mit '*' sind Pflichtfelder und m&uuml;ssen ausgef&uuml;llt
werden.</form>
<table>
	<tr>
		<td><input type="submit" name="anlegen" value="Anlegen"
			tabindex="11">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="submit" name="abbrechen"
			value="   Zur&uuml;ck   " tabindex="12"></td>
	</tr>
</table>

<br>
<%@include file="include/inc_footer.jsp"%></div>
<div id="show_none"></div>
<div id="show_none">
<div id="sub_SV"><%@include file="include/inc_menue.jsp"%>
</div>
</div>
</body>
</html>
