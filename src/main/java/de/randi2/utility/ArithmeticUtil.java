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

package de.randi2.utility;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author jthoenes
 */
public final class ArithmeticUtil {
	public static int ggt(int x, int y) {
		assert(x > 0);
		assert(y > 0);
		while (x != y) {
			if (x > y) {
				x = x - y;
			} else {
				y = y - x;
			}
		}
		return x;
	}
	
	/**
	 * from http://stackoverflow.com/questions/714108/cartesian-product-of-arbitrary-sets-in-java
	 * @param sets
	 * @return
	 */
	public static <E> Set<Set<E>> cartesianProduct(Set<E>... sets) {
	    if (sets.length < 2)
	        throw new IllegalArgumentException(
	                "Can't have a product of fewer than two sets (got " +
	                sets.length + ")");

	    return _cartesianProduct(0, sets);
	}

	private static <E> Set<Set<E>> _cartesianProduct(int index, Set<E>... sets) {
	    Set<Set<E>> ret = new HashSet<Set<E>>();
	    if (index == sets.length) {
	        ret.add(new HashSet<E>());
	    } else {
	        for (E obj : sets[index]) {
	            for (Set<E> set : _cartesianProduct(index+1, sets)) {
	                set.add(obj);
	                ret.add(set);
	            }
	        }
	    }
	    return ret;
	}

}
