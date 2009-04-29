package de.randi2.jsf.wrappers;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIPanel;
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
import com.icesoft.faces.component.ext.HtmlPanelGroup;
import com.icesoft.faces.component.ext.HtmlSelectBooleanCheckbox;
import com.icesoft.faces.component.ext.HtmlSelectManyMenu;
import com.icesoft.faces.component.ext.HtmlSelectOneMenu;
import com.icesoft.faces.component.panelcollapsible.PanelCollapsible;
import com.icesoft.faces.component.selectinputtext.SelectInputText;
import com.icesoft.faces.component.selectinputtext.SelectInputTextTag;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.jsf.utility.AutoCompleteObject;
import de.randi2.model.SubjectProperty;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.utility.ReflectionUtil;

/**
 * Wrapper for the SubjectProperty/Criterion
 * @author Lukasz Plotnicki
 *
 */
public class SubjectPropertyWrapper {

	/**
	 * The number of the subject property which represents also the index within
	 * the properties collection of the Step4 object
	 */
	private final int propertyNr;

	private final Application app = FacesContext.getCurrentInstance()
			.getApplication();
	private final ELContext elContext = FacesContext.getCurrentInstance()
			.getELContext();
	private final ExpressionFactory expressionFactory = app
			.getExpressionFactory();

	/**
	 * ResourceBundle for l16n.
	 */
	private ResourceBundle criteriaNames = null;

	/**
	 * The selected criterion.
	 */
	private AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<?>> selectedCriterion = null;
	private SubjectProperty<?> property = null;

	private AutoCompleteObject<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<?>>> criteriaAC;
	private UIComponent propertyPanel;
	private HtmlPanelGrid criteriaPanel;
	private HtmlPanelGrid firstPanel;
	private HtmlPanelGrid secondPanel;

	private Listener listener;

	private SelectInputText criteriaInputText;

	private List<SelectItem> valuesToShow;
	
	public List<? extends Serializable> getSelectedValues(){
		if(selectionComponents==null)
			return null;
		ArrayList<Serializable> list = new ArrayList<Serializable>();		
		for(UIInput s : selectionComponents){
//			System.out.println(s.getSubmittedValue());
			if(s instanceof HtmlSelectOneMenu){
				System.out.println(((HtmlSelectOneMenu)s).getLocalValue());
				list.add((Serializable) ((HtmlSelectOneMenu)s).getLocalValue());
			} else if(s instanceof HtmlSelectManyMenu){
				for(Object o : ((HtmlSelectManyMenu)s).getSelectedValues()){
					System.out.println(o);
					list.add((Serializable)o);
				}
			}
		}
		return list;
	}

	private HtmlSelectBooleanCheckbox isInclCritCheckbox;

	private HtmlPanelGrid constraintsPanel;

	private List<UIInput> selectionComponents;

	@SuppressWarnings("unchecked")
	public SubjectPropertyWrapper(int _propertyNr) {

		propertyNr = _propertyNr;
		listener = new Listener();

		ValueExpression ve = expressionFactory.createValueExpression(elContext,
				"#{loginHandler}", LoginHandler.class);
		LoginHandler currentLoginHandler = (LoginHandler) ve
				.getValue(elContext);
		criteriaNames = ResourceBundle.getBundle("de.randi2.jsf.i18n.criteria",
				currentLoginHandler.getChosenLocale());
		criteriaAC = new AutoCompleteObject<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<?>>>(
				(ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<?>>>) expressionFactory
						.createValueExpression(
								FacesContext.getCurrentInstance()
										.getELContext(),
								"#{trialHandler.criteriaList}", ArrayList.class)
						.getValue(elContext));
		propertyPanel = app.createComponent(PanelCollapsible.COMPONENET_TYPE);
		propertyPanel.setValueExpression("expanded", expressionFactory
				.createValueExpression(Boolean.TRUE, Boolean.class));
		propertyPanel.setValueExpression("toggleOnClick", expressionFactory
				.createValueExpression(Boolean.FALSE, Boolean.class));
		HtmlPanelGroup headerPanel = (HtmlPanelGroup) app
				.createComponent(HtmlPanelGroup.COMPONENT_TYPE);
		headerPanel.setStyle("padding-left: 25px;");
		HtmlOutputLabel headerLabel = (HtmlOutputLabel) app
				.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
		headerLabel.setValue("!Property! " + propertyNr);
		headerPanel.getChildren().add(headerLabel);
		((PanelCollapsible) propertyPanel).getFacets().put("header",
				headerPanel);
		HtmlPanelGrid criteriaChoicePanel = (HtmlPanelGrid) app
				.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
		criteriaChoicePanel.setColumns(2);

		HtmlOutputLabel crieteriaLabel = (HtmlOutputLabel) app
				.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
		crieteriaLabel.setValue(criteriaNames.getString("criteriaType"));

		criteriaInputText = (SelectInputText) app
				.createComponent(SelectInputText.COMPONENT_TYPE);
		SelectInputTextTag sitTag = new SelectInputTextTag();
		criteriaInputText.setRendererType(sitTag.getRendererType());
		criteriaInputText.setPartialSubmit(true);
		criteriaInputText.setImmediate(true);
		criteriaInputText.setSize(25);
		UISelectItems selectItems = (UISelectItems) app
				.createComponent(UISelectItems.COMPONENT_TYPE);
		for (SelectItem si : criteriaAC.getObjectList()) {
			si.setLabel(criteriaNames.getString(si.getLabel()));
		}
		selectItems.setValue(criteriaAC.getObjectList());
		criteriaInputText.getChildren().add(selectItems);
		criteriaInputText.addValueChangeListener(listener);

		criteriaChoicePanel.getChildren().add(crieteriaLabel);
		criteriaChoicePanel.getChildren().add(criteriaInputText);
		propertyPanel.getChildren().add(criteriaChoicePanel);

	}

	public SubjectProperty<?> getProperty() {
		return property;
	}

	@SuppressWarnings("unchecked")
	public void createCriterionPanel() throws IllegalArgumentException,
			IllegalAccessException, SecurityException, NoSuchFieldException {
		assert (selectedCriterion != null);
		if (criteriaPanel == null) {
			criteriaPanel = (HtmlPanelGrid) app
					.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
			criteriaPanel.setColumns(2);
		} else
			criteriaPanel.getChildren().clear();

		// Create new content Panels
		firstPanel = (HtmlPanelGrid) app
				.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
		firstPanel.setColumns(1);
		secondPanel = (HtmlPanelGrid) app
				.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
		secondPanel.setColumns(1);

		// Create name fields
		firstPanel.getChildren().addAll(
				creatComponentsForProperty(selectedCriterion.getClass()
						.getField("name"), expressionFactory
						.createValueExpression(elContext, "#{step4.properties["
								+ propertyNr + "].selectedCriterion.name}",
								String.class), HtmlInputText.class));

		// Create description fields
		firstPanel.getChildren().addAll(
				creatComponentsForProperty(selectedCriterion.getClass()
						.getField("description"), expressionFactory
						.createValueExpression(elContext, "#{step4.properties["
								+ propertyNr
								+ "].selectedCriterion.description}",
								String.class), HtmlInputTextarea.class));

		// Examine the object
		Map<Field, Method> properties = ReflectionUtil
				.getPropertyWithGetter(selectedCriterion);
		Method m = null;
		for (Field f : properties.keySet()) {
			m = properties.get(f);
			// String
			if (m.getReturnType().equals(String.class)) {
				firstPanel.getChildren().addAll(
						creatComponentsForProperty(f, expressionFactory
								.createValueExpression(elContext,
										"#{step4.properties[" + propertyNr
												+ "].selectedCriterion."
												+ f.getName() + "}",
										String.class), HtmlInputText.class));
			}
			// List<String>
			else if (m.getReturnType().equals(List.class)) {
				try {
					for (int nr = 0; nr < ((List<String>) m.invoke(selectedCriterion, new Object[]{}))
							.size(); nr++) {
						firstPanel.getChildren().addAll(
								creatComponentsForProperty(f, expressionFactory
										.createValueExpression(elContext,
												"#{step4.properties[" + propertyNr
														+ "].selectedCriterion."
														+ f.getName() + "[" + nr
														+ "]}", String.class),
										HtmlInputText.class));
					}
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		HtmlOutputLabel isInclCritLabel = (HtmlOutputLabel) app
				.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
		isInclCritLabel
				.setValue(criteriaNames
						.getString("de.randi2.model.criteria.AbstractCriterion.isInclusionCriterion"));
		isInclCritCheckbox = (HtmlSelectBooleanCheckbox) app
				.createComponent(HtmlSelectBooleanCheckbox.COMPONENT_TYPE);
		isInclCritCheckbox.setPartialSubmit(true);
		isInclCritCheckbox.setValueExpression("disabled", expressionFactory
				.createValueExpression(elContext, "#{step4.properties["
						+ propertyNr
						+ "].selectedCriterion.configuredValues==null}",
						Boolean.class));
		isInclCritCheckbox.addValueChangeListener(listener);
		// isInclCritCheckbox.setValueExpression("value", expressionFactory
		// .createValueExpression(elContext, "#{step4.properties["
		// + propertyNr
		// + "].selectedCriterion.inclusionCriterion}",
		// Boolean.class));

		secondPanel.getChildren().add(isInclCritLabel);
		secondPanel.getChildren().add(isInclCritCheckbox);

		criteriaPanel.getChildren().add(firstPanel);
		criteriaPanel.getChildren().add(secondPanel);
		propertyPanel.getChildren().add(criteriaPanel);

	}

	/**
	 * This method created the panel for the Inclusion Constraints definition
	 * 
	 * @param parentPanel
	 *            - the UIPanel in which the new objects should be placed.
	 * @throws IllegalArgumentException
	 *             - may be thrown if the examination of the field goes wrong
	 * @throws IllegalAccessException
	 *             - may be thrown if the examination of the field goes wrong
	 */
	private void createConstraintsPanel(UIPanel parentPanel)
			throws IllegalArgumentException, IllegalAccessException {
		assert (selectedCriterion != null);
		if (constraintsPanel == null) {
			constraintsPanel = (HtmlPanelGrid) app
					.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
			constraintsPanel.setColumns(2);
		} else
			constraintsPanel.getChildren().clear();

		// Examine the object
		Map<Field, Method> properties;
		properties = ReflectionUtil.getPropertyWithGetter(selectedCriterion
				.getContstraintType());
		Method m = null;
		List<UIComponent> components;
		selectionComponents = new ArrayList<UIInput>();
		for (Field f : properties.keySet()) {
			System.out.println(f.getName());
			m = properties.get(f);
			// String
			if (m.getReturnType().equals(String.class)) {
				updateConstraintValues();
				components = creatComponentsForProperty(f, HtmlSelectOneMenu.class);
				selectionComponents.add((UIInput) components.get(1));
				constraintsPanel.getChildren().addAll(components
						);
			}
			// List<String>
			else if (m.getReturnType().equals(List.class)) {
				updateConstraintValues();
				components = creatComponentsForProperty(f, HtmlSelectManyMenu.class);
				selectionComponents.add((UIInput) components.get(1));
				constraintsPanel.getChildren().addAll(components
						);
			}
			components = null;

		}
		// constraintsPanel.setValueExpression("visible", expressionFactory
		// .createValueExpression(elContext, "#{step4.properties["
		// + propertyNr
		// + "].selectedCriterion.inclusionCriterion}",
		// Boolean.class));
		parentPanel.getChildren().add(constraintsPanel);

	}

	private List<UIComponent> creatComponentsForProperty(Field field,
			ValueExpression ve, Class<? extends UIComponent> c) {

		List<UIComponent> components = new ArrayList<UIComponent>();

		HtmlOutputLabel label = (HtmlOutputLabel) app
				.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
		label.setValue(criteriaNames.getString(field.getDeclaringClass()
				.getCanonicalName()
				+ "." + field.getName()));
		components.add(label);
		UIComponent input = null;

		if (c.equals(HtmlInputText.class)) {
			input = (HtmlInputText) app
					.createComponent(HtmlInputText.COMPONENT_TYPE);
			((HtmlInputText) input).setPartialSubmit(true);
			((HtmlInputText) input).setSize(30);
		} else if (c.equals(HtmlInputTextarea.class)) {
			input = (HtmlInputTextarea) app
					.createComponent(HtmlInputTextarea.COMPONENT_TYPE);
			((HtmlInputTextarea) input).setPartialSubmit(true);
			((HtmlInputTextarea) input)
					.setStyle("width:175px;height:50px;overflow: auto;");
		}
		if(input!=null){
			input.setValueExpression("value", ve);
			((UIInput) input).addValueChangeListener(listener);
			components.add(input);
		}
		return components;
	}

	private List<UIComponent> creatComponentsForProperty(Field field,
	/* ValueExpression ve, */Class<? extends UIComponent> c) {

		List<UIComponent> components = new ArrayList<UIComponent>();

		HtmlOutputLabel label = (HtmlOutputLabel) app
				.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
		label.setValue(criteriaNames.getString(field.getDeclaringClass()
				.getCanonicalName()
				+ "." + field.getName()));
		components.add(label);
		UIInput input = null;

		if (c.equals(HtmlSelectOneMenu.class)) {
			input = (HtmlSelectOneMenu) app
					.createComponent(HtmlSelectOneMenu.COMPONENT_TYPE);
			((HtmlSelectOneMenu) input).setPartialSubmit(true);
			UISelectItems itemsComponent = (UISelectItems) app
					.createComponent(UISelectItems.COMPONENT_TYPE);
			// itemsComponent.setValue(itemsToShow);
			itemsComponent.setValueExpression("value", expressionFactory
					.createValueExpression(elContext, "#{step4.properties["
							+ propertyNr + "].valuesToShow}", List.class));
			input.getChildren().add(itemsComponent);
		} else if (c.equals(HtmlSelectManyMenu.class)) {
			input = (HtmlSelectManyMenu) app
					.createComponent(HtmlSelectManyMenu.COMPONENT_TYPE);
			((HtmlSelectManyMenu) input).setPartialSubmit(true);
			UISelectItems itemsComponent = (UISelectItems) app
					.createComponent(UISelectItems.COMPONENT_TYPE);
			// itemsComponent.setValue(itemsToShow);
			itemsComponent.setValueExpression("value", expressionFactory
					.createValueExpression(elContext, "#{step4.properties["
							+ propertyNr + "].valuesToShow}", List.class));
			input.getChildren().add(itemsComponent);
			((HtmlSelectManyMenu) input)
					.setStyle("height: 75px; widht: 100px;");
		}

		// input.setValueExpression("value", ve);
		if (input != null) {
			input.addValueChangeListener(listener);
			components.add(input);
		}

		return components;
	}

	public UIComponent getPropertyPanel() {
		return propertyPanel;
	}

	public AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<?>> getSelectedCriterion() {
		return selectedCriterion;
	}

	private void updateConstraintValues() {
		if (selectedCriterion.getConfiguredValues() != null) {
			if (valuesToShow == null)
				valuesToShow = new ArrayList<SelectItem>();
			else
				valuesToShow.clear();
			for (Object value : selectedCriterion.getConfiguredValues()) {
				valuesToShow.add(new SelectItem(value, value.toString()));
			}
		}
	}

	class Listener implements ValueChangeListener {

		@Override
		public void processValueChange(ValueChangeEvent arg0)
				throws AbortProcessingException {
			if (arg0 == null || arg0.getComponent() == null)
				return;
			if (arg0.getComponent().equals(criteriaInputText)) {
				criteriaInputValueChange(arg0);
			} else if (arg0.getComponent().equals(isInclCritCheckbox)) {
				inculsionCriteriaCheckboxValueChanged(arg0);
			} else {
				// if (!arg0.getOldValue().equals(arg0.getNewValue()))
				updateConstraintValues();
			}

		}

		private void inculsionCriteriaCheckboxValueChanged(ValueChangeEvent arg0) {
			try {
				if (arg0.getNewValue().toString().equals("true")) {
					// FIXME
					// selectedCriterion.setInclusionCriterion(true);
					createConstraintsPanel(secondPanel);
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@SuppressWarnings("unchecked")
		private void criteriaInputValueChange(ValueChangeEvent arg0) {
			criteriaAC.updateObjectList(arg0);
			if (criteriaAC.isObjectSelected())
				try {
					selectedCriterion = criteriaAC.getSelectedObject()
							.getClass().getConstructor().newInstance();
					createCriterionPanel();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	public List<SelectItem> getValuesToShow() {
		return valuesToShow;
	}

	public void setValuesToShow(List<SelectItem> valuesToShow) {
		this.valuesToShow = valuesToShow;
	}

}
