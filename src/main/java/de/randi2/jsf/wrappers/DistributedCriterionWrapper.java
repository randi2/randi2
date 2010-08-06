package de.randi2.jsf.wrappers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;
import de.randi2.jsf.backingBeans.AlgorithmConfig.AlgorithmPanelId;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.randomization.BiasedCoinRandomizationConfig;
import de.randi2.model.randomization.BlockRandomizationConfig;
import de.randi2.model.randomization.CompleteRandomizationConfig;
import de.randi2.model.randomization.MinimizationConfig;
import de.randi2.model.randomization.TruncatedBinomialDesignConfig;
import de.randi2.model.randomization.UrnDesignConfig;
import de.randi2.simulation.distribution.AbstractDistribution;
import de.randi2.simulation.distribution.ConcreteDistribution;
import de.randi2.simulation.distribution.UniformDistribution;
import de.randi2.simulation.model.DistributionSubjectProperty;

public class DistributedCriterionWrapper<V extends Serializable, C extends AbstractConstraint<V>> {

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
	
	
	@Getter @Setter
	private boolean seedB = false;
	
	@Getter @Setter
	private long seed;
	
	@Getter @Setter
	private List<DistributedConstraintWrapper> strataDistributions;
	
	private CriterionWrapper<V> criterion;
	
	private Class<?> distributionClass = UniformDistribution.class;
	
	@Getter
	private String selectedDistributionId = DistributionId.UNIFORM_DISTRIBUTION.toString();
	
	
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
	
	public DistributedCriterionWrapper(List<DistributedConstraintWrapper> strataDistributions, CriterionWrapper<V> criterionWrapper ) {
		super();
		this.strataDistributions = strataDistributions;
		this.criterion = criterionWrapper;
	}
	
	
	public Class<?> getDistributionClass() {
		return distributionClass;
	}


	public void setDistributionClass(
			Class<? extends AbstractDistribution<? extends Serializable>> distributionClass) {
		this.distributionClass = distributionClass;
	}
	


	public CriterionWrapper<V> getCriterion() {
		return criterion;
	}


	public void setCriterion(CriterionWrapper<V> criterion) {
		this.criterion = criterion;
	}

	public boolean isConcreteDistribution(){
		return (distributionClass == ConcreteDistribution.class);
	}
	
	public DistributionSubjectProperty getDistributionSubjectProperty(){
		DistributionSubjectProperty property = null;
		if(distributionClass == UniformDistribution.class){
			
			UniformDistribution<Serializable> distri; 
			if(seedB){
				distri = new UniformDistribution<Serializable>((List<Serializable>) criterion.getWrappedCriterion().getConfiguredValues(),seed);
			}else{
				distri = new UniformDistribution<Serializable>((List<Serializable>) criterion.getWrappedCriterion().getConfiguredValues());
			}
			property = new DistributionSubjectProperty(criterion.getWrappedCriterion(), distri);
		}else if(distributionClass == ConcreteDistribution.class){
			int[] ints = new int[strataDistributions.size()];
			for(int i =0; i< strataDistributions.size();i++){
				ints[i] = strataDistributions.get(i).getRatio();
			}
			ConcreteDistribution<Serializable> distri;
			if(seedB){
				distri = new ConcreteDistribution<Serializable>(seed,(List<Serializable>) criterion.getWrappedCriterion().getConfiguredValues(), ints);
			}else{
				distri = new ConcreteDistribution<Serializable>((List<Serializable>) criterion.getWrappedCriterion().getConfiguredValues(), ints);
			}
			property = new DistributionSubjectProperty(criterion.getWrappedCriterion(), distri);
		}
		 
		return property;
	}
}
