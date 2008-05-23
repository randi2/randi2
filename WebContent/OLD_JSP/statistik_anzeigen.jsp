<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="de.randi2.utility.*"
	import="de.randi2.controller.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.STATISTIK_ANZEIGEN.toString());

	StudieBean aStudie = (StudieBean) request.getSession()
			.getAttribute(
			DispatcherServlet.sessionParameter.AKTUELLE_STUDIE
			.toString());
	Vector<StudienarmBean> aStudienarme = aStudie.getStudienarme();
	long[][] aStatistik = (long[][]) request
			.getAttribute(StudieServlet.requestParameter.AKTUELLE_STATISTIK
			.toString());
	
	HashMap<Long,String> armId_armBezeichnung = new HashMap<Long,String>();
	
	long id = 0;
	String bezeichnung = null;
	for(int i=1;i<aStatistik.length;i++){
		id = aStatistik[i][0];
		for(StudienarmBean s:aStudienarme){
			if(id==s.getId()){
				bezeichnung = s.getBezeichnung();
				break;
			}
		}
		armId_armBezeichnung.put(id,bezeichnung);
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="de.randi2.model.fachklassen.beans.*"%>
<%@page import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Vector"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Randi2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<%@include file="include/inc_extjs.jsp"%>
<script>
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	
 
	var grid = new Ext.grid.TableGrid("statistik");
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
<%@include file="include/inc_nachricht.jsp"%>
<h1>Statistik</h1>
<br>
<table width="90%" id="statistik">
	<thead align="left">
		<tr style="background:#eeeeee;">
			<th width="40%"></th>
			<th width="20%">Anzahl der Patienten</th>
			<th width="20%">Anzahl der männlichen Pat.</th>
			<th width="20%">Anzahl der weiblichen Pat.</th>
		</tr>
	</thead>

	<tr class="tblrow1">
		<td><%="Studie: "+aStudie.getName()%></td>
		<td><%=aStatistik[0][1]%></td>
		<td><%=aStatistik[0][2]%></td>
		<td><%=aStatistik[0][3]%></td>
	</tr>

	<%
		String reihe = "tblrow2";
		int tabindex = 1;
		for(int i=0;i<aStudienarme.size();i++) {
	%>

	<tr class="<%=reihe %>">
		<td><%="Studienarm: "+armId_armBezeichnung.get(aStatistik[tabindex][0])%></td>
		<td><%=aStatistik[tabindex][1]%></td>
		<td><%=aStatistik[tabindex][2]%></td>
		<td><%=aStatistik[tabindex][3]%></td>
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
<%@include file="include/inc_footer.jsp"%>
</div>
<%@include file="/include/inc_menue.jsp"%>
</body>
</html>
