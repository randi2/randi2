package de.randi2.jsf.backingBeans;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.jsf.controllerBeans.TrialHandler;
import de.randi2.model.randomization.BlockRandomizationConfig;

public class BlockR {

	public static enum BlockDesignTypeId {
		VARIABLE_BLOCK("variableBlockSize"), CONSTANT_BOLCK("constantBlockSize");

		private String id = null;

		private BlockDesignTypeId(String id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return this.id;
		}
	}

	@Setter
	private LoginHandler loginHandler;

	@Setter
	private TrialHandler trialHandler;

	private List<SelectItem> blockRandTypes;

	public List<SelectItem> getBlockRandTypes() {
		if (blockRandTypes == null) {
			ResourceBundle bundle = ResourceBundle.getBundle(
					"de.randi2.jsf.i18n.algorithms", loginHandler
							.getChosenLocale());
			blockRandTypes = new ArrayList<SelectItem>();
			blockRandTypes.add(new SelectItem(BlockDesignTypeId.CONSTANT_BOLCK
					.toString(), bundle
					.getString(BlockRandomizationConfig.class
							.getCanonicalName()
							+ ".constantBlockSize")));
			blockRandTypes.add(new SelectItem(BlockDesignTypeId.VARIABLE_BLOCK
					.toString(), bundle
					.getString(BlockRandomizationConfig.class
							.getCanonicalName()
							+ ".variableBlockSize")));

		}
		return blockRandTypes;
	}

	/**
	 * The following property is a virtual property used only for the UI. It don't need to be pushed to the config object
	 */
	@Getter
	private String selectedBlockRandTypes;

	@Getter
	private boolean renderVariable = false;

	public void setSelectedBlockRandTypes(String selectedBlockRandTypes) {
		this.selectedBlockRandTypes = selectedBlockRandTypes;
		if (selectedBlockRandTypes.equals(BlockDesignTypeId.VARIABLE_BLOCK
				.toString())) {
			renderVariable = true;

		} else
			renderVariable = false;
	}


	public void minValueChanged(ValueChangeEvent event) {
		// set maximum block size to minimum block size, in case of constant
		// block design
		if (!renderVariable) {
			((BlockRandomizationConfig) trialHandler.getRandomizationConfig())
					.setMaximum((Integer) event.getNewValue());
		}
	}
	
	public void clean(){
		renderVariable = false;
		selectedBlockRandTypes = null;
	}

}
