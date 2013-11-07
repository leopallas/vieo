/**
 * 
 */
package org.workin.utils.orm;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

/**
 * @author <a href="mailto:yao.angellin@gmail.com">Angellin Yao</a>
 *
 */
public class PersistenceUtils {

	/**
	 * Build Query String(SELECT COUNT(*) OR SELECT * ) Using class type.
	 * 
	 * @param clazz the specific class
	 * @param isCount whether this is a count query
	 * @return query string
	 */
	public static StringBuilder buildQueryString(final Class<?> clazz, final boolean isCount) {
		StringBuilder queryBuilder = new StringBuilder();

		if (isCount)
			queryBuilder.append(HQL_SELECT_COUNT_FROM);
		else
			queryBuilder.append(HQL_SELECT_FROM);

		queryBuilder.append(clazz.getName());
		queryBuilder.append(HQL_ALIAS_OBJECT);

		logger.debug(" Build query string: {}.", queryBuilder);

		return queryBuilder;
	}

	/**
	 * 
	 * Build Query String, Using class and values of parameters.
	 * 
	 * @param isCount
	 * @param clazz
	 * @param values
	 * @return
	 * 
	 */
	public static String buildQueryString(final Class<?> clazz, final boolean isCount, final String... values) {
		StringBuilder queryBuilder = buildQueryString(clazz, isCount);

		if (values != null && values.length > 0) {
			queryBuilder.append(HQL_KEYWORD_WHERE);
			for (int index = 0, size = values.length; index < size; index++) {
				queryBuilder.append(values[index]).append(HQL_KEYWORD_EQUALS).append(HQL_KEYWORD_QUESTION_MARK);
				if (index < size - 1) {
					queryBuilder.append(HQL_KEYWORD_AND);
				}
			}
		}
		logger.debug(" Build query string: {}.", queryBuilder);
		return queryBuilder.toString();
	}

	/**
	 * 
	 * Build Query String, Using class and values of parameters.
	 * 
	 * @param clazz
	 * @param params
	 * 
	 * @return string
	 * 
	 */
	public static String buildQueryString(final Class<?> clazz, final boolean isCount, final Map<String, ?> params) {
		StringBuilder queryBuilder = buildQueryString(clazz, isCount);

		if (!CollectionUtils.isEmpty(params)) {
			queryBuilder.append(HQL_KEYWORD_WHERE);

			for (Map.Entry<String, ?> entry : params.entrySet()) {
				queryBuilder.append(entry.getKey()).append(HQL_KEYWORD_EQUALS).append(":").append(entry.getKey())
						.append(HQL_KEYWORD_AND);
			}

			if (queryBuilder.lastIndexOf(HQL_KEYWORD_AND) == (queryBuilder.length() - 5)) {
				queryBuilder.delete(queryBuilder.length() - 5, queryBuilder.length());
			}
		}

		logger.info(" Build query string: {}", queryBuilder.toString());
		return queryBuilder.toString();
	}

	public static final transient Logger logger = LoggerFactory.getLogger(PersistenceUtils.class);

	public static final String HQL_SELECT_COUNT_FROM = "SELECT COUNT(*) as totalCount FROM ";
	public static final String HQL_SELECT_FROM = "SELECT obj FROM ";
	public static final String HQL_ALIAS_OBJECT = " obj";

	// Define HQL Key Word.
	public static final String HQL_KEYWORD_OR = " OR ";
	public static final String HQL_KEYWORD_AND = " AND ";
	public static final String HQL_KEYWORD_EQUALS = " = ";
	public static final String HQL_KEYWORD_LIKE = " LIKE ";
	public static final String HQL_KEYWORD_QUESTION_MARK = "?";
	public static final String HQL_KEYWORD_WHERE = " WHERE ";

}
