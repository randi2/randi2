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
package de.randi2.utility.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String>{

	private int min;
	private int max;
	private int hash_length;
	
	@Override
	public void initialize(Password parameters) {
		min = parameters.min();
		max = parameters.max();
		hash_length = parameters.hash_length();
		
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext constraintContext) {
		if(password==null)	return false;
		
		if(password.length()<= max && password.length()>= min){
			String pLetter = ".*[A-Za-z].*";
			String pNumber = ".*[0-9].*";
			String pSpecialSign = ".*[-\\[\\]\\^,.#+;:_'*!\"§$%&/()@{}=?|<>\\\\].*";
			return password.matches(pLetter) && password.matches(pNumber)
					&& password.matches(pSpecialSign);			
		}else if(password.length()==hash_length) return true;
		
		return false;
	}



}
