package de.randi2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.randi2.model.criteria.AbstractCriterion;
import de.randi2.unsorted.ContraintViolatedException;
import de.randi2.utility.StratumProc;

public final class SubjectProperty<V extends Serializable> extends AbstractDomainObject {

	private static final long serialVersionUID = 6795792982229806832L;
	private V value;
	private AbstractCriterion<V> criterion;
	private List<Object> possibleValues = new ArrayList<Object>();
	private StratumProc stratumProc = StratumProc.noStratification();

	public void setStratumComputation(StratumProc p) {
		this.stratumProc = p;
	}

	public void addPossibleValue(Object o) {
		possibleValues.add(o);
	}

	public List<Object> getPossibleValues() {
		return possibleValues;
	}

	public int getStratum() {
		return stratumProc.stratify(this.value);
	}

	// Get- and Set Methods
	public V getValue() {
		return value;
	}

	public void setValue(V value) throws ContraintViolatedException {
		this.checkConstraints(value);
		this.value = value;
	}

	// Check-Methoden
	private void checkConstraints(V value) throws ContraintViolatedException {
		this.checkPossibleValues(value);
	}

	private void checkPossibleValues(V value) throws ContraintViolatedException {
		if (!possibleValues.isEmpty() && !possibleValues.contains(value)) {
			throw new ContraintViolatedException(); // TODO nice constraint violation message
		}
	}

	public AbstractCriterion<V> getCriterion() {
		return criterion;
	}

	public void setCriterion(AbstractCriterion<V> criterion) {
		this.criterion = (AbstractCriterion<V>) criterion;
		criterion.applyConstraints((SubjectProperty<V>) this);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "TO_STRING_NOT_IMPLEMENTED";
	}
}
