package de.randi2.jsf.wrappers;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import com.icesoft.faces.component.ext.HtmlPanelGroup;
import com.icesoft.faces.component.panelcollapsible.PanelCollapsible;
import com.icesoft.faces.component.selectinputtext.SelectInputText;
import com.icesoft.faces.component.selectinputtext.SelectInputTextTag;

import de.randi2.jsf.handlers.LoginHandler;
import de.randi2.jsf.utility.AutoCompleteObject;
import de.randi2.model.SubjectProperty;
import de.randi2.model.criteria.AbstractCriterion;

public class SubjectPropertyWrapper {

	/**
	 * The number of the subject property which represents also the index within
	 * the properties collection of the Step4 object
	 */
	private final int propertyNr;

	private final Application app = FacesContext.getCurrentInstance()
			.getApplication();
	private final  ELContext elContext = FacesContext.getCurrentInstance()
			.getELContext();
	private final ExpressionFactory expressionFactory = app
			.getExpressionFactory();

	/**
	 * ResourceBundle for l16n.
	 */
	private ResourceBundle criteria = null;

	/**
	 * The selected criterion.
	 */
	private AbstractCriterion selectedCriterion = null;
	private SubjectProperty property = null;

	private AutoCompleteObject<AbstractCriterion> criteriaAC;
	private UIComponent propertyPanel;
	private HtmlPanelGrid criteriaPanel;

	@SuppressWarnings("unchecked")
	public SubjectPropertyWrapper(int _propertyNr) {

		propertyNr = _propertyNr;

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
		HtmlPanelGroup headerPanel = (HtmlPanelGroup) app.createComponent(HtmlPanelGroup.COMPONENT_TYPE);
		headerPanel.setStyle("padding-left: 25px;");
		HtmlOutputLabel headerLabel = (HtmlOutputLabel) app.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
		headerLabel.setValue("!Property! "+propertyNr);
		headerPanel.getChildren().add(headerLabel);
		((PanelCollapsible)propertyPanel).getFacets().put("header", headerPanel);
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
						selectedCriterion = criteriaAC.getSelectedObject().getClass().getConstructor().newInstance();
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
			IllegalAccessException, SecurityException, NoSuchFieldException {
		assert (selectedCriterion != null);
		if (criteriaPanel == null) {
			criteriaPanel = (HtmlPanelGrid) app
					.createComponent(HtmlPanelGrid.COMPONENT_TYPE);
			criteriaPanel.setColumns(1);
		} else
			criteriaPanel.getChildren().clear();

		// Create name fields
		criteriaPanel.getChildren().addAll(
				creatComponentsForField(selectedCriterion.getClass().getField(
						"name"), expressionFactory.createValueExpression(
						elContext, "#{step4.properties[" + propertyNr
								+ "].selectedCriterion.name}", String.class),
						HtmlInputText.class));

		// Create description fields
		criteriaPanel.getChildren().addAll(
				creatComponentsForField(selectedCriterion.getClass().getField(
						"description"), expressionFactory
						.createValueExpression(elContext, "#{step4.properties["
								+ propertyNr
								+ "].selectedCriterion.description}",
								String.class), HtmlInputTextarea.class));

		// Examine the object
		for (Field f : selectedCriterion.getClass().getDeclaredFields()) {
			// We're only interested in non-static, public fields
			if (!Modifier.isStatic(f.getModifiers())
					&& Modifier.isPublic(f.getModifiers()))
				// String
				if (f.get(selectedCriterion) instanceof String) {
					criteriaPanel
							.getChildren()
							.addAll(
									creatComponentsForField(
											f,
											expressionFactory
													.createValueExpression(
															elContext,
															"#{step4.properties["
																	+ propertyNr
																	+ "].selectedCriterion."
																	+ f
																			.getName()
																	+ "}",
															String.class),
											HtmlInputText.class));
				}
				// List<String>
				else if (f.get(selectedCriterion) instanceof List) {
					if (!((List<String>) f.get(selectedCriterion)).isEmpty()) {
						for (int nr=0;nr<((List<String>) f.get(selectedCriterion)).size();nr++) {
							criteriaPanel
									.getChildren()
									.addAll(
											creatComponentsForField(
													f,
													expressionFactory
															.createValueExpression(
																	elContext,
																	"#{step4.properties["
																			+ propertyNr
																			+ "].selectedCriterion."
																			+ f
																					.getName()
																			+ "["
																			+ nr
																			+ "]}",
																	String.class),
													HtmlInputText.class));
						}
					} else {
						// FIXME TEMP
						for (int i = 0; i < 4; i++) {
							((List<String>) f.get(selectedCriterion))
									.add(new String("test" + i));
						}
						for (int nr=0;nr<((List<String>) f.get(selectedCriterion)).size();nr++) {
							criteriaPanel
									.getChildren()
									.addAll(
											creatComponentsForField(
													f,
													expressionFactory
															.createValueExpression(
																	elContext,
																	"#{step4.properties["
																			+ propertyNr
																			+ "].selectedCriterion."
																			+ f
																					.getName()
																			+ "["
																			+ nr
																			+ "]}",
																	String.class),
													HtmlInputText.class));
						}
					}

				} else if (f.get(selectedCriterion) instanceof String[]) { // String
					// Arrays
					for (int j = 0; j < Array.getLength(f.get(criteriaAC
							.getSelectedObject())); j++) {
						criteriaPanel.getChildren().addAll(
								creatComponentsForField(
										f,
										expressionFactory
												.createValueExpression(
														elContext,
														"#{step4.properties["
																+ propertyNr
																+ "].selectedCriterion."
																+ f
																		.getName()
																+ "["
																+ j
																+ "]}",
														String.class),
										HtmlInputText.class));
					}
				}

		}
		propertyPanel.getChildren().add(criteriaPanel);

	}

	private List<UIComponent> creatComponentsForField(Field field,
			ValueExpression ve, Class<?> c) {
		List<UIComponent> components = new ArrayList<UIComponent>();

		HtmlOutputLabel label = (HtmlOutputLabel) app
				.createComponent(HtmlOutputLabel.COMPONENT_TYPE);
		label.setValue(criteria.getString(field.getDeclaringClass()
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
			((HtmlInputTextarea) input)
					.setStyle("width:175px;height:50px;overflow: auto;");
		}

		input.setValueExpression("value", ve);
		components.add(input);

		return components;
	}

	public UIComponent getPropertyPanel() {
		return propertyPanel;
	}

	public AbstractCriterion getSelectedCriterion() {
		return selectedCriterion;
	}

}
