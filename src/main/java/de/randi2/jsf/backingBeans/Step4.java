package de.randi2.jsf.backingBeans;

import java.util.ArrayList;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.icesoft.faces.component.ext.HtmlPanelGrid;

import de.randi2.jsf.wrappers.SubjectPropertyWrapper;

/**
 * <p>This class wrapped the subject property configuration's functionality.</p>
 * @author luki
 *
 */
public class Step4 {

	private final Application app = FacesContext.getCurrentInstance()
			.getApplication();
	
	private HtmlPanelGrid propertiesPanel = null;

	private ArrayList<SubjectPropertyWrapper> properties = null;

	public HtmlPanelGrid getPropertiesPanel() {
		if (propertiesPanel == null) { 
			
			propertiesPanel = (HtmlPanelGrid) app
					.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
			propertiesPanel.setColumns(2);
		}
		return propertiesPanel;
	}

	public void setPropertiesPanel(HtmlPanelGrid _panel) {
		propertiesPanel = _panel;
	}

	public void addProperty(ActionEvent event) {
		assert (propertiesPanel != null);
		SubjectPropertyWrapper pWrapper = new SubjectPropertyWrapper(getProperties().size());
		getProperties().add(pWrapper);
		propertiesPanel.getChildren().add((UIComponent) pWrapper.getPropertyPanel());
	}

	public void removeProperty(ActionEvent event) {
		assert (propertiesPanel != null);
		propertiesPanel.getChildren().remove(
				getProperties().get(getProperties().size() - 1).getPropertyPanel());
		getProperties().remove(this.getProperties().size() - 1);
	}

	public ArrayList<SubjectPropertyWrapper> getProperties() {
		if (properties == null)
			properties = new ArrayList<SubjectPropertyWrapper>();
		return properties;
	}

	public boolean isPropertiesEmpty() {
		return getProperties().size()==0;
	}

}
