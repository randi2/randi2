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
package de.randi2.simulation.model;

import java.io.Serializable;

import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.simulation.distribution.AbstractDistribution;

/**
 * Wrapper class between distribution with subject constraint and the criterion
 * with the constraints.
 * 
 * @author Daniel Schrimpf <ds@randi2.de>
 * 
 */
public class DistributionSubjectProperty {

	public DistributionSubjectProperty(
			AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> criterion,
			AbstractDistribution<?> distribution) {
		super();
		this.criterion = criterion;
		this.distribution = distribution;
	}

	private AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> criterion;

	private AbstractDistribution<?> distribution;

	/**
	 * Return one element, if the method is called several times the elements are
	 * special distributed (the distribution of the distribution class).
	 * 
	 * @return The next element
	 */
	public Serializable getNextSubjectValue() {
		return distribution.getNextValue();
	}

	public AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> getCriterion() {
		return criterion;
	}

	public void setCriterion(
			AbstractCriterion<? extends Serializable, ? extends AbstractConstraint<? extends Serializable>> criterion) {
		this.criterion = criterion;
	}

}
