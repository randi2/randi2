<%@ page language="java" contentType="text/html; charset=utf-8;"
	pageEncoding="utf-8"%>

<%@ page import="de.randi2.controller.DispatcherServlet"%>
<!DOCTYPE PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="de.randi2.utility.Config"%>
<%@page import="de.randi2.utility.*"%>
<%
	request.setAttribute(DispatcherServlet.requestParameter.TITEL
			.toString(), JspTitel.INDEX.toString());
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RANDI2 :: <%=request
									.getAttribute(DispatcherServlet.requestParameter.TITEL
											.toString())%></title>
<%@include file="include/inc_extjs.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/style_login.css" />
<script>
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var form_login = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 100,
		buttonAlign: 'left'
    });

    var login_name = new Ext.form.TextField({
        fieldLabel: 'Benutzername',
        name: '<%=Parameter.benutzerkonto.LOGINNAME.name() %>',
        allowBlank:false,
        blankText:'Bitte Namen eingeben!',
        width:150
    });
    
     var login_passwort = new Ext.form.TextField({
        fieldLabel: 'Kennwort',
        name: '<%=Parameter.benutzerkonto.PASSWORT.name() %>',
        allowBlank:false,
        blankText:'Bitte Passwort eingeben!',
        inputType:'password',
        width:150
    });
	
	form_login.addButton('Login', function(){
		if (this.isValid()) {
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
		}else{
			Ext.MessageBox.alert('Fehler', 'Die Eingaben waren fehlerhaft!');
		}
	}, form_login);
	
	form_login.add(login_name);
	form_login.add(login_passwort);
			
	form_login.render('form_login');
	
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_login.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.JSP_INDEX_LOGIN.name() %>'});	

    var form_benutzer_registrieren = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 0,
		buttonAlign: 'center'
    });
    
    var form_passwort_vergessen = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 0,
		buttonAlign: 'center'
    });
    
    var form_test_zugang = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 0,
		buttonAlign: 'center'
    });
    
	form_benutzer_registrieren.addButton('Benutzer registrieren', function(){
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
	}, form_benutzer_registrieren);    

	form_benutzer_registrieren.render('form_benutzer_registrieren');
	
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_benutzer_registrieren.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.JSP_INDEX_BENUTZER_REGISTRIEREN_EINS.name() %>'});	

	form_passwort_vergessen.addButton('Passwort vergessen?', function(){
		
            var frm = document.getElementById(this.id);
            frm.method = 'POST';
            frm.action = 'DispatcherServlet';
			frm.submit();
			
	}, form_passwort_vergessen);    

	form_passwort_vergessen.render('form_passwort_vergessen');
	
	<!--  Die ANFRAGE_ID fuer SUBMIT wird hier gesetzt. dhaehn	-->
	form_passwort_vergessen.el.createChild({tag: 'input', name: '<%=Parameter.anfrage_id %>', type:'hidden', value: '<%=DispatcherServlet.anfrage_id.JSP_PASSWORT_VERGESSEN.name() %>'});	

	form_test_zugang.addButton('Demozugang', Info, form_test_zugang);    

	form_test_zugang.render('form_test_zugang');

});
</script>
<script>
// create the RANDI2 Demozugang Dialog!
var Info = function(){

    var dialog, showBtn;

    // return a public interface
    return {
        init : function(){
             showBtn = Ext.get('form_test_zugang');
             // attach to click event
             showBtn.on('click', this.showDialog, this);
             
        },
        
        showDialog : function(){
            if(!dialog){ // lazy initialize the dialog and only create it once
                dialog = new Ext.LayoutDialog("info", { 
                        modal:true,
                        width:580,
                        height:480,
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
	            layout.add('center', new Ext.ContentPanel('center', {title: 'Zugangsdaten'}));
				layout.endUpdate();
            }
            dialog.show(showBtn.dom);
        }
    };
}();

// using onDocumentReady instead of window.onload initializes the application
// when the DOM is ready, without waiting for images and other resources to load
Ext.EventManager.onDocumentReady(Info.init, Info, true);
</script>
</head>

<body>
<!--  RELEASE 2 -->
<div id="header"><img
	src="<%=Config.getProperty(Config.Felder.RELEASE_BILD_LOGO) %>"
	width="337" height="63" title="" alt=""></div>

<div id="breadcrumb">
<table class="breadcrumb_tbl" width="100%" summary="Impressum">
	<tr>
		<td align="right" valign="middle" height="25">
		<form action="DispatcherServlet" method="POST" name="impressum_form"
			id="impressum_form"><input type="hidden"
			name="<%=Parameter.anfrage_id %>" value=""></form>
		<span id="logout_link" style="cursor: pointer"
			onClick="document.forms['impressum_form'].<%=Parameter.anfrage_id %>.value = '<%=DispatcherServlet.anfrage_id.JSP_HEADER_IMPRESSUM.name() %>';document.forms['impressum_form'].submit();">
		Impressum</span></td>
	</tr>
</table>
<%@include file="include/inc_nachricht.jsp"%></div>

<div id="inhalt_login"><br>
<p><img id="bild_login"
	src="<%=Config.getProperty(Config.Felder.RELEASE_BILD_STARTSEITE) %>"
	width="537" height="291" alt="Heidelberg"></p>
</div>

<div id="login_benutzer"><br>
<p id="pageheader">Herzlich Willkommen</p>
<br>
<br>
<div id="form_login"></div>
<br>
<br>
<br>



<table cellPadding="0" cellSpacing="0" border="0">
	<tr>
		<td align="right">
		<div id="form_benutzer_registrieren"></div>
		</td>
		<td>&nbsp;&nbsp;&nbsp;::&nbsp;&nbsp;&nbsp;</td>
		<td align="center">
		<div id="form_passwort_vergessen"></div>
		</td>
		<td>&nbsp;&nbsp;&nbsp;::&nbsp;&nbsp;&nbsp;</td>
		<td align="left">
		<div id="form_test_zugang"></div>
		</td>
	</tr>
</table>
</div>
<div id="info" style="visibility: hidden;">
<div class="x-dlg-hd">RANDI2 Demozugang</div>
<div class="x-dlg-bd">
<div id="center" class="x-layout-inactive-content"
	style="padding: 10px; font: 10px arial, sans-serif;">
<div class=Section1>

<p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm 177.15pt 212.6pt 248.05pt 283.45pt 318.9pt 354.35pt 389.75pt 425.2pt 460.65pt'>Um
das Kennenlernen des Systems Ihnen zu vereinfachen, stehen Ihnen folgende
Demo-Benutzer zur Verfügung. Somit haben Sie die Möglichkeit, alle Features vom
RANDI2 zu ausprobieren und zu testen. Falls Sie uns ihre Erfahrungen mitteilen
möchten, worüber wir uns wirklich freuen würden, schicken Sie bitte eine E-Mail
an eine den beiden Adressen:</p>

<p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm 177.15pt 212.6pt 248.05pt 283.45pt 318.9pt 354.35pt 389.75pt 425.2pt 460.65pt'><o:p>&nbsp;</o:p></p>

<p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm 177.15pt 212.6pt 248.05pt 283.45pt 318.9pt 354.35pt 389.75pt 425.2pt 460.65pt'><span
style='mso-tab-count:1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
style='mso-tab-count:1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><a
href="mailto:l.plotnicki@dkfz.de"><span style='color:#000099'>l.plotnicki@dkfz.de</span></a><span
style='mso-tab-count:4'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><a
href="mailto:d.schrimpf@dkfz.de"><span style='color:#000099'>d.schrimpf@dkfz.de</span></a></p>

<p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm 177.15pt 212.6pt 248.05pt 283.45pt 318.9pt 354.35pt 389.75pt 425.2pt 460.65pt'><o:p>&nbsp;</o:p></p>

<table class=MsoNormalTable border=0 cellspacing=0 cellpadding=0
 style='margin-left:5.4pt;background:white;border-collapse:collapse;mso-table-layout-alt:
 fixed;mso-padding-alt:0cm 5.4pt 0cm 5.4pt'>
 <thead>
  <tr style='page-break-inside:avoid;height:14.0pt'>
   <td width=160 valign=top style='width:160.2pt;border:solid black 1.0pt;
   background:#B0B3B2;padding:5.0pt 5.0pt 5.0pt 5.0pt;height:14.0pt'>
   <p class=Zwischenberschrift align=center style='text-align:center;
   tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'>Benutzerrolle</p>
   </td>
   <td width=160 valign=top style='width:160.2pt;border:solid black 1.0pt;
   border-left:none;mso-border-left-alt:solid black 1.0pt;background:#B0B3B2;
   padding:5.0pt 5.0pt 5.0pt 5.0pt;height:14.0pt'>
   <p class=Zwischenberschrift align=center style='text-align:center;
   tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'>Login</p>
   </td>
   <td width=160 valign=top style='width:160.2pt;border:solid black 1.0pt;
   border-left:none;mso-border-left-alt:solid black 1.0pt;background:#B0B3B2;
   padding:5.0pt 5.0pt 5.0pt 5.0pt;height:14.0pt'>
   <p class=Zwischenberschrift align=center style='text-align:center;
   tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'>Passwort</p>
   </td>
  </tr>
 </thead>
 <tr style='page-break-inside:avoid;height:14.0pt'>
  <td width=160 valign=top style='width:160.2pt;border:solid black 1.0pt;
  border-top:none;mso-border-top-alt:solid black 1.0pt;padding:5.0pt 5.0pt 5.0pt 5.0pt;
  height:14.0pt'>
  <p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'>Studienarzt</p>
  </td>
  <td width=160 valign=top style='width:160.2pt;border-top:none;border-left:
  none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;
  mso-border-top-alt:solid black 1.0pt;mso-border-left-alt:solid black 1.0pt;
  padding:5.0pt 5.0pt 5.0pt 5.0pt;height:14.0pt'>
  <p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'><a
  href="mailto:randi2@dkfz.de"><span style='color:#000099'>randi2@dkfz.de</span></a></p>
  </td>
  <td width=160 valign=top style='width:160.2pt;border-top:none;border-left:
  none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;
  mso-border-top-alt:solid black 1.0pt;mso-border-left-alt:solid black 1.0pt;
  padding:5.0pt 5.0pt 5.0pt 5.0pt;height:14.0pt'>
  <p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'>1$arzt</p>
  </td>
 </tr>
 <tr style='page-break-inside:avoid;height:14.0pt'>
  <td width=160 valign=top style='width:160.2pt;border:solid black 1.0pt;
  border-top:none;mso-border-top-alt:solid black 1.0pt;padding:5.0pt 5.0pt 5.0pt 5.0pt;
  height:14.0pt'>
  <p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'>Studienleiter</p>
  </td>
  <td width=160 valign=top style='width:160.2pt;border-top:none;border-left:
  none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;
  mso-border-top-alt:solid black 1.0pt;mso-border-left-alt:solid black 1.0pt;
  padding:5.0pt 5.0pt 5.0pt 5.0pt;height:14.0pt'>
  <p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'>Demoleiter</p>
  </td>
  <td width=160 valign=top style='width:160.2pt;border-top:none;border-left:
  none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;
  mso-border-top-alt:solid black 1.0pt;mso-border-left-alt:solid black 1.0pt;
  padding:5.0pt 5.0pt 5.0pt 5.0pt;height:14.0pt'>
  <p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'>1$leiter</p>
  </td>
 </tr>
 <tr style='page-break-inside:avoid;height:14.0pt'>
  <td width=160 valign=top style='width:160.2pt;border:solid black 1.0pt;
  border-top:none;mso-border-top-alt:solid black 1.0pt;padding:5.0pt 5.0pt 5.0pt 5.0pt;
  height:14.0pt'>
  <p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'>Statistiker</p>
  </td>
  <td width=160 valign=top style='width:160.2pt;border-top:none;border-left:
  none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;
  mso-border-top-alt:solid black 1.0pt;mso-border-left-alt:solid black 1.0pt;
  padding:5.0pt 5.0pt 5.0pt 5.0pt;height:14.0pt'>
  <p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'>Demostat</p>
  </td>
  <td width=160 valign=top style='width:160.2pt;border-top:none;border-left:
  none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;
  mso-border-top-alt:solid black 1.0pt;mso-border-left-alt:solid black 1.0pt;
  padding:5.0pt 5.0pt 5.0pt 5.0pt;height:14.0pt'>
  <p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'>1$statistiker</p>
  </td>
 </tr>
 <tr style='page-break-inside:avoid;height:14.0pt'>
  <td width=160 valign=top style='width:160.2pt;border:solid black 1.0pt;
  border-top:none;mso-border-top-alt:solid black 1.0pt;padding:5.0pt 5.0pt 5.0pt 5.0pt;
  height:14.0pt'>
  <p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'>Admin</p>
  </td>
  <td width=160 valign=top style='width:160.2pt;border-top:none;border-left:
  none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;
  mso-border-top-alt:solid black 1.0pt;mso-border-left-alt:solid black 1.0pt;
  padding:5.0pt 5.0pt 5.0pt 5.0pt;height:14.0pt'>
  <p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'>Demoadmin</p>
  </td>
  <td width=160 valign=top style='width:160.2pt;border-top:none;border-left:
  none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;
  mso-border-top-alt:solid black 1.0pt;mso-border-left-alt:solid black 1.0pt;
  padding:5.0pt 5.0pt 5.0pt 5.0pt;height:14.0pt'>
  <p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'>1$admin</p>
  </td>
 </tr>
 <tr style='mso-yfti-lastrow:yes;page-break-inside:avoid;height:14.0pt'>
  <td width=160 valign=top style='width:160.2pt;border:solid black 1.0pt;
  border-top:none;mso-border-top-alt:solid black 1.0pt;padding:5.0pt 5.0pt 5.0pt 5.0pt;
  height:14.0pt'>
  <p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'>SysOp</p>
  </td>
  <td width=160 valign=top style='width:160.2pt;border-top:none;border-left:
  none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;
  mso-border-top-alt:solid black 1.0pt;mso-border-left-alt:solid black 1.0pt;
  padding:5.0pt 5.0pt 5.0pt 5.0pt;height:14.0pt'>
  <p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'>Demosysop</p>
  </td>
  <td width=160 valign=top style='width:160.2pt;border-top:none;border-left:
  none;border-bottom:solid black 1.0pt;border-right:solid black 1.0pt;
  mso-border-top-alt:solid black 1.0pt;mso-border-left-alt:solid black 1.0pt;
  padding:5.0pt 5.0pt 5.0pt 5.0pt;height:14.0pt'>
  <p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'>1$sysop</p>
  </td>
 </tr>
</table>

<p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm 177.15pt 212.6pt 248.05pt 283.45pt 318.9pt 354.35pt 389.75pt 425.2pt 460.65pt'><o:p>&nbsp;</o:p></p>

<table class=MsoNormalTable border=0 cellspacing=0 cellpadding=0
 style='margin-left:5.4pt;background:white;border-collapse:collapse;mso-table-layout-alt:
 fixed;mso-padding-alt:0cm 5.4pt 0cm 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes;mso-yfti-lastrow:yes;
  page-break-inside:avoid;height:15.4pt'>
  <td width=162 valign=top style='width:161.85pt;border:solid black 1.0pt;
  padding:5.0pt 5.0pt 5.0pt 5.0pt;height:15.4pt'>
  <p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'><b
  style='mso-bidi-font-weight:normal'>Passwort für das<o:p></o:p></b></p>
  <p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'><b
  style='mso-bidi-font-weight:normal'>Randi2 Testzentrum</b><o:p></o:p></p>
  </td>
  <td width=320 valign=top style='width:319.7pt;border:solid black 1.0pt;
  border-left:none;mso-border-left-alt:solid black 1.0pt;padding:5.0pt 5.0pt 5.0pt 5.0pt;
  height:15.4pt'>
  <p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm'>Randi2Testcenter!<o:p></o:p></p>
  </td>
 </tr>
</table>

<p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm 177.15pt 212.6pt 248.05pt 283.45pt 318.9pt 354.35pt 389.75pt 425.2pt 460.65pt'><o:p>&nbsp;</o:p></p>

<p class=Text style='tab-stops:35.45pt 70.85pt 106.3pt 5.0cm 177.15pt 212.6pt 248.05pt 283.45pt 318.9pt 354.35pt 389.75pt 425.2pt 460.65pt'><span
style='font-size:10.0pt;font-family:"Times New Roman";mso-fareast-font-family:
"Times New Roman";color:windowtext'><o:p>&nbsp;</o:p></span></p>

</div>

</div>
</div>
</div>
</body>

</html>
