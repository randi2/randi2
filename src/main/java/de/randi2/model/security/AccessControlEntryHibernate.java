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

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

import org.springframework.security.acls.AccessControlEntry;
import org.springframework.security.acls.Acl;


@Entity
@Data
public class AccessControlEntryHibernate implements AccessControlEntry, Serializable {

	private static final long serialVersionUID = 6843101926609540483L;
	
	@ManyToOne(targetEntity=AclHibernate.class)
	private Acl acl;
	
	@Embedded
	private PermissionHibernate permission;
	
	private String roleName;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@ManyToOne(targetEntity=SidHibernate.class)
	private SidHibernate sid;
	private boolean granting = true;
	
	
}
