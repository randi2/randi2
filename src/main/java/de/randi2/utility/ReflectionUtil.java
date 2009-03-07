/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.randi2.utility;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author jthoenes
 */
public final class ReflectionUtil {

	private ReflectionUtil() {
		throw new RuntimeException();
	}

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
		} catch (NullPointerException x) {
			throw new ClassNotFoundException(pckgname + " (" + directory + ") does not appear to be a valid package");
		} catch (UnsupportedEncodingException x) {
			x.printStackTrace();
		}
		return classes;
	}

	public static Map<Field, Method> getPropertyWithGetter(Object o) {
		return getPropertyWithGetter(o.getClass());
	}

	public static Map<Field, Method> getPropertyWithGetter(Class<?> klass) {
		Map<Field, Method> properties = new HashMap<Field, Method>();
		for (Method getter : getGetters(klass)) {
			for (Method setter : getSetters(klass)) {
				if (!Modifier.isStatic(getter.getModifiers()) &&
						Modifier.isPublic(getter.getModifiers()) &&
						!Modifier.isStatic(setter.getModifiers()) &&
						Modifier.isPublic(setter.getModifiers()) &&
						getter.getName().substring(3).equals(
						setter.getName().substring(3))) {
					for (Field field : klass.getDeclaredFields()) {
						if (field.getName().equals(
								getPropertyName(getter))) {
							properties.put(field, getter);
						}
					}
				}
			}
		}

		return properties;
	}

	public static Set<Method> getGetters(Object o) {
		return getGetters(o.getClass());
	}

	public static Set<Method> getGetters(Class<? extends Object> klass) {
		Set<Method> getters = new HashSet<Method>();
		for (Method m : Arrays.asList(klass.getDeclaredMethods())) {
			if (m.getName().startsWith("get") &&
					m.getParameterTypes().length == 0 &&
					!void.class.equals(m.getReturnType())) {
				getters.add(m);
			}
		}
		return getters;
	}

	public static Set<Method> getSetters(Object o) {
		return getSetters(o.getClass());
	}

	public static Set<Method> getSetters(Class<? extends Object> klass) {
		Set<Method> getters = new HashSet<Method>();
		for (Method m : Arrays.asList(klass.getDeclaredMethods())) {
			if (m.getName().startsWith("set") &&
					m.getParameterTypes().length == 1 &&
					void.class.equals(m.getReturnType())) {
				getters.add(m);
			}
		}
		return getters;
	}

	public static String getPropertyName(Method m) {
		return m.getName().substring(3, 4).toLowerCase(Locale.ENGLISH) + m.getName().substring(4);
	}
}
