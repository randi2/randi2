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
package de.randi2.model.exceptions;

import org.hibernate.validator.InvalidValue;

@SuppressWarnings("serial")
public class ValidationException extends RuntimeException {

	private InvalidValue[] invalids;
	private String[] messages;

	public ValidationException(InvalidValue[] _invalids) {
		this.invalids = _invalids.clone();
	}
	
	public String[] getMessages(){
		if(messages == null){
			messages = new String[invalids.length];
			for(int i = 0; i< messages.length; i++){
				messages[i] = invalids[i].getMessage();
			}
		}
		return messages.clone();
	}
	
	public InvalidValue[] getInvalids(){
		return this.invalids.clone();
	}

}
