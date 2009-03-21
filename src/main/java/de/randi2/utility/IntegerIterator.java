/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
