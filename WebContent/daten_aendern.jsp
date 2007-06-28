<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.*"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.*" import="de.randi2.controller.*"%>
<%@ page import="de.randi2.utility.*"%>
<%

// es werden als Namen fuer die Formularfelder die Werte aus Parameter.person.* und Parameter.benutzerkonto.* benutzt.
// die Parameter.anfrage_id fuer den Dispatcher ist DispatcherServlet.anfrage_id.JSP_DATEN_AENDERN.name()
// in der JS funktion eraseStellvertreter steht wie mann erkennen kann ob ein stellvertreter gesetzt ist, oder nciht

// FIXME BEIM STELLVERTRETER: es muss unbedingt noch realisiert werden, dass wenn Parameter gesetzt wurden (sprich das Formular schon mal abgeschickt wurde, jedoch fehlerhaft war), dass dann nicht immer die Werte vom aktuellen Bean genommen werden, sondern die requestParameter
// ist nurn bischen JSP arbeit

	request.setAttribute(DispatcherServlet.requestParameter.TITEL
	.toString(), JspTitel.DATEN_AENDERN.toString());

	BenutzerkontoBean aBenutzer_form = (BenutzerkontoBean) (request
	.getSession()).getAttribute(DispatcherServlet.sessionParameter.A_Benutzer.toString());

	
	PersonBean aPerson = aBenutzer_form.getBenutzer();
	PersonBean aStellvertreter = aPerson.getStellvertreter();


String aVorname = aPerson.getVorname();
String aNachname = aPerson.getNachname();
String aTitel = aPerson.getTitel().toString();
char aGeschlecht = aPerson.getGeschlecht();
String aTelefonnummer = aPerson.getTelefonnummer();
String aHandynummer = aPerson.getHandynummer();
String aFax = aPerson.getFax();
String aEmail = aPerson.getEmail();

String aStellvertreterVorname = null;
String aStellvertreterNachname = null;
String aStellvertreterTitel = null;
String aStellvertreterTelefon = null;
char aStellvertreterGeschlecht = NullKonstanten.NULL_CHAR;
String aStellvertreterEmail = null;

if(aStellvertreter!=null) {

	aStellvertreterVorname = aStellvertreter.getVorname();
	aStellvertreterNachname = aStellvertreter.getNachname();

	if (aStellvertreter.getTitel()==null) {
		aStellvertreterTitel = PersonBean.Titel.KEIN_TITEL.toString();
	} else {
		aStellvertreterTitel = aStellvertreter.getTitel().toString();
	
	}
aStellvertreterGeschlecht  = aPerson.getGeschlecht();
aStellvertreterTelefon = aStellvertreter.getTelefonnummer();
aStellvertreterEmail = aStellvertreter.getEmail();

}

if(aVorname==null) {
	
	aVorname = "";
	
}
if(aNachname==null) {
	
	aNachname = "";
	
}

if (request.getParameter(Parameter.person.TITEL.name()) == null) {

	aTitel = aPerson.getTitel().toString();

} else {

	aTitel = request.getParameter(Parameter.person.TITEL.name());

}


if (request.getParameter(Parameter.person.TELEFONNUMMER.name()) == null) {

	aTelefonnummer = aPerson.getTelefonnummer();

} else {

	aTelefonnummer = request
	.getParameter(Parameter.person.TELEFONNUMMER.name());

}

if (request.getParameter(Parameter.person.HANDYNUMMER.name()) == null) {

	aHandynummer = aPerson.getHandynummer();

} else {

	aHandynummer = request
	.getParameter(Parameter.person.HANDYNUMMER.name());

}

if (request.getParameter(Parameter.person.FAX.name()) == null) {

	aFax = aPerson.getFax();

} else {

	aFax = request.getParameter(Parameter.person.FAX.name());

}

if (aTelefonnummer==null) {
	
	aTelefonnummer = "";
	
}

if (aHandynummer==null) {
	
	aHandynummer = "";
	
}

if (aFax==null) {
	
	aFax = "";
	
}

if(aEmail==null){
	aEmail="";
}

if(aStellvertreterVorname==null) {
	
	aStellvertreterVorname = "";
}
if(aStellvertreterNachname==null) {
	
	aStellvertreterNachname = "";
}
if(aStellvertreterTelefon==null) {
	
	aStellvertreterTelefon = "";
}
if(aStellvertreterEmail==null) {
	
	aStellvertreterEmail = "";
}
if(aStellvertreterGeschlecht==NullKonstanten.NULL_LONG) {
	
	aStellvertreterGeschlecht = 'w';
	
}
if(aStellvertreterTitel==null) {
	
	aStellvertreterTitel = PersonBean.Titel.KEIN_TITEL.toString();
	
}

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="include/inc_extjs.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: <%=request.getAttribute(DispatcherServlet.requestParameter.TITEL.toString())%></title>
<script>
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var form_daten_aendern = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 200,
		buttonAlign: 'left',
		id: 'form_daten_aendern'
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
			['<%=titel%>'],
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
        fieldLabel: 'Vorname:',
        name: '<%=Parameter.person.VORNAME.name() %>',
        value: '<%=aVorname %>',
        width:190,
        allowBlank:false,
        editable:false,
        minLength:2,
        maxLength:50,
        readOnly:true,
        maxLengthText:'Vorname muss 2 bis 50 Zeichen lang sein!',
        minLengthText:'Vorname muss 2 bis 50 Zeichen lang sein!',
        blankText:'Bitte Ihren Vornamen eintragen!'
    });
    
    var nachname = new Ext.form.TextField({
        fieldLabel: 'Nachname:',
        name: '<%=Parameter.person.NACHNAME.name() %>',
        value: '<%=aNachname %>',
        width:190,
        editable:false,
        allowBlank:false,
        minLength:2,
        readOnly:true,
        maxLength:50,
        maxLengthText:'Nachname muss 2 bis 50 Zeichen lang sein!',
        minLengthText:'Nachname muss 2 bis 50 Zeichen lang sein!',
        blankText:'Bitte Ihren Nachnamen eintragen!'
    });
    
    var geschlecht = new Ext.form.ComboBox({
        fieldLabel: 'Geschlecht:',
        hiddenName:'<%=Parameter.person.GESCHLECHT.name()%>',
        store: new Ext.data.SimpleStore({
            fields: ['geschlecht'],
            data : [['<%
        	if(aGeschlecht=='m') {
        %>maennlich<%
        	} else if(aGeschlecht=='w') {
		%>weiblich<%
			}
		%>']]
            
        }),
        displayField:'geschlecht',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        <%
        	if(aGeschlecht=='m') {
        %>
		value:'maennlich',
		<%
        	} else if(aGeschlecht=='w') {
		%>
		value:'weiblich',
		<%
			}
		%>
		selectOnFocus:true,
        editable:false,
        width:140,
        allowBlank:false,
        readOnly:true,
        blankText:'Bitte Ihr Geschlecht auswaehlen!'
    });
    
    var passwort = new Ext.form.TextField({
        fieldLabel: 'Neues Passwort *:',
        name: '<%=Parameter.benutzerkonto.PASSWORT.name() %>',
        value: '',
        allowBlank:true,
        width:190,
        inputType:'password',
        minLength:6,
        minLengthText:'Das Passwort muss mindestens 6 Zeichen umfassen!',
        blankText:'Bitte ein Passwort eingeben!'
    });
    
    var passwort_wh = new Ext.form.TextField({
        fieldLabel: 'Neues Passwort wiederholen *:',
        name: '<%=Parameter.benutzerkonto.PASSWORT_WIEDERHOLUNG.name() %>',
        value: '',
        allowBlank:true,
        width:190,
        inputType:'password',
		minLength:6,
        minLengthText:'Das Passwort muss mindestens 6 Zeichen umfassen!',
        blankText:'Bitte das Passwort erneut eingeben!'
    });
    
    form_daten_aendern.fieldset({legend:'Pers&ouml;nliche Angaben',
    labelSeparator:''},
    titel,
    vorname,
    nachname,
    geschlecht,passwort, passwort_wh);
 
    var email = new Ext.form.TextField({
        fieldLabel: 'E-Mail:',
        name: '<%=Parameter.person.EMAIL.name() %>',
        value: '<%=aEmail %>',
        width:190,
        allowBlank:false,
        editable:false,
        readOnly:true
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

    form_daten_aendern.fieldset({legend:'Kontaktdaten',
    labelSeparator:''},
    email,
    telefon,
    handy,
    fax);
    
	var stellvertreter_vorname = new Ext.form.TextField({
        fieldLabel: 'Vorname *:',
        name: '<%=Parameter.person.STELLVERTRETER_VORNAME.name() %>',
        value: '<%=aStellvertreterVorname %>',
        width:190,
        allowBlank:true,
        editable:true,
        minLength:2,
        maxLength:50,
        maxLengthText:'Vorname muss 2 bis 50 Zeichen lang sein!',
        minLengthText:'Vorname muss 2 bis 50 Zeichen lang sein!'
    });
    
	var stellvertreter_nachname = new Ext.form.TextField({
        fieldLabel: 'Vorname *:',
        name: '<%=Parameter.person.STELLVERTRETER_NACHNAME.name() %>',
        value: '<%=aStellvertreterNachname %>',
        width:190,
        allowBlank:true,
        editable:true,
        minLength:2,
        maxLength:50,
        maxLengthText:'Nachname muss 2 bis 50 Zeichen lang sein!',
        minLengthText:'Nachname muss 2 bis 50 Zeichen lang sein!'
    });
    
    var stellvertreter_geschlecht = new Ext.form.ComboBox({
        fieldLabel: 'Geschlecht *:',
        hiddenName:'<%=Parameter.person.STELLVERTRETER_GESCHLECHT.name()%>',
        store: new Ext.data.SimpleStore({
            fields: ['geschlecht'],
            data : [['maennlich'],['weiblich']]
            
        }),
        displayField:'geschlecht',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        <%
        	if(aStellvertreterGeschlecht=='m') {
        %>
		value:'maennlich',
		<%
        	} else if(aStellvertreterGeschlecht=='w') {
		%>
		value:'weiblich',
		<%
			}
		%>
		selectOnFocus:true,
        editable:false,
        width:140,
        allowBlank:true,
        blankText:'Bitte Ihr Geschlecht auswaehlen!'
    });
    
    var stellvertreter_titel = new Ext.form.ComboBox({
        fieldLabel: 'Titel *:',
        hiddenName:'<%=Parameter.person.STELLVERTRETER_TITEL.name()%>',
        store: new Ext.data.SimpleStore({
            fields: ['titel'],
            data : [
			<%
				StringBuffer titel2 = new StringBuffer();
				for (int i = 0; i < PersonBean.Titel.values().length; i++) {
					titel2.append(PersonBean.Titel.values()[i].toString());
			%>
			['<%=titel2%>'],
			<%
					titel2.delete(0, titel2.length());
				}
			%>
            ]
        }),
        displayField:'titel',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'<%=aStellvertreterTitel %>',
        value:'<%=aStellvertreterTitel %>',
        selectOnFocus:true,
        editable:false,
        width:140
    });
    
    var stellvertreter_telefon = new Ext.form.TextField({
        fieldLabel: 'Telefon *:',
        name: '<%=Parameter.person.STELLVERTRETER_TELEFONNUMMER.name() %>',
        value: '<%=aStellvertreterTelefon %>',
        width:190,
        allowBlank:true,
        minLength:6,
        maxLength:26,
        maxLengthText:'Telefonnummer muss 6 bis 26 Zeichen lang sein!',
        minLengthText:'Telefonnummer muss 6 bis 26 Zeichen lang sein!'
    });
    
    Ext.form.VTypes['emailText'] = 'Bitte eine gueltige E-Mail eintragen!';
    
    var stellvertreter_email = new Ext.form.TextField({
        fieldLabel: 'E-Mail *:',
        name: '<%=Parameter.person.STELLVERTRETER_EMAIL.name() %>',
        value: '<%=aStellvertreterEmail %>',
        width:190,
        allowBlank:true,
        minLength:2,
        maxLength:255,
        maxLengthText:'E-Mail muss 2 bis 255 Zeichen lang sein!',
        minLengthText:'E-Mail muss 2 bis 255 Zeichen lang sein!',
        vtype:'email'
    });
    
    form_daten_aendern.fieldset({legend:'Angaben zum Stellvertreter <img src="images/remove-user-blue.gif" style="cursor:pointer" onmousedown="eraseStellvertreter()" title="Den Stellvertreter l&ouml;schen!">',
    labelSeparator:''},
    stellvertreter_titel,
    stellvertreter_vorname,
    stellvertreter_nachname,
    stellvertreter_geschlecht,
    stellvertreter_telefon,
    stellvertreter_email);
    
	form_daten_aendern.addButton('Speichern', function(){
		if (this.isValid()) {
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
		}else{
			Ext.MessageBox.alert('Errors', 'Die Eingaben waren fehlerhaft!');
		}
	}, form_daten_aendern);
	
	
	<!--  Die ANFRAGE_ID fuer ABBRECHEN wird hier gesetzt. dhaehn	-->
	form_daten_aendern.addButton('Abbrechen', function(){

            var frm = document.getElementById(this.id);
			frm.<%=Parameter.anfrage_id %>.value='<%=DispatcherServlet.anfrage_id.JSP_STUDIE_ANSEHEN.name() %>';	
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();

	}, form_daten_aendern);

    form_daten_aendern.render('form_daten_aendern');    
    
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_daten_aendern.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.JSP_DATEN_AENDERN.name() %>'});	
    
    
   });
</script>
<script>
function eraseStellvertreter() {

	var frm = document.getElementById('form_daten_aendern');
	frm.<%=Parameter.person.STELLVERTRETER_NACHNAME %>.value = '';
	frm.<%=Parameter.person.STELLVERTRETER_VORNAME %>.value = '';
	frm.<%=Parameter.person.STELLVERTRETER_EMAIL %>.value = '';
	frm.<%=Parameter.person.STELLVERTRETER_GESCHLECHT %>.value = 'w';
	frm.<%=Parameter.person.STELLVERTRETER_TELEFONNUMMER %>.value = '';
	frm.<%=Parameter.person.STELLVERTRETER_TITEL %>.value = '<%=PersonBean.Titel.KEIN_TITEL.toString()%>';



}

</script>
</head>

<body>
<%@include file="include/inc_header.jsp"%>


<div id="content">
<%@include file="include/inc_nachricht.jsp"%>
<h1>Daten &auml;ndern</h1>
<div id="form_daten_aendern"></div>


&nbsp;Die mit '*' gekennzeichneten Felder sind Pflichtfelder. Bei der Einrichtung eines Stellvertreters sind alle Felder n&ouml;tig. <%@include
	file="include/inc_footer.jsp"%></div>
<%@include file="include/inc_menue.jsp"%>
</body>
</html>
