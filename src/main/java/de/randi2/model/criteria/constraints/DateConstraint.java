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

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import de.randi2.unsorted.ContraintViolatedException;

@Entity
public class DateConstraint extends AbstractConstraint<GregorianCalendar> {


	private static final long serialVersionUID = -9068633271254996713L;
	
	@Getter @Setter
	private GregorianCalendar firstDate;
	@Getter @Setter
	private GregorianCalendar secondDate;

	public DateConstraint() {

	}

	public DateConstraint(List<GregorianCalendar> args) throws ContraintViolatedException {
		super(args);
	}

	

	
	/* (non-Javadoc)
	 * @see de.randi2.model.criteria.constraints.AbstractConstraint#configure(java.util.List)
	 */
	@Override
	protected void configure(List<GregorianCalendar> list)
			throws ContraintViolatedException {
		if(list==null || list.size() ==0 || list.size()>2){
			throw new ContraintViolatedException();
		}else if (list.size()==1) {
			firstDate = list.get(0);
		}else{
			firstDate = list.get(0);
			secondDate = list.get(1);
		}
	}

	/* (non-Javadoc)
	 * @see de.randi2.model.criteria.constraints.AbstractConstraint#isValueCorrect(java.lang.Object)
	 */
	@Override
	public void isValueCorrect(GregorianCalendar value)
			throws ContraintViolatedException {
		if(value ==null){
			throw new ContraintViolatedException();
		}else if( firstDate!=null && secondDate!=null && (value.before(firstDate) || value.after(secondDate))){
			throw new ContraintViolatedException();
		}else if(firstDate!=null && secondDate==null && value.before(firstDate) ){
			throw new ContraintViolatedException();
		}else if( firstDate==null && secondDate!=null && value.after(secondDate) ){
			throw new ContraintViolatedException();
		}
	}
	
	/* (non-Javadoc)
	 * @see de.randi2.model.AbstractDomainObject#getUIName()
	 */
	@Override
	public String getUIName() {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			if(firstDate!=null && secondDate!=null){
				return (sdf.format(firstDate.getTime()) + "|" + sdf.format(secondDate.getTime()));
			}else if(firstDate!=null){
				return ">" + sdf.format(firstDate.getTime());
			}else if(secondDate!=null){
				return "<" + sdf.format(secondDate.getTime());
			}
			return "ERROR";
	}
}
