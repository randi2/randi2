package de.randi2.jsf.pages;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import com.icesoft.faces.component.ext.HtmlCommandButton;
import com.icesoft.faces.component.ext.HtmlOutputLabel;
import com.icesoft.faces.component.ext.HtmlPanelGrid;
import com.icesoft.faces.component.ext.HtmlPanelGroup;

import de.randi2.jsf.handlers.LoginHandler;
import de.randi2.jsf.wrappers.SubjectPropertyWrapper;

public class Step4 {

	private final Application app = FacesContext.getCurrentInstance()
			.getApplication();
	private final ELContext elContext = FacesContext.getCurrentInstance()
			.getELContext();
	private final ExpressionFactory expressionFactory = app
			.getExpressionFactory();
	private ResourceBundle labels = null;
	private HtmlPanelGrid propertiesPanel = null;

	private ArrayList<SubjectPropertyWrapper> properties = null;

	public HtmlPanelGrid getPropertiesPanel() {
		if (propertiesPanel == null) {
			propertiesPanel = (HtmlPanelGrid) app
					.createComponent(HtmlPanelGrid.COMPONENT_TYPE);

			propertiesPanel.setId("_id_panelProperties");
			propertiesPanel.setColumns(2);
			propertiesPanel.setBorder(1);

			// Panel Label
			HtmlOutputLabel panelLabel = (HtmlOutputLabel) app
					.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
			ValueExpression ve = expressionFactory.createValueExpression(
					elContext, "#{loginHandler}", LoginHandler.class);
			LoginHandler currentLoginHandler = (LoginHandler) ve
					.getValue(elContext);
			labels = ResourceBundle.getBundle("de.randi2.jsf.i18n.labels",
					currentLoginHandler.getChosenLocale());
			panelLabel.setValue(labels
					.getString("pages.trialEditPanel.propLabel"));
			// Add & Remove Controls
			HtmlPanelGroup controlsPanel = (HtmlPanelGroup) app
					.createComponent(HtmlPanelGroup.COMPONENT_TYPE);
			HtmlCommandButton addButton = (HtmlCommandButton) app
					.createComponent(HtmlCommandButton.COMPONENT_TYPE);
			HtmlCommandButton removeButton = (HtmlCommandButton) app
					.createComponent(HtmlCommandButton.COMPONENT_TYPE);

			// <ice:panelGrid columns="3">
			// <ice:outputLabel
			// value="#{labels['pages.trialEditPanel.propLabel']}" />
			// <ice:commandButton image="/img/icons/add16.png"
			// actionListener="#{trialHandler.addProperty}"
			// style="margin-right:5px;" />
			// <ice:commandButton image="/img/icons/remove16.png"
			// disabled="#{trialHandler.subjectPropertiesCount==0}"
			// actionListener="#{trialHandler.removeProperty}" />
			// </ice:panelGrid>
			addButton.setImage("/img/icons/add16.png");
			MethodExpression add = expressionFactory.createMethodExpression(
					elContext, "#{step4.addProperty}", Void.TYPE,
					new Class<?>[0]);
			addButton.setActionExpression(add);
			addButton.setStyle("margin-right:5px;");

			removeButton.setImage("/img/icons/remove16.png");
			MethodExpression remove = expressionFactory.createMethodExpression(
					elContext, "#{step4.removeProperty}", Void.TYPE,
					new Class<?>[0]);
			removeButton.setActionExpression(remove);
			removeButton.setDisabled(getProperties().size() != 0);
			removeButton.setStyle("margin-right:5px;");

			controlsPanel.getChildren().add(addButton);
			controlsPanel.getChildren().add(removeButton);
			propertiesPanel.getChildren().add(panelLabel);
			propertiesPanel.getChildren().add(controlsPanel);

			// headerInfo.set

		}
		return propertiesPanel;
	}

	public void setPropertiesPanel(HtmlPanelGrid _panel) {
		propertiesPanel = _panel;
	}

	public void addProperty() {
		assert (propertiesPanel != null);
		SubjectPropertyWrapper pWrapper = new SubjectPropertyWrapper();
		getProperties().add(pWrapper);
		propertiesPanel.getChildren().add(pWrapper.getPanelGrid());
	}

	public void removeProperty() {
		assert (propertiesPanel != null);
		propertiesPanel.getChildren().remove(
				getProperties().get(getProperties().size() - 1).getPanelGrid());
		getProperties().remove(this.getProperties().size() - 1);
	}

	public ArrayList<SubjectPropertyWrapper> getProperties() {
		if (properties == null)
			properties = new ArrayList<SubjectPropertyWrapper>();
		return properties;
	}

}
