package de.randi2.simulation.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import de.randi2.model.SubjectProperty;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.TrialSubject;
import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.DateCriterion;
import de.randi2.model.criteria.DichotomousCriterion;
import de.randi2.model.criteria.OrdinalCriterion;
import de.randi2.model.criteria.constraints.DateConstraint;
import de.randi2.model.criteria.constraints.DichotomousConstraint;
import de.randi2.model.criteria.constraints.OrdinalConstraint;
import de.randi2.model.randomization.MinimizationConfig;
import de.randi2.randomization.Minimization;
import de.randi2.simulation.distribution.AbstractDistribution;
import de.randi2.simulation.model.DistributionSubjectProperty;
import de.randi2.simulation.model.SimulationResult;
import de.randi2.simulation.model.SimulationRun;
import de.randi2.unsorted.ContraintViolatedException;

@Service("simulationService")
public class SimulationServiceImpl implements SimulationService {

	@Override
	public SimulationResult simulateTrial(Trial trial, List<DistributionSubjectProperty> properties, AbstractDistribution<TrialSite> distributionTrialSites, int runs, long maxTime) {
		Trial copyTrial = copyAndPrepareTrial(trial, properties);
		SimulationResult simResult = new SimulationResult(trial.getTreatmentArms(), trial.getRandomizationConfiguration());
		long startTime;
		TreatmentArm assignedArm;
		TrialSubject subject = new TrialSubject();
		for (int run = 0; run < runs; run++) {
			startTime = System.nanoTime();
			Trial simTrial = resetTrial(copyTrial);
			SimulationRun simRun = simResult.getEmptyRun();
			for (int i = 0; i < simTrial.getPlannedSubjectAmount(); i++) {
				 subject = generateTrialSubject(properties, subject);
				subject.setTrialSite(distributionTrialSites.getNextValue());
			
				assignedArm = simTrial
						.getRandomizationConfiguration().getAlgorithm()
						.randomize(subject);
		
				subject.setArm(assignedArm);
				assignedArm.addSubject(subject);
			}
			for(int i = 0; i<simTrial.getTreatmentArms().size();i++){
				simRun.getSubjectsPerArms()[i] = simTrial.getTreatmentArms().get(i).getCurrentSubjectsAmount();
			}
			simRun.setTime((System.nanoTime()-startTime));
			simResult.addSimulationRun(simRun);
		}
		simResult.analyze();
		return simResult;
	}

	
	public static Trial copyAndPrepareTrial(Trial trial,  List<DistributionSubjectProperty> properties) {
		int id = 0;
		Trial cTrial = new Trial();
		cTrial.setId(id++);
		cTrial.setParticipatingSites(new HashSet<TrialSite>(trial
				.getParticipatingSites()));
		cTrial.setStratifyTrialSite(trial.isStratifyTrialSite());
		cTrial.setStartDate(trial.getStartDate());
		cTrial.setEndDate(trial.getEndDate());

		ArrayList<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		for (TreatmentArm arm : trial.getTreatmentArms()) {
			TreatmentArm cArm = new TreatmentArm();
			cArm.setName(arm.getName());
			cArm.setPlannedSubjects(arm.getPlannedSubjects());
			cArm.setId(id++);
			cArm.setTrial(cTrial);
			arms.add(cArm);
		}
		cTrial.setTreatmentArms(arms);
		for (DistributionSubjectProperty dsp: properties) {
			AbstractCriterion<?, ?> cr = dsp.getCriterion();
			if (DateCriterion.class.isInstance(cr)) {
				DateCriterion ccr = new DateCriterion();
				ccr.setInclusionConstraint(cr.getInclusionConstraint());
				for (DateConstraint co : DateCriterion.class.cast(cr).getStrata()) {
					DateConstraint cco = new DateConstraint();
					cco.setFirstDate(co.getFirstDate());
					cco.setSecondDate(co.getSecondDate());
					cco.setId(id++);
					ccr.addStrata(cco);
				}
				cTrial.addCriterion(ccr);
			} else if (DichotomousCriterion.class.isInstance(cr)) {
				DichotomousCriterion ccr = new DichotomousCriterion();
				ccr.setInclusionConstraint(cr.getInclusionConstraint());
				ccr.setOption1(DichotomousCriterion.class.cast(cr).getOption1());
				ccr.setOption2(DichotomousCriterion.class.cast(cr).getOption2());
				for (DichotomousConstraint co : DichotomousCriterion.class.cast(cr)
						.getStrata()) {
					ArrayList<String> values = new ArrayList<String>();
					values.add(co.getExpectedValue());
					DichotomousConstraint cco;
					try {
						cco = new DichotomousConstraint(values);
						cco.setId(id++);
						ccr.addStrata(cco);
					} catch (ContraintViolatedException e) {
					}
				}
				cTrial.addCriterion(ccr);
			} else if (OrdinalCriterion.class.isInstance(cr)) {
				OrdinalCriterion ccr = new OrdinalCriterion();
				ccr.setInclusionConstraint(cr.getInclusionConstraint());
				ccr.setElements(OrdinalCriterion.class.cast(cr).getElements());
				for (OrdinalConstraint co : OrdinalCriterion.class.cast(cr).getStrata()) {
					ArrayList<String> values = new ArrayList<String>(co
							.getExpectedValues());
					OrdinalConstraint cco;
					try {
						cco = new OrdinalConstraint(values);
						cco.setId(id++);
						ccr.addStrata(cco);
					} catch (ContraintViolatedException e) {
					}
				}
				cTrial.addCriterion(ccr);
			}

		}
		cTrial.setRandomizationConfiguration(trial.getRandomizationConfiguration());
		cTrial.getRandomizationConfiguration().setTrial(cTrial);
		cTrial.getRandomizationConfiguration().resetAlgorithm();
		return cTrial;
	}
	
	
	public long estimateSimulationDuration(Trial trial, List<DistributionSubjectProperty> properties, AbstractDistribution<TrialSite> distributionTrialSites, int runs, long maxTime){
		SimulationResult result = simulateTrial(trial, properties, distributionTrialSites, 30, maxTime);
		long time = 0;
		for(int i =10 ; i<30;i++){
			time+= result.getRuns().get(i).getTime();
		}
		time = ((time /20)*runs) / 1000000;
        return time;
	 }

	private static Trial resetTrial(Trial trial) {
		for (TreatmentArm arm : trial.getTreatmentArms()) {
			arm.getSubjects().clear();
		}
		trial.getRandomizationConfiguration().setTrial(trial);
		trial.getRandomizationConfiguration().setTempData(null);
		trial.getRandomizationConfiguration().resetAlgorithm();
		if(MinimizationConfig.class.isInstance(trial.getRandomizationConfiguration())){
			((Minimization) trial.getRandomizationConfiguration().getAlgorithm()).clear();
		}
		return trial;
	}

	public static TrialSubject generateTrialSubject(List<DistributionSubjectProperty> properties, TrialSubject oldSubject) {
		oldSubject.setProperties(null);
		HashSet<SubjectProperty<?>> tempSet = new HashSet<SubjectProperty<?>>();
		for (DistributionSubjectProperty dsp :properties) {
			SubjectProperty<Serializable> pr = new SubjectProperty<Serializable>(
					dsp.getCriterion());
			try {
				pr.setValue(dsp.getNextSubjectValue());
			} catch (ContraintViolatedException e) {
			}
			tempSet.add(pr);
		}
		oldSubject.setProperties(tempSet);
		return oldSubject;
	}
}
