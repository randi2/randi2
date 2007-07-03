<%@ page import="de.randi2.model.fachklassen.*"
	import="de.randi2.model.fachklassen.beans.BenutzerkontoBean"
	import="de.randi2.model.fachklassen.beans.*"
	import="de.randi2.model.fachklassen.beans.AktivierungBean"
	import="java.util.GregorianCalendar"
	import="de.randi2.model.fachklassen.beans.PersonBean"
	import="de.randi2.controller.DispatcherServlet"
	import="de.randi2.utility.Hilfe"
	import="de.randi2.utility.JspTitel"
	%>
<script>

// create the RANDI2 HELP Dialog!
var Hilfe = function(){

    var dialog, showBtn;

    // return a public interface
    return {
        init : function(){
             showBtn = Ext.get('show-dialog-btn');
             // attach to click event
             showBtn.on('click', this.showDialog, this);
             
        },
        
        showDialog : function(){
            if(!dialog){ // lazy initialize the dialog and only create it once
                dialog = new Ext.LayoutDialog("randi_help", { 
                        modal:true,
                        width:600,
                        height:400,
                        shadow:true,
                        minWidth:300,
                        minHeight:300,
                        proxyDrag: true,
	                    center: {
	                        autoScroll:true,
	                        tabPosition: 'top',
	                        closeOnTab: true,
	                        alwaysShowTabs: true
	                    }
                });
                dialog.addKeyListener(27, dialog.hide, dialog);
                dialog.addButton('Schliessen', dialog.hide, dialog);
                

                var layout = dialog.getLayout();
                layout.beginUpdate();
	            layout.add('center', new Ext.ContentPanel('center', {title: '<%= (String) request
	    			.getAttribute(DispatcherServlet.requestParameter.TITEL
	    					.toString()) %>'}));
				layout.endUpdate();
            }
            dialog.show(showBtn.dom);
        }
    };
}();

// using onDocumentReady instead of window.onload initializes the application
// when the DOM is ready, without waiting for images and other resources to load
Ext.EventManager.onDocumentReady(Hilfe.init, Hilfe, true);
</script>
<%
			BenutzerkontoBean aBenutzer = (BenutzerkontoBean) (request
			.getSession()).getAttribute(DispatcherServlet.sessionParameter.A_Benutzer.toString());

	Rolle seineRolle = aBenutzer.getRolle();

	StudieBean aStudie_header = (StudieBean) (request.getSession()
			.getAttribute(DispatcherServlet.sessionParameter.AKTUELLE_STUDIE
			.toString()));

	String aLocation = (String) request
			.getAttribute(DispatcherServlet.requestParameter.TITEL
			.toString());
	String aStudieName = "";

	if (aLocation == null) {

		aLocation = "KEIN TITEL GESETZT!";

	}

	if (aStudie_header == null) {

		aStudieName = "KEINE STUDIE GEWAEHLT!";

	} else {

		aStudieName = aStudie_header.getName();
	}

	PersonBean aPersonHeader = null;
	Rolle.Rollen aRolleHeader = null;

	if (aBenutzer != null) {
		aBenutzer = (BenutzerkontoBean) request.getSession()
		.getAttribute("aBenutzer");
		aPersonHeader = aBenutzer.getBenutzer();
		aRolleHeader = aBenutzer.getRolle().getRollenname();
	} else {
		response.sendRedirect(response.encodeRedirectURL("index.jsp"));
		return;
	}
%>
<%@page import="de.randi2.utility.Config"%>
<%@page import="de.randi2.utility.Parameter"%>
<%@page import="de.randi2.controller.StudieServlet"%>
<div id="header"><img
	src="<%=Config.getProperty(Config.Felder.RELEASE_BILD_LOGO) %>"
	width="337" height="63" title="" alt=""></div>

<div id="breadcrumb">
<table width="100%" border="0" cellSpacing="2" class="breadcrumb_tbl">
	<tr>
		<td>
		<%
					if (aRolleHeader != Rolle.Rollen.SYSOP
					&& request
					.getAttribute(DispatcherServlet.requestParameter.TITEL
					.toString()) != "Studie anlegen"
					&& request
					.getAttribute(DispatcherServlet.requestParameter.TITEL
					.toString()) != "Studie ausw&auml;hlen" && aRolleHeader != Rolle.Rollen.ADMIN) {
		%> <span id="studie_highlight">Aktuelle Studie <%=aStudieName%></span>
		&gt; <%=aLocation%> <%
 } else {
 %> <span id="studie_highlight">RANDI2</span> &gt; <%=aLocation%> <%
 }
 %>
		</td>
		<td align="right">
		<%
		if (aPersonHeader.getGeschlecht() == 'm') {
		%>Herr<%
		} else {
		%>Frau<%
		}
		%>&nbsp;<%=aPersonHeader.getVorname()%>&nbsp;<%=aPersonHeader.getNachname()%>&nbsp;<%
		if (aRolleHeader != Rolle.Rollen.STUDIENARZT) {
		%>(<span id="rolle_highlight"><%=aRolleHeader%></span>)<%
		}
		%> ::

		<span id="logout_link" style="cursor:pointer"
			onClick="document.forms['logout_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.AKTION_LOGOUT.name() %>';document.forms['logout_form'].submit();">
		Logout</span> &nbsp;&nbsp;&nbsp;</td>
		<td align="right" width="48">
		<%
					if ((aStudie_header != null
					& (seineRolle == Rolle.getStudienarzt() | seineRolle == Rolle
					.getStudienleiter()) | seineRolle == Rolle
					.getStatistiker())
					|| seineRolle == Rolle.getAdmin()
					|| seineRolle == Rolle.getSysop()) {
		%>
		<img src="images/message.gif" border="0" alt="Nachricht senden"
			title="Nachricht senden" width="22" height="22"
			style="cursor:pointer"
			onClick="document.forms['nachrichtendienst_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_HEADER_NACHRICHTENDIENST.name() %>';document.forms['nachrichtendienst_form'].submit();">&nbsp;<%
		}
		%><img src="images/help.gif" border="0" alt="Hilfe" title="Hilfe"
			width="22" height="22" style="cursor:pointer" id="show-dialog-btn"></td>
	</tr>
</table>
<form action="DispatcherServlet" method="POST"
			name="nachrichtendienst_form" id="nachrichtendienst_form"><input
			type="hidden" name="<%=Parameter.anfrage_id %>" value=""></form>
		<form action="DispatcherServlet" method="POST" name="logout_form"
			id="logout_form"><input type="hidden"
			name="<%=Parameter.anfrage_id %>" value=""></form>
</div>
<div id="randi_help" style="visibility:hidden;">
    <div class="x-dlg-hd">RANDI2 Hilfe</div>
    <div class="x-dlg-bd">
	    <div id="center" class="x-layout-inactive-content" style="padding:10px; font: 10px arial, sans-serif;">
			<%= Hilfe.getInstance().getHilfe((String) request
			.getAttribute(DispatcherServlet.requestParameter.TITEL
					.toString())) %>
	    </div>
    </div>
</div>