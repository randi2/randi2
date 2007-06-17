<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: Benutzer anlegen</title>

<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.controller.DispatcherServlet"%>
<script type="text/javascript" src="js/prototype.js"></script>
<script type="text/javascript" src="js/zebda.js"></script>
<script type="text/javascript" src="js/passwordmeter.js"></script>

</head>
<body>
<%@include file="include/inc_header_clean.jsp"%>

<div id="content">
<form action="DispatcherServlet" method="post" name="user" id="user"><input
	type="hidden" name="anfrage_id"
	value="<%=DispatcherServlet.anfrage_id.JSP_BENUTZER_ANLEGEN_DREI_BENUTZER_REGISTRIEREN_VIER.name() %>">
<h1>Benutzer anlegen</h1>
<%@include file="include/inc_nachricht.jsp"%>
<fieldset style="width: 60%"><legend><b>Pers&ouml;nliche
Angaben</b></legend>
<table height="244">
	<tr>
		<td>Titel<br>
		<select name="Titel">
			<%
					for (PersonBean.Titel e : PersonBean.Titel.values()) {
					out.print("<option value=\"" + e.toString() + "\"");//1. Option Teil
					//Ist der Titel bereits selected?!
					if ((PersonBean.Titel) request.getAttribute("Titel") != null
					&& ((PersonBean.Titel) request.getAttribute("Titel")) == e) {
						out.print("selected");
					}
					//Ende Option, Option Text
					out.print(">" + e.toString() + "</option>");

				}
			%>
		</select><br>
		</td>
	</tr>
	<tr>
		<td>Vorname *<br>
		<input type="text" size="25" maxlength="30" name="Vorname"
			value="<%if(request.getAttribute("Vorname")!=null){out.print(request.getAttribute("Vorname"));} %>"
			tabindex="2"
			z:length="{max: 50, min: 2}"
			z:length_message="Vorname muss 2 bis 50 Zeichen lang sein"></td>
		<td>&nbsp;&nbsp;&nbsp;Nachname *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Nachname"
			value="<%if(request.getAttribute("Nachname")!=null){out.print(request.getAttribute("Nachname"));} %>"
			tabindex="3"
			z:length="{max: 50, min: 2}"
			z:length_message="Nachname muss 2 bis 50 Zeichen lang sein"></td>
	</tr>
	<tr>
		<td>Geschlecht *<br>
		<input type="radio" name="Geschlecht" value="w"
			<%if(request.getAttribute("Geschlecht")!=null&&request.getAttribute("Geschlecht").equals("w")){out.print("checked");} %>>weiblich
		<input type="radio" name="Geschlecht" value="m"
			<%if(request.getAttribute("Geschlecht")!=null&&request.getAttribute("Geschlecht").equals("m")){out.print("checked");} %>>m&auml;nnlich</td>
	</tr>
	<tr>
		<td>Passwort *<br>
		<input type="password" size="25" maxlength="30" name="Passwort"
			id="Passwort"
			value="<%if(request.getAttribute("Passwort")!=null){out.print(request.getAttribute("Passwort"));} %>"
			tabindex="6"
			onkeyup="testPassword(document.forms.user.Passwort.value);"
			z:length="{min: 6}"
			z:length_message="Passwort muss mind. 6 Zeichen lang sein"></td>
		<td><a id=Words>
		<table border=0 cellpadding=0 cellspacing=0>
			<tr>
				<td class=bold width=100>St&auml;rke:</td>
				<td>
				<table cellpadding=0 cellspacing=2>
					<tr>
						<td height=15 width=150 bgcolor=#dddddd></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</a></td>
	</tr>
	<tr>
		<td>Passwort wiederholen *<br>
		<input type="password" size="25" maxlength="30" name="Passwort_wh"
			value="<%if(request.getAttribute("Passwort_wh")!=null){out.print(request.getAttribute("Passwort_wh"));} %>"
			tabindex="7"
			z:length="{min: 6}"
			z:length_message="Wiederholungs-Passwort muss ebenfalls mind. 6 Zeichen lang sein"></td>
	</tr>
</table>
</fieldset>
<br>
<fieldset style="width: 60%"><legend><b>Kontaktdaten</b></legend>
<table>
	<tr>
		<td>E-Mail *<br>
		<input type="text" size="25" maxlength="30" name="Email"
			value="<%if(request.getAttribute("Email")!=null){out.print(request.getAttribute("Email"));} %>"
			tabindex="8" z:required="true"
			z:required_message="Bitte eMail-Adresse eingeben"
			z:length="{max: 255}"
			z:length_message="Email-Adresse darf max. 255 Zeichen lang sein"></td>
		<td>&nbsp;&nbsp;&nbsp;Telefonnummer *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Telefon"
			value="<%if(request.getAttribute("Telefon")!=null){out.print(request.getAttribute("Telefon"));} %>"
			tabindex="9"
			z:length="{max: 26, min: 6}"
			z:length_message="Telefonnummer muss 6 bis 26 Zeichen lang sein"></td>
	</tr>
	<tr>
		<td>Handy<br>
		<input type="text" size="25" maxlength="30" name="Handy"
			value="<%if(request.getAttribute("Handy")!=null){out.print(request.getAttribute("Handy"));} %>"
			tabindex="10" /> <!-- z:length="{max: 26, min: 7}"
			z:length_message="Handynummer muss 7 bis 26 Zeichen lang sein"--></td>
		<td>&nbsp;&nbsp;&nbsp;Fax<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30"
			name="Fax"
			value="<%if(request.getAttribute("Fax")!=null){out.print(request.getAttribute("Fax"));} %>"
			tabindex="11" /><!--  z:required="false" z:length="{max: 26, min: 6}"
			z:length_message="Faxnummer muss 6 bis 26 Zeichen lang sein"--></td>
	</tr>
</table>
</fieldset>
<br>


<input type="submit" name="anlegen" value="Anlegen" tabindex="13">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</form>
<br>
&nbsp;Die mit '*' gekennzeichneten Felder sind Pflichtfelder. <%@include
	file="include/inc_footer_clean.jsp"%></div>
</body>
</html>
