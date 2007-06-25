<%@ page language="java" contentType="text/html; charset=utf-8;"
	pageEncoding="utf-8"%>

<%@ page import="de.randi2.controller.DispatcherServlet"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="de.randi2.utility.Config"%><%@page import="de.randi2.utility.Parameter"%>
<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8;" />
<title>RANDI 2</title>
<%@include file="include/inc_extjs.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/style_login.css" />
<script>
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var form_login = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 100,
		buttonAlign: 'left',
    });

    var login_name = new Ext.form.TextField({
        fieldLabel: 'Benutzername',
        name: '<%=Parameter.benutzerkonto.LOGINNAME.name() %>',
        allowBlank:false,
        blankText:'Bitte Namen eingeben!',
        width:150
    });
    
     var login_passwort = new Ext.form.TextField({
        fieldLabel: 'Kennwort',
        name: '<%=Parameter.benutzerkonto.PASSWORT.name() %>',
        allowBlank:false,
        blankText:'Bitte Passwort eingeben!',
        inputType:'password',
        width:150
    });
	
	form_login.addButton('Login', function(){
		if (this.isValid()) {
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
		}else{
			Ext.MessageBox.alert('Errors', 'Die Eingaben waren fehlerhaft!');
		}
	}, form_login);
	
	form_login.add(login_name);
	form_login.add(login_passwort);
			
	form_login.render('form_login');
	
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_login.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.JSP_INDEX_LOGIN.name() %>'});	

    var form_benutzer_registrieren = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 0,
		buttonAlign: 'center',
    });
    
    var form_passwort_vergessen = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 0,
		buttonAlign: 'center',
    });
    
	form_benutzer_registrieren.addButton('Benutzer registrieren', function(){
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
	}, form_benutzer_registrieren);    

	form_benutzer_registrieren.render('form_benutzer_registrieren');
	
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_benutzer_registrieren.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.JSP_INDEX_BENUTZER_REGISTRIEREN_EINS.name() %>'});	

	form_passwort_vergessen.addButton('Passwort vergessen?', function(){
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
	}, form_passwort_vergessen);    

	form_passwort_vergessen.render('form_passwort_vergessen');
	
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_passwort_vergessen.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.JSP_PASSWORT_VERGESSEN.name() %>'});	


});


</script>
</head>

<body>
<!--  RELEASE 2 -->
<div id="header"><img src="<%=Config.getProperty(Config.Felder.RELEASE_BILD_LOGO) %>" width="337"
	height="63" title="" alt=""></div>

<div id="breadcrumb">
<table class="breadcrumb_tbl" width="100%" summary="Impressum">
	<tr>
		<td align="right" valign="middle" height="25"><a
			href="DispatcherServlet?<%=Parameter.anfrage_id %>=<%=DispatcherServlet.anfrage_id.JSP_HEADER_IMPRESSUM %>" id="logout_link">Impressum</a></td>
	</tr>
</table>
<%@include file="include/inc_nachricht.jsp"%></div>

<div id="inhalt_login">
<br>
<p><img id="bild_login" src="<%=Config.getProperty(Config.Felder.RELEASE_BILD_STARTSEITE) %>" width="537"
	height="291" alt="Heidelberg"></p>
</div>

<div id="login_benutzer">
<br>
<p id="pageheader">Herzlich Willkommen</p>
<br><br>
<div id="form_login"></div>
<br><br>
<br>



<table cellPadding="0" cellSpacing="0" border="0">
	<tr>
		<td align="right">

		<div id="form_benutzer_registrieren"></div>
		</td>
		<td>&nbsp;&nbsp;&nbsp;::&nbsp;&nbsp;&nbsp;</td>
		<td align="left">
		<div id="form_passwort_vergessen"></div>
		</td>
	</tr>
</table>
</div>

</body>

</html>
