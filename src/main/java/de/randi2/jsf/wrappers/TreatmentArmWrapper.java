package de.randi2.jsf.wrappers;

import javax.faces.event.ActionEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import de.randi2.jsf.controllerBeans.AbstractTrialHandler;
import de.randi2.model.TreatmentArm;

@Data
@EqualsAndHashCode(exclude={"arm", "handler"})
public class TreatmentArmWrapper {

	
	private int id = 0;
	
	private TreatmentArm arm;
	
	private final AbstractTrialHandler handler;
	

	public TreatmentArmWrapper(AbstractTrialHandler handler) {
		this.handler = handler;
	}
	
	public TreatmentArmWrapper(AbstractTrialHandler handler, TreatmentArm arm) {
		this(handler);
		this.arm = arm;		
	}
	
	/**
	 * Action listener for removing an existing treatment arm.
	 * 
	 * @param event
	 */
	public void removeArm(ActionEvent event) {
		handler.getListArmsWrapper().remove(this);
	}
}
