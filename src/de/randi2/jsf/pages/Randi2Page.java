package de.randi2.jsf.pages;

import de.randi2.jsf.handlers.CenterHandler;
import de.randi2.jsf.handlers.LoginHandler;
import de.randi2.model.Center;
import de.randi2.model.Login;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public class Randi2Page {

    private CenterHandler centerHandler;
    
    
    /**
     * The active content Panel.
     */
    private String activePanel = "welcomePanel";
    
    /**
     * The current logged in user.
     */
    private Login currentUser = null;

    public Randi2Page() {
    	centerHandler = (CenterHandler)FacesContext.getCurrentInstance().getApplication().getVariableResolver().resolveVariable(FacesContext.getCurrentInstance(), "centerHandler");
    	currentUser = ((LoginHandler) FacesContext.getCurrentInstance()
				.getApplication().getVariableResolver().resolveVariable(
						FacesContext.getCurrentInstance(), "loginHandler")).getLogin();
    }

    public String getActivePanel() {
        return activePanel;
    }

    public void setActivePanel(String activePanel) {
        this.activePanel = activePanel;
    }

    public void viewCenters(ActionEvent event) {
        activePanel = "centersViewPanel";
    }

    public void myCenter(ActionEvent event) {
        centerHandler.setShowedCenter(currentUser.getPerson().getCenter());
        activePanel = "centerEditPanel";
    }
    
    public void showCenter(ActionEvent event){
        Center tCenter =  (Center) (((UIComponent)event.getComponent().getChildren().get(0)).getValueBinding("value").getValue(FacesContext.getCurrentInstance()));
        centerHandler.setShowedCenter(tCenter);
        activePanel = "centerEditPanel";
    }
    
    public void createCenter(ActionEvent event){
    	centerHandler.setShowedCenter(null);
    	activePanel = "centerEditPanel";
    }

 
}
