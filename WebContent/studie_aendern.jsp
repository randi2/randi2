<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.*"
	import="java.util.GregorianCalendar" import="java.util.Calendar"
	import="java.util.Vector" import="java.text.SimpleDateFormat"
	import="java.util.Locale" import="de.randi2.utility.*"
	import="de.randi2.controller.*"%>

<%
		request.setAttribute(DispatcherServlet.requestParameter.TITEL
		.toString(), JspTitel.STUDIE_AENDERN.toString());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: <%=request.getAttribute(DispatcherServlet.requestParameter.TITEL.toString())%></title>

<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

<div id="content">
<form action="DispatcherServlet" method="post" name="user" id="user"><input
	type="hidden" name="anfrage_id"
	value="<%=DispatcherServlet.anfrage_id.JSP_STUDIE_AENDERN.name() %>">

<h1>Studie &auml;ndern</h1>

<fieldset><legend><b>Studienangaben</b></legend>
<table>
	<%
		StudieBean aStudieBean = (StudieBean) request.getSession().getAttribute(DispatcherServlet.sessionParameter.AKTUELLE_STUDIE.name());
		Calendar cal = new GregorianCalendar();
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy",Locale.GERMANY);
		Vector<StudienarmBean> aStudienarme = aStudieBean.getStudienarme();
		int counter = aStudienarme.size();
	%>
	<tr>
		<td>Name der Studie<br>
		<input type="text" size="40" maxlength="40"
			name="Parameter.studie.NAME" tabindex="1" readonly
			value="<%=aStudieBean.getName()%>"></td>
	</tr>
	<tr>
		<td>Beschreibung der Studie *<br>
		<textarea cols="37" rows="4" name="Parameter.studie.BESCHREIBUNG"
			tabindex="2">
		<%=aStudieBean.getBeschreibung()%>
		</textarea></td>
	</tr>
	<tr>
		<td>Startdatum *<br>
		<input type="text" size="40" maxlength="40"
			name="Parameter.studie.STARTDATUM" tabindex="3"
			value="<%=
				formatter.format(aStudieBean.getStartDatum().getTime())
			%>"
			<% 
			   	if(aStudieBean.getStartDatum().before(cal.get(Calendar.DATE)))
			   		out.print(" readonly ");
			   	%>>
		</td>
		<td>Enddatum *<br>
		<input type="text" size="40" maxlength="40"
			name="Parameter.studie.ENDDATUM" tabindex="4"
			value="<%=formatter.format(aStudieBean.getEndDatum().getTime())%>"
			<% 
			   	if(aStudieBean.getEndDatum().before(cal.get(Calendar.DATE)))
			   		out.print(" readonly ");
			 %>>
		</td>
	</tr>
	<tr>

	</tr>
</table>
</fieldset>
<br>
<fieldset><legend><b>Zusatzangaben</b></legend>
<table>
	<tbody>
		<tr>
			<td>Studienprotokoll &nbsp;&nbsp;&nbsp;<input
				name="Parameter.studie.STUDIENPROTOKOLL" size="50"
				maxlength="100000" accept="text/*" id="datei" tabindex="5"
				type="file" readonly="readonly"
				value="<%out.print(aStudieBean.getStudienprotokollpfad());%>">
			<br>
			<br>
			</td>
		</tr>
		<tr>
			<td>Arme der Studie</td>
		</tr>
		<tr>
			<td>
			<table width="90%">
				<tbody>
					<tr>
						<th align="left">Bezeichnung</th>
						<th align="left">Beschreibung</th>
					</tr>
					<tr>
						<td><input name="studienarm"
							value="Parameter.studie.ARME_STUDIE" size="30" type="text"
							readonly
							value="<%out.print(aStudienarme.get(aStudienarme.size()-counter).getBezeichnung());%>"
							<%counter--; %>></td>
					</tr>
					<%
					while(counter>0){
					%>
					<tr align="left">
						<td></td>
						<td><%=aStudienarme.get(aStudienarme.size()-counter).getBezeichnung()%>
						<%
						counter--;
						%>
						</td>
						<td></td>
					</tr>
					<%
									counter--;
									}
					%>

				</tbody>
			</table>
			</td>
		</tr>
	</tbody>
</table>
<table>
	<tbody>
		<tr>
			<td><br>
			Randomisationsbezogene Eigenschaften<br>
			<textarea cols="37" rows="4"
				name="Parameter.studie.RANDOMISATIONSEIGENSCHAFTEN" tabindex="6"
				readonly
				value="<%--out.print(aStudieBean.getRandomisationsart());--%>"></textarea>
			</td>
		</tr>
		<tr>
			<td><br>
			Leitende Institution<br>
			<input size="40" maxlength="40" name="Parameter.studie.INSTITUT"
				tabindex="7" type="text" readonly
				value="<%out.print(aStudieBean.getInstitution());%>"></td>
			<td></td>
			<td><br>
			Verantwortliche(r) Studienleiter(in)<br>
			<input size="40" maxlength="40" name="Parameter.studie.STUDIENLEITER"
				tabindex="8" type="text" readonly
				value="<%out.print(aStudieBean.getBenutzerkonto().getBenutzer().getVorname()+" "+aStudieBean.getBenutzerkonto().getBenutzer().getNachname());%>"></td>
		</tr>
	</tbody>
</table>

</fieldset>
</form>
<table align="center">
	<tr>
		<td><input type="button" name="bestaetigen"
			value="Best&auml;tigen" tabindex="11">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<td><input type="button" name="abbrechen" value="Abbrechen"
			tabindex="12" onclick="location.href='studie_ansehen.jsp'"></td>
	</tr>
</table>
<br>
<%@include file="include/inc_footer.jsp"%></div>
<div id="show_none"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
