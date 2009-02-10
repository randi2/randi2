/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.randi2.model.criteria;

import javax.persistence.Embeddable;

@Embeddable
public class DichotomousConstraints extends AbstractConstraints<String> {

	public String expectedValue;

	@Override
	public boolean checkValue(String _value) {
		return expectedValue.equals(_value);
	}

	public String getExpectedValue() {
		return expectedValue;
	}

	public void setExpectedValue(String expectedValue) {
		this.expectedValue = expectedValue;
	}
}
