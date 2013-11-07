/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: ReflectionUtils.java 1303 2010-11-19 17:04:05Z calvinxiu $
 */
package org.workin.utils.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ReflectionUtils {

	public static final String CGLIB_CLASS_SEPARATOR = "$$";

	private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

	/**
	 * Invoke getter method from object
	 * 
	 * @param obj
	 * @param propertyName
	 * @return
	 */
	public static Object invokeGetterMethod(Object obj, String propertyName) {
		String getterMethodName = "get" + StringUtils.capitalize(propertyName);
		return invokeMethod(obj, getterMethodName, new Class[] {}, new Object[] {});
	}

	/**
	 * Invoke setter method. It will find the setter method by class type for value
	 * 
	 * @param obj
	 * @param propertyName
	 * @param value
	 */
	public static void invokeSetterMethod(Object obj, String propertyName, Object value) {
		invokeSetterMethod(obj, propertyName, value, null);
	}

	/**
	 * Invoke setter method.
	 * 
	 * @param obj
	 * @param propertyName
	 * @param value
	 * @param propertyType
	 * 			Used for finding the setter method, and it will be replace with class type of the value if value is null.
	 */
	public static void invokeSetterMethod(Object obj, String propertyName, Object value, Class<?> propertyType) {
		Class<?> type = propertyType != null ? propertyType : value.getClass();
		String setterMethodName = "set" + StringUtils.capitalize(propertyName);
		invokeMethod(obj, setterMethodName, new Class[] { type }, new Object[] { value });
	}

	/**
	 * Read the property value of the object without using getter method, no matter the method is private or protected.
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(final Object obj, final String fieldName) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			logger.error("Impossibly throw errors: {}", e.getMessage());
		}
		return result;
	}

	/**
	 * Set the property value of the object without using getter method, no matter the method is private or protected.
	 * 
	 * @param obj
	 * @param fieldName
	 * @param value
	 */
	public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}

		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			logger.error("Impossibly throw errors: {}", e.getMessage());
		}
	}

	/**
	 * Iterator the super class for object, and retrieve the DeclareField, then this method will be forced set to available.
	 * If DeclareFileld not find when super class is Object.class, then return null.
	 * This method is used for situation of one time invoking.
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Field getAccessibleField(final Object obj, final String fieldName) {
		Assert.notNull(obj, "object could not be null.");
		Assert.hasText(fieldName, "fieldName");
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {//NOSONAR
				// Field is not defined in this class, continue.
			}
		}
		return null;
	}

	/**
	 * Get actual class type for the object AOPed by CGLIB.
	 * 
	 * @param clazz
	 * @return
	 */
	public static Class<?> getUserClass(Class<?> clazz) {
		if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null && !Object.class.equals(superClass)) {
				return superClass;
			}
		}
		return clazz;
	}

	/**
	 * Directly invoke the specific method, no matter the method is private or protected.
	 * 
	 * @param obj
	 * @param methodName
	 * @param parameterTypes
	 * @param args
	 * @return
	 */
	public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
			final Object[] args) {
		Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
		}

		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * Iterator the super class for object, and retrieve the DeclareField, then this method will be forced set to available.
	 * If DeclareFileld not find when super class is Object.class, then return null.
	 * This method is used for situation of multiple invoking. 1) Use this method to get Method; 2) Invoke Method.invoke(Object obj, Object... args)
	 * 
	 * @param obj
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 */
	public static Method getAccessibleMethod(final Object obj, final String methodName,
			final Class<?>... parameterTypes) {
		Assert.notNull(obj, "object could not be null.");

		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Method method = superClass.getDeclaredMethod(methodName, parameterTypes);

				method.setAccessible(true);

				return method;

			} catch (NoSuchMethodException e) {//NOSONAR
				// Field is not defined in this class, continue.
			}
		}
		return null;
	}

	/**
	 * Get super generic type defined in class. If not found, then return Object.class
	 * eg.
	 * public UserDao extends HibernateDao<User>

	 * @param clazz The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	public static <T> Class<T> getSuperClassGenericType(final Class clazz) {
		return getSuperClassGenericType(clazz, 0);
	}

	/**	 
	 * Get super generic type defined in class. If not found, then return Object.class
	 * eg.
	 * public UserDao extends HibernateDao<User，Long>
	 *
	 * @param clazz The class to introspect
	 * @param index the Index of the generic declaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */
	public static Class getSuperClassGenericType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	/**
	 * Translate checked exception occurred when reflect to unchecked exception.
	 * @param e
	 * @return
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException("Reflection Exception.", e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}
}
