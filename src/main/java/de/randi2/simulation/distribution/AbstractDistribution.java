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
package de.randi2.simulation.distribution;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

/**
 * This abstract class for an distribution provides methods to get elements in a
 * special distribution.
 * 
 * @author dschrimpf <ds@randi2.de>
 * 
 * @param <E>
 */
public abstract class AbstractDistribution<E extends Serializable> {

	protected List<E> elements;

	protected Random random;

	public AbstractDistribution(List<E> elements, long seed) {
		this.elements = elements;
		random = new Random(seed);
	}

	public AbstractDistribution(List<E> elements) {
		this.elements = elements;
		random = new Random();
	}

	public AbstractDistribution() {
		random = new Random();
	}

	public AbstractDistribution(long seed) {
		random = new Random(seed);
	}

	public List<E> getElements() {
		return elements;
	}

	/**
	 * Returns a element, if you call this method several times you get the
	 * elements in a special distribution.
	 * 
	 * @return a element in a special distribution
	 */
	public abstract E getNextValue();
}
