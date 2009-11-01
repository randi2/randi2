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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CollectionOfElements;

import de.randi2.unsorted.ContraintViolatedException;

@Entity
public class OrdinalConstraint extends AbstractConstraint<String> {

	private static final long serialVersionUID = 3642808577019112783L;

	
	protected OrdinalConstraint(){}
	
	public OrdinalConstraint(List<String> args)
			throws ContraintViolatedException {
		super(args);
		// TODO Auto-generated constructor stub
	}

	@CollectionOfElements(fetch=FetchType.EAGER)
	@Getter @Setter
	public Set<String> expectedValues;


	@Override
	public void isValueCorrect(String _value) throws ContraintViolatedException {
		if(!expectedValues.contains(_value)){
			throw new ContraintViolatedException();
		}
		
	}

	@Override
	protected void configure(List<String> args)
			throws ContraintViolatedException {
		if(args == null || args.size() <1){
			throw new ContraintViolatedException();
		}
		this.expectedValues = new HashSet<String>(args);
		
	}
	
	@Override
	public String getUIName() {
		StringBuffer result = new StringBuffer();
		for(String s: expectedValues){
			result.append(s + "|");
		}
		return result.toString();
	}
}
