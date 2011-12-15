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

import de.randi2.model.criteria.constraints.FreeTextConstraint;
import de.randi2.unsorted.ConstraintViolatedException;

/**
 * 
 * 
 * @author dschrimpf
 */
@Entity
public class FreeTextCriterion extends AbstractCriterion<String, FreeTextConstraint>{

	private static final long serialVersionUID = 7359750785478879268L;

	@Override
	public void isValueCorrect(String value) throws ConstraintViolatedException {
		if(value == null || value.length()==0){
			throw new ConstraintViolatedException();
		}
		if(inclusionConstraint!=null){
			inclusionConstraint.isValueCorrect(value);
		}
		
	}
	
	@Override
	public List<String> getConfiguredValues() {
		return new ArrayList<String>();
	}
	
	@Override
	public Class<FreeTextConstraint> getContstraintType() {
		return FreeTextConstraint.class;
	}
	
	@Override
	public void setInclusionConstraint(FreeTextConstraint inclusionConstraint) throws ConstraintViolatedException {
			this.inclusionConstraint = inclusionConstraint;
	}
	
}
