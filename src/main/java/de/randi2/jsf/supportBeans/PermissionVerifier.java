package de.randi2.jsf.supportBeans;

import org.springframework.security.acls.Acl;
import org.springframework.security.acls.AclService;
import org.springframework.security.acls.NotFoundException;
import org.springframework.security.acls.sid.PrincipalSid;
import org.springframework.security.acls.sid.Sid;

import de.randi2.jsf.controllerBeans.LoginHandler;
import de.randi2.model.Login;
import de.randi2.model.Trial;
import de.randi2.model.TrialSite;
import de.randi2.model.security.ObjectIdentityHibernate;
import de.randi2.model.security.PermissionHibernate;

public class PermissionVerifier {

	private AclService aclService;

	public void setAclService(AclService aclService) {
		this.aclService = aclService;
	}

	private LoginHandler loginHandler;

	public LoginHandler getLoginHandler() {
		return loginHandler;
	}

	public void setLoginHandler(LoginHandler loginHandler) {
		this.loginHandler = loginHandler;
	}

	public boolean isAllowedCreateUser() {
		return loginHandler.getLoggedInUser().hasPermission(Login.class,
				PermissionHibernate.CREATE);
	}

	public boolean isAllowedWriteUser() {
		return loginHandler.getLoggedInUser().hasPermission(Login.class,
				PermissionHibernate.WRITE);
	}
	
	public boolean isAllowedCreateTrial() {
		return loginHandler.getLoggedInUser().hasPermission(Trial.class,
				PermissionHibernate.CREATE);
	}

	public boolean isAllowedReadTrial() {
		return loginHandler.getLoggedInUser().hasPermission(Trial.class,
				PermissionHibernate.READ);
	}

	public boolean isAllowedCreateTrialSite() {
		return loginHandler.getLoggedInUser().hasPermission(TrialSite.class,
				PermissionHibernate.CREATE);
	}

	public boolean isAllowedReadTrialSite() {
		return loginHandler.getLoggedInUser().hasPermission(TrialSite.class,
				PermissionHibernate.READ);
	}

	public boolean isAllowedEditTrialSite(TrialSite trialSite) {
		try {
			Acl acl = aclService.readAclById(new ObjectIdentityHibernate(
					TrialSite.class, trialSite.getId()),
					new Sid[] { new PrincipalSid(loginHandler.getLoggedInUser()
							.getUsername()) });
			System.out.println(acl);
			return acl.isGranted(
					new PermissionHibernate[] { PermissionHibernate.WRITE },
					new Sid[] { new PrincipalSid(loginHandler.getLoggedInUser()
							.getUsername()) }, false);
		} catch (NotFoundException e) {
			System.out.println(e);
			return false;
		}
	}
	
	public boolean isAllowedEditUser(Login user) {
		try {
			Acl acl = aclService.readAclById(new ObjectIdentityHibernate(
					Login.class, user.getId()),
					new Sid[] { new PrincipalSid(loginHandler.getLoggedInUser()
							.getUsername()) });
			return acl.isGranted(
					new PermissionHibernate[] { PermissionHibernate.WRITE },
					new Sid[] { new PrincipalSid(loginHandler.getLoggedInUser()
							.getUsername()) }, false);
		} catch (NotFoundException e) {
			return false;
		}
	}
	
	public boolean isAllowedChangeUserTrialSite(){
		return loginHandler.isEditable() && isAllowedWriteUser();
	}
}
