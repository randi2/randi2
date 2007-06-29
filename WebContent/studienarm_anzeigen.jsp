<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.utility.*" import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.STUDIENARM_ANZEIGEN.toString());

	StudienarmBean aStudienarm = (StudienarmBean) request
			.getAttribute(StudieServlet.requestParameter.AKTUELLER_STUDIENARM
			.toString());
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
<script>
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	
	var form_beenden = new Ext.form.Form({
		id:'beenden_button',
        labelAlign: 'left',
        labelWidth: 0,
		buttonAlign: 'center'
    });
    
	form_beenden.addButton('Studienarm beenden', function(){
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
	}, form_beenden);    

	form_beenden.render('form_beenden');
	form_beenden.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.AKTION_LOGOUT %>'});
	form_beenden.el.createChild({tag: 'input', name: '<%=Parameter.studienarm.ID.toString() %>', type:'hidden', value: '<%=aStudienarm.getId() %>'});
	
});
</script>
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Studienarm</h1>
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
						<td>Studienarmbezeichnung: &nbsp;</td>
					</tr>
					<tr class="tblrow1">
						<td>
						<h3><%=aStudienarm.getBezeichnung()%></h3>
						</td>
					</tr>
				</tbody>
			</table>
			<br>
			</td>
		</tr>
		<tr>
			<td colspan="2" rowspan="1">
			<table style="text-align: left; width: 100%;" border="0"
				cellpadding="2" cellspacing="2">
				<tbody>
					<tr class="tblrow3" align="left">
						<td style="width: 50%; text-align: left;">Studienarmbeschreibung</td>
						<td>Status des Studienarms</td>
					</tr>
					<tr align="left" class="tblrow1">
						<td><%=aStudienarm.getBeschreibung()%></td>
						<td><%=aStudienarm.getStatus().toString()%></td>
					</tr>
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td style="text-align: left; width: 50%; vertical-align: top;">
			<table style="text-align: left; width: 100%;" border="0"
				cellpadding="2" cellspacing="2">
				<tbody>
					<tr class="tblrow3">
						<td>Anzahl der Patienten in dem Arm</td>
					</tr>
					<tr>
						<td class="tblrow1"><%=aStudienarm.getPatienten().size()%></td>
					</tr>
				</tbody>
				
			</table>
			</tr>
			</tbody>
			</table>
</fieldset>
<table cellPadding="0" cellSpacing="0" border="0">
	<tr>
		<td align="left">
		<div id="form_beenden"></div>
		</td>
	</tr>
</table>
<%@include file="include/inc_footer.jsp"%>
</div>
<%@include file="include/inc_menue.jsp"%>
</body>
</html>

