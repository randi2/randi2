package de.randi2.utility;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;

import de.randi2.model.AbstractDomainObject;

public abstract class ManyToManyList<E extends AbstractDomainObject> extends
		AbstractList<E> {

	ArrayList<E> list = new ArrayList<E>();

	/**
	 * Override this method to implement the cross-propagation between two
	 * Many-to-Many Lists.
	 * 
	 * @param element
	 *            The element added to this list.
	 * @return <code>true</code> if successfull.
	 */
	public abstract boolean propagateAdd(E element);

	/**
	 * Override this method to implement the cross-propagation between two
	 * Many-to-Many Lists.
	 * 
	 * @param element
	 *            The element added to this list.
	 * @return <code>true</code> if successfull.
	 * @throws ClassCastException
	 *             Will be transformed into not sucessfull.
	 */
	public abstract boolean propagateRemove(Object element)
			throws ClassCastException;

	/**
	 * This Methods adds an Element to the list and propagates it to
	 * corresponding List, <b>if the element does not yet exists within the
	 * list.</b> If that is not the case, nothing will happen but
	 * <code>true</code> will be returns.
	 * 
	 * @param element
	 *            The element to be added to the list.
	 * @return <code>true</code> if the element is part of the list.
	 */
	@Override
	public boolean add(E element) {
		if (list.contains(element)) {
			return true;
		} else {
			// If the propagation is successfull
			// The add of the element will be tried
			return propagateAdd(element) && list.add(element);
		}
	}

	/**
	 * This Methods removes an Element to the list and propagates it to
	 * corresponding List, <b>if the element exists within the list.</b> If
	 * that is not the case, nothing will happen but <code>true</code> will be
	 * returns.
	 * 
	 * @param element
	 *            The element to be added to the list.
	 * @return <code>true</code> if the element is part of the list.
	 */
	@Override
	public boolean remove(Object element) {
		if (list.contains(element)) {
			return true;
		} else {
			// If the propagation is successfull
			// The remove of the element will be tried
			try {
				return propagateRemove(element) && list.remove(element);
			}
			catch (ClassCastException e) {
				return false;
			}
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean successfull = true;
		for (E element : c) {
			successfull = successfull && this.add(element);
		}
		return successfull;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean successfull = true;
		for (Object element : c) {
			successfull = successfull && this.remove(element);
		}
		return successfull;
	}

	@Override
	public E get(int index) {
		return list.get(index);
	}

	@Override
	public int size() {
		return list.size();
	}

}
