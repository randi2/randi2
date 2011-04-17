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
package de.randi2.model.randomization;

import static de.randi2.utility.IntegerIterator.upto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

import de.randi2.model.AbstractDomainObject;
import de.randi2.model.TreatmentArm;

/**
 * @author Natalie Waskowzow 
 *
 */

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class ResponseAdaptiveUrn extends AbstractDomainObject {

	private static final long serialVersionUID = -2472302592410115052L;
	
	
	@ManyToMany
	@JoinTable(name = "ResponseAdaptiveUrn_Treatmentarm", 
			joinColumns = {	@JoinColumn(name = "ResponseAdaptiveUrn_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "Treatmentarm_id") })
	private List<TreatmentArm> responseAdaptiveUrn = new ArrayList<TreatmentArm>();
	
	public static ResponseAdaptiveUrn generate(ResponseAdaptiveRandomizationConfig config){
		ResponseAdaptiveUrn responseAdaptiveUrn = new ResponseAdaptiveUrn();
		Set<TreatmentArm> arms = config.getTrial().getTreatmentArms();
	    int initializeCount = config.getInitializeCountBalls();
		if (initializeCount != 0) {
			for (int i : upto(initializeCount)) {
				for (TreatmentArm arm : arms) {
					responseAdaptiveUrn.add(arm);
				}
			}
		}
		return responseAdaptiveUrn;
	}
	
	
	public void add(TreatmentArm arm){
		responseAdaptiveUrn.add(arm);
	}
	
	public TreatmentArm drawFromUrn(ResponseAdaptiveRandomizationConfig config, Random rand) {
		if (responseAdaptiveUrn.size() != 0) {
			return responseAdaptiveUrn.remove(rand.nextInt(responseAdaptiveUrn
					.size()));
		} else {
			TreatmentArm drawnArm = new TreatmentArm();
            double randomNumber = rand.nextDouble();
            Set<TreatmentArm> arms = config.getTrial().getTreatmentArms();
            int  i=0;
            for(TreatmentArm arm: arms){
            	i++;
            	if((randomNumber<(i/arms.size())) && (randomNumber>=(i-1)/arms.size())){
            		drawnArm=arm;
            		break;
            	}
            }
            return drawnArm;
		}
	}

}
