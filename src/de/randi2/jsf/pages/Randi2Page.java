package de.randi2.jsf.pages;

import de.randi2.jsf.handlers.CenterHandler;
import de.randi2.jsf.handlers.LoginHandler;
import de.randi2.model.Center;
import de.randi2.model.Login;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 * <p>
 * This class contains the logic of the randi2.jspx. The main view of the application.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
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
