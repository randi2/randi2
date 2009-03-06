package de.randi2.model.security;


import java.io.Serializable;
import javax.persistence.Embeddable;

import org.springframework.security.acls.AclFormattingUtils;
import org.springframework.security.acls.Permission;

@Embeddable
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
	

	public void setMask(int mask) {
		this.mask = mask;
	}


    public static final PermissionHibernate READ = new PermissionHibernate(1 << 0, 'R'); // 1
    public static final PermissionHibernate WRITE = new PermissionHibernate(1 << 1, 'W'); // 2
    public static final PermissionHibernate CREATE = new PermissionHibernate(1 << 2, 'C'); // 4
    public static final PermissionHibernate DELETE = new PermissionHibernate(1 << 3, 'D'); // 8
    public static final PermissionHibernate ADMINISTRATION = new PermissionHibernate(1 << 4, 'A'); // 16
    

	public char getCode() {
		return code;
	}


	public void setCode(char code) {
		this.code = code;
	}

	@Override
	public int getMask() {
		return this.mask;
	}

	@Override
	public String getPattern() {
	   return AclFormattingUtils.printBinary(mask, code);
	}
	

}
