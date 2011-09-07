package de.randi2.jsf.wrappers;

import javax.faces.event.ActionEvent;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import de.randi2.jsf.backingBeans.BlockR;
import de.randi2.jsf.controllerBeans.SimulationHandler;
import de.randi2.jsf.utility.JSFViewUtitlity;
import de.randi2.model.randomization.AbstractRandomizationConfig;
import de.randi2.model.randomization.BiasedCoinRandomizationConfig;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import de.randi2.model.randomization.MinimizationConfig;
import de.randi2.model.randomization.TruncatedBinomialDesignConfig;
import de.randi2.model.randomization.UrnDesignConfig;

@EqualsAndHashCode(of={"id"})
public class AlgorithmWrapper {

	private final static String COMPANEL = "compPanel";
	private final static String BIASPANEL = "biasPanel";
	private final static String URNPANEL = "urnPanel";
	private final static String BLOCKPANEL = "blockPanel";
	private final static String TRUNCPANEL = "truncPanel";
	private final static String MINIPANEL = "miniPanel";

	@Getter
	@Setter
	private int possition;

	@Getter
	private final int id;
	
	@Getter
	@Setter
	private BlockR blockR = new BlockR();

	@Getter
	@Setter
	private AbstractRandomizationConfig conf;

	@Getter
	@Setter
	private String description;

	private SimulationHandler handler;


	public AlgorithmWrapper(AbstractRandomizationConfig config,
			SimulationHandler handler, int id) {
		this.conf = config;
		this.handler = handler;
		this.id = id;
	}

	/**
	 * 
	 * String ID defining the showed algorithm panel.
	 */
	private String panelType = "AlgorithmErrorPanel";

	/**
	 * Returns the String ID of an panel which need
	 * 
	 * @return
	 */
	public String getPanelType() {
		if (CompleteRandomizationConfig.class.isInstance(conf))
			panelType = COMPANEL;
		else if (BiasedCoinRandomizationConfig.class.isInstance(conf))
			panelType = BIASPANEL;
		else if (UrnDesignConfig.class.isInstance(conf))
			panelType = URNPANEL;
		else if (BlockRandomizationConfig.class.isInstance(conf))
			panelType = BLOCKPANEL;
		else if (TruncatedBinomialDesignConfig.class.isInstance(conf))
			panelType = TRUNCPANEL;
		else if (MinimizationConfig.class.isInstance(conf))
			panelType = MINIPANEL;
		return panelType;
	}

	public String getPossitionString() {
		return Integer.toString(possition);
	}
	
	
	/**
	 * Action listener for removing an algorithm.
	 * 
	 * @param event
	 */
	public void removeAlgorithm(ActionEvent event) {
		handler.getRandomisationConfigs().remove(this);
		handler.renameAlgorithmPossitions();
		JSFViewUtitlity.refreshJSFPage();
		handler.setSimulationConfigurationTabIndex(2);

	}

}
