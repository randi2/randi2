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

/**
 *
 * @author jthoenes
 */
public class Pair<E extends Object, F extends Object> {
	private E first;
	private F last;

	private Pair(E _first, F _last){
		first = _first;
		last = _last;
	}

        public static <E, F> Pair<E, F> of(E first, F last) {
            return new Pair<E, F>(first, last);
        }


	public E getFirst(){
		return this.first;
	}

	public E first(){
		return getFirst();
	}

	public F getLast(){
		return this.last;
	}

	public F last(){
		return getLast();
	}
}
