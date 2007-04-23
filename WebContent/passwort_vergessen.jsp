<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import= "de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import= "java.util.GregorianCalendar"
	import= "java.text.SimpleDateFormat" 
	import= "java.util.Locale"
%>
<jsp:setProperty name="user" property="*" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Passwort vergessen</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header_clean.jsp"%>

<div id="content">  

<h1>Passwort anfordern</h1> 
<br>
<form>
<fieldset style="width:60%">
	<legend><b>Passwort anfordern<br></b></legend>
		<table>
		<tr>
		<td>Benutzername<br><input type="text" size="20" maxlength="10" name="Initialen" tabindex="1"></td>
	
		</tr>
		  
		</table>
</fieldset>
</form>
<br>
<table> 
		<tr>
		<td><input type="button" name="anfordern" value="Anfordern" tabindex="2">&nbsp;&nbsp;&nbsp;</td>
		<td><input type="button" name="abbrechen" value="Abbrechen" tabindex="3" onClick="top.location.href='index.jsp'"></td>
		</tr>
		</table>
		
		<%@include file="include/inc_footer.jsp"%>
	</div>
	<div id="show_none">
</div>
<div id="show_none">

</div>
</body>
</html>