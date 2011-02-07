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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.Trial;
import de.randi2.randomization.RandomizationAlgorithm;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "RandomizationConfig")
@ToString(callSuper=true, exclude={"trial"})
public abstract class AbstractRandomizationConfig extends AbstractDomainObject {

	private static final long serialVersionUID = -942332706403245140L;
	@Transient
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
		algorithm=null;
		getAlgorithm();
		
	}
	
	public void resetAlgorithmWithNextSeed(){
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
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((algorithm == null) ? 0 : algorithm.hashCode());
		result = prime * result + ((seed == null) ? 0 : seed.hashCode());
		result = prime * result + ((tempData == null) ? 0 : tempData.hashCode());
		result = prime * result + ((trial == null) ? 0 : trial.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractRandomizationConfig other = (AbstractRandomizationConfig) obj;
		if (algorithm == null) {
			if (other.algorithm != null)
				return false;
		} else if (!algorithm.equals(other.algorithm))
			return false;
		if (seed == null) {
			if (other.seed != null)
				return false;
		} else if (!seed.equals(other.seed))
			return false;
		if (tempData == null) {
			if (other.tempData != null)
				return false;
		} else if (!tempData.equals(other.tempData))
			return false;
		if (trial == null) {
			if (other.trial != null)
				return false;
		} else if (!trial.equals(other.trial))
			return false;
		return true;
	}

	
}
