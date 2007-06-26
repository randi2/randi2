<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.model.fachklassen.beans.PersonBean"
	import="de.randi2.model.fachklassen.*" import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.PASSWORT_VERGESSEN.toString());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="de.randi2.utility.Parameter"%>
<%@page import="de.randi2.controller.DispatcherServlet"%>
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

    var form_pw = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 140,
		buttonAlign: 'left',
    });
    
    var benutzername = new Ext.form.TextField({
        fieldLabel: 'Benutzername *',
        name: '<%=Parameter.benutzerkonto.LOGINNAME.name() %>',
        value: '',
        width:190,
        allowBlank:false,
        blankText:'Bitte Ihren Benutzernamen eintragen!'
    });    
    
	form_pw.addButton('Neues Passwort anfordern', function(){
		if (this.isValid()) {
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
		}else{
			Ext.MessageBox.alert('Errors', 'Die Eingaben waren fehlerhaft!');
		}
	}, form_pw);
	
	<!--  Die ANFRAGE_ID fuer ABBRECHEN wird hier gesetzt. dhaehn	-->
	form_pw.addButton('Abbrechen', function(){
		top.location.href='DispatcherServlet';
	}, form_pw);    

    form_pw.fieldset({legend:'Ihr Account'},
    benutzername);
    
    form_pw.render('form_pw');

<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_pw.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.JSP_PASSWORT_VERGESSEN.name() %>'});	
    
    });
</script>
</head>
<body>
<%@include file="include/inc_nachricht.jsp"%>

<div id="content">

<h1>Passwort anfordern</h1>
<div id="form_pw"></div>

<br>
<%@include file="include/inc_footer.jsp"%></div>
<div id="show_none"></div>
<div id="show_none"></div>
</body>
</html>
