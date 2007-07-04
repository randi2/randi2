<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.utility.*" import="java.util.Iterator"
	import="java.util.Vector" import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.ZENTRUM_ANZEIGEN_ADMIN.toString());


String aZentrumInstitution = "";
if (request.getParameter(Parameter.zentrum.INSTITUTION.toString()) != null) {
	aZentrumInstitution = (String) request
	.getParameter(Parameter.zentrum.INSTITUTION.toString());
}
String aZentrumAbteilung = "";
if (request.getParameter(Parameter.zentrum.ABTEILUNGSNAME.toString()) != null) {
	aZentrumAbteilung = (String) request
	.getParameter(Parameter.zentrum.ABTEILUNGSNAME.toString());
}

String aStatus = ZentrumServlet.aktiviertDeaktiviert.KEINE_AUSWAHL.toString();
if (request.getParameter(Parameter.zentrum.AKTIVIERT.name()) != null) {
	aStatus = (String) request
	.getParameter(Parameter.zentrum.AKTIVIERT.name());
}

%>
<%@ page import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="de.randi2.controller.ZentrumServlet"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="include/inc_extjs.jsp"%>
<script>
Ext.onReady(function() {


	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var form_filter = new Ext.form.Form({
        labelAlign: 'top',
        labelWidth: 0,
		buttonAlign: 'left',
		id:'form_filter'
    });
    
    var zentrum_institution = new Ext.form.TextField({
        fieldLabel: 'Name der Institution',
        name: '<%=Parameter.zentrum.INSTITUTION.toString() %>',
        value: '<%=aZentrumInstitution %>',
        allowBlank:true,
        width:190
    });
    
    var zentrum_abteilung = new Ext.form.TextField({
        fieldLabel: 'Name der Abteilung',
        name: '<%=Parameter.zentrum.ABTEILUNGSNAME.toString() %>',
        value: '<%=aZentrumAbteilung %>',
        allowBlank:true,
        width:190
    });

   var zentrum_status = new Ext.form.ComboBox({
        fieldLabel: 'Status',
        hiddenName:'<%=Parameter.zentrum.AKTIVIERT.name() %>',
        store: new Ext.data.SimpleStore({
            fields: ['inst'],
            data : [
			<%
			for(ZentrumServlet.aktiviertDeaktiviert aktdeakt: ZentrumServlet.aktiviertDeaktiviert.values()){ 
			%>
			['<%=aktdeakt.toString()%>'],
			<%
					
				}
			%>
            ]
        }),
        displayField:'inst',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'<%=aStatus%>',
        value:'<%=aStatus%>',
        selectOnFocus:true,
        editable:false,
        width:250
    });

    var filter = form_filter.fieldset({legend:'<img src="images/find.png"> Filterfunktion',style:''});
    var linksoben = new Ext.form.Column({width:'200'});
    var rechtsoben = new Ext.form.Column({width:'200'});
    var rechtsoben2 = new Ext.form.Column({width:'300'});
    
    
    form_filter.start(linksoben);
    form_filter.add(zentrum_institution);
    form_filter.end(linksoben);
    form_filter.start(rechtsoben);
    form_filter.add(zentrum_abteilung);
    form_filter.end(rechtsoben);
    form_filter.start(rechtsoben2);
    form_filter.add(zentrum_status);
    form_filter.end(rechtsoben2);
    
	form_filter.addButton('Filtern', function(){
		if (this.isValid()) {
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
		}else{
			Ext.MessageBox.alert('Fehler', 'Die Eingaben waren fehlerhaft!');
		}
	}, form_filter);
	
	

	
    form_filter.render('form_filter');
	
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_filter.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.ZENTRUM_ANZEIGEN_ADMIN.name()%>'});	
	form_filter.el.createChild({tag: 'input', name: '<%=Parameter.filter %>', type:'hidden', value: '<%=Parameter.filter %>'});	
		

    var grid = new Ext.grid.TableGrid("zentren");
    grid.render();
    
    
 
    
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

		if (i<3){
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
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: <%=request.getAttribute(DispatcherServlet.requestParameter.TITEL.toString())%></title>
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<%@include file="include/inc_nachricht.jsp" %>
<h1>Zentren suchen</h1>
<div id="form_filter"></div>
<br>
<form action="DispatcherServlet" method="POST" name="liste_form" id="liste_form">
<input type="hidden" name="<%=Parameter.anfrage_id %>" value="<%=DispatcherServlet.anfrage_id.ZENTRUM_AENDERN_SPERREN.name() %>">
<input type="hidden" name="button" value="">
</form>
<table width="90%" id="zentren">
	<thead align="left">
		<tr class="tblrow1" align="left">
			<th width="30%">Name&nbsp;der&nbsp;Institution</th>
			<th width="30%">Name der Abteilung</th>
			<th width="20%">Status</th>
			<th width="30%">Aktion</th>
		</tr>
	</thead>
	<%
		Iterator listeZentrum = ((Vector) request.getAttribute("listeZentrum")).iterator();
		String reihe = "tblrow1";
		int tabIndex = 1;
		boolean aktiviert = false;
		while (listeZentrum.hasNext()) {
			ZentrumBean zentrum = (ZentrumBean) listeZentrum.next();
	%>
	<tr class="<%=reihe%>">
		<td><%=zentrum.getInstitution()%></td>
		<td><%=zentrum.getAbteilung()%></td>
		<%
			aktiviert = zentrum.getIstAktiviert();
			if(aktiviert){
		%>
		<td>aktiviert</td>
		<%
			} else {
		%>
		<td>deaktiviert</td>
		<%
			}
		%>
		<td><span  id="anzeige_aendern_link" style="cursor:pointer"
			onClick="document.forms['liste_form'].button.value = 'a_<%=zentrum.getId() %>';document.forms['liste_form'].submit();"><b>
		Anzeigen/&Auml;ndern</b>
		</span><br><span  id="sperren_link" style="cursor:pointer"
			onClick="document.forms['liste_form'].button.value = 's_<%=zentrum.getId() %>';document.forms['liste_form'].submit();">	<b>	
		<%
		if(aktiviert){
		%>
			Deaktivieren
		<%
		} else {
		%>	
			Aktivieren
		<%
			}
		%></b>
		</span></td>
	</tr>
	<%
			tabIndex++;
			if (reihe.equals("tblrow1")) {
				reihe = "tblrow2";
			} else {
				reihe = "tblrow1";
			}
		}//while
	%>
</table>

<%@include file="include/inc_footer.jsp"%></div>
<%@include file="include/inc_menue.jsp"%>
</body>
</html>
