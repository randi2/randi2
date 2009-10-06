/* This file is part of RANDI2.
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
package de.randi2.jsf.controllerBeans;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import com.icesoft.faces.context.ByteArrayResource;
import com.icesoft.faces.context.FileResource;
import com.icesoft.faces.context.Resource;

import de.randi2.jsf.backingBeans.Step4;
import de.randi2.jsf.backingBeans.Step5;
import de.randi2.jsf.supportBeans.Popups;
import de.randi2.jsf.supportBeans.Randi2;
import de.randi2.jsf.utility.AutoCompleteObject;
import de.randi2.jsf.wrappers.ConstraintWrapper;
import de.randi2.jsf.wrappers.CriterionWrapper;
import de.randi2.model.Login;
import de.randi2.model.Role;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.model.enumerations.TrialStatus;
import de.randi2.model.randomization.AbstractRandomizationConfig;
import de.randi2.model.randomization.BiasedCoinRandomizationConfig;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import de.randi2.model.randomization.TruncatedBinomialDesignConfig;
import de.randi2.services.TrialService;
import de.randi2.services.TrialSiteService;
import de.randi2.unsorted.ContraintViolatedException;
import de.randi2.utility.ReflectionUtil;
import de.randi2.utility.logging.LogEntry;
import de.randi2.utility.logging.LogService;

/**
 * <p>
 * This class cares about the newTrial object and contains all the needed
 * methods to work with this object for the UI.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 */
public class TrialHandler extends AbstractHandler<Trial> {

	@Setter
	private TrialSiteService siteService = null;

	@Setter
	private TrialService trialService = null;

	@Setter
	private LogService logService;
	
	@Setter
	private Popups popups;

	@Setter  @Getter
	private AbstractRandomizationConfig randomizationConfig;


	@SuppressWarnings("unchecked")
	public TrialHandler() {
		criteriaList = new ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>>();
		try {
			for (Class<?> c : ReflectionUtil
					.getClasses("de.randi2.model.criteria")) {
				try {
					if (c.getSuperclass().equals(AbstractCriterion.class))
						criteriaList
								.add((AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>) c
										.getConstructor().newInstance());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private AutoCompleteObject<TrialSite> trialSitesAC = null;
	private AutoCompleteObject<Login> sponsorInvestigatorsAC = null;
	private AutoCompleteObject<TrialSite> participatingSitesAC = null;

	public AutoCompleteObject<TrialSite> getTrialSitesAC() {
		if (trialSitesAC == null)
			trialSitesAC = new AutoCompleteObject<TrialSite>(siteService);
		return trialSitesAC;

	}

	public AutoCompleteObject<Login> getSponsorInvestigatorsAC() {
		if (sponsorInvestigatorsAC != null && trialSitesAC.isObjectSelected())
			return sponsorInvestigatorsAC;
		if (trialSitesAC.isObjectSelected())
			sponsorInvestigatorsAC = new AutoCompleteObject<Login>(trialSitesAC
					.getSelectedObject().getMembersWithSpecifiedRole(
							Role.ROLE_P_INVESTIGATOR));
		else
			sponsorInvestigatorsAC = new AutoCompleteObject<Login>(
					new ArrayList<Login>());
		return sponsorInvestigatorsAC;
	}

	public AutoCompleteObject<TrialSite> getParticipatingSitesAC() {
		if (participatingSitesAC == null)
			participatingSitesAC = new AutoCompleteObject<TrialSite>(
					siteService);
		return participatingSitesAC;
	}

	private ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> criteriaList = null;

	public ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> getCriteriaList() {
		return criteriaList;
	}

	private boolean addingSubjectsEnabled = false;

	public Resource getTempProtocol() {
		if (showedObject != null && showedObject.getProtocol() != null)
			return new FileResource(showedObject.getProtocol());
		else
			try {
				return new ByteArrayResource(toByteArray(FacesContext
						.getCurrentInstance().getExternalContext()
						.getResourceAsStream("/protocol.pdf")));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}

	// TODO Probably not the best place for this method ... after the decision
	// about the protocol files has been made, rethink this solution
	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buf = new byte[4096];
		int len = 0;
		while ((len = input.read(buf)) > -1)
			output.write(buf, 0, len);
		return output.toByteArray();
	}

	public boolean isAddingSubjectsEnabled() {
		addingSubjectsEnabled = !creatingMode && showedObject != null;
		return addingSubjectsEnabled;
	}

	public List<SelectItem> getStateItems() {
		List<SelectItem> stateItems = new ArrayList<SelectItem>(TrialStatus
				.values().length);
		ResourceBundle tempRB = ResourceBundle.getBundle(
				"de.randi2.jsf.i18n.trialState", ((LoginHandler) FacesContext
						.getCurrentInstance().getApplication().getELResolver()
						.getValue(
								FacesContext.getCurrentInstance()
										.getELContext(), null, "loginHandler"))
						.getChosenLocale());
		for (TrialStatus s : TrialStatus.values()) {
			stateItems.add(new SelectItem(s, tempRB.getString(s.toString())));
		}
		return stateItems;
	}

	public void addTrialSite(ActionEvent event) {
		assert (participatingSitesAC.getSelectedObject() != null);
		showedObject.getParticipatingSites().add(
				participatingSitesAC.getSelectedObject());
	}

	public void removeTrialSite(ActionEvent event) {
		TrialSite tTrialSite = (TrialSite) (((UIComponent) event.getComponent()
				.getChildren().get(0)).getValueExpression("value")
				.getValue(FacesContext.getCurrentInstance().getELContext()));
		showedObject.getParticipatingSites().remove(tTrialSite);

	}

	public String createTrial() {
		// try {
		/* Leading Trial Site & Sponsor Investigator */
		showedObject.setLeadingSite(trialSitesAC.getSelectedObject());
		if (sponsorInvestigatorsAC.getSelectedObject() != null)
			showedObject.setSponsorInvestigator(sponsorInvestigatorsAC
					.getSelectedObject().getPerson());
		// TODO Protokoll
		// TODO Status
		showedObject.setStatus(TrialStatus.ACTIVE);

		/* SubjectProperties Configuration */
		ValueExpression ve1 = FacesContext.getCurrentInstance()
				.getApplication().getExpressionFactory().createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{step4}", Step4.class);
		Step4 currentStep4 = (Step4) ve1.getValue(FacesContext.getCurrentInstance()
				.getELContext());
		ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> configuredCriteria = new ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>>();
		for (CriterionWrapper<? extends Serializable> cr : currentStep4.getCriteria()) {
			/* Strata configuration */
			if(cr.isStrataFactor()){
				if(DichotomousCriterion.class.isInstance(cr.getWrappedCriterion())){
					DichotomousCriterion temp = DichotomousCriterion.class.cast(cr.getWrappedCriterion());
		    		try {
						temp.addStrata(new DichotomousConstraint(Arrays.asList(new String[]{temp.getConfiguredValues().get(0)})));
						temp.addStrata(new DichotomousConstraint(Arrays.asList(new String[]{temp.getConfiguredValues().get(1)})));
					} catch (ContraintViolatedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	else{
					for(ConstraintWrapper<?> cw : cr.getStrata()){
						cr.getWrappedCriterion().addStrata(cw.configure());
					}
					//TODO sysout
					for(Object c : cr.getWrappedCriterion().getStrata()){
						System.out.println(c);
					}
				}
			}
			/* End of strata configuration */
			configuredCriteria
					.add((AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>) cr
							.getWrappedCriterion());
		}
		showedObject.setCriteria(configuredCriteria);
		/* End of SubjectProperites Configuration */

		/* Algorithm Configuration */
		ValueExpression ve2 = FacesContext.getCurrentInstance()
				.getApplication().getExpressionFactory().createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{step5}", Step5.class);
		Step5 currentStep5 = (Step5) ve2.getValue(FacesContext.getCurrentInstance()
				.getELContext());
		if (currentStep5.getSelectedAlgorithmPanelId().equals(
				Step5.AlgorithmPanelId.COMPLETE_RANDOMIZATION.toString())) {
			showedObject
					.setRandomizationConfiguration(new CompleteRandomizationConfig());
		} else if (currentStep5.getSelectedAlgorithmPanelId().equals(
				Step5.AlgorithmPanelId.BIASEDCOIN_RANDOMIZATION.toString())) {
			showedObject
					.setRandomizationConfiguration(new BiasedCoinRandomizationConfig());
		} else if (currentStep5.getSelectedAlgorithmPanelId().equals(
				Step5.AlgorithmPanelId.BLOCK_RANDOMIZATION.toString())) {
			showedObject.setRandomizationConfiguration(randomizationConfig);
		}
		else if (currentStep5.getSelectedAlgorithmPanelId().equals(
				Step5.AlgorithmPanelId.TRUNCATED_RANDOMIZATION.toString())) {
			showedObject.setRandomizationConfiguration(new TruncatedBinomialDesignConfig());
		}
		else if (currentStep5.getSelectedAlgorithmPanelId().equals(
				Step5.AlgorithmPanelId.URN_MODEL.toString())) {
			showedObject.setRandomizationConfiguration(randomizationConfig);
		}
		/* End of the Algorithm Configuration */

		trialService.create(showedObject);
		popups.showTrialCreatedPopup();
		currentStep4.clean();
		currentStep5.clean();
		randomizationConfig = null;
		trialSitesAC = null;
		sponsorInvestigatorsAC = null;
		return Randi2.SUCCESS;
		// } catch (Exception e) {
		// e.printStackTrace();
		// Randi2.showMessage(e);
		// return Randi2.ERROR;
		// }

	}

	public void addArm(ActionEvent event) {
		assert (showedObject != null);
		TreatmentArm temp = new TreatmentArm();
		showedObject.getTreatmentArms().add(temp);
	}

	public void removeArm(ActionEvent event) {
		assert (showedObject != null);
		showedObject.getTreatmentArms().remove(
				showedObject.getTreatmentArms().size() - 1);
	}

	public int getTreatmentArmsCount() {
		assert (showedObject != null);
		return showedObject.getTreatmentArms().size();
	}

	@Override
	public String refreshShowedObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getTrialsAmount() {
		return trialService.getAll().size();
	}

	public List<Trial> getAllTrials() {
		return trialService.getAll();
	}

	public List<LogEntry> getLogEntries() {
		return logService.getLogEntries(showedObject.getClass(), showedObject
				.getId());
	}

	@Override
	protected Trial createPlainObject() {
		Trial t = new Trial();
		// Start & End Date will be initalised with the today's date
		t.setStartDate(new GregorianCalendar());
		t.setEndDate(new GregorianCalendar());
		// Each new Trial has automatic 2 Treatment Arms
		t.getTreatmentArms().add(new TreatmentArm());
		t.getTreatmentArms().add(new TreatmentArm());
		return t;
	}


}
