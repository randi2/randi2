 /* This file is part of RANDI2.
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
package de.randi2.jsf.exceptions;
/**
 * <p>
 * Exception class for the Registration-process.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 */
public class RegistrationException extends Exception{
	
	private static final long serialVersionUID = -753491874667682122L;
	
	public static String CENTER_ERROR = "Wrong/None center selected!";
	
	public static String PASSWORD_ERROR = "Wrong confirmation password!";
	
	public RegistrationException(String message){
		super(message);
	}
}
