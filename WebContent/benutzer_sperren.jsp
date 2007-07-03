<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.beans.AktivierungBean"
	import="de.randi2.controller.*"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.*"%>

<%
request.setAttribute(DispatcherServlet.requestParameter.TITEL
		.toString(), JspTitel.BENUTZER_SPERREN.toString());

	BenutzerkontoBean benutzer=(BenutzerkontoBean)session.getAttribute(DispatcherServlet.sessionParameter.BENUTZER_SPERREN_ENTSPERREN_ADMIN.toString()); 


	String aNachricht = request.getParameter(Parameter.benutzerkonto.NACHRICHT.toString());

	if(aNachricht == null) { aNachricht = ""; }
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<%@include file="include/inc_extjs.jsp"%>
<script>
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var form_sperren = new Ext.form.Form({
        labelAlign: 'top',
        labelWidth: 100,
		buttonAlign: 'left'
    });
	
	var hintfield = new Ext.form.MiscField({
		fieldLabel: '',
		value: 'Wollen sie den gew&auml;hlten Benutzer (Benutzername: <%= benutzer.getBenutzername()%>) wirklich <%if(!benutzer.isGesperrt()){out.print("sperren");}else{out.print("entsperren");} %>?',
		width: 400,
		height: 30
	});    

    var grund = new Ext.form.TextArea({
        fieldLabel: 'Grund:',
        name: '<%=Parameter.benutzerkonto.NACHRICHT.toString()%>',
        allowBlank:false,
        blankText:'Bitte einen Grund eingeben!',
        width:250,
        value:'<%=aNachricht.replaceAll("\\n","\\\\n").replaceAll("\\r","\\\\r")%>',
        height:150
    });
	
    form_sperren.fieldset(
        {legend:'Angaben zur Studie',labelSeparator:''},
		
		hintfield,
		grund
	);	
	
	form_sperren.addButton('<%if(!benutzer.isGesperrt()){out.print("Sperren");}else{out.print("Entsperren");} %>', function(){
		if (this.isValid()) {
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
		}else{
			Ext.MessageBox.alert('Fehler', 'Die Eingaben waren fehlerhaft!');
		}
	}, form_sperren);
	

	
	form_sperren.render('form_sperren');
	
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_sperren.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.JSP_BENUTZER_SPERREN_SPERREN_ENTSPERREN.name() %>'});	


});


</script>					

<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

<%@include file="include/inc_header.jsp"%>

<div id="content">
<%@include file="include/inc_nachricht.jsp" %>

<h1><%if(!benutzer.isGesperrt()){out.print("Benutzer sperren");}else{out.print("Benutzer entsperren");} %></h1>

<div id="form_sperren"></div>

<%@include file="include/inc_footer.jsp"%></div>
<%@include file="include/inc_menue.jsp"%>

</body>
</html>
