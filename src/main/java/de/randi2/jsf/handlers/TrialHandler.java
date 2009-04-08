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
package de.randi2.jsf.handlers;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;

import de.randi2.dao.TrialDao;
import de.randi2.dao.TrialSiteDao;
import de.randi2.jsf.Randi2;
import de.randi2.jsf.pages.Step4;
import de.randi2.jsf.pages.Step5;
import de.randi2.jsf.utility.AutoCompleteObject;
import de.randi2.jsf.wrappers.SubjectPropertyWrapper;
import de.randi2.model.Login;
import de.randi2.model.Role;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.OrdinalCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.model.criteria.constraints.OrdinalConstraint;
import de.randi2.model.enumerations.TrialStatus;
import de.randi2.model.randomization.BiasedCoinRandomizationConfig;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import de.randi2.services.TrialSiteService;
import de.randi2.unsorted.ContraintViolatedException;
import de.randi2.utility.ReflectionUtil;

/**
 * <p>
 * This class cares about the newTrial object and contains all the needed
 * methods to work with this object for the UI.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 */
public class TrialHandler extends AbstractHandler<Trial> {
	
	@Autowired
	private TrialSiteService siteService;
	
	public void setSiteService(TrialSiteService siteService) {
		this.siteService = siteService;
	}

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
		if (sponsorInvestigatorsAC == null)
			sponsorInvestigatorsAC = new AutoCompleteObject<Login>(trialSitesAC
					.getSelectedObject().getMembersWithSpecifiedRole(
							Role.ROLE_P_INVESTIGATOR));
		return sponsorInvestigatorsAC;
	}

	public AutoCompleteObject<TrialSite> getParticipatingSitesAC() {
		if (participatingSitesAC == null)
			participatingSitesAC = new AutoCompleteObject<TrialSite>(siteService);
		return participatingSitesAC;
	}

	private ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> criteriaList = null;
	
	public ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> getCriteriaList() {
		return criteriaList;
	}
	
	private boolean addingSubjectsEnabled = false;
	
	public boolean isAddingSubjectsEnabled() {
		addingSubjectsEnabled = !creatingMode && showedObject!=null;
		return addingSubjectsEnabled;
	}

	// TODO TEMP OBJECTS
	private TimeZone zone;

	// DB Access
	private TrialDao trialDao;
	private TrialSiteDao trialSiteDao;

	public TrialDao getTrialDao() {
		return trialDao;
	}

	public void setTrialDao(TrialDao trialDao) {
		this.trialDao = trialDao;
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

	@SuppressWarnings("unchecked")
	public String createTrial() {
		// try {
		/* Leading Trial Site & Sponsor Investigator */
		showedObject.setLeadingSite(trialSitesAC.getSelectedObject());
		if (sponsorInvestigatorsAC.getSelectedObject() != null)
			showedObject.setSponsorInvestigator(sponsorInvestigatorsAC
					.getSelectedObject().getPerson());
		// TODO Protokoll

		/* Creating the relationship between Trial and TreatmentArm */
		// FIXME Maybe it should be made automatic by DAO object
		for (TreatmentArm tA : showedObject.getTreatmentArms()) {
			tA.setTrial(showedObject);
		}

		/* SubjectProperties Configuration */
		ValueExpression ve1 = FacesContext.getCurrentInstance()
				.getApplication().getExpressionFactory().createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{step4}", Step4.class);
		Step4 temp1 = (Step4) ve1.getValue(FacesContext.getCurrentInstance()
				.getELContext());
		ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> configuredCriteria = new ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>>();
		for (SubjectPropertyWrapper wr : temp1.getProperties()) {
			List<? extends Serializable> configuredConstraints = wr
					.getSelectedValues();
			if (configuredConstraints != null
					&& !configuredConstraints.isEmpty()) {
				if (DichotomousCriterion.class.isInstance(wr
						.getSelectedCriterion())) {
					DichotomousConstraint t;
					try {
						t = new DichotomousConstraint(
								(List<String>) configuredConstraints);
						DichotomousCriterion.class.cast(
								wr.getSelectedCriterion())
								.setInclusionCriterion(t);
					} catch (ContraintViolatedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (OrdinalCriterion.class.isInstance(wr
						.getSelectedCriterion())) {
					OrdinalConstraint o;
					try {
						o = new OrdinalConstraint(
								(List<String>) configuredConstraints);
						OrdinalCriterion.class.cast(wr.getSelectedCriterion())
								.setInclusionCriterion(o);
					} catch (ContraintViolatedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			configuredCriteria
					.add((AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>) wr
							.getSelectedCriterion());
		}
		showedObject.setCriteria(configuredCriteria);
		/* End of SubjectProperites Configuration */

		/* Algorithm Configuration */
		ValueExpression ve2 = FacesContext.getCurrentInstance()
				.getApplication().getExpressionFactory().createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{step5}", Step5.class);
		Step5 temp2 = (Step5) ve2.getValue(FacesContext.getCurrentInstance()
				.getELContext());
		if (temp2.getSelectedAlgorithmPanelId().equals(
				Step5.AlgorithmPanelId.COMPLETE_RANDOMIZATION.toString())) {
			showedObject
					.setRandomizationConfiguration(new CompleteRandomizationConfig());
		} else if (temp2.getSelectedAlgorithmPanelId().equals(
				Step5.AlgorithmPanelId.BIASEDCOIN_RANDOMIZATION.toString())) {
			showedObject
					.setRandomizationConfiguration(new BiasedCoinRandomizationConfig());
		}
		/* End of the Algorithm Configuration */

		trialDao.save(showedObject);
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

	// TEMP
	public TimeZone getZone() {
		if (zone == null) {
			zone = TimeZone.getDefault();
		}
		return zone;
	}

	public int getTreatmentArmsCount() {
		assert (showedObject != null);
		return showedObject.getTreatmentArms().size();
	}



	public TrialSiteDao getTrialSiteDao() {
		return trialSiteDao;
	}

	public void setTrialSiteDao(TrialSiteDao trialSiteDao) {
		this.trialSiteDao = trialSiteDao;
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
		return trialDao.getAll().size();
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
