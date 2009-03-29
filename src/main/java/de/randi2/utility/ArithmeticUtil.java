/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.randi2.utility;

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
}
