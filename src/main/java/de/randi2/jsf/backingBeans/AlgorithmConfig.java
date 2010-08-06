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

public class AlgorithmConfig {

	public static enum AlgorithmPanelId {
		COMPLETE_RANDOMIZATION("completeRandomization"), BIASEDCOIN_RANDOMIZATION(
				"biasedCoinRandomization"), BLOCK_RANDOMIZATION(
				"blockRandomization"), TRUNCATED_RANDOMIZATION("truncatedRandomization"), 
				URN_MODEL("urnModel"), MINIMIZATION("minimization");
		private String id = null;

		private AlgorithmPanelId(String id) {
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

	private List<SelectItem> algorithms;

	public List<SelectItem> getAlgorithms() {
		if (algorithms == null) {
			ResourceBundle bundle = ResourceBundle.getBundle(
					"de.randi2.jsf.i18n.algorithms", loginHandler
							.getChosenLocale());
			algorithms = new ArrayList<SelectItem>();
			algorithms.add(new SelectItem(
					AlgorithmPanelId.COMPLETE_RANDOMIZATION.toString(), bundle
							.getString(CompleteRandomizationConfig.class
									.getCanonicalName()
									+ ".short")));
			algorithms.add(new SelectItem(
					AlgorithmPanelId.BIASEDCOIN_RANDOMIZATION.toString(),
					bundle.getString(BiasedCoinRandomizationConfig.class
							.getCanonicalName()
							+ ".short")));
			algorithms.add(new SelectItem(AlgorithmPanelId.TRUNCATED_RANDOMIZATION
					.toString(), bundle
					.getString(TruncatedBinomialDesignConfig.class
							.getCanonicalName()
							+ ".short")));
			algorithms.add(new SelectItem(AlgorithmPanelId.BLOCK_RANDOMIZATION
					.toString(), bundle
					.getString(BlockRandomizationConfig.class
							.getCanonicalName()
							+ ".short")));
			algorithms.add(new SelectItem(AlgorithmPanelId.URN_MODEL
					.toString(), bundle
					.getString(UrnDesignConfig.class
							.getCanonicalName()
							+ ".short")));
			algorithms.add(new SelectItem(AlgorithmPanelId.MINIMIZATION
					.toString(), bundle
					.getString(MinimizationConfig.class
							.getCanonicalName()
							+ ".short")));

		}
		return algorithms;
	}

	private String selectedAlgorithmPanelId = "none";
	
	public String getSelectedAlgorithmPanelId() {
		AbstractRandomizationConfig rand = trialHandler.getCurrentObject().getRandomizationConfiguration();
		if(rand!=null){
			if(rand instanceof CompleteRandomizationConfig)
				return AlgorithmPanelId.COMPLETE_RANDOMIZATION.toString();
			else if(rand instanceof BiasedCoinRandomizationConfig)
				return AlgorithmPanelId.BIASEDCOIN_RANDOMIZATION.toString();
			else if(rand instanceof TruncatedBinomialDesignConfig)
				return AlgorithmPanelId.TRUNCATED_RANDOMIZATION.toString();
			else if(rand instanceof BlockRandomizationConfig)
				return AlgorithmPanelId.BLOCK_RANDOMIZATION.toString();
			else if(rand instanceof UrnDesignConfig)
				return AlgorithmPanelId.URN_MODEL.toString();
			else if(rand instanceof MinimizationConfig)
				return AlgorithmPanelId.MINIMIZATION.toString();
		}
		return selectedAlgorithmPanelId;
	}

	public void algorithmChanged(ValueChangeEvent event){
		String newSelection = (String) event.getNewValue();
		if(newSelection.equals(selectedAlgorithmPanelId))
			return;
		else
			if(AlgorithmPanelId.COMPLETE_RANDOMIZATION.toString().equals(newSelection))
				trialHandler.getCurrentObject().setRandomizationConfiguration(new CompleteRandomizationConfig());
			else if(AlgorithmPanelId.BIASEDCOIN_RANDOMIZATION.toString().equals(newSelection))
				trialHandler.getCurrentObject().setRandomizationConfiguration(new BiasedCoinRandomizationConfig());
			else if(AlgorithmPanelId.TRUNCATED_RANDOMIZATION.toString().equals(newSelection))
				trialHandler.getCurrentObject().setRandomizationConfiguration(new TruncatedBinomialDesignConfig());
			else if(AlgorithmPanelId.BLOCK_RANDOMIZATION.toString().equals(newSelection))
				trialHandler.getCurrentObject().setRandomizationConfiguration(new BlockRandomizationConfig());
			else if(AlgorithmPanelId.URN_MODEL.toString().equals(newSelection))
				trialHandler.getCurrentObject().setRandomizationConfiguration(new UrnDesignConfig());
			else if(AlgorithmPanelId.MINIMIZATION.toString().equals(newSelection))
				trialHandler.getCurrentObject().setRandomizationConfiguration(new MinimizationConfig());
	}
	
	public void setSelectedAlgorithmPanelId(String selectedAlgorithmPanelId) {
		this.selectedAlgorithmPanelId = selectedAlgorithmPanelId;
	}
	
	public void clean(){
		selectedAlgorithmPanelId = "none";
		((BlockR)FacesContext.getCurrentInstance()
				.getApplication().getExpressionFactory().createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{blockR}", BlockR.class).getValue(FacesContext.getCurrentInstance()
								.getELContext())).clean();
		((Strata)FacesContext.getCurrentInstance()
		.getApplication().getExpressionFactory().createValueExpression(
				FacesContext.getCurrentInstance().getELContext(),
				"#{strata}", Strata.class).getValue(FacesContext.getCurrentInstance()
						.getELContext())).clean();
	}
}
