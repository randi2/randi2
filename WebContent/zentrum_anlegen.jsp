<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.beans.AktivierungBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.Parameter"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="de.randi2.controller.ZentrumServlet"%>
<%@page import="de.randi2.controller.DispatcherServlet"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: Neues Zentrum anlegen</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Neues Zentrum anlegen</h1>
<%@include file="include/inc_nachricht.jsp"%>
<form action="DispatcherServlet" method="POST" name="zentrum_anlegen">
<input type="hidden"
	value="<%=DispatcherServlet.anfrage_id.JSP_ZENTRUM_ANLEGEN %>"
	name="<%=Parameter.anfrage_id %>">
<fieldset style="width: 60%"><legend><b>Angaben zum Zentrum</b></legend>
<table>
	<tr>
		<td>Name der Institution *<br>
		<input type="text" size="46" maxlength="40" name="<%=Parameter.zentrum.INSTITUTION %>"   
		value="<%if(request.getAttribute(Parameter.zentrum.INSTITUTION.name())!=null){out.print(request.getAttribute(Parameter.zentrum.INSTITUTION.name()));} %>"
			tabindex="1"></td>

	</tr>
	<tr>
		<td>Name der genauen Abteilung *<br>
		<input type="text" size="46" maxlength="40" name="<%=Parameter.zentrum.ABTEILUNGSNAME %>" 
		value="<%if(request.getAttribute(Parameter.zentrum.ABTEILUNGSNAME.name())!=null){out.print(request.getAttribute(Parameter.zentrum.ABTEILUNGSNAME.name()));} %>"
			tabindex="2"></td>

	</tr>
	<tr>
		<td>Strasse *
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hausnr
		* <br>
		<input type="text" size="30" maxlength="30" name=<%=Parameter.zentrum.STRASSE %>
		value="<%if(request.getAttribute(Parameter.zentrum.STRASSE.name())!=null){out.print(request.getAttribute(Parameter.zentrum.STRASSE.name()));} %>"
			tabindex="3"> &nbsp;&nbsp;&nbsp; <input type="text"
			size="8" maxlength="8" name="<%=Parameter.zentrum.HAUSNUMMER %>"
			 value="<%if(request.getAttribute(Parameter.zentrum.HAUSNUMMER.name())!=null){out.print(request.getAttribute(Parameter.zentrum.HAUSNUMMER.name()));} %>"
			  tabindex="4"></td>

	</tr>
	<tr>
		<td>PLZ * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Ort * <br>
		<input type="text" size="6" maxlength="6" name="<%=Parameter.zentrum.PLZ%>"
		 value="<%if(request.getAttribute(Parameter.zentrum.PLZ.name())!=null){out.print(request.getAttribute(Parameter.zentrum.PLZ.name()));} %>"
		 tabindex="5"
			>&nbsp;&nbsp;&nbsp; <input type="text" size="33"
			maxlength="33" name="<%=Parameter.zentrum.ORT %>"  
			value="<%if(request.getAttribute(Parameter.zentrum.ORT.name())!=null){out.print(request.getAttribute(Parameter.zentrum.ORT.name()));} %>"
			tabindex="6"></td>

	</tr>

</table>
</fieldset>
<br>
<fieldset><legend><b>Angaben zum Ansprechpartner</b></legend>
<table>
	<tr>
		<td>Vorname *<br>
		<input type="text" size"38" maxlength="40" name="<%=Parameter.person.VORNAME %>"
		value="<%if(request.getAttribute(Parameter.person.VORNAME.name())!=null){out.print(request.getAttribute(Parameter.person.VORNAME.name()));} %>"
		tabindex="7"
			>&nbsp;&nbsp;&nbsp;</td>
		<td>Nachname *<br>
		<input type="text" size="38" maxlength="40" name="<%=Parameter.person.NACHNAME %>"
		 value="<%if(request.getAttribute(Parameter.person.NACHNAME.name())!=null){out.print(request.getAttribute(Parameter.person.NACHNAME.name()));} %>"
			tabindex="8"></td>
	</tr>
	<tr>
		<td>Telefon *<br>
		<input type="text" size="40" maxlength="40" name="<%=Parameter.person.TELEFONNUMMER %>"
		value="<%if(request.getAttribute(Parameter.person.TELEFONNUMMER.name())!=null){out.print(request.getAttribute(Parameter.person.TELEFONNUMMER.name()));} %>"
		tabindex="9"
			></td>
	</tr>
	<tr>
		<td>Fax<br>
		<input type="text" size="40" maxlength="40" name="<%=Parameter.person.FAX %>"
		value="<%if(request.getAttribute(Parameter.person.FAX.name())!=null){out.print(request.getAttribute(Parameter.person.FAX.name()));} %>"
		tabindex="10"
			></td>
	</tr>
	<tr>
		<td>Email *<br>
		<input type="text" size="46" maxlength="50" name="<%=Parameter.person.EMAIL %>"
		value="<%if(request.getAttribute(Parameter.person.EMAIL.name())!=null){out.print(request.getAttribute(Parameter.person.EMAIL.name()));} %>"
		tabindex="11"
			>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>
</table>
</fieldset>
<br>

&nbsp;Felder mit '*' sind Pflichtfelder und m&uuml;ssen ausgef&uuml;llt
werden.
<table>

	<tr>
		<td><input type="submit" name="Submit"
			value="Zentrum anlegen" tabindex="12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="button" name="abbrechen" value="Formular zur&uuml;cksetzen"
			tabindex="13"></td>
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
