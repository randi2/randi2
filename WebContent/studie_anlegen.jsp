<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="de.randi2.model.fachklassen.beans.*"
	import="java.util.Iterator" import="java.util.Vector"%>
<%@page import="de.randi2.controller.*"%>
<%@page import="de.randi2.utility.*"%>
<%
			Rolle.Rollen aRolle = ((BenutzerkontoBean) request.getSession()
			.getAttribute("aBenutzer")).getRolle().getRollenname();

	GregorianCalendar heute = new GregorianCalendar();
%>
<html>
<head>
<title>Randi2 :: Studie anlegen</title>
<link rel="stylesheet" type="text/css"
	href="js/ext/resources/css/ext-all.css" />
<!-- GC -->
<!-- link rel="stylesheet" type="text/css" 	href="js/ext/resources/css/xtheme-gray.css" /-->
<!-- LIBS -->
<script type="text/javascript" src="js/ext/adapter/yui/yui-utilities.js"></script>
<script type="text/javascript"
	src="js/ext/adapter/yui/ext-yui-adapter.js"></script>
<!-- ENDLIBS -->
<script type="text/javascript" src="js/ext/ext-all.js"></script>
<script>
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var form_studie_anlegen = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 250,
		buttonAlign: 'right',
    });

    var studie_name = new Ext.form.TextField({
        fieldLabel: 'Name der Studie',
        name: '<%=Parameter.studie.NAME.name() %>',
        allowBlank:false,
        minLength:3,
        maxLength:50,
        invalidText:'Dieser Name ist ung&uuml;ltig!',
        blankText:'Der Name darf nicht leer sein!',
        width:190
    });

    var studie_beschreibung = new Ext.form.TextArea({
        fieldLabel: 'Beschreibung der Studie',
        name: '<%=Parameter.studie.BESCHREIBUNG.name() %>',
        allowBlank:true,
        width:300
    });

    var studie_startdatum = new Ext.form.DateField({
        fieldLabel: 'Startdatum',
        name: '<%=Parameter.studie.STARTDATUM.name() %>',
        width:100,
        allowBlank:false,
        blankText:'Bitte das Startdatum w&auml;hlen!',
		format:'d.m.Y'
    });
    
    var studie_enddatum = new Ext.form.DateField({
        fieldLabel: 'Enddatum',
        name: '<%=Parameter.studie.ENDDATUM.name() %>',
        width:100,
        allowBlank:false,
        blankText:'Bitte das Enddatum w&auml;hlen!',
		format:'d.m.Y'
    });

	var studie_statistiker_boolean = new Ext.form.Checkbox({
        fieldLabel: 'Statistiker Account anlegen',
        name: '<%=Parameter.studie.STATISTIKER_BOOL.name() %>'
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
	
	form_studie_anlegen.fieldset({legend:'Randomisation'},studie_name);
	
	form_studie_anlegen.fieldset({legend:'Studienarme'},studie_name);
	
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
		top.location.href='DispatcherServlet?<%=Parameter.anfrage_id %>=<%=DispatcherServlet.anfrage_id.JSP_STUDIE_ANLEGEN %>';
	}, form_studie_anlegen);
	
			
	form_studie_anlegen.render('studie_anlegen');
	
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_studie_anlegen.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.JSP_STUDIE_ANLEGEN %>'});	

	<!-- folgende Zeile ermoeglicht File-Uploads via POST -->
	form_studie_anlegen.el.enctype = 'multipart/form-data';

});


</script>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content"><%@include file="include/inc_nachricht.jsp"%>
<h1>Studie anlegen</h1>
<div id="studie_anlegen"></div>



<form action="DispatcherServlet"><input type="hidden" name=""
	value="<%=DispatcherServlet.anfrage_id.JSP_STUDIE_ANLEGEN %>">
<fieldset style="width: 70%;"><legend><b>Studienangaben</b></legend>
<table>
	<tr>
		<td>Name der Studie *<br>
		<input size="40" maxlength="40"
			name="<%=Parameter.studie.NAME.name() %>" tabindex="1" type="text"></td>
		<td>Startdatum *<br>
		<input size="40" maxlength="40"
			name="<%=Parameter.studie.STARTDATUM.name() %>" tabindex="3"
			value="Vorerst nur ein Textfeld" type="text"></td>
	</tr>
	<tr>
		<td>Beschreibung der Studie *<br>
		<textarea cols="37" rows="4"
			name="<%=Parameter.studie.BESCHREIBUNG.name() %>" tabindex="2"></textarea></td>
		<td>Enddatum *<br>
		<input size="40" maxlength="40"
			name="<%=Parameter.studie.ENDDATUM.name() %>" tabindex="4"
			value="Vorerst nur ein Textfeld" type="text"></td>
	</tr>
</table>
</fieldset>
<fieldset style="width: 70%;"><legend><b>Zusatzangaben</b></legend>
<table>
	<tbody>
		<tr>
			<td>Studienprotokoll *&nbsp;&nbsp;&nbsp;<input
				name="<%=Parameter.studie.STUDIENPROTOKOLL.name() %>" size="50"
				maxlength="100000" accept="text/*" tabindex="5" type="file"><br>
			<br>
			</td>
		</tr>
		<tr>
			<td>Arme der Studie</td>
		</tr>
	</tbody>
</table>
<table width="90%">
	<tbody>
		<tr>
			<th align="left">Bezeichnung</th>
			<th align="left">Beschreibung</th>
		</tr>
		<tr>
			<td><input name="studienarm1" value="Studienarm1" size="30"
				type="text"></td>
			<td><input name="beschreibung1" value="..." size="80"
				type="text"></td>
		</tr>
		<tr>
			<td><input name="studienarm2" value="Studienarm2" size="30"
				type="text"></td>
			<td><input name="beschreibung2" value="..." size="80"
				type="text"></td>
		</tr>
		<tr>
			<td><input name="studienarm3" value="Studienarm3" size="30"
				type="text"></td>
			<td><input name="beschreibung3" value="..." size="80"
				type="text"></td>
		</tr>
		<tr>
			<td><input name="studienarm4" value="Studienarm4" size="30"
				type="text"></td>
			<td><input name="beschreibung4" value="..." size="80"
				type="text"></td>
		</tr>
		<tr>
			<td><input name="studienarm5" value="Studienarm5" size="30"
				type="text"></td>
			<td><input name="beschreibung5" value="..." size="80"
				type="text"></td>
		</tr>
	</tbody>
</table>
</fieldset>
<fieldset style="width: 70%;"><legend><b>Statistiker</b></legend>
<table>
	<tbody>
		<tr>
			<td>Soll ein Statistiker-Account f&uuml;r die Studie angelegt
			werden ?</td>
		</tr>
		<tr>
			<td><br>
			<input name="<%=Parameter.studie.STATISTIKER_BOOL.name() %>"
				value="TRUE" tabindex="1" type="radio">Ja&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input name="<%=Parameter.studie.STATISTIKER_BOOL.name() %>"
				value="FALSE" tabindex="2" type="radio">Nein</td>
		</tr>
	</tbody>
</table>
</fieldset>
<br>
<table align="center">
	<tbody>
		<tr>
			<td><input name="bestaetigen" value="Bestätigen" tabindex="11"
				onclick="location.href='randomisation.jsp'" type="button">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td><input name="abbrechen" value="Abbrechen" tabindex="12"
				onclick="top.location.href='studie_auswaehlen.jsp'" type="button"></td>
		</tr>
	</tbody>
</table>

</form>
<%@include file="include/inc_footer.jsp"%></div>
</body>
</html>
