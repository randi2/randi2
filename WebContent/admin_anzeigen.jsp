<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import= "de.randi2.model.fachklassen.beans.*"
	import= "java.util.GregorianCalendar"
	import= "java.text.SimpleDateFormat" 
	import= "java.util.Locale"
%>
<jsp:setProperty name="user" property="*" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Randi2 :: Benutzerverwaltung</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1> Admin-Liste </h1>
<table  width="90%">
<tr class="tblrow1"  align="left">
	<th width="5%"> Titel </th>
	<th width="20%"> Nachname </th>
	<th width="20%"> Vorname </th>
	<th width="20%"> Geschlecht </th>
	<th width="30%"> Institut </th>
	
</tr>
<tr class="tblrow2" align="left">
	<td>Dr.</td>
	<td>Wurscht</td>
	<td>Hans</td>
	<td>m&auml;nnlich</td>
	<td>Uli Hoenes Wurst GmbH</td>

</tr>
</table><br><br><br><br><br><br>


<table width="90%">
<tr class="tblrow1"  align="left">
	<th width="25%"> E-Mail </th>
	<th width="20%"> Telefonnummer </th>
	<th width="20%"> Handynummer </th>
	<th width="30%"> Fax </th>
</tr>
<tr class="tblrow2" align="left">
	<td>wurscht@wurscht.de</td>
	<td>123456678</td>
	<td>876287364786</td>
	<td>872638746876</td>
</tr>
</table><br><br><br><br><br><br>


<table width="90%">
<tr class="tblrow1"  align="left">
	<th width="25%"> Nachname Ansprechpartner </th>
	<th width="20%"> Vorname Ansprechpartner </th>
	<th width="50%"> Telefonnummer Ansprechpartner </th>
</tr>
<tr class="tblrow2" align="left">
	<td>K&auml;se</td>
	<td>H&uuml;tten</td>
	<td>987298374987987</td>
</tr>
</table>
<p></p>
<table align="left">
			<tr>
				<td><input type="button" name="zurueck" value="Zur&uuml;ck"
					tabindex="1" onclick="location.href='admin_liste.jsp'"></td>
			</tr>
		</table>

	<%@include file="include/inc_footer.jsp"%>
  </div>
	<div id="show_none">
	</div>
<div id="show_none">
<%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>