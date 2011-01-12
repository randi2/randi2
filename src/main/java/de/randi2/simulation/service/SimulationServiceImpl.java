package de.randi2.simulation.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
import de.randi2.simulation.model.SimulationRawDataEntry;
import de.randi2.simulation.model.SimulationResult;
import de.randi2.simulation.model.SimulationRun;
import de.randi2.unsorted.ContraintViolatedException;
import de.randi2.utility.Pair;

@Service("simulationService")
public class SimulationServiceImpl implements SimulationService {

	@Override
	public SimulationResult simulateTrial(Trial trial,
			List<DistributionSubjectProperty> properties,
			AbstractDistribution<TrialSite> distributionTrialSites, int runs,
			long maxTime, boolean collectRawData) {
		// copy the trial to avoid side effects
		Trial copyTrial;
		try {
			copyTrial = copyAndPrepareTrial(trial, properties,
					distributionTrialSites);
		} catch (ContraintViolatedException e) {
			return null;
		}
		Map<String, String> strataIdsNames = new HashMap<String, String>();
		Pair<List<String>, List<String>> pair = copyTrial.getAllStrataIdsAndNames();
		for(int i =0;i< pair.first().size();i++){
			strataIdsNames.put(pair.first().get(i), pair.last().get(i));
		}
		// initialize the simulation result
		SimulationResult simResult = new SimulationResult(new ArrayList<TreatmentArm>(copyTrial.getTreatmentArms()), copyTrial.getRandomizationConfiguration(), strataIdsNames);
		long startTime;
		TreatmentArm assignedArm;
		TrialSubject subject = new TrialSubject();
		long endTime = System.currentTimeMillis() + maxTime;
		// loop over all simulation runs
		for (int run = 0; run < runs; run++) {
			if (System.currentTimeMillis() < endTime) {
				startTime = System.currentTimeMillis();
				Trial simTrial = resetTrial(copyTrial);
				SimulationRun simRun = simResult.getEmptyRun();
				// loop over all trial subjects to randomize
				for (int i = 0; i < simTrial.getPlannedSubjectAmount(); i++) {
					// generate the subject properties
					subject = generateTrialSubject(properties, subject);
					subject.setTrialSite(distributionTrialSites.getNextValue());

					// randomize the subject an add it to the treatment arm
					assignedArm = simTrial.getRandomizationConfiguration()
							.getAlgorithm().randomize(subject);
					subject.setArm(assignedArm);
					subject.setRandNumber(i + "_" + assignedArm.getName());
					subject.setCounter(i);
					subject.setIdentification(subject.getRandNumber());

					if(collectRawData){
						SimulationRawDataEntry entry = new SimulationRawDataEntry();
						entry.setRun(run);
						entry.setCount(i);
						entry.setTreatmentArm(assignedArm.getName());
						entry.setTrialSite(subject.getTrialSite().getName());
						String stratum = "";
						if (trial.isStratifyTrialSite()) {
							stratum = subject.getTrialSite().getId() + "__";
						}
						stratum += subject.getStratum();
						entry.setStratum(stratum);
						simResult.getRawData().add(entry);
					}
					String stratum = "";
					if (trial.isStratifyTrialSite()) {
						stratum = subject.getTrialSite().getId() + "__";
					}
					stratum += subject.getStratum();
					if(!stratum.equals("")){
						Integer count = simRun.getStrataCountsPerArm().get(assignedArm).get(stratum);
						count++;
						simRun.getStrataCountsPerArm().get(assignedArm).put(stratum, count);
					}
					
					
					assignedArm.addSubject(subject);
				}
				// set data for this simulation run and add it to the simulation
				// result
				List<TreatmentArm> arms = new ArrayList<TreatmentArm>(simTrial.getTreatmentArms());
				for (int i = 0; i < simTrial.getTreatmentArms().size(); i++) {
					simRun.getSubjectsPerArms()[i] = arms.get(i)
							.getCurrentSubjectsAmount();
				}
				simRun.setTime((System.currentTimeMillis() - startTime));
				simResult.addSimulationRun(simRun);
			} else {
				break;
			}
		}
			// Analyze the simulation result
//			simResult.analyze();
			return simResult;
	}

	/**
	 * TThis method clones the objects and initializes them (id, ...).
	 * 
	 * @param trial
	 * @param properties
	 * @param distributionTrialSites
	 * @return
	 */
	private static Trial copyAndPrepareTrial(Trial trial,
			List<DistributionSubjectProperty> properties,
			AbstractDistribution<TrialSite> distributionTrialSites) throws ContraintViolatedException{
		long id = 0;
		// copy plain trail data
		Trial cTrial = new Trial();
		cTrial.setId(id++);
		cTrial.setStratifyTrialSite(trial.isStratifyTrialSite());
		cTrial.setStartDate(trial.getStartDate());
		cTrial.setEndDate(trial.getEndDate());

		// clone and copy the patient properties
		for (int i = 0; i < distributionTrialSites.getElements().size(); i++) {
			TrialSite site = distributionTrialSites.getElements().get(i);
			TrialSite cSite = new TrialSite();
			cSite.setName(site.getName());
			cSite.setId(id++);
			distributionTrialSites.getElements().set(i, cSite);
			cTrial.addParticipatingSite(cSite);
		}
		HashSet<TreatmentArm> arms = new HashSet<TreatmentArm>();
		for (TreatmentArm arm : trial.getTreatmentArms()) {
			TreatmentArm cArm = new TreatmentArm();
			cArm.setName(arm.getName());
			cArm.setPlannedSubjects(arm.getPlannedSubjects());
			cArm.setId(id++);
			cArm.setTrial(cTrial);
			arms.add(cArm);
		}
		cTrial.setTreatmentArms(arms);
		for (DistributionSubjectProperty dsp : properties) {
			AbstractCriterion<?, ?> cr = dsp.getCriterion();
			if (DateCriterion.class.isInstance(cr)) {
				DateCriterion ccr = new DateCriterion();
				ccr.setName(cr.getName());
				ccr.setId(id++);
				ccr.setInclusionConstraintAbstract(cr.getInclusionConstraint());
				for (DateConstraint co : DateCriterion.class.cast(cr)
						.getStrata()) {
					DateConstraint cco = new DateConstraint();
					cco.setFirstDate(co.getFirstDate());
					cco.setSecondDate(co.getSecondDate());
					cco.setId(id++);
					ccr.addStrata(cco);
				}
				dsp.setCriterion(ccr);
				cTrial.addCriterion(ccr);
			} else if (DichotomousCriterion.class.isInstance(cr)) {
				DichotomousCriterion ccr = new DichotomousCriterion();
				ccr.setName(cr.getName());
				ccr.setId(id++);
				ccr.setInclusionConstraintAbstract(cr.getInclusionConstraint());
				ccr
						.setOption1(DichotomousCriterion.class.cast(cr)
								.getOption1());
				ccr
						.setOption2(DichotomousCriterion.class.cast(cr)
								.getOption2());
				for (DichotomousConstraint co : DichotomousCriterion.class
						.cast(cr).getStrata()) {
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
				dsp.setCriterion(ccr);
				cTrial.addCriterion(ccr);
			} else if (OrdinalCriterion.class.isInstance(cr)) {
				OrdinalCriterion ccr = new OrdinalCriterion();
				ccr.setName(cr.getName());
				ccr.setId(id++);
				ccr.setInclusionConstraintAbstract(cr.getInclusionConstraint());
				ccr.setElements(OrdinalCriterion.class.cast(cr).getElements());
				for (OrdinalConstraint co : OrdinalCriterion.class.cast(cr)
						.getStrata()) {
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
				dsp.setCriterion(ccr);
				cTrial.addCriterion(ccr);
			}

		}
		// reset the randomization configuration
		cTrial.setRandomizationConfiguration(trial
				.getRandomizationConfiguration());
		cTrial.getRandomizationConfiguration().setTrial(cTrial);
		cTrial.getRandomizationConfiguration().resetAlgorithm();
		return cTrial;
	}

	//	
	// public long estimateSimulationDuration(Trial trial,
	// List<DistributionSubjectProperty> properties,
	// AbstractDistribution<TrialSite> distributionTrialSites, int runs, long
	// maxTime){
	// SimulationResult result = simulateTrial(trial, properties,
	// distributionTrialSites, 30, maxTime);
	// long time = 0;
	// for(int i =10 ; i<30;i++){
	// time+= result.getRuns().get(i).getTime();
	// }
	// time = ((time /20)*runs) / 1000000;
	// return time;
	// }

	/**
	 * Reset the passed trial.
	 */
	private static Trial resetTrial(Trial trial) {
		for (TreatmentArm arm : trial.getTreatmentArms()) {
			arm.getSubjects().clear();
		}
		trial.getRandomizationConfiguration().setTrial(trial);
		trial.getRandomizationConfiguration().setTempData(null);
		trial.getRandomizationConfiguration().resetAlgorithmWithNext();
		if (MinimizationConfig.class.isInstance(trial
				.getRandomizationConfiguration())) {
			((Minimization) trial.getRandomizationConfiguration()
					.getAlgorithm()).clear();
		}
		return trial;
	}

	/**
	 * Generate a trial subject with the distribution of the subject properties.
	 * 
	 * @param properties
	 *            The subject properties with the distribution.
	 * @param oldSubject
	 *            The old trialSubject object.
	 * @return The reseted and changed trial subject object.
	 */
	public static TrialSubject generateTrialSubject(
			List<DistributionSubjectProperty> properties,
			TrialSubject oldSubject) {
		oldSubject.setProperties(null);
		HashSet<SubjectProperty<?>> tempSet = new HashSet<SubjectProperty<?>>();
		for (DistributionSubjectProperty dsp : properties) {
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
