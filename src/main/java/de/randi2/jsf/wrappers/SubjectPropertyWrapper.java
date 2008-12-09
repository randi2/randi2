package de.randi2.jsf.wrappers;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import org.apache.jasper.tagplugins.jstl.core.Set;

import com.icesoft.faces.component.ext.HtmlInputText;
import com.icesoft.faces.component.ext.HtmlOutputLabel;
import com.icesoft.faces.component.ext.HtmlPanelGrid;
import com.icesoft.faces.component.selectinputtext.SelectInputText;

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
	private ResourceBundle labels = null;

	private SubjectProperty property = null;
	private final AutoCompleteObject<AbstractCriterion> criteriaAC;
	private final HtmlPanelGrid panelGrid;

	@SuppressWarnings("unchecked")
	public SubjectPropertyWrapper() {
		criteriaAC = new AutoCompleteObject<AbstractCriterion>(
				(ArrayList<AbstractCriterion>) expressionFactory
						.createValueExpression(
								FacesContext.getCurrentInstance()
										.getELContext(),
								"#{trialHandler.criteriaList}", ArrayList.class)
						.getValue(elContext));
		panelGrid = (HtmlPanelGrid) app
				.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
		SelectInputText criteriaInputText = (SelectInputText) app
				.createComponent(SelectInputText.COMPONENT_TYPE);
		criteriaInputText.setImmediate(true);
		criteriaInputText.setListValue(criteriaAC.getObjectList());
		criteriaInputText.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void processValueChange(ValueChangeEvent arg0)
					throws AbortProcessingException {
				criteriaAC.updateObjectList(arg0);
				System.out.println("kdsajdlsajalkjdlka");
				if (criteriaAC.isObjectSelected())
					try {
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
		HtmlOutputLabel crieteriaLabel = (HtmlOutputLabel) app
				.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
		ValueExpression ve = expressionFactory.createValueExpression(
				elContext, "#{loginHandler}", LoginHandler.class);
		LoginHandler currentLoginHandler = (LoginHandler) ve
				.getValue(elContext);
		labels = ResourceBundle.getBundle("de.randi2.jsf.i18n.labels",
				currentLoginHandler.getChosenLocale());
		crieteriaLabel.setValue("!TYPE!");

		panelGrid.setColumns(2);
		panelGrid.setBorder(1);
		panelGrid.getChildren().add(crieteriaLabel);
		panelGrid.getChildren().add(criteriaInputText);

	}

	public SubjectProperty getProperty() {
		return property;
	}

	public void createCriterionPanel() throws IllegalArgumentException,
			IllegalAccessException {

		HtmlPanelGrid panel = (HtmlPanelGrid) app
				.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
		int componentNr = 0;
		for (Field f : criteriaAC.getSelectedObject().getClass()
				.getDeclaredFields()) {
			System.out.println(f.getType().getCanonicalName());
			if (!Modifier.isStatic(f.getModifiers())) // We're only interested
				// in not-static fields
				if (f.getType().equals(String.class)) {
					HtmlOutputLabel label = (HtmlOutputLabel) app
							.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
					label.setValue(f.getName());
					label.setId("label" + componentNr);
					panel.getChildren().add(label);
					componentNr++;
					HtmlInputText inputText = (HtmlInputText) app
							.createComponent(HtmlInputText.COMPONENT_TYPE);
					inputText.setValue(f.get(criteriaAC.getSelectedObject()));
					inputText.setLabel("inputText" + componentNr);
					panel.getChildren().add(inputText);
					componentNr++;
				} else if (f.getType().equals(Set.class)) {

				} else if (f.getType().equals(String[].class)) {
					System.out.println(Array.getLength(f.get(criteriaAC
							.getSelectedObject())));
					for (int j = 0; j < Array.getLength(f.get(criteriaAC
							.getSelectedObject())); j++) {
						HtmlOutputLabel label = (HtmlOutputLabel) app
								.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
						label.setValue(j);
						label.setId("label" + componentNr);
						panel.getChildren().add(label);
						componentNr++;
						HtmlInputText inputText = (HtmlInputText) app
								.createComponent(HtmlInputText.COMPONENT_TYPE);
						inputText.setValue(Array.get(f.get(criteriaAC
								.getSelectedObject()), j));
						inputText.setLabel("inputText" + componentNr);
						panel.getChildren().add(inputText);
						componentNr++;
					}
				}

		}

	}

	public HtmlPanelGrid getPanelGrid() {
		return panelGrid;
	}

}
