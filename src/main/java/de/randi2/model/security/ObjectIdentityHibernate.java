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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

import org.springframework.security.acls.objectidentity.ObjectIdentity;

import de.randi2.model.AbstractDomainObject;

@Entity
@Data
public class ObjectIdentityHibernate implements ObjectIdentity, Serializable {


	private static final long serialVersionUID = -5277986931816599596L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;
	
	private Class<? extends AbstractDomainObject> javaType;
	private Long identifier;
	
	public ObjectIdentityHibernate() {
		super();
	}

	public ObjectIdentityHibernate(Class<? extends AbstractDomainObject> javaType, long identifier) {
		super();
		this.javaType = javaType;
		this.identifier = identifier;
	}


	public Class<? extends AbstractDomainObject> getJavaType() {
		
		return javaType;
	}
	
	public void setJavaType(Class<? extends AbstractDomainObject> javaType) {
		this.javaType = javaType;
	}


}
