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

import javax.persistence.Entity;

import de.randi2.model.AbstractDomainObject;
import de.randi2.unsorted.ContraintViolatedException;

@Entity
public abstract class AbstractConstraint<V extends Object> extends AbstractDomainObject{

	private static final long serialVersionUID = -5608235144658474459L;
	
	public AbstractConstraint(List<V> args) throws ContraintViolatedException{
		configure(args);
	}
	
	protected AbstractConstraint(){}
	
	protected abstract void configure(List<V> args) throws ContraintViolatedException;

	public abstract void isValueCorrect(V _value) throws ContraintViolatedException;

	public boolean checkValue(V value){
		try{
			isValueCorrect(value);
			return true;
		}
		catch(ContraintViolatedException e){
			return false;
		}
	}
}
