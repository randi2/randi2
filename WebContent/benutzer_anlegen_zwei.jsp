<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.utility.*"
	import="de.randi2.controller.DispatcherServlet"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.*"%>

<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.BENUTZER_ANLEGEN_ZWEI.toString());

	String aInstitution;
	String aAbteilung;

	if (request.getParameter(Parameter.zentrum.INSTITUTION.name()) == null) {

		aInstitution = "";

	} else {

		aInstitution = (String) request
		.getParameter(Parameter.zentrum.INSTITUTION.name());

	}
	if (request.getParameter(Parameter.zentrum.ABTEILUNGSNAME.name()) == null) {

		aAbteilung = "";

	} else {

		aAbteilung = (String) request
		.getParameter(Parameter.zentrum.ABTEILUNGSNAME.name());

	}

	Iterator listeZentren = ((Vector) request
			.getAttribute("listeZentren")).iterator();

	Iterator listeZentren2 = ((Vector) request
			.getAttribute("listeZentren")).iterator();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>

<script language="Javascript" src="js/motionpack.js"> </script>
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<%@include file="include/inc_extjs.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css">
<script language="Javascript" src="js/inlinebutton.js"> </script>
<script>
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var form_filter = new Ext.form.Form({
        labelAlign: 'top',
        labelWidth: 0,
		buttonAlign: 'left',
    });
    
    var institution_name = new Ext.form.TextField({
        fieldLabel: 'Name der Institution',
        name: '<%=Parameter.zentrum.INSTITUTION.name() %>',
        value: '<%=aInstitution %>',
        allowBlank:true,
        width:190
    });
    
    var abteilung_name = new Ext.form.TextField({
        fieldLabel: 'Name der Abteilung',
        name: '<%=Parameter.zentrum.ABTEILUNGSNAME.name() %>',
        value: '<%=aAbteilung %>',
        allowBlank:true,
        width:190
    });
    
    var filter = form_filter.fieldset({legend:'<img src="images/find.png"> Filterfunktion',style:''});
    var linksoben = new Ext.form.Column({width:'200'});
    var rechtsoben = new Ext.form.Column({width:'200'});
    
    
    form_filter.start(linksoben);
    form_filter.add(institution_name);
    form_filter.end(linksoben);
    form_filter.start(rechtsoben);
    form_filter.add(abteilung_name);
    form_filter.end(rechtsoben);
    

    
	form_filter.addButton('Filtern', function(){
		if (this.isValid()) {
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
		}else{
			Ext.MessageBox.alert('Errors', 'Die Eingaben waren fehlerhaft!');
		}
	}, form_filter);
	
	

	
    form_filter.render('form_filter');
	
	
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_filter.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.JSP_BENUTZER_ANLEGEN_ZWEI_BENUTZER_REGISTRIEREN_DREI.name() %>'});	
	form_filter.el.createChild({tag: 'input', name: '<%=Parameter.filter %>', type:'hidden', value: '<%=Parameter.filter %>'});	
    
    <%
		while (listeZentren.hasNext()) {
			ZentrumBean aktuellesZentrum = (ZentrumBean) listeZentren
			.next();
	%>    
	
	    var form_zentrum<%=aktuellesZentrum.getId() %> = new Ext.form.Form({
	        labelAlign: 'left',
	        labelWidth: 0,
			buttonAlign: 'left',
			
	    });
	    
	     var zentrum_passwort<%=aktuellesZentrum.getId() %> = new Ext.form.TextField({
	        name: '<%=Parameter.zentrum.PASSWORT.name() %><%=aktuellesZentrum.getId() %>',
	        allowBlank:false,
	        blankText:'Bitte Passwort eingeben!',
	        inputType:'password',
	        width:150
	    });
    
    	form_zentrum<%=aktuellesZentrum.getId() %>.fieldset({legend:'eingeben und weiter mit RETURN',hideLabels:true},zentrum_passwort<%=aktuellesZentrum.getId() %>);

    	
    	
    	form_zentrum<%=aktuellesZentrum.getId() %>.render('form_zentrum<%=aktuellesZentrum.getId() %>');
        

		form_zentrum<%=aktuellesZentrum.getId() %>.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.JSP_BENUTZER_ANLEGEN_ZWEI_BENUTZER_REGISTRIEREN_DREI.name() %>'});	
		form_zentrum<%=aktuellesZentrum.getId() %>.el.createChild({tag: 'input', name: '<%=Parameter.bestaetigen %><%=aktuellesZentrum.getId() %>', type:'hidden', value: '<%=Parameter.weiter %><%=aktuellesZentrum.getId() %>'});	
	      
    
	<%
		}	
    
    	
    
	%>
    
	var grid = new Ext.grid.TableGrid("zentren");
    grid.render();

	var form_abbrechen = new Ext.form.Form({
	        labelAlign: 'left',
	        labelWidth: 0,
			buttonAlign: 'left',
	    });

	<!--  Die ANFRAGE_ID fuer ABBRECHEN wird hier gesetzt. dhaehn	-->
	form_abbrechen.addButton('Abbrechen', function(){
		top.location.href='DispatcherServlet';
	}, form_abbrechen);  
	
	form_abbrechen.render('form_abbrechen');


    });

/**
 * @class Ext.grid.TableGrid
 * @extends Ext.grid.Grid
 * A Grid which creates itself from an existing HTML table element.
 * @constructor
 * @param {String/HTMLElement/Ext.Element} table The table element from which this grid will be created - 
 * The table MUST have some type of size defined for the grid to fill. The container will be 
 * automatically set to position relative if it isn't already.
 * @param {Object} config A config object that sets properties on this grid and has two additional (optional)
 * properties: fields and columns which allow for customizing data fields and columns for this grid.
 * @history
 * 2007-03-01 Original version by Nige "Animal" White
 * 2007-03-10 jvs Slightly refactored to reuse existing classes
 */
Ext.grid.TableGrid = function(table, config) {
    config = config || {};
    var cf = config.fields || [], ch = config.columns || [];
    table = Ext.get(table);

    var ct = table.insertSibling();

    var fields = [], cols = [];
    var headers = table.query("thead th");
	for (var i = 0, h; h = headers[i]; i++) {
		var text = h.innerHTML;
		var name = 'tcol-'+i;

        fields.push(Ext.applyIf(cf[i] || {}, {
            name: name,
            mapping: 'td:nth('+(i+1)+')/@innerHTML'
        }));
		if (i<2){
			cols.push(Ext.applyIf(ch[i] || {}, {
			'header': text,
			'dataIndex': name,
			'width': h.offsetWidth,
			'tooltip': h.title,
            'sortable': true
        }));
		}else{
				cols.push(Ext.applyIf(ch[i] || {}, {
			'header': text,
			'dataIndex': name,
			'width': h.offsetWidth,
			'tooltip': h.title,
            'sortable': false
        }));
		}
		
	}

    var ds  = new Ext.data.Store({
        reader: new Ext.data.XmlReader({
            record:'tbody tr'
        }, fields)
    });

	ds.loadData(table.dom);

    var cm = new Ext.grid.ColumnModel(cols);

    if(config.width || config.height){
        ct.setSize(config.width || 'auto', config.height || 'auto');
    }
    if(config.remove !== false){
        table.remove();
    }

    Ext.grid.TableGrid.superclass.constructor.call(this, ct,
        Ext.applyIf(config, {
            'ds': ds,
            'cm': cm,
            'sm': new Ext.grid.RowSelectionModel(),
            autoHeight:true,
            autoWidth:true
        }
    ));
};

Ext.extend(Ext.grid.TableGrid, Ext.grid.Grid);

</script>
</head>
<body>
<%@include file="include/inc_header_clean.jsp"%>

<div id="content">

<h1>Benutzer anlegen</h1>
<%@include file="include/inc_nachricht.jsp"%>
<div id="form_filter"></div>
<br>
<table width="90%" id="zentren">
	<thead align="left">
		<tr style="background:#eeeeee;">
			<th width="40%">Name der Institution</th>
			<th width="30%">Abteilung</th>
			<th width="40%">Passwort</th>
		</tr>
	</thead>

	<%
		String reihe = "tblrow1";
		int tabindex = 1;
		while (listeZentren2.hasNext()) {
			ZentrumBean aktuellesZentrum = (ZentrumBean) listeZentren2
			.next();
	%>

	<tr class="<%=reihe %>">
		<td><%=aktuellesZentrum.getInstitution()%></td>
		<td><%=aktuellesZentrum.getAbteilung()%></td>
		<td>
		<div id="form_zentrum<%=aktuellesZentrum.getId() %>"></div>
		</td>
	</tr>
	<%
			tabindex++;
			if (reihe.equals("tblrow1"))
				reihe = "tblrow2";
			else
				reihe = "tblrow1";
		}
	%>

</table>


<div id="form_abbrechen"></div>
<%@include file="include/inc_footer_clean.jsp"%>
</div>

</body>
</html>
