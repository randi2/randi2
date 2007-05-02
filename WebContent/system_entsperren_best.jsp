<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%--jsp:setProperty name="user" property="*" /--%>
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
<title>Randi2 :: System entsperren</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
	<div id="content">
		<h1>System entsperren</h1>
			<form>
				<fieldset style="width:60%">
				<legend><b>System</b></legend>
					<table>
					<tr>
						<td>Sind Sie sicher, dass Sie das System entsperren wollen?</td>
					</tr>
					<tr>
						<td>
						<input type="button" name="entsp_ja" value="Ja" tabindex="1" onclick="location.href=''">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="entsp_nein" value="Nein" tabindex="2" onclick="location.href=''"></td>
					</tr>
					</table>

			</fieldset>
		</form>
	</div>

<%@ include file="include/inc_footer.jsp"%>
<div id="show_none"><%@include file="include/inc_menue.jsp"%></div>
</body>
</html>
