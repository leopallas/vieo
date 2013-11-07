package org.workin.utils.reflection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ConvertUtils {

	/**
	 * Retrieve property(Use getter method) from element collection as a list.
	 * 
	 * @param collection
	 *            Source collection.
	 * @param propertyName
	 *            The property name need to convert.
	 */
	public static List convertElementPropertyToList(final Collection collection, final String propertyName) {
		List list = new ArrayList();

		try {
			for (Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}

		return list;
	}

	/**
	 * Retrieve property(Use getter method) from element collection as a string with a specific separator.
	 * 
	 * @param collection
	 *            Source collection.
	 * @param propertyName
	 *            The property name need to convert.
	 * @param separator
	 *            Specific separator.
	 */
	public static String convertElementPropertyToString(final Collection collection, final String propertyName,
			final String separator) {
		List list = convertElementPropertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

}
