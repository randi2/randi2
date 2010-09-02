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

public class TelephonNumberValidation implements ConstraintValidator<TelephonNumber, String>{

	@Override
	public void initialize(TelephonNumber arg0) {
		
	}

	@Override
	public boolean isValid(String number, ConstraintValidatorContext constraintContext) {
		if (number == null || number.trim().equals("")) {
			return true;
		}
		number = number.trim();

		String[] digits = number.split("[-/() \t.]");
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < digits.length; i++) {
			buf.append(digits[i].trim());
		}
		number = buf.toString();
		if (number.charAt(0) == '+') {
			number = number.substring(1, number.length());
		}	
		if(!number.matches("^\\d+$")){
			return false;
		}
		
		return true;
	}
}
