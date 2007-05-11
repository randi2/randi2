<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
import="de.randi2.model.fachklassen.AktivierungBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"%>
<jsp:setProperty name="user" property="*" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: Arm der Studie beenden</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Arm beenden</h1>
<b>Name Studie</b>
<form>
<table>
	<tbody>
		<tr>
			<td id="strich"></td>
		</tr>
		<tr>
			<td><input name="armbeenden" value="Arm der Studie beenden"
				onclick="" type="submit"></td>
		</tr>
	</tbody>
</table>
<table>
	<tbody>
		<tr>
			<td id="strich"></td>
		</tr>
		<tr>
			<td id="strich"></td>
		</tr>
	</tbody>
</table>
<br>
</form>
<%@include file="include/inc_footer.jsp"%></div>
<div id="show_none"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>

