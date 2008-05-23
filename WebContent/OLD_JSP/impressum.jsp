<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@page import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.IMPRESSUM.toString());
%>
<%@include file="include/inc_extjs.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<script>
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var form_imprint = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 0,
		buttonAlign: 'left',
    });
   
    var imprint_text = new Ext.form.TextArea({
        name: '',
        value:'<%=Config.getProperty(Config.Felder.RELEASE_IMPRESSUM) %>',
        width:500, 
        height:120,
        readOnly:true
    });   
   
    var imprint_text3 = new Ext.form.TextArea({
        name: '',
        value:'DIESE SOFTWARE WIRD OHNE JEGLICHE SPEZIELLE ODER IMPLIZIERTE GARANTIEN ZUR VERFÜGUNG GESTELLT,DIE UNTER ANDEREM EINSCHLIESSEN: DIE IMPLIZIERTE GARANTIE DER VERWENDBARKEIT DER SOFTWARE FÜR EINEN BESTIMMTEN ZWECK. AUF KEINEN FALL SIND DIE ENTWICKLER FÜR IRGENDWELCHE DIREKTEN, INDIREKTEN, ZUFÄLLIGEN,  SPEZIELLEN, BEISPIELHAFTEN ODER FOLGENDEN SCHADEN (UNTER ANDEREM VERSCHAFFEN VON ERSATZGÜTERN ODER -DIENSTLEISTUNGEN; EINSCHRÄNKUNG DER NUTZUNGSFÄHIGKEIT; VERLUST VON NUTZUNGSFÄHIGKEIT; DATEN; PROFIT ODER GESCHÄFTSUNTERBRECHUNG),WIE AUCH IMMER VERURSACHT UND UNTER WELCHER VERPFLICHTUNG AUCH IMMER,OB IN VERTRAG, STRIKTER VERPFLICHTUNG ODER UNERLAUBTE HANDLUNG (INKLUSIVE FAHRLÄSSIGKEIT) VERANTWORTLICH, AUF WELCHEM WEG SIE AUCH IMMER DURCH DIE BENUTZUNG DIESER SOFTWARE ENTSTANDEN SIND, SOGAR, WENN SIE AUF DIE MÖGLICHKEIT EINES SOLCHEN SCHADENS HINGEWIESEN WORDEN SIND.',
        width:500, 
        height:120,
        readOnly:true
    });     
    
    var imprint_text2 = new Ext.form.TextArea({ 
        value:'Die Webapplikation RANDI2 wurde im Rahmen des Softwarepraktikums vom Studiengang Medizinische Informatik an der Universität Heidelberg/HS Heilbronn im\nWS2006/07 - SS07 entwickelt.\n\nFolgende Personen waren an der Entwicklung beteiligt:\n\nChruscz Katharina, Freudling Andreas, Friedrich Susanne, Ganszky Thomas, Graeff Valentin, Haehn Daniel, Hess Frank, Krupka Kai Marco, Noack Tino, Plotnicki Lukasz, Reifschneider Frederik, Theel Benjamin, Thönes Johannes, Willert Thomas, Zwink Nadine, Prof.Dr.Martin Haag \n \nVerwendete Icons:  http://www.ndesign-studio.com', 
        width:500, 
        height:180,  
        readOnly:true  
    });       
              
    form_imprint.fieldset({legend:'Impressum und rechtliche Hinweise',hideLabels:true},
    imprint_text);
   
    form_imprint.fieldset({legend:'Entwicklung',hideLabels:true},
    imprint_text2);
     
    form_imprint.fieldset({legend:'Haftungsausschluss',hideLabels:true},
    imprint_text3);
     
   

   
    
    <!--  Die ANFRAGE_ID fuer ABBRECHEN wird hier gesetzt. dhaehn	-->
	form_imprint.addButton('Abbrechen', function(){
		top.location.href='DispatcherServlet';
	}, form_imprint);   
	
     form_imprint.render('form_imprint'); 
  
     
    }); 
</script>
</head>
<body>
<%@include file="include/inc_header_clean.jsp"%>
 
<div id="content">
<h1>Impressum</h1>
<div id="form_imprint"></div>
   
<%@include file="include/inc_footer_clean.jsp"%>
</div>

</body>
</html>