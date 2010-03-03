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
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import de.randi2.utility.Pair;

/**
 * Represented the concrete distribution. The elements you get with the
 * getNextValue() method are concrete distributed.
 * 
 * @author dschrimpf <ds@randi2.de>
 * 
 * @param <E>
 */
public class ConcreteDistribution<E extends Serializable> extends
		AbstractDistribution<E> {

	@Getter
	private int[] ratio;
	private int all = 0;

	public ConcreteDistribution(long seed,
			Pair<E, Integer>... elementRatioPairs) {
		super(seed);
		elements = new ArrayList<E>();
		ratio = new int[elementRatioPairs.length];
		int i = 0;
		for (Pair<E, Integer> pair : elementRatioPairs) {
			elements.add(pair.first());
			all += pair.last();
			ratio[i] = pair.last();
			i++;
		}
	}

	/**
	 * 
	 * @param elementRatioPairs
	 *            A list of pairs with a ration and an element.
	 */
	public ConcreteDistribution(Pair<E, Integer>... elementRatioPairs) {
		elements = new ArrayList<E>();
		ratio = new int[elementRatioPairs.length];
		int i = 0;
		for (Pair<E, Integer> pair : elementRatioPairs) {
			elements.add(pair.first());
			all += pair.last();
			ratio[i] = pair.last();
			i++;
		}
	}

	public ConcreteDistribution(long seed, List<E> elements, int... ratio) {
		super(elements, seed);
		this.ratio = ratio;
		for (int i : ratio) {
			all += i;
		}
	}

	/**
	 * The mapping between the list of elements and the array of ratios is
	 * sequential (thirst element of the list <-> thirst element of the array)
	 * 
	 * @param elements
	 *            List of element
	 * @param ratio
	 *            List of ratios
	 */
	public ConcreteDistribution(List<E> elements, int... ratio) {
		super(elements);
		this.ratio = ratio;
		for (int i : ratio) {
			all += i;
		}
	}

	@Override
	public E getNextValue() {
		double number = random.nextDouble();
		boolean found = false;
		int i = 0;
		double sum = 0.0;
		while (!found && i < ratio.length) {
			sum += ratio[i];
			if ((sum / all) >= number) {
				found = true;
			} else {
				i++;
			}
		}

		return elements.get(i);
	}

}
