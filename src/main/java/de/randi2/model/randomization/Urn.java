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

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.TreatmentArm;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class Urn  extends AbstractDomainObject{
	private static final long serialVersionUID = -7375855875576682823L;
	
	public static Urn generate(UrnDesignConfig config){
		Urn urn = new Urn();
		List<TreatmentArm> arms = config.getTrial().getTreatmentArms();
		for(int i : upto(config.getInitializeCountBalls())){
			for (TreatmentArm arm : arms){
				urn.add(arm);
			}
		}
		return urn;
	}
	
	@ManyToMany
	@JoinTable(name = "Urn_Treatmentarm", 
			joinColumns = {	@JoinColumn(name = "Urn_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "Treatmentarm_id") })
	private List<TreatmentArm> urn = new ArrayList<TreatmentArm>();
	
	public void add(TreatmentArm arm){
		urn.add(arm);
	}
	
	public TreatmentArm drawFromUrn(Random rand) {
		return urn.remove(rand.nextInt(urn.size()));
	}
}
