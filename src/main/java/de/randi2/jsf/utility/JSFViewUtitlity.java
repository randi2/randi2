package de.randi2.jsf.utility;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class JSFViewUtitlity {

	
	public static void refreshJSFPage(){
		FacesContext context = FacesContext.getCurrentInstance();
		String viewId = context.getViewRoot().getViewId();
		ViewHandler viewHandler = context.getApplication().getViewHandler();
		UIViewRoot root = viewHandler.createView(context, viewId);
		root.setViewId(viewId);
		context.setViewRoot(root);
	}
}
