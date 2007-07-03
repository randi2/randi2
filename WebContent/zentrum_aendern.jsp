<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="de.randi2.utility.*"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.beans.AktivierungBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.Parameter"
	import="de.randi2.controller.DispatcherServlet"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.ZENTRUM_AENDERN.toString());

//Holen des Zentrums, das angezeigt und geaendert werden soll.
ZentrumBean aZentrum = (ZentrumBean) request.getAttribute("aZentrum");

char aGeschlecht = 'w';

if (aZentrum != null) {
	if(aZentrum.getAnsprechpartner() != null){


	aGeschlecht = aZentrum.getAnsprechpartner().getGeschlecht();
	
}
	
}

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="include/inc_extjs.jsp"%>
<script>
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var zentrum_aendern = new Ext.form.Form({
        labelAlign: 'top',
        labelWidth: 200,
		buttonAlign: 'left',
		id: 'zentrum_aendern'
    });
    
    var institution = new Ext.form.TextField({
        fieldLabel: 'Institution *',
        name: '<%=Parameter.zentrum.INSTITUTION %>',
        value: '<%if(aZentrum != null) {out.print(aZentrum.getInstitution());}%>',
        width:190,
        allowBlank:false,
        minLength:3,
        maxLength:70,
        maxLengthText:'Name der Institution muss 3 bis 70 Zeichen lang sein!',
        minLengthText:'Name der Institution muss 3 bis 70 Zeichen lang sein!',
        blankText:'Bitte die Institution eintragen!'
    });
    
    var abteilung = new Ext.form.TextField({
        fieldLabel: 'Abteilung *',
        name: '<%=Parameter.zentrum.ABTEILUNGSNAME %>',
        value: '<%if(aZentrum != null) {out.print(aZentrum.getAbteilung());}%>',
        width:190,
        allowBlank:false,
        minLength:3,
        maxLength:70,
        maxLengthText:'Name der Abteilung muss 3 bis 70 Zeichen lang sein!',
        minLengthText:'Name der Abteilung muss 3 bis 70 Zeichen lang sein!',
        blankText:'Bitte die Abteilung eintragen!'
    });
    
    var strasse = new Ext.form.TextField({
        fieldLabel: 'Strasse *',
        name: '<%=Parameter.zentrum.STRASSE %>',
        value: '<%if(aZentrum != null) {out.print(aZentrum.getStrasse());}%>',
        width:190,
        allowBlank:false,
        minLength:3,
        maxLength:50,
        maxLengthText:'Die Strasse muss 3 bis 50 Zeichen lang sein!',
        minLengthText:'Die Strasse muss 3 bis 50 Zeichen lang sein!',
        blankText:'Bitte die Strasse eintragen!'
    });    
    
    var hausnummer = new Ext.form.TextField({
        fieldLabel: 'Hausnr. *',
        name: '<%=Parameter.zentrum.HAUSNUMMER %>',
        value: '<%if(aZentrum != null) {out.print(aZentrum.getHausnr());}%>',
        width:60,
        allowBlank:false,
        minLength:1,
        maxLength:10,
        maxLengthText:'Die Hausnummer muss 1 bis 10 Zeichen lang sein!',
        minLengthText:'Die Hausnummer muss 1 bis 10 Zeichen lang sein!',
        blankText:'Bitte die Hausnummer eintragen!'
    });    
    
    var plz = new Ext.form.NumberField({
        fieldLabel: 'Postleitzahl *',
        name: '<%=Parameter.zentrum.PLZ %>',
        value: '<%if(aZentrum != null) {out.print(aZentrum.getPlz());}%>',
        width:60,
        allowNegative: false,
        allowDecimals: false,
        allowBlank:false,
        minValue:00001,
        maxValue:99999,
        blankText:'Bitte die Postleitzahl eintragen!',
        invalidText:'Bitte einen Wert zwischen 00001-99999 eingeben!',
        maxText:'Bitte einen Wert zwischen 00001-99999 eingeben!',
        minText:'Bitte einen Wert zwischen 00001-99999 eingeben!',
        nanText:'Bitte einen Wert zwischen 00001-99999 eingeben!'
    });    
    
    var ort = new Ext.form.TextField({
        fieldLabel: 'Ort *',
        name: '<%=Parameter.zentrum.ORT %>',
        value: '<%if(aZentrum != null) {out.print(aZentrum.getOrt());}%>',
        width:190,
        allowBlank:false,
        minLength:3,
        maxLength:50,
        maxLengthText:'Ortsname muss 3 bis 50 Zeichen lang sein!',
        minLengthText:'Ortsname muss 3 bis 50 Zeichen lang sein!',
        blankText:'Bitte den Ort eintragen!'
    });    
    
    var passwort = new Ext.form.TextField({
        fieldLabel: 'Neues Passwort *',
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
        fieldLabel: 'Neues Passwort wiederholen *',
        name: '<%=Parameter.benutzerkonto.PASSWORT_WIEDERHOLUNG.name() %>',
        value: '',
        allowBlank:true,
        width:190,
        inputType:'password',
		minLength:6,
        minLengthText:'Das Passwort muss mindestens 6 Zeichen umfassen!',
        blankText:'Bitte das Passwort erneut eingeben!'
    });
    
    zentrum_aendern.fieldset({legend:'Angaben zum Zentrum',labelSeparator:':'});
    var linksoben = new Ext.form.Column({width:'300'});
    var rechtsoben = new Ext.form.Column({width:'300'});
    
    zentrum_aendern.start(linksoben);
    zentrum_aendern.add(institution);
    zentrum_aendern.add(strasse);
    zentrum_aendern.add(plz);
    zentrum_aendern.add(passwort);
	zentrum_aendern.add(passwort_wh);
    zentrum_aendern.end(linksoben);
    zentrum_aendern.start(rechtsoben);
    zentrum_aendern.add(abteilung);
    zentrum_aendern.add(hausnummer);
    zentrum_aendern.add(ort);
    zentrum_aendern.end(rechtsoben);
    
	zentrum_aendern.end();
	

	var stellvertreter_vorname = new Ext.form.TextField({
        fieldLabel: 'Vorname *',
        name: '<%=Parameter.person.VORNAME.name() %>',
        value: '<%if(aZentrum != null) {if(aZentrum.getAnsprechpartner() != null) {out.print(aZentrum.getAnsprechpartner().getVorname());}}%>',
        width:190,
        allowBlank:false,
        editable:true,
        minLength:2,
        maxLength:50,
        blankText:'Bitte den Vornamen des Stellvertreters eintragen!',
        maxLengthText:'Vorname muss 2 bis 50 Zeichen lang sein!',
        minLengthText:'Vorname muss 2 bis 50 Zeichen lang sein!'
    });
    
	var stellvertreter_nachname = new Ext.form.TextField({
        fieldLabel: 'Nachname *',
        name: '<%=Parameter.person.NACHNAME.name() %>',
        value: '<%if(aZentrum != null) {if(aZentrum.getAnsprechpartner() != null) {out.print(aZentrum.getAnsprechpartner().getNachname());}}%>',
        width:190,
        allowBlank:false,
        editable:true,
        minLength:2,
        maxLength:50,
        blankText:'Bitte den Nachnamen des Stellvertreters eintragen!',
        maxLengthText:'Nachname muss 2 bis 50 Zeichen lang sein!',
        minLengthText:'Nachname muss 2 bis 50 Zeichen lang sein!'
    });
    
    var stellvertreter_geschlecht = new Ext.form.ComboBox({
        fieldLabel: 'Geschlecht *',
        hiddenName:'<%=Parameter.person.GESCHLECHT.name()%>',
        store: new Ext.data.SimpleStore({
            fields: ['geschlecht'],
            data : [['maennlich'],['weiblich']]
            
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
        allowBlank:true,
        blankText:'Bitte das Geschlecht des Stellvertreters auswaehlen!'
    });
    
    var stellvertreter_telefon = new Ext.form.TextField({
        fieldLabel: 'Telefon *',
        name: '<%=Parameter.person.TELEFONNUMMER.name() %>',
        value: '<%if(aZentrum != null) {if(aZentrum.getAnsprechpartner() != null) {out.print(aZentrum.getAnsprechpartner().getTelefonnummer());}}%>',
        width:190,
        allowBlank:false,
        minLength:6,
        maxLength:26,
        blankText:'Bitte die Telefonnummer des Stellvertreters eintragen!',
        maxLengthText:'Telefonnummer muss 6 bis 26 Zeichen lang sein!',
        minLengthText:'Telefonnummer muss 6 bis 26 Zeichen lang sein!'
    });
    
    var stellvertreter_fax = new Ext.form.TextField({
        fieldLabel: 'Fax',
        name: '<%=Parameter.person.FAX.name() %>',
        value: '<%if(aZentrum != null) {
						if(aZentrum.getAnsprechpartner() != null) {
							String fax = aZentrum.getAnsprechpartner().getFax();
							if(fax != null) {
								out.print(fax);
							} else {
								out.print("");
							}
						}
					 }%>',
        width:190,
        allowBlank:true,
        minLength:6,
        maxLength:26,
        maxLengthText:'Faxnummer muss 6 bis 26 Zeichen lang sein!',
        minLengthText:'Faxnummer muss 6 bis 26 Zeichen lang sein!'
    });
    
    Ext.form.VTypes['emailText'] = 'Bitte eine gueltige E-Mail eintragen!';
    
    var stellvertreter_email = new Ext.form.TextField({
        fieldLabel: 'E-Mail *',
        name: '<%=Parameter.person.EMAIL.name() %>',
        value: '<%if(aZentrum != null) {if(aZentrum.getAnsprechpartner() != null) {out.print(aZentrum.getAnsprechpartner().getEmail());}}%>',
        width:190,
        allowBlank:false,
        minLength:2,
        maxLength:255,
        maxLengthText:'E-Mail muss 2 bis 255 Zeichen lang sein!',
        minLengthText:'E-Mail muss 2 bis 255 Zeichen lang sein!',
        blankText:'Bitte die E-Mail des Stellvertreters eintragen!',
        vtype:'email'
    });
    
    
    zentrum_aendern.fieldset({legend:'Angaben zum Ansprechpartner',labelSeparator:''});

    var linksoben = new Ext.form.Column({width:'300'});
    var rechtsoben = new Ext.form.Column({width:'300'});
    
    zentrum_aendern.start(linksoben);
    zentrum_aendern.add(stellvertreter_vorname);
    zentrum_aendern.add(stellvertreter_geschlecht);
    zentrum_aendern.add(stellvertreter_fax);
    zentrum_aendern.end(linksoben);
    zentrum_aendern.start(rechtsoben);
    zentrum_aendern.add(stellvertreter_nachname);
    zentrum_aendern.add(stellvertreter_telefon);
    zentrum_aendern.add(stellvertreter_email);
    zentrum_aendern.end(rechtsoben);
    
	zentrum_aendern.end();

	zentrum_aendern.addButton('Zentrum &auml;ndern', function(){
		if (this.isValid()) {
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
		}else{
			Ext.MessageBox.alert('Fehler', 'Die Eingaben waren fehlerhaft!');
		}
	}, zentrum_aendern);
	
	

	
    zentrum_aendern.render('zentrum_aendern');
	
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	zentrum_aendern.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.JSP_ZENTRUM_AENDERN.toString() %>'});	

		
    
});

</script>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>

</head>

<body>
<%@include file="include/inc_header.jsp"%>

<div id="content">
<%@include file="include/inc_nachricht.jsp"%>
<h1>Zentrum &auml;ndern</h1>
<div id="zentrum_aendern"></div>
<br>
&nbsp;Die mit '*' gekennzeichneten Felder sind Pflichtfelder. 
<%@include file="include/inc_footer.jsp"%></div>
<%@include file="include/inc_menue.jsp"%>
</body>
</html>
