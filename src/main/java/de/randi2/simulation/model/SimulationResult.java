package de.randi2.simulation.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import de.randi2.model.TreatmentArm;
import edu.emory.mathcs.backport.java.util.Collections;

public class SimulationResult {

	@Getter
	private int amountRuns;
	
	@Getter
	private List<TreatmentArm> arms = new ArrayList<TreatmentArm>();
	
	@Getter
	private List<SimualtionResultArm> simResultArms = new ArrayList<SimualtionResultArm>();
	
	private int[] mins;
	private int[] maxs;
	
	private double marginalBalanceMin = Double.NaN;
	private double marginalBalanceMax = Double.NaN;;
	private double marginalBalanceMean = Double.NaN;;
	
	private double[] means;
	
	private double[] medians;
	
	private long duration = Long.MIN_VALUE;
	
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
	
	public double[] getMedians(){
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
	
	public long getDuration(){
		if(duration == Long.MIN_VALUE){
			analyze();
			duration = duration / 1000000;
		}
		return duration;
	}
	
	private void analyze(){
		duration=0;
		simResultArms = new ArrayList<SimualtionResultArm>();
		mins = new int[arms.size()];
		for(int i = 0 ;i<mins.length;i++){
			mins[i] = Integer.MAX_VALUE;
		}
		maxs = new int[arms.size()];
		means = new double[arms.size()];
		medians = new double[arms.size()];
		
		marginalBalanceMax =Double.MIN_VALUE;
		marginalBalanceMin = Double.MAX_VALUE;
		marginalBalanceMean = 0.0;
		HashMap<Integer, ArrayList<Integer>> tempMedian = new HashMap<Integer, ArrayList<Integer>>();
		for(int i =0; i< arms.size();i++){
			tempMedian.put(i, new ArrayList<Integer>());
		}
		
		for(int i = 0 ; i < runs.size();i++){
			for(int j = 0; j<arms.size();j++){
				if(runs.get(i).getSubjectsPerArms()[j]<mins[j]) mins[j] = runs.get(i).getSubjectsPerArms()[j];
				if(runs.get(i).getSubjectsPerArms()[j]>maxs[j]) maxs[j] = runs.get(i).getSubjectsPerArms()[j];
				tempMedian.get(j).add(runs.get(i).getSubjectsPerArms()[j]);
				means[j] += runs.get(i).getSubjectsPerArms()[j];
			}

			if(runs.get(i).getMarginalBalace()<marginalBalanceMin) marginalBalanceMin = runs.get(i).getMarginalBalace();
			if(runs.get(i).getMarginalBalace()>marginalBalanceMax) marginalBalanceMax = runs.get(i).getMarginalBalace();
			marginalBalanceMean+=runs.get(i).getMarginalBalace();
			duration += runs.get(i).getTime();
		}
		for(int i = 0 ;i<arms.size();i++){
			means[i] = means[i] / amountRuns;
			ArrayList<Integer> listMedian = tempMedian.get(i);
			Collections.sort(listMedian);
			if(listMedian.size()%2==0){
				medians[i] = (listMedian.get((listMedian.size()/2)) + listMedian.get((listMedian.size()/2)+1))/2; 
			}else{
				medians[i] = listMedian.get((listMedian.size()/2)+1);
			}
			SimualtionResultArm rArm= new SimualtionResultArm();
			rArm.setArm(arms.get(i));
			rArm.setMean(means[i]);
			rArm.setMedian(medians[i]);
			rArm.setMin(mins[i]);
			rArm.setMax(maxs[i]);
			simResultArms.add(rArm);
		}
		marginalBalanceMean = marginalBalanceMean / amountRuns;
	}
}
