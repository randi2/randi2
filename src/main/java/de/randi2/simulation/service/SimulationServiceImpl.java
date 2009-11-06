package de.randi2.simulation.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

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
import de.randi2.simulation.model.DistributionSubjectProperty;
import de.randi2.simulation.model.SimulationResult;
import de.randi2.simulation.model.SimulationRun;
import de.randi2.simulation.model.distribution.AbstractDistribution;
import de.randi2.unsorted.ContraintViolatedException;


public class SimulationServiceImpl implements SimulationService {

	@Override
	public SimulationResult simulateTrial(Trial trial, List<DistributionSubjectProperty> properties, AbstractDistribution distributionTrialSites, int runs, long maxTime) {
		Random random = new Random();
		Trial copyTrial = copyAndPrepareTrial(trial, properties);
		SimulationResult simResult = new SimulationResult(trial.getTreatmentArms());
		long startTime;
		TreatmentArm assignedArm;
		TrialSubject subject = new TrialSubject();
		ArrayList<TrialSite> pSites = new ArrayList<TrialSite>(copyTrial
				.getParticipatingSites());
		for (int run = 0; run < runs; run++) {
			startTime = System.nanoTime();
			Trial simTrial = resetTrial(copyTrial);
			SimulationRun simRun = simResult.getEmptyRun();
			for (int i = 0; i < simTrial.getPlannedSubjectAmount(); i++) {
				 subject = generateTrialSubject(properties, subject);
				subject.setTrialSite(pSites.get(distributionTrialSites.getNextInt(pSites.size())));
				assignedArm = simTrial
						.getRandomizationConfiguration().getAlgorithm()
						.randomize(subject);
				subject.setArm(assignedArm);
				subject.setRandNumber(i + "_" + assignedArm.getName());
				subject.setCounter(i);
				subject.setIdentification(subject.getRandNumber());
				assignedArm.addSubject(subject);
				for(TreatmentArm arm :simTrial.getTreatmentArms()){
					if(arm.getName().equals(assignedArm.getName())){
						arm.addSubject(subject);
					}
				}
			}
			for(int i = 0; i<simTrial.getTreatmentArms().size();i++){
				simRun.getSubjectsPerArms()[i] = simTrial.getTreatmentArms().get(i).getCurrentSubjectsAmount();
			}
			simRun.setTime((System.nanoTime()-startTime));
			simResult.addSimulationRun(simRun);
		}
		return simResult;
	}

	private static Trial copyAndPrepareTrial(Trial trial,  List<DistributionSubjectProperty> properties) {
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
			if (cr instanceof DateCriterion) {
				DateCriterion ccr = new DateCriterion();
				ccr.setId(id++);
				ccr.setInclusionConstraint(cr.getInclusionConstraint());
				for (DateConstraint co : ((DateCriterion) cr).getStrata()) {
					DateConstraint cco = new DateConstraint();
					cco.setFirstDate(co.getFirstDate());
					cco.setSecondDate(co.getSecondDate());
					cco.setId(id++);
					ccr.addStrata(cco);
				}
				cTrial.addCriterion(ccr);
			} else if (cr instanceof DichotomousCriterion) {
				DichotomousCriterion ccr = new DichotomousCriterion();
				ccr.setId(id++);
				ccr.setInclusionConstraint(cr.getInclusionConstraint());
				ccr.setOption1(((DichotomousCriterion) cr).getOption1());
				ccr.setOption2(((DichotomousCriterion) cr).getOption2());
				for (DichotomousConstraint co : ((DichotomousCriterion) cr)
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
			} else if (cr instanceof OrdinalCriterion) {
				OrdinalCriterion ccr = new OrdinalCriterion();
				ccr.setId(id++);
				ccr.setInclusionConstraint(cr.getInclusionConstraint());
				ccr.setElements(((OrdinalCriterion)cr).getElements());
				for (OrdinalConstraint co : ((OrdinalCriterion) cr).getStrata()) {
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
		return cTrial;
	}

	private static Trial resetTrial(Trial trial) {
		int id = 0;
		ArrayList<TreatmentArm> arms = new ArrayList<TreatmentArm>();
		for (TreatmentArm arm : trial.getTreatmentArms()) {
			TreatmentArm cArm = new TreatmentArm();
			cArm.setName(arm.getName());
			cArm.setPlannedSubjects(arm.getPlannedSubjects());
			cArm.setId(id++);
			cArm.setTrial(trial);
			arms.add(cArm);
		}
		trial.setTreatmentArms(arms);
		return trial;
	}

	private static TrialSubject generateTrialSubject(List<DistributionSubjectProperty> properties, TrialSubject oldSubject) {
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
