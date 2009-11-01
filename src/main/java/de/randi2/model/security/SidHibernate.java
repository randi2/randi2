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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

import org.springframework.security.acls.sid.GrantedAuthoritySid;
import org.springframework.security.acls.sid.PrincipalSid;
import org.springframework.security.acls.sid.Sid;

@Entity
public class SidHibernate implements Sid{

	private static final long serialVersionUID = -3238954373971096868L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Getter @Setter
	private long id;
	
	@Getter @Setter
	private String sidname;
	
	public SidHibernate() {
	}

	
	public SidHibernate(String sidname) {
		this.sidname=sidname;
	}


	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		else if (obj instanceof SidHibernate)
			return (this.sidname.equals(((SidHibernate)obj).sidname));
		//this is necessary to compare a SidHibernate with a PrincipalSid (Security framework)    
		else if (obj instanceof PrincipalSid)
			return (this.sidname.equals(((PrincipalSid)obj).getPrincipal()));
		//this is necessary to compare a SidHibernate with a GrantedAuthoritySid (Security framework)    
		else if (obj instanceof GrantedAuthoritySid)
			return (this.sidname.equals(((GrantedAuthoritySid)obj).getGrantedAuthority()));
		else
			return false;
	}
	
	@Override
	public int hashCode() {
		return this.sidname.hashCode();
	}
	
	@Override
	public String toString() {
		return sidname;
	}

}
