/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.randi2.utility;

/**
 *
 * @author jthoenes
 */
public class Pair<E extends Object, F extends Object> {
	private E first;
	private F last;

	public Pair(E _first, F _last){
		first = _first;
		last = _last;
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
