package de.randi2.jsf.wrappers;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.model.SelectItem;

import com.icesoft.faces.component.ext.HtmlInputText;
import com.icesoft.faces.component.ext.HtmlInputTextarea;
import com.icesoft.faces.component.ext.HtmlOutputLabel;
import com.icesoft.faces.component.ext.HtmlPanelGrid;
import com.icesoft.faces.component.panelcollapsible.PanelCollapsible;
import com.icesoft.faces.component.selectinputtext.SelectInputText;
import com.icesoft.faces.component.selectinputtext.SelectInputTextTag;

import de.randi2.jsf.handlers.LoginHandler;
import de.randi2.jsf.utility.AutoCompleteObject;
import de.randi2.model.SubjectProperty;
import de.randi2.model.criteria.AbstractCriterion;

public class SubjectPropertyWrapper {

	private final Application app = FacesContext.getCurrentInstance()
			.getApplication();
	private final ELContext elContext = FacesContext.getCurrentInstance()
			.getELContext();
	private final ExpressionFactory expressionFactory = app
			.getExpressionFactory();
	private ResourceBundle criteria = null;

	private AbstractCriterion selectedCriterion = null;

	private SubjectProperty property = null;
	private final AutoCompleteObject<AbstractCriterion> criteriaAC;
	private final UIComponent propertyPanel;
	private HtmlPanelGrid criteriaPanel;

	@SuppressWarnings("unchecked")
	public SubjectPropertyWrapper() {
		ValueExpression ve = expressionFactory.createValueExpression(elContext,
				"#{loginHandler}", LoginHandler.class);
		LoginHandler currentLoginHandler = (LoginHandler) ve
				.getValue(elContext);
		criteria = ResourceBundle.getBundle("de.randi2.jsf.i18n.criteria",
				currentLoginHandler.getChosenLocale());
		criteriaAC = new AutoCompleteObject<AbstractCriterion>(
				(ArrayList<AbstractCriterion>) expressionFactory
						.createValueExpression(
								FacesContext.getCurrentInstance()
										.getELContext(),
								"#{trialHandler.criteriaList}", ArrayList.class)
						.getValue(elContext));
		propertyPanel = app.createComponent(PanelCollapsible.COMPONENET_TYPE);
		propertyPanel.setValueExpression("expanded", expressionFactory
				.createValueExpression(new Boolean(true), Boolean.class));
		propertyPanel.setValueExpression("toggleOnClick", expressionFactory
				.createValueExpression(new Boolean(false), Boolean.class));
		propertyPanel.setValueExpression("toggleOnClick", expressionFactory
				.createValueExpression(new Boolean(false), Boolean.class));

		HtmlPanelGrid criteriaChoicePanel = (HtmlPanelGrid) app
				.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
		criteriaChoicePanel.setColumns(2);

		HtmlOutputLabel crieteriaLabel = (HtmlOutputLabel) app
				.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
		crieteriaLabel.setValue(criteria.getString("criteriaType"));

		SelectInputText criteriaInputText = (SelectInputText) app
				.createComponent(SelectInputText.COMPONENT_TYPE);
		SelectInputTextTag sitTag = new SelectInputTextTag();
		criteriaInputText.setRendererType(sitTag.getRendererType());
		criteriaInputText.setPartialSubmit(true);
		criteriaInputText.setImmediate(true);
		criteriaInputText.setSize(25);
		UISelectItems selectItems = (UISelectItems) app
				.createComponent(UISelectItems.COMPONENT_TYPE);
		for (SelectItem si : criteriaAC.getObjectList()) {
			si.setLabel(criteria.getString(si.getLabel()));
		}
		selectItems.setValue(criteriaAC.getObjectList());
		criteriaInputText.getChildren().add(selectItems);
		criteriaInputText.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void processValueChange(ValueChangeEvent arg0)
					throws AbortProcessingException {
				criteriaAC.updateObjectList(arg0);
				if (criteriaAC.isObjectSelected())
					try {
						selectedCriterion = criteriaAC.getSelectedObject();
						createCriterionPanel();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});

		criteriaChoicePanel.getChildren().add(crieteriaLabel);
		criteriaChoicePanel.getChildren().add(criteriaInputText);
		propertyPanel.getChildren().add(criteriaChoicePanel);

	}

	public SubjectProperty getProperty() {
		return property;
	}

	@SuppressWarnings("unchecked")
	public void createCriterionPanel() throws IllegalArgumentException,
			IllegalAccessException {
		assert (selectedCriterion != null);
		if (criteriaPanel == null) {
			criteriaPanel = (HtmlPanelGrid) app
					.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
			criteriaPanel.setColumns(1);
		} else
			criteriaPanel.getChildren().clear();

		HtmlOutputLabel acNameL = (HtmlOutputLabel) app
				.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
		acNameL.setValue(criteria
				.getString("de.randi2.model.criteria.AbstractCriterion.name"));
		criteriaPanel.getChildren().add(acNameL);
		HtmlInputText acNameIn = (HtmlInputText) app
				.createComponent(HtmlInputText.COMPONENT_TYPE);
		acNameIn.setValueExpression("value", expressionFactory.createValueExpression(selectedCriterion.getName(), String.class));
		acNameIn.setSize(30);
		criteriaPanel.getChildren().add(acNameIn);

		HtmlOutputLabel acDescriptionL = (HtmlOutputLabel) app
				.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
		acDescriptionL
				.setValue(criteria
						.getString("de.randi2.model.criteria.AbstractCriterion.description"));
		criteriaPanel.getChildren().add(acDescriptionL);
		HtmlInputTextarea acDescriptionIn = (HtmlInputTextarea) app
				.createComponent(HtmlInputTextarea.COMPONENT_TYPE);
		acDescriptionIn.setValue(selectedCriterion.getDescription());
		acDescriptionIn.setStyle("width:175px;height:50px;overflow: auto;");
		criteriaPanel.getChildren().add(acDescriptionIn);

		for (Field f : selectedCriterion.getClass().getDeclaredFields()) {
			System.out.println(selectedCriterion.getClass().getCanonicalName());
			System.out.println(f.getGenericType());
			if (!Modifier.isStatic(f.getModifiers())
					&& Modifier.isPublic(f.getModifiers())) // We're only
				// interested
				// in non-static, public fields
				if (f.get(selectedCriterion) instanceof String) { // Strings
					HtmlOutputLabel label = (HtmlOutputLabel) app
							.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
					label.setValue(f.getName());
					criteriaPanel.getChildren().add(label);
					HtmlInputText inputText = (HtmlInputText) app
							.createComponent(HtmlInputText.COMPONENT_TYPE);
					inputText.setValue(f.get(criteriaAC.getSelectedObject()));
					inputText.setSize(30);
					criteriaPanel.getChildren().add(inputText);
				} else if (f.get(selectedCriterion) instanceof Set) { // Sets
					Set<String> temp = (java.util.Set<String>) f
							.get(selectedCriterion);
					if (!temp.isEmpty()) {
						int nr = 1;
						for (String ob : temp) {
							HtmlOutputLabel label = (HtmlOutputLabel) app
									.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
							label.setValue(criteria.getString(selectedCriterion
									.getClass().getCanonicalName()
									+ "." + f.getName())
									+ " " + nr);
							criteriaPanel.getChildren().add(label);
							HtmlInputText inputText = (HtmlInputText) app
									.createComponent(HtmlInputText.COMPONENT_TYPE);
							inputText.setValue(ob);
							inputText.setSize(30);
							criteriaPanel.getChildren().add(inputText);
							nr++;

						}
					} else {
						// FIXME TEMP
						for(int i=0;i<4;i++){
							temp.add(new String("test"+i));
						}
						int nr = 1;
						for (String ob : temp) {
							HtmlOutputLabel label = (HtmlOutputLabel) app
									.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
							label.setValue(criteria.getString(selectedCriterion
									.getClass().getCanonicalName()
									+ "." + f.getName())
									+ " " + nr);
							criteriaPanel.getChildren().add(label);
							HtmlInputText inputText = (HtmlInputText) app
									.createComponent(HtmlInputText.COMPONENT_TYPE);
							inputText.setValue(ob);
							inputText.setSize(30);
							criteriaPanel.getChildren().add(inputText);
							nr++;
						}
					}

				} else if (f.get(selectedCriterion) instanceof String[]) { // String
					// Arrays
					System.out.println(f.getName());
					for (int j = 0; j < Array.getLength(f.get(criteriaAC
							.getSelectedObject())); j++) {
						HtmlOutputLabel label = (HtmlOutputLabel) app
								.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
						label.setValue(criteria.getString(selectedCriterion
								.getClass().getCanonicalName()
								+ "." + f.getName() + "." + j));
						criteriaPanel.getChildren().add(label);
						HtmlInputText inputText = (HtmlInputText) app
								.createComponent(HtmlInputText.COMPONENT_TYPE);
						inputText.setValue(Array.get(f.get(criteriaAC
								.getSelectedObject()), j));
						inputText.setSize(30);
						criteriaPanel.getChildren().add(inputText);
					}
				}

		}
		propertyPanel.getChildren().add(criteriaPanel);

	}

	public UIComponent getPropertyPanel() {
		return propertyPanel;
	}
	
	public AbstractCriterion getSelectedCriterion(){
		return selectedCriterion;
	}

}
