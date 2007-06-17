Ext.onReady(function(){

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

    var form_studie_anlegen = new Ext.form.Form({
        labelAlign: 'left',
        labelWidth: 175,
		buttonAlign: 'right'
    });

    var studie_name = new Ext.form.TextField({
        fieldLabel: 'Name der Studie',
        name: 'name',
        allowBlank:false,
        width:190
    });

    var studie_beschreibung = new Ext.form.TextArea({
        fieldLabel: 'Beschreibung der Studie',
        name: 'title',
        allowBlank:false,
        width:190
    });

    var studie_startdatum = new Ext.form.DateField({
        fieldLabel: 'Startdatum',
        name: 'hire_date',
        width:90,
        allowBlank:false,
		format:'d.m.Y'
    });
    
    var studie_enddatum = new Ext.form.DateField({
        fieldLabel: 'Startdatum',
        name: 'hire_date',
        width:90,
        allowBlank:false,
		format:'d.m.Y'
    });

	var studie_statistiker_boolean = new Ext.form.Checkbox({
        fieldLabel: 'Statistiker Account anlegen',
        name: 'active'
    });

    form_studie_anlegen.fieldset(
        {legend:'Studienangaben'},
		studie_name,
		studie_beschreibung,
		studie_startdatum,
		studie_enddatum
	);
	
	
		
	form_studie_anlegen.addButton('Best&auml;tigen', function(){
		if (form_employee.isValid()) {
			Ext.MessageBox.alert('Success', 'You have filled out the form correctly.');	
		}else{
			Ext.MessageBox.alert('Errors', 'Please fix the errors noted.');
		}
	}, form_studie_anlegen);
	
	form_studie_anlegen.addButton('Abbrechen', function(){
		top.location.href='studie_auswaehlen.jsp';
	}, form_studie_anlegen);
	
	form_studie_anlegen.render('studie_anlegen');

});