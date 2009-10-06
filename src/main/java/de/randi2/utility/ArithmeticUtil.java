/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
	public static Set<Set<String>> cartesianProduct(Set<String>... sets) {
	    if (sets.length < 2)
	        throw new IllegalArgumentException(
	                "Can't have a product of fewer than two sets (got " +
	                sets.length + ")");

	    return _cartesianProduct(0, sets);
	}

	private static Set<Set<String>> _cartesianProduct(int index, Set<String>... sets) {
	    Set<Set<String>> ret = new HashSet<Set<String>>();
	    if (index == sets.length) {
	        ret.add(new HashSet<String>());
	    } else {
	        for (String obj : sets[index]) {
	            for (Set<String> set : _cartesianProduct(index+1, sets)) {
	                set.add(obj);
	                ret.add(set);
	            }
	        }
	    }
	    return ret;
	}

}
