package de.randi2.jsf.controllerBeans;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import lombok.Getter;
import lombok.Setter;
import de.randi2.jsf.backingBeans.SimulationAlgorithm;
import de.randi2.jsf.backingBeans.SimulationSubjectProperty;
import de.randi2.jsf.backingBeans.Step4;
import de.randi2.jsf.backingBeans.Step5;
import de.randi2.jsf.supportBeans.Popups;
import de.randi2.jsf.supportBeans.Randi2;
import de.randi2.jsf.wrappers.AlgorithmWrapper;
import de.randi2.jsf.wrappers.ConstraintWrapper;
import de.randi2.jsf.wrappers.CriterionWrapper;
import de.randi2.jsf.wrappers.DistributedConstraintWrapper;
import de.randi2.jsf.wrappers.DistributedCriterionWrapper;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.FreeTextCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.model.randomization.AbstractRandomizationConfig;
import de.randi2.model.randomization.BiasedCoinRandomizationConfig;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import de.randi2.model.randomization.MinimizationConfig;
import de.randi2.model.randomization.TruncatedBinomialDesignConfig;
import de.randi2.model.randomization.UrnDesignConfig;
import de.randi2.simulation.distribution.UniformDistribution;
import de.randi2.simulation.model.DistributionSubjectProperty;
import de.randi2.simulation.model.SimulationResult;
import de.randi2.simulation.service.SimulationService;
import de.randi2.unsorted.ContraintViolatedException;
import de.randi2.utility.ReflectionUtil;

public class SimulationHandler {

	@Getter
	@Setter
	private TrialHandler trialHandler;

	@Getter
	@Setter
	private LoginHandler loginHandler;

	@Getter
	@Setter
	private SimulationService simulationService;

	private AbstractRandomizationConfig randomizationConfig;

	@Getter
	@Setter
	private int runs = 1000;

	@Getter
	@Setter
	private long maxTime;

	@Setter
	private Trial simTrial;

	@Getter
	private SimulationResult simResult;

	@Getter
	private int countTrialSites;
	
	/*
	 * Access to the application popups.
	 */

	@Setter
	private Popups popups;
	
	@Setter
	private boolean changedCriterion;
	
	public void criterionChanged() {
		System.out.println("asdgs");
		changedCriterion = true;
	}
	
	public boolean isChangedCriterion() {
		changedCriterion = true;
		return changedCriterion;
	}

	@Getter
	private List<AlgorithmWrapper> randomisationConfigs = new ArrayList<AlgorithmWrapper>();

	@Getter 
	@Setter
	private List<SimulationResult> simulationResults = new ArrayList<SimulationResult>();

	@Getter
	@Setter
	private boolean simOnly;

	private ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> criteriaList = null;

	private List<DistributedCriterionWrapper<Serializable, AbstractConstraint<Serializable>>> distributedCriterions;

	public SimulationHandler() {
		criteriaList = new ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>>();
		try {
			/*
			 * Checking which subject properites are supported.
			 */
			for (Class<?> c : ReflectionUtil
					.getClasses("de.randi2.model.criteria")) {
				try {
					if (c.getSuperclass().equals(AbstractCriterion.class))
						criteriaList
								.add((AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>) c
										.getConstructor().newInstance());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void setDistributedCriterions(
			List<DistributedCriterionWrapper<Serializable, AbstractConstraint<Serializable>>> distributedCriterions) {
		this.distributedCriterions = distributedCriterions;
	}

	public List<DistributedCriterionWrapper<Serializable, AbstractConstraint<Serializable>>> getDistributedCriterions() {
		//if (distributedCriterions == null && distributedCriterions.size()!= criteriaList.size()) {
			distributedCriterions = new ArrayList<DistributedCriterionWrapper<Serializable, AbstractConstraint<Serializable>>>();
			for (AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> c : simTrial
					.getCriteria()) {
				List<DistributedConstraintWrapper> strataDistributions = new ArrayList<DistributedConstraintWrapper>();
				for (AbstractConstraint<? extends Serializable> con : c
						.getStrata()) {
					strataDistributions.add(new DistributedConstraintWrapper(
							con));
				}
				distributedCriterions.add(new DistributedCriterionWrapper(
						strataDistributions,
						new CriterionWrapper<Serializable>(
								(AbstractCriterion<Serializable, ?>) c)));
			}
//		}
		return distributedCriterions;
	}

	public Trial getSimTrial() {
		if (simTrial == null && !simOnly) {
			simTrial = trialHandler.getShowedObject();
			randomizationConfig = trialHandler.getRandomizationConfig();
			try {
				/* Leading Trial Site & Sponsor Investigator */
				simTrial.setLeadingSite(trialHandler.getTrialSitesAC()
						.getSelectedObject());
				if (trialHandler.getSponsorInvestigatorsAC()
						.getSelectedObject() != null)
					simTrial.setSponsorInvestigator(trialHandler
							.getSponsorInvestigatorsAC().getSelectedObject()
							.getPerson());

				/* SubjectProperties Configuration - done in Step4 */
				ValueExpression ve1 = FacesContext.getCurrentInstance()
						.getApplication().getExpressionFactory()
						.createValueExpression(
								FacesContext.getCurrentInstance()
										.getELContext(), "#{step4}",
								Step4.class);
				Step4 currentStep4 = (Step4) ve1.getValue(FacesContext
						.getCurrentInstance().getELContext());
				ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> configuredCriteria = new ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>>();
				for (CriterionWrapper<? extends Serializable> cr : currentStep4
						.getCriteria()) {
					/* Strata configuration - done in Step5 */
					if (cr.isStrataFactor()) {
						if (DichotomousCriterion.class.isInstance(cr
								.getWrappedCriterion())) {
							DichotomousCriterion temp = DichotomousCriterion.class
									.cast(cr.getWrappedCriterion());
							try {
								temp.addStrata(new DichotomousConstraint(
										Arrays
												.asList(new String[] { temp
														.getConfiguredValues()
														.get(0) })));
								temp.addStrata(new DichotomousConstraint(
										Arrays
												.asList(new String[] { temp
														.getConfiguredValues()
														.get(1) })));
							} catch (ContraintViolatedException e) {
								e.printStackTrace();
							}
						} else {
							for (ConstraintWrapper<?> cw : cr.getStrata()) {
								cr.getWrappedCriterion().addStrata(
										cw.configure());
							}
						}
					}
					/* End of strata configuration */
					configuredCriteria
							.add((AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>) cr
									.getWrappedCriterion());
				}
				simTrial.setCriteria(configuredCriteria);
				/* End of SubjectProperites Configuration */

				/* Algorithm Configuration */
				ValueExpression ve2 = FacesContext.getCurrentInstance()
						.getApplication().getExpressionFactory()
						.createValueExpression(
								FacesContext.getCurrentInstance()
										.getELContext(), "#{step5}",
								Step5.class);
				Step5 currentStep5 = (Step5) ve2.getValue(FacesContext
						.getCurrentInstance().getELContext());
				if (currentStep5.getSelectedAlgorithmPanelId().equals(
						Step5.AlgorithmPanelId.COMPLETE_RANDOMIZATION
								.toString())) {
					simTrial
							.setRandomizationConfiguration(new CompleteRandomizationConfig());
				} else if (currentStep5.getSelectedAlgorithmPanelId().equals(
						Step5.AlgorithmPanelId.BIASEDCOIN_RANDOMIZATION
								.toString())) {
					simTrial
							.setRandomizationConfiguration(new BiasedCoinRandomizationConfig());
				} else if (currentStep5.getSelectedAlgorithmPanelId().equals(
						Step5.AlgorithmPanelId.BLOCK_RANDOMIZATION.toString())) {
					simTrial.setRandomizationConfiguration(randomizationConfig);
				} else if (currentStep5.getSelectedAlgorithmPanelId().equals(
						Step5.AlgorithmPanelId.TRUNCATED_RANDOMIZATION
								.toString())) {
					simTrial
							.setRandomizationConfiguration(new TruncatedBinomialDesignConfig());
				} else if (currentStep5.getSelectedAlgorithmPanelId().equals(
						Step5.AlgorithmPanelId.URN_MODEL.toString())) {
					simTrial.setRandomizationConfiguration(randomizationConfig);
				} else if (currentStep5.getSelectedAlgorithmPanelId().equals(
						Step5.AlgorithmPanelId.MINIMIZATION.toString())) {
					simTrial.setRandomizationConfiguration(randomizationConfig);
				}
			} catch (Exception e) {
				return null;
			}
		} else if (simTrial == null && simOnly) {
			simTrial = new Trial();
			List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
			arms.add(new TreatmentArm());
			arms.add(new TreatmentArm());
			simTrial.setTreatmentArms(arms);
		
			/* End of SubjectProperites Configuration */

		}
		if(simOnly){
			/* SubjectProperties Configuration - done in Step4 */
			ValueExpression ve1 = FacesContext.getCurrentInstance()
					.getApplication().getExpressionFactory()
					.createValueExpression(
							FacesContext.getCurrentInstance().getELContext(),
							"#{simulationSubjectProperty}",
							SimulationSubjectProperty.class);
			SimulationSubjectProperty currentSimulationSubjectProperty = (SimulationSubjectProperty) ve1
					.getValue(FacesContext.getCurrentInstance().getELContext());
			ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> configuredCriteria = new ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>>();
			for (CriterionWrapper<? extends Serializable> cr : currentSimulationSubjectProperty
					.getCriteria()) {
				/* Strata configuration - done in Step5 */
				if (cr.isStrataFactor()) {
					if (DichotomousCriterion.class.isInstance(cr
							.getWrappedCriterion())) {
						DichotomousCriterion temp = DichotomousCriterion.class
								.cast(cr.getWrappedCriterion());
						try {
							temp.addStrata(new DichotomousConstraint(Arrays
									.asList(new String[] { temp
											.getConfiguredValues().get(0) })));
							temp.addStrata(new DichotomousConstraint(Arrays
									.asList(new String[] { temp
											.getConfiguredValues().get(1) })));
						} catch (ContraintViolatedException e) {
							e.printStackTrace();
						}
					} else {
						for (ConstraintWrapper<?> cw : cr.getStrata()) {
							cr.getWrappedCriterion().addStrata(cw.configure());
						}
					}
				}
				/* End of strata configuration */
				configuredCriteria
						.add((AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>) cr
								.getWrappedCriterion());
			}
			simTrial.setCriteria(configuredCriteria);
		}

		return simTrial;

	}

	private List<CriterionWrapper<Serializable>> strata;

	@SuppressWarnings("unchecked")
	public List<CriterionWrapper<Serializable>> getStrata() {
		strata = new ArrayList<CriterionWrapper<Serializable>>();
		for (AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> c : simTrial
				.getCriteria()) {
			strata.add(new CriterionWrapper<Serializable>(
					(AbstractCriterion<Serializable, ?>) c));
		}
		return strata;
	}

	/**
	 * Checks if any strata factors are defined.
	 * 
	 * @return
	 */
	public boolean isStrataFactorsDefined() {

		for (AbstractCriterion<?, ?> c : simTrial.getCriteria()) {
			if (c.getStrata() != null) {
				if (c.getStrata().size() > 0)
					return true;
			}
		}
		return false;
	}

	public String getAlgName() {
		ResourceBundle bundle = ResourceBundle
				.getBundle("de.randi2.jsf.i18n.algorithms", loginHandler
						.getChosenLocale());
		return bundle.getString(simTrial.getRandomizationConfiguration()
				.getClass().getCanonicalName()
				+ ".name");
	}

	public String getFurtherDetails() {
		StringBuffer furtherDetails = new StringBuffer();
		ResourceBundle bundle = ResourceBundle.getBundle(
				"de.randi2.jsf.i18n.labels", loginHandler.getChosenLocale());
		if (BlockRandomizationConfig.class.isInstance(simTrial
				.getRandomizationConfiguration())) {
			BlockRandomizationConfig conf = BlockRandomizationConfig.class
					.cast(simTrial.getRandomizationConfiguration());
			furtherDetails.append("<b>");
			furtherDetails.append(bundle
					.getString("pages.blockR.variableBSize"));
			furtherDetails.append("</b> ");
			furtherDetails.append(conf.isVariableBlockSize());
			furtherDetails.append("<br//>");
			if (conf.isVariableBlockSize()) {
				furtherDetails.append("<b>");
				furtherDetails.append(bundle
						.getString("pages.blockR.minBlockSize"));
				furtherDetails.append("</b> ");
				furtherDetails.append(conf.getMinimum());
				furtherDetails.append("<br//>");
				furtherDetails.append("<b>");
				furtherDetails.append(bundle
						.getString("pages.blockR.maxBlockSize"));
				furtherDetails.append("</b> ");
				furtherDetails.append(conf.getMaximum());
				furtherDetails.append("<br//>");
			} else {
				furtherDetails.append("<b>");
				furtherDetails.append(bundle
						.getString("pages.blockR.blockSize"));
				furtherDetails.append("</b> ");
				furtherDetails.append(conf.getMinimum());
				furtherDetails.append("<br//>");
			}
		} else if (UrnDesignConfig.class.isInstance(simTrial
				.getRandomizationConfiguration())) {
			UrnDesignConfig conf = UrnDesignConfig.class.cast(simTrial
					.getRandomizationConfiguration());
			furtherDetails.append("<b>");
			furtherDetails.append(bundle.getString("pages.urnR.initialCount"));
			furtherDetails.append("</b> ");
			furtherDetails.append(conf.getInitializeCountBalls());
			furtherDetails.append("<br//>");
			furtherDetails.append("<b>");
			furtherDetails.append(bundle.getString("pages.urnR.replacedBalls"));
			furtherDetails.append("</b> ");
			furtherDetails.append(conf.getCountReplacedBalls());
			furtherDetails.append("<br//>");
		} else if (MinimizationConfig.class.isInstance(simTrial
				.getRandomizationConfiguration())) {
			MinimizationConfig conf = MinimizationConfig.class.cast(simTrial
					.getRandomizationConfiguration());
			furtherDetails.append("<b>");
			furtherDetails
					.append(bundle.getString("pages.minimization.pvalue"));
			furtherDetails.append("</b> ");
			furtherDetails.append(conf.getP());
			furtherDetails.append("<br//>");
		} else {
			furtherDetails.append("--");
		}
		return furtherDetails.toString();
	}

	/**
	 * Specifies if the algorithm is stratified or not.
	 * 
	 * @return
	 */
	public boolean isStratified() {
		boolean t = isStrataFactorsDefined();
		if (t)
			return t;
		else
			return simTrial.isStratifyTrialSite();
	}

	public String simTrial() {
		List<DistributionSubjectProperty> properties = new ArrayList<DistributionSubjectProperty>();
		if (distributedCriterions != null) {
			for (DistributedCriterionWrapper<Serializable, AbstractConstraint<Serializable>> dcw : distributedCriterions) {
				properties.add(dcw.getDistributionSubjectProperty());
			}
		}
		List<TrialSite> sites = new ArrayList<TrialSite>(simTrial
				.getParticipatingSites());
		UniformDistribution<TrialSite> trialSiteDistribution = new UniformDistribution<TrialSite>(
				sites);

		if (simOnly) {
			simulationResults = new ArrayList<SimulationResult>();
			for(AlgorithmWrapper alg : randomisationConfigs){
			simTrial.setRandomizationConfiguration(alg.getConf());
			SimulationResult result = simulationService.simulateTrial(simTrial,
					properties, trialSiteDistribution, runs, maxTime);
			result.getMarginalBalanceMax();
				simulationResults.add(result);
			}
			
		} else {
			SimulationResult result = simulationService.simulateTrial(simTrial,
					properties, trialSiteDistribution, runs, maxTime);
			simResult = result;
		}
		popups.showSimulationCompletePopup();
		return Randi2.SUCCESS;
	}

	public boolean isResultComplete() {
		return simResult != null;
	}

	public boolean isResultsComplete() {
		return (simulationResults != null && !simulationResults.isEmpty());
	}

	/**
	 * Action listener for adding a new treatment arm.
	 * 
	 * @param event
	 */
	public void addArm(ActionEvent event) {
		assert (simTrial != null);
		TreatmentArm temp = new TreatmentArm();
		simTrial.getTreatmentArms().add(temp);
	}

	/**
	 * Action listener for removing an existing treatment arm.
	 * 
	 * @param event
	 */
	public void removeArm(ActionEvent event) {
		assert (simTrial != null);
		simTrial.getTreatmentArms().remove(
				simTrial.getTreatmentArms().size() - 1);
	}

	/**
	 * Provieds the current amount of defined treatment arms.
	 * 
	 * @return
	 */
	public int getTreatmentArmsCount() {
		assert (simTrial != null);
		return simTrial.getTreatmentArms().size();
	}

	public ArrayList<AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>>> getCriteriaList() {
		return criteriaList;
	}

	public void addAlgorithm(ActionEvent event) {
		ValueExpression ve2 = FacesContext.getCurrentInstance()
				.getApplication().getExpressionFactory().createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{simulationAlgorithm}", SimulationAlgorithm.class);
		SimulationAlgorithm currentAlg = (SimulationAlgorithm) ve2
				.getValue(FacesContext.getCurrentInstance().getELContext());
		if (currentAlg.getSelectedAlgorithmPanelId().equals(
				Step5.AlgorithmPanelId.COMPLETE_RANDOMIZATION.toString())) {
			randomisationConfigs.add(new AlgorithmWrapper(
					new CompleteRandomizationConfig()));
		} else if (currentAlg.getSelectedAlgorithmPanelId().equals(
				Step5.AlgorithmPanelId.BIASEDCOIN_RANDOMIZATION.toString())) {
			randomisationConfigs.add(new AlgorithmWrapper(
					new BiasedCoinRandomizationConfig()));
		} else if (currentAlg.getSelectedAlgorithmPanelId().equals(
				Step5.AlgorithmPanelId.BLOCK_RANDOMIZATION.toString())) {
			randomisationConfigs.add(new AlgorithmWrapper(
					new BlockRandomizationConfig()));
		} else if (currentAlg.getSelectedAlgorithmPanelId().equals(
				Step5.AlgorithmPanelId.TRUNCATED_RANDOMIZATION.toString())) {
			randomisationConfigs.add(new AlgorithmWrapper(
					new TruncatedBinomialDesignConfig()));
		} else if (currentAlg.getSelectedAlgorithmPanelId().equals(
				Step5.AlgorithmPanelId.URN_MODEL.toString())) {
			randomisationConfigs
					.add(new AlgorithmWrapper(new UrnDesignConfig()));
		} else if (currentAlg.getSelectedAlgorithmPanelId().equals(
				Step5.AlgorithmPanelId.MINIMIZATION.toString())) {
			randomisationConfigs.add(new AlgorithmWrapper(
					new MinimizationConfig()));
		}
		System.out.println(randomisationConfigs.size());
	}

	/**
	 * Action listener for removing an existing treatment arm.
	 * 
	 * @param event
	 */
	public void removeAlgorithm(ActionEvent event) {
		assert (simTrial != null);
		randomisationConfigs.remove(randomisationConfigs.size() - 1);
	}
	
	public void setCountTrialSites(int countTrialSites) {
		this.countTrialSites = countTrialSites;
		simTrial.setParticipatingSites(new HashSet<TrialSite>());
		for (int i = 0; i < countTrialSites; i++) {
			TrialSite site = new TrialSite();
			site.setName("Trial Site " + (i + 1));
			simTrial.addParticipatingSite(site);
		}
	}
}
