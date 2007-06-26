<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="java.util.Iterator" import="java.util.*"%>
<%@page import="de.randi2.controller.*"%>
<%@page import="de.randi2.utility.*"%>
<%@page import="de.randi2.randomisation.Randomisation"%>
<%
	String aName = "";
	String aBeschreibung = "";
	String aStartdatum = "";
	String aEnddatum = "";
	String aStatistiker = "false";
	String aAlgorithmus = "bitte auswaehlen";
	String aStrataname = "";
	String aStratabeschreibung = "";
	String aStrataauspraegungen = "";
	String aArmbezeichnung = "";
	String aArmbeschreibung = "";

	GregorianCalendar heute = new GregorianCalendar();

	request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.STUDIE_ANLEGEN.toString());

	boolean aArmeEntfernenMoeglich = true;
	boolean aStrataEntfernenMoeglich = true;

	if (request
			.getAttribute(DispatcherServlet.requestParameter.ANZAHL_ARME
			.toString()) == null
			|| (Integer) request
			.getAttribute(DispatcherServlet.requestParameter.ANZAHL_ARME
			.toString()) < 3) {

		request.setAttribute(
		DispatcherServlet.requestParameter.ANZAHL_ARME
				.toString(), 2);
		aArmeEntfernenMoeglich = false;

	}
	if (request
			.getAttribute(DispatcherServlet.requestParameter.ANZAHL_STRATA
			.toString()) == null
			|| (Integer) request
			.getAttribute(DispatcherServlet.requestParameter.ANZAHL_STRATA
			.toString()) < 2) {

		request.setAttribute(
		DispatcherServlet.requestParameter.ANZAHL_STRATA
				.toString(), 1);
		aStrataEntfernenMoeglich = false;

	}

	if (request.getParameter(Parameter.studie.NAME.name()) == null) {

		aName = "";

	} else {

		aName = request.getParameter(Parameter.studie.NAME.name());

	}

	if (request.getParameter(Parameter.studie.BESCHREIBUNG.name()) == null) {

		aBeschreibung = "";

	} else {

		aBeschreibung = request
		.getParameter(Parameter.studie.BESCHREIBUNG.name());

	}

	if (request.getParameter(Parameter.studie.STARTDATUM.name()) == null) {

		aStartdatum = "new Date(" + heute.get(GregorianCalendar.YEAR)
		+ "," + heute.get(GregorianCalendar.MONTH) + ","
		+ heute.get(GregorianCalendar.DAY_OF_MONTH) + ")";

	} else {

		aStartdatum = request.getParameter(Parameter.studie.STARTDATUM
		.name());

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy",
			Locale.GERMAN);
			sdf.setCalendar(Calendar.getInstance());
			Date d = sdf.parse(aStartdatum);
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(d);

			aStartdatum = "new Date(" + gc.get(GregorianCalendar.YEAR)
			+ "," + gc.get(GregorianCalendar.MONTH) + ","
			+ gc.get(GregorianCalendar.DAY_OF_MONTH) + ")";

		} catch (Exception e) {
			aStartdatum = "new Date("
			+ heute.get(GregorianCalendar.YEAR) + ","
			+ heute.get(GregorianCalendar.MONTH) + ","
			+ heute.get(GregorianCalendar.DAY_OF_MONTH) + ")";
		}

	}

	if (request.getParameter(Parameter.studie.ENDDATUM.name()) == null) {

		aEnddatum = "new Date(" + heute.get(GregorianCalendar.YEAR)
		+ "," + heute.get(GregorianCalendar.MONTH) + ","
		+ heute.get(GregorianCalendar.DAY_OF_MONTH) + ")";

	} else {

		aEnddatum = request.getParameter(Parameter.studie.ENDDATUM
		.name());

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy",
			Locale.GERMAN);
			sdf.setCalendar(Calendar.getInstance());
			Date d = sdf.parse(aEnddatum);
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(d);

			aEnddatum = "new Date(" + gc.get(GregorianCalendar.YEAR)
			+ "," + gc.get(GregorianCalendar.MONTH) + ","
			+ gc.get(GregorianCalendar.DAY_OF_MONTH) + ")";

		} catch (Exception e) {
			aEnddatum = "new Date(" + heute.get(GregorianCalendar.YEAR)
			+ "," + heute.get(GregorianCalendar.MONTH) + ","
			+ heute.get(GregorianCalendar.DAY_OF_MONTH) + ")";
		}

	}

	if (request.getParameter(Parameter.studie.STATISTIKER_BOOL.name()) == null) {

		aStatistiker = "false";

	} else {

		aStatistiker = "true";

	}

	if (request.getParameter(Parameter.studie.RANDOMISATIONSALGORITHMUS
			.name()) == null) {

		aAlgorithmus = "bitte auswaehlen";

	} else {

		aAlgorithmus = request
		.getParameter(Parameter.studie.RANDOMISATIONSALGORITHMUS
				.name());

	}

	Rolle.Rollen aRolle = ((BenutzerkontoBean) request.getSession()
			.getAttribute("aBenutzer")).getRolle().getRollenname();
%>
<html>
<head>
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<%@include file="include/inc_extjs.jsp"%>
<script>
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var form_studie_anlegen = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 250,
		buttonAlign: 'right',
		id:'form_studie_anlegen'
    });

    var studie_name = new Ext.form.TextField({
        fieldLabel: 'Name der Studie',
        name: '<%=Parameter.studie.NAME.name() %>',
        allowBlank:false,
        minLength:3,
        maxLength:50,
        invalidText:'Dieser Name ist ung&uuml;ltig!',
        blankText:'Der Name darf nicht leer sein!',
        maxLengthText:'Der Name muss 3 bis 50 Zeichen lang sein!',
        minLengthText:'Der Name muss 3 bis 50 Zeichen lang sein!',
        value:'<%=aName%>',
        width:190
    });

    var studie_beschreibung = new Ext.form.TextArea({
        fieldLabel: 'Beschreibung der Studie',
        name: '<%=Parameter.studie.BESCHREIBUNG.name() %>',
        allowBlank:true,
        value:'<%=aBeschreibung%>',
        width:300
    });

    var studie_startdatum = new Ext.form.DateField({
        fieldLabel: 'Startdatum',
        name: '<%=Parameter.studie.STARTDATUM.name() %>',
        width:100,
        allowBlank:false,
        blankText:'Bitte das Startdatum w&auml;hlen!',
		format:'d.m.Y',
		value:<%=aStartdatum%>
    });
    
    var studie_enddatum = new Ext.form.DateField({
        fieldLabel: 'Enddatum',
        name: '<%=Parameter.studie.ENDDATUM.name() %>',
        width:100,
        allowBlank:false,
        blankText:'Bitte das Enddatum w&auml;hlen!',
		format:'d.m.Y',
		value:<%=aEnddatum%>
    });

	var studie_statistiker_boolean = new Ext.form.Checkbox({
        fieldLabel: 'Statistiker Account anlegen',
        name: '<%=Parameter.studie.STATISTIKER_BOOL.name() %>',
        checked:<%=aStatistiker%>
    });
    
	var fileField = new Ext.form.TextField({
		msgTarget: 'side',
		allowBlank: false,
		inputType: 'file',
		name: '<%=Parameter.studie.STUDIENPROTOKOLL.name() %>',
		fieldLabel: 'Studienprotokoll',
		width: 250,
		blankText: 'Ein Studienprotokoll wird ben&ouml;tigt!'
	});    

    form_studie_anlegen.fieldset(
        {legend:'Angaben zur Studie'},
		studie_name,
		studie_beschreibung,
		studie_startdatum,
		studie_enddatum,
		fileField
	);
	
    var algorithmus = new Ext.form.ComboBox({
        fieldLabel: 'Randomisationsalgorithmus',
        hiddenName:'<%=Parameter.studie.RANDOMISATIONSALGORITHMUS.name()%>',
        store: new Ext.data.SimpleStore({
            fields: ['algorithmus'],
            data : [
			<%
				StringBuffer algorithmus = new StringBuffer();
				for (int i = 0; i < Randomisation.Algorithmen.values().length; i++) {
					algorithmus.append(Randomisation.Algorithmen.values()[i].toString());
			%>
			['<%=algorithmus%>'],
			<%
					algorithmus.delete(0, algorithmus.length());
				}
			%>
            ]
        }),
        displayField:'algorithmus',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'<%=aAlgorithmus%>',
        value:'<%=aAlgorithmus%>',
        selectOnFocus:true,
        editable:false,
        width:250
    });
	

	form_studie_anlegen.fieldset({legend:'Randomisation'},algorithmus);
	
	form_studie_anlegen.fieldset({legend:'Stratakonfiguration <img src="images/add-page-green.gif" style="cursor:pointer" onmousedown="addStrata()">&nbsp;<% if (aStrataEntfernenMoeglich) { %><img src="images/omit-page-green.gif" style="cursor:pointer" onmousedown="delStrata()"><% } %>',labelAlign:'top'});
	<%
	
		for(int i=1;i<(Integer)request.getAttribute(DispatcherServlet.requestParameter.ANZAHL_STRATA.toString())+1;i++) {
			
			aStrataname = "";
			aStratabeschreibung = "";
			aStrataauspraegungen = "";
			
			if (request.getParameter(Parameter.strata.NAME.name()+i)==null) {
				
				aStrataname = "";
				
			} else {
				
				aStrataname = request.getParameter(Parameter.strata.NAME.name()+i);
				
			}
			
			if (request.getParameter(Parameter.strata.BESCHREIBUNG.name()+i)==null) {
				
				aStratabeschreibung = "";
				
			} else {
				
				aStratabeschreibung = request.getParameter(Parameter.strata.BESCHREIBUNG.name()+i);
				
			}
			
			if (request.getParameter(Parameter.strata.AUSPRAEGUNGEN.name()+i)==null) {
				
				aStrataauspraegungen = "";
				
			} else {
				
				aStrataauspraegungen = request.getParameter(Parameter.strata.AUSPRAEGUNGEN.name()+i);
				
			}
			
	%>
	<!--  Hier wird die Strata konfiguriert	-->
	
	var strata_name<%=i%> = new Ext.form.TextField({
		msgTarget: 'side',
		allowBlank: false,
		name: '<%=Parameter.strata.NAME.name() %><%=i%>',
		fieldLabel: 'Name',
		width: 250,
		value: '<%=aStrataname%>',
        minLength:1,
        maxLength:100,
        invalidText:'Dieser Name ist ung&uuml;ltig!',
        maxLengthText:'Der Name muss 1 bis 100 Zeichen lang sein!',
        minLengthText:'Der Name muss 1 bis 100 Zeichen lang sein!',
		blankText: 'Ein Name wird ben&ouml;tigt!'
	});    
	
	var strata_beschreibung<%=i%> = new Ext.form.TextArea({
		msgTarget: 'side',
		allowBlank: true,
		name: '<%=Parameter.strata.BESCHREIBUNG.name() %><%=i%>',
		fieldLabel: 'Beschreibung',
		value: '<%=aStratabeschreibung%>',
		width: 250,
		height:50
	});    
	
	var strata_auspraegung<%=i%> = new Ext.form.TextArea({
		msgTarget: 'side',
		allowBlank: false,
		name: '<%=Parameter.strata.AUSPRAEGUNGEN.name() %><%=i%>',
		fieldLabel: 'Auspr&auml;gungen',
		value: '<%=aStrataauspraegungen%>',
		width: 250,
		height:100,
		blankText: 'Mindestens eine Auspr&auml;gung wird ben&ouml;tigt!'
	});    	
	
	
    form_studie_anlegen.fieldset({legend:'Strata Nr. <%=i%>'});
    var linksoben<%=i%> = new Ext.form.Column({width:'300'});
    var rechtsoben<%=i%> = new Ext.form.Column({width:'300'});
    
    form_studie_anlegen.start(linksoben<%=i%>);
    form_studie_anlegen.add(strata_name<%=i%>);
    form_studie_anlegen.add(strata_beschreibung<%=i%>);
    form_studie_anlegen.end(linksoben<%=i%>);
    form_studie_anlegen.start(rechtsoben<%=i%>);
    form_studie_anlegen.add(strata_auspraegung<%=i%>);
    form_studie_anlegen.end(rechtsoben<%=i%>);
	form_studie_anlegen.end();
	
	
    <!--  Hier wird die Strata konfiguriert	(ENDE) -->
    <%
    
		}
    
    %>
    form_studie_anlegen.end();
    form_studie_anlegen.fieldset({legend:'Studienarme <img src="images/add-page-red.gif" style="cursor:pointer" onmousedown="addStudienarm()">&nbsp;<% if(aArmeEntfernenMoeglich) { %><img src="images/omit-page-red.gif" style="cursor:pointer" onmousedown="delStudienarm()"><% } %>',labelAlign:'top'});
	<%
	
		for(int i=1;i<(Integer)request.getAttribute(DispatcherServlet.requestParameter.ANZAHL_ARME.toString())+1;i++) {
			
			aArmbezeichnung = "";
			aArmbeschreibung = "";
			
			if (request.getParameter(Parameter.studienarm.BEZEICHNUNG.name()+i)==null) {
				
				aArmbezeichnung = "";
				
			} else {
				
				aArmbezeichnung = request.getParameter(Parameter.studienarm.BEZEICHNUNG.name()+i);
				
			}
			
			if (request.getParameter(Parameter.studienarm.BESCHREIBUNG.name()+i)==null) {
				
				aArmbeschreibung = "";
				
			} else {
				
				aArmbeschreibung = request.getParameter(Parameter.studienarm.BESCHREIBUNG.name()+i);
				
			}
			
	%>
	<!--  Hier werden die Studienarme konfiguriert	-->
	
	var sa_bezeichnung<%=i%> = new Ext.form.TextField({
		msgTarget: 'side',
		allowBlank: false,
		name: '<%=Parameter.studienarm.BEZEICHNUNG.name() %><%=i%>',
		fieldLabel: 'Bezeichnung',
		value: '<%=aArmbezeichnung%>',
        minLength:3,
        maxLength:50,
        invalidText:'Diese Bezeichnung ist ung&uuml;ltig!',
        blankText:'Die Bezeichnung darf nicht leer sein!',
        maxLengthText:'Die Bezeichnung muss 3 bis 50 Zeichen lang sein!',
        minLengthText:'Die Bezeichnung muss 3 bis 50 Zeichen lang sein!',
		width: 250,
		blankText: 'Eine Bezeichnung wird ben&ouml;tigt!'
	});    
	
	var sa_beschreibung<%=i%> = new Ext.form.TextArea({
		msgTarget: 'side',
		allowBlank: true,
		name: '<%=Parameter.studienarm.BESCHREIBUNG.name() %><%=i%>',
		fieldLabel: 'Beschreibung',
		value: '<%=aArmbeschreibung%>',
		width: 250,
		height:50
	});    
	

    form_studie_anlegen.fieldset({legend:'Studienarm Nr. <%=i%>'});
    var linksoben<%=i%> = new Ext.form.Column({width:'300'});
    var rechtsoben<%=i%> = new Ext.form.Column({width:'300'});
    
    form_studie_anlegen.start(linksoben<%=i%>);
    form_studie_anlegen.add(sa_bezeichnung<%=i%>);
    form_studie_anlegen.end(linksoben<%=i%>);
    form_studie_anlegen.start(rechtsoben<%=i%>);
    form_studie_anlegen.add(sa_beschreibung<%=i%>);
    form_studie_anlegen.end(rechtsoben<%=i%>);
	form_studie_anlegen.end();

	
    <!--  Hier werden die Studienarme konfiguriert	(ENDE) -->
	
	<%
	
		}
	
	%>
	
	form_studie_anlegen.end();
	
    form_studie_anlegen.fieldset(
        {legend:'Statistik und Auswertung'},
		studie_statistiker_boolean
	); 
	
	form_studie_anlegen.addButton('Best&auml;tigen', function(){
		if (this.isValid()) {
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
		}else{
			Ext.MessageBox.alert('Errors', 'Die Eingaben waren fehlerhaft!');
		}
	}, form_studie_anlegen);
	
	<!--  Die ANFRAGE_ID fuer ABBRECHEN wird hier gesetzt. dhaehn	-->
	form_studie_anlegen.addButton('Abbrechen', function(){
		top.location.href='DispatcherServlet?<%=Parameter.anfrage_id %>=<%=DispatcherServlet.anfrage_id.JSP_STUDIE_AUSWAEHLEN.name() %>';
	}, form_studie_anlegen);
	
			
	form_studie_anlegen.render('studie_anlegen');
	
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_studie_anlegen.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.JSP_STUDIE_ANLEGEN.name() %>'});	

	form_studie_anlegen.el.createChild({tag: 'input', name: '<%=DispatcherServlet.requestParameter.ANZAHL_ARME.toString() %>', type:'hidden', value: '<%=request.getAttribute(DispatcherServlet.requestParameter.ANZAHL_ARME.toString()) %>'});	
	form_studie_anlegen.el.createChild({tag: 'input', name: '<%=DispatcherServlet.requestParameter.ANZAHL_STRATA.toString() %>', type:'hidden', value: '<%=request.getAttribute(DispatcherServlet.requestParameter.ANZAHL_STRATA.toString()) %>'});	

	<!-- folgende Zeile ermoeglicht File-Uploads via POST -->
	form_studie_anlegen.el.enctype = 'multipart/form-data';

});
</script>
<script type="text/javascript">
<!--
function addStudienarm() {

	var frm = document.getElementById('form_studie_anlegen');
	frm.<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_STUDIE_ANLEGEN_ADD_STUDIENARM.name() %>';
	frm.submit();
	
}

function delStudienarm() {

	var frm = document.getElementById('form_studie_anlegen');
	frm.<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_STUDIE_ANLEGEN_DEL_STUDIENARM.name() %>';
	frm.submit();

}

function addStrata() {

	var frm = document.getElementById('form_studie_anlegen');
	frm.<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_STUDIE_ANLEGEN_ADD_STRATA.name() %>';
	frm.submit();
	
}

function delStrata() {

	var frm = document.getElementById('form_studie_anlegen');
	frm.<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_STUDIE_ANLEGEN_DEL_STRATA.name() %>';
	frm.submit();

}

-->


</script>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content"><%@include file="include/inc_nachricht.jsp"%>
<h1>Studie anlegen</h1>
<div id="studie_anlegen"></div>

<%@include file="include/inc_footer.jsp"%></div>
</body>
</html>
