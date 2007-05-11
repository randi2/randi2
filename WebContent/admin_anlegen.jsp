<%@ page import="de.randi2.model.fachklassen.beans.PersonBean"
		import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
		 import= "de.randi2.controller.DispatcherServlet" 
		 import="de.randi2.model.fachklassen.AktivierungBean"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<form method="post" action="DispatcherServlet">
	<input type="hidden" name="anfrage_id" value="<%=DispatcherServlet.anfrage_id.AKTION_ADMIN_ANLEGEN.name() %>">
	<fieldset style="width: 60%">
	<legend><b>Pers&ouml;nliche Angaben</b></legend>
	<label for="Titel">Titel*:</label><br>
	<table>
	<tr>
		<td>
		<select id="Titel" name="Titel" tabindex="1">
		<%
		for (PersonBean.Titel aTitel: PersonBean.Titel.values()){
		    %>
		    <option value="<%=aTitel.toString()%>" ><%=aTitel.toString()%></option>
		    <%		    
		}
		%>
		</select>
		</td>
	</tr>
	<tr>
		<td><label for="Vorname">Vorname *</label><br>
		<input type="text" size="25" maxlength="30" id="Vorname" name="Vorname" tabindex="2" z:required="true" z:message="Bitte Vornamen angeben"></td>
		<td><label for="Nachname">Nachname *</label><br>
		<input type="text" size="25" maxlength="30" id="Nachname" name="Nachname" tabindex="3" z:required="true" z:message="Bitte Nachnamen angeben"></td>
	</tr>
	<tr>
		<td>Geschlecht *<br>
		<input type="radio" id="maennlich" name="Geschlecht" value="m"><label for="maennlich">m&auml;nnlich</label>
		<input type="radio" id="weiblich" name="Geschlecht" value="w"><label for="weiblich">weiblich</label>
		</td>
	</tr>
	</table>	
	</fieldset>
	<fieldset style="width: 60%"><legend><b>Kontaktdaten</b></legend>
	<table>
	<tr>
		<td><label for="EMail">E-Mail Adresse *</label><br>
		<input type="text" size="25" maxlength="30" id="EMail" name="EMail" tabindex="5" z:required="true" z:message="Bitte E-Mail angeben" z:email="true"
z:email_message="Bitte geben sie eine gültige E-Mail Adresse"></td>
		<td>&nbsp;&nbsp;&nbsp;<label for="Telefon">Telefonnummer *</label><br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30" id="Telefon" name="Telefon" tabindex="6" z:required="true" z:message="Bitte Telefonnummer angeben"></td>
	</tr>
	<tr>
		<td><label for="Fax">Fax<br>
		<input type="text" size="25" maxlength="30" id="Fax" name="Fax" tabindex="7"></label></td>
		<td>&nbsp;&nbsp;&nbsp;<label for="Institut">Institut *<br>
		&nbsp;&nbsp;&nbsp;<input type="text" size="25" maxlength="30" id="Institut" name="Institut" tabindex="8"></label></td>
	</tr>
</table>
</fieldset>
Felder mit '*' sind Pflichtfelder und m&uuml;ssen
ausgef&uuml;llt werden.
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
