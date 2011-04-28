/* 
 * (c) 2008- RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
package de.randi2.randomization;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.ResponseAdaptiveRConfig;
import de.randi2.model.randomization.ResponseAdaptiveRandomizationTempData;
import de.randi2.model.randomization.ResponseAdaptiveUrn;

/**
 * @author Natalie Waskowzow
 *
 */
public class ResponseAdaptiveRandomization extends RandomizationAlgorithm<ResponseAdaptiveRConfig> {

	public ResponseAdaptiveRandomization(Trial trial) {
		super(trial);
	}
	public ResponseAdaptiveRandomization(Trial trial, long seed) {
		super(trial, seed);
	}

	@Override
	protected TreatmentArm doRadomize(TrialSubject subject, Random random) {
		ResponseAdaptiveRandomizationTempData tempData = (ResponseAdaptiveRandomizationTempData) super.configuration.getTempData();
		String stratum = "";
		if(trial.isStratifyTrialSite()) 
			stratum = subject.getTrialSite().getId() + "__";
		stratum += subject.getStratum();
		ResponseAdaptiveUrn responseAdaptiveUrn = tempData.getResponseAdaptiveUrn(stratum);
		if (responseAdaptiveUrn == null) {
			responseAdaptiveUrn = ResponseAdaptiveUrn.generate(configuration);
			tempData.setResponseAdaptiveUrn(stratum, responseAdaptiveUrn);
		}
		TreatmentArm drawnArm = responseAdaptiveUrn.drawFromUrn(configuration,random);
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>(trial.getTreatmentArms());
	    for(TreatmentArm arm: arms){
			if (drawnArm.getName().equals(arm.getName())) {
				responseAdaptiveUrn.add(arm);
			}
	    }
		return drawnArm;
	}
	
	public void addResponse(TrialSubject subject){
		ResponseAdaptiveRandomizationTempData tempData = (ResponseAdaptiveRandomizationTempData) super.configuration.getTempData();
		String stratum = "";
		if(trial.isStratifyTrialSite()) 
			stratum = subject.getTrialSite().getId() + "__";
		stratum += subject.getStratum();
		ResponseAdaptiveUrn responseAdaptiveUrn = tempData.getResponseAdaptiveUrn(stratum);
		TreatmentArm arm = subject.getArm();
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>(trial.getTreatmentArms());
		arms.remove(arm);
		String response = subject.getResponseProperty().getValue();
		if (response.equals(trial.getTreatmentResponse().getOption1())) {
			for (int i = 0; i < configuration.getCountBallsResponseSuccess(); i++) {
				responseAdaptiveUrn.add(arm);
			}
			int countBallsOtherArms = configuration
					.getCountBallsResponseFailure() / (arms.size());
			for (TreatmentArm tArm : arms) {
				for (int i = 0; i < countBallsOtherArms; i++) {
					responseAdaptiveUrn.add(tArm);
				}
			}
		} else {
			for (int i = 0; i < configuration.getCountBallsResponseFailure(); i++) {
				responseAdaptiveUrn.add(arm);
			}
			int countBallsOtherArms = configuration
					.getCountBallsResponseSuccess() / (arms.size());
			for (TreatmentArm tArm : arms) {
				for (int i = 0; i < countBallsOtherArms; i++) {
					responseAdaptiveUrn.add(tArm);
				}
			}
		}
	}

}
