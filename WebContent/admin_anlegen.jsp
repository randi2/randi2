<%@ page import="de.randi2.model.fachklassen.beans.PersonBean"
	import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="de.randi2.controller.DispatcherServlet"
	import="de.randi2.model.fachklassen.beans.StudieBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
String vorname = (String)request.getAttribute("Vorname");
String nachname = (String)request.getAttribute("Nachname");
String benutzername = (String)request.getAttribute("Benutzername");
String email = (String)request.getAttribute("Email");
String telefon = (String)request.getAttribute("Telefon");
String fax = (String)request.getAttribute("Fax");
String institut = (String)request.getAttribute("Institut");
String vornameA = (String)request.getAttribute("VornameA");
String nachnameA = (String)request.getAttribute("NachnameA");
String telefonA = (String)request.getAttribute("TelefonA");

	if (vorname == null)vorname ="";
	if(nachname == null)nachname="";
	if(benutzername == null)benutzername="";
	if(email == null)email="";
	if(telefon == null)telefon="";
	if(fax == null)fax="";
	if(institut == null)institut="";
	if(vornameA == null)vornameA="";
	if(nachnameA == null)nachnameA="";
	if(telefonA == null)telefonA="";
%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: Administrator anlegen</title>
<script type="text/javascript" src="js/prototype.js"></script>
<script type="text/javascript" src="js/zebda.js"></script>

</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Administratorenkonto anlegen</h1>
<%@include file="include/inc_nachricht.jsp"%>
<form method="post" action="DispatcherServlet"><input
	type="hidden" name="anfrage_id"
	value="<%=DispatcherServlet.anfrage_id.AKTION_ADMIN_ANLEGEN.name() %>">
<fieldset style="width: 60%"><legend><b>Pers&ouml;nliche
Angaben</b></legend> <label for="Titel">Titel*:</label><br>
<table>
	<tr>
		<td><select id="Titel" name="Titel" tabindex="1">
			<%
		for (PersonBean.Titel aTitel: PersonBean.Titel.values()){
		    %>
			<option value="<%=aTitel.toString()%>"><%=aTitel.toString()%></option>
			<%		    
		}
		%>
		</select></td>
	</tr>
	<tr>
		<td><label for="Vorname">Vorname *</label><br>
		<input type="text" size="25" maxlength="30" id="Vorname"
			name="Vorname" tabindex="2" value="<%=vorname%>" 
			z:required="true"
			z:message="Bitte Vornamen angeben"></td>
		<td><label for="Nachname">Nachname *</label><br>
		<input type="text" size="25" maxlength="30" id="Nachname" value="<%=nachname%>"
			name="Nachname" tabindex="3" z:required="true"
			z:message="Bitte Nachnamen angeben"></td>
	</tr>
	<tr>
		<td>Geschlecht *<br>
		<input type="radio" id="maennlich" name="Geschlecht" value="m"><label
			for="maennlich">m&auml;nnlich</label> <input type="radio"
			id="weiblich" name="Geschlecht" value="w"><label
			for="weiblich">weiblich</label></td>
		<td>Benutzername *<br>
		<input type="text" size="25" maxlength="30" id="Benutzername"
		name="Benutzername" tabindex="" value="<%=benutzername %>"
		z:required="true"
		z:message="Bitte Benutzernamen angeben"></td>
	</tr>
</table>
</fieldset>
<br>
<fieldset style="width: 60%"><legend><b>Kontaktdaten</b></legend>
<table>
	<tr>
		<td><label for="EMail">E-Mail Adresse *</label><br>
		<input type="text" size="25" maxlength="30" id="Email" name="Email" value="<%=email%>"
			tabindex="5" z:required="true" z:message="Bitte E-Mail angeben"
			z:email="true"
			z:email_message="Bitte geben sie eine gültige E-Mail Adresse"></td>
		<td>&nbsp;&nbsp;&nbsp;<label for="Telefon">Telefonnummer
		*</label><br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30" value="<%=telefon %>"
			id="Telefon" name="Telefon" tabindex="6" z:required="true"
			z:message="Bitte Telefonnummer angeben"></td>
	</tr>
	<tr>
		<td><label for="Fax">Fax<br>
		<input type="text" size="25" maxlength="30" id="Fax" name="Fax" value="<%=fax %>"
			tabindex="7"></label></td>
		<td>&nbsp;&nbsp;&nbsp;<label for="Institut">Institut *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30" value="<%=institut %>"
			id="Institut" name="Institut" tabindex="8"></label></td>
	</tr>
</table>
</fieldset>
<fieldset style="width: 60%"><legend><b>Angaben
zum Ansprechpartner</b></legend>
<table>
	<tr>
		<td>Vorname *<br>
		<input type="text" size="25" maxlength="30" name="VornameA" value="<%=vornameA %>"
			tabindex="12" value="$ansprechpartner_vorn"></td>
		<td>&nbsp;&nbsp;&nbsp;Nachname *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30" value="<%=nachnameA %>"
			name="NachnameA" tabindex="13" value="$ansprechpartner_nachn"></td>
	</tr>
	<tr>
		<td>Telefonnummer *<br>
		<input type="text" size="25" maxlength="30" name="TelefonA" value="<%=telefonA %>"
			tabindex="14" value="$ansprechpartner_telenr"></td>
	</tr>
</table>
</fieldset>
Felder mit '*' sind Pflichtfelder und m&uuml;ssen ausgef&uuml;llt
werden.
<table>
	<tr>
		<td><input type="submit" id="Anlegen" value="Anlegen"
			tabindex="9">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="reset" id="Abbrechen"
			value="Eingaben l&ouml;schen" tabindex="10"></td>
	</tr>
</table>
</form>
</div>
<div id="show_SA"><%@include file="include/inc_menue.jsp"%></div>

</body>
</html>
