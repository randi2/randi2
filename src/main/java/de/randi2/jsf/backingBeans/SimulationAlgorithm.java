package de.randi2.jsf.backingBeans;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;
import de.randi2.jsf.backingBeans.AlgorithmConfig.AlgorithmPanelId;
import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.jsf.controllerBeans.TrialHandler;
import de.randi2.model.randomization.AbstractRandomizationConfig;
import de.randi2.model.randomization.BiasedCoinRandomizationConfig;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import de.randi2.model.randomization.MinimizationConfig;
import de.randi2.model.randomization.TruncatedBinomialDesignConfig;
import de.randi2.model.randomization.UrnDesignConfig;

@ManagedBean(name = "simulationAlgorithm")
@SessionScoped
public class SimulationAlgorithm {

	@ManagedProperty(value = "#{loginHandler}")
	@Setter
	private LoginHandler loginHandler;

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
	private String selectedAlgorithmPanelId = AlgorithmPanelId.COMPLETE_RANDOMIZATION
			.toString();

	public void setSelectedAlgorithmPanelId(String selectedAlgorithmPanelId) {
		this.selectedAlgorithmPanelId = selectedAlgorithmPanelId;
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
	
	/**
	 * Change Listener for the algorithm selection box. It tries to identify the
	 * selected algorithm
	 * 
	 * @param event
	 */
	public void algorithmChanged(ValueChangeEvent event) {
		String newSelection = (String) event.getNewValue();
		selectedAlgorithmPanelId = newSelection;
	}
}
