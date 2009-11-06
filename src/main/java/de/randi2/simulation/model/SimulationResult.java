package de.randi2.simulation.model;

import java.util.ArrayList;
import java.util.List;

import de.randi2.model.TreatmentArm;

import lombok.Data;
import lombok.Getter;

public class SimulationResult {

	@Getter
	private int amountRuns;
	
	@Getter
	private List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
	
	private int[] mins;
	private int[] maxs;
	
	private double marginalBalanceMin = Double.NaN;
	private double marginalBalanceMax = Double.NaN;;
	private double marginalBalanceMean = Double.NaN;;
	
	private double[] means;
	
	private int[] medians;
	
	@Getter
	private List<SimulationRun> runs = new ArrayList<SimulationRun>();

	
	public SimulationResult(List<TreatmentArm> arms){
		this.arms = arms;
	}
	
	
	public void addSimulationRun(SimulationRun run){
		amountRuns++;
		runs.add(run);
	}
	
	
	public SimulationRun getEmptyRun(){
		return new SimulationRun(arms.size());
	}
	
	
	public double[] getMeans(){
		if(means == null){
			analyze();
		}
		return means;
	}
	
	public int[] getMins(){
		if(mins == null){
			analyze();
		}
		return mins;
	}
	
	public int[] getMaxs(){
		if(maxs == null){
			analyze();
		}
		return maxs;
	}
	
	public int[] getMedians(){
		if(medians == null){
			analyze();
		}
		return medians;
	}
	
	public double getMarginalBalanceMin(){
		if(Double.isNaN(marginalBalanceMin)){
			analyze();
		}
		return marginalBalanceMin;
	}
	
	public double getMarginalBalanceMax(){
		if(Double.isNaN(marginalBalanceMax)){
			analyze();
		}
		return marginalBalanceMax;
	}
	
	
	public double getMarginalBalanceMean(){
		if(Double.isNaN(marginalBalanceMean)){
			analyze();
		}
		return marginalBalanceMean;
	}
	
	private void analyze(){
		mins = new int[arms.size()];
		for(int i = 0 ;i<mins.length;i++){
			mins[i] = Integer.MAX_VALUE;
		}
		maxs = new int[arms.size()];
		means = new double[arms.size()];
		medians = new int[arms.size()];
		marginalBalanceMax =Double.MIN_VALUE;
		marginalBalanceMin = Double.MAX_VALUE;
		marginalBalanceMean = 0.0;
		for(int i = 0 ; i < runs.size();i++){
			for(int j = 0; j<arms.size();j++){
				if(runs.get(i).getSubjectsPerArms()[j]<mins[j]) mins[j] = runs.get(i).getSubjectsPerArms()[j];
				if(runs.get(i).getSubjectsPerArms()[j]>maxs[j]) maxs[j] = runs.get(i).getSubjectsPerArms()[j];
				
				means[j] += runs.get(i).getSubjectsPerArms()[j];
			}

			if(runs.get(i).getMarginalBalace()<marginalBalanceMin) marginalBalanceMin = runs.get(i).getMarginalBalace();
			if(runs.get(i).getMarginalBalace()>marginalBalanceMax) marginalBalanceMax = runs.get(i).getMarginalBalace();
			marginalBalanceMean+=runs.get(i).getMarginalBalace();
		}
		for(int i = 0 ;i<means.length;i++){
			means[i] = means[i] / amountRuns;
		}
		marginalBalanceMean = marginalBalanceMean / amountRuns;
	}
}
