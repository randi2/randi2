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
package de.randi2.model.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CollectionOfElements;

import de.randi2.model.criteria.constraints.OrdinalConstraint;
import de.randi2.unsorted.ContraintViolatedException;

/**
 * <p>
 * This class represents an ordinal scale or a set of some not computable
 * properties of a trial subject. (If you are looking for a criterion meant for
 * numerical data @see de.randi2.model.criteria.NumericCriteion)
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 * 
 */
@Entity
public class OrdinalCriterion extends
		AbstractCriterion<String, OrdinalConstraint> {

	private static final long serialVersionUID = -1596645059608735663L;
	/**
	 * List object storing the possible values.
	 */
	@CollectionOfElements(fetch=FetchType.EAGER)
	@Getter @Setter
	private List<String> elements;

	public OrdinalCriterion() {
		elements = new ArrayList<String>();
		for (int i = 0; i < 3; i++) {
			elements.add("");
		}
	}

	@Override
	public List<String> getConfiguredValues() {
		if(elements == null || elements.size() == 0){
			return null;
		}
		boolean configured = true;
		for (String s : elements) {
			configured = !(s.isEmpty() || s.equals(""));
		}
		if (configured) {
			return elements;
		}
		return null;
	}

	@Override
	public void isValueCorrect(String value) throws ContraintViolatedException {
		if(!elements.contains(value)){
			throw new ContraintViolatedException();
		}		
	}
	
	@Override
	public Class<OrdinalConstraint> getContstraintType() {
		return OrdinalConstraint.class;
	}
	
	
	@Override
	public void setInclusionConstraint(OrdinalConstraint inclusionConstraint)
			throws ContraintViolatedException {
		if(inclusionConstraint == null || elements.containsAll(inclusionConstraint.expectedValues)){
			this.inclusionConstraint = inclusionConstraint;
		}else{
			throw new ContraintViolatedException();
		}
	}


}
