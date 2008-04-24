<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.controller.DispatcherServlet"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.*"
	import="de.randi2.controller.StudieServlet"
	import="de.randi2.utility.Parameter" import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.ZENTRUM_ANZEIGEN.toString());
%>
<%
			StudieBean aSession = (StudieBean) request.getSession()
			.getAttribute(
			DispatcherServlet.sessionParameter.AKTUELLE_STUDIE.toString());
	ZentrumBean chefZentrum = null;
	boolean veraendernErlaubt = false;
	if (aSession != null) {
		chefZentrum= aSession.getBenutzerkonto()
		.getZentrum();
	}
	if(aSession.getStatus().toString().equals(Studie.Status.INVORBEREITUNG.toString())){
		veraendernErlaubt = true;
	}

	Vector<ZentrumBean> zugehoerigeZentren = new Vector<ZentrumBean>();
	Vector<ZentrumBean> nichtZugehoerigeZentren = new Vector<ZentrumBean>();
	ZentrumBean aktuellesZentrum = null;
	Iterator itZugehoerigeZentren = null;
	Iterator itNichtZugehoerigeZentren = null;

	try {
		zugehoerigeZentren = (Vector<ZentrumBean>) request
		.getAttribute(StudieServlet.requestParameter.ZUGHOERIGE_ZENTREN
				.toString());

		nichtZugehoerigeZentren = (Vector<ZentrumBean>) request
		.getAttribute(StudieServlet.requestParameter.NICHT_ZUGEHOERIGE_ZENTREN
				.toString());

		itZugehoerigeZentren = zugehoerigeZentren.iterator();
		itNichtZugehoerigeZentren = nichtZugehoerigeZentren.iterator();
		
	} catch (NullPointerException npe) {
		;
	}

	String aZentrumInstitution = "";
	if (request.getParameter(Parameter.zentrum.INSTITUTION.toString()) != null) {
		aZentrumInstitution = (String) request
		.getParameter(Parameter.zentrum.INSTITUTION.toString());
	}
	String aZentrumAbteilung = "";
	if (request.getParameter(Parameter.zentrum.ABTEILUNGSNAME
			.toString()) != null) {
		aZentrumAbteilung = (String) request
		.getParameter(Parameter.zentrum.ABTEILUNGSNAME
				.toString());
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<%@include file="include/inc_extjs.jsp"%>
<script>
Ext.onReady(function() {


	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var form_filter = new Ext.form.Form({
        labelAlign: 'top',
        labelWidth: 0,
		buttonAlign: 'left',
		id:'id_form_filter'
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

    var filter = form_filter.fieldset({legend:'<img src="images/find.png"> Filterfunktion',style:''});
    var linksoben = new Ext.form.Column({width:'200'});
    var rechtsoben = new Ext.form.Column({width:'300'});
    
    
    form_filter.start(linksoben);
    form_filter.add(zentrum_institution);
    form_filter.end(linksoben);
    form_filter.start(rechtsoben);
    form_filter.add(zentrum_abteilung);
    form_filter.end(rechtsoben);

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
	form_filter.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=StudieServlet.anfrage_id.JSP_ZENTRUM_ANZEIGEN%>'});	
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
</head>
<body>

<%@include file="include/inc_header.jsp"%>

<div id="content">

<h1>Zentrum suchen</h1>
<div id="form_filter"></div>
<br>
<table width="90%" id="zentren">
	<thead align="left">
		<tr style="background: #eeeeee;">
			<th width="30%">Name der Institution</th>
			<th width="30%">Name der Abteilung</th>
			<th width="10%">Status</th>
			<th width="30%">Aktion</th>
		</tr>
	</thead>
	<tbody>

		<%
			String aktiv = null;
			int tabindex = 1;
			String reihe = "tblrow2";

			if (zugehoerigeZentren != null) {

				while (itZugehoerigeZentren.hasNext()) {
					aktuellesZentrum = (ZentrumBean) itZugehoerigeZentren
					.next();

					if (aktuellesZentrum.getIstAktiviert()) {
				aktiv = "aktiv";
					} else {
				aktiv = "inaktiv";
					}
		%>

		<tr class="<%=reihe %>">
			<td><%=aktuellesZentrum.getInstitution()%></td>
			<td><%=aktuellesZentrum.getAbteilung()%></td>
			<td><%=aktiv%></td>
			<td>
			<form action="DispatcherServlet" method="POST"
				name="zentrenAnzeigen_form<%=tabindex %>"
				id="zentrenAnzeigen_form<%=tabindex %>"><input type="hidden"
				name="<%=Parameter.zentrum.ZENTRUM_ID.toString()%>"
				value="<%=aktuellesZentrum.getId() %>"><input type="hidden"
				name="<%=Parameter.anfrage_id %>" value="hallo"></form>
			<span id="zentrenAnzeigen_link<%=tabindex %>" style="cursor: pointer"
				onClick="document.forms['zentrenAnzeigen_form<%=tabindex %>'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_ZENTRUM_ANSEHEN.name() %>';

				document.forms['zentrenAnzeigen_form<%=tabindex %>'].<%=Parameter.zentrum.ZENTRUM_ID.toString()%>.value = '<%=aktuellesZentrum.getId() %>';document.forms['zentrenAnzeigen_form<%=tabindex %>'].submit();">
			<b>Zentrumsdetails</b></span> <%if(veraendernErlaubt){
 if (!aktuellesZentrum.equals(chefZentrum)) {
 %> <span id="zentrenAnzeigen_link<%=tabindex %>"
				style="cursor: pointer"
				onClick="document.forms['zentrenAnzeigen_form<%=tabindex %>'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.AKTION_ZENTRUM_ENTZIEHEN.name() %>';

				document.forms['zentrenAnzeigen_form<%=tabindex %>'].<%=Parameter.zentrum.ZENTRUM_ID.toString()%>.value = '<%=aktuellesZentrum.getId() %>';document.forms['zentrenAnzeigen_form<%=tabindex %>'].submit();"><br>
			<b>Zentrum von Studie entfernen</b></span> <%
 }}
 %>
			</td>
		</tr>
		<%
					tabindex++;
					if (reihe.equals("tblrow1")) {
				reihe = "tblrow2";
					} else
				reihe = "tblrow1";
				}

			}
			if (nichtZugehoerigeZentren != null) {

				while (itNichtZugehoerigeZentren.hasNext()) {
					aktuellesZentrum = (ZentrumBean) itNichtZugehoerigeZentren
					.next();
					if (aktuellesZentrum.getIstAktiviert()) {
				aktiv = "aktiv";
					} else {
				aktiv = "inaktiv";
					}
		%>

		<tr class="<%=reihe %>">
			<td><%=aktuellesZentrum.getInstitution()%></td>
			<td><%=aktuellesZentrum.getAbteilung()%></td>
			<td><%=aktiv%></td>
			<td>
			<form action="DispatcherServlet" method="POST"
				name="zentrenAnzeigen_form<%=tabindex %>"
				id="zentrenAnzeigen_form<%=tabindex %>"><input type="hidden"
				name="<%=Parameter.zentrum.ZENTRUM_ID.toString()%>"
				value="<%=aktuellesZentrum.getId() %>"><input type="hidden"
				name="<%=Parameter.anfrage_id %>" value="hallo"></form>
			<span id="zentrenAnzeigen_link<%=tabindex %>" style="cursor: pointer"
				onClick="document.forms['zentrenAnzeigen_form<%=tabindex %>'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_ZENTRUM_ANSEHEN.name() %>';

				document.forms['zentrenAnzeigen_form<%=tabindex %>'].<%=Parameter.zentrum.ZENTRUM_ID.toString()%>.value = '<%=aktuellesZentrum.getId() %>';document.forms['zentrenAnzeigen_form<%=tabindex %>'].submit();">
			<b>Zentrumsdetails</b></span><% if (!aktuellesZentrum.equals(chefZentrum)) { %><span id="zentrenAnzeigen_link<%=tabindex %>"
				style="cursor: pointer"
				onClick="document.forms['zentrenAnzeigen_form<%=tabindex %>'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.AKTION_ZENTRUM_ZUWEISEN.name() %>';

				document.forms['zentrenAnzeigen_form<%=tabindex %>'].<%=Parameter.zentrum.ZENTRUM_ID.toString()%>.value = '<%=aktuellesZentrum.getId() %>';document.forms['zentrenAnzeigen_form<%=tabindex %>'].submit();"><br>
			<b>Zentrum zur Studie hinzuf&uuml;gen</b></span><% } %></td>
		</tr>
		<%
					tabindex++;
					if (reihe.equals("tblrow1"))
				reihe = "tblrow2";
					else
				reihe = "tblrow1";
				}

			}
		%>
	</tbody>
</table>

<%@include file="include/inc_footer.jsp"%></div>


<%@include file="include/inc_menue.jsp"%>

</body>
</html>
