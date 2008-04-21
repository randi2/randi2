<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.beans.AktivierungBean"
	import="java.util.*" import="java.text.SimpleDateFormat"
	import="java.util.Locale" import="de.randi2.utility.*" import="de.randi2.utility.Parameter"
	import="de.randi2.controller.StudieServlet"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="de.randi2.randomisation.Randomisation"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%

	// 1 Formular via POST an Dispatcher mit Parameter.anfrage_id = DispatcherServlet.anfrage_id.JSP_PATIENT_HINZUFUEGEN_AUSFUEHREN
	// alle Felder sind wie in der enum Parameter.patient benannt und werden als String uebermittelt
	// bei Strata: die jeweilige Auspraegung ist in der POST-variable Parameter.patient.AUSPRAEGUNG+aStrata.getId() gesetzt

	request.setAttribute(DispatcherServlet.requestParameter.TITEL
	.toString(), JspTitel.PATIENT_HINZUFUEGEN.toString());

	String aInitialen = "";
	String aGeschlecht = "weiblich";
	String aGeburtsdatum = "";
	String aDatumaufklaerung = "";
	String aKoerperoberflaeche = "";
	String aPerformancestatus = "-1";
	
	boolean mitStrata = false;
	Vector<StrataBean> aStrata = null;
	
	StudieBean aStudie = ((StudieBean)request.getSession().getAttribute(DispatcherServlet.sessionParameter.AKTUELLE_STUDIE.toString()));
	
	if (aStudie.getAlgorithmus()==Randomisation.Algorithmen.BLOCKRANDOMISATION_MIT_STRATA) {
		
		// mit Strata
		aStrata = aStudie.getStrata();
		if (aStrata == null) {
	
	// koennte ein Fehler sein, was soll passieren?
			request.setAttribute(DispatcherServlet.FEHLERNACHRICHT,"Fehler beim Auslesen der Strata!");
			mitStrata = false;
			
		} else {
		
			mitStrata = true;
		
		}
	}

	GregorianCalendar heute = new GregorianCalendar();


	if (request.getParameter(Parameter.patient.INITIALEN.name()) == null) {

		aInitialen = "";

	} else {

		aInitialen = request
		.getParameter(Parameter.patient.INITIALEN.name());

	}

	if (request.getParameter(Parameter.patient.GESCHLECHT.name()) == null
	|| ((String) request
	.getParameter(Parameter.patient.GESCHLECHT.name()))
	.equals("bitte auswaehlen")) {

		aGeschlecht = "weiblich";

	} else {

		aGeschlecht = request.getParameter(Parameter.patient.GESCHLECHT
		.name());

	}

	if (request.getParameter(Parameter.patient.GEBURTSDATUM.name()) == null) {

		aGeburtsdatum = "new Date(" + heute.get(GregorianCalendar.YEAR)
		+ "," + heute.get(GregorianCalendar.MONTH) + ","
		+ heute.get(GregorianCalendar.DAY_OF_MONTH) + ")";

	} else {

		aGeburtsdatum = request.getParameter(Parameter.patient.GEBURTSDATUM
		.name());

		try {
	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy",
	Locale.GERMAN);
	sdf.setCalendar(Calendar.getInstance());
	Date d = sdf.parse(aGeburtsdatum);
	GregorianCalendar gc = new GregorianCalendar();
	gc.setTime(d);

	aGeburtsdatum = "new Date(" + gc.get(GregorianCalendar.YEAR)
	+ "," + gc.get(GregorianCalendar.MONTH) + ","
	+ gc.get(GregorianCalendar.DAY_OF_MONTH) + ")";

		} catch (Exception e) {
	aGeburtsdatum = "new Date("
	+ heute.get(GregorianCalendar.YEAR) + ","
	+ heute.get(GregorianCalendar.MONTH) + ","
	+ heute.get(GregorianCalendar.DAY_OF_MONTH) + ")";
		}

	}
	
	if (request.getParameter(Parameter.patient.DATUMAUFKLAERUNG.name()) == null) {

		aDatumaufklaerung = "new Date(" + heute.get(GregorianCalendar.YEAR)
		+ "," + heute.get(GregorianCalendar.MONTH) + ","
		+ heute.get(GregorianCalendar.DAY_OF_MONTH) + ")";

	} else {

		aDatumaufklaerung = request.getParameter(Parameter.patient.DATUMAUFKLAERUNG
		.name());

		try {
	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy",
	Locale.GERMAN);
	sdf.setCalendar(Calendar.getInstance());
	Date d = sdf.parse(aGeburtsdatum);
	GregorianCalendar gc = new GregorianCalendar();
	gc.setTime(d);

	aDatumaufklaerung = "new Date(" + gc.get(GregorianCalendar.YEAR)
	+ "," + gc.get(GregorianCalendar.MONTH) + ","
	+ gc.get(GregorianCalendar.DAY_OF_MONTH) + ")";

		} catch (Exception e) {
	aDatumaufklaerung = "new Date("
	+ heute.get(GregorianCalendar.YEAR) + ","
	+ heute.get(GregorianCalendar.MONTH) + ","
	+ heute.get(GregorianCalendar.DAY_OF_MONTH) + ")";
		}

	}
	
	if (request.getParameter(Parameter.patient.KOERPEROBERFLAECHE.name()) == null) {

		aKoerperoberflaeche = "";

	} else {

		aKoerperoberflaeche = request
		.getParameter(Parameter.patient.KOERPEROBERFLAECHE.name());

	}

	if (request.getParameter(Parameter.patient.PERFORMANCESTATUS.name()) == null) {

		aPerformancestatus = "-1";

	} else {

		aPerformancestatus = request
		.getParameter(Parameter.patient.PERFORMANCESTATUS.name());

	}
%>
<%@include file="include/inc_extjs.jsp"%>
<script>
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var form_patient_adden = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 200,
		buttonAlign: 'left',
		id: 'form_patient_adden1'
    });
    
    
    var initialen = new Ext.form.TextField({
        fieldLabel: 'Initialen *',
        name: '<%=Parameter.patient.INITIALEN.name() %>',
        value: '<%=aInitialen %>',
        width:140,
        allowBlank:false,
        minLength:2,
        maxLength:4,
        regex:new RegExp('^[A-Z]{2,4}$'),
        regexText:'Initialen duerfen nur Grossbuchstaben enthalten',
        maxLengthText:'Initialen duerfen nur 2-4 Zeichen lang sein!',
        minLengthText:'Initialen duerfen nur 2-4 Zeichen lang sein!',
        blankText:'Bitte die Initialen eintragen!'
    });
    
    var geschlecht = new Ext.form.ComboBox({
        fieldLabel: 'Geschlecht *',
        hiddenName:'<%=Parameter.patient.GESCHLECHT.name()%>',
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
        blankText:'Bitte das Geschlecht auswaehlen!'
    });
    
    var geburtsdatum = new Ext.form.DateField({
        fieldLabel: 'Geburtsdatum *',
        name: '<%=Parameter.patient.GEBURTSDATUM.name() %>',
        width:140,
        allowBlank:false,
        blankText:'Bitte das Geburtsdatum w&auml;hlen!',
		format:'d.m.Y',
		maxValue:new Date(<%=heute.get(GregorianCalendar.YEAR)%>,<%=heute.get(GregorianCalendar.MONTH)%>,<%=heute.get(GregorianCalendar.DAY_OF_MONTH)%>),
		value:<%=aGeburtsdatum%>
    });    
    
    var datumaufklaerung = new Ext.form.DateField({
        fieldLabel: 'Datum der Patientenaufkl&auml;rung *',
        name: '<%=Parameter.patient.DATUMAUFKLAERUNG.name() %>',
        width:140,
        allowBlank:false,
        blankText:'Bitte das Datum der Aufkl&auml;rung w&auml;hlen!',
		format:'d.m.Y',
		maxValue:new Date(<%=heute.get(GregorianCalendar.YEAR)%>,<%=heute.get(GregorianCalendar.MONTH)%>,<%=heute.get(GregorianCalendar.DAY_OF_MONTH)%>),
		value:<%=aDatumaufklaerung%>
    });    
    
    var koerperoberflaeche = new Ext.form.NumberField({
        fieldLabel: 'K&ouml;rperoberfl&auml;che [m²] *',
        name: '<%=Parameter.patient.KOERPEROBERFLAECHE.name() %>',
        value: '<%=aKoerperoberflaeche %>',
        decimalPrecision: 2,
        decimalSeparator: '.',
        allowNegative: false,
        allowDecimals: true,
        width:140,
        allowBlank:false,
        minValue:0.05,
        maxValue:4,
        invalidText:'Bitte einen Wert zwischen 0.05-4 eingeben!',
        maxText:'Bitte einen Wert zwischen 0.05-4 eingeben!',
        minText:'Bitte einen Wert zwischen 0.05-4 eingeben!',
        blankText:'Bitte die Koerperoberfl&auml;che eintragen!',
        nanText:'Bitte einen Wert zwischen 0.05-4 eingeben!'
    });
    
    var performancestatus = new Ext.form.ComboBox({
        fieldLabel: 'Performancestatus *',
        hiddenName:'<%=Parameter.patient.PERFORMANCESTATUS.name()%>',
        store: new Ext.data.SimpleStore({
            fields: ['performancestatus'],
            data : [['0'],['1'],['2'],['3'],['4']
            ]
        }),
        <% if (!aPerformancestatus.equals("-1")){%>
        selectByValue:'<%=aPerformancestatus%>',
        <%}%>
        displayField:'performancestatus',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
		selectOnFocus:true,
        editable:false,
        width:140,
        allowBlank:false,
        blankText:'Bitte den Performancestatus ausw&auml;hlen!'
    });

      
    form_patient_adden.fieldset({legend:'Patienteneigenschaften',
    labelSeparator:':'},
    initialen,
    geschlecht,
    geburtsdatum,
    datumaufklaerung,koerperoberflaeche, performancestatus);
    
	<%
    
		if (mitStrata) {
	
	%>
	
	
	form_patient_adden.fieldset({legend:'Konfiguration der Strata'});
	
	<%		
			
			
		for(int i=0;i<aStrata.size();i++) {
    
			StrataBean dieseStrata = (StrataBean) aStrata.get(i);
			
			long aStrataId = dieseStrata.getId();
			
			String aStrataname = dieseStrata.getName();
			String aStratabeschreibung = dieseStrata.getBeschreibung();
			
			Iterator aAuspraegungen = dieseStrata.getAuspraegungen().iterator();
			
			
    %>
    

	    var auspraegung<%=aStrataId%> = new Ext.form.ComboBox({
	        fieldLabel: 'Auspr&auml;gung *:',
	        hiddenName:'<%=Parameter.patient.AUSPRAEGUNG.name()%><%=aStrataId%>',
	        store: new Ext.data.SimpleStore({
	            fields: ['id','auspraegung'],
	            data : [<%
	        			while(aAuspraegungen.hasNext()){
	        			
	        				StrataAuspraegungBean aAuspraegung = (StrataAuspraegungBean)aAuspraegungen.next();
	
	        			%>
	        			['<%=aAuspraegung.getId()%>','<%=aAuspraegung.getName()%>']<%if(aAuspraegungen.hasNext()){ %>,<%}%>
	        			<%
	        			}
	            
	            		if (aStratabeschreibung == null) {
	            			
	            			aStratabeschreibung = "";
	            			
	            		}
	            
	                    %>
	            ]
	        }),
	        <% if (request.getAttribute(Parameter.patient.AUSPRAEGUNG.name()+aStrataId)!=null){%>
	        selectByValue:'<%=request.getAttribute(Parameter.patient.AUSPRAEGUNG.name()+aStrataId)%>',
	        <%}%>
	        displayField:'auspraegung',
	        typeAhead: true,
	        valueField:'id',
	        mode: 'local',
	        triggerAction: 'all',
			selectOnFocus:true,
	        editable:false,
	        width:140,
	        allowBlank:false,
	        blankText:'Bitte die Auspraegung auswaehlen!'
	    });    
		
		var strata_beschreibung<%=aStrataId%> = new Ext.form.MiscField({
			msgTarget: 'side',
			name: '<%=Parameter.strata.STRATABESCHREIBUNG.name() %><%=aStrataId%>',
			fieldLabel: 'Beschreibung:',
			value: '<%=aStratabeschreibung.replaceAll("\\n","\\\\n").replaceAll("\\r","\\\\r")%>',
			width: 250,
			editable:false
		});    
    
	    form_patient_adden.fieldset({legend:'Strata: <%=aStrataname%>',
	    labelSeparator:''},
	    strata_beschreibung<%=aStrataId%>,
	    auspraegung<%=aStrataId%>);
	    
    
    <%
    
		}
		
	%>
	form_patient_adden.end();
	
	<%
	
	
		}
    
    %>
    
	form_patient_adden.addButton('Hinzuf&uuml;gen und Randomisieren', function(){
		if (this.isValid()) {
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
		}else{
			Ext.MessageBox.alert('Fehler', 'Die Eingaben waren fehlerhaft!');
		}
	}, form_patient_adden);
	

	

    form_patient_adden.render('form_patient_adden');    
    
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_patient_adden.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=StudieServlet.anfrage_id.JSP_PATIENT_HINZUFUEGEN.name() %>'});	
    
    
   });
</script>
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>

<div id="content">
<%@include file="include/inc_nachricht.jsp"%>

<h1>Patient hinzuf&uuml;gen</h1>
<div id="form_patient_adden"></div>
<br>
<br>
<%@include file="include/inc_footer.jsp"%></div>
<%@include file="include/inc_menue.jsp"%>
</body>
</html>
