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
package de.randi2.model.security;


import java.io.Serializable;
import javax.persistence.Embeddable;

import lombok.Data;

import org.springframework.security.acls.AclFormattingUtils;
import org.springframework.security.acls.Permission;

@Embeddable
@Data
public class PermissionHibernate implements Permission, Serializable {

	private static final long serialVersionUID = -2551309525159046911L;

	private char code;
	private int mask;

	public PermissionHibernate(int mask, char code) {
		this.mask = mask;
		this.code = code;
	}
	
	public PermissionHibernate() {
	}
	




    public static final PermissionHibernate READ = new PermissionHibernate(1 << 0, 'R'); // 1
    public static final PermissionHibernate WRITE = new PermissionHibernate(1 << 1, 'W'); // 2
    public static final PermissionHibernate CREATE = new PermissionHibernate(1 << 2, 'C'); // 4
    public static final PermissionHibernate DELETE = new PermissionHibernate(1 << 3, 'D'); // 8
    public static final PermissionHibernate ADMINISTRATION = new PermissionHibernate(1 << 4, 'A'); // 16
    
	@Override
	public String getPattern() {
	   return AclFormattingUtils.printBinary(mask, code);
	}
	

}
