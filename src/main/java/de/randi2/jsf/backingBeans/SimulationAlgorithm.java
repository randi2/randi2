package de.randi2.jsf.backingBeans;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;
import de.randi2.jsf.backingBeans.AlgorithmConfig.AlgorithmPanelId;
import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.jsf.controllerBeans.SimulationHandler;
import de.randi2.jsf.controllerBeans.TrialHandler;
import de.randi2.model.randomization.BiasedCoinRandomizationConfig;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import de.randi2.model.randomization.MinimizationConfig;
import de.randi2.model.randomization.TruncatedBinomialDesignConfig;
import de.randi2.model.randomization.UrnDesignConfig;

public class SimulationAlgorithm {

	
	@Setter
	private LoginHandler loginHandler;

	@Setter
	private SimulationHandler simulationHandler;

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

	@Getter
	private String selectedAlgorithmPanelId = AlgorithmPanelId.COMPLETE_RANDOMIZATION.toString();

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
