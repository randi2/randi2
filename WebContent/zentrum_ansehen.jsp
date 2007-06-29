<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.beans.AktivierungBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.*"
%>

<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.ZENTRUM_ANSEHEN.toString());
			long id = Long.parseLong((String) request.getAttribute(Parameter.zentrum.ZENTRUM_ID.toString()));
			ZentrumBean aZentrum = Zentrum.getZentrum(id);
			PersonBean aPerson = aZentrum.getAnsprechpartner();
		

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<%@include file="include/inc_extjs.jsp"%>

<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Zentrum anzeigen</h1>
<form>
<fieldset style="width: 60%"><legend><b>Angaben
zum Zentrum</b></legend>
<table>
	<tr>
		<td>Name der Institution *<br>
		<input type="text" size="49" maxlength="40" name="Name_Institution"
			tabindex="1" value="<%=aZentrum.getInstitution() %>"></td>
	</tr>
	<tr>
		<td>Name der genauen Abteilung *<br>
		<input type="text" size="49" maxlength="40" name="Zentrum_Abteilung"
			tabindex="2" value="<%=aZentrum.getAbteilung()%>"></td>
	</tr>
	<tr>
		<td>Ort *<br>
		<input type="text" size="40" maxlength="40" name="Ort" tabindex="3"
			value="<%=aZentrum.getOrt() %>"></td>
		<td>PLZ *<br>
		<input type="text" size="6" maxlength="6" name="PLZ" tabindex="4"
			value="<%=aZentrum.getPlz() %>"></td>
	</tr>
	<tr>
		<td>Strasse * <br>
		<input type="text" size="40" maxlength="40" name="strasse"
			tabindex="5" value="<%=aZentrum.getStrasse() %>"></td>
		<td>Hausnummer *<br>
		<input type="text" size"10" maxlength="11" name="hausnummer"
			tabindex="6" value="<%=aZentrum.getHausnr() %>"></td>
	</tr>
	<tr>
		<td>Passwort * <br>
		<input type="password" size"20" maxlength="20" name="hausnummer"
			tabindex="7" value="<%=aZentrum.getPasswort() %>"></td>
	</tr>
</table>
</fieldset>
<br>
<fieldset style="width: 60%"><legend><b>Angaben
zum Ansprechpartner</b></legend>

<table>
<% 
if(aPerson!=null){ 
%>

	<tr>
		<td>Nachname *<br>
		<input type="text" size"40" maxlength="40" name="nachname"
			tabindex="8" value="<%=aPerson.getNachname() %>"></td>
		<td>Vorname *<br>
		
		<input type="text" size"40" maxlength="40" name="vorname" tabindex="9"
			value="<%=aPerson.getVorname() %>"></td>
	</tr>
	<tr>
		<td>Telefon *<br>
		<input type="text" size"40" maxlength="40" name="telefon"
			tabindex="10" value="<%=aPerson.getTelefonnummer() %>"></td>
	</tr>
	<tr>
		<td>Fax<br>
		<input type="text" size"40" maxlength="40" name="fax" tabindex="11"
			value="<%=aPerson.getFax()%>"></td>
	</tr>
	<tr>
		<td>Email *<br>
		<input type="text" size"40" maxlength="40" name="email" tabindex="12"
			value="<%=aPerson.getEmail() %>"></td>
	</tr>




<%}else{
%>
<tr style="text-align: center;"><td><h2>Kein Ansprechpartner vorhanden</h2></td></tr>
<%} %>
</table>
</fieldset>
</form>
<form action="DispatcherServlet" method="POST" name="back_form"
			id="back_form"><input type="hidden"
			name="<%=Parameter.anfrage_id %>" value=""></form>
<table align="left">
	<tr>
	
		<td><span  class="sub_SA n entry" style="cursor:pointer"
			onClick="document.forms['back_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_ZENTRUM_ANZEIGEN.name() %>';document.forms['back_form'].submit();">
		<b>zurück zu den Zentren</b></span></td>
	</tr>
</table>
<br>
<br>


<div id="show_none"><%@include file="include/inc_footer.jsp"%></div>


</div>

<div id="show_BV"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
