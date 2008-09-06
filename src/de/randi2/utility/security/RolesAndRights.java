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
package de.randi2.utility.security;

import org.springframework.orm.hibernate3.HibernateTemplate;

import de.randi2.dao.HibernateAclService;
import de.randi2.model.AbstractDomainObject;
import de.randi2.model.GrantedAuthorityEnum;
import de.randi2.model.Login;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.TrialSubject;
import de.randi2.model.security.PermissionHibernate;

/**
 * <p>
 * This class realize the mapping between the roles and rights in the RANDI2
 * system.
 * </p>
 * 
 * @author Lukasz Plotnicki <lplotni@users.sourceforge.net>
 */
public class RolesAndRights {

	//TODO Do we really need this template object
	private HibernateTemplate template;
	private HibernateAclService aclService;

	public void initializeRoles() {
		// TODO This Method should be called just after the system installation
		// to create the general rights for RANDI2 roles
		
		//TODO Example
		// The investigator has the permission to create a Trial-Subject
		aclService.createAclwithPermissions(new TrialSubject(),
				GrantedAuthorityEnum.ROLE_INVESTIGATOR.toString(),
				new PermissionHibernate[] { PermissionHibernate.CREATE });
	}
	
	public HibernateTemplate getTemplate() {
		return template;
	}

	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}

	public HibernateAclService getAclService() {
		return aclService;
	}

	public void setAclService(HibernateAclService aclService) {
		this.aclService = aclService;
	}

	public void grantInvestigatorRights(AbstractDomainObject object, TrialSite scope){
		for(Login l : scope.getMembersWithSpecifiedRole(GrantedAuthorityEnum.ROLE_INVESTIGATOR)){
			if (object instanceof Login) {
				aclService.createAclwithPermissions(object, l.getUsername(),
						new PermissionHibernate[] { PermissionHibernate.READ});
			}
			//TODO here we must specify the rights for all other objects like TrialSite, Trial etc.
		}
	}
	
	//TODO the grantRights Methods must exist for every Role just like the registerRole Methods

	public void registerInvestigator(Login user) {
		// The investigator has the permission to change his own Login-Object
		aclService.createAclwithPermissions(user, user.getUsername(),
				new PermissionHibernate[] { PermissionHibernate.READ,
						PermissionHibernate.WRITE });
		// The investigator has the permission to change his own Person-Object
		aclService.createAclwithPermissions(user.getPerson(), user
				.getUsername(), new PermissionHibernate[] {
				PermissionHibernate.READ, PermissionHibernate.WRITE });
		
		//
		for (Trial t : user.getPerson().getCenter().getTrials()) {
			aclService.createAclwithPermissions(t, user
					.getUsername(), new PermissionHibernate[] {
					PermissionHibernate.READ});
		}
	}

}
