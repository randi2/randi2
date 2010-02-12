package de.randi2.jsf.wrappers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import lombok.Getter;
import de.randi2.model.TrialSite;
import de.randi2.simulation.distribution.AbstractDistribution;
import de.randi2.simulation.distribution.ConcreteDistribution;
import de.randi2.simulation.distribution.UniformDistribution;
import de.randi2.utility.Pair;

public class DistributionTrialSiteWrapper {

	public static enum DistributionId {
		UNIFORM_DISTRIBUTION("uniformDistribution"), CONCRETE_DISTRIBUTION(
				"concreteDistribution");
		
		private String id = null;

		private DistributionId(String id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return this.id;
		}

	}	
	
	private Class<?> distributionClass = UniformDistribution.class;
	

	public Class<?> getDistributionClass() {
		return distributionClass;
	}


	public void setDistributionClass(
			Class<? extends AbstractDistribution<? extends Serializable>> distributionClass) {
		this.distributionClass = distributionClass;
	}
	
	
	@Getter
	private String selectedDistributionId = DistributionId.UNIFORM_DISTRIBUTION.toString();
	
	@Getter
	private List<TrialSiteRatioWrapper> trialSitesRatioWrappers;
	
	public DistributionTrialSiteWrapper(List<TrialSiteRatioWrapper> trialSitesRatioWrappers){
		this.trialSitesRatioWrappers = trialSitesRatioWrappers;
	}
	
	private List<SelectItem> distributions;

	public List<SelectItem> getDistributions() {
		if (distributions == null) {
			distributions = new ArrayList<SelectItem>();
			distributions.add(new SelectItem(DistributionId.UNIFORM_DISTRIBUTION.toString()));
			distributions.add(new SelectItem(DistributionId.CONCRETE_DISTRIBUTION.toString()));
		}
		return distributions;
	}

	public void setSelectedDistributionId(String selectedDistributionId) {
		this.selectedDistributionId = selectedDistributionId;
		if (selectedDistributionId
				.equals(DistributionId.CONCRETE_DISTRIBUTION.toString())){
				distributionClass = ConcreteDistribution.class;
		}else if (selectedDistributionId
				.equals(DistributionId.UNIFORM_DISTRIBUTION.toString())){
				distributionClass = UniformDistribution.class;
		}
				
	}
	
	public AbstractDistribution<TrialSite> getDistributionTrialSites(){
		
		if(distributionClass == UniformDistribution.class){
			List<TrialSite> trialSites = new ArrayList<TrialSite>();
			for(TrialSiteRatioWrapper wrapper: trialSitesRatioWrappers){
				trialSites.add(wrapper.getSite());
			}
			return  new UniformDistribution<TrialSite>(trialSites);
			
		}else{
			List<Pair<TrialSite, Integer>> trialSitesRatio = new ArrayList<Pair<TrialSite, Integer>>();
			for(TrialSiteRatioWrapper wrapper: trialSitesRatioWrappers){
				trialSitesRatio.add(Pair.of(wrapper.getSite(), wrapper.getRatio()));
			}
			return new ConcreteDistribution<TrialSite>(trialSitesRatio.toArray(new Pair[0]));
		}
		
	}
	
	public boolean isConcreteDistribution(){
		return (distributionClass == ConcreteDistribution.class);
	}
}
