package de.randi2.utility;

import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import de.randi2.model.AbstractDomainObject;

public class ManyToManyList<E extends AbstractDomainObject> extends AbstractList<E> {

	ArrayList<E> actualList = new ArrayList<E>();	
	
	
	//public abstract propa
	
	
	
	
	@Override
	public E get(int index) {
		return actualList.get(index);
	}

	@Override
	public int size() {
		return actualList.size();
	}

}
