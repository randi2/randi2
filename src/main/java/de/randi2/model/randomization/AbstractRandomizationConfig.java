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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Trial;
import de.randi2.randomization.RandomizationAlgorithm;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "RandomizationConfig")
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true, exclude={"trial"})
public abstract class AbstractRandomizationConfig extends AbstractDomainObject {

	private static final long serialVersionUID = -942332706403245140L;
	@OneToOne
	@Getter 
	private Trial trial;
	@Transient
	private RandomizationAlgorithm<? extends AbstractRandomizationConfig> algorithm;
	@Transient
	private final Long seed;
	
	
	/**
	 * if seed == null create a unseeded algorithm 
	 * @param seed
	 */
	public AbstractRandomizationConfig(Long seed){
		this.seed = seed;
	}
	
	
	@Getter @Setter
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	protected AbstractRandomizationTempData tempData;
	
	
	public final RandomizationAlgorithm<? extends AbstractRandomizationConfig> getAlgorithm() {
		if (algorithm == null) {
			if(seed == null){
				algorithm = createAlgorithm();
			}else{
				algorithm = createAlgorithm(seed);
			}
		}
		return algorithm;
	}

	public abstract RandomizationAlgorithm<? extends AbstractRandomizationConfig> createAlgorithm();
	
	public abstract RandomizationAlgorithm<? extends AbstractRandomizationConfig> createAlgorithm(long seed);


	public void setTrial(Trial _trial) {
		this.trial = _trial;
		if (trial != null && trial.getRandomizationConfiguration() == null) {
			trial.setRandomizationConfiguration(this);
		}
	}
	
	/**
	 * This method resets the algorithm
	 * @param seed
	 */
	public void resetAlgorithm(){
		if(algorithm!=null && algorithm.isSeeded()){
			algorithm = createAlgorithm(algorithm.getSeed());
		}else{
			algorithm = createAlgorithm();
		}
		
	}
	
	public void resetAlgorithmWithNext(){
		if(algorithm!=null && algorithm.isSeeded()){
			algorithm = createAlgorithm(algorithm.getSeed()+10000);
		}else{
			algorithm = createAlgorithm();
		}
	}
	
	/**
	 * This method resets the algorithm with a new seed value
	 * @param seed
	 */
	public void resetAlgorithm(long seed){
		algorithm = createAlgorithm(seed);
	}
}
