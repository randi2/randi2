<%@ page import="de.randi2.model.fachklassen.beans.PersonBean"
	import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="de.randi2.controller.DispatcherServlet"
	import="de.randi2.model.fachklassen.beans.StudieBean"
		import="de.randi2.model.fachklassen.beans.ZentrumBean"
	import="de.randi2.utility.*" import="java.util.*" import="de.randi2.utility.Parameter"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%

	Collection<ZentrumBean> zentren = (Vector<ZentrumBean>) request
			.getAttribute(DispatcherServlet.requestParameter.LISTE_ZENTREN
			.toString());

	if (zentren == null) {
		zentren = new Vector<ZentrumBean>();
	}

	String aVorname = (String) request
			.getAttribute(Parameter.person.VORNAME.name());
	if (aVorname == null) {
		aVorname = "";
	}
	String aNachname = (String) request
			.getAttribute(Parameter.person.NACHNAME.name());
	if (aNachname == null) {
		aNachname = "";
	}
	String scheisse = (String) request
			.getAttribute(Parameter.person.GESCHLECHT.name());
	char aGeschlecht = '\0';
	if (scheisse != null) {
		aGeschlecht = scheisse.charAt(0);
	}
	
	String aEmail = (String) request
			.getAttribute(Parameter.person.EMAIL.name());
	if (aEmail == null) {
		aEmail = "";
	}
	String aTelefon = (String) request
			.getAttribute(Parameter.person.TELEFONNUMMER.name());
	if (aTelefon == null) {
		aTelefon = "";
	}
	String aFax = (String) request.getAttribute(Parameter.person.FAX
			.name());
	if (aFax == null) {
		aFax = "";
	}

	Long aInstitut = (Long) request.getAttribute(Parameter.benutzerkonto.ZENTRUM_FK.name());

	String aTitel = (String) request
			.getAttribute(Parameter.person.TITEL.name());
	if (aTitel == null) {
		aTitel = "";
	}
	String aVornameA = (String) request
			.getAttribute(Parameter.person.STELLVERTRETER_VORNAME
			.name());
	if (aVornameA == null) {
		aVornameA = "";
	}
	String aNachnameA = (String) request
			.getAttribute(Parameter.person.STELLVERTRETER_NACHNAME
			.name());
	if (aNachnameA == null) {
		aNachnameA = "";
	}
	String aTelefonA = (String) request
			.getAttribute(Parameter.person.STELLVERTRETER_TELEFONNUMMER
			.name());
	if (aTelefonA == null) {
		aTelefonA = "";
	}
	String aBenutzername = (String) request
			.getAttribute(Parameter.benutzerkonto.LOGINNAME.name());
	if (aBenutzername == null) {
		aBenutzername = "";
	}
	String aTitelA = (String) request.getAttribute(Parameter.person.STELLVERTRETER_TITEL.name());
	if(aTitelA == null) {
		aTitelA = "";
	}
	String aEmailA = (String) request.getAttribute(Parameter.person.STELLVERTRETER_EMAIL.name());
	if (aEmailA == null) {
	aEmailA = "";
	}
	
	scheisse = (String) request.getAttribute(Parameter.person.STELLVERTRETER_GESCHLECHT.name());
	char aGeschlechtA = '\0';
	if (scheisse != null) {
		aGeschlechtA = scheisse.charAt(0);
		}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Randi2 :: <%=request.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<%@include file="include/inc_extjs.jsp"%>
<script>
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var form_admin_anlegen = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 200,
		buttonAlign: 'left',
		id: 'id_form_admin_anlegen'
    });
    
    var titel = new Ext.form.ComboBox({
        fieldLabel: 'Titel *:',
        hiddenName:'<%=Parameter.person.TITEL.name()%>',
        store: new Ext.data.SimpleStore({
            fields: ['titel'],
            data : [
			<%
				StringBuffer titel = new StringBuffer();
				for (int i = 0; i < PersonBean.Titel.values().length; i++) {
					titel.append(PersonBean.Titel.values()[i].toString());
			%>
			['<%=titel%>']<%if (i!=PersonBean.Titel.values().length-1){%>,<%}%>
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
        <%if (!aTitel.equals("")){%>
        value:'<%=aTitel %>',
        <%}%>
        emptyText:'--Bitte auswählen--',
        selectOnFocus:true,
        allowBlank:false,
		blankText:'Bitte einen Titel auswählen!',
        editable:false,
        width:150
    });
    
        var institut = new Ext.form.ComboBox({
        fieldLabel: 'Institut *:',
        hiddenName:'<%=Parameter.benutzerkonto.ZENTRUM_FK.name()%>',
        store: new Ext.data.SimpleStore({
            fields: ['id','zentrum'],
            data : 
            <%
            StringBuffer buf = new StringBuffer();
            for (Iterator<ZentrumBean> i = zentren.iterator(); i.hasNext();) {
            	ZentrumBean bean = i.next();
            	if(i.hasNext()){
            		buf.append("['"+bean.getId()+"','"+bean.getInstitution()+", "+bean.getAbteilung()+"'],");
            	}else{
            		buf.append("['"+bean.getId()+"','"+bean.getInstitution()+", "+bean.getAbteilung()+"']");
            	}
            }
            %>
            [<%=buf.toString()%>]
        }),
        <%if (aInstitut!=null){%>
        value:'<%=aInstitut%>',
        <%}%>
        displayField:'zentrum',
        valueField:'id',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'--Bitte auswählen--',
		selectOnFocus:true,
		allowBlank:false,
		blankText:'Bitte ein Institut auswählen!',
        editable:false,
        width:150
    });
    
    var vorname = new Ext.form.TextField({
        fieldLabel: 'Vorname *:',
        name: '<%=Parameter.person.VORNAME.name() %>',
        value: '<%=aVorname %>',
        width:150,
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
        width:150,
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
            fields: ['id','geschlecht'],
            data : [['w','weiblich'],['m','männlich']
            ]
        }),
        <%if (aGeschlecht == 'w'){%>
        value:['weiblich'],
        <%} else if (aGeschlecht == 'm'){%>
        value:['männlich'],
        <%}%>
        displayField:'geschlecht',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
		selectOnFocus:true,
        editable:false,
        width:150,
        allowBlank:false,
        emptyText:'--Bitte auswählen--',
        blankText:'Bitte das Geschlecht auswählen!'
    });

    
    
    var benutzername = new Ext.form.TextField({
        fieldLabel: 'Benutzername *:',
        name: '<%=Parameter.benutzerkonto.LOGINNAME.name() %>',
        value: '<%=aBenutzername%>',
        allowBlank:false,
        width:150,
        minLength:6,
        minLengthText:'Der Benutzername muss mindestens 6 Zeichen umfassen!',
        blankText:'Bitte ein Benutzernamen eingeben!'
    });
    
    form_admin_anlegen.fieldset({legend:'Pers&ouml;nliche Angaben',
    labelSeparator:''},
    titel,
    vorname,
    nachname,
    geschlecht,benutzername);
    
    Ext.form.VTypes['emailText'] = 'Bitte eine gueltige E-Mail eintragen!';
    
    var email = new Ext.form.TextField({
        fieldLabel: 'E-Mail *:',
        name: '<%=Parameter.person.EMAIL.name() %>',
        value: '<%=aEmail %>',
        width:150,
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
        value: '<%=aTelefon%>',
        width:150,
        allowBlank:false,
        minLength:6,
        maxLength:26,
        maxLengthText:'Telefonnummer muss 6 bis 26 Zeichen lang sein!',
        minLengthText:'Telefonnummer muss 6 bis 26 Zeichen lang sein!',
        blankText:'Bitte Ihre Telefonnummer eintragen!'
    });
 
     var fax = new Ext.form.TextField({
        fieldLabel: 'Fax:',
        name: '<%=Parameter.person.FAX.name() %>',
        value: '<%=aFax %>',
        width:150,
        allowBlank:true,
        minLength:6,
        maxLength:26,
        maxLengthText:'Faxnummer muss 6 bis 26 Zeichen lang sein!',
        minLengthText:'Faxnummer muss 6 bis 26 Zeichen lang sein!'
    });

    form_admin_anlegen.fieldset({legend:'Kontaktdaten',
    labelSeparator:''},
    email,
    telefon,
    fax,institut);
    
 var vornameStell = new Ext.form.TextField({
        fieldLabel: 'Vorname *:',
        name: '<%=Parameter.person.STELLVERTRETER_VORNAME.name() %>',
        width:150,
        allowBlank:false,
        value:'<%=aVornameA%>',
        minLength:2,
        maxLength:50,
        maxLengthText:'Vorname muss 2 bis 50 Zeichen lang sein!',
        minLengthText:'Vorname muss 2 bis 50 Zeichen lang sein!',
        blankText:'Bitte Ihren Vornamen eintragen!'
    });
    
 var geschlechtStell = new Ext.form.ComboBox({
        fieldLabel: 'Geschlecht *:',
        hiddenName:'<%=Parameter.person.STELLVERTRETER_GESCHLECHT.name()%>',
        store: new Ext.data.SimpleStore({
            fields: ['id','geschlecht'],
            data : [['w','weiblich'],['m','männlich']
            ]
        }),
        <%if (aGeschlecht == 'w'){%>
        value:['weiblich'],
        <%} else if (aGeschlecht == 'm'){%>
        value:['männlich'],
        <%}%>
        displayField:'geschlecht',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
		selectOnFocus:true,
        editable:false,
        width:150,
        allowBlank:false,
        emptyText:'--Bitte auswählen--',
        blankText:'Bitte das Geschlecht auswählen!'
    });
    
    var nachnameStell = new Ext.form.TextField({
        fieldLabel: 'Nachname *:',
        name: '<%=Parameter.person.STELLVERTRETER_NACHNAME.name() %>',
        value: '<%=aNachnameA %>',
        width:150,
        allowBlank:false,
        minLength:2,
        maxLength:50,
        maxLengthText:'Nachname muss 2 bis 50 Zeichen lang sein!',
        minLengthText:'Nachname muss 2 bis 50 Zeichen lang sein!',
        blankText:'Bitte Ihren Nachnamen eintragen!'
    });
    
     var telefonStell = new Ext.form.TextField({
        fieldLabel: 'Telefon *:',
        name: '<%=Parameter.person.STELLVERTRETER_TELEFONNUMMER.name() %>',
        value: '<%=aTelefonA%>',
        width:150,
        allowBlank:false,
        minLength:6,
        maxLength:26,
        maxLengthText:'Telefonnummer muss 6 bis 26 Zeichen lang sein!',
        minLengthText:'Telefonnummer muss 6 bis 26 Zeichen lang sein!',
        blankText:'Bitte Ihre Telefonnummer eintragen!'
    });
    
    
     var emailStell = new Ext.form.TextField({
        fieldLabel: 'E-Mail *:',
        name: '<%=Parameter.person.STELLVERTRETER_EMAIL.name()%>',
        value: '<%=aEmailA %>',
        width:150,
        allowBlank:false,
        minLength:2,
        maxLength:255,
        maxLengthText:'E-Mail muss 2 bis 255 Zeichen lang sein!',
        minLengthText:'E-Mail muss 2 bis 255 Zeichen lang sein!',
        blankText:'Bitte Ihre E-Mail eintragen!',
        vtype:'email'
    });
    
    
     var titelStell = new Ext.form.ComboBox({
        fieldLabel: 'Titel *:',
        hiddenName:'<%=Parameter.person.STELLVERTRETER_TITEL.name()%>',
        store: new Ext.data.SimpleStore({
            fields: ['titelStell'],
            data : [
			<%
				StringBuffer titelStell = new StringBuffer();
				for (int i = 0; i < PersonBean.Titel.values().length; i++) {
					titelStell.append(PersonBean.Titel.values()[i].toString());
			%>
			['<%=titelStell%>']<%if (i!=PersonBean.Titel.values().length-1){%>,<%}%>
			<%
					titelStell.delete(0, titelStell.length());
				}
			%>
            ]
        }),
        displayField:'titelStell',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        <% if (!aTitelA.equals("")){%>
        	value:'<%=aTitelA %>',
        <%}%>        	
        emptyText:'--Bitte auswählen--',
        selectOnFocus:true,
        allowBlank:false,
		blankText:'Bitte einen Titel auswählen!',
        editable:false,
        width:150
    });
    
    
    
    
    
    form_admin_anlegen.fieldset({legend:'Ansprechpartner',
    labelSeparator:''},
    titelStell,
    vornameStell,
    nachnameStell,
    geschlechtStell,
    emailStell,
    telefonStell);
    
	form_admin_anlegen.addButton('Anlegen', function(){
		if (this.isValid()) {
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
		}else{
		Ext.MessageBox.alert('Fehler', 'Die Eingaben waren fehlerhaft!<br>Bitte überprüfen Sie ihre Eingaben und füllen alle Pflichtfelder aus.');
		}
	}, form_admin_anlegen);
	


    form_admin_anlegen.render('form_admin_anlegen');    
    
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_admin_anlegen.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.AKTION_ADMIN_ANLEGEN.name() %>'});	
    
    
   });
</script>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1><%=request.getAttribute(DispatcherServlet.requestParameter.TITEL.toString())%></h1>
<%@include file="include/inc_nachricht.jsp"%>
<div id="form_admin_anlegen"></div>
</div>
<div id="show_SA"><%@include file="include/inc_menue.jsp"%>
</div>
</body>
</html>
