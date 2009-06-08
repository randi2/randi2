package de.randi2.jsf.backingBeans;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.icesoft.faces.component.ext.HtmlPanelGrid;
import com.icesoft.faces.context.ByteArrayResource;
import com.icesoft.faces.context.FileResource;
import com.icesoft.faces.context.Resource;

import de.randi2.jsf.controllerBeans.TrialHandler;
import de.randi2.jsf.wrappers.CriterionWrapper;
import de.randi2.model.Trial;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;

public class TSubjectAdd {

	private Trial currentTrial = null;

	public Trial getCurrentTrial() {
		return currentTrial;
	}

	public void setCurrentTrial(Trial currentTrial) {
		this.currentTrial = currentTrial;
		properties = new ArrayList<CriterionWrapper>();
		CriterionWrapper cWrapper = null;
		for (AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> c : currentTrial
				.getCriteria()) {
			cWrapper = new CriterionWrapper(c);
			properties.add(cWrapper);
		}
	}

	private ArrayList<CriterionWrapper> properties = null;
	
	public ArrayList<CriterionWrapper> getProperties() {
		return properties;
	}

	public Resource getTempProtocol() {
		if (currentTrial != null && currentTrial.getProtocol() != null)
			return new FileResource(currentTrial.getProtocol());
		else
			try {
				return new ByteArrayResource(TrialHandler
						.toByteArray(FacesContext.getCurrentInstance()
								.getExternalContext().getResourceAsStream(
										"/protocol.pdf")));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}
}
