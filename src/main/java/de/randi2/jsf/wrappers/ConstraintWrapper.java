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
package de.randi2.jsf.wrappers;

import java.io.Serializable;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.faces.event.ValueChangeEvent;

import lombok.Getter;
import lombok.Setter;
import de.randi2.model.criteria.OrdinalCriterion;
import de.randi2.model.criteria.constraints.AbstractConstraint;
import de.randi2.model.criteria.constraints.DateConstraint;
import de.randi2.model.criteria.constraints.OrdinalConstraint;
import de.randi2.model.exceptions.BoxedException;
import de.randi2.unsorted.ContraintViolatedException;

public class ConstraintWrapper<V extends Serializable> {
	
	@Getter @Setter
	private AbstractConstraint<V> wrappedConstraint = null;
	
	@Getter @Setter
	private int groupNr = 0;
	
	public ConstraintWrapper(int nr){
		groupNr = nr;
	}

    public ConstraintWrapper(int nr, AbstractConstraint<V> _constraint){
        groupNr = nr;
        wrappedConstraint = _constraint;
        if(DateConstraint.class.isInstance(_constraint)){
           date1 = DateConstraint.class.cast(wrappedConstraint).getFirstDate();
           date2 = DateConstraint.class.cast(wrappedConstraint).getSecondDate();
        }
    }
	
	@SuppressWarnings("unchecked")
	public void update(ValueChangeEvent event){
		List<V> tValues;
        try{
			tValues = Arrays.asList((V[]) event.getNewValue());
            if(!tValues.isEmpty())
                wrappedConstraint = (AbstractConstraint<V>) new OrdinalConstraint((List<String>)tValues);
		}catch(ClassCastException ex1){
			BoxedException.throwBoxed(ex1);
		} catch (ContraintViolatedException ex2) {
			BoxedException.throwBoxed(ex2);
		}
	}

	@Getter @Setter
	private GregorianCalendar date1 = new GregorianCalendar();
	
	@Getter @Setter
	private GregorianCalendar date2 = new GregorianCalendar();
	
	
	@SuppressWarnings("unchecked")
	public AbstractConstraint<V> configure(){
		if(wrappedConstraint!=null && OrdinalConstraint.class.isInstance(wrappedConstraint))
			return wrappedConstraint;
		else{
			try {
				wrappedConstraint = (AbstractConstraint<V>) new DateConstraint(Arrays.asList(new GregorianCalendar[]{date1, date2}));
			} catch (ContraintViolatedException e) {
				e.printStackTrace();
			}
		}
		return wrappedConstraint;
	}

    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public String getFormatedDate1(){
        return sdf.format(date1.getTime());
    }

    public String getFormatedDate2(){
        return sdf.format(date2.getTime());
    }
}
