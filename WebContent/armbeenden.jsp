<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import= "de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import= "java.util.GregorianCalendar"
	import= "java.text.SimpleDateFormat" 
	import= "java.util.Locale"
%>
<jsp:setProperty name="user" property="*"/> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Randi2 :: Arm der Studie beenden</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%> 
<div id="content">
<h1>Arm beenden</h1> 
<form>
	<legend><b>Name Studie</b></legend>		
		<table>
		<tr>
		<td id="strich"></td>
		</tr>		
		<tr>
		<td>
		<input type="submit" name="armbeenden" value="Arm der Studie beenden" onClick=""></td>
		</tr>	
		</table>
<table><br>
<tr>
<td id="strich"></td>
</tr>
<tr>
<td id="strich"></td>
</tr>
</table><br>

</form>
		
<%@include file="include/inc_footer.jsp"%>
</div>
<div id="show_none">
<%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>