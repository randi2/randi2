<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL.toString(),"Studie ausw&auml;hlen");

			Rolle.Rollen aRolle = ((BenutzerkontoBean) request.getSession()
			.getAttribute("aBenutzer")).getRolle().getRollenname();

	Iterator listeStudien = ((Vector) request
			.getAttribute(StudieServlet.requestParameter.LISTE_DER_STUDIEN
			.name())).iterator();
	
	Iterator listeStudien2 = ((Vector) request
			.getAttribute(StudieServlet.requestParameter.LISTE_DER_STUDIEN
			.name())).iterator();
	
	String aStudie_Name = "";
	if(request.getParameter(Parameter.studie.NAME.toString())!=null){
		aStudie_Name = (String) request.getParameter(Parameter.studie.NAME.toString());
	}
	
	String aStudie_Status = "";
	if(request.getParameter(Parameter.studie.STATUS.toString())!=null){
		aStudie_Status = request.getParameter(Parameter.studie.STATUS.toString());
	}
%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Vector"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Randi2 :: <%=request.getAttribute(DispatcherServlet.requestParameter.TITEL.toString()) %></title>

<%@include file="include/inc_extjs.jsp"%>
<script>
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var form_filter = new Ext.form.Form({
        labelAlign: 'top',
        labelWidth: 0,
		buttonAlign: 'left',
    });
    
    var studie_name = new Ext.form.TextField({
        fieldLabel: 'Name der Studie',
        name: '<%=Parameter.studie.NAME.toString() %>',
        value: '<%=aStudie_Name %>',
        allowBlank:true,
        width:190
    });

    
    var studie_status = new Ext.form.ComboBox({
        fieldLabel: 'Status der Studie',
        hiddenName:'<%=Parameter.studie.STATUS.toString()%>',
        store: new Ext.data.SimpleStore({
            fields: ['status'],
            data : [
			<%
				StringBuffer status = new StringBuffer();
				for (int i = 0; i < Studie.Status.values().length; i++) {
					status.append(Studie.Status.values()[i].toString());
			%>
			['<%=status%>'],
			<%
					status.delete(0, status.length());
				}
			%>
            ]
        }),
        displayField:'status',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'<%=aStudie_Status%>',
        value:'<%=aStudie_Status%>',
        selectOnFocus:true,
        editable:false,
        width:250
    });
    
    var filter = form_filter.fieldset({legend:'<img src="images/find.png"> Filterfunktion',style:''});
    var linksoben = new Ext.form.Column({width:'200'});
    var rechtsoben = new Ext.form.Column({width:'300'});
    
    
    form_filter.start(linksoben);
    form_filter.add(studie_name);
    form_filter.end(linksoben);
    form_filter.start(rechtsoben);
    form_filter.add(studie_status);
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
	form_filter.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=StudieServlet.anfrage_id.JSP_STUDIE_AUSWAEHLEN_FILTERN%>'});	
	form_filter.el.createChild({tag: 'input', name: '<%=Parameter.filter %>', type:'hidden', value: '<%=Parameter.filter %>'});	
    
    <%
		while (listeStudien.hasNext()) {
			StudieBean aktuelleStudie = (StudieBean) listeStudien
			.next();
	%>    
	
	    var form_studie<%=aktuelleStudie.getId() %> = new Ext.form.Form({
	        labelAlign: 'left',
	        labelWidth: 0,
			buttonAlign: 'left',
			
	    });
	     
    
	<%
		}	
    
    	
    
	%>
    
	var grid = new Ext.grid.TableGrid("studien");
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
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<h1>Studie ausw&auml;hlen</h1>

<%
if (aRolle == Rolle.Rollen.STUDIENLEITER) {
%>
<form action="DispatcherServlet" method="POST"><input
	type="hidden" name="<%=Parameter.anfrage_id%>"
	value="<%=DispatcherServlet.anfrage_id.JSP_STUDIE_AUSWAEHLEN_NEUESTUDIE.name() %>"><input
	type="submit" value="Neue Studie anlegen"></form>
&nbsp;&nbsp;&nbsp;::&nbsp;&nbsp;&nbsp;
<form action="DispatcherServlet" method="POST"><input
	type="hidden" name="<%=Parameter.anfrage_id%>"
	value="JSP_STUDIE_AUSWAEHLEN_SIMULATION"><input type="submit"
	value="Simulation"></form>
<br>
<br>
<%
}
%>
<div id="form_filter"></div>
<br>
<table width="90%" id="studien">
	<thead align="left" >
		<tr style="background:#eeeeee;">
			<th width="40%">Name der Studie</th>
			<th width="30%">Leitendes Zentrum</th>
			<th width="40%">Status</th>
		</tr>
	</thead>

	<%
		String reihe = "tblrow1";
		int tabindex = 1;
		while (listeStudien2.hasNext()) {
			StudieBean aktuelleStudie = (StudieBean) listeStudien2
			.next();
	%>

	<tr class="<%=reihe %>">
		<td><%=aktuelleStudie.getName()%></td>
		<td><%=aktuelleStudie.getBenutzerkonto().getZentrum().getInstitution()%></td>
		<td><%=aktuelleStudie.getStatus().toString() %></td>
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

<%@include file="include/inc_footer.jsp"%></div>
<%
if (aRolle == Rolle.Rollen.ADMIN) {
%>
<%@include file="/include/inc_menue.jsp"%>
<%
}
%>
</body>
</html>
