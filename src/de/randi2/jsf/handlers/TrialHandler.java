package de.randi2.jsf.handlers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import de.randi2.dao.TrialDao;
import de.randi2.dao.TrialSiteDao;
import de.randi2.jsf.Randi2;
import de.randi2.jsf.utility.AutoCompleteObject;
import de.randi2.jsf.wrappers.SubjectPropertyWrapper;
import de.randi2.model.Login;
import de.randi2.model.Role;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.enumerations.TrialStatus;

/**
 * <p>
 * This class cares about the newTrial object and contains all the needed
 * methods to work with this object for the UI.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 */
public class TrialHandler extends AbstractHandler<Trial> {

	@SuppressWarnings("unchecked")
	public TrialHandler() {
		super(Trial.class);
		tempList = new ArrayList<AbstractCriterion>();
		try {
			for (Class c : Randi2.getClasses("de.randi2.model.criteria")) {
				try {
					if (c.getGenericSuperclass()
							.equals(AbstractCriterion.class))
						tempList.add((AbstractCriterion) c.getConstructor()
								.newInstance());
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

	/**
	 * Object for the trial creation.
	 */
	private Trial newTrial;

	private AutoCompleteObject<TrialSite> trialSitesAC = null;
	private AutoCompleteObject<Login> sponsorInvestigatorsAC = null;
	private AutoCompleteObject<TrialSite> participatingSitesAC = null;

	private ArrayList<AbstractCriterion> tempList = null;

	// TODO TEMP OBJECTS
	private TimeZone zone;
	private ArrayList<SubjectPropertyWrapper> properties = null;

	// DB Access
	private TrialDao trialDao;
	private TrialSiteDao centerDao;

	public TrialDao getTrialDao() {
		return trialDao;
	}

	public void setTrialDao(TrialDao trialDao) {
		this.trialDao = trialDao;
	}

	public Trial getNewTrial() {
		if (newTrial == null) { // TODO
			newTrial = new Trial();
			newTrial.setStartDate(new GregorianCalendar());
			newTrial.setEndDate(new GregorianCalendar());
		}
		return newTrial;
	}

	public void setNewTrial(Trial trial) {
		this.newTrial = trial;
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
		newTrial.getParticipatingSites().add(
				participatingSitesAC.getSelectedObject());
	}

	public void removeTrialSite(ActionEvent event) {
		TrialSite tTrialSite = (TrialSite) (((UIComponent) event.getComponent()
				.getChildren().get(0)).getValueExpression("value")
				.getValue(FacesContext.getCurrentInstance().getELContext()));
		newTrial.getParticipatingSites().remove(tTrialSite);

	}

	public String createTrial() {
		// TODO Need to be implemented!
		return Randi2.SUCCESS;
	}

	public void addArm(ActionEvent event) {
		assert (newTrial != null);
		TreatmentArm temp = new TreatmentArm();
		temp.setPlannedSubjects(0);
		newTrial.getTreatmentArms().add(temp);
	}

	public void removeArm(ActionEvent event) {
		assert (newTrial != null);
		newTrial.getTreatmentArms().remove(
				newTrial.getTreatmentArms().size() - 1);
	}

	public void addProperty(ActionEvent event) {
		SubjectPropertyWrapper pWrapper = new SubjectPropertyWrapper(tempList);
		this.getProperties().add(pWrapper);
	}

	public void removeProperty(ActionEvent event) {
		this.getProperties().remove(this.getProperties().size() - 1);
	}

	// TEMP
	public TimeZone getZone() {
		if (zone == null) {
			zone = TimeZone.getDefault();
		}
		return zone;
	}

	public ArrayList<SubjectPropertyWrapper> getProperties() {
		if (properties == null)
			properties = new ArrayList<SubjectPropertyWrapper>();
		return properties;
	}

	public int getTreatmentArmsCount() {
		assert (newTrial != null);
		return newTrial.getTreatmentArms().size();
	}

	public int getSubjectPropertiesCount() {
		return this.getProperties().size();
	}

	public AutoCompleteObject<TrialSite> getTrialSitesAC() {
		if (trialSitesAC == null)
			trialSitesAC = new AutoCompleteObject<TrialSite>(centerDao);
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
			participatingSitesAC = new AutoCompleteObject<TrialSite>(centerDao);
		return participatingSitesAC;
	}

	public TrialSiteDao getCenterDao() {
		return centerDao;
	}

	public void setCenterDao(TrialSiteDao centerDao) {
		this.centerDao = centerDao;
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
}
