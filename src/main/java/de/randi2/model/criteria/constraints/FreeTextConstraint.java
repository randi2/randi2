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
package de.randi2.model.criteria.constraints;

import java.util.List;

import de.randi2.unsorted.ContraintViolatedException;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
public class FreeTextConstraint extends AbstractConstraint<String>{


	private static final long serialVersionUID = 992550887900777486L;
	
	@Getter @Setter
	private String expectedValue;
	

	public FreeTextConstraint(){
		
	}
	
	public FreeTextConstraint(List<String> args)
			throws ContraintViolatedException {
		super(args);
	}

	
	@Override
	protected void configure(List<String> args)
			throws ContraintViolatedException {	
		if (args == null || args.size() != 1)
			throw new ContraintViolatedException();
		this.expectedValue = args.get(0);
	}

	
	
	@Override
	public void isValueCorrect(String _value) throws ContraintViolatedException {
		if (!expectedValue.equals(_value)) {
			throw new ContraintViolatedException();
		}
		
	}
	
	@Override
	public String getUIName() {
		return expectedValue;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((expectedValue == null) ? 0 : expectedValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FreeTextConstraint other = (FreeTextConstraint) obj;
		if (expectedValue == null) {
			if (other.expectedValue != null)
				return false;
		} else if (!expectedValue.equals(other.expectedValue))
			return false;
		return true;
	}
	
}
