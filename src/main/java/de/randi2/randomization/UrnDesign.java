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

import static de.randi2.utility.IntegerIterator.upto;

import java.util.ArrayList;
import java.util.List;
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
		UrnDesignTempData tempData = (UrnDesignTempData) super.configuration.getTempData();
		String stratum = "";
		if(trial.isStratifyTrialSite()) stratum = subject.getTrialSite().getId() + "__";
		stratum += subject.getStratum();
		Urn urn = tempData.getUrn(stratum);
		if (urn == null) {
			urn = Urn.generate(configuration);
			tempData.setUrn(stratum, urn);
		}
		TreatmentArm drawnArm = urn.drawFromUrn(random);
		List<TreatmentArm> arms = new ArrayList<TreatmentArm>(trial.getTreatmentArms());
		//TODO now only for two arms
		if(drawnArm.getName().equals(arms.get(0).getName())){
			for(int i : upto(configuration.getCountReplacedBalls())){
				urn.add(arms.get(1));
			}
		}else{
			for(int i : upto(configuration.getCountReplacedBalls())){
				urn.add(arms.get(0));
			}
		}
		return drawnArm;
	}

}
