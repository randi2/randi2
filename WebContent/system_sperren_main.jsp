<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="de.randi2.model.fachklassen.beans.PersonBean"
	import="java.util.GregorianCalendar"
	import="java.text.SimpleDateFormat" import="java.util.Locale"
	import="de.randi2.controller.DispatcherServlet"
	import="de.randi2.model.fachklassen.beans.StudieBean"
	import="de.randi2.utility.*"%>
<%
			request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.SYSTEM_SPERREN_MAIN.toString());

	boolean gesperrt = (Boolean) request
			.getAttribute("system_gesperrt");
	String msg_gesperrt = (String) request
			.getAttribute("mitteilung_system_gesperrt");
	
	if (msg_gesperrt==null) { msg_gesperrt="keine"; }
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
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var form_sperren = new Ext.form.Form({
        labelAlign: 'top',
        labelWidth: 100,
		buttonAlign: 'left'
    });
	

    var grund = new Ext.form.TextArea({
        fieldLabel: 'Grund der letzten Sperrung/Default:',
        name: '<%=DispatcherServlet.requestParameter.MITTEILUNG_SYSTEM_GESPERRT.toString() %>',
        allowBlank:false,
        blankText:'Bitte einen Grund eingeben!',
        width:350,
        value:'<%=msg_gesperrt.replaceAll("\\n","\\\\n").replaceAll("\\r","\\\\r")%>',
        height:150
    });
	
    form_sperren.fieldset(
        {legend:'Angaben zur Sperrung',labelSeparator:''},
		grund
	);	
	
	form_sperren.addButton('<%if(!gesperrt){out.print("Sperren");}else{out.print("Entsperren");} %>', function(){
		if (this.isValid()) {
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
		}else{
			Ext.MessageBox.alert('Fehler', 'Die Eingaben waren fehlerhaft!');
		}
	}, form_sperren);
	

	
	form_sperren.render('form_sperren');
	
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	<%if (gesperrt) {%>
	form_sperren.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.AKTION_SYSTEM_ENTSPERREN.name() %>'});	
	<% } else { %>
	form_sperren.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.AKTION_SYSTEM_SPERREN.name() %>'});	
	<% } %>
});

</script>
<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@include file="include/inc_header.jsp"%>
<div id="content">
<%
if (gesperrt) {
%>
<h1>System entsperren</h1><br>
<p><b>Das System ist derzeit gesperrt.</b></p><br>
<p>Folgende Begr&uuml;ndung wurde angegeben;<br>
"<%=msg_gesperrt%>"</p>
<%
} else {
%>
<h1>System sperren</h1><br>
<p><b>Das System ist derzeit nicht gesperrt.</b></p><br>
<p>Wenn Sie das System sperren m&ouml;chten, so werden sich keine
neuen Benutzer in das System einloggen k&ouml;nnen. Bereits eingeloggte
Benutzer bleiben allerdings eingeloggt!</p>
<%
}
%>
<br><br><div id="form_sperren"></div>

<%@include file="include/inc_footer.jsp"%></div>
<%@include file="include/inc_menue.jsp"%>

</body>
</html>
