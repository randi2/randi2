package de.randi2.randomization;

import static de.randi2.utility.IntegerIterator.upto;

import java.util.Random;
import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.Urn;
import de.randi2.model.randomization.UrnDesignConfig;
import de.randi2.model.randomization.UrnDesignTempData;

/**
 * Implementation of wei's urn design (replace a drawn ball with k balls of the other color)
 * 
 * @author dschrimpf
 */
public class UrnDesign extends RandomizationAlgorithm<UrnDesignConfig> {

	public UrnDesign(Trial trial) {
		super(trial);
	}
	
	public UrnDesign(Trial trial, long seed) {
		super(trial, seed);
	}


	@Override
	protected TreatmentArm doRadomize(TrialSubject subject, Random random) {
		UrnDesignTempData tempData = super.configuration.getTempData();
		String stratum = "";
		if(trial.isStratifyTrialSite()) stratum = subject.getTrialSite().getId() + "";
		stratum += subject.getStratum();
		Urn urn = tempData.getUrn(stratum);
		if (urn == null) {
			urn = Urn.generate(configuration);
			tempData.setUrn(stratum, urn);
		}
		TreatmentArm drawnArm = urn.drawFromUrn(random);
		//TODO now only for two arms
		if(drawnArm.getName().equals(trial.getTreatmentArms().get(0).getName())){
			for(int i : upto(configuration.getCountReplacedBalls())){
				urn.add(trial.getTreatmentArms().get(1));
			}
		}else{
			for(int i : upto(configuration.getCountReplacedBalls())){
				urn.add(trial.getTreatmentArms().get(0));
			}
		}
		return drawnArm;
	}

}
