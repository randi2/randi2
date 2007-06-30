<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.STUDIE_ANSEHEN.toString());
%>
<%
			Rolle.Rollen aRolle = ((BenutzerkontoBean) request.getSession()
			.getAttribute(
			DispatcherServlet.sessionParameter.A_Benutzer
			.toString())).getRolle().getRollenname();
	StudieBean aStudie = (StudieBean) request.getSession()
			.getAttribute(
			DispatcherServlet.sessionParameter.AKTUELLE_STUDIE
			.toString());
	Vector<StudienarmBean> aStudienarme = aStudie.getStudienarme();
	int counter = aStudienarme.size();
	SimpleDateFormat formater = new SimpleDateFormat("dd.MM.yyyy",
			Locale.GERMANY);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Vector"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<%@include file="include/inc_extjs.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css">
<script>
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	
	<%if(aRolle == Rolle.Rollen.STUDIENLEITER){%>
	var form_aendern = new Ext.form.Form({
		id:'aendern_button',
        labelAlign: 'left',
        labelWidth: 0,
		buttonAlign: 'center'
    });
    
    var form_status = new Ext.form.Form({
    	id:'status_button',
        labelAlign: 'left',
        labelWidth: 0,
		buttonAlign: 'center'
    });
    
    	form_aendern.addButton('&Auml;ndern', function(){
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
	}, form_aendern);    

	form_aendern.render('form_aendern');
	
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	<!-- Die anfrage_id muss spaeter angepasst werden - sobald man das Aendern der Studie ausimplementiert! Erstmal wird man automatisch ausgeloggt! lplotni-->
	form_aendern.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.AKTION_LOGOUT %>'});	
	
	<%
		if (aStudie.getStatus().equals(Studie.Status.AKTIV)) {
		%>
		form_status.addButton('Pausieren', function(){
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
	}, form_status);    

	form_status.render('form_status');
	
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_status.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=StudieServlet.anfrage_id.JSP_STUDIE_PAUSIEREN.toString()%>'});
		
		<%
		}
		%>
	<%
	if (aStudie.getStatus().equals(Studie.Status.PAUSE)) {
	%>
		form_status.addButton('Fortsetzen', function(){
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
	}, form_status);    

	form_status.render('form_status');
	
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_status.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=StudieServlet.anfrage_id.JSP_STUDIE_FORTSETZEN.toString()%>'});
		
	<%
	}
	%>
	<%
	if (aStudie.getStatus().equals(Studie.Status.INVORBEREITUNG)) {
	%>
		form_status.addButton('Starten', function(){
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
	}, form_status);    

	form_status.render('form_status');
	
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_status.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=StudieServlet.anfrage_id.JSP_STUDIE_STARTEN.toString()%>'});
		
	<%
	}
	%>
    <%}%>
    <%if(aRolle == Rolle.Rollen.STUDIENLEITER||aRolle == Rolle.Rollen.ADMIN||aRolle == Rolle.Rollen.STATISTIKER){%>
    var form_statistik = new Ext.form.Form({
    	id:'statistik_button',
        labelAlign: 'left',
        labelWidth: 0,
		buttonAlign: 'center'
    });
    
    var form_random = new Ext.form.Form({
    	id:'randomErg_button',
        labelAlign: 'left',
        labelWidth: 0,
		buttonAlign: 'center'
    });
    
    	form_statistik.addButton('Statistik', function(){
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
	}, form_statistik);    

	form_statistik.render('form_statistik');
	
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	<!-- Die anfrage_id muss spaeter angepasst werden - sobald man die Statistik ausimplementiert! Erstmal wird man automatisch ausgeloggt! lplotni-->
	form_statistik.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.AKTION_LOGOUT %>'});	
	
	form_random.addButton('Randomisationsergebnisse', function(){
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
	}, form_random);    

	form_random.render('form_random');
	
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	<!-- Die anfrage_id muss spaeter angepasst werden - sobald man sich die Randomisationsergebniss anzeigen kann! Erstmal wird man automatisch ausgeloggt! lplotni-->
	form_random.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.AKTION_LOGOUT %>'});	
    
    <%}%>
    
});
</script>
</head>


<body>
<%@include file="include/inc_header.jsp"%>
<div id="content"><%@include file="include/inc_nachricht.jsp"%>
<h1>Studie ansehen</h1>
<fieldset>
<table style="text-align: left; width: 100%;" border="0" cellpadding="2"
	cellspacing="2">
	<tbody>
		<tr>
			<td style="width: 300px; text-align: left; vertical-align: top;">
			<table style="text-align: left; width: 100%;" border="0"
				cellpadding="2" cellspacing="2">
				<tbody>
					<tr class="tblrow3">
						<td>Studienname: &nbsp;</td>
					</tr>
					<tr class="tblrow1">
						<td>
						<h3><%=aStudie.getName()%></h3>
						</td>
					</tr>
				</tbody>
			</table>
			<br>
			</td>
			<td>
			<table style="text-align: left; width: 100%; height: 100%;"
				border="0" cellpadding="2" cellspacing="2">
				<tbody>
					<tr class="tblrow3">
						<td style="width: 200px; text-align: left;">Leitende
						Institution</td>
						<td style="text-align: left;">Verantwortliche(r)
						Studienleiter(in)</td>
					</tr>
					<tr class="tblrow1">
						<td style="text-align: left;"><%=aStudie.getBenutzerkonto().getZentrum().getInstitution()%></td>
						<td style="text-align: left;"><%=(aStudie.getBenutzerkonto().getBenutzer().getVorname()
							+ " " + aStudie.getBenutzerkonto().getBenutzer()
							.getNachname())%></td>
					</tr>
					<tr class="tblrow3">
						<td style="text-align: left;">Startdatum</td>
						<td style="text-align: left;">Enddatum</td>
					</tr>
					<tr class="tblrow1">
						<td style="text-align: left;"><%=formater.format(aStudie.getStartDatum().getTime())%></td>
						<td style="text-align: left;"><%=formater.format(aStudie.getEndDatum().getTime())%></td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td colspan="2" rowspan="1">
			<table style="text-align: left; width: 100%;" border="0"
				cellpadding="2" cellspacing="2">
				<tbody>
					<tr class="tblrow3" align="left">
						<td style="width: 50%; text-align: left;">Beschreibung</td>
						<td>Status der Studie</td>
					</tr>
					<tr align="left" class="tblrow1">
						<td><%=aStudie.getBeschreibung()%></td>
						<td><%=aStudie.getStatus().toString()%></td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr></tr>
		<tr>
			<td style="text-align: left; width: 50%; vertical-align: top;">
			<table style="text-align: left; width: 100%;" border="0"
				cellpadding="2" cellspacing="2">
				<tbody>
					<tr class="tblrow3">
						<td>Randomisationsbezogene Eigenschaften</td>
					</tr>
					<tr>
						<td class="tblrow1"><%=aStudie.getAlgorithmus()%></td>
					</tr>
				</tbody>
			</table>
			<br>
			</td>
			<td colspan="1" rowspan="2"
				style="text-align: left; vertical-align: top;">
			<table style="text-align: left; width: 100%; height: 100%;"
				border="0" cellpadding="2" cellspacing="2">
				<tbody>
					<tr class="tblrow3">
						<td>Studienarme</td>
					</tr>
					<%
					while (counter > 0) {
					%>
					<tr align="left">
						<%
						if (aRolle == Rolle.Rollen.STUDIENLEITER) {
						%>
						
						<td class="tblrow1">
						<form action="DispatcherServlet" method="POST" name="studienarm"
							id="studienarm"><input type="hidden"
							name="<%=Parameter.anfrage_id %>" value=""> <input
							type="hidden" name="<%=Parameter.studienarm.ID.toString() %>"
							value=""></form>
						<%=aStudienarme.get(aStudienarme.size() - counter)
									.getBezeichnung()+" "+"("+aStudienarme.get(aStudienarme.size() - counter).getStatus().toString()+")"%> <img src="images/anzeigen.gif"
							onClick="document.forms['studienarm'].<%=Parameter.anfrage_id %>.value = '<%=StudieServlet.anfrage_id.JSP_STUDIENARM_ANZEIGEN.toString() %>';document.forms['studienarm'].<%=Parameter.studienarm.ID.toString()%>.value = '<%=aStudienarme.get(aStudienarme.size() - counter)
							.getId() %>';document.forms['studienarm'].submit();"></td>
						<%
						} else {
						%>
						
						<td class="tblrow1"><%=aStudienarme.get(aStudienarme.size() - counter)
									.getBezeichnung()+" "+"("+aStudienarme.get(aStudienarme.size() - counter).getStatus().toString()+")"%></td>
						<%
						}
						%>
						<%
						counter--;
						%>

					</tr>
					<%
					}
					%>
				</tbody>
			</table>
			<br>
			</td>
		</tr>
		<tr>
			<td style="text-align: left; vertical-align: top;">
			<table style="text-align: left; width: 100%;" border="0"
				cellpadding="2" cellspacing="2">
				<tbody>
					<tr class="tblrow3">
						<td>Studienprotokoll &nbsp;</td>
					</tr>
					<tr>
						<td class="tblrow1"><a
							href="<%=aStudie.getStudienprotokollpfad() %>"><%=aStudie.getStudienprotokollpfad()%></a></td>
					</tr>
				</tbody>
			</table>

			</td>
		</tr>
	</tbody>
</table>

</fieldset>
<%
if (aRolle == Rolle.Rollen.STUDIENLEITER) {
%>
<table cellPadding="0" cellSpacing="0" border="0">
	<tr>
		<td align="right">
		<div id="form_aendern"></div>
		</td>
		<td>&nbsp;&nbsp;&nbsp;::&nbsp;&nbsp;&nbsp;</td>
		<td align="left">
		<div id="form_status"></div>
		</td>
		<td>&nbsp;&nbsp;&nbsp;::&nbsp;&nbsp;&nbsp;</td>
		<td align="left">
		<div id="form_statistik"></div>
		</td>
		<td>&nbsp;&nbsp;&nbsp;::&nbsp;&nbsp;&nbsp;</td>
		<td align="left">
		<div id="form_random"></div>
		</td>
	</tr>
</table>
<br>
<%
}
%> 
<%
if (aRolle == Rolle.Rollen.ADMIN || aRolle == Rolle.Rollen.STATISTIKER) {
%>
<table cellPadding="0" cellSpacing="0" border="0">
	<tr>
		<td align="left">
		<div id="form_statistik"></div>
		</td>
		<td>&nbsp;&nbsp;&nbsp;::&nbsp;&nbsp;&nbsp;</td>
		<td align="left">
		<div id="form_random"></div>
		</td>
	</tr>
</table>
<br>
<%
}
%>
<%@include file="include/inc_footer.jsp"%></div>
<%@include file="include/inc_menue.jsp"%>
</body>
</html>
