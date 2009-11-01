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

import static de.randi2.utility.ArithmeticUtil.ggt;
import static de.randi2.utility.IntegerIterator.upto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.randi2.model.TreatmentArm;
import de.randi2.model.Trial;
import de.randi2.model.TrialSubject;
import de.randi2.model.randomization.Block;
import de.randi2.model.randomization.TruncatedBinomialDesignConfig;

public class TruncatedBinomialDesign extends
		RandomizationAlgorithm<TruncatedBinomialDesignConfig> {

	public TruncatedBinomialDesign(Trial trial) {
		super(trial);
	}

	protected TruncatedBinomialDesign(Trial trial, long seed) {
		super(trial, seed);
	}

	@Override
	protected TreatmentArm doRadomize(TrialSubject subject, Random random) {
		List<TreatmentArm> possibleArms = new ArrayList<TreatmentArm>();
		for(TreatmentArm arm : trial.getTreatmentArms()){
			if(arm.getCurrentSubjectsAmount() < arm.getPlannedSubjects()){
				possibleArms.add(arm);
			}
		}
		if(possibleArms.isEmpty()){
			return null;
		}else{
			Block block = new Block();
		
			int[] sizes = new int[possibleArms.size()];
			int i = 0;
			for (TreatmentArm arm : possibleArms) {
				sizes[i] = arm.getPlannedSubjects();
				i++;
			}

			int divide = sizes[0];
			for (i = 1; i < sizes.length; i++) {
				divide = ggt(divide, sizes[i]);
			}

			for (TreatmentArm arm : possibleArms) {
				int size = arm.getPlannedSubjects() / divide;
				for (int j : upto(size)) {
					block.add(arm);
				}
			}
			
			return block.pullFromBlock(random);
		}
	}

}
