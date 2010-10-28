package de.randi2.jsf.controllerBeans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import com.icesoft.faces.context.Resource;

import de.randi2.jsf.backingBeans.AlgorithmConfig;
import de.randi2.jsf.backingBeans.Randi2Page;
import de.randi2.jsf.backingBeans.SimulationAlgorithm;
import de.randi2.jsf.backingBeans.SimulationSubjectProperty;
import de.randi2.jsf.wrappers.AlgorithmWrapper;
import de.randi2.jsf.wrappers.CriterionWrapper;
import de.randi2.jsf.wrappers.DistributedConstraintWrapper;
import de.randi2.jsf.wrappers.DistributedCriterionWrapper;
import de.randi2.jsf.wrappers.DistributionTrialSiteWrapper;
import de.randi2.jsf.wrappers.TrialSiteRatioWrapper;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.randomization.BiasedCoinRandomizationConfig;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import de.randi2.model.randomization.MinimizationConfig;
import de.randi2.model.randomization.TruncatedBinomialDesignConfig;
import de.randi2.model.randomization.UrnDesignConfig;
import de.randi2.simulation.model.DistributionSubjectProperty;
import de.randi2.simulation.model.SimulationRawDataEntry;
import de.randi2.simulation.model.SimulationResult;
import de.randi2.simulation.model.SimulationResultArm;
import de.randi2.simulation.model.helper.StrataResultComperatorAST;
import de.randi2.simulation.model.helper.StrataResultComperatorATS;
import de.randi2.simulation.model.helper.StrataResultComperatorSAT;
import de.randi2.simulation.model.helper.StrataResultComperatorSTA;
import de.randi2.simulation.model.helper.StrataResultWrapper;
import de.randi2.simulation.service.SimulationService;

/**
 * <p>
 * This class cares about the simulation of trials and contains all the needed
 * methods to work with this object for the UI.
 * </p>
 * 
 * @author ds@randi2.de
 */
@ManagedBean(name="simulationHandler")
@SessionScoped
public class SimulationHandler extends AbstractTrialHandler {

	@ManagedProperty(value="#{trialHandler}")
	@Getter
	@Setter
	private TrialHandler trialHandler;
	@ManagedProperty(value="#{simulationService}")
	@Getter
	@Setter
	private SimulationService simulationService;

	@Getter
	@Setter
	private int runs = 1000;

	@Getter
	@Setter
	private long maxTime;

	@Getter
	private SimulationResult simResult;

	@Getter
	private int countTrialSites;

	@Setter
	private boolean criterionChanged = true;

	public void criterionChanged() {
		criterionChanged = true;
	}

	public boolean isCriterionChanged() {
		criterionChanged = true;
		return criterionChanged;
	}

	@Getter
	private List<AlgorithmWrapper> randomisationConfigs = new ArrayList<AlgorithmWrapper>();

	@Getter
	@Setter
	private List<SimulationResult> simulationResults = new ArrayList<SimulationResult>();

	@Getter
	@Setter
	private boolean simOnly;

	@Getter
	@Setter
	private boolean seedRandomisationAlgorithmB;

	@Getter
	@Setter
	private long seedRandomisationAlgorithm;

	@Getter
	@Setter
	private boolean simFromTrialCreationFirst = true;
	
	@Getter
	@Setter
	private SimulationResultFactorsOrderEnum selectedOrder= SimulationResultFactorsOrderEnum.SAT;
	
	public static enum SimulationResultFactorsOrderEnum{
		SAT("SAT"),STA("STA"),ATS("ATS"),AST("AST");
		
		private String id = null;

		private SimulationResultFactorsOrderEnum(String id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return this.id;
		}
	}


	@Getter
	@Setter
	private boolean collectRawData = false;
	
	private DistributionTrialSiteWrapper distributedTrialSites;

	private List<DistributedCriterionWrapper<Serializable, AbstractConstraint<Serializable>>> distributedCriterions;
	
	
	public DistributionTrialSiteWrapper getDistributionTrialSiteWrapper() {
		if (distributedTrialSites == null
				|| distributedTrialSites.getTrialSitesRatioWrappers().size() != countTrialSites) {
			List<TrialSiteRatioWrapper> tRatioWrapper = new ArrayList<TrialSiteRatioWrapper>();
			for (TrialSite site : currentObject.getParticipatingSites()) {
				tRatioWrapper.add(new TrialSiteRatioWrapper(site));
			}
			distributedTrialSites = new DistributionTrialSiteWrapper(
					tRatioWrapper);
		}
		return distributedTrialSites;
	}

	public void setDistributedCriterions(
			List<DistributedCriterionWrapper<Serializable, AbstractConstraint<Serializable>>> distributedCriterions) {
		this.distributedCriterions = distributedCriterions;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<DistributedCriterionWrapper<Serializable, AbstractConstraint<Serializable>>> getDistributedCriterions() {
		if (distributedCriterions == null
				|| distributedCriterions.size() != currentObject.getCriteria()
						.size()) {
			distributedCriterions = new ArrayList<DistributedCriterionWrapper<Serializable, AbstractConstraint<Serializable>>>();
			for (AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> c : currentObject
					.getCriteria()) {
				if (c.getStrata() != null) {
					List<DistributedConstraintWrapper> strataDistributions = new ArrayList<DistributedConstraintWrapper>();

					for (AbstractConstraint<? extends Serializable> con : c
							.getStrata()) {
						strataDistributions
								.add(new DistributedConstraintWrapper(con));
					}
					distributedCriterions.add(new DistributedCriterionWrapper(
							strataDistributions,
							new CriterionWrapper<Serializable>(
									(AbstractCriterion<Serializable, ?>) c)));
				}
			}
		}
		return distributedCriterions;
	}

	public Trial getSimTrial() {
		if (simFromTrialCreationFirst && !simOnly) {
			currentObject = trialHandler.getCurrentObject();
			try {
				/* Leading Trial Site & Sponsor Investigator */
				currentObject.setLeadingSite(trialHandler.getTrialSitesAC()
						.getSelectedObject());
				if (trialHandler.getSponsorInvestigatorsAC()
						.getSelectedObject() != null)
					currentObject.setSponsorInvestigator(trialHandler
							.getSponsorInvestigatorsAC().getSelectedObject()
							.getPerson());
				currentObject.setCriteria(configureCriteriaStep4());
				simFromTrialCreationFirst = false;
			} catch (Exception e) {
				return null;
			}

		} else if (currentObject == null && simOnly) {
			currentObject = new Trial();
			List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
			arms.add(new TreatmentArm());
			arms.add(new TreatmentArm());
			currentObject.setTreatmentArms(arms);
		}
		if (simOnly && criterionChanged) {
			/* SubjectProperties Configuration - done in Step4 */
			ValueExpression ve1 = FacesContext.getCurrentInstance()
					.getApplication().getExpressionFactory()
					.createValueExpression(
							FacesContext.getCurrentInstance().getELContext(),
							"#{simulationSubjectProperty}",
							SimulationSubjectProperty.class);
			SimulationSubjectProperty currentSimulationSubjectProperty = (SimulationSubjectProperty) ve1
					.getValue(FacesContext.getCurrentInstance().getELContext());
			currentObject
					.setCriteria(addAllConfiguredCriteria(currentSimulationSubjectProperty
							.getCriteria()));
			criterionChanged = false;
		}

		return currentObject;

	}

	private List<CriterionWrapper<Serializable>> strata;

	@SuppressWarnings("unchecked")
	public List<CriterionWrapper<Serializable>> getStrata() {
		strata = new ArrayList<CriterionWrapper<Serializable>>();
		for (AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> c : currentObject
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

		for (AbstractCriterion<?, ?> c : currentObject.getCriteria()) {
			if (c.getStrata() != null) {
				if (c.getStrata().size() > 0)
					return true;
			}
		}
		return false;
	}

	public String getAlgName() {
		ResourceBundle bundle = ResourceBundle.getBundle(
				"de.randi2.jsf.i18n.algorithms", getLoginHandler()
						.getChosenLocale());
		return bundle.getString(currentObject.getRandomizationConfiguration()
				.getClass().getCanonicalName()
				+ ".name");
	}

	public String getFurtherDetails() {
		StringBuffer furtherDetails = new StringBuffer();
		ResourceBundle bundle = ResourceBundle.getBundle(
				"de.randi2.jsf.i18n.labels", getLoginHandler()
						.getChosenLocale());
		if (BlockRandomizationConfig.class.isInstance(currentObject
				.getRandomizationConfiguration())) {
			BlockRandomizationConfig conf = BlockRandomizationConfig.class
					.cast(currentObject.getRandomizationConfiguration());
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
		} else if (UrnDesignConfig.class.isInstance(currentObject
				.getRandomizationConfiguration())) {
			UrnDesignConfig conf = UrnDesignConfig.class.cast(currentObject
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
		} else if (MinimizationConfig.class.isInstance(currentObject
				.getRandomizationConfiguration())) {
			MinimizationConfig conf = MinimizationConfig.class
					.cast(currentObject.getRandomizationConfiguration());
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
			return currentObject.isStratifyTrialSite();
	}

	public void simTrial() {
		simulationResults = null;
		simResult = null;
		List<DistributionSubjectProperty> properties = new ArrayList<DistributionSubjectProperty>();
		if (distributedCriterions != null) {
			for (DistributedCriterionWrapper<Serializable, AbstractConstraint<Serializable>> dcw : distributedCriterions) {
				properties.add(dcw.getDistributionSubjectProperty());
			}
		}

		if (simOnly) {
			simulationResults = new ArrayList<SimulationResult>();
			for (AlgorithmWrapper alg : randomisationConfigs) {
				currentObject.setRandomizationConfiguration(alg.getConf());
				alg.getConf().setTempData(null);
				alg.getConf().setTrial(currentObject);
				if (seedRandomisationAlgorithmB) {
					alg.getConf().resetAlgorithm(seedRandomisationAlgorithm);
				} else {
					alg.getConf().resetAlgorithm();
				}
				SimulationResult result = simulationService.simulateTrial(
						currentObject, properties, distributedTrialSites
								.getDistributionTrialSites(), runs, maxTime, collectRawData);
				result.setAlgorithmDescription(alg.getDescription());
				simulationResults.add(result);
			}

		} else {
			SimulationResult result = simulationService.simulateTrial(
					currentObject, properties, distributedTrialSites
							.getDistributionTrialSites(), runs, maxTime, collectRawData);
			simResult = result;
			Randi2Page rPage = ((Randi2Page) FacesContext.getCurrentInstance()
					.getApplication().getELResolver().getValue(
							FacesContext.getCurrentInstance()
									.getELContext(), null, "randi2Page"));
			rPage.simulationResult(null);
		}
	}

	public boolean isResultComplete() {
		return simResult != null;
	}

	public boolean isResultsComplete() {
		return (simulationResults != null && !simulationResults.isEmpty());
	}

	public boolean isSimulationComplete() {
		return isResultComplete() || isResultsComplete();
	}

	public void addAlgorithm(ActionEvent event) {
		ValueExpression ve2 = FacesContext.getCurrentInstance()
				.getApplication().getExpressionFactory().createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{simulationAlgorithm}", SimulationAlgorithm.class);
		SimulationAlgorithm currentAlg = (SimulationAlgorithm) ve2
				.getValue(FacesContext.getCurrentInstance().getELContext());
		if (currentAlg.getSelectedAlgorithmPanelId().equals(
				AlgorithmConfig.AlgorithmPanelId.COMPLETE_RANDOMIZATION.toString())) {
			randomisationConfigs.add(new AlgorithmWrapper(
					new CompleteRandomizationConfig()));
		} else if (currentAlg.getSelectedAlgorithmPanelId().equals(
				AlgorithmConfig.AlgorithmPanelId.BIASEDCOIN_RANDOMIZATION.toString())) {
			randomisationConfigs.add(new AlgorithmWrapper(
					new BiasedCoinRandomizationConfig()));
		} else if (currentAlg.getSelectedAlgorithmPanelId().equals(
				AlgorithmConfig.AlgorithmPanelId.BLOCK_RANDOMIZATION.toString())) {
			AlgorithmWrapper algWrapper = new AlgorithmWrapper(
					new BlockRandomizationConfig());
			algWrapper.getBlockR().setLoginHandler(getLoginHandler());
			randomisationConfigs.add(algWrapper);
		} else if (currentAlg.getSelectedAlgorithmPanelId().equals(
				AlgorithmConfig.AlgorithmPanelId.TRUNCATED_RANDOMIZATION.toString())) {
			randomisationConfigs.add(new AlgorithmWrapper(
					new TruncatedBinomialDesignConfig()));
		} else if (currentAlg.getSelectedAlgorithmPanelId().equals(
				AlgorithmConfig.AlgorithmPanelId.URN_MODEL.toString())) {
			randomisationConfigs
					.add(new AlgorithmWrapper(new UrnDesignConfig()));
		} else if (currentAlg.getSelectedAlgorithmPanelId().equals(
				AlgorithmConfig.AlgorithmPanelId.MINIMIZATION.toString())) {
			randomisationConfigs.add(new AlgorithmWrapper(
					new MinimizationConfig()));
		}
		randomisationConfigs.get(randomisationConfigs.size() - 1).setPossition(
				randomisationConfigs.size() - 1);
		randomisationConfigs.get(randomisationConfigs.size() - 1).getBlockR()
				.setPossitionForSimulation(randomisationConfigs.size() - 1);
	}

	/**
	 * Action listener for removing an existing treatment arm.
	 * 
	 * @param event
	 */
	public void removeAlgorithm(ActionEvent event) {
		assert (currentObject != null);
		randomisationConfigs.remove(randomisationConfigs.size() - 1);
	}

	public void setCountTrialSites(int countTrialSites) {
		this.countTrialSites = countTrialSites;
		currentObject.setParticipatingSites(new HashSet<TrialSite>());
		for (int i = 0; i < countTrialSites; i++) {
			TrialSite site = new TrialSite();
			site.setName("Trial Site " + (i + 1));
			currentObject.addParticipatingSite(site);
		}
	}

	public Resource getExportSimulationResults() {
		StringBuffer sb = new StringBuffer();
		sb.append("<h2>Studie: "+ currentObject.getName() + "</h2> \n");
		
		sb.append("<h3>Sites:</h3>\n");
		sb.append("<table border=1 width=200px><tr><th>Name</th><th>Ratio</th></tr>");
		for (TrialSite site : currentObject.getParticipatingSites()) {
			sb.append("<tr><td>" + site.getName() + "</td><td>"+ 1 +"</td></tr> \n");
		}
		sb.append("</table>");
		
		sb.append("<h3>Treatment Arms:</h3>\n");
		sb.append("<table border=1 width=400px><tr><th>Name</th><th>Description</th><th>Ration</th></tr>\n");
		for (TreatmentArm arm : currentObject.getTreatmentArms()) {
			sb.append(" <tr><td> " + arm.getName() + "</td>");
			sb.append(" <td> "+ arm.getDescription() +"</td>");
			sb.append(" <td> " + arm.getPlannedSubjects() +"</td>"
					+ "</tr>\n");
		}
		sb.append("</table");
		
		sb.append("<h3>Simulation runs: </h3>" + runs + " \n");
		sb.append("<h3>Algorithms:</h3>\n");
		sb.append("<table border=1 width=800px>" +
				"<tr>" +
				"<th>Type</th><th>Time in ms</th><th>min Marginal Balance</th> <th>mean Marginal Balance</th> <th>max Marginal Balance</th>" +
				"</tr>");
		for (SimulationResult res : simulationResults) {
			sb.append(" <tr><td> "
					+ res.getAlgConf().getClass().getSimpleName() + "</td>");
			sb.append("<td> " + res.getDuration() + "</td>");
			sb.append("<td>"
					+ res.getMarginalBalanceMin() + "</td>");
			sb.append("<td>"
					+ res.getMarginalBalanceMean() + "</td>");
			sb.append("<td>"
					+ res.getMarginalBalanceMax() + "</td></tr>\n");
		}
		
		sb.append("</table>");
		sb.append("<table>");
		sb.append("<table border=0><tr><th><h3>Details Arms</h3></th></tr>\n");
		for (SimulationResult res : simulationResults) {
			sb.append("<tr><th>"+res.getAlgConf().getClass().getSimpleName()+"</th></tr>");
			sb.append("<tr><td>");
			sb.append("<table border=1 width=800px><tr><th>Arm name</th><th>min</th>" +
					"<th>min per cent</th><th>max</th><th>max per cent</th><th>mean</th>" +
					"<th>median</th></tr>");
			for (SimulationResultArm simArm : res.getSimResultArms()) {
				sb.append("<tr><td>" + simArm.getArm().getName() + "</td>");
				sb.append("<td>" + simArm.getMin() + "</td>");
				sb.append("<td>" + simArm.getMinPercentString() + "</td>");
				sb.append("<td>" + simArm.getMax() + "</td>");
				sb.append("<td>" + simArm.getMaxPercentString() + "</td>");
				sb.append("<td>" + simArm.getMean() + "</td>");
				sb.append("<td>" + simArm.getMedian() + "</td></tr>\n");
			}
			sb.append("</table>");
			sb.append("</td></tr>");
		}
		sb.append("</table>");
		
		sb.append("<h3>Details Subgroups:</h3>");
		sb.append("<table border=1 width=800px><tr><th>Algorithm</th><th>Treatment arm</th><th>Subgroup</th><th>min</th><th>mean</th><th>max</th></tr>\n");
		SimulationResultFactorsOrderEnum temp = selectedOrder;
		selectedOrder = SimulationResultFactorsOrderEnum.ATS;
		List<StrataResultWrapper> listWrapper = getAllStrataResults();
		for(int i =0; i<listWrapper.size();i++ ){
			sb.append("<tr>");
			//new Algorithm
			if(i % (listWrapper.size()/simulationResults.size()) == 0){
				sb.append("<td rowspan=\" "+ (listWrapper.size()/simulationResults.size()) +"\">"+ listWrapper.get(i).getAlgorithmName()+"</td>");
			}
			if(i % ((listWrapper.size()/simulationResults.size())/currentObject.getTreatmentArms().size()) == 0){
				sb.append("<td rowspan=\" "+ ((listWrapper.size()/simulationResults.size())/currentObject.getTreatmentArms().size()) +"\">"+ listWrapper.get(i).getTreatmentName()+"</td>");
			}
			sb.append("<td>"+ listWrapper.get(i).getStrataName()+"</td>");
			sb.append("<td>"+ listWrapper.get(i).getMinCount()+"</td>");
			sb.append("<td>"+ listWrapper.get(i).getMean()+"</td>");
			sb.append("<td>"+ listWrapper.get(i).getMaxCount()+"</td>");
			sb.append("</tr>");
		}
		
		selectedOrder = temp;
		sb.append("</table>");
	
		
		return new MyStringResource("simulationResult.html", sb.toString());
	}
	
	public Resource getExportSimulationRawData(){
		StringBuffer sb = new StringBuffer();
		for(SimulationResult simRes : simulationResults){
			sb.append(simRes.getAlgorithmDescription() + ":\n");
			for(SimulationRawDataEntry entry : simRes.getRawData()){
				sb.append(entry.getRun() +";");
				sb.append(entry.getCount() +";");
				sb.append(entry.getTreatmentArm() +";");
				sb.append(entry.getTrialSite() +";");
				String stratumNameComp =simRes.getStrataIdNames().get(entry.getStratum());
				String stratumName = stratumNameComp.substring((stratumNameComp.lastIndexOf("|")+1));
				sb.append(stratumName + "\n");
			}
			sb.append("-------------------------------------------\n");
		}
		return new MyStringResource("rawData.csv", sb.toString());
	}

	public List<StrataResultWrapper> getAllStrataResults(){
		List<StrataResultWrapper> wrapper = new ArrayList<StrataResultWrapper>();
		for(SimulationResult simRes : simulationResults){
			for(SimulationResultArm simArm : simRes.getSimResultArms()){
				wrapper.addAll(simArm.getStrataResults());
			}
		}
		if(selectedOrder == SimulationResultFactorsOrderEnum.SAT){
			Collections.sort(wrapper, new StrataResultComperatorSAT());
		}else if(selectedOrder == SimulationResultFactorsOrderEnum.STA){
			Collections.sort(wrapper, new StrataResultComperatorSTA());
		}else if(selectedOrder == SimulationResultFactorsOrderEnum.ATS){
			Collections.sort(wrapper, new StrataResultComperatorATS());
		}if(selectedOrder == SimulationResultFactorsOrderEnum.AST){
			Collections.sort(wrapper, new StrataResultComperatorAST());
		}
		
		return wrapper;
	}
	
	public boolean isSta(){
		return selectedOrder==SimulationResultFactorsOrderEnum.STA;
	}
	
	public boolean isSat(){
		return selectedOrder==SimulationResultFactorsOrderEnum.SAT;
	}
	
	public boolean isAts(){
		return selectedOrder==SimulationResultFactorsOrderEnum.ATS;
	}
	
	public boolean isAst(){
		return selectedOrder==SimulationResultFactorsOrderEnum.AST;
	}
	
	public SelectItem[] getOrderItems(){
		SelectItem[] items = new SelectItem[SimulationResultFactorsOrderEnum.values().length];
		for(int i= 0; i< SimulationResultFactorsOrderEnum.values().length;i++ ){
			items[i] = new SelectItem(SimulationResultFactorsOrderEnum.values()[i]);
		}
		return items;
	}
	
	private class MyStringResource implements Resource, Serializable{

		private static final long serialVersionUID = 2523708207015651805L;
		private String resourceName;
		private String content;
		private final Date lastModified;
		
		@Override
		public String calculateDigest() {
			return resourceName;
		}

		@Override
		public Date lastModified() {
			return lastModified;
		}

		@Override
		public InputStream open() throws IOException {
			return  new ByteArrayInputStream(content.getBytes());
		}

		@Override
		public void withOptions(Options arg0) throws IOException {
		}
		
		public MyStringResource(String resourceName, String content) {
			this.resourceName = resourceName;
			this.content = content;
			this.lastModified = new Date();
		}
		
	}
}
