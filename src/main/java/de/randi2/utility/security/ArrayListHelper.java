package de.randi2.utility.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

public final class ArrayListHelper {

	public static List<Sid> sidsOf(Sid... sids) {
		List<Sid> sidList = new ArrayList<Sid>();
		for (Sid sid : sids) {
			sidList.add(sid);
		}
		return sidList;
	}

	public static List<Permission> permissionsOf(Permission... permissions) {
		List<Permission> permissionList = new ArrayList<Permission>();
		for (Permission permission : permissions) {
			permissionList.add(permission);
		}
		return permissionList;
	}

}
