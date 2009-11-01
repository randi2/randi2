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

import java.util.Iterator;

/**
 *
 * @author jthoenes
 */
public class IntegerIterator implements Iterator<Integer>, Iterable<Integer>{

	private int current = 0;
	private int upto;

	public IntegerIterator(int _upto){
		this.upto = _upto;
	}

	@Override
	public boolean hasNext() {
		return current < upto;
	}

	@Override
	public Integer next() {
		return Integer.valueOf(current++);
	}

	@Override
	public void remove() {
		next();
	}

	@Override
	public Iterator<Integer> iterator() {
		return this;
	}

	public static IntegerIterator upto(int n){
		return new IntegerIterator(n);
	}

}
