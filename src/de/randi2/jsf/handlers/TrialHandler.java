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

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import de.randi2.dao.TrialDao;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.enumerations.TrialStatus;

/**
 * <p>
 * This class cares about the trial object and contains all the needed methods
 * to work with this object for the UI.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 */
public class TrialHandler {

	private Trial trial;

	// TODO TEMP OBJECTS
	private Date tDate1;
	private Date tDate2;
	private TimeZone zone;
	private ArrayList<TreatmentArm> arms = null;

	// Trial Status as SelectItems
	private List<SelectItem> stateItems = null;

	// DB Access
	private TrialDao trialDao;

	public TrialDao getTrialDao() {
		return trialDao;
	}

	public void setTrialDao(TrialDao trialDao) {
		this.trialDao = trialDao;
	}

	public Trial getTrial() {
		if (trial == null) // TODO
			trial = new Trial();
		return trial;
	}

	public void setTrial(Trial trial) {
		this.trial = trial;
	}

	public List<SelectItem> getStateItems() {
		if (stateItems == null) {
			stateItems = new Vector<SelectItem>(TrialStatus.values().length);
			for (TrialStatus s : TrialStatus.values()) {
				stateItems.add(new SelectItem(s, s.toString()));
			}
		}
		return stateItems;
	}

	public void addCenter(ActionEvent event) {
		TrialSite tCenter = ((CenterHandler) FacesContext.getCurrentInstance()
				.getApplication().getVariableResolver().resolveVariable(
						FacesContext.getCurrentInstance(), "centerHandler"))
				.getSelectedCenter();
		trial.getParticipatingSites().add(tCenter);
	}

	public void removeCenter(ActionEvent event) {
		TrialSite tCenter = (TrialSite) (((UIComponent) event.getComponent()
				.getChildren().get(0)).getValueBinding("value")
				.getValue(FacesContext.getCurrentInstance()));
		trial.getParticipatingSites().remove(tCenter);

	}

	public void addArm(ActionEvent event) {
		TreatmentArm temp = new TreatmentArm();
		this.getArms().add(temp);
	}

	public void removeArm(ActionEvent event) {
		this.getArms().remove(this.getArms().size()-1);
	}

	// TEMP
	public Date getTDate1() {
		if (tDate1 == null)
			return (new GregorianCalendar()).getTime();
		return tDate1;
	}

	public void setTDate1(Date date1) {
		tDate1 = date1;
	}

	public Date getTDate2() {
		if (tDate2 == null)
			return (new GregorianCalendar()).getTime();
		return tDate2;
	}

	public void setTDate2(Date date2) {
		tDate2 = date2;
	}

	public TimeZone getZone() {
		if (zone == null) {
			zone = TimeZone.getDefault();
		}
		return zone;
	}

	public void setZone(TimeZone zone) {
		this.zone = zone;
	}

	public ArrayList<TreatmentArm> getArms() {
		if(arms == null)
			arms = new ArrayList<TreatmentArm>();
		return arms;
	}

	public void setArms(ArrayList<TreatmentArm> arms) {
		this.arms = arms;
	}

}
