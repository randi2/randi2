/* 
 * (c) 2008- RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
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
package de.randi2.jsf.backingBeans;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.icesoft.faces.component.ext.HtmlInputText;

import lombok.Getter;
import lombok.Setter;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.jsf.controllerBeans.SimulationHandler;
import de.randi2.jsf.controllerBeans.TrialHandler;
import de.randi2.model.randomization.BlockRandomizationConfig;

@ManagedBean(name = "blockR")
@RequestScoped
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

	@ManagedProperty(value = "#{loginHandler}")
	@Setter
	private LoginHandler loginHandler;
	@ManagedProperty(value = "#{trialHandler}")
	@Setter
	private TrialHandler trialHandler;

	private List<SelectItem> blockRandTypes;

	public List<SelectItem> getBlockRandTypes() {
		if (blockRandTypes == null) {
			ResourceBundle bundle = ResourceBundle.getBundle(
					"de.randi2.jsf.i18n.algorithms",
					loginHandler.getChosenLocale());
			blockRandTypes = new ArrayList<SelectItem>();
			blockRandTypes.add(new SelectItem(BlockDesignTypeId.CONSTANT_BOLCK
					.toString(), bundle
					.getString(BlockRandomizationConfig.class
							.getCanonicalName() + ".constantBlockSize")));
			blockRandTypes.add(new SelectItem(BlockDesignTypeId.VARIABLE_BLOCK
					.toString(), bundle
					.getString(BlockRandomizationConfig.class
							.getCanonicalName() + ".variableBlockSize")));

		}
		return blockRandTypes;
	}

	/**
	 * The following property is a virtual property used only for the UI. It
	 * don't need to be pushed to the config object
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

	@Setter
	private int possitionForSimulation;

	public void minValueChanged(ValueChangeEvent event) {
		// set maximum block size to minimum block size, in case of constant
		// block design
		if (!renderVariable) {
			if (HtmlInputText.class.cast(event.getSource()).getId()
					.equals("blocksize")) {
				((BlockRandomizationConfig) trialHandler.getCurrentObject().getRandomizationConfiguration()).setMaximum((Integer) event
						.getNewValue());
			} else {

				SimulationHandler simulationHandler = ((SimulationHandler) FacesContext
						.getCurrentInstance()
						.getApplication()
						.getELResolver()
						.getValue(
								FacesContext.getCurrentInstance()
										.getELContext(), null,
								"simulationHandler"));
				((BlockRandomizationConfig) simulationHandler
						.getRandomisationConfigs().get(possitionForSimulation)
						.getConf()).setMaximum((Integer) event.getNewValue());
			}
		}
	}

	public void clean() {
		renderVariable = false;
		selectedBlockRandTypes = null;
	}

}
