package de.randi2.jsf.pages;

//import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

//import de.randi2.jsf.Randi2;

public class Randi2Page {

//	private Randi2 currentRandi2 = ((Randi2) FacesContext.getCurrentInstance()
//			.getExternalContext().getSessionMap().get("randi2"));
	
	private String activePanel = "welcomePanel";

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
		activePanel = "centerEditPanel";
	}

}
