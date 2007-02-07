<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:setProperty name="user" property="*" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>System (ent)sperren</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>

<div id="content">
<h1>System (ent)sperren</h1>
<form>
<fieldset style="width:60%">
	<legend><b>System</b></legend>
		<table >
		<tr>
		<td><input type="button" name="bestaetigen" value="Sperren" tabindex="1" onclick="location.href='system_sperren_best.jsp'">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="button" name="abbrechen" value="Entsperren" tabindex="2" onclick="location.href='system_entsperren_best.jsp'"></td>
		</tr>
		</table>

</fieldset><br>
</form>
	</div>
	<div id="show_none">
		
	</div>

<%@include file="include/inc_footer.jsp"%>
<div id="show_none">
<%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
