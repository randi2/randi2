<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<% session.removeAttribute("FILE_UPLOAD_STATS"); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">
<%@page import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.BENUTZER_ANLEGEN_DREI.toString());
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>

<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.controller.DispatcherServlet"%>
<%
	String aVorname = "";
	String aNachname = "";
	String aTitel = PersonBean.Titel.KEIN_TITEL.toString();
	String aGeschlecht = "weiblich";
	String aTelefonnummer = "";
	String aHandynummer = "";
	String aFax = "";
	String aEmail = "";

	if (request.getParameter(Parameter.person.VORNAME.name()) == null) {

		aVorname = "";

	} else {

		aVorname = request
		.getParameter(Parameter.person.VORNAME.name());

	}

	if (request.getParameter(Parameter.person.NACHNAME.name()) == null) {

		aNachname = "";

	} else {

		aNachname = request.getParameter(Parameter.person.NACHNAME
		.name());

	}

	if (request.getParameter(Parameter.person.TITEL.name()) == null) {

		aTitel = PersonBean.Titel.KEIN_TITEL.toString();

	} else {

		aTitel = request.getParameter(Parameter.person.TITEL.name());

	}

	if (request.getParameter(Parameter.person.GESCHLECHT.name()) == null
			|| ((String) request
			.getParameter(Parameter.person.GESCHLECHT.name()))
			.equals("bitte auswaehlen")) {

		aGeschlecht = "weiblich";

	} else {

		aGeschlecht = request.getParameter(Parameter.person.GESCHLECHT
		.name());

	}

	if (request.getParameter(Parameter.person.TELEFONNUMMER.name()) == null) {

		aTelefonnummer = "";

	} else {

		aTelefonnummer = request
		.getParameter(Parameter.person.TELEFONNUMMER.name());

	}

	if (request.getParameter(Parameter.person.HANDYNUMMER.name()) == null) {

		aHandynummer = "";

	} else {

		aHandynummer = request
		.getParameter(Parameter.person.HANDYNUMMER.name());

	}

	if (request.getParameter(Parameter.person.FAX.name()) == null) {

		aFax = "";

	} else {

		aFax = request.getParameter(Parameter.person.FAX.name());

	}

	if (request.getParameter(Parameter.person.EMAIL.name()) == null) {

		aEmail = "";

	} else {

		aEmail = request.getParameter(Parameter.person.EMAIL.name());

	}
%>
<%@include file="include/inc_extjs.jsp"%>
<script>
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var form_benutzer_anlegen = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 200,
		buttonAlign: 'left',
		id: 'id_form_benutzer_anlegen'
    });
    
    var titel = new Ext.form.ComboBox({
        fieldLabel: 'Titel:',
        hiddenName:'<%=Parameter.person.TITEL.name()%>',
        store: new Ext.data.SimpleStore({
            fields: ['titel'],
            data : [
			<%
				StringBuffer titel = new StringBuffer();
				for (int i = 0; i < PersonBean.Titel.values().length; i++) {
					titel.append(PersonBean.Titel.values()[i].toString());
			%>
			['<%=titel.toString()%>'],
			<%
					titel.delete(0, titel.length());
				}
			%>
            ]
        }),
        displayField:'titel',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'<%=aTitel %>',
        value:'<%=aTitel %>',
        selectOnFocus:true,
        editable:false,
        width:140
    });
    
    var vorname = new Ext.form.TextField({
        fieldLabel: 'Vorname *:',
        name: '<%=Parameter.person.VORNAME.name() %>',
        value: '<%=aVorname %>',
        width:190,
        allowBlank:false,
        minLength:2,
        maxLength:50,
        maxLengthText:'Vorname muss 2 bis 50 Zeichen lang sein!',
        minLengthText:'Vorname muss 2 bis 50 Zeichen lang sein!',
        blankText:'Bitte Ihren Vornamen eintragen!'
    });
    
    var nachname = new Ext.form.TextField({
        fieldLabel: 'Nachname *:',
        name: '<%=Parameter.person.NACHNAME.name() %>',
        value: '<%=aNachname %>',
        width:190,
        allowBlank:false,
        minLength:2,
        maxLength:50,
        maxLengthText:'Nachname muss 2 bis 50 Zeichen lang sein!',
        minLengthText:'Nachname muss 2 bis 50 Zeichen lang sein!',
        blankText:'Bitte Ihren Nachnamen eintragen!'
    });
    
    var geschlecht = new Ext.form.ComboBox({
        fieldLabel: 'Geschlecht *:',
        hiddenName:'<%=Parameter.person.GESCHLECHT.name()%>',
        store: new Ext.data.SimpleStore({
            fields: ['geschlecht'],
            data : [['weiblich'],['maennlich']
            ]
        }),
        displayField:'geschlecht',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        <%
        	if(aGeschlecht.equals("maennlich")) {
        %>
		value:'maennlich',
		<%
        	} else if(aGeschlecht.equals("weiblich")) {
		%>
		value:'weiblich',
		<%
			}
		%>
		selectOnFocus:true,
        editable:false,
        width:140,
        allowBlank:false,
        blankText:'Bitte Ihr Geschlecht auswaehlen!'
    });
    
    var passwort = new Ext.form.TextField({
        fieldLabel: 'Passwort *:',
        name: '<%=Parameter.benutzerkonto.PASSWORT.name() %>',
        value: '',
        allowBlank:false,
        width:190,
        inputType:'password',
        minLength:6,
        minLengthText:'Das Passwort muss mindestens 6 Zeichen umfassen!',
        blankText:'Bitte ein Passwort eingeben!'
    });
    
    var passwort_wh = new Ext.form.TextField({
        fieldLabel: 'Passwort wiederholen *:',
        name: '<%=Parameter.benutzerkonto.PASSWORT_WIEDERHOLUNG.name() %>',
        value: '',
        allowBlank:false,
        width:190,
        inputType:'password',
		minLength:6,
        minLengthText:'Das Passwort muss mindestens 6 Zeichen umfassen!',
        blankText:'Bitte das Passwort erneut eingeben!'
    });
    
    form_benutzer_anlegen.fieldset({legend:'Pers&ouml;nliche Angaben',
    labelSeparator:''},
    titel,
    vorname,
    nachname,
    geschlecht,passwort, passwort_wh);
    
    Ext.form.VTypes['emailText'] = 'Bitte eine gueltige E-Mail eintragen!';
    
    var email = new Ext.form.TextField({
        fieldLabel: 'E-Mail *:',
        name: '<%=Parameter.person.EMAIL.name() %>',
        value: '<%=aEmail %>',
        width:190,
        allowBlank:false,
        minLength:2,
        maxLength:255,
        maxLengthText:'E-Mail muss 2 bis 255 Zeichen lang sein!',
        minLengthText:'E-Mail muss 2 bis 255 Zeichen lang sein!',
        blankText:'Bitte Ihre E-Mail eintragen!',
        vtype:'email'
    });
    
    var telefon = new Ext.form.TextField({
        fieldLabel: 'Telefon *:',
        name: '<%=Parameter.person.TELEFONNUMMER.name() %>',
        value: '<%=aTelefonnummer %>',
        width:190,
        allowBlank:false,
        minLength:6,
        maxLength:26,
        maxLengthText:'Telefonnummer muss 6 bis 26 Zeichen lang sein!',
        minLengthText:'Telefonnummer muss 6 bis 26 Zeichen lang sein!',
        blankText:'Bitte Ihre Telefonnummer eintragen!'
    });
    
    var handy = new Ext.form.TextField({
        fieldLabel: 'Handy:',
        name: '<%=Parameter.person.HANDYNUMMER.name() %>',
        value: '<%=aHandynummer %>',
        width:190,
        allowBlank:true,
        minLength:7,
        maxLength:26,
        maxLengthText:'Handynummer muss 7 bis 26 Zeichen lang sein!',
        minLengthText:'Handynummer muss 7 bis 26 Zeichen lang sein!'
    });

    var fax = new Ext.form.TextField({
        fieldLabel: 'Fax:',
        name: '<%=Parameter.person.FAX.name() %>',
        value: '<%=aFax %>',
        width:190,
        allowBlank:true,
        minLength:6,
        maxLength:26,
        maxLengthText:'Faxnummer muss 6 bis 26 Zeichen lang sein!',
        minLengthText:'Faxnummer muss 6 bis 26 Zeichen lang sein!'
    });

    form_benutzer_anlegen.fieldset({legend:'Kontaktdaten',
    labelSeparator:''},
    email,
    telefon,
    handy,
    fax);
    

    
	form_benutzer_anlegen.addButton('Anlegen', function(){
		if (this.isValid()) {
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
		}else{
			Ext.MessageBox.alert('Fehler', 'Die Eingaben waren fehlerhaft!');
		}
	}, form_benutzer_anlegen);
	
	
	<!--  Die ANFRAGE_ID fuer ABBRECHEN wird hier gesetzt. dhaehn	-->
	form_benutzer_anlegen.addButton('Abbrechen', function(){
		top.location.href='DispatcherServlet';
	}, form_benutzer_anlegen);  

    form_benutzer_anlegen.render('form_benutzer_anlegen');    
    
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_benutzer_anlegen.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.JSP_BENUTZER_ANLEGEN_DREI_BENUTZER_REGISTRIEREN_VIER.name() %>'});	
    
    
   });
</script>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header_clean.jsp"%>



<div id="content">
<h1>Benutzer anlegen</h1>
<%@include file="include/inc_nachricht.jsp"%>
<div id="form_benutzer_anlegen"></div>

<br>
&nbsp;Die mit '*' gekennzeichneten Felder sind Pflichtfelder. <%@include
	file="include/inc_footer_clean.jsp"%></div>
</body>
</html>
