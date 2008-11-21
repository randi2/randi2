package de.randi2.jsf.wrappers;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Random;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.jasper.tagplugins.jstl.core.Set;

import com.icesoft.faces.component.ext.HtmlInputText;
import com.icesoft.faces.component.ext.HtmlOutputLabel;
import com.icesoft.faces.component.ext.HtmlPanelGrid;

import de.randi2.jsf.utility.AutoCompleteObject;
import de.randi2.model.SubjectProperty;
import de.randi2.model.criteria.AbstractCriterion;

public class SubjectPropertyWrapper {
	private SubjectProperty property = null;
	private AutoCompleteObject<AbstractCriterion> criteriaAC = null;
	private final List<AbstractCriterion> possibleCriteria;
	private final String id;

	public SubjectPropertyWrapper(List<AbstractCriterion> _possibleCriteria) {
		possibleCriteria = _possibleCriteria;
		id = Integer.toString(new Random().nextInt());
	}

	public SubjectProperty getProperty() {
		return property;
	}

	public AutoCompleteObject<AbstractCriterion> getCriteriaAC() {
		if (criteriaAC == null) {
			criteriaAC = new AutoCompleteObject<AbstractCriterion>(
					possibleCriteria);
		}
		return criteriaAC;
	}

	public void createCriterionPanel(UIComponent component)
			throws IllegalArgumentException, IllegalAccessException {
		// create sample components
		// HtmlSelectOneListbox listbox = new HtmlSelectOneListbox();
		// HtmlInputText text1 = new HtmlInputText();
		// text1.setValue("TEST");
		// List valueList = new ArrayList();
		// SelectItem selectItem = new SelectItem("TEST1", "TEST1");
		// valueList.add(selectItem);
		// selectItem = new SelectItem("TEST2", "TEST2");
		// valueList.add(selectItem);
		// UISelectItems items = new UISelectItems();
		// items.setValue(valueList);
		// listbox.getChildren().add(items);
		// grid1 = getGrid1();
		// UIComponent testGrid =
		// FacesContext.getCurrentInstance().getViewRoot().findComponent(this.getId());
		// Add components
		// testGrid.getChildren().add(text1);

		Application application = FacesContext.getCurrentInstance()
				.getApplication();
		HtmlPanelGrid panel = null;
		for(UIComponent comp : component.getParent()
				.getChildren()){
			if(comp instanceof HtmlPanelGrid)
				panel = (HtmlPanelGrid)comp;
		}
		System.out.println(panel.getId());
		panel.getChildren().clear();
		int componentNr = 0;
		for (Field f : criteriaAC.getSelectedObject().getClass()
				.getDeclaredFields()) {
			System.out.println(f.getType().getCanonicalName());
			if (!Modifier.isStatic(f.getModifiers())) // We're only interested
				// in not-static fields
				if (f.getType().equals(String.class)) {
					HtmlOutputLabel label = (HtmlOutputLabel) application
							.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
					label.setValue(f.getName());
					label.setId("label" + componentNr);
					panel.getChildren().add(label);
					componentNr++;
					HtmlInputText inputText = (HtmlInputText) application
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
						HtmlOutputLabel label = (HtmlOutputLabel) application
								.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
						label.setValue(j);
						label.setId("label" + componentNr);
						panel.getChildren().add(label);
						componentNr++;
						HtmlInputText inputText = (HtmlInputText) application
								.createComponent(HtmlInputText.COMPONENT_TYPE);
						inputText.setValue(Array.get(f.get(criteriaAC
								.getSelectedObject()), j));
						inputText.setLabel("inputText" + componentNr);
						panel.getChildren().add(inputText);
						componentNr++;
					}
				}

		}

		// HtmlOutputText newOutputText = (HtmlOutputText) application
		// .createComponent(HtmlOutputText.COMPONENT_TYPE);
		// newOutputText.setValue("Hello");
		// newOutputText.setId("Label2");
		// panel.getChildren().add(newOutputText);

	}

	public void criterionChanged(ValueChangeEvent event) {
		System.out.println(event.getComponent().getId());
		criteriaAC.updateObjectList(event);
		if (criteriaAC.isObjectSelected()) {
			try {
				createCriterionPanel(event.getComponent());
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getId() {
		return id;
	}

}
