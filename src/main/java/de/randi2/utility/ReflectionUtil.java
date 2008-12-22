/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.randi2.utility;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jthoenes
 */
public final class ReflectionUtil {

	/**
	 * This method returns all found classes in a specified package by going
	 * through the package directory.
	 *
	 * @param pckgname
	 *            the name of the package (e.g. de.randi2.model.criteria)
	 * @return all found classes
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>> getClasses(final String pckgname)
			throws ClassNotFoundException {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		// Get a File object for the package
		File directory = null;
		try {
			ClassLoader cld = Thread.currentThread().getContextClassLoader();
			if (cld == null) {
				throw new ClassNotFoundException("Can't get class loader.");
			}
			String path = pckgname.replace('.', '/');
			URL resource = cld.getResource(path);
			if (resource == null) {
				throw new ClassNotFoundException("No resource for " + path);
			}
			directory = new File(URLDecoder.decode(resource.getPath(), "UTF-8"));
		} catch (NullPointerException x) {
			throw new ClassNotFoundException(pckgname + " (" + directory + ") does not appear to be a valid package");
		} catch (UnsupportedEncodingException x) {
			x.printStackTrace();
		}
		if (directory.exists()) {
			// Get the list of the files contained in the package
			String[] files = directory.list();
			for (int i = 0; i < files.length; i++) {
				// we are only interested in .class files
				if (files[i].endsWith(".class")) {
					// removes the .class extension
					classes.add(Class.forName(pckgname + '.' + files[i].substring(0, files[i].length() - 6)));
				}
			}
		} else {
			throw new ClassNotFoundException(pckgname + " does not appear to be a valid package");
		}
		return classes;
	}
}
