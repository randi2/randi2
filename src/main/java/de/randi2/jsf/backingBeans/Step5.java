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
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;
import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.jsf.controllerBeans.TrialHandler;
import de.randi2.model.randomization.BiasedCoinRandomizationConfig;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import de.randi2.model.randomization.MinimizationConfig;
import de.randi2.model.randomization.TruncatedBinomialDesignConfig;
import de.randi2.model.randomization.UrnDesignConfig;

@ManagedBean(name = "step5")
@RequestScoped
public class Step5 {

	public static enum AlgorithmPanelId {
		COMPLETE_RANDOMIZATION("completeRandomization"), BIASEDCOIN_RANDOMIZATION(
				"biasedCoinRandomization"), BLOCK_RANDOMIZATION(
				"blockRandomization"), TRUNCATED_RANDOMIZATION(
				"truncatedRandomization"), URN_MODEL("urnModel"), MINIMIZATION(
				"minimization");
		private String id = null;

		private AlgorithmPanelId(String id) {
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

	private List<SelectItem> algorithms;

	public List<SelectItem> getAlgorithms() {
		if (algorithms == null) {
			ResourceBundle bundle = ResourceBundle.getBundle(
					"de.randi2.jsf.i18n.algorithms",
					loginHandler.getChosenLocale());
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

	@Getter
	private String selectedAlgorithmPanelId = "none";

	public void setSelectedAlgorithmPanelId(String selectedAlgorithmPanelId) {
		this.selectedAlgorithmPanelId = selectedAlgorithmPanelId;
		if (selectedAlgorithmPanelId
				.equals(AlgorithmPanelId.BLOCK_RANDOMIZATION.toString())
				&& !BlockRandomizationConfig.class.isInstance(trialHandler
						.getCurrentObject().getRandomizationConfiguration())) {
			trialHandler.getCurrentObject().setRandomizationConfiguration(
					new BlockRandomizationConfig());
		} else if (selectedAlgorithmPanelId.equals(AlgorithmPanelId.URN_MODEL
				.toString())
				&& !UrnDesignConfig.class.isInstance(trialHandler
						.getCurrentObject().getRandomizationConfiguration())) {
			trialHandler.getCurrentObject().setRandomizationConfiguration(
					new UrnDesignConfig());
		} else if (selectedAlgorithmPanelId
				.equals(AlgorithmPanelId.MINIMIZATION.toString())
				&& !MinimizationConfig.class.isInstance(trialHandler
						.getCurrentObject().getRandomizationConfiguration())) {
			trialHandler.getCurrentObject().setRandomizationConfiguration(
					new MinimizationConfig());
		}

	}

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
