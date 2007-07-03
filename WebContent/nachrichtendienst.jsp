<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="de.randi2.model.fachklassen.beans.*"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.controller.Nachrichtendienst"
	import="de.randi2.controller.DispatcherServlet"
	import="de.randi2.utility.*"%>
<%

request.setAttribute(DispatcherServlet.requestParameter.TITEL
		.toString(), JspTitel.NACHRICHTENDIENST.toString());

%>
<%
	PersonBean aPerson = ((BenutzerkontoBean)session.getAttribute("aBenutzer")).getBenutzer();
//Feldervorbelegungen
	String betreff = (String)request.getAttribute(Nachrichtendienst.requestParameter.BETREFF.name());
	if (betreff==null){
    	betreff="";
	}
	String nachrichtentext= (String)request.getAttribute(Nachrichtendienst.requestParameter.NACHRICHTENTEXT.name());
	if (nachrichtentext==null){
	    nachrichtentext="";
	}   	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="include/inc_extjs.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: <%=request.getAttribute(DispatcherServlet.requestParameter.TITEL.toString()) %></title>
<script>
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var form_nachrichtendienst = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 130,
		buttonAlign: 'left',
		id: 'form_nachrichtendienst'
    });
    
	var absender = new Ext.form.MiscField({
        fieldLabel: 'Absender:',
        value: '<%= aPerson.getVorname() %>&nbsp;<%= aPerson.getNachname() %>',
        width:190
	});    
	

	var email = new Ext.form.MiscField({
        fieldLabel: 'E-Mail:',
        value: '<%= aPerson.getEmail() %>',
        width:190
	});    	

	var empfaenger = new Ext.form.MiscField({
        fieldLabel: 'Empf&auml;nger *:',
        value: '<select class=\'x-combo-list x-combo-list-hd\' name=\'<%= Nachrichtendienst.requestParameter.EMPFAENGER.name() %>\'><%=Nachrichtendienst.getEmpfaengerListe(request).replaceAll("\"","\\\\'").replaceAll("\n","")%></select>',
        width:190
	});    	

    var betreff = new Ext.form.TextField({
        fieldLabel: 'Betreff *:',
        name: '<%=Nachrichtendienst.requestParameter.BETREFF.name() %>',
        value: '',
        allowBlank:false,
        width:400,
        value:'<%=betreff %>',
        blankText:'Bitte einen Betreff eingeben!'
    });
    
	var nachricht = new Ext.form.TextArea({
        fieldLabel: 'Nachricht *:',
        name: '<%=Nachrichtendienst.requestParameter.NACHRICHTENTEXT.name() %>',
        value: '<%=nachrichtentext.replaceAll("\\n","\\\\n").replaceAll("\\r","\\\\r") %>',
        allowBlank:false,
        width:400,
        height:200,
        blankText:'Bitte eine Nachricht eingeben!'
    });    
    
    form_nachrichtendienst.fieldset({legend:'Mitteilung schreiben',
    labelSeparator:''},absender,email,empfaenger,betreff,nachricht);
    
	form_nachrichtendienst.addButton('Senden', function(){
		if (this.isValid()) {
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
		}else{
			Ext.MessageBox.alert('Fehler', 'Die Eingaben waren fehlerhaft!');
		}
	}, form_nachrichtendienst);
	
	
	<!--  Die ANFRAGE_ID fuer ABBRECHEN wird hier gesetzt. dhaehn	-->
	form_nachrichtendienst.addButton('Abbrechen', function(){

            history.back();

	}, form_nachrichtendienst);

    form_nachrichtendienst.render('form_nachrichtendienst');    
    
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_nachrichtendienst.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.AKTION_NACHRICHT_VERSENDEN.name() %>'});	
    
    
 });
</script>
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Nachrichtendienst</h1>
<%@include file="include/inc_nachricht.jsp"%>
<div id="form_nachrichtendienst"></div>
<%@include file="include/inc_footer.jsp"%></div>
<%@include file="include/inc_menue.jsp"%>
</body>
</html>
