<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
       "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<%@include file="include/inc_extjs.jsp"%>
<link rel="stylesheet" type="text/css" href="css/style.css">
<title>Randi2 :: Impressum</title>
<script>
Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var form_imprint = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 0,
		buttonAlign: 'left',
    });
    
    var imprint_text = new Ext.form.TextArea({
        name: '',
        value:'<%=Config.getProperty(Config.Felder.RELEASE_IMPRESSUM) %>',
        width:500,
        height:500,
        readOnly:true
    });    
	
	<!--  Die ANFRAGE_ID fuer ABBRECHEN wird hier gesetzt. dhaehn	-->
	form_imprint.addButton('Abbrechen', function(){
		top.location.href='DispatcherServlet';
	}, form_imprint);    

    form_imprint.fieldset({legend:'Impressum und rechtliche Hinweise',hideLabels:true},
    imprint_text);
    
    form_imprint.render('form_imprint');

    });
</script>
</head>
<body>
<%@include file="include/inc_header_clean.jsp"%>

<div id="content">

<h1>Impressum</h1>
<div id="form_imprint"></div>

<br>
<%@include file="include/inc_footer_clean.jsp"%>
</div>



</body>
</html>
