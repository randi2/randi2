<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">
<html>
<%@ page import="de.randi2.controller.DispatcherServlet"
	import="java.util.GregorianCalendar"
	import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="de.randi2.model.fachklassen.beans.PersonBean"
	import="de.randi2.model.fachklassen.*"
	import="de.randi2.utility.Parameter"
	%>
<head>
<%@include file="include/inc_extjs.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css">
<script>
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var form_disclaimer = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 0,
		buttonAlign: 'left',
    });
    
    var disclaimer_text = new Ext.form.TextArea({
        name: '',
        value:'<%=Config.getProperty(Config.Felder.RELEASE_DISCLAIMER) %>',
        width:500,
        height:500,
        readOnly:true
    });    
    
	form_disclaimer.addButton('Akzeptieren', function(){
		if (this.isValid()) {
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
		}else{
			Ext.MessageBox.alert('Errors', 'Die Eingaben waren fehlerhaft!');
		}
	}, form_disclaimer);
	
	<!--  Die ANFRAGE_ID fuer ABBRECHEN wird hier gesetzt. dhaehn	-->
	form_disclaimer.addButton('Abbrechen', function(){
		top.location.href='DispatcherServlet';
	}, form_disclaimer);    

    form_disclaimer.fieldset({legend:'Haftungsausschluss',hideLabels:true},
    disclaimer_text);
    
    form_disclaimer.render('form_disclaimer');

	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_disclaimer.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.JSP_BENUTZER_ANLEGEN_EINS_BENUTZER_REGISTRIEREN_ZWEI.name() %>'});	
    
    });
</script>
<title>BRandi2 :: Benutzer anlegen</title>

</head>
<body>
<%@include file="include/inc_header_clean.jsp"%>

<div id="content">
<h1>Benutzer anlegen</h1>
<div id="form_disclaimer"></div>






<%--<%@include file="include/inc_footer.jsp"%>--%></div>
</body>
</html>
