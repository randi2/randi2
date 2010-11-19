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

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import lombok.Setter;
import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.jsf.controllerBeans.TrialHandler;
import de.randi2.model.randomization.AbstractRandomizationConfig;
import de.randi2.model.randomization.BiasedCoinRandomizationConfig;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import de.randi2.model.randomization.MinimizationConfig;
import de.randi2.model.randomization.TruncatedBinomialDesignConfig;
import de.randi2.model.randomization.UrnDesignConfig;

/**
 * Simple backing bean for the randomization algorithm configuration process.
 * (Corresponding JSF page algorihtmConfig.xhtml)
 * 
 * @author L. Plotnicki <l.plotnicki@dkfz.de>
 * 
 */
public class AlgorithmConfig {

	/**
	 * Identifies the correct panels for the algorithms
	 * 
	 * @author L. Plotnicki <l.plotnicki@dkfz.de>
	 * 
	 */
	public static enum AlgorithmPanelId {
		COMPLETE_RANDOMIZATION, BIASEDCOIN_RANDOMIZATION, BLOCK_RANDOMIZATION, TRUNCATED_RANDOMIZATION, URN_MODEL, MINIMIZATION;
	}

	/**
	 * Current algorithm panel
	 */
	private String selectedAlgorithmPanelId = "none";

	@Setter
	private LoginHandler loginHandler;

	@Setter
	private TrialHandler trialHandler;

	private List<SelectItem> algorithms;

	public List<SelectItem> getAlgorithms() {
		if (algorithms == null) {
			ResourceBundle bundle = ResourceBundle.getBundle(
					"de.randi2.jsf.i18n.algorithms",
					loginHandler.getChosenLocale());
			/*
			 * Constructing the items for the algorithm selection widget ITEM =
			 * ALgorithmPanelID + localized algorithm name
			 */
			algorithms = new ArrayList<SelectItem>();
			algorithms.add(new SelectItem(
					AlgorithmPanelId.COMPLETE_RANDOMIZATION.toString(), bundle
							.getString(CompleteRandomizationConfig.class
									.getCanonicalName() + ".short")));
			algorithms.add(new SelectItem(
					AlgorithmPanelId.BIASEDCOIN_RANDOMIZATION.toString(),
					bundle.getString(BiasedCoinRandomizationConfig.class
							.getCanonicalName() + ".short")));
			algorithms.add(new SelectItem(
					AlgorithmPanelId.TRUNCATED_RANDOMIZATION.toString(), bundle
							.getString(TruncatedBinomialDesignConfig.class
									.getCanonicalName() + ".short")));
			algorithms.add(new SelectItem(AlgorithmPanelId.BLOCK_RANDOMIZATION
					.toString(), bundle
					.getString(BlockRandomizationConfig.class
							.getCanonicalName() + ".short")));
			algorithms.add(new SelectItem(
					AlgorithmPanelId.URN_MODEL.toString(), bundle
							.getString(UrnDesignConfig.class.getCanonicalName()
									+ ".short")));
			algorithms.add(new SelectItem(AlgorithmPanelId.MINIMIZATION
					.toString(), bundle.getString(MinimizationConfig.class
					.getCanonicalName() + ".short")));

		}
		return algorithms;
	}

	public String getSelectedAlgorithmPanelId() {
		AbstractRandomizationConfig rand = trialHandler.getCurrentObject()
				.getRandomizationConfiguration();
		if (rand != null) {
			/**
			 * If algorithm is already configured (present in the trial object)
			 * - a proper algorithm panel needs to be rendered
			 */
			if (rand instanceof CompleteRandomizationConfig)
				return AlgorithmPanelId.COMPLETE_RANDOMIZATION.toString();
			else if (rand instanceof BiasedCoinRandomizationConfig)
				return AlgorithmPanelId.BIASEDCOIN_RANDOMIZATION.toString();
			else if (rand instanceof TruncatedBinomialDesignConfig)
				return AlgorithmPanelId.TRUNCATED_RANDOMIZATION.toString();
			else if (rand instanceof BlockRandomizationConfig)
				return AlgorithmPanelId.BLOCK_RANDOMIZATION.toString();
			else if (rand instanceof UrnDesignConfig)
				return AlgorithmPanelId.URN_MODEL.toString();
			else if (rand instanceof MinimizationConfig)
				return AlgorithmPanelId.MINIMIZATION.toString();
		}
		return selectedAlgorithmPanelId;
	}

	/**
	 * Change Listener for the algorithm selection box. It tries to identify the
	 * selected algorithm and if successful creates a corresponding object
	 * relating to the current trial in {@link TrialHandler}
	 * 
	 * @param event
	 */
	public void algorithmChanged(ValueChangeEvent event) {
		String newSelection = (String) event.getNewValue();
		/*
		 * If the same algorithm as the current has been selected - ignore the
		 * event
		 */
		if (newSelection.equals(selectedAlgorithmPanelId))
			return;
		/*
		 * Otherwise construct a proper config object and set it on the current
		 * trial object
		 */
		AbstractRandomizationConfig newConfig = null;
		switch (AlgorithmPanelId.valueOf(newSelection)) {
		case COMPLETE_RANDOMIZATION:
			newConfig = new CompleteRandomizationConfig();
			break;
		case BIASEDCOIN_RANDOMIZATION:
			newConfig = new BiasedCoinRandomizationConfig();
			break;
		case BLOCK_RANDOMIZATION:
			newConfig = new BlockRandomizationConfig();
			break;
		case MINIMIZATION:
			newConfig = new MinimizationConfig();
			break;
		case TRUNCATED_RANDOMIZATION:
			newConfig = new TruncatedBinomialDesignConfig();
			break;
		case URN_MODEL:
			newConfig = new UrnDesignConfig();
			break;
		}
		trialHandler.getCurrentObject()
				.setRandomizationConfiguration(newConfig);
	}

	public void setSelectedAlgorithmPanelId(String selectedAlgorithmPanelId) {
		this.selectedAlgorithmPanelId = selectedAlgorithmPanelId;
	}

	/**
	 * If called this method resets the selected panel and data stored during
	 * the config process
	 */
	public void clean() {
		selectedAlgorithmPanelId = "none";
		((BlockR) FacesContext
				.getCurrentInstance()
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{blockR}", BlockR.class)
				.getValue(FacesContext.getCurrentInstance().getELContext()))
				.clean();
		((Strata) FacesContext
				.getCurrentInstance()
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{strata}", Strata.class)
				.getValue(FacesContext.getCurrentInstance().getELContext()))
				.clean();
	}
}
