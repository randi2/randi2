package de.randi2.jsf.handlers;

import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Login;
import de.randi2.model.Person;
import de.randi2.model.TrialSite;

public abstract class AbstractHandler<O extends AbstractDomainObject> {

	private Class<O> object = null;
	protected O showedObject = null;
	protected boolean creatingMode = false;
	protected boolean editable = false;

	public AbstractHandler(Class<O> _object) {
		object = _object;
	}

	public O getShowedObject() {
		if (showedObject == null)
			showedObject = createPlainObject();
		return showedObject;
	}

	public void setShowedObject(O _showedObject) {
		if (_showedObject == null) { // A new object is to be created
			creatingMode = true;
			showedObject = createPlainObject();
			refresh();
		}else{  // A selected object will be shown
			creatingMode = false;
			showedObject = _showedObject;
			refresh();
		}
		
	}

	@SuppressWarnings("unchecked")
	private O createPlainObject() {
		if (object.getCanonicalName().equals(Login.class.getCanonicalName())) {
			Login plainO = new Login();
			plainO.setPerson(new Person());
			return (O) plainO;
		}else if(object.getCanonicalName().equals(Login.class.getCanonicalName())){
			TrialSite plainO = new TrialSite();
			plainO.setContactPerson(new Person());
			return (O) plainO;
		}
		return null;
	}
	
	public abstract String refreshShowedObject();

	/**
	 * This method should be use to refresh the current view.
	 */
	protected void refresh() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		ViewHandler viewHandler = application.getViewHandler();
		UIViewRoot viewRoot = viewHandler.createView(context, context
				.getViewRoot().getViewId());
		context.setViewRoot(viewRoot);
		context.renderResponse(); // Optional
	}

	public boolean isCreatingMode() {
		return creatingMode;
	}

}
