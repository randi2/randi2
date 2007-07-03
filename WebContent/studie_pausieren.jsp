<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.*"
	import="de.randi2.controller.*"%>
	
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.STUDIE_PAUSIEREN.toString());
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

    var bestaetigen = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 0,
		buttonAlign: 'left',
		id: 'form_nachrichtendienst'
    });
    
	var text = new Ext.form.MiscField({
        fieldLabel: '',
        value: 'Soll die Studie wirklich pausiert werden?',
        width:300
	});    
	
    
    bestaetigen.fieldset({legend:'Best&auml;tigen',
    labelSeparator:''},text);
    
	bestaetigen.addButton('Ja', function(){
		if (this.isValid()) {
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
		}else{
			Ext.MessageBox.alert('Fehler', 'Die Eingaben waren fehlerhaft!');
		}
	}, bestaetigen);
	
	<!--  Die ANFRAGE_ID fuer ABBRECHEN wird hier gesetzt. dhaehn	-->
	bestaetigen.addButton('Nein', function(){

            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.<%=Parameter.anfrage_id %>.value='<%=DispatcherServlet.anfrage_id.JSP_STUDIE_ANSEHEN%>';
            frm.action = 'DispatcherServlet';
			frm.submit();

	}, bestaetigen);

    bestaetigen.render('bestaetigen');    
    
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	bestaetigen.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=StudieServlet.anfrage_id.JSP_STUDIE_PAUSIEREN_JA.name() %>'});	
    
    
 });
</script>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>

<div id="content">
<h1>Studie pausieren</h1>
<div id="bestaetigen"></div>
<br>

<%@include file="include/inc_footer.jsp"%>
</div>
<%@include file="include/inc_menue.jsp"%>
</body>
</html>
