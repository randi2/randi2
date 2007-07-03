<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.utility.*" import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="java.util.Iterator" import="java.util.Vector"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.ADMIN_LISTE.toString());


String aVorname = "";
if (request.getParameter(Parameter.person.VORNAME.toString()) != null) {
	aVorname = (String) request
	.getParameter(Parameter.person.VORNAME.toString());
}
String aNachname = "";
if (request.getParameter(Parameter.person.NACHNAME.toString()) != null) {
	aNachname = (String) request
	.getParameter(Parameter.person.NACHNAME.toString());
}
String aLoginname = "";
if (request.getParameter(Parameter.benutzerkonto.LOGINNAME.toString()) != null) {
	aLoginname = (String) request
	.getParameter(Parameter.benutzerkonto.LOGINNAME.toString());
}
String aEmail = "";
if (request.getParameter(Parameter.person.EMAIL.toString()) != null) {
	aEmail = (String) request
	.getParameter(Parameter.person.EMAIL.toString());
}
String aInstitut = ZentrumServlet.ALLE_ZENTREN;
if (request.getParameter(Parameter.zentrum.INSTITUTION.toString()) != null) {
	aInstitut = (String) request
	.getParameter(Parameter.zentrum.INSTITUTION.toString());
}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="de.randi2.controller.ZentrumServlet"%>
<%
//Rolle holen
Rolle.Rollen aRolle=((BenutzerkontoBean)request.getSession().getAttribute(DispatcherServlet.sessionParameter.A_Benutzer.toString())).getRolle().getRollenname(); 
%>
<%@page import="de.randi2.model.fachklassen.Rolle.Rollen"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="Javascript" src="js/motionpack.js"> </script>
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
		id:'form_filter'
    });
    
    var vorname = new Ext.form.TextField({
        fieldLabel: 'Vorname',
        name: '<%=Parameter.person.VORNAME.toString() %>',
        value: '<%=aVorname %>',
        allowBlank:true,
        width:190
    });
    
    var nachname = new Ext.form.TextField({
        fieldLabel: 'Nachname',
        name: '<%=Parameter.person.NACHNAME.toString() %>',
        value: '<%=aNachname %>',
        allowBlank:true,
        width:190
    });
    
    var loginname = new Ext.form.TextField({
        fieldLabel: 'Benutzername',
        name: '<%=Parameter.benutzerkonto.LOGINNAME.toString() %>',
        value: '<%=aLoginname %>',
        allowBlank:true,
        width:190
    });
    
    var email = new Ext.form.TextField({
        fieldLabel: 'E-Mail',
        name: '<%=Parameter.person.EMAIL.toString() %>',
        value: '<%=aEmail %>',
        allowBlank:true,
        width:190
    });

   var institution = new Ext.form.ComboBox({
        fieldLabel: 'Institution',
        hiddenName:'<%=Parameter.zentrum.INSTITUTION.toString()%>',
        store: new Ext.data.SimpleStore({
            fields: ['inst'],
            data : [['<%=ZentrumServlet.ALLE_ZENTREN %>'],
			<%
			Iterator<ZentrumBean> it=Zentrum.getAlleZentrenAktiviertDeaktiviert().iterator();
			while(it.hasNext()){
			%>
			['<%=it.next().getInstitution()%>'],
			<%
					
				}
			%>
            ]
        }),
        displayField:'inst',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'<%=aInstitut%>',
        value:'<%=aInstitut%>',
        selectOnFocus:true,
        editable:false,
        width:250
    });

    var filter = form_filter.fieldset({legend:'<img src="images/find.png"> Filterfunktion',style:''});
    var linksoben = new Ext.form.Column({width:'300'});
    var rechtsoben = new Ext.form.Column({width:'300'});
    var rechtsoben2 = new Ext.form.Column({width:'300'});
    
    form_filter.start(linksoben);
    form_filter.add(nachname);
    form_filter.add(vorname);

    
    form_filter.end(linksoben);
    form_filter.start(rechtsoben);
    form_filter.add(loginname);
    form_filter.add(email);
    form_filter.end(rechtsoben);
    form_filter.start(rechtsoben2);
    form_filter.add(institution);
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
	form_filter.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.BENUTZER_SUCHEN.name() %>'});	
	form_filter.el.createChild({tag: 'input', name: '<%=Parameter.filter %>', type:'hidden', value: '<%=Parameter.filter %>'});	
		

    var grid = new Ext.grid.TableGrid("users");
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
<h1><%if(aRolle==Rolle.Rollen.ADMIN){out.print("Benutzer anzeigen/suchen");}
else if(aRolle==Rolle.Rollen.STUDIENLEITER){out.print("Studien&auml;rzte anzeigen/suchen");}
else if (aRolle==Rolle.Rollen.SYSOP){out.print("Admins anzeigen/suchen");}
%></h1>
<div id="form_filter"></div>
<br>
<form action="DispatcherServlet" method="POST" name="liste_form" id="liste_form">
<input type="hidden" name="<%=Parameter.anfrage_id %>" value="<%=DispatcherServlet.anfrage_id.JSP_BENUTZER_LISTE_ADMIN_ANZEIGEN_SPERREN.name() %>" />
<input type="hidden" name="button" value="" /></form>
<table width="90%" id="users">



	<thead align="left">
		<tr class="tblrow1" align="left">
			<th width="10%">Nachname</th>
			<th width="10%">Vorname</th>
			<th width="10%">Loginname</th>
			<th width="30%">Institut</th>
			<th width="25%">E-Mail</th>
			<%//Patientenanzahl in aktueller Studie, falls Studienleiter
			if(aRolle==Rolle.Rollen.STUDIENLEITER){
				out.print("<th>Patientenanzahl</th>");
			}%>
			<th width="15%">Aktion</th>
		</tr>
	</thead>
	<%
				Iterator listeBenutzer = ((Vector) request
				.getAttribute("listeBenutzer")).iterator();
		String reihe = "tblrow1";
		int tabIndex = 1;
		while (listeBenutzer.hasNext()) {
			BenutzerSuchenBean benutzer = (BenutzerSuchenBean) listeBenutzer.next();
	%>
	<tr class="<%=reihe%>">
		<td><%=benutzer.getNachname()%></td>
		<td><%=benutzer.getVorname()%></td>
		<td><%=benutzer.getLoginname()%></td>
		<td><%=benutzer.getInstitut()%></td>
		<td><%=benutzer.getEmail()%></td>
		<%//Patientenanzahl in aktueller Studie, falls Studienleiter
			if(aRolle==Rolle.Rollen.STUDIENLEITER){
				out.print("<td>"+benutzer.getPatientenAnzahl()+"</td>");
			}%>
		<td><span  id="benutzer_anzeigen_link" style="cursor:pointer"
			onClick="document.forms['liste_form'].button.value = 'a_<%=benutzer.getBenutzerId() %>';document.forms['liste_form'].submit();">
		<b>Anzeigen</b>
		</span></td>
		<td><span  id="sperren_link" style="cursor:pointer"
			onClick="document.forms['liste_form'].button.value = 's_<%=benutzer.getBenutzerId() %>';document.forms['liste_form'].submit();">		
		<%if(benutzer.isGesperrt()){out.print("Entsperren");}else{out.print("Sperren");} %>
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
