package de.randi2.model;
//TODO Comment!
public enum GrantedAuthorityEnum {

	/**
	 * User without an account
	 */
	ROLE_ANONYMOUS,
	/**
	 * Every user with an account
	 */
	ROLE_USER,
	/**
	 * Investigator (German: Pruefarzt)
	 */
	ROLE_INVESTIGATOR,
	/**
	 * Principial Investigator (German: Studienleiter)
	 */
	ROLE_P_INVASTIGATOR,
	/**
	 * Statistician
	 */
	ROLE_STATISTICIAN,
	/**
	 * Monitor
	 */
	ROLE_MONITOR,
	/**
	 * Administrator
	 */
	ROLE_ADMIN,
	/**
	 * SysOp - SPECIAL ROLE!
	 */
	ROLE_SYSOP;

}
