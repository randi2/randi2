package de.randi2.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.NotEmpty;

import de.randi2.model.security.PermissionHibernate;

@Entity
public class Role extends AbstractDomainObject {

	private static final long serialVersionUID = 7986310852028135642L;

	public static final Role ROLE_INVESTIGATOR = new Role(
			"ROLE_INVESTIGATOR", false, false, true, true, false, false, true,
			true, false, true, false, true, false, true, true, false, true,
			false, true, false, true, true, false, true, false, false, false,
			false, new ArrayList<Role>());

	
	public static final Role ROLE_STATISTICAN = new Role("ROLE_STATISTICAN",
			false, false, true, true, false, false, true, true, true, true,
			false, true, false, true, true, false, true, false, true, false,
			true, true, false, false, false, true, false, false, null);

	public static final Role ROLE_MONITOR = new Role("ROLE_MONITOR",
			false, false, true, true, false, false, true, true, true, true,
			false, true, false, true, true, false, true, false, true, false,
			true, true, false, false, false, true, false, false, null);

	public static final Role ROLE_ADMIN = new Role("ROLE_ADMIN", true, false,
			true, true, true, true, true, true, false, false, true, false,
			true, false, true, true, false, false, false, false, false, true,
			false, false, false, false, false, true, null);

	public static final Role ROLE_ANONYMOUS = new Role("ROLE_ANONYMOUS",
			false, false, true, false, false, false, false, false, false,
			false, true, false, false, false, true, false, false, false, false,
			false, false, false, false, false, false, false, false, false, null);

	public static final Role ROLE_USER = new Role("ROLE_USER", false, true,
			false, true, false, false, true, true, false, true, false, true,
			false, true, false, false, true, false, true, false, true, false,
			false, false, false, false, false, false, null);
	
	public static final Role ROLE_P_INVESTIGATOR = new Role("ROLE_P_INVESTIGATOR", false, false, true, true, false, false, true,
			true, false, true, true, true, true, true, true, false, true,
			true, true, true, true, true, false, false, false, true, false,
			false, new ArrayList<Role>(Arrays.asList(new Role[]{Role.ROLE_INVESTIGATOR,Role.ROLE_STATISTICAN,Role.ROLE_MONITOR})));


	
	
	@Column(unique=true)
	@NotEmpty
	@Getter @Setter
	private String name;

	// to create trial site is no scope necessary
	@Getter @Setter
	private boolean createTrialSite = false;

	// scope for read trial site objects
	@Getter @Setter
	private boolean scopeTrialSiteView = true;
	@Getter @Setter
	private boolean readTrialSite = true;

	// scope for write trial site objects
	@Getter @Setter
	private boolean scopeTrialSiteWrite = false;
	@Getter @Setter
	private boolean writeTrialSite = false;

	@Getter @Setter
	private boolean adminTrialSite = false;

	@Transient
	private Set<PermissionHibernate> trialSitePermissions = null;

	@Getter @Setter
	private boolean writeOwnUser = true;
	@Getter @Setter
	private boolean readOwnUser = true;
	@Getter @Setter
	private boolean adminOwnUser = false;

	@Transient
	private Set<PermissionHibernate> ownUserPermissions = null;

	// Scope for create user objects
	@Getter @Setter
	private boolean scopeUserCreate = true;
	@Getter @Setter
	private boolean createUser = false;

	// scope for write user objects
	@Getter @Setter
	private boolean scopeUserWrite = false;
	@Getter @Setter
	private boolean writeOtherUser = false;

	// scope for read user objects
	@Getter @Setter
	private boolean scopeUserRead = true;
	@Getter @Setter
	private boolean readOtherUser = true;
	@Getter @Setter
	private boolean adminOtherUser = false;

	// scope for trial objects
	@Getter @Setter
	private boolean scopeTrialCreat = true;
	@Getter @Setter
	private boolean createTrial = false;

	@Getter @Setter
	private boolean scopeTrialWrite = true;
	@Getter @Setter
	private boolean writeTrial = false;

	@Getter @Setter
	private boolean scopeTrialRead = true;
	@Getter @Setter
	private boolean readTrial = false;
	@Getter @Setter
	private boolean adminTrial = false;

	@Getter @Setter
	private boolean createTrialSubject = false;
	@Getter @Setter
	private boolean writeTrialSubject = false;
	@Getter @Setter
	private boolean readTrialSubject = false;
	@Getter @Setter
	private boolean adminTrialSubject = false;

	@Getter @Setter
	private boolean createRole = false;
	@ManyToMany
	@Getter @Setter
	private List<Role> rolesToAssign = new ArrayList<Role>();

	public Role(){
	}

	public Role(String name, boolean createTrialSite,
			boolean scopeTrialSiteView, boolean readTrialSite,
			boolean scopeTrialSiteWrite, boolean writeTrialSite,
			boolean adminTrialSite, boolean writeOwnUser, boolean readOwnUser,
			boolean adminOwnUser, boolean scopeUserCreate, boolean createUser,
			boolean scopeUserWrite, boolean writeOtherUser,
			boolean scopeUserRead, boolean readOtherUser,
			boolean adminOtherUser, boolean scopeTrialCreat,
			boolean createTrial, boolean scopeTrialWrite, boolean writeTrial,
			boolean scopeTrialRead, boolean readTrial, boolean adminTrial,
			boolean createTrialSubject, boolean writeTrialSubject,
			boolean readTrialSubject, boolean adminTrialSubject,
			boolean createRole, List<Role> rolesToAssign) {
		super();
		this.name = name;
		this.createTrialSite = createTrialSite;
		this.scopeTrialSiteView = scopeTrialSiteView;
		this.readTrialSite = readTrialSite;
		this.scopeTrialSiteWrite = scopeTrialSiteWrite;
		this.writeTrialSite = writeTrialSite;
		this.adminTrialSite = adminTrialSite;
		this.writeOwnUser = writeOwnUser;
		this.readOwnUser = readOwnUser;
		this.adminOwnUser = adminOwnUser;
		this.scopeUserCreate = scopeUserCreate;
		this.createUser = createUser;
		this.scopeUserWrite = scopeUserWrite;
		this.writeOtherUser = writeOtherUser;
		this.scopeUserRead = scopeUserRead;
		this.readOtherUser = readOtherUser;
		this.adminOtherUser = adminOtherUser;
		this.scopeTrialCreat = scopeTrialCreat;
		this.createTrial = createTrial;
		this.scopeTrialWrite = scopeTrialWrite;
		this.writeTrial = writeTrial;
		this.scopeTrialRead = scopeTrialRead;
		this.readTrial = readTrial;
		this.adminTrial = adminTrial;
		this.createTrialSubject = createTrialSubject;
		this.writeTrialSubject = writeTrialSubject;
		this.readTrialSubject = readTrialSubject;
		this.adminTrialSubject = adminTrialSubject;
		this.createRole = createRole;
		if(rolesToAssign == null){
			this.rolesToAssign = new ArrayList<Role>();
		}else{
			this.rolesToAssign = rolesToAssign;
		}
	}

	

	/**
	 * @return the trialSitePermissions
	 */
	public Set<PermissionHibernate> getTrialSitePermissions() {
		if (trialSitePermissions == null) {
			trialSitePermissions = new HashSet<PermissionHibernate>();
			if (writeTrialSite) {
				trialSitePermissions.add(PermissionHibernate.WRITE);
			}
			if (readTrialSite) {
				trialSitePermissions.add(PermissionHibernate.READ);
			}
			if (adminTrialSite) {
				trialSitePermissions.add(PermissionHibernate.ADMINISTRATION);
			}
		}
		return trialSitePermissions;
	}

	/**
	 * @return the ownUserPermissions
	 */
	public Set<PermissionHibernate> getOwnUserPermissions() {
		if (ownUserPermissions == null) {
			ownUserPermissions = new HashSet<PermissionHibernate>();
			if (writeOwnUser) {
				ownUserPermissions.add(PermissionHibernate.WRITE);
			}
			if (readOwnUser) {
				ownUserPermissions.add(PermissionHibernate.READ);
			}
			if (adminOwnUser) {
				ownUserPermissions.add(PermissionHibernate.ADMINISTRATION);
			}
		}

		return ownUserPermissions;
	}
	
	@Override
	public boolean equals(Object o){
		if(o.getClass().isInstance(this)){
			return ((Role)o).getName().equals(this.getName());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	
	@Override
	public String getUIName() {
		return name;
	}

}