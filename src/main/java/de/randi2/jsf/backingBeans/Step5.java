package de.randi2.jsf.backingBeans;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.model.SelectItem;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.jsf.controllerBeans.TrialHandler;
import de.randi2.model.randomization.BiasedCoinRandomizationConfig;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.randomization.CompleteRandomizationConfig;

public class Step5 {

	public static enum AlgorithmPanelId {
		COMPLETE_RANDOMIZATION("completeRandomization"), BIASEDCOIN_RANDOMIZATION(
				"biasedCoinRandomization"), BLOCK_RANDOMIZATION(
				"blockRandomization");
		private String id = null;

		private AlgorithmPanelId(String id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return this.id;
		}

	}

	private LoginHandler loginHandler;

	public void setLoginHandler(LoginHandler loginHandler) {
		this.loginHandler = loginHandler;
	}

	private TrialHandler trialHandler;

	public void setTrialHandler(TrialHandler trialHandler) {
		this.trialHandler = trialHandler;
	}

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
			algorithms.add(new SelectItem(AlgorithmPanelId.BLOCK_RANDOMIZATION
					.toString(), bundle
					.getString(BlockRandomizationConfig.class
							.getCanonicalName()
							+ ".short")));

		}
		return algorithms;
	}

	private String selectedAlgorithmPanelId = "none";

	public void setSelectedAlgorithmPanelId(String selectedAlgorithmPanelId) {
		this.selectedAlgorithmPanelId = selectedAlgorithmPanelId;
		if (selectedAlgorithmPanelId
				.equals(AlgorithmPanelId.BLOCK_RANDOMIZATION.toString())
				&& !BlockRandomizationConfig.class.isInstance(trialHandler
						.getRandomizationConfig())) {
			trialHandler.setRandomizationConfig(new BlockRandomizationConfig());
		}
	}

	public String getSelectedAlgorithmPanelId() {
		return selectedAlgorithmPanelId;
	}
}
