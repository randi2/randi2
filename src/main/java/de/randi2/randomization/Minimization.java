package de.randi2.randomization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

import de.randi2.model.SubjectProperty;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.TrialSubject;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.randomization.MinimizationConfig;
import de.randi2.model.randomization.MinimizationTempData;
import de.randi2.unsorted.ContraintViolatedException;

public class Minimization extends RandomizationAlgorithm<MinimizationConfig>{





	private Random randomEqualScore = new Random();
	
	
	
	
	public Minimization(Trial _trial) {
		super(_trial);
		
	}

	public Minimization(Trial _trial, long seed) {
		super(_trial, seed);
	}
	
	public Minimization(Trial _trial, long seed, long seedEqualScore){
		super(_trial, seed);
		randomEqualScore = new Random(seedEqualScore);
	}
	
	
	@Override
	protected TreatmentArm doRadomize(TrialSubject subject, Random random) {
		if (configuration.isBiasedCoinMinimization()){
			if(((MinimizationTempData) configuration.getTempData()).getProbabilitiesPerPreferredTreatment() == null) initProbabilitiesPerPreferredTreatment();
			return doRandomizeBiasedCoinMinimization(subject, random);
		}else{
			return doRandomizeNaiveMinimization(subject, random);
		}
	}
	
	private TreatmentArm doRandomizeNaiveMinimization(TrialSubject subject, Random random){
		//calculate the p-values for this allocation
		List<TreatmentArm> arms = trial.getTreatmentArms();
		ArrayList<Double> a = new ArrayList<Double>();
		for(TreatmentArm arm : arms){
			double plannedSubjects = configuration.isWithRandomizedSubjects() ? (arm.getPlannedSubjects()-arm.getCurrentSubjectsAmount()):arm.getPlannedSubjects();
			double sum = 0;
			double totalPlannedSubjects = 0;
			for(TreatmentArm arm1 :arms){
				if(!arm.equals(arm1)){
					sum+=configuration.isWithRandomizedSubjects() ? (arm1.getPlannedSubjects()-arm1.getCurrentSubjectsAmount()) :arm1.getPlannedSubjects();
				}
				totalPlannedSubjects +=arm1.getPlannedSubjects();
			}
			//Formula from: "Randomization by minimization for unbalanced treatment allocation" Baoguang Han, et al. 
				double value = plannedSubjects*configuration.getP() + (1.0-configuration.getP())/(trial.getTreatmentArms().size() -1.0)*sum;
				value = value / (configuration.isWithRandomizedSubjects() ? totalPlannedSubjects-trial.getTotalSubjectAmount(): totalPlannedSubjects);
				a.add(value);
		
		}
		//get Treatment arm with calculated p-values
		double randomNumber = random.nextDouble();
		double sum = 0;
		int i = 0;
		TreatmentArm arm = null;
		while(i<a.size() && arm == null){
			sum+=a.get(i);
			if(randomNumber <sum){
				arm = arms.get(i);
			}
			i++;
		}
		return arm;
	}
	
	
	
	@SuppressWarnings("unchecked")
	private TreatmentArm doRandomizeBiasedCoinMinimization(TrialSubject subject, Random random){
		
		List<TreatmentArm> arms = Collections.unmodifiableList(trial.getTreatmentArms());
		
		HashMap<AbstractConstraint<?>,HashMap<TreatmentArm, Double>> relevantConstraints = new HashMap<AbstractConstraint<?>, HashMap<TreatmentArm, Double>>();
		HashMap<TreatmentArm, Double> relevantTrialSite = null;
		
		if(((MinimizationTempData) configuration.getTempData()).getProbabilitiesPerPreferredTreatment()== null) initProbabilitiesPerPreferredTreatment();
		//Counter for trial sites
		if(trial.isStratifyTrialSite()){
			if(((MinimizationTempData) configuration.getTempData()).getCountTrialSites()== null) ((MinimizationTempData) configuration.getTempData()).setCountTrialSites(new HashMap<TrialSite, HashMap<TreatmentArm,Double>>());
			HashMap<TreatmentArm, Double> actMap = ((MinimizationTempData) configuration.getTempData()).getCountTrialSites().get(subject.getTrialSite());
			if(actMap == null){
				actMap = new HashMap<TreatmentArm, Double>();
				for(TreatmentArm arm : arms){
					actMap.put(arm, 0.0);							
				}
				((MinimizationTempData) configuration.getTempData()).getCountTrialSites().put(subject.getTrialSite(), actMap);
			}
			relevantTrialSite = actMap;
		}else  if(((MinimizationTempData) configuration.getTempData()).getCountConstraints() == null) ((MinimizationTempData) configuration.getTempData()).setCountConstraints(new HashMap<AbstractConstraint<?>, HashMap<TreatmentArm,Double>>());
		
		
		//Get relevant constraints and if necessary create a new counter 
		for(SubjectProperty prop : subject.getProperties()){
				try {
					HashMap<TreatmentArm, Double> actMap = ((MinimizationTempData) configuration.getTempData()).getCountConstraints().get(prop.getCriterion().stratify(prop.getValue()));
					if(actMap == null){
						actMap = new HashMap<TreatmentArm, Double>();
						for(TreatmentArm arm : arms){
							actMap.put(arm, 0.0);							
						}
						((MinimizationTempData) configuration.getTempData()).getCountConstraints().put(prop.getCriterion().stratify(prop.getValue()), actMap);
					}
					relevantConstraints.put(prop.getCriterion().stratify(prop.getValue()), actMap);
				} catch (ContraintViolatedException e) {	}
		}
		
		//calculate imbalance scores
		HashMap<TreatmentArm, Double> imbalacedScores = new HashMap<TreatmentArm, Double>();		
		
		for(TreatmentArm arm :arms){
			double imbalacedScore = 0.0;
			List<HashMap<TreatmentArm, Double>> listAllRelevantValues = new ArrayList<HashMap<TreatmentArm,Double>>();
			if(trial.isStratifyTrialSite()){
				listAllRelevantValues.add(relevantTrialSite);
			}
			listAllRelevantValues.addAll(relevantConstraints.values());
			for(HashMap<TreatmentArm, Double> map : listAllRelevantValues){
				double[] adjustetCountsPerArm = new double[arms.size()];
				int i = 0;
				for(TreatmentArm actArm : map.keySet()){
					double adjustetCount = 0.0;
					if(actArm.getId() == arm.getId()){
						adjustetCount  =map.get(actArm)+1;
					}else{
						adjustetCount  =map.get(actArm);
					}
					//calculate adjusted counts
					adjustetCount = adjustetCount / actArm.getPlannedSubjects();
					adjustetCountsPerArm[i] = adjustetCount;
					i++;
				}
				//calculate marginal balance
				double marginalBalance = 0.0;
				double numerator = 0.0;	
				for(i=0;i<adjustetCountsPerArm.length-1;i++){
					for(int j = i+1;j<adjustetCountsPerArm.length;j++){
						marginalBalance += Math.abs(adjustetCountsPerArm[i]-adjustetCountsPerArm[j]);
					}
					numerator += adjustetCountsPerArm[i];
				}
				numerator+=adjustetCountsPerArm[adjustetCountsPerArm.length-1];
				numerator =(adjustetCountsPerArm.length-1.0) * numerator;
				marginalBalance = marginalBalance/numerator;
				
				imbalacedScore+= marginalBalance;
			}
			imbalacedScores.put(arm, imbalacedScore);
		}

		//find preferred treatment
		double tmpMinValue = Double.MAX_VALUE;
		ArrayList<TreatmentArm> armsWithSameScore = new ArrayList<TreatmentArm>();
		
		for(TreatmentArm arm : imbalacedScores.keySet()){
			if(imbalacedScores.get(arm)<tmpMinValue){
				armsWithSameScore.clear();
				tmpMinValue = imbalacedScores.get(arm);
				armsWithSameScore.add(arm);
			}else if (imbalacedScores.get(arm)==tmpMinValue){
				armsWithSameScore.add(arm);
			}
		}
		//get all with min value
		ArrayList<Double> a = new ArrayList<Double>();
		
		//==1 Default case one treatment arm with smallest imbalance score
		//all treatment with same score, calculate probability with ratio
		//other cases take randomly one treatment	
		if(armsWithSameScore.size()==1){
			for(TreatmentArm arm : trial.getTreatmentArms()){
				a.add(((MinimizationTempData) configuration.getTempData()).getProbabilitiesPerPreferredTreatment().get(armsWithSameScore.get(0)).get(arm));
			}
		}else if(armsWithSameScore.size()==arms.size()){
			for(TreatmentArm arm : arms){
				a.add(((arm.getPlannedSubjects()*1.0)/(trial.getPlannedSubjectAmount()*1.0)));
			}
		}else{
			TreatmentArm preferredArm = arms.get(randomEqualScore.nextInt(arms.size()));
			for(TreatmentArm arm : arms){
				a.add(((MinimizationTempData) configuration.getTempData()).getProbabilitiesPerPreferredTreatment().get(preferredArm).get(arm));
			}
		}
			
		//get Treatment arm with calculated p-values
		double randomNumber = random.nextDouble();
		double sum = 0;
		int i = 0;
		TreatmentArm arm = null;
		while(i<a.size() && arm == null){
			sum+=a.get(i);
			if(randomNumber <sum){
				arm = arms.get(i);
			}
			i++;
		}
		//increase the count for the relevant constraints
		for(AbstractConstraint<?> constraint : relevantConstraints.keySet()){
			((MinimizationTempData) configuration.getTempData()).getCountConstraints().get(constraint).put(arm, (((MinimizationTempData) configuration.getTempData()).getCountConstraints().get(constraint).get(arm) +1.0));
		}
		if(trial.isStratifyTrialSite()){
			((MinimizationTempData) configuration.getTempData()).getCountTrialSites().get(subject.getTrialSite()).put(arm, (((MinimizationTempData) configuration.getTempData()).getCountTrialSites().get(subject.getTrialSite()).get(arm)+1.0));
		}
		return arm;
	}
	

	/**
	 * Calculate the probabilities per preferred treatment arm (Biased Coin Minimization) 
	 */
	private void initProbabilitiesPerPreferredTreatment(){
		((MinimizationTempData) configuration.getTempData()).setProbabilitiesPerPreferredTreatment(new HashMap<TreatmentArm, Map<TreatmentArm,Double>>());
		TreatmentArm minArm = trial.getTreatmentArms().get(0);
		for(TreatmentArm arm : trial.getTreatmentArms()){
			if(arm.getPlannedSubjects()< minArm.getPlannedSubjects()){
				minArm = arm;
			}
		}
	
		for(TreatmentArm prefArm : trial.getTreatmentArms()){
			HashMap<TreatmentArm,Double> probabilities = new HashMap<TreatmentArm, Double>();
			double pH_pref = 0.0;
			double denuminator = 0.0;
			double numinator = 0.0;
			for(TreatmentArm arm : trial.getTreatmentArms()){
				if(!arm.equals(prefArm)){
					denuminator+=arm.getPlannedSubjects();
				}
				if(!arm.equals(trial.getTreatmentArms().get(0))){
					numinator+=arm.getPlannedSubjects();
				}
			}
			if(prefArm.equals(minArm)){
				pH_pref = configuration.getP();
			}else{
				pH_pref	= 1.0-(denuminator/numinator)*(1-configuration.getP());
			}
			numinator = 0.0;
			for(TreatmentArm arm : trial.getTreatmentArms()){
				if(!arm.equals(prefArm)){
					numinator+= arm.getPlannedSubjects();
				}
			}
			double pL_without_ri = (1-pH_pref)/numinator;
			
			for(TreatmentArm arm : trial.getTreatmentArms()){
				if(arm.equals(prefArm)){
					probabilities.put(arm, pH_pref);
				}else{
					probabilities.put(arm, (pL_without_ri*arm.getPlannedSubjects()));
				}
			}
			((MinimizationTempData) configuration.getTempData()).getProbabilitiesPerPreferredTreatment().put(prefArm, probabilities);
		}
	}


	/**
	 * Necessary to test the algorithm.
	 * @return the probabilities per preferred treatment arm
	 */
	public Map<TreatmentArm, Map<TreatmentArm, Double>> getProbabilitiesPerPreferredTreatment() {
		if(((MinimizationTempData) configuration.getTempData()).getProbabilitiesPerPreferredTreatment()==null) initProbabilitiesPerPreferredTreatment();
		return ((MinimizationTempData) configuration.getTempData()).getProbabilitiesPerPreferredTreatment();
	}
	
	/**
	 * Necessary to reset the algorithm for simulation.
	 */
	public void clear(){
		((MinimizationTempData) configuration.getTempData()).setCountConstraints(null);
		((MinimizationTempData) configuration.getTempData()).setCountConstraints(new HashMap<AbstractConstraint<?>, HashMap<TreatmentArm,Double>>());
		((MinimizationTempData) configuration.getTempData()).setCountTrialSites( new HashMap<TrialSite, HashMap<TreatmentArm,Double>>());
	}
}
